package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
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
    /* @Bind(R.id.tv_about_me_tittle)
     TextView mTv_about_me_tittle;*/
    @Bind(R.id.et_write_about_me)
    TextView mEt_write_about_me;
    @Bind(R.id.btn_save_about_me_details)
    Button mBtn_save_about_me_details;
    @Bind(R.id.charecter_cout_number)
    TextView charecter_cout_number;
    @Bind(R.id.iv_write_about_line)
    ImageView mIv_write_about_line;
    @Bind(R.id.tv_about_me_tittle)
    TextView mTv_about_me_tittle;

    @Bind(R.id.cl_profile_about_me)


    public CustomCollapsingToolbarLayout ctProfileAboutMe;

    private ProfileAboutMeFragmentListener profileAboutMeFragmentListener;


    String mAbout_Me_Des;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {
            if (getActivity() instanceof ProfileAboutMeFragmentListener) {

                profileAboutMeFragmentListener = (ProfileAboutMeFragmentListener) getActivity();

            }
        } catch (Exception e) {


        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_personal_aboutme, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);
        // mTv_about_me_tittle.setText(R.string.ID_ABOUT_ME);

        //TODO:Change Subtittle
        ctProfileAboutMe.setExpandedSubTitleColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        ctProfileAboutMe.setExpandedTitleColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));

        ctProfileAboutMe.setTitle("ABOUT ME");
        ctProfileAboutMe.setSubtitle("ABOUT ME");
        mTv_about_me_tittle.setText(R.string.ID_ABOUT_ME);

        mEt_write_about_me.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {

                    charecter_cout_number.setVisibility(View.VISIBLE);
                    charecter_cout_number.setText(String.valueOf(AppConstants.MAX_WORD_COUNTER - s.toString().length()));

                } else {

                    charecter_cout_number.setVisibility(View.GONE);
                }
            }
        });


        return view;

    }

    //Click on Edit_Text
    @OnTouch(R.id.et_write_about_me)

    public boolean OnEdit_Text_Click() {

        mIv_write_about_line.setBackgroundColor(getResources().getColor(R.color.blue));
        mBtn_save_about_me_details.setEnabled(true);
        mBtn_save_about_me_details.setBackgroundColor(getResources().getColor(R.color.red));
        mBtn_save_about_me_details.setVisibility(View.VISIBLE);

        return false;
    }


    //Click On Save About_Me Button

    @OnClick(R.id.btn_save_about_me_details)

    public void Save_about_me_function() {

        mAbout_Me_Des = mEt_write_about_me.getText().toString();

        if (StringUtil.isNotNullOrEmptyString(mAbout_Me_Des)) {

            UserSummaryRequest userSummaryRequest = new UserSummaryRequest();
            AppUtils appUtils = AppUtils.getInstance();
            userSummaryRequest.setAppVersion(appUtils.getAppVersionName());
            //TODO:Check Cloud
            userSummaryRequest.setCloudMessagingId(appUtils.getCloudMessaging());
            userSummaryRequest.setDeviceUniqueId(appUtils.getDeviceId());
            userSummaryRequest.setLastScreenName(AppConstants.STRING);
            userSummaryRequest.setScreenName(AppConstants.STRING);
            userSummaryRequest.setSource(AppConstants.STRING);
            userSummaryRequest.setType(AppConstants.SUMMARY);
            userSummaryRequest.setSummary(mAbout_Me_Des);
            userSummaryRequest.setSubType(AppConstants.USER_SUMMARY_SERVICE);
            mProfilePresenter.getUserSummaryDetailsAuthTokeInPresenter(userSummaryRequest);

        } else

        {
            //TODO:Change Message
            Toast.makeText(getActivity(), "Please Fill up Details!",
                    Toast.LENGTH_LONG).show();
        }

    }


    //call back_listener
    @OnClick(R.id.tv_about_me_back)

    public void Onback_Click() {

        profileAboutMeFragmentListener.AboutMeBack();

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


        //TODO:Change Message
        Toast.makeText(getActivity(), userSummaryResponse.getStatus(),
                Toast.LENGTH_LONG).show();


        if (userSummaryResponse.getStatus().equals(AppConstants.SUCCESS)) {


            profileAboutMeFragmentListener.AboutMeBack();

        }

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


    public interface ProfileAboutMeFragmentListener {

        void onErrorOccurence();

        void AboutMeBack();
    }


}