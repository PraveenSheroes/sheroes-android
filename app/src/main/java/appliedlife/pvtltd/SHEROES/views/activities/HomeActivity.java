package appliedlife.pvtltd.SHEROES.views.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.clevertap.android.sdk.CleverTapAPI;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.login.LoginManager;
import com.tooltip.Tooltip;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.CleverTapHelper;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.animation.SnowFlakeView;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.ArticleCategory;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotification;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.AppStatus;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationItems;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.MainActivityPresenter;
import appliedlife.pvtltd.SHEROES.service.FCMClientManager;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.LogOutUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunitiesDrawerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ShowcaseManager;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FAQSFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ICCMemberListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SearchProfileLocationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SelectLanguageDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.INavDrawerCallback;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.LANGUAGE_KEY;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_LISTING;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_USER_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.notificationReadCountRequestBuilder;

public class HomeActivity extends BaseActivity implements BaseHolderInterface, INavDrawerCallback, CustomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, HomeView {

    // region Constants
    private static final String SCREEN_LABEL = "Home Screen";
    private static final String COMMUNITY_CATEGORY_SCREEN = "Communities Category Screen";
    private final String TAG = LogUtils.makeLogTag(HomeActivity.class);
    private static final int ANIMATION_DELAY_TIME = 2000;
    private static final int ANIMATION_DURATION_TIME = 5000;
    private static final String MORE_TOP_ICON = "More Top Icon";
    //endregion

    // region inject variables
    @Inject
    Preference<AppConfiguration> mConfiguration;
    @Inject
    Preference<AppStatus> mInstallUpdatePreference;
    @Inject
    Preference<AppInstallation> mAppInstallation;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    MainActivityPresenter mActivityDataPresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    LogOutUtils mLogOutUtils;
    @Inject
    FeedUtils mFeedUtils;
    //endregion

    // region View variables
    @Bind(R.id.beginner)
    ImageView beginnerTick;

    @Bind(R.id.intermediate)
    ImageView intermediateTick;

    @Bind(R.id.home_toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.fl_home_footer_list)
    public FrameLayout mFlHomeFooterList;

    @Bind(R.id.li_article_spinner_icon)
    public RelativeLayout mliArticleSpinnerIcon;

    @Bind(R.id.tv_catagory_text)
    public TextView mTvCategoryText;

    @Bind(R.id.tv_catagory_choose)
    public TextView mTvCategoryChoose;

    @Bind(R.id.iv_spinner_icon)
    public ImageView mIvSpinner;

    @Bind(R.id.appbar_layout)
    public AppBarLayout mAppBarLayout;

    @Bind(R.id.fl_article_card_view)
    public FrameLayout flFeedFullView;

    @Bind(R.id.tv_notification_read_count)
    public TextView mTvNotificationReadCount;

    @Bind(R.id.fab_filter)
    public FloatingActionButton mFloatActionBtn;

    @Bind(R.id.fl_notification_read_count)
    public FrameLayout flNotificationReadCount;

    @Bind(R.id.iv_drawer_profile_circle_icon)
    CircleImageView ivDrawerProfileCircleIcon;

    @Bind(R.id.tv_user_name)
    TextView mTvUserName;

    @Bind(R.id.tv_user_location)
    TextView mTvUserLocation;

    @Bind(R.id.cl_main_layout)
    CoordinatorLayout mCLMainLayout;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.nav_view_left_drawer)
    NavigationView mNavigationViewLeftDrawer;

    @Bind(R.id.nav_view_right_drawer_communities)
    NavigationView mNavigationViewRightDrawerWithCommunities;

    @Bind(R.id.rv_drawer)
    RecyclerView mRecyclerView;

    @Bind(R.id.rv_right_drawer_communities)
    RecyclerView mRecyclerViewDrawerCommunities;

    @Bind(R.id.tv_home)
    public ImageView mTvHome;

    @Bind(R.id.tv_communities)
    public ImageView mTvCommunities;

    @Bind(R.id.tv_search)
    public ImageView mTvSearch;

    @Bind(R.id.tv_home_notification_icon)
    TextView mTvNotification;

    @Bind(R.id.title_text)
    TextView mTitleText;

