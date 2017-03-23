package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.OnboardingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnBoardingHowCanSheroesHelpYouFragment extends BaseFragment{
    private final String TAG = LogUtils.makeLogTag(OnBoardingHowCanSheroesHelpYouFragment.class);
    @Bind(R.id.rv_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private List<OnBoardingData> listFeelter = new ArrayList<OnBoardingData>();
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    public static OnBoardingHowCanSheroesHelpYouFragment createInstance(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        OnBoardingHowCanSheroesHelpYouFragment onBoardingHowCanSheroesHelpYouFragment = new OnBoardingHowCanSheroesHelpYouFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.HOW_SHEROES_CAN_HELP,masterDataResult);
        onBoardingHowCanSheroesHelpYouFragment.setArguments(bundle);
        return onBoardingHowCanSheroesHelpYouFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.how_can_sheroes_help_fragment, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            Bundle mBusSeatMapDataInBundle=getArguments();
            mMasterDataResult =(HashMap<String, HashMap<String, ArrayList<LabelValue>>>)mBusSeatMapDataInBundle.getSerializable(AppConstants.HOW_SHEROES_CAN_HELP);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (OnboardingActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        setFilterValues();
        mAdapter.setSheroesGenericListData(listFeelter);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mRecyclerView, manager,new FragmentListRefreshData()) {
            @Override
            public void onHide() {
                ((OnboardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onShow() {
                ((OnboardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.GONE);

            }

            @Override
            public void dismissReactions() {

            }
        });
        return view;
    }

    private void setFilterValues() {
        HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_CAN_HELP_IN_KEY);
        List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_DEFAULT_CATEGORY);

        OnBoardingData filterList = new OnBoardingData();
        filterList.setName("JobAt");
        List<String> jobAtList = new ArrayList<>();
        jobAtList.add("Proff");
        jobAtList.add("Contact ");
        jobAtList.add("Typing ");
        jobAtList.add("Typing ");
        filterList.setBoardingDataList(jobAtList);

        OnBoardingData filterList1 = new OnBoardingData();
        filterList1.setName("Contact Info");
        List<String> jobAtList1 = new ArrayList<>();
        jobAtList1.add("Proffestional");
        jobAtList1.add("Second data planning");
        jobAtList1.add("Finding data");
        filterList1.setBoardingDataList(jobAtList1);


        listFeelter.add(filterList);
        listFeelter.add(filterList1);
        listFeelter.add(filterList);
        listFeelter.add(filterList1);
        listFeelter.add(filterList);
        listFeelter.add(filterList1);
        listFeelter.add(filterList);
        listFeelter.add(filterList1);
    }

}
