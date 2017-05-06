package appliedlife.pvtltd.SHEROES.views.fragments;

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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
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
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

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
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    private CommunityEnum communityEnum = null;
    private String mScreenName;
    private  int positionOfFeedDetail;
    public static CommunitiesDetailFragment createInstance(FeedDetail feedDetail, CommunityEnum communityEnum) {
        CommunitiesDetailFragment communitiesDetailFragment = new CommunitiesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, feedDetail);
        bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, communityEnum);
        communitiesDetailFragment.setArguments(bundle);
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
        if (null != mFeedDetail) {
            mTvJoinView.setEnabled(true);
            mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.USER_COMMUNITY_POST_FRAGMENT, mFeedDetail.getIdOfEntityOrParticipant());
            mFragmentListRefreshData.setCommunityId(mFeedDetail.getIdOfEntityOrParticipant());
            positionOfFeedDetail=mFeedDetail.getItemPosition();
            mPullRefreshList = new SwipPullRefreshList();
            mPullRefreshList.setPullToRefresh(false);
            mHomePresenter.attachView(this);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
                @Override
                public void onHide() {
                    if ((boolean) mTvJoinView.getTag()) {
                        if (mTvJoinView.getVisibility() == View.GONE) {
                            mTvJoinView.setVisibility(View.VISIBLE);
                            mTvJoinView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                        }
                    }
                }

                @Override
                public void onShow() {
                    if ((boolean) mTvJoinView.getTag()) {
                        if (mTvJoinView.getVisibility() == View.VISIBLE) {
                            mTvJoinView.setVisibility(View.GONE);
                            mTvJoinView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                        }
                    }
                }

                @Override
                public void dismissReactions() {

                }
            });
            mFragmentListRefreshData.setSearchStringName(AppConstants.COMMUNITIES_DETAIL);
            super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mFeedDetail, mRecyclerView, 0, 0, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
            //  mCreateCommunityPresenter.getFeedFromPresenter(mAppUtils.userCommunityPostRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId()));
            mHomePresenter.getFeedFromPresenter(mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId()));
            mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeToRefreshList();
                }
            });
        }
        return view;
    }

    public void communityPostClick() {
        FeedDetail feedDetail = mFeedDetail;
        feedDetail.setCallFromName(AppConstants.COMMUNITIES_DETAIL);
        ((CommunitiesDetailActivity) getActivity()).createCommunityPostClick(feedDetail);
    }

    private void swipeToRefreshList() {
        setListLoadFlag(false);
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        mHomePresenter.getFeedFromPresenter(mAppUtils.userCommunityPostRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId()));
    }

    @OnClick(R.id.tv_join_view)
    public void joinClick() {
        String joinTxt = mTvJoinView.getText().toString();
        ((CommunitiesDetailActivity) getActivity()).inviteJoinEventClick(joinTxt, mFeedDetail);
    }

    public void updateUiAccordingToFeedDetail(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
        if (null != communityEnum) {
            switch (communityEnum) {
                case SEARCH_COMMUNITY:
                    mScreenName = AppConstants.ALL_SEARCH;
                    if (!feedDetail.isMember() && !feedDetail.isOwner() && !feedDetail.isRequestPending()&&feedDetail.isFeatured()) {
                        mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                        mTvJoinView.setText(getString(R.string.ID_JOIN));
                        mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                        mTvJoinView.setTag(true);
                    } else {
                        mTvJoinView.setVisibility(View.GONE);
                        mTvJoinView.setTag(false);
                    }
                    break;
                case FEATURE_COMMUNITY:
                    ((CommunitiesDetailActivity) getActivity()).ivFabPostCommunity.setVisibility(View.INVISIBLE);
                    mScreenName = AppConstants.FEATURE_FRAGMENT;
                    if (!feedDetail.isMember() && !feedDetail.isOwner() && !feedDetail.isRequestPending()&&feedDetail.isFeatured()) {
                        mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                        mTvJoinView.setText(getString(R.string.ID_JOIN));
                        mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                        mTvJoinView.setTag(true);
                    } else {
                        mTvJoinView.setVisibility(View.GONE);
                        mTvJoinView.setTag(false);
                    }
                    break;
                case MY_COMMUNITY:
                    ((CommunitiesDetailActivity) getActivity()).ivFabPostCommunity.setVisibility(View.VISIBLE);
                    mTvJoinView.setVisibility(View.GONE);
                    mScreenName = AppConstants.MY_COMMUNITIES_FRAGMENT;
                    mTvJoinView.setTag(false);
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
            }
        }
        swipeToRefreshList();
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && null != mFragmentListRefreshData) {
            mLiNoResult.setVisibility(View.GONE);
            mPageNo = mFragmentListRefreshData.getPageNo();
            if (StringUtil.isNotNullOrEmptyString(mFragmentListRefreshData.getSearchStringName()) && mFragmentListRefreshData.getSearchStringName().equalsIgnoreCase(AppConstants.COMMUNITIES_DETAIL)) {
                mFeedDetail = feedDetailList.get(0);
                mFeedDetail.setItemPosition(positionOfFeedDetail);
                ((CommunitiesDetailActivity) getActivity()).initializeUiContent(mFeedDetail);
                 updateUiAccordingToFeedDetail(mFeedDetail);
                mFragmentListRefreshData.setSearchStringName(AppConstants.EMPTY_STRING);
            } else {
                if (mPageNo == AppConstants.ONE_CONSTANT) {
                    try {
                        FeedDetail mCommunityHeaderDetail = (FeedDetail) mFeedDetail.clone();
                        mCommunityHeaderDetail.setSubType(AppConstants.MY_COMMUNITIES_HEADER);
                        feedDetailList.add(0, mCommunityHeaderDetail);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                mFragmentListRefreshData.setPageNo(++mPageNo);
                mPullRefreshList.allListData(feedDetailList);
                mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
                mAdapter.notifyDataSetChanged();
                if (!mPullRefreshList.isPullToRefresh()) {
                    mLayoutManager.scrollToPosition(mPullRefreshList.getFeedResponses().size() - feedDetailList.size() - 1);
                } else {
                    mLayoutManager.scrollToPositionWithOffset(0, 0);
                }
            }

        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            List<FeedDetail> noDataList = new ArrayList<>();
            try {
                FeedDetail mCommunityHeaderDetail = (FeedDetail) mFeedDetail.clone();
                mCommunityHeaderDetail.setSubType(AppConstants.MY_COMMUNITIES_HEADER);
                noDataList.add(mCommunityHeaderDetail);
                FeedDetail noDetail = (FeedDetail) mFeedDetail.clone();
                noDetail.setSubType(AppConstants.NO_COMMUNITIES);
                noDetail.setScreenName(mScreenName);
                noDataList.add(noDetail);
                mAdapter.setSheroesGenericListData(noDataList);
                mAdapter.notifyDataSetChanged();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        mSwipeView.setRefreshing(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    public void joinRequestForOpenCommunity(FeedDetail feedDetail) {
        super.joinRequestForOpenCommunity(feedDetail);
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        super.getSuccessForAllResponse(baseResponse, feedParticipationEnum);
        switch (feedParticipationEnum) {
            case JOIN_INVITE:
                joinSuccessFailed(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    public void joinSuccessFailed(BaseResponse baseResponse) {
        if (baseResponse instanceof CommunityResponse) {
            switch (baseResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (mFeedDetail.isClosedCommunity()) {
                        mFeedDetail.setRequestPending(true);
                        mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinView.setText(getContext().getString(R.string.ID_REQUESTED));
                        mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                    } else {
                        swipeToRefreshList();
                        mFeedDetail.setMember(true);
                        mTvJoinView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinView.setText(getContext().getString(R.string.ID_JOINED));
                        mTvJoinView.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                    }
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                    break;
                default:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
            }
        }
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
    }


    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        super.likeAndUnlikeRequest(baseResponse, reactionValue, position);
    }

    public void commentListRefresh(FeedDetail feedDetail, FeedParticipationEnum feedParticipationEnum) {
        super.commentListRefresh(feedDetail, feedParticipationEnum);
    }

    public void markAsSpamCommunityPost(FeedDetail feedDetail) {
        super.markAsSpamCommunityPost(feedDetail);
    }

    public void deleteCommunityPost(FeedDetail feedDetail) {
        super.deleteCommunityPost(feedDetail);
    }

}

