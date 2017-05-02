package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
    @Bind(R.id.tv_date_details)
    TextView mTvDateDetails;
    @Bind(R.id.tv_education_name)
    TextView mTvEducationName;
    @Bind(R.id.tv_college_name)
    TextView mTvCollegeName;
    @Bind(R.id.tv_subject_name)
    TextView mTvSubjectName;
    @Bind(R.id.tv_grade_value)
    TextView mTvGradeValue;
    @Bind(R.id.a1_profile_workexperiences)
    AppBarLayout ma1ProfileWorkexperiences;
    @Bind(R.id.tv_education)
    TextView mTvEducationTittle;


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






        ma1ProfileWorkexperiences.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mTvEducationTittle.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    mTvEducationTittle.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });

        if(null !=getArguments())

        {
             myProfileView=getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);

            List<EducationEntity> educationEntity=this.myProfileView.getEducationEntity();

           if(null !=educationEntity) {
                if (StringUtil.isNotEmptyCollection(educationEntity)) {



                    if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getDegree())) {
                        mTvEducationName.setVisibility(View.VISIBLE);
                        mTvEducationName.setText(educationEntity.get(0).getDegree());
                    }if(educationEntity.get(0).getSessionStartYear()>0) {
                        String session="";
                        if(educationEntity.get(0).getSessionEndYear()>0)
                        {
                            session="("+educationEntity.get(0).getSessionStartYear()+"-"+educationEntity.get(0).getSessionEndYear()+")";
                        }
                        else
                        {
                            session="("+educationEntity.get(0).getSessionStartYear()+")";
                        }
                        mTvDateDetails.setVisibility(View.VISIBLE);
                        mTvDateDetails.setText(session);
                    }if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getSchool())) {
                        mTvCollegeName.setVisibility(View.VISIBLE);
                        mTvCollegeName.setText(educationEntity.get(0).getSchool());
                    }if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getFieldOfStudy())) {
                        mTvSubjectName.setVisibility(View.VISIBLE);
                        mTvSubjectName.setText(educationEntity.get(0).getFieldOfStudy());
                    }if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getGrade())) {
                        mTvGradeValue.setVisibility(View.VISIBLE);
                        mTvGradeValue.setText(educationEntity.get(0).getGrade());
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

    //click on edit icon
    @OnClick(R.id.tv_edit_education)

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
