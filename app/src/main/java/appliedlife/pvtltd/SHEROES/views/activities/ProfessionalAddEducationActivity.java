package appliedlife.pvtltd.SHEROES.views.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserDetailsRequest;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by priyanka on 07/03/17.
 */

public class ProfessionalAddEducationActivity extends BaseActivity implements ProfileView,View.OnClickListener
{

    @Inject
    ProfilePersenter mProfilePresenter;
    private final String TAG = LogUtils.makeLogTag(ProfessionalAddEducationActivity.class);
    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SheroesApplication.getAppComponent(this).inject(this);

        renderAddEducationFragmentView();

    }


    public void renderAddEducationFragmentView() {
        setContentView(R.layout.fragment_professional_addeducation);
        ButterKnife.bind(this);
        mTv_setting_tittle.setText(R.string.ID_ADD_EDUCATION);
        mProfilePresenter.attachView(this);
    }

    @OnClick(R.id.iv_back_setting)
    public void backOnclick()
    {

        //overridePendingTransition(R.anim.top_to_bottom_exit,R.anim.top_bottom_exit_anim);
        finish();

    }


    @OnClick(R.id.btn_save_education_details_)
    public void btn_Onclick()
    {

        GetUserDetailsRequest getUserDetailsRequest = new GetUserDetailsRequest();
        getUserDetailsRequest.setAppVersion("string");
        getUserDetailsRequest.setCloudMessagingId("string");
        getUserDetailsRequest.setDeviceUniqueId("string");
        getUserDetailsRequest.setLastScreenName("string");
        getUserDetailsRequest.setScreenName("string");
        getUserDetailsRequest.setType("EDUCATION");
        getUserDetailsRequest.setSubType("BaseProfileRequest");
        mProfilePresenter.getUserDetailsAuthTokeInPresenter(getUserDetailsRequest);



    }




    @Override
    public void backListener(int id) {

    }

    @Override
    public void callFragment(int id) {

    }

    @Override
    public void getEducationResponse(EducationResponse educationResponse) {




    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, int errorFor) {

    }




}
