package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.AccessToken;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;

import javax.inject.Inject;

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
import appliedlife.pvtltd.SHEROES.models.entities.login.AppStatus;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.service.FCMClientManager;
import appliedlife.pvtltd.SHEROES.social.FBConnectHelper;
import appliedlife.pvtltd.SHEROES.social.GoogleConnectHelper;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ErrorUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.SheroesWelcomeViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.GenderInputFormDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MaleErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;

/**
 * Created by sheroes on 06/03/17.
 */

public class WelcomeActivity extends BaseActivity implements FBConnectHelper.IOnFbSignInListener, GoogleConnectHelper.IOnGoogleConnectListener, ViewPager.OnPageChangeListener, LoginView {
    //region constant variables
    public static final String SCREEN_LABEL = "Intro Screen";
    public static final String GENDER = "female";
    public static final int TOKEN_LOGGING_PROGRESS_DIALOG = 2;
    public static final String GOOGLE = "google";
    public static final String FACEBOOK = "facebook";
    public static final int LOGGING_IN_DIALOG = 1;
    //endregion constant variables

    //region member variables
    private int mCurrentPage = 0;
    private Timer mTimer;
    private Handler mHandler;
    private Runnable mRunnable;
    private ProgressDialog mProgressDialog, mLoggingProgressDialog;
    private String mToken = null;
    private String mLoginViaSocial = GOOGLE;
    private long mCurrentTime;
    private String mFcmId;
    //Ads Navigation
    private boolean mIsBranchFirstSession = false;
    private String mDeepLinkUrl = null;
    private String mDefaultTab = null;
    private ArrayList<Integer> mScreenNameList = new ArrayList<>();
    private FBConnectHelper mFbConnectHelper;
    private GoogleConnectHelper mGoogleConnectHelper;
    //endregion member variables

    //region inject variables
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<AppStatus> mInstallUpdatePreference;
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    Preference<AppInstallation> mAppInstallation;
    @Inject
    AppUtils mAppUtils;
    @Inject
    ErrorUtil mErrorUtil;
    //endregion inject variables

    // region view
    @Bind(R.id.welcome_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.iv_welcome_first)
    ImageView ivWelcomeFirst;
    @Bind(R.id.iv_welcome_second)
    ImageView ivWelcomeSecond;
    @Bind(R.id.iv_welcome_third)
    ImageView ivWelcomeThird;
    @Bind(R.id.click_to_join_fb_signup)
    Button fbLogin;
    @Bind(R.id.btn_login_google)
    Button btnGoogleLogin;
    @Bind(R.id.cl_welcome)
    RelativeLayout clWelcome;
    @Bind(R.id.tv_permission)
    TextView tvPermission;
    @Bind(R.id.tv_other_login_option)
    TextView tvOtherLoginOption;
    @Bind(R.id.tv_user_msg)
    TextView tvUserMsg;
    //endregion view

    //region lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mLoginPresenter.attachView(this);
        mLoginPresenter.queryConfig();

