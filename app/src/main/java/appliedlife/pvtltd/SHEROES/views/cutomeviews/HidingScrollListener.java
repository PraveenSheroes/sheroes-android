package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.presenters.CommunitiesListPresenter;
import appliedlife.pvtltd.SHEROES.presenters.HelplinePresenter;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getBookMarks;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.helplineGetChatThreadRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.makeFeedRequest;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
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
    HelplinePresenter mHelplinePresenter;
    CommunitiesListPresenter mCommunitiesListPresenter;
    RecyclerView mRecyclerView;
    ProfilePresenterImpl profilePresenter;
    private LinearLayoutManager mManager;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 1;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private FragmentListRefreshData mFragmentListRefreshData;
    private OnBoardingPresenter mOnBoardingPresenter;

    public HidingScrollListener(OnBoardingPresenter onBoardingPresenter, RecyclerView recyclerView, GridLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mOnBoardingPresenter = onBoardingPresenter;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mFragmentListRefreshData = fragmentListRefreshData;
    }

    public HidingScrollListener(HomePresenter homePresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mHomePresenter = homePresenter;
        mRecyclerView = recyclerView;
        mManager = manager;
        this.mFragmentListRefreshData = fragmentListRefreshData;
    }

    public HidingScrollListener(ProfilePresenterImpl presenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        profilePresenter = presenter;
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

    public HidingScrollListener(CommunitiesListPresenter communitiesListPresenter, RecyclerView recyclerView, LinearLayoutManager mLayoutManager, FragmentListRefreshData mFragmentListRefreshData) {
        mCommunitiesListPresenter = communitiesListPresenter;
        mRecyclerView = recyclerView;
        mManager = mLayoutManager;
        this.mFragmentListRefreshData = mFragmentListRefreshData;
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
                if (mAppUtils == null) {
                    mAppUtils = AppUtils.getInstance();
                }
                switch (mFragmentListRefreshData.getCallFromFragment()) {
                    case AppConstants.ARTICLE_FRAGMENT:
                        FeedRequestPojo feedRequestArticlePojo = mAppUtils.articleCategoryRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCategoryIdList());
                        mHomePresenter.getFeedFromPresenter(feedRequestArticlePojo);
                        break;
                    case AppConstants.COMMUNITY_POST_FRAGMENT:
                        FeedRequestPojo feedRequestCommPostFragPojo = mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST, pageNo);
                        feedRequestCommPostFragPojo.setPostingDate(mFragmentListRefreshData.getPostedDate());
                        mHomePresenter.getFeedFromPresenter(feedRequestCommPostFragPojo);
                        break;
                    case AppConstants.FEATURE_FRAGMENT:
                        FeedRequestPojo feedRequestFeatureCommPojo = mAppUtils.feedRequestBuilder(AppConstants.FEATURED_COMMUNITY, pageNo);
                        mHomePresenter.getFeedFromPresenter(feedRequestFeatureCommPojo);
                        break;
                    case AppConstants.MY_COMMUNITIES_FRAGMENT:
                        if (mFragmentListRefreshData.getPageNo() != AppConstants.ONE_CONSTANT) {
                            mCommunitiesListPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, pageNo));
                        }
                        break;
                    case AppConstants.HOME_FRAGMENT:
                        if (mFragmentListRefreshData.isChallenge()) {
                            FeedRequestPojo feedRequestPojo = AppUtils.makeChallengeResponseRequest(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getSourceEntity(), pageNo);
                            feedRequestPojo.setPostingDate(mFragmentListRefreshData.getPostedDate());
                            mHomePresenter.getChallengeResponse(feedRequestPojo, mFragmentListRefreshData);
                        } else {
                            FeedRequestPojo feedRequestPojo = mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, pageNo);
                            feedRequestPojo.setPostingDate(mFragmentListRefreshData.getPostedDate());
                            mHomePresenter.getFeedFromPresenter(feedRequestPojo);
                        }
                        break;
                    case AppConstants.JOB_FRAGMENT:
                        if (null != mFragmentListRefreshData) {
                            if (null != mFragmentListRefreshData.getFeedRequestPojo()) {
                                FeedRequestPojo feedRequestJobPojo = mFragmentListRefreshData.getFeedRequestPojo();
                                feedRequestJobPojo.setPageNo(pageNo);
                                mHomePresenter.getFeedFromPresenter(feedRequestJobPojo);
                            } else {
                                FeedRequestPojo feedRequestJobPojo = mAppUtils.feedRequestBuilder(AppConstants.FEED_JOB, pageNo);
                                mHomePresenter.getFeedFromPresenter(feedRequestJobPojo);
                            }
                        }
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

                    case AppConstants.USER_COMMUNITY_POST_FRAGMENT:
                        if (StringUtil.isNotNullOrEmptyString(mFragmentListRefreshData.getCallForNameUser()) && mFragmentListRefreshData.getCallForNameUser().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) {
                            FeedRequestPojo feedRequestCommunnityDetailPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId());
                            feedRequestCommunnityDetailPojo.setIdForFeedDetail(null);
                            Integer autherId = (int) mFragmentListRefreshData.getCommunityId();
                            feedRequestCommunnityDetailPojo.setAutherId(autherId);
                            feedRequestCommunnityDetailPojo.setAnonymousPostHide(mFragmentListRefreshData.isAnonymous());
                            mHomePresenter.getFeedFromPresenter(feedRequestCommunnityDetailPojo);
                        } else {
                            mHomePresenter.getFeedFromPresenter(userCommunityPostRequestBuilder(AppConstants.FEED_COMMUNITY_POST, pageNo, mFragmentListRefreshData.getCommunityId()));
                        }
                        break;
                    case AppConstants.QA_POST_FRAGMENT:
                        FeedRequestPojo feedRequestCommunnityDetailPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId());
                        feedRequestCommunnityDetailPojo.setIdForFeedDetail(null);
                        feedRequestCommunnityDetailPojo.setCommunityId(mFragmentListRefreshData.getCommunityId());
                        mHomePresenter.getFeedFromPresenter(feedRequestCommunnityDetailPojo);
                        break;
                    case AppConstants.INVITE_MEMBER:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.USER_SUB_TYPE, mFragmentListRefreshData.getSearchStringName(), mFragmentListRefreshData.getPageNo(), AppConstants.INVITE_MEMBER, mFragmentListRefreshData.getEnitityOrParticpantid(), AppConstants.INVITE_PAGE_SIZE));
                        break;
                    case AppConstants.HELPLINE_FRAGMENT:
                        mHelplinePresenter.getHelplineChatDetails(helplineGetChatThreadRequestBuilder(pageNo));
                        break;
                    case AppConstants.ALL_SEARCH:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.FEED_JOB, mFragmentListRefreshData.getSearchStringName(), mFragmentListRefreshData.getPageNo(), AppConstants.ALL_SEARCH, null, AppConstants.PAGE_SIZE));
                        break;
                    case AppConstants.SPAM_LIST_FRAGMENT:
                        FeedRequestPojo feedRequestSpamListPojo = mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo());
                        feedRequestSpamListPojo.setSpamPost(true);
                        feedRequestSpamListPojo.setCommunityId(mFragmentListRefreshData.getCommunityId());
                        mHomePresenter.getFeedFromPresenter(feedRequestSpamListPojo);
                        break;
                    case AppConstants.MENTOR_LISTING:
                        if (mFragmentListRefreshData.getPageNo() != AppConstants.ONE_CONSTANT) {
                            FeedRequestPojo feedMentor = mAppUtils.feedRequestBuilder(AppConstants.CAROUSEL_SUB_TYPE, mFragmentListRefreshData.getPageNo());
                            mHomePresenter.getFeedFromPresenter(feedMentor);
                        }
                        break;

                    case AppConstants.PROFILE_COMMUNITY_LISTING:
                        if (mFragmentListRefreshData.getPageNo() != AppConstants.ONE_CONSTANT) {
                            if (mFragmentListRefreshData.isSelfProfile()) {
                                profilePresenter.getPublicProfileCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getMentorUserId()));
                            } else {
                                profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getMentorUserId()));
                            }
                        }
                        break;

                    case AppConstants.PROFILE_FOLLOWING:
                        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getMentorUserId()));
                        break;

                    case AppConstants.ON_BOARDING_COMMUNITIES:
                        FeedRequestPojo feedRequestPojo = makeFeedRequest(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo());
                        feedRequestPojo.setOnBoardingCommunities(true);
                        mOnBoardingPresenter.getFeedFromPresenter(feedRequestPojo);
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + mFragmentListRefreshData.getCallFromFragment());
                }
            }
            loading = true;
            previousTotal = totalItemCount;
        }

    }

    public abstract void onHide();

    public abstract void onShow();

    public abstract void dismissReactions();
}
