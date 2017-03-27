package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Section;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.presenters.SettingFeedbackPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka
 * SettingFragment_Page
 */


public class SettingFragment extends BaseFragment implements SettingFeedbackView, View.OnClickListener {

    private final String TAG = LogUtils.makeLogTag(SettingFragment.class);
    private final String SCREEN_NAME = "Setting_dashboard_screen";
    @Bind(R.id.tv_setting_feedback)
    TextView mTv_setting_feedback;
    @Bind(R.id.tv_logout)
    TextView mTv_logout;
    @Bind(R.id.tv_setting_preferences)
    TextView mTv_preferences;
    @Bind(R.id.tv_setting_about)
    TextView mTv_setting_about;
    @Bind(R.id.tv_setting_terms_and_condition)
    TextView mTv_setting_terms_and_condition;
    SettingView msettingFragmentCallBack;
    Integer prrference_ids;
TextView textView;
    final Handler handler_interact=new Handler();

    @Inject
    SettingFeedbackPresenter mSettingFeedbackPresenter;


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
        mSettingFeedbackPresenter.attachView(this);
        mTv_setting_feedback.setOnClickListener(this);
        mTv_logout.setOnClickListener(this);
        mTv_preferences.setOnClickListener(this);
        mTv_setting_about.setOnClickListener(this);
        mTv_setting_terms_and_condition.setOnClickListener(this);
        return view;
    }

    public void get_user_preference()

    {

        UserPreferenceRequest UserPreferenceRequest = new UserPreferenceRequest();
        UserPreferenceRequest.setAppVersion("string");
        UserPreferenceRequest.setCloudMessagingId("string");
        UserPreferenceRequest.setDeviceUniqueId("string");
        UserPreferenceRequest.setLastScreenName("string");
        UserPreferenceRequest.setScreenName("string");
        mSettingFeedbackPresenter.getUserPreferenceAuthTokeInPresenter(UserPreferenceRequest);

    }

    @Override
    public void onClick(View view) {

        prrference_ids = view.getId();


        msettingFragmentCallBack.settingpreference(prrference_ids, null);






        switch (prrference_ids) {

            case R.id.tv_setting_feedback:
                mTv_setting_feedback.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView=mTv_setting_feedback;
                settextcolor();
                break;
            case R.id.tv_setting_preferences:
                mTv_preferences.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView=mTv_preferences;
                settextcolor();
                break;
            case R.id.tv_setting_about:
                mTv_setting_about.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView=mTv_setting_about;
                settextcolor();
                break;
            case R.id.tv_setting_terms_and_condition:
                mTv_setting_terms_and_condition.setTextColor(getResources().getColor(R.color.search_tab_text));
                textView=mTv_setting_terms_and_condition;
                settextcolor();

                break;

            default:


                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + prrference_ids);

        }
    }


    public void settextcolor()
    {

        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {UpdateGUI();}
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





    @Override
    public void getFeedbackResponse(SettingFeedbackResponce feedbackResponce) {

    }

    @Override
    public void getUserRatingResponse(SettingRatingResponse ratingResponse) {

    }

    @Override
    public void getUserDeactiveResponse(SettingDeActivateResponse deActivateResponse) {

    }

    @Override
    public void getUserPreferenceResponse(UserpreferenseResponse userpreferenseResponse) {


        List<Section> sections = userpreferenseResponse.getSections();

        Log.e("Section value....", "" + sections);

        msettingFragmentCallBack.settingpreference(prrference_ids, sections);


    }

    @Override
    public void getUserChangePreferenceResponse(SettingChangeUserPreferenseResponse settingChangeUserPreferenseResponse) {

    }

    @Override
    public void showNwError() {

    }


    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }



    public interface settingFragmentCallBack {


        void callBackSettingActivity(int id);


    }


}
