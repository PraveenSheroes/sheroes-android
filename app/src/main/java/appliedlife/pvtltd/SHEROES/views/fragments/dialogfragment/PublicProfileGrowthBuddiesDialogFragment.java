package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorDetailItem;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;

/**
 * Created by Praveen_Singh on 03-08-2017.
 */

public class PublicProfileGrowthBuddiesDialogFragment extends BaseDialogFragment implements HomeView {
    private static final String SCREEN_LABEL = "Champions Screen";
    private final String TAG = LogUtils.makeLogTag(PublicProfileGrowthBuddiesDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_growth_detail_list)
    RecyclerView mRecyclerView;
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.pb_growth_detail_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_growth)
    SwipeRefreshLayout mSwipeView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList mPullRefreshList;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    @Bind(R.id.li_no_growth_list_result)
    LinearLayout mLiNoResult;
    private MentorDetailItem mMentorDetailItem;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.public_profile_list, container, false);
        ButterKnife.bind(this, view);
        //  getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mHomePresenter.attachView(this);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.GROWTH_PUBLIC_PROFILE, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mRecyclerView, mLayoutManager, mFragmentListRefreshData, mHomePresenter) {
            @Override
            public void onHide() {
            }

            @Override
            public void onShow() {
            }

            @Override
            public void dismissReactions() {

            }
        });
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mHomePresenter.getPublicProfileMentorListFromPresenter(mAppUtils.pubicProfileRequestBuilder(mFragmentListRefreshData.getPageNo()));
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
                mPullRefreshList = new SwipPullRefreshList();
                mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
                mHomePresenter.getPublicProfileMentorListFromPresenter(mAppUtils.pubicProfileRequestBuilder(mFragmentListRefreshData.getPageNo()));
            }
        });
        setCancelable(true);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        return view;
    }


    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        if (baseResponse instanceof PublicProfileListResponse) {
            PublicProfileListResponse publicProfileListResponse = (PublicProfileListResponse) baseResponse;
            growthBuddiesSuccess(publicProfileListResponse);
        } else if (baseResponse instanceof MentorFollowUnfollowResponse) {
            MentorFollowUnfollowResponse mentorFollowUnfollowResponse = (MentorFollowUnfollowResponse) baseResponse;
            mentorFollowUnFollowSuccess(mentorFollowUnfollowResponse);
        }
    }

    protected void mentorFollowUnFollowSuccess(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
        switch (mentorFollowUnfollowResponse.getStatus()) {
            case AppConstants.SUCCESS:
                mAdapter.notifyItemChanged(mMentorDetailItem.getItemPosition(), mMentorDetailItem);
                break;
            case AppConstants.FAILED:
                if (!mMentorDetailItem.isFollowed()) {
                    mMentorDetailItem.setFollowed(true);
                } else {
                    mMentorDetailItem.setFollowed(false);
                }
                mAdapter.notifyItemChanged(mMentorDetailItem.getItemPosition(), mMentorDetailItem);
                showError(mentorFollowUnfollowResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), FOLLOW_UNFOLLOW);
                break;
            default:
                showError(getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);
        }

    }

    protected void growthBuddiesSuccess(PublicProfileListResponse publicProfileListResponse) {
        switch (publicProfileListResponse.getStatus()) {
            case AppConstants.SUCCESS:
                List<MentorDetailItem> mentorDetailItemList = publicProfileListResponse.getMentorDetailItems();
                growthBuddiesList(mentorDetailItemList);
                break;
            case AppConstants.FAILED:
                showError(publicProfileListResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_LIKE_UNLIKE);
                break;
            default:
                showError(getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
        }

    }

    private void growthBuddiesList(List<MentorDetailItem> mentorDetailItemList) {
        if (StringUtil.isNotEmptyCollection(mentorDetailItemList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(mentorDetailItemList);
            List<FeedDetail> data=mPullRefreshList.getFeedResponses();
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.notifyItemRangeChanged(mentorDetailItemList.size()+1,data.size());
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            // mLiNoResult.setVisibility(View.VISIBLE);
        } else {
            mLiNoResult.setVisibility(View.GONE);
        }
        mSwipeView.setRefreshing(false);
    }

    public void followUnfollowRequest(MentorDetailItem mentorDetailItem) {
        mMentorDetailItem = mentorDetailItem;
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(mentorDetailItem.getEntityOrParticipantId());
        if (mMentorDetailItem.isFollowed()) {
            mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest);
        } else {
            mHomePresenter.getFollowFromPresenter(publicProfileListRequest);
        }

    }

    public void notifyList(MentorDetailItem mentorDetailItem) {
        mAdapter.setMentoreDataOnPosition(mentorDetailItem,mentorDetailItem.getItemPosition());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_growth_buddies_back)
    public void onGrowthBuddyBack() {
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}