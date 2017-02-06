package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.SettingPreferencesActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by priyanka
 * Setting_Preferences_DeactiveAccount_Screen
 */



public class SettingPreferencesDeactiveAccountFragment extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(SettingPreferencesDeactiveAccountFragment.class);


    @Bind(R.id.deactive_text1)
    TextView mtvdeactive_text1;
    @Bind(R.id.deactive_check_box1)
    CheckBox mdeactive_check_box1;
    @Bind(R.id.deactive_text1a)
    TextView mtvdeactive_text1a;
    @Bind(R.id.deactive_check_box1a)
    CheckBox mdeactive_check_box1a;
    @Bind(R.id.deactive_text1b)
    TextView mtvdeactive_text1b;
    @Bind(R.id.deactive_check_box1b)
    CheckBox mdeactive_check_box1b;
    @Bind(R.id.deactive_text1c)
    TextView mtvdeactive_text1c;
    @Bind(R.id.deactive_check_box1c)
    CheckBox mdeactive_check_box1c;
    @Bind(R.id.deactive_text1d)
    TextView mtvdeactive_text1d;
    @Bind(R.id.deactive_check_box1d)
    CheckBox mdeactive_check_box1d;
    @Bind(R.id.et_edit_text_reson)
    EditText editText_reson;
    @Bind(R.id.ID_tvreason)
    TextView tv_reson;
    @Bind(R.id.preferences_deactiveaccount_button)
    Button mpreferences_deactiveaccount_button;



    int flag=0;


    public SettingPreferences_DeactiveAccounActivitytLisIntractionListener settingPreferences_deactiveAccounActivitytLisIntractionListener;



    ImageView miv_back_setting;

    public SettingPreferencesDeactiveAccountFragment(TextView mtv_setting_tittle, TextView mtv_setting_tittle1, ImageView miv_back_setting)
    {
        mtv_setting_tittle.setText(R.string.ID_DEACTIVEACCOUNT);
        mtv_setting_tittle1.setText(R.string.ID_PREFERENCES);
        this.miv_back_setting= miv_back_setting;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_preferences_deactiveaccount, container, false);
        ButterKnife.bind(this, view);


        //Open setting_preferences_Activity

        miv_back_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), SettingPreferencesActivity.class);
                startActivity(i);
                getActivity().finish();

            }
        });
        return view;
    }

    //click on deactive_text1

    @OnClick(R.id.preferences_deactiveaccount_button)

    public void ondeactive_buttonclick() {

        PreferencesDeactiveAccountDialogFragment newFragment = new PreferencesDeactiveAccountDialogFragment(this);
        newFragment.show(getActivity().getFragmentManager(), "dialog");

    }



    //click on deactive_text1

    @OnClick(R.id.deactive_text1)

    public void ondeactivetext1click() {

        setcolorontextview(mtvdeactive_text1,mdeactive_check_box1);

    }
//click on check_1

    @OnClick(R.id.deactive_check_box1)

    public void ondeactivecheck1click() {

        setcolorontextview(mtvdeactive_text1,mdeactive_check_box1);

    }

    //click on deactive_text2

    @OnClick(R.id.deactive_text1a)

    public void ondeactivetext1aclick() {

        setcolorontextview(mtvdeactive_text1a,mdeactive_check_box1a);

    }


    //click on deactive_check2

    @OnClick(R.id.deactive_check_box1a)

    public void ondeactivecheck1aclick() {

        setcolorontextview(mtvdeactive_text1a,mdeactive_check_box1a);

    }


    //click on deactive_text3

    @OnClick(R.id.deactive_text1b)

    public void ondeactivetext1bclick() {

        setcolorontextview(mtvdeactive_text1b,mdeactive_check_box1b);


    }

    //click on deactive_check_3

    @OnClick(R.id.deactive_check_box1b)

    public void ondeactivecheck3bclick() {

        setcolorontextview(mtvdeactive_text1b,mdeactive_check_box1b);


    }



    //click on deactive_text4

    @OnClick(R.id.deactive_text1c)

    public void ondeactivetext1cclick() {

        setcolorontextview(mtvdeactive_text1c,mdeactive_check_box1c);

    }


    //click on deactive_check4

    @OnClick(R.id.deactive_check_box1c)

    public void ondeactivecheck4cclick() {

        setcolorontextview(mtvdeactive_text1c,mdeactive_check_box1c);

    }

    //click on deactive_text5

    @OnClick(R.id.deactive_text1d)

    public void ondeactivetext1dclick() {

        setcolorontextview(mtvdeactive_text1d,mdeactive_check_box1d);

    }


    //click on deactive_check_5

    @OnClick(R.id.deactive_check_box1d)

    public void ondeactivecheck5click() {

        setcolorontextview(mtvdeactive_text1d,mdeactive_check_box1d);

    }

    //function for change text color and show checkbox

    public void setcolorontextview(TextView textview,CheckBox checkbox)

    {
        if(flag==0)
        {
            textview.setTextColor(getResources().getColor(R.color.search_tab_text));
            checkbox.setChecked(true);

            if(textview.getText().equals(getResources().getString(R.string.ID_OTHER)))
            {
                editText_reson.setVisibility(View.VISIBLE);
                tv_reson.setVisibility(View.VISIBLE);
                mpreferences_deactiveaccount_button.setBackgroundColor(getResources().getColor(R.color.red));

            }

            flag=1;
        }
        else

        {
            textview.setTextColor(getResources().getColor(R.color.black));
            checkbox.setChecked(false);
            if(textview.getText().equals(getResources().getString(R.string.ID_OTHER)))
            {
                editText_reson.setVisibility(View.GONE);
                tv_reson.setVisibility(View.GONE);
                mpreferences_deactiveaccount_button.setBackgroundColor(getResources().getColor(R.color.grey2));

            }
            flag=0;
        }


    }



   /* @OnClick(R.id.preferences_deactiveaccount_button)
    public void deactivebuttonclick() {
        Intent i = new Intent(getActivity(), PreferencesDeactiveAccountDialogFragment.class);
        startActivity(i);

    }*/

    interface SettingPreferences_DeactiveAccounActivitytLisIntractionListener {

        void onErrorOccurence();
    }



}
