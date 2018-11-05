package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsflyer.AppsFlyerLib;
import com.f2prateek.rx.preferences2.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.CleverTapHelper;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.SuperProperty;
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
import appliedlife.pvtltd.SHEROES.models.entities.login.AppStatus;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LanguageType;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MaleErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.LANGUAGE_KEY;

public class LanguageSelectionActivity extends BaseActivity implements LoginView {
    private static final String SCREEN_LABEL = "Select Language Screen";
    private static final String BRANCH_DEEP_LINK = "deep_link_url";
    private static final String BRANCH_REFERRER_LINK = "~referring_link";
    //region Inject variables
    @Inject
    AppUtils appUtils;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<AppStatus> mInstallUpdatePreference;
    @Inject
    Preference<AppInstallation> mAppInstallation;
    @Inject
    LoginPresenter mLoginPresenter;
    //endregion

    //region View variables
    @Bind(R.id.tv_continue)
    TextView tvContinue;
    @Bind(R.id.iv_lang_hind)
    ImageView ivLangHind;
    @Bind(R.id.iv_lang_eng)
    ImageView ivLangEng;
    @Bind(R.id.fl_hindi)
    FrameLayout flHindi;
    @Bind(R.id.fl_english)
    FrameLayout flEnglish;
    //endregion

    //region member variables
    private boolean isBranchFirstSession = false;
    private boolean isFirstTimeUser = false;
    private String deepLinkUrl = null;
    private String defaultTab = null;
    private boolean isLanguageSelected;
    //endregion

