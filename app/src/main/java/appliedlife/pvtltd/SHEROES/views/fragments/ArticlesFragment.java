package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 09-01-2017.
 */

public class ArticlesFragment extends BaseFragment {
    //region constants
    public static final String SCREEN_LABEL = "Article Listing Screen";
    private final String TAG = LogUtils.makeLogTag(ArticlesFragment.class);
    private int mPageNo = AppConstants.ONE_CONSTANT;
    //endregion constants

    //region injected variables
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    AppUtils mAppUtils;
    //endregion injected variables

    //region member variables
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    private FragmentListRefreshData mFragmentListRefreshData;
    private HidingScrollListener mHidingScrollListener;
    private List<FeedDetail> mTrendingFeedDetail = new ArrayList<>();
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    private boolean mIsSearch = false;
    private String mSearchText, mSearchCategory;
    private FeedRequestPojo mFeedRequestPojo;
    private List<Long> mCategoryIdList = new ArrayList<>();
    private View mView;
    private String mScreenLabel;
    private boolean mIsActiveTabFragment;
    //endregion member variables

    //region view variables
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout mSwipeView;
    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;
    @Bind(R.id.rl_empty)
    RelativeLayout emptyLayout;
    @Bind(R.id.progress_bar_first_load)
    ProgressBar mProgressBarFirstLoad;
    @Bind(R.id.loader_gif)
    CardView loaderGif;
    @Bind(R.id.no_internet)
    CardView noInternet;
    @Bind(R.id.tv_no_results_title)
    TextView noResultsTitleTxt;
    @Bind(R.id.tv_no_results_subtitle)
    TextView noResultsSubTitleTxt;
    @Bind(R.id.iv_image)
    ImageView noResultsImage;
    //endregion view variables

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        mView = inflater.inflate(R.layout.fragment_card_list, container, false);
        ButterKnife.bind(this, mView);

