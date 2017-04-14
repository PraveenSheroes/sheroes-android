package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by priyanka on 27/03/17.
 */

public class ProfileEditVisitingCardFragment extends BaseFragment implements ProfileView {


    @Inject
    ProfilePersenter profilePersenter;
    private final String TAG = LogUtils.makeLogTag(ProfileEditVisitingCardFragment.class);
    ProfileView profileViewlistener;
    String mVisitingResponseValue;
    ProfileEditVisitingCardResponse profileEditVisitingCardResponse;
    @Bind(R.id.tv_profile_tittle)
    TextView mTvProfileTittle;
    @Bind(R.id.btn_save_visitingcards)
    Button mBtnSaveVisitingcards;
    @Bind(R.id.et_visiting_first_name)
    EditText mEtVisitingFirstName;
    @Bind(R.id.et_visiting_last_name)
    EditText mEtVisitingLastName;
    @Bind(R.id.et_visiting_mobile_number)
    EditText mEtVisitingMobileNumber;
    @Bind(R.id.et_visiting_email_name)
    EditText mEtVisitingEmailName;
    @Bind(R.id.et_visiting_location)
    EditText mEt_visiting_location;
    @Bind(R.id.et_visiting_degree)
    EditText mEt_visiting_degree;
    @Bind(R.id.et_school_name)
    EditText mEt_school_name;
    @Bind(R.id.et_visiting_designation)
    EditText mEtVisitingDesignation;
    @Bind(R.id.et_visiting_company_name)
    EditText mEtVisitingCompanyName;
    @Bind(R.id.et_visiting_goot_at)
    EditText mEtVisitingGootAt;



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
        View view = inflater.inflate(R.layout.fragment_personal_visitingcard_editdetails, container, false);
        ButterKnife.bind(this, view);
        profilePersenter.attachView(this);
        mTvProfileTittle.setText(R.string.ID_MY_CONTACT_CARD);

        if (null != getArguments()) {

            mVisitingResponseValue = getArguments().getString("user_visiting_card_value");

            Gson gson = new Gson();
            Type type = new TypeToken<ProfileEditVisitingCardResponse>() {

            }.getType();

            profileEditVisitingCardResponse = gson.fromJson(mVisitingResponseValue, type);
            if (null != profileEditVisitingCardResponse) {

                mEtVisitingFirstName.setText(profileEditVisitingCardResponse.getFirstName());
                mEtVisitingLastName.setText(profileEditVisitingCardResponse.getLastName());
                mEt_school_name.setText(profileEditVisitingCardResponse.getSchool());
                mEtVisitingMobileNumber.setText(profileEditVisitingCardResponse.getMobile());
                mEtVisitingDesignation.setText(profileEditVisitingCardResponse.getCurrentDesignation());
                mEtVisitingEmailName.setText(profileEditVisitingCardResponse.getEmailid());
                mEt_visiting_location.setText(profileEditVisitingCardResponse.getCurrentLocation());
                mEt_visiting_degree.setText(profileEditVisitingCardResponse.getHeighestDegree());
                mEtVisitingGootAt.setText(profileEditVisitingCardResponse.getAboutMe());
                mEtVisitingCompanyName.setText(profileEditVisitingCardResponse.getCurrentCompany());



            }
        }




        mEtVisitingGootAt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (mEtVisitingLastName.getText().length()>0 && mEtVisitingEmailName.getText().length()>0 ) {

                    mBtnSaveVisitingcards.setVisibility(View.VISIBLE);

                }else {


                    mBtnSaveVisitingcards.setVisibility(View.GONE);


                }

                return false;
            }
        });

        return view;
    }









    @OnClick(R.id.btn_save_visitingcards)

  public void Click_SaveVisiting_Details()

  {


      //TODO:Change Request

      if((StringUtil.isNotNullOrEmptyString(mEtVisitingEmailName.getText().toString())&&( StringUtil.isNotNullOrEmptyString(mEtVisitingFirstName.getText().toString()) &&(StringUtil.isNotNullOrEmptyString(mEtVisitingLastName.getText().toString()))))){

          GetUserVisitingCardRequest getUserVisitingCardRequest = new GetUserVisitingCardRequest();
          AppUtils appUtils = AppUtils.getInstance();

          getUserVisitingCardRequest.setAboutMe(profileEditVisitingCardResponse.getAboutMe());
          getUserVisitingCardRequest.setScreenName("string");
          getUserVisitingCardRequest.setCurrentLocation(profileEditVisitingCardResponse.getCurrentLocation());
          getUserVisitingCardRequest.setMobile(mEtVisitingMobileNumber.getText().toString());
          getUserVisitingCardRequest.setFirstName(mEtVisitingFirstName.getText().toString());
          getUserVisitingCardRequest.setLastName(mEtVisitingLastName.getText().toString());
          getUserVisitingCardRequest.setCurrentDesignation(mEtVisitingDesignation.getText().toString());
          getUserVisitingCardRequest.setCurrentCompany(profileEditVisitingCardResponse.getCurrentCompany());
          getUserVisitingCardRequest.setEmailId(mEtVisitingEmailName.getText().toString());
          getUserVisitingCardRequest.setHeighestDegree(profileEditVisitingCardResponse.getHeighestDegree().toString());
          getUserVisitingCardRequest.setSchool("string");
          profilePersenter.getVisitingCardDetailsAuthTokeInPresenter(getUserVisitingCardRequest);

      }else {

          //TODO:check condition

          Toast.makeText(getActivity(), "Please fill up All Details",
                  Toast.LENGTH_LONG).show();
      }



  }

    @OnClick(R.id.iv_back_profile)
    public  void OnclickBackImg()
    {

        profileViewlistener.onBackPressed(R.id.iv_back_profile);


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
}