    //region overridden variables
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        AppsFlyerLib.getInstance().setAndroidIdData(appUtils.getDeviceId());
        if (CommonUtil.getPrefValue(AppConstants.MALE_ERROR_SHARE_PREF)) {
            showMaleError("");
        } else {
            checkAuthTokenExpireOrNot();
        }
        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(SheroesApplication.mContext);
        appInstallationHelper.setupAndSaveInstallation(false);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
    }

    private void checkAuthTokenExpireOrNot() {
        if (null != mUserPreference && mUserPreference.isSet()) {
            if (StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
                long daysDifference = System.currentTimeMillis() - mUserPreference.get().getTokenTime();
                if (daysDifference >= AppConstants.SAVED_DAYS_TIME) {
                    mLoginPresenter.getAuthTokenRefreshPresenter();
                } else {
                    initializeAllDataAfterGCMId();
                }
            } else {
                initializeAllDataAfterGCMId();
            }
        } else {
            initializeAllDataAfterGCMId();
        }
    }

    private void setNewLocale(String language) {
        LocaleManager.setNewLocale(this, language);
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
        if (loginResponse != null) {
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
                refreshAuthTokenResponse(loginResponse);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
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

    private void initializeAllDataAfterGCMId() {
        int versionCode = BuildConfig.VERSION_CODE;
        if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet()) {
            if (mInstallUpdatePreference.get().getAppVersion() < versionCode) {
                AppStatus appStatus = mInstallUpdatePreference.get();
                appStatus.setFirstOpen(true);
                appStatus.setAppVersion(versionCode);
                appStatus.setWelcome(true);
                appStatus.setAppInstallFirstTime(true);
                mInstallUpdatePreference.set(appStatus);
            }
            if (mInstallUpdatePreference.get().isAppInstallFirstTime()) {
                AppStatus appStatus = mInstallUpdatePreference.get();
                appStatus.setWalkThroughShown(true);
                mInstallUpdatePreference.set(appStatus);
            }
        } else {
            AppStatus appStatus = new AppStatus();
            appStatus.setAppVersion(versionCode);
            appStatus.setFirstOpen(true);
            appStatus.setAppInstallFirstTime(false);
            appStatus.setWalkThroughShown(false);
            mInstallUpdatePreference.set(appStatus);
            isFirstTimeUser = true;
        }

        if (null != mUserPreference && mUserPreference.isSet() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
            isFirstTimeUser = false;
            DrawerViewHolder.selectedOptionName = null;
            openHomeScreen();
        } else { //Get Branch Details for First Install
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.getBoolean(AppConstants.HIDE_SPLASH_THEME)) {
                setUpView();
            } else {
                final Branch branch = Branch.getInstance();
                branch.resetUserSession();
                if (CleverTapHelper.getCleverTapInstance(getApplicationContext()) != null) {
                    branch.setRequestMetadata(CleverTapHelper.CLEVERTAP_ATTRIBUTION_ID,
                            CleverTapHelper.getCleverTapInstance(getApplicationContext()).getCleverTapAttributionIdentifier());
                }
                JSONObject sessionParams = branch.getLatestReferringParams();
                if (sessionParams == null) {
                    branch.initSession(new Branch.BranchReferralInitListener() {
                        @Override
                        public void onInitFinished(JSONObject sessionParams, BranchError error) {
                            branchSessionData(sessionParams);
                        }
                    });
                } else {
                    branchSessionData(sessionParams);
                }
                setUpView();
            }
        }
    }

    private void branchSessionData(JSONObject sessionParams) {
        isBranchFirstSession = CommonUtil.deepLinkingRedirection(sessionParams);
        if (isBranchFirstSession) {
            if (sessionParams.has(BRANCH_DEEP_LINK)) {
                String deepLink;
                String branchLink;
                try {
                    deepLink = sessionParams.getString(BRANCH_DEEP_LINK);
                    branchLink = sessionParams.getString(BRANCH_REFERRER_LINK);
                    if (StringUtil.isNotNullOrEmptyString(deepLink)) {
                        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
                        SharedPreferences.Editor editor = null;
                        if (prefs != null) {
                            editor = prefs.edit();
                            editor.putString(AppConstants.REFERRER_BRANCH_LINK_URL, branchLink);
                            editor.apply();
                        }
                        AppInstallationHelper appInstallationHelper = new AppInstallationHelper(LanguageSelectionActivity.this);
                        appInstallationHelper.setupAndSaveInstallation(false);
                        if (deepLink.contains("sheroes") && deepLink.contains("/communities")) {  //Currently it allows only community
                            deepLinkUrl = deepLink;

                                            if (mInstallUpdatePreference != null) {
                                                AppStatus appStatus = mInstallUpdatePreference.get();
                                                appStatus.setOnBoardingSkipped(true);
                                                mInstallUpdatePreference.set(appStatus);
                                            }
                                        }
                                    }

                    if (sessionParams.has(CommunityDetailActivity.TAB_KEY)) {
                        defaultTab = sessionParams.getString(CommunityDetailActivity.TAB_KEY);
                    }
                } catch (JSONException e) {
                    deepLinkUrl = null;
                    defaultTab = null;
                    isBranchFirstSession = false;
                }
            }
        }
    }

    private void setUpView() {
        if (CommonUtil.getPrefValue(AppConstants.SELECT_LANGUAGE_SHARE_PREF)) {
            openWelcomeScreen();
            finish();
        } else {
            setContentView(R.layout.select_language_dialog);
            mLoginPresenter.attachView(this);
            ButterKnife.bind(LanguageSelectionActivity.this);
            if (isFirstTimeUser) {
                AnalyticsManager.trackScreenView(getScreenName());
            }
            mLoginPresenter.getMasterDataToPresenter();
        }
    }

    private void refreshAuthTokenResponse(LoginResponse loginResponse) {
        loginResponse.setTokenTime(System.currentTimeMillis());
        loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
        mUserPreference.set(loginResponse);
        openHomeScreen();
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

    private void openHomeScreen() {

        if (isBranchFirstSession && StringUtil.isNotNullOrEmptyString(deepLinkUrl)) { //ads for community

            //Event for on-boarding skipping for new user came through branch link
            final HashMap<String, Object> properties = new EventProperty.Builder().branchLink(deepLinkUrl).build();
            AnalyticsManager.trackEvent(Event.ONBOARDING_SKIPPED, getScreenName(), properties);

            Uri url = Uri.parse(deepLinkUrl);
            Intent intent = new Intent(LanguageSelectionActivity.this, SheroesDeepLinkingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(CommunityDetailActivity.TAB_KEY, "");
            bundle.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
            bundle.putBoolean(AppConstants.IS_FROM_ADVERTISEMENT, isBranchFirstSession);
            if (StringUtil.isNotNullOrEmptyString(defaultTab)) {
                bundle.putString(CommunityDetailActivity.TAB_KEY, defaultTab);
            }
            intent.putExtras(bundle);
            intent.setData(url);
            startActivity(intent);
            finish();
        } else {
            Intent boardingIntent = new Intent(LanguageSelectionActivity.this, OnBoardingActivity.class);
            Bundle bundle = new Bundle();
            boardingIntent.putExtras(bundle);
            boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(boardingIntent);
            finish();
        }
    }

    @OnClick(R.id.fl_hindi)
    public void onHindiClick() {
        tvContinue.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        ivLangHind.setVisibility(View.VISIBLE);
        ivLangEng.setVisibility(View.GONE);
        setNewLocale(LanguageType.HINDI.toString());
        isLanguageSelected = true;
    }

    @OnClick(R.id.fl_english)
    public void onEnglishClick() {
        tvContinue.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        ivLangEng.setVisibility(View.VISIBLE);
        ivLangHind.setVisibility(View.GONE);
        setNewLocale(LanguageType.ENGLISH.toString());
        isLanguageSelected = true;
    }

    @OnClick(R.id.tv_continue)
    public void onContinueClick() {
        if (isLanguageSelected) {
            openWelcomeScreen();
            final HashMap<String, Object> properties = new EventProperty.Builder().build();
            properties.put(SuperProperty.LANGUAGE.getString(), CommonUtil.getPrefStringValue(LANGUAGE_KEY));
            AnalyticsManager.trackEvent(Event.LANGUAGE_SELECTED, SCREEN_LABEL, properties);
        }
    }

    private void openWelcomeScreen() {
        Intent intent = new Intent(LanguageSelectionActivity.this, WelcomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(BaseActivity.BRANCH_FIRST_SESSION, isBranchFirstSession);
        bundle.putString(BaseActivity.DEEP_LINK_URL, deepLinkUrl);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

}
