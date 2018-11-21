package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clevertap.android.sdk.CleverTapAPI;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.login.LoginManager;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.CleverTapHelper;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.service.FCMClientManager;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ErrorUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EmailVerificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ResetPasswordDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ResetPasswordSuccessDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;


/**
 * Created by Praveen Singh on 04/01/2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 04/01/2017.
 * Title: A login screen that offers login via mEmail/mPassword.
 */
public class LoginActivity extends BaseActivity implements LoginView {
    // region Constants
    private static final String SCREEN_LABEL = "Email Login Screen";
    private final String TAG = LogUtils.makeLogTag(LoginActivity.class);
    public static final int LOGGING_IN_DIALOG = 1;
    //endregion

    // region Inject
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<AppInstallation> mAppInstallation;
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    ErrorUtil mErrorUtil;
    //endregion

    // region views
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.email_sign_in_button)
    Button mEmailSign;
    @Bind(R.id.tv_email_description)
    TextView mTvEmailDescription;
    //endregion

    // region member variables
    private boolean mIsBranchFirstSession = false;
    private String mDeepLinkUrl = null;
    private String mDefaultTab = null;
    private ProgressDialog mProgressDialog;
    private String mFcmId;
    private String mEmail;
    private String mPassword;
    private boolean mIsPasswordVisible;
    private long mCurrentTime;
    private ResetPasswordDialogFragment mResetPasswordDialogFragment;
    //endregion

    // region lifecycle override methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != mUserPreference && mUserPreference.isSet() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken()) && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getNextScreen())) {
            if (mUserPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.FEED_SCREEN)) {
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
            } else if (mUserPreference.get().isSheUser() && mUserPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION)) {
                showEmailVerificationFragment();
            } else {
                Intent boardingIntent = new Intent(this, OnBoardingActivity.class);
                boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(boardingIntent);
            }
        } else {
            renderLoginView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == AppConstants.REQUEST_CODE_FOR_FACEBOOK && null != intent) {
            int verificationMessageId = intent.getExtras().getInt(AppConstants.HOME_FRAGMENT);
            if (verificationMessageId == AppConstants.TWO_CONSTANT) {
                onLoginAuthToken();
            } else {
                onBackPressed();
            }
        }
    }

    @Override
    public void onDestroy() {
        mLoginPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mUserPreference.delete();
        super.onBackPressed();
    }
    //endregion

    // region public methods

    /**
     * Stor token into share prefrances
     *
     * @param loginResponse
     */
    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        mEmailSign.setEnabled(true);
        checkDialogDismiss();
        if (null != loginResponse) {
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getStatus())) {
                switch (loginResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        if (loginResponse.getUserSummary() == null || loginResponse.getUserSummary().getUserBO() == null)
                            return;

                        long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());
                        loginResponse.setTokenTime(System.currentTimeMillis());
                        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                        loginResponse.setFcmId(mFcmId);
                        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(this);
                        appInstallationHelper.setupAndSaveInstallation(true);
                        mUserPreference.set(loginResponse);
                        if (null != loginResponse.getUserSummary()) {
                            ((SheroesApplication) this.getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
                        }
                        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_USING_EMAIL, AppConstants.EMPTY_STRING);
                        AnalyticsManager.initializeMixpanel(this);

                        AnalyticsManager.initializeCleverTap(SheroesApplication.mContext, mCurrentTime < createdDate);
                        final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(mCurrentTime < createdDate).authProvider("Email").build();
                        AnalyticsManager.trackEvent(Event.APP_LOGIN, getScreenName(), properties);
                        onLoginAuthToken();
                        break;
                    case AppConstants.FAILED:
                        LoginManager.getInstance().logOut();
                        onErrorOccurence(loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), loginResponse.getFieldErrorMessageMap().get(AppConstants.IS_DEACTIVATED));
                        break;
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken()) && null != loginResponse.getUserSummary()) {
                    long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());
                    AnalyticsManager.initializeMixpanel(this);
                    AppInstallationHelper appInstallationHelper = new AppInstallationHelper(this);
                    appInstallationHelper.setupAndSaveInstallation(true);
                    loginResponse.setTokenTime(System.currentTimeMillis());
                    loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                    loginResponse.setFcmId(mFcmId);
                    mUserPreference.set(loginResponse);
                    ((SheroesApplication) this.getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_USING_EMAIL, AppConstants.EMPTY_STRING);
                    AnalyticsManager.initializeMixpanel(this);
                    AnalyticsManager.initializeCleverTap(SheroesApplication.mContext, mCurrentTime < createdDate);
                    final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(mCurrentTime < createdDate).authProvider("Email").build();
                    AnalyticsManager.trackEvent(Event.APP_LOGIN, getScreenName(), properties);
                    if (!this.isFinishing()) {
                        onLoginAuthToken();
                    }

                } else {
                    LoginManager.getInstance().logOut();
                    onErrorOccurence(loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), loginResponse.getFieldErrorMessageMap().get(AppConstants.IS_DEACTIVATED));
                }
            }
        }
    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {

    }

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordResponse forgotPasswordResponse) {

    }

    @Override
    public void sendVerificationEmailSuccess(EmailVerificationResponse emailVerificationResponse) {

    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.HTTP_401_UNAUTHORIZED_ERROR:
                case AppConstants.HTTP_401_UNAUTHORIZED:
                    showNetworkTimeoutDialog(true, false, getString(R.string.IDS_INVALID_USER_PASSWORD));
                    break;
                default: {
                    super.onShowErrorDialog(errorReason, feedParticipationEnum);
                }
            }
        }

    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mLoginPresenter;
    }

    @Override
    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        checkDialogDismiss();
        mEmailSign.setEnabled(true);
        onShowErrorDialog(errorMsg, feedParticipationEnum);
    }

    @OnClick(R.id.email_sign_in_button)
    public void onLogInBtnClick() {
        sheroesLogIn();
    }

    @OnClick(R.id.forgot_password)
    public void onForgotPasswordClick() {
        showResetPasswordFragment();
    }

    @OnClick(R.id.iv_password_eye)
    public void passwordEyeOnClick() {
        if (mIsPasswordVisible) {
            mIsPasswordVisible = false;
            mPasswordView.setTransformationMethod(new PasswordTransformationMethod());
            mPasswordView.setSelection(mPasswordView.length());
        } else {
            mIsPasswordVisible = true;
            mPasswordView.setTransformationMethod(null);
            mPasswordView.setSelection(mPasswordView.length());
        }
    }

    @OnClick(R.id.tv_login_back)
    public void backOnClick() {
        onBackPressed();
    }

    public void closeResetPasswordFragment() {
        if (mResetPasswordDialogFragment != null) {
            mResetPasswordDialogFragment.dismiss();
        }
    }

    public void showEmailVerificationFragment() {
        EmailVerificationDialogFragment fragment = (EmailVerificationDialogFragment) getFragmentManager().findFragmentByTag(EmailVerificationDialogFragment.class.getName());
        if (fragment == null) {
            fragment = new EmailVerificationDialogFragment();
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), EmailVerificationDialogFragment.class.getName());
        }
    }

    public void showResetPasswordFragment() {
        mResetPasswordDialogFragment = (ResetPasswordDialogFragment) getFragmentManager().findFragmentByTag(ResetPasswordDialogFragment.class.getName());
        if (mResetPasswordDialogFragment == null) {
            mResetPasswordDialogFragment = new ResetPasswordDialogFragment();
        }
        if (!mResetPasswordDialogFragment.isVisible() && !mResetPasswordDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mResetPasswordDialogFragment.show(getFragmentManager(), ResetPasswordDialogFragment.class.getName());
        }
    }

    public void showResetPasswordSuccessFragment(String password) {
        ResetPasswordSuccessDialogFragment fragment = (ResetPasswordSuccessDialogFragment) getFragmentManager().findFragmentByTag(ResetPasswordSuccessDialogFragment.class.getName());
        if (fragment == null) {
            fragment = new ResetPasswordSuccessDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.EMAIL, password);
            fragment.setArguments(bundle);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), ResetPasswordSuccessDialogFragment.class.getName());
        }
    }

    public void onErrorOccurence(String errorMessage, String isDeactivated) {
        if (!StringUtil.isNotNullOrEmptyString(errorMessage)) {
            errorMessage = getString(R.string.ID_GENERIC_ERROR);
        }
        if (StringUtil.isNotNullOrEmptyString(isDeactivated) && isDeactivated.equalsIgnoreCase("true")) {
            mErrorUtil.showErrorDialogOnUserAction(this, true, false, errorMessage, "true");
        } else {
            showNetworkTimeoutDialog(true, false, errorMessage);
        }
    }

    public void onLoginAuthToken() {
        if (mUserPreference.get().isSheUser() && mUserPreference.get().getNextScreen() != null && mUserPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION)) {
            showEmailVerificationFragment();
        } else {
            if (mIsBranchFirstSession && StringUtil.isNotNullOrEmptyString(mDeepLinkUrl)) { //ads for community
                //Event for on-boarding skipping for new user came through branch link
                final HashMap<String, Object> properties = new EventProperty.Builder().branchLink(mDeepLinkUrl).build();
                AnalyticsManager.trackEvent(Event.ONBOARDING_SKIPPED, getScreenName(), properties);

                Uri url = Uri.parse(mDeepLinkUrl);
                Intent intent = new Intent(LoginActivity.this, SheroesDeepLinkingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
                bundle.putBoolean(AppConstants.IS_FROM_ADVERTISEMENT, mIsBranchFirstSession);
                if (StringUtil.isNotNullOrEmptyString(mDefaultTab)) {
                    bundle.putString(CommunityDetailActivity.TAB_KEY, mDefaultTab);
                }
                intent.putExtras(bundle);
                intent.setData(url);
                startActivity(intent);
            } else {
                Intent boardingIntent = new Intent(this, OnBoardingActivity.class);
                Bundle bundle = new Bundle();
                boardingIntent.putExtras(bundle);
                boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(boardingIntent);
                CommonUtil.setPrefValue(AppConstants.SELECT_LANGUAGE_SHARE_PREF);
            }
        }
        finishAffinity();
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    public void renderLoginView() {
        setContentView(R.layout.activity_login);
        SheroesApplication.getAppComponent(this).inject(this);
        ButterKnife.bind(this);
        mLoginPresenter.attachView(this);
        mLoginPresenter.getMasterDataToPresenter();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mIsBranchFirstSession = bundle.getBoolean(AppConstants.IS_FROM_ADVERTISEMENT);
            mDeepLinkUrl = bundle.getString(AppConstants.ADS_DEEP_LINK_URL);
            mDefaultTab = bundle.getString(CommunityDetailActivity.TAB_KEY);
        }
        String description = getString(R.string.ID_LOGIN_MSG);
        if (StringUtil.isNotNullOrEmptyString(description)) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                mTvEmailDescription.setText(Html.fromHtml(description, 0)); // for 24 api and more
            } else {
                mTvEmailDescription.setText(Html.fromHtml(description));// or for older api
            }
        }
    }
    //endregion

    // region private methods
    private void getFcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FCMClientManager pushClientManager = new FCMClientManager(this, getString(R.string.ID_PROJECT_ID));
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
                    LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                    loginRequest.setUsername(mEmail);
                    loginRequest.setPassword(mPassword);
                    loginRequest.setFcmorapnsid(mFcmId);
                    mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, false);
                } else {
                    getFcmId();
                }
            }

            @Override
            public void onFailure(String ex) {
                mFcmId = ex;
            }
        });
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
            switch (id) {
                case LOGGING_IN_DIALOG: {
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage(getString(R.string.ID_PLAY_STORE_DATA));
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    break;
                }
                default:
                    break;
            }
            return mProgressDialog;
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            return null;
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid mEmail, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void sheroesLogIn() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid mPassword, if the user entered one.
        if (!StringUtil.isNotNullOrEmptyString(mPassword)) {
            mPasswordView.setError(getString(R.string.ID_ERROR_INVALID_PASSWORD));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid mEmail address.
        if (!StringUtil.isNotNullOrEmptyString(mEmail)) {
            mEmailView.setError(getString(R.string.ID_ERROR_EMAIL));
            focusView = mEmailView;
            cancel = true;
            return;
        }
        if (!mAppUtils.checkEmail(mEmail)) {
            mEmailView.setError(getString(R.string.ID_ERROR_INVALID_EMAIL));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            mEmailSign.setEnabled(false);
            showProgressDialog(LOGGING_IN_DIALOG);
            if (StringUtil.isNotNullOrEmptyString(mFcmId)) {
                LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                loginRequest.setUsername(mEmail);
                loginRequest.setPassword(mPassword);
                loginRequest.setFcmorapnsid(mFcmId);
                mCurrentTime = System.currentTimeMillis();
                mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, false);
            } else {
                if (!NetworkUtil.isConnected(this)) {
                    showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                } else {
                    getFcmId();
                }
            }
        }
    }

    /**
     * Show dialog
     *
     * @param id id of dialog
     */
    private void showProgressDialog(int id) {
        mProgressDialog = createCustomDialog(id);
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    private void checkDialogDismiss() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            Crashlytics.getInstance().core.logException(e);
            LogUtils.error(this.getClass().getName(), e.toString(), e);
        }
    }
    //endregion
}


