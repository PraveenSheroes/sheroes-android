package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.LoginFragment;
import butterknife.ButterKnife;


/**
 * Created by Praveen Singh on 04/01/2017.
 * @author Praveen Singh
 * @version 5.0
 * @since 04/01/2017.
 * Title: A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginFragment.LoginActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(LoginActivity.class);
    @Inject
    Preference<LoginResponse> userPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getToken())) {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
            finish();

        } else {
            renderLoginFragmentView();
        }
    }

    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        LoginFragment frag = new LoginFragment();
        callFirstFragment(R.id.fragment_login, frag);
    }

    @Override
    public void onErrorOccurence(String errorMessage) {
        if(!StringUtil.isNotNullOrEmptyString(errorMessage))
        {
            errorMessage = getString(R.string.ID_GENERIC_ERROR);
        }
        showNetworkTimeoutDoalog(true,false,errorMessage);
    }

    @Override
    public void onLoginAuthToken() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(homeIntent);
        finish();
    }
    @Override
    public void onShowErrorDialog(String errorReason,int errorFor) {
        switch (errorReason)
        {
            case AppConstants.CHECK_NETWORK_CONNECTION:
                showNetworkTimeoutDoalog(true,false,getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                break;
            case AppConstants.HTTP_401_UNAUTHORIZED:
                showNetworkTimeoutDoalog(true,false,getString(R.string.IDS_INVALID_USER_PASSWORD));
                break;
            default:
                showNetworkTimeoutDoalog(true,false,getString(R.string.ID_GENERIC_ERROR));
        }
        //getSupportFragmentManager().popBackStack();
    }
}

