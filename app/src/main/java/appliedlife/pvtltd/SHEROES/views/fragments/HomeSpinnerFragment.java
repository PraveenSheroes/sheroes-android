package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class HomeSpinnerFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(HomeSpinnerFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_spinner_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_spinner_progress_bar)
    ProgressBar mProgressBar;
    GenericRecyclerViewAdapter mAdapter;
    @Bind(R.id.tv_cancel)
    TextView mTvCancel;
    @Bind(R.id.tv_done)
    TextView mTvDone;
    List<HomeSpinnerItem> mHomeSpinnerItemList;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private HomeSpinnerFragmentListner homeSpinnerFragmentListner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (mActivity instanceof HomeSpinnerFragmentListner) {
                homeSpinnerFragmentListner = (HomeSpinnerFragmentListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home_spinner_layout, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        setProgressBar(mProgressBar);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mHomeSpinnerItemList = bundle.getParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT);
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
            mAdapter.setSheroesGenericListData(mHomeSpinnerItemList);
            mAdapter.notifyDataSetChanged();
        } else {
            mHomePresenter.getMasterDataToPresenter();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {
        setArticleCategoryFilterValues();
    }

    @OnClick(R.id.tv_cancel)
    public void onCancelClick() {
        homeSpinnerFragmentListner.onCancelDone(AppConstants.NO_REACTION_CONSTANT);
    }

    @OnClick(R.id.tv_done)
    public void onDoneClick() {
        homeSpinnerFragmentListner.onCancelDone(AppConstants.ONE_CONSTANT);
    }

    public interface HomeSpinnerFragmentListner {
        void onCancelDone(int pressedEvent);
    }

    private void setArticleCategoryFilterValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult = mUserPreferenceMasterData.get().getData();
            if (null != masterDataResult && null != masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY)) {
                {
                    HashMap<String, ArrayList<LabelValue>> hashMap = masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY);
                    List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
                    if (StringUtil.isNotEmptyCollection(labelValueArrayList)) {
                        List<HomeSpinnerItem> homeSpinnerItemList = new ArrayList<>();
                        HomeSpinnerItem homeSpinnerFirst=new HomeSpinnerItem();
                        homeSpinnerFirst.setName(AppConstants.FOR_ALL);
                        homeSpinnerItemList.add(homeSpinnerFirst);
                        for (LabelValue lookingFor : labelValueArrayList) {

                            HomeSpinnerItem homeSpinnerItem = new HomeSpinnerItem();
                            homeSpinnerItem.setId(lookingFor.getValue());
                            homeSpinnerItem.setName(lookingFor.getLabel());
                            homeSpinnerItemList.add(homeSpinnerItem);
                        }
                        mHomeSpinnerItemList = homeSpinnerItemList;
                    }
                    mAdapter.setSheroesGenericListData(mHomeSpinnerItemList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}