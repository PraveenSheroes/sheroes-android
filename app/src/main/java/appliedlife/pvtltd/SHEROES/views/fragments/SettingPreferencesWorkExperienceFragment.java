package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.setting.PrivilegeList;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Section;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.presenters.SettingFeedbackPresenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.SettingPreferencesActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.CustomSpinnerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka.
 * Setting_preferences_workexperiencepage
 */

public class SettingPreferencesWorkExperienceFragment extends BaseFragment implements SettingFeedbackView {
    private static final String SCREEN_LABEL = "Setting Preferences Work Experience Screen ";
    private final String TAG = LogUtils.makeLogTag(SettingPreferencesWorkExperienceFragment.class);
    List<Section> sectionList;
    List<PrivilegeList> privilegeList;
    String strtext;
    @Inject
    SettingFeedbackPresenter mSettingFeedbackPresenter;
    @Bind(R.id.spinner_workexp)
    Spinner mSpinner_workexp;
    @Bind(R.id.spinner_workexp1)
    Spinner mSpinner_workexp1;
    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;
    @Bind(R.id.tv_setting_tittle1)
    TextView mTv_setting_tittle1;
    @Bind(R.id.iv_back_setting)
    ImageView mIv_back_setting;
    private static final String[] total_iteam = {

            "ONLY ME", "PUBLIC"
    };

    int total_images[] = {R.drawable.ic_onlyme_icon, R.drawable.ic_public_icon};

    private SettingPreferencesWorkExperienceFragment.Preferences_WorkexperienceFctivityLisIntractionListener preferencesWorkexperienceFctivityLisIntractionListener;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_preferences_workexperience, container, false);
        ButterKnife.bind(this, view);
        mTv_setting_tittle.setText(R.string.ID_WORK_EXPERIENCE);
        mTv_setting_tittle1.setText(R.string.ID_PREFERENCES);
        mTv_setting_tittle.setTextSize(14);
        mTv_setting_tittle1.setTextSize(12);
        mSettingFeedbackPresenter.attachView(this);
        mSpinner_workexp.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam, total_images));
        mSpinner_workexp1.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam, total_images));

        if (null != getArguments()) {
            strtext = getArguments().getString("Section list_value");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Section>>() {
            }.getType();

            sectionList = gson.fromJson(strtext, type);

            if (null != sectionList) {

                for (Section section : sectionList) {

                    if (section.getOrder() == 3) {

                        privilegeList = section.getPrivilegeList();


                        if (privilegeList.get(0).getPrivacySettingType().equals("public")) {

                            mSpinner_workexp.setSelection(1);

                        } else {

                            mSpinner_workexp.setSelection(0);

                        }

                        /*if (privilegeList.get(1).getPrivacySettingType().equals("public")) {


                            mSpinner_workexp1.setSelection(1);

                        } else {

                            mSpinner_workexp1.setSelection(0);

                        }*/

                    }

                }

            }
        }


        AdapterView.OnItemSelectedListener countrySelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {


                LogUtils.error("selected first iteam ", "" + position);
                SettingChangeUserPreferenceRequest settingChangeUserPreferenceRequest = new SettingChangeUserPreferenceRequest();
                settingChangeUserPreferenceRequest.setAppVersion("string");
                settingChangeUserPreferenceRequest.setCloudMessagingId("string");
                settingChangeUserPreferenceRequest.setDeviceUniqueId("string");
                settingChangeUserPreferenceRequest.setSettingActionId(7);


                if (position == 0) {

                    settingChangeUserPreferenceRequest.setSettingPrivilegeId(2);
                    mSettingFeedbackPresenter.getUserChangePreferenceAuthTokeInPresenter(settingChangeUserPreferenceRequest);


                } else {
                    settingChangeUserPreferenceRequest.setSettingPrivilegeId(1);
                    mSettingFeedbackPresenter.getUserChangePreferenceAuthTokeInPresenter(settingChangeUserPreferenceRequest);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                LogUtils.error("not selected: ", "0");


                //LogUtils.error("user_get_preference_response req: ",""+position);
            }

        };

        mSpinner_workexp.setOnItemSelectedListener(countrySelectedListener);

        AdapterView.OnItemSelectedListener countrySelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {


                LogUtils.error("selected first iteam ", "" + position);
                SettingChangeUserPreferenceRequest settingChangeUserPreferenceRequest = new SettingChangeUserPreferenceRequest();
                settingChangeUserPreferenceRequest.setAppVersion("string");
                settingChangeUserPreferenceRequest.setCloudMessagingId("string");
                settingChangeUserPreferenceRequest.setDeviceUniqueId("string");
                settingChangeUserPreferenceRequest.setSettingActionId(8);


                if (position == 0) {

                    settingChangeUserPreferenceRequest.setSettingPrivilegeId(2);
                    mSettingFeedbackPresenter.getUserChangePreferenceAuthTokeInPresenter(settingChangeUserPreferenceRequest);


                } else {
                    settingChangeUserPreferenceRequest.setSettingPrivilegeId(1);
                    mSettingFeedbackPresenter.getUserChangePreferenceAuthTokeInPresenter(settingChangeUserPreferenceRequest);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                LogUtils.error("not selected: ", "0");


                //LogUtils.error("user_get_preference_response req: ",""+position);
            }

        };
        mSpinner_workexp1.setOnItemSelectedListener(countrySelectedListener1);

        //Open setting_preferences_Activity

        mIv_back_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((SettingPreferencesActivity)getActivity()).getSupportFragmentManager().popBackStack();

             /*   Intent intent = new Intent(getActivity(), SettingPreferencesActivity.class);
                startActivity(intent);*/


            }
        });
        return view;


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

    }

    @Override
    public void getUserChangePreferenceResponse(SettingChangeUserPreferenseResponse settingChangeUserPreferenseResponse) {

    }

    @Override
    public void showNwError() {

    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }


    public interface Preferences_WorkexperienceFctivityLisIntractionListener {

        void onErrorOccurence();
    }


}
