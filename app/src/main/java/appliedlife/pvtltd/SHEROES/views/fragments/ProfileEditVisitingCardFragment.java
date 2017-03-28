package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.SUCCESS;


/**
 * Created by priyanka on 27/03/17.
 */



public class ProfileEditVisitingCardFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(ProfileEditVisitingCardFragment.class);


    ProfileView profileViewlistener;
    String mVisitingResponseValue;
    ProfileEditVisitingCardResponse profileEditVisitingCardResponse;
    @Bind(R.id.tv_profile_tittle)
    TextView mTv_profile_tittle;
    @Bind(R.id.btn_save_visitingcards)
    Button mBtn_save_visitingcards;
    @Bind(R.id.et_visiting_first_name)
    EditText mEt_visiting_first_name;
    @Bind(R.id.et_visiting_last_name)
    EditText mEt_visiting_last_name;
    @Bind(R.id.et_visiting_mobile_number)
    EditText mEt_visiting_mobile_number;
    @Bind(R.id.et_visiting_email_name)
    EditText mEt_visiting_email_name;
    @Bind(R.id.et_visiting_location)
    EditText mEt_visiting_location;
    @Bind(R.id.et_visiting_degree)
    EditText mEt_visiting_degree;
    @Bind(R.id.et_school_name)
    EditText mEt_school_name;
    @Bind(R.id.et_visiting_designation)
    EditText mEt_visiting_designation;
    @Bind(R.id.et_visiting_company_name)
    EditText mEt_visiting_company_name;
    @Bind(R.id.et_visiting_goot_at)
    EditText mEt_visiting_goot_at;

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
        mTv_profile_tittle.setText(R.string.ID_MY_CONTACT_CARD);


        if (null != getArguments()) {

            mVisitingResponseValue = getArguments().getString("user_visiting_card_value");

            Gson gson = new Gson();
            Type type = new TypeToken<ProfileEditVisitingCardResponse>() {

            }.getType();

            profileEditVisitingCardResponse = gson.fromJson(mVisitingResponseValue, type);
            if (null != profileEditVisitingCardResponse) {

                mEt_visiting_first_name.setText(profileEditVisitingCardResponse.getFirstName());
                mEt_visiting_last_name.setText(profileEditVisitingCardResponse.getLastName());
                mEt_school_name.setText(profileEditVisitingCardResponse.getSchool());
                mEt_visiting_mobile_number.setText(profileEditVisitingCardResponse.getMobile());
                mEt_visiting_designation.setText(profileEditVisitingCardResponse.getCurrentDesignation());
                mEt_visiting_email_name.setText(profileEditVisitingCardResponse.getEmailid());
            }
        }

        return view;
    }

    @OnClick(R.id.btn_save_visitingcards)

  public void Click_SaveVisiting_Details()

  {
      GetUserVisitingCardRequest getUserVisitingCardRequest = new GetUserVisitingCardRequest();
      getUserVisitingCardRequest.setAboutMe("string");
      getUserVisitingCardRequest.setScreenName("string");
      getUserVisitingCardRequest.setCurrentCompany("string");
      getUserVisitingCardRequest.setCurrentDesignation("string");
      getUserVisitingCardRequest.setCurrentLocation("string");
      getUserVisitingCardRequest.setMobile(mEt_visiting_mobile_number.getText().toString());
      getUserVisitingCardRequest.setFirstName(mEt_visiting_first_name.getText().toString());
      getUserVisitingCardRequest.setLastName(mEt_visiting_last_name.getText().toString());
      getUserVisitingCardRequest.setMobile(mEt_visiting_mobile_number.getText().toString());
      getUserVisitingCardRequest.setCurrentDesignation(mEt_visiting_designation.getText().toString());
      getUserVisitingCardRequest.setEmailId(mEt_visiting_email_name.getText().toString());
      getUserVisitingCardRequest.setSchool("string");
      // getUserVisitingCardRequest.getVisitingCardDetailsAuthTokeInPresenter(getUserVisitingCardRequest);



  }


}
