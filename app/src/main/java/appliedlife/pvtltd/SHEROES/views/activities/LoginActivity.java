package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
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
public class LoginActivity extends BaseActivity implements LoginFragment.LoginActivityIntractionListner, FragmentIntractionWithActivityListner {
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
    public void onErrorOccurence() {
        showNetworkTimeoutDoalog(true);
    }

    @Override
    public void onLoginAuthToken() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void onShowErrorDialog() {
        Toast.makeText(this,"Please check your email id, password",Toast.LENGTH_SHORT).show();
        //getSupportFragmentManager().popBackStack();
    }
}

