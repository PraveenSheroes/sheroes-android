package appliedlife.pvtltd.SHEROES.views.activities;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;

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
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.animation.SnowFlakeView;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
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
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ShowcaseManager;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FAQSFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ICCMemberListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MainActivityNavDrawerView;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EventDetailDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_LISTING;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_INVITE_FRIEND;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.notificationReadCountRequestBuilder;

public class HomeActivity extends BaseActivity implements MainActivityNavDrawerView, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, ArticleCategorySpinnerFragment.HomeSpinnerFragmentListner, HomeView {
    private static final String SCREEN_LABEL = "Home Screen";
    private final String TAG = LogUtils.makeLogTag(HomeActivity.class);
    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;
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

    @Inject
    Preference<Configuration> mConfiguration;

    private static final int ANIMATION_DELAY_TIME = 2000;
    private static final int ANIMATION_DURATION_TIME = 5000;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    HomePresenter mHomePresenter;

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
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.rv_drawer)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_search_box)
    TextView mTvSearchBox;
    @Bind(R.id.tv_setting)
    TextView mTvSetting;
    @Bind(R.id.tv_job_home)
    TextView mTvJob;
    @Bind(R.id.tv_home)
    public TextView mTvHome;
    @Bind(R.id.tv_communities)
    public TextView mTvCommunities;
    @Bind(R.id.iv_side_drawer_profile_blur_background)
    ImageView mIvSideDrawerProfileBlurBackground;
    @Bind(R.id.iv_home_notification_icon)
    TextView mIvHomeNotification;
    @Bind(R.id.fl_notification)
    FrameLayout mFlNotification;
    @Bind(R.id.title_text)
    TextView mTitleText;
    @Bind(R.id.view_tool_tip_notification)
    View viewtoolTipNotification;
    @Bind(R.id.ic_sheroes)
    ImageView mICSheroes;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar pbNavDrawer;
    @Bind(R.id.snow_flake_view)
    SnowFlakeView mSnowFlakView;
    @Bind(R.id.santa_view)
    ImageView mSantaView;
    @Bind(R.id.tv_drawer_navigation)
    public TextView tvDrawerNavigation;
    GenericRecyclerViewAdapter mAdapter;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    boolean doubleBackToExitPressedOnce = false;
    @Inject
    MainActivityPresenter activityDataPresenter;
    @Inject
    AppUtils mAppUtils;

    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    private ArticleCategorySpinnerFragment mArticleCategorySpinnerFragment;
    private FragmentOpen mFragmentOpen;
    private CustiomActionBarToggle mCustiomActionBarToggle;
    private FeedDetail mFeedDetail;
    private String profile;
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
    public PopupWindow popupWindowNavTooTip;
    public PopupWindow popUpNotificationWindow;
    private String mGcmId;
    private ShowcaseManager showcaseManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        activityDataPresenter.attachView(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        moEngageUtills.entityMoEngageViewFeed(this, mMoEHelper, payloadBuilder, 0);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && false != mUserPreference.get().isSheUser()) {
            isSheUser = true;
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserId()) {
            mUserId = mUserPreference.get().getUserSummary().getUserId();

            if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.MENTOR_TYPE_ID) {
                isMentor = true;
            }
        }

        mHomePresenter.attachView(this);
        mHomePresenter.queryConfig();
        fetchAllCommunity();

        if (null == mUserPreference) {
            logOut();
        } else if (null != mUserPreference.get()) {

            if (!StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
                logOut();
            } else {
                long daysDifference = System.currentTimeMillis() - mUserPreference.get().getTokenTime();
                if (daysDifference >= AppConstants.SAVED_DAYS_TIME) {
                    mHomePresenter.getAuthTokenRefreshPresenter();
                } else {
                    renderHomeFragmentView();
                    assignNavigationRecyclerListView();
                    sheUserInit();
                }
            }
            if (null != mUserPreference.get().getUserSummary()) {
                mUserId = mUserPreference.get().getUserSummary().getUserId();
                AppsFlyerLib.getInstance().setCustomerUserId(String.valueOf(mUserId));
                AppsFlyerLib.getInstance().startTracking(SheroesApplication.mContext, getString(R.string.ID_APPS_FLYER_DEV_ID));
                ((SheroesApplication) this.getApplication()).trackUserId(String.valueOf(mUserId));
            }
            }else {
            mHomePresenter.getAuthTokenRefreshPresenter();
        }
        mHomePresenter.getNotificationCountFromPresenter(notificationReadCountRequestBuilder(TAG));
        try {
            getGcmId();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
        toolTipForNotification();
        if (CommonUtil.forGivenCountOnly(AppConstants.NAV_SESSION_PREF, AppConstants.DRAWER_SESSION) == AppConstants.DRAWER_SESSION) {
            if (CommonUtil.ensureFirstTime(AppConstants.NAV_PREF)) {
                toolTipForNav();
            }
        }
    }

    private void toolTipForNotification() {
        if (CommonUtil.forGivenCountOnly(AppConstants.NOTIFICATION_SESSION_SHARE_PREF, AppConstants.NOTIFICATION_SESSION) == AppConstants.NOTIFICATION_SESSION) {
            if (CommonUtil.ensureFirstTime(AppConstants.NOTIFICATION_SHARE_PREF)) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final View popupViewNotificationToolTip;
                            int width = AppUtils.getWindowWidth(HomeActivity.this);
                            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            popupViewNotificationToolTip = layoutInflater.inflate(R.layout.tooltip_arrow_up_side, null);
                            popUpNotificationWindow = new PopupWindow(popupViewNotificationToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            popUpNotificationWindow.setOutsideTouchable(true);
                            if (width < 750) {
                                popUpNotificationWindow.showAsDropDown(viewtoolTipNotification, -100, 30);
                            } else {
                                popUpNotificationWindow.showAsDropDown(viewtoolTipNotification, -150, 30);
                            }

                            final ImageView ivArrow = popupViewNotificationToolTip.findViewById(R.id.iv_arrow);
                            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(10, HomeActivity.this), 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
                            imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                            ivArrow.setLayoutParams(imageParams);
                            final TextView tvGotIt = popupViewNotificationToolTip.findViewById(R.id.got_it);
                            final TextView tvTitle = popupViewNotificationToolTip.findViewById(R.id.title);
                            tvTitle.setText(getString(R.string.tool_tip_notification));
                            tvGotIt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popUpNotificationWindow.dismiss();
                                }
                            });
                        } catch (WindowManager.BadTokenException e) {
                            Crashlytics.getInstance().core.logException(e);
                        }
                    }
                }, 2000);

            }
        }

    }

    private void toolTipForNav() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    final View navToolTip;
                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    navToolTip = layoutInflater.inflate(R.layout.tooltip_arrow_up_side, null);
                    popupWindowNavTooTip = new PopupWindow(navToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindowNavTooTip.setOutsideTouchable(true);
                    popupWindowNavTooTip.showAsDropDown(tvDrawerNavigation, 0, -10);
                    final ImageView ivArrow = navToolTip.findViewById(R.id.iv_arrow);
                    RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    imageParams.setMargins(CommonUtil.convertDpToPixel(10, HomeActivity.this), 0, 0, 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
                    ivArrow.setLayoutParams(imageParams);
                    final TextView tvGotIt = navToolTip.findViewById(R.id.got_it);
                    final TextView tvTitle = navToolTip.findViewById(R.id.title);
                    tvTitle.setText(getString(R.string.tool_tip_nav));
                    tvGotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindowNavTooTip.dismiss();
                        }
                    });
                } catch (WindowManager.BadTokenException e) {
                    Crashlytics.getInstance().core.logException(e);
                }
            }
        }, 1000);


    }

    @Override
    public void onDestroy() {
        if (popupWindowNavTooTip != null && popupWindowNavTooTip.isShowing()) {
            popupWindowNavTooTip.dismiss();
        }

        if (popUpNotificationWindow != null && popUpNotificationWindow.isShowing()) {
            popUpNotificationWindow.dismiss();
        }
        super.onDestroy();
        activityDataPresenter.detachView();
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
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isInviteReferral) {
            if (this!=null && !this.isFinishing() && null != mProgressDialog) {
                mProgressDialog.dismiss();
            }
            isInviteReferral = false;
        } else {
            setProfileImage();
        }
        resetHamburgerSelectedItems();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(ArticlesFragment.SCREEN_LABEL)) {
                    openArticleFragment(setCategoryIds(), false);
                }
            }
            if (CommonUtil.isNotEmpty(intent.getStringExtra(AppConstants.HELPLINE_CHAT)) && intent.getStringExtra(AppConstants.HELPLINE_CHAT).equalsIgnoreCase(AppConstants.HELPLINE_CHAT)) {
                handleHelpLineFragmentFromDeepLinkAndLoading();
            }

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.COMMUNITY_URL)) {

                    communityOnClick();
                }
            }

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.CHAMPION_URL)) {

                    mentorListActivity();
                }
            }

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.FAQ_URL)) {

                    renderFAQSView();
                }
            }

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.ICC_MEMBERS_URL)) {

                    renderICCMemberListView();
                }
            }
            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if (intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(AppConstants.INVITE_FRIEND_URL)) {
                    int notification = intent.getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
                    AllContactActivity.navigateTo(this, notification, null, null, REQUEST_CODE_FOR_INVITE_FRIEND);
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
    }

    public void renderHomeFragmentView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet() && !mInstallUpdatePreference.get().isAppInstallFirstTime()) {
            mIsFirstTimeOpen = true;
            Branch branch = Branch.getInstance();
            branch.resetUserSession();
            branch.initSession(new Branch.BranchReferralInitListener() {
                                   @Override
                                   public void onInitFinished(JSONObject referringParams, BranchError error) {
                                       deepLinkingRedirection();
                                   }
                               }
                    , this.getIntent().getData(), this);
        }

        if (shouldShowSnowFlake()) {
            mSantaView.setVisibility(View.GONE);
            animateSnowFlake();
        } else {
            mSnowFlakView.setVisibility(View.GONE);
        }
        pbNavDrawer.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.VISIBLE);
        mTitleText.setVisibility(View.GONE);
        mInvite.setVisibility(View.VISIBLE);
        mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustiomActionBarToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
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
        if(mIsFirstTimeOpen){
            this.mIsFirstTimeOpen = false;
            showcaseManager = new ShowcaseManager(this,mFloatActionBtn,mTvHome,mTvCommunities,tvDrawerNavigation,mRecyclerView);
            showcaseManager.showFirstMainActivityShowcase();
            InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
            installUpdateForMoEngage.setAppInstallFirstTime(true);
            mInstallUpdatePreference.set(installUpdateForMoEngage);
        }
    }

    private void deepLinkingRedirection() {
        // params are the deep linked params associated with the link that the user clicked before showing up
        // params will be empty if no data found
        Intent intent = new Intent();
        Branch branch = Branch.getInstance(getApplicationContext());
        JSONObject sessionParams = branch.getFirstReferringParams();
        try {
            // JSONObject firstSession = branch.getLatestReferringParams();
            //   if (firstSession.length() > 0 && (Boolean)branch.getLatestReferringParams().get("+is_first_session")|| (Boolean)branch.getLatestReferringParams().get("+clicked_branch_link")) {
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
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SNOW))) {
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
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
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
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            profile = mUserPreference.get().getUserSummary().getPhotoUrl();
            if (null != profile) {
                ivDrawerProfileCircleIcon.setCircularImage(true);
                ivDrawerProfileCircleIcon.setPlaceHolderId(R.drawable.default_img);
                ivDrawerProfileCircleIcon.setErrorPlaceHolderId(R.drawable.default_img);
                ivDrawerProfileCircleIcon.bindImage(profile);
            }
            String profileUserName = getResources().getString(R.string.PLACEHOLDER_PROFILE_USER_NAME, mUserPreference.get().getUserSummary().getFirstName(), mUserPreference.get().getUserSummary().getLastName());
            mTvUserName.setText(profileUserName);
            if (mUserPreference.get().getUserSummary().getEmailId() != null) {
                mTvUserLocation.setText(mUserPreference.get().getUserSummary().getEmailId());
            }
        }
    }

    private void setArticleCategoryFilterValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
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
                //  mDrawer.openDrawer(Gravity.LEFT);
            }
        };
    }

    public void openWebUrlFragment(String url, String menuItemName) { //To open the web-pages in app
        setAllValues(mFragmentOpen);
        NavigateToWebViewFragment navigateToWebViewFragment = NavigateToWebViewFragment.newInstance(url, null, menuItemName, true);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(NavigateToWebViewFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction().replace(R.id.fl_article_card_view, navigateToWebViewFragment, NavigateToWebViewFragment.class.getName()).addToBackStack(NavigateToWebViewFragment.class.getName()).commitAllowingStateLoss();

        DrawerViewHolder.selectedOptionName = menuItemName;
        resetHamburgerSelectedItems();
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

    @OnClick(R.id.invite)
    public void onInviteClicked() {
        AllContactActivity.navigateTo(this, getScreenName(), null);
    }


    public void logOut() {
        AnalyticsManager.initializeMixpanel(HomeActivity.this);
        HashMap<String, Object> properties = new EventProperty.Builder().build();
        AnalyticsManager.trackEvent(Event.USER_LOG_OUT, getScreenName(), properties);
        mUserPreference.delete();
        MoEHelper.getInstance(getApplicationContext()).logoutUser();
        MixpanelHelper.clearMixpanel(SheroesApplication.mContext);
        ((NotificationManager) SheroesApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOG_OUT, GoogleAnalyticsEventActions.LOG_OUT_OF_APP, AppConstants.EMPTY_STRING);
        finish();
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
            if (StringUtil.isNotNullOrEmptyString(bellNotificationResponse.getScreenName())) {
                if (StringUtil.isNotNullOrEmptyString(bellNotificationResponse.getSolrIgnoreDeepLinkUrl())) {
                    String urlStr = bellNotificationResponse.getSolrIgnoreDeepLinkUrl();
                    challengeIdHandle(urlStr);
                } else if (bellNotificationResponse.getScreenName().contains(AppConstants.COMMUNITY_URL)) {
                    mTitleText.setText("");
                    communityOnClick();
                } else {
                    mTitleText.setText("");
                    homeOnClick();
                }
            }
            if (null != bellNotificationDialogFragment) {
                bellNotificationDialogFragment.dismiss();
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
        mTvSearchBox.setVisibility(View.GONE);
        mICSheroes.setVisibility(View.VISIBLE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
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

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
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
            case R.id.tv_mentor_follow:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).followUnFollowRequest((UserSolrObj) baseResponse);
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
            case R.id.tv_approve_spam_post:
                Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragmentHome)) {
                    ((HomeFragment) fragmentHome).approveSpamPost(mFeedDetail, true, false, true);
                }
                break;
            case R.id.tv_delete_spam_post:
                Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(homeFragment)) {
                    ((HomeFragment) homeFragment).approveSpamPost(mFeedDetail, true, true, false);
                }
                break;
            default:
                if (mFeedDetail instanceof UserPostSolrObj) {
                    mFragmentOpen.setOwner(((UserPostSolrObj) mFeedDetail).isCommunityOwner());
                }
                setAllValues(mFragmentOpen);
                // setViewPagerAndViewAdapter(mViewPagerAdapter, mViewPager);
                //  setViewPagerAndViewAdapter();
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
        ProfileActivity.navigateTo(this, userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), true, AppConstants.HOME_FRAGMENT, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void openProfileActivity() {
        ProfileActivity.navigateTo(this, mUserId, isMentor, AppConstants.NAV_PROFILE, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    public void refreshHomeFragment(FeedDetail feedDetail) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((HomeFragment) fragment).commentListRefresh(feedDetail, COMMENT_REACTION);
        }
    }

    private void handleHelpLineFragmentFromDeepLinkAndLoading() {
        openHelplineFragment();
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
       /* if (!mFragmentOpen.isImageBlur()) {
            assignNavigationRecyclerListView();
            mFragmentOpen.setImageBlur(true);
        }*/
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

    private void renderFAQSView() {
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        mTitleText.setText(getString(R.string.ID_APP_NAME));
        mTitleText.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        FAQSFragment faqsFragment = new FAQSFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_article_card_view, faqsFragment, FAQSFragment.class.getName()).commitAllowingStateLoss();
    }


    private void renderICCMemberListView() {
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        mTitleText.setText(getString(R.string.ID_APP_NAME));
        mTitleText.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        ICCMemberListFragment iccMemberListFragment = new ICCMemberListFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_article_card_view, iccMemberListFragment, ICCMemberListFragment.class.getName()).commitAllowingStateLoss();
    }

    public void changeFragmentWithCommunities() {
        mFragmentOpen.setCommunityOpen(false);
        mFragmentOpen.setFeedFragment(false);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvJob.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_job_unselected), null, null);

        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvJob.setText(getString(R.string.ID_CARRIER));

        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvJob.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));

        mFloatActionBtn.setVisibility(View.GONE);

        flFeedFullView.setVisibility(View.VISIBLE);
    }

    private void initHomeViewPagerAndTabs() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        /*Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(mFeedDetail);
        bundle.putParcelable(AppConstants.HOME_FRAGMENT, parcelable);
        bundle.putLong(AppConstants.CHALLENGE_ID, mChallengeId);
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, homeFragment, HomeFragment.class.getName()).commitAllowingStateLoss();*/


        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream");
        bundle.putBoolean(FeedFragment.IS_HOME_FEED, true);
        bundle.putString(AppConstants.SCREEN_NAME, "Feed Screen");
        feedFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, feedFragment, FeedFragment.class.getName()).commitAllowingStateLoss();

    }

    private void initCommunityViewPagerAndTabs() {

        mTitleText.setText(getString(R.string.ID_COMMUNITIES));
        mTitleText.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        mInvite.setVisibility(View.GONE);

        CommunitiesListFragment communitiesListFragment = new CommunitiesListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, communitiesListFragment, CommunitiesListFragment.class.getName()).commitAllowingStateLoss();
    }


    @OnClick(R.id.li_article_spinner_icon)
    public void openSpinnerOnClick() {
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

    @OnClick(R.id.tv_home)
    public void homeOnClick() {
        DrawerViewHolder.selectedOptionName = null;
        resetHamburgerSelectedItems();
        flFeedFullView.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        homeButtonUi();
        initHomeViewPagerAndTabs();
    }

    private void resetHamburgerSelectedItems() { //Reset navigation drawer selected item
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void homeButtonUi() {
        DrawerViewHolder.selectedOptionName = null;
        resetHamburgerSelectedItems();

        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mFragmentOpen.setFeedFragment(true);

        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));

        mTvJob.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_job_unselected), null, null);
        mTvJob.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvJob.setText(getString(R.string.ID_CARRIER));

        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mFloatActionBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.email)));
        mFloatActionBtn.setImageResource(R.drawable.ic_pencil);
        mFloatActionBtn.setTag(AppConstants.FEED_SUB_TYPE);
        mTvSearchBox.setVisibility(View.GONE);
        mICSheroes.setVisibility(View.VISIBLE);

        mTitleText.setText("");
        mTitleText.setVisibility(View.VISIBLE);
        mInvite.setVisibility(View.VISIBLE);

    }


    @OnClick(R.id.tv_communities)
    public void communityOnClick() {
        DrawerViewHolder.selectedOptionName = null;
        resetHamburgerSelectedItems();
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_COMMUNITIES));
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        initCommunityViewPagerAndTabs();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        communityButton();
    }

    public void communityButton() {
        mFragmentOpen.setCommunityOpen(true);

        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvJob.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_job_unselected), null, null);
        mTvJob.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvJob.setText(getString(R.string.ID_CARRIER));

        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
    }

    public void createCommunityPostOnClick(CommunityPost communityPost) {
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
        //  PostBottomSheetFragment.showDialog(this, SCREEN_LABEL);
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

    public void articleUi() {
        mliArticleSpinnerIcon.setVisibility(View.VISIBLE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        mICSheroes.setVisibility(View.VISIBLE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);

        mTitleText.setText("");
        mTitleText.setVisibility(View.VISIBLE);
        mInvite.setVisibility(View.VISIBLE);
    }

    public void inviteMyCommunityDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.ID_INVITE_WOMEN_FRIEND));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        LoginManager.getInstance().logOut();
        String appLinkUrl, previewImageUrl;
        appLinkUrl = AppConstants.FB_APP_LINK_URL;
        previewImageUrl = AppConstants.FB_APP_LINK_URL_PREVIEW_IMAGE;
        if (AppInviteDialog.canShow()) {
            AppEventsLogger logger = AppEventsLogger.newLogger(this);
            logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT);
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_INVITES, GoogleAnalyticsEventActions.OPEN_INVITE_FB_FRDZ, AppConstants.EMPTY_STRING);

    }

    private void openHelplineFragment() {
        if (isSheUser && startedFirstTime()) {
            mTitleText.setText(getString(R.string.ID_APP_NAME));
            mTitleText.setVisibility(View.VISIBLE);
            mICSheroes.setVisibility(View.GONE);
        } else {
            mTitleText.setText("");
            mTitleText.setVisibility(View.GONE);
            mICSheroes.setVisibility(View.VISIBLE);
        }
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        HelplineFragment helplineFragment = new HelplineFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction().replace(R.id.fl_article_card_view, helplineFragment, HelplineFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    public void helplineUi() {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        if (isSheUser) {
            mICSheroes.setVisibility(View.GONE);
        } else {
            mICSheroes.setVisibility(View.VISIBLE);
        }
        mTitleText.setText("");
        mTitleText.setVisibility(View.VISIBLE);
        mInvite.setVisibility(View.VISIBLE);

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
            if (mFragmentOpen.isFeedFragment() || mFragmentOpen.isCommunityOpen()) {
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
                super.onBackPressed();
            }
        }
        resetHamburgerSelectedItems();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return activityDataPresenter;
    }

    @OnClick(R.id.fl_notification)
    public void notificationClick() {
        // mDrawer.openDrawer(Gravity.LEFT);
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

    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        AppUtils.hideKeyboard(mTvUserName, TAG);
        mDrawer.openDrawer(Gravity.LEFT);
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_DRAWER_NAVIGATION));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        resetHamburgerSelectedItems();
        if (requestCode == AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_article_card_view);
            if (fragment instanceof HomeFragment) {
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).onRefreshClick();
                }
            } else if (fragment instanceof CommunitiesListFragment) {
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
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FeedFragment.class.getName());
                        ((FeedFragment) fragment).refreshList();
                        break;

                    case AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                        if (null != intent.getExtras()) {
                            UserSolrObj userSolrObj = Parcels.unwrap(intent.getParcelableExtra(AppConstants.FEED_SCREEN));
                            if (null != userSolrObj) {
                                Fragment fragmentMentor = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                                if (AppUtils.isFragmentUIActive(fragmentMentor)) {
                                    invalidateItem(userSolrObj);
                                }
                            }
                        }
                        break;

                    case AppConstants.REQUEST_CODE_FOR_JOB_DETAIL:
                        if (null != intent && null != intent.getExtras()) {
                            JobFeedSolrObj jobFeedSolrObj = null;
                            jobFeedSolrObj = Parcels.unwrap(intent.getParcelableExtra(AppConstants.JOB_FRAGMENT));
                            invalidateItem(jobFeedSolrObj);
                        }
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






                /*case AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL:
                    articleDetailActivityResponse(intent);
                    break;*/
                    case AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL:
                        //communityDetailActivityResponse(intent);
                        break;

                /*case AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL:
                    if (resultCode == Activity.RESULT_OK) {
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                        if (AppUtils.isFragmentUIActive(fragment)) {
                            ((HomeFragment) fragment).onRefreshClick();
                        }
                    }
                    break;*/
               /* case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                    boolean isPostDeleted = false;
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        Parcelable parcelable = intent.getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
                        if (parcelable != null) {
                            UserPostSolrObj userPostSolrObj = Parcels.unwrap(parcelable);
                            isPostDeleted = intent.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                            mFeedDetail = userPostSolrObj;
                        }
                        if (isPostDeleted) {
                            ((HomeFragment) fragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.DELETE_COMMUNITY_POST);
                        } else {
                            ((HomeFragment) fragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.COMMENT_REACTION);
                        }
                    }
                    break;*/

                    case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY:
                       // createCommunityActivityResponse(intent);
                        break;
                /*case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    editCommunityPostResponse(intent);
                    break;*/
                    case AppConstants.REQ_CODE_SPEECH_INPUT:
                        //  helplineSpeechActivityResponse(intent, resultCode);
                        break;
              /*  case REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                    if (null != intent.getExtras()) {
                        UserSolrObj userSolrObj = Parcels.unwrap(intent.getParcelableExtra(AppConstants.FEED_SCREEN));
                        if (null != userSolrObj) {
                            Fragment fragmentMentor = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                            if (AppUtils.isFragmentUIActive(fragmentMentor)) {
                                userSolrObj.currentItemPosition = mSuggestionItemPosition;
                                userSolrObj.setItemPosition(mMentorCardPosition);
                                userSolrObj.setSuggested(true);
                                ((HomeFragment) fragmentMentor).getSuccessForAllResponse(userSolrObj, FOLLOW_UNFOLLOW);
                            }
                        } else {
                            homeOnClick();
                        }
                    }
                    break;*/
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
                    case AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL:
                        Fragment fragmentMentor = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                        if (AppUtils.isFragmentUIActive(fragmentMentor)) {
                            ((HomeFragment) fragmentMentor).onRefreshClick();
                        }
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
                }
            }
        }
        if (this!=null && !this.isFinishing() && null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    private void removeItem(FeedDetail feedDetail) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FeedFragment.class.getName());
        ((FeedFragment) fragment).removeItem(feedDetail);
    }

    private void invalidateItem(FeedDetail feedDetail) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FeedFragment.class.getName());
        ((FeedFragment) fragment).updateItem(feedDetail);
    }

    private void refreshCurrentFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FeedFragment.class.getName());
        ((FeedFragment) fragment).refreshList();
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

    private void editCommunityPostResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            mFeedDetail = Parcels.unwrap(intent.getParcelableExtra(AppConstants.COMMUNITY_POST_FRAGMENT));
            if (null != mFeedDetail) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (mFeedDetail.isFromHome()) {
                        homeOnClick();
                    } else {
                        ((HomeFragment) fragment).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                    }
                }
            } else {
                homeOnClick();
            }
        }
    }

    private void articleDetailActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            mFeedDetail = Parcels.unwrap(intent.getParcelableExtra(AppConstants.HOME_FRAGMENT));
            Fragment fragmentArticle = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragmentArticle)) {
                ((ArticlesFragment) fragmentArticle).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }

        }
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
                getSupportFragmentManager().popBackStack();
            }
        }
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

    @OnClick(R.id.profile_link)
    public void onClickProfile() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        openProfileActivity();
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
        } else if (baseResponse instanceof UserPostSolrObj && mValue == AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_USER_DETAIL) {
            UserPostSolrObj postDetails = (UserPostSolrObj) baseResponse;
            if (StringUtil.isNotEmptyCollection(postDetails.getLastComments())) {
                Comment comment = postDetails.getLastComments().get(0);
                if (!comment.isAnonymous()) {
                    championDetailActivity(comment.getParticipantUserId(), comment.getItemPosition(), comment.isVerifiedMentor(), AppConstants.COMMENT_REACTION_FRAGMENT);
                }
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

    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void championLinkHandle(UserPostSolrObj userPostSolrObj) {
        ProfileActivity.navigateTo(this, userPostSolrObj.getAuthorParticipantId(), isMentor, AppConstants.FEED_SCREEN, null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

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
                        if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet() && null != mInstallUpdatePreference.get()) {
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
                                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getGcmId())) {
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

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        if (null != loginResponse && StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
            loginResponse.setTokenTime(System.currentTimeMillis());
            loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
            mUserPreference.set(loginResponse);
            renderHomeFragmentView();
            assignNavigationRecyclerListView();
            sheUserInit();
        }
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

    private void unReadNotificationCount(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof NotificationReadCountResponse) {
                    NotificationReadCountResponse notificationReadCountResponse = (NotificationReadCountResponse) baseResponse;
                    StringBuilder stringBuilder = new StringBuilder();
                    int  notificationCount=notificationReadCountResponse.getUnread_notification_count();
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

    public void fetchAllCommunity() {
        mHomePresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
    }
}