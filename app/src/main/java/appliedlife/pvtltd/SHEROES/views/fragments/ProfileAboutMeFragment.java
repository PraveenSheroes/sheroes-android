package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by priyanka on 07/03/17.
 */

public class ProfileAboutMeFragment extends BaseFragment implements ProfileView {

        private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
        private final String SCREEN_NAME = "Profile_About_Me_screen";

    @Inject
    ProfilePersenter mProfilePresenter;
        @Bind(R.id.tv_about_me_tittle)
        TextView mTv_about_me_tittle;
        @Bind(R.id.et_write_about_me)
        TextView mEt_write_about_me;
        @Bind(R.id.btn_save_about_me_details)
        Button mBtn_save_about_me_details;
        ProfileView profileViewlistener;
        String mAbout_Me_Des;


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
            View view = inflater.inflate(R.layout.fragment_personal_aboutme, container, false);
            ButterKnife.bind(this, view);
            mProfilePresenter.attachView(this);
            mTv_about_me_tittle.setText(R.string.ID_ABOUT_ME);
            return view;
        }

    //Click on Edit_Text
    @OnTouch(R.id.et_write_about_me)

    public  boolean OnEdit_Text_Click()
    {
        mBtn_save_about_me_details.setEnabled(true);
        mBtn_save_about_me_details.setBackgroundColor(getResources().getColor(R.color.red));
        return false;

    }

    //Click On Save About_Me Button

    @OnClick(R.id.btn_save_about_me_details)

    public void Save_about_me_function()
    {

        mAbout_Me_Des=mEt_write_about_me.getText().toString();

        if(StringUtil.isNotNullOrEmptyString(mAbout_Me_Des)) {

            UserSummaryRequest userSummaryRequest = new UserSummaryRequest();
            userSummaryRequest.setAppVersion("string");
            userSummaryRequest.setCloudMessagingId("string");
            userSummaryRequest.setDeviceUniqueId("string");
            userSummaryRequest.setLastScreenName("string");
            userSummaryRequest.setScreenName("string");
            userSummaryRequest.setSource("string");
            userSummaryRequest.setType("SUMMARY");
            userSummaryRequest.setSummary(mAbout_Me_Des);
            userSummaryRequest.setSubType("USER_SUMMARY_SERVICE");
            mProfilePresenter.getUserSummaryDetailsAuthTokeInPresenter(userSummaryRequest);

        }

        else

        {
            //will be change
            Toast.makeText(getActivity(), "Please Fill up Details!",
                    Toast.LENGTH_LONG).show();
        }

    }


    //call back_listener

     @OnClick(R.id.tv_about_me_back)

       public  void Onback_Click()
        {

            profileViewlistener.backListener(R.id.tv_about_me_back);

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




        //will be change
        Toast.makeText(getActivity(), userSummaryResponse.getStatus(),
                Toast.LENGTH_LONG).show();


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