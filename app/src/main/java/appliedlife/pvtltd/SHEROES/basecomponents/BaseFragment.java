package appliedlife.pvtltd.SHEROES.basecomponents;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.OrganizationFeedObj;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HelplineView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.MARK_AS_SPAM;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base fragment for all child fragment.
 * all the common behaviour.
 */
public abstract class BaseFragment extends Fragment implements EventInterface, View.OnClickListener, HomeView, HelplineView, LoginView {
    private final String TAG = LogUtils.makeLogTag(BaseFragment.class);
    public FragmentActivity mActivity;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList mPullRefreshList;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mPageNo;
    private SwipeRefreshLayout mSwipeView;
    private LinearLayout mLiNoResult;
    private FeedDetail mFeedDetail;
    private Comment mComment;
    private RecyclerView mRecyclerView;
    private int mPosition;
    private int mPressedEmoji;
    private boolean mListLoad = true;
    private boolean mIsEdit;
    private HomePresenter mHomePresenter;
    private AppUtils mAppUtils;
    private ProgressBar mProgressBar;
    public FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;
    private FragmentOpen mFragmentOpen = new FragmentOpen();

    @Inject
    Preference<LoginResponse> userPreference;

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


    public void setAllInitializationForFeeds(FragmentListRefreshData mFragmentListRefreshData, GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, RecyclerView mRecyclerView, HomePresenter mHomePresenter, AppUtils mAppUtils, ProgressBar mProgressBar) {
        this.mFragmentListRefreshData = mFragmentListRefreshData;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        this.mRecyclerView = mRecyclerView;
        this.mHomePresenter = mHomePresenter;
        this.mAppUtils = mAppUtils;
        this.mProgressBar = mProgressBar;
    }


    public void setListLoadFlag(boolean mListLoad) {
        this.mListLoad = mListLoad;
    }

    public void setFeedDetail(FeedDetail feedDetail) {
        this.mFeedDetail = feedDetail;
    }

    public void setRefreshList(SwipPullRefreshList mPullRefreshList) {
        this.mPullRefreshList = mPullRefreshList;
    }

    public void setInitializationForHelpline(FragmentListRefreshData mFragmentListRefreshData, GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, RecyclerView mRecyclerView, AppUtils mAppUtils, ProgressBar mProgressBar) {
        this.mFragmentListRefreshData = mFragmentListRefreshData;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        this.mRecyclerView = mRecyclerView;
        this.mAppUtils = mAppUtils;
        this.mProgressBar = mProgressBar;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPresenter() != null) {
            getPresenter().onCreate();
        }

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
        if (getPresenter() != null) {
            getPresenter().onAttach();
        }
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void showHomeFeedList(List<FeedDetail> feedDetailList) {

    }

