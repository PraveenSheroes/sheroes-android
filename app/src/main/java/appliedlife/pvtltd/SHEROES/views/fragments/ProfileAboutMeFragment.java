package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
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
    @Bind(R.id.et_write_about_me)
    EditText mEtWriteAboutMe;
    @Bind(R.id.btn_save_about_me_details)
    Button mBtnSaveAboutMeDetails;
    @Bind(R.id.charecter_cout_number)
    TextView mCharecterCountNumber;
    @Bind(R.id.iv_write_about_line)
    ImageView mIvWriteAboutLine;
    @Bind(R.id.tv_about_me_tittle)
    TextView mCollapeTitleTxt;
    @Bind(R.id.al_community_open_about)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgress;
    @Bind(R.id.iv_profile_image_about_me)
    CircleImageView mIvProfileImage;
    public CustomCollapsingToolbarLayout mProfileAboutMe;
    @Inject
    Preference<LoginResponse> mUserPreference;
    private ProfileAboutMeFragmentListener profileAboutMeFragmentListener;


    String mAbout_Me_Des;

    private MyProfileView mProfileView;

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
        try {
            mProfileView = (MyProfileView) getArguments().getParcelable(AppConstants.MODEL_KEY);
        } catch (ClassCastException ex) {
            LogUtils.error(TAG, "Error while casting MyProfileView", ex);
        }
        View view = inflater.inflate(R.layout.fragment_personal_aboutme, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);
        setProgressBar(mProgress);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapeTitleTxt.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    mCollapeTitleTxt.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });

        setData();
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

    private void setData() {
        if (mProfileView != null && mProfileView.getUserDetails() != null) {
            mEtWriteAboutMe.setText(mProfileView.getUserDetails().getUserSummary());
        }

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {

            mIvProfileImage.setCircularImage(true);
            mIvProfileImage.bindImage(mUserPreference.get().getUserSummary().getPhotoUrl());

        }

    }

    @OnTouch(R.id.et_write_about_me)
    public boolean OnEdit_Text_Click() {
        mIvWriteAboutLine.setBackgroundColor(getResources().getColor(R.color.blue));
        mBtnSaveAboutMeDetails.setEnabled(true);
        mBtnSaveAboutMeDetails.setBackgroundColor(getResources().getColor(R.color.red));
        mBtnSaveAboutMeDetails.setVisibility(View.VISIBLE);
        return false;
    }


    //Click On Save About_Me Button

    @OnClick(R.id.btn_save_about_me_details)

    public void Save_about_me_function() {

        mAbout_Me_Des = mEtWriteAboutMe.getText().toString();

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

        profileAboutMeFragmentListener.aboutMeBack();

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
        int toastDuration;
        if (boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {
            toastDuration = Toast.LENGTH_SHORT;
            profileAboutMeFragmentListener.aboutMeBack();
        } else {
            toastDuration = Toast.LENGTH_LONG;
        }
        //TODO:TBD for toast showing
        Toast.makeText(getActivity(), boardingDataResponse.getStatus(),
                toastDuration).show();
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


    public interface ProfileAboutMeFragmentListener {

        void onErrorOccurence();

        void aboutMeBack();
    }


}