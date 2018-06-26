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
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.login.LoginManager;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;
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
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.animation.SnowFlakeView;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.ProgressbarView;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotification;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationItems;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.MainActivityPresenter;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunitiesDrawerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ShowcaseManager;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FAQSFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ICCMemberListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MainActivityNavDrawerView;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EventDetailDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileProgressDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_LISTING;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.notificationReadCountRequestBuilder;

public class HomeActivity extends BaseActivity implements MainActivityNavDrawerView, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, ArticleCategorySpinnerFragment.HomeSpinnerFragmentListner, HomeView, ProgressbarView {
    private static final String SCREEN_LABEL = "Home Screen";
    private final String TAG = LogUtils.makeLogTag(HomeActivity.class);
    private static final int ANIMATION_DELAY_TIME = 2000;
    private static final int ANIMATION_DURATION_TIME = 5000;
    private static final int APP_BAR_ELEVATION = 10;

    // region inject variables
    @Inject
    Preference<Configuration> mConfiguration;
    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;

    @Inject
    Preference<AppInstallation> mAppInstallation;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    boolean doubleBackToExitPressedOnce = false;

    @Inject
    MainActivityPresenter activityDataPresenter;

    @Inject
    AppUtils mAppUtils;
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

    @Bind(R.id.invite)
    ImageView mInvite;

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
    public TextView mTvHome;

    @Bind(R.id.tv_communities)
    public TextView mTvCommunities;

    @Bind(R.id.tv_home_notification_icon)
    TextView mTvNotification;

    @Bind(R.id.title_text)
    TextView mTitleText;

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


    @Bind(R.id.iv_new_tag)
    public ImageView ivNewTag;

    @BindDimen(R.dimen.dp_size_64)
    int navProfileSize;
    // endregion

    // region member variables
    private GenericRecyclerViewAdapter mAdapter;
    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    private ArticleCategorySpinnerFragment mArticleCategorySpinnerFragment;
    private FragmentOpen mFragmentOpen;
    private CustiomActionBarToggle mCustiomActionBarToggle;
    private FeedDetail mFeedDetail;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private long mChallengeId;
    private String mHelpLineChat;
    private EventDetailDialogFragment eventDetailDialogFragment;
    private ProgressDialog mProgressDialog;
    private boolean isInviteReferral;
    private BellNotificationDialogFragment bellNotificationDialogFragment;
    private boolean isSheUser = false;
    private long mUserId = -1L;
    boolean isMentor;
    private int mEventId;
    public boolean mIsFirstTimeOpen = false;
    private String mGcmId;
    private ShowcaseManager showcaseManager;
    private String mUserName;
    private FragmentListRefreshData mFragmentListRefreshData;
    private MyCommunitiesDrawerAdapter mMyCommunitiesAdapter;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private SwipPullRefreshList mPullRefreshList;
    //endregion

