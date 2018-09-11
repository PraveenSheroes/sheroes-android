package appliedlife.pvtltd.SHEROES.presenters;


import android.support.v7.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTopPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatePollResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.DeletePollRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollVote;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollVoteResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.usertagging.ui.RichEditorView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.RxSearchObservable;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_CREATE_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.postCommentRequestBuilder;


/**
 * Created by ujjwal on 06/10/17.
 */

public class PostDetailViewImpl extends BasePresenter<IPostDetailView> {
    SheroesAppServiceApi mSheroesAppServiceApi;
    private FeedDetail mFeedDetail;
    private String mCommunityPostDetailDeepLink;
    private String mFeedDetailObjId;
    private List<BaseResponse> mBaseResponseList;
    private SheroesApplication mSheroesApplication;
    private int headerCount = 0;
    private int pageNumber = 1;
    private Comment lastComment;
    private boolean mIsScrollAllowed;


    @Inject
    AppUtils mAppUtils;

    @Inject
    public PostDetailViewImpl(SheroesAppServiceApi mSheroesAppServiceApi, SheroesApplication sheroesApplication) {
        this.mSheroesAppServiceApi = mSheroesAppServiceApi;
        this.mBaseResponseList = new ArrayList<>();
        this.mSheroesApplication = sheroesApplication;
    }

    //region Presenter methods

    public void smoothScrollOnComment(boolean isScrollAllowed) {
        this.mIsScrollAllowed = isScrollAllowed;
    }

    public void setUserPost(FeedDetail feedDetail, String userPostId, String communityPostDetailDeepLink) {
        this.mFeedDetail = feedDetail;
        this.mFeedDetailObjId = userPostId;
        mCommunityPostDetailDeepLink = communityPostDetailDeepLink;
    }

