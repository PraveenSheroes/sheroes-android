package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
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

import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.FollowerFollowingCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
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
import butterknife.BindDimen;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment.SELF_PROFILE;
import static appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment.USER_MENTOR_ID;

/**
 * Created by ravi on 10/01/18.
 * Followed mentoring listing
 */

public class FollowingFragment extends BaseFragment implements IFollowerFollowingView, FollowerFollowingCallback {
    //region constants
    private static final String SCREEN_LABEL = "Followed Champions Screen";
    //endregion constants

    //region bind variable
    @Bind(R.id.communities)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_view_communities)
    SwipeRefreshLayout mSwipeView;

    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindDimen(R.dimen.imagesize_unfollow_dialog)
    int mProfileSizeSmall;
    //endregion bind variable

    //region injected variable
    @Inject
    FollowingPresenterImpl mFollowingPresenter;

    @Inject
    AppUtils mAppUtils;

    @Inject
    Preference<LoginResponse> mUserPreference;
    //endregion injected variable

    //region private member variable
    private long mUserMentorId;
    private boolean mIsSelfProfile;
    private FragmentListRefreshData mFragmentListRefreshData;
    private FollowerFollowingAdapter mAdapter;
    private SwipPullRefreshList mPullRefreshList;
    private FollowingEnum mMode;
    private FollowersFollowingRequest mProfileFollowedMentor;
    private Dialog mDialog = null;
    //endregion private member variable

    //region constructor
    public static FollowingFragment createInstance(long userId, boolean isSelfProfile, String enumValue) {
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        bundle.putBoolean(SELF_PROFILE, isSelfProfile);
        bundle.putString(FollowingActivity.MEMBERS_TYPE, enumValue);
        followingFragment.setArguments(bundle);
        return followingFragment;
    }
    //endregion constructor

    //region fragment lifecycle methods
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communities_list, container, false);
        mFollowingPresenter.attachView(this);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mUserMentorId = getArguments().getLong(USER_MENTOR_ID);
            mIsSelfProfile = getArguments().getBoolean(SELF_PROFILE);
            mMode = FollowingEnum.valueOf(getArguments().getString(FollowingActivity.MEMBERS_TYPE));
        }

        if (mMode == null) return null;
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, mMode.name(), AppConstants.NO_REACTION_CONSTANT);
        mFragmentListRefreshData.setSelfProfile(mIsSelfProfile);
        mFragmentListRefreshData.setMentorUserId(mUserMentorId);
        mProfileFollowedMentor = mAppUtils.followerFollowingRequest(mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getMentorUserId(), mMode.name());
        if (mProfileFollowedMentor == null) return null;
        followedListPagination(mFragmentListRefreshData);
        return view;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mFollowingPresenter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFollowingPresenter.detachView();
    }
    //endregion fragment lifecycle methods

    //region override methods
    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getFollowersOrFollowing(FollowedUsersResponse profileFeedResponsePojo) {
        List<UserSolrObj> feedDetailList = profileFeedResponsePojo.getFeedDetails();
        mProgressBar.setVisibility(View.GONE);

        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            int mPageNo = mFragmentListRefreshData.getPageNo();
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
            // mRecyclerView.setEmptyViewWithImage(emptyView, R.string.empty_mentor_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);
        }
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public void onItemClick(UserSolrObj userSolrObj) {
        boolean isChampion = userSolrObj.getEntityOrParticipantTypeId()!=null && userSolrObj.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID;
        long id = userSolrObj.getIdOfEntityOrParticipant();
        ProfileActivity.navigateTo(getActivity(), id, isChampion, PROFILE_NOTIFICATION_ID, AppConstants.PROFILE_FOLLOWED_CHAMPION,
                null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, false);
    }

    @Override
    public void onFollowFollowingClick(UserSolrObj userSolrObj, String followFollowingBtnText) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                        .name(userSolrObj.getNameOrTitle())
                        .isMentor(userSolrObj.getEntityOrParticipantTypeId()!=null && userSolrObj.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID)
                        .build();
        if (userSolrObj.isSolrIgnoreIsUserFollowed() || userSolrObj.isSolrIgnoreIsMentorFollowed()) {
            if(getActivity()!=null && !getActivity().isFinishing()) {
                ((FollowingActivity) getActivity()).unFollowConfirmation(publicProfileListRequest, userSolrObj, getScreenName());
            }
        } else {
            AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWED, getScreenName(), properties);
            mFollowingPresenter.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
        }
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
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
    }

    @Override
    public void refreshItem(FeedDetail feedDetail) {
        int position = feedDetail.getItemPosition();
        if (mAdapter != null && mAdapter.getItemCount() > position) {
            if (feedDetail instanceof UserSolrObj) {
                mAdapter.setData(position, (UserSolrObj) feedDetail);
            }
        }
    }

    public void invalidateItem(UserSolrObj userSolrObj) {
        if (mAdapter == null || userSolrObj == null) return;
        mAdapter.findPositionAndUpdateItem(userSolrObj, userSolrObj.getIdOfEntityOrParticipant());
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
    //endregion override methods

    //region private methods
    private void followedListPagination(FragmentListRefreshData mFragmentListRefreshData) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FollowerFollowingAdapter(getContext(), this, mUserPreference);
        mRecyclerView.setAdapter(mAdapter);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mFollowingPresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
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
        mFollowingPresenter.getFollowersFollowing(mProfileFollowedMentor);

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
        mFollowingPresenter.getFollowersFollowing(mProfileFollowedMentor);
    }
    //endregion private methods
}
