package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.android.gms.plus.Plus;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.SignupRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
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

public class SignupFragment extends BaseFragment implements LoginView, SocialListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String SCREEN_LABEL = "Login Screen";
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
    @Bind(R.id.btn_sign_up)
    Button mSignUp;
    @Bind(R.id.click_to_join_fb_signup)
    LoginButton mFbSignUp;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.btn_login_google)
    Button btnLoginGoogle;
    @Bind(R.id.li_login_via_fb_google)
    LinearLayout liLoginViaFbGoogle;
    @Bind(R.id.rl_sign_up_form)
    RelativeLayout rlSignUpForm;
    private String mGcmId;
    private String password;
    private String email;
    private String mobileNo;
    private String firstName;
    private String lastName;
    private int mSignUpVia;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private CallbackManager callbackManager;
    private PayloadBuilder payloadBuilder;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private String loggedInChannel;
    private String loggedInImageUrl;
    private String socialLoginFirstName;
    private String socialLoginLastName;
    private GooglePlusHelper mGooglePlusHelper;
    private GoogleSignInOptions gso;
    private Dialog dialog;
    //google api client
    public static GoogleApiClient mGoogleApiClient;
    private String mToken = null;
    private String loginViaSocial = MoEngageConstants.GOOGLE;
    private  long currentTime;
    @Bind(R.id.cb_female)
    CheckBox mCbFemale;
    @Bind(R.id.cb_sign_up_female)
    CheckBox mCbSignUpFemale;
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
        faceBookInitialization();
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mGcmId = getArguments().getString(AppConstants.GCM_ID);
        }
        mLoginPresenter.attachView(this);
        liLoginViaFbGoogle.setVisibility(View.VISIBLE);
        rlSignUpForm.setVisibility(View.GONE);
        currentTime=System.currentTimeMillis();
        // initGoogleLogin();
        googlePlusLogin();
        mEmailView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        mPasswordView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        setProgressBar(mProgressBar);
        mFbSignUp.setFragment(this);
        // setGooglePlusButtonText(btnLoginGoogle, getString(R.string.IDS_GOOGLE_BUTTON));
        mFbSignUp.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        fbSignIn();
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_SIGN_UP_FORM));
        return view;
    }

    private void initGoogleLogin() {
        mGooglePlusHelper = GooglePlusHelper.getInstance();
        mGooglePlusHelper.initializeGoogleAPIClient(getContext());

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
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .addApi(Plus.API)
                    .build();
        }

    }

    private void signIn() {
        //Creating an intent
        signOut();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //Starting intent for result
        showDialog(CustomSocialDialog.LOGGING_IN_DIALOG);
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
    private void showDialog(int id) {
        dialog = createCustomDialog(id);
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * Creates and returns dialog
     *
     * @param id id of dialog
     * @return dialog
     */
    private Dialog createCustomDialog(int id) {
        Dialog dialog = null;
        try {
            CustomSocialDialog dialogCreater = null;
            switch (id) {
                case CustomSocialDialog.LOGGING_IN_DIALOG: {
                    dialogCreater = new CustomSocialDialog(getContext(), id);
                    dialog = dialogCreater.createCustomDialog();
                    break;
                }
                default:
                    break;
            }
            return dialog;
        } catch (Exception e) {
            LogUtils.error(TAG, e);
            return null;
        }
    }

    @OnClick(R.id.btn_login_google)
    public void googleLoginClick() {
        if(mCbFemale.isChecked()) {
            launchGooglePlusLogin();
        }else {
            ((WelcomeActivity) getActivity()).showFaceBookError(getString(R.string.ID_WOMEN_ERROR));
        }

    }

    public void launchGooglePlusLogin() {
        if (AppUtils.getInstance().isNetworkAvailable()) {
          /*  if (mGooglePlusHelper != null) {
                mGooglePlusHelper.signIn(this);
            }*/
            signIn();
        } else {
            Toast.makeText(getActivity(), getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGooglePlusHelper != null) {
            mGooglePlusHelper.signOut();
        }
        mLoginPresenter.detachView();
    }

    @OnClick(R.id.btn_sign_up)
    public void signup() {
        signupUser();
    }

    private void signupUser() {

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
        if (StringUtil.isNotNullOrEmptyString(lastName)) {
            lastName = lastName.trim();
        } else {
            mLName.setError(getString(R.string.ID_LAST_NAME));
            focusView = mLName;
            cancel = true;
        }
        if (StringUtil.isNotNullOrEmptyString(firstName)) {
            firstName = firstName.trim();
        } else {
            mFName.setError(getString(R.string.ID_FIRST_NAME));
            focusView = mFName;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else if (password.length() < 6) {
            focusView = mPasswordView;
            mPasswordView.setError(getString(R.string.ID_PASSWORD_STRENTH));
            focusView.requestFocus();
        } else {
            if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                if(mCbSignUpFemale.isChecked()) {
                    SignupRequest signupRequest = AppUtils.signupRequestBuilder();
                    signupRequest.setEmailId(email);
                    signupRequest.setFirstName(firstName);
                    signupRequest.setLastName(lastName);
                    signupRequest.setMobile(mobileNo);
                    signupRequest.setPassword(password);
                    signupRequest.setGcmorapnsid(mGcmId);
                    mSignUpVia = AppConstants.ONE_CONSTANT;
                    mLoginPresenter.getAuthTokenSignupInPresenter(signupRequest);
                }else {
                    ((WelcomeActivity) getActivity()).showFaceBookError(getString(R.string.ID_WOMEN_ERROR));
                }
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
        GCMClientManager pushClientManager = new GCMClientManager(getActivity(), getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                LogUtils.info(TAG, "******* ******Registarion" + registrationId);
                mGcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                    PushManager.getInstance().refreshToken(getActivity(), mGcmId);
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
        LoginManager.getInstance().logOut();
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        mSignUp.setEnabled(true);
        mFbSignUp.setEnabled(true);
        dismissDialog();
        if (loginResponse != null) {
            switch (loginResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                        loginResponse.setTokenTime(System.currentTimeMillis());
                        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                        loginResponse.setGcmId(mGcmId);
                        mUserPreference.set(loginResponse);
                        AnalyticsManager.initializeMixpanel(getActivity());
                        moEngageUtills.entityMoEngageUserAttribute(getActivity(), mMoEHelper, payloadBuilder, loginResponse);
                        if (StringUtil.isNotNullOrEmptyString(mobileNo)) {
                            mMoEHelper.setNumber(mobileNo);

                        }
                        if (mSignUpVia == AppConstants.ONE_CONSTANT) {
                            ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SIGN_UP, GoogleAnalyticsEventActions.SIGN_UP_WITH_EMAIL, AppConstants.EMPTY_STRING);
                            final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(true).authProvider("Email").build();
                            trackEvent(Event.APP_LOGIN, properties);
                            AnalyticsManager.initializeMixpanel(getActivity());
                        }else
                        {
                            if (null != loginResponse.getUserSummary() && null != loginResponse.getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getCrdt())) {
                                long createdDate = Long.parseLong(loginResponse.getUserSummary().getUserBO().getCrdt());

                                final HashMap<String, Object> properties = new EventProperty.Builder().isNewUser(currentTime<createdDate).authProvider(loginViaSocial==MoEngageConstants.FACEBOOK ? "Facebook" : "Google").build();
                                trackEvent(Event.APP_LOGIN, properties);
                                if (createdDate<currentTime) {
                                    moEngageUtills.entityMoEngageLoggedIn(getActivity(), mMoEHelper, payloadBuilder, loginViaSocial);
                                    if(loginViaSocial==MoEngageConstants.FACEBOOK)
                                    {
                                        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_WITH_FACEBOOK, AppConstants.EMPTY_STRING);
                                    }else
                                    {
                                        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOGINS, GoogleAnalyticsEventActions.LOGGED_IN_WITH_GOOGLE, AppConstants.EMPTY_STRING);
                                    }

                                } else {
                                    moEngageUtills.entityMoEngageSignUp(getActivity(), mMoEHelper, payloadBuilder, loginViaSocial);
                                    if(loginViaSocial==MoEngageConstants.FACEBOOK)
                                    {
                                        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SIGN_UP, GoogleAnalyticsEventActions.SIGN_UP_WITH_FACEBOOK, AppConstants.EMPTY_STRING);
                                    }else
                                    {
                                        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SIGN_UP, GoogleAnalyticsEventActions.SIGN_UP_WITH_GOOGLE, AppConstants.EMPTY_STRING);
                                    }
                                }
                                ((SheroesApplication) getActivity().getApplication()).trackUserId(String.valueOf(loginResponse.getUserSummary().getUserId()));
                            }
                            mMoEHelper.setUserAttribute(MoEngageConstants.ACQUISITION_CHANNEL, loginViaSocial);
                        }
                        mUserPreference.set(loginResponse);
                        openHomeScreen();

                    } else {
                        mUserPreference.delete();
                        LoginManager.getInstance().logOut();
                        signOut();
                        ((WelcomeActivity) getActivity()).showFaceBookError(AppConstants.EMPTY_STRING);
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
                        ((WelcomeActivity) getActivity()).showFaceBookError(errorMessage);
                    } else {
                        errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR);
                        ((WelcomeActivity) getActivity()).showFaceBookError(errorMessage);
                    }
                    break;
            }
        } else {
            ((WelcomeActivity) getActivity()).showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {
       /* if (null != expireInResponse.getGooglePlusResponse()) {
            if (expireInResponse.getGooglePlusResponse().getStatus() && StringUtil.isNotNullOrEmptyString(expireInResponse.getGooglePlusResponse().getMessage()) && AppConstants.SUCCESS.equalsIgnoreCase(expireInResponse.getGooglePlusResponse().getMessage()) && StringUtil.isNotNullOrEmptyString(expireInResponse.getGooglePlusResponse().getGooglePlusLogInAuthToken())) {
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(expireInResponse.getGooglePlusResponse().getGooglePlusLogInAuthToken());
                mUserPreference.set(loginResponse);
                loginViaSocial = MoEngageConstants.GOOGLE;
                mLoginPresenter.getGooglePlusUserResponse();
            }
        } else {
            if (expireInResponse.getExpiresIn() > 0 && StringUtil.isNotNullOrEmptyString(mToken)) {
                User user = new User();
                user.setExpiresIn(String.valueOf(expireInResponse.getExpiresIn()));
                String created = String.valueOf(System.currentTimeMillis());
                user.setCreated(created);
                user.setGpAccessToken(mToken);
                GooglePlusRequest googlePlusRequest = mAppUtils.googlePlusRequestBuilder(user);
                mLoginPresenter.getGoogleLoginFromPresenter(googlePlusRequest);
            }
        }*/

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

   /* @OnClick(R.id.click_to_join_fb_signup)
    public void fbOnClick() {
        if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
            if(mCbFemale.isChecked()) {
               // fbSignIn();
            }else {
                ((WelcomeActivity) getActivity()).showFaceBookError(getString(R.string.ID_WOMEN_ERROR));
            }
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
*/
    private void fbSignIn() {
        LoginManager.getInstance().logOut();
        mFbSignUp.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        mFbSignUp.registerCallback(callbackManager, callback);
    }

    private void openHomeScreen() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getNextScreen()) && mUserPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION) && mUserPreference.get().isSheUser()) {
            Intent loginIntent = new Intent(getContext(), LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.SHEROES_AUTH_TOKEN, mGcmId);
            loginIntent.putExtras(bundle);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(loginIntent);
            getActivity().finish();
        } else {
            Intent boardingIntent = new Intent(getActivity(), OnBoardingActivity.class);
            boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(boardingIntent);
            getActivity().finish();
        }
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
            }
        }

        @Override
        public void onCancel() {
            mUserPreference.delete();
        }

        @Override
        public void onError(FacebookException e) {
            mUserPreference.delete();
            ((WelcomeActivity) getActivity()).showNetworkTimeoutDoalog(true, false, e.getMessage());
        }
    };

    @OnClick(R.id.tv_signup_already_user)
    public void signInExistingUser() {
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppConstants.REQUEST_CODE_FOR_GOOGLE_PLUS:
                if (resultCode == Activity.RESULT_OK) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
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
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
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

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            final String SCOPES = "oauth2:profile email";

            try {
                mToken = GoogleAuthUtil.getToken(getContext(), accountName, SCOPES);
            } catch (IOException e) {
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
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mGoogleApiClient) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void userLoggedIn(SocialPerson person) {
        if (person == null) {
            if (mGooglePlusHelper != null) {
                mGooglePlusHelper.dismissDialog();
            }
            getActivity().runOnUiThread(new Runnable() {
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
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AppUtils.getInstance().getApplicationContext(), getString(R.string.ID_GENERIC_ERROR), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return;
        }
        if (SocialPerson.LOGIN_TYPE_GOOGLE.equalsIgnoreCase(person.getLoginType())) {
            loggedInChannel = AppConstants.GOOGLE_PLUS;
        }
        loggedInImageUrl = person.getImageUrl();
        socialLoginFirstName = person.getFirstname();
        socialLoginLastName = person.getLastname();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Connection failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        mSignUp.setEnabled(true);
        mFbSignUp.setEnabled(true);
        mUserPreference.delete();
        super.showError(errorMsg, feedParticipationEnum);
    }
    @OnClick(R.id.cb_female)
    public void OnCheckFemaleByClick() {
        if(mCbFemale.isChecked())
        {
            mCbFemale.setChecked(true);
        }else {
            mCbFemale.setChecked(false);
        }
    }
    @OnClick(R.id.cb_sign_up_female)
    public void OnCheckFemaleSignUpClick() {
        if(mCbSignUpFemale.isChecked())
        {
            mCbSignUpFemale.setChecked(true);
        }else {
            mCbSignUpFemale.setChecked(false);
        }
    }

    @OnClick(R.id.tv_continue_sign_up)
    public void OnSignUpClick() {
        WelcomeActivity.isSignUpOpen=AppConstants.ONE_CONSTANT;
       liLoginViaFbGoogle.setVisibility(View.GONE);
        rlSignUpForm.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.tv_sign_up_back)
    public void OnSignUpBackClick() {
        AppUtils.hideKeyboard(mobile, TAG);
        WelcomeActivity.isSignUpOpen=AppConstants.NO_REACTION_CONSTANT;
        liLoginViaFbGoogle.setVisibility(View.VISIBLE);
        rlSignUpForm.setVisibility(View.GONE);
    }


}