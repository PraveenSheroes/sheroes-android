package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LastComment;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Home fragment within Home activity perform all the UI operation .
 * Fragment will have all UI components and operate with activity .
 */
public class HomeFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(HomeFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout swipeView;
    @Bind(R.id.li_no_result)
    LinearLayout liNoResult;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentIntractionWithActivityListner mHomeActivityFragmentIntractionWithActivityListner;
    private SwipPullRefreshList mPullRefreshList;
    @Inject
    AppUtils mAppUtils;
    FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    int mPosition;
    int mPressedEmoji;
    boolean mListLoad = true;
    boolean mIsEdit = false;
    int mPageNo = AppConstants.ONE_CONSTANT;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof FragmentIntractionWithActivityListner) {
                mHomeActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //   forceCrash();
        // TODO: Move this to where you establish a user session
        // logUser();

        //  GoogleAnalyticsTracing.screenNameTracking(getActivity(),SCREEN_NAME);

        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.HOME_FRAGMENT, AppConstants.EMPTY_STRING);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);

        //  mHomePresenter.saveMasterDataTypes();

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                mListLoad = true;
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
                mListLoad = true;
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {
                if (null != ((HomeActivity) getActivity()).mArticlePopUp) {
                    ((HomeActivity) getActivity()).mArticlePopUp.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    ((HomeActivity) getActivity()).mArticlePopUp.clearAnimation();
                    animation.setFillAfter(false);
                }
                if (null != ((HomeActivity) getActivity()).mCommunityPopUp) {
                    ((HomeActivity) getActivity()).mCommunityPopUp.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    ((HomeActivity) getActivity()).mCommunityPopUp.clearAnimation();
                    animation.setFillAfter(false);
                }
                if (null != ((HomeActivity) getActivity()).mCommunityPostPopUp) {
                    ((HomeActivity) getActivity()).mCommunityPostPopUp.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    ((HomeActivity) getActivity()).mCommunityPostPopUp.clearAnimation();
                    animation.setFillAfter(false);
                }
            }
        });
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListLoad = true;
                mPullRefreshList.setPullToRefresh(true);
                mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
            }
        });
        return view;
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mHomePresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(feedDetail.getEntityOrParticipantId()), feedDetail.isBookmarked());
    }

    public void editDeleteRecentComment(FeedDetail feedDetail, boolean isEdit) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mIsEdit = isEdit;
        List<LastComment> lastCommentList = feedDetail.getLastComments();
        if (StringUtil.isNotEmptyCollection(lastCommentList) && null != lastCommentList.get(lastCommentList.size() - 1)) {
            LastComment lastComment = lastCommentList.get(lastCommentList.size() - 1);
            mHomePresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(lastComment.getEntityId(), lastComment.getComment(), lastComment.isAnonymous(), isEdit, lastComment.getId()));
        }
    }

    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        mListLoad = false;
        mFeedDetail = (FeedDetail) baseResponse;
        this.mPosition = position;
        this.mPressedEmoji = reactionValue;
        if (null != mFeedDetail && mFeedDetail.isLongPress()) {
            mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), reactionValue));
        } else {
            if (reactionValue == AppConstants.NO_REACTION_CONSTANT) {
                mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(mFeedDetail.getEntityOrParticipantId()));
            } else {
                mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), reactionValue));
            }
        }
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("ajit@sheroies.in");
        Crashlytics.setUserName("Test User");
    }

    public void forceCrash() {
        throw new RuntimeException(AppConstants.APP_CRASHED);
    }

    private void setCustomAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - feedDetailList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
            swipeView.setRefreshing(false);
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            liNoResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {
        switch (successFrom) {
            case AppConstants.ONE_CONSTANT:
                likeSuccess(success);
                break;
            case AppConstants.TWO_CONSTANT:
                recentCommentEditDelete(success);
                break;
            case AppConstants.THREE_CONSTANT:
                bookMarkSuccess(success);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + successFrom);
        }
    }

    private void recentCommentEditDelete(String success) {
        if (success.equalsIgnoreCase(AppConstants.SUCCESS)) {
            List<LastComment> lastCommentList = mFeedDetail.getLastComments();
            if (StringUtil.isNotEmptyCollection(lastCommentList) && null != lastCommentList.get(lastCommentList.size() - 1)) {
                LastComment lastComment = lastCommentList.get(lastCommentList.size() - 1);
                lastCommentList.remove(lastComment);
                mFeedDetail.setLastComments(lastCommentList);
            }
            mAdapter.setDataOnPosition(mFeedDetail, mFeedDetail.getItemPosition());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void commentListRefresh(FeedDetail feedDetail) {
       // mAdapter.setDataOnPosition(feedDetail, feedDetail.getItemPosition());
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
        }
    }

    private void bookMarkSuccess(String success) {
        if (success.equalsIgnoreCase(AppConstants.SUCCESS) && null != mFeedDetail) {
            if (!mFeedDetail.isBookmarked()) {
                mFeedDetail.setBookmarked(true);
            } else {
                mFeedDetail.setBookmarked(false);
            }
            mAdapter.notifyDataSetChanged();
            if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
            }
        }
    }

    private void likeSuccess(String success) {

        if (success.equalsIgnoreCase(AppConstants.SUCCESS) && null != mFeedDetail) {

            if (mFeedDetail.isLongPress()) {
                if (mFeedDetail.getReactionValue() == AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(mPressedEmoji);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(mPressedEmoji);
                }

            } else {

                if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                }
            }
            mAdapter.setDataOnPosition(mFeedDetail, mPosition);
            mAdapter.notifyDataSetChanged();
            if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
            }
        }
    }


    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {
    }


    @Override
    public void startProgressBar() {
        if (mListLoad) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg) {
        mHomeActivityFragmentIntractionWithActivityListner.onShowErrorDialog();
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
