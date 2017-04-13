package appliedlife.pvtltd.SHEROES.views.fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileSpinnerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by priyanka on 22/03/17.
 * Personal Basic Details Fragment
 */

public class PersonalBasicDetailsFragment extends BaseFragment implements ProfileView {
    private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
    private final String SCREEN_NAME = "Personal_edit_basic_details_screen";
    @Inject
    ProfilePersenter mProfilePresenter;
    @Bind(R.id.tv_profile_tittle)
    TextView mTvProfileTittle;
    @Bind(R.id.iv_back_profile)
    ImageView mIvBackProfile;
    @Bind(R.id.btn_personal_basic_details_save)
    Button mBtnPersonalBasicDetailsSave;
    @Bind(R.id.et_first_name)
    EditText mEtFirstName;
    @Bind(R.id.et_last_name)
    EditText mEtLastName;
    @Bind(R.id.et_child_number)
    EditText mEtChildNumber;
    @Bind(R.id.et_currnt_location)
    EditText mEtCurrntLocation;
    @Bind(R.id.spinner_relation_status)
    Spinner mSpinnerRelationStatus;
    @Bind(R.id.tv_mobile_value)
    TextView mTvMobileNo;
    @Bind(R.id.tv_email_value)
    TextView mTvEmailNo;
    ProfileView mProfileBasicDetailsCallBack;
    String mCitiId,mcityNm;
    MyProfileView myProfileView;

    private String mFirst_name,mLast_name,mChild_number,mCurrent_location,mRelation_status;


    private static final String[] total_iteam = {
            "Married", "Unmarried"
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        super.onAttach(context);
        try {
            if (getActivity() instanceof ProfileView) {

                mProfileBasicDetailsCallBack = (ProfileView) getActivity();

            }
        } catch (Exception e) {


        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_personal_basicdetails, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);
        mTvProfileTittle.setText(R.string.ID_BASICDETAILS);
        mSpinnerRelationStatus.setAdapter(new ProfileSpinnerAdapter(getActivity(), R.layout.profile_current_status_spinner, total_iteam));
        if(null !=getArguments())

        {
            myProfileView = getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);

            if(StringUtil.isNotNullOrEmptyString(myProfileView.getUserDetails().getName())) {
                String[] splitStr = myProfileView.getUserDetails().getName().split("\\s+");
                if(StringUtil.isNotNullOrEmptyString(splitStr[0])) {
                    mEtFirstName.setText(splitStr[0]);
                }
                if(StringUtil.isNotNullOrEmptyString(splitStr[1])) {
                    mEtLastName.setText(splitStr[1]);
                }
            }
            if(StringUtil.isNotNullOrEmptyString(myProfileView.getUserDetails().getCityMaster())) {
                mEtCurrntLocation.setText(myProfileView.getUserDetails().getCityMaster());
            }
            if(myProfileView.getUserDetails().getNoOfChildren()>=0) {
                mEtChildNumber.setText(""+myProfileView.getUserDetails().getNoOfChildren());
            }
            if(StringUtil.isNotNullOrEmptyString(myProfileView.getUserDetails().getMaritalStatus())) {
               if(myProfileView.getUserDetails().getMaritalStatus().equalsIgnoreCase("Married")) {
                   mSpinnerRelationStatus.setSelection(0);
               }
                else {
                   mSpinnerRelationStatus.setSelection(1);
               }
            }
            if(StringUtil.isNotNullOrEmptyString(myProfileView.getUserDetails().getMobile())) {
                mTvMobileNo.setText(myProfileView.getUserDetails().getMobile());
            }
            if(StringUtil.isNotNullOrEmptyString(myProfileView.getUserDetails().getEmailid())) {
                mTvEmailNo.setText(myProfileView.getUserDetails().getEmailid());
            }
            if(myProfileView.getUserDetails().getCityMasterId()>0) {
                mCitiId=""+myProfileView.getUserDetails().getCityMasterId();
            }


            }
        return view;
    }








    public void submitLocation(String cityId,String city)
    {
        mcityNm=city;
        mCitiId=cityId;
        mEtCurrntLocation.setText(city);

    }


    @OnClick(R.id.btn_personal_basic_details_save)

    public void  Save_Basic_Details()
    {
        mFirst_name= mEtFirstName.getText().toString();
        mLast_name= mEtLastName.getText().toString();
        mChild_number = mEtChildNumber.getText().toString();
        mCurrent_location=mEtCurrntLocation.getText().toString();
        mRelation_status=mSpinnerRelationStatus.getSelectedItem().toString();

      PersonalBasicDetailsRequest personalBasicDetailsRequest = new PersonalBasicDetailsRequest();
      AppUtils appUtils = AppUtils.getInstance();
      personalBasicDetailsRequest.setAppVersion(appUtils.getAppVersionName());
      //TODO:change Messageid
      personalBasicDetailsRequest.setCloudMessagingId(appUtils.getCloudMessaging());
      personalBasicDetailsRequest.setDeviceUniqueId(appUtils.getDeviceId());
      personalBasicDetailsRequest.setLastScreenName("string");
      personalBasicDetailsRequest.setScreenName("string");
      personalBasicDetailsRequest.setType("BASIC_PROFILE");
      personalBasicDetailsRequest.setSubType("BASIC_USER_PROFILE_SERVICE");
      personalBasicDetailsRequest.setSource("string");

        if(StringUtil.isNotNullOrEmptyString(mCitiId)) {
            personalBasicDetailsRequest.setCityMasterId(Integer.parseInt(mCitiId));
        }
      personalBasicDetailsRequest.setMaritalStatus(mRelation_status);
        if(null !=mChild_number && StringUtil.isNotNullOrEmptyString(mChild_number)) {
            personalBasicDetailsRequest.setNoOfChildren(Integer.parseInt(mChild_number));
        }
      personalBasicDetailsRequest.setFirstName(mFirst_name);
      personalBasicDetailsRequest.setLastName(mLast_name);
      mProfilePresenter.getPersonalBasicDetailsAuthTokeInPresenter(personalBasicDetailsRequest);

    }

    @OnClick(R.id.et_currnt_location)
    public void onLocationClick()
    {

        ((ProfileActicity)getActivity()).callProfileLocation();

    }

    @OnClick(R.id.iv_back_profile)

    public void OnbackClick()
    {
        mProfileBasicDetailsCallBack.backListener(R.id.iv_back_profile);

    }


    @Override
    public void onClick(View view) {


        int id = view.getId();

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


        //TODO:Change Message

        Toast.makeText(getActivity(), boardingDataResponse.getStatus(),
                Toast.LENGTH_LONG).show();



        //TODO:Change Message

        if(boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {

            mProfileBasicDetailsCallBack.backListener(0);


        }else {



        }

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
