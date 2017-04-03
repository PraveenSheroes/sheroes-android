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

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnBoardingHowCanSheroesHelpYouFragment extends BaseFragment implements OnBoardingView {
    private final String TAG = LogUtils.makeLogTag(OnBoardingHowCanSheroesHelpYouFragment.class);
    @Bind(R.id.rv_looking_for_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_how_can_progress_bar)
    ProgressBar mProgressBar;
    private GenericRecyclerViewAdapter mAdapter;
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
            mMasterDataResult = (HashMap<String, HashMap<String, ArrayList<LabelValue>>>) mBusSeatMapDataInBundle.getSerializable(AppConstants.HOW_SHEROES_CAN_HELP);
        }
        super.setProgressBar(mProgressBar);
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
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY)) {
            {
                HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY);
                List<OnBoardingData> listBoardingList = new ArrayList<>();
                Set<String> lookingForCategorySet = hashMap.keySet();
                for (String lookingForCategory : lookingForCategorySet) {
                    OnBoardingData boardingData = new OnBoardingData();
                    boardingData.setFragmentName(AppConstants.HOW_SHEROES_CAN_HELP);
                    boardingData.setCategory(lookingForCategory);
                    boardingData.setBoardingDataList(hashMap.get(lookingForCategory));
                    listBoardingList.add(boardingData);
                }
                return listBoardingList;
            }
        }
        return null;
    }

    public void onLookingForHowCanSheroesRequestClick(List<LabelValue> mSelectedTag) {
        Set<Long> opportunityIds = new HashSet<>();
        for(LabelValue labelValue:mSelectedTag)
        {
            opportunityIds.add(labelValue.getValue());
        }
        mOnBoardingPresenter.getLookingForHowCanToPresenter(mAppUtils.boardingLookingHowCanFormDataRequestBuilder(opportunityIds));
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
                    ((OnBoardingActivity) getActivity()).onLookingForHowCanSheroesNextClick();
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), FeedParticipationEnum.ERROR_ON_ONBOARDING);
                    break;
            }
        }
    }
}
