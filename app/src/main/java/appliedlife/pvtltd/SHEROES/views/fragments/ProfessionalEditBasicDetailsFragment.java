package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import appliedlife.pvtltd.SHEROES.models.entities.community.PopularTag;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.materialspinner.MaterialSpinner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 07/03/17.
 */

public class ProfessionalEditBasicDetailsFragment extends BaseFragment implements ProfileView,DayPickerProfile.MyDayPickerListener {
    private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
    private final String SCREEN_NAME = "Professional_Edit_Basic_Details_screen";
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult;

    @Inject
    ProfilePersenter mprofilePersenter;
    ProfileView profileViewlistener;
    @Bind(R.id.tv_profile_tittle)
    TextView mTvProfileTittle;
    @Bind(R.id.et_year)
    EditText mEtYear;
    @Bind(R.id.et_month)
    EditText mEtMonth;
    @Bind(R.id.et_select_current_status)
    TextInputLayout mEtSelectCurrentStatus;
    @Bind(R.id.tv_language_delete)
    TextView mTvLanguageDelete;
    @Bind(R.id.et_add_Proficient)
    EditText mEtAddProficient;
    @Bind(R.id.l1_add_language)
    LinearLayout mR1AddLanguage;
    @Bind(R.id.et_current_status)
    EditText mCurrentStatus;
    Long mCurrentStatusId,mCurrentSectorId;
    @Bind(R.id.et_sector)
    EditText mEtSector;


    @Override
    public void onAttach(Context context) {

        try {

            if (getActivity() instanceof ProfileView) {
                profileViewlistener = (ProfileView) getActivity();
            }

        } catch (InstantiationException exception) {
        }
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_basic_details, container, false);
        mprofilePersenter.attachView(this);
        ButterKnife.bind(this, view);
        mTvProfileTittle.setText(R.string.ID_BASICDETAILS);
        mR1AddLanguage.setVisibility(View.GONE);

        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
        } else {
            mprofilePersenter.getMasterDataToPresenter();
        }

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
        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener(this);
        dayPickerProfile.show(getActivity().getFragmentManager(),"dialog");
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

        DayPickerProfile dayPickerProfile=new DayPickerProfile();
        dayPickerProfile.setListener(this);
        dayPickerProfile.show(getActivity().getFragmentManager(),"dialog");


    }


    @OnClick(R.id.tv_language_delete)
    public void OnClickCrossLanguageIcon()
    {
        mR1AddLanguage.setVisibility(View.GONE);



    }


    @OnClick(R.id.add_language)
    public void AddLanguage()
    {
        mR1AddLanguage.setVisibility(View.VISIBLE);



    }



    @OnClick(R.id.iv_back_profile)

    public  void Onback_Click()
    {

        profileViewlistener.backListener(R.id.iv_back_profile);
    }



    @OnClick(R.id.btn_profile_professional_details)

    public void  Save_professional_basic_details()

    {
        ProfessionalBasicDetailsRequest professionalBasicDetailsRequest=new ProfessionalBasicDetailsRequest();
        professionalBasicDetailsRequest.setSource("string");
        professionalBasicDetailsRequest.setDeviceUniqueId("string");
        professionalBasicDetailsRequest.setAppVersion("string");
        professionalBasicDetailsRequest.setCloudMessagingId("string");
        professionalBasicDetailsRequest.setSource("string");
        professionalBasicDetailsRequest.setLastScreenName("string");
        professionalBasicDetailsRequest.setScreenName("string");
        professionalBasicDetailsRequest.setType("PROF_DETAILS");
        professionalBasicDetailsRequest.setSubType("PROFESSIONAL_DETAILS_SERVICE");
        professionalBasicDetailsRequest.setJobTag("string");
        professionalBasicDetailsRequest.setJobTagId(0);
        professionalBasicDetailsRequest.setSector("string");
        professionalBasicDetailsRequest.setMaritalStatus("string");
        professionalBasicDetailsRequest.setTotalExp(2);
        mprofilePersenter.getProfessionalBasicDetailsAuthTokeInPresenter(professionalBasicDetailsRequest);


    }
    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> apOfResult) {
        this.mapOfResult=apOfResult;
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
    public void onErrorOccurence() {

    }

    @Override
    public void onDaySubmit(int tagsval) {

    }
}
