package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
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
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.json.JSONObject;

import java.util.Arrays;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.SignupRequest;
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
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;

/**
 * Created by Deepak on 30-05-2017.
 */

public class SignupFragment extends BaseFragment implements LoginView {

    private final String TAG = LogUtils.makeLogTag(SignupFragment.class);
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.fname_signup)
    EditText mFName;
    @Bind(R.id.lname_signup)
    EditText mLName;
    @Bind(R.id.email_signup)
    EditText mEmailView;
    @Bind(R.id.mobile_signup)
    EditText mobile;
    @Bind(R.id.password_signup)
    EditText mPasswordView;
    @Bind(R.id.signupbtn)
    Button mSignUp;
    @Bind(R.id.click_to_join_fb_signup)
    LoginButton mFbSignUp;
    @Bind(R.id.tv_signup_already_user)
    TextView mTvExistingUser;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    private String mGcmId;
    private String password;
    private String email;
    private String mobileNo;
    private String firstName;
    private String lastName;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private CallbackManager callbackManager;
    private PayloadBuilder payloadBuilder;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        moEngageUtills.entityMoEngageAppOpened(getActivity(), mMoEHelper, payloadBuilder);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        faceBookInitialization();
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mGcmId = getArguments().getString(AppConstants.SHEROES_AUTH_TOKEN);
        }
        mLoginPresenter.attachView(this);
        mEmailView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        mPasswordView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        setProgressBar(mProgressBar);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoginPresenter.detachView();
    }

    @OnClick(R.id.signupbtn)
    public void signup(){
        signupUser();
    }

    private void signupUser(){

        mEmailView.setError(null);
        mPasswordView.setError(null);
        mFName.setError(null);
        mLName.setError(null);
        mobile.setError(null);
        boolean cancel = false;
        View focusView = null;

        email = mEmailView.getText().toString();
        mobileNo = mobile.getText().toString();
        password = mPasswordView.getText().toString();
        firstName = mFName.getText().toString();
        lastName = mLName.getText().toString();

        if (!StringUtil.isNotNullOrEmptyString(password)) {
            mPasswordView.setError(getString(R.string.ID_ERROR_INVALID_PASSWORD));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!mAppUtils.checkMobileNumber(mobileNo)) {
            mobile.setError(getString(R.string.ID_MOBILE_NUMBER));
            focusView = mobile;
            cancel = true;
        }
        if (!mAppUtils.checkEmail(email)) {
            mEmailView.setError(getString(R.string.ID_ERROR_INVALID_EMAIL));
            focusView = mEmailView;
            cancel = true;
        }
        if(StringUtil.isNotNullOrEmptyString(lastName)) {
            lastName= lastName.trim();
        }else{
            mLName.setError(getString(R.string.ID_LAST_NAME));
            focusView = mLName;
            cancel = true;
        }
        if(StringUtil.isNotNullOrEmptyString(firstName)) {
            firstName= firstName.trim();
        }else{
            mFName.setError(getString(R.string.ID_FIRST_NAME));
            focusView = mFName;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }else{
            if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                SignupRequest signupRequest = AppUtils.signupRequestBuilder();
                signupRequest.setEmailId(email);
                signupRequest.setFirstName(firstName);
                signupRequest.setLastName(lastName);
                signupRequest.setMobile(mobileNo);
                signupRequest.setPassword(password);
                signupRequest.setGcmorapnsid(mGcmId);
                mLoginPresenter.getAuthTokenSignupInPresenter(signupRequest);
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

    private void getGcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(getActivity(), AppConstants.PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                LogUtils.info(TAG, "******* ******Registarion" + registrationId);
                mGcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                    mSignUp.setEnabled(true);
                    mFbSignUp.setEnabled(true);
                } else {
                    mSignUp.setEnabled(false);
                    mFbSignUp.setEnabled(false);
                    if (!NetworkUtil.isConnected(getContext())) {
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

    private void faceBookInitialization() {
        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                // displayMessage(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        mSignUp.setEnabled(true);
        mFbSignUp.setEnabled(true);
        if(loginResponse != null){
            switch (loginResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                        loginResponse.setTokenTime(System.currentTimeMillis());
                        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                        loginResponse.setGcmId(mGcmId);
                        moEngageUtills.entityMoEngageUserAttribute(getActivity(), mMoEHelper, payloadBuilder, loginResponse);
                        mUserPreference.set(loginResponse);
                        if (null != loginResponse.getUserSummary() && null != loginResponse.getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getCrdt())) {
                            long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());
                            if (createdDate < System.currentTimeMillis()) {
                                moEngageUtills.entityMoEngageLoggedIn(getActivity(), mMoEHelper, payloadBuilder, MoEngageConstants.FACEBOOK);
                            } else {
                                moEngageUtills.entityMoEngageSignUp(getActivity(), mMoEHelper, payloadBuilder, MoEngageConstants.FACEBOOK);
                            }
                        }
                        mMoEHelper.setUserAttribute(MoEngageConstants.ACQUISITION_CHANNEL, MoEngageConstants.FACEBOOK);
                        openHomeScreen();
                    } else {
                        mUserPreference.delete();
                        LoginManager.getInstance().logOut();
                        ((WelcomeActivity) getActivity()).showFaceBookError(AppConstants.EMPTY_STRING);
                    }
                    break;
                case AppConstants.INVALID:
                    mUserPreference.delete();
                    break;
                case AppConstants.FAILED:
                    mUserPreference.delete();
                    String errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA);
                    if (errorMessage.equalsIgnoreCase(AppConstants.USER_ALREADY_EXIST_ERROR)) {
                        ((WelcomeActivity) getActivity()).showNetworkTimeoutDoalog(true, false, getString(R.string.ID_STR_USER_ALREADY_EXIST_ERROR));
                    } else {
                        LoginManager.getInstance().logOut();
                        ((WelcomeActivity) getActivity()).showFaceBookError(loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR));
                    }
              /*  LoginManager.getInstance().logOut();
                String errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA);
                if (!StringUtil.isNotNullOrEmptyString(errorMessage)) {
                    errorMessage = getString(R.string.ID_GENERIC_ERROR);
                }
                showNetworkTimeoutDoalog(true, false, errorMessage);*/
                    break;
            }
        }else{
            ((WelcomeActivity) getActivity()).showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

    @OnClick(R.id.click_to_join_fb_signup)
    public void fbOnClick() {
        if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
            fbSignIn();
        } else {
            mFbSignUp.setEnabled(false);
            mSignUp.setEnabled(false);
            if (!NetworkUtil.isConnected(getContext())) {
                showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                return;
            } else {
                getGcmId();
            }
        }
    }

    private void fbSignIn() {
        LoginManager.getInstance().logOut();
        mFbSignUp.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        mFbSignUp.registerCallback(callbackManager, callback);
    }

    private void openHomeScreen() {
        Intent boardingIntent = new Intent(getActivity(), OnBoardingActivity.class);
        boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(boardingIntent);
        getActivity().finish();
    }

    public FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(final LoginResult loginResult) {
            final AccessToken accessToken = loginResult.getAccessToken();
            // Facebook Email address
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            if (null != accessToken && StringUtil.isNotNullOrEmptyString(accessToken.getToken())) {
                                mProgressBar.setVisibility(View.VISIBLE);
                                LoginRequest loginRequest = loginRequestBuilder();
                                loginRequest.setAccessToken(accessToken.getToken());
                                AppUtils appUtils = AppUtils.getInstance();
                                loginRequest.setAppVersion(appUtils.getAppVersionName());
                                //TODO:: NEED to Change
                                loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                                loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
                                loginRequest.setGcmorapnsid(mGcmId);
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
            }
        }

        @Override
        public void onCancel() {
            mUserPreference.delete();
        }

        @Override
        public void onError(FacebookException e) {
            mUserPreference.delete();
            ((WelcomeActivity)getActivity()).showNetworkTimeoutDoalog(true, false, e.getMessage());
        }
    };

    @OnClick(R.id.tv_signup_already_user)
    public void signInExistingUser(){
        if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
            Intent loginIntent = new Intent(getContext(), LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.SHEROES_AUTH_TOKEN, mGcmId);
            loginIntent.putExtras(bundle);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(loginIntent);
            getActivity().finish();
        } else {
            if (!NetworkUtil.isConnected(getContext())) {
                showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
                return;
            } else {
                mSignUp.setEnabled(false);
                mFbSignUp.setEnabled(false);
                getGcmId();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}