    public void fetchUserPost() {
        if (mFeedDetail == null) {
            if (StringUtil.isNotNullOrEmptyString(mCommunityPostDetailDeepLink)) {
                if (AppConstants.POLL_URL_COM.equalsIgnoreCase(mCommunityPostDetailDeepLink)) {
                    fetchPollFromServer();
                } else {
                    fetchUserPostFromServer();
                }
            }
        } else {
            mBaseResponseList.add(mFeedDetail);
            getMvpView().addData(0, mFeedDetail);
            headerCount++;
            getMvpView().startProgressBar();
            getAllCommentFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), pageNumber));
        }
    }

    private void fetchUserPostFromServer() {
        FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, 1, Long.valueOf(mFeedDetailObjId));
        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
        getFeedFromPresenter(feedRequestPojo);
    }

    private void fetchPollFromServer() {
        FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_POLL, 1, Long.valueOf(mFeedDetailObjId));
        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getPollDetail(mFeedDetailObjId,feedRequestPojo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
                    }


                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        if (null != feedResponsePojo && !CommonUtil.isEmpty(feedResponsePojo.getFeedDetails())) {
                            mFeedDetail = feedResponsePojo.getFeedDetails().get(0);
                            if (CommonUtil.isNotEmpty(getMvpView().getStreamType())) {
                                mFeedDetail.setStreamType(getMvpView().getStreamType());
                            }
                            mBaseResponseList.add(mFeedDetail);
                            getMvpView().addData(0, mFeedDetail);
                            headerCount++;
                            getAllCommentFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), pageNumber));
                        }else
                        {
                            getMvpView().stopProgressBar();
                        }
                    }
                });
    }

    public void fetchMoreComments() {
        getMvpView().commentStartedLoading();
        getAllCommentFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), pageNumber));
    }

    private void getAllCommentFromPresenter(final CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            getMvpView().hideProgressBar();
            return;
        }
        getAllCommentListFromModel(commentReactionRequestPojo).subscribe(new DisposableObserver<CommentReactionResponsePojo>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_SERVER_PROBLEM), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().commentFinishedLoading();
                if (pageNumber == 1 && commentResponsePojo.getNumFound() > AppConstants.PAGE_SIZE_FOR_COMMENTS) {
                    BaseResponse baseResponse = new BaseResponse();
                    mBaseResponseList.add(baseResponse);
                    getMvpView().addData(baseResponse);
                    headerCount++;
                    getMvpView().setHasMoreComments(true);
                }
                Collections.reverse(commentResponsePojo.getCommentList());
                mBaseResponseList.addAll(headerCount, commentResponsePojo.getCommentList());
                getMvpView().addAllPost(headerCount, commentResponsePojo.getCommentList());

                if (pageNumber != 1) {
                    if (commentResponsePojo.getCommentList().size() < AppConstants.PAGE_SIZE_FOR_COMMENTS) {
                        mBaseResponseList.remove(1);
                        getMvpView().setHasMoreComments(false);
                    }
                    mIsScrollAllowed = false;
                } else {
                    if (mFeedDetail instanceof UserPostSolrObj) {
                        UserPostSolrObj userPostSolrObj = (UserPostSolrObj) mFeedDetail;
                        if (userPostSolrObj.getIsEditOrDelete() == AppConstants.COMMENT_EDIT) {
                            userPostSolrObj.setIsEditOrDelete(0);
                            mBaseResponseList.set(0, userPostSolrObj);
                            getMvpView().editLastComment();
                        }
                        if (userPostSolrObj.getIsEditOrDelete() == AppConstants.COMMENT_DELETE) {
                            userPostSolrObj.setIsEditOrDelete(0);
                            mBaseResponseList.set(0, userPostSolrObj);
                            getMvpView().deleteLastComment();
                        }
                    } else if (mFeedDetail instanceof PollSolarObj) {
                        PollSolarObj pollSolarObj = (PollSolarObj) mFeedDetail;
                        if (pollSolarObj.getIsEditOrDelete() == AppConstants.COMMENT_EDIT) {
                            pollSolarObj.setIsEditOrDelete(0);
                            mBaseResponseList.set(0, pollSolarObj);
                            getMvpView().editLastComment();
                        }
                        if (pollSolarObj.getIsEditOrDelete() == AppConstants.COMMENT_DELETE) {
                            pollSolarObj.setIsEditOrDelete(0);
                            mBaseResponseList.set(0, pollSolarObj);
                            getMvpView().deleteLastComment();
                        }
                    }
                }
                if (mIsScrollAllowed) {
                    getMvpView().smoothScrollToBottom();
                }
                pageNumber++;
            }
        });

    }

    public void editTopPost(final CommunityTopPostRequest communityTopPostRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        editPostCommunity(communityTopPostRequest)
                .compose(this.<CreateCommunityResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CreateCommunityResponse>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                        getMvpView().stopProgressBar();
                        getMvpView().setData(0, communityPostCreateResponse.getFeedDetail());
                    }

                });

    }

    private Observable<CreateCommunityResponse> editPostCommunity(CommunityTopPostRequest communityPostCreateRequest) {
        return mSheroesAppServiceApi.topPostCommunityPost(communityPostCreateRequest)
                .map(new Function<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse apply(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getFeedFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        getFeedFromModel(feedRequestPojo).subscribe(new DisposableObserver<FeedResponsePojo>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);

            }


            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                if (null != feedResponsePojo && !CommonUtil.isEmpty(feedResponsePojo.getFeedDetails())) {
                    mFeedDetail = (UserPostSolrObj) feedResponsePojo.getFeedDetails().get(0);
                    if (CommonUtil.isNotEmpty(getMvpView().getStreamType())) {
                        mFeedDetail.setStreamType(getMvpView().getStreamType());
                    }
                    mBaseResponseList.add(mFeedDetail);
                    getMvpView().addData(0, mFeedDetail);
                    headerCount++;
                    getAllCommentFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), pageNumber));
                }
            }
        });

    }


    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        return mSheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Function<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo apply(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<CommentReactionResponsePojo> getAllCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return mSheroesAppServiceApi.getCommentFromApi(commentReactionRequestPojo)
                .map(new Function<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                    @Override
                    public CommentReactionResponsePojo apply(CommentReactionResponsePojo commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void addComment(String commentText, boolean isAnonymous, boolean hasMention, List<MentionSpan> mentionSpanList) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        if (mFeedDetail == null) {
            return;
        }
        getMvpView().startProgressBar();
        CommentReactionRequestPojo commentReactionRequestPojo = postCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), commentText, isAnonymous, hasMention, mentionSpanList);
        addCommentListFromModel(commentReactionRequestPojo).subscribe(new DisposableObserver<CommentAddDelete>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_UNABLE_TO_COMMENT), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentAddDelete commentResponsePojo) {
                getMvpView().stopProgressBar();
                if (null != commentResponsePojo) {
                    Comment comment = commentResponsePojo.getCommentReactionModel();
                    mBaseResponseList.add(comment);
                    mFeedDetail.setNoOfComments(mFeedDetail.getNoOfComments() + 1);
                    mBaseResponseList.set(0, mFeedDetail);
                    getMvpView().setData(0, mFeedDetail);
                    getMvpView().addData(comment);
                    getMvpView().smoothScrollToBottom();
                    HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .id(Long.toString(commentResponsePojo.getCommentReactionModel().getId()))
                                    .postId(Long.toString(mFeedDetail.getIdOfEntityOrParticipant()))
                                    .postCommentId(Long.toString(commentResponsePojo.getCommentReactionModel().getEntityId()))
                                    .postType(AnalyticsEventType.COMMUNITY.toString())
                                    .body(commentResponsePojo.getCommentReactionModel().getComment())
                                    .streamType(CommonUtil.isNotEmpty(mFeedDetail.getStreamType()) ? mFeedDetail.getStreamType() : "")
                                    .communityId(commentResponsePojo.getCommentReactionModel().getCommunityId())
                                    .build();
                    AnalyticsManager.trackEvent(Event.REPLY_CREATED, PostDetailActivity.SCREEN_LABEL, properties);
                }
            }
        });

    }

    private Observable<CommentAddDelete> addCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return mSheroesAppServiceApi.addCommentFromApi(commentReactionRequestPojo)
                .map(new Function<CommentAddDelete, CommentAddDelete>() {
                    @Override
                    public CommentAddDelete apply(CommentAddDelete commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void editCommentListFromPresenter(final CommentReactionRequestPojo commentReactionRequestPojo, final int editDeleteId) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        //  getMvpView().startProgressBar();
        editCommentListFromModel(commentReactionRequestPojo).subscribe(new DisposableObserver<CommentAddDelete>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_UNABLE_TO_EDIT_DELETE), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentAddDelete commentResponsePojo) {
                getMvpView().stopProgressBar();
                if (commentResponsePojo.getStatus().equals(AppConstants.SUCCESS)) {

                    if (editDeleteId == AppConstants.ONE_CONSTANT) {
                        int pos = findCommentPositionById(mBaseResponseList, commentReactionRequestPojo.getParticipationId());
                        if (pos != RecyclerView.NO_POSITION) {
                            mBaseResponseList.remove(pos);
                            getMvpView().removeData(pos);

                            mFeedDetail.setNoOfComments(mFeedDetail.getNoOfComments() - 1);
                            mBaseResponseList.set(0, mFeedDetail);
                            getMvpView().setData(0, mFeedDetail);
                        }
                    } else {
                        getMvpView().updateComment(commentResponsePojo.getCommentReactionModel());
                    }
                }
            }
        });
    }

    private Observable<CommentAddDelete> editCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return mSheroesAppServiceApi.editCommentFromApi(commentReactionRequestPojo)
                .map(new Function<CommentAddDelete, CommentAddDelete>() {
                    @Override
                    public CommentAddDelete apply(CommentAddDelete commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static int findCommentPositionById(List<BaseResponse> baseResponses, long id) {
        if (CommonUtil.isEmpty(baseResponses)) {
            return -1;
        }

        for (int i = 0; i < baseResponses.size(); ++i) {
            BaseResponse baseResponse = baseResponses.get(i);
            if (baseResponse instanceof Comment) {
                Comment comment = (Comment) baseResponse;
                if (comment.getId() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void getCommentUnLikesFromPresenter(final LikeRequestPojo likeRequestPojo, final Comment comment) {
        final int pos = findCommentPositionById(mBaseResponseList, comment.getId());
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = true;
            comment.likeCount++;
            getMvpView().setData(pos, comment);
            return;
        }
        getMvpView().startProgressBar();
        getUnLikesFromModel(likeRequestPojo).subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                comment.isLiked = true;
                comment.likeCount++;
                getMvpView().setData(pos, comment);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                    comment.isLiked = true;
                    comment.likeCount++;
                }
                getMvpView().setData(pos, comment);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(comment.getId()))
                                .postId(Long.toString(comment.getEntityId()))
                                .postType(AnalyticsEventType.COMMUNITY.toString())
                                .body(comment.getComment())
                                .communityId(comment.getCommunityId())
                                .streamType((mFeedDetail != null && CommonUtil.isNotEmpty(mFeedDetail.getStreamType())) ? mFeedDetail.getStreamType() : "")
                                .build();
                AnalyticsManager.trackEvent(Event.REPLY_UNLIKED, PostDetailActivity.SCREEN_LABEL, properties);
            }
        });

    }

    public void getCommentLikesFromPresenter(LikeRequestPojo likeRequestPojo, final Comment comment) {
        final int pos = findCommentPositionById(mBaseResponseList, comment.getId());
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = false;
            comment.likeCount--;
            getMvpView().setData(pos, comment);
            return;
        }
        getMvpView().startProgressBar();
        getLikesFromModel(likeRequestPojo).subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                comment.isLiked = false;
                comment.likeCount--;
                getMvpView().setData(pos, comment);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                    comment.isLiked = false;
                    comment.likeCount--;
                }
                getMvpView().setData(pos, comment);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(comment.getId()))
                                .postId(Long.toString(comment.getEntityId()))
                                .postType(AnalyticsEventType.COMMUNITY.toString())
                                .body(comment.getComment())
                                .communityId(comment.getCommunityId())
                                .streamType((mFeedDetail != null && CommonUtil.isNotEmpty(mFeedDetail.getStreamType())) ? mFeedDetail.getStreamType() : "")
                                .build();
                AnalyticsManager.trackEvent(Event.REPLY_LIKED, PostDetailActivity.SCREEN_LABEL, properties);
            }
        });

    }

    public void deleteCommunityPostFromPresenter(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        deleteCommunityPostFromModel(deleteCommunityPostRequest).subscribe(new DisposableObserver<DeleteCommunityPostResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);

            }

            @Override
            public void onNext(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                getMvpView().stopProgressBar();
                getMvpView().onPostDeleted();
            }
        });

    }

    public void deletePollFromPresenter(DeletePollRequest deletePollRequest, final PollSolarObj pollSolarObj) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.deletePoll(deletePollRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CreatePollResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CreatePollResponse>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(CreatePollResponse communityPostCreateResponse) {
                        if (communityPostCreateResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().onPostDeleted();
                        }
                        getMvpView().stopProgressBar();
                    }

                });
    }


    private Observable<DeleteCommunityPostResponse> deleteCommunityPostFromModel(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        return mSheroesAppServiceApi.getCommunityPostDeleteResponse(deleteCommunityPostRequest)
                .map(new Function<DeleteCommunityPostResponse, DeleteCommunityPostResponse>() {
                    @Override
                    public DeleteCommunityPostResponse apply(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                        return deleteCommunityPostResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getPollVoteFromPresenter(PollVote pollVote, final FeedDetail feedDetail, final PollOptionModel pollOptionModel) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            PollSolarObj pollSolarObj = (PollSolarObj) feedDetail;
            pollSolarObj.setTotalNumberOfResponsesOnPoll(pollSolarObj.getTotalNumberOfResponsesOnPoll() - AppConstants.ONE_CONSTANT);
            mBaseResponseList.set(0, pollSolarObj);
            getMvpView().setData(0, pollSolarObj);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getPollVoteFromApi(pollVote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<PollVoteResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<PollVoteResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
                        PollSolarObj pollSolarObj = (PollSolarObj) feedDetail;
                        pollSolarObj.setTotalNumberOfResponsesOnPoll(pollSolarObj.getTotalNumberOfResponsesOnPoll() - AppConstants.ONE_CONSTANT);
                        mBaseResponseList.set(0, pollSolarObj);
                        getMvpView().setData(0, pollSolarObj);
                    }

                    @Override
                    public void onNext(PollVoteResponse voteResponse) {
                        getMvpView().stopProgressBar();
                        PollSolarObj pollSolarObj = null;
                        if (voteResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            pollSolarObj = voteResponse.getPollReactionModel().getPollSolrObj();
                            AnalyticsManager.trackPollAction(Event.POLL_VOTED, feedDetail, PostDetailActivity.SCREEN_LABEL,pollOptionModel.getPollOptionId());
                        } else if (voteResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            pollSolarObj = (PollSolarObj) feedDetail;
                            pollSolarObj.setTotalNumberOfResponsesOnPoll(pollSolarObj.getTotalNumberOfResponsesOnPoll() - AppConstants.ONE_CONSTANT);
                        }
                        mBaseResponseList.set(0, pollSolarObj);
                        getMvpView().setData(0, pollSolarObj);
                    }
                });

    }


    public void getLikesFromPresenter(LikeRequestPojo likeRequestPojo, final FeedDetail feedDetail) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            feedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            mBaseResponseList.set(0, feedDetail);
            getMvpView().setData(0, feedDetail);
            return;
        }
        getMvpView().startProgressBar();
        getLikesFromModel(likeRequestPojo).subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                feedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                mBaseResponseList.set(0, feedDetail);
                getMvpView().setData(0, feedDetail);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                    feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                    feedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                    mBaseResponseList.set(0, feedDetail);
                    getMvpView().setData(0, feedDetail);
                }
                if (feedDetail instanceof UserPostSolrObj) {
                    AnalyticsManager.trackPostAction(Event.POST_LIKED, feedDetail, PostDetailActivity.SCREEN_LABEL);
                } else if (feedDetail instanceof PollSolarObj) {
                    AnalyticsManager.trackPollAction(Event.POLL_LIKED, feedDetail, PostDetailActivity.SCREEN_LABEL);
                }
                getMvpView().setData(0, feedDetail);
            }
        });

    }

    public void getUnLikesFromPresenter(LikeRequestPojo likeRequestPojo, final FeedDetail feedDetail) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            feedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            mBaseResponseList.set(0, feedDetail);
            getMvpView().setData(0, feedDetail);
            return;
        }
        getMvpView().startProgressBar();
        getUnLikesFromModel(likeRequestPojo).subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                feedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mBaseResponseList.set(0, feedDetail);
                getMvpView().setData(0, feedDetail);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                    feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                    feedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                    mBaseResponseList.set(0, feedDetail);
                }
                if (feedDetail instanceof UserPostSolrObj) {
                    AnalyticsManager.trackPostAction(Event.POST_UNLIKED, feedDetail, PostDetailActivity.SCREEN_LABEL);
                } else if (feedDetail instanceof PollSolarObj) {
                    AnalyticsManager.trackPollAction(Event.POLL_UNLIKED, feedDetail, PostDetailActivity.SCREEN_LABEL);
                }

                getMvpView().setData(0, feedDetail);
            }
        });

    }


    private Observable<LikeResponse> getLikesFromModel(LikeRequestPojo likeRequestPojo) {
        return mSheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                .map(new Function<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse apply(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<LikeResponse> getUnLikesFromModel(LikeRequestPojo likeRequestPojo) {
        return mSheroesAppServiceApi.getUnLikesFromApi(likeRequestPojo)
                .map(new Function<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse apply(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getSpamPostApproveFromPresenter(final ApproveSpamPostRequest approveSpamPostRequest, final UserPostSolrObj userPostSolrObj) {
        getMvpView().startProgressBar();
        getSpamPostApproveFromModel(approveSpamPostRequest).subscribe(new DisposableObserver<ApproveSpamPostResponse>() {

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
            }

            @Override
            public void onNext(ApproveSpamPostResponse approveSpamPostResponse) {
                getMvpView().stopProgressBar();
                if (null != approveSpamPostResponse) {
                    if (approveSpamPostResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                        getMvpView().onPostDeleted();
                    } else {
                        userPostSolrObj.setSpamPost(false);
                        mBaseResponseList.set(0, userPostSolrObj);
                        getMvpView().setData(0, userPostSolrObj);
                    }
                }
            }
        });

    }

    private Observable<ApproveSpamPostResponse> getSpamPostApproveFromModel(ApproveSpamPostRequest approveSpamPostRequest) {
        return mSheroesAppServiceApi.spamPostApprove(approveSpamPostRequest)
                .map(new Function<ApproveSpamPostResponse, ApproveSpamPostResponse>() {
                    @Override
                    public ApproveSpamPostResponse apply(ApproveSpamPostResponse approveSpamPostResponse) {
                        return approveSpamPostResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public FeedDetail getUserPostObj() {
        if (mFeedDetail == null) {
            return null;
        }
        List<Comment> comments = new ArrayList<>();
        if (!mBaseResponseList.isEmpty()) {
            if (mBaseResponseList.get(mBaseResponseList.size() - 1) instanceof Comment) {
                comments.add((Comment) mBaseResponseList.get(mBaseResponseList.size() - 1));
            }
            if (mFeedDetail instanceof PollSolarObj) {
                mFeedDetail = (PollSolarObj) mBaseResponseList.get(0);
            } else {
                mFeedDetail.setLastComments(comments);
            }
        }
        return mFeedDetail;
    }

    public void updateUserPost(UserPostSolrObj userPostSolrObj) {
        mFeedDetail = userPostSolrObj;
        if (CommonUtil.isNotEmpty(getMvpView().getStreamType())) {
            mFeedDetail.setStreamType(getMvpView().getStreamType());
        }
        mBaseResponseList.set(0, userPostSolrObj);
        getMvpView().setData(0, userPostSolrObj);
    }

    public Comment getLastComment() {
        if (!CommonUtil.isEmpty(mBaseResponseList)) {
            BaseResponse baseResponse = mBaseResponseList.get(mBaseResponseList.size() - 1);
            if (baseResponse instanceof Comment) {
                return (Comment) baseResponse;
            } else {
                return null;
            }
        }
        return null;
    }

    //Debounce for usermention function

    public void getUserMentionSuggestion(final RichEditorView richEditorView, final FeedDetail feedDetail) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }

        RxSearchObservable.fromView(richEditorView, getMvpView())
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMap(new Function<String, ObservableSource<SearchUserDataResponse>>() {
                    @Override
                    public ObservableSource<SearchUserDataResponse> apply(String query) throws Exception {
                        SearchUserDataRequest searchUserDataRequest = null;
                        Long communityId = null, postEntityId = null, postAuthorUserId = null;
                        if (null != feedDetail) {
                            if (feedDetail instanceof UserPostSolrObj) {
                                if (((UserPostSolrObj) feedDetail).getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                                    communityId = null;
                                } else {
                                    communityId = ((UserPostSolrObj) feedDetail).getCommunityId();
                                }
                            } else if (feedDetail instanceof PollSolarObj) {
                                communityId = ((PollSolarObj) feedDetail).getCommunityId();
                            }
                            postEntityId = feedDetail.getEntityOrParticipantId();
                            postAuthorUserId = feedDetail.getAuthorId();
                        } else {
                            if (null != mFeedDetail) {
                                if (mFeedDetail instanceof UserPostSolrObj) {
                                    UserPostSolrObj userPostSolrObj = (UserPostSolrObj) mFeedDetail;
                                    if (userPostSolrObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                                        communityId = null;
                                    } else {
                                        communityId = userPostSolrObj.getCommunityId();
                                    }
                                    postEntityId = userPostSolrObj.getEntityOrParticipantId();
                                    postAuthorUserId = userPostSolrObj.getAuthorId();
                                } else if (mFeedDetail instanceof PollSolarObj) {
                                    PollSolarObj pollSolarObj = (PollSolarObj) mFeedDetail;
                                    communityId = pollSolarObj.getCommunityId();
                                    postEntityId = pollSolarObj.getEntityOrParticipantId();
                                    postAuthorUserId = pollSolarObj.getAuthorId();
                                }
                            }
                        }
                        if (query.length() == 1) {
                            searchUserDataRequest = mAppUtils.searchUserDataRequest("", communityId, postEntityId, postAuthorUserId, "COMMENT");
                        } else {
                            searchUserDataRequest = mAppUtils.searchUserDataRequest(query.trim().replace("@", ""), communityId, postEntityId, postAuthorUserId, "COMMENT");
                        }
                        if (searchUserDataRequest == null) {
                            return Observable.empty();
                        }
                        return getUserMentionSuggestionSearchResult(searchUserDataRequest);
                    }
                })
                .compose(this.<SearchUserDataResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SearchUserDataResponse>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_COMMENT_REACTION);
                    }

                    @Override
                    public void onNext(SearchUserDataResponse searchUserDataResponse) {
                        if (null != searchUserDataResponse) {
                            if (searchUserDataResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                                getMvpView().userMentionSuggestionResponse(searchUserDataResponse, null);
                            } else {
                                getMvpView().showError("No user found", ERROR_COMMENT_REACTION);
                            }
                        }
                    }

                });

    }

    private Observable<SearchUserDataResponse> getUserMentionSuggestionSearchResult(SearchUserDataRequest searchUserDataRequest) {
        return mSheroesAppServiceApi.userMentionSuggestion(searchUserDataRequest)
                .map(new Function<SearchUserDataResponse, SearchUserDataResponse>() {
                    @Override
                    public SearchUserDataResponse apply(SearchUserDataResponse searchUserDataResponse) {
                        return searchUserDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    //endregion


    public void reportSpamPostOrComment(SpamPostRequest spamPostRequest, final UserPostSolrObj userPostSolrObj, final Comment comment) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.reportSpamPostOrComment(spamPostRequest)
                .subscribeOn(Schedulers.io())
                .compose(this.<SpamResponse>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SpamResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(SpamResponse spamPostOrCommentResponse) {
                        getMvpView().onSpamPostOrCommentReported(spamPostOrCommentResponse, userPostSolrObj, comment);
                        getMvpView().stopProgressBar();
                    }
                });

    }

    public Observable<MentorFollowUnfollowResponse> getFollowFromModel(PublicProfileListRequest publicProfileListRequest) {
        return mSheroesAppServiceApi.getMentorFollowFromApi(publicProfileListRequest)
                .map(new Function<MentorFollowUnfollowResponse, MentorFollowUnfollowResponse>() {
                    @Override
                    public MentorFollowUnfollowResponse apply(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                        return mentorFollowUnfollowResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MentorFollowUnfollowResponse> getUnFollowFromModel(PublicProfileListRequest publicProfileListRequest) {
        return mSheroesAppServiceApi.getMentorUnFollowFromApi(publicProfileListRequest)
                .map(new Function<MentorFollowUnfollowResponse, MentorFollowUnfollowResponse>() {
                    @Override
                    public MentorFollowUnfollowResponse apply(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                        return mentorFollowUnfollowResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //Spam Comment for admin
    public void getSpamCommentApproveFromPresenter(final ApproveSpamPostRequest approveSpamPostRequest, final Comment comment) {
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.approveSpamComment(approveSpamPostRequest)
                .subscribeOn(Schedulers.io())
                .compose(this.<SpamResponse>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                    }

                    @Override
                    public void onNext(BaseResponse approveSpamPostResponse) {
                        getMvpView().stopProgressBar();
                        if (null != approveSpamPostResponse) {
                            int pos = findCommentPositionById(mBaseResponseList, comment.getId());
                            if (pos != RecyclerView.NO_POSITION) {
                                mBaseResponseList.remove(pos);
                                getMvpView().removeData(pos);

                                mFeedDetail.setNoOfComments(mFeedDetail.getNoOfComments() - 1);
                                mBaseResponseList.set(0, mFeedDetail);
                                getMvpView().setData(0, mFeedDetail);

                                //Event for the post comment deleted by admin
                                HashMap<String, Object> properties =
                                        new EventProperty.Builder()
                                                .id(Long.toString(comment.getId()))
                                                .postId(Long.toString(comment.getEntityId()))
                                                .postType(AnalyticsEventType.ARTICLE.toString())
                                                .body(comment.getComment())
                                                .streamType(getMvpView().getStreamType())
                                                .build();
                                AnalyticsManager.trackEvent(Event.REPLY_DELETED, ArticleActivity.SOURCE_SCREEN, properties);

                            }
                        }
                    }
                });

    }

    //Post Follow /Following
    public void getPostAuthorFollowed(PublicProfileListRequest publicProfileListRequest, final UserPostSolrObj userPostSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        getFollowFromModel(publicProfileListRequest)
                .compose(this.<MentorFollowUnfollowResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MentorFollowUnfollowResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                        userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                    }

                    @Override
                    public void onNext(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                        getMvpView().stopProgressBar();
                        if (mentorFollowUnfollowResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            userPostSolrObj.setSolrIgnoreIsUserFollowed(true);
                        } else {
                            userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                        }
                        getMvpView().setData(0, userPostSolrObj);
                    }
                });

    }

    //Unfollowed
    public void getPostAuthorUnfollowed(PublicProfileListRequest publicProfileListRequest, final UserPostSolrObj userPostSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        getUnFollowFromModel(publicProfileListRequest)
                .compose(this.<MentorFollowUnfollowResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MentorFollowUnfollowResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                        userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                    }

                    @Override
                    public void onNext(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                        getMvpView().stopProgressBar();
                        if (mentorFollowUnfollowResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (userPostSolrObj.getEntityOrParticipantTypeId() == 7) {
                                userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                            } else {
                                userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                            }
                        } else {
                            userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                        }
                        getMvpView().setData(0, userPostSolrObj);
                    }
                });
    }
}
