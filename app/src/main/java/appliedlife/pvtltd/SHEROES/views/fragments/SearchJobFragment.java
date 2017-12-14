package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class SearchJobFragment extends BaseFragment implements HomeView {
    private static final String SCREEN_LABEL = "Job Search Screen";
    private final String TAG = LogUtils.makeLogTag(SearchJobFragment.class);
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
    TextView mTvSearchResult;
    @Bind(R.id.swipe_view_search)
    SwipeRefreshLayout mSwipeView;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private FragmentListRefreshData mFragmentListRefreshData;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private Handler mHandler = new Handler();
    public static SearchJobFragment createInstance() {
        SearchJobFragment searchJobFragment = new SearchJobFragment();
        return searchJobFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
     //   mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.NO_REACTION_CONSTANT);
        editTextWatcher();
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_SEARCH_JOB_SCREEN, GoogleAnalyticsEventActions.USED_SEARCH_JOB_SCREEN, AppConstants.EMPTY_STRING);
     //   jobSearcgPagination(mFragmentListRefreshData);
        return view;
    }

    private void jobSearcgPagination(FragmentListRefreshData fragmentListRefreshData) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeSearchActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
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
        super.setAllInitializationForFeeds(mFragmentListRefreshData,  mAdapter, mLayoutManager, mRecyclerView, mHomePresenter, mAppUtils, mProgressBar);
        mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.FEED_JOB,mFragmentListRefreshData.getSearchStringName() ,mFragmentListRefreshData.getPageNo(),AppConstants.ALL_SEARCH,null,AppConstants.PAGE_SIZE));

        HashMap<String, Object> properties = new EventProperty.Builder()
                .keyword(mFragmentListRefreshData.getSearchStringName())
                .build();
        AnalyticsManager.trackEvent(Event.JOBS_SEARCH, getScreenName(),  properties);

        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList=feedResponsePojo.getFeedDetails();
        if(StringUtil.isNotEmptyCollection(feedDetailList)&&mAdapter!=null) {
            mLiNoSearchResult.setVisibility(View.GONE);
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.setCallForRecycler(AppConstants.ALL_SEARCH);
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - feedDetailList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
            mSwipeView.setRefreshing(false);
        }
        else  if(StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())&&mAdapter!=null)
        {
            mPageNo = mFragmentListRefreshData.getPageNo();
            if(mPageNo==AppConstants.ONE_CONSTANT)
            {
                mLiNoSearchResult.setVisibility(View.VISIBLE);
                mTvSearchResult.setText(getString(R.string.ID_NO_RESULT_FOUND_SEARCH)+AppConstants.SPACE+((HomeSearchActivity)getActivity()).mSearchEditText.getText().toString());
            }else
            {
                mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
                mAdapter.setCallForRecycler(AppConstants.ALL_SEARCH);
                mAdapter.notifyDataSetChanged();
                if (!mPullRefreshList.isPullToRefresh()) {
                    mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size(), 0);
                } else {
                    mLayoutManager.scrollToPositionWithOffset(0, 0);
                }
                mSwipeView.setRefreshing(false);
            }
        }else
        {
            mLiNoSearchResult.setVisibility(View.VISIBLE);
            mTvSearchResult.setText(getString(R.string.ID_NO_RESULT_FOUND_SEARCH)+AppConstants.SPACE+((HomeSearchActivity)getActivity()).mSearchEditText.getText().toString());
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
                /**As soon as user starts typing take the scroll to top **/
             /*   mSearchDataName = inputSearch.toString();
                if (!((HomeSearchActivity) getActivity()).mIsDestroyed) {
                    mAdapter.getFilter().filter(mSearchDataName);
                }*/

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
                mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.NO_REACTION_CONSTANT);
                mFragmentListRefreshData.setSearchStringName(mSearchDataName);
                jobSearcgPagination(mFragmentListRefreshData);
            }
        }
    };

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}