/*    @Bind(R.id.tv_communities_text)
    ImageView mTvCommunitiesText;*/

    @Bind(R.id.tv_communities_search)
    TextView mTvCommunitiesSearch;

    @Bind(R.id.ic_sheroes)
    ImageView mICSheroes;

    @Bind(R.id.pb_login_progress_bar)
    ProgressBar pbNavDrawer;

    @Bind(R.id.pb_communities_drawer)
    ProgressBar pbCommunitiesDrawer;

    @Bind(R.id.snow_flake_view)
    SnowFlakeView mSnowFlakView;

    @Bind(R.id.santa_view)
    ImageView mSantaView;

    @Bind(R.id.tv_drawer_navigation)
    public TextView tvDrawerNavigation;


    @Bind(R.id.tv_new_tag)
    public TextView mTvNewTag;

    @Bind(R.id.rl_search_box)RelativeLayout searchBoxLayout;

    @BindDimen(R.dimen.dp_size_64)
    int navProfileSize;
    // endregion

    // region member variables
    private boolean mIsSheUser = false;
    private boolean mIsInviteReferral;
    public boolean mIsFirstTimeOpen = false;
    boolean mDoubleBackToExitPressedOnce = false;
    boolean mIsChampion;
    private long mUserId = -1L;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private String mFcmId;
    private String mHelpLineChat;
    private String mUserName;
    private GenericRecyclerViewAdapter mAdapter;
    private List<ArticleCategory> mArticleCategoryItemList = new ArrayList<>();
    private FragmentOpen mFragmentOpen;
    private CustomActionBarToggle mCustomActionBarToggle;
    private FeedDetail mFeedDetail;
    private long mChallengeId;
    private ProgressDialog mProgressDialog;
    private FragmentListRefreshData mFragmentListRefreshData;
    private MyCommunitiesDrawerAdapter mMyCommunitiesAdapter;
    private SwipPullRefreshList mPullRefreshList;
    private ProfileFragment mProfileFragment;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;
    //endregion

    // region Public methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mActivityDataPresenter.attachView(this);
        mHomePresenter.attachView(this);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserId()) {
            mIsSheUser = mUserPreference.get().isSheUser();
            mUserId = mUserPreference.get().getUserSummary().getUserId();
            mUserName = mUserPreference.get().getUserSummary().getFirstName();
            if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                mIsChampion = true;
            }
        }
        renderHomeFragmentView();
        assignNavigationRecyclerListView();
        sheUserInit();
        mHomePresenter.queryConfig();
        mHomePresenter.getNotificationCountFromPresenter(notificationReadCountRequestBuilder(TAG));
        if (null == mUserPreference) {
            logOut();
        } else if (mUserPreference.isSet()) {
            if (null != mUserPreference.get().getUserSummary()) {
                mUserId = mUserPreference.get().getUserSummary().getUserId();
                AppsFlyerLib.getInstance().setCustomerUserId(String.valueOf(mUserId));
                AppsFlyerLib.getInstance().startTracking(SheroesApplication.mContext, getString(R.string.ID_APPS_FLYER_DEV_ID));
                ((SheroesApplication) this.getApplication()).trackUserId(String.valueOf(mUserId));
            }
        }
        try {
            getFcmId();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
        toolTipForNotification();
        if (CommonUtil.ensureFirstTime(AppConstants.NEW_TAG_FOR_RIGHT_SWIP)) {
            mTvNewTag.setVisibility(View.VISIBLE);
        }
        if (!CommonUtil.getPrefValue(AppConstants.SELECT_LANGUAGE_SHARE_PREF)) {
            showSelectLanguageOption();
            CommonUtil.setPrefValue(AppConstants.SELECT_LANGUAGE_SHARE_PREF);
        }
    }

    public void renderHomeFragmentView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet() && !mInstallUpdatePreference.get().isAppInstallFirstTime()) {
            mIsFirstTimeOpen = true;
            if (!mInstallUpdatePreference.get().isOnBoardingSkipped()) {
                Branch branch = Branch.getInstance();
                branch.resetUserSession();
                if (CleverTapHelper.getCleverTapInstance(getApplicationContext()) != null) {
                    branch.setRequestMetadata(CleverTapHelper.CLEVERTAP_ATTRIBUTION_ID,
                            CleverTapHelper.getCleverTapInstance(getApplicationContext()).getCleverTapAttributionIdentifier());
                }
                branch.initSession(new Branch.BranchReferralInitListener() {
                                       @Override
                                       public void onInitFinished(JSONObject referringParams, BranchError error) {
                                           deepLinkingRedirection();
                                       }
                                   }
                        , this.getIntent().getData(), this);
            }
        }
        if (shouldShowSnowFlake()) {
            mSantaView.setVisibility(View.GONE);
            animateSnowFlake();
        } else {
            mSnowFlakView.setVisibility(View.GONE);
        }
        pbNavDrawer.setVisibility(View.VISIBLE);
        mCustomActionBarToggle = new CustomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustomActionBarToggle);
        mNavigationViewLeftDrawer.setNavigationItemSelectedListener(this);
        mNavigationViewRightDrawerWithCommunities.setNavigationItemSelectedListener(this);
        mFragmentOpen = new FragmentOpen();
        if (null != getIntent() && null != getIntent().getExtras()) {
            if (getIntent().getExtras().get(AppConstants.HELPLINE_CHAT) != null) {
                mHelpLineChat = getIntent().getExtras().getString(AppConstants.HELPLINE_CHAT);
            }
            mFloatActionBtn.setTag(AppConstants.FEED_SUB_TYPE);
            if (!mIsSheUser) {
                initHomeViewPagerAndTabs();
            }
        }
        mFeedUtils.setConfigurableShareOption(isWhatsAppShare());
    }

    public void showCaseDesign() {
        if (mIsFirstTimeOpen) {
            this.mIsFirstTimeOpen = false;
            ShowcaseManager showcaseManager = new ShowcaseManager(this, mFloatActionBtn, mTvHome, mTvCommunities, tvDrawerNavigation, mRecyclerView, mUserName);
            showcaseManager.showFirstMainActivityShowcase();
            AppStatus appStatus = mInstallUpdatePreference.get();
            appStatus.setAppInstallFirstTime(true);
            mInstallUpdatePreference.set(appStatus);
            mHomePresenter.updateSelectedLanguage(mAppUtils.updateSelectedLanguageRequestBuilder(CommonUtil.getPrefStringValue(LANGUAGE_KEY), mUserId));
        }
    }

    public void refreshHomeViews() {
        mHomePresenter.queryConfig();
        initHomeViewPagerAndTabs();
        //mTvHome.setText(R.string.home_label);
        //mTvCommunities.setText(R.string.ID_COMMUNITIES);
        mTitleText.setText("");
        //mTvCommunitiesText.setText(R.string.ID_MY_COMMUNITIES);
        mTvCommunitiesSearch.setText(R.string.explore_All);
        mTvNewTag.setText(R.string.new_tag);
        mTvCategoryChoose.setText(R.string.ID_CHOOSE_CATEGORY);
        mICSheroes.setVisibility(View.VISIBLE);
        mActivityDataPresenter.getNavigationDrawerOptions(mAppUtils.navigationOptionsRequestBuilder());
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        pbCommunitiesDrawer.setVisibility(View.VISIBLE);
        mRecyclerViewDrawerCommunities.setVisibility(View.GONE);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mActivityDataPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mHomePresenter.updateSelectedLanguage(mAppUtils.updateSelectedLanguageRequestBuilder(CommonUtil.getPrefStringValue(LANGUAGE_KEY), mUserId));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivityDataPresenter.detachView();
    }

    public void openWebUrlFragment(String url, String menuItemName) { //To open the web-pages in app
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        WebViewActivity.navigateTo(this, getScreenName(), null, url, menuItemName);
        DrawerViewHolder.selectedOptionName = menuItemName;
    }

    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        AppUtils.hideKeyboard(mTvUserName, TAG);
        mDrawer.openDrawer(Gravity.START);
    }

    @OnClick(R.id.fl_nav_communities)
    public void onClickNavigationCommunities() {
        mTvNewTag.setVisibility(View.GONE);
        mDrawer.openDrawer(GravityCompat.END);
    }

    @OnClick(R.id.fab_filter)
    public void createPostActivity() {
        String fabString = (String) mFloatActionBtn.getTag();
        if (fabString.equalsIgnoreCase(AppConstants.FEED_SUB_TYPE)) {
            CommunityPost communityPost = new CommunityPost();
            communityPost.createPostRequestFrom = AppConstants.CREATE_POST;
            createCommunityPostOnClick(communityPost);
        }
    }

    public void removeTrendingFAB(int tabPosition) {
        if (tabPosition == AppConstants.TRENDING_TAB) {
            mFloatActionBtn.setVisibility(View.GONE);
        } else {
            mFloatActionBtn.setVisibility(View.VISIBLE);
        }
    }

    private void writeAStory() {
        CreateStoryActivity.navigateTo(this, 1, getScreenName(), null);
    }

    public void showSelectLanguageOption() {
        SelectLanguageDialog selectLanguageDialog = (SelectLanguageDialog) getFragmentManager().findFragmentByTag(SelectLanguageDialog.class.getName());
        if (selectLanguageDialog == null) {
            selectLanguageDialog = new SelectLanguageDialog();
            Bundle b = new Bundle();
            selectLanguageDialog.setArguments(b);
        }
        if (!selectLanguageDialog.isVisible() && !selectLanguageDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            selectLanguageDialog.show(getFragmentManager(), SelectLanguageDialog.class.getName());
        }
    }

    public void logOut() {
        AnalyticsManager.initializeMixpanel(HomeActivity.this);
        mLogOutUtils.logOutUser(getScreenName(), this);
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            feedRelatedOptions(view, baseResponse);
        } else if (baseResponse instanceof NavMenuItem) {
            drawerItemOptions(view, baseResponse);
        } else if (baseResponse instanceof Comment) {
            /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            mFeedUtils.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU, this, getScreenName());
        } else if (baseResponse instanceof FAQS) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FAQSFragment.class.getName());
            ((FAQSFragment) fragment).setDataChange((FAQS) baseResponse);
        } else if (baseResponse instanceof BellNotificationResponse) {
            BellNotificationResponse bellNotificationResponse = (BellNotificationResponse) baseResponse;
            BellNotification bellNotification = bellNotificationResponse.getNotification();
            if (StringUtil.isNotNullOrEmptyString(bellNotification.getDeepLinkUrl())) {
                challengeIdHandle(bellNotification.getDeepLinkUrl());
            } else {
                mTitleText.setText("");
                homeOnClick();
            }
        }
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
        ContestActivity.navigateTo(this, mContest, SCREEN_LABEL, null, 0, 0, AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL);
    }

    public void resetUiSelectedOptions() {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDrawerOpened() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            AppUtils.hideKeyboard(mTvUserName, TAG);
            AnalyticsManager.trackScreenView(AppConstants.RIGHT_SWIPE_NAVIGATION, getScreenName(), null);
        }
    }

    @Override
    public void onDrawerClosed() {
    }

    public void notifyNavigationDrawerItems(List<NavMenuItem> menuItems) {
        if (mRecyclerView != null && mAdapter != null) {
            mAdapter.setSheroesGenericListData(menuItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void changeFragmentWithCommunities() {
        mFragmentOpen.setFeedFragment(false);
    //    mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.vector_home_unselected_icon), null, null);
     //   mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.vector_community_unselected_icon), null, null);

       // mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
      //  mTvHome.setText(getString(R.string.home_label));

        //TvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));
      //  mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));

        mFloatActionBtn.setVisibility(View.GONE);

        flFeedFullView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.li_article_spinner_icon)
    public void openSpinnerOnClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ArticleCategorySpinnerFragment.class.getName()); //check if mSelectLanguageDialog exist
        if (!AppUtils.isFragmentUIActive(fragment)) {
            mFlHomeFooterList.setVisibility(View.GONE);
            if (!StringUtil.isNotEmptyCollection(mArticleCategoryItemList) && !StringUtil.isNotEmptyCollection(mFragmentOpen.getArticleCategoryList())) {
                // select category id when it comes from deeplinks otherwise on manual click change the selected category
                setArticleCategoryFilterValues(-1);
                mFragmentOpen.setArticleCategoryList(mArticleCategoryItemList);
            } else if (StringUtil.isNotEmptyCollection(mFragmentOpen.getArticleCategoryList())) {
                mArticleCategoryItemList = mFragmentOpen.getArticleCategoryList();
            }
            ArticleCategorySpinnerFragment articleCategoryFragment = new ArticleCategorySpinnerFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mArticleCategoryItemList);
            bundle.putParcelable(AppConstants.ARTICLE_CATEGORY_SPINNER_FRAGMENT, parcelable);
            articleCategoryFragment.setArguments(bundle);
            addNewFragment(articleCategoryFragment, R.id.fl_article_card_view, ArticleCategorySpinnerFragment.class.getName(), null, true);
        }
    }

    //Refresh the feed after clicking the Sheroes logo and home button
    @OnClick({R.id.tv_home, R.id.ic_sheroes})
    public void homeOnClick() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                flFeedFullView.getLayoutParams();

        params.setBehavior(new AppBarLayout.ScrollingViewBehavior(flFeedFullView.getContext(), null));
        highlightHome();
        mAppBarLayout.setVisibility(View.VISIBLE);
        DrawerViewHolder.selectedOptionName = null;
        flFeedFullView.setVisibility(View.VISIBLE);
