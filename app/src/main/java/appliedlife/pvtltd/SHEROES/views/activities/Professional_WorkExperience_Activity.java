package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by priyanka on 10/02/17.
 */

public class Professional_WorkExperience_Activity extends BaseActivity {


    private final String TAG = LogUtils.makeLogTag(LoginActivity.class);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();
    }
    public void renderLoginFragmentView() {
        setContentView(R.layout.fragment_profile_professional);
        ButterKnife.bind(this);
      /*  LoginFragment frag = new LoginFragment();
        callFirstFragment(R.id.fl_fragment_container, frag);*/
    }
}
