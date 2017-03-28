package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 01/03/17.
 */
public class ProfileCityWorkFragment extends BaseFragment implements ProfileView {
    private final String TAG = LogUtils.makeLogTag(ProfileCityWorkFragment.class);
    private ProfileWorkLocationFragmentListener profileWorkLocationFragmentListener;
    private final String SCREEN_NAME = "Profile_City_Work_screen";
    @Bind(R.id.tv_profile_tittle)
    TextView mTv_profile_tittle;
    @Bind(R.id.cb_worklocation_text1)
    CheckBox mCb_worklocation_text1;
    @Bind(R.id.cb_worklocation_text2)
    CheckBox mCb_worklocation_text2;
    @Bind(R.id.cb_worklocation_text3)
    CheckBox mCb_worklocation_text3;
    @Bind(R.id.cb_worklocation_text4)
    CheckBox mCb_worklocation_text4;
    @Bind(R.id.et_add_city)
    EditText mEt_add_city;
    @Bind(R.id.bt_save_cities)
    Button mBt_save_cities;
    private String mChecked_value;
    private String CityValue;

    @Inject
    ProfilePersenter mProfilePresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof
                    ProfileWorkLocationFragmentListener) {

                profileWorkLocationFragmentListener = (ProfileCityWorkFragment.ProfileWorkLocationFragmentListener) getActivity();

            }
        } catch (Exception e) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_choose_city, container, false);
        mProfilePresenter.attachView(this);
        ButterKnife.bind(this, view);
        mTv_profile_tittle.setText(R.string.ID_PREFERRED_WORK_LOCATION);
        return view;
    }

    @OnClick(R.id.cb_worklocation_text1)
    public void click_On_City1() {
        if (mCb_worklocation_text1.isChecked()) {

            mCb_worklocation_text2.setChecked(false);
            mCb_worklocation_text3.setChecked(false);
            mCb_worklocation_text4.setChecked(false);
            mChecked_value = "LOCAL";
            mEt_add_city.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.cb_worklocation_text2)
    public void click_On_City2() {
        if (mCb_worklocation_text2.isChecked()) {
            mCb_worklocation_text1.setChecked(false);
            mCb_worklocation_text3.setChecked(false);
            mCb_worklocation_text4.setChecked(false);
            mChecked_value = "COUNTRY";
            mEt_add_city.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.cb_worklocation_text3)
    public void click_On_City3() {
        if (mCb_worklocation_text3.isChecked()) {
            mCb_worklocation_text1.setChecked(false);
            mCb_worklocation_text2.setChecked(false);
            mCb_worklocation_text4.setChecked(false);
            mChecked_value = "GLOBAL";
            mEt_add_city.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.cb_worklocation_text4)


    public void click_On_City4() {
        if (mCb_worklocation_text4.isChecked()) {
            mCb_worklocation_text1.setChecked(false);
            mCb_worklocation_text2.setChecked(false);
            mCb_worklocation_text3.setChecked(false);
            mChecked_value = "ADD_ANY_CITY";
            mEt_add_city.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_back_profile)
    public void callBack() {
        profileWorkLocationFragmentListener.locationBack();
    }

    @OnClick(R.id.bt_save_cities)
    public void Save_Cities() {
        if (mCb_worklocation_text4.isChecked()) {

            CityValue = mEt_add_city.getText().toString();

            if (StringUtil.isNotNullOrEmptyString(CityValue)) {

                ProfilePreferredWorkLocationRequest profilePreferredWorkLocationRequest = new ProfilePreferredWorkLocationRequest();
                profilePreferredWorkLocationRequest.setAppVersion("string");
                profilePreferredWorkLocationRequest.setCloudMessagingId("string");
                profilePreferredWorkLocationRequest.setDeviceUniqueId("string");
                profilePreferredWorkLocationRequest.setLastScreenName("string");
                profilePreferredWorkLocationRequest.setScreenName("string");
                profilePreferredWorkLocationRequest.setSource("string");
                profilePreferredWorkLocationRequest.setType("VISIT_PREFERENCE");
                profilePreferredWorkLocationRequest.setSubType("CLIENT_SIDE_VISIT_PREFERENCE_SERVICE");
                profilePreferredWorkLocationRequest.setClientSideVisitPreference(mChecked_value);
                profilePreferredWorkLocationRequest.setCityId(0);
                profilePreferredWorkLocationRequest.setCity(CityValue);
                mProfilePresenter.getUserWorkLocationAuthTokeInPresenter(profilePreferredWorkLocationRequest);

            } else {

                //will be change
                Toast.makeText(getActivity(), "Please fill up city name",
                        Toast.LENGTH_LONG).show();

            }

        } else

        {
            if (mCb_worklocation_text1.isChecked() || mCb_worklocation_text2.isChecked() || mCb_worklocation_text3.isChecked())

            {
                ProfilePreferredWorkLocationRequest profilePreferredWorkLocationRequest = new ProfilePreferredWorkLocationRequest();
                profilePreferredWorkLocationRequest.setAppVersion("string");
                profilePreferredWorkLocationRequest.setCloudMessagingId("string");
                profilePreferredWorkLocationRequest.setDeviceUniqueId("string");
                profilePreferredWorkLocationRequest.setLastScreenName("string");
                profilePreferredWorkLocationRequest.setScreenName("string");
                profilePreferredWorkLocationRequest.setSource("string");
                profilePreferredWorkLocationRequest.setType("VISIT_PREFERENCE");
                profilePreferredWorkLocationRequest.setCityId(0);
                profilePreferredWorkLocationRequest.setCity(CityValue);
                profilePreferredWorkLocationRequest.setSubType("CLIENT_SIDE_VISIT_PREFERENCE_SERVICE");
                profilePreferredWorkLocationRequest.setClientSideVisitPreference(mChecked_value);
                mProfilePresenter.getUserWorkLocationAuthTokeInPresenter(profilePreferredWorkLocationRequest);

            } else {

                //will be change
                Toast.makeText(getActivity(), "Please Checked",
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void backListener(int id) {

    }

    @Override
    public void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void callFragment(int id) {

    }

    @Override
    public void getEducationResponse(EducationResponse educationResponse) {

    }

    @Override
    public void getPersonalBasicDetailsResponse(PersonalBasicDetailsResponse personalBasicDetailsResponse) {

    }

    @Override
    public void getprofiletracelflexibilityResponse(ProfileTravelFlexibilityResponse profileTravelFlexibilityResponse) {

    }

    @Override
    public void getUserSummaryResponse(UserSummaryResponse userSummaryResponse) {

    }

    @Override
    public void getProfessionalBasicDetailsResponse(ProfessionalBasicDetailsResponse professionalBasicDetailsResponse) {

    }

    @Override
    public void getProfessionalWorkLocationResponse(ProfilePreferredWorkLocationResponse profilePreferredWorkLocationResponse) {


        Toast.makeText(getActivity(), profilePreferredWorkLocationResponse.getStatus(),
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    public interface ProfileWorkLocationFragmentListener {

        void onErrorOccurence();

        void locationBack();
    }
}
