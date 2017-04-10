package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.presenters.SettingFeedbackPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka
 * SettingFragment_Page
 */


public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = LogUtils.makeLogTag(SettingFragment.class);
    @Bind(R.id.tv_setting_feedback)
    TextView mTvSettingFeedback;
    @Bind(R.id.tv_logout)
    TextView mTvLogout;
    @Bind(R.id.tv_setting_preferences)
    TextView mTvPreferences;
    @Bind(R.id.tv_setting_about)
    TextView mTvSettingAbout;
    @Bind(R.id.tv_setting_terms_and_condition)
    TextView mTvSettingTermsAndCondition;
    int preferenceIds;
    TextView textView;
    final Handler handler_interact = new Handler();
    @Inject
    SettingFeedbackPresenter mSettingFeedbackPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_dashboard, container, false);
        ButterKnife.bind(this, view);
        mTvSettingFeedback.setOnClickListener(this);
        mTvLogout.setOnClickListener(this);
        mTvPreferences.setOnClickListener(this);
        mTvSettingAbout.setOnClickListener(this);
        mTvSettingTermsAndCondition.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        preferenceIds = view.getId();
        ((HomeActivity) getActivity()).settingListItemSelected(preferenceIds);
        switch (preferenceIds) {
            case R.id.tv_setting_feedback:
                mTvSettingFeedback.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView = mTvSettingFeedback;
                settextcolor();
                break;
            case R.id.tv_setting_preferences:
                mTvPreferences.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView = mTvPreferences;
                settextcolor();
                break;
            case R.id.tv_setting_about:
                mTvSettingAbout.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView = mTvSettingAbout;
                settextcolor();
                break;
            case R.id.tv_setting_terms_and_condition:
                mTvSettingTermsAndCondition.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView = mTvSettingTermsAndCondition;
                settextcolor();
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + preferenceIds);
        }
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

            textView.setTextColor(getResources().getColor(R.color.searchbox_text_color));
        }
    };


}
