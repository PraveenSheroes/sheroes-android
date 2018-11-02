package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.Impression.ImpressionCallback;
import appliedlife.pvtltd.SHEROES.analytics.Impression.ImpressionHelper;
import appliedlife.pvtltd.SHEROES.analytics.Impression.ImpressionPresenter;
import appliedlife.pvtltd.SHEROES.analytics.Impression.ImpressionSuperProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.SpamContentType;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Spam;
import appliedlife.pvtltd.SHEROES.models.SpamReasons;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ImageSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatorType;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.FeedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.EndlessRecyclerViewScrollListener;
import appliedlife.pvtltd.SHEROES.utils.SpamUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CollectionActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateStoryActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.UsersCollectionActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.HeaderRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EventDetailDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.removeMemberRequestBuilder;
import static appliedlife.pvtltd.SHEROES.views.activities.MentorsUserListingActivity.CHAMPION_SUBTYPE;

/**
 * Created by ujjwal on 27/12/17.
 */

public class FeedFragment extends BaseFragment implements IFeedView, FeedItemCallback, ImpressionCallback {

    //region public static constants
    public static String SCREEN_LABEL = "Generic Feed Fragment";
    public static final String PRIMARY_COLOR = "Primary Color";
    public static final String TITLE_TEXT_COLOR = "Title Text Color";
    public static final String IS_HOME_FEED = "Is Home Feed";
    public static final String STREAM_NAME = "stream_name";
    public static final String SCREEN_PROPERTIES = "Screen Properties";
    private static final int HIDE_THRESHOLD = 20;
    //endregion

    //region private constants
    //Menu Item Id
    private static final int SHARE_MENU_ID = 1;
    private static final int EDIT_MENU_ID = 2;
    private static final int DELETE_MENU_ID = 3;
    private static final int REPORT_MENU_ID = 4;
    private static final int FEATURED_POST_MENU_ID = 5;
    private static final int BOOKMARK_MENU_ID = 6;
    //endregion

    //region injected variables
    @Inject
    AppUtils mAppUtils;

    @Inject
    FeedPresenter mFeedPresenter;

    @Inject
    ImpressionPresenter impressionPresenter;

    @Inject
    Preference<AppConfiguration> mConfiguration;

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

    //region private variables
    private long mLoggedInUser = -1;
    private int mScrollDirection = -1;
    private int mScrolledDistance = 0;

    private boolean isLoggedInUserMentor;
    private boolean isWhatsappShare = false;
    private boolean mControlsVisible = true;
    private boolean isActiveTabFragment;
    private boolean hasFeedEnded;
    private boolean isHomeFeed;

    private String mPrimaryColor = "#6e2f95";
    private String mTitleTextColor = "#ffffff";
    private String mStreamName;
    private String mScreenLabel;
    private String mSetOrderKey;

    private HashMap<String, Object> mScreenProperties;
    private HashMap<String, Object> mProperties;

    private LinearLayoutManager mLinearLayoutManager;
    private ImpressionHelper impressionHelper;
    private FeedAdapter mAdapter;
    private EventDetailDialogFragment eventDetailDialogFragment;
    private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    private CommunityTab mCommunityTab;
    private boolean isTrackingEnabled = true;
    //endregion

