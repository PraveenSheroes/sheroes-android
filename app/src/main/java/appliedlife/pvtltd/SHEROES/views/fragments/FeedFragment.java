package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.FeedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 27/12/17.
 */

public class FeedFragment extends BaseFragment implements IFeedView, FeedItemCallback {
    public static final String SCREEN_LABEL = "Feed Fragment";

    @Inject
    AppUtils mAppUtils;

    @Inject
    FeedPresenter mFeedPresenter;

    @Inject
    Preference<LoginResponse> mUserPreference;

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

    @Override
    public void onArticleUnBookMarkClicked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), articleSolrObj.isBookmarked());
    }

    @Override
    public void onArticleBookMarkClicked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), articleSolrObj.isBookmarked());
    }

    @Override
    public void onArticleItemClicked(ArticleSolrObj articleSolrObj) {
        ArticleActivity.navigateTo(getActivity(), articleSolrObj, getScreenName(), null,  AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
    }

    @Override
    public void onArticleShared(ArticleSolrObj articleSolrObj) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, articleSolrObj.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        AnalyticsManager.trackPostAction(Event.POST_SHARED, articleSolrObj, getScreenName());
    }

    @Override
    public void onUserPostClicked(UserPostSolrObj userPostSolrObj) {
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj , AppConstants.REQUEST_CODE_FOR_POST_DETAIL, null, false);
    }

    @Override
    public void onUserPostCommentClicked(UserPostSolrObj userPostSolrObj) {
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj , AppConstants.REQUEST_CODE_FOR_POST_DETAIL, null, true);
    }

    @Override
    public void onUserPostImageClicked(UserPostSolrObj userPostObj) {
        AlbumActivity.navigateTo(getActivity(), userPostObj, getScreenName(), null);
    }

    @Override
    public void onPostMenuClicked(final UserPostSolrObj userPostObj, View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
            if (adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            CommunityPostActivity.navigateTo(getActivity(), userPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST);
                            return true;
                        case R.id.delete:
                            AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostObj, getScreenName());
                            mFeedPresenter.deleteCommunityPostFromPresenter(mAppUtils.deleteCommunityPostRequest(userPostObj.getIdOfEntityOrParticipant()));
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    @Override
    public void onCommentMenuClicked(UserPostSolrObj userPostObj, TextView tvFeedCommunityPostUserCommentPostMenu) {
        PopupMenu popup = new PopupMenu(getActivity(), tvFeedCommunityPostUserCommentPostMenu);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
            if (adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                        //    onEditMenuClicked(comment);
                            return true;
                        case R.id.delete:
                        //    onDeleteMenuClicked(comment);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

  /*  private void onDeleteMenuClicked(Comment comment) {
        HashMap<String, Object> propertiesDelete =
                new EventProperty.Builder()
                        .id(Long.toString(comment.getId()))
                        .postId(Long.toString(comment.getEntityId()))
                        .postType(AnalyticsEventType.COMMUNITY.toString())
                        .body(comment.getComment())
                        .build();
        trackEvent(Event.REPLY_DELETED, propertiesDelete);
        mFeedPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(comment.getEntityId(), comment.getComment(), false, false, comment.getId()), AppConstants.ONE_CONSTANT);
    }

    private void onEditMenuClicked(Comment comment) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(comment.getId()))
                        .postId(Long.toString(comment.getEntityId()))
                        .postType(AnalyticsEventType.COMMUNITY.toString())
                        .body(comment.getComment())
                        .build();
        trackEvent(Event.REPLY_EDITED, properties);
        mInputText.setText(comment.getComment());
        mInputText.setSelection(comment.getComment().length());
        mPostDetailPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(comment.getEntityId(), comment.getComment(), false, false, comment.getId()), AppConstants.ONE_CONSTANT);
    }*/
}
