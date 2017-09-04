package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.presenters.CommentReactionPresenter;
import appliedlife.pvtltd.SHEROES.presenters.HelplinePresenter;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.RequestedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.articleCategoryRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.feedRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getBookMarks;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getPandingMemberRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.helplineGetChatThreadRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.userCommunityDetailRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.userCommunityPostRequestBuilder;

/*
* This class is a ScrollListener for RecyclerView that allows to show/hide
* views when list is scrolled. It assumes that you have added a header
* to your list. @see pl.michalz.hideonscrollexample.adapter.partone.RecyclerAdapter
* */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private final String TAG = LogUtils.makeLogTag(HidingScrollListener.class);
    private static final int HIDE_THRESHOLD = 20;
    private int mScrolledDistance = 0;
    private boolean mControlsVisible = true;
    @Inject
    AppUtils mAppUtils;
    HomePresenter mHomePresenter;
    MembersPresenter mMembersPresenter;
    HelplinePresenter mHelplinePresenter;
    RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 1;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private FragmentListRefreshData mFragmentListRefreshData;
    private CommentReactionPresenter mCommentReactionPresenter;
    RequestedPresenter requestedPresenter;

    public HidingScrollListener(HomePresenter homePresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mHomePresenter = homePresenter;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mFragmentListRefreshData = fragmentListRefreshData;
    }

    public HidingScrollListener(MembersPresenter membersPresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mMembersPresenter = membersPresenter;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mFragmentListRefreshData = fragmentListRefreshData;
    }

    public HidingScrollListener(RequestedPresenter requestedPresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        this.requestedPresenter = requestedPresenter;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mFragmentListRefreshData = fragmentListRefreshData;
    }

    public HidingScrollListener(CommentReactionPresenter commentReactionPresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mCommentReactionPresenter = commentReactionPresenter;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mFragmentListRefreshData = fragmentListRefreshData;
    }

    public HidingScrollListener(RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        this.mFragmentListRefreshData = fragmentListRefreshData;
        mRecyclerView = recyclerView;
        mManager = manager;
    }

    public HidingScrollListener(RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData, HomePresenter homePresenter) {
        this.mFragmentListRefreshData = fragmentListRefreshData;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mHomePresenter = homePresenter;
    }

    public HidingScrollListener(HelplinePresenter helplinePresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mHelplinePresenter = helplinePresenter;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mFragmentListRefreshData = fragmentListRefreshData;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        dismissReactions();
        if (firstVisibleItem == 0) {
            if (!mControlsVisible) {
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
        if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
            mScrolledDistance += dy;
        }
        visibleItemCount = mRecyclerView.getChildCount();
        totalItemCount = mManager.getItemCount();
        int lastVisibleItem = mManager.findLastVisibleItemPosition();
        if (mFragmentListRefreshData.getSwipeToRefresh() == AppConstants.ONE_CONSTANT) {
            previousTotal = totalItemCount;
            loading = false;
            mFragmentListRefreshData.setSwipeToRefresh(AppConstants.TWO_CONSTANT);
        }
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (totalItemCount > visibleThreshold && !loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            if (null != mFragmentListRefreshData && StringUtil.isNotNullOrEmptyString(mFragmentListRefreshData.getCallFromFragment())) {
                int pageNo = mFragmentListRefreshData.getPageNo();
                switch (mFragmentListRefreshData.getCallFromFragment()) {
                    case AppConstants.ARTICLE_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(articleCategoryRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCategoryIdList()));
                        break;
                    case AppConstants.COMMUNITY_POST_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST, pageNo));
                        break;
                    case AppConstants.FEATURE_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEATURED_COMMUNITY, pageNo));
                        break;
                    case AppConstants.MY_COMMUNITIES_FRAGMENT:
                        mHomePresenter.getMyCommunityFromPresenter(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, pageNo));
                        break;
                    case AppConstants.HOME_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEED_SUB_TYPE, pageNo));
                        break;
                    case AppConstants.JOB_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEED_JOB, pageNo));
                        break;
                    case AppConstants.COMMUNITY_DETAIL:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, pageNo, mFragmentListRefreshData.getIdFeedDetail()));
                        break;
                    case AppConstants.ARTICLE_DETAIL:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_ARTICLE, pageNo, mFragmentListRefreshData.getIdFeedDetail()));
                        break;
                    case AppConstants.BOOKMARKS:
                        mHomePresenter.getBookMarkFromPresenter(getBookMarks(pageNo));
                        break;
                    case AppConstants.COMMENT_REACTION_FRAGMENT:
                        mCommentReactionPresenter.getAllCommentListFromPresenter(getCommentRequestBuilder(mFragmentListRefreshData.getEnitityOrParticpantid(), pageNo), mFragmentListRefreshData.isReactionList(), AppConstants.NO_REACTION_CONSTANT);
                        break;
                    case AppConstants.MEMBER_FRAGMENT:
                        mMembersPresenter.getAllMembers(getPandingMemberRequestBuilder(mFragmentListRefreshData.getEnitityOrParticpantid(), pageNo));
                        break;
                    case AppConstants.PANDING_MEMBER_FRAGMENT:
                        requestedPresenter.getAllPendingRequest(getPandingMemberRequestBuilder(mFragmentListRefreshData.getEnitityOrParticpantid(), pageNo));
                        break;
                    case AppConstants.USER_COMMUNITY_POST_FRAGMENT:
                        if (StringUtil.isNotNullOrEmptyString(mFragmentListRefreshData.getCallForNameUser()) && mFragmentListRefreshData.getCallForNameUser().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) {
                            FeedRequestPojo feedRequestPojo = userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId());
                            feedRequestPojo.setIdForFeedDetail(null);
                            Integer autherId = (int) mFragmentListRefreshData.getCommunityId();
                            feedRequestPojo.setAutherId(autherId);
                            mHomePresenter.getFeedFromPresenter(feedRequestPojo);
                        } else {
                            mHomePresenter.getFeedFromPresenter(userCommunityPostRequestBuilder(AppConstants.FEED_COMMUNITY_POST, pageNo, mFragmentListRefreshData.getCommunityId()));
                        }

                        break;
                    case AppConstants.INVITE_MEMBER:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.USER_SUB_TYPE, mFragmentListRefreshData.getSearchStringName(), mFragmentListRefreshData.getPageNo(), AppConstants.INVITE_MEMBER, mFragmentListRefreshData.getEnitityOrParticpantid(), AppConstants.INVITE_PAGE_SIZE));
                        break;
                    case AppConstants.HELPLINE_FRAGMENT:
                        mHelplinePresenter.getHelplineChatDetails(helplineGetChatThreadRequestBuilder(pageNo));
                        break;
                    case AppConstants.ALL_SEARCH:
                        if (mAppUtils == null) {
                            mAppUtils = AppUtils.getInstance();
                        }
                        mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.FEED_JOB, mFragmentListRefreshData.getSearchStringName(), mFragmentListRefreshData.getPageNo(), AppConstants.ALL_SEARCH, null, AppConstants.PAGE_SIZE));
                    case AppConstants.GROWTH_PUBLIC_PROFILE:
                        if (mAppUtils == null) {
                            mAppUtils = AppUtils.getInstance();
                        }
                        mHomePresenter.getPublicProfileMentorListFromPresenter(mAppUtils.pubicProfileRequestBuilder(mFragmentListRefreshData.getPageNo()));
                        break;
                    case AppConstants.SPAM_LIST_FRAGMENT:
                        if (mAppUtils == null) {
                            mAppUtils = AppUtils.getInstance();
                        }
                        FeedRequestPojo feedRequestPojo=feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo());
                        feedRequestPojo.setSpamPost(true);
                        mHomePresenter.getFeedFromPresenter(feedRequestPojo);
                        break;

                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + mFragmentListRefreshData.getCallFromFragment());
                }
            }

            loading = true;
        }

    }

    public abstract void onHide();

    public abstract void onShow();

    public abstract void dismissReactions();
}
