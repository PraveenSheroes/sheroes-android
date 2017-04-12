package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.presenters.CommentReactionPresenter;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.RequestedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

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
    RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private int  previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 1;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private FragmentListRefreshData mFragmentListRefreshData;
    private CommentReactionPresenter mCommentReactionPresenter;
    RequestedPresenter requestedPresenter;
    public HidingScrollListener(HomePresenter homePresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mHomePresenter=homePresenter;
        mRecyclerView=recyclerView;
        mManager=manager;
        this.mFragmentListRefreshData=fragmentListRefreshData;
    }
    public HidingScrollListener(MembersPresenter membersPresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mMembersPresenter=membersPresenter;
        mRecyclerView=recyclerView;
        mManager=manager;
        this.mFragmentListRefreshData=fragmentListRefreshData;
    }
    public HidingScrollListener(RequestedPresenter requestedPresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        requestedPresenter=requestedPresenter;
        mRecyclerView=recyclerView;
        mManager=manager;
        this.mFragmentListRefreshData=fragmentListRefreshData;
    }
    public HidingScrollListener(CommentReactionPresenter commentReactionPresenter, RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        mCommentReactionPresenter=commentReactionPresenter;
        mRecyclerView=recyclerView;
        mManager=manager;
        this.mFragmentListRefreshData=fragmentListRefreshData;
    }
    public HidingScrollListener(RecyclerView recyclerView, LinearLayoutManager manager, FragmentListRefreshData fragmentListRefreshData) {
        this.mFragmentListRefreshData=fragmentListRefreshData;
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
        int lastVisibleItem = mManager.findLastVisibleItemPosition();
        if(mFragmentListRefreshData.getSwipeToRefresh()==AppConstants.ONE_CONSTANT)
        {
            previousTotal=totalItemCount;
            loading=false;
            mFragmentListRefreshData.setSwipeToRefresh(AppConstants.TWO_CONSTANT);
        }
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (totalItemCount>visibleThreshold&&!loading && totalItemCount <=(lastVisibleItem + visibleThreshold)) {
            if(null!=mFragmentListRefreshData&& StringUtil.isNotNullOrEmptyString(mFragmentListRefreshData.getCallFromFragment())) {
                int pageNo=mFragmentListRefreshData.getPageNo();
                switch (mFragmentListRefreshData.getCallFromFragment()) {
                    case AppConstants.ARTICLE_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.articleCategoryRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo(),mFragmentListRefreshData.getCategoryIdList()));
                        break;
                    case AppConstants.COMMUNITY_POST_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST,pageNo));
                        break;
                    case AppConstants.FEATURE_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEATURED_COMMUNITY,pageNo));
                        break;
                    case AppConstants.MY_COMMUNITIES_FRAGMENT:
                        mHomePresenter.getMyCommunityFromPresenter(mAppUtils.myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY,pageNo));
                        break;
                    case AppConstants.HOME_FRAGMENT:
                       mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE,pageNo));
                        break;
                    case AppConstants.JOB_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_JOB,pageNo));
                        break;
                    case AppConstants.COMMUNITY_DETAIL:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST,pageNo,mFragmentListRefreshData.getIdFeedDetail()));
                        break;
                    case AppConstants.ARTICLE_DETAIL:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_ARTICLE,pageNo,mFragmentListRefreshData.getIdFeedDetail()));
                        break;
                    case AppConstants.BOOKMARKS:
                        mHomePresenter.getBookMarkFromPresenter(mAppUtils.getBookMarks(pageNo));
                        break;
                    case AppConstants.COMMENT_REACTION_FRAGMENT:
                       mCommentReactionPresenter.getAllCommentListFromPresenter(mAppUtils.getCommentRequestBuilder(mFragmentListRefreshData.getEnitityOrParticpantid(),pageNo), mFragmentListRefreshData.isReactionList(),AppConstants.NO_REACTION_CONSTANT);
                        break;
                    case AppConstants.MEMBER_FRAGMENT:
                        mMembersPresenter.getAllMembers(mAppUtils.getPandingMemberRequestBuilder(mFragmentListRefreshData.getEnitityOrParticpantid(),pageNo));
                        break;
                    case AppConstants.PANDING_MEMBER_FRAGMENT:
                        requestedPresenter.getAllMembers(mAppUtils.getPandingMemberRequestBuilder(mFragmentListRefreshData.getEnitityOrParticpantid(),pageNo));

                        break;
                    case AppConstants.USER_COMMUNITY_POST_FRAGMENT:
                        mHomePresenter.getFeedFromPresenter(mAppUtils.userCommunityPostRequestBuilder(AppConstants.FEED_COMMUNITY_POST,pageNo,mFragmentListRefreshData.getCommunityId()));
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
