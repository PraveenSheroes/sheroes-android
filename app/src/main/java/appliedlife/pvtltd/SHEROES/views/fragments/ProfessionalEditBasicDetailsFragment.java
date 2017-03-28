package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 07/03/17.
 */

public class ProfessionalEditBasicDetailsFragment extends BaseFragment implements ProfileView{
    private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
    private final String SCREEN_NAME = "Professional_Edit_Basic_Details_screen";


    @Bind(R.id.tv_profile_tittle)
    TextView mTv_profile_tittle;
    @Bind(R.id.et_year)
    EditText mEt_year;
    @Bind(R.id.et_month)
    EditText mEt_month;
    ProfileView profileViewlistener;
    @Inject
    ProfilePersenter mprofilePersenter;





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
        mTv_profile_tittle.setText(R.string.ID_BASICDETAILS);
        return view;
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





    @OnClick(R.id.et_year)
    public void Click_On_Year()

    {

        /*DatePickerForProfile datePickerForProfile = new DatePickerForProfile();
        datePickerForProfile.setListener(this);
        datePickerForProfile.show(getActivity().getFragmentManager(), "MonthYearPickerDialog");
*/
    }



    @OnClick(R.id.iv_back_profile)

    public  void Onback_Click()
    {

        //profileViewlistener.backListener(R.id.tv_profile_education_back);
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
}