    // region Public methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        activityDataPresenter.attachView(this);
        mHomePresenter.attachView(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        moEngageUtills.entityMoEngageViewFeed(this, mMoEHelper, payloadBuilder, 0);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserId()) {
            isSheUser = mUserPreference.get().isSheUser();
            mUserId = mUserPreference.get().getUserSummary().getUserId();
            mUserName = mUserPreference.get().getUserSummary().getFirstName();
            if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.MENTOR_TYPE_ID) {
                isMentor = true;
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
            getGcmId();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
        toolTipForNotification();
        if (CommonUtil.ensureFirstTime(AppConstants.NEW_TAG_FOR_RIGHT_SWIP)) {
            ivNewTag.setVisibility(View.VISIBLE);
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
                branch.initSession(new Branch.BranchReferralInitListener() {
                                       @Override
                                       public void onInitFinished(JSONObject referringParams, BranchError error) {
                                           deepLinkingRedirection(referringParams);
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
        mInvite.setVisibility(View.VISIBLE);
        mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustiomActionBarToggle);
        mNavigationViewLeftDrawer.setNavigationItemSelectedListener(this);
        mNavigationViewRightDrawerWithCommunities.setNavigationItemSelectedListener(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        if (null != getIntent() && null != getIntent().getExtras()) {
            if (getIntent().getExtras().get(AppConstants.CHALLENGE_ID) != null) {
                mChallengeId = getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID);
            }
            if (getIntent().getExtras().get(AppConstants.HELPLINE_CHAT) != null) {
                mHelpLineChat = getIntent().getExtras().getString(AppConstants.HELPLINE_CHAT);
            }
            mEventId = getIntent().getExtras().getInt(AppConstants.EVENT_ID);
            mFloatActionBtn.setTag(AppConstants.FEED_SUB_TYPE);
            if (!isSheUser) {
                initHomeViewPagerAndTabs();
            }
            if (mEventId > 0) {
                eventDetailDialog(mEventId);
            }
        }
        setConfigurableShareOption(isWhatsAppShare());
    }

    public void showCaseDesign() {
        if (mIsFirstTimeOpen) {
            this.mIsFirstTimeOpen = false;
            showcaseManager = new ShowcaseManager(this, mFloatActionBtn, mTvHome, mTvCommunities, tvDrawerNavigation, mRecyclerView, mUserName);
            showcaseManager.showFirstMainActivityShowcase();
            InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
            installUpdateForMoEngage.setAppInstallFirstTime(true);
            mInstallUpdatePreference.set(installUpdateForMoEngage);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityDataPresenter.detachView();
    }

    public void openWebUrlFragment(String url, String menuItemName) { //To open the web-pages in app
        setAllValues(mFragmentOpen);
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        WebViewActivity.navigateTo(this, getScreenName(), null, url, menuItemName);
        DrawerViewHolder.selectedOptionName = menuItemName;
        setAppBarElevation();
    }

    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        AppUtils.hideKeyboard(mTvUserName, TAG);
        mDrawer.openDrawer(Gravity.START);
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_DRAWER_NAVIGATION));
    }

    @OnClick(R.id.fl_nav_communities)
    public void onClickNavigationCommunities() {
        ivNewTag.setVisibility(View.GONE);
        mDrawer.openDrawer(GravityCompat.END);
    }

    @OnClick(R.id.fab_filter)
    public void createPostActivity() {
        /*String fabString = (String) mFloatActionBtn.getTag();
        if (fabString.equalsIgnoreCase(AppConstants.FEED_SUB_TYPE)) {
            CommunityPost communityPost = new CommunityPost();
            communityPost.createPostRequestFrom = AppConstants.CREATE_POST;
            createCommunityPostOnClick(communityPost);

        }*/
        ArticleSubmissionActivity.navigateTo(this, 1, getScreenName(), null);

    }

    @OnClick(R.id.invite)
    public void onInviteClicked() {
        AllContactActivity.navigateTo(this, getScreenName(), null);
    }

    public void logOut() {
        AnalyticsManager.initializeMixpanel(HomeActivity.this);
        logOutUser();
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            feedRelatedOptions(view, baseResponse);
        } else if (baseResponse instanceof NavMenuItem) {
            drawerItemOptions(view, baseResponse);
        } else if (baseResponse instanceof Comment) {
            setAllValues(mFragmentOpen);
             /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU);
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
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
        ContestActivity.navigateTo(this, mContest, SCREEN_LABEL, null, 0, 0, AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL);
    }

    public void resetUiSelectedOptions() {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
    }

    public void eventDetailDialog(long eventID) {
        eventDetailDialogFragment = (EventDetailDialogFragment) getFragmentManager().findFragmentByTag(EventDetailDialogFragment.class.getName());
        if (eventDetailDialogFragment == null) {
            eventDetailDialogFragment = new EventDetailDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putLong(AppConstants.EVENT_ID, eventID);
            Parcelable parcelable = Parcels.wrap(mFeedDetail);
            bundle.putParcelable(AppConstants.EVENT_DETAIL, parcelable);
            eventDetailDialogFragment.setArguments(bundle);
        }
        if (!eventDetailDialogFragment.isVisible() && !eventDetailDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            eventDetailDialogFragment.show(getFragmentManager(), EventDetailDialogFragment.class.getName());
        }
    }

