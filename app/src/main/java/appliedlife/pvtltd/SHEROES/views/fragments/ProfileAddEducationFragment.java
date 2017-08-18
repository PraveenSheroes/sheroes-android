package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 01/03/17.
 *  profile professional Add_education screen.
 */


public class ProfileAddEducationFragment extends BaseFragment implements ProfileView{
    private final String TAG = LogUtils.makeLogTag(ProfileAddEducationFragment.class);
    private final String SCREEN_NAME = "Profile_add_education_screen";
    MyProfileView myProfileView;
    ProfileView profileViewlistener;
    @Bind(R.id.rv_profile_education_list)
    RecyclerView mRecyclerView;
    GenericRecyclerViewAdapter mAdapter;
    @Inject
    ProfilePersenter mProfilePersenter;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgressBar;
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_profile_education, container, false);
        ButterKnife.bind(this, view);
        mProfilePersenter.attachView(this);
        setProgressBar(mProgressBar);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


     /*   ma1ProfileWorkexperiences.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
        });*/

        if(null !=getArguments())
        {
             myProfileView=getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);

            List<EducationEntity> educationEntity=this.myProfileView.getEducationEntity();

           if(null !=educationEntity) {
               mAdapter.setSheroesGenericListData((educationEntity));
               mAdapter.notifyDataSetChanged();

               /* if (StringUtil.isNotEmptyCollection(educationEntity)) {



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
                    }*//*if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getGrade())) {
                        mTvGradeValue.setVisibility(View.VISIBLE);
                        mTvGradeValue.setText(educationEntity.get(0).getGrade());
                    }*//*


                }*/
            }

        }
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_VIEW_ADDED_EDUCATION));
        return view;

    }
    public void refreshWorkExpList() {
        mProfilePersenter.getALLUserDetails();
    }
   @Override
    public void onClick(View view) {

        int id = view.getId();
    }

  //click on flotting button
    @OnClick(R.id.fab_add_other_education)

        public  void fab_add_education_click()
        {
            ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_ADD_EDUCATION));
            ((ProfileActicity)getActivity()).callEditEducation(null);
            ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_PROFILE_EDITS, GoogleAnalyticsEventActions.ADDED_NEW_EDUCATION, AppConstants.EMPTY_STRING);
        }

    //click on edit icon
   /* @OnClick(R.id.tv_edit_education)


    public  void fab_edit_education_click()
    {

        ((ProfileActicity)getActivity()).callEditEducation(myProfileView);

    }*/

    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {
        List<EducationEntity> educationEntity=userProfileResponse.getEducation();
        if (StringUtil.isNotEmptyCollection(educationEntity)) {
            mAdapter.setSheroesGenericListData(educationEntity);
            mAdapter.notifyDataSetChanged();
        }
    }
    @OnClick(R.id.tv_profile_education_back)

public  void Onback_Click()
{

    //profileViewlistener.onBackPressed(R.id.tv_profile_education_back);
    ((ProfileActicity)getActivity()).updateProffesstionalEducationListItem();

}



    public interface ProfileActivityIntractionListner {

        void onErrorOccurence(String errorMessage);

        void onLoginAuthToken();
    }


}
