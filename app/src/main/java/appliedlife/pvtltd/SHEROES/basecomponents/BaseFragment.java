package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LastComment;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.CommentReactionPresenter;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base fragment for all child fragment.
 * all the common behaviour.
 */
public class BaseFragment extends Fragment implements View.OnClickListener, HomeView {
    private final String TAG = LogUtils.makeLogTag(BaseFragment.class);
    public FragmentActivity mActivity;
    private FragmentListRefreshData mFragmentListRefreshData;
    private CommentReactionPresenter mCommentReactionPresenter;
    private SwipPullRefreshList mPullRefreshList;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mPageNo;
    private SwipeRefreshLayout mSwipeView;
    private LinearLayout mLiNoResult;
    private FeedDetail mFeedDetail;
    private RecyclerView mRecyclerView;
    private int mPosition;
    private int mPressedEmoji;
    private boolean mListLoad = true;
    private boolean mIsEdit;
    private HomePresenter mHomePresenter;
    private AppUtils mAppUtils;
    private ProgressBar mProgressBar;
    private FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;
    private FragmentOpen mFragmentOpen = new FragmentOpen();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void setFragmentData(FragmentOpen fragmentOpen) {
        this.mFragmentOpen = fragmentOpen;
    }

    public void setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    public void setAllInitializationForFeeds(FragmentListRefreshData mFragmentListRefreshData, SwipPullRefreshList mPullRefreshList, GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, int mPageNo, SwipeRefreshLayout swipeView, LinearLayout liNoResult, FeedDetail mFeedDetail, RecyclerView mRecyclerView, int mPosition, int mPressedEmoji, boolean mListLoad, boolean mIsEdit, HomePresenter mHomePresenter, AppUtils mAppUtils, ProgressBar mProgressBar) {
        this.mFragmentListRefreshData = mFragmentListRefreshData;
        this.mPullRefreshList = mPullRefreshList;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        this.mPageNo = mPageNo;
        this.mSwipeView = swipeView;
        this.mLiNoResult = liNoResult;
        this.mFeedDetail = mFeedDetail;
        this.mRecyclerView = mRecyclerView;
        this.mPosition = mPosition;
        this.mPressedEmoji = mPressedEmoji;
        this.mListLoad = mListLoad;
        this.mIsEdit = mIsEdit;
        this.mHomePresenter = mHomePresenter;
        this.mAppUtils = mAppUtils;
        this.mProgressBar = mProgressBar;
    }

    public void setAllInitializationForFeeds(FragmentListRefreshData mFragmentListRefreshData, GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, FeedDetail mFeedDetail, RecyclerView mRecyclerView, int mPosition, int mPressedEmoji, boolean mListLoad, HomePresenter mHomePresenter, AppUtils mAppUtils, ProgressBar mProgressBar) {
        this.mFragmentListRefreshData = mFragmentListRefreshData;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        this.mFeedDetail = mFeedDetail;
        this.mRecyclerView = mRecyclerView;
        this.mPosition = mPosition;
        this.mPressedEmoji = mPressedEmoji;
        this.mListLoad = mListLoad;
        this.mHomePresenter = mHomePresenter;
        this.mAppUtils = mAppUtils;
        this.mProgressBar = mProgressBar;
    }

    public void setAllInitializationForFeeds(FragmentListRefreshData mFragmentListRefreshData, GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, RecyclerView mRecyclerView, HomePresenter mHomePresenter, AppUtils mAppUtils, ProgressBar mProgressBar) {
        this.mFragmentListRefreshData = mFragmentListRefreshData;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        this.mRecyclerView = mRecyclerView;
        this.mHomePresenter = mHomePresenter;
        this.mAppUtils = mAppUtils;
        this.mProgressBar = mProgressBar;
    }

