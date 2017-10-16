package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LanguageEntity;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserDetails;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.DatePickerForProfile;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.DayPickerProfile;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MonthPickerForProfileDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMUNITY_OWNER;

/**
 * Created by priyanka on 07/03/17.
 */

public class ProfessionalEditBasicDetailsFragment extends BaseFragment implements MonthPickerForProfileDialogFragment.MonthPicker,ProfileView,DayPickerProfile.MyDayPickerListener, DatePickerForProfile.YearPicker {
    private static final String SCREEN_LABEL = "Professional Basic Details Screen";
    private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
    private final String SCREEN_NAME = "Professional_Edit_Basic_Details_screen";
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult;
    @Inject
    ProfilePersenter mprofilePersenter;
    @Bind(R.id.tv_profile_tittle)
    TextView mTvProfileTittle;
    @Bind(R.id.et_year)
    EditText mEtYear;
    @Bind(R.id.et_month)
    EditText mEtMonth;
    @Bind(R.id.et_select_current_status)
    TextInputLayout mEtSelectCurrentStatus;

    @Bind(R.id.et_current_status)
    EditText mCurrentStatus;
    long mCurrentStatusId,mCurrentSectorId,mLanguageNameId;
    int mMonthValue,mYearValue;
    Long mLanguageId;
    @Bind(R.id.et_sector)
    EditText mEtSector;

