package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/*
* This class is a ScrollListener for RecyclerView that allows to show/hide
* views when list is scrolled. It assumes that you have added a header
* to your list. @see pl.michalz.hideonscrollexample.adapter.partone.RecyclerAdapter
* */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int HIDE_THRESHOLD = 20;
    private int mScrolledDistance = 0;
    private boolean mControlsVisible = true;
    HomePresenter mHomePresenter;
    RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 2;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public HidingScrollListener(HomePresenter homePresenter, RecyclerView recyclerView, LinearLayoutManager manager) {
        mHomePresenter=homePresenter;
        mRecyclerView=recyclerView;
        mManager=manager;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        dismissReactions();
        if (firstVisibleItem == 0) {
            if(!mControlsVisible) {
                onShow();
                mControlsVisible = true;
            }
        } else {
            if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                onHide();
                mControlsVisible = false;
                mScrolledDistance = 0;
            } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                onShow();
                mControlsVisible = true;
                mScrolledDistance = 0;
            }
        }
        if((mControlsVisible && dy>0) || (!mControlsVisible && dy<0)) {
            mScrolledDistance += dy;
        }
        visibleItemCount = mRecyclerView.getChildCount();
        totalItemCount = mManager.getItemCount();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        totalItemCount = mManager.getItemCount();
        int lastVisibleItem = mManager.findLastVisibleItemPosition();

        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
        //    mHomePresenter.getFeedFromPresenter(MockService.makeCityRequest());
            LogUtils.info("swipe", "*****************on last scrolling");
            loading = true;
        }

    }

    public abstract void onHide();
    public abstract void onShow();
    public abstract void dismissReactions();
}
