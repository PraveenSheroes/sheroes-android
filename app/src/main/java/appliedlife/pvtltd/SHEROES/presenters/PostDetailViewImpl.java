package appliedlife.pvtltd.SHEROES.presenters;


import android.support.v7.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTopPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Post;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_CREATE_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.postCommentRequestBuilder;


/**
 * Created by ujjwal on 06/10/17.
 */

public class PostDetailViewImpl extends BasePresenter<IPostDetailView> {
    SheroesAppServiceApi sheroesAppServiceApi;
    private UserPostSolrObj mUserPostObj;
    private String mUserPostId;
    private List<BaseResponse> mBaseResponseList;
    private SheroesApplication mSheroesApplication;
    private int headerCount = 0;
    private int pageNumber = 1;
    private Comment lastComment;

    @Inject
    AppUtils mAppUtils;

    @Inject
    public PostDetailViewImpl(SheroesAppServiceApi sheroesAppServiceApi, SheroesApplication sheroesApplication) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.mBaseResponseList = new ArrayList<>();
        this.mSheroesApplication = sheroesApplication;
    }

    //region Presenter methods

    public void setUserPost(UserPostSolrObj userPostSolrObj, String userPostId) {
        this.mUserPostObj = userPostSolrObj;
        this.mUserPostId = userPostId;
    }

    public void fetchUserPost() {
        if (mUserPostObj == null) {
            fetchUserPostFromServer();
        } else {
            mBaseResponseList.add(mUserPostObj);
            getMvpView().addData(0, mUserPostObj);
            headerCount++;
            getMvpView().startProgressBar();
            getAllCommentFromPresenter(getCommentRequestBuilder(mUserPostObj.getEntityOrParticipantId(), pageNumber));
        }
    }

    private void fetchUserPostFromServer() {
        FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, 1, Long.valueOf(mUserPostId));
        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
        getFeedFromPresenter(feedRequestPojo);
    }

    public void fetchMoreComments() {
        getMvpView().commentStartedLoading();
        getAllCommentFromPresenter(getCommentRequestBuilder(mUserPostObj.getEntityOrParticipantId(), pageNumber));
    }

    public void getAllCommentFromPresenter(final CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            getMvpView().hideProgressBar();
            return;
        }
        Subscription subscription = getAllCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
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
                boolean smoothScrollToBottom = false;
                getMvpView().stopProgressBar();
                getMvpView().commentFinishedLoading();
                if (pageNumber == 1 && commentResponsePojo.getNumFound() > 10) {
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
                    if (commentResponsePojo.getCommentList().size() < 10) {
                        mBaseResponseList.remove(1);
                        getMvpView().setHasMoreComments(false);
                    }
                    smoothScrollToBottom = false;
                } else {
                    smoothScrollToBottom = true;
                    if (mUserPostObj.getIsEditOrDelete() == 1) {
                        mUserPostObj.setIsEditOrDelete(0);
                        mBaseResponseList.set(0, mUserPostObj);
                        getMvpView().editLastComment();
                    }
                    if (mUserPostObj.getIsEditOrDelete() == 2) {
                        mUserPostObj.setIsEditOrDelete(0);
                        mBaseResponseList.set(0, mUserPostObj);
                        getMvpView().deleteLastComment();
                    }
                }
                if (smoothScrollToBottom) {
                    getMvpView().smoothScrollToBottom();
                }
                pageNumber++;
            }
        });
        registerSubscription(subscription);
    }

    public void editTopPost(final CommunityTopPostRequest communityTopPostRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = editPostCommunity(communityTopPostRequest).subscribe(new Subscriber<CreateCommunityResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                getMvpView().stopProgressBar();
                getMvpView().setData(0, communityPostCreateResponse.getFeedDetail());
            }

        });
        registerSubscription(subscription);
    }

    public Observable<CreateCommunityResponse> editPostCommunity(CommunityTopPostRequest communityPostCreateRequest){
        return sheroesAppServiceApi.topPostCommunityPost(communityPostCreateRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {
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
        Subscription subscription = getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

            }


            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                if (null != feedResponsePojo) {
                    mUserPostObj = (UserPostSolrObj) feedResponsePojo.getFeedDetails().get(0);
                    mBaseResponseList.add(mUserPostObj);
                    getMvpView().addData(0, mUserPostObj);
                    headerCount++;
                    getAllCommentFromPresenter(getCommentRequestBuilder(mUserPostObj.getEntityOrParticipantId(), pageNumber));
                }
            }
        });
        registerSubscription(subscription);
    }


    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<CommentReactionResponsePojo> getAllCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return sheroesAppServiceApi.getCommentFromApi(commentReactionRequestPojo)
                .map(new Func1<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                    @Override
                    public CommentReactionResponsePojo call(CommentReactionResponsePojo commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addComment(String commentText, boolean isAnonymous) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        CommentReactionRequestPojo commentReactionRequestPojo = postCommentRequestBuilder(mUserPostObj.getEntityOrParticipantId(), commentText, isAnonymous);
        Subscription subscription = addCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentAddDelete>() {
            @Override
            public void onCompleted() {
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
                    mUserPostObj.setNoOfComments(mUserPostObj.getNoOfComments() + 1);
                    mBaseResponseList.set(0, mUserPostObj);
                    getMvpView().setData(0, mUserPostObj);
                    getMvpView().addData(comment);
                    getMvpView().smoothScrollToBottom();
                    HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .id(Long.toString(commentResponsePojo.getCommentReactionModel().getId()))
                                    .postId(Long.toString(commentResponsePojo.getCommentReactionModel().getEntityId()))
                                    .postType(AnalyticsEventType.COMMUNITY.toString())
                                    .body(commentResponsePojo.getCommentReactionModel().getComment())
                                    .build();
                    AnalyticsManager.trackEvent(Event.REPLY_CREATED, PostDetailActivity.SCREEN_LABEL, properties);
                }
            }
        });
        registerSubscription(subscription);
    }

    public Observable<CommentAddDelete> addCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return sheroesAppServiceApi.addCommentFromApi(commentReactionRequestPojo)
                .map(new Func1<CommentAddDelete, CommentAddDelete>() {
                    @Override
                    public CommentAddDelete call(CommentAddDelete commentReactionResponsePojo) {
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
        Subscription subscription = editCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentAddDelete>() {
            @Override
            public void onCompleted() {
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

                    int pos = findCommentPositionById(mBaseResponseList, commentReactionRequestPojo.getParticipationId());
                    if (pos != RecyclerView.NO_POSITION) {
                        mBaseResponseList.remove(pos);
                        getMvpView().removeData(pos);

                        mUserPostObj.setNoOfComments(mUserPostObj.getNoOfComments() - 1);
                        mBaseResponseList.set(0, mUserPostObj);
                        getMvpView().setData(0, mUserPostObj);
                    }
                }
            }
        });
        registerSubscription(subscription);
    }

    public Observable<CommentAddDelete> editCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return sheroesAppServiceApi.editCommentFromApi(commentReactionRequestPojo)
                .map(new Func1<CommentAddDelete, CommentAddDelete>() {
                    @Override
                    public CommentAddDelete call(CommentAddDelete commentReactionResponsePojo) {
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
                if (comment != null && comment.getId() == id) {
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
        Subscription subscription = getUnLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                comment.isLiked = true;
                comment.likeCount++;
                getMvpView().setData(pos, comment);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus() == AppConstants.FAILED) {
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
                                .build();
                AnalyticsManager.trackEvent(Event.REPLY_UNLIKED, PostDetailActivity.SCREEN_LABEL, properties);
            }
        });
        registerSubscription(subscription);
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
        Subscription subscription = getLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                comment.isLiked = false;
                comment.likeCount--;
                getMvpView().setData(pos, comment);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus() == AppConstants.FAILED) {
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
                                .build();
                AnalyticsManager.trackEvent(Event.REPLY_LIKED, PostDetailActivity.SCREEN_LABEL, properties);
            }
        });
        registerSubscription(subscription);
    }

    public void deleteCommunityPostFromPresenter(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = deleteCommunityPostFromModel(deleteCommunityPostRequest).subscribe(new Subscriber<DeleteCommunityPostResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

            }

            @Override
            public void onNext(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                getMvpView().stopProgressBar();
                getMvpView().onPostDeleted();
            }
        });
        registerSubscription(subscription);
    }

    public Observable<DeleteCommunityPostResponse> deleteCommunityPostFromModel(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        return sheroesAppServiceApi.getCommunityPostDeleteResponse(deleteCommunityPostRequest)
                .map(new Func1<DeleteCommunityPostResponse, DeleteCommunityPostResponse>() {
                    @Override
                    public DeleteCommunityPostResponse call(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                        return deleteCommunityPostResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void getPostLikesFromPresenter(LikeRequestPojo likeRequestPojo, final UserPostSolrObj userPostSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            userPostSolrObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            userPostSolrObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            mBaseResponseList.set(0, userPostSolrObj);
            getMvpView().setData(0, userPostSolrObj);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                userPostSolrObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                userPostSolrObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                mBaseResponseList.set(0, userPostSolrObj);
                getMvpView().setData(0, userPostSolrObj);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus() == AppConstants.FAILED) {
                    userPostSolrObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                    userPostSolrObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                    mBaseResponseList.set(0, userPostSolrObj);
                    getMvpView().setData(0, userPostSolrObj);
                }
                AnalyticsManager.trackPostAction(Event.POST_LIKED, userPostSolrObj, PostDetailActivity.SCREEN_LABEL);
                getMvpView().setData(0, userPostSolrObj);
            }
        });
        registerSubscription(subscription);
    }

    public void getPostUnLikesFromPresenter(LikeRequestPojo likeRequestPojo, final UserPostSolrObj userPostSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            userPostSolrObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            userPostSolrObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            mBaseResponseList.set(0, userPostSolrObj);
            getMvpView().setData(0, userPostSolrObj);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getUnLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                userPostSolrObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                userPostSolrObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mBaseResponseList.set(0, userPostSolrObj);
                getMvpView().setData(0, userPostSolrObj);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if (likeResponse.getStatus() == AppConstants.FAILED) {
                    userPostSolrObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                    userPostSolrObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                    mBaseResponseList.set(0, userPostSolrObj);
                }
                AnalyticsManager.trackPostAction(Event.POST_UNLIKED, userPostSolrObj, PostDetailActivity.SCREEN_LABEL);
                getMvpView().setData(0, userPostSolrObj);
            }
        });
        registerSubscription(subscription);
    }

    public Observable<LikeResponse> getLikesFromModel(LikeRequestPojo likeRequestPojo) {
        return sheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                .map(new Func1<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse call(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LikeResponse> getUnLikesFromModel(LikeRequestPojo likeRequestPojo) {
        return sheroesAppServiceApi.getUnLikesFromApi(likeRequestPojo)
                .map(new Func1<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse call(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getSpamPostApproveFromPresenter(final ApproveSpamPostRequest approveSpamPostRequest, final UserPostSolrObj userPostSolrObj) {
        getMvpView().startProgressBar();
        final Subscription subscription = getSpamPostApproveFromModel(approveSpamPostRequest).subscribe(new Subscriber<ApproveSpamPostResponse>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
            }

            @Override
            public void onNext(ApproveSpamPostResponse approveSpamPostResponse) {
                getMvpView().stopProgressBar();
                if (null != approveSpamPostResponse) {
                    if(approveSpamPostRequest.isApproved() == false && approveSpamPostRequest.isSpam() == true){
                        // spam post was rejected
                        getMvpView().onPostDeleted();
                    }else if(approveSpamPostRequest.isApproved() == true && approveSpamPostRequest.isSpam() == false){
                        // spam post was approved
                        userPostSolrObj.setSpamPost(false);
                        mBaseResponseList.set(0, userPostSolrObj);
                        getMvpView().setData(0, userPostSolrObj);

                    }
                    //getMvpView().getNotificationReadCountSuccess(approveSpamPostResponse,SPAM_POST_APPROVE);
                }
            }
        });
        registerSubscription(subscription);
    }

    public Observable<ApproveSpamPostResponse> getSpamPostApproveFromModel(ApproveSpamPostRequest approveSpamPostRequest) {
        return sheroesAppServiceApi.spamPostApprove(approveSpamPostRequest)
                .map(new Func1<ApproveSpamPostResponse, ApproveSpamPostResponse>() {
                    @Override
                    public ApproveSpamPostResponse call(ApproveSpamPostResponse approveSpamPostResponse) {
                        return approveSpamPostResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public UserPostSolrObj getUserPostObj() {
        if (mUserPostObj == null) {
            return null;
        }
        UserPostSolrObj userPostSolrObj = mUserPostObj;
        List<Comment> comments = new ArrayList<>();
        if (!mBaseResponseList.isEmpty()) {
            if (mBaseResponseList.get(mBaseResponseList.size() - 1) instanceof Comment) {
                comments.add((Comment) mBaseResponseList.get(mBaseResponseList.size() - 1));
            }
        }
        userPostSolrObj.setLastComments(comments);
        return userPostSolrObj;
    }

    public void updateUserPost(UserPostSolrObj userPostSolrObj) {
        mUserPostObj = userPostSolrObj;
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

    //endregion
}
