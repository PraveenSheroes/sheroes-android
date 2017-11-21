package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.RangeSeekBar;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.LOCATION;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.jobCategoryRequestBuilder;

/**
 * Created by Ajit Kumar on 10-02-2017.
 */

public class JobFilterDialogFragment extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Job Filters Screen";
    private final String TAG = LogUtils.makeLogTag(JobFilterDialogFragment.class);
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
    RecyclerView tvFunctionAreaRecycler;
    private int minSeekValue = 0;
    private int maxSeekValue = 25;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    AppUtils appUtils;
    private GenericRecyclerViewAdapter mAdapter;
    private List<String> cities = new ArrayList<>();
    private List<String> skill = new ArrayList<>();
    private Integer experienceFrom;
    private Integer experienceTo;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
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

        opportunityRecyclerDataList();
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_JOB_FILTER_SCREEN));
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }
        // set the animations to use on showing and hiding the dialog
    }

    private void opportunityRecyclerDataList()
    {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity)getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        } else {
            mHomePresenter.getMasterDataToPresenter();
        }
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        if (StringUtil.isNotEmptyCollection(setJobOpportunityValues())) {
            mAdapter.setSheroesGenericListData(setJobOpportunityValues());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void locationData(List<String> jobLocationList) {
        if (StringUtil.isNotEmptyCollection(jobLocationList)) {
            StringBuilder stringBuilder=new StringBuilder();
            for(String city:jobLocationList)
            {
                cities.add(city);
                stringBuilder.append(city).append(AppConstants.COMMA);
            }
            String loc=stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
            tvLocationData.setText(loc);
        }
    }
    @OnClick(R.id.tv_save_job_filter)
    public void applyFilterOnClick() {
        FeedRequestPojo feedRequestPojo = jobCategoryRequestBuilder(AppConstants.FEED_JOB, AppConstants.ONE_CONSTANT, cities, experienceFrom, experienceTo, null, ((HomeActivity) getActivity()).mListOfOpportunity, skill);
        ((HomeActivity) getActivity()).jobFilterActivityResponse(feedRequestPojo);
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SEARCH_FILTER, GoogleAnalyticsEventActions.USED_FILTER_ON_JOBS, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.tv_close_community)
    public void backBtnClick() {
      dismiss();
    }

    @OnClick(R.id.tv_opportunity_type_lable)
    public void opportunityType() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_loaction_label)
    public void locationJob() {
        tvLocationData.setVisibility(View.VISIBLE);
        ((HomeActivity) getActivity()).searchLocationData(AppConstants.LOCATION_CITY_GET_ALL_DATA_KEY, LOCATION);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_JOB_FILTERS_SELECT_LOCATION));
    }
    @OnClick(R.id.tv_location_data)
    public void locationJobData() {
        locationJob();
    }
    @OnClick(R.id.tv_filter_exp_label)
    public void filterExperience() {
        tvExp.setVisibility(View.VISIBLE);
    }


    private List<OnBoardingData> setJobOpportunityValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        }
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.JOB_DATA_OPPORTUNITY_KEY)) {
            HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.JOB_DATA_OPPORTUNITY_KEY);
            List<OnBoardingData> listBoardingList = new ArrayList<>();
            Set<String> lookingForCategorySet = hashMap.keySet();
            for (String lookingForCategory : lookingForCategorySet) {
                OnBoardingData boardingData = new OnBoardingData();
                boardingData.setFragmentName(AppConstants.JOB_DATA_OPPORTUNITY_KEY);
                boardingData.setCategory(lookingForCategory);
                boardingData.setBoardingDataList(hashMap.get(lookingForCategory));
                listBoardingList.add(boardingData);
            }
            return listBoardingList;
        }
        return null;
    }

   /* @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                backBtnClick();
            }
        };
    }
}