    public void challengeAddOnFeed(FeedDetail feedDetail) {
        if (null != feedDetail) {
            mAdapter.addDataOnPosition(feedDetail, 0);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollBy(0, 0);
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case LIKE_UNLIKE:
                likeSuccess(baseResponse);
                break;
            case COMMENT_REACTION:
                recentCommentEditDelete(baseResponse);
                break;
            case BOOKMARK_UNBOOKMARK:
                bookMarkSuccess(baseResponse);
                break;
            case JOIN_INVITE:
                joinInviteResponse(baseResponse);
                break;
            case DELETE_COMMUNITY_POST:
                deleteCommunityPostRespose(baseResponse);
                break;
            case MARK_AS_SPAM:
                reportAsSpamPostRespose(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    public void reportAsSpamPostRespose(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                Toast.makeText(getContext(), AppConstants.MARK_AS_SPAM, Toast.LENGTH_SHORT).show();
                mFeedDetail.setFromHome(true);
                commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                AnalyticsManager.trackPostAction(Event.POST_REPORTED, mFeedDetail, getScreenName());
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), MARK_AS_SPAM);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), MARK_AS_SPAM);
        }
    }

    public void deleteCommunityPostRespose(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                commentListRefresh(mFeedDetail, DELETE_COMMUNITY_POST);
                AnalyticsManager.trackPostAction(Event.POST_DELETED, mFeedDetail, getScreenName());
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }
    }

    public void joinInviteResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (((CommunityFeedSolrObj) mFeedDetail).isClosedCommunity()) {
                    ((CommunityFeedSolrObj) mFeedDetail).setRequestPending(true);

                } else {
                    ((CommunityFeedSolrObj) mFeedDetail).setMember(true);
                }
                commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                MoEHelper mMoEHelper = MoEHelper.getInstance(getActivity());
                PayloadBuilder payloadBuilder = new PayloadBuilder();
                MoEngageUtills moEngageUtills = MoEngageUtills.getInstance();
                //moEngageUtills.entityMoEngageJoinedCommunity(getActivity(), mMoEHelper, payloadBuilder, mFeedDetail.getNameOrTitle(), mFeedDetail.getIdOfEntityOrParticipant(), ((CommunityFeedSolrObj)mFeedDetail).isClosed(), MoEngageConstants.COMMUNITY_TAG, TAG, mFeedDetail.getItemPosition());
                HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant())).name(mFeedDetail.getNameOrTitle()).build();
                AnalyticsManager.trackEvent(Event.COMMUNITY_JOINED, getScreenName(), properties);
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }
    }


    private void recentCommentEditDelete(BaseResponse baseResponse) {
        if (null != mFeedDetail) {
            switch (baseResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    List<Comment> lastCommentList = mFeedDetail.getLastComments();
                    if (StringUtil.isNotEmptyCollection(lastCommentList) && null != lastCommentList.get(lastCommentList.size() - 1)) {
                        Comment lastComment = lastCommentList.get(lastCommentList.size() - 1);
                        lastCommentList.remove(lastComment);
                        mFeedDetail.setLastComments(lastCommentList);
                        AnalyticsManager.trackPostAction(Event.POST_EDITED, mFeedDetail, getScreenName());
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case AppConstants.FAILED:
                    showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_COMMENT_REACTION);
                    break;
                default:
                    showError(getString(R.string.ID_GENERIC_ERROR), ERROR_COMMENT_REACTION);
            }
        }
    }

    /*1:- If pass one from home activity means its comments section changes
    2:- If two Home activity means its Detail section changes of activity,and refresh particular card*/
    public void commentListRefresh(FeedDetail feedDetail, FeedParticipationEnum feedParticipationEnum) {
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
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void bookMarkSuccess(BaseResponse baseResponse) {
        if (null != mFeedDetail) {
            switch (baseResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    MoEHelper mMoEHelper = MoEHelper.getInstance(getActivity());
                    PayloadBuilder payloadBuilder = new PayloadBuilder();
                    MoEngageUtills moEngageUtills = MoEngageUtills.getInstance();
                    moEngageUtills.entityMoEngageBookMarkData(getActivity(), mMoEHelper, payloadBuilder, mFeedDetail);
                    AnalyticsManager.trackPostAction(Event.POST_BOOKMARKED, mFeedDetail, getScreenName());
                    break;
                case AppConstants.FAILED:
                    if (!mFeedDetail.isBookmarked()) {
                        mFeedDetail.setBookmarked(true);
                    } else {
                        mFeedDetail.setBookmarked(false);
                    }
                    mAdapter.notifyItemChanged(mFeedDetail.getItemPosition(), mFeedDetail);
                    showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_BOOKMARK_UNBOOKMARK);
                    break;
                default:
                    showError(getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);
            }
        }
    }

    protected void likeSuccess(BaseResponse baseResponse) {
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
                    MoEHelper mMoEHelper = MoEHelper.getInstance(getActivity());
                    PayloadBuilder payloadBuilder = new PayloadBuilder();
                    MoEngageUtills moEngageUtills = MoEngageUtills.getInstance();
                    moEngageUtills.entityMoEngageReaction(getActivity(), mMoEHelper, payloadBuilder, mFeedDetail, mPressedEmoji, mPosition);
                    if (mFeedDetail instanceof OrganizationFeedObj) {
                        AnalyticsManager.trackPostAction(Event.ORGANIZATION_UPVOTED, mFeedDetail, getScreenName());
                    } else {
                        AnalyticsManager.trackPostAction(Event.POST_LIKED, mFeedDetail, getScreenName());
                    }
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
                    showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_LIKE_UNLIKE);
                    break;
                default:
                    showError(getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
            }
        }

    }

    public void markAsSpamCommunityPost(FeedDetail feedDetail) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mHomePresenter.markAsSpamFromPresenter(mAppUtils.bookMarkRequestBuilder(feedDetail.getEntityOrParticipantId()));
    }

    public void deleteCommunityPost(FeedDetail feedDetail) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mHomePresenter.deleteCommunityPostFromPresenter(AppUtils.deleteCommunityPostRequest(feedDetail.getIdOfEntityOrParticipant()));
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mHomePresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(feedDetail.getEntityOrParticipantId()), feedDetail.isBookmarked());
    }


    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        if (baseResponse instanceof FeedDetail) {
            mListLoad = false;
            mFeedDetail = (FeedDetail) baseResponse;
            this.mPosition = position;
            this.mPressedEmoji = reactionValue;
            if (null != mFeedDetail && mFeedDetail.isLongPress()) {
                mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), reactionValue));
            } else {
                if (reactionValue == AppConstants.NO_REACTION_CONSTANT) {
                    mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), AppConstants.EVENT_CONSTANT));
                } else {
                    mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(mFeedDetail.getEntityOrParticipantId()));
                }
            }
        }

        if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            if (reactionValue == AppConstants.NO_REACTION_CONSTANT) {
                mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(comment.getEntityId(), comment.getCommentsId()), comment);
            } else {
                mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(comment.getEntityId(), reactionValue, comment.getCommentsId()), comment);
            }
        }

    }

    @Override
    public void invalidateLikeUnlike(Comment comment) {

    }


    @Override
    public void getNotificationListSuccess(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        if (feedParticipationEnum == ERROR_FEED_RESPONSE) {
            if (null != mSwipeView) {
                // mLiNoResult.setVisibility(View.VISIBLE);
                mSwipeView.setRefreshing(false);
            }
        }
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(errorMsg, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {

    }

    @Override
    public void sendVerificationEmailSuccess(EmailVerificationResponse emailVerificationResponse) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (trackScreenTime()) {
            AnalyticsManager.timeScreenView(getScreenName());
        }
        if (getPresenter() != null) {
            getPresenter().onResume();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getPresenter() != null) {
            getPresenter().onDetach();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        boolean handled = false;
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW)) {
            if (AppUtils.matchesWebsiteURLPattern(intent.getDataString())) {
                Uri url = Uri.parse(intent.getDataString());
                AppUtils.openChromeTab(getActivity(), url);
                handled = true;
            }
        }
        if (!handled) {
            super.startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (shouldTrackScreen()) {
            AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
        }
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    public boolean shouldTrackScreen() {
        return true;
    }

    protected boolean trackScreenTime() {
        return false;
    }

    protected Map<String, Object> getExtraProperties() {
        return null;
    }

    @Override
    public void trackEvent(final Event event, final Map<String, Object> properties) {
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }


    /**
     * this method will be use for fragment pop
     */
    protected void onBackPress() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected void getExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                LogUtils.info("testing", "Permission is revoked");


            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                LogUtils.info("testing", "Permission is revoked");

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            LogUtils.info("testing", "Permission is already granted");

        }

    }

    public void joinRequestForOpenCommunity(FeedDetail feedDetail) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add(userPreference.get().getUserSummary().getUserId());
            setFeedDetail(feedDetail);
            mHomePresenter.communityJoinFromPresenter(AppUtils.communityRequestBuilder(userIdList, feedDetail.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY));
        }
    }


    @Override
    public void getHelpChatThreadSuccess(HelplineGetChatThreadResponse helplineGetChatThreadResponse) {

    }

    @Override
    public void getPostQuestionSuccess(HelplinePostQuestionResponse helplinePostQuestionResponse) {

    }

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordResponse forgotPasswordResponse) {

    }

    protected abstract SheroesPresenter getPresenter();
}
