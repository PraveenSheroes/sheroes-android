package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.RangeSeekBar;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.JobFilterActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.LOCATION;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.jobCategoryRequestBuilder;

/**
 * Created by Ajit Kumar on 10-02-2017.
 */

public class JobFilterFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(JobFilterFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_filter_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_community_title)
    TextView mTvCommunityTitle;
    @Bind(R.id.tv_filter_exp)
    TextView tvExp;
    @Bind(R.id.tv_location_data)
    TextView tvLocationData;
    @Bind(R.id.tv_functional_area)
    TextView tvFunctionArea;
    private int minSeekValue = 0;
    private int maxSeekValue = 25;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    AppUtils appUtils;
    private GenericRecyclerViewAdapter mAdapter;
    private List<String> cities = new ArrayList<>();
    private List<String> functionArea = new ArrayList<>();
    private List<String> skill = new ArrayList<>();
    private Integer experienceFrom;
    private Integer experienceTo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.job_filter_fragment, container, false);
        ButterKnife.bind(this, view);
        RangeSeekBar<Integer> seekBar = (RangeSeekBar<Integer>) view.findViewById(R.id.rangeSeekbar);
        seekBar.setRangeValues(minSeekValue, maxSeekValue);
        tvExp.setText(minSeekValue + AppConstants.DASH + maxSeekValue + AppConstants.SPACE + getString(R.string.ID_YEARS));
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                experienceFrom = minValue;
                experienceTo = maxValue;
                tvExp.setText(minValue + AppConstants.DASH + maxValue + AppConstants.SPACE + getString(R.string.ID_YEARS));
            }
        });

        seekBar.setNotifyWhileDragging(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (JobFilterActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        } else {
            mHomePresenter.getMasterDataToPresenter();
        }
        if (StringUtil.isNotEmptyCollection(setJobOpportunityValues())) {
            mAdapter.setSheroesGenericListData(setJobOpportunityValues());
            mAdapter.notifyDataSetChanged();
        }

        return view;
    }

    public void locationData(GetAllDataDocument getAllDataDocument) {
        if (StringUtil.isNotNullOrEmptyString(getAllDataDocument.getTitle())) {
            cities.add(getAllDataDocument.getTitle());
            tvLocationData.setText(getAllDataDocument.getTitle());
        }
    }

    @OnClick(R.id.tv_save_job_filter)
    public void applyFilterOnClick() {
        FeedRequestPojo feedRequestPojo = jobCategoryRequestBuilder(AppConstants.FEED_JOB, AppConstants.ONE_CONSTANT, cities, experienceFrom, experienceTo, functionArea, ((JobFilterActivity) getActivity()).listOfOpportunity, skill);
        ((JobFilterActivity) getActivity()).applyFilterData(feedRequestPojo);
    }

    @OnClick(R.id.tv_close_community)
    public void backBtnClick() {
        ((JobFilterActivity) getActivity()).onBackPressed();
    }

    @OnClick(R.id.tv_opportunity_type_lable)
    public void opportunityType() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_loaction_label)
    public void locationJob() {
        tvLocationData.setVisibility(View.VISIBLE);
        ((JobFilterActivity) getActivity()).searchLocationData(AppConstants.LOCATION_CITY_GET_ALL_DATA_KEY, LOCATION);

    }

    @OnClick(R.id.tv_functional_area_lable)
    public void functionalArea() {
        List<OnBoardingData> boardingDataList = functionalAreaValues();
        StringBuilder stringBuilder = new StringBuilder();
        for (OnBoardingData onBoardingData : boardingDataList) {
            functionArea.add(onBoardingData.getCategory());
            stringBuilder.append(onBoardingData.getCategory());
            stringBuilder.append(AppConstants.COMMA);
        }
        String functionalArea = stringBuilder.toString();
        if (StringUtil.isNotNullOrEmptyString(functionalArea)) {
            tvFunctionArea.setText(functionalArea.substring(0, functionalArea.length() - 1));
        }
        tvFunctionArea.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_filter_exp_label)
    public void filterExperience() {
        tvExp.setVisibility(View.VISIBLE);
    }


    private List<OnBoardingData> setJobOpportunityValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        }
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY)) {
            HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY);
            List<OnBoardingData> listBoardingList = new ArrayList<>();
            Set<String> lookingForCategorySet = hashMap.keySet();
            for (String lookingForCategory : lookingForCategorySet) {
                OnBoardingData boardingData = new OnBoardingData();
                boardingData.setCategory(lookingForCategory);
                boardingData.setBoardingDataList(hashMap.get(lookingForCategory));
                listBoardingList.add(boardingData);
            }
            return listBoardingList;
        }
        return null;
    }

    private List<OnBoardingData> functionalAreaValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        }
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_FUNCTIONAL_AREA_KEY)) {
            HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_FUNCTIONAL_AREA_KEY);
            List<OnBoardingData> listBoardingList = new ArrayList<>();
            Set<String> lookingForCategorySet = hashMap.keySet();
            for (String lookingForCategory : lookingForCategorySet) {
                OnBoardingData boardingData = new OnBoardingData();
                boardingData.setCategory(lookingForCategory);
                boardingData.setBoardingDataList(hashMap.get(lookingForCategory));
                listBoardingList.add(boardingData);
            }
            return listBoardingList;
        }
        return null;
    }

}