package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
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
import appliedlife.pvtltd.SHEROES.views.adapters.CustomSpinnerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by priyanka on 24/01/17.
 */

public class SettingPreferencesBasicDetailsFragment extends BaseFragment implements SettingFeedbackView {


    private SettingPreferencesBasicDetailsIntractionListener settingPreferencesBasicDetailsIntractionListener;

    String strtext;
    Section privilegeLists;
    List<Section> sectionList;
    List<PrivilegeList> privilegeList;
    @Inject
    SettingFeedbackPresenter mSettingFeedbackPresenter;
    @Bind(R.id.spinner1)
    Spinner mSpinner1;
    @Bind(R.id.spinner2)
    Spinner mSpinner2;
    @Bind(R.id.spinner3)
    Spinner mSpinner3;
    @Bind(R.id.spinner4)
    Spinner mSpinner4;
    @Bind(R.id.tv_setting_tittle)
    TextView mtv_setting_tittle;
    @Bind(R.id.tv_setting_tittle1)
    TextView mtv_setting_tittle1;
    @Bind(R.id.iv_back_setting)
    ImageView miv_back_setting;
    SettingView settingViewlistener;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {
            if (getActivity() instanceof SettingView) {

                settingViewlistener = (SettingView) getActivity();

            }
        } catch (Exception e) {


        }
    }


    private final String TAG = LogUtils.makeLogTag(SettingPreferencesBasicDetailsFragment.class);


    private static final String[] total_iteam = {
            "ONLY ME", "PUBLIC"
    };

    int total_images[] = {R.drawable.ic_onlyme_icon, R.drawable.ic_public_icon};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_preferences_basicdetails, container, false);
        ButterKnife.bind(this, view);
        mSettingFeedbackPresenter.attachView(this);
        mSpinner1.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam, total_images));
        mSpinner2.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam, total_images));
        mSpinner3.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam, total_images));
        mSpinner4.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam, total_images));

        mtv_setting_tittle.setText(R.string.ID_BASICDETAILS);
        mtv_setting_tittle1.setText(R.string.ID_PREFERENCES);


        if (null != getArguments()) {

            strtext = getArguments().getString("Section list_value");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Section>>() {
            }.getType();
            sectionList = gson.fromJson(strtext, type);

            if (null != sectionList) {
                for (Section section : sectionList) {

                    if (section.getOrder() == 1) {

                        privilegeList = section.getPrivilegeList();

                        if (privilegeList.get(0).getPrivacySettingType().equals("public")) {
                            mSpinner1.setSelection(1);

                        } else {

                            mSpinner1.setSelection(0);

                        }

                        if (privilegeList.get(1).getPrivacySettingType().equals("public")) {

                            mSpinner2.setSelection(1);

                        } else {

                            mSpinner2.setSelection(0);

                        }

                        if (privilegeList.get(2).getPrivacySettingType().equals("public")) {


                            mSpinner3.setSelection(1);

                        } else {

                            mSpinner3.setSelection(0);

                        }

                        if (privilegeList.get(3).getPrivacySettingType().equals("public")) {


                            mSpinner4.setSelection(1);

                        } else {

                            mSpinner4.setSelection(0);
                        }
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
                settingChangeUserPreferenceRequest.setSettingActionId(1);


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

        mSpinner1.setOnItemSelectedListener(countrySelectedListener);

        AdapterView.OnItemSelectedListener countrySelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {


                LogUtils.error("selected first iteam ", "" + position);
                SettingChangeUserPreferenceRequest settingChangeUserPreferenceRequest = new SettingChangeUserPreferenceRequest();
                settingChangeUserPreferenceRequest.setAppVersion("string");
                settingChangeUserPreferenceRequest.setCloudMessagingId("string");
                settingChangeUserPreferenceRequest.setDeviceUniqueId("string");
                settingChangeUserPreferenceRequest.setSettingActionId(2);


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
        mSpinner2.setOnItemSelectedListener(countrySelectedListener1);
        AdapterView.OnItemSelectedListener countrySelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {


                LogUtils.error("selected first iteam ", "" + position);
                SettingChangeUserPreferenceRequest settingChangeUserPreferenceRequest = new SettingChangeUserPreferenceRequest();
                settingChangeUserPreferenceRequest.setAppVersion("string");
                settingChangeUserPreferenceRequest.setCloudMessagingId("string");
                settingChangeUserPreferenceRequest.setDeviceUniqueId("string");
                settingChangeUserPreferenceRequest.setSettingActionId(3);


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
        mSpinner3.setOnItemSelectedListener(countrySelectedListener2);
        AdapterView.OnItemSelectedListener countrySelectedListener3 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {


                LogUtils.error("selected first iteam ", "" + position);
                SettingChangeUserPreferenceRequest settingChangeUserPreferenceRequest = new SettingChangeUserPreferenceRequest();
                settingChangeUserPreferenceRequest.setAppVersion("string");
                settingChangeUserPreferenceRequest.setCloudMessagingId("string");
                settingChangeUserPreferenceRequest.setDeviceUniqueId("string");
                settingChangeUserPreferenceRequest.setSettingActionId(4);


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
        mSpinner4.setOnItemSelectedListener(countrySelectedListener3);

        //Open setting_preferences_Activity
        miv_back_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                settingViewlistener.backListener(R.id.iv_back_setting);

               /* Intent intent = new Intent(getActivity(), SettingPreferencesActivity.class);
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

    public interface SettingPreferencesBasicDetailsIntractionListener {

        void onErrorOccurence();

        void basicDetailBack();
    }


}
