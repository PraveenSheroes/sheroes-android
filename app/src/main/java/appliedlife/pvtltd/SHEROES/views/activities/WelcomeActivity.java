package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserFromReferralRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkTimeoutDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.SignupFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenFirstFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenFourthFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenSecondFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenThirdFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.FacebookErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;

/**
 * Created by sheroes on 06/03/17.
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, LoginView {
    private static final String SCREEN_LABEL = "Intro Screen";
    private final String TAG = LogUtils.makeLogTag(WelcomeActivity.class);
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;
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
    @Bind(R.id.tv_join)
    TextView mTvJoin;
    @Bind(R.id.tv_growth_women)
    TextView mTvGrowthWomen;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_other_login_option)
    TextView mOtherLoginOption;
    @Bind(R.id.btn_get_started)
    Button mGetStarted;
    @Bind(R.id.scroll_view_welcome)
    ScrollView mScrollView;
    private PayloadBuilder payloadBuilder;
    private int currentPage = 0;
    private Timer timer;
    private int NUM_PAGES = 4;
    private static final String LEFT = "<b><font color='#ffffff'>";
    private static final String RIGHT = "</font></b>";
    private String mGcmId;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private FragmentOpen mFragmentOpen;
    @Inject
    AppUtils appUtils;
    private ProgressDialog mProgressDialog;
    public static int isSignUpOpen = AppConstants.NO_REACTION_CONSTANT;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        AppsFlyerLib.getInstance().startTracking(getApplication(), getString(R.string.ID_APPS_FLYER_DEV_ID));
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
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getToken())) {
            openHomeScreen();
        } else {
            setContentView(R.layout.welcome_activity);
            ButterKnife.bind(this);
            initHomeViewPagerAndTabs();
            if (!NetworkUtil.isConnected(mSheroesApplication)) {
                showNetworkTimeoutDoalog(false, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                return;
            } else {
            mLoginPresenter.getMasterDataToPresenter();
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.ID_PLAY_STORE_DATA));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            mGetStarted.setEnabled(false);
            mOtherLoginOption.setEnabled(false);
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.fullScroll(mScrollView.FOCUS_DOWN);
                }
            });
            mScrollView.scrollTo(0, mScrollView.getBottom() + 1);

                if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getGcmId())) {
                    mGcmId = userPreference.get().getGcmId();
                    mOtherLoginOption.setEnabled(true);
                } else {
                    getGcmId();
                }
            }
            ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_INTRO_SCREEN));
        }

    }

    private void getGcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(WelcomeActivity.this, getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                mGcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
                    LogUtils.info(TAG, "******* ******success Registarion" + registrationId);
                    PushManager.getInstance().refreshToken(getApplicationContext(), mGcmId);
                    mGetStarted.setEnabled(true);
                    mOtherLoginOption.setEnabled(true);
                    if (null != mProgressDialog && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } else {
                    mGetStarted.setEnabled(false);
                    mOtherLoginOption.setEnabled(false);
                    if (!NetworkUtil.isConnected(mSheroesApplication)) {
                        showNetworkTimeoutDoalog(false, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                        return;
                    } else {
                        getGcmId();
                    }
                }
            }

            @Override
            public void onFailure(String ex) {
                getGcmId();
            }
        });
    }

    private void openHomeScreen() {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getNextScreen()) && userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION) && userPreference.get().isSheUser()) {
            openLoginActivity();
        } else {
            Intent boardingIntent = new Intent(this, OnBoardingActivity.class);
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
            //  mTvJoin.setText(Html.fromHtml(stringBuilder.toString(), 0)); // for 24 api and more
            mTvGrowthWomen.setText(Html.fromHtml(womenGrowth.toString(), 0)); // for 24 api and more
        } else {
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

        //fbSignIn();
        mHandler= new Handler();
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
        }, 500, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @OnClick(R.id.btn_get_started)
    public void getStartedOnClick() {
        if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
            SignupFragment signupFragment = new SignupFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.GCM_ID, mGcmId);
            signupFragment.setArguments(bundle);
            mFragmentOpen.setSignupFragment(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_welcome_sign_up, signupFragment, SignupFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

        } else {
            mGetStarted.setEnabled(false);
            mOtherLoginOption.setEnabled(false);
            if (!NetworkUtil.isConnected(mSheroesApplication)) {
                showNetworkTimeoutDoalog(false, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                return;
            } else {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.ID_PLAY_STORE_DATA));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                getGcmId();
            }
}
           }

    @OnClick(R.id.tv_other_login_option)
    public void otherLoginOption() {
        if (StringUtil.isNotNullOrEmptyString(mGcmId)) {
            openLoginActivity();
        } else {
            mGetStarted.setEnabled(false);
            mOtherLoginOption.setEnabled(false);
            if (!NetworkUtil.isConnected(mSheroesApplication)) {
                showNetworkTimeoutDoalog(false, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                return;
            } else {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.ID_PLAY_STORE_DATA));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                getGcmId();
            }
        }
    }

    private void openLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SHEROES_AUTH_TOKEN, mGcmId);
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
    public void onBackPressed() {
        if (isSignUpOpen == AppConstants.ONE_CONSTANT) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SignupFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((SignupFragment) fragment).OnSignUpBackClick();
            }else
            {
                finish();
            }
        } else {
            if (mFragmentOpen.isSignupFragment()) {
                mFragmentOpen.setSignupFragment(false);
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }

    }

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
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

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

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
    protected void onResume() {
        super.onResume();
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
}

