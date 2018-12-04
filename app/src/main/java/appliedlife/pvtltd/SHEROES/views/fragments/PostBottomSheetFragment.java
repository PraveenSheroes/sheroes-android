package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.MyCommunities;
import appliedlife.pvtltd.SHEROES.models.entities.post.PollOptionType;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateStoryActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.CommunityListAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.PollSurveyTypeAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;

/**
 * Created by ujjwal on 27/10/17.
 */

public class PostBottomSheetFragment extends BottomSheetDialogFragment implements HomeView {
    private static final String SCREEN_LABEL = "PostBottomSheetFragment";
    // region Inject variables
    @Inject
    HomePresenter mHomePresenter;

    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;
    // endregion'

    // region View variables
    @Bind(R.id.community_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.tv_choose_community)
    TextView mTvChooseCommunity;

    @BindDimen(R.dimen.poll_type_recyler_list_margin)
    int mPollTypeRecyclerListMargin;

    // endregion

    // region Member variables
    private CommunityListAdapter mCommunityListAdapter;
    public List<Community> mCommunityList = new ArrayList<>();
    private MyCommunities mMyCommunities;
    private CommunityPostActivity mCommunityPostActivity;
    private CreateStoryActivity mCreateStoryActivity;
    private PollSurveyTypeAdapter mPollSurveyTypeAdapter;
    // endregion

    //region Fragment LifeCycle Methods
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof CommunityPostActivity) {
            mCommunityPostActivity = (CommunityPostActivity) context;
        } else {
            mCreateStoryActivity = (CreateStoryActivity) context;
        }
        super.onAttach(context);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        mHomePresenter.attachView(this);
        super.setupDialog(dialog, style);
        View containerView = View.inflate(getContext(), R.layout.dialog_community_list, null);
        dialog.setContentView(containerView);
        ButterKnife.bind(this, containerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        Parcelable parcelable;

        if (getArguments() != null) {
            if (getArguments().getParcelable(MyCommunities.MY_COMMUNITY_OBJ) != null) {
                mMyCommunities = new MyCommunities();
                parcelable = getArguments().getParcelable(MyCommunities.MY_COMMUNITY_OBJ);
                mMyCommunities = Parcels.unwrap(parcelable);
                mCommunityList = mMyCommunities.myCommunities;
                showCommunity();
            } else if (getArguments().getParcelable(PollOptionType.POLL_TYPE) != null) {
                mTvChooseCommunity.setText(getString(R.string.choose_poll));
                parcelable = getArguments().getParcelable(PollOptionType.POLL_TYPE);
                List<PollOptionType> pollOptionTypeList = Parcels.unwrap(parcelable);
                setPollTypeList(pollOptionTypeList);
                LinearLayout.LayoutParams photo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
                photo.setMargins(mPollTypeRecyclerListMargin, 0, 0, 0);
                mRecyclerView.setLayoutParams(photo);
            } else {
                mMyCommunities = new MyCommunities();
                setMyCommunityList();
                showCommunity();
            }
        } else {
            mMyCommunities = new MyCommunities();
            setMyCommunityList();
            showCommunity();
        }
    }

    private void setMyCommunityList() {
        mMyCommunities = new MyCommunities();
        boolean isFirstOtherSet = false;
        if (mAllCommunities != null && mAllCommunities.isSet() && !CommonUtil.isEmpty(mAllCommunities.get().feedDetails)) {
            for (FeedDetail feedDetail : mAllCommunities.get().feedDetails) {
                Community community = new Community();
                community.id = feedDetail.getIdOfEntityOrParticipant();
                community.name = feedDetail.getNameOrTitle();
                community.thumbImageUrl = feedDetail.getThumbnailImageUrl();
                community.isOwner = ((CommunityFeedSolrObj) feedDetail).isOwner();
                if (!((CommunityFeedSolrObj) feedDetail).isOwner() && !((CommunityFeedSolrObj) feedDetail).isMember() && !isFirstOtherSet) {
                    community.isFirstOther = true;
                    isFirstOtherSet = true;
                }
                mCommunityList.add(community);
            }
            mMyCommunities.myCommunities = new ArrayList<>(mCommunityList);
        } else {
            mHomePresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
        }
    }

    private void setPollTypeList(List<PollOptionType> pollOptionTypeList) {
        mPollSurveyTypeAdapter = new PollSurveyTypeAdapter(getActivity(), ((CommunityPostActivity) getActivity()), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mPollSurveyTypeAdapter);
        mPollSurveyTypeAdapter.setData(pollOptionTypeList);
        mPollSurveyTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        if (getActivity() instanceof CommunityPostActivity) {
            mCommunityPostActivity.showError(s, feedParticipationEnum);
        } else {
            if (getActivity() instanceof CreateStoryActivity) {
                mCreateStoryActivity.showError(s, feedParticipationEnum);
            }
        }
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        if (null != feedResponsePojo && !CommonUtil.isEmpty(feedResponsePojo.getFeedDetails())) {
            for (FeedDetail feedDetail : feedResponsePojo.getFeedDetails()) {
                Community community = new Community();
                community.id = feedDetail.getIdOfEntityOrParticipant();
                community.name = feedDetail.getNameOrTitle();
                community.thumbImageUrl = feedDetail.getThumbnailImageUrl();
                community.isOwner = ((CommunityFeedSolrObj) feedDetail).isOwner();
                mCommunityList.add(community);
            }
            mMyCommunities.myCommunities = new ArrayList<>(mCommunityList);
            showCommunity();
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    //endregion

    //region private methods
    private void showCommunity() {
        mTvChooseCommunity.setText(getString(R.string.choose_community));
        mRecyclerView.setAdapter(mCommunityListAdapter = new CommunityListAdapter(getActivity(), mCommunityList, new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                int position = mRecyclerView.getChildAdapterPosition(item);
                Community community = mCommunityList.get(position);
                clearCheck();
                mMyCommunities.myCommunities.get(position).isChecked = true;
                mCommunityListAdapter.notifyDataSetChanged();
                if (getActivity() instanceof CommunityPostActivity) {
                    mCommunityPostActivity.setMainCommunity(community, mMyCommunities);
                }
                dismiss();
            }
        }));
    }

    private void clearCheck() {
        if (!CommonUtil.isEmpty(mCommunityList)) {
            for (Community community : mCommunityList) {
                community.isChecked = false;
            }
        }
    }
    //endregion

    //region Public Static methods
    public static PostBottomSheetFragment showDialog(AppCompatActivity activity, String sourceScreen) {
        PostBottomSheetFragment postBottomSheetFragment = new PostBottomSheetFragment();
        Bundle args = new Bundle();
        postBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        postBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return postBottomSheetFragment;
    }

    public static PostBottomSheetFragment showDialog(AppCompatActivity activity, String sourceScreen, MyCommunities myCommunities) {
        PostBottomSheetFragment postBottomSheetFragment = new PostBottomSheetFragment();
        Bundle args = new Bundle();
        args.putParcelable(MyCommunities.MY_COMMUNITY_OBJ, Parcels.wrap(myCommunities));
        postBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        postBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return postBottomSheetFragment;
    }

    public static PostBottomSheetFragment showDialog(AppCompatActivity activity, String sourceScreen, List<PollOptionType> pollOptionTypeList) {
        PostBottomSheetFragment postBottomSheetFragment = new PostBottomSheetFragment();
        Bundle args = new Bundle();
        args.putParcelable(PollOptionType.POLL_TYPE, Parcels.wrap(pollOptionTypeList));
        postBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        postBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return postBottomSheetFragment;
    }

    @Override
    public void onConfigFetched() {

    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }
    //endregion
}
