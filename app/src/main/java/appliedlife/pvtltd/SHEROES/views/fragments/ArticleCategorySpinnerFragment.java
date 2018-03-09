package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class ArticleCategorySpinnerFragment extends BaseFragment implements HomeView {
    private static final String SCREEN_LABEL = "Article Category Spinner Screen";
    private final String TAG = LogUtils.makeLogTag(ArticleCategorySpinnerFragment.class);
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home_spinner_layout, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mHomeSpinnerItemList = Parcels.unwrap(bundle.getParcelable(AppConstants.HOME_SPINNER_FRAGMENT));
        }
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
            mAdapter.setSheroesGenericListData(mHomeSpinnerItemList);
            mAdapter.notifyDataSetChanged();
        } else {
            mHomePresenter.getMasterDataToPresenter();
        }

        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_ARTICLE_SELECT_CATEGORY));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = AppConstants.NAV_ARTICLES;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
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
        if(getActivity()!=null && !getActivity().isFinishing()) {
            ((HomeActivity) getActivity()).onCancelDone(AppConstants.NO_REACTION_CONSTANT);
        }

    }

    @OnClick(R.id.tv_done)
    public void onDoneClick() {
        if(getActivity()!=null && !getActivity().isFinishing()) {
            ((HomeActivity) getActivity()).onCancelDone(AppConstants.ONE_CONSTANT);
            ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SEARCH_FILTER, GoogleAnalyticsEventActions.USED_FILTER_ON_ARTICLES, AppConstants.EMPTY_STRING);
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
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