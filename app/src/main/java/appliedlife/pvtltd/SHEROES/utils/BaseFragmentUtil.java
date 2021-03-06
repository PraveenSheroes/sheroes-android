package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.MARK_AS_SPAM;

public class BaseFragmentUtil {
    //region member variables
    private static BaseFragmentUtil mInstance;
    private FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;
    //endregion

    //region public methods
    public static synchronized BaseFragmentUtil getInstance() {
        if (mInstance == null) {
            mInstance = new BaseFragmentUtil();
        }
        return mInstance;
    }

    public BaseFragmentUtil() {
    }

    public void reportAsSpamPostRespose(Context context, FeedDetail mFeedDetail, String screenName, BaseResponse baseResponse, GenericRecyclerViewAdapter adapter, LinearLayoutManager layoutManager) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                Toast.makeText(context, AppConstants.MARK_AS_SPAM, Toast.LENGTH_SHORT).show();
                mFeedDetail.setFromHome(true);
                commentListRefresh(adapter, layoutManager, mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                AnalyticsManager.trackPostAction(Event.POST_REPORTED, mFeedDetail, screenName);
                break;
            case AppConstants.FAILED:
                if (context instanceof FragmentIntractionWithActivityListner) {
                    mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) context;
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), MARK_AS_SPAM);
                }
                break;
            default:
                if (context instanceof FragmentIntractionWithActivityListner) {
                    mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) context;
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(context.getString(R.string.ID_GENERIC_ERROR), MARK_AS_SPAM);
                }
        }
    }

    public void deleteCommunityPostRespose(GenericRecyclerViewAdapter adapter, LinearLayoutManager layoutManager, Context context, FeedDetail mFeedDetail, String screenName, BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                commentListRefresh(adapter, layoutManager, mFeedDetail, DELETE_COMMUNITY_POST);
                AnalyticsManager.trackPostAction(Event.POST_DELETED, mFeedDetail, screenName);
                break;
            case AppConstants.FAILED:
                if (context instanceof FragmentIntractionWithActivityListner) {
                    mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) context;
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                }
                break;
            default:
                if (context instanceof FragmentIntractionWithActivityListner) {
                    mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) context;
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(context.getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
                }
        }
    }

    public void joinInviteResponse(Context context, GenericRecyclerViewAdapter adapter, LinearLayoutManager layoutManager, FeedDetail mFeedDetail, String screenName, BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                ((CommunityFeedSolrObj) mFeedDetail).setMember(true);
                commentListRefresh(adapter, layoutManager, mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant())).name(mFeedDetail.getNameOrTitle()).build();
                AnalyticsManager.trackEvent(Event.COMMUNITY_JOINED, screenName, properties);
                break;
            case AppConstants.FAILED:
                if (context instanceof FragmentIntractionWithActivityListner) {
                    mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) context;
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                }
                break;
            default:
                if (context instanceof FragmentIntractionWithActivityListner) {
                    mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) context;
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(context.getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
                }
        }
    }

    public void bookMarkSuccess(Context context, BaseResponse baseResponse, FeedDetail mFeedDetail, GenericRecyclerViewAdapter mAdapter, SwipeRefreshLayout mSwipeView, String screenName) {
        if (null != mFeedDetail) {
            switch (baseResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    AnalyticsManager.trackPostAction(Event.POST_BOOKMARKED, mFeedDetail, screenName);
                    break;
                case AppConstants.FAILED:
                    if (!mFeedDetail.isBookmarked()) {
                        mFeedDetail.setBookmarked(true);
                    } else {
                        mFeedDetail.setBookmarked(false);
                    }
                    mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    showError(mSwipeView, baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_BOOKMARK_UNBOOKMARK);
                    break;
                default:
                    showError(mSwipeView, context.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);
            }
        }
    }

    public void recentCommentEditDelete(Context context, SwipeRefreshLayout swipeView, String screenName, FeedDetail mFeedDetail, BaseResponse baseResponse, GenericRecyclerViewAdapter mAdapter) {
        if (null != mFeedDetail) {
            switch (baseResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    List<Comment> lastCommentList = mFeedDetail.getLastComments();
                    if (StringUtil.isNotEmptyCollection(lastCommentList) && null != lastCommentList.get(lastCommentList.size() - 1)) {
                        Comment lastComment = lastCommentList.get(lastCommentList.size() - 1);
                        lastCommentList.remove(lastComment);
                        mFeedDetail.setLastComments(lastCommentList);
                        AnalyticsManager.trackPostAction(Event.POST_EDITED, mFeedDetail, screenName);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case AppConstants.FAILED:
                    showError(swipeView, baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_COMMENT_REACTION);
                    break;
                default:
                    showError(swipeView, context.getString(R.string.ID_GENERIC_ERROR), ERROR_COMMENT_REACTION);
            }
        }
    }

    /*1:- If pass one from home activity means its comments section changes
    2:- If two Home activity means its Detail section changes of activity,and refresh particular card*/
    public void commentListRefresh(GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, FeedDetail feedDetail, FeedParticipationEnum feedParticipationEnum) {
        if (null != feedDetail) {
            switch (feedParticipationEnum) {
                case ACTIVITY_FOR_REFRESH_FRAGMENT_LIST:
                    mAdapter.setDataOnPosition(feedDetail, feedDetail.getItemPosition());
                    mLayoutManager.scrollToPosition(feedDetail.getItemPosition());
                    break;
                case COMMENT_REACTION:
                    mAdapter.setDataOnPosition(feedDetail, feedDetail.getItemPosition());
                    mLayoutManager.scrollToPosition(feedDetail.getItemPosition());
                    break;
                case DELETE_COMMUNITY_POST:
                    mAdapter.removeDataOnPosition(feedDetail, feedDetail.getItemPosition());
                    break;
                default:
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void likeSuccess(Context context, BaseResponse baseResponse, FeedDetail mFeedDetail, GenericRecyclerViewAdapter mAdapter, SwipeRefreshLayout mSwipeView, String screenName, int mPressedEmoji) {
        if (null != mFeedDetail) {
            switch (baseResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (mFeedDetail.isLongPress()) {
                        if (mFeedDetail.getReactionValue() == AppConstants.NO_REACTION_CONSTANT) {
                            mFeedDetail.setReactionValue(mPressedEmoji);
                            mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                        } else {
                            mFeedDetail.setReactionValue(mPressedEmoji);
                        }
                    }
                    mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    AnalyticsManager.trackPostAction(Event.POST_LIKED, mFeedDetail, screenName);
                    break;
                case AppConstants.FAILED:
                    if (!mFeedDetail.isLongPress()) {
                        if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                            mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                            mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                        } else {
                            mFeedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                            mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                        }
                        mFeedDetail.setReactionValue(mFeedDetail.getLastReactionValue());
                        mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    }
                    showError(mSwipeView, baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_LIKE_UNLIKE);
                    break;
                default:
                    showError(mSwipeView, context.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
            }
        }

    }

    public void showError(SwipeRefreshLayout mSwipeView, String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        if (feedParticipationEnum == ERROR_FEED_RESPONSE) {
            if (null != mSwipeView) {
                mSwipeView.setRefreshing(false);
            }
        }
    }
    //endregion
}
