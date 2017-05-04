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
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.OpportunityType;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
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
 * Created by Priyanka  on 13-02-2017.
 */

public class PersonalProfileFragment extends BaseFragment implements ProfileView {

    @Inject
    ProfilePersenter profilePersenter;
    private final String TAG = LogUtils.makeLogTag(PersonalProfileFragment.class);
    @Bind(R.id.rv_profile_spinner_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgressBar;

    GenericRecyclerViewAdapter mAdapter;
    private HomeActivityIntractionWithPersonalProfile mHomeActivityIntractionWithpersonalProfile;
    private static PersonalProfileFragment personalProfileFragment = new PersonalProfileFragment();


    public static PersonalProfileFragment getInstance() {
        return personalProfileFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityIntractionWithPersonalProfile) {
                mHomeActivityIntractionWithpersonalProfile = (HomeActivityIntractionWithPersonalProfile) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.profile_visiting_card, container, false);
        ButterKnife.bind(this, view);
        profilePersenter.attachView(this);
        setProgressBar(mProgressBar);
        callGetAllDetailsAPI();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public void onDataRefresh() {
        profilePersenter.getALLUserDetails();
    }

    private void callGetAllDetailsAPI() {
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


        MyProfileView oppertunityView = new MyProfileView();
        oppertunityView.setType(AppConstants.OPPORTUNITY_PROFILE);
        ArrayList<OpportunityType> opportunityTypes = new ArrayList<OpportunityType>();

        List<LabelValue> oppertunity = userProfileResponse.getUserDetails().getOpportunityTypes();
        if (null != oppertunity) {
            for (LabelValue canHelps : oppertunity) {
                OpportunityType opportunityType = new OpportunityType();
                opportunityType.setId(canHelps.getValue());
                opportunityType.setName(canHelps.getLabel());
                opportunityTypes.add(opportunityType);
            }
        }

        oppertunityView.setOpportunityType(opportunityTypes);


        MyProfileView userSummary = new MyProfileView();
        userSummary.setType(AppConstants.ABOUT_ME_PROFILE);
        if (null != userProfileResponse.getUserDetails()) {
            UserDetails userDetails = userProfileResponse.getUserDetails();
            userSummary.setUserDetails(userDetails);
        }

        MyProfileView userProfile = new MyProfileView();
        userProfile.setType(AppConstants.USER_PROFILE);
        if (null != userProfileResponse.getUserDetails()) {
            UserDetails userDetails = userProfileResponse.getUserDetails();
            userProfile.setUserDetails(userDetails);
        }


        myProfileViewList.add(oppertunityView);
        myProfileViewList.add(userSummary);
        myProfileViewList.add(userProfile);
        return myProfileViewList;
    }


    @Override
    public void getProfileListSuccess(GetTagData getAllData) {


    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {

    }


    public interface HomeActivityIntractionWithPersonalProfile {
        void onErrorOccurence();
    }
}