        if (getArguments() != null) {
            mScreenLabel = getArguments().getString(AppConstants.SCREEN_NAME);
        }

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ARTICLE_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        loaderGif.setVisibility(View.VISIBLE);
        if (getArguments() != null) {
            mCategoryIdList = (List<Long>) getArguments().getSerializable(AppConstants.ARTICLE_FRAGMENT);
        }
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (BaseHolderInterface) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mHidingScrollListener = new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                if (null != getActivity() && getActivity() instanceof HomeActivity)
                    ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
                if (null != getActivity() && getActivity() instanceof HomeActivity)
                    ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {

            }
        };
        mRecyclerView.addOnScrollListener(mHidingScrollListener);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, null, mRecyclerView, 0, 0, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
        mFragmentListRefreshData.setCategoryIdList(mCategoryIdList);
        categoryArticleFilter(mCategoryIdList);

        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categoryArticleFilter(mCategoryIdList);
            }
        });

        if (null != getActivity() && getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).changeFragmentWithCommunities();
            ((HomeActivity) getActivity()).articleUi();
        }

        return mView;
    }

    public void categoryArticleFilter(final List<Long> categoryIds) {
        loaderGif.setVisibility(View.VISIBLE);
        mHomePresenter.attachView(this);
        setProgressBar(mProgressBar);
        mPullRefreshList.setPullToRefresh(true);
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mTrendingFeedDetail.clear();
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        mFeedRequestPojo = mAppUtils.articleCategoryRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo(), categoryIds);
        mFeedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);

        if (mIsSearch && mSearchText != null && mSearchCategory != null) {
            mHomePresenter.getArticleFeeds(mSearchText, mSearchCategory, true, true);
        } else {
            mHomePresenter.getFeedFromPresenter(mFeedRequestPojo);
        }
    }

    public void setSearchParamterFromDeeplink(boolean isSearch, String searchText, String searchCategory) {
        mIsSearch = isSearch;
        mSearchText = searchText;
        mSearchCategory = searchCategory;
    }

    public void fetchSearchedArticles(boolean isSearch, String searchText, String searchCategory) {
        loaderGif.setVisibility(View.VISIBLE);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ARTICLE_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        setFilterParams(isSearch, searchText, searchCategory);
        mHomePresenter.getArticleFeeds(mSearchText, mSearchCategory, false, true);
    }

    public void setFilterParams(boolean isSearch, String searchText, String searchCategory) {
        this.mIsSearch = isSearch;
        this.mSearchText = searchText;
        this.mSearchCategory = searchCategory;
        mHidingScrollListener.setSearchParameter(mIsSearch, mSearchText, mSearchCategory);
    }

    public List<FeedDetail> handleArticleFeedEnded(List<FeedDetail> data, FeedDetail feedProgressBar, FeedResponsePojo feedResponsePojo) {
        if (mIsSearch) {
            if (StringUtil.isNotNullOrEmptyString(feedResponsePojo.getNextToken())) {
                data.add(feedProgressBar);
            } else {
                mHidingScrollListener.setSearchFeedEnded(true);
            }
        } else {
            data.add(feedProgressBar);
        }
        return data;
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
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {  //When UI is visible to user
            mIsActiveTabFragment = true;

            if (getParentFragment() instanceof SearchFragment) {
                String screenName = ((SearchFragment) getParentFragment()).getInactiveTabFragmentName();
                if (mScreenLabel != null && screenName != null && !mScreenLabel.equalsIgnoreCase(screenName)) {
                    //Send event of previous selected tab with duration, and start the time capture for current selected tab
                    HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .tabTitle(SearchFragment.searchTabName)
                                    .sourceTabTitle(HomeFragment.SOURCE_ACTIVE_TAB)
                                    .build();
                    AnalyticsManager.trackScreenView(screenName, properties);
                    AnalyticsManager.timeScreenView(mScreenLabel);
                }
            }
        } else { //When UI is not visible to user

            //Capture the screen event of the tab got unselected
            if (mIsActiveTabFragment && mScreenLabel != null && !(getActivity() instanceof HomeActivity)) {
                AnalyticsManager.trackScreenView(mScreenLabel, getExtraProperties());
            }

            mIsActiveTabFragment = false;
        }
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        emptyLayout.setVisibility(View.GONE);
        List<FeedDetail> feedDetailList;
        List<FeedDetail> newFeedDetailList = new ArrayList<>();
        boolean isTrendingData = false;
        if (null != feedResponsePojo && StringUtil.isNotEmptyCollection(feedResponsePojo.getFeaturedDocs())) {
            feedDetailList = feedResponsePojo.getFeaturedDocs();
            isTrendingData = true;
        } else {
            feedDetailList = feedResponsePojo.getFeedDetails();
        }
        mProgressBarFirstLoad.setVisibility(View.GONE);
        loaderGif.setVisibility(View.GONE);
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            newFeedDetailList.addAll(feedDetailList);
            mLiNoResult.setVisibility(View.GONE);
            mPageNo = mFragmentListRefreshData.getPageNo();
            if (mPageNo == AppConstants.ONE_CONSTANT) {
                mFragmentListRefreshData.setPostedDate(feedDetailList.get(0).getPostedDate());
            }
            if (isTrendingData) {
                for (FeedDetail feedDetail : feedDetailList) {
                    feedDetail.setTrending(true);
                    mTrendingFeedDetail.add(feedDetail);
                }
                List<FeedDetail> trendingWithNormalArticleList = new ArrayList<>();
                List<FeedDetail> normalArticleList = feedResponsePojo.getFeedDetails();
                if (StringUtil.isNotEmptyCollection(normalArticleList)) {
                    trendingWithNormalArticleList.addAll(mTrendingFeedDetail);
                    trendingWithNormalArticleList.addAll(normalArticleList);
                    if (StringUtil.isNotEmptyCollection(mTrendingFeedDetail)) {
                        for (FeedDetail normalArticle : normalArticleList) {
                            for (FeedDetail trendingFeedDetail : mTrendingFeedDetail) {
                                if (normalArticle.getEntityOrParticipantId() == trendingFeedDetail.getEntityOrParticipantId()) {
                                    trendingWithNormalArticleList.remove(normalArticle);
                                }
                            }
                        }
                    }
                }
                mPullRefreshList.allListData(trendingWithNormalArticleList);
                mFragmentListRefreshData.setPageNo(++mPageNo);
                List<FeedDetail> data = null;
                FeedDetail feedProgressBar = new FeedDetail();
                feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
                data = mPullRefreshList.getFeedResponses();
                data.add(feedProgressBar);
                mAdapter.setSheroesGenericListData(data);
                mAdapter.notifyDataSetChanged();
            } else {
                if (StringUtil.isNotEmptyCollection(mTrendingFeedDetail)) {
                    for (FeedDetail feedDetail : feedDetailList) {
                        for (FeedDetail trendingFeedDetail : mTrendingFeedDetail) {
                            if (feedDetail.getEntityOrParticipantId() == trendingFeedDetail.getEntityOrParticipantId()) {
                                newFeedDetailList.remove(feedDetail);
                            }
                        }
                    }
                }
                mPullRefreshList.allListData(newFeedDetailList);
                mFragmentListRefreshData.setPageNo(++mPageNo);
                List<FeedDetail> data = null;
                FeedDetail feedProgressBar = new FeedDetail();
                feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
                data = mPullRefreshList.getFeedResponses();
                int position = data.size() - newFeedDetailList.size();
                if (position > 0) {
                    data.remove(position - 1);
                }
                data = handleArticleFeedEnded(data, feedProgressBar, feedResponsePojo);
                mAdapter.setSheroesGenericListData(data);
                if (mPageNo == AppConstants.TWO_CONSTANT) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.notifyItemRangeChanged(position + 1, newFeedDetailList.size());
                }
            }
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            mLiNoResult.setVisibility(View.VISIBLE);
        }
        mSwipeView.setRefreshing(false);
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        super.getSuccessForAllResponse(baseResponse, feedParticipationEnum);
    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {
    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
    }

    @Override
    public void onConfigFetched() {
    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void showEmptyScreen(String s) {
        loaderGif.setVisibility(View.GONE);
        if (mIsSearch) {
            emptyLayout.setVisibility(View.VISIBLE);
            noResultsSubTitleTxt.setText(s);
            noResultsTitleTxt.setText(getString(R.string.empty_articles));
            noResultsImage.setImageResource(R.drawable.articles_empty_vector);
        }
    }

    @OnClick({R.id.tv_retry_for_internet})
    public void onRetryClick() {
        noInternet.setVisibility(View.GONE);
        loaderGif.setVisibility(View.VISIBLE);
        if (mIsSearch && mSearchText != null && mSearchCategory != null) {
            mHomePresenter.getArticleFeeds(mSearchText, mSearchCategory, true, true);
        } else {
            mHomePresenter.getFeedFromPresenter(mFeedRequestPojo);
        }
    }

    @OnClick({R.id.tv_goto_setting})
    public void onSettingClick() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorMsg) && errorMsg.equalsIgnoreCase(AppConstants.CHECK_NETWORK_CONNECTION)) {
            noInternet.setVisibility(View.VISIBLE);
            loaderGif.setVisibility(View.GONE);
        } else {
            super.showError(errorMsg, feedParticipationEnum);
        }
    }

    @Override
    public boolean shouldTrackScreen() {
        if (HomeActivity.isSearchClicked)
            return false;
        else
            return true;
    }
}
