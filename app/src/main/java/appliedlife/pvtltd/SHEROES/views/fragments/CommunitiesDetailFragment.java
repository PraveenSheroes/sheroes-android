package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
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
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 01-02-2017.
 */

public class CommunitiesDetailFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(CommunitiesDetailFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_communities_detail_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_communities_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_communities_detail)
    SwipeRefreshLayout mSwipeView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    @Bind(R.id.tv_join_view)
    TextView mTvJoinView;

    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private FragmentListRefreshData mFragmentListRefreshData;
    private FeedDetail mFeedDetail;
    private int mPosition;
    private int mPressedEmoji;
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    private CommunityEnum communityEnum = null;

    public static CommunitiesDetailFragment createInstance(Intent intent) {
        CommunitiesDetailFragment communitiesDetailFragment = new CommunitiesDetailFragment();
        communitiesDetailFragment.setArguments(intent.getExtras());
        return communitiesDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communities_detail, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
            communityEnum = (CommunityEnum) getArguments().getSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT);
        }
        mTvJoinView.setEnabled(true);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.COMMUNITY_POST_FRAGMENT, mFeedDetail.getId());
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if(null!=communityEnum)
        {
        switch (communityEnum) {

            case SEARCH_COMMUNITY:
                mTvJoinView.setVisibility(View.VISIBLE);
                if (!mFeedDetail.isMember() && !mFeedDetail.isOwner() && !mFeedDetail.isRequestPending()) {
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                    mTvJoinView.setText(getString(R.string.ID_JOIN));
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                } else if (mFeedDetail.isRequestPending()) {
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTvJoinView.setText(getString(R.string.ID_REQUESTED));
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                    mTvJoinView.setEnabled(false);
                } else if (mFeedDetail.isOwner() || mFeedDetail.isMember()) {
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTvJoinView.setText(getString(R.string.ID_JOINED));
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                    mTvJoinView.setEnabled(false);
                } else {
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_community_invite);
                    mTvJoinView.setText(getString(R.string.ID_INVITE));
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }
                break;
            case FEATURE_COMMUNITY:
                mTvJoinView.setVisibility(View.VISIBLE);
                if (!mFeedDetail.isMember() && !mFeedDetail.isOwner() && !mFeedDetail.isRequestPending()) {
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                    mTvJoinView.setText(getString(R.string.ID_JOIN));
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                } else if (mFeedDetail.isRequestPending()) {
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTvJoinView.setText(getString(R.string.ID_REQUESTED));
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                    mTvJoinView.setEnabled(false);
                } else if (mFeedDetail.isOwner() || mFeedDetail.isMember()) {
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTvJoinView.setText(getString(R.string.ID_JOINED));
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                    mTvJoinView.setVisibility(View.VISIBLE);
                    mTvJoinView.setEnabled(false);
                }
                break;
            case MY_COMMUNITY:
                mTvJoinView.setVisibility(View.VISIBLE);
                if (mFeedDetail.isMember() && !mFeedDetail.isOwner()) {
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTvJoinView.setText(getString(R.string.ID_VIEW));
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                }
                else {
                    mTvJoinView.setBackgroundResource(R.drawable.rectangle_community_invite);
                    mTvJoinView.setText(getString(R.string.ID_INVITE));
                    mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
        }
        }
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                if (mTvJoinView.getVisibility() == View.GONE) {
                    mTvJoinView.setVisibility(View.VISIBLE);
                    mTvJoinView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
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
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mFeedDetail, mRecyclerView, mPosition, mPressedEmoji, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getId())) {
            mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo()));
        }
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setListLoadFlag(false);
                mPullRefreshList.setPullToRefresh(true);
                mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo()));
            }
        });
        return view;
    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            if (mPageNo == AppConstants.ONE_CONSTANT) {
                FeedDetail feedDetail = new FeedDetail();
                //TODO:: Please remove this or correct
                feedDetail.setSubType(AppConstants.MY_COMMUNITIES_HEADER);
                feedDetail.setNameOrTitle(mFeedDetail.getNameOrTitle());
                feedDetail.setCommunityType(mFeedDetail.getCommunityType());
                feedDetail.setMember(mFeedDetail.isMember());
                feedDetail.setOwner(mFeedDetail.isOwner());
                feedDetail.setRequestPending(mFeedDetail.isRequestPending());
                feedDetail.setClosedCommunity(mFeedDetail.isClosedCommunity());
                feedDetailList.add(0, feedDetail);
            }
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - feedDetailList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
            mSwipeView.setRefreshing(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }


    @Override
    public void getSuccessForAllResponse(String success, FeedParticipationEnum feedParticipationEnum) {
        super.getSuccessForAllResponse(success, feedParticipationEnum);
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
    }

    public void editDeleteRecentComment(FeedDetail feedDetail, boolean isEdit) {
        super.editDeleteRecentComment(feedDetail, isEdit);
    }

    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        super.likeAndUnlikeRequest(baseResponse, reactionValue, position);
    }

    public void commentListRefresh(FeedDetail feedDetail, FeedParticipationEnum feedParticipationEnum) {
        super.commentListRefresh(feedDetail, feedParticipationEnum);
    }

}

