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
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 30-01-2017.
 */

public class MyCommunitiesFragment  extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(MyCommunitiesFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout swipeView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private HomeActivityIntractionListner mHomeActivityIntractionListner;
    private SwipPullRefreshList mPullRefreshList;
    private AppUtils mAppUtils;
    public static MyCommunitiesFragment createInstance() {
        MyCommunitiesFragment myCommunitiesFragment = new MyCommunitiesFragment();
        return myCommunitiesFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityIntractionListner) {
                mHomeActivityIntractionListner = (HomeActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mAppUtils = AppUtils.getInstance();
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager,AppConstants.MY_COMMUNITIES_FRAGMENT) {
            @Override
            public void onHide() {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {

            }
        });
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                LogUtils.info("swipe", "*****************end called");
                mPullRefreshList.setPullToRefresh(true);
                mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY));
            }
        });
        return view;
    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - feedDetailList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
            swipeView.setRefreshing(false);
        }
    }

    @Override
    public void getLikesSuccess(String success) {

    }


    @Override
    public void getDB(List<MasterData> masterDatas) {

    }

    @Override
    public void showNwError() {
        mHomeActivityIntractionListner.onErrorOccurence();
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
        mAdapter.notifyDataSetChanged();
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

    public interface HomeActivityIntractionListner {
        void onErrorOccurence();
    }

}