//        mliArticleSpinnerIcon.setVisibility(View.GONE);
        homeButtonUi();
        initHomeViewPagerAndTabs();
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
    }

    public void homeButtonUi() {

        DrawerViewHolder.selectedOptionName = null;
        resetHamburgerSelectedItems();

        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mFragmentOpen.setFeedFragment(true);

      //  mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.comment_text));
    //    mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.vector_home_selected_icon), null, null);
//        mTvHome.setText(getString(R.string.home_label));
      //  mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));
        //mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.vector_community_unselected_icon), null, null);
        //mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));


        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mFloatActionBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.email)));
        mFloatActionBtn.setImageResource(R.drawable.vector_pencil);
        mFloatActionBtn.setTag(AppConstants.FEED_SUB_TYPE);
    }

    @OnClick({R.id.tv_communities, R.id.tv_communities_search})
    public void communityOnClick() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                flFeedFullView.getLayoutParams();

        params.setBehavior(null);
       /* DrawerViewHolder.selectedOptionName = null;
        resetHamburgerSelectedItems();
        mliArticleSpinnerIcon.setVisibility(View.GONE);*/
        initProfileViewPagerAndTabs();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       /* communityButton();
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        }*/

        highlightProfile();

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        }

        //openProfileActivity(null);
    }

    @OnClick(R.id.tv_search)
    public void searchOnClick() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                flFeedFullView.getLayoutParams();

        params.setBehavior(null);

        highlightSearch();
        openSearchFragment();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

//        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
//            mDrawer.closeDrawer(GravityCompat.START);
//        }
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        }
//        openSearchActivity();
    }

