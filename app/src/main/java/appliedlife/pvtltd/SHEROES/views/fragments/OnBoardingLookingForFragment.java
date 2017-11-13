package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LookingForLableValues;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnBoardingLookingForFragment extends BaseFragment implements OnBoardingView {
    private static final String SCREEN_LABEL = "OnBoarding Looking For Screen";
    private final String TAG = LogUtils.makeLogTag(OnBoardingLookingForFragment.class);
    @Bind(R.id.rv_looking_for_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_how_can_progress_bar)
    ProgressBar mProgressBar;
    private LookingForLableValues lookingForLableValues;
    private GenericRecyclerViewAdapter mAdapter;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Inject
    AppUtils mAppUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.looking_for_layout, container, false);
        ButterKnife.bind(this, view);
        mOnBoardingPresenter.attachView(this);
        ((OnBoardingActivity) getActivity()).mAppbarLayout.setVisibility(View.VISIBLE);
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
            }

            @Override
            public void onShow() {

            }

            @Override
            public void dismissReactions() {

            }
        });
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_ONBOARDING_LOOKING_FOR));
        return view;
    }

    private List<LookingForLableValues> setFilterValues() {
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_LOOKING_FOR_KEY)) {
            {
                HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_LOOKING_FOR_KEY);
                List<LookingForLableValues> listBoardingList = new ArrayList<>();
                if(StringUtil.isNotEmptyCollection(hashMap.get(AppConstants.MASTER_DATA_LOOKING_FOR_KEY))) {
                    for(LabelValue labelValue:hashMap.get(AppConstants.MASTER_DATA_LOOKING_FOR_KEY))
                    {
                        LookingForLableValues lookingForLableValues=new LookingForLableValues();
                        lookingForLableValues.setValue(labelValue.getValue());
                        lookingForLableValues.setLabel(labelValue.getLabel());
                        lookingForLableValues.setDesc(labelValue.getDesc());
                        lookingForLableValues.setImgUrl(labelValue.getImgUrl());
                        listBoardingList.add(lookingForLableValues);
                    }
                }
               // Set<String> lookingForCategorySet = hashMap.keySet();
               /* for (String lookingForCategory : lookingForCategorySet) {
                    LookingForData lookingForData = new LookingForData();
                    lookingForData.setFragmentName(AppConstants.HOW_SHEROES_CAN_HELP);
                    lookingForData.setCategory(lookingForCategory);
                    lookingForData.setBoardingDataList(hashMap.get(lookingForCategory));
                    listBoardingList.add(lookingForData);
                }*/
                return listBoardingList;
            }
        }
        return null;
    }

    public void onLookingForRequestClick(LookingForLableValues lookingForLableValues) {
       this.lookingForLableValues=lookingForLableValues;
       mAdapter.notifyDataSetChanged();
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
                    ((OnBoardingActivity) getActivity()).onLookingForHowCanSheroesNextClick(lookingForLableValues);
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), FeedParticipationEnum.ERROR_ON_ONBOARDING);
                    break;
            }
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }


    @OnClick(R.id.ripple_finish)
    public void rippleFinishClick() {
        if(lookingForLableValues!=null) {
            Set<Long> opportunityIds = new HashSet<>();
            opportunityIds.add(lookingForLableValues.getValue());
            mOnBoardingPresenter.getLookingForHowCanToPresenter(mAppUtils.boardingLookingHowCanFormDataRequestBuilder(opportunityIds));
        }else
        {
            Toast.makeText(getActivity(),getString(R.string.ID_SELECT_LOOKING_FOR),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mOnBoardingPresenter.detachView();
        ((OnBoardingActivity) getActivity()).mAppbarLayout.setVisibility(View.GONE);

    }
}
