package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import javax.inject.Inject;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileAddEditEducationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfessionalAddEducationActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 10/04/17.
 */

public class ProfileAddEditEducationFragment extends BaseFragment implements DayPickerProfile.MyDayPickerListener,ProfileView,View.OnClickListener{
    private final String TAG = LogUtils.makeLogTag(ProfessionalAddEducationActivity.class);
    private final String SCREEN_NAME = "Proffesional_Add_Edi_Education_screen";
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
    @Bind(R.id.et_gpa)
    EditText mEt_Gpa;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgressbar;
    Long mEducationId,mDeegreeId,mFieldOfStudyId,mSchoolNameId;


    String mDegree_details,mSchool_details,mField_of_studay,mdegreeId,mdegreeName,mSchoolId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_addeducation, container, false);
        ButterKnife.bind(this, view);

        mTvProfileTittle.setText(R.string.ID_ADD_EDUCATION);
        mProfilePresenter.attachView(this);
        setProgressBar(mProgressbar);

        if(null !=getArguments())

        {

            myProfileView=getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);
            List<EducationEntity> educationEntity = this.myProfileView.getEducationEntity();

            if (null != educationEntity) {
                if (StringUtil.isNotEmptyCollection(educationEntity)) {
                    if(educationEntity.get(0).getId()>0)
                    {
                        mEducationId=educationEntity.get(0).getId();
                    }
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getDegree())) {
                        mEtDegreeDetails.setText(educationEntity.get(0).getDegree());

                    }
                    if(educationEntity.get(0).getDegreeNameMasterId()>0)
                    {
                        mDeegreeId=educationEntity.get(0).getDegreeNameMasterId();
                    }
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getSchool())) {
                        mEtSchoolDetails.setText(educationEntity.get(0).getSchool());
                    }

                    if(educationEntity.get(0).getSchoolNameMasterId()>0)
                    {
                        mSchoolNameId=educationEntity.get(0).getSchoolNameMasterId();
                    }
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getFieldOfStudy())) {
                        mEtFieldOfStudy.setText(educationEntity.get(0).getFieldOfStudy());
                    }
                    if(educationEntity.get(0).getFieldOfStudyMasterId()>0)
                    {
                        mFieldOfStudyId=educationEntity.get(0).getFieldOfStudyMasterId();
                    }

                    if(StringUtil.isNotNullOrEmptyString(""+educationEntity.get(0).getSessionStartYear())) {
                        mEtJobStartDate.setText(""+educationEntity.get(0).getSessionStartYear());
                    }

                    if(StringUtil.isNotNullOrEmptyString(""+educationEntity.get(0).getSessionEndYear())) {
                        mEtJobEndDate.setText(""+educationEntity.get(0).getSessionEndYear());
                    }
                    if(StringUtil.isNotNullOrEmptyString(""+educationEntity.get(0).getGrade())) {
                        mEt_Gpa.setText(""+educationEntity.get(0).getGrade());
                    }


                }


            }
        }



return view;

    }



    //TODO:Change request

    @OnClick(R.id.btn_save_education_details_)


    public void btn_Onclick(){

        mDegree_details=  mEtDegreeDetails.getText().toString();
        mSchool_details=  mEtSchoolDetails.getText().toString();
        mField_of_studay= mEtFieldOfStudy.getText().toString();
        if(null !=myProfileView)
        {
            editEducationRequest();
        }
        else {
            addEducationRequest();
        }





    }
    private void addEducationRequest()
    {
        ProfileAddEditEducationRequest profileAddEditEducationRequest = new ProfileAddEditEducationRequest();
        profileAddEditEducationRequest.setAppVersion("string");
        profileAddEditEducationRequest.setCloudMessagingId("string");
        profileAddEditEducationRequest.setDeviceUniqueId("string");
        profileAddEditEducationRequest.setLastScreenName("string");
        profileAddEditEducationRequest.setScreenName("string");
        profileAddEditEducationRequest.setType("EDUCATION");
        profileAddEditEducationRequest.setSubType("BaseProfileRequest");
        mProfilePresenter.getEducationDetailsAuthTokeInPresenter(profileAddEditEducationRequest);

    }
    private void editEducationRequest()
    {
        AppUtils appUtils = AppUtils.getInstance();
        ProfileAddEditEducationRequest profileAddEditEducationRequest = new ProfileAddEditEducationRequest();
        profileAddEditEducationRequest.setAppVersion(appUtils.getAppVersionName());
        profileAddEditEducationRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        profileAddEditEducationRequest.setDeviceUniqueId(appUtils.getDeviceId());
        profileAddEditEducationRequest.setLastScreenName("string");
        profileAddEditEducationRequest.setScreenName(TAG);
        profileAddEditEducationRequest.setType("EDUCATION");
        profileAddEditEducationRequest.setSubType("BaseProfileRequest");

       mProfilePresenter.getEducationDetailsAuthTokeInPresenter(AppUtils.profileAddEditEducationRequest(mEducationId,mSchoolNameId,mDeegreeId,mField_of_studay));
    }

    @OnClick(R.id.et_degree_details)
    public void clickOnDegree()
    {
        ((ProfileActicity)getActivity()).callProfileDegree();
    }
    public void submitDegree(String degreeId,String degree)
    {
        mdegreeId=degreeId;
        mdegreeName=degree;
        mEtDegreeDetails.setText(mdegreeName);

    }
    @OnClick(R.id.et_school_details)
    public void clickOnSchool()
    {
        ((ProfileActicity)getActivity()).callProfileSchool();
    }
    public void submitSchool(String schoolId,String school)
    {
        mSchoolId=schoolId;
        mdegreeName=school;
        mEtSchoolDetails.setText(school);

    }
    @OnClick(R.id.et_field_of_study)
    public void clickOnStudy()
    {
        ((ProfileActicity)getActivity()).callProfileStudy();
    }
    public void submitStudy(String mStudyId,String school)
    {
        mStudyId=mStudyId;
        mdegreeName=school;
        mEtFieldOfStudy.setText(school);

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onDaySubmit(int tagsval) {

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


        //TODO:check condition

        Toast.makeText(getActivity(), boardingDataResponse.getStatus(),
                Toast.LENGTH_LONG).show();

        //TODO:Change Message

        if(boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {

           // profileWorkLocationFragmentListener.locationBack();

        }else {

        }




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
}
