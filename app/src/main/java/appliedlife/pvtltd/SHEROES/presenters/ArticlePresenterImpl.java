package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateStoryActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;

/**
 * Created by avinash on 28/01/16.
 */
public class ArticlePresenterImpl extends BasePresenter<IArticleView> {
    SheroesAppServiceApi sheroesAppServiceApi;

    //region Constructor

    @Inject
    public ArticlePresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    //endregion

    //region IArticlePresenter methods
    public void fetchArticle(final FeedRequestPojo feedRequestPojo, final boolean isImageLoaded, final boolean isUserStory) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        getFeedFromModel(feedRequestPojo, isUserStory)
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), null);

                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        FeedDetail feedDetail = feedResponsePojo.getFeedDetails().get(0);
                        ArticleSolrObj articleObj = new ArticleSolrObj();
                        if (feedDetail instanceof ArticleSolrObj) {
                            articleObj = (ArticleSolrObj) feedDetail;
                            articleObj.isLiked = articleObj.getReactionValue() == AppConstants.HEART_REACTION_CONSTANT;
                            articleObj.likesCount = articleObj.getNoOfLikes();
                            if (CommonUtil.isNotEmpty(getMvpView().getStreamType())) {
                                articleObj.setStreamType(getMvpView().getStreamType());
                                feedDetail.setStreamType(getMvpView().getStreamType());
                            }
                            getMvpView().setFeedDetail(feedDetail);
                            getMvpView().showArticle(articleObj, isImageLoaded);
                            getMvpView().showComments(feedDetail.getLastComments(), articleObj.getNoOfComments());
                        }
                    }
                });

    }


    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo, boolean isUserStory) {
        if (isUserStory) {
            return sheroesAppServiceApi.getUserStory(String.valueOf(feedRequestPojo.getIdForFeedDetail()), feedRequestPojo);
        } else {
            return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo);
        }
    }

    public void onDeleteCommentClicked(final int position, CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        editCommentListFromModel(commentReactionRequestPojo)
                .compose(this.<CommentAddDelete>bindToLifecycle())
                .subscribe(new DisposableObserver<CommentAddDelete>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_UNABLE_TO_EDIT_DELETE), ERROR_COMMENT_REACTION);
                    }

                    @Override
                    public void onNext(CommentAddDelete commentResponsePojo) {

                        if (null != commentResponsePojo) {
                            getMvpView().removeAndNotifyComment(position);
                            getMvpView().showMessage(R.string.comment_deleted);
                            HashMap<String, Object> properties =
                                    new EventProperty.Builder()
                                            .id(Long.toString(commentResponsePojo.getCommentReactionModel().getId()))
                                            .postId(Long.toString(commentResponsePojo.getCommentReactionModel().getEntityId()))
                                            .postType(AnalyticsEventType.ARTICLE.toString())
                                            .body(commentResponsePojo.getCommentReactionModel().getComment())
                                            .streamType(getMvpView().getStreamType())
                                            .build();
                            AnalyticsManager.trackEvent(Event.REPLY_DELETED, ArticleActivity.SOURCE_SCREEN, properties);
                        }
                    }
                });

    }

    public void onEditComment(final int position, CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        //  getMvpView().startProgressBar();
        editCommentListFromModel(commentReactionRequestPojo)
                .compose(this.<CommentAddDelete>bindToLifecycle())
                .subscribe(new DisposableObserver<CommentAddDelete>() {
                    @Override
                    public void onComplete() {
                        //getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // getMvpView().stopProgressBar();
                        getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_UNABLE_TO_EDIT_DELETE), ERROR_COMMENT_REACTION);
                    }

                    @Override
                    public void onNext(CommentAddDelete commentResponsePojo) {
                        //  getMvpView().stopProgressBar();
                        if (null != commentResponsePojo) {
                            Comment comment = commentResponsePojo.getCommentReactionModel();
                            getMvpView().setAndNotify(position, comment);
                            HashMap<String, Object> properties =
                                    new EventProperty.Builder()
                                            .id(Long.toString(commentResponsePojo.getCommentReactionModel().getId()))
                                            .postId(Long.toString(commentResponsePojo.getCommentReactionModel().getEntityId()))
                                            .postType(AnalyticsEventType.ARTICLE.toString())
                                            .body(commentResponsePojo.getCommentReactionModel().getComment())
                                            .streamType(getMvpView().getStreamType())
                                            .build();
                            AnalyticsManager.trackEvent(Event.REPLY_EDITED, ArticleActivity.SCREEN_LABEL, properties);
                        }
                    }
                });

    }

    public void postComment(final CommentReactionRequestPojo commentReactionRequestPojo, final ArticleSolrObj articleSolrObj) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        // getMvpView().startProgressBar();
        addCommentListFromModel(commentReactionRequestPojo)
                .compose(this.<CommentAddDelete>bindToLifecycle())
                .subscribe(new DisposableObserver<CommentAddDelete>() {
                    @Override
                    public void onComplete() {
                        // getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_UNABLE_TO_COMMENT), ERROR_COMMENT_REACTION);
                    }

                    @Override
                    public void onNext(CommentAddDelete commentResponsePojo) {
                        //getMvpView().stopProgressBar();
                        if (null != commentResponsePojo) {
                            if (commentResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                                Comment comment = commentResponsePojo.getCommentReactionModel();
                                getMvpView().addAndNotifyComment(comment);
                                if (articleSolrObj.isUserStory()) {
                                    HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, CreateStoryActivity.SCREEN_LABEL);
                                    AnalyticsManager.trackEvent(Event.STORY_REPLY_CREATED, CreateStoryActivity.SCREEN_LABEL, properties);
                                } else {
                                    HashMap<String, Object> properties =
                                            new EventProperty.Builder()
                                                    .id(Long.toString(commentResponsePojo.getCommentReactionModel().getId()))
                                                    .postId(Long.toString(commentResponsePojo.getCommentReactionModel().getEntityId()))
                                                    .postType(AnalyticsEventType.ARTICLE.toString())
                                                    .body(commentResponsePojo.getCommentReactionModel().getComment())
                                                    .streamType(getMvpView().getStreamType())
                                                    .build();
                                    AnalyticsManager.trackEvent(Event.REPLY_CREATED, ArticleActivity.SCREEN_LABEL, properties);

                                }
                            }
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

    public void prepareBookmark(ArticleSolrObj articleSolrObj) {
        if (!articleSolrObj.isBookmarked()) {
            articleSolrObj.setBookmarked(true);
            getMvpView().invalidateBookmark(articleSolrObj);
            postBookMark(articleSolrObj, AppUtils.getInstance().bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), false);
        } else {
            articleSolrObj.setBookmarked(false);
            getMvpView().invalidateBookmark(articleSolrObj);
            postBookMark(articleSolrObj, AppUtils.getInstance().bookMarkRequestBuilder(articleSolrObj.getEntityOrParticipantId()), true);
        }
    }

    public void prepareLike(ArticleSolrObj articleSolrObj) {
        if (!articleSolrObj.isLiked) {
            articleSolrObj.isLiked = true;
            articleSolrObj.likesCount++;
            getMvpView().invalidateLike(articleSolrObj);
            postLike(articleSolrObj, AppUtils.getInstance().likeRequestBuilder(articleSolrObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), false);
        } else {
            articleSolrObj.isLiked = false;
            articleSolrObj.likesCount--;
            getMvpView().invalidateLike(articleSolrObj);
            postLike(articleSolrObj, AppUtils.getInstance().likeRequestBuilder(articleSolrObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), true);
        }
    }

    private void postBookMark(final ArticleSolrObj articleSolrObj, BookmarkRequestPojo bookmarkRequestPojo, final boolean isBookMarked) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            articleSolrObj.setBookmarked(isBookMarked);
            getMvpView().invalidateBookmark(articleSolrObj);
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            return;
        }
        addBookmarkFromModel(bookmarkRequestPojo, isBookMarked)
                .compose(this.<BookmarkResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<BookmarkResponsePojo>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        articleSolrObj.setBookmarked(isBookMarked);
                        getMvpView().invalidateBookmark(articleSolrObj);
                        getMvpView().showError(e.getMessage(), ERROR_BOOKMARK_UNBOOKMARK);

                    }

                    @Override
                    public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                        if (bookmarkResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().invalidateBookmark(articleSolrObj);
                            if (articleSolrObj.isBookmarked()) {
                                getMvpView().trackEvent(Event.POST_BOOKMARKED);
                            } else {
                                getMvpView().trackEvent(Event.POST_UNBOOKMARKED);
                            }
                        } else {
                            articleSolrObj.setBookmarked(isBookMarked);
                            getMvpView().invalidateBookmark(articleSolrObj);
                        }
                        getMvpView().invalidateBookmark(articleSolrObj);
                    }
                });

    }

    private void postLike(final ArticleSolrObj articleSolrObj, LikeRequestPojo likeRequestPojo, final boolean isLiked) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            if (!isLiked) {
                articleSolrObj.isLiked = false;
                articleSolrObj.likesCount--;
            } else {
                articleSolrObj.isLiked = true;
                articleSolrObj.likesCount++;
            }
            getMvpView().invalidateLike(articleSolrObj);
            return;
        }
        //  getMvpView().startProgressBar();
        addLikeFromModel(likeRequestPojo, isLiked)
                .compose(this.<LikeResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LikeResponse>() {
                    @Override
                    public void onComplete() {
                        //  getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        //   getMvpView().stopProgressBar();
                        if (!isLiked) {
                            articleSolrObj.isLiked = false;
                            articleSolrObj.likesCount--;
                        } else {
                            articleSolrObj.isLiked = true;
                            articleSolrObj.likesCount++;
                        }
                        getMvpView().invalidateLike(articleSolrObj);
                        getMvpView().showError(e.getMessage(), ERROR_BOOKMARK_UNBOOKMARK);

                    }

                    @Override
                    public void onNext(LikeResponse likeResponse) {
                        if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().invalidateLike(articleSolrObj);
                            if (articleSolrObj.isLiked) {
                                getMvpView().trackEvent(Event.POST_LIKED);
                            } else {
                                getMvpView().trackEvent(Event.POST_UNLIKED);
                            }
                        } else {
                            if (!isLiked) {
                                articleSolrObj.isLiked = false;
                                articleSolrObj.likesCount--;
                            } else {
                                articleSolrObj.isLiked = true;
                                articleSolrObj.likesCount++;
                            }
                            getMvpView().invalidateLike(articleSolrObj);
                        }
                    }
                });

    }

    public int getBookmarkDrawable(ArticleSolrObj articleSolrObj) {
        if (articleSolrObj != null) {
            return articleSolrObj.getBookmarkActivityDrawable();
        } else {
            return R.drawable.vector_unbookmarked;
        }
    }

    public int getLikeDrawable(ArticleSolrObj articleSolrObj) {
        if (articleSolrObj != null) {
            return articleSolrObj.getLikeActivityDrawable();
        } else {
            return R.drawable.vector_unlike;
        }
    }

    public boolean getMenuItemsVisibility(ArticleSolrObj articleSolrObj) {
        return articleSolrObj != null;
    }

    public void fetchAllComments(CommentReactionRequestPojo commentRequestBuilder) {
        getAllCommentListFromModel(commentRequestBuilder)
                .compose(this.<CommentReactionResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<CommentReactionResponsePojo>() {
                    @Override
                    public void onComplete() {
                        // getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        //   getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_COMMENT_REACTION);
                    }

                    @Override
                    public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                        //     getMvpView().stopProgressBar();
                        getMvpView().showComments(new ArrayList<Comment>(commentResponsePojo.getCommentList()), commentResponsePojo.getCommentList().size());
                    }
                });

    }

    public Observable<CommentReactionResponsePojo> getAllCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
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

    //endregion


    public Observable<BookmarkResponsePojo> addBookmarkFromModel(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
        //LogUtils.info(TAG, "*******************" + new Gson().toJson(bookmarkRequestPojo));
        if (!isBookmarked) {
            return sheroesAppServiceApi.addBookMarkToApi(bookmarkRequestPojo)
                    .map(new Function<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo apply(BookmarkResponsePojo bookmarkResponsePojo) {
                            return bookmarkResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return sheroesAppServiceApi.UnBookMarkToApi(bookmarkRequestPojo)
                    .map(new Function<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo apply(BookmarkResponsePojo bookmarkResponsePojo) {
                            return bookmarkResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public Observable<LikeResponse> addLikeFromModel(LikeRequestPojo likeRequestPojo, boolean isLiked) {
        //LogUtils.info(TAG, "*******************" + new Gson().toJson(bookmarkRequestPojo));
        if (!isLiked) {
            return sheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                    .map(new Function<LikeResponse, LikeResponse>() {
                        @Override
                        public LikeResponse apply(LikeResponse likeResponse) {
                            return likeResponse;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
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
    }

    public void reportSpamPostOrComment(SpamPostRequest spamPostRequest, final Comment comment, final int position) {
        getMvpView().startProgressBar();

        sheroesAppServiceApi.reportSpamPostOrComment(spamPostRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<SpamResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<SpamResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_COMMENT_REACTION);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(SpamResponse spamResponse) {
                        getMvpView().onSpamPostOrCommentReported(spamResponse, comment, position);
                        getMvpView().stopProgressBar();
                    }
                });
    }

    //Approve/Delete of spam comment only for admin
    public void getSpamCommentApproveOrDeleteByAdmin(final ApproveSpamPostRequest approveSpamPostRequest, final int position, final Comment comment) {
        getMvpView().startProgressBar();
        sheroesAppServiceApi.approveSpamComment(approveSpamPostRequest)
                .compose(this.<SpamResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BaseResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
                    }

                    @Override
                    public void onNext(BaseResponse approveSpamPostResponse) {
                        getMvpView().stopProgressBar();
                        if (null != approveSpamPostResponse) {
                            getMvpView().removeAndNotifyComment(position);
                            getMvpView().showMessage(R.string.comment_deleted);

                            //Event for the article comment deleted by admin
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
                });

    }

}
