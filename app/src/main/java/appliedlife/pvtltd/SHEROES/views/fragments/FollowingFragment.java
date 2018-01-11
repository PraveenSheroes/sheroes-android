package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.MentorUserProfileActvity;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileFollowedMentorAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileNewView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment.SELF_PROFILE;
import static appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment.USER_MENTOR_ID;

/**
 * Created by ravi on 10/01/18.
 */

public class FollowingFragment extends BaseFragment implements ProfileNewView, ProfileFollowedMentorAdapter.OnItemClicked {

    public static final String SCREEN_LABEL = "Followed Mentors";

    private long userMentorId;
    private boolean isSelfProfile;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private LinearLayoutManager mLayoutManager;
    private FragmentListRefreshData mFragmentListRefreshData;
    private ProfileFollowedMentorAdapter mAdapter;
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

    @Bind(R.id.tv_profile_tittle)
    TextView toolbarTitle;

    public static FollowingFragment createInstance(long userId, String name) {
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        followingFragment.setArguments(bundle);
        return followingFragment;
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
        toolbarTitle.setText(R.string.ID_CHAMPION);
        
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.PROFILE_FOLLOWING, AppConstants.NO_REACTION_CONSTANT);
        mFragmentListRefreshData.setSelfProfile(isSelfProfile);
        mFragmentListRefreshData.setMentorUserId(userMentorId);

        followedListPagination(mFragmentListRefreshData);

        return view;
    }

    private void followedListPagination(FragmentListRefreshData mFragmentListRefreshData) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProfileFollowedMentorAdapter(getContext(), profilePresenter, this);
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
        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));

        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedMethod();
            }
        });
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_CHAMPION_LISTING));

    }

    private void refreshFeedMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @OnClick(R.id.iv_back_profile)
    public void backOnclick() {
        getActivity().finish();
    }

    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse profileFeedResponsePojo) {
        List<UserSolrObj> feedDetailList = profileFeedResponsePojo.getFeedDetails();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
        mProgressBar.setLayoutParams(params);

        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<UserSolrObj> data = null;
            //UserSolrObj userSolrObj = new UserSolrObj();
            //userSolrObj.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            //if (position > 0) {
            //    data.remove(position - 1);
            //}
            //data.add(userSolrObj);
            mAdapter.setData(data);
            mSwipeView.setRefreshing(false);

        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mAdapter != null) {
            //List<CommunityFeedSolrObj> data = mPullRefreshList.getFeedResponses();
            //data.remove(data.size() - 1);
            //mAdapter.notifyDataSetChanged();
            mSwipeView.setRefreshing(false);
        } else {
            // mRecyclerView.setEmptyViewWithImage(emptyView, R.string.empty_mentor_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);
        }

    }

    @Override
    public void getUsersFollowerCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersFollowingCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {

    }

    @Override
    public void getUsersPostCount(int totalPost) {

    }

    @Override
    public void onItemClick(UserSolrObj mentor) {
        Intent intent = new Intent(getContext(), MentorUserProfileActvity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.CHAMPION_ID, mentor.getIdOfEntityOrParticipant());
        bundle.putBoolean(AppConstants.IS_MENTOR_ID, true);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
