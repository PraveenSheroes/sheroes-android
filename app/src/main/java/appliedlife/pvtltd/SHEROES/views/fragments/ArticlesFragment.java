package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
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

/**
 * Created by Praveen_Singh on 09-01-2017.
 */


public class ArticlesFragment extends BaseFragment {
    public static final String SCREEN_LABEL = "Article Listing Screen";
    private final String TAG = LogUtils.makeLogTag(ArticlesFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout mSwipeView;
    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    @Inject
    AppUtils mAppUtils;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private List<FeedDetail> mTrendingFeedDetail = new ArrayList<>();
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    @Bind(R.id.progress_bar_first_load)
    ProgressBar mProgressBarFirstLoad;
    @Bind(R.id.loader_gif)
    CardView loaderGif;
    private List<Long> categoryIdList = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.fragment_card_list, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ARTICLE_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        loaderGif.setVisibility(View.VISIBLE);
        if (getArguments() != null) {
            categoryIdList = (List<Long>) getArguments().getSerializable(AppConstants.ARTICLE_FRAGMENT);
        }
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                if (null != getActivity())
                    ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
                if (null != getActivity())
                    ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {

            }
        });
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, null, mRecyclerView, 0, 0, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
        mFragmentListRefreshData.setCategoryIdList(categoryIdList);
        categoryArticleFilter(categoryIdList);

        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categoryArticleFilter(categoryIdList);
            }
        });
        if (null != getActivity()) {
            ((HomeActivity) getActivity()).changeFragmentWithCommunities();
            ((HomeActivity) getActivity()).articleUi();
        }
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(AppConstants.ARTICLE_LISTING);

        return view;
    }

    public void categoryArticleFilter(final List<Long> categoryIds) {
        mHomePresenter.attachView(this);
        setProgressBar(mProgressBar);
        mPullRefreshList.setPullToRefresh(true);
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mTrendingFeedDetail.clear();
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        FeedRequestPojo feedRequestPojo = mAppUtils.articleCategoryRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo(), categoryIds);
        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
        mHomePresenter.getFeedFromPresenter(feedRequestPojo);
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
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
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
                data.add(feedProgressBar);
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
}
