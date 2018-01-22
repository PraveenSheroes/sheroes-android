package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences.Preference;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.FeedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.EndlessRecyclerViewScrollListener;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EventDetailDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 27/12/17.
 */

public class FeedFragment extends BaseFragment implements IFeedView, FeedItemCallback {
    public static final String SCREEN_LABEL = "Community Screen Activity";
    public static final String PRIMARY_COLOR = "Primary Color";
    public static final String TITLE_TEXT_COLOR = "Title Text Color";

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

    @Bind(R.id.empty_view)
    LinearLayout emptyView;

    @Bind(R.id.empty_image)
    ImageView emptyImage;

    @Bind(R.id.empty_text)
    TextView emptyText;

    @Bind(R.id.empty_subtext)
    TextView emptySubText;
    // endregion

    private FeedAdapter mAdapter;
    EventDetailDialogFragment eventDetailDialogFragment;
    private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    private CommunityTab mCommunityTab;
    private boolean hasFeedEnded;
    private String mPrimaryColor = "#6e2f95";
    private String mTitleTextColor = "#ffffff";
    HashMap<String, Object> mScreenProperties;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        mFeedPresenter.attachView(this);

        if (null != getArguments()) {
            Parcelable parcelable = getArguments().getParcelable(CommunityTab.COMMUNITY_TAB_OBJ);
            if (parcelable != null) {
                mCommunityTab = Parcels.unwrap(parcelable);
            }
            mPrimaryColor = getArguments().getString(PRIMARY_COLOR);
            mTitleTextColor = getArguments().getString(TITLE_TEXT_COLOR);
        }
        if (CommonUtil.isNotEmpty(mCommunityTab.dataUrl)) {
            mFeedPresenter.setEndpointUrl(mCommunityTab.dataUrl);
        } else {

        }
        mScreenProperties = new EventProperty.Builder()
                .sourceScreenId(((CommunityDetailActivity)getActivity()).getCommunityId())
                .sourceTabKey(mCommunityTab.key)
                .sourceTabTitle(mCommunityTab.title)
                .build();
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
                mFeedPresenter.fetchFeed(FeedPresenter.NORMAL_REQUEST);
            }
        });
        mSwipeRefresh.setColorSchemeResources(R.color.accent);

        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (mFeedPresenter.isFeedLoading() || hasFeedEnded) {
                    return;
                }
                mAdapter.feedStartedLoading();
                mFeedPresenter.fetchFeed(FeedPresenter.LOAD_MORE_REQUEST);
            }
        };
        mFeedRecyclerView.addOnScrollListener(mEndlessRecyclerViewScrollListener);
        mFeedPresenter.fetchFeed(FeedPresenter.NORMAL_REQUEST);
        return view;
    }

    @Override
    public void showFeedList(List<FeedDetail> feedDetailList) {
        if(CommonUtil.isEmpty(feedDetailList)){
            mFeedRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            loadEmptyView();
        }else {
            mFeedRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        mAdapter.feedFinishedLoading();
        mAdapter.setData(feedDetailList);
        mAdapter.notifyDataSetChanged();
    }

    private void loadEmptyView() {
        if(mCommunityTab!=null){
            if(CommonUtil.isNotEmpty(mCommunityTab.emptyTitle)){
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText(mCommunityTab.emptyTitle);
            }else {
                emptyText.setVisibility(View.GONE);
            }
            if(CommonUtil.isNotEmpty(mCommunityTab.emptyDescription)){
                emptySubText.setVisibility(View.VISIBLE);
                emptySubText.setText(mCommunityTab.emptyDescription);
            }else {
                emptySubText.setVisibility(View.GONE);
            }
            if(CommonUtil.isNotEmpty(mCommunityTab.emptyImageUrl)){
                emptyImage.setVisibility(View.VISIBLE);
                if(getActivity()!=null){
                    Glide.with(getActivity())
                            .load(mCommunityTab.emptyImageUrl)
                            .into(emptyImage);
                }
            }else {
                emptyImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setData(int position, FeedDetail feedDetail) {
        mAdapter.setData(position, feedDetail);
    }

    @Override
    public void invalidateItem(FeedDetail feedDetail) {
        if (getActivity() instanceof CommunityDetailActivity) {
            ((CommunityDetailActivity) getActivity()).invalidateItem(feedDetail);
        }
    }

    @Override
    public void notifyAllItemRemoved(FeedDetail feedDetail) {
        ((CommunityDetailActivity) getActivity()).notifyAllItemRemoved(feedDetail);
    }

    @Override
    public void addAllFeed(List<FeedDetail> feedList) {
        mAdapter.addAll(feedList);
    }

    @Override
    public void setFeedEnded(boolean feedEnded) {
        this.hasFeedEnded = feedEnded;
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
        onChampionProfileClicked((UserPostSolrObj)baseResponse,championValue);
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }

    @Override
    public void onArticleUnBookMarkClicked(ArticleSolrObj articleSolrObj) {
        AnalyticsManager.trackPostAction(Event.POST_BOOKMARKED, articleSolrObj, getScreenName());
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), articleSolrObj.isBookmarked());
    }

    @Override
    public void onArticleBookMarkClicked(ArticleSolrObj articleSolrObj) {
        AnalyticsManager.trackPostAction(Event.POST_UNBOOKMARKED, articleSolrObj, getScreenName());
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), articleSolrObj.isBookmarked());
    }

    @Override
    public void onArticleItemClicked(ArticleSolrObj articleSolrObj) {
        ArticleActivity.navigateTo(getActivity(), articleSolrObj, getScreenName(), mScreenProperties, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
    }

    @Override
    public void onPostShared(FeedDetail feedDetail) {
        String deepLinkUrl;
        if(StringUtil.isNotNullOrEmptyString(feedDetail.getPostShortBranchUrls()))
        {
            deepLinkUrl=feedDetail.getPostShortBranchUrls();
        }else
        {
            deepLinkUrl=feedDetail.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.setPackage(AppConstants.WHATS_APP);
        intent.putExtra(Intent.EXTRA_TEXT,deepLinkUrl);
        startActivity(intent);
        // startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        if(feedDetail.getSubType().equals(AppConstants.FEED_JOB)){
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .title(feedDetail.getNameOrTitle())
                            .companyId(Long.toString(((JobFeedSolrObj)feedDetail).getCompanyMasterId()))
                            .location(feedDetail.getAuthorCityName())
                            .build();
            trackEvent(Event.JOBS_SHARED, properties);
        }else {
            AnalyticsManager.trackPostAction(Event.POST_SHARED, feedDetail, getScreenName());
        }
        AnalyticsManager.trackPostAction(Event.POST_SHARED, feedDetail, getScreenName());
    }
    private void shareCardDetail(UserPostSolrObj userPostObj)
    {
        String deepLinkUrl;
        if(StringUtil.isNotNullOrEmptyString(userPostObj.getPostShortBranchUrls()))
        {
            deepLinkUrl=userPostObj.getPostShortBranchUrls();
        }else
        {
            deepLinkUrl=userPostObj.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT,deepLinkUrl);
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        AnalyticsManager.trackPostAction(Event.POST_SHARED, userPostObj, getScreenName());
    }

    @Override
    public void onUserPostClicked(UserPostSolrObj userPostSolrObj) {
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, mScreenProperties, false, mPrimaryColor, mTitleTextColor);
    }

    @Override
    public void onUserPostCommentClicked(UserPostSolrObj userPostSolrObj) {
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, mScreenProperties, true, mPrimaryColor, mTitleTextColor);
    }

    @Override
    public void onUserPostImageClicked(UserPostSolrObj userPostObj) {
        AlbumActivity.navigateTo(getActivity(), userPostObj, getScreenName(), mScreenProperties);
    }

    @Override
    public void onPostMenuClicked(final UserPostSolrObj userPostObj, View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        long currentUserId = -1;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            currentUserId = mUserPreference.get().getUserSummary().getUserId();
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            // popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
            Menu menu=popup.getMenu();
            menu.add(0, R.id.share, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_share_black), getResources().getString(R.string.ID_SHARE)));
            menu.add(0, R.id.edit, 2, menuIconWithText(getResources().getDrawable(R.drawable.ic_create), getResources().getString(R.string.ID_EDIT)));
            menu.add(0, R.id.delete, 3, menuIconWithText(getResources().getDrawable(R.drawable.ic_delete), getResources().getString(R.string.ID_DELETE)));
            menu.add(0, R.id.top_post, 4, menuIconWithText(getResources().getDrawable(R.drawable.ic_create), getResources().getString(R.string.FEATURE_POST)));

            if (currentUserId!=userPostObj.getAuthorId() && adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }
            if (adminId == AppConstants.TWO_CONSTANT || userPostObj.isCommunityOwner()) {
                popup.getMenu().findItem(R.id.top_post).setVisible(true);
                if(userPostObj.isTopPost()){
                    popup.getMenu().findItem(R.id.top_post).setTitle(R.string.UNFEATURE_POST);
                }else {
                    popup.getMenu().findItem(R.id.top_post).setTitle(R.string.FEATURE_POST);
                }
            } else {
                popup.getMenu().findItem(R.id.top_post).setVisible(false);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.share:
                            shareCardDetail(userPostObj);
                            return true;
                        case R.id.edit:
                            CommunityPostActivity.navigateTo(getActivity(), userPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, mPrimaryColor, mTitleTextColor, mScreenProperties);
                            return true;
                        case R.id.delete:
                            AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostObj, getScreenName());
                            mFeedPresenter.deleteCommunityPostFromPresenter(AppUtils.deleteCommunityPostRequest(userPostObj.getIdOfEntityOrParticipant()), userPostObj);
                            return true;
                        case R.id.top_post:
                            AnalyticsManager.trackPostAction(Event.POST_TOP_POST, userPostObj, getScreenName());
                            mFeedPresenter.editTopPost(AppUtils.topCommunityPostRequestBuilder(userPostObj.communityId, getCreatorType(userPostObj), userPostObj.getListDescription(), userPostObj.getIdOfEntityOrParticipant(),!userPostObj.isTopPost()));
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }
    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    private String getCreatorType(UserPostSolrObj userPostSolrObj) {
        if (userPostSolrObj.isAnonymous()) {
            return AppConstants.ANONYMOUS;
        }else if (userPostSolrObj.getEntityOrParticipantTypeId() == 15) {
            return AppConstants.COMMUNITY_OWNER;
        } else {
            return AppConstants.USER;
        }
    }

    @Override
    public void onCommentMenuClicked(final UserPostSolrObj userPostObj, TextView tvFeedCommunityPostUserCommentPostMenu) {
        PopupMenu popup = new PopupMenu(getActivity(), tvFeedCommunityPostUserCommentPostMenu);
        long currentUserId = -1;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            currentUserId = mUserPreference.get().getUserSummary().getUserId();
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            popup.getMenuInflater().inflate(R.menu.menu_edit_delete_comment, popup.getMenu());
            Comment comment = userPostObj.getLastComments().get(0);
            if (currentUserId !=comment.getEntityAuthorUserId() && adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            onEditMenuClicked(userPostObj);
                            return true;
                        case R.id.delete:
                            onDeleteMenuClicked(userPostObj);
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
    public void onPostBookMarkedClicked(UserPostSolrObj userPostObj) {
        AnalyticsManager.trackPostAction(Event.POST_BOOKMARKED, userPostObj, getScreenName());
        mFeedPresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(userPostObj.getEntityOrParticipantId()), userPostObj.isBookmarked());
    }

    @Override
    public void onLikesCountClicked(long postId) {
        LikeListBottomSheetFragment.showDialog((AppCompatActivity) getActivity(), getScreenName(), postId);
    }

    @Override
    public void onUserPostLiked(UserPostSolrObj userPostObj) {
        mFeedPresenter.getPostLikesFromPresenter(mAppUtils.likeRequestBuilder(userPostObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), userPostObj);
    }

    @Override
    public void onUserPostUnLiked(UserPostSolrObj userPostObj) {
        mFeedPresenter.getPostUnLikesFromPresenter(mAppUtils.likeRequestBuilder(userPostObj.getEntityOrParticipantId(), AppConstants.NO_REACTION_CONSTANT), userPostObj);
    }

    @Override
    public void onArticlePostLiked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.getPostLikesFromPresenter(mAppUtils.likeRequestBuilder(articleSolrObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), articleSolrObj);
    }

    @Override
    public void onArticlePostUnLiked(ArticleSolrObj articleSolrObj) {
        mFeedPresenter.getPostUnLikesFromPresenter(mAppUtils.likeRequestBuilder(articleSolrObj.getEntityOrParticipantId(), AppConstants.NO_REACTION_CONSTANT), articleSolrObj);
    }

    @Override
    public void onCommunityTitleClicked(UserPostSolrObj userPostObj) {
        if (userPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
            if (null != userPostObj) {
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                    if (StringUtil.isNotNullOrEmptyString(userPostObj.getDeepLinkUrl())) {
                        Uri url = Uri.parse(userPostObj.getDeepLinkUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(url);
                        startActivity(intent);
                    }
                }
            }
        } else {
            if (userPostObj.getCommunityId() == 0) {
                ContestActivity.navigateTo(getActivity(), Long.toString(userPostObj.getUserPostSourceEntityId()), userPostObj.getScreenName(), mScreenProperties);

            } else {
                CommunityDetailActivity.navigateTo(getActivity(),  userPostObj.getCommunityId(), getScreenName(), mScreenProperties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
               /* Intent intentFromCommunityPost = new Intent(getActivity(), CommunitiesDetailActivity.class);
                Bundle bundleFromPost = new Bundle();
                bundleFromPost.putBoolean(AppConstants.COMMUNITY_POST_ID, true);
                Parcelable parcelablesss = Parcels.wrap(userPostObj);
                bundleFromPost.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelablesss);
                bundleFromPost.putLong(AppConstants.COMMUNITY_ID, userPostObj.getCommunityId());
                bundleFromPost.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.MY_COMMUNITY);
                intentFromCommunityPost.putExtras(bundleFromPost);
                startActivityForResult(intentFromCommunityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);*/
            }
        }
    }

    @Override
    public void userCommentLikeRequest(UserPostSolrObj userPostSolrObj, boolean isLikedAction, int adapterPosition) {
        if (CommonUtil.isEmpty(userPostSolrObj.getLastComments())) {
            return;
        }
        Comment comment = userPostSolrObj.getLastComments().get(0);
        if (isLikedAction) {
            mFeedPresenter.getCommentLikesFromPresenter(mAppUtils.likeRequestBuilder(comment.getEntityId(), AppConstants.HEART_REACTION_CONSTANT, comment.getCommentsId()), comment, userPostSolrObj);
        } else {
            mFeedPresenter.getCommentUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(comment.getEntityId(), comment.getCommentsId()), comment, userPostSolrObj);
        }
    }

    @Override
    public void onJobPostClicked(JobFeedSolrObj jobFeedObj) {
    }

    @Override
    public void onChallengeClicked(Contest contest) {
        ContestActivity.navigateTo(getActivity(), contest, SCREEN_LABEL, mScreenProperties, 0, 0, AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL);
    }

    @Override
    public void onChallengePostShared(BaseResponse baseResponse) {
        String shareText = Config.COMMUNITY_POST_CHALLENGE_SHARE + System.getProperty("line.separator") + ((FeedDetail) baseResponse).getDeepLinkUrl();
        String sourceId = "";
        if (baseResponse instanceof UserPostSolrObj) {
            sourceId = Long.toString(((UserPostSolrObj) baseResponse).getUserPostSourceEntityId());
        } else if (baseResponse instanceof ChallengeSolrObj) {
            sourceId = Long.toString(((ChallengeSolrObj) baseResponse).getIdOfEntityOrParticipant());
        }
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(sourceId)
                        .build();
        AnalyticsManager.trackEvent(Event.CHALLENGE_SHARED,getScreenName(), properties);
        ShareBottomSheetFragment.showDialog((AppCompatActivity) getActivity(), shareText, ((FeedDetail) baseResponse).getThumbnailImageUrl(), ((FeedDetail) baseResponse).getDeepLinkUrl(), getScreenName(), true, ((FeedDetail) baseResponse).getDeepLinkUrl(), true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onEventPostClicked(UserPostSolrObj userPostSolrObj) {
        eventDetailDialogFragment = (EventDetailDialogFragment) getActivity().getFragmentManager().findFragmentByTag(EventDetailDialogFragment.class.getName());
        if (eventDetailDialogFragment == null) {
            eventDetailDialogFragment = new EventDetailDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putLong(AppConstants.EVENT_ID, 0);
            bundle.putBoolean(AppConstants.IS_FROM_COMMUNITY_SCREEN, true);
            Parcelable parcelable = Parcels.wrap(userPostSolrObj);
            bundle.putParcelable(AppConstants.EVENT_DETAIL, parcelable);
            eventDetailDialogFragment.setArguments(bundle);
        }
        if (!eventDetailDialogFragment.isVisible() && !eventDetailDialogFragment.isAdded() && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            eventDetailDialogFragment.show(getActivity().getFragmentManager(), EventDetailDialogFragment.class.getName());
        }
    }

    @Override
    public void onEventInterestedClicked(UserPostSolrObj userPostSolrObj) {
        mFeedPresenter.getEventInterestedFromPresenter(mAppUtils.likeRequestBuilder(userPostSolrObj.getEntityOrParticipantId(), AppConstants.EVENT_CONSTANT), userPostSolrObj);
    }

    @Override
    public void onEventNotInterestedClicked(UserPostSolrObj userPostSolrObj) {
        mFeedPresenter.getEventNotInteresetedFromPresenter(mAppUtils.unLikeRequestBuilder(userPostSolrObj.getEntityOrParticipantId()), userPostSolrObj);
    }

    @Override
    public void onEventGoingClicked(UserPostSolrObj userPostSolrObj) {
        mFeedPresenter.postBookmarked(mAppUtils.bookMarkRequestBuilder(userPostSolrObj.getEntityOrParticipantId()), userPostSolrObj.isBookmarked());
    }

    @Override
    public void onOrgTitleClicked(UserPostSolrObj userPostObj) {
        if (null != userPostObj) {
            if (StringUtil.isNotNullOrEmptyString(userPostObj.getDeepLinkUrl())) {
                Uri url = Uri.parse(userPostObj.getDeepLinkUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(url);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onMentorFollowClicked(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        if (userSolrObj.isSolrIgnoreIsMentorFollowed()) {
            mFeedPresenter.getUnFollowFromPresenter(publicProfileListRequest, userSolrObj);
        } else {
            mFeedPresenter.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
        }
    }

    @Override
    public void onMentorAskQuestionClicked(UserSolrObj userSolrObj) {
        CommunityPost communityPost = new CommunityPost();
        communityPost.community = new Community();
        communityPost.community.id = userSolrObj.getSolrIgnoreMentorCommunityId();
        communityPost.community.name = userSolrObj.getNameOrTitle();
        communityPost.createPostRequestFrom = AppConstants.MENTOR_CREATE_QUESTION;
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(getActivity(), communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, mPrimaryColor, mTitleTextColor, mScreenProperties);
    }

    @Override
    public void onMentorProfileClicked(UserSolrObj userSolrObj) {
        if (userSolrObj.getEntityOrParticipantTypeId() == 7) {
            ProfileActivity.navigateTo(getActivity(), userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), true, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }

        else if (userSolrObj.getEntityOrParticipantTypeId() == 1) {
            ProfileActivity.navigateTo(getActivity(), userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), false, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    @Override
    public void onChampionProfileClicked(UserPostSolrObj userPostObj, int requestCodeForMentorProfileDetail) {
        long userId = userPostObj.getCreatedBy();
        int position = userPostObj.getItemPosition();
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        ProfileActivity.navigateTo(getActivity(), communityFeedSolrObj, userId, userPostObj.isAuthorMentor(),position, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);

    }

    @Override
    public void onArticleCommentClicked(ArticleSolrObj articleObj) {
        ArticleActivity.navigateTo(getActivity(), articleObj, getScreenName(), mScreenProperties, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
    }

    @Override
    public void startProgressBar() {
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefresh != null) {
                    mSwipeRefresh.setRefreshing(true);
                    mSwipeRefresh.setColorSchemeResources(R.color.mentor_green, R.color.link_color, R.color.email);
                }
            }
        });
    }

    @Override
    public void stopProgressBar() {
        mEndlessRecyclerViewScrollListener.finishLoading();
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.setRefreshing(false);
        mAdapter.feedFinishedLoading();
    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
        onChampionProfileClicked((UserPostSolrObj)baseResponse, mValue);
    }

    private void onDeleteMenuClicked(UserPostSolrObj userPostSolrObj) {
        userPostSolrObj.setIsEditOrDelete(AppConstants.TWO_CONSTANT);
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, mScreenProperties, false, mPrimaryColor, mTitleTextColor);
    }

    private void onEditMenuClicked(UserPostSolrObj userPostSolrObj) {
        userPostSolrObj.setIsEditOrDelete(AppConstants.ONE_CONSTANT);
        PostDetailActivity.navigateTo(getActivity(), getScreenName(), userPostSolrObj, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, mScreenProperties, false, mPrimaryColor, mTitleTextColor);
    }

    public void updateItem(FeedDetail feedDetail) {
        int position = findPositionById(feedDetail.getIdOfEntityOrParticipant());
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        mAdapter.setData(position, feedDetail);
    }

    public void removeItem(FeedDetail feedDetail) {
        int position = findPositionById(feedDetail.getIdOfEntityOrParticipant());
        mAdapter.removeItem(position);
    }

    public int findPositionById(long id) {
        if (mAdapter == null) {
            return -1;
        }
        List<FeedDetail> feedDetails = mAdapter.getDataList();

        if (CommonUtil.isEmpty(feedDetails)) {
            return -1;
        }

        for (int i = 0; i < feedDetails.size(); ++i) {
            FeedDetail feedDetail = feedDetails.get(i);
            if (feedDetail != null && feedDetail.getIdOfEntityOrParticipant() == id) {
                return i;
            }
        }
        return -1;
    }

    public void refreshList() {
        mFeedPresenter.fetchFeed(FeedPresenter.NORMAL_REQUEST);
    }
}
