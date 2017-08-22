package appliedlife.pvtltd.SHEROES.views.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.WorkExpListResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.DayPickerProfile;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by priyanka on 07/03/17.
 */

public class ProfessionalAddEducationActivity extends BaseActivity implements DayPickerProfile.MyDayPickerListener,ProfileView
{   private final String TAG = LogUtils.makeLogTag(ProfessionalAddEducationActivity.class);
    private final String SCREEN_NAME = "Proffesional_Add_Education_screen";
    @Inject
    ProfilePersenter mProfilePresenter;
    @Bind(R.id.tv_profile_tittle)
    TextView mTvProfileTittle;
    @Bind(R.id.et_degree_details)
    EditText mEtDegreeDetails;
    @Bind(R.id.et_school_details)
    EditText mEtSchoolDetails;
    @Bind(R.id.et_field_of_study)
    EditText mEtFieldOfStudy;
    @Bind(R.id.et_job_start_date)
    EditText mEtJobStartDate;
    @Bind(R.id.et_job_end_date)
    EditText mEtJobEndDate;
    @Bind(R.id.cb_working_from)
    CheckBox mCbWorkingFrom;
    MyProfileView myProfileView;



    String mDegree_details,mSchool_details,mField_of_studay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderAddEducationFragmentView();
        ((SheroesApplication)this.getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_EDIT_EDUCATION));
    }

    public void renderAddEducationFragmentView() {
        setContentView(R.layout.fragment_professional_addeducation);
        ButterKnife.bind(this);
        mTvProfileTittle.setText(R.string.ID_ADD_EDUCATION);
        mProfilePresenter.attachView(this);

        if (getIntent() != null && getIntent().getExtras() != null) {


            myProfileView = getIntent().getExtras().getParcelable(AppConstants.EDUCATION_PROFILE);
            List<EducationEntity> educationEntity = this.myProfileView.getEducationEntity();

            if (null != educationEntity) {
                if (StringUtil.isNotEmptyCollection(educationEntity)) {
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getDegree())) {
                        mEtDegreeDetails.setText(educationEntity.get(0).getDegree());
                    }

                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getSchool())) {
                        mEtSchoolDetails.setText(educationEntity.get(0).getSchool());
                    }


                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getFieldOfStudy())) {
                        mEtFieldOfStudy.setText(educationEntity.get(0).getFieldOfStudy());
                    }
                }


            }
         }
        }






    @OnClick(R.id.iv_back_profile)
    public void backOnclick()
    {


        //overridePendingTransition(R.anim.top_to_bottom_exit,R.anim.top_bottom_exit_anim);
        finish();

    }

//TODO:Change request

    @OnClick(R.id.btn_save_education_details_)


    public void btn_Onclick()


    {
       /* mDegree_details=  mEtDegreeDetails.getText().toString();
        mSchool_details=  mEtSchoolDetails.getText().toString();
        mField_of_studay= mEtFieldOfStudy.getText().toString();
        PersonalBasicDetailsRequest personalBasicDetailsRequest = new PersonalBasicDetailsRequest();
        personalBasicDetailsRequest.setAppVersion("string");
        personalBasicDetailsRequest.setCloudMessagingId("string");
        personalBasicDetailsRequest.setDeviceUniqueId("string");
        personalBasicDetailsRequest.setLastScreenName("string");
        personalBasicDetailsRequest.setScreenName("string");
        personalBasicDetailsRequest.setType("EDUCATION");
        personalBasicDetailsRequest.setSubType("BaseProfileRequest");
        mProfilePresenter.getEducationDetailsAuthTokeInPresenter(personalBasicDetailsRequest);*/
    }


    //click on star date of job

    @OnClick(R.id.et_job_start_date)
    public void startDateClick()
    {
        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener(this);
        dayPickerProfile.show(this.getFragmentManager(),"dialog");
    }


    //Click on end date of job

    @OnClick(R.id.et_job_end_date)
    public void EndDateClick()
    {

        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener(this);
        dayPickerProfile.show(this.getFragmentManager(),"dialog");


    }


    //click on check box of current working from


    @OnClick(R.id.cb_working_from)
    public void ClickOnCheckBox()
    {
        if (mCbWorkingFrom.isChecked())
        {

            mEtJobEndDate.setVisibility(View.GONE);
           //mEtJobStartDate.setVisibility(View.GONE);

        }else {

            mEtJobEndDate.setVisibility(View.VISIBLE);
            //mEtJobStartDate.setVisibility(View.VISIBLE);
        }


    }
    @Override
    public void onBackPressed(int id) {

    }

    @Override
    public void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void callFragment(int id) {

    }

    @Override
    public void getEducationResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getprofiletracelflexibilityResponse(BoardingDataResponse boardingDataResponse) {

    }



    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalWorkLocationResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {

    }


    @Override
    public void getProfileListSuccess(GetTagData getAllData) {

    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {

    }

    @Override
    public void getWorkExpListSuccess(WorkExpListResponse workExpListResponse) {

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
    public void onDateSubmit(String tagsval) {

    }


}