    @Bind(R.id.progressbar)
    ProgressBar mProgressBar;
    private EditProfileCallable mCallback;
    MyProfileView myProfileView;
    ProfileView mProfileBasicDetailsCallBack;
    ArrayList <LanguageEntity> mlanguagevalue=new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof ProfileView) {

                mProfileBasicDetailsCallBack = (ProfileView) getActivity();

            }
        } catch (Exception e) {


        }
        try {
            mCallback = (EditProfileCallable) getActivity();
        } catch (ClassCastException exception) {
            LogUtils.error("", "Activity must implements ProfileGoodAtListener",exception);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_basic_details, container, false);
        mprofilePersenter.attachView(this);
        ButterKnife.bind(this, view);
        setProgressBar(mProgressBar);
        mTvProfileTittle.setText(R.string.ID_BASICDETAILS);




        if (null != getArguments()) {
            myProfileView = getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);
            if (myProfileView != null && myProfileView.getUserDetails() != null) {
                UserDetails userDetails = myProfileView.getUserDetails();

                if (userDetails != null) {

                    mEtYear.setText(String.valueOf(userDetails.getTotalExp()));
                    mEtMonth.setText(String.valueOf(userDetails.getTotalExpMonth()));
                    mCurrentStatus.setText(userDetails.getJobTag());
                    mEtSector.setText(userDetails.getSector());
                    mCurrentStatusId=userDetails.getJobTagId();
                    mCurrentSectorId=userDetails.getSectorId();
                }
            }
        }

        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
        } else {
            mprofilePersenter.getMasterDataToPresenter();
        }
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_EDIT_BASIC_DETAIL));
        return view;
    }


    public void submitCurrentStatus(String currentStatus,Long currentStatusId)
    {
        mCurrentStatus.setText(currentStatus);
        mCurrentStatusId=currentStatusId;
    }

    public void submitSectorStatus(String currentSector,Long currentSectorId)
    {
        mEtSector.setText(currentSector);
        mCurrentSectorId=currentSectorId;
    }

    //click on star date of experience

    @OnClick(R.id.et_month)
    public void startDateClick()
    {
        MonthPickerForProfileDialogFragment monthPickerForProfileDialogFragment =new MonthPickerForProfileDialogFragment();
        monthPickerForProfileDialogFragment.setListener(this);
        monthPickerForProfileDialogFragment.show(getActivity().getFragmentManager(),"dialog");
    }

    @OnClick(R.id.et_current_status)
    public void clickCurrentStatus()
    {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            ((ProfileActicity) getActivity()).showCurrentStatusDialog(mUserPreferenceMasterData.get().getData());
        }
        else{
            ((ProfileActicity) getActivity()).showCurrentStatusDialog(mapOfResult);

        }
     }

    @OnClick(R.id.et_sector)
    public void clickSectorName()
    {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            ((ProfileActicity) getActivity()).showSectoreDialog(mUserPreferenceMasterData.get().getData());
        }

        else{
            ((ProfileActicity) getActivity()).showSectoreDialog(mapOfResult);
        }


    }

    //Click on end date of experience

    @OnClick(R.id.et_year)
    public void EndDateClick()
    {

/*
        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener((DayPickerProfile.MyDayPickerListener) this);
        dayPickerProfile.show(getActivity().getFragmentManager(),"dialog");*/


        DatePickerForProfile yearPickerProfile=new DatePickerForProfile();
        yearPickerProfile.setListener(this);
        yearPickerProfile.show(getActivity().getFragmentManager(),"dialog");

    }


    @OnClick(R.id.iv_back_profile)
    public void onBackPressed() {


        mProfileBasicDetailsCallBack.onBackPressed(R.id.iv_back_profile);


    }



    @OnClick(R.id.btn_profile_professional_details)

    public void  Save_professional_basic_details()

    {
        ProfessionalBasicDetailsRequest professionalBasicDetailsRequest=new ProfessionalBasicDetailsRequest();
        AppUtils appUtils = AppUtils.getInstance();
        professionalBasicDetailsRequest.setSource(AppConstants.SOURCE_NAME);
        professionalBasicDetailsRequest.setDeviceUniqueId(appUtils.getDeviceId());
        professionalBasicDetailsRequest.setAppVersion(appUtils.getAppVersionName());
        professionalBasicDetailsRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        professionalBasicDetailsRequest.setType("PROF_DETAILS");
        professionalBasicDetailsRequest.setSubType("PROFESSIONAL_DETAILS_SERVICE");

        if (mCurrentStatusId>0) {
            professionalBasicDetailsRequest.setJobTagId(mCurrentStatusId);
        }
        if (mCurrentSectorId>0) {
            professionalBasicDetailsRequest.setSectorId(mCurrentSectorId);
        }
        if (StringUtil.isNotNullOrEmptyString(mEtMonth.getText().toString())) {
            int month=Integer.parseInt(mEtMonth.getText().toString());
            professionalBasicDetailsRequest.setTotalExpMonth(month);
        }
        if (StringUtil.isNotNullOrEmptyString(mEtYear.getText().toString())) {
            int year=Integer.parseInt(mEtYear.getText().toString());
        professionalBasicDetailsRequest.setTotalExpYear(year);
        }
        mprofilePersenter.getProfessionalBasicDetailsAuthTokeInPresenter(professionalBasicDetailsRequest);
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_PROFILE_EDITS, GoogleAnalyticsEventActions.EDITING_BASIC_DETAIL_PROFESSIONAL, AppConstants.EMPTY_STRING);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> apOfResult) {


        this.mapOfResult=apOfResult;


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
        int toastDuration = Toast.LENGTH_LONG;
        switch (boardingDataResponse.getStatus()) {
            case AppConstants.SUCCESS: {
                toastDuration = Toast.LENGTH_SHORT;
                ((ProfileActicity)getActivity()).proffestionalAtBack();
                break;
            }
            case AppConstants.FAILED: {
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), COMMUNITY_OWNER);
                break;
            }
            default: {
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, COMMUNITY_OWNER);
                break;
            }
        }
        Toast.makeText(getActivity(), boardingDataResponse.getStatus(),
                toastDuration).show();
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
    public void onErrorOccurence() {

    }

    @Override
    public void onDateSubmit(String tagsval) {

    }

    @Override
    public void OnMonthPicker(int monthval) {

        mEtMonth.setText(""+monthval);
        mMonthValue = monthval;

    }

    @Override
    public void onDaySubmit(int YearValue) {

        mEtYear.setText(""+YearValue);
        mYearValue=YearValue;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public interface EditProfileCallable {
        void onBasicDetailsUpdate();
    }

}
