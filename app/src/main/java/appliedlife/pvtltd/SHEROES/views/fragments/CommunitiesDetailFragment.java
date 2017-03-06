package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 01-02-2017.
 */

public class CommunitiesDetailFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(CommunitiesDetailFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_communities_detail_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_communities_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_communities_detail)
    SwipeRefreshLayout swipeView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentIntractionWithActivityListner mHomeActivityFragmentIntractionWithActivityListner;
    private SwipPullRefreshList mPullRefreshList;
    @Bind(R.id.tv_join_view)
    TextView mTvJoinView;
    @Inject
    AppUtils mAppUtils;
    int pageNo=AppConstants.ONE_CONSTANT;
    private FragmentListRefreshData mFragmentListRefreshData;
    private FeedDetail mFeedDetail;
    public static CommunitiesDetailFragment createInstance(FeedDetail feedDetail) {
        CommunitiesDetailFragment communitiesDetailFragment = new CommunitiesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, feedDetail);
        communitiesDetailFragment.setArguments(bundle);
        return communitiesDetailFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof FragmentIntractionWithActivityListner) {
                mHomeActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communities_detail, container, false);
        ButterKnife.bind(this, view);
        if(null!=getArguments())
        {
            mFeedDetail =getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        mFragmentListRefreshData=new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.COMMUNITY_POST_FRAGMENT,mFeedDetail.getId());
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager,mFragmentListRefreshData) {
            @Override
            public void onHide() {
                if (mTvJoinView.getVisibility() == View.GONE) {
                    mTvJoinView.setVisibility(View.VISIBLE);
                    mTvJoinView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }

            @Override
            public void onShow() {
                if (mTvJoinView.getVisibility() == View.VISIBLE) {
                    mTvJoinView.setVisibility(View.GONE);
                    mTvJoinView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }

            @Override
            public void dismissReactions() {

            }
        });
        if(StringUtil.isNotNullOrEmptyString(mFeedDetail.getId())) {
            mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo()));
        }
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.info("swipe", "*****************end called");
                mPullRefreshList.setPullToRefresh(true);
                mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST,mFragmentListRefreshData.getPageNo()));
            }
        });
        return view;
    }


    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {

    }



     @Override
        public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
            if (StringUtil.isNotEmptyCollection(feedDetailList)) {
                pageNo=mFragmentListRefreshData.getPageNo();
                if(pageNo==AppConstants.ONE_CONSTANT)
                {
                    FeedDetail feedDetail = new FeedDetail();
                    //TODO:: Please remove this or correct
                    feedDetail.setSubType(AppConstants.MY_COMMUNITIES_HEADER);
                    feedDetailList.add(0, feedDetail);
                }
                mFragmentListRefreshData.setPageNo(++pageNo);
                mPullRefreshList.allListData(feedDetailList);
                mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
                if (!mPullRefreshList.isPullToRefresh()) {
                    mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - feedDetailList.size(), 0);
                } else {
                    mLayoutManager.scrollToPositionWithOffset(0, 0);
                }
                swipeView.setRefreshing(false);
            }
        }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

    }



    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg) {
        mHomeActivityFragmentIntractionWithActivityListner.onShowErrorDialog();
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}

