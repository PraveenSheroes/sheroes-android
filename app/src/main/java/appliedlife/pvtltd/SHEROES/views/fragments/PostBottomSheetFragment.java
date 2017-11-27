package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.f2prateek.rx.preferences.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.MyCommunities;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.CommunityListAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;

/**
 * Created by ujjwal on 27/10/17.
 */

public class PostBottomSheetFragment extends BottomSheetDialogFragment implements HomeView {
    private static final String SCREEN_LABEL = "PostBottomSheetFragment";

    private CommunityListAdapter mCommunityListAdapter;
    public List<Community> mCommunityList = new ArrayList<>();
    private MyCommunities mMyCommunities;
    CommunityPostActivity mCommunityPostActivity;
    @Inject
    HomePresenter mHomePresenter;

    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;

    // region View variables
    @Bind(R.id.community_list)
    RecyclerView mRecyclerView;
    // endregion

    //region Fragment LifeCycle Methods
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getArguments()!=null){

        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        mCommunityPostActivity = (CommunityPostActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        mHomePresenter.attachView(this);
        super.setupDialog(dialog, style);
        View containerView = View.inflate(getContext(), R.layout.dialog_community_list, null);
        dialog.setContentView(containerView);
        ButterKnife.bind(this, containerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity()){
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
        mMyCommunities = new MyCommunities();
        if (getArguments().getParcelable(MyCommunities.MY_COMMUNITY_OBJ) != null) {
            parcelable = getArguments().getParcelable(MyCommunities.MY_COMMUNITY_OBJ);
            mMyCommunities = Parcels.unwrap(parcelable);
            mCommunityList = mMyCommunities.myCommunities;
        }else {
            setMyCommunityList();
        }
        showCommunity();
    }

    private void setMyCommunityList() {
        mMyCommunities = new MyCommunities();
        boolean isFirstOtherSet = false;
        if (mAllCommunities.isSet() && mAllCommunities.get() != null && !CommonUtil.isEmpty(mAllCommunities.get().feedDetails)) {
            for (FeedDetail feedDetail : mAllCommunities.get().feedDetails) {
                Community community = new Community();
                community.id = feedDetail.getIdOfEntityOrParticipant();
                community.name = feedDetail.getNameOrTitle();
                community.thumbImageUrl = feedDetail.getThumbnailImageUrl();
                community.isOwner = ((CommunityFeedSolrObj)feedDetail).isOwner();
                if (!((CommunityFeedSolrObj)feedDetail).isOwner() && !((CommunityFeedSolrObj)feedDetail).isMember() && !isFirstOtherSet) {
                    community.isFirstOther = true;
                    isFirstOtherSet = true;
                }
                mCommunityList.add(community);
            }
            mMyCommunities.myCommunities = new ArrayList<>(mCommunityList);
        }else {
            mHomePresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        mCommunityPostActivity.showError(s, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        if (null != feedResponsePojo && !CommonUtil.isEmpty(feedResponsePojo.getFeedDetails())) {
            for (FeedDetail feedDetail : feedResponsePojo.getFeedDetails()){
                Community community = new Community();
                community.id = feedDetail.getIdOfEntityOrParticipant();
                community.name = feedDetail.getNameOrTitle();
                community.thumbImageUrl = feedDetail.getThumbnailImageUrl();
                community.isOwner = ((CommunityFeedSolrObj)feedDetail).isOwner();
                mCommunityList.add(community);
            }
            mMyCommunities.myCommunities = new ArrayList<>(mCommunityList);
            showCommunity();
        }
    }

    @Override
    public void showHomeFeedList(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getNotificationListSuccess(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    //endregion

    //region private methods
    private void showCommunity() {
        mRecyclerView.setAdapter(mCommunityListAdapter = new CommunityListAdapter(getActivity(), mCommunityList, new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                int position = mRecyclerView.getChildAdapterPosition(item);
                Community community = mCommunityList.get(position);
                clearCheck();
                mMyCommunities.myCommunities.get(position).isChecked = true;
                mCommunityListAdapter.notifyDataSetChanged();
                mCommunityPostActivity.setMainCommunity(community, mMyCommunities);
                dismiss();
            }
        }));
    }

    private void clearCheck() {
        if(!CommonUtil.isEmpty(mCommunityList)){
            for (Community community : mCommunityList){
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

    public static PostBottomSheetFragment showDialog(AppCompatActivity activity, String sourceScreen,MyCommunities myCommunities) {
        PostBottomSheetFragment postBottomSheetFragment = new PostBottomSheetFragment();
        Bundle args = new Bundle();
        args.putParcelable(MyCommunities.MY_COMMUNITY_OBJ, Parcels.wrap(myCommunities));
        postBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        postBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return postBottomSheetFragment;
    }

    @Override
    public void invalidateLikeUnlike(Comment comment) {

    }
    //endregion
}
