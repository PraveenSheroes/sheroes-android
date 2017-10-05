package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka.
 */

public class SettingPreferencsFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = LogUtils.makeLogTag(SettingPreferencsFragment.class);
    private final String SCREEN_NAME = "Setting_preferences_screen";

    @Bind(R.id.tv_setting_preferences_basicdetails)
    TextView mT_setting_preferences_basicdetails;
    @Bind(R.id.tv_setting_preferences_education_details)
    TextView mT_setting_preferences_education_details;
    @Bind(R.id.tv_setting_preferences_work_experience)
    TextView mT_setting_preferences_work_experience;
    @Bind(R.id.tv_setting_preferences_deactive_account)
    TextView mTv_setting_preferences_deactive_account;
    settingPreferencesCallBack msettingPreferencesCallBack;
    final Handler handler_interact = new Handler();
    TextView mTextview;

    @Bind(R.id.tv_setting_tittle)
    TextView mtv_setting_tittle;
    @Bind(R.id.tv_setting_tittle1)
    TextView mtv_setting_tittle1;
    @Bind(R.id.iv_back_setting)
    ImageView miv_back_setting;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {
            if (getActivity() instanceof settingPreferencesCallBack) {
                msettingPreferencesCallBack = (settingPreferencesCallBack) getActivity();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_preferences, container, false);
        ButterKnife.bind(this, view);
        mT_setting_preferences_basicdetails.setOnClickListener(this);
        mT_setting_preferences_education_details.setOnClickListener(this);
        mT_setting_preferences_work_experience.setOnClickListener(this);
        mTv_setting_preferences_deactive_account.setOnClickListener(this);
        mtv_setting_tittle.setText(R.string.ID_PREFERENCES);
        mtv_setting_tittle1.setText(R.string.ID_SETTINGS);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        msettingPreferencesCallBack.callBackSettingPreferenceActivity(id);
        switch (id) {

            case R.id.tv_setting_preferences_basicdetails:
                mT_setting_preferences_basicdetails.setTextColor(getResources().getColor(R.color.search_tab_text));
                mTextview = mT_setting_preferences_basicdetails;
                settextcolor();
                break;
            case R.id.tv_setting_preferences_education_details:
                mT_setting_preferences_education_details.setTextColor(getResources().getColor(R.color.search_tab_text));
                mTextview = mT_setting_preferences_education_details;
                settextcolor();
                break;
            case R.id.tv_setting_preferences_work_experience:
                mT_setting_preferences_work_experience.setTextColor(getResources().getColor(R.color.search_tab_text));
                mTextview = mT_setting_preferences_work_experience;
                settextcolor();
                break;
            case R.id.tv_setting_preferences_deactive_account:
                mTv_setting_preferences_deactive_account.setTextColor(getResources().getColor(R.color.search_tab_text));
                mTextview = mTv_setting_preferences_deactive_account;
                settextcolor();

                break;

            default:


                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

        }
    }


    @OnClick(R.id.iv_back_setting)
    public void backPressed() {
        getActivity().finish();
    }

    public void settextcolor() {

        Timer timer_interact = new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateGUI();
            }
        }, 200);
    }

    private void UpdateGUI() {


        handler_interact.post(runnable_interact);
    }

    //creating runnable
    final Runnable runnable_interact = new Runnable() {
        public void run() {


            mTextview.setTextColor(getResources().getColor(R.color.searchbox_text_color));
        }
    };

    @Override
    public String getScreenName() {
        return null;
    }


    public interface settingPreferencesCallBack {

        void callBackSettingPreferenceActivity(int id);

    }


}
