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

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.setting.PrivilegesList;
import appliedlife.pvtltd.SHEROES.models.entities.setting.RelationshipStatus;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Segments;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Setting_Education;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Setting_PrivilegesList_1;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Setting_Privileges_List;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Setting_basic_details;
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

import static appliedlife.pvtltd.SHEROES.R.id.view;

/**
 * Created by priyanka
 * SettingFragment_Page
 */


public class SettingFragment extends BaseFragment implements SettingFeedbackView,View.OnClickListener {

    private final String TAG = LogUtils.makeLogTag(SettingFragment.class);
    private final String SCREEN_NAME = "Setting_dashboard_screen";
    @Bind(R.id.tv_setting_feedback)
    TextView mTv_setting_feedback;
    @Bind(R.id.tv_logout)
    TextView mTv_logout;
   /* @Bind(R.id.tv_setting_preferences)
    TextView mTv_preferences;*/
    @Bind(R.id.tv_setting_about)
    TextView mTv_setting_about;
    @Bind(R.id.tv_setting_terms_and_condition)
    TextView mTv_setting_terms_and_condition;
    SettingView msettingFragmentCallBack;
    Integer prrference_ids;

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
       // mTv_preferences.setOnClickListener(this);
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




        msettingFragmentCallBack.backListener(prrference_ids,null);



       /* switch (prrference_ids) {

            case R.id.tv_setting_preferences:

                get_user_preference();

                break;

            default:

                msettingFragmentCallBack.backListener(prrference_ids,null);
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + prrference_ids);

        }*/
    }

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

        Segments segments=userpreferenseResponse.getSegments();
        Setting_basic_details setting_basic_details=segments.get1();
        PrivilegesList privilegesList=setting_basic_details.getPrivilegesList();
        RelationshipStatus relationshipStatus=privilegesList.getRelationshipStatus();
        String privacy_type= relationshipStatus.getPrivacySettingType();
        msettingFragmentCallBack.backListener(prrference_ids,segments);

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

    @Override
    public void showError(String s) {

    }

    public interface settingFragmentCallBack {


        void callBackSettingActivity(int id);
    }


}
