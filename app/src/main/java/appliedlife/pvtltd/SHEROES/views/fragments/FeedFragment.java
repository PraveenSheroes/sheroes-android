package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.FeedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 27/12/17.
 */

public class FeedFragment extends BaseFragment implements IFeedView, FeedItemCallBack{
    public static final String SCREEN_LABEL = "Feed Fragment";

    @Inject
    AppUtils mAppUtils;

    @Inject
    FeedPresenter mFeedPresenter;

    // region View variables
    @Bind(R.id.swipeRefreshContainer)
    SwipeRefreshLayout mSwipeRefresh;

    @Bind(R.id.feed)
    RecyclerView mFeedRecyclerView;
    // endregion

    private FeedAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        mFeedPresenter.attachView(this);

        // Initialize recycler view
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFeedRecyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) mFeedRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mAdapter = new FeedAdapter(getContext(), this);
        mFeedRecyclerView.setAdapter(mAdapter);

        // Initialize Swipe Refresh Layout
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // mNewsfeedPresenter.fetchFeed(NewsfeedPresenterImpl.NORMAL_REQUEST);
            }
        });
        mSwipeRefresh.setColorSchemeResources(R.color.accent);

        fetchFeed();
        return view;
    }

    public void fetchFeed(){
        FeedRequestPojo feedRequestPojo = mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, 1);
        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
        FragmentListRefreshData mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.HOME_FRAGMENT, AppConstants.NO_REACTION_CONSTANT, false);
        mFeedPresenter.getNewHomeFeedFromPresenter(feedRequestPojo, mAppUtils.appIntroRequestBuilder(AppConstants.APP_INTRO),mFragmentListRefreshData);
    }

    @Override
    public void showFeedList(List<FeedDetail> feedDetailList) {
        mAdapter.feedFinishedLoading();
        mAdapter.setData(feedDetailList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public List getListData() {
        return null;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }

    @Override
    public void championProfile(BaseResponse baseResponse, int championValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }
}
