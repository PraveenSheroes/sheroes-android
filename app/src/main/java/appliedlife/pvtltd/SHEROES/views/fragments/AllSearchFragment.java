package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 09-01-2017.
 */
public class AllSearchFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(AllSearchFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_search_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_search_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.li_no_search_result)
    LinearLayout mLiNoSearchResult;
    @Bind(R.id.tv_search_result)
    TextView tvSearchResult;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private FragmentListRefreshData mFragmentListRefreshData;
    private Handler mHandler = new Handler();
    public static AllSearchFragment createInstance() {
        AllSearchFragment allSearchFragment = new AllSearchFragment();
        return allSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.EMPTY_STRING);
        mHomePresenter.attachView(this);
        editTextWatcher();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeSearchActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        super.setAllInitializationForFeeds(mFragmentListRefreshData,  mAdapter, manager, mRecyclerView, mHomePresenter, mAppUtils, mProgressBar);
        return view;
    }


    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList=feedResponsePojo.getFeedDetails();
        if(StringUtil.isNotEmptyCollection(feedDetailList)&&mAdapter!=null) {
            mLiNoSearchResult.setVisibility(View.GONE);
            mAdapter.setCallForRecycler(AppConstants.ALL_SEARCH);
            mAdapter.setSheroesGenericListData(feedDetailList);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            mLiNoSearchResult.setVisibility(View.VISIBLE);
            tvSearchResult.setText(getString(R.string.ID_NO_RESULT_FOUND_SEARCH)+((HomeSearchActivity)getActivity()).mSearchEditText.getText().toString());
        }
    }
    public void setEditText(String stringForSearch)
    {
        mSearchDataName = stringForSearch;
        /**hitting the servers to get data if length is greater than threshold defined **/
        if(StringUtil.isNotNullOrEmptyString(mSearchDataName)) {
            mHandler.removeCallbacks(mFilterTask);
            mHandler.postDelayed(mFilterTask, AppConstants.SEARCH_CONSTANT_DELAY);
        }
    }
    public void saveRecentSearchData(FeedDetail feedDetail)
    {
        List<RecentSearchData> recentSearchData= new ArrayList<>();
        RecentSearchData masterData=new RecentSearchData();
        masterData.setInternalId(feedDetail.getEntityOrParticipantId());
        masterData.setEntityOrParticipantId(feedDetail.getEntityOrParticipantId());
        Gson gson = new Gson();
        String feedObject=gson.toJson(feedDetail, FeedDetail.class);
        masterData.setRecentSearchFeed(feedObject);
        recentSearchData.add(masterData);
        mHomePresenter.saveMasterDataTypes(recentSearchData);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }


    /**
     * When user type city name it works for each character.
     */
    protected void editTextWatcher() {
        ((HomeSearchActivity) getActivity()).mSearchEditText.addTextChangedListener(dataSearchTextWatcher());
        ((HomeSearchActivity) getActivity()).mSearchEditText.setFocusableInTouchMode(true);
        ((HomeSearchActivity) getActivity()).mSearchEditText.requestFocus();
    }

    /**
     * Text watcher workes on every character change and make hit for server accordingly.
     */
    private TextWatcher dataSearchTextWatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable inputSearch) {

                if (StringUtil.isNotNullOrEmptyString(inputSearch.toString())&&inputSearch.toString().length()>AppConstants.THREE_CONSTANT)
                {
                    mLiNoSearchResult.setVisibility(View.GONE);
                    mSearchDataName = inputSearch.toString();
                    /**hitting the servers to get data if length is greater than threshold defined **/
                    mHandler.removeCallbacks(mFilterTask);
                    mHandler.postDelayed(mFilterTask, AppConstants.SEARCH_CONSTANT_DELAY);
                }
            }
        };
    }
    /**
     * Runnable use to make network call on every character change while search for city name.
     */
    Runnable mFilterTask = new Runnable()
    {
        @Override
        public void run()
        {
            if (!isDetached())
            {
                mSearchDataName = mSearchDataName.trim();
                mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.SEARCH_SUB_TYPE,mSearchDataName ,mFragmentListRefreshData.getPageNo(),AppConstants.ALL_SEARCH));
            }
        }
    };
}