//    private void openSearchActivity() {
//        SearchActivity.navigateTo(this, AppConstants.REQUEST_CODE_FOR_SEARCH);
//    }

    public void communityButton() {

      //  mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.vector_community_selected_icon), null, null);
       // mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.comment_text));
        //mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
     //   mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.vector_home_unselected_icon), null, null);
     //   mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));
     //   mTvHome.setText(getString(R.string.home_label));
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTitleText.setText(getString(R.string.ID_COMMUNITIES));
        mICSheroes.setVisibility(View.GONE);
    }

    public void createCommunityPostOnClick(CommunityPost communityPost) {
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
        // PostBottomSheetFragment.showDialog(this, SCREEN_LABEL);
    }

    public void articleUi() {
        mliArticleSpinnerIcon.setVisibility(View.VISIBLE);
        mFloatActionBtn.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
    }

    public void inviteMyCommunityDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.ID_INVITE_WOMEN_FRIEND));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        LoginManager.getInstance().logOut();
    }

    public void helplineUi() {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
    }

    public void fetchAllCommunity() {
        mHomePresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
    }

    public void onCancelDone(int pressedEvent) {
        if (ArticleCategorySpinnerFragment.CATEGORY_SELECTED_DONE == pressedEvent) {
            getSupportFragmentManager().popBackStack(ArticlesFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            articleCategorySelected();
        } else {
            mArticleCategoryItemList = mFragmentOpen.getArticleCategoryList();
            List<ArticleCategory> localList = new ArrayList<>();
            for (ArticleCategory articleCategory : mArticleCategoryItemList) {
                if (articleCategory.isDone()) {
                    articleCategory.setChecked(true);
                } else {
                    articleCategory.setChecked(false);
                }
                localList.add(articleCategory);
            }
            if (StringUtil.isNotEmptyCollection(localList)) {
                mArticleCategoryItemList.clear();
                mArticleCategoryItemList.addAll(localList);
            }

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(ArticleCategorySpinnerFragment.class.getName()); //check if mSelectLanguageDialog exist
            if (AppUtils.isFragmentUIActive(fragment)) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }

    private void articleCategorySelected() {
        mTvCategoryChoose.setVisibility(View.GONE);
        mFragmentOpen.setArticleCategoryList(mArticleCategoryItemList);
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtil.isNotEmptyCollection(mArticleCategoryItemList)) {
            List<Long> categoryIds = new ArrayList<>();
            List<ArticleCategory> localList = new ArrayList<>();
            for (ArticleCategory articleCategory : mArticleCategoryItemList) {
                if (articleCategory.isChecked()) {
                    categoryIds.add(articleCategory.getId());
                    if (!articleCategory.getName().equalsIgnoreCase(AppConstants.FOR_ALL)) {
                        stringBuilder.append(articleCategory.getName());
                        stringBuilder.append(AppConstants.COMMA);
                    }
                    articleCategory.setDone(true);
                } else {
                    articleCategory.setDone(false);
                }
                localList.add(articleCategory);
            }
            if (StringUtil.isNotEmptyCollection(localList)) {
                mArticleCategoryItemList.clear();
                mArticleCategoryItemList.addAll(localList);
            }
            if (StringUtil.isNotNullOrEmptyString(stringBuilder.toString())) {
                String total = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
                if (total.length() > 25) {
                    total = stringBuilder.toString().substring(0, 25) + AppConstants.DOTS;
                    mTvCategoryText.setText(total);
                } else {
                    mTvCategoryText.setText(total);
                }
            } else {
                mTvCategoryText.setText(AppConstants.EMPTY_STRING);
                mTvCategoryChoose.setVisibility(View.VISIBLE);
            }
            openArticleFragment(categoryIds, false);
        }
    }

    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    public void updateFollowOnAuthorFollowed(boolean isFollowed) {
        mProfileFragment.updateFollowOnAuthorFollowed(isFollowed);
    }

    @Override
    public void onBackPressed() {
        if (mIsSheUser) {
            super.onBackPressed();
            getFragmentManager().executePendingTransactions();
            int count = getFragmentManager().getBackStackEntryCount();
            if (count <= 1) {
                finish();
            } else {
                getFragmentManager().popBackStack();
            }
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_article_card_view);
            if (fragment != null && fragment instanceof CommunitiesListFragment) {
                if (AppUtils.isFragmentUIActive(fragment)) {
                    homeOnClick();
                }
            } else if (fragment != null && fragment instanceof HomeFragment) {
                if (mDoubleBackToExitPressedOnce) {
                    getSupportFragmentManager().popBackStackImmediate();
                    finish();
                    return;
                }
                mDoubleBackToExitPressedOnce = true;
                if (flFeedFullView.getVisibility() == View.VISIBLE) {
                    Snackbar.make(mCLMainLayout, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();
                } else {
                    homeOnClick();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDoubleBackToExitPressedOnce = false;
                    }
                }, 2000);

            } else {
                resetUiSelectedOptions();
                super.onBackPressed();
            }
        }
        resetHamburgerSelectedItems();
        if (mDrawer.isDrawerOpen(GravityCompat.START) || mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.START);
            mDrawer.closeDrawer(GravityCompat.END);
        }
    }

    @OnClick(R.id.tv_home_notification_icon)
    public void notificationClick() {
        AppUtils.hideKeyboard(mTvUserName, TAG);
        bellNotificationDialog();
    }

    public void bellNotificationDialog() {
        BellNotificationDialogFragment bellNotificationDialogFragment = (BellNotificationDialogFragment) getFragmentManager().findFragmentByTag(BellNotificationDialogFragment.class.getName());
        if (bellNotificationDialogFragment == null) {
            bellNotificationDialogFragment = new BellNotificationDialogFragment();
            Bundle bundle = new Bundle();
            bellNotificationDialogFragment.setArguments(bundle);
            flNotificationReadCount.setVisibility(View.GONE);
        }
        if (!bellNotificationDialogFragment.isVisible() && !bellNotificationDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            bellNotificationDialogFragment.show(getFragmentManager(), BellNotificationDialogFragment.class.getName());
        }
    }

    @Override
    public void setListData(BaseResponse data, boolean isCheked) {
        List<ArticleCategory> localList = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mArticleCategoryItemList)) {
            ArticleCategory passedHomeItem = (ArticleCategory) data;
            if (passedHomeItem.getName().equalsIgnoreCase(AppConstants.FOR_ALL)) {
                for (ArticleCategory articleCategory : mArticleCategoryItemList) {
                    articleCategory.setChecked(passedHomeItem.isChecked());
                    localList.add(articleCategory);
                }
            } else {
                for (ArticleCategory articleCategory : mArticleCategoryItemList) {
                    if (articleCategory.getId() == (passedHomeItem.getId())) {
                        articleCategory.setChecked(passedHomeItem.isChecked());
                        localList.add(articleCategory);
                    } else {
                        localList.add(articleCategory);
                    }
                }
            }
        }
        mArticleCategoryItemList.clear();
        mArticleCategoryItemList.addAll(localList);
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getNavigationDrawerItemsSuccess(List<NavMenuItem> navigationItems) {
        if (StringUtil.isNotEmptyCollection(navigationItems)) {
            List<NavMenuItem> mNavigationDrawerItems = new ArrayList<>(); //Filter only navigation drawer items
            for (NavMenuItem navMenuItem : navigationItems) {
                if (navMenuItem.getMenuType() == 1) {
                    mNavigationDrawerItems.add(navMenuItem);
                }
            }
            pbNavDrawer.setVisibility(View.GONE);
            notifyNavigationDrawerItems(mNavigationDrawerItems);
        }
    }

    @Override
    public void getNavigationDrawerItemsFailed() {
        if (null != pbNavDrawer) {
            pbNavDrawer.setVisibility(View.GONE);
        }

        String stringContent = "";
        if (mIsSheUser) {
            stringContent = AppUtils.getStringContent(AppConstants.NAV_DRAWER_SHE_FILE_NAME); //read from local file
        } else {
            stringContent = AppUtils.getStringContent(AppConstants.NAV_DRAWER_FILE_NAME); //read from local file
        }
        NavigationItems eventResponseList = AppUtils.parseUsingGSONFromJSON(stringContent, NavigationItems.class.getName());
        if (null != eventResponseList) {
            List<NavMenuItem> navMenuItems = eventResponseList.getMenuItems();
            List<NavMenuItem> mNavigationDrawerItems = new ArrayList<>(); //Filter only navigation drawer items
            for (NavMenuItem navMenuItem : navMenuItems) {
                if (navMenuItem.getMenuType() == 1) {
                    mNavigationDrawerItems.add(navMenuItem);
                }
            }
            Collections.sort(mNavigationDrawerItems, new Comparator<NavMenuItem>() { //Sort based on display order
                public int compare(NavMenuItem obj1, NavMenuItem obj2) {
                    return obj1.getDisplayOrder().compareTo(obj2.getDisplayOrder());
                }
            });
            notifyNavigationDrawerItems(mNavigationDrawerItems);
        }
    }

    @Override
    public void showMyCommunities(FeedResponsePojo myCommunityResponse) {
        List<FeedDetail> feedDetailList = myCommunityResponse.getFeedDetails();
        pbCommunitiesDrawer.setVisibility(View.GONE);
        mRecyclerViewDrawerCommunities.setVisibility(View.VISIBLE);
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mMyCommunitiesAdapter != null) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<FeedDetail> data = null;
            FeedDetail feedProgressBar = new FeedDetail();
            feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            if (position > 0) {
                data.remove(position - 1);
            }
            data.add(feedProgressBar);
            mMyCommunitiesAdapter.setData(data);
            mMyCommunitiesAdapter.notifyItemRangeInserted(position, data.size());
        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mMyCommunitiesAdapter != null) {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mMyCommunitiesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case NOTIFICATION_COUNT:
                unReadNotificationCount(baseResponse);
                break;
            case FCM_ID:
                fcmIdResponse(baseResponse);
                break;
            case USER_CONTACTS_ACCESS_SUCCESS:
                getAppContactsListSuccess(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    @Override
    public void onConfigFetched() {
        AnalyticsManager.initializeMixpanel(this, false);
        AnalyticsManager.initializeCleverTap(this, false);
        AnalyticsManager.initializeGoogleAnalytics(this);
        AnalyticsManager.initializeFirebaseAnalytics(this);
    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {
    }

    @OnClick(R.id.nav_menu_header)
    public void onClickProfile() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        openProfileActivity(null);
    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
        if (mValue == REQUEST_CODE_FOR_USER_PROFILE_DETAIL) {
            ArticleSolrObj articleSolrObj = (ArticleSolrObj) baseResponse;
            if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                mIsChampion = true;
            }
            championDetailActivity(articleSolrObj.getCreatedBy(), 1, mIsChampion, ArticlesFragment.SCREEN_LABEL); //self profile
        } else if (mValue == REQUEST_CODE_FOR_SELF_PROFILE_DETAIL) {
            championDetailActivity(mUserId, 1, mIsChampion, AppConstants.FEED_SCREEN); //self profile
        } else if (mValue == REQUEST_CODE_CHAMPION_TITLE) {
            UserPostSolrObj feedDetail = (UserPostSolrObj) baseResponse;
            championLinkHandle(feedDetail);
        } else if (mValue == REQUEST_CODE_FOR_COMMUNITY_DETAIL) {
            UserPostSolrObj postDetails = (UserPostSolrObj) baseResponse;
            CommunityDetailActivity.navigateTo(this, postDetails.getCommunityId(), getScreenName(), null, 1);
        } else if (baseResponse instanceof Comment && mValue == AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_USER_DETAIL) {
            Comment comment = (Comment) baseResponse;
            if (!comment.isAnonymous()) {
                championDetailActivity(comment.getParticipantUserId(), comment.getItemPosition(), comment.isVerifiedMentor(), AppConstants.COMMENT_REACTION_FRAGMENT);
            }
        } else if (baseResponse instanceof ArticleSolrObj && mValue == AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_FROM_ARTICLE) {
            ArticleSolrObj articleDetails = (ArticleSolrObj) baseResponse;
            if (StringUtil.isNotEmptyCollection(articleDetails.getLastComments())) {
                Comment comment = articleDetails.getLastComments().get(0);
                championDetailActivity(comment.getParticipantUserId(), comment.getItemPosition(), comment.isVerifiedMentor(), AppConstants.COMMENT_REACTION_FRAGMENT);
            }
        } else if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            championDetailActivity(feedDetail.getCreatedBy(), feedDetail.getItemPosition(), feedDetail.isAuthorMentor(), AppConstants.FEED_SCREEN);
        } else if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            championDetailActivity(comment.getParticipantId(), comment.getItemPosition(), comment.isVerifiedMentor(), AppConstants.COMMENT_REACTION_FRAGMENT);
        }
    }
    // endregion

    // region Protected methods
    @OnClick({R.id.beginner})
    protected void openBeginnerDialog() {
        openProfileActivity(ProfileStrengthDialog.ProfileStrengthType.BEGINNER);
    }

    @OnClick(R.id.intermediate)
    protected void openIntermediateProgressDialog() {
        openProfileActivity(ProfileStrengthDialog.ProfileStrengthType.INTERMEDIATE);
    }

    @OnClick(R.id.all_star)
    protected void openAllStarProgressDialog() {
        openProfileActivity(ProfileStrengthDialog.ProfileStrengthType.ALLSTAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsInviteReferral) {
            if (!this.isFinishing() && null != mProgressDialog) {
                mProgressDialog.dismiss();
            }
            mIsInviteReferral = false;
        } else {
            setProfileImage();
        }
        resetHamburgerSelectedItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mActivityDataPresenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        resetHamburgerSelectedItems();
        if (resultCode == AppConstants.RESULT_CODE_FOR_DEACTIVATION) {
            refreshCurrentFragment();
        } else if (resultCode == AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED) {
            Parcelable parcelable = intent.getParcelableExtra(AppConstants.USER_FOLLOWED_DETAIL);
            if (parcelable != null) {
                UserSolrObj userSolrObj = Parcels.unwrap(parcelable);
                invalidatePostItem(userSolrObj, userSolrObj.getIdOfEntityOrParticipant());

            }
        } else if (resultCode == AppConstants.REQUEST_CODE_FOR_USER_LISTING) {
            Parcelable parcelable = intent.getParcelableExtra(AppConstants.USER_FOLLOWED_DETAIL);
            if (parcelable != null) {
                List<FeedDetail> userSolrObj = Parcels.unwrap(parcelable);
                for (int i = 0; i < userSolrObj.size(); i++) {
                    FeedDetail userSolrObj1 = userSolrObj.get(i);
                    invalidatePostItem(userSolrObj1, userSolrObj1.getIdOfEntityOrParticipant());
                }
            }
        } else if (requestCode == AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_article_card_view);
            if (fragment instanceof CommunitiesListFragment) {
                CommunitiesListFragment currentFragment = (CommunitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fl_article_card_view);
                if (currentFragment != null && currentFragment.isVisible()) {
                    currentFragment.refreshList();
                }
            }
        } else   if (resultCode == REQUEST_CODE_FOR_EDIT_PROFILE) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mProfileFragment.updateProfileDetails(bundle.getString(EditUserProfileActivity.USER_NAME), bundle.getString(EditUserProfileActivity.LOCATION), bundle.getString(EditUserProfileActivity.USER_DESCRIPTION), bundle.getString(EditUserProfileActivity.IMAGE_URL),
                        bundle.getString(EditUserProfileActivity.FILLED_FIELDS), bundle.getString(EditUserProfileActivity.UNFILLED_FIELDS), bundle.getFloat(EditUserProfileActivity.PROFILE_COMPLETION_WEIGHT));
            }
        } else {
            if (null != intent) {
                switch (requestCode) {
                    case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                        Snackbar.make(mFloatActionBtn, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT).show();
                        refreshCurrentFragment();
                        break;
                    case AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL:
                        if (resultCode == Activity.RESULT_OK) {
                            refreshCurrentFragment();
                        }
                        break;

                    case AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL:
                        Parcelable parcelableArticlePost = intent.getParcelableExtra(AppConstants.HOME_FRAGMENT);
                        ArticleSolrObj articleSolrObj = null;
                        if (parcelableArticlePost != null && Parcels.unwrap(parcelableArticlePost) instanceof ArticleSolrObj) {
                            articleSolrObj = Parcels.unwrap(parcelableArticlePost);
                        }
                        if (articleSolrObj != null) {
                            invalidateItem(articleSolrObj);
                        }
                        break;

                    case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                        boolean isPostDeleted = false;
                        FeedDetail feedDetail = null;
                        Parcelable parcelableUserPost = intent.getParcelableExtra(FeedDetail.FEED_COMMENTS);
                        if (parcelableUserPost != null) {
                            feedDetail = Parcels.unwrap(parcelableUserPost);
                            isPostDeleted = intent.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                        }
                        if (feedDetail == null) {
                            break;
                        }
                        if (isPostDeleted) {
                            removeItem(feedDetail);
                        } else {
                            invalidateItem(feedDetail);
                        }
                    case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    case AppConstants.REQUEST_CODE_FOR_GALLERY:
                        mImageCaptureUri = intent.getData();
                        if (resultCode == Activity.RESULT_OK) {
                            mProfileFragment.croppingIMG();
                        }
                        break;
                    case AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING:
                        mProfileFragment.imageCropping(intent);
                        break;
                    case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                        CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                        if (resultCode == RESULT_OK) {
                            try {
                                if (result != null && result.getUri() != null && result.getUri().getPath() != null) {
                                    File file = new File(result.getUri().getPath());
                                    Bitmap photo = CompressImageUtil.decodeFile(file);
                                    mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mProfileFragment.getUserSummaryDetails(mAppUtils.getUserProfileRequestBuilder(AppConstants.PROFILE_PIC_SUB_TYPE, AppConstants.PROFILE_PIC_TYPE, mEncodeImageUrl));
                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                            Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
                }
            }
        }
        if (!this.isFinishing() && null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.WRITE_STORY_URL)) {
                    writeAStory();
                } else if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.SELECT_LANGUAGE_URL_COM)) {
                    showSelectLanguageOption();
                } else if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(ArticlesFragment.SCREEN_LABEL)) {
                    openArticleFragment(intent);
                } else if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.COMMUNITY_URL)) {
                    communityOnClick();
                } else if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.CHAMPION_URL)) {
                    mentorListActivity();
                } else if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.FAQ_URL)) {
                    renderFAQSView();
                } else if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.ICC_MEMBERS_URL)) {
                    renderICCMemberListView();
                }
            } else if (CommonUtil.isNotEmpty(intent.getStringExtra(AppConstants.HELPLINE_CHAT)) && intent.getStringExtra(AppConstants.HELPLINE_CHAT).equalsIgnoreCase(AppConstants.HELPLINE_CHAT)) {
                handleHelpLineFragmentFromDeepLinkAndLoading();
            } else {
                homeOnClick();
            }
        }
    }
    //endregion

    // region Private methods
    private void openArticleFragment(Intent intent) {
        if (intent.hasExtra(AppConstants.ARTICLE_CATEGORY_ID)) {
            long categoryId = intent.getLongExtra(AppConstants.ARTICLE_CATEGORY_ID, -1);
            if (categoryId > -1) {
                setArticleCategoryFilterValues(categoryId);
                if (StringUtil.isNotEmptyCollection(mArticleCategoryItemList)) {
                    articleCategorySelected();
                }
            }
        } else {
            openArticleFragment(setCategoryIds(), false);
        }
    }

    private void toolTipForNotification() {
        try {
            if (CommonUtil.forGivenCountOnly(AppConstants.NOTIFICATION_SESSION_SHARE_PREF, AppConstants.NOTIFICATION_SESSION) == AppConstants.NOTIFICATION_SESSION) {
                if (CommonUtil.ensureFirstTime(AppConstants.NOTIFICATION_SHARE_PREF)) {
                    Tooltip.Builder builder = new Tooltip.Builder(mTvNotification, R.style.Tooltip)
                            .setCancelable(true)
                            .setDismissOnClick(true)
                            .setGravity(Gravity.BOTTOM)
                            .setText(R.string.tool_tip_notification);
                    builder.show();
                }
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    private void sheUserInit() {
        if (mIsSheUser && startedFirstTime()) {
            openHelplineFragment();
        }

        if (StringUtil.isNotNullOrEmptyString(mHelpLineChat) && mHelpLineChat.equalsIgnoreCase(AppConstants.HELPLINE_CHAT)) {
            handleHelpLineFragmentFromDeepLinkAndLoading();
        }
        if (getIntent() != null) {
            if (CommonUtil.isNotEmpty(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase("Community List")) {
                    communityOnClick();
                } else if (getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.FAQ_URL)) {
                    renderFAQSView();
                } else if (getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.ICC_MEMBERS_URL)) {
                    renderICCMemberListView();
                }
                if (CommonUtil.isNotEmpty(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                    if (getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(ArticlesFragment.SCREEN_LABEL)) {
                        openArticleFragment(getIntent());
                    }
                }
                if (CommonUtil.isNotEmpty(getIntent().getStringExtra(AppConstants.HELPLINE_CHAT)) && getIntent().getStringExtra(AppConstants.HELPLINE_CHAT).equalsIgnoreCase(AppConstants.HELPLINE_CHAT)) {
                    handleHelpLineFragmentFromDeepLinkAndLoading();
                }
                if (CommonUtil.isNotEmpty(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                    if (getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.CHAMPION_URL)) {
                        mentorListActivity();
                    }
                }
                if (getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.SELECT_LANGUAGE_URL_COM)) {
                    showSelectLanguageOption();
                }
            }
        }
    }

    private boolean startedFirstTime() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            return getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID) == 0;
        } else {
            return true;
        }
    }

    private void assignNavigationRecyclerListView() {
        mAdapter = new GenericRecyclerViewAdapter(this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        //For navigation drawer items
        mActivityDataPresenter.getNavigationDrawerOptions(mAppUtils.navigationOptionsRequestBuilder());

        mMyCommunitiesAdapter = new MyCommunitiesDrawerAdapter(this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewDrawerCommunities.setLayoutManager(gridLayoutManager);
        mRecyclerViewDrawerCommunities.setAdapter(mMyCommunitiesAdapter);
        //For right navigation drawer communities items
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_DRAWER, AppConstants.NO_REACTION_CONSTANT);
        pbCommunitiesDrawer.setVisibility(View.VISIBLE);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mActivityDataPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mRecyclerViewDrawerCommunities.addOnScrollListener(new HidingScrollListener(mActivityDataPresenter, mRecyclerViewDrawerCommunities, gridLayoutManager, mFragmentListRefreshData) {
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
        ((SimpleItemAnimator) mRecyclerViewDrawerCommunities.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void deepLinkingRedirection() {
        // params are the deep linked params associated with the link that the user clicked before showing up
        // params will be empty if no data found
        Branch branch = Branch.getInstance(getApplicationContext());
        JSONObject sessionParams = branch.getFirstReferringParams();
        Intent intent;
        try {
            if (sessionParams.length() > 0) {
                String url = sessionParams.getString(AppConstants.DEEP_LINK_URL);
                String openWebViewFlag = sessionParams.getString(AppConstants.OPEN_IN_WEBVIEW);
                if (StringUtil.isNotNullOrEmptyString(url)) {
                    if (openWebViewFlag.equalsIgnoreCase("true")) {
                        Uri urlWebSite = Uri.parse(url);
                        AppUtils.openChromeTabForce(this, urlWebSite);
                        return;
                    }
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (url.startsWith("https://sheroes.com") || url.startsWith("http://sheroes.com") || url.startsWith("https://sheroes.in") || url.startsWith("http://sheroes.in")) {
                        // Do not let others grab our call
                        intent.setPackage(SheroesApplication.mContext.getPackageName());
                    } else {
                        startActivity(intent);
                        return;
                    }
                    Iterator<String> iterator = sessionParams.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        try {
                            Object value = sessionParams.get(key);
                            if (value instanceof String) {
                                intent.putExtra(key, (String) value);
                            }
                            if (value instanceof Boolean) {
                                intent.putExtra(key, (boolean) value);
                            }
                            if (value instanceof Integer) {
                                intent.putExtra(key, (int) value);
                            }
                        } catch (JSONException e) {
                            Crashlytics.getInstance().core.logException(e);
                        }
                    }
                    if (isIntentAvailable(this, intent)) {
                        intent.putExtra(BaseActivity.SOURCE_SCREEN, getScreenName());
                        if (Uri.parse(url).getPath().equals("/home/") && intent.getExtras() != null) {
                            intent.setClass(this, SheroesDeepLinkingActivity.class);
                        }
                        startActivity(intent);
                    }
                }
            }
            // }
        } catch (JSONException e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void animateSnowFlake() {
        mSnowFlakView.setVisibility(View.VISIBLE);
        ValueAnimator getmTranslateAnimatorLeft;
        getmTranslateAnimatorLeft = ObjectAnimator.ofFloat(mSantaView, "translationX", CommonUtil.convertDpToPixel(192, this), -(CommonUtil.getWindowWidth(this)));
        Interpolator mSelectedInterpolator = new AccelerateDecelerateInterpolator();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(mSelectedInterpolator);
        animatorSet.setStartDelay(ANIMATION_DELAY_TIME);
        animatorSet.setDuration(ANIMATION_DURATION_TIME);
        animatorSet.play(getmTranslateAnimatorLeft);
        animatorSet.start();
        getmTranslateAnimatorLeft.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mSantaView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSantaView.setVisibility(View.GONE);
            }
        });
    }

    private boolean shouldShowSnowFlake() {
        boolean showSnowFlake = false;
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SNOW))) {
            String snowFlakeFlag = "";
            snowFlakeFlag = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SNOW).get(0).getLabel();
            if (CommonUtil.isNotEmpty(snowFlakeFlag)) {
                if (snowFlakeFlag.equalsIgnoreCase("true")) {
                    showSnowFlake = true;
                }
            }
        }
        return showSnowFlake;
    }

    private boolean isWhatsAppShare() {
        boolean isWhatsappShare = false;
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareText = "";
            shareText = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareText)) {
                if (shareText.equalsIgnoreCase("true")) {
                    isWhatsappShare = true;
                }
            }
        }
        return isWhatsappShare;
    }

    private void setProfileImage() { //Drawer top image
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            String profile = mUserPreference.get().getUserSummary().getPhotoUrl();
            if (null != profile) {
                ivDrawerProfileCircleIcon.setCircularImage(true);
                ivDrawerProfileCircleIcon.setPlaceHolderId(R.drawable.vector_default_img);
                ivDrawerProfileCircleIcon.setErrorPlaceHolderId(R.drawable.vector_default_img);
                String authorThumborUrl = CommonUtil.getThumborUri(profile, navProfileSize, navProfileSize);
                ivDrawerProfileCircleIcon.bindImage(authorThumborUrl);
            }
            String profileUserName = getResources().getString(R.string.PLACEHOLDER_PROFILE_USER_NAME, mUserPreference.get().getUserSummary().getFirstName(), mUserPreference.get().getUserSummary().getLastName());
            mTvUserName.setText(profileUserName);
            if (mUserPreference.get().getUserSummary().getEmailId() != null) {
                mTvUserLocation.setText(mUserPreference.get().getUserSummary().getEmailId());
            }
        }
    }

    private void setArticleCategoryFilterValues(long categoryId) {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get().getData()) {
            HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult = mUserPreferenceMasterData.get().getData();
            if (null != masterDataResult && null != masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY)) {
                {
                    HashMap<String, ArrayList<LabelValue>> hashMap = masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY);
                    List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
                    if (StringUtil.isNotEmptyCollection(labelValueArrayList)) {
                        List<ArticleCategory> articleCategoryList = new ArrayList<>();
                        ArticleCategory first = new ArticleCategory();
                        first.setName(AppConstants.FOR_ALL);
                        articleCategoryList.add(first);
                        for (LabelValue lookingFor : labelValueArrayList) {

                            ArticleCategory articleCategory = new ArticleCategory();
                            articleCategory.setId(lookingFor.getValue());
                            if (categoryId > 0) {
                                if (lookingFor.getValue() == categoryId) {
                                    articleCategory.setChecked(true);
                                }
                            }
                            articleCategory.setName(lookingFor.getLabel());
                            articleCategoryList.add(articleCategory);
                        }
                        mArticleCategoryItemList = articleCategoryList;
                    }
                }
            }
        }
    }

    private void challengeIdHandle(String urlOfSharedCard) {
        if (urlOfSharedCard.contains(AppConstants.CHALLENGE_URL) || urlOfSharedCard.contains(AppConstants.CHALLENGE_URL_COM)) {
            try {
                int indexOfFirstEqual = AppUtils.findNthIndexOf(urlOfSharedCard, "=", 1);
                String challengeUrl = urlOfSharedCard.substring(indexOfFirstEqual + 1, urlOfSharedCard.length());
                if (StringUtil.isNotNullOrEmptyString(challengeUrl)) {
                    String ChallengeId = challengeUrl;
                    byte[] challengeBytes = Base64.decode(ChallengeId, Base64.DEFAULT);
                    String newChallengeId = new String(challengeBytes, AppConstants.UTF_8);
                    homeOnClick();
                }
            } catch (UnsupportedEncodingException e) {
                Crashlytics.getInstance().core.logException(e);
                e.printStackTrace();
            }
        } else {
            Uri url = Uri.parse(urlOfSharedCard);
            Intent intent = new Intent(this, SheroesDeepLinkingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 0);
            intent.putExtras(bundle);
            intent.setData(url);
            startActivity(intent);
        }
    }

    private void openNativeViews(String url) {
        if (null != url && StringUtil.isNotNullOrEmptyString(url)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    private void drawerItemOptions(View view, BaseResponse baseResponse) {
        NavMenuItem navMenuItem = (NavMenuItem) baseResponse;

        if (mDrawer.isDrawerOpen(GravityCompat.START) || mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.START);
            mDrawer.closeDrawer(GravityCompat.END);
        }

        String url = navMenuItem.getMenuUrl();
        String menuName = navMenuItem.getMenuName();

        if (url.contains(AppConstants.LOG_OUT_URL)) {
            logOut();
        } else if (url.contains("#") && menuName.equalsIgnoreCase(AppConstants.INVITE_FRIENDS)) {
            inviteMyCommunityDialog();
        } else {
            if (navMenuItem.isNative()) {
                openNativeViews(url);
            } else {
                openWebUrlFragment(url, menuName);
            }
        }
        resetHamburgerSelectedItems();
    }

    private void mentorListActivity() {
        Intent intent = new Intent(this, ChampionListingActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void feedRelatedOptions(View view, BaseResponse baseResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.icon:
                if (baseResponse instanceof CarouselDataObj) {
                    CarouselDataObj carouselDataObj = (CarouselDataObj) baseResponse;
                    if (carouselDataObj.getFeedDetails().get(0) instanceof UserSolrObj) {
                        mentorListActivity();
                    } else {
                        navigateToCollectionScreen(carouselDataObj);
                    }
                }
                break;

            case R.id.iv_header_circle_icon:
                mFeedDetail = (FeedDetail) baseResponse;
                championDetailActivity(mFeedDetail.getEntityOrParticipantId(), 0, mFeedDetail.isAuthorMentor(), AppConstants.FEED_HEADER);
                break;

            case R.id.user_name:
                mFeedDetail = (FeedDetail) baseResponse;
                championDetailActivity(mFeedDetail.getEntityOrParticipantId(), 0, mFeedDetail.isAuthorMentor(), AppConstants.FEED_HEADER);
                break;

            case R.id.header_msg:
                mFeedDetail = (FeedDetail) baseResponse;
                CommunityPost communityPost = new CommunityPost();
                communityPost.createPostRequestFrom = AppConstants.CREATE_POST;
                createCommunityPostOnClick(communityPost);
                break;
            case R.id.share:
                if (StringUtil.isNotNullOrEmptyString(((FeedDetail) baseResponse).getPostShortBranchUrls())) {
                    ((FeedDetail) baseResponse).setDeepLinkUrl(((FeedDetail) baseResponse).getPostShortBranchUrls());
                }
                String shareText = Config.COMMUNITY_POST_CHALLENGE_SHARE + System.getProperty("line.separator") + ((FeedDetail) baseResponse).getDeepLinkUrl();
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
                trackEvent(Event.CHALLENGE_SHARED_CLICKED, properties);
                ShareBottomSheetFragment.showDialog(this, shareText, ((FeedDetail) baseResponse).getThumbnailImageUrl(), ((FeedDetail) baseResponse).getDeepLinkUrl(), SOURCE_SCREEN, true, ((FeedDetail) baseResponse).getDeepLinkUrl(), true, Event.CHALLENGE_SHARED, properties);
                break;
            default:
                if (mFeedDetail instanceof UserPostSolrObj) {
                    mFragmentOpen.setOwner(((UserPostSolrObj) mFeedDetail).isCommunityOwner());
                }
                mFeedUtils.feedCardsHandled(view, baseResponse, this, getScreenName());
        }
    }

    private void navigateToCollectionScreen(CarouselDataObj carouselDataObj) {
        if (carouselDataObj != null) {
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .name(MORE_TOP_ICON)
                            .communityCategory(carouselDataObj.getScreenTitle())
                            .build();
            CollectionActivity.navigateTo(this, carouselDataObj.getEndPointUrl(), carouselDataObj.getScreenTitle(), MORE_TOP_ICON, COMMUNITY_CATEGORY_SCREEN, properties, REQUEST_CODE_FOR_COMMUNITY_LISTING);
        }
    }

    private void openProfileActivity(ProfileStrengthDialog.ProfileStrengthType profileStrengthType) {
        //TODO - Its was added to show profile strength on Nav menu, required api changes, future task
       // ProfileFragment.createInstance(mUserId, mIsChampion, -1, AppConstants.DRAWER_NAVIGATION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, false);
        ProfileActivity.navigateTo(this, mUserId, mIsChampion, -1, AppConstants.DRAWER_NAVIGATION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, false);
    }

    private void handleHelpLineFragmentFromDeepLinkAndLoading() {
        openHelplineFragment();
    }

    private void renderFAQSView() {
        changeFragmentWithCommunities();
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FAQSFragment faqsFragment = new FAQSFragment();
        addNewFragment(faqsFragment, R.id.fl_article_card_view, FAQSFragment.class.getName(), null, false);

    }

    private void renderICCMemberListView() {
        changeFragmentWithCommunities();
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ICCMemberListFragment iccMemberListFragment = new ICCMemberListFragment();
        addNewFragment(iccMemberListFragment, R.id.fl_article_card_view, ICCMemberListFragment.class.getName(), null, false);
    }

    private void initHomeViewPagerAndTabs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAppBarLayout.setElevation(0f);
        } else {
            ViewCompat.setElevation(mAppBarLayout, 0f);
        }
       // mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
//        mTvHome.setText(getString(R.string.home_label));
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SCREEN_NAME, SCREEN_LABEL);
        homeFragment.setArguments(bundle);
        mFragmentOpen.setFeedFragment(true);
        addNewFragment(homeFragment, R.id.fl_article_card_view, HomeFragment.class.getName(), null, false);
    }

    private void highlightHome(){
        mTvHome.setImageResource(R.drawable.home_red_vector);
        mTvSearch.setImageResource(R.drawable.search_grey_vector);
        mTvCommunities.setImageResource(R.drawable.profile_grey_vector);
    }

    private void highlightSearch(){
        mTvHome.setImageResource(R.drawable.ic_home_unselected_icon);
        mTvSearch.setImageResource(R.drawable.search_red_vector);
        mTvCommunities.setImageResource(R.drawable.profile_grey_vector);
    }

    private void highlightProfile(){
        mTvHome.setImageResource(R.drawable.ic_home_unselected_icon);
        mTvSearch.setImageResource(R.drawable.search_grey_vector);
        mTvCommunities.setImageResource(R.drawable.profile_red_vector);
    }

    private void openSearchFragment(){
        SearchFragment searchFragment = new SearchFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        addNewFragment(searchFragment, R.id.fl_article_card_view, SearchFragment.class.getName(), null, false);
        mAppBarLayout.setVisibility(View.GONE);

    }

    private void initCommunityViewPagerAndTabs() {
        CommunitiesListFragment communitiesListFragment = new CommunitiesListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        addNewFragment(communitiesListFragment, R.id.fl_article_card_view, CommunitiesListFragment.class.getName(), null, false);
    }

    private void initProfileViewPagerAndTabs() {
        mProfileFragment = ProfileFragment.createInstance(null, null, null, mUserId, mIsChampion, -1, AppConstants.DRAWER_NAVIGATION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        addNewFragment(mProfileFragment, R.id.fl_article_card_view, ProfileFragment.class.getName(), null, false);
    }

    private void resetHamburgerSelectedItems() { //Reset navigation drawer selected item
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
        if (null != mMyCommunitiesAdapter) {
            mMyCommunitiesAdapter.notifyDataSetChanged();
        }
    }

    private void openArticleFragment(List<Long> categoryIds, boolean fromDrawer) {
        changeFragmentWithCommunities();
        ArticlesFragment articlesFragment = new ArticlesFragment();
        FragmentManager fm = getSupportFragmentManager();
        if (fromDrawer) {
            fm.popBackStackImmediate(ArticlesFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Bundle bundleArticle = new Bundle();
        bundleArticle.putSerializable(AppConstants.ARTICLE_FRAGMENT, (ArrayList) categoryIds);
        articlesFragment.setArguments(bundleArticle);
        addNewFragment(articlesFragment, R.id.fl_article_card_view, ArticlesFragment.class.getName(), ArticlesFragment.class.getName(), true);
    }

    private void openHelplineFragment() {
        Intent helplineIntent = new Intent(this, HelplineActivity.class);
        startActivity(helplineIntent);
    }

    private void removeItem(FeedDetail feedDetail) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (fragment != null) {
            ((HomeFragment) fragment).removeItem(feedDetail);
        }
    }

    private void invalidateItem(FeedDetail feedDetail) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (fragment != null) {
            ((HomeFragment) fragment).invalidateItem(feedDetail);
        }
    }

    private void invalidatePostItem(FeedDetail feedDetail, long id) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (fragment != null) {
            ((HomeFragment) fragment).refreshAtPosition(feedDetail, id);
        }
    }

    private void refreshCurrentFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (fragment != null) {
            ((HomeFragment) fragment).refreshCurrentFragment();
        }
    }

    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return null;
    }

    private List<Long> setCategoryIds() {
        List<Long> categoryIds = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mArticleCategoryItemList)) {
            for (ArticleCategory articleCategory : mArticleCategoryItemList) {
                if (articleCategory.isChecked()) {
                    categoryIds.add(articleCategory.getId());
                }
            }
        }
        return categoryIds;
    }

    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
