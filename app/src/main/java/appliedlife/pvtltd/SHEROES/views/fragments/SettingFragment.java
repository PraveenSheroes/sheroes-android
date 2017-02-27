package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.presenters.SettingFeedbackPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.Feedback_ThankyouActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka
 * SettingFragment_Page
 */


public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = LogUtils.makeLogTag(SettingFragment.class);
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
        mid_setting_feedback.setOnClickListener(this);
        mid_preferences.setOnClickListener(this);
        mid_setting_about.setOnClickListener(this);
        mid_setting_terms_and_condition.setOnClickListener(this);
        return view;
    }


    public void get_user_preference()
    {
        UserPreferenceRequest UserPreferenceRequest = new UserPreferenceRequest();
        UserPreferenceRequest.setAppVersion("string");
        UserPreferenceRequest.setCloudMessagingId("string");
        UserPreferenceRequest.setDeviceUniqueId("string");
        mSettingFeedbackPresenter.getUserPreferenceAuthTokeInPresenter(UserPreferenceRequest);

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
