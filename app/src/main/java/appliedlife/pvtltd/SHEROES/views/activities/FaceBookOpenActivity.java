package appliedlife.pvtltd.SHEROES.views.activities;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.FacebookErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-04-2017.
 */

public class FaceBookOpenActivity extends BaseActivity implements LoginView {
    private final String TAG = LogUtils.makeLogTag(FaceBookOpenActivity.class);
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    LoginPresenter mLoginPresenter;
    @Bind(R.id.verify_fb_button)
    LoginButton mFbLogin;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST1 = 1;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        faceBookInitialization();
        setContentView(R.layout.facebook_verification);
        ButterKnife.bind(this);
        initHomeViewPagerAndTabs();
        ((SheroesApplication)this.getApplication()).trackScreenView(getString(R.string.ID_FACEBOOK_SCREEN));
    }

    private void faceBookInitialization() {
        FacebookSdk.sdkInitialize(getApplicationContext());
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

    private void initHomeViewPagerAndTabs() {
        mLoginPresenter.attachView(this);
        if (Build.VERSION.SDK_INT >= 23) {
            getPermissionToReadUserContacts();
        }
        fbSignIn();
    }

    @OnClick(R.id.verify_fb_button)
    public void fbOnClick() {
        fbSignIn();
    }

    public void backToLogInActivity() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.HOME_FRAGMENT, AppConstants.TWO_CONSTANT);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void backToWelcome() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.HOME_FRAGMENT, AppConstants.ONE_CONSTANT);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void fbSignIn() {
        LoginManager.getInstance().logOut();
        mFbLogin.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        mFbLogin.registerCallback(callbackManager, callback);
    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        LoginManager.getInstance().logOut();
        switch (errorReason) {
            case AppConstants.CHECK_NETWORK_CONNECTION:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                break;
            case AppConstants.HTTP_401_UNAUTHORIZED:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_INVALID_USER_PASSWORD));
                break;
            default:
                showFaceBookError(AppConstants.EMPTY_STRING);
        }
    }


    // Class for facebook access token and user details
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
                                LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                                loginRequest.setAccessToken(accessToken.getToken());
                                AppUtils appUtils = AppUtils.getInstance();
                                loginRequest.setAppVersion(appUtils.getAppVersionName());
                                //TODO:: Google gcm ID
                                loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                                loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
                                mLoginPresenter.getFBVerificationInPresenter(loginRequest);
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
            userPreference.delete();
        }

        @Override
        public void onError(FacebookException e) {
            userPreference.delete();
            showNetworkTimeoutDoalog(true, false,e.getMessage());
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSIONS_REQUEST1);
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


    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        if (null != loginResponse) {
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getStatus())) {
                switch (loginResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        backToLogInActivity();
                        break;
                    case AppConstants.INVALID:
                        mUserPreference.delete();
                        LoginManager.getInstance().logOut();
                        showFaceBookError(loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR));
                        break;
                    default:
                        mUserPreference.delete();
                        LoginManager.getInstance().logOut();
                }
            } else {
                mUserPreference.delete();
                LoginManager.getInstance().logOut();
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
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        switch (errorMsg) {
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

    public DialogFragment showFaceBookError(String message) {
        FacebookErrorDialog fragment = (FacebookErrorDialog) getFragmentManager().findFragmentByTag(FacebookErrorDialog.class.getName());
        if (fragment == null) {
            fragment = new FacebookErrorDialog();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.SHEROES_AUTH_TOKEN, message);
            fragment.setArguments(bundle);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), FacebookErrorDialog.class.getName());
        }
        return fragment;
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void sendVerificationEmailSuccess(EmailVerificationResponse emailVerificationResponse){

    }
}

