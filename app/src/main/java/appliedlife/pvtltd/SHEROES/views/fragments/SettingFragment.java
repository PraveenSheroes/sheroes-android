package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka
 * SettingFragment_Page
 */


public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = LogUtils.makeLogTag(ArticlesFragment.class);
    private final String SCREEN_NAME = "Setting_dashboard_screen";
    @Bind(R.id.id_setting_feedback)
    TextView mid_setting_feedback;
    @Bind(R.id.id_setting_preferences)
    TextView mid_preferences;
    @Bind(R.id.id_setting_about)
    TextView mid_setting_about;
    @Bind(R.id.id_setting_terms_and_condition)
    TextView mid_setting_terms_and_condition;
    SettingView msettingFragmentCallBack;


    @Override
    public void onAttach(Context context) {


        super.onAttach(context);
        try {
            if (getActivity() instanceof SettingView) {
                msettingFragmentCallBack = (SettingView) getActivity();
            }

        } catch (Exception e) {
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_dashboard, container, false);
        ButterKnife.bind(this, view);
        mid_setting_feedback.setOnClickListener(this);
        mid_preferences.setOnClickListener(this);
        mid_setting_about.setOnClickListener(this);
        mid_setting_terms_and_condition.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        msettingFragmentCallBack.backListener(id);


    }

    public interface settingFragmentCallBack {
        void callBackSettingActivity(int id);
    }


}
