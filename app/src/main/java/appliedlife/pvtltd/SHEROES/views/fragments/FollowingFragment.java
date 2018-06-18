package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.FollowerFollowingCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.presenters.FollowingPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.FollowingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FollowerFollowingAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowerFollowingView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment.SELF_PROFILE;
import static appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment.USER_MENTOR_ID;

/**
 * Created by ravi on 10/01/18.
 * Followed mentoring listing
 */

public class FollowingFragment extends BaseFragment implements IFollowerFollowingView, FollowerFollowingCallback {

    private static final String SCREEN_LABEL = "Followed Champions Screen";
    private static final int MENTOR_TYPE_ID = 7;

    private long userMentorId;
    private boolean isSelfProfile;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private LinearLayoutManager mLayoutManager;
    private FragmentListRefreshData mFragmentListRefreshData;
    private FollowerFollowingAdapter mAdapter;
    private SwipPullRefreshList mPullRefreshList;
    private FollowingEnum mode;
    private FollowersFollowingRequest profileFollowedMentor;

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
    FollowingPresenterImpl followingPresenter;

    public static FollowingFragment createInstance(long userId, boolean isSelfProfile, String enumValue) {
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        bundle.putBoolean(SELF_PROFILE, isSelfProfile);
        bundle.putString(FollowingActivity.MEMBERS_TYPE, enumValue);
        followingFragment.setArguments(bundle);
        return followingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communities_list, container, false);
        followingPresenter.attachView(this);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            userMentorId = getArguments().getLong(USER_MENTOR_ID);
            isSelfProfile = getArguments().getBoolean(SELF_PROFILE);
            mode = FollowingEnum.valueOf(getArguments().getString(FollowingActivity.MEMBERS_TYPE));
        }

        if (mode == null) return null;

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, mode.name(), AppConstants.NO_REACTION_CONSTANT);
        mFragmentListRefreshData.setSelfProfile(isSelfProfile);
        mFragmentListRefreshData.setMentorUserId(userMentorId);

        profileFollowedMentor = mAppUtils.followerFollowingRequest(mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getMentorUserId(), mode.name());

        if(profileFollowedMentor == null) return null;

        followedListPagination(mFragmentListRefreshData);

        return view;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return followingPresenter;
    }

    private void followedListPagination(FragmentListRefreshData mFragmentListRefreshData) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FollowerFollowingAdapter(getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(followingPresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
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
        followingPresenter.getFollowersFollowing(profileFollowedMentor);

        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedMethod();
            }
        });

        if(mode!=null) {
            String type = mode.name();
             if(type.equalsIgnoreCase(AppConstants.FOLLOWED_CHAMPION))
                ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_FOLLOWED_CHAMPION_LISTING));
            else if(type.equalsIgnoreCase(AppConstants.FOLLOWERS))
                ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_FOLLOWERS_LISTING));
            else if(type.equalsIgnoreCase(AppConstants.FOLLOWING))
                ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_FOLLOWING_LISTING));
        }

    }

    private void refreshFeedMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        followingPresenter.getFollowersFollowing(profileFollowedMentor);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        followingPresenter.detachView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getFollowersOrFollowing(UserFollowedMentorsResponse profileFeedResponsePojo) {
        List<UserSolrObj> feedDetailList = profileFeedResponsePojo.getFeedDetails();
        mProgressBar.setVisibility(View.GONE);

        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<UserSolrObj> data = null;
            UserSolrObj userSolrObj = new UserSolrObj();
            userSolrObj.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            if (position > 0) {
                data.remove(position - 1);
            }
            data.add(userSolrObj);
            mAdapter.setData(data);
            mSwipeView.setRefreshing(false);

        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mAdapter != null) {
            List<UserSolrObj> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mAdapter.notifyDataSetChanged();
            mSwipeView.setRefreshing(false);
        } else {
            // mBadgeRecyceler.setEmptyViewWithImage(emptyView, R.string.empty_mentor_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);
        }

    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public void onItemClick(UserSolrObj mentor) {
        boolean isChampion = mentor.getEntityOrParticipantTypeId() ==  MENTOR_TYPE_ID;
        long id = mentor.getIdOfEntityOrParticipant();
        ProfileActivity.navigateTo(getActivity(), id, isChampion, PROFILE_NOTIFICATION_ID, AppConstants.PROFILE_FOLLOWED_CHAMPION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public List getListData() {
        return null;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }
}
