package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserFromReferralRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
import appliedlife.pvtltd.SHEROES.social.CustomSocialDialog;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.social.GooglePlusHelper;
import appliedlife.pvtltd.SHEROES.social.SocialListener;
import appliedlife.pvtltd.SHEROES.social.SocialPerson;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.SheroesWelcomeViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.FacebookErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;

/**
 * Created by sheroes on 06/03/17.
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, LoginView, SocialListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String SCREEN_LABEL = "Intro Screen";
    private final String TAG = LogUtils.makeLogTag(WelcomeActivity.class);
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;
    @Inject
    LoginPresenter mLoginPresenter;
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
    CoordinatorLayout clWelcome;
    @Bind(R.id.scroll_view_welcome)
    ScrollView mScrollView;
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
    private boolean isFirstTimeUser;
    private Handler mHandler;
    private Runnable mRunnable;
    private CallbackManager callbackManager;
    private int gcmForGoogleAndFacebook;
    public static final int GOOGLE_CALL = 101;
    public static final int FACEBOOK_CALL = 201;
    public static final int NORMAL_CALL = 301;
    private GooglePlusHelper mGooglePlusHelper;
    private GoogleSignInOptions gso;
    private ProgressDialog mProgressDialog;
    //google api client
    public static GoogleApiClient mGoogleApiClient;
    private String mToken = null;
    private String loginViaSocial = MoEngageConstants.GOOGLE;
    private long currentTime;
    private String mGcmId;
    private String loggedInChannel;
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mLoginPresenter.attachView(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        AppsFlyerLib.getInstance().setImeiData(appUtils.getIMEI());
        AppsFlyerLib.getInstance().setAndroidIdData(appUtils.getDeviceId());
        initializeAllDataAfterGCMId();
    }

    private void initializeAllDataAfterGCMId() {
        int versionCode = BuildConfig.VERSION_CODE;
        moEngageUtills.entityMoEngageAppVersion(this, mMoEHelper, payloadBuilder, versionCode);
        if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet() && null != mInstallUpdatePreference.get()) {
            if (mInstallUpdatePreference.get().getAppVersion() < versionCode) {
                InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                installUpdateForMoEngage.setFirstOpen(true);
                installUpdateForMoEngage.setAppVersion(versionCode);
                installUpdateForMoEngage.setWelcome(true);
                mInstallUpdatePreference.set(installUpdateForMoEngage);
            }
            mMoEHelper.setExistingUser(true);
        } else {
            InstallUpdateForMoEngage installUpdateForMoEngage = new InstallUpdateForMoEngage();
            installUpdateForMoEngage.setAppVersion(versionCode);
            installUpdateForMoEngage.setFirstOpen(true);
            mInstallUpdatePreference.set(installUpdateForMoEngage);
            mMoEHelper.setExistingUser(false);
            mMoEHelper.setUserAttribute(MoEngageConstants.FIRST_APP_OPEN, new Date());
        }
        moEngageUtills.entityMoEngageLastOpen(this, mMoEHelper, payloadBuilder, new Date());
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
            isFirstTimeUser = false;
            DrawerViewHolder.selectedOptionName = null;
            openHomeScreen();
        } else {
            setContentView(R.layout.welcome_activity);
            ButterKnife.bind(WelcomeActivity.this);
            isFirstTimeUser = true;
            initHomeViewPagerAndTabs();
            loginSetUp();
            if (!NetworkUtil.isConnected(mSheroesApplication)) {
                showNetworkTimeoutDoalog(false, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                return;
            } else {
                mScrollView.post(new Runnable() {
                    public void run() {
                        mScrollView.fullScroll(mScrollView.FOCUS_DOWN);
                    }
                });
                mScrollView.scrollTo(0, mScrollView.getBottom() + 1);

            }

            ((SheroesApplication) WelcomeActivity.this.getApplication()).trackScreenView(getString(R.string.ID_INTRO_SCREEN));
        }
        mLoginPresenter.getMasterDataToPresenter();
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
                                            if (null != accessToken) {
                                                LoginRequest loginRequest = loginRequestBuilder();
                                                loginRequest.setAccessToken(accessToken.getToken());
                                                AppUtils appUtils = AppUtils.getInstance();
                                                loginRequest.setAppVersion(appUtils.getAppVersionName());
                                                //TODO:: NEED to Change
                                                loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                                                loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
                                                loginRequest.setGcmorapnsid(mGcmId);
                                                loginViaSocial = MoEngageConstants.FACEBOOK;
                                                mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, true);
                                            }
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
                        dismissDialog();
                        (WelcomeActivity.this).showNetworkTimeoutDoalog(true, false, AppConstants.CHECK_NETWORK_CONNECTION);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        mUserPreference.delete();
                        dismissDialog();
                        (WelcomeActivity.this).showNetworkTimeoutDoalog(true, false, exception.getMessage());
                    }
                });

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gcmForGoogleAndFacebook = FACEBOOK_CALL;
                showDialogInWelcome(CustomSocialDialog.LOGGING_IN_DIALOG);
                if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
                    showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                    return;
                } else {
                    getGcmId();
                }
            }
        });


    }

    private void openHomeScreen() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getNextScreen()) && mUserPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION) && mUserPreference.get().isSheUser()) {
            openLoginActivity();
        } else {
            Intent boardingIntent = new Intent(WelcomeActivity.this, OnBoardingActivity.class);
            boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(boardingIntent);
            finish();
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

        mViewPagerAdapter = new SheroesWelcomeViewPagerAdapter(screenNameList,this,screenText);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(WelcomeActivity.this);
        ivWelcomeFirst.setImageResource(R.drawable.ic_circle_red);
        ivWelcomeSecond.setImageResource(R.drawable.ic_circle_w);
        ivWelcomeThird.setImageResource(R.drawable.ic_circle_w);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                mHandler.post(mRunnable);
            }
        }, 500, 10000);

    }

    @Override
    protected void onDestroy() {
        if (mGooglePlusHelper != null) {
            mGooglePlusHelper.signOut();
        }
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
        super.onDestroy();
    }

    @OnClick(R.id.tv_other_login_option)
    public void otherLoginOption() {
        openLoginActivity();
    }

    private void openLoginActivity() {
        Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        // bundle.putString(AppConstants.SHEROES_AUTH_TOKEN, mGcmId);
        loginIntent.putExtras(bundle);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        switch (errorReason) {
            case AppConstants.CHECK_NETWORK_CONNECTION:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                break;
            case AppConstants.HTTP_401_UNAUTHORIZED:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_INVALID_USER_PASSWORD));
                break;
            default:
                showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
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
                ivWelcomeFirst.setImageResource(R.drawable.ic_circle_red);
                ivWelcomeSecond.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeThird.setImageResource(R.drawable.ic_circle_w);
                currentPage = 0;
                break;
            case AppConstants.ONE_CONSTANT:
                ivWelcomeSecond.setImageResource(R.drawable.ic_circle_red);
                ivWelcomeFirst.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeThird.setImageResource(R.drawable.ic_circle_w);
                currentPage = 0;
                break;
            case AppConstants.TWO_CONSTANT:
                ivWelcomeThird.setImageResource(R.drawable.ic_circle_red);
                ivWelcomeFirst.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeSecond.setImageResource(R.drawable.ic_circle_w);
                currentPage = 0;
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

    public void showFaceBookError(String message) {
        FacebookErrorDialog fragment = (FacebookErrorDialog) getFragmentManager().findFragmentByTag(FacebookErrorDialog.class.getName());
        if (fragment == null) {
            fragment = new FacebookErrorDialog();
            Bundle b = new Bundle();
            b.putString(AppConstants.SHEROES_AUTH_TOKEN, message);
            b.putInt(AppConstants.FACEBOOK_VERIFICATION, AppConstants.ONE_CONSTANT);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), FacebookErrorDialog.class.getName());
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Snackbar.make(clWelcome, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
        if (mProgressDialog != null ) {
            mProgressDialog.dismiss();
        }
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && null != extras.getString(AppConstants.GOOGLE_PLAY_URL_REFERRAL_CONTACT_ID)) {
                if (StringUtil.isNotNullOrEmptyString(extras.getString(AppConstants.GOOGLE_PLAY_URL_REFERRAL_CONTACT_ID))) {
                    String appContactId = extras.getString(AppConstants.GOOGLE_PLAY_URL_REFERRAL_CONTACT_ID);
                    LogUtils.info(TAG, "********Id of  new Intent ***********" + appContactId);
                    UserFromReferralRequest userFromReferralRequest = new UserFromReferralRequest();
                    if (StringUtil.isNotNullOrEmptyString(appContactId)) {
                        try {
                            userFromReferralRequest.setAppUserContactTableId(Long.parseLong(appContactId));
                            mLoginPresenter.updateUserReferralInPresenter(userFromReferralRequest);
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.info(TAG, "********For new Intent ***********");
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
        mProgressDialog = createCustomDialog(id);
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    /**
     * Creates and returns dialog
     *
     * @param id id of dialog
     * @return dialog
     */
    private ProgressDialog createCustomDialog(int id) {
        ProgressDialog mProgressDialog = null;
        try {
            CustomSocialDialog dialogCreater = null;
            switch (id) {
                case CustomSocialDialog.LOGGING_IN_DIALOG: {
                    mProgressDialog = new ProgressDialog(WelcomeActivity.this);
                    mProgressDialog.setMessage(getString(R.string.ID_PLAY_STORE_DATA));
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
                    break;
                }
                default:
                    break;
            }
            return mProgressDialog;
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            LogUtils.error(TAG, e);
            return null;
        }
    }

    @OnClick(R.id.btn_login_google)
    public void googleLoginClick() {
        gcmForGoogleAndFacebook = GOOGLE_CALL;
        showDialogInWelcome(CustomSocialDialog.LOGGING_IN_DIALOG);
        if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
            showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        } else {
            getGcmId();
        }
    }

    public void launchGooglePlusLogin() {
        if (AppUtils.getInstance().isNetworkAvailable()) {
            signIn();
        } else {
            Toast.makeText(WelcomeActivity.this, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    private void getGcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(WelcomeActivity.this, getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                LogUtils.info(TAG, "******* ******Registarion" + registrationId);
                mGcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                    PushManager.getInstance().refreshToken(WelcomeActivity.this, mGcmId);
                    fbLogin.setEnabled(true);
                    checkSignUpCall(gcmForGoogleAndFacebook);
                } else {
                    fbLogin.setEnabled(false);
                    if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
                        showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                        return;
                    } else {
                        getGcmId();
                    }
                }
            }

            @Override
            public void onFailure(String ex) {
                mGcmId = ex;
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
        fbLogin.setEnabled(true);
        dismissDialog();
        if (loginResponse != null) {
            switch (loginResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                        loginResponse.setTokenTime(System.currentTimeMillis());
                        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                        loginResponse.setGcmId(mGcmId);
                        mUserPreference.set(loginResponse);
                        AnalyticsManager.initializeMixpanel(WelcomeActivity.this);
                        moEngageUtills.entityMoEngageUserAttribute(WelcomeActivity.this, mMoEHelper, payloadBuilder, loginResponse);

                        if (null != loginResponse.getUserSummary() && null != loginResponse.getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getCrdt())) {
                            long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());

                            final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(currentTime < createdDate).authProvider(loginViaSocial == MoEngageConstants.FACEBOOK ? "Facebook" : "Google").build();
                            AnalyticsManager.trackEvent(Event.APP_LOGIN, getScreenName(), properties);
                            if (createdDate < currentTime) {
                                moEngageUtills.entityMoEngageLoggedIn(WelcomeActivity.this, mMoEHelper, payloadBuilder, loginViaSocial);
                                if (loginViaSocial == MoEngageConstants.FACEBOOK) {
                                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_WITH_FACEBOOK, AppConstants.EMPTY_STRING);
                                } else {
                                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_WITH_GOOGLE, AppConstants.EMPTY_STRING);
                                }

                            } else {
                                moEngageUtills.entityMoEngageSignUp(WelcomeActivity.this, mMoEHelper, payloadBuilder, loginViaSocial);
                                if (loginViaSocial == MoEngageConstants.FACEBOOK) {
                                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SIGN_UP, GoogleAnalyticsEventActions.SIGN_UP_WITH_FACEBOOK, AppConstants.EMPTY_STRING);
                                } else {
                                    ((SheroesApplication) WelcomeActivity.this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SIGN_UP, GoogleAnalyticsEventActions.SIGN_UP_WITH_GOOGLE, AppConstants.EMPTY_STRING);
                                }
                            }
                            ((SheroesApplication) WelcomeActivity.this.getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
                        }
                        mMoEHelper.setUserAttribute(MoEngageConstants.ACQUISITION_CHANNEL, loginViaSocial);

                        mUserPreference.set(loginResponse);
                        openHomeScreen();

                    } else {
                        mUserPreference.delete();
                        LoginManager.getInstance().logOut();
                        signOut();
                        showFaceBookError(AppConstants.EMPTY_STRING);
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
                    if (StringUtil.isNotNullOrEmptyString(errorMessage)) {
                        showFaceBookError(errorMessage);
                    } else {
                        errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR);
                        showFaceBookError(errorMessage);
                    }
                    break;
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {
        if (expireInResponse.getExpiresIn() > 0 && StringUtil.isNotNullOrEmptyString(mToken)) {
            LoginRequest loginRequest = loginRequestBuilder();
            loginRequest.setAccessToken(mToken);
            AppUtils appUtils = AppUtils.getInstance();
            loginRequest.setAppVersion(appUtils.getAppVersionName());
            //TODO:: NEED to Change
            loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
            loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
            loginRequest.setGcmorapnsid(mGcmId);
            loginRequest.setCallForSignUp(AppConstants.GOOGLE_PLUS);
            loginViaSocial = MoEngageConstants.GOOGLE;
            mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, true);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppConstants.REQUEST_CODE_FOR_GOOGLE_PLUS:
                if (resultCode == Activity.RESULT_OK) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    showDialogInWelcome(CustomSocialDialog.LOGGING_IN_DIALOG);
                    handleSignInResult(result);
                } else {
                    dismissDialog();
                }
                break;
            default:
                LogUtils.info(TAG, "Request is not supported");
                break;
        }
    }

    public void dismissDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
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
            new RetrieveTokenTask().execute(personEmail);
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
    }

    @Override
    public void userLoggedIn(SocialPerson person) {
        if (person == null) {
            if (mGooglePlusHelper != null) {
                mGooglePlusHelper.dismissDialog();
            }
            WelcomeActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(AppUtils.getInstance().getApplicationContext(), getString(R.string.ID_GENERIC_ERROR), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        LogUtils.info(TAG, person.getFirstname() + " " + person.getLastname() + " " + person.getEmail() + " " + person.getLoginType());
        if (StringUtil.isNotNullOrEmptyString(person.getEmail())) {
            if (person.getLoginType().equalsIgnoreCase(SocialPerson.LOGIN_TYPE_GOOGLE)) {
                if (mGooglePlusHelper != null) {
                    mGooglePlusHelper.signOut();
                }
            }
            if (person.getLoginType().equalsIgnoreCase(SocialPerson.LOGIN_TYPE_GOOGLE)) {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AppUtils.getInstance().getApplicationContext(), getString(R.string.ID_GENERIC_ERROR), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(WelcomeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        //super.showError(errorMsg, feedParticipationEnum);
        dismissDialog();
        fbLogin.setEnabled(true);
        mUserPreference.delete();

    }


}