        if (getIntent() != null && getIntent().getExtras() != null) {
            mIsBranchFirstSession = getIntent().getExtras().getBoolean(BaseActivity.BRANCH_FIRST_SESSION);
            mDeepLinkUrl = getIntent().getExtras().getString(BaseActivity.DEEP_LINK_URL);
        }
        setUpView();
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
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        mLoginPresenter.detachView();
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
    }
    //endregion lifecycle methods

    //region inherited methods
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
                mCurrentPage = -1;
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
    public void sendForgotPasswordEmail(ForgotPasswordResponse forgotPasswordResponse) {

    }

    @Override
    public void sendVerificationEmailSuccess(EmailVerificationResponse emailVerificationResponse) {

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

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        if (fbLogin != null) {
            fbLogin.setEnabled(true);
        }
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
        if (loginResponse != null) {
            switch (loginResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                        loginAuthTokenResponse(loginResponse);
                    } else {
                        mUserPreference.delete();
                        LoginManager.getInstance().logOut();
                        mGoogleConnectHelper.signOut();
                        showMaleError("");
                    }
                    break;
                case AppConstants.INVALID:
                    mUserPreference.delete();
                    break;
                case AppConstants.FAILED:
                    mUserPreference.delete();
                    mGoogleConnectHelper.signOut();
                    LoginManager.getInstance().logOut();
                    String errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA);
                    String deactivated = loginResponse.getFieldErrorMessageMap().get(AppConstants.IS_DEACTIVATED);
                    if (StringUtil.isNotNullOrEmptyString(errorMessage)) {
                        if (StringUtil.isNotNullOrEmptyString(deactivated) && deactivated.equalsIgnoreCase("true")) {
                            mErrorUtil.showErrorDialogOnUserAction(this, true, false, errorMessage, "true");
                        } else {
                            showMaleError("");
                        }
                    } else {
                        errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR);
                        showMaleError("");
                    }
                    break;
            }
        } else {
            showNetworkTimeoutDialog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {
        if (expireInResponse.getExpiresIn() > 0 && StringUtil.isNotNullOrEmptyString(mToken)) {
            LoginRequest loginRequest = loginRequestBuilder();
            loginRequest.setAccessToken(mToken);
            loginRequest.setCloudMessagingId(mAppUtils.getCloudMessaging());
            loginRequest.setDeviceUniqueId(mAppUtils.getDeviceId());
            loginRequest.setFcmorapnsid(mFcmId);
            loginRequest.setCallForSignUp(AppConstants.GOOGLE_PLUS);
            loginRequest.setUserGender(GENDER);
            mLoginViaSocial = GOOGLE;
            mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFbConnectHelper.onActivityResult(requestCode, resultCode, data);
        mGoogleConnectHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onGoogleSuccess(String name, String email) {
        dismissProgressDialog(LOGGING_IN_DIALOG);
        showGenderInputDialog(name, email);
    }

    @Override
    public void dismissProgress() {
        dismissProgressDialog(LOGGING_IN_DIALOG);
    }

    @Override
    public void onFbSuccess(GraphResponse graphResponse, AccessToken accessToken) {
        if (null != accessToken && StringUtil.isNotNullOrEmptyString(accessToken.getToken())) {
            LoginRequest loginRequest = loginRequestBuilder();
            loginRequest.setAccessToken(accessToken.getToken());
            loginRequest.setCloudMessagingId(mAppUtils.getCloudMessaging());
            loginRequest.setDeviceUniqueId(mAppUtils.getDeviceId());
            loginRequest.setFcmorapnsid(mFcmId);
            loginRequest.setUserGender(GENDER);
            mLoginViaSocial = FACEBOOK;
            mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, true);
        }
    }

    @Override
    public void onFbError(String errorMessage) {
        mUserPreference.delete();
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
        (WelcomeActivity.this).showNetworkTimeoutDialog(true, false, errorMessage);
    }

    @Override
    public void onFbCancel() {
        mUserPreference.delete();
        dismissProgressDialog(LOGGING_IN_DIALOG);
        dismissProgressDialog(TOKEN_LOGGING_PROGRESS_DIALOG);
        (WelcomeActivity.this).showNetworkTimeoutDialog(true, false, AppConstants.CHECK_NETWORK_CONNECTION);
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
                    MixpanelHelper.clearMixpanel(SheroesApplication.mContext);
                    ((NotificationManager) SheroesApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
                    break;
                default:
                    onShowErrorDialog(errorMsg, feedParticipationEnum);
            }
        }
    }
    //endregion inherited methods

    //region public methods
    @OnClick(R.id.tv_other_login_option)
    public void otherLoginOption() {
        openLoginActivity();
    }

    @OnClick(R.id.btn_login_google)
    public void googleLoginClick() {
        showDialogInWelcome(LOGGING_IN_DIALOG);
        if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
            showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        } else {
            if (!StringUtil.isNotNullOrEmptyString(mFcmId))
                getFcmId();
            launchGooglePlusLogin();
        }
    }

    public void launchGooglePlusLogin() {
        if (mAppUtils.isNetworkAvailable()) {
            mGoogleConnectHelper.signIn(this);
        } else {
            Toast.makeText(WelcomeActivity.this, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION), Toast.LENGTH_SHORT).show();
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

    public void showMaleError(String userName) {
        MaleErrorDialog fragment = (MaleErrorDialog) getFragmentManager().findFragmentByTag(MaleErrorDialog.class.getName());
        if (fragment == null) {
            fragment = new MaleErrorDialog();
            Bundle b = new Bundle();
            b.putString(BaseDialogFragment.USER_NAME, userName);
            b.putInt(AppConstants.FACEBOOK_VERIFICATION, AppConstants.ONE_CONSTANT);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), MaleErrorDialog.class.getName());
        }
    }
    //endregion public methods

    //region private methods
    private void setUpView() {
        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(this);
        initHomeViewPagerAndTabs();

        mFbConnectHelper = new FBConnectHelper(this);
        mGoogleConnectHelper = GoogleConnectHelper.getInstance();
        getFcmId();

        loginSetUp();
    }

    private void loginSetUp() {
        mCurrentTime = System.currentTimeMillis();
        mGoogleConnectHelper.initialize(this, this);
        mFbConnectHelper.connectFb();

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogInWelcome(LOGGING_IN_DIALOG);
                if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
                    showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                } else {
                    if (!StringUtil.isNotNullOrEmptyString(mFcmId))
                        getFcmId();
                    LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
                }
            }
        });
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void initHomeViewPagerAndTabs() {
        mScreenNameList.add(R.drawable.welcome_first);
        mScreenNameList.add(R.drawable.welcome_second);
        mScreenNameList.add(R.drawable.welcome_third);

        SheroesWelcomeViewPagerAdapter viewPagerAdapter = new SheroesWelcomeViewPagerAdapter(mScreenNameList, this);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(WelcomeActivity.this);
        ivWelcomeFirst.setImageResource(R.drawable.vector_circle_red);
        ivWelcomeSecond.setImageResource(R.drawable.vector_circle_w);
        ivWelcomeThird.setImageResource(R.drawable.vector_circle_w);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            public void run() {
                mViewPager.setCurrentItem(mCurrentPage++, true);
                mHandler.postDelayed(mRunnable, 10000);
            }
        };
        mHandler.postDelayed(mRunnable, 10000);
    }

    private void openLoginActivity() {
        Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConstants.IS_FROM_ADVERTISEMENT, mIsBranchFirstSession);
        bundle.putString(AppConstants.ADS_DEEP_LINK_URL, mDeepLinkUrl);
        loginIntent.putExtras(bundle);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        finish();
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

    private void getFcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FCMClientManager pushClientManager = new FCMClientManager(WelcomeActivity.this, getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new FCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                mFcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(mFcmId)) {
                    //Refresh FCM token
                    CleverTapAPI cleverTapAPI = CleverTapHelper.getCleverTapInstance(SheroesApplication.mContext);
                    if (cleverTapAPI != null) {
                        cleverTapAPI.data.pushFcmRegistrationId(registrationId, true);
                    }
                    fbLogin.setEnabled(true);
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

    private void loginAuthTokenResponse(LoginResponse loginResponse) {
        loginResponse.setTokenTime(System.currentTimeMillis());
        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
        loginResponse.setFcmId(mFcmId);
        mUserPreference.set(loginResponse);
        AnalyticsManager.initializeMixpanel(WelcomeActivity.this);

        if (null != loginResponse.getUserSummary() && null != loginResponse.getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getCrdt())) {
            long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());
            AnalyticsManager.initializeCleverTap(WelcomeActivity.this, mCurrentTime < createdDate);
            AnalyticsManager.initializeGoogleAnalytics(WelcomeActivity.this);
            AnalyticsManager.initializeFirebaseAnalytics(WelcomeActivity.this);
            final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(mCurrentTime < createdDate).authProvider(mLoginViaSocial.equalsIgnoreCase(FACEBOOK) ? "Facebook" : "Google").build();
            AnalyticsManager.trackEvent(Event.APP_LOGIN, getScreenName(), properties);
            ((SheroesApplication) WelcomeActivity.this.getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
        }
        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(this);
        appInstallationHelper.setupAndSaveInstallation(true);
        openHomeScreen();
    }

    private void openHomeScreen() {

        if (mIsBranchFirstSession && StringUtil.isNotNullOrEmptyString(mDeepLinkUrl)) { //ads for community

            //Event for on-boarding skipping for new user came through branch link
            final HashMap<String, Object> properties = new EventProperty.Builder().branchLink(mDeepLinkUrl).build();
            AnalyticsManager.trackEvent(Event.ONBOARDING_SKIPPED, getScreenName(), properties);

            Uri url = Uri.parse(mDeepLinkUrl);
            Intent intent = new Intent(WelcomeActivity.this, SheroesDeepLinkingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(CommunityDetailActivity.TAB_KEY, "");
            bundle.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
            bundle.putBoolean(AppConstants.IS_FROM_ADVERTISEMENT, mIsBranchFirstSession);
            if (StringUtil.isNotNullOrEmptyString(mDefaultTab)) {
                bundle.putString(CommunityDetailActivity.TAB_KEY, mDefaultTab);
            }
            intent.putExtras(bundle);
            intent.setData(url);
            startActivity(intent);
        } else {
            Intent boardingIntent = new Intent(WelcomeActivity.this, OnBoardingActivity.class);
            Bundle bundle = new Bundle();
            boardingIntent.putExtras(bundle);
            boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(boardingIntent);
            CommonUtil.setPrefValue(AppConstants.SELECT_LANGUAGE_SHARE_PREF);
        }
        finishAffinity();
    }
    //endregion private methods

    //region protected methods
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    //endregion protected methods

    //region inner class
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
    //endregion inner class

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

}

