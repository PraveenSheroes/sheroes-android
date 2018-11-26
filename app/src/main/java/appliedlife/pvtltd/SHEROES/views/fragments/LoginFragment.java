package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.service.FCMClientManager;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
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
 * Title: A login screen that offers login via email/password.
 */

public class LoginFragment extends BaseFragment implements LoginView {
    private static final String SCREEN_LABEL = "Email Login Screen";
    public static final int LOGGING_IN_DIALOG = 1;
    private long currentTime;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<AppInstallation> mAppInstallation;

    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.email_sign_in_button)
    Button mEmailSign;
    @Bind(R.id.tv_email_description)
    TextView tvEmailDescription;
    private ProgressDialog mProgressDialog;
    private String mFcmId;
    private String email;
    private String password;
    private boolean isEye;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mLoginPresenter.attachView(this);
        mLoginPresenter.getMasterDataToPresenter();
        String description = getString(R.string.ID_LOGIN_MSG);
        SpannableString spannableString = new SpannableString(description);
        if (StringUtil.isNotNullOrEmptyString(description)) {
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.feed_article_label)), 15, 38, 0);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 15, 38, 0);
            tvEmailDescription.setMovementMethod(LinkMovementMethod.getInstance());
            tvEmailDescription.setText(spannableString, TextView.BufferType.SPANNABLE);
            tvEmailDescription.setSelected(true);
        }
        return view;
    }

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
                        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(getActivity());
                        appInstallationHelper.setupAndSaveInstallation(true);
                        mUserPreference.set(loginResponse);
                        if (getActivity() != null && null != loginResponse.getUserSummary()) {
                            ((SheroesApplication) getActivity().getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
                        }
                        AnalyticsManager.initializeMixpanel(getContext());
                        AnalyticsManager.initializeCleverTap(SheroesApplication.mContext, currentTime < createdDate);
                        AnalyticsManager.initializeGoogleAnalytics(getContext());
                        final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(currentTime < createdDate).authProvider("Email").build();
                        AnalyticsManager.trackEvent(Event.APP_LOGIN, getScreenName(), properties);
                        ((LoginActivity) getActivity()).onLoginAuthToken();
                        break;
                    case AppConstants.FAILED:
                        LoginManager.getInstance().logOut();
                        if (getActivity() != null)
                            ((LoginActivity) getActivity()).onErrorOccurence(loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), loginResponse.getFieldErrorMessageMap().get(AppConstants.IS_DEACTIVATED));
                        break;
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken()) && null != loginResponse.getUserSummary()) {
                    long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());
                    AnalyticsManager.initializeMixpanel(getContext());
                    AppInstallationHelper appInstallationHelper = new AppInstallationHelper(getActivity());
                    appInstallationHelper.setupAndSaveInstallation(true);
                    loginResponse.setTokenTime(System.currentTimeMillis());
                    loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                    loginResponse.setFcmId(mFcmId);
                    mUserPreference.set(loginResponse);
                    if (getActivity() != null) {
                        ((SheroesApplication) getActivity().getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
                    }
                    AnalyticsManager.initializeMixpanel(getContext());
                    AnalyticsManager.initializeCleverTap(SheroesApplication.mContext, currentTime < createdDate);
                    AnalyticsManager.initializeGoogleAnalytics(getContext());
                    final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(currentTime < createdDate).authProvider("Email").build();
                    AnalyticsManager.trackEvent(Event.APP_LOGIN, getScreenName(), properties);
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        ((LoginActivity) getActivity()).onLoginAuthToken();
                    }
                } else {
                    LoginManager.getInstance().logOut();
                    if (getActivity() != null)
                        ((LoginActivity) getActivity()).onErrorOccurence(loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), loginResponse.getFieldErrorMessageMap().get(AppConstants.IS_DEACTIVATED));
                }
            }
        }
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
    protected SheroesPresenter getPresenter() {
        return mLoginPresenter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoginPresenter.detachView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @OnClick(R.id.email_sign_in_button)
    public void onLogInBtnClick() {
        sheroesLogIn();
    }

    @OnClick(R.id.forgot_password)
    public void onForgotPasswordClick() {
        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_login, resetPasswordFragment, ResetPasswordFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void sheroesLogIn() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (!StringUtil.isNotNullOrEmptyString(password)) {
            mPasswordView.setError(getString(R.string.ID_ERROR_INVALID_PASSWORD));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (!StringUtil.isNotNullOrEmptyString(email)) {
            mEmailView.setError(getString(R.string.ID_ERROR_EMAIL));
            focusView = mEmailView;
            cancel = true;
            return;
        }
        if (!mAppUtils.checkEmail(email)) {
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
            showDialog(LOGGING_IN_DIALOG);
            if (StringUtil.isNotNullOrEmptyString(mFcmId)) {
                LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                loginRequest.setUsername(email);
                loginRequest.setPassword(password);
                loginRequest.setFcmorapnsid(mFcmId);
                currentTime = System.currentTimeMillis();
                mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, false);
            } else {
                if (!NetworkUtil.isConnected(getContext())) {
                    showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                    return;
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
    private void showDialog(int id) {
        mProgressDialog = createCustomDialog(id);
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        checkDialogDismiss();
        mEmailSign.setEnabled(true);
        super.showError(errorMsg, feedParticipationEnum);
    }

    private void getFcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (getActivity() == null || !isAdded()) {
            return;
        }
        FCMClientManager pushClientManager = new FCMClientManager(getActivity(), getString(R.string.ID_PROJECT_ID));
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
                    loginRequest.setUsername(email);
                    loginRequest.setPassword(password);
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
                    mProgressDialog = new ProgressDialog(getActivity());
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

    @OnClick(R.id.iv_password_eye)
    public void passwordEyeOnClick() {
        if (isEye) {
            isEye = false;
            mPasswordView.setTransformationMethod(new PasswordTransformationMethod());
            mPasswordView.setSelection(mPasswordView.length());
        } else {
            isEye = true;
            mPasswordView.setTransformationMethod(null);
            mPasswordView.setSelection(mPasswordView.length());
        }
    }

    @OnClick(R.id.tv_login_back)
    public void onBack() {
        getActivity().onBackPressed();
    }
}