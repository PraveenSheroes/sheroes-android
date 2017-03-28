package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFLexibilityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 20/02/17.
 * Profile_Travel_client_Screen
 */

public class ProfileTravelClientFragment extends BaseFragment implements ProfileView{

private final String TAG = LogUtils.makeLogTag(ProfileTravelClientFragment.class);
private final String SCREEN_NAME = "Profile_Travel_screen";
    private ProfileTravelClientFragmentListener profileTravelClientFragmentListener;

    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;
    @Bind(R.id.cb_no)
    CheckBox mCb_no;
    @Bind(R.id.cb_yes)
    CheckBox mCb_yes;
    @Bind(R.id.tv_yes_then)
    TextView mtv_yes_then;
    @Bind(R.id.cb_yes1)
     CheckBox cb_yes1;
    @Bind(R.id.cb_yes2)
    CheckBox cb_yes2;
    @Bind(R.id.cb_yes3)
    CheckBox cb_yes3;
    @Inject
    ProfilePersenter mProfilePresenter;
    String num;
    int checkValue=0;

@Override
public void onAttach(Context context) {
    super.onAttach(context);
    try {
        if (getActivity() instanceof ProfileTravelClientFragmentListener) {

            profileTravelClientFragmentListener = (ProfileTravelClientFragmentListener) getActivity();

        }
    } catch (Exception e) {


    }

}



@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_travel_client, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);
        mTv_setting_tittle.setText(R.string.ID_TRAVEL_FLEXIBILITY);
        mCb_no.setChecked(true);

    return view;
        }

    @OnClick(R.id.bt_save_client_location)
    public void save_cities()
    {
        ProfileTravelFLexibilityRequest profileTravelFLexibilityRequest = new ProfileTravelFLexibilityRequest();
        profileTravelFLexibilityRequest.setAppVersion("string");
        profileTravelFLexibilityRequest.setCloudMessagingId("string");
        profileTravelFLexibilityRequest.setDeviceUniqueId("string");
        profileTravelFLexibilityRequest.setLastScreenName("string");
        profileTravelFLexibilityRequest.setScreenName("string");
        profileTravelFLexibilityRequest.setSource("string");
        profileTravelFLexibilityRequest.setType("TRAVEL_FLEXIBILITY");
        profileTravelFLexibilityRequest.setSubType("TRAVEL_FLEXIBILITY");
        profileTravelFLexibilityRequest.setTravelFlexibility(checkValue);
        mProfilePresenter.getUserTravelDetailsAuthTokeInPresenter(profileTravelFLexibilityRequest);

    }

    @OnClick(R.id.cb_yes)
    public  void checkclick()
    {
        if(mCb_yes.isChecked()) {
            mCb_no.setChecked(false);
            mtv_yes_then.setVisibility(View.VISIBLE);
            cb_yes1.setVisibility(View.VISIBLE);
            cb_yes2.setVisibility(View.VISIBLE);
            cb_yes3.setVisibility(View.VISIBLE);
        }

    }
    @OnClick(R.id.cb_yes1)
    public  void checkclick1()
    {
        if(cb_yes1.isChecked()) {
            mCb_no.setChecked(false);
            cb_yes2.setChecked(false);
            cb_yes3.setChecked(false);
            checkValue=1;

        }
    }
    @OnClick(R.id.cb_yes2)
    public  void checkclick2()
    {
        if(cb_yes2.isChecked()) {
            mCb_no.setChecked(false);
            cb_yes1.setChecked(false);
            cb_yes3.setChecked(false);
            checkValue=2;
        }

    }
    @OnClick(R.id.cb_yes3)
    public  void checkclick3()
    {
        if(cb_yes3.isChecked()) {
            mCb_no.setChecked(false);
            cb_yes1.setChecked(false);
            cb_yes2.setChecked(false);
            checkValue=3;
        }
    }

    @OnClick(R.id.cb_no)
    public  void checknoclick()
    {
        if (cb_yes3.isChecked()||cb_yes2.isChecked()||cb_yes1.isChecked())
        {
            cb_yes3.setChecked(false);
            cb_yes2.setChecked(false);
            cb_yes1.setChecked(false);


        }

        mCb_yes.setChecked(false);
        mtv_yes_then.setVisibility(View.GONE);
        cb_yes1.setVisibility(View.GONE);
        cb_yes2.setVisibility(View.GONE);
        cb_yes3.setVisibility(View.GONE);
        checkValue=0;





    }


@OnClick(R.id.iv_back_setting)
public void callBack()
{

    profileTravelClientFragmentListener.clintBack();
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

        //will be change
        Toast.makeText(getActivity(), profileTravelFlexibilityResponse.getStatus(),
                Toast.LENGTH_LONG).show();

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

    public interface ProfileTravelClientFragmentListener {

        void onErrorOccurence();

        void clintBack();
    }



}
