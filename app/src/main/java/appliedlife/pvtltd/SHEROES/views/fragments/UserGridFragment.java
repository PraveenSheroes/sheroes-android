package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.UserCardCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.FeedPresenter;
import appliedlife.pvtltd.SHEROES.presenters.UsersListPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.EndlessRecyclerViewScrollListener;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.UsersCollectionActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.UserListAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.activities.MentorsUserListingActivity.CHAMPION_SUBTYPE;

/**
 * Created by Ravi on 05/09/18.
 * User fragment for carousel Item
 */

public class UserGridFragment extends BaseFragment implements IFeedView, UserCardCallback {
    public static String SCREEN_LABEL = "User Grid Screen";
    public static final String PRIMARY_COLOR = "Primary Color";
    public static final String TITLE_TEXT_COLOR = "Title Text Color";
    public static final String IS_HOME_FEED = "Is Home Feed";
    public static final String STREAM_NAME = "stream_name";
    public static final String SCREEN_PROPERTIES = "Screen Properties";
    private static final int HIDE_THRESHOLD = 20;

    @Inject
    AppUtils mAppUtils;

    @Inject
    UsersListPresenter mUserListPresenter;

    @Inject
    Preference<LoginResponse> mUserPreference;

    // region View variables
    @Bind(R.id.swipeRefreshContainer)
    SwipeRefreshLayout mSwipeRefresh;

    @Bind(R.id.feed)
    RecyclerView mFeedRecyclerView;

    @Bind(R.id.empty_view)
    LinearLayout emptyView;

    @Bind(R.id.empty_image)
    ImageView emptyImage;

    @Bind(R.id.empty_text)
    TextView emptyText;

    @Bind(R.id.empty_subtext)
    TextView emptySubText;

    @Bind(R.id.loader_gif)
    CardView gifLoader;

    @Bind(R.id.no_internet)
    CardView noInternet;

    @Bind(R.id.tv_goto_setting)
    TextView tvGoToSetting;
    // endregion

    private UserListAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    private CommunityTab mCommunityTab;
    private boolean hasFeedEnded;
    private String mPrimaryColor = "#6e2f95";
    private String mTitleTextColor = "#ffffff";
    HashMap<String, Object> mScreenProperties;
    HashMap<String, Object> mProperties;
    private boolean isHomeFeed;
    private String mStreamName;
    private String mScreenLabel;
    private boolean mControlsVisible = true;
    private int mScrolledDistance = 0;
    private GridLayoutManager mLinearLayoutManager;
    private boolean isActiveTabFragment;

    private Set<FeedDetail> mUpdatedUsers;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        LocaleManager.setLocale(getContext());
        mUserListPresenter.attachView(this);
        initialSetup();
        initializeRecyclerView();
        initializeSwipeRefreshView();
        setupRecyclerScrollListener();
        showGifLoader();

