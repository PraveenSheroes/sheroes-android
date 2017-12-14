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
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
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
import appliedlife.pvtltd.SHEROES.models.entities.post.Article;
import appliedlife.pvtltd.SHEROES.models.entities.post.UserProfile;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    public void fetchArticle(final FeedRequestPojo feedRequestPojo, final boolean isImageLoaded) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                //getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                //getMvpView().stopProgressBar();
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), null);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                //getMvpView().stopProgressBar();
                // LogUtils.info(TAG, "********response***********");
                FeedDetail feedDetail = feedResponsePojo.getFeedDetails().get(0);
                ArticleSolrObj articleObj = new ArticleSolrObj();
                if(feedDetail instanceof ArticleSolrObj){
                    articleObj = (ArticleSolrObj) feedDetail;
                }
                getMvpView().setFeedDetail(feedDetail);
                if (null != feedResponsePojo) {
                    Article article = new Article();
                    article.createdAt = articleObj.getPostedDate();
                    article.title = articleObj.getNameOrTitle();
                    article.body = articleObj.getListDescription();
                    article.remote_id = (int) articleObj.getEntityOrParticipantId();
                    article.featureImage = articleObj.getImageUrl();
                    article.commentsCount = articleObj.getNoOfComments();
                    article.totalViews = articleObj.getNoOfViews();
                    article.likesCount = articleObj.getNoOfLikes();
                    article.deepLink = articleObj.getDeepLinkUrl();
                    article.author = new UserProfile();
                    article.author.name = articleObj.getAuthorName();
                    article.author.shortDescription = articleObj.getAuthorShortDescription();
                    article.author.thumbUrl = articleObj.getAuthorImageUrl();
                    article.isBookmarked = articleObj.isBookmarked();
                    article.isLiked = articleObj.getReactionValue() == AppConstants.HEART_REACTION_CONSTANT;
                    article.thumbImageWidth = articleObj.getThumbImageWidth();
                    article.thumbImageHeight = articleObj.getThumbImageHeight();
                    article.featureImageHeight = articleObj.getHighresImageHeight();
                    article.featureImageWidth = articleObj.getHighresImageWidth();
                    article.readingTime = articleObj.getCharCount();
                    if (!CommonUtil.isEmpty(feedDetail.getLastComments())) {
                        article.comments = new ArrayList<>(feedDetail.getLastComments());
                    }
                    getMvpView().showArticle(article, isImageLoaded);
                    getMvpView().showComments(article.comments, article.commentsCount);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void fetchArticle(Article article) {
        getMvpView().showArticle(article, false);
    }

    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        //  LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
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

    public void onDeleteCommentClicked(final int position, CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        //  getMvpView().startProgressBar();
        Subscription subscription = editCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentAddDelete>() {
            @Override
            public void onCompleted() {
                // getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                //   getMvpView().stopProgressBar();
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_UNABLE_TO_EDIT_DELETE), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentAddDelete commentResponsePojo) {
                //  getMvpView().stopProgressBar();
                if (null != commentResponsePojo) {
                    getMvpView().removeAndNotifyComment(position);
                    getMvpView().showMessage(R.string.comment_deleted);
                    HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .id(Long.toString(commentResponsePojo.getCommentReactionModel().getId()))
                                    .postId(Long.toString(commentResponsePojo.getCommentReactionModel().getEntityId()))
                                    .postType(AnalyticsEventType.ARTICLE.toString())
                                    .body(commentResponsePojo.getCommentReactionModel().getComment())
                                    .build();
                    AnalyticsManager.trackEvent(Event.REPLY_DELETED,ArticleActivity.SOURCE_SCREEN, properties);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void onEditComment(final int position, CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        //  getMvpView().startProgressBar();
        Subscription subscription = editCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentAddDelete>() {
            @Override
            public void onCompleted() {
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
                                    .build();
                    AnalyticsManager.trackEvent(Event.REPLY_EDITED, ArticleActivity.SCREEN_LABEL, properties);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void postComment(final CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        // getMvpView().startProgressBar();
        Subscription subscription = addCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentAddDelete>() {
            @Override
            public void onCompleted() {
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
                        Comment comment = new Comment();
                        comment = commentResponsePojo.getCommentReactionModel();
                        getMvpView().addAndNotifyComment(comment);
                        HashMap<String, Object> properties =
                                new EventProperty.Builder()
                                        .id(Long.toString(commentResponsePojo.getCommentReactionModel().getId()))
                                        .postId(Long.toString(commentResponsePojo.getCommentReactionModel().getEntityId()))
                                        .postType(AnalyticsEventType.ARTICLE.toString())
                                        .body(commentResponsePojo.getCommentReactionModel().getComment())
                                        .build();
                        AnalyticsManager.trackEvent(Event.REPLY_CREATED, ArticleActivity.SCREEN_LABEL, properties);
                    }
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

    public void prepareBookmark(Article article) {
        if (!article.isBookmarked) {
            article.isBookmarked = true;
            getMvpView().invalidateBookmark(article);
            postBookMark(article, AppUtils.getInstance().bookMarkRequestBuilder(article.remote_id), false);
        } else {
            article.isBookmarked = false;
            getMvpView().invalidateBookmark(article);
            postBookMark(article, AppUtils.getInstance().bookMarkRequestBuilder(article.remote_id), true);
        }
    }

    public void prepareLike(Article article) {
        if (!article.isLiked) {
            article.isLiked = true;
            article.likesCount++;
            getMvpView().invalidateLike(article);
            postLike(article, AppUtils.getInstance().likeRequestBuilder(article.remote_id, AppConstants.HEART_REACTION_CONSTANT), false);
        } else {
            article.isLiked = false;
            article.likesCount--;
            getMvpView().invalidateLike(article);
            postLike(article, AppUtils.getInstance().likeRequestBuilder(article.remote_id, AppConstants.HEART_REACTION_CONSTANT), true);
        }
    }

    private void postBookMark(final Article article, BookmarkRequestPojo bookmarkRequestPojo, final boolean isBookMarked) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            if (!isBookMarked) {
                article.isBookmarked = false;
            } else {
                article.isBookmarked = true;
            }
            getMvpView().invalidateBookmark(article);
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            return;
        }
        Subscription subscription = addBookmarkFromModel(bookmarkRequestPojo, isBookMarked).subscribe(new Subscriber<BookmarkResponsePojo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                if (!isBookMarked) {
                    article.isBookmarked = false;
                } else {
                    article.isBookmarked = true;
                }
                getMvpView().invalidateBookmark(article);
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);

            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                if (bookmarkResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    getMvpView().invalidateBookmark(article);
                    if(article.isBookmarked){
                        getMvpView().trackEvent(Event.POST_BOOKMARKED);
                    }else {
                        getMvpView().trackEvent(Event.POST_UNBOOKMARKED);
                    }
                } else {
                    if (!isBookMarked) {
                        article.isBookmarked = false;
                    } else {
                        article.isBookmarked = true;
                    }
                    getMvpView().invalidateBookmark(article);
                }
                getMvpView().invalidateBookmark(article);
            }
        });
        registerSubscription(subscription);
    }

    private void postLike(final Article article, LikeRequestPojo likeRequestPojo, final boolean isLiked) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            if (!isLiked) {
                article.isLiked = false;
                article.likesCount--;
            } else {
                article.isLiked = true;
                article.likesCount++;
            }
            getMvpView().invalidateLike(article);
            return;
        }
        //  getMvpView().startProgressBar();
        Subscription subscription = addLikeFromModel(likeRequestPojo, isLiked).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                //  getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                //   getMvpView().stopProgressBar();
                if (!isLiked) {
                    article.isLiked = false;
                    article.likesCount--;
                } else {
                    article.isLiked = true;
                    article.likesCount++;
                }
                getMvpView().invalidateLike(article);
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    getMvpView().invalidateLike(article);
                    if (article.isLiked) {
                        getMvpView().trackEvent(Event.POST_LIKED);
                    } else {
                        getMvpView().trackEvent(Event.POST_UNLIKED);
                    }
                } else {
                    if (!isLiked) {
                        article.isLiked = false;
                        article.likesCount--;
                    } else {
                        article.isLiked = true;
                        article.likesCount++;
                    }
                    getMvpView().invalidateLike(article);
                }
            }
        });
        registerSubscription(subscription);
    }

    public int getBookmarkDrawable(Article article) {
        if (article != null) {
            return article.getBookmarkActivityDrawable();
        } else {
            return R.drawable.vector_unbookmarked;
        }
    }

    public int getLikeDrawable(Article article) {
        if (article != null) {
            return article.getLikeActivityDrawable();
        } else {
            return R.drawable.vector_unlike;
        }
    }

    public boolean getMenuItemsVisibility(Article article) {
        if (article != null) {
            return true;
        }
        return false;
    }

    public void fetchAllComments(CommentReactionRequestPojo commentRequestBuilder) {
        Subscription subscription = getAllCommentListFromModel(commentRequestBuilder).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
                // getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                //   getMvpView().stopProgressBar();
                //getMvpView().showError(mSheroesApplication.getString(R.string.ID_SERVER_PROBLEM),ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                //     getMvpView().stopProgressBar();
                getMvpView().showComments(new ArrayList<Comment>(commentResponsePojo.getCommentList()), commentResponsePojo.getCommentList().size());
            }
        });
        registerSubscription(subscription);
    }

    public Observable<CommentReactionResponsePojo> getAllCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
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

    //endregion


    public Observable<BookmarkResponsePojo> addBookmarkFromModel(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
        //LogUtils.info(TAG, "*******************" + new Gson().toJson(bookmarkRequestPojo));
        if (!isBookmarked) {
            return sheroesAppServiceApi.addBookMarkToApi(bookmarkRequestPojo)
                    .map(new Func1<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo call(BookmarkResponsePojo bookmarkResponsePojo) {
                            return bookmarkResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return sheroesAppServiceApi.UnBookMarkToApi(bookmarkRequestPojo)
                    .map(new Func1<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo call(BookmarkResponsePojo bookmarkResponsePojo) {
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
                    .map(new Func1<LikeResponse, LikeResponse>() {
                        @Override
                        public LikeResponse call(LikeResponse likeResponse) {
                            return likeResponse;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
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
    }
}
