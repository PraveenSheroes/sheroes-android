package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Section;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.presenters.SettingFeedbackPresenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesDeactiveAccountFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesEducationDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesWorkExperienceFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import butterknife.ButterKnife;

/**
 * Created by priyanka.
 * seting_preferences_screen
 */

public class SettingPreferencesActivity extends BaseActivity implements SettingFeedbackView, SettingPreferencsFragment.settingPreferencesCallBack {
    private static final String SCREEN_LABEL = "Setting Screen";
    int mid;
    private final String TAG = LogUtils.makeLogTag(SettingPreferencesActivity.class);
    String privacy_type;
    String SettingListAsString;

    List<Section> sectionList;

    Section basic_details_section, education_details_section, work_experienes;


    @Inject
    SettingFeedbackPresenter mSettingFeedbackPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mSettingFeedbackPresenter.attachView(this);
        renderPreferencsFragmentView();

    }

    public void renderPreferencsFragmentView() {
        setContentView(R.layout.activity_setting_dashboard_for_header_nav);
        ButterKnife.bind(this);
        SettingPreferencsFragment frag = new SettingPreferencsFragment();
        callFirstFragment(R.id.fl_prefrences_container, frag);
        get_user_preference();

    }

    public void get_user_preference() {
        UserPreferenceRequest UserPreferenceRequest = new UserPreferenceRequest();
        UserPreferenceRequest.setAppVersion("string");
        UserPreferenceRequest.setCloudMessagingId("string");
        UserPreferenceRequest.setDeviceUniqueId("string");
        UserPreferenceRequest.setLastScreenName("string");
        UserPreferenceRequest.setScreenName("string");
        mSettingFeedbackPresenter.getUserPreferenceAuthTokeInPresenter(UserPreferenceRequest);

    }


    @Override
    public void callBackSettingPreferenceActivity(int id) {

        mid = id;
        switch (id) {

            case R.id.tv_setting_preferences_basicdetails:
                SettingPreferencesBasicDetailsFragment settingPreferencesBasicDetailsFragment = new SettingPreferencesBasicDetailsFragment();
                Gson gson = new Gson();
                String jsonSections = gson.toJson(sectionList);
                Bundle bundle = new Bundle();
                bundle.putString("Section list_value", jsonSections);
                settingPreferencesBasicDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_prefrences_container, settingPreferencesBasicDetailsFragment, ArticleCategorySpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();


                break;
            case R.id.tv_setting_preferences_education_details:
                SettingPreferencesEducationDetailsFragment settingPreferencesEducationDetailsFragment = new SettingPreferencesEducationDetailsFragment();
                Gson gson1 = new Gson();
                String jsonSections1 = gson1.toJson(sectionList);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Section list_value", jsonSections1);
                settingPreferencesEducationDetailsFragment.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_prefrences_container, settingPreferencesEducationDetailsFragment, ArticleCategorySpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();


             /*   ButterKnife.bind(this);
                SettingPreferencesEducationDetailsFragment frag1 = new SettingPreferencesEducationDetailsFragment(mtv_setting_tittle,mtv_setting_tittle1,miv_back_setting);
                Gson gson1 = new Gson();
                String jsonSections1 = gson1.toJson(sectionList);
                Bundle bundle1=new Bundle();
                bundle1.putString("Section list_value",jsonSections1);
                frag1.setArguments(bundle1);
                callFirstFragment(R.id.fl_prefrences_container, frag1);*/
                break;
            case R.id.tv_setting_preferences_work_experience:
                SettingPreferencesWorkExperienceFragment settingPreferencesWorkExperienceFragment = new SettingPreferencesWorkExperienceFragment();
                Gson gson2 = new Gson();
                String jsonSections2 = gson2.toJson(sectionList);
                Bundle bundle2 = new Bundle();
                bundle2.putString("Section list_value", jsonSections2);
                settingPreferencesWorkExperienceFragment.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_prefrences_container, settingPreferencesWorkExperienceFragment, ArticleCategorySpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();


                break;
            case R.id.tv_setting_preferences_deactive_account:
                SettingPreferencesDeactiveAccountFragment settingPreferencesDeactiveAccountFragment = new SettingPreferencesDeactiveAccountFragment();
                Gson gson3 = new Gson();
                String jsonSections3 = gson3.toJson(sectionList);
                Bundle bundle3 = new Bundle();
                bundle3.putString("Section list_value", jsonSections3);
                settingPreferencesDeactiveAccountFragment.setArguments(bundle3);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_prefrences_container, settingPreferencesDeactiveAccountFragment, ArticleCategorySpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;


        }

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


        sectionList = userpreferenseResponse.getSections();

        Log.e("Section value....", "" + sectionList);


    }

    @Override
    public void getUserChangePreferenceResponse(SettingChangeUserPreferenseResponse settingChangeUserPreferenseResponse) {

    }

    @Override
    public void showNwError() {

    }

    public void backListener(int id) {
        get_user_preference();
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
