package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFLexibilityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
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
    @Bind(R.id.tv_profile_tittle)
    TextView mTv_profile_tittle;
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
    @Bind(R.id.bt_save_client_location)
    Button mBt_save_client_location;
    String num;
    int checkValue=0;
    int flag;

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
        mTv_profile_tittle.setText(R.string.ID_TRAVEL_FLEXIBILITY);
        mCb_no.setTextColor(getResources().getColor(R.color.search_tab_text));
        mBt_save_client_location.setBackgroundColor(getResources().getColor(R.color.red));
        mCb_no.setChecked(true);


    return view;
        }



    @OnClick(R.id.bt_save_client_location)
    public void save_cities()
    {
        ProfileTravelFLexibilityRequest profileTravelFLexibilityRequest = new ProfileTravelFLexibilityRequest();
        AppUtils appUtils=AppUtils.getInstance();
        profileTravelFLexibilityRequest.setAppVersion(appUtils.getAppVersionName());
        //TODO:check cloud
        profileTravelFLexibilityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        profileTravelFLexibilityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        profileTravelFLexibilityRequest.setLastScreenName(AppConstants.STRING);
        profileTravelFLexibilityRequest.setScreenName(AppConstants.STRING);
        profileTravelFLexibilityRequest.setSource(AppConstants.STRING);
        profileTravelFLexibilityRequest.setType(AppConstants.TRAVEL);
        profileTravelFLexibilityRequest.setSubType(AppConstants.TRAVEL);
        profileTravelFLexibilityRequest.setTravelFlexibility(checkValue);
        mProfilePresenter.getUserTravelDetailsAuthTokeInPresenter(profileTravelFLexibilityRequest);

    }

    @OnClick(R.id.cb_yes)
    public  void checkclick()
    {
        if(mCb_yes.isChecked()) {


            mCb_yes.setTextColor(getResources().getColor(R.color.search_tab_text));
            mBt_save_client_location.setBackgroundColor(getResources().getColor(R.color.red));
            cb_yes2.setTextColor(getResources().getColor(R.color.grey2));
            cb_yes1.setTextColor(getResources().getColor(R.color.grey2));
            cb_yes3.setTextColor(getResources().getColor(R.color.grey2));
            mCb_no.setTextColor(getResources().getColor(R.color.grey2));
            mBt_save_client_location.setVisibility(View.VISIBLE);
            mCb_no.setChecked(false);
            mtv_yes_then.setVisibility(View.VISIBLE);
            cb_yes1.setVisibility(View.VISIBLE);
            cb_yes2.setVisibility(View.VISIBLE);
            cb_yes3.setVisibility(View.VISIBLE);

        }else {

            mBt_save_client_location.setVisibility(View.GONE);
            mCb_yes.setTextColor(getResources().getColor(R.color.grey2));
            mtv_yes_then.setVisibility(View.GONE);
            cb_yes1.setVisibility(View.GONE);
            cb_yes2.setVisibility(View.GONE);
            cb_yes3.setVisibility(View.GONE);


        }
    }
    @OnClick(R.id.cb_yes1)
    public  void checkclick1()
    {
        if(cb_yes1.isChecked()) {

            cb_yes1.setTextColor(getResources().getColor(R.color.search_tab_text));
            cb_yes2.setTextColor(getResources().getColor(R.color.grey2));
            cb_yes3.setTextColor(getResources().getColor(R.color.grey2));
            mCb_yes.setTextColor(getResources().getColor(R.color.blue));
            mCb_no.setTextColor(getResources().getColor(R.color.grey2));
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
            cb_yes2.setTextColor(getResources().getColor(R.color.search_tab_text));
            cb_yes3.setTextColor(getResources().getColor(R.color.grey2));
            cb_yes1.setTextColor(getResources().getColor(R.color.grey2));
            mCb_yes.setTextColor(getResources().getColor(R.color.blue));
            mCb_no.setTextColor(getResources().getColor(R.color.grey2));
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
            cb_yes3.setTextColor(getResources().getColor(R.color.search_tab_text));

            cb_yes2.setTextColor(getResources().getColor(R.color.grey2));
            cb_yes1.setTextColor(getResources().getColor(R.color.grey2));
            mCb_yes.setTextColor(getResources().getColor(R.color.blue));
            mCb_no.setTextColor(getResources().getColor(R.color.grey2));

            mCb_no.setChecked(false);
            cb_yes1.setChecked(false);
            cb_yes2.setChecked(false);
            checkValue=3;
        }
    }

    @OnClick(R.id.cb_no)

    public  void checknoclick()
    {


        if (cb_yes3.isChecked()||cb_yes2.isChecked()||cb_yes1.isChecked()|| mCb_no.isChecked())
        {
            mCb_no.setTextColor(getResources().getColor(R.color.search_tab_text));
            mCb_yes.setChecked(false);
            mBt_save_client_location.setVisibility(View.VISIBLE);
            mBt_save_client_location.setBackgroundColor(getResources().getColor(R.color.red));
            cb_yes2.setTextColor(getResources().getColor(R.color.grey2));
            cb_yes1.setTextColor(getResources().getColor(R.color.grey2));
            mCb_yes.setTextColor(getResources().getColor(R.color.grey2));
            cb_yes3.setChecked(false);
            cb_yes2.setChecked(false);
            cb_yes1.setChecked(false);
            mtv_yes_then.setVisibility(View.GONE);
            cb_yes1.setVisibility(View.GONE);
            cb_yes2.setVisibility(View.GONE);
            cb_yes3.setVisibility(View.GONE);
        }else {

            mtv_yes_then.setVisibility(View.GONE);
            cb_yes1.setVisibility(View.GONE);
            cb_yes2.setVisibility(View.GONE);
            cb_yes3.setVisibility(View.GONE);
            mBt_save_client_location.setVisibility(View.GONE);
            checkValue = 0;
        }
    }








@OnClick(R.id.iv_back_profile)
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
    public void getEducationResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getprofiletracelflexibilityResponse(BoardingDataResponse boardingDataResponse) {


        //TODO:check condition

        Toast.makeText(getActivity(), boardingDataResponse.getStatus(),
                Toast.LENGTH_LONG).show();

        //TODO:Change Message

        if(boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {

            profileTravelClientFragmentListener.clintBack();


        }else {

        }

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

    public interface ProfileTravelClientFragmentListener {

        void onErrorOccurence();

        void clintBack();
    }



}
