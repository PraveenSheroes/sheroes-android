package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.SettingPreferencesActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.CustomSpinnerAdapter;
import butterknife.ButterKnife;

/**
 * Created by priyanka.
 * Setting_preferences_workexperiencepage
 */

public class SettingPreferencesWorkExperienceFragment extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(SettingPreferencesWorkExperienceFragment.class);


    ImageView miv_back_setting;

    public SettingPreferencesWorkExperienceFragment(TextView mtv_setting_tittle, TextView mtv_setting_tittle1, ImageView miv_back_setting)
    {
        mtv_setting_tittle.setText(R.string.ID_WORKEXPERIENCEDETAILS);
        mtv_setting_tittle1.setText(R.string.ID_PREFERENCES);
        this.miv_back_setting=miv_back_setting;

    }

    private static final String[] total_iteam = {

            "ONLY ME", "PUBLIC"
    };

    int total_images[] = { R.drawable.ic_onlyme_icon, R.drawable.ic_public_icon };

    private SettingPreferencesWorkExperienceFragment.Preferences_WorkexperienceFctivityLisIntractionListener preferencesWorkexperienceFctivityLisIntractionListener;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);


        View view = inflater.inflate(R.layout.fragment_setting_preferences_workexperience, container, false);
        ButterKnife.bind(this, view);

        final Spinner mySpinner = (Spinner) view.findViewById(R.id.spinner_workexp);
        mySpinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam,total_images));
        final Spinner mySpinner1= (Spinner) view.findViewById(R.id.spinner_workexp1);
        mySpinner1.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam,total_images));


        //Open setting_preferences_Activity

        miv_back_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), SettingPreferencesActivity.class);
                startActivity(i);
            }
        });

        return view;



    }



    public interface Preferences_WorkexperienceFctivityLisIntractionListener {

        void onErrorOccurence();
    }



}
