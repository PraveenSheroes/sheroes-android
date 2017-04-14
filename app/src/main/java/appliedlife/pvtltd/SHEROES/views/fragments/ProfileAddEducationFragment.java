package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfessionalAddEducationActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 01/03/17.
 *  profile professional Add_education screen.
 */


public class ProfileAddEducationFragment extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(ProfileAddEducationFragment.class);
    private final String SCREEN_NAME = "Profile_add_education_screen";
    MyProfileView myProfileView;
    ProfileView profileViewlistener;

    @Bind(R.id.tv_degree1)
    TextView mTv_degree1;
    @Bind(R.id.tv_date1)
    TextView mTv_date1;
    @Bind(R.id.tv_degree11)
    TextView mTv_degree11;
    @Bind(R.id.tv_degree2)
    TextView mTv_degree2;
    @Bind(R.id.tv_degree21)
    TextView mTv_degree21;
    @Bind(R.id.tv_date2)
    TextView mTv_date2;

    private ProfileActivityIntractionListner mProfileActivityIntractionListner;

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
        View view = inflater.inflate(R.layout.fragment_profile_education, container, false);
        ButterKnife.bind(this, view);

        if(null !=getArguments())

        {
             myProfileView=getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);

            List<EducationEntity> educationEntity=this.myProfileView.getEducationEntity();

            if(null !=educationEntity) {
                if (StringUtil.isNotEmptyCollection(educationEntity)) {
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getDegree())) {
                        mTv_degree1.setVisibility(View.VISIBLE);
                        mTv_degree1.setText(educationEntity.get(0).getDegree());
                    }
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getSchool())) {
                        mTv_degree11.setVisibility(View.VISIBLE);
                        mTv_degree11.setText(educationEntity.get(0).getSchool());
                    }
                    if(educationEntity.get(0).getSessionStartYear()>0) {
                        String session="";
                        if(educationEntity.get(0).getSessionEndYear()>0)
                        {
                            session="("+educationEntity.get(0).getSessionStartYear()+"-"+educationEntity.get(0).getSessionEndYear()+")";
                        }
                        else
                        {
                            session="("+educationEntity.get(0).getSessionStartYear()+")";
                        }
                        mTv_date1.setVisibility(View.VISIBLE);
                        mTv_date1.setText(session);
                    }


                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(1).getDegree())) {
                        mTv_degree2.setVisibility(View.VISIBLE);
                        mTv_degree2.setText(educationEntity.get(1).getDegree());
                    }
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(1).getSchool())) {
                        mTv_degree21.setVisibility(View.VISIBLE);
                        mTv_degree21.setText(educationEntity.get(1).getSchool());
                    }
                    if(educationEntity.get(1).getSessionStartYear()>1) {
                        String session="";
                        if(educationEntity.get(1).getSessionEndYear()>1)
                        {
                            session="("+educationEntity.get(1).getSessionStartYear()+"-"+educationEntity.get(1).getSessionEndYear()+")";
                        }
                        else
                        {
                            session="("+educationEntity.get(1).getSessionStartYear()+")";
                        }
                        mTv_date2.setVisibility(View.VISIBLE);
                        mTv_date2.setText(session);
                    }




                }
            }

        }

        return view;

    }

   @Override
    public void onClick(View view) {

        int id = view.getId();
    }

  //click on flotting button
    @OnClick(R.id.fab_add_other_education)

        public  void fab_add_education_click()
        {
            Intent intent = new Intent(getActivity(), ProfessionalAddEducationActivity.class);
            startActivity(intent);

        }

    //click on flotting button
    @OnClick(R.id.tv_add_education)

    public  void fab_edit_education_click()
    {

        ((ProfileActicity)getActivity()).callEditEducation(myProfileView);

    }


    @OnClick(R.id.tv_profile_education_back)

public  void Onback_Click()
{

    profileViewlistener.onBackPressed(R.id.tv_profile_education_back);

}



    public interface ProfileActivityIntractionListner {

        void onErrorOccurence(String errorMessage);

        void onLoginAuthToken();
    }


}
