package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.EmailVerificationFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LoginFragment;
import butterknife.ButterKnife;


/**
 * Created by Praveen Singh on 04/01/2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 04/01/2017.
 * Title: A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    private final String TAG = LogUtils.makeLogTag(LoginActivity.class);

    //For ads Navigation
    private boolean isBranchFirstSession = false;
    private String deepLinkUrl = null;
    private String defaultTab = null;

    @Inject
    Preference<LoginResponse> userPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getToken()) && StringUtil.isNotNullOrEmptyString(userPreference.get().getNextScreen())) {
            if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.FEED_SCREEN)) {
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
            } else if (userPreference.get().isSheUser() && userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION)) {
                renderEmailVerifyFragmentView();
            } else {
                Intent boardingIntent = new Intent(this, OnBoardingActivity.class);
                boardingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(boardingIntent);
            }
        } else {
            renderLoginFragmentView();
        }
    }

    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        LoginFragment frag = new LoginFragment();
        frag.setArguments(bundle);
        if (bundle != null) {
            isBranchFirstSession = bundle.getBoolean(AppConstants.IS_FROM_ADVERTISEMENT);
            deepLinkUrl = bundle.getString(AppConstants.ADS_DEEP_LINK_URL);
            defaultTab = bundle.getString(CommunityDetailActivity.TAB_KEY);
        }

        callFirstFragment(R.id.fragment_login, frag);
    }

    public void onErrorOccurence(String errorMessage, String isDeactivated) {
        if (!StringUtil.isNotNullOrEmptyString(errorMessage)) {
            errorMessage = getString(R.string.ID_GENERIC_ERROR);
        }
        if (StringUtil.isNotNullOrEmptyString(isDeactivated) && isDeactivated.equalsIgnoreCase("true")) {
            showErrorDialogOnUserAction(true, false, errorMessage, "true");
        } else {
            showNetworkTimeoutDoalog(true, false, errorMessage);
        }
    }


    public void onLoginAuthToken() {
        if (userPreference.get().isSheUser() && userPreference.get().getNextScreen() != null && userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.EMAIL_VERIFICATION)) {
            renderEmailVerifyFragmentView();
        } else {

            if (isBranchFirstSession && StringUtil.isNotNullOrEmptyString(deepLinkUrl)) { //ads for community

                //Event for on-boarding skipping for new user came through branch link
                final HashMap<String, Object> properties = new EventProperty.Builder().branchLink(deepLinkUrl).build();
                AnalyticsManager.trackEvent(Event.ONBOARDING_SKIPPED, getScreenName(), properties);

                Uri url = Uri.parse(deepLinkUrl);
                Intent intent = new Intent(LoginActivity.this, SheroesDeepLinkingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
                bundle.putBoolean(AppConstants.IS_FROM_ADVERTISEMENT, isBranchFirstSession);
                if (StringUtil.isNotNullOrEmptyString(defaultTab)) {
                    bundle.putString(CommunityDetailActivity.TAB_KEY, defaultTab);
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

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.HTTP_401_UNAUTHORIZED_ERROR:
                case AppConstants.HTTP_401_UNAUTHORIZED:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_INVALID_USER_PASSWORD));
                    break;
                default: {
                    super.onShowErrorDialog(errorReason, feedParticipationEnum);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        userPreference.delete();
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(AppConstants.HIDE_SPLASH_THEME, true);
        startActivity(intent);
        finish();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
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

    public void renderEmailVerifyFragmentView() {
        setContentView(R.layout.activity_login);
        EmailVerificationFragment emailVerificationFragment = new EmailVerificationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_login, emailVerificationFragment, EmailVerificationFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }


    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public String getScreenName() {
        return null;
    }
}


