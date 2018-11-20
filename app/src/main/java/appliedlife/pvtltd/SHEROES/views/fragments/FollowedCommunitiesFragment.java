package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileCommunityAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RecyclerRowDivider;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment.SELF_PROFILE;
import static appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment.USER_MENTOR_ID;

/**
 * Created by ravi on 11/01/18.
 * User's Joined Communities listing from profile
 */

public class FollowedCommunitiesFragment extends BaseFragment implements IProfileView, ProfileCommunityAdapter.OnItemClicked {

    public static final String SCREEN_LABEL = "Followed Communities Screen";

    private long userMentorId;
    private boolean isSelfProfile;
    private List<CommunityFeedSolrObj> profileCommunities;
    private FragmentListRefreshData mFragmentListRefreshData;
    private ProfileCommunityAdapter mAdapter;
    private SwipPullRefreshList mPullRefreshList;

    @Bind(R.id.communities)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_view_communities)
    SwipeRefreshLayout mSwipeView;

    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;

    @Inject
    AppUtils mAppUtils;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    ProfilePresenterImpl profilePresenter;

    public static FollowedCommunitiesFragment createInstance(long userId, String name, boolean isSelfProfile) {
        FollowedCommunitiesFragment followedCommunitiesFragment = new FollowedCommunitiesFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        bundle.putBoolean(SELF_PROFILE, isSelfProfile);
        followedCommunitiesFragment.setArguments(bundle);
        return followedCommunitiesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communities_list, container, false);
        profilePresenter.attachView(this);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            userMentorId = getArguments().getLong(USER_MENTOR_ID);
            isSelfProfile = getArguments().getBoolean(SELF_PROFILE);
        }

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.PROFILE_COMMUNITY_LISTING, AppConstants.NO_REACTION_CONSTANT);
        mFragmentListRefreshData.setSelfProfile(isSelfProfile);
        mFragmentListRefreshData.setMentorUserId(userMentorId);
        mentorSearchInListPagination(mFragmentListRefreshData);

        return view;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return profilePresenter;
    }

    private void mentorSearchInListPagination(FragmentListRefreshData fragmentListRefreshData) {

        if(getContext() ==null) return;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerRowDivider decoration = new RecyclerRowDivider(getContext(), ContextCompat.getColor(getContext(), R.color.on_board_work), 1);
        mRecyclerView.addItemDecoration(decoration);
        mAdapter = new ProfileCommunityAdapter(getContext(), isSelfProfile, this);
        mRecyclerView.setAdapter(mAdapter);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(profilePresenter, mRecyclerView, mLayoutManager, fragmentListRefreshData) {
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

        if (isSelfProfile) {
            profilePresenter.getPublicProfileCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));
        } else {
            profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));

        }

        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedMethod();
            }
        });
    }

    private void refreshFeedMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        if (isSelfProfile) {
            profilePresenter.getPublicProfileCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));
        } else {
            profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));

        }

    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getFollowedMentors(FollowedUsersResponse profileFeedResponsePojo) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        profilePresenter.detachView();
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
        if (userCommunities.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
            mProgressBar.setLayoutParams(params);

            List<CommunityFeedSolrObj> otherCommunities = getUsersCommunity(userCommunities, mFragmentListRefreshData.getPageNo());
            if (StringUtil.isNotEmptyCollection(otherCommunities) && mAdapter != null) {
                int mPageNo = mFragmentListRefreshData.getPageNo();
                mFragmentListRefreshData.setPageNo(++mPageNo);
                mPullRefreshList.allListData(otherCommunities);
                mAdapter.setData(mPullRefreshList.getFeedResponses());
            }
            mSwipeView.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse communityFeedSolrObj) {
    }

    @Override
    public void onUserDeactivation(BaseResponse baseResponse) {
    }

    public List<CommunityFeedSolrObj> getUsersCommunity(ProfileCommunitiesResponsePojo userCommunities, int mPageNo) {

        List<CommunityFeedSolrObj> mutualCommunity = userCommunities.getMutualCommunities();
        List<CommunityFeedSolrObj> otherCommunity = userCommunities.getOtherCommunities();
        boolean isTopHeaderAvailable = false;
        if (!isSelfProfile && StringUtil.isNotEmptyCollection(mutualCommunity)) {
            CommunityFeedSolrObj mutualCommunityFirstItem = mutualCommunity.get(0);
            mutualCommunityFirstItem.setMutualCommunityCount(mutualCommunity.size());
            mutualCommunityFirstItem.setMutualCommunityFirstItem(true);
            mutualCommunityFirstItem.setShowHeader(true);
            mutualCommunity.set(0, mutualCommunityFirstItem);
            isTopHeaderAvailable = true;
        }

        if (StringUtil.isNotEmptyCollection(otherCommunity)) {
            CommunityFeedSolrObj myCommunity = otherCommunity.get(0);
            myCommunity.setShowHeader(true);
            if (!isTopHeaderAvailable) {
                myCommunity.setMutualCommunityCount(0);
                if (mPageNo == 1) {
                    myCommunity.setShowHeader(true);
                }
            } else {
                myCommunity.setMutualCommunityCount(mutualCommunity.size());
            }
            if (mPageNo == 1) {
                myCommunity.setOtherCommunityFirstItem(true);
            }
            otherCommunity.set(0, myCommunity);

            if (mutualCommunity != null) { //other have both mutual and non mutual so making single lust here
                mutualCommunity.addAll(otherCommunity);
                profileCommunities = mutualCommunity;
            } else {
                profileCommunities = otherCommunity;
            }
        }
        return profileCommunities;
    }


    @Override
    public void onItemClick(CommunityFeedSolrObj communityFeedSolrObj) {
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedSolrObj, getScreenName(), null, 1);
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void onConfigFetched() {

    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
}