//        ProfileFragment.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);

        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void championLinkHandle(UserPostSolrObj userPostSolrObj) {
        ProfileActivity.navigateTo(this, userPostSolrObj.getAuthorParticipantId(), mIsChampion, PROFILE_NOTIFICATION_ID,
                AppConstants.FEED_SCREEN, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL, false);
    }

    private void unReadNotificationCount(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof NotificationReadCountResponse) {
                    NotificationReadCountResponse notificationReadCountResponse = (NotificationReadCountResponse) baseResponse;
                    StringBuilder stringBuilder = new StringBuilder();
                    int notificationCount = notificationReadCountResponse.getUnread_notification_count();
                    if (notificationReadCountResponse.getUnread_notification_count() > 0) {
                        flNotificationReadCount.setVisibility(View.VISIBLE);
                        String notification = String.valueOf(notificationCount);
                        stringBuilder.append(notification);
                        mTvNotificationReadCount.setText(stringBuilder.toString());
                    } else {
                        flNotificationReadCount.setVisibility(View.GONE);
                    }
                }
                break;
            case AppConstants.FAILED:
                flNotificationReadCount.setVisibility(View.GONE);
                break;
            default:
                flNotificationReadCount.setVisibility(View.GONE);
        }
    }

    private void fcmIdResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof GcmIdResponse) {
                    LoginResponse loginResponse = mUserPreference.get();
                    loginResponse.setFcmId(mFcmId);
                    mUserPreference.set(loginResponse);
                    AppStatus appStatus = mInstallUpdatePreference.get();
                    appStatus.setFirstOpen(false);
                    mInstallUpdatePreference.set(appStatus);
                }
                break;
            case AppConstants.FAILED:
                break;
            default:
        }
    }

    private void getAppContactsListSuccess(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                LoginResponse loginResponse = mUserPreference.get();
                loginResponse.setAppContactAccessed(true);
                mUserPreference.set(loginResponse);
        }
    }

    private void getFcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FCMClientManager pushClientManager = new FCMClientManager(this, getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new FCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                mFcmId = registrationId;
                //Refresh FCM token
                CleverTapAPI cleverTapAPI = CleverTapHelper.getCleverTapInstance(SheroesApplication.mContext);
                if (cleverTapAPI != null) {
                    cleverTapAPI.data.pushFcmRegistrationId(registrationId, true);
                }
                if (StringUtil.isNotNullOrEmptyString(registrationId)) {
                    if (mAppInstallation != null && mAppInstallation.isSet()) {
                        AppInstallation appInstallation = mAppInstallation.get();
                        appInstallation.fcmId = registrationId;
                        mAppInstallation.set(appInstallation);
                    }
                    if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet()) {
                        if (mInstallUpdatePreference.get().isFirstOpen()) {
                            LoginRequest loginRequest = loginRequestBuilder();
                            loginRequest.setFcmorapnsid(registrationId);
                            if (mInstallUpdatePreference.get().isWelcome()) {
                                AppStatus appStatus = mInstallUpdatePreference.get();
                                appStatus.setWelcome(false);
                                mInstallUpdatePreference.set(appStatus);
                            }
                            mHomePresenter.getNewFCMidFromPresenter(loginRequest);
                        } else {
                            if (null != mUserPreference && mUserPreference.isSet() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getFcmId())) {
                                String mOldFcmId = mUserPreference.get().getFcmId();
                                if (StringUtil.isNotNullOrEmptyString(mOldFcmId)) {
                                    if (!mOldFcmId.equalsIgnoreCase(registrationId)) {
                                        LoginRequest loginRequest = loginRequestBuilder();
                                        loginRequest.setFcmorapnsid(registrationId);
                                        mHomePresenter.getNewFCMidFromPresenter(loginRequest);
                                    }
                                }
                            }
                            if (mInstallUpdatePreference.get().isWelcome()) {
                                AppStatus appStatus = mInstallUpdatePreference.get();
                                appStatus.setWelcome(false);
                                mInstallUpdatePreference.set(appStatus);
                            }
                        }
                    }
                } else {
                    getFcmId();
                }
            }

            @Override
            public void onFailure(String ex) {

            }
        });
    }
    //endregion
}