        mUserListPresenter.fetchUserFeed(FeedPresenter.NORMAL_REQUEST, mStreamName);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {  //When UI is visible to user

            isActiveTabFragment = true;
            if (getParentFragment() instanceof HomeFragment) {
                String screenName = ((HomeFragment) getParentFragment()).getInactiveTabFragmentName();
                if (mScreenLabel != null && screenName != null && !mScreenLabel.equalsIgnoreCase(screenName)) {
                    //Send event of previous selected tab with duration, and start the time capture for current selected tab
                    AnalyticsManager.trackScreenView(screenName, getExtraProperties());
                    AnalyticsManager.timeScreenView(mScreenLabel);
                }
            } else if (getActivity() instanceof ProfileActivity || getActivity() instanceof ContestActivity) {
                AnalyticsManager.timeScreenView(mScreenLabel);
            }
        } else { //When UI is not visible to user

            //Capture the screen event of the tab got unselected
            if (isActiveTabFragment && mScreenLabel != null && !(getActivity() instanceof HomeActivity)) {
                AnalyticsManager.trackScreenView(mScreenLabel, getExtraProperties());
            }
            isActiveTabFragment = false;
        }
    }

    @Override
    public void showFeedList(List<FeedDetail> feedDetailList) {
        if (CommonUtil.isEmpty(feedDetailList)) {
            mFeedRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            loadEmptyView();
        } else {
            mFeedRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        mAdapter.feedFinishedLoading();
        mAdapter.setData(feedDetailList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        if (mCommunityTab != null) {
            return new EventProperty.Builder()
                    .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                    .sourceTabKey(mCommunityTab.key)
                    .sourceTabTitle(mCommunityTab.title)
                    .build();
        } else {
            if (mProperties != null) {
                return mProperties;
            } else {
                return null;
            }
        }
    }

    private void loadEmptyView() {
        if (mCommunityTab != null) {
            if (CommonUtil.isNotEmpty(mCommunityTab.emptyTitle)) {
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText(mCommunityTab.emptyTitle);
            } else {
                emptyText.setVisibility(View.GONE);
            }
            if (CommonUtil.isNotEmpty(mCommunityTab.emptyDescription)) {
                emptySubText.setVisibility(View.VISIBLE);
                emptySubText.setText(mCommunityTab.emptyDescription);
            } else {
                emptySubText.setVisibility(View.GONE);
            }
            if (CommonUtil.isNotEmpty(mCommunityTab.emptyImageUrl)) {
                emptyImage.setVisibility(View.VISIBLE);
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .load(mCommunityTab.emptyImageUrl)
                            .into(emptyImage);
                }
            } else {
                emptyImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setData(int position, FeedDetail feedDetail) {
        mAdapter.setData(position, feedDetail);
    }

    @Override
    public void invalidateItem(FeedDetail feedDetail) {
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof UsersCollectionActivity) {
            updateItem(feedDetail);

            if (mUpdatedUsers == null) {
                mUpdatedUsers = new HashSet<>();
            }
            mUpdatedUsers.add(feedDetail);
            ((UsersCollectionActivity) getActivity()).updateTopCarouselUsers(mUpdatedUsers);

        }
    }

    @Override
    public void pollVoteResponse(FeedDetail feedDetail, long polOptionId) {
    }

    @Override
    public void likeUnlikeResponse(FeedDetail feedDetail, boolean isLike) {
    }

    @Override
    public void bookmarkedUnBookMarkedResponse(UserPostSolrObj userPostObj) {
    }

    @Override
    public void notifyAllItemRemoved(FeedDetail feedDetail) {
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof CommunityDetailActivity) {
            ((CommunityDetailActivity) getActivity()).invalidateItem(feedDetail, true);
        } else {
            removeItem(feedDetail);
        }
    }

    @Override
    public void addAllFeed(List<FeedDetail> feedList) {
        mAdapter.addAll(feedList);
    }

    @Override
    public void setFeedEnded(boolean feedEnded) {
        this.hasFeedEnded = feedEnded;
    }

    @Override
    public String getScreenName() {
        if (CommonUtil.isNotEmpty(mScreenLabel)) {
            return mScreenLabel;
        } else {
            return SCREEN_LABEL;
        }
    }



    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
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

    //region private methods
    private void initialSetup() {
        if (null != getArguments()) {
            Parcelable parcelable = getArguments().getParcelable(CommunityTab.COMMUNITY_TAB_OBJ);
            if (parcelable != null) {
                mCommunityTab = Parcels.unwrap(parcelable);
            }
            mPrimaryColor = getArguments().getString(PRIMARY_COLOR);
            mTitleTextColor = getArguments().getString(TITLE_TEXT_COLOR);
        }
        if (mCommunityTab == null) {
            if (getArguments() != null) {
                String dataUrl = getArguments().getString(AppConstants.END_POINT_URL);
                String screenName = getArguments().getString(AppConstants.SCREEN_NAME);
                mProperties = (HashMap<String, Object>) getArguments().getSerializable(SCREEN_PROPERTIES);
                String sourceScreenId = mProperties != null && mProperties.get(EventProperty.ID.getString()) != null ? ((String) mProperties.get(EventProperty.ID.getString())) : "";
                if (CommonUtil.isNotEmpty(screenName)) {
                    mScreenLabel = screenName;
                }
                if (CommonUtil.isNotEmpty(dataUrl)) {
                    mUserListPresenter.setEndpointUrl(dataUrl);
                }
                isHomeFeed = getArguments().getBoolean(IS_HOME_FEED, false);
                mStreamName = getArguments().getString(UserGridFragment.STREAM_NAME, "");
                mUserListPresenter.setIsHomeFeed(isHomeFeed);
                mScreenProperties = new EventProperty.Builder()
                        .sourceCollectionName(screenName)
                        .sourceUrl(dataUrl)
                        .sourceScreenId(sourceScreenId)
                        .build();
            }
        } else {
            if (CommonUtil.isNotEmpty(mCommunityTab.dataUrl)) {
                mUserListPresenter.setEndpointUrl(mCommunityTab.dataUrl);
            }
            mScreenProperties = new EventProperty.Builder()
                    .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                    .sourceTabKey(mCommunityTab.key)
                    .sourceTabTitle(mCommunityTab.title)
                    .build();
        }
    }

    private void setupRecyclerScrollListener() {
        mFeedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getActivity() != null && getActivity() instanceof HomeActivity) {
                    int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (firstVisibleItem == 0) {
                        if (!mControlsVisible) {
                            ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
                            mControlsVisible = true;
                        }
                    } else {
                        if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                            ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
                            mControlsVisible = false;
                            mScrolledDistance = 0;
                        } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                            ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
                            mControlsVisible = true;
                            mScrolledDistance = 0;
                        }
                    }
                    if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
                        mScrolledDistance += dy;
                    }
                }
            }
        });
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (mUserListPresenter.isFeedLoading() || hasFeedEnded) {
                    return;
                }
                mFeedRecyclerView.post(new Runnable() {
                    public void run() {
                        mAdapter.feedStartedLoading();
                    }
                });
                mUserListPresenter.fetchUserFeed(FeedPresenter.LOAD_MORE_REQUEST, mStreamName);
            }
        };
        mFeedRecyclerView.addOnScrollListener(mEndlessRecyclerViewScrollListener);
    }

    private void initializeSwipeRefreshView() {
        // Initialize Swipe Refresh Layout
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserListPresenter.fetchUserFeed(FeedPresenter.NORMAL_REQUEST, mStreamName);
            }
        });
        mSwipeRefresh.setColorSchemeResources(R.color.mentor_green, R.color.link_color, R.color.email);

    }

    private void initializeRecyclerView() {
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 2);
        mFeedRecyclerView.setLayoutManager(mLinearLayoutManager);
        ((SimpleItemAnimator) mFeedRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new UserListAdapter(getContext(), this);
        mFeedRecyclerView.setAdapter(mAdapter);
    }
    //endregion

    //region view actions
    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
    }


    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    protected boolean trackScreenTime() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isActiveTabFragment) {
            AnalyticsManager.timeScreenView(mScreenLabel);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isActiveTabFragment) {
            AnalyticsManager.trackScreenView(mScreenLabel, getExtraProperties());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isActiveTabFragment = false;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mUserListPresenter;
    }

    @Override
    public void onUserFollowedUnFollowed(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        if (userSolrObj.isSolrIgnoreIsUserFollowed()) {
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                            .name(userSolrObj.getNameOrTitle())
                            .isMentor((userSolrObj.getUserSubType() != null && userSolrObj.getUserSubType().equalsIgnoreCase(CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor())
                            .build();
            AnalyticsManager.trackEvent(Event.PROFILE_UNFOLLOWED, getScreenName(), properties);

            mUserListPresenter.getUnFollowFromPresenter(publicProfileListRequest, userSolrObj);
        } else {
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                            .name(userSolrObj.getNameOrTitle())
                            .isMentor((userSolrObj.getUserSubType() != null && userSolrObj.getUserSubType().equalsIgnoreCase(CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor())
                            .build();
            AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWED, getScreenName(), properties);
            mUserListPresenter.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFeedRecyclerView != null) {
            mFeedRecyclerView.setAdapter(null);
        }
    }

    @Override
    public void onUserProfileClicked(UserSolrObj userSolrObj) {
        if (userSolrObj.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID) {
            ProfileActivity.navigateTo(getActivity(), userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), true, -1, AppConstants.FEED_SCREEN, mScreenProperties, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        } else {
            ProfileActivity.navigateTo(getActivity(), userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), false, -1, AppConstants.FEED_SCREEN, mScreenProperties, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    //endregion

    @Override
    public void startProgressBar() {
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefresh != null) {
                    mSwipeRefresh.setRefreshing(true);
                    mSwipeRefresh.setColorSchemeResources(R.color.mentor_green, R.color.link_color, R.color.email);
                }
            }
        });
    }

    @Override
    public void stopProgressBar() {
        mEndlessRecyclerViewScrollListener.finishLoading();
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.setRefreshing(false);
        mAdapter.feedFinishedLoading();
    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
    }

    public void updateItem(FeedDetail updatedFeedDetail) {
        if (updatedFeedDetail == null) return;
        findPositionAndUpdateItem(updatedFeedDetail, updatedFeedDetail.getIdOfEntityOrParticipant());
    }

    @Override
    public void removeItem(FeedDetail feedDetail) {
        int position = findPositionById(feedDetail.getIdOfEntityOrParticipant());
        if (position != RecyclerView.NO_POSITION) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public void invalidateCommunityJoin(CommunityFeedSolrObj communityFeedSolrObj) {
    }

    @Override
    public void invalidateCommunityLeft(CommunityFeedSolrObj communityFeedSolrObj) {
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse spamResponse, UserPostSolrObj userPostSolrObj) {
    }

    @Override
    public void showGifLoader() {
        gifLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGifLoader() {
        gifLoader.setVisibility(View.GONE);
    }

    @Override
    public void updateFeedConfigDataToMixpanel(FeedResponsePojo feedResponsePojo) {
    }

    public int findPositionById(long id) {
        if (mAdapter == null) {
            return -1;
        }
        List<FeedDetail> feedDetails = mAdapter.getDataList();

        if (CommonUtil.isEmpty(feedDetails)) {
            return -1;
        }

        for (int i = 0; i < feedDetails.size(); ++i) {
            FeedDetail feedDetail = feedDetails.get(i);
            if (feedDetail != null && feedDetail.getIdOfEntityOrParticipant() == id) {
                return i;
            }
        }
        return -1;
    }

    public void findPositionAndUpdateItem(FeedDetail updatedFeedDetail, long id) {
        if (mAdapter == null) {
            return;
        }
        List<FeedDetail> feedDetails = mAdapter.getDataList();

        if (CommonUtil.isEmpty(feedDetails)) {
            return;
        }

        for (int i = 0; i < feedDetails.size(); ++i) {
            FeedDetail feedDetail = feedDetails.get(i);
            if (feedDetail != null && feedDetail.getIdOfEntityOrParticipant() == id) {
                if(feedDetail instanceof UserSolrObj && updatedFeedDetail instanceof UserSolrObj) {
                    UserSolrObj userSolrObj1 = (UserSolrObj) feedDetail;
                    if (StringUtil.isNotNullOrEmptyString(userSolrObj1.getmSolarIgnoreCommunityName())) {
                        UserSolrObj updatedUserSolrObj1 = (UserSolrObj) updatedFeedDetail;
                        updatedUserSolrObj1.setmSolarIgnoreCommunityName(userSolrObj1.getmSolarIgnoreCommunityName());
                        updatedFeedDetail = updatedUserSolrObj1;
                    }
                }
                mAdapter.setData(i, updatedFeedDetail);
            }
        }
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorMsg) && errorMsg.equalsIgnoreCase(AppConstants.CHECK_NETWORK_CONNECTION)) {
            noInternet.setVisibility(View.VISIBLE);
            mFeedRecyclerView.setVisibility(View.GONE);
            gifLoader.setVisibility(View.GONE);
        } else {
            super.showError(errorMsg, feedParticipationEnum);
        }
    }

    @OnClick({R.id.tv_retry_for_internet})
    public void onRetryClick() {
        noInternet.setVisibility(View.GONE);
        mFeedRecyclerView.setVisibility(View.VISIBLE);
        gifLoader.setVisibility(View.VISIBLE);
        if (null != getActivity()) {
            if (getActivity() instanceof HomeActivity) {
                ((HomeActivity) getActivity()).homeOnClick();
            }
        }
    }

    @OnClick({R.id.tv_goto_setting})
    public void onSettingClick() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
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
}