    @Override
    public List getListData() {
        return mHomeSpinnerItemList;
    }

    @Override
    public void onDrawerOpened() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            AppUtils.hideKeyboard(mTvUserName, TAG);
            AnalyticsManager.trackScreenView(getString(R.string.ID_DRAWER_NAVIGATION_COMMUNITIES), getScreenName(), null);
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
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);

        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.home_label));

        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));

        mFloatActionBtn.setVisibility(View.GONE);

        flFeedFullView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.li_article_spinner_icon)
    public void openSpinnerOnClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ArticleCategorySpinnerFragment.class.getName()); //check if fragment exist
        if (!AppUtils.isFragmentUIActive(fragment)) {
            mFlHomeFooterList.setVisibility(View.GONE);
            if (!StringUtil.isNotEmptyCollection(mHomeSpinnerItemList) && !StringUtil.isNotEmptyCollection(mFragmentOpen.getHomeSpinnerItemList())) {
                setArticleCategoryFilterValues();
                mFragmentOpen.setHomeSpinnerItemList(mHomeSpinnerItemList);
            } else if (StringUtil.isNotEmptyCollection(mFragmentOpen.getHomeSpinnerItemList())) {
                mHomeSpinnerItemList = mFragmentOpen.getHomeSpinnerItemList();
            }
            mArticleCategorySpinnerFragment = new ArticleCategorySpinnerFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mHomeSpinnerItemList);
            bundle.putParcelable(AppConstants.HOME_SPINNER_FRAGMENT, parcelable);
            mArticleCategorySpinnerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, mArticleCategorySpinnerFragment, ArticleCategorySpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    @OnClick(R.id.tv_home)
    public void homeOnClick() {
        DrawerViewHolder.selectedOptionName = null;
        resetHamburgerSelectedItems();
        flFeedFullView.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
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

        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.comment_text));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
        mTvHome.setText(getString(R.string.home_label));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));


        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mFloatActionBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.email)));
        mFloatActionBtn.setImageResource(R.drawable.ic_pencil);
        mFloatActionBtn.setTag(AppConstants.FEED_SUB_TYPE);

        mInvite.setVisibility(View.VISIBLE);

    }


    @OnClick({R.id.tv_communities, R.id.iv_communities_search})
    public void communityOnClick() {
        DrawerViewHolder.selectedOptionName = null;
        resetHamburgerSelectedItems();
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        initCommunityViewPagerAndTabs();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        communityButton();
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        }
        setAppBarElevation();
    }

    public void communityButton() {

        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.comment_text));
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));
        mTvHome.setText(getString(R.string.home_label));
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mInvite.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTitleText.setText(getString(R.string.ID_COMMUNITIES));
        mICSheroes.setVisibility(View.GONE);
    }

    public void createCommunityPostOnClick(CommunityPost communityPost) {
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
        //  PostBottomSheetFragment.showDialog(this, SCREEN_LABEL);
    }

    public void articleUi() {
        mliArticleSpinnerIcon.setVisibility(View.VISIBLE);
        mFloatActionBtn.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mInvite.setVisibility(View.VISIBLE);
        setAppBarElevation();
    }

    public void inviteMyCommunityDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.ID_INVITE_WOMEN_FRIEND));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        LoginManager.getInstance().logOut();
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_INVITES, GoogleAnalyticsEventActions.OPEN_INVITE_FB_FRDZ, AppConstants.EMPTY_STRING);

    }

    public void helplineUi() {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mInvite.setVisibility(View.VISIBLE);
        setAppBarElevation();
    }

    @Override
    public void onBackPressed() {
        if (isSheUser) {
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
                if (doubleBackToExitPressedOnce) {
                    getSupportFragmentManager().popBackStackImmediate();
                    finish();
                    return;
                }
                doubleBackToExitPressedOnce = true;
                if (flFeedFullView.getVisibility() == View.VISIBLE) {
                    Snackbar.make(mCLMainLayout, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();
                } else {
                    homeOnClick();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
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
        bellNotificationDialogFragment = (BellNotificationDialogFragment) getFragmentManager().findFragmentByTag(BellNotificationDialogFragment.class.getName());
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
    public void dataOperationOnClick(BaseResponse baseResponse) {
        setAllValues(mFragmentOpen);
        super.dataOperationOnClick(baseResponse);
    }

    @Override
    public void setListData(BaseResponse data, boolean isCheked) {
        List<HomeSpinnerItem> localList = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
            HomeSpinnerItem passedHomeItem = (HomeSpinnerItem) data;
            if (passedHomeItem.getName().equalsIgnoreCase(AppConstants.FOR_ALL)) {
                for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                    homeSpinnerItem.setChecked(passedHomeItem.isChecked());
                    localList.add(homeSpinnerItem);
                }
            } else {
                for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                    if (homeSpinnerItem.getId() == (passedHomeItem.getId())) {
                        homeSpinnerItem.setChecked(passedHomeItem.isChecked());
                        localList.add(homeSpinnerItem);
                    } else {
                        localList.add(homeSpinnerItem);
                    }
                }
            }
        }
        mHomeSpinnerItemList.clear();
        mHomeSpinnerItemList.addAll(localList);
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
        if (isSheUser) {
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
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

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
        switch (feedParticipationEnum) {
            case NOTIFICATION_COUNT:
                unReadNotificationCount(baseResponse);
                break;
            case GCM_ID:
                gcmIdResponse(baseResponse);
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
    }

    public void fetchAllCommunity() {
        mHomePresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
    }

    @Override
    public void onViewRendered(float dashWidth) {
        ConfigData configData = new ConfigData();
        int beginnerTickIndex = configData.beginnerStartIndex;
        int intermediateTickIndex = configData.intermediateStartIndex;

        if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
            beginnerTickIndex = mConfiguration.get().configData.beginnerStartIndex;
            intermediateTickIndex = mConfiguration.get().configData.intermediateStartIndex;
        }
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins((int) (dashWidth * beginnerTickIndex), 0, 0, 0);
        beginnerTick.setLayoutParams(buttonLayoutParams);

        RelativeLayout.LayoutParams intermediateLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        intermediateLayoutParams.setMargins((int) (dashWidth * intermediateTickIndex), 0, 0, 0);
        intermediateTick.setLayoutParams(intermediateLayoutParams);
    }

    @Override
    public void onCancelDone(int pressedEvent) {
        if (AppConstants.ONE_CONSTANT == pressedEvent) {
            getSupportFragmentManager().popBackStack(ArticlesFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mTvCategoryChoose.setVisibility(View.GONE);
            StringBuilder stringBuilder = new StringBuilder();
            mFragmentOpen.setHomeSpinnerItemList(mHomeSpinnerItemList);
            if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
                List<Long> categoryIds = new ArrayList<>();
                List<HomeSpinnerItem> localList = new ArrayList<>();
                for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                    if (homeSpinnerItem.isChecked()) {
                        categoryIds.add(homeSpinnerItem.getId());
                        if (!homeSpinnerItem.getName().equalsIgnoreCase(AppConstants.FOR_ALL)) {
                            stringBuilder.append(homeSpinnerItem.getName());
                            stringBuilder.append(AppConstants.COMMA);
                        }
                        homeSpinnerItem.setDone(true);
                    } else {
                        homeSpinnerItem.setDone(false);
                    }
                    localList.add(homeSpinnerItem);
                }
                if (StringUtil.isNotEmptyCollection(localList)) {
                    mHomeSpinnerItemList.clear();
                    mHomeSpinnerItemList.addAll(localList);
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
        } else {
            mHomeSpinnerItemList = mFragmentOpen.getHomeSpinnerItemList();
            List<HomeSpinnerItem> localList = new ArrayList<>();
            for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                if (homeSpinnerItem.isDone()) {
                    homeSpinnerItem.setChecked(true);
                } else {
                    homeSpinnerItem.setChecked(false);
                }
                localList.add(homeSpinnerItem);
            }
            if (StringUtil.isNotEmptyCollection(localList)) {
                mHomeSpinnerItemList.clear();
                mHomeSpinnerItemList.addAll(localList);
            }

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(ArticleCategorySpinnerFragment.class.getName()); //check if fragment exist
            if (AppUtils.isFragmentUIActive(fragment)) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
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
        if (mValue == REQUEST_CODE_FOR_SELF_PROFILE_DETAIL) {
            championDetailActivity(mUserId, 1, isMentor, AppConstants.FEED_SCREEN); //self profile
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
        openProfileActivity(ProfileProgressDialog.ProfileLevelType.BEGINNER);
    }

    @OnClick(R.id.intermediate)
    protected void openIntermediateProgressDialog() {
        openProfileActivity(ProfileProgressDialog.ProfileLevelType.INTERMEDIATE);
    }

    @OnClick(R.id.all_star)
    protected void openAllStarProgressDialog() {
        openProfileActivity(ProfileProgressDialog.ProfileLevelType.ALLSTAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInviteReferral) {
            if (!this.isFinishing() && null != mProgressDialog) {
                mProgressDialog.dismiss();
            }
            isInviteReferral = false;
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
        return activityDataPresenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        resetHamburgerSelectedItems();
        if (resultCode == AppConstants.RESULT_CODE_FOR_DEACTIVATION) {
            refreshCurrentFragment();
        } else if (requestCode == AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_article_card_view);
            if (fragment instanceof CommunitiesListFragment) {
                CommunitiesListFragment currentFragment = (CommunitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fl_article_card_view);
                if (currentFragment != null && currentFragment.isVisible()) {
                    currentFragment.refreshList();
                }
            }
        } else {
            if (null != intent) {
                switch (requestCode) {
                    case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                        Snackbar.make(mFloatActionBtn, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT)
                                .show();
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
                        UserPostSolrObj userPostSolrObj = null;
                        Parcelable parcelableUserPost = intent.getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
                        if (parcelableUserPost != null) {
                            userPostSolrObj = Parcels.unwrap(parcelableUserPost);
                            isPostDeleted = intent.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                        }
                        if (userPostSolrObj == null) {
                            break;
                        }
                        if (isPostDeleted) {
                            removeItem(userPostSolrObj);
                        } else {
                            invalidateItem(userPostSolrObj);
                        }
                    case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                        CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                        if (resultCode == RESULT_OK) {
                            try {
                                File file = new File(result.getUri().getPath());
                                Bitmap photo = decodeFile(file);
                            } catch (Exception e) {
                                Crashlytics.getInstance().core.logException(e);
                                e.printStackTrace();
                            }

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
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(ArticlesFragment.SCREEN_LABEL)) {
                    openArticleFragment(setCategoryIds(), false);
                }

                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.COMMUNITY_URL)) {

                    communityOnClick();
                }


                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.CHAMPION_URL)) {

                    mentorListActivity();
                }


                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.FAQ_URL)) {

                    renderFAQSView();
                }


                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.ICC_MEMBERS_URL)) {

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
        if (isSheUser && startedFirstTime()) {
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
                        openArticleFragment(setCategoryIds(), false);
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
        activityDataPresenter.getNavigationDrawerOptions(mAppUtils.navigationOptionsRequestBuilder());


        mMyCommunitiesAdapter = new MyCommunitiesDrawerAdapter(this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewDrawerCommunities.setLayoutManager(gridLayoutManager);
        mRecyclerViewDrawerCommunities.setAdapter(mMyCommunitiesAdapter);
        //For right navigation drawer communities items
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_DRAWER, AppConstants.NO_REACTION_CONSTANT);
        pbCommunitiesDrawer.setVisibility(View.VISIBLE);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        activityDataPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mRecyclerViewDrawerCommunities.addOnScrollListener(new HidingScrollListener(activityDataPresenter, mRecyclerViewDrawerCommunities, gridLayoutManager, mFragmentListRefreshData) {
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

    private void deepLinkingRedirection(JSONObject sessionParams) {
        // params are the deep linked params associated with the link that the user clicked before showing up
        // params will be empty if no data found
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
                ivDrawerProfileCircleIcon.setPlaceHolderId(R.drawable.default_img);
                ivDrawerProfileCircleIcon.setErrorPlaceHolderId(R.drawable.default_img);
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

    private void setArticleCategoryFilterValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get().getData()) {
            HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult = mUserPreferenceMasterData.get().getData();
            if (null != masterDataResult && null != masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY)) {
                {
                    HashMap<String, ArrayList<LabelValue>> hashMap = masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY);
                    List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
                    if (StringUtil.isNotEmptyCollection(labelValueArrayList)) {
                        List<HomeSpinnerItem> homeSpinnerItemList = new ArrayList<>();
                        HomeSpinnerItem first = new HomeSpinnerItem();
                        first.setName(AppConstants.FOR_ALL);
                        homeSpinnerItemList.add(first);
                        for (LabelValue lookingFor : labelValueArrayList) {

                            HomeSpinnerItem homeSpinnerItem = new HomeSpinnerItem();
                            homeSpinnerItem.setId(lookingFor.getValue());
                            homeSpinnerItem.setName(lookingFor.getLabel());
                            homeSpinnerItemList.add(homeSpinnerItem);
                        }
                        mHomeSpinnerItemList = homeSpinnerItemList;
                    }
                }
            }
        }
    }

    private Runnable openDrawerRunnable() {
        return new Runnable() {

            @Override
            public void run() {
                //  mDrawer.openDrawer(Gravity.START);
            }
        };
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
                    mChallengeId = Long.parseLong(newChallengeId);
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

        if (url.contains(getString(R.string.logoutUrl))) {
            logOut();
        } else if (url.contains("#") && menuName.equalsIgnoreCase(getString(R.string.ID_INVITE_WOMEN_FRIEND))) {
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
        Intent intent = new Intent(this, MentorsUserListingActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void feedRelatedOptions(View view, BaseResponse baseResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.icon:  //TODO - Fix hardcoding
                if (baseResponse instanceof CarouselDataObj) {
                    CarouselDataObj carouselDataObj = (CarouselDataObj) baseResponse;
                    if (carouselDataObj.getFeedDetails().get(0) instanceof UserSolrObj) {
                        mentorListActivity();
                    } else {
                        navigateToCollectionScreen(carouselDataObj);
                    }
                }
                break;

            case R.id.tv_mentor_ask_question:
                CommunityPost mentorPost = new CommunityPost();
                mFeedDetail = (FeedDetail) baseResponse;
                if (mFeedDetail instanceof UserSolrObj) {
                    UserSolrObj userSolrObj = (UserSolrObj) mFeedDetail;
                    mentorPost.community = new Community();
                    mentorPost.community.id = userSolrObj.getSolrIgnoreMentorCommunityId();
                    mentorPost.community.name = userSolrObj.getNameOrTitle();
                    mentorPost.createPostRequestFrom = AppConstants.MENTOR_CREATE_QUESTION;
                    createCommunityPostOnClick(mentorPost);
                }
                break;

            case R.id.iv_header_circle_icon:
                mFeedDetail = (FeedDetail) baseResponse;
                championDetailActivity(mFeedDetail.getEntityOrParticipantId(), 0, mFeedDetail.isAuthorMentor(), getString(R.string.feed_header));
                break;

            case R.id.user_name:
                mFeedDetail = (FeedDetail) baseResponse;
                championDetailActivity(mFeedDetail.getEntityOrParticipantId(), 0, mFeedDetail.isAuthorMentor(), getString(R.string.feed_header));
                break;

            case R.id.header_msg:
                mFeedDetail = (FeedDetail) baseResponse;
                CommunityPost communityPost = new CommunityPost();
                communityPost.createPostRequestFrom = AppConstants.CREATE_POST;
                createCommunityPostOnClick(communityPost);
                break;
            case R.id.li_event_card_main_layout:
                eventDetailDialog(0);
                break;
            case R.id.li_mentor:
                openMentorProfileDetail(baseResponse);
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
            case R.id.tv_event_detail_interested_btn:
                if (null != eventDetailDialogFragment) {
                    eventDetailDialogFragment.eventInterestedListData(mFeedDetail);
                }
                break;
            case R.id.tv_event_detail_going_btn:
                if (null != eventDetailDialogFragment) {
                    eventDetailDialogFragment.eventGoingListData(mFeedDetail);
                }
                break;
            default:
                if (mFeedDetail instanceof UserPostSolrObj) {
                    mFragmentOpen.setOwner(((UserPostSolrObj) mFeedDetail).isCommunityOwner());
                }
                setAllValues(mFragmentOpen);
                super.feedCardsHandled(view, baseResponse);

        }

    }

    private void navigateToCollectionScreen(CarouselDataObj carouselDataObj) {
        if (carouselDataObj != null) {
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .name(getString(R.string.carousel_outer_seemore))
                            .communityCategory(carouselDataObj.getScreenTitle())
                            .build();
            CollectionActivity.navigateTo(this, carouselDataObj.getEndPointUrl(), carouselDataObj.getScreenTitle(), getString(R.string.carousel_outer_seemore), getString(R.string.ID_COMMUNITIES_CATEGORY), properties, REQUEST_CODE_FOR_COMMUNITY_LISTING);
        }
    }

    private void openMentorProfileDetail(BaseResponse baseResponse) {
        UserSolrObj userSolrObj = (UserSolrObj) baseResponse;
        userSolrObj.setSuggested(false);
        mFeedDetail = userSolrObj;
        ProfileActivity.navigateTo(this, userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), true, -1, AppConstants.HOME_FRAGMENT, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void openProfileActivity(ProfileProgressDialog.ProfileLevelType profileLevelType) {
        ProfileActivity.navigateTo(this, mUserId, isMentor, profileLevelType, AppConstants.NAV_PROFILE, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    private void handleHelpLineFragmentFromDeepLinkAndLoading() {
        openHelplineFragment();
    }

    private void renderFAQSView() {
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        FAQSFragment faqsFragment = new FAQSFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_article_card_view, faqsFragment, FAQSFragment.class.getName()).commitAllowingStateLoss();
        setAppBarElevation();
    }

    private void renderICCMemberListView() {
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        ICCMemberListFragment iccMemberListFragment = new ICCMemberListFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_article_card_view, iccMemberListFragment, ICCMemberListFragment.class.getName()).commitAllowingStateLoss();
        setAppBarElevation();
    }

    private void initHomeViewPagerAndTabs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAppBarLayout.setElevation(0f);
        } else {
            ViewCompat.setElevation(mAppBarLayout, 0f);
        }
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.home_label));
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SCREEN_NAME, "Home Screen");
        homeFragment.setArguments(bundle);
        mFragmentOpen.setFeedFragment(true);
        /*FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream");
        bundle.putBoolean(FeedFragment.IS_HOME_FEED, true);
        bundle.putString(AppConstants.SCREEN_NAME, "Feed Screen");
        feedFragment.setArguments(bundle);
        mFragmentOpen.setFeedFragment(true);*/

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, homeFragment, HomeFragment.class.getName()).commitAllowingStateLoss();

    }

    private void initCommunityViewPagerAndTabs() {

        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        CommunitiesListFragment communitiesListFragment = new CommunitiesListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, communitiesListFragment, CommunitiesListFragment.class.getName()).commitAllowingStateLoss();
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
        setAllValues(mFragmentOpen);
        ArticlesFragment articlesFragment = new ArticlesFragment();
        FragmentManager fm = getSupportFragmentManager();
        if (fromDrawer) {
            fm.popBackStackImmediate(ArticlesFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Bundle bundleArticle = new Bundle();
        bundleArticle.putSerializable(AppConstants.ARTICLE_FRAGMENT, (ArrayList) categoryIds);
        articlesFragment.setArguments(bundleArticle);
        fm.beginTransaction().replace(R.id.fl_article_card_view, articlesFragment, ArticlesFragment.class.getName()).addToBackStack(ArticlesFragment.class.getName()).commitAllowingStateLoss();
    }

    private void openHelplineFragment() {
        mTitleText.setText("");
        mICSheroes.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        HelplineFragment helplineFragment = HelplineFragment.createInstance(AppConstants.helpline_desk);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction().replace(R.id.fl_article_card_view, helplineFragment, HelplineFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        setAppBarElevation();
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
        if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
            for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                if (homeSpinnerItem.isChecked()) {
                    categoryIds.add(homeSpinnerItem.getId());
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
        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void championLinkHandle(UserPostSolrObj userPostSolrObj) {
        ProfileActivity.navigateTo(this, userPostSolrObj.getAuthorParticipantId(), isMentor, PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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

    private void gcmIdResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof GcmIdResponse) {
                    LoginResponse loginResponse = mUserPreference.get();
                    loginResponse.setGcmId(mGcmId);
                    mUserPreference.set(loginResponse);
                    InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                    installUpdateForMoEngage.setFirstOpen(false);
                    mInstallUpdatePreference.set(installUpdateForMoEngage);
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

    private void getGcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(this, getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                mGcmId = registrationId;
                PushManager.getInstance().refreshToken(getBaseContext(), mGcmId);
                if (StringUtil.isNotNullOrEmptyString(registrationId)) {
                    if (mAppInstallation != null && mAppInstallation.isSet()) {
                        AppInstallation appInstallation = mAppInstallation.get();
                        appInstallation.gcmId = registrationId;
                        mAppInstallation.set(appInstallation);
                    }
                    if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet()) {
                        if (mInstallUpdatePreference.get().isFirstOpen()) {
                            LoginRequest loginRequest = loginRequestBuilder();
                            loginRequest.setGcmorapnsid(registrationId);
                            if (mInstallUpdatePreference.get().isWelcome()) {
                                InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                                installUpdateForMoEngage.setWelcome(false);
                                mInstallUpdatePreference.set(installUpdateForMoEngage);
                            }
                            mHomePresenter.getNewGCMidFromPresenter(loginRequest);
                        } else {
                            if (null != mUserPreference && mUserPreference.isSet() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getGcmId())) {
                                String mOldGcmId = mUserPreference.get().getGcmId();
                                if (StringUtil.isNotNullOrEmptyString(mOldGcmId)) {
                                    if (!mOldGcmId.equalsIgnoreCase(registrationId)) {
                                        LoginRequest loginRequest = loginRequestBuilder();
                                        loginRequest.setGcmorapnsid(registrationId);
                                        mHomePresenter.getNewGCMidFromPresenter(loginRequest);
                                    }
                                }
                            }
                            if (mInstallUpdatePreference.get().isWelcome()) {
                                InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                                installUpdateForMoEngage.setWelcome(false);
                                mInstallUpdatePreference.set(installUpdateForMoEngage);
                            }
                        }
                    }
                } else {
                    getGcmId();
                }
            }

            @Override
            public void onFailure(String ex) {

            }
        });
    }

    private void setAppBarElevation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAppBarLayout.setElevation(APP_BAR_ELEVATION);
        } else {
            ViewCompat.setElevation(mAppBarLayout, APP_BAR_ELEVATION);
        }
    }
    //endregion

}