    public void setCommentReaction(FragmentListRefreshData mFragmentListRefreshData, GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, FeedDetail mFeedDetail, RecyclerView mRecyclerView, CommentReactionPresenter commentReactionPresenter, AppUtils mAppUtils, ProgressBar mProgressBar) {
        this.mFragmentListRefreshData = mFragmentListRefreshData;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        this.mFeedDetail = mFeedDetail;
        this.mRecyclerView = mRecyclerView;
        this.mCommentReactionPresenter = commentReactionPresenter;
        this.mAppUtils = mAppUtils;
        this.mProgressBar = mProgressBar;
    }

    public void setListLoadFlag(boolean mListLoad) {
        this.mListLoad = mListLoad;
    }

    public void setRefreshList(SwipPullRefreshList mPullRefreshList) {
        this.mPullRefreshList = mPullRefreshList;
    }

    public void callFragment(int layout, Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            mActivity = getActivity();
        }
        try {
            if (mActivity instanceof FragmentIntractionWithActivityListner) {
                mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }


    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mLiNoResult.setVisibility(View.GONE);
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.notifyDataSetChanged();
            if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
            }
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPosition(mPullRefreshList.getFeedResponses().size() - feedDetailList.size()-1);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
            mSwipeView.setRefreshing(false);
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            mLiNoResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getTagListSuccess(List<Doc> feedDetailList) {

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
        if (null != mFeedDetail) {
            switch (success) {
                case AppConstants.SUCCESS:
                    List<LastComment> lastCommentList = mFeedDetail.getLastComments();
                    if (StringUtil.isNotEmptyCollection(lastCommentList) && null != lastCommentList.get(lastCommentList.size() - 1)) {
                        LastComment lastComment = lastCommentList.get(lastCommentList.size() - 1);
                        lastCommentList.remove(lastComment);
                        mFeedDetail.setLastComments(lastCommentList);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case AppConstants.FAILED:
                    showError(getString(R.string.ID_ALREADY_BOOKMARK), AppConstants.TWO_CONSTANT);
                    break;
                default:
                    showError(AppConstants.HTTP_401_UNAUTHORIZED, AppConstants.TWO_CONSTANT);
            }
        }
    }

    public void commentListRefresh(FeedDetail feedDetail, int callFrom) {
        if (callFrom == AppConstants.TWO_CONSTANT) {
            mAdapter.setDataOnPosition(feedDetail, feedDetail.getItemPosition());
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void bookMarkSuccess(String success) {
        if (null != mFeedDetail) {
            switch (success) {
                case AppConstants.SUCCESS:
                    if (!mFeedDetail.isBookmarked()) {
                        mFeedDetail.setBookmarked(true);
                    } else {
                        mFeedDetail.setBookmarked(false);
                    }
                    if (mFragmentOpen.isBookmarkFragment()) {
                        mAdapter.removeDataOnPosition(mFeedDetail, mFeedDetail.getItemPosition());
                        mAdapter.notifyDataSetChanged();
                    }else {
                        mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    }
                    if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
                        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
                    }

                    break;
                case AppConstants.FAILED:
                    showError(getString(R.string.ID_ALREADY_BOOKMARK), AppConstants.TWO_CONSTANT);
                    break;
                default:
                    showError(AppConstants.HTTP_401_UNAUTHORIZED, AppConstants.TWO_CONSTANT);
            }
        }
    }

    protected void likeSuccess(String success) {
        if (null != mFeedDetail) {
            switch (success) {
                case AppConstants.SUCCESS:

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
                    mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
                        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
                    }
                    break;
                case AppConstants.FAILED:
                    showError(getString(R.string.ID_ALREADY_BOOKMARK), AppConstants.TWO_CONSTANT);
                    break;
                default:
                    showError(AppConstants.HTTP_401_UNAUTHORIZED, AppConstants.TWO_CONSTANT);

            }
        }

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

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {
    }


    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String errorMsg, int errorFor) {
        if (errorFor == AppConstants.ONE_CONSTANT) {
            if (null != mLiNoResult) {
                mLiNoResult.setVisibility(View.VISIBLE);
            }
        } else {
            mAdapter.notifyDataSetChanged();

        }
        stopProgressBar();
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(errorMsg, errorFor);
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
    }

    /**
     * this method will be use for fragment pop
     */
    protected void onBackPress() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
