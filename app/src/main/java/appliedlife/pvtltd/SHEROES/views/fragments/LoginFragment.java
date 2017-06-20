package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.f2prateek.rx.preferences.Preference;
import com.facebook.login.LoginManager;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
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

public class LoginFragment extends BaseFragment implements LoginView{
    private final String TAG = LogUtils.makeLogTag(LoginFragment.class);
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.email_sign_in_button)
    Button mEmailSign;
    private LoginActivityIntractionListner mLoginActivityIntractionListner;
    private MoEHelper mMoEHelper;
    private String mGcmId;
    private String email;
    private String password;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof LoginActivityIntractionListner) {
                mLoginActivityIntractionListner = (LoginActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mGcmId = getArguments().getString(AppConstants.SHEROES_AUTH_TOKEN);
        }
        mLoginPresenter.attachView(this);
        mEmailView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        mPasswordView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        setProgressBar(mProgressBar);
        //  mLoginPresenter.getMasterDataToPresenter();
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
        if (null != loginResponse) {
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getStatus())) {
                switch (loginResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        loginResponse.setTokenTime(System.currentTimeMillis());
                        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                        loginResponse.setGcmId(mGcmId);
                        moEngageUtills.entityMoEngageUserAttribute(getActivity(), mMoEHelper, payloadBuilder, loginResponse);
                        mUserPreference.set(loginResponse);
                        moEngageUtills.entityMoEngageLoggedIn(getActivity(), mMoEHelper, payloadBuilder, MoEngageConstants.EMAIL);
                        mLoginActivityIntractionListner.onLoginAuthToken();
                        break;
                    case AppConstants.FAILED:
                        LoginManager.getInstance().logOut();
                        mLoginActivityIntractionListner.onErrorOccurence(loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA));
                        break;
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken()) && null != loginResponse.getUserSummary()) {
                  /*  if (loginResponse.getUserSummary().isFbVerificationRequired()) {
                        mUserPreference.set(loginResponse);
                        setProgressBar(mProgressBar);
                        mLoginActivityIntractionListner.onErrorOccurence(AppConstants.FACEBOOK_VERIFICATION);
                    } else {
                        loginResponse.setTokenTime(System.currentTimeMillis());
                        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                        mUserPreference.set(loginResponse);
                        mLoginActivityIntractionListner.onLoginAuthToken();
                    }*/
                    loginResponse.setTokenTime(System.currentTimeMillis());
                    loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                    loginResponse.setGcmId(mGcmId);
                    moEngageUtills.entityMoEngageUserAttribute(getActivity(), mMoEHelper, payloadBuilder, loginResponse);
                    mUserPreference.set(loginResponse);
                    moEngageUtills.entityMoEngageLoggedIn(getActivity(), mMoEHelper, payloadBuilder, MoEngageConstants.EMAIL);
                    mLoginActivityIntractionListner.onLoginAuthToken();
                } else {
                    LoginManager.getInstance().logOut();
                    mLoginActivityIntractionListner.onErrorOccurence(loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA));
                }
            }
        }
    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoginPresenter.detachView();
    }

    public interface LoginActivityIntractionListner {
        void onErrorOccurence(String errorMessage);

        void onLoginAuthToken();
    }

    @OnClick(R.id.email_sign_in_button)
    public void onLogInBtnClick() {
        sheroesLogIn();
    }

    @OnClick(R.id.forgot_password)
    public void onForgotPasswordClick() {

        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_login, resetPasswordFragment, ResetPasswordFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

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
            if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                loginRequest.setUsername(email);
                loginRequest.setPassword(password);
                loginRequest.setGcmorapnsid(mGcmId);
                mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, false);
            } else {
                if (!NetworkUtil.isConnected(getContext())) {
                    showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                    return;
                } else {
                    getGcmId();
                }
            }
        }
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        mEmailSign.setEnabled(true);
        super.showError(errorMsg, feedParticipationEnum);
    }


    private void getGcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(getActivity(), getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                mGcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                    LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                    loginRequest.setUsername(email);
                    loginRequest.setPassword(password);
                    loginRequest.setGcmorapnsid(mGcmId);
                    mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, false);
                } else {
                    getGcmId();
                }
            }

            @Override
            public void onFailure(String ex) {
                mGcmId = ex;
            }
        });
    }

    @OnClick(R.id.iv_login_back)
    public void backOnClick() {
        ((LoginActivity)getActivity()).onBackPressed();
    }
}