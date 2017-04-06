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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_ON_ONBOARDING;

/**
 * Created by Praveen_Singh on 24-03-2017.
 */

public class OnBoardingJobAtFragment extends BaseFragment implements OnBoardingView {
    private final String TAG = LogUtils.makeLogTag(OnBoardingJobAtFragment.class);
    @Bind(R.id.rv_looking_for_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_how_can_progress_bar)
    ProgressBar mProgressBar;
    private GenericRecyclerViewAdapter mAdapter;
    private List<OnBoardingData> listFeelter = new ArrayList<OnBoardingData>();
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Inject
    AppUtils mAppUtils;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.how_can_sheroes_help_fragment, container, false);
        ButterKnife.bind(this, view);
        mOnBoardingPresenter.attachView(this);
        if (null != getArguments()) {
            Bundle mBusSeatMapDataInBundle = getArguments();
            mMasterDataResult = (HashMap<String, HashMap<String, ArrayList<LabelValue>>>) mBusSeatMapDataInBundle.getSerializable(AppConstants.JOB_AT);
        }
        super.setProgressBar(mProgressBar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (OnBoardingActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(setFilterValues())) {
            mAdapter.setSheroesGenericListData(setFilterValues());
            manager.scrollToPositionWithOffset(0, 0);
            mAdapter.notifyDataSetChanged();
        }
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mRecyclerView, manager, new FragmentListRefreshData()) {
            @Override
            public void onHide() {
                ((OnBoardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onShow() {
                ((OnBoardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.VISIBLE);

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
            Set<String> jobAtCategorySet = hashMap.keySet();
            for (String jobAtCategory : jobAtCategorySet) {
                OnBoardingData boardingData = new OnBoardingData();
                boardingData.setFragmentName(AppConstants.JOB_AT);
                boardingData.setCategory(jobAtCategory);
                boardingData.setBoardingDataList(hashMap.get(jobAtCategory));
                listBoardingList.add(boardingData);
            }
            return listBoardingList;
        }
        return null;
    }
    public void onJobAtNextClick(List<LabelValue> mSelectedTag) {
        Set<Long> skillIds = new HashSet<>();
        for(LabelValue labelValue:mSelectedTag)
        {
            skillIds.add(labelValue.getValue());
        }
        mOnBoardingPresenter.getJobAtToPresenter(mAppUtils.boardingJobAtRequestBuilder(skillIds));
    }
    @Override
    public void getAllDataResponse(GetAllData getAllData) {

    }

    @Override
    public void getIntersetJobResponse(GetInterestJobResponse getInterestJobResponse) {

    }
    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
         mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(errorMsg, feedParticipationEnum);
    }
    @Override
    public void getBoardingJobResponse(BoardingDataResponse boardingDataResponse) {
        if (null != boardingDataResponse) {
            switch (boardingDataResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    ((OnBoardingActivity)getActivity()).setJobAtNextClick();
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_ON_ONBOARDING);
                    break;
            }
        }
    }
}