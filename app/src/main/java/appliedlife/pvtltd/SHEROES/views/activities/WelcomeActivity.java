package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;

/**
 * Created by sheroes on 06/03/17.
 */

public class WelcomeActivity extends BaseActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.welcome_activity);

    }

}
