package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAtSkill;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserDetails;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka on 13-02-2017.
 */

public class ProffestionalProfileFragment extends BaseFragment implements ProfileView {
    private static final String SCREEN_LABEL = "Professional Profile Screen";
    private final String TAG = LogUtils.makeLogTag(ProffestionalProfileFragment.class);
    @Bind(R.id.rv_profile_spinner_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgressBar;
    @Inject
    ProfilePersenter profilePersenter;
    GenericRecyclerViewAdapter mAdapter;
    List<ProfileViewList> profileList = new ArrayList<ProfileViewList>();
    private static ProffestionalProfileFragment proffestionalProfileFragment = new ProffestionalProfileFragment();

    public static ProffestionalProfileFragment getInstance() {
        return proffestionalProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.profile_visiting_card, container, false);
        ButterKnife.bind(this, view);
        profilePersenter.attachView(this);
        setProgressBar(mProgressBar);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        onDataRefresh();
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL));
        return view;

    }


    public void onDataRefresh() {
        profilePersenter.getALLUserDetails();
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (null != userProfileResponse && StringUtil.isNotEmptyCollection(renderAllProfileViews(userProfileResponse))) {
            mAdapter.setSheroesGenericListData((renderAllProfileViews(userProfileResponse)));
            mAdapter.notifyDataSetChanged();
        }
    }


    private List<MyProfileView> renderAllProfileViews(UserProfileResponse userProfileResponse) {
        List<MyProfileView> myProfileViewList = new ArrayList<>();
        MyProfileView goodAtSkillProfile = new MyProfileView();
        goodAtSkillProfile.setType(AppConstants.GOOD_AT_SKILL_PROFILE);
        List<GoodAtSkill> goodAtSkill = new ArrayList<GoodAtSkill>();
        List<LabelValue> skill = userProfileResponse.getUserDetails().getSkills();
        if (StringUtil.isNotEmptyCollection(skill)) {
            for (LabelValue goodAtVal : skill) {

                GoodAtSkill goodAtSkillValue = new GoodAtSkill();
                goodAtSkillValue.setId(goodAtVal.getValue());
                goodAtSkillValue.setName(goodAtVal.getLabel());
                goodAtSkill.add(goodAtSkillValue);
            }
        }
        goodAtSkillProfile.setGoodAtSkill(goodAtSkill);

        MyProfileView educationProfile = new MyProfileView();
        educationProfile.setType(AppConstants.EDUCATION_PROFILE);
        List<EducationEntity> educationEntities = new ArrayList<>();
        List<EducationEntity> educationEntityList = userProfileResponse.getEducation();
        if (StringUtil.isNotEmptyCollection(educationEntityList)){
            educationEntities.addAll(educationEntityList);
        }
        educationProfile.setEducationEntity(educationEntities);

        MyProfileView experienceProfile = new MyProfileView();
        experienceProfile.setType(AppConstants.EXPERIENCE_PROFILE);
        List<ExprienceEntity> exprienceEntityList = userProfileResponse.getExperience();


        MyProfileView userProfile = new MyProfileView();
        userProfile.setType(AppConstants.USER_PROFILE1);
        if (null != userProfileResponse.getUserDetails()){
            UserDetails userDetails = userProfileResponse.getUserDetails();
            userProfile.setUserDetails(userDetails);
        }


   /*     MyProfileView cliendsidelocation = new MyProfileView();

        cliendsidelocation.setType(AppConstants.CLIENTSIDE);
        ClientSideLocation clientside = new ClientSideLocation();
        clientside.setDescription("Data to be test");
        cliendsidelocation.setClientSideLocation(clientside);*/


      //  myProfileViewList.add(UservisitingCard);
        myProfileViewList.add(goodAtSkillProfile);
        myProfileViewList.add(educationProfile);
        if (StringUtil.isNotEmptyCollection(exprienceEntityList)) {
            experienceProfile.setExprienceEntity(exprienceEntityList);
            myProfileViewList.add(experienceProfile);
        } else {
            List<ExprienceEntity> localWorkList = new ArrayList<>();
            ExprienceEntity exprienceEntity = new ExprienceEntity();
            localWorkList.add(exprienceEntity);
            experienceProfile.setExprienceEntity(localWorkList);
            myProfileViewList.add(experienceProfile);
        }
        myProfileViewList.add(userProfile);
        return myProfileViewList;

    }


    @Override
    public void getProfileListSuccess(GetTagData getAllData) {

    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {

    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public interface HomeActivityIntractionWithProffestionalProfile {
        void onErrorOccurence();
    }
}