    //region fragment lifecycle method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        ButterKnife.bind(this, view);
        LocaleManager.setLocale(getContext());
        mFeedPresenter.attachView(this);
        impressionPresenter.attachView(this);
        initialSetup();
        initializeRecyclerView();
        initializeSwipeRefreshView();
        setIsLoggedInUser();
        setupRecyclerScrollListener();
        showGifLoader();
        mFeedPresenter.fetchFeed(FeedPresenter.NORMAL_REQUEST, mStreamName);
        isWhatsappShare = CommonUtil.isAppInstalled(SheroesApplication.mContext, AppConstants.WHATS_APP_URI);
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).homeButtonUi();
        }
        String underLineData = getString(R.string.setting);
        SpannableString content = new SpannableString(underLineData);
        content.setSpan(new UnderlineSpan(), 0, underLineData.length(), 0);
        tvGoToSetting.setText(content);

        ConfigData configData = new ConfigData();
        isTrackingEnabled  = configData.isImpressionTrackEnabled;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) {//impression tracking flag
            isTrackingEnabled = mConfiguration.get().configData.isImpressionTrackEnabled;
        }

        //fetch latest all communities and save it in sharePref
        if (isHomeFeed) {
            mFeedPresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {  //When UI is visible to user

            isActiveTabFragment = true;

            if (isTrackingEnabled && impressionHelper != null) {
                updateVisibleImpressionViews();
            }

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

            if (isTrackingEnabled && impressionHelper != null) {
                impressionHelper.stopImpression();
            }
            isActiveTabFragment = false;
        }
    }
    //endregion

    //region private methods
    //Initialize the impression Helper class
    private void initializeImpression() {
        if (impressionHelper == null && isTrackingEnabled) {
            ImpressionSuperProperty impressionSuperProperty = new ImpressionSuperProperty();
            impressionSuperProperty.setCommunityTab(mCommunityTab != null ? mCommunityTab.key : "");
            impressionSuperProperty.setOrderKey(mSetOrderKey == null ? "" : mSetOrderKey);
            impressionSuperProperty.setLoggedInUserId(mLoggedInUser);
            impressionHelper = new ImpressionHelper(impressionSuperProperty, impressionPresenter, mConfiguration, mFeedRecyclerView, mAppUtils, this);
        }
    }

    //update the visible views on screen , update to impression helper about visible views
    private void updateVisibleImpressionViews() {
        int startPos = mLinearLayoutManager.findFirstVisibleItemPosition();
        int endPos = mLinearLayoutManager.findLastVisibleItemPosition();
        impressionHelper.setHeaderEnabled(isHomeFeed);
        impressionHelper.getVisibleViews(startPos, endPos);
    }

    public boolean showUpdateCard() {
        if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.updateVersion != null && !CommonUtil.isLaterClicked()) {
            return CommonUtil.versionCompare(CommonUtil.getCurrentAppVersion(), mConfiguration.get().configData.updateVersion);
        }
        return false;
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        if (mCommunityTab != null) {
            HashMap<String, Object> properties = new EventProperty.Builder()
                    .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                    .sourceTabKey(mCommunityTab.key)
                    .sourceTabTitle(mCommunityTab.title)
                    .build();
            return properties;
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

    private void openProfileScreen(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        ProfileActivity.navigateTo(getActivity(), communityFeedSolrObj, userId, isMentor, position, source, mScreenProperties, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void onDeleteMenuClicked(UserPostSolrObj userPostSolrObj) {
        userPostSolrObj.setIsEditOrDelete(AppConstants.COMMENT_DELETE);
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(userPostSolrObj.getItemPosition()));
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, screenProperties, false, mPrimaryColor, mTitleTextColor);
    }

    private void onEditMenuClicked(UserPostSolrObj userPostSolrObj) {
        userPostSolrObj.setIsEditOrDelete(AppConstants.COMMENT_EDIT);
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(userPostSolrObj.getItemPosition()));
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, screenProperties, false, mPrimaryColor, mTitleTextColor);
    }
    //endregion

    //region public methods
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

        if (showUpdateCard()) {
            mAdapter.addHeader(HeaderRecyclerViewAdapter.header);
        }
        mAdapter.feedFinishedLoading();
        mAdapter.setData(feedDetailList);
        mAdapter.notifyDataSetChanged();
        if (getActivity() != null && isAdded() && getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).showCaseDesign();
        }

        initializeImpression();
    }

    @Override
    public void setData(int position, FeedDetail feedDetail) {
        mAdapter.setData(position, feedDetail);
    }

    @Override
    public void invalidateItem(FeedDetail feedDetail) {
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof CommunityDetailActivity) {
            ((CommunityDetailActivity) getActivity()).invalidateItem(feedDetail);
        } else {
            updateItem(feedDetail);
        }
    }

    @Override
    public void pollVoteResponse(FeedDetail feedDetail, long polOptionId) {
        if (feedDetail instanceof PollSolarObj) {
            AnalyticsManager.trackPollAction(Event.POLL_VOTED, feedDetail, getScreenName(), polOptionId);
        }
    }

    @Override
    public void likeUnlikeResponse(FeedDetail feedDetail, boolean isLike) {
        if (isLike) {
            if (feedDetail instanceof ArticleSolrObj) {
                ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
                if (articleSolrObj.isUserStory()) {
                    HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
                    AnalyticsManager.trackEvent(Event.STORY_LIKED, getScreenName(), properties);
                } else {
                    HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
                    AnalyticsManager.trackEvent(Event.ARTICLE_LIKED, getScreenName(), properties);
                }
            } else if (feedDetail instanceof PollSolarObj) {
                AnalyticsManager.trackPollAction(Event.POLL_LIKED, feedDetail, getScreenName());
            } else {
                if (mCommunityTab != null) {
                    HashMap<String, Object> properties = new EventProperty.Builder()
                            .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                            .sourceTabKey(mCommunityTab.key)
                            .sourceTabTitle(mCommunityTab.title)
                            .build();
                    AnalyticsManager.trackPostAction(Event.POST_LIKED, feedDetail, getScreenName(), properties);
                } else {
                    AnalyticsManager.trackPostAction(Event.POST_LIKED, feedDetail, getScreenName());
                }
            }
        } else {
            if (feedDetail instanceof ArticleSolrObj) {
                ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
                if (articleSolrObj.isUserStory()) {
                    HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
                    AnalyticsManager.trackEvent(Event.STORY_UN_LIKED, getScreenName(), properties);
                } else {
                    HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
                    AnalyticsManager.trackEvent(Event.ARTICLE_UNLIKED, getScreenName(), properties);
                }
            } else if (feedDetail instanceof PollSolarObj) {
                AnalyticsManager.trackPollAction(Event.POLL_UNLIKED, feedDetail, getScreenName());
            } else {
                if (mCommunityTab != null) {
                    HashMap<String, Object> properties = new EventProperty.Builder()
                            .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                            .sourceTabKey(mCommunityTab.key)
                            .sourceTabTitle(mCommunityTab.title)
                            .build();
                    AnalyticsManager.trackPostAction(Event.POST_UNLIKED, feedDetail, getScreenName(), properties);
                } else {
                    AnalyticsManager.trackPostAction(Event.POST_UNLIKED, feedDetail, getScreenName());
                }
            }
        }
    }

    @Override
    public void bookmarkedUnBookMarkedResponse(UserPostSolrObj userPostObj) {
        if (userPostObj == null) return;

        if (userPostObj.isBookmarked()) {
            if (mCommunityTab != null) {
                HashMap<String, Object> properties = new EventProperty.Builder()
                        .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                        .sourceTabKey(mCommunityTab.key)
                        .sourceTabTitle(mCommunityTab.title)
                        .build();
                AnalyticsManager.trackPostAction(Event.POST_BOOKMARKED, userPostObj, getScreenName(), properties);
            } else {
                AnalyticsManager.trackPostAction(Event.POST_BOOKMARKED, userPostObj, getScreenName());
            }
        } else {
            if (mCommunityTab != null) {
                HashMap<String, Object> properties = new EventProperty.Builder()
                        .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                        .sourceTabKey(mCommunityTab.key)
                        .sourceTabTitle(mCommunityTab.title)
                        .build();
                AnalyticsManager.trackPostAction(Event.POST_UNBOOKMARKED, userPostObj, getScreenName(), properties);
            } else {
                AnalyticsManager.trackPostAction(Event.POST_UNBOOKMARKED, userPostObj, getScreenName());
            }
        }
    }

    @Override
    public void notifyAllItemRemoved(FeedDetail feedDetail) {
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof CommunityDetailActivity) {
            ((CommunityDetailActivity) getActivity()).notifyAllItemRemoved(feedDetail);
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
    public List getListData() {
        return null;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

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

        if (mValue == REQUEST_CODE_FOR_SELF_PROFILE_DETAIL && mLoggedInUser != -1) {
            openProfileScreen(mLoggedInUser, 1, isLoggedInUserMentor, AppConstants.FEED_SCREEN); //Logged in profile
        } else if (baseResponse instanceof ArticleSolrObj && mValue == AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_FROM_ARTICLE) {
            ArticleSolrObj articleDetails = (ArticleSolrObj) baseResponse;
            if (StringUtil.isNotEmptyCollection(articleDetails.getLastComments())) {
                Comment comment = articleDetails.getLastComments().get(0);
                if (!comment.isAnonymous()) {
                    openProfileScreen(comment.getParticipantUserId(), comment.getItemPosition(), comment.isVerifiedMentor(), AppConstants.COMMENT_REACTION_FRAGMENT);
                }
            }
        } else if (baseResponse instanceof Comment && mValue == AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_USER_DETAIL) { //working fine for last cmnt
            Comment comment = (Comment) baseResponse;
            if (!comment.isAnonymous()) {
                openProfileScreen(comment.getParticipantUserId(), comment.getItemPosition(), comment.isVerifiedMentor(), AppConstants.COMMENT_REACTION_FRAGMENT);
            }
        } else if (baseResponse instanceof UserPostSolrObj && mValue == REQUEST_CODE_FOR_COMMUNITY_DETAIL) {
            UserPostSolrObj postDetails = (UserPostSolrObj) baseResponse;
            CommunityDetailActivity.navigateTo(getActivity(), postDetails.getCommunityId(), getScreenName(), null, 1);
        } else if (baseResponse instanceof UserPostSolrObj) {
            UserPostSolrObj postDetails = (UserPostSolrObj) baseResponse;
            if (postDetails.getEntityOrParticipantTypeId() != 15) {
                onChampionProfileClicked(postDetails, mValue);
            }
        } else if (baseResponse instanceof PollSolarObj) {
            PollSolarObj pollSolarObj = (PollSolarObj) baseResponse;
            onChampionProfileClicked(pollSolarObj, mValue);
        }

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
        findPositionAndUpdateItem(communityFeedSolrObj, communityFeedSolrObj.getIdOfEntityOrParticipant());
        if (getActivity() != null && getActivity() instanceof CollectionActivity) {
            ((CollectionActivity) getActivity()).setData(mAdapter.getDataList());
        }
    }

    @Override
    public void invalidateCommunityLeft(CommunityFeedSolrObj communityFeedSolrObj) {
        findPositionAndUpdateItem(communityFeedSolrObj, communityFeedSolrObj.getIdOfEntityOrParticipant());
        if (getActivity() != null && getActivity() instanceof CollectionActivity) {
            ((CollectionActivity) getActivity()).setData(mAdapter.getDataList());
        }
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse spamResponse, UserPostSolrObj userPostSolrObj) {
        if (spamResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            int adminId = 0;
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                    adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
                }
            }

            if (adminId == AppConstants.TWO_CONSTANT) {
                if (userPostSolrObj != null) {
                    if (spamResponse.getModelType().toLowerCase().contains(SpamContentType.COMMENT.name())) {
                        onDeleteMenuClicked(userPostSolrObj);
                    } else {
                        AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostSolrObj, getScreenName());
                        mFeedPresenter.getSpamPostApproveFromPresenter(mAppUtils.spamPostApprovedRequestBuilder(userPostSolrObj, true, true, false), userPostSolrObj);
                    }
                }
            }

            if (getActivity() == null || getActivity().isFinishing()) return;

            if (spamResponse.isSpammed()) {
                CommonUtil.createDialog(getActivity(), getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_marked_dialog_message, spamResponse.getModelType().toLowerCase()));
            } else if (!spamResponse.isSpamAlreadyReported()) {
                CommonUtil.createDialog(getActivity(), getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.spam_confirmation_dialog_message));
            } else {
                CommonUtil.createDialog(getActivity(), getResources().getString(R.string.reported_spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_confirmation_dialog_message, spamResponse.getModelType().toLowerCase()));
            }
        }
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
        if (isHomeFeed) {
            String setOrderKey = feedResponsePojo.getSetOrderKey();
            String feedConfigVersion = feedResponsePojo.getServerFeedConfigVersion() != null ? Integer.toString(feedResponsePojo.getServerFeedConfigVersion()) : "";
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
            if (null != prefs) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(AppConstants.FEED_CONFIG_VERSION, feedConfigVersion);
                editor.putString(AppConstants.SET_ORDER_KEY, setOrderKey);
                editor.apply();
            }
            AnalyticsManager.initializeMixpanel(getActivity(), false);
        }

        if (feedResponsePojo != null && StringUtil.isNotNullOrEmptyString(feedResponsePojo.getSetOrderKey())) {
            mSetOrderKey = feedResponsePojo.getSetOrderKey();
        }
    }

    @Override
    public FeedDetail getListItemAtPos(int index) {
        if (mAdapter == null) {
            return null;
        }

        List<FeedDetail> feedDetails = mAdapter.getDataList();
        if (CommonUtil.isEmpty(feedDetails)) {
            return null;
        }

        if (feedDetails.size() > index) {
            return feedDetails.get(index);
        }
        return null;
    }

    @Override
    public int findPositionById(long id) { //TODO - move to presenter
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

    public void findPositionAndUpdateItem(FeedDetail updatedFeedDetail, long id) { //TODO - move to presenter
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
                mAdapter.setData(i, updatedFeedDetail);
            } else if (feedDetail instanceof CarouselDataObj) {
                for (int j = 0; j < ((CarouselDataObj) feedDetail).getFeedDetails().size(); j++) {
                    FeedDetail innerFeedDetail = ((CarouselDataObj) feedDetail).getFeedDetails().get(j);
                    if (innerFeedDetail != null && innerFeedDetail.getIdOfEntityOrParticipant() == id) {
                        if (updatedFeedDetail instanceof UserSolrObj && innerFeedDetail instanceof UserSolrObj) { //Since community name not available in user solr object
                            UserSolrObj updatedUserSolrObj = (UserSolrObj) updatedFeedDetail;
                            if (!StringUtil.isNotNullOrEmptyString(updatedUserSolrObj.getmSolarIgnoreCommunityName())) {
                                UserSolrObj userSolrObj = (UserSolrObj) innerFeedDetail;
                                updatedUserSolrObj.setmSolarIgnoreCommunityName(userSolrObj.getmSolarIgnoreCommunityName());
                                updatedFeedDetail = updatedUserSolrObj;
                            }
                        }
                        ((CarouselDataObj) feedDetail).getFeedDetails().set(j, updatedFeedDetail);
                        mAdapter.setData(i, feedDetail);
                    }
                }
            } else if (feedDetail != null && feedDetail instanceof LeaderBoardUserSolrObj) {
                if (((LeaderBoardUserSolrObj) feedDetail).getUserSolrObj() != null) {
                    mAdapter.setData(i, feedDetail);
                }
            } else if (feedDetail != null && feedDetail instanceof UserPostSolrObj && updatedFeedDetail instanceof UserSolrObj && feedDetail.getAuthorId() == id) {
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) feedDetail;
                UserSolrObj userSolrObj = (UserSolrObj) updatedFeedDetail;
                userPostSolrObj.setSolrIgnoreIsUserFollowed(userSolrObj.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID ? userSolrObj.isSolrIgnoreIsMentorFollowed() : userSolrObj.isSolrIgnoreIsUserFollowed());
                mAdapter.setData(i, userPostSolrObj);
            }
        }
        return;
    }

    public void refreshList() {
        mFeedPresenter.fetchFeed(FeedPresenter.NORMAL_REQUEST, mStreamName);
        if (getActivity() != null && getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).fetchAllCommunity();
        }
    }

    public void scrollToTopInList() {
        if (mFeedRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) mFeedRecyclerView.getLayoutManager();
            int lastVis = mLayoutManager.findLastVisibleItemPosition();
            if (lastVis <= AppConstants.RECYCLER_SMOOTH_SCROLL_COUNT_SIZE)
                mFeedRecyclerView.smoothScrollToPosition(0);
            else {
                mFeedRecyclerView.scrollToPosition(AppConstants.RECYCLER_SMOOTH_SCROLL_COUNT_SIZE);
                mFeedRecyclerView.smoothScrollToPosition(0);
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
    //endregion

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
                    mFeedPresenter.setEndpointUrl(dataUrl);
                }
                isHomeFeed = getArguments().getBoolean(IS_HOME_FEED, false);
                mStreamName = getArguments().getString(FeedFragment.STREAM_NAME, "");
                mFeedPresenter.setIsHomeFeed(isHomeFeed);
                mScreenProperties = new EventProperty.Builder()
                        .sourceCollectionName(screenName)
                        .sourceUrl(dataUrl)
                        .sourceScreenId(sourceScreenId)
                        .build();
            }
        } else {
            if (CommonUtil.isNotEmpty(mCommunityTab.dataUrl)) {
                mFeedPresenter.setEndpointUrl(mCommunityTab.dataUrl);
            }
            mScreenProperties = new EventProperty.Builder()
                    .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                    .sourceTabKey(mCommunityTab.key)
                    .sourceTabTitle(mCommunityTab.title)
                    .build();
        }
    }

    private void setupRecyclerScrollListener() {

        if (isTrackingEnabled) {
            final ViewTreeObserver viewTreeObserver = mFeedRecyclerView.getViewTreeObserver();
            ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    mFeedRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (impressionHelper != null) {
                        int startPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                        int endPos = mLinearLayoutManager.findLastVisibleItemPosition();
                        impressionHelper.setHeaderEnabled(isHomeFeed);
                        impressionHelper.getVisibleViews(startPos, endPos);
                    }
                }
            };
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
        }

        mFeedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isTrackingEnabled && impressionHelper != null) {
                        impressionHelper.scrollingIdle(System.currentTimeMillis());
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy < 0) {
                    mScrollDirection = ImpressionHelper.SCROLL_UP;
                } else if (dy > 0) {
                    mScrollDirection = ImpressionHelper.SCROLL_DOWN;
                }

                if (isTrackingEnabled && impressionHelper != null) {
                    int startPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                    int endPos = mLinearLayoutManager.findLastVisibleItemPosition();
                    impressionHelper.setHeaderEnabled(isHomeFeed);
                    impressionHelper.onScrollChange(recyclerView, mScrollDirection, startPos, endPos);
                }

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
                if (mFeedPresenter.isFeedLoading() || hasFeedEnded) {
                    return;
                }
                mFeedRecyclerView.post(new Runnable() {
                    public void run() {
                        mAdapter.feedStartedLoading();
                    }
                });
                mFeedPresenter.fetchFeed(FeedPresenter.LOAD_MORE_REQUEST, mStreamName);
            }
        };
        mFeedRecyclerView.addOnScrollListener(mEndlessRecyclerViewScrollListener);
    }

    private void setIsLoggedInUser() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserId()) {
            mLoggedInUser = mUserPreference.get().getUserSummary().getUserId();

            if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                isLoggedInUserMentor = true;
            }
        }
    }

    private void initializeSwipeRefreshView() {
        // Initialize Swipe Refresh Layout
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFeedPresenter.fetchFeed(FeedPresenter.NORMAL_REQUEST, mStreamName);
            }
        });
        mSwipeRefresh.setColorSchemeResources(R.color.mentor_green, R.color.link_color, R.color.email);

    }

    private void initializeRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mFeedRecyclerView.setLayoutManager(mLinearLayoutManager);
        ((SimpleItemAnimator) mFeedRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new FeedAdapter(getContext(), this);
        mFeedRecyclerView.setAdapter(mAdapter);
    }

    private void shareCardDetail(UserPostSolrObj userPostObj) {
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(userPostObj.getPostShortBranchUrls())) {
            deepLinkUrl = userPostObj.getPostShortBranchUrls();
        } else {
            deepLinkUrl = userPostObj.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));

        HashMap<String, Object> properties = MixpanelHelper.getPostProperties(userPostObj, getScreenName());
        if (mCommunityTab != null) {
            HashMap<String, Object> propertiesMap = new EventProperty.Builder()
                    .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                    .sourceTabKey(mCommunityTab.key)
                    .sourceTabTitle(mCommunityTab.title)
                    .build();
            properties.putAll(propertiesMap);
        }
        AnalyticsManager.trackEvent(Event.POST_SHARED, getScreenName(), properties);
    }

    private void sharePollCardDetail(PollSolarObj pollSolarObj) {
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(pollSolarObj.getPostShortBranchUrls())) {
            deepLinkUrl = pollSolarObj.getPostShortBranchUrls();
        } else {
            deepLinkUrl = pollSolarObj.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));

        HashMap<String, Object> properties = MixpanelHelper.getPollProperties(pollSolarObj, getScreenName());
        AnalyticsManager.trackEvent(Event.POLL_SHARED, getScreenName(), properties);
    }

    private void shareCardDetail(ArticleSolrObj articleSolrObj) {
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(articleSolrObj.getPostShortBranchUrls())) {
            deepLinkUrl = articleSolrObj.getPostShortBranchUrls();
        } else {
            deepLinkUrl = articleSolrObj.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        if (articleSolrObj.subType.equalsIgnoreCase(AppConstants.FEED_ARTICLE)) {
            HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
            if (articleSolrObj.isUserStory()) {
                AnalyticsManager.trackEvent(Event.STORY_SHARED, getScreenName(), properties);
            } else {
                AnalyticsManager.trackEvent(Event.ARTICLE_SHARED, getScreenName(), properties);
            }
        } else {
            HashMap<String, Object> properties = MixpanelHelper.getPostProperties(articleSolrObj, getScreenName());
            AnalyticsManager.trackEvent(Event.POST_SHARED, getScreenName(), properties);
        }
    }
    //endregion

    //region view actions
    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }

    @Override
    public void onArticleUnBookMarkClicked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), articleSolrObj.isBookmarked());
        if (articleSolrObj.isUserStory()) {
            HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
            AnalyticsManager.trackEvent(Event.STORY_UN_BOOKMARKED, getScreenName(), properties);
        } else {
            AnalyticsManager.trackPostAction(Event.POST_UNBOOKMARKED, articleSolrObj, getScreenName());
        }
    }

    @Override
    public void onArticleBookMarkClicked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), articleSolrObj.isBookmarked());
        if (articleSolrObj.isUserStory()) {
            HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
            AnalyticsManager.trackEvent(Event.STORY_BOOKMARKED, getScreenName(), properties);
        } else {
            AnalyticsManager.trackPostAction(Event.POST_BOOKMARKED, articleSolrObj, getScreenName());
        }
    }

    @Override
    public void onArticleItemClicked(ArticleSolrObj articleSolrObj) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(articleSolrObj.getItemPosition()));
        ArticleActivity.navigateTo(getActivity(), articleSolrObj, getScreenName(), screenProperties, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL, articleSolrObj.isUserStory());
    }

    @Override
    public void onPostShared(FeedDetail feedDetail) {
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getPostShortBranchUrls())) {
            deepLinkUrl = feedDetail.getPostShortBranchUrls();
        } else {
            deepLinkUrl = feedDetail.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        if (isWhatsappShare) {
            intent.setPackage(AppConstants.WHATS_APP_URI);
            intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
            startActivity(intent);
        } else {
            intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
            startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        }
        if (!isWhatsappShare) {
            if (feedDetail.subType.equalsIgnoreCase(AppConstants.FEED_ARTICLE)) {
                ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
                HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
                properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
                if (articleSolrObj.isUserStory()) {
                    AnalyticsManager.trackEvent(Event.STORY_SHARED, getScreenName(), properties);
                } else {
                    AnalyticsManager.trackEvent(Event.ARTICLE_SHARED, getScreenName(), properties);
                }
            }
            if (feedDetail.subType.equalsIgnoreCase(AppConstants.FEED_POLL)) {
                HashMap<String, Object> properties = MixpanelHelper.getPollProperties(feedDetail, getScreenName());
                properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
                AnalyticsManager.trackEvent(Event.POLL_SHARED, getScreenName(), properties);
            } else {
                HashMap<String, Object> properties = MixpanelHelper.getPostProperties(feedDetail, getScreenName());
                properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
                AnalyticsManager.trackEvent(Event.POST_SHARED, getScreenName(), properties);
            }
        } else {
            if (feedDetail.subType.equalsIgnoreCase(AppConstants.FEED_ARTICLE)) {
                ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
                HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, getScreenName());
                properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
                if (articleSolrObj.isUserStory()) {
                    AnalyticsManager.trackEvent(Event.STORY_SHARED, getScreenName(), properties);
                } else {
                    AnalyticsManager.trackEvent(Event.ARTICLE_SHARED, getScreenName(), properties);
                }
            }
            if (feedDetail.subType.equalsIgnoreCase(AppConstants.FEED_POLL)) {
                HashMap<String, Object> properties = MixpanelHelper.getPollProperties(feedDetail, getScreenName());
                properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
                AnalyticsManager.trackEvent(Event.POLL_SHARED, getScreenName(), properties);
            } else {
                HashMap<String, Object> properties = MixpanelHelper.getPostProperties(feedDetail, getScreenName());
                properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
                if (mCommunityTab != null) {
                    HashMap<String, Object> propertiesMap = new EventProperty.Builder()
                            .sourceScreenId(getActivity() instanceof CommunityDetailActivity ? ((CommunityDetailActivity) getActivity()).getCommunityId() : "")
                            .sourceTabKey(mCommunityTab.key)
                            .sourceTabTitle(mCommunityTab.title)
                            .build();
                    properties.putAll(propertiesMap);
                }
                AnalyticsManager.trackEvent(Event.POST_SHARED, getScreenName(), properties);
            }
        }

    }

    @Override
    public void onUserPostClicked(FeedDetail feedDetail) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(feedDetail.getItemPosition()));
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), feedDetail, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, screenProperties, false, mPrimaryColor, mTitleTextColor);
    }

    @Override
    public void onUserPostCommentClicked(UserPostSolrObj userPostSolrObj) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(userPostSolrObj.getItemPosition()));
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, screenProperties, true, mPrimaryColor, mTitleTextColor);
    }

    @Override
    public void onUserPostImageClicked(UserPostSolrObj userPostObj) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(userPostObj.getItemPosition()));
        AlbumActivity.navigateTo(getActivity(), userPostObj, getScreenName(), screenProperties);
    }

    @Override
    public void onPostMenuClicked(final UserPostSolrObj userPostObj, final View view) {
        if (getActivity() == null) return;
        PopupMenu popup = new PopupMenu(getActivity(), view);
        long currentUserId = -1;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            currentUserId = mUserPreference.get().getUserSummary().getUserId();
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }

            popup.getMenu().add(0, R.id.share, SHARE_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_share_black), getResources().getString(R.string.ID_SHARE)));
            popup.getMenu().add(0, R.id.edit, EDIT_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_create), getResources().getString(R.string.ID_EDIT)));
            popup.getMenu().add(0, R.id.delete, DELETE_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_delete), getResources().getString(R.string.ID_DELETE)));
            popup.getMenu().add(0, R.id.report_spam, REPORT_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_report_spam), getResources().getString(R.string.REPORT_SPAM)));

            if (adminId == AppConstants.TWO_CONSTANT || userPostObj.isCommunityOwner()) {
                if (userPostObj.isTopPost()) {
                    popup.getMenu().add(0, R.id.top_post, FEATURED_POST_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_feature_post), getResources().getString(R.string.UNFEATURE_POST)));
                } else {
                    popup.getMenu().add(0, R.id.top_post, FEATURED_POST_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_feature_post), getResources().getString(R.string.FEATURE_POST)));
                }
            }
            //****   Hide/show options according to user
            if (userPostObj.isBookmarked()) {
                popup.getMenu().add(0, R.id.bookmark, BOOKMARK_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_menu_bookmarked), getResources().getString(R.string.Bookmarked))).setVisible(true);
            } else {
                popup.getMenu().add(0, R.id.bookmark, BOOKMARK_MENU_ID, menuIconWithText(getResources().getDrawable(R.drawable.vector_menu_bookmark), getResources().getString(R.string.Bookmark))).setVisible(true);
            }

            if (userPostObj.getAuthorId() == currentUserId || adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.report_spam).setVisible(false);
            } else {
                popup.getMenu().findItem(R.id.report_spam).setVisible(true);
            }

            if (userPostObj.getAuthorId() == currentUserId || userPostObj.isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {

                popup.getMenu().findItem(R.id.delete).setVisible(true);
                if (userPostObj.isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
                    if (userPostObj.getAuthorId() == currentUserId) {
                        popup.getMenu().findItem(R.id.edit).setVisible(true);
                    } else {
                        popup.getMenu().findItem(R.id.edit).setVisible(false);
                    }
                } else {
                    popup.getMenu().findItem(R.id.edit).setVisible(true);
                }

                if (userPostObj.communityId == 0 && adminId == AppConstants.TWO_CONSTANT) {
                    popup.getMenu().findItem(R.id.delete).setVisible(false);
                }

            } else {
                popup.getMenu().findItem(R.id.delete).setVisible(false);
                popup.getMenu().findItem(R.id.edit).setVisible(false);
            }

            //Enable delete response for admin in challenge response
            if (userPostObj.communityId == 0) {
                popup.getMenu().findItem(R.id.edit).setVisible(false);
                if (adminId == AppConstants.TWO_CONSTANT) {
                    popup.getMenu().findItem(R.id.delete).setVisible(true);
                } else {
                    popup.getMenu().findItem(R.id.delete).setVisible(false);
                }
            }

            if (userPostObj.isSpamPost()) {
                popup.getMenu().findItem(R.id.share).setVisible(false);
            } else {
                popup.getMenu().findItem(R.id.share).setVisible(true);
            }
            final int finalAdminId = adminId;
            final long finalCurrentUserId = currentUserId;
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.share:
                            shareCardDetail(userPostObj);
                            return true;
                        case R.id.edit:
                            CommunityPostActivity.navigateTo(getActivity(), userPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, mPrimaryColor, mTitleTextColor, mScreenProperties);
                            return true;
                        case R.id.delete:
                            if (finalCurrentUserId != userPostObj.getAuthorId() && finalAdminId == AppConstants.TWO_CONSTANT) {
                                reportSpamDialog(SpamContentType.POST, userPostObj);
                            } else {
                                AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostObj, getScreenName());
                                mFeedPresenter.deleteCommunityPostFromPresenter(AppUtils.deleteCommunityPostRequest(userPostObj.getIdOfEntityOrParticipant()), userPostObj);
                            }
                            return true;
                        case R.id.top_post:
                            AnalyticsManager.trackPostAction(Event.POST_TOP_POST, userPostObj, getScreenName());
                            mFeedPresenter.editTopPost(AppUtils.topCommunityPostRequestBuilder(userPostObj.communityId, getCreatorType(userPostObj), userPostObj.getListDescription(), userPostObj.getIdOfEntityOrParticipant(), !userPostObj.isTopPost()));
                            return true;
                        case R.id.report_spam:
                            reportSpamDialog(SpamContentType.POST, userPostObj);
                            return true;
                        case R.id.bookmark:
                            onPostBookMarkedClicked(userPostObj);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    @Override
    public void onPollMenuClicked(final PollSolarObj pollSolarObj, final View view) {
        if (getActivity() == null) return;
        PopupMenu popup = new PopupMenu(getActivity(), view);

        popup.getMenu().add(0, R.id.share, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_share_black), getResources().getString(R.string.ID_SHARE)));
        popup.getMenu().add(0, R.id.delete, 3, menuIconWithText(getResources().getDrawable(R.drawable.ic_delete), getResources().getString(R.string.ID_DELETE)));

        //****   Hide/show options according to user


        if (pollSolarObj.isAuthorizeToDelete()) {

            popup.getMenu().findItem(R.id.delete).setVisible(true);

        } else {
            popup.getMenu().findItem(R.id.delete).setVisible(false);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        sharePollCardDetail(pollSolarObj);
                        return true;
                    case R.id.delete:
                        mFeedPresenter.deletePollFromPresenter(mAppUtils.deletePollRequestBuilder(pollSolarObj.getIdOfEntityOrParticipant()), pollSolarObj);
                        AnalyticsManager.trackPostAction(Event.POLL_DELETED, pollSolarObj, getScreenName());
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
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

        if (!isActiveTabFragment && impressionHelper != null) { // resume from back-stack cases
            updateVisibleImpressionViews();
        }

        if (isActiveTabFragment) {
            AnalyticsManager.timeScreenView(mScreenLabel);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (impressionHelper != null) { //app exit cases and back-stack of activity
            impressionHelper.stopImpression();
        }

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
        return mFeedPresenter;
    }

    private String getCreatorType(UserPostSolrObj userPostSolrObj) {
        if (userPostSolrObj.isAnonymous()) {
            return CreatorType.ANONYMOUS.toString();
        } else if (userPostSolrObj.getEntityOrParticipantTypeId() == 15) {
            return CreatorType.COMMUNITY_OWNER.toString();
        } else {
            return CreatorType.USER.toString();
        }
    }

    @Override
    public void onCommentMenuClicked(final UserPostSolrObj userPostObj, final TextView tvFeedCommunityPostUserCommentPostMenu) {
        if (getActivity() == null || getActivity().isFinishing()) return;

        PopupMenu popup = new PopupMenu(getActivity(), tvFeedCommunityPostUserCommentPostMenu);
        long currentUserId = -1;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            currentUserId = mUserPreference.get().getUserSummary().getUserId();
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            // popup.getMenuInflater().inflate(R.menu.menu_edit_delete_comment, popup.getMenu());
            if (CommonUtil.isEmpty(userPostObj.getLastComments())) {
                return;
            }

            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }

            popup.getMenu().add(0, R.id.edit, 1, menuIconWithText(getResources().getDrawable(R.drawable.vector_create), getResources().getString(R.string.ID_EDIT)));
            popup.getMenu().add(0, R.id.delete, 2, menuIconWithText(getResources().getDrawable(R.drawable.vector_delete), getResources().getString(R.string.ID_DELETE)));
            popup.getMenu().add(0, R.id.report_spam, 4, menuIconWithText(getResources().getDrawable(R.drawable.vector_report_spam), getResources().getString(R.string.REPORT_SPAM)));

            Comment comment = userPostObj.getLastComments().get(0);

            if (comment.getParticipantUserId() == currentUserId || adminId == AppConstants.TWO_CONSTANT) {
                if (comment.getParticipantUserId() == currentUserId) {
                    popup.getMenu().findItem(R.id.edit).setVisible(true);
                } else {
                    popup.getMenu().findItem(R.id.edit).setVisible(false);
                }
                popup.getMenu().findItem(R.id.delete).setVisible(true);
                popup.getMenu().findItem(R.id.report_spam).setVisible(false);
            } else {
                popup.getMenu().findItem(R.id.report_spam).setVisible(true);
                popup.getMenu().findItem(R.id.edit).setVisible(false);
                popup.getMenu().findItem(R.id.delete).setVisible(false);
            }


            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            onEditMenuClicked(userPostObj);
                            return true;
                        case R.id.delete:
                            AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostObj, getScreenName());
                            onDeleteMenuClicked(userPostObj);
                            return true;
                        case R.id.report_spam:
                            reportSpamDialog(SpamContentType.COMMENT, userPostObj);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    @Override
    public void onPostBookMarkedClicked(UserPostSolrObj userPostObj) {
        mFeedPresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(userPostObj.getEntityOrParticipantId()), userPostObj.isBookmarked(), userPostObj);
    }

    @Override
    public void onLikesCountClicked(long postId) {
        if (getActivity() != null) {
            LikeListBottomSheetFragment.showDialog((AppCompatActivity) getActivity(), getScreenName(), postId);
        }
    }

    @Override
    public void onPollVote(PollSolarObj pollSolarObj, PollOptionModel pollOptionModel) {
        mFeedPresenter.getPollVoteFromPresenter(mAppUtils.pollVoteRequestBuilder(pollSolarObj.getIdOfEntityOrParticipant(), pollOptionModel.getPollOptionId()), pollSolarObj, pollOptionModel.getPollOptionId());
    }

    @Override
    public void onUserPostLiked(UserPostSolrObj userPostObj) {
        mFeedPresenter.getPostLikesFromPresenter(mAppUtils.likeRequestBuilder(userPostObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), userPostObj);
    }

    @Override
    public void onPollLiked(PollSolarObj pollSolarObj) {
        mFeedPresenter.getPostLikesFromPresenter(mAppUtils.likeRequestBuilder(pollSolarObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), pollSolarObj);
    }

    @Override
    public void onUserPostUnLiked(UserPostSolrObj userPostObj) {
        mFeedPresenter.getPostUnLikesFromPresenter(mAppUtils.likeRequestBuilder(userPostObj.getEntityOrParticipantId(), AppConstants.NO_REACTION_CONSTANT), userPostObj);
    }

    @Override
    public void onPollUnLiked(PollSolarObj pollSolarObj) {
        mFeedPresenter.getPostUnLikesFromPresenter(mAppUtils.likeRequestBuilder(pollSolarObj.getEntityOrParticipantId(), AppConstants.NO_REACTION_CONSTANT), pollSolarObj);
    }

    @Override
    public void onArticlePostLiked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.getPostLikesFromPresenter(mAppUtils.likeRequestBuilder(articleSolrObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), articleSolrObj);
    }

    @Override
    public void onArticlePostUnLiked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.getPostUnLikesFromPresenter(mAppUtils.likeRequestBuilder(articleSolrObj.getEntityOrParticipantId(), AppConstants.NO_REACTION_CONSTANT), articleSolrObj);
    }

    @Override
    public void onSpamPostApprove(UserPostSolrObj userPostObj) {
        mFeedPresenter.getSpamPostApproveFromPresenter(mAppUtils.spamPostApprovedRequestBuilder(userPostObj, true, false, true), userPostObj);
    }

    @Override
    public void onSpamPostDelete(UserPostSolrObj userPostObj) {
        mFeedPresenter.getSpamPostApproveFromPresenter(mAppUtils.spamPostApprovedRequestBuilder(userPostObj, true, true, false), userPostObj);
    }

    @Override
    public void onCommunityClicked(CommunityFeedSolrObj communityFeedObj) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(communityFeedObj.getItemPosition()));
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedObj, getScreenName(), screenProperties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }

    @Override
    public void onCommunityClicked(long communityId) {
        CommunityDetailActivity.navigateTo(getActivity(), communityId, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }

    @Override
    public void onCommunityJoinOrLeave(CommunityFeedSolrObj mCommunityFeedObj) {
        if (mCommunityFeedObj.isMember()) {
            mCommunityFeedObj.setMember(false);
            mCommunityFeedObj.setNoOfMembers(mCommunityFeedObj.getNoOfMembers() - 1);
            AnalyticsManager.trackCommunityAction(Event.COMMUNITY_LEFT, mCommunityFeedObj, getScreenName());

            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                mFeedPresenter.leaveCommunity(removeMemberRequestBuilder(mCommunityFeedObj.getIdOfEntityOrParticipant(), mUserPreference.get().getUserSummary().getUserId()), mCommunityFeedObj);
            }
        } else {
            mCommunityFeedObj.setMember(true);
            mCommunityFeedObj.setNoOfMembers(mCommunityFeedObj.getNoOfMembers() + 1);
            AnalyticsManager.trackCommunityAction(Event.COMMUNITY_JOINED, mCommunityFeedObj, getScreenName());

            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                List<Long> userIdList = new ArrayList();
                userIdList.add(mUserPreference.get().getUserSummary().getUserId());
                mFeedPresenter.joinCommunity(AppUtils.communityRequestBuilder(userIdList, mCommunityFeedObj.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY), mCommunityFeedObj);
            }
        }
    }

    @Override
    public void onSeeMoreClicked(CarouselDataObj carouselDataObj) {
        if (carouselDataObj != null && carouselDataObj.getEndPointUrl() != null) {

            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .name(getString(R.string.ID_CAROUSEL_SEE_MORE))
                            .communityCategory(carouselDataObj.getScreenTitle())
                            .build();

            if(carouselDataObj.getStreamType()!=null && carouselDataObj.getStreamType().equalsIgnoreCase(AppConstants.LEADERBOARD_CAROUSEL_STREAM)) {
                UsersCollectionActivity.navigateTo(getActivity(), carouselDataObj.getEndPointUrl(), carouselDataObj.getScreenTitle(), mScreenLabel, getString(R.string.ID_COMMUNITIES_CATEGORY), properties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            } else {
                CollectionActivity.navigateTo(getActivity(), carouselDataObj.getEndPointUrl(), carouselDataObj.getScreenTitle(), mScreenLabel, getString(R.string.ID_COMMUNITIES_CATEGORY), properties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            }
        }
    }

    @Override
    public void onImagePostClicked(ImageSolrObj imageSolrObj) {
        if (CommonUtil.isNotEmpty(imageSolrObj.getDeepLinkUrl())) {
            try {
                URI uri = new URI(imageSolrObj.getDeepLinkUrl());
                String uriPath = uri.getPath();
                String mPromoCardUrl = "Quiz";
                if (null != mConfiguration && mConfiguration.isSet() && mConfiguration.get().configData != null) {
                    mPromoCardUrl = mConfiguration.get().configData.mPromoCardUrl;
                }
                if (uriPath.contains("/" + mPromoCardUrl.toLowerCase())) {
                    if (null != getActivity() && getActivity() instanceof HomeActivity)
                        ((HomeActivity) getActivity()).openWebUrlFragment(imageSolrObj.getDeepLinkUrl(), mPromoCardUrl);
                } else {
                    Uri url = Uri.parse(imageSolrObj.getDeepLinkUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(url);
                    startActivity(intent);
                }
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(String.valueOf(imageSolrObj.getIdOfEntityOrParticipant()))
                                .url(imageSolrObj.getDeepLinkUrl())
                                .build();
                AnalyticsManager.trackEvent(Event.IMAGE_CARD, null, properties);
            } catch (URISyntaxException e) {
                Crashlytics.getInstance().core.logException(e);
            }
        }
    }

    @Override
    public void onUserHeaderClicked(CommunityFeedSolrObj communityFeedSolrObj, boolean authorMentor) {
        ProfileActivity.navigateTo(getActivity(), communityFeedSolrObj, communityFeedSolrObj.getIdOfEntityOrParticipant(), authorMentor, 0, mScreenLabel, mScreenProperties, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public void onPostMenuClicked(final ArticleSolrObj articleObj, final View view) {
        if (getActivity() == null || getActivity().isFinishing()) return;

        PopupMenu popup = new PopupMenu(getActivity(), view);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            if (view.getId() == R.id.tv_feed_article_user_comment_post_menu || view.getId() == R.id.spam_article_comment_menu) {
                long currentUserId = -1;
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                    currentUserId = mUserPreference.get().getUserSummary().getUserId();
                }

                if (articleObj.getLastComments().size() > 0) {
                    Comment comment = articleObj.getLastComments().get(0);
                    if (comment == null) return;

                    long adminId = 0;
                    if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                        if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                            adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
                        }
                    }
                    if (comment.getParticipantUserId() == currentUserId || adminId == AppConstants.TWO_CONSTANT) {

                        if (comment.isMyOwnParticipation()) {
                            popup.getMenu().add(0, R.id.edit, 1, menuIconWithText(getResources().getDrawable(R.drawable.vector_create), getResources().getString(R.string.ID_EDIT)));
                        }

                        popup.getMenu().add(0, R.id.delete, 2, menuIconWithText(getResources().getDrawable(R.drawable.vector_delete), getResources().getString(R.string.ID_DELETE)));
                    } else {
                        popup.getMenu().add(0, R.id.report_spam, 2, menuIconWithText(getResources().getDrawable(R.drawable.vector_report_spam), getResources().getString(R.string.REPORT_SPAM)));
                    }
                }

            } else {
                popup.getMenu().add(0, R.id.share, 1, menuIconWithText(getResources().getDrawable(R.drawable.vector_share_black), getResources().getString(R.string.ID_SHARE)));
            }

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {

                        case R.id.edit:
                            //onArticleCommentClicked(articleObj);
                            return true;
                        case R.id.delete:
                            //onDeleteMenuClicked(userPostObj);

                        case R.id.share:
                            shareCardDetail(articleObj);
                            return true;

                        case R.id.report_spam:
                            // SpamPostRequest spamPostRequest  = SpamUtil.spamRequestBuilder(articleObj, view, mLoggedInUser);
                            //  reportSpamDialog(spamPostRequest);
                            return true;

                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    @Override
    public void onHerStoryPostMenuClicked(final ArticleSolrObj articleObj, final View view) {
        if (getActivity() == null || getActivity().isFinishing()) return;
        PopupMenu popup = new PopupMenu(getActivity(), view);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            if (articleObj.getCreatedBy() == mUserPreference.get().getUserSummary().getUserId()) {
                popup.getMenu().add(0, R.id.edit, 1, menuIconWithText(getResources().getDrawable(R.drawable.vector_create), getResources().getString(R.string.ID_EDIT)));
                popup.getMenu().add(0, R.id.delete, 2, menuIconWithText(getResources().getDrawable(R.drawable.vector_delete), getResources().getString(R.string.ID_DELETE)));
            } else if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.ADMIN_TYPE_ID) {
                    popup.getMenu().add(0, R.id.delete, 2, menuIconWithText(getResources().getDrawable(R.drawable.vector_delete), getResources().getString(R.string.ID_DELETE)));
                }
            }
        }
        if (!articleObj.getUserStoryStatus().equalsIgnoreCase(AppConstants.STORY_DRAFT)) {
            popup.getMenu().add(0, R.id.share, 3, menuIconWithText(getResources().getDrawable(R.drawable.vector_share_black), getResources().getString(R.string.ID_SHARE)));
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        onHerStoryEdit(articleObj);
                        return true;
                    case R.id.delete:
                        onHerStoryDelete(articleObj);
                        return true;
                    case R.id.share:
                        shareCardDetail(articleObj);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();

    }

    public void onHerStoryEdit(ArticleSolrObj articleObj) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(articleObj.getItemPosition()));
        if (articleObj.isUserStory()) {
            screenProperties.put(EventProperty.IS_STORY.toString(), true);
            screenProperties.put(EventProperty.ID.toString(), String.valueOf(articleObj.getIdOfEntityOrParticipant()));
            screenProperties.put(EventProperty.NAME.toString(), articleObj.getNameOrTitle());
            screenProperties.put(EventProperty.AUTHOR_ID.toString(), articleObj.getCreatedBy());
            screenProperties.put(EventProperty.AUTHOR_NAME.toString(), articleObj.getAuthorName());
        }
        CreateStoryActivity.navigateTo(getActivity(), articleObj, getScreenName(), null);
    }

    public void onHerStoryDelete(ArticleSolrObj articleObj) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(articleObj.getItemPosition()));
        if (articleObj.isUserStory()) {
            screenProperties.put(EventProperty.IS_STORY.toString(), true);
            screenProperties.put(EventProperty.ID.toString(), String.valueOf(articleObj.getIdOfEntityOrParticipant()));
            screenProperties.put(EventProperty.NAME.toString(), articleObj.getNameOrTitle());
            screenProperties.put(EventProperty.AUTHOR_ID.toString(), articleObj.getCreatedBy());
            screenProperties.put(EventProperty.AUTHOR_NAME.toString(), articleObj.getAuthorName());
        }
        mFeedPresenter.deleteArticle(mAppUtils.articleDeleteRequest(articleObj));
    }

    @Override
    public void onUpdateNowClicked() {
        AnalyticsManager.trackEvent(Event.APP_UPDATE_YES, getScreenName(), null);
        CommonUtil.openPlayStore(getContext(), SheroesApplication.mContext.getPackageName());
    }

    @Override
    public void onUpdateLaterClicked() {
        AnalyticsManager.trackEvent(Event.APP_UPDATE_NO, getScreenName(), null);
        CommonUtil.saveReminderForTomorrow();
        mAdapter.removeHeader(HeaderRecyclerViewAdapter.header);
    }

    @Override
    public void onLeaderBoardItemClick(LeaderBoardUserSolrObj leaderBoardUserSolrObj, String screenName) {
        if (getActivity() != null && getActivity().isFinishing()) return;

        if (leaderBoardUserSolrObj != null) {
            BadgeDetails badgeDetails = leaderBoardUserSolrObj.getSolrIgnoreBadgeDetails();
            badgeDetails.setSolrIgnoreStartDate(leaderBoardUserSolrObj.getSolrIgnoreStartDate());
            badgeDetails.setSolrIgnoreEndDate(leaderBoardUserSolrObj.getSolrIgnoreEndDate());
            BadgeDetailsDialogFragment.showDialog(getActivity(), leaderBoardUserSolrObj.getUserSolrObj(), badgeDetails, screenName, true);
        }
    }

    @Override
    public void onLeaderBoardHeaderClick(LeaderBoardUserSolrObj leaderBoardUserSolrObj, String screenName) {
        if (leaderBoardUserSolrObj != null && getActivity() != null && !getActivity().isFinishing()) {
            SuperSheroesCriteriaFragment.showDialog((AppCompatActivity) getActivity(), leaderBoardUserSolrObj, screenName);
        }
    }

    @Override
    public void onLeaderBoardUserClick(long userId, String sourceScreenName) {
        ProfileActivity.navigateTo(getActivity(), userId, false, PROFILE_NOTIFICATION_ID, sourceScreenName, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public void onPostAuthorFollowed(UserPostSolrObj userPostSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userPostSolrObj.getAuthorId());

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userPostSolrObj.getIdOfEntityOrParticipant()))
                        .name(userPostSolrObj.getNameOrTitle())
                        .isMentor((userPostSolrObj.getUserSubType() != null && userPostSolrObj.getUserSubType().equalsIgnoreCase(CHAMPION_SUBTYPE)) || userPostSolrObj.isAuthorMentor())
                        .build();
        if (getExtraProperties() != null && properties != null && mCommunityTab != null) {
            properties.putAll(getExtraProperties());
        }

        if (userPostSolrObj.isSolrIgnoreIsUserFollowed()) {
            AnalyticsManager.trackEvent(Event.PROFILE_UNFOLLOWED, getScreenName(), properties);
            mFeedPresenter.getPostAuthorUnfollowed(publicProfileListRequest, userPostSolrObj);
        } else {
            AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWED, getScreenName(), properties);
            mFeedPresenter.getPostAuthorFollowed(publicProfileListRequest, userPostSolrObj);
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
    public void onAskQuestionClicked() {
        CommunityPost communityPost = new CommunityPost();
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(getActivity(), communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, mScreenProperties);
    }

    @Override
    public void onCommunityTitleClicked(FeedDetail feedDetail) {
        if (feedDetail instanceof UserPostSolrObj) {
            UserPostSolrObj userPostObj = (UserPostSolrObj) feedDetail;
            if (userPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                    if (StringUtil.isNotNullOrEmptyString(userPostObj.getDeepLinkUrl())) {
                        Uri url = Uri.parse(userPostObj.getDeepLinkUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(url);
                        startActivity(intent);
                    }

                }
            } else {
                if (userPostObj.getCommunityId() == 0) {
                    ContestActivity.navigateTo(getActivity(), Long.toString(userPostObj.getUserPostSourceEntityId()), userPostObj.getScreenName(), mScreenProperties);

                } else {
                    HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
                    screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(userPostObj.getItemPosition()));
                    CommunityDetailActivity.navigateTo(getActivity(), userPostObj.getCommunityId(), getScreenName(), screenProperties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                }
            }
        } else if (feedDetail instanceof PollSolarObj) {
            PollSolarObj pollSolarObj = (PollSolarObj) feedDetail;
            HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
            CommunityDetailActivity.navigateTo(getActivity(), pollSolarObj.getCommunityId(), getScreenName(), screenProperties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
        }
    }

    @Override
    public void userCommentLikeRequest(UserPostSolrObj userPostSolrObj, boolean isLikedAction, int adapterPosition) {
        if (CommonUtil.isEmpty(userPostSolrObj.getLastComments())) {
            return;
        }
        Comment comment = userPostSolrObj.getLastComments().get(0);
        if (isLikedAction) {
            mFeedPresenter.getCommentLikesFromPresenter(mAppUtils.likeRequestBuilder(comment.getEntityId(), AppConstants.HEART_REACTION_CONSTANT, comment.getCommentsId()), comment, userPostSolrObj);
        } else {
            mFeedPresenter.getCommentUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(comment.getEntityId(), comment.getCommentsId()), comment, userPostSolrObj);
        }
    }

    @Override
    public void onChallengeClicked(Contest contest) {
        ContestActivity.navigateTo(getActivity(), contest, mScreenLabel, mScreenProperties, 0, 0, AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL);
    }

    @Override
    public void onChallengePostShared(BaseResponse baseResponse) {
        String postShareUrl = "";
        if (CommonUtil.isNotEmpty(((FeedDetail) baseResponse).getPostShortBranchUrls())) {
            postShareUrl = ((FeedDetail) baseResponse).getPostShortBranchUrls();

        } else {
            postShareUrl = ((FeedDetail) baseResponse).getDeepLinkUrl();
        }
        String shareText = Config.COMMUNITY_POST_CHALLENGE_SHARE + System.getProperty("line.separator") + postShareUrl;
        String sourceId = "";
        if (baseResponse instanceof UserPostSolrObj) {
            sourceId = Long.toString(((UserPostSolrObj) baseResponse).getUserPostSourceEntityId());
        } else if (baseResponse instanceof ChallengeSolrObj) {
            sourceId = Long.toString(((ChallengeSolrObj) baseResponse).getIdOfEntityOrParticipant());
        }
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(sourceId)
                        .build();
        AnalyticsManager.trackEvent(Event.CHALLENGE_SHARED, getScreenName(), properties);
        ShareBottomSheetFragment.showDialog((AppCompatActivity) getActivity(), shareText, ((FeedDetail) baseResponse).getThumbnailImageUrl(), postShareUrl, getScreenName(), true, postShareUrl, true, Event.CHALLENGE_SHARED, properties);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onEventPostClicked(UserPostSolrObj userPostSolrObj) {
        eventDetailDialogFragment = (EventDetailDialogFragment) getActivity().getFragmentManager().findFragmentByTag(EventDetailDialogFragment.class.getName());
        if (eventDetailDialogFragment == null) {
            eventDetailDialogFragment = new EventDetailDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putLong(AppConstants.EVENT_ID, 0);
            bundle.putBoolean(AppConstants.IS_FROM_COMMUNITY_SCREEN, true);
            Parcelable parcelable = Parcels.wrap(userPostSolrObj);
            bundle.putParcelable(AppConstants.EVENT_DETAIL, parcelable);
            eventDetailDialogFragment.setArguments(bundle);
        }
        if (!eventDetailDialogFragment.isVisible() && !eventDetailDialogFragment.isAdded() && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            eventDetailDialogFragment.show(getActivity().getFragmentManager(), EventDetailDialogFragment.class.getName());
        }
    }

    @Override
    public void onEventInterestedClicked(UserPostSolrObj userPostSolrObj) {
        mFeedPresenter.getEventInterestedFromPresenter(mAppUtils.likeRequestBuilder(userPostSolrObj.getEntityOrParticipantId(), AppConstants.EVENT_CONSTANT), userPostSolrObj);
    }

    @Override
    public void onEventNotInterestedClicked(UserPostSolrObj userPostSolrObj) {
        mFeedPresenter.getEventNotInteresetedFromPresenter(mAppUtils.unLikeRequestBuilder(userPostSolrObj.getEntityOrParticipantId()), userPostSolrObj);
    }

    @Override
    public void onEventGoingClicked(UserPostSolrObj userPostSolrObj) {
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(userPostSolrObj.getEntityOrParticipantId()), userPostSolrObj.isBookmarked());
    }

    @Override
    public void onOrgTitleClicked(UserPostSolrObj userPostObj) {
        if (null != userPostObj) {
            if (StringUtil.isNotNullOrEmptyString(userPostObj.getDeepLinkUrl())) {
                Uri url = Uri.parse(userPostObj.getDeepLinkUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(url);
                startActivity(intent);
            }
        }
    }

    private void reportSpamDialog(final SpamContentType spamContentType, final UserPostSolrObj userPostSolrObj) {

        if (getActivity() == null || getActivity().isFinishing()) return;

        SpamReasons spamReasons;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.reasonOfSpamCategory != null) {
            spamReasons = mConfiguration.get().configData.reasonOfSpamCategory;
        } else {
            String spamReasonsContent = AppUtils.getStringContent(AppConstants.SPAM_REASONS_FILE); //read spam reasons from local file
            spamReasons = AppUtils.parseUsingGSONFromJSON(spamReasonsContent, SpamReasons.class.getName());
        }

        if (spamReasons == null) return;

        final Dialog spamReasonsDialog = new Dialog(getActivity());
        spamReasonsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        spamReasonsDialog.setCancelable(true);
        spamReasonsDialog.setContentView(R.layout.dialog_spam_options);

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.convertDpToPixel(16, getActivity()), CommonUtil.convertDpToPixel(10, getActivity()), 0, 0);

        TextView reasonTitle = spamReasonsDialog.findViewById(R.id.reason_title);
        TextView reasonSubTitle = spamReasonsDialog.findViewById(R.id.reason_sub_title);
        reasonTitle.setLayoutParams(layoutParams);
        reasonSubTitle.setLayoutParams(layoutParams);

        final RadioGroup spamOptions = spamReasonsDialog.findViewById(R.id.options_container);

        List<Spam> spamList = null;
        SpamPostRequest spamRequest = null;
        if (spamContentType == SpamContentType.POST) {
            spamList = spamReasons.getPostTypeSpams();
            spamRequest = SpamUtil.createSpamPostRequest(userPostSolrObj, false, mLoggedInUser);
        } else if (spamContentType == SpamContentType.COMMENT) {
            spamList = spamReasons.getCommentTypeSpams();
            spamRequest = SpamUtil.createSpamPostRequest(userPostSolrObj, true, mLoggedInUser);
        }

        if (spamRequest == null || spamList == null) return;

        final EditText reason = spamReasonsDialog.findViewById(R.id.edit_text_reason);

        SpamUtil.addRadioToView(getContext(), spamList, spamOptions);

        Button submit = spamReasonsDialog.findViewById(R.id.submit);

        final SpamPostRequest finalSpamRequest = spamRequest;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spamOptions.getCheckedRadioButtonId() != -1) {
                    RadioButton radioButton = spamOptions.findViewById(spamOptions.getCheckedRadioButtonId());
                    Spam spam = (Spam) radioButton.getTag();
                    if (spam != null) {
                        finalSpamRequest.setSpamReason(spam.getReason());
                        finalSpamRequest.setScore(spam.getScore());
                        if (spam.getLabel().equalsIgnoreCase(getString(R.string.others))) {
                            if (reason.getVisibility() == View.VISIBLE) {
                                if (reason.getText().length() > 0 && reason.getText().toString().trim().length() > 0) {
                                    finalSpamRequest.setSpamReason(spam.getReason().concat(":" + reason.getText().toString()));
                                    mFeedPresenter.reportSpamPostOrComment(finalSpamRequest, userPostSolrObj); //submit
                                    spamReasonsDialog.dismiss();
                                    if (spamContentType == SpamContentType.POST) {
                                        AnalyticsManager.trackPostAction(Event.POST_REPORTED, userPostSolrObj, getScreenName());
                                    } else if (spamContentType == SpamContentType.COMMENT) {
                                        AnalyticsManager.trackCommentAction(Event.REPLY_REPORTED, userPostSolrObj, getScreenName());
                                    }
                                } else {
                                    reason.setError(getString(R.string.add_reason));
                                }
                            } else {
                                reason.setVisibility(View.VISIBLE);
                                SpamUtil.hideSpamReason(spamOptions, spamOptions.getCheckedRadioButtonId());
                            }
                        } else {
                            mFeedPresenter.reportSpamPostOrComment(finalSpamRequest, userPostSolrObj);  //submit request
                            spamReasonsDialog.dismiss();
                            if (spamContentType == SpamContentType.POST) {
                                AnalyticsManager.trackPostAction(Event.POST_REPORTED, userPostSolrObj, getScreenName());
                            } else if (spamContentType == SpamContentType.COMMENT) {
                                AnalyticsManager.trackCommentAction(Event.REPLY_REPORTED, userPostSolrObj, getScreenName());
                            }
                        }
                    }
                }
            }
        });
        spamReasonsDialog.show();
    }

    @Override
    public void onFollowClicked(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                        .name(userSolrObj.getNameOrTitle())
                        .isMentor((userSolrObj.getUserSubType() != null && userSolrObj.getUserSubType().equalsIgnoreCase(CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor())
                        .build();
        if (getExtraProperties() != null && properties != null && mCommunityTab != null) {
            properties.putAll(getExtraProperties());
        }

        if (userSolrObj.isSolrIgnoreIsMentorFollowed()) {
            AnalyticsManager.trackEvent(Event.PROFILE_UNFOLLOWED, getScreenName(), properties);
            mFeedPresenter.getUnFollowFromPresenter(publicProfileListRequest, userSolrObj);
        } else {
            AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWED, getScreenName(), properties);
            mFeedPresenter.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
        }
    }

    @Override
    public void onMentorAskQuestionClicked(UserSolrObj userSolrObj) {
        CommunityPost communityPost = new CommunityPost();
        communityPost.community = new Community();
        communityPost.community.id = userSolrObj.getSolrIgnoreMentorCommunityId();
        communityPost.community.name = userSolrObj.getNameOrTitle();
        communityPost.createPostRequestFrom = AppConstants.MENTOR_CREATE_QUESTION;
        communityPost.isEdit = false;
        communityPost.isCompanyAdmin = userSolrObj.getCompanyAdmin();
        CommunityPostActivity.navigateTo(getActivity(), communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, mPrimaryColor, mTitleTextColor, mScreenProperties);
    }

    @Override
    public void onMentorProfileClicked(UserSolrObj userSolrObj) {
        if (userSolrObj.getEntityOrParticipantTypeId() == 7) {
            ProfileActivity.navigateTo(getActivity(), userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), true, -1, AppConstants.FEED_SCREEN, mScreenProperties, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        } else if (userSolrObj.getEntityOrParticipantTypeId() == 1) {
            ProfileActivity.navigateTo(getActivity(), userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), false, -1, AppConstants.FEED_SCREEN, mScreenProperties, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    @Override
    public void onMentorProfileClicked(UserPostSolrObj userSolrObj) {
        if (!userSolrObj.isAnonymous() && userSolrObj.getEntityOrParticipantTypeId() == 14) { //for user post .Here type 14 for user & mentor
            boolean isMentor = (userSolrObj.getUserSubType() != null && userSolrObj.getUserSubType().equalsIgnoreCase(CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor();
            ProfileActivity.navigateTo(getActivity(), userSolrObj.getCreatedBy(), isMentor, PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }

    }

    @Override
    public void onFeedLastCommentUserClicked(UserPostSolrObj userSolrObj) {
        if (userSolrObj.getEntityOrParticipantTypeId() == 14) { //for user post .Here type 14 for user & mentor
            List<Comment> lastComments = userSolrObj.getLastComments();
            if (StringUtil.isNotEmptyCollection(lastComments)) {
                Comment comment = lastComments.get(0);
                if (comment != null && !comment.isAnonymous()) {
                    ProfileActivity.navigateTo(getActivity(), comment.getParticipantUserId(), comment.isVerifiedMentor(), PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                }
            }

        }
    }

    @Override
    public void onChampionProfileClicked(FeedDetail feedDetail, int requestCodeForMentorProfileDetail) {
        long userId = feedDetail.getCreatedBy();
        int position = feedDetail.getItemPosition();
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        ProfileActivity.navigateTo(getActivity(), communityFeedSolrObj, userId, feedDetail.isAuthorMentor(), position, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }

    @Override
    public void onArticleCommentClicked(ArticleSolrObj articleObj) {
        HashMap<String, Object> screenProperties = (HashMap<String, Object>) mScreenProperties.clone();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(articleObj.getItemPosition()));
        if (articleObj.isUserStory()) {
            screenProperties.put(EventProperty.IS_STORY.toString(), true);
            screenProperties.put(EventProperty.ID.toString(), String.valueOf(articleObj.getIdOfEntityOrParticipant()));
            screenProperties.put(EventProperty.NAME.toString(), articleObj.getNameOrTitle());
            screenProperties.put(EventProperty.AUTHOR_ID.toString(), articleObj.getCreatedBy());
            screenProperties.put(EventProperty.AUTHOR_NAME.toString(), articleObj.getAuthorName());
        }

        ArticleActivity.navigateTo(getActivity(), articleObj, getScreenName(), screenProperties, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL, articleObj.isUserStory());

    }
    //endregion

    //region onclick methods
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
    //endregion

}