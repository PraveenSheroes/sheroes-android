package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

import org.json.JSONObject;

import java.util.Arrays;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Praveen Singh on 04/01/2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 04/01/2017.
 * Title: A login screen that offers login via email/password.
 */

public class LoginFragment extends BaseFragment implements LoginView {
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
    @Bind(R.id.login_button)
    LoginButton mFbLogin;
    @Bind(R.id.email_sign_in_button)
    Button mEmailSign;
    private LoginActivityIntractionListner mLoginActivityIntractionListner;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST1 = 1;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private MoEHelper mMoEHelper;
    private String mGcmId;
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
        mMoEHelper = MoEHelper.getInstance(getApplicationContext());
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        if(null!=getArguments()) {
            mGcmId = getArguments().getString(AppConstants.SHEROES_AUTH_TOKEN);
        }
        mLoginPresenter.attachView(this);
        mEmailView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        mPasswordView.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        setProgressBar(mProgressBar);
        if (Build.VERSION.SDK_INT >= 23) {
            getPermissionToReadUserContacts();

        }
        //  mLoginPresenter.getMasterDataToPresenter();
        return view;
    }


    @OnClick(R.id.login_button)
    public void fbOnClick() {
        fbSignIn();
    }

    private void fbSignIn() {
        LoginManager.getInstance().logOut();
        mFbLogin.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        mFbLogin.setFragment(this);
        mFbLogin.registerCallback(callbackManager, callback);
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
                        setUserAttributeOnMoEngage(loginResponse);
                        mUserPreference.set(loginResponse);
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
                    setUserAttributeOnMoEngage(loginResponse);
                    mUserPreference.set(loginResponse);
                    mLoginActivityIntractionListner.onLoginAuthToken();
                } else {
                    LoginManager.getInstance().logOut();
                    mLoginActivityIntractionListner.onErrorOccurence(loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA));
                }
            }
        }
    }

    @Override
    public void getGcmResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    private void setUserAttributeOnMoEngage(LoginResponse loginResponse) {
        if (null != loginResponse.getUserSummary() && loginResponse.getUserSummary().getUserId() > 0) {
            mMoEHelper.setUniqueId(loginResponse.getUserSummary().getUserId());
            // If you have first and last name separately
            if (null != loginResponse.getUserSummary().getUserBO()) {
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getFirstName())) {
                    mMoEHelper.setFirstName(loginResponse.getUserSummary().getUserBO().getFirstName());
                }
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getLastName())) {
                    mMoEHelper.setLastName(loginResponse.getUserSummary().getUserBO().getLastName());
                }
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getDob())) {
                    mMoEHelper.setBirthDate(loginResponse.getUserSummary().getUserBO().getDob());
                }
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getEmailid())) {
                    mMoEHelper.setEmail(loginResponse.getUserSummary().getUserBO().getEmailid());
                }
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getGender())) {
                    mMoEHelper.setGender(loginResponse.getUserSummary().getUserBO().getGender());
                }
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getMobile())) {
                    mMoEHelper.setNumber(loginResponse.getUserSummary().getUserBO().getMobile());
                }
            }
        }
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
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

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
            LoginRequest loginRequest = AppUtils.loginRequestBuilder();
            loginRequest.setUsername(email);
            loginRequest.setPassword(password);
            loginRequest.setGcmorapnsid(mGcmId);
            mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, false);
        }
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        mEmailSign.setEnabled(true);
        super.showError(errorMsg, feedParticipationEnum);
    }

    // Class for facebook access token and user details
    public FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(final LoginResult loginResult) {
            final AccessToken accessToken = loginResult.getAccessToken();
            // Facebook Email address
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    if (null != accessToken && StringUtil.isNotNullOrEmptyString(accessToken.getToken())) {
                        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
                            LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                            loginRequest.setAccessToken(accessToken.getToken());
                            AppUtils appUtils = AppUtils.getInstance();
                            loginRequest.setAppVersion(appUtils.getAppVersionName());
                            //TODO:: Google gcm ID
                            loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                            loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
                            loginRequest.setGcmorapnsid(mGcmId);
                            mLoginPresenter.getFBVerificationInPresenter(loginRequest);
                        } else {
                            LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                            loginRequest.setAccessToken(accessToken.getToken());
                            AppUtils appUtils = AppUtils.getInstance();
                            loginRequest.setAppVersion(appUtils.getAppVersionName());
                            //TODO:check cloud
                            loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                            loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
                            loginRequest.setGcmorapnsid(mGcmId);
                            mLoginPresenter.getLoginAuthTokeInPresenter(loginRequest, true);
                        }
                    }

                }
            });
            try

            {
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,last_name,first_name");
                request.setParameters(parameters);
                request.executeAsync();
            } catch (
                    Exception e)

            {
            }
        }

        @Override
        public void onCancel() {

            mLoginActivityIntractionListner.onErrorOccurence(getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));

        }

        @Override
        public void onError(FacebookException e) {
            mLoginActivityIntractionListner.onErrorOccurence(getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
        }
    };


    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_CONTACTS)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_PERMISSIONS_REQUEST1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


}