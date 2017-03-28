package appliedlife.pvtltd.SHEROES.views.fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileSpinnerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by priyanka on 22/03/17.
 * Personal Basic Details Fragment
 */

public class PersonalBasicDetailsFragment extends BaseFragment implements ProfileView {
    private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
    private final String SCREEN_NAME = "Personal_basic_details_screen";
    @Inject
    ProfilePersenter mProfilePresenter;
    @Bind(R.id.tv_profile_tittle)
    TextView mTv_profile_tittle;
    @Bind(R.id.iv_back_profile)
    ImageView mIv_back_profile;
    @Bind(R.id.btn_personal_basic_details_save)
    Button mBtn_personal_basic_details_save;
    @Bind(R.id.et_first_name)
    EditText mEt_first_name;
    @Bind(R.id.et_last_name)
    EditText mEt_last_name;
    @Bind(R.id.et_child_number)
    EditText mEt_child_number;
    @Bind(R.id.tv_mobile_number)
    TextView mEt_mobile_number;
    @Bind(R.id.tv_user_email_id)
    TextView mTv_email_id;
    @Bind(R.id.et_currnt_location)
    EditText mEt_currnt_location;
    @Bind(R.id.spinner_relation_status)
    Spinner mSpinner_relation_status;


    String mFirst_name,mLast_name,mChild_number,mCurrent_location,mRelation_status,mMobile_number;

    private static final String[] total_iteam = {
            "Married", "Unmarried"
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_personal_basicdetails, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);
        mTv_profile_tittle.setText(R.string.ID_BASICDETAILS);
        mSpinner_relation_status.setAdapter(new ProfileSpinnerAdapter(getActivity(), R.layout.setting_spinner, total_iteam));


        return view;
    }




    @OnClick(R.id.btn_personal_basic_details_save)

    public void  Save_Basic_Details()
    {
        mFirst_name= mEt_first_name.getText().toString();
        mLast_name= mEt_last_name.getText().toString();
        mChild_number = mEt_child_number.getText().toString();
        mCurrent_location=mEt_currnt_location.getText().toString();
        mRelation_status=mSpinner_relation_status.getSelectedItem().toString();

        PersonalBasicDetailsRequest personalBasicDetailsRequest = new PersonalBasicDetailsRequest();
        personalBasicDetailsRequest.setAppVersion("string");
        personalBasicDetailsRequest.setCloudMessagingId("string");
        personalBasicDetailsRequest.setDeviceUniqueId("string");
        personalBasicDetailsRequest.setLastScreenName("string");
        personalBasicDetailsRequest.setScreenName("string");
        personalBasicDetailsRequest.setType("BASIC_PROFILE");
        personalBasicDetailsRequest.setSubType("BASIC_USER_PROFILE_SERVICE");
        personalBasicDetailsRequest.setSource("string");
        personalBasicDetailsRequest.setCityMasterId(0);
        personalBasicDetailsRequest.setMaritalStatus(mRelation_status);
        personalBasicDetailsRequest.setNoOfChildren(Integer.parseInt(mChild_number));
        personalBasicDetailsRequest.setFirstName(mFirst_name);
        personalBasicDetailsRequest.setLastName( mLast_name);
        mProfilePresenter.getPersonalBasicDetailsAuthTokeInPresenter(personalBasicDetailsRequest);

    }



    @Override
    public void onClick(View view) {
        int id = view.getId();

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



        //will be change

        Toast.makeText(getActivity(), personalBasicDetailsResponse.getStatus(),
                Toast.LENGTH_LONG).show();

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

    }

    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }
}
