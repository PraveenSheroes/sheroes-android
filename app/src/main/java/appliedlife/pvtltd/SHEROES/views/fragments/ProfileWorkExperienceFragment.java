package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.WorkExpListResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
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
 */

public class ProfileWorkExperienceFragment extends BaseFragment implements ProfileView {
    private static final String SCREEN_LABEL = "Work Experiences Screen";
    private final String TAG = LogUtils.makeLogTag(ProfileWorkExperienceFragment.class);
    @Bind(R.id.a1_profile_workexperiences)
    AppBarLayout ma1ProfileWorkexperiences;
    @Bind(R.id.tv_workexperience)
    TextView mtvWorkexperience;
    @Bind(R.id.rv_work_exp_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_work_exp_progress_bar)
    ProgressBar mProgressBar;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    // private List<ExprienceEntity> workExperienceList;
    @Inject
    AppUtils mAppUtils;
    @Inject
    ProfilePersenter mProfilePersenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_workexperience, container, false);
        ButterKnife.bind(this, view);
        mProfilePersenter.attachView(this);
        setProgressBar(mProgressBar);
        ma1ProfileWorkexperiences.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mtvWorkexperience.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    mtvWorkexperience.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
        initializeAllWorkExpData();
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_VIEW_ADDED_WORK_EXPERIENCE));
        return view;
    }

    public void refreshWorkExpList() {
        mProfilePersenter.getALLUserDetails();
    }

    private void initializeAllWorkExpData() {
        Bundle bundle = getArguments();
       /* if (bundle != null) {
            workExperienceList = bundle.getParcelableArrayList(AppConstants.EXPERIENCE_PROFILE);
        }*/
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setInitialPrefetchItemCount(1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        refreshWorkExpList();
    }

    @OnClick(R.id.fab_add_other_work_experience)
    public void fabon_click() {

        ((ProfileActicity) getActivity()).openEditAddWorkExpFragment(null);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_ADD_WORK_EXPERIENCE));
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_PROFILE_EDITS, GoogleAnalyticsEventActions.ADDED_NEW_WORK_EXP, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.tv_profile_workexperience_back)
    public void Onback_Click() {
        ((ProfileActicity) getActivity()).locationBack();
    }

    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {
        List<ExprienceEntity> exprienceEntityList = userProfileResponse.getExperience();
        if (StringUtil.isNotEmptyCollection(exprienceEntityList)) {
            mAdapter.setSheroesGenericListData(exprienceEntityList);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getWorkExpListSuccess(WorkExpListResponse workExpListResponse) {
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
