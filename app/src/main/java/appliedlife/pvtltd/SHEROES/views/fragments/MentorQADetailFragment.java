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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 06/12/17.
 */

public class MentorQADetailFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Mentor QA Detail Screen";
    private final String TAG = LogUtils.makeLogTag(MentorQADetailFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_mentor_qa_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_mentor_qa_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_mentor_qa)
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
    private UserSolrObj mUserSolrObj;
    private boolean mListLoad = true;
    private boolean mIsEdit = false;

    @Inject
    Preference<LoginResponse> mUserPreference;

    private Comment mComment;


    public static MentorQADetailFragment createInstance(FeedDetail feedDetail, CommunityEnum communityEnum, long communityPostId) {
        MentorQADetailFragment mentorQADetailFragment = new MentorQADetailFragment();
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(feedDetail);
        if (feedDetail instanceof UserSolrObj) {
            bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelable);
        }
        mentorQADetailFragment.setArguments(bundle);
        return mentorQADetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.mentor_qa_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            Parcelable parcelable = getArguments().getParcelable(AppConstants.MENTOR_DETAIL);
            if (null != parcelable) {
                UserSolrObj mUserMentorObj = Parcels.unwrap(parcelable);
                UserSolrObj userSolrObj = new UserSolrObj();
                userSolrObj.setIdOfEntityOrParticipant(mUserMentorObj.getSolrIgnoreMentorCommunityId());
                userSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
                this.mUserSolrObj = userSolrObj;
            }
            if (null == mUserSolrObj) {
                return view;
            }
        }
        initializeAndListner();


        return view;
    }

    private void initializeAndListner() {
        if (null != mUserSolrObj) {
            mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.QA_POST_FRAGMENT, mUserSolrObj.getIdOfEntityOrParticipant());
            mFragmentListRefreshData.setCommunityId(mUserSolrObj.getIdOfEntityOrParticipant());
            mPullRefreshList = new SwipPullRefreshList();
            mPullRefreshList.setPullToRefresh(false);
            mHomePresenter.attachView(this);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActivity) getActivity());
            mFragmentListRefreshData.setCallForNameUser(AppConstants.GROWTH_PUBLIC_PROFILE);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
                @Override
                public void onHide() {
                    ((ProfileActivity) getActivity()).clHomeFooterList.setVisibility(View.GONE);

                }

                @Override
                public void onShow() {
                    ((ProfileActivity) getActivity()).clHomeFooterList.setVisibility(View.VISIBLE);
                }

                @Override
                public void dismissReactions() {

                }
            });
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mUserSolrObj, mRecyclerView, 0, 0, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);

            mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
            mFragmentListRefreshData.setSearchStringName(AppConstants.COMMUNITY_POST_FRAGMENT);
            FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), 0);
            feedRequestPojo.setIdForFeedDetail(null);
            feedRequestPojo.setCommunityId(mUserSolrObj.getIdOfEntityOrParticipant());
            feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
            mHomePresenter.getFeedFromPresenter(feedRequestPojo);

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
        FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, mFragmentListRefreshData.getPageNo(), 0);
        feedRequestPojo.setIdForFeedDetail(null);
        feedRequestPojo.setCommunityId(mUserSolrObj.getIdOfEntityOrParticipant());
        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
        mHomePresenter.getFeedFromPresenter(feedRequestPojo);

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && null != mFragmentListRefreshData) {
            mLiNoResult.setVisibility(View.GONE);
            mPageNo = mFragmentListRefreshData.getPageNo();
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
            mProgressBar.getLayoutParams().width = 0;
            mProgressBar.getLayoutParams().height = 0;

        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            List<FeedDetail> noDataList = new ArrayList<>();
            try {

                UserSolrObj userSolrObj = (UserSolrObj) this.mUserSolrObj.clone();
                userSolrObj.setSubType(AppConstants.NO_COMMUNITIES);
                userSolrObj.setSuggested(true);
                noDataList.add(userSolrObj);
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
        if(baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS) && getActivity() instanceof ProfileActivity&& feedParticipationEnum == FeedParticipationEnum.DELETE_COMMUNITY_POST) {
            ((ProfileActivity)getActivity()).refreshPostCount(true);
        }
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
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        if (null != mUserSolrObj) {
            HashMap<String, Object> properties = new
                    EventProperty.Builder()
                    .id(Long.toString(mUserSolrObj.getIdOfEntityOrParticipant()))
                    .build();
            return properties;
        }
        return null;
    }
}


