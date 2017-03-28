package appliedlife.pvtltd.SHEROES.views.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
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
import appliedlife.pvtltd.SHEROES.views.fragments.DayPickerDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.DayPickerProfile;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;


/**
 * Created by priyanka on 07/03/17.
 */

public class ProfessionalAddEducationActivity extends BaseActivity implements DayPickerProfile.MyDayPickerListener,ProfileView,View.OnClickListener
{   private final String TAG = LogUtils.makeLogTag(ProfessionalAddEducationActivity.class);
    private final String SCREEN_NAME = "Proffesional_Add_Education_screen";
    @Inject
    ProfilePersenter mProfilePresenter;
    @Bind(R.id.tv_profile_tittle)
    TextView mTv_profile_tittle;
    @Bind(R.id.et_degree_details)
    EditText mEt_degree_details;
    @Bind(R.id.et_school_details)
    EditText mEt_school_details;
    @Bind(R.id.et_field_of_study)
    EditText mEt_field_of_study;
    @Bind(R.id.et_job_start_date)
    EditText et_job_start_date;
    String mDegree_details,mSchool_details,mField_of_studay;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SheroesApplication.getAppComponent(this).inject(this);

        renderAddEducationFragmentView();

    }

    public void renderAddEducationFragmentView() {
        setContentView(R.layout.fragment_professional_addeducation);
        ButterKnife.bind(this);
        mTv_profile_tittle.setText(R.string.ID_ADD_EDUCATION);
        mProfilePresenter.attachView(this);
    }

    @OnClick(R.id.iv_back_profile)
    public void backOnclick()
    {


        //overridePendingTransition(R.anim.top_to_bottom_exit,R.anim.top_bottom_exit_anim);
        finish();

    }


    @OnClick(R.id.btn_save_education_details_)


    public void btn_Onclick()


    {
        mDegree_details=  mEt_degree_details.getText().toString();
        mSchool_details=  mEt_school_details.getText().toString();
        mField_of_studay= mEt_field_of_study.getText().toString();

        PersonalBasicDetailsRequest personalBasicDetailsRequest = new PersonalBasicDetailsRequest();
        personalBasicDetailsRequest.setAppVersion("string");
        personalBasicDetailsRequest.setCloudMessagingId("string");
        personalBasicDetailsRequest.setDeviceUniqueId("string");
        personalBasicDetailsRequest.setLastScreenName("string");
        personalBasicDetailsRequest.setScreenName("string");
        personalBasicDetailsRequest.setType("EDUCATION");
        personalBasicDetailsRequest.setSubType("BaseProfileRequest");
        mProfilePresenter.getUserDetailsAuthTokeInPresenter(personalBasicDetailsRequest);



    }
    @OnClick(R.id.et_job_start_date)
    public void startDateClick()
    {
        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener(this);
        dayPickerProfile.show(this.getFragmentManager(),"dialog");
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

    }

    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

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
    public void onErrorOccurence() {

    }

    @Override
    public void onDaySubmit(int tagsval) {

    }
}
