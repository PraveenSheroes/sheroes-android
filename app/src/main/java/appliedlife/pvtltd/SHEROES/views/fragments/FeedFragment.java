package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 27/12/17.
 */

public class FeedFragment extends BaseFragment {

    // region View variables
    @Bind(R.id.swipeRefreshContainer)
    SwipeRefreshLayout mSwipeRefresh;

    @Bind(R.id.feed)
    RecyclerView mFeedRecyclerView;

    // endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        // Initialize recycler view
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFeedRecyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) mFeedRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        // Initialize Swipe Refresh Layout
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // mNewsfeedPresenter.fetchFeed(NewsfeedPresenterImpl.NORMAL_REQUEST);
            }
        });
        mSwipeRefresh.setColorSchemeResources(R.color.accent);

       /* mNewsfeedPresenter = PresenterFactory.createNewsfeedPresenter(this);
        createNewsFeedAdapter();
        mFeedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                // We shouldn't fetch if user is scrolling up or there's a request in flight already
                if (dy <= 0 || mNewsfeedPresenter.isFeedLoading()) {
                    return;
                }

                final int visiblePostsCount = recyclerView.getChildCount();
                final int totalPostsCount = linearLayoutManager.getItemCount();
                final int firstVisiblePost = linearLayoutManager.findFirstVisibleItemPosition();

                if ((totalPostsCount - visiblePostsCount) <= (firstVisiblePost + VISIBLE_POSTS_THRESHOLD)) {
                    mNewsfeedPresenter.fetchFeed(NewsfeedPresenterImpl.LOAD_MORE_REQUEST);
                    mNewsfeedAdapter.feedStartedLoading();
                }

            }
        });*/

        return view;
    }

    @Override
    public String getScreenName() {
        return null;
    }
}
