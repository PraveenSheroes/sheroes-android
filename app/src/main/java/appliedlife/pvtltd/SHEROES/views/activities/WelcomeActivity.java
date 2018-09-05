package appliedlife.pvtltd.SHEROES.views.activities;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.regex.Pattern;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.CleverTapHelper;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.social.GooglePlusHelper;
import appliedlife.pvtltd.SHEROES.social.SocialListener;
import appliedlife.pvtltd.SHEROES.social.SocialPerson;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.SheroesWelcomeViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.GenderInputFormDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MaleErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;

/**
 * Created by sheroes on 06/03/17.
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, LoginView, SocialListener, GoogleApiClient.OnConnectionFailedListener {
    public static final String SCREEN_LABEL = "Intro Screen";
    private static final String BRANCH_DEEP_LINK = "deep_link_url";
    private static final String BRANCH_REFERRER_LINK = "~referring_link";
    public static final int LOGGING_IN_DIALOG = 1;
    public static final String GENDER = "female";
    public static final int TOKEN_LOGGING_PROGRESS_DIALOG = 2;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    Preference<AppInstallation> mAppInstallation;


    @Bind(R.id.welcome_view_pager)
    ViewPager mViewPager;
    private SheroesWelcomeViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.iv_welcome_first)
    ImageView ivWelcomeFirst;
    @Bind(R.id.iv_welcome_second)
    ImageView ivWelcomeSecond;
    @Bind(R.id.iv_welcome_third)
    ImageView ivWelcomeThird;
    @Bind(R.id.click_to_join_fb_signup)
    Button fbLogin;
    @Bind(R.id.cl_welcome)
    RelativeLayout clWelcome;
    private PayloadBuilder payloadBuilder;
    private int currentPage = 0;
    private Timer timer;
    private int NUM_PAGES = 4;
    private static final String LEFT = "<b><font color='#ffffff'>";
    private static final String RIGHT = "</font></b>";
    //private String mGcmId;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private FragmentOpen mFragmentOpen;
    @Inject
    AppUtils appUtils;
    public static int isSignUpOpen = AppConstants.NO_REACTION_CONSTANT;
    private boolean isFirstTimeUser = false;
    private Handler mHandler;
    private Runnable mRunnable;
    private CallbackManager callbackManager;
    private int gcmForGoogleAndFacebook;
    public static final int GOOGLE_CALL = 101;
    public static final int FACEBOOK_CALL = 201;
    private GoogleSignInOptions gso;
    private ProgressDialog mProgressDialog, mLoggingProgressDialog;
    //google api client
    public static GoogleApiClient mGoogleApiClient;
    private String mToken = null;
    private String loginViaSocial = MoEngageConstants.GOOGLE;
    private long currentTime;
    private String mFcmId;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isHandleAuthTokenRefresh = false;

    //Ads Navigation
    private boolean isBranchFirstSession = false;
    private String deepLinkUrl = null;
    private String defaultTab = null;
    private Account[] account;
    private Pattern pattern;
    private String googleAccounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mLoginPresenter.attachView(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        AppsFlyerLib.getInstance().setAndroidIdData(appUtils.getDeviceId());
        if (CommonUtil.getPrefValue(AppConstants.MALE_ERROR_SHARE_PREF)) {
            showMaleError(getString(R.string.sheroes_gender_error), "");
        } else {
            checkAuthTokenExpireOrNot();
        }
        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(SheroesApplication.mContext);
        appInstallationHelper.setupAndSaveInstallation(false);
    }

    private void checkAuthTokenExpireOrNot() {
        if (null != mUserPreference && mUserPreference.isSet()) {
            if (StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
                long daysDifference = System.currentTimeMillis() - mUserPreference.get().getTokenTime();
                if (daysDifference >= AppConstants.SAVED_DAYS_TIME) {
                    isHandleAuthTokenRefresh = true;
                    mLoginPresenter.getAuthTokenRefreshPresenter();
                } else {
                    initializeAllDataAfterGCMId();
                }
            } else {
                initializeAllDataAfterGCMId();
            }
        } else {
            initializeAllDataAfterGCMId();
        }
    }

    private void initializeAllDataAfterGCMId() {
        int versionCode = BuildConfig.VERSION_CODE;
        moEngageUtills.entityMoEngageAppVersion(this, mMoEHelper, payloadBuilder, versionCode);
        if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet()) {
            if (mInstallUpdatePreference.get().getAppVersion() < versionCode) {
                InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                installUpdateForMoEngage.setFirstOpen(true);
                installUpdateForMoEngage.setAppVersion(versionCode);
                installUpdateForMoEngage.setWelcome(true);
                installUpdateForMoEngage.setAppInstallFirstTime(true);
                mInstallUpdatePreference.set(installUpdateForMoEngage);
            }
            mMoEHelper.setExistingUser(true);
            if (mInstallUpdatePreference.get().isAppInstallFirstTime()) {
                InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                installUpdateForMoEngage.setWalkThroughShown(true);
                mInstallUpdatePreference.set(installUpdateForMoEngage);
            }
        } else {
            InstallUpdateForMoEngage installUpdateForMoEngage = new InstallUpdateForMoEngage();
            installUpdateForMoEngage.setAppVersion(versionCode);
            installUpdateForMoEngage.setFirstOpen(true);
            installUpdateForMoEngage.setAppInstallFirstTime(false);
            installUpdateForMoEngage.setWalkThroughShown(false);
            mInstallUpdatePreference.set(installUpdateForMoEngage);
            mMoEHelper.setExistingUser(false);
            mMoEHelper.setUserAttribute(MoEngageConstants.FIRST_APP_OPEN, new Date());
        }
        moEngageUtills.entityMoEngageLastOpen(this, mMoEHelper, payloadBuilder, new Date());

        if (null != mUserPreference && mUserPreference.isSet() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
            isFirstTimeUser = false;
            DrawerViewHolder.selectedOptionName = null;
            openHomeScreen();
        } else { //Get Branch Details for First Install
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.getBoolean(AppConstants.HIDE_SPLASH_THEME)) {
                setUpView();
            } else {
                final Branch branch = Branch.getInstance();
                branch.resetUserSession();
                if(CleverTapHelper.getCleverTapInstance(getApplicationContext())!=null) {
                    branch.setRequestMetadata(CleverTapHelper.CLEVERTAP_ATTRIBUTION_ID,
                            CleverTapHelper.getCleverTapInstance(getApplicationContext()).getCleverTapAttributionIdentifier());
                }
                branch.initSession(new Branch.BranchReferralInitListener() {
                    @Override
                    public void onInitFinished(JSONObject sessionParams, BranchError error) {
                        isBranchFirstSession = CommonUtil.deepLinkingRedirection(sessionParams);
                        if (isBranchFirstSession) {
                            if (sessionParams.has(BRANCH_DEEP_LINK)) {
                                String deepLink;
                                String branchLink;
                                try {
                                    deepLink = sessionParams.getString(BRANCH_DEEP_LINK);
                                    branchLink = sessionParams.getString(BRANCH_REFERRER_LINK);
                                    if (StringUtil.isNotNullOrEmptyString(deepLink)) {
                                        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
                                        SharedPreferences.Editor editor = null;
                                        if (prefs != null) {
                                            editor = prefs.edit();
                                            editor.putString(AppConstants.REFERRER_BRANCH_LINK_URL, branchLink);
                                            editor.apply();
                                        }
                                        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(WelcomeActivity.this);
                                        appInstallationHelper.setupAndSaveInstallation(false);
                                        if (deepLink.contains("sheroes") && deepLink.contains("/communities")) {  //Currently it allows only community
                                            deepLinkUrl = deepLink;

                                            if (mInstallUpdatePreference != null) {
                                                InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                                                installUpdateForMoEngage.setOnBoardingSkipped(true);
                                                mInstallUpdatePreference.set(installUpdateForMoEngage);
                                            }
                                        }
                                    }

                                    if (sessionParams.has(CommunityDetailActivity.TAB_KEY)) {
                                        defaultTab = sessionParams.getString(CommunityDetailActivity.TAB_KEY);
                                    }
                                } catch (JSONException e) {
                                    deepLinkUrl = null;
                                    defaultTab = null;
                                    isBranchFirstSession = false;
                                }
                            }
                        }
                        setUpView();
                    }
                });
            }
        }
    }

    private void setUpView() {
        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(WelcomeActivity.this);
        isFirstTimeUser = true;

        initHomeViewPagerAndTabs();

        loginSetUp();
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            showNetworkTimeoutDoalog(false, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
            return;
        }

        ((SheroesApplication) WelcomeActivity.this.getApplication()).trackScreenView(getString(R.string.ID_INTRO_SCREEN));

        if (isFirstTimeUser) {
            AnalyticsManager.trackScreenView(getScreenName());
        }
        mLoginPresenter.getMasterDataToPresenter();
        mLoginPresenter.queryConfig();
    }

    private void loginSetUp() {
        currentTime = System.currentTimeMillis();
        googlePlusLogin();
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();

                        // Facebook Email address
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        if (null != accessToken && StringUtil.isNotNullOrEmptyString(accessToken.getToken())) {
                                            LoginRequest loginRequest = loginRequestBuilder();
                                            loginRequest.setAccessToken(accessToken.getToken());
                                            AppUtils appUtils = AppUtils.getInstance();
                                            loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                                            loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
                                            loginRequest.setGcmorapnsid(mFcmId);
                                            loginRequest.setUserGender(GENDER);
                                            loginViaSocial = MoEngageConstants.FACEBOOK;
                                            mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, true);
                                        }
                                    }
                                });
                        try {
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,gender,last_name,first_name");
                            request.setParameters(parameters);
                            request.executeAsync();
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                        }

                    }

                    @Override
                    public void onCancel() {
                        mUserPreference.delete();
                        dismissProgressDialog(LOGGING_IN_DIALOG);
                        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
                        (WelcomeActivity.this).showNetworkTimeoutDoalog(true, false, AppConstants.CHECK_NETWORK_CONNECTION);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        mUserPreference.delete();
                        dismissProgressDialog(LOGGING_IN_DIALOG);
                        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
                        (WelcomeActivity.this).showNetworkTimeoutDoalog(true, false, exception.getMessage());
                    }
                });

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gcmForGoogleAndFacebook = FACEBOOK_CALL;
                showDialogInWelcome(LOGGING_IN_DIALOG);
                if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
                    showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                    return;
                } else {
                    getFcmId();
                }
            }
        });


    }

    //before
    private void openHomeScreen() {

        if (isBranchFirstSession && StringUtil.isNotNullOrEmptyString(deepLinkUrl)) { //ads for community

            //Event for on-boarding skipping for new user came through branch link
            final HashMap<String, Object> properties = new EventProperty.Builder().branchLink(deepLinkUrl).build();
            AnalyticsManager.trackEvent(Event.ONBOARDING_SKIPPED, getScreenName(), properties);

            Uri url = Uri.parse(deepLinkUrl);
            Intent intent = new Intent(WelcomeActivity.this, SheroesDeepLinkingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(CommunityDetailActivity.TAB_KEY, "");
            bundle.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
            bundle.putBoolean(AppConstants.IS_FROM_ADVERTISEMENT, isBranchFirstSession);
            if (StringUtil.isNotNullOrEmptyString(defaultTab)) {
                bundle.putString(CommunityDetailActivity.TAB_KEY, defaultTab);
            }
            intent.putExtras(bundle);
            intent.setData(url);
            startActivity(intent);
            finish();
        } else {
            if (null != mUserPreference && mUserPreference.isSet() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getNextScreen()) && mUserPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION) && mUserPreference.get().isSheUser()) {
                openLoginActivity();
            } else {
                Intent boardingIntent = new Intent(WelcomeActivity.this, OnBoardingActivity.class);
                Bundle bundle = new Bundle();
                boardingIntent.putExtras(bundle);
                boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(boardingIntent);
                finish();
            }
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void initHomeViewPagerAndTabs() {
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Join").append(LEFT).append(" 1 Million+ Women ").append(RIGHT).append("Just As Awesome As You");
        StringBuilder womenGrowth = new StringBuilder();
        womenGrowth.append("A Growth Network").append(LEFT).append(" Only for Women ").append(RIGHT).append("Because");
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            //    mTvGrowthWomen.setText(Html.fromHtml(womenGrowth.toString(), 0)); // for 24 api and more
        } else {
            //  mTvGrowthWomen.setText(Html.fromHtml(womenGrowth.toString())); // for 24 api and more
        }
        ArrayList<Integer> screenNameList = new ArrayList<>();
        screenNameList.add(R.drawable.welcome_first);
        screenNameList.add(R.drawable.welcome_second);
        screenNameList.add(R.drawable.welcome_third);
        ArrayList<String> screenText = new ArrayList<>();
        screenText.add(getString(R.string.ID_WELCOME_FIRST));
        screenText.add(getString(R.string.ID_WELCOME_SECOND));
        screenText.add(getString(R.string.ID_WELCOME_THIRD));

        mViewPagerAdapter = new SheroesWelcomeViewPagerAdapter(screenNameList, this, screenText);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(WelcomeActivity.this);
        ivWelcomeFirst.setImageResource(R.drawable.vector_circle_red);
        ivWelcomeSecond.setImageResource(R.drawable.vector_circle_w);
        ivWelcomeThird.setImageResource(R.drawable.vector_circle_w);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
                mHandler.postDelayed(mRunnable, 10000);
            }
        };

    }


    @Override
    protected void onDestroy() {
        if (null != mGoogleApiClient) {
            mGoogleApiClient.stopAutoManage(WelcomeActivity.this);
            mGoogleApiClient.disconnect();
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        mLoginPresenter.detachView();
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
        super.onDestroy();
    }

    @OnClick(R.id.tv_other_login_option)
    public void otherLoginOption() {
        openLoginActivity();
    }

    private void openLoginActivity() {
        Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConstants.IS_FROM_ADVERTISEMENT, isBranchFirstSession);
        bundle.putString(AppConstants.ADS_DEEP_LINK_URL, deepLinkUrl);
        loginIntent.putExtras(bundle);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onShowErrorDialog(String s, FeedParticipationEnum feedParticipationEnum) {
        super.onShowErrorDialog(s, feedParticipationEnum);
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case AppConstants.NO_REACTION_CONSTANT:
                ivWelcomeFirst.setImageResource(R.drawable.vector_circle_red);
                ivWelcomeSecond.setImageResource(R.drawable.vector_circle_w);
                ivWelcomeThird.setImageResource(R.drawable.vector_circle_w);
                break;
            case AppConstants.ONE_CONSTANT:
                ivWelcomeSecond.setImageResource(R.drawable.vector_circle_red);
                ivWelcomeFirst.setImageResource(R.drawable.vector_circle_w);
                ivWelcomeThird.setImageResource(R.drawable.vector_circle_w);
                break;
            case AppConstants.TWO_CONSTANT:
                ivWelcomeThird.setImageResource(R.drawable.vector_circle_red);
                ivWelcomeFirst.setImageResource(R.drawable.vector_circle_w);
                ivWelcomeSecond.setImageResource(R.drawable.vector_circle_w);
                break;
        }
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void showMaleError(String message, String userName) {
        MaleErrorDialog fragment = (MaleErrorDialog) getFragmentManager().findFragmentByTag(MaleErrorDialog.class.getName());
        if (fragment == null) {
            fragment = new MaleErrorDialog();
            Bundle b = new Bundle();
            b.putString(AppConstants.SHEROES_AUTH_TOKEN, message);
            b.putString(BaseDialogFragment.USER_NAME, userName);
            b.putInt(AppConstants.FACEBOOK_VERIFICATION, AppConstants.ONE_CONSTANT);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), MaleErrorDialog.class.getName());
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        if (null != clWelcome) {
            Snackbar.make(clWelcome, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mLoginPresenter;
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
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }


    @Override
    public void sendForgotPasswordEmail(ForgotPasswordResponse forgotPasswordResponse) {

    }

    @Override
    public void sendVerificationEmailSuccess(EmailVerificationResponse emailVerificationResponse) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
        if (mHandler != null && mRunnable != null) {
            mHandler.postDelayed(mRunnable, 10000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    private void googlePlusLogin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();
        if (null == mGoogleApiClient) {
            mGoogleApiClient = new GoogleApiClient.Builder(WelcomeActivity.this)
                    .enableAutoManage(WelcomeActivity.this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

    }

    private void signIn() {
        //Creating an intent
        signOut();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //Starting intent for result
        startActivityForResult(signInIntent, AppConstants.REQUEST_CODE_FOR_GOOGLE_PLUS);
    }

    public void signOut() {
        //Check is required otherwise illegal state exception might be thrown
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
    }

    /**
     * Show dialog
     *
     * @param id id of dialog
     */
    void showDialogInWelcome(int id) {
        try {
            createCustomDialog(id);
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    /**
     * Creates and returns dialog
     *
     * @param id id of dialog
     * @return dialog
     */
    private void createCustomDialog(int id) {
        try {
            switch (id) {
                case LOGGING_IN_DIALOG: {

                    if (isFinishing()) return;

                    mProgressDialog = new ProgressDialog(WelcomeActivity.this);
                    mProgressDialog.setMessage(getString(R.string.ID_PLAY_STORE_DATA));
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    break;
                }
                case TOKEN_LOGGING_PROGRESS_DIALOG: {

                    if (isFinishing()) return;

                    mLoggingProgressDialog = new ProgressDialog(WelcomeActivity.this);
                    mLoggingProgressDialog.setMessage(getString(R.string.ID_PLAY_STORE_DATA));
                    mLoggingProgressDialog.setCancelable(true);
                    mLoggingProgressDialog.show();
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    @OnClick(R.id.btn_login_google)
    public void googleLoginClick() {
        gcmForGoogleAndFacebook = GOOGLE_CALL;
        showDialogInWelcome(LOGGING_IN_DIALOG);
        if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
            showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        } else {
            getFcmId();
        }
    }

    public void launchGooglePlusLogin() {
        if (AppUtils.getInstance().isNetworkAvailable()) {
            signIn();
        } else {
            Toast.makeText(WelcomeActivity.this, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    private void getFcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(WelcomeActivity.this, getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                mFcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(mFcmId)) {
                    PushManager.getInstance().refreshToken(WelcomeActivity.this, mFcmId);
                    //Refresh FCM token
                    CleverTapAPI cleverTapAPI = CleverTapHelper.getCleverTapInstance(SheroesApplication.mContext);
                    if(cleverTapAPI!=null) {
                        cleverTapAPI.data.pushFcmRegistrationId(registrationId, true);
                    }
                    fbLogin.setEnabled(true);
                    checkSignUpCall(gcmForGoogleAndFacebook);
                } else {
                    fbLogin.setEnabled(false);
                    if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
                        showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                        return;
                    } else {
                        getFcmId();
                    }
                }
            }

            @Override
            public void onFailure(String ex) {
                mFcmId = ex;
            }
        });
    }


    private void checkSignUpCall(int checkCall) {
        switch (checkCall) {
            case FACEBOOK_CALL:
                if (!NetworkUtil.isConnected(getApplicationContext())) {
                    (WelcomeActivity.this).showNetworkTimeoutDoalog(true, false, AppConstants.CHECK_NETWORK_CONNECTION);
                    return;
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
                }
                break;
            case GOOGLE_CALL:
                launchGooglePlusLogin();
                break;

            default:
        }
    }


    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        if (fbLogin != null) {
            fbLogin.setEnabled(true);
        }
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
        if (loginResponse != null) {
            if (isHandleAuthTokenRefresh) {
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                    refreshAuthTokenResponse(loginResponse);
                }
            } else {
                switch (loginResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                            loginAuthTokenResponse(loginResponse);

                        } else {
                            mUserPreference.delete();
                            LoginManager.getInstance().logOut();
                            signOut();
                            showMaleError(AppConstants.EMPTY_STRING, "");
                        }
                        break;
                    case AppConstants.INVALID:
                        mUserPreference.delete();
                        break;
                    case AppConstants.FAILED:
                        mUserPreference.delete();
                        signOut();
                        LoginManager.getInstance().logOut();
                        String errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA);
                        String deactivated = loginResponse.getFieldErrorMessageMap().get(AppConstants.IS_DEACTIVATED);
                        if (StringUtil.isNotNullOrEmptyString(errorMessage)) {
                            if (StringUtil.isNotNullOrEmptyString(deactivated) && deactivated.equalsIgnoreCase("true")) {

                                showErrorDialogOnUserAction(true, false, errorMessage, "true");

                            } else {

                                showMaleError(errorMessage, "");
                            }
                        } else {
                            errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR);
                            showMaleError(errorMessage, "");
                        }
                        break;
                }
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }

    private void refreshAuthTokenResponse(LoginResponse loginResponse) {
        loginResponse.setTokenTime(System.currentTimeMillis());
        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
        mUserPreference.set(loginResponse);
        openHomeScreen();
    }

    private void loginAuthTokenResponse(LoginResponse loginResponse) {
        loginResponse.setTokenTime(System.currentTimeMillis());
        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
        loginResponse.setGcmId(mFcmId);
        mUserPreference.set(loginResponse);
        AnalyticsManager.initializeMixpanel(WelcomeActivity.this);

        moEngageUtills.entityMoEngageUserAttribute(WelcomeActivity.this, mMoEHelper, payloadBuilder, loginResponse);

        if (null != loginResponse.getUserSummary() && null != loginResponse.getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getCrdt())) {
            long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());
            AnalyticsManager.initializeCleverTap(WelcomeActivity.this, currentTime < createdDate);

            final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(currentTime < createdDate).authProvider(loginViaSocial.equalsIgnoreCase(MoEngageConstants.FACEBOOK) ? "Facebook" : "Google").build();
            AnalyticsManager.trackEvent(Event.APP_LOGIN, getScreenName(), properties);

            if (createdDate < currentTime) {
                moEngageUtills.entityMoEngageLoggedIn(WelcomeActivity.this, mMoEHelper, payloadBuilder, loginViaSocial);
                if (loginViaSocial.equalsIgnoreCase(MoEngageConstants.FACEBOOK)) {
                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_WITH_FACEBOOK, AppConstants.EMPTY_STRING);
                } else {
                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_WITH_GOOGLE, AppConstants.EMPTY_STRING);
                }

            } else {
                moEngageUtills.entityMoEngageSignUp(WelcomeActivity.this, mMoEHelper, payloadBuilder, loginViaSocial);
                if (loginViaSocial.equalsIgnoreCase(MoEngageConstants.FACEBOOK)) {
                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SIGN_UP, GoogleAnalyticsEventActions.SIGN_UP_WITH_FACEBOOK, AppConstants.EMPTY_STRING);
                } else {
                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SIGN_UP, GoogleAnalyticsEventActions.SIGN_UP_WITH_GOOGLE, AppConstants.EMPTY_STRING);
                }
            }
            ((SheroesApplication) WelcomeActivity.this.getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
        }
        mMoEHelper.setUserAttribute(MoEngageConstants.ACQUISITION_CHANNEL, loginViaSocial);
        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(this);
        appInstallationHelper.setupAndSaveInstallation(true);
        openHomeScreen();
    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {
        if (expireInResponse.getExpiresIn() > 0 && StringUtil.isNotNullOrEmptyString(mToken)) {
            LoginRequest loginRequest = loginRequestBuilder();
            loginRequest.setAccessToken(mToken);
            AppUtils appUtils = AppUtils.getInstance();
            loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
            loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
            loginRequest.setGcmorapnsid(mFcmId);
            loginRequest.setCallForSignUp(AppConstants.GOOGLE_PLUS);
            loginRequest.setUserGender(GENDER);
            loginViaSocial = MoEngageConstants.GOOGLE;
            mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, true);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        switch (requestCode) {
            case AppConstants.REQUEST_CODE_FOR_GOOGLE_PLUS:
                if (resultCode == Activity.RESULT_OK) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (!isFinishing()) {
                        //    showDialogInWelcome(TOKEN_LOGGING_PROGRESS_DIALOG);
                    }
                    handleSignInResult(result);
                } else {
                    dismissProgressDialog(LOGGING_IN_DIALOG);
                }
                break;
            default:
                break;
        }
    }

    public void dismissProgressDialog(int id) {
        try {
            switch (id) {
                case LOGGING_IN_DIALOG: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                }
                case TOKEN_LOGGING_PROGRESS_DIALOG: {
                    if (mLoggingProgressDialog != null && mLoggingProgressDialog.isShowing()) {
                        mLoggingProgressDialog.dismiss();
                    }
                    break;
                }
                default:
                    break;
            }

        } catch (IllegalArgumentException e) {
            Crashlytics.getInstance().core.logException(e);
            LogUtils.error(this.getClass().getName(), e.toString(), e);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String firstName = "";
            String lastName = "";
            if (personName != null) {
                String[] names = personName.split(" ");
                firstName = names[0];
                lastName = names[names.length - 1];
            }
            String personEmail = acct.getEmail();
            String imageURL = "";
            if (acct.getPhotoUrl() != null) {
                imageURL = acct.getPhotoUrl().toString();
            }
            String idToken = acct.getIdToken();
            String socialId = acct.getId();
            dismissProgressDialog(LOGGING_IN_DIALOG);
            showGenderInputDialog(personName, personEmail);
        }
    }

    public void getTokenFromGoogleAuth(String personEmail) {
        new RetrieveTokenTask().execute(personEmail);
    }

    public void showGenderInputDialog(String userName, String personEmail) {
        GenderInputFormDialogFragment fragment = (GenderInputFormDialogFragment) getFragmentManager().findFragmentByTag(AppConstants.GENDER_INPUT_DIALOG);
        if (fragment == null) {
            fragment = new GenderInputFormDialogFragment();
            Bundle b = new Bundle();
            b.putString(BaseDialogFragment.USER_NAME, userName);
            b.putString(BaseDialogFragment.EMAIL_ID, personEmail);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), AppConstants.GENDER_INPUT_DIALOG);
        }
    }

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            final String SCOPES = "oauth2:profile email";

            try {
                mToken = GoogleAuthUtil.getToken(WelcomeActivity.this, accountName, SCOPES);
            } catch (IOException e) {
                Crashlytics.getInstance().core.logException(e);
                e.printStackTrace();
            } catch (UserRecoverableAuthException e) {

            } catch (GoogleAuthException e) {
            }
            return mToken;
        }

        @Override
        protected void onPostExecute(String token) {
            super.onPostExecute(token);
            showDialogInWelcome(TOKEN_LOGGING_PROGRESS_DIALOG);
            String URL_ACCESS_TOKEN = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + token;
            mLoginPresenter.googleTokenExpireInFromPresenter(URL_ACCESS_TOKEN);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(WelcomeActivity.this);
            mGoogleApiClient.disconnect();
        }
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
    }

    @Override
    public void userLoggedIn(SocialPerson person) {
        if (person == null) {
            WelcomeActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(AppUtils.getInstance().getApplicationContext(), getString(R.string.ID_GENERIC_ERROR), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if (StringUtil.isNotNullOrEmptyString(person.getEmail())) {
            if (person.getLoginType().equalsIgnoreCase(SocialPerson.LOGIN_TYPE_GOOGLE)) {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AppUtils.getInstance().getApplicationContext(), getString(R.string.ID_GENERIC_ERROR), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(WelcomeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
        if (null != fbLogin) {
            fbLogin.setEnabled(true);
            mUserPreference.delete();
        }
        if (StringUtil.isNotNullOrEmptyString(errorMsg)) {
            switch (errorMsg) {
                case AppConstants.LOGOUT_USER:
                    AnalyticsManager.initializeMixpanel(WelcomeActivity.this);
                    HashMap<String, Object> properties = new EventProperty.Builder().build();
                    AnalyticsManager.trackEvent(Event.USER_LOG_OUT, getScreenName(), properties);
                    mUserPreference.delete();
                    MoEHelper.getInstance(getApplicationContext()).logoutUser();
                    MixpanelHelper.clearMixpanel(SheroesApplication.mContext);
                    ((NotificationManager) SheroesApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOG_OUT, GoogleAnalyticsEventActions.LOG_OUT_OF_APP, AppConstants.EMPTY_STRING);
                    initializeAllDataAfterGCMId();
                    break;
                default:
                    onShowErrorDialog(errorMsg, feedParticipationEnum);

            }
        }

    }

}

