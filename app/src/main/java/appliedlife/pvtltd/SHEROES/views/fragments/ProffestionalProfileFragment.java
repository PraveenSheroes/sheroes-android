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
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 13-02-2017.
 */

public class ProffestionalProfileFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(ProffestionalProfileFragment.class);
    @Bind(R.id.rv_profile_spinner_list)
    RecyclerView mRecyclerView;



    GenericRecyclerViewAdapter mAdapter;
    private HomeActivityIntractionWithProffestionalProfile mHomeActivityIntractionWithProffestionalProfile;
    List<ProfileViewList> profileList = new ArrayList<ProfileViewList>();

    public static ProffestionalProfileFragment createInstance() {
        ProffestionalProfileFragment proffestionalProfileFragment = new ProffestionalProfileFragment();
        return proffestionalProfileFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityIntractionWithProffestionalProfile) {
                mHomeActivityIntractionWithProffestionalProfile = (HomeActivityIntractionWithProffestionalProfile) getActivity();
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
        mAdapter.setSheroesGenericListData(profileList);

        //  checkForSpinnerItemSelection();
        return view;
    }



    private void checkForSpinnerItemSelection() {




        /*if (StringUtil.isNotEmptyCollection(AppUtils.profileDetail())) {
            mAdapter.setSheroesGenericListData(AppUtils.profileDetail());
        }*/
    }
    private void setListValue() {




        ProfileViewList listProfile_card=new ProfileViewList();
        listProfile_card.setId("0");
        listProfile_card.setTag("My Contact Card");
        listProfile_card.setItem1("Download Now");
        listProfile_card.setItem2("");
        listProfile_card.setItem3("");
        listProfile_card.setItem4("");
        listProfile_card.setItem5("");
        listProfile_card.setItem6("");
        listProfile_card.setItem7("");
        listProfile_card.setItem8("");
        listProfile_card.setItem9("");
        listProfile_card.setItem10("");
        profileList.add(listProfile_card);




        ProfileViewList listProfile=new ProfileViewList();
        listProfile.setId("1");
        listProfile.setTag("Good At");
        listProfile.setItem1("WORK FROM HOME");
        listProfile.setItem2("FREELANCE WORK");
        listProfile.setItem3("CORPORATE JOBS");
        listProfile.setItem4("INTERNSHIP/VOLUNTEER");
        listProfile.setItem5("");
        listProfile.setItem6("");
        listProfile.setItem7("");
        listProfile.setItem8("");
        listProfile.setItem9("");
        listProfile.setItem10("");
        profileList.add(listProfile);

        ProfileViewList listProfile1=new ProfileViewList();
        listProfile1.setId("2");
        listProfile1.setTag("EDUCATION");
        listProfile1.setItem1("M.COM");
        listProfile1.setItem2("Lady Shri Ram College");
        listProfile1.setItem3("(2014-2016)");
        listProfile1.setItem4("B.COM");
        listProfile1.setItem5("Gargi College");
        listProfile1.setItem6("(2012-2014)");
        listProfile1.setItem7("");
        listProfile1.setItem8("");
        listProfile1.setItem9("");
        listProfile1.setItem10("");
        profileList.add(listProfile1);


        ProfileViewList listProfile2=new ProfileViewList();
        listProfile2.setId("3");
        listProfile2.setTag("WORK EXPERIENCE");
        listProfile2.setItem1("Lead Designer");
        listProfile2.setItem2("Snapdeal");
        listProfile2.setItem3("Job");
        listProfile2.setItem4("(2015-PRESENT)");
        listProfile2.setItem5("Lead Designer");
        listProfile2.setItem6("CouponDunia");
        listProfile2.setItem7("Business Owner");
        listProfile2.setItem8("(2014-2015)");
        listProfile2.setItem9("");
        listProfile2.setItem10("");
        profileList.add(listProfile2);

        ProfileViewList listProfile3=new ProfileViewList();
        listProfile3.setId("4");
        listProfile3.setTag("Horizontal");
        listProfile3.setItem1("Are you willing to travel to client side location?");
        listProfile3.setItem2("Yes, My Country");
        listProfile3.setItem3("");
        listProfile3.setItem4("");
        listProfile3.setItem5("");
        listProfile3.setItem6("");
        listProfile3.setItem7("");
        listProfile3.setItem8("");
        listProfile3.setItem9("");
        listProfile3.setItem10("");
        profileList.add(listProfile3);


        ProfileViewList listProfile4=new ProfileViewList();
        listProfile4.setId("5");
        listProfile4.setTag("BASIC DETAILS");
        listProfile4.setItem1("Current Status");
        listProfile4.setItem2("Employed");
        listProfile4.setItem3("Sector");
        listProfile4.setItem4("Design");
        listProfile4.setItem5("Total work_experience");
        listProfile4.setItem6("4 Years 11 Months");
        listProfile4.setItem7("Language");
        listProfile4.setItem8("English");
        listProfile4.setItem9("");
        listProfile4.setItem10("");
        profileList.add(listProfile4);

        ProfileViewList listProfile5=new ProfileViewList();
        listProfile5.setId("6");
        listProfile5.setTag("OTHER");
        listProfile5.setItem1("");
        listProfile5.setItem2("");
        listProfile5.setItem3("");
        listProfile5.setItem4("");
        listProfile5.setItem5("");
        listProfile5.setItem6("");
        listProfile5.setItem7("");
        listProfile5.setItem8("");
        listProfile5.setItem9("");
        listProfile5.setItem10("");
        profileList.add(listProfile5);



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

    public interface HomeActivityIntractionWithProffestionalProfile {
        void onErrorOccurence();
    }
}