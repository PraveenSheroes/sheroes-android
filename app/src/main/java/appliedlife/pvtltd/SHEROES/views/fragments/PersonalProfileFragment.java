package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Priyanka  on 13-02-2017.
 */

public class PersonalProfileFragment extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(PersonalProfileFragment.class);
    @Bind(R.id.rv_profile_spinner_list)
    RecyclerView mRecyclerView;
    GenericRecyclerViewAdapter mAdapter;
    private HomeActivityIntractionWithPersonalProfile mHomeActivityIntractionWithpersonalProfile;
    List<ProfilePersonalViewList> personalprofileList = new ArrayList<ProfilePersonalViewList>();

    public static PersonalProfileFragment createInstance() {

        PersonalProfileFragment personalProfileFragment = new PersonalProfileFragment();
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


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        setListValue();

        mAdapter.setSheroesGenericListData(personalprofileList);
        //  checkForSpinnerItemSelection();
        return view;
    }

    private void checkForSpinnerItemSelection() {
        /*if (StringUtil.isNotEmptyCollection(AppUtils.profileDetail())) {
            mAdapter.setSheroesGenericListData(AppUtils.profileDetail());
        }*/
    }




    private void setListValue() {


        ProfilePersonalViewList personal_profile_card = new ProfilePersonalViewList();
        personal_profile_card.setId("0");
        personal_profile_card.setTag("My Contact Card");
        personal_profile_card.setItem1("Download Now");
        personal_profile_card.setItem2("");
        personal_profile_card.setItem3("");
        personal_profile_card.setItem4("");
        personal_profile_card.setItem5("");
        personal_profile_card.setItem6("");
        personal_profile_card.setItem7("");
        personal_profile_card.setItem8("");
        personal_profile_card.setItem9("");
        personal_profile_card.setItem10("");
        personal_profile_card.setItem11("");
        personal_profile_card.setItem12("");
        personal_profile_card.setItem13("");
        personal_profile_card.setItem14("");
        personalprofileList.add(personal_profile_card);


        ProfilePersonalViewList personalprofile=new ProfilePersonalViewList();
        personalprofile.setId("1");
        personalprofile.setTag("Looking For");
        personalprofile.setItem1("Opportunity Search");
        personalprofile.setItem2("Business & Entrepreneurship");
        personalprofile.setItem3("");
        personalprofile.setItem4("");
        personalprofile.setItem5("");
        personalprofile.setItem6("");
        personalprofile.setItem7("");
        personalprofile.setItem8("");
        personalprofile.setItem9("");
        personalprofile.setItem10("");
        personalprofile.setItem11("");
        personalprofile.setItem12("");
        personalprofile.setItem13("");
        personalprofile.setItem14("");
        personalprofileList.add(personalprofile);


        ProfilePersonalViewList personalprofile1=new ProfilePersonalViewList();
        personalprofile1.setId("2");
        personalprofile1.setTag("I Can Help With");
        personalprofile1.setItem1("Trends & Insights");
        personalprofile1.setItem2("Motivation & Inspiration");
        personalprofile.setItem3("");
        personalprofile.setItem4("");
        personalprofile.setItem5("");
        personalprofile.setItem6("");
        personalprofile.setItem7("");
        personalprofile.setItem8("");
        personalprofile.setItem9("");
        personalprofile.setItem10("");
        personalprofile.setItem11("");
        personalprofile.setItem12("");
        personalprofile.setItem13("");
        personalprofile.setItem14("");
        personalprofileList.add(personalprofile1);


        ProfilePersonalViewList personalprofile2=new ProfilePersonalViewList();
        personalprofile2.setId("3");
        personalprofile2.setTag("About Me");
        personalprofile2.setItem1("sed do eiusmod tempor incididunt ut labore et dolore…");
        personalprofile.setItem2("");
        personalprofile.setItem3("");
        personalprofile.setItem4("");
        personalprofile.setItem5("");
        personalprofile.setItem6("");
        personalprofile.setItem7("");
        personalprofile.setItem8("");
        personalprofile.setItem9("");
        personalprofile.setItem10("");
        personalprofile.setItem11("");
        personalprofile.setItem12("");
        personalprofile.setItem13("");
        personalprofile.setItem14("");
        personalprofileList.add(personalprofile2);


        ProfilePersonalViewList personalprofile3=new ProfilePersonalViewList();
        personalprofile3.setId("4");
        personalprofile3.setTag("Basic Details");
        personalprofile3.setItem1("Date of Birth");
        personalprofile3.setItem2("11 JUL 1992");
        personalprofile3.setItem3("Current Location");
        personalprofile3.setItem4("Saket, Delhi, India");
        personalprofile3.setItem5("Hometown");
        personalprofile3.setItem6("Chandigarh, Punjab, India");
        personalprofile3.setItem7("Email");
        personalprofile3.setItem8("deepika@gmail.com");
        personalprofile3.setItem9("Contact Number");
        personalprofile3.setItem10("+91-9654808379");
        personalprofile3.setItem11("Relationship Status");
        personalprofile3.setItem12("Single");
        personalprofile3.setItem13("Number Of Children");
        personalprofile3.setItem14("1");
        personalprofileList.add(personalprofile3);


        ProfilePersonalViewList personalprofile4=new ProfilePersonalViewList();
        personalprofile4.setId("5");
        personalprofile4.setTag("I AM INTERESTED IN");
        personalprofile4.setItem1("WORK FROM HOME");
        personalprofile4.setItem2("FREELANCE WORK");
        personalprofile4.setItem3("CORPORATE JOBS");
        personalprofile4.setItem4("INTERNSHIP/VOLUNTEER");
        personalprofile4.setItem5("");
        personalprofile4.setItem6("");
        personalprofile4.setItem7("");
        personalprofile4.setItem8("");
        personalprofile4.setItem9("");
        personalprofile4.setItem10("");
        personalprofile4.setItem11("");
        personalprofile4.setItem12("");
        personalprofile4.setItem13("");
        personalprofile4.setItem14("");
        personalprofileList.add(personalprofile4);

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

    public interface HomeActivityIntractionWithPersonalProfile {
        void onErrorOccurence();
    }
}