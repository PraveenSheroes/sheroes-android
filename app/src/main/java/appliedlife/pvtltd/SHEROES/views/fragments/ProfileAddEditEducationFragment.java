package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
    EducationEntity educationEntity;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgressbar;
    @Bind(R.id.et_write_desc)
    EditText mEtWriteAboutMe;
    @Bind(R.id.charecter_cout_number)
    TextView mCharecterCountNumber;
    long mEducationId,mDeegreeId,mFieldOfStudyId,mSchoolNameId,mdegreeId;
    int mDateFlag=0,mCurrentlyFlag=0;
    String mStartMonth,mStartYear,mEndMonth,mEndYear,mSchoolName,mStudyName;
    String[] mStartTime,mEndTime;

    String mDegree_details,mSchool_details,mField_of_studay,mdegreeName,mSchoolId;
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

            educationEntity=getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);

            if (null != educationEntity) {
                    if (educationEntity.getId() > 0) {
                        mEducationId = educationEntity.getId();
                    }
                    if (StringUtil.isNotNullOrEmptyString(educationEntity.getDegree())) {
                        mEtDegreeDetails.setText(educationEntity.getDegree());

                    }
                    if (educationEntity.getDegreeNameMasterId() > 0) {
                        mDeegreeId = educationEntity.getDegreeNameMasterId();
                    }
                    if (StringUtil.isNotNullOrEmptyString(educationEntity.getSchool())) {
                        mSchoolName=educationEntity.getSchool();
                        mEtSchoolDetails.setText(educationEntity.getSchool());
                    }

                    if (educationEntity.getSchoolNameMasterId() > 0) {
                        mSchoolNameId = educationEntity.getSchoolNameMasterId();
                    }
                    if (StringUtil.isNotNullOrEmptyString(educationEntity.getFieldOfStudy())) {
                        mEtFieldOfStudy.setText(educationEntity.getFieldOfStudy());
                        mStudyName=educationEntity.getFieldOfStudy();
                    }
                    if (educationEntity.getFieldOfStudyMasterId() > 0) {
                        mFieldOfStudyId = educationEntity.getFieldOfStudyMasterId();
                    }

                    if (educationEntity.getSessionStartYear()>0) {
                        mEtJobStartDate.setText(educationEntity.getSessionStartMonth()+ "-" + educationEntity.getSessionStartYear());
                        mStartTime = mEtJobStartDate.getText().toString().split("-");

                    }

                    if (educationEntity.getSessionEndYear()>0) {
                        mEtJobEndDate.setText(educationEntity.getSessionEndMonth()+ "-" + educationEntity.getSessionEndYear());
                        mEndTime = mEtJobEndDate.getText().toString().split("-");
                    }
                    if (StringUtil.isNotNullOrEmptyString(educationEntity.getDescription())) {
                        mEtWriteAboutMe.setText(educationEntity.getDescription());
                    }


            }
        }

        mEtWriteAboutMe.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {

                    mCharecterCountNumber.setVisibility(View.VISIBLE);
                    mCharecterCountNumber.setText(String.valueOf(AppConstants.MAX_WORD_COUNTER - s.toString().length()));

                } else {

                    mCharecterCountNumber.setVisibility(View.GONE);
                }
            }
        });


return view;

    }


    @OnClick(R.id.iv_back_profile)
    public void backClick()
    {
        ((ProfileActicity)getActivity()).getSupportFragmentManager().popBackStack();
    }
    //TODO:Change request

    @OnClick(R.id.btn_save_education_details_)
    public void btn_Onclick(){

        mDegree_details=  mEtDegreeDetails.getText().toString();
        mSchool_details=  mEtSchoolDetails.getText().toString();
        mField_of_studay= mEtFieldOfStudy.getText().toString();
        if(null != educationEntity)
        {
            editEducationRequest();
        }
        else {
            addEducationRequest();
        }

    }
    @OnClick(R.id.cb_working_from)
    public void checkCurrently()
    {
        if(mCbWorkingFrom.isChecked()) {
            mEtJobEndDate.setVisibility(View.GONE);
        }
        else{
            mEtJobEndDate.setVisibility(View.VISIBLE);

        }
    }
    @OnClick(R.id.et_job_start_date)
    public void startDateClick()
    {
        mDateFlag=1;
        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener(this);
        dayPickerProfile.show(getActivity().getFragmentManager(),"dialog");
    }


    //Click on end date of job

    @OnClick(R.id.et_job_end_date)
    public void EndDateClick()
    {
        mDateFlag=2;
        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener(this);
        dayPickerProfile.show(getActivity().getFragmentManager(),"dialog");


    }
    private void addEducationRequest()
    {
        mProfilePresenter.getEducationDetailsAuthTokeInPresenter(AppUtils.profileAddEducationRequest(mSchoolName,mdegreeId,mStudyName,mStartTime,mEndTime,mEtWriteAboutMe.getText().toString()));

    }
    private void editEducationRequest()
    {
       mProfilePresenter.getEducationDetailsAuthTokeInPresenter(AppUtils.profileEditEducationRequest(mEducationId,mSchoolName,mDeegreeId,mStudyName,mStartTime,mEndTime,mEtWriteAboutMe.getText().toString()));
    }

    @OnClick(R.id.et_degree_details)
    public void clickOnDegree()
    {
        ((ProfileActicity)getActivity()).callProfileDegree();
    }
    public void submitDegree(String degreeId,String degree)
    {
        mdegreeId=Long.parseLong(degreeId);
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
        mSchoolName=school;
        mEtSchoolDetails.setText(school);

    }
    @OnClick(R.id.et_field_of_study)
    public void clickOnStudy()
    {
        ((ProfileActicity)getActivity()).callProfileStudy();
    }
    public void submitStudy(String mStudyId,String study)
    {
        mStudyId=mStudyId;
        mStudyName=study;
        mEtFieldOfStudy.setText(study);

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onDateSubmit(String tagsval) {
        if(mDateFlag==1)
        {
            mStartTime = tagsval.split("-");
            mEtJobStartDate.setText(tagsval);
        }
        else if(mDateFlag==2){
            mEndTime = tagsval.split("-");
            mEtJobEndDate.setText(tagsval);
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
