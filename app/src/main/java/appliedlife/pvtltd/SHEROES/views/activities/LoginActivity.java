package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.LoginFragment;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import butterknife.ButterKnife;

/** Created by Praveen Singh on 04/01/2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 04/01/2017.
 * Title: A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginFragment.LoginActivityIntractionListner,FragmentIntractionWithActivityListner {
    private final String TAG = LogUtils.makeLogTag(LoginActivity.class);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();
    }
    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        LoginFragment frag = new LoginFragment();
        callFirstFragment(R.id.fl_fragment_container, frag);
    }

    @Override
    public void onErrorOccurence() {
        showNetworkTimeoutDoalog(true);
    }

    @Override
    public void onLoginAuthToken() {
        Intent homeIntent=new Intent(this,HomeActivity.class);
        startActivity(homeIntent);

    }

    @Override
    public void onShowErrorDialog() {
        getSupportFragmentManager().popBackStack();
    }
}

