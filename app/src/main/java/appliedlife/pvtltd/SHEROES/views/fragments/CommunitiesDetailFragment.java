package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.MentorUserProfileActvity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.SPAM_POST_APPROVE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.userCommunityPostRequestBuilder;

/**
 * Created by Praveen_Singh on 01-02-2017.
 */


public class CommunitiesDetailFragment extends BaseFragment {
    private static String SCREEN_LABEL = "Community Screen";
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
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private FragmentListRefreshData mFragmentListRefreshData;
    private CommunityFeedSolrObj mCommunityFeedObj;
    private FeedDetail mCommunityPostDetail;
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    private CommunityEnum communityEnum = null;
    private String mScreenName;
    private int positionOfFeedDetail;
    private String mPressedButtonName;
    private long mCommunityPostId;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    @Inject
    Preference<LoginResponse> mUserPreference;
    private FeedDetail mApprovePostFeedDetail;
    private boolean mIsSpam;
    private long mUserId;
    private Comment mComment;
    private boolean hideAnonymousPost = true;

    public static CommunitiesDetailFragment createInstance(FeedDetail feedDetail, CommunityEnum communityEnum, long communityPostId, String sourceName) {
        CommunitiesDetailFragment communitiesDetailFragment = new CommunitiesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.COMMUNITY_POST_ID, communityPostId);
        Parcelable parcelable = Parcels.wrap(feedDetail);
        if (feedDetail instanceof UserSolrObj) {
            bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelable);
        } else if (feedDetail instanceof CommunityFeedSolrObj) {
            bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
        }
        bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, communityEnum);
        bundle.putString(BaseActivity.SOURCE_SCREEN, sourceName);
        communitiesDetailFragment.setArguments(bundle);
        return communitiesDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communities_detail, container, false);
        ButterKnife.bind(this, view);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        if (null != getArguments()) {
            mCommunityPostId = getArguments().getLong(AppConstants.COMMUNITY_POST_ID);
            if (getArguments().getString(BaseActivity.SOURCE_SCREEN) != null) {
                SCREEN_LABEL = getArguments().getString(BaseActivity.SOURCE_SCREEN);
            }
            Parcelable parcelable = getArguments().getParcelable(AppConstants.MENTOR_DETAIL);
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                mUserId = mUserPreference.get().getUserSummary().getUserId();
            }

            if (null != parcelable) {
                UserSolrObj mUserMentorObj = Parcels.unwrap(parcelable);
                CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
                communityFeedSolrObj.setIdOfEntityOrParticipant(mUserMentorObj.getIdOfEntityOrParticipant());

                if (mUserMentorObj.getIdOfEntityOrParticipant() == mUserId) {
                    hideAnonymousPost = false;
                }
                // communityFeedSolrObj.setIdOfEntityOrParticipant(mUserMentorObj.getSolrIgnoreMentorCommunityId());
                communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
                mCommunityFeedObj = communityFeedSolrObj;
            } else {
                mCommunityFeedObj = Parcels.unwrap(getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL));
            }
            if (null == mCommunityFeedObj) {
                return view;
            }
            communityEnum = (CommunityEnum) getArguments().getSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT);
        }

        networkAndListenerData();

        return view;
    }

    private void networkAndListenerData() {
        if (null != mCommunityFeedObj) {
            mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.USER_COMMUNITY_POST_FRAGMENT, mCommunityFeedObj.getIdOfEntityOrParticipant());
            mFragmentListRefreshData.setCommunityId(mCommunityFeedObj.getIdOfEntityOrParticipant());
            positionOfFeedDetail = mCommunityFeedObj.getItemPosition();
            mPullRefreshList = new SwipPullRefreshList();
            mPullRefreshList.setPullToRefresh(false);
            mHomePresenter.attachView(this);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            if (StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getCallFromName()) && mCommunityFeedObj.getCallFromName().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) {
                mAdapter = new GenericRecyclerViewAdapter(getContext(), (MentorUserProfileActvity) getActivity());
                mFragmentListRefreshData.setCallForNameUser(AppConstants.GROWTH_PUBLIC_PROFILE);
            }
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
                @Override
                public void onHide() {
                    try {

                        if (getActivity() instanceof MentorUserProfileActvity) {
                            ((MentorUserProfileActvity) getActivity()).clHomeFooterList.setVisibility(View.GONE);
                        }

                    } catch (ClassCastException ex) {
                        LogUtils.error(TAG, ex.getMessage());
                    }

                }

                @Override
                public void onShow() {

                }

                @Override
                public void dismissReactions() {

                }
            });
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mCommunityFeedObj, mRecyclerView, 0, 0, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);

            if (mCommunityPostId > 0) {
                if (StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getCallFromName()) && mCommunityFeedObj.getCallFromName().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) { //todo - profile - goes here
                    mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
                    mFragmentListRefreshData.setSearchStringName(AppConstants.COMMUNITY_POST_FRAGMENT);
                    FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mCommunityPostId);
                    feedRequestPojo.setIdForFeedDetail(null);
                    Integer autherId = (int) mCommunityFeedObj.getIdOfEntityOrParticipant();
                    feedRequestPojo.setAutherId(autherId);
                    feedRequestPojo.setAnonymousPostHide(hideAnonymousPost);

                    feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
                    mHomePresenter.getFeedForProfileFromPresenter(feedRequestPojo);
                } else {
                    mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
                    mFragmentListRefreshData.setSearchStringName(AppConstants.COMMUNITY_POST_FRAGMENT);
                    FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mCommunityPostId);
                    feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
                    mHomePresenter.getFeedFromPresenter(feedRequestPojo);
                }
            } else {
                mFragmentListRefreshData.setSearchStringName(AppConstants.COMMUNITIES_DETAIL);
                FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId());
                mHomePresenter.getFeedFromPresenter(feedRequestPojo);
            }

            mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeToRefreshList();
                }
            });
        }
    }

    public void swipeToRefreshList() {
        setProgressBar(mProgressBar);
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        if (null != mCommunityFeedObj && StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getCallFromName()) && mCommunityFeedObj.getCallFromName().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) {
            FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mCommunityPostId);
            feedRequestPojo.setIdForFeedDetail(null);
            Integer autherId = (int) mCommunityFeedObj.getIdOfEntityOrParticipant();
            feedRequestPojo.setAutherId(autherId);
            feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
            mHomePresenter.getFeedFromPresenter(feedRequestPojo);
        } else {
            mHomePresenter.getFeedFromPresenter(userCommunityPostRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId()));
        }
    }


    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        int totalPostCount = feedResponsePojo.getNumFound();

        if (StringUtil.isNotEmptyCollection(feedDetailList) && null != mFragmentListRefreshData) {
            mLiNoResult.setVisibility(View.GONE);
            mPageNo = mFragmentListRefreshData.getPageNo();
            if (StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getCallFromName()) && mCommunityFeedObj.getCallFromName().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) {
                //((MentorUserProfileActvity) getActivity()).setUsersPostCount(totalPostCount); //set post count
                mFragmentListRefreshData.setPageNo(++mPageNo);
                mProgressBar.setVisibility(View.GONE);
                mPullRefreshList.allListData(feedDetailList);
                List<FeedDetail> data = null;
                FeedDetail feedProgressBar = new FeedDetail();
                feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
                data = mPullRefreshList.getFeedResponses();
                int position = data.size() - feedDetailList.size();
                if (position > 0) {
                    data.remove(position - 1);
                }
                data.add(feedProgressBar);
                mAdapter.setSheroesGenericListData(data);
                mAdapter.notifyDataSetChanged();
                if (StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getCallFromName()) && mCommunityFeedObj.getCallFromName().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) {
                    mProgressBar.getLayoutParams().width = 0;
                    mProgressBar.getLayoutParams().height = 0;

                }

            } else {
                if (StringUtil.isNotNullOrEmptyString(mFragmentListRefreshData.getSearchStringName()) && mFragmentListRefreshData.getSearchStringName().equalsIgnoreCase(AppConstants.COMMUNITY_POST_FRAGMENT) && mCommunityPostId > 0) {
                    mCommunityPostDetail = feedDetailList.get(0);
                    mFragmentListRefreshData.setSearchStringName(AppConstants.COMMUNITIES_DETAIL);
                    FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getCommunityId());
                    mHomePresenter.getFeedFromPresenter(feedRequestPojo);
                } else if (StringUtil.isNotNullOrEmptyString(mFragmentListRefreshData.getSearchStringName()) && mFragmentListRefreshData.getSearchStringName().equalsIgnoreCase(AppConstants.COMMUNITIES_DETAIL)) {
                    mCommunityFeedObj = (CommunityFeedSolrObj) feedDetailList.get(0);
                    mCommunityFeedObj.setItemPosition(positionOfFeedDetail);
                    mProgressBar.setVisibility(View.VISIBLE);
                    swipeToRefreshList();
                    mFragmentListRefreshData.setSearchStringName(AppConstants.EMPTY_STRING);
                } else {
                    if (mPageNo == AppConstants.ONE_CONSTANT) {
                        mProgressBar.getLayoutParams().width = 0;
                        mProgressBar.getLayoutParams().height = 0;
                        try {
                            CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) mCommunityFeedObj.clone();
                            communityFeedSolrObj.setSubType(AppConstants.MY_COMMUNITIES_HEADER);
                            feedDetailList.add(0, communityFeedSolrObj);
                            if (mCommunityPostId > 0) {
                                int postPosition = 0;
                                for (FeedDetail feedDetail : feedDetailList) {
                                    if (mCommunityPostId == feedDetail.getIdOfEntityOrParticipant()) {
                                        break;
                                    }
                                    postPosition++;
                                }
                                if (feedDetailList.size() > postPosition) {
                                    feedDetailList.remove(postPosition);
                                }
                                if (feedDetailList.size() > 0) {
                                    feedDetailList.add(1, mCommunityPostDetail);
                                }
                            }
                        } catch (CloneNotSupportedException e) {
                            LogUtils.error(TAG, e.getMessage());
                        }
                    }
                    mFragmentListRefreshData.setPageNo(++mPageNo);
                    mProgressBar.setVisibility(View.GONE);
                    mPullRefreshList.allListData(feedDetailList);
                    List<FeedDetail> data = null;
                    FeedDetail feedProgressBar = new FeedDetail();
                    feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
                    data = mPullRefreshList.getFeedResponses();
                    int position = data.size() - feedDetailList.size();
                    if (position > 0) {
                        data.remove(position - 1);
                    }
                    data.add(feedProgressBar);
                    mAdapter.setSheroesGenericListData(data);
                    mAdapter.setUserId(mUserId);
                    if (mPageNo == AppConstants.TWO_CONSTANT) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter.notifyItemRangeChanged(position + 1, feedDetailList.size());
                    }
                }
            }
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            List<FeedDetail> noDataList = new ArrayList<>();
            try {
                if (StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getCallFromName()) && mCommunityFeedObj.getCallFromName().equalsIgnoreCase(AppConstants.GROWTH_PUBLIC_PROFILE)) {

                } else {
                    CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) mCommunityFeedObj.clone();
                    communityFeedSolrObj.setSubType(AppConstants.MY_COMMUNITIES_HEADER);
                    noDataList.add(communityFeedSolrObj);
                }
                CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) mCommunityFeedObj.clone();
                communityFeedSolrObj.setSubType(AppConstants.NO_COMMUNITIES);
                communityFeedSolrObj.setScreenName(mScreenName);
                noDataList.add(communityFeedSolrObj);
                mAdapter.setSheroesGenericListData(noDataList);
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
        mSwipeView.setRefreshing(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        super.getSuccessForAllResponse(baseResponse, feedParticipationEnum);

    }

    @Override
    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        if (baseResponse instanceof Comment) {
            mComment = (Comment) baseResponse;
            Comment comment = (Comment) baseResponse;
            if (reactionValue == AppConstants.NO_REACTION_CONSTANT) {
                if (mComment.getId() != -1) {
                    mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(mComment.getEntityId(), mComment.getCommentsId()), comment);
                }
            } else {
                if (mComment.getId() != -1) {
                    mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mComment.getEntityId(), reactionValue, mComment.getCommentsId()), comment);
                }
            }
        }
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            setFeedDetail(feedDetail);
            if (reactionValue == AppConstants.NO_REACTION_CONSTANT) {
                mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(feedDetail.getEntityOrParticipantId()));
            } else {
                mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(feedDetail.getEntityOrParticipantId(), reactionValue));
            }
        }
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
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

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        if (mFragmentListRefreshData.getPageNo() != AppConstants.ONE_CONSTANT) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case SPAM_POST_APPROVE:
                approveSpamPostResponse(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    private void approveSpamPostResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof ApproveSpamPostResponse) {
                    if (null != mApprovePostFeedDetail) {
                        if (mIsSpam) {
                            commentListRefresh(mApprovePostFeedDetail, DELETE_COMMUNITY_POST);
                        } else {
                            mApprovePostFeedDetail.setSpamPost(false);
                            commentListRefresh(mApprovePostFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                        }
                    }
                }
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), SPAM_POST_APPROVE);
                break;
            default:
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        if (null != mCommunityFeedObj) {
            HashMap<String, Object> properties = new
                    EventProperty.Builder()
                    .id(Long.toString(mCommunityFeedObj.getIdOfEntityOrParticipant()))
                    .build();
            return properties;
        }
        return null;
    }
}

