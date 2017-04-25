package appliedlife.pvtltd.SHEROES.views.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.FacebookErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenFirstFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenFourthFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenSecondFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenThirdFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 06/03/17.
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, LoginView {
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    LoginPresenter mLoginPresenter;
    @Bind(R.id.welcome_view_pager)
    ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.iv_welcome_first)
    ImageView ivWelcomeFirst;
    @Bind(R.id.iv_welcome_second)
    ImageView ivWelcomeSecond;
    @Bind(R.id.iv_welcome_third)
    ImageView ivWelcomeThird;
    @Bind(R.id.iv_welcome_fourth)
    ImageView ivWelcomeFourth;
    @Bind(R.id.click_to_join)
    LoginButton mFbLogin;
    @Bind(R.id.tv_join)
    TextView mTvJoin;
    @Bind(R.id.tv_growth_women)
    TextView mTvGrowthWomen;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST1 = 1;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    int currentPage = 0;
    Timer timer;
    int NUM_PAGES = 4;
    private static final String LEFT = "<b><font color='#ffffff'>";
    private static final String RIGHT = "</font></b>";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getToken())) {
            openHomeScreen();
        } else {
            faceBookInitialization();
            setContentView(R.layout.welcome_activity);
            ButterKnife.bind(this);
            initHomeViewPagerAndTabs();
        }

    }

    private void openHomeScreen() {
        Intent homeIntent = new Intent(this, OnBoardingActivity.class);
        startActivity(homeIntent);
        finish();
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

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void initHomeViewPagerAndTabs() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Join").append(LEFT).append(" 1 Million+ Women ").append(RIGHT).append("Just As Awesome As You");
        StringBuilder womenGrowth = new StringBuilder();
        womenGrowth.append("A Growth Network").append(LEFT).append(" Only for Women ").append(RIGHT).append("Because");
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
          //  mTvJoin.setText(Html.fromHtml(stringBuilder.toString(), 0)); // for 24 api and more
            mTvGrowthWomen.setText(Html.fromHtml(womenGrowth.toString(), 0)); // for 24 api and more
        }else {
           // mTvJoin.setText(Html.fromHtml(stringBuilder.toString())); // for 24 api and more
            mTvGrowthWomen.setText(Html.fromHtml(womenGrowth.toString())); // for 24 api and more
        }
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new WelcomeScreenFirstFragment(), AppConstants.EMPTY_STRING);
        mViewPagerAdapter.addFragment(new WelcomeScreenSecondFragment(), AppConstants.EMPTY_STRING);
        mViewPagerAdapter.addFragment(new WelcomeScreenThirdFragment(), AppConstants.EMPTY_STRING);
        mViewPagerAdapter.addFragment(new WelcomeScreenFourthFragment(), AppConstants.EMPTY_STRING);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mLoginPresenter.attachView(this);
        if (Build.VERSION.SDK_INT >= 23) {
            getPermissionToReadUserContacts();
        }
        mLoginPresenter.getMasterDataToPresenter();
        fbSignIn();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
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
                handler.post(Update);
            }
        }, 500, 3000);
    }

    @OnClick(R.id.click_to_join)
    public void fbOnClick() {
        fbSignIn();
    }

    @OnClick(R.id.tv_other_login_option)
    public void otherLoginOption() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
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
                ivWelcomeFourth.setImageResource(R.drawable.ic_circle_w);
                break;
            case AppConstants.ONE_CONSTANT:
                ivWelcomeSecond.setImageResource(R.drawable.ic_circle_red);
                ivWelcomeFirst.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeThird.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeFourth.setImageResource(R.drawable.ic_circle_w);
                break;
            case AppConstants.TWO_CONSTANT:
                ivWelcomeThird.setImageResource(R.drawable.ic_circle_red);
                ivWelcomeFirst.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeSecond.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeFourth.setImageResource(R.drawable.ic_circle_w);
                break;
            case AppConstants.THREE_CONSTANT:
                ivWelcomeFourth.setImageResource(R.drawable.ic_circle_red);
                ivWelcomeFirst.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeSecond.setImageResource(R.drawable.ic_circle_w);
                ivWelcomeThird.setImageResource(R.drawable.ic_circle_w);
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
                                mProgressBar.setVisibility(View.VISIBLE);
                                LoginRequest loginRequest = AppUtils.loginRequestBuilder();
                                loginRequest.setAccessToken(accessToken.getToken());
                                AppUtils appUtils = AppUtils.getInstance();
                                loginRequest.setAppVersion(appUtils.getAppVersionName());
                                //TODO:: NEED to Change
                                loginRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                                loginRequest.setDeviceUniqueId(appUtils.getDeviceId());
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
            userPreference.delete();
        }

        @Override
        public void onError(FacebookException e) {
            userPreference.delete();
            showNetworkTimeoutDoalog(true, false, e.getMessage());
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

    public DialogFragment showFaceBookError(String message) {
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
        return fragment;
    }


    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        mProgressBar.setVisibility(View.GONE);
        switch (loginResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (null != loginResponse && StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                    loginResponse.setTokenTime(System.currentTimeMillis());
                    loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
                    userPreference.set(loginResponse);
                    openHomeScreen();
                } else {
                    userPreference.delete();
                    LoginManager.getInstance().logOut();
                    showFaceBookError(AppConstants.EMPTY_STRING);
                }
                break;
            case AppConstants.INVALID:
                userPreference.delete();
                LoginManager.getInstance().logOut();
                showFaceBookError(loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR));
                break;
            case AppConstants.FAILED:
                userPreference.delete();
                LoginManager.getInstance().logOut();
                showFaceBookError(loginResponse.getFieldErrorMessageMap().get(AppConstants.ERROR));
              /*  LoginManager.getInstance().logOut();
                String errorMessage = loginResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA);
                if (!StringUtil.isNotNullOrEmptyString(errorMessage)) {
                    errorMessage = getString(R.string.ID_GENERIC_ERROR);
                }
                showNetworkTimeoutDoalog(true, false, errorMessage);*/
                break;
        }
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
        onShowErrorDialog(errorMsg, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    private void setCustomAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top_slide_anim);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public void onSuccessResult(String result, FeedDetail feedDetail) {
        if (result.equalsIgnoreCase(AppConstants.SUCCESS)) {
        }
    }
}

