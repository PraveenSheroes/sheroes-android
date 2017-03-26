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
import java.util.Set;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 24-03-2017.
 */

public class OnBoardingJobAtFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(OnBoardingJobAtFragment.class);
    @Bind(R.id.rv_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private List<OnBoardingData> listFeelter = new ArrayList<OnBoardingData>();
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.how_can_sheroes_help_fragment, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            Bundle mBusSeatMapDataInBundle = getArguments();
            mMasterDataResult = (HashMap<String, HashMap<String, ArrayList<LabelValue>>>) mBusSeatMapDataInBundle.getSerializable(AppConstants.JOB_AT);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (OnBoardingActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(setFilterValues())) {
            mAdapter.setSheroesGenericListData(setFilterValues());
            mAdapter.notifyDataSetChanged();
        }
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mRecyclerView, manager, new FragmentListRefreshData()) {
            @Override
            public void onHide() {
                ((OnBoardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onShow() {
                ((OnBoardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.GONE);

            }

            @Override
            public void dismissReactions() {

            }
        });
        return view;
    }

    private List<OnBoardingData> setFilterValues() {
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_SKILL_KEY)) {
            HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_SKILL_KEY);

            List<OnBoardingData> listBoardingList = new ArrayList<>();
            Set<String> lookingForSet = hashMap.keySet();
            for (String lookingFor : lookingForSet) {
                OnBoardingData boardingData = new OnBoardingData();
                boardingData.setFragmentName(AppConstants.JOB_AT);
                boardingData.setName(lookingFor);
                boardingData.setBoardingDataList(hashMap.get(lookingFor));
                listBoardingList.add(boardingData);
            }
            return listBoardingList;
        }
        return null;
    }

}