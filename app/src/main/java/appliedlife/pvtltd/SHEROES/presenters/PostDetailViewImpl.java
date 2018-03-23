package appliedlife.pvtltd.SHEROES.presenters;


import android.support.v7.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;

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
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
                boolean smoothScrollToBottom = false;
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

    }

    public void editTopPost(final CommunityTopPostRequest communityTopPostRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        editPostCommunity(communityTopPostRequest).subscribe(new DisposableObserver<CreateCommunityResponse>() {

            @Override
            public void onComplete() {

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

    }

    public Observable<CreateCommunityResponse> editPostCommunity(CommunityTopPostRequest communityPostCreateRequest) {
        return sheroesAppServiceApi.topPostCommunityPost(communityPostCreateRequest)
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

            }


            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                if (null != feedResponsePojo && !CommonUtil.isEmpty(feedResponsePojo.getFeedDetails())) {
                    mUserPostObj = (UserPostSolrObj) feedResponsePojo.getFeedDetails().get(0);
                    if(CommonUtil.isNotEmpty(getMvpView().getStreamType())){
                        mUserPostObj.setStreamType(getMvpView().getStreamType());
                    }
                    mBaseResponseList.add(mUserPostObj);
                    getMvpView().addData(0, mUserPostObj);
                    headerCount++;
                    getAllCommentFromPresenter(getCommentRequestBuilder(mUserPostObj.getEntityOrParticipantId(), pageNumber));
                }
            }
        });

    }


    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
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
        return sheroesAppServiceApi.getCommentFromApi(commentReactionRequestPojo)
                .map(new Function<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                    @Override
                    public CommentReactionResponsePojo apply(CommentReactionResponsePojo commentReactionResponsePojo) {
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
        CommentReactionRequestPojo  commentReactionRequestPojo = postCommentRequestBuilder(mUserPostObj.getEntityOrParticipantId(), commentText, isAnonymous);
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
                                    .streamType(CommonUtil.isNotEmpty(mUserPostObj.getStreamType()) ? mUserPostObj.getStreamType() : "")
                                    .communityId(commentResponsePojo.getCommentReactionModel().getCommunityId())
                                    .build();
                    AnalyticsManager.trackEvent(Event.REPLY_CREATED, PostDetailActivity.SCREEN_LABEL, properties);
                }
            }
        });

    }

    public Observable<CommentAddDelete> addCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return sheroesAppServiceApi.addCommentFromApi(commentReactionRequestPojo)
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

    }

    public Observable<CommentAddDelete> editCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return sheroesAppServiceApi.editCommentFromApi(commentReactionRequestPojo)
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
        getUnLikesFromModel(likeRequestPojo).subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
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
                                .communityId(comment.getCommunityId())
                                .streamType((mUserPostObj!=null && CommonUtil.isNotEmpty(mUserPostObj.getStreamType())) ? mUserPostObj.getStreamType(): "")
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
                                .communityId(comment.getCommunityId())
                                .streamType((mUserPostObj!=null && CommonUtil.isNotEmpty(mUserPostObj.getStreamType())) ? mUserPostObj.getStreamType(): "")
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

            }

            @Override
            public void onNext(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                getMvpView().stopProgressBar();
                getMvpView().onPostDeleted();
            }
        });

    }

    public Observable<DeleteCommunityPostResponse> deleteCommunityPostFromModel(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        return sheroesAppServiceApi.getCommunityPostDeleteResponse(deleteCommunityPostRequest)
                .map(new Function<DeleteCommunityPostResponse, DeleteCommunityPostResponse>() {
                    @Override
                    public DeleteCommunityPostResponse apply(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                        return deleteCommunityPostResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void searchUserTagging(SearchUserDataRequest searchUserDataRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            return;
        }
        searchUserDataFromModel(searchUserDataRequest).subscribe(new DisposableObserver<SearchUserDataResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
            }

            @Override
            public void onNext(SearchUserDataResponse searchUserDataResponse) {
                if (!searchUserDataResponse.getStatus().equalsIgnoreCase(AppConstants.INVALID)) {
                    getMvpView().showListOfParticipate(searchUserDataResponse.getParticipantList());
                }
            }
        });

    }

    public Observable<SearchUserDataResponse> searchUserDataFromModel(SearchUserDataRequest searchUserDataRequest) {
        return sheroesAppServiceApi.getUserTaggingResponse(searchUserDataRequest)
                .map(new Function<SearchUserDataResponse, SearchUserDataResponse>() {
                    @Override
                    public SearchUserDataResponse apply(SearchUserDataResponse searchUserDataResponse) {
                        return searchUserDataResponse;
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
        getLikesFromModel(likeRequestPojo).subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
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
        getUnLikesFromModel(likeRequestPojo).subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
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

    }

    public Observable<LikeResponse> getLikesFromModel(LikeRequestPojo likeRequestPojo) {
        return sheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                .map(new Function<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse apply(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LikeResponse> getUnLikesFromModel(LikeRequestPojo likeRequestPojo) {
        return sheroesAppServiceApi.getUnLikesFromApi(likeRequestPojo)
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
            }

            @Override
            public void onNext(ApproveSpamPostResponse approveSpamPostResponse) {
                getMvpView().stopProgressBar();
                if (null != approveSpamPostResponse) {
                    if (approveSpamPostRequest.isApproved() == false && approveSpamPostRequest.isSpam() == true) {
                        // spam post was rejected
                        getMvpView().onPostDeleted();
                    } else if (approveSpamPostRequest.isApproved() == true && approveSpamPostRequest.isSpam() == false) {
                        // spam post was approved
                        userPostSolrObj.setSpamPost(false);
                        mBaseResponseList.set(0, userPostSolrObj);
                        getMvpView().setData(0, userPostSolrObj);

                    }
                    //getMvpView().getNotificationReadCountSuccess(approveSpamPostResponse,SPAM_POST_APPROVE);
                }
            }
        });

    }

    public Observable<ApproveSpamPostResponse> getSpamPostApproveFromModel(ApproveSpamPostRequest approveSpamPostRequest) {
        return sheroesAppServiceApi.spamPostApprove(approveSpamPostRequest)
                .map(new Function<ApproveSpamPostResponse, ApproveSpamPostResponse>() {
                    @Override
                    public ApproveSpamPostResponse apply(ApproveSpamPostResponse approveSpamPostResponse) {
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
        if(CommonUtil.isNotEmpty(getMvpView().getStreamType())){
            mUserPostObj.setStreamType(getMvpView().getStreamType());
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

    //endregion
}
