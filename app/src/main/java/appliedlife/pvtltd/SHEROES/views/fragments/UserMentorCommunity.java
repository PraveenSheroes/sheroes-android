package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileCommunityAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileNewView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment.SELF_PROFILE;
import static appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment.USER_MENTOR_ID;

/**
 * Created by ravi on 11/01/18.
 * Community listing
 */

public class UserMentorCommunity extends BaseFragment implements ProfileNewView, ProfileCommunityAdapter.OnItemClicked {

    public static final String SCREEN_LABEL = "Mentor_User Community";

    private long userMentorId;
    private boolean isSelfProfile;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private List<CommunityFeedSolrObj> profileCommunities;
    private LinearLayoutManager mLayoutManager;
    private FragmentListRefreshData mFragmentListRefreshData;
    private ProfileCommunityAdapter mAdapter;
    private SwipPullRefreshList mPullRefreshList;

    @Bind(R.id.tv_profile_tittle)
    TextView toolbarTitle;

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

    public static UserMentorCommunity createInstance(long userId, String name, boolean isSelfProfile) {
        UserMentorCommunity userMentorCommunity = new UserMentorCommunity();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        bundle.putBoolean(SELF_PROFILE, isSelfProfile);
        userMentorCommunity.setArguments(bundle);
        return userMentorCommunity;
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
        toolbarTitle.setText(R.string.ID_COMMUNITIES);

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.PROFILE_COMMUNITY_LISTING, AppConstants.NO_REACTION_CONSTANT);
        mFragmentListRefreshData.setSelfProfile(isSelfProfile);
        mFragmentListRefreshData.setMentorUserId(userMentorId);
        mentorSearchInListPagination(mFragmentListRefreshData);

        return view;
    }

    private void mentorSearchInListPagination(FragmentListRefreshData fragmentListRefreshData) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProfileCommunityAdapter(getContext(), profilePresenter, this);
        mRecyclerView.setAdapter(mAdapter);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(profilePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
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
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_COMMUNITIES));
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

    @OnClick(R.id.iv_back_profile)
   public void backOnclick() {
        getActivity().finish();
   }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse profileFeedResponsePojo) {

    }

    @Override
    public void getUsersFollowerCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersFollowingCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
        if(userCommunities.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
            mProgressBar.setLayoutParams(params);

            List<CommunityFeedSolrObj> otherCommunities = getUsersCommunity(userCommunities, mFragmentListRefreshData.getPageNo());
            if (StringUtil.isNotEmptyCollection(otherCommunities) && mAdapter != null) {
                mPageNo = mFragmentListRefreshData.getPageNo();
                mFragmentListRefreshData.setPageNo(++mPageNo);
                mPullRefreshList.allListData(otherCommunities);
                List<CommunityFeedSolrObj> data = null;
                // CommunityFeedSolrObj feedProgressBar = new CommunityFeedSolrObj();
                // feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
                data = mPullRefreshList.getFeedResponses();
                //int position = data.size() - otherCommunities.size();
                //if (position > 0) {
                //    data.remove(position - 1);
                //}
                //data.add(feedProgressBar);
                mAdapter.setData(data);
                mSwipeView.setRefreshing(false);

            } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mAdapter != null) {
                List<CommunityFeedSolrObj> data = mPullRefreshList.getFeedResponses();
                data.remove(data.size() - 1);
                mAdapter.notifyDataSetChanged();
                mSwipeView.setRefreshing(false);
            } else {
                // mRecyclerView.setEmptyViewWithImage(emptyView, R.string.empty_mentor_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);
            }
        }
    }

    @Override
    public void getUsersPostCount(int totalPost) {

    }

    public List<CommunityFeedSolrObj> getUsersCommunity(ProfileCommunitiesResponsePojo userCommunities, int mPageNo) {

        List<CommunityFeedSolrObj> mutualCommunity = userCommunities.getMutualCommunities();
        List<CommunityFeedSolrObj> otherCommunity = userCommunities.getOtherCommunities();
        if (!isSelfProfile && mPageNo == AppConstants.ONE_CONSTANT) {
            if (StringUtil.isNotEmptyCollection(mutualCommunity)) {
                CommunityFeedSolrObj profileCommunity = mutualCommunity.get(0);
                profileCommunity.setMutualCommunityCount(mutualCommunity.size());
                profileCommunity.setMutualCommunityFirstItem(true);
                profileCommunity.setShowHeader(true);
                mutualCommunity.set(0 ,profileCommunity);
            }
        }

        if (StringUtil.isNotEmptyCollection(otherCommunity)) {
            CommunityFeedSolrObj profileCommunity = otherCommunity.get(0);
           if(mPageNo == AppConstants.ONE_CONSTANT) {
               profileCommunity.setShowHeader(true);
           } else {
               profileCommunity.setShowHeader(false);
           }

            if (isSelfProfile) {
                profileCommunity.setMutualCommunityCount(0);
            } else if (mutualCommunity != null && mutualCommunity.size() > 0) {
                profileCommunity.setMutualCommunityCount(mutualCommunity.size());
            }

            if(mPageNo ==AppConstants.ONE_CONSTANT) {
                profileCommunity.setOtherCommunityFirstItem(true);
            }
            otherCommunity.set(0 ,profileCommunity);

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
    public void onItemClick( CommunityFeedSolrObj communityFeedSolrObj) {
        Intent intent = new Intent(getContext(), CommunitiesDetailActivity.class);
        Bundle bundle = new Bundle();
        Parcelable parcelables = Parcels.wrap(communityFeedSolrObj);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelables);
        //bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.MY_COMMUNITY);
        intent.putExtras(bundle);
        intent.putExtra(AppConstants.FROM_DEEPLINK, false);
        startActivity(intent);

    }
}
