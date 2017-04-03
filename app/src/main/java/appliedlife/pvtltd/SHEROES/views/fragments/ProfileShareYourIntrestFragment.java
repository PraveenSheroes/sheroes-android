package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAt;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sheroes on 27/03/17.
 */

public class ProfileShareYourIntrestFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(OnBoardingShareYourInterestFragment.class);
    @Bind(R.id.rv_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    @Inject
    Preference<MasterDataResponse> mUserPreference;
    private GenericRecyclerViewAdapter mAdapter;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();
    private List<GoodAt> listFeelter = new ArrayList<GoodAt>();
    List<String> jobAtList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_profile_can_help, container, false);
        ButterKnife.bind(this, view);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getData() ) {
            data= mUserPreference.get().getData();
            LogUtils.error("Master Data",data+"");
            HashMap<String, ArrayList<LabelValue>> hashMap=data.get(AppConstants.INTEREST);
            List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_DEFAULT_CATEGORY);
            GoodAt filterList = new GoodAt();
            filterList.setName("Popular Tag");
            List<String> jobAtList = new ArrayList<>();

            for(int i=0;i<labelValueArrayList.size();i++)
            {
                String abc=labelValueArrayList.get(i).getLabel();
                jobAtList.add(abc);
            }
            filterList.setBoardingDataList(jobAtList);
            listFeelter.add(filterList);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(setFilterValues())) {
            mAdapter.setSheroesGenericListData(setFilterValues());
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setSheroesGenericListData(listFeelter);
        mAdapter.notifyDataSetChanged();
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
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_INTEREST_KEY)) {
            HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_INTEREST_KEY);

            List<OnBoardingData> listBoardingList = new ArrayList<>();
            Set<String> lookingForSet = hashMap.keySet();
            for (String lookingFor : lookingForSet) {
                OnBoardingData boardingData = new OnBoardingData();
                boardingData.setFragmentName(AppConstants.YOUR_INTEREST);
                boardingData.setCategory(lookingFor);
                boardingData.setBoardingDataList(hashMap.get(lookingFor));
                listBoardingList.add(boardingData);
            }
            return listBoardingList;
        }
        return null;
    }
}