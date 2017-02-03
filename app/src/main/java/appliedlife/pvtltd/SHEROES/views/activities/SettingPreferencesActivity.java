package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesDeactiveAccountFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesEducationDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesWorkExperienceFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencsFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka.
 * seting_preferences_screen
 */

public class SettingPreferencesActivity extends BaseActivity implements SettingPreferencsFragment.settingPreferencesCallBack {


    private final String TAG = LogUtils.makeLogTag(SettingPreferencesActivity.class);

    @Bind(R.id.tv_setting_tittle)
    TextView mtv_setting_tittle;
    @Bind(R.id.tv_setting_tittle1)
    TextView mtv_setting_tittle1;
    @Bind(R.id.iv_back_setting)
    ImageView miv_back_setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderPreferencsFragmentView();
    }



    public void renderPreferencsFragmentView() {
        setContentView(R.layout.activity_setting_dashboard_for_header_nav);
        ButterKnife.bind(this);
        mtv_setting_tittle.setText(R.string.ID_PREFERENCES);
        mtv_setting_tittle1.setText(R.string.ID_SETTINGS);
        SettingPreferencsFragment frag = new SettingPreferencsFragment();
        callFirstFragment(R.id.fl_fragment_container, frag);
    }


    @Override
    public void callBackSettingPreferenceActivity(int id) {

        switch (id){


            case R.id.id_setting_preferences_basicdetails:
                setContentView(R.layout.activity_setting_dashboard_for_header_nav);
                ButterKnife.bind(this);
                SettingPreferencesBasicDetailsFragment frag = new SettingPreferencesBasicDetailsFragment(mtv_setting_tittle,mtv_setting_tittle1,miv_back_setting);
                callFirstFragment(R.id.fl_fragment_container, frag);
                break;

            case R.id.id_setting_preferences_education_details:
                setContentView(R.layout.activity_setting_dashboard_for_header_nav);
                ButterKnife.bind(this);
                SettingPreferencesEducationDetailsFragment frag1 = new SettingPreferencesEducationDetailsFragment(mtv_setting_tittle,mtv_setting_tittle1,miv_back_setting);
                callFirstFragment(R.id.fl_fragment_container, frag1);
                break;

            case R.id.id_setting_preferences_work_experience:
                setContentView(R.layout.activity_setting_dashboard_for_header_nav);
                ButterKnife.bind(this);
                SettingPreferencesWorkExperienceFragment frag2= new SettingPreferencesWorkExperienceFragment(mtv_setting_tittle,mtv_setting_tittle1,miv_back_setting);
                callFirstFragment(R.id.fl_fragment_container, frag2);
                break;
            case R.id.id_setting_preferences_deactive_account:
                setContentView(R.layout.activity_setting_dashboard_for_header_nav);
                ButterKnife.bind(this);
                SettingPreferencesDeactiveAccountFragment frag3= new SettingPreferencesDeactiveAccountFragment(mtv_setting_tittle,mtv_setting_tittle1,miv_back_setting);
                callFirstFragment(R.id.fl_fragment_container, frag3);
                break;


        }

    }

    //open SettingActivity press on id_back

    @OnClick(R.id.iv_back_setting)
    public void onbacklick() {
        getFragmentManager().popBackStack();
        finish();
    }







}
