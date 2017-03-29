package appliedlife.pvtltd.SHEROES.presenters;


import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.RecentSearchDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOK_MARK_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_SEARCH_DATA;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.MARK_AS_SPAM;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Hotel presenter perform required response data for Home activity.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    HomeModel mHomeModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    RecentSearchDataModel mRecentSearchDataModel;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    MasterDataModel mMasterDataModel;

    @Inject
    public HomePresenter(MasterDataModel masterDataModel, HomeModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, RecentSearchDataModel recentSearchDataModel, Preference<MasterDataResponse> mUserPreferenceMasterData) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
        this.mRecentSearchDataModel = recentSearchDataModel;
        this.mMasterDataModel = masterDataModel;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;

    }

    public void getMasterDataToPresenter() {
        super.getMasterDataToAllPresenter(mSheroesApplication, mMasterDataModel, mUserPreferenceMasterData);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    public void getAuthTokenRefreshPresenter() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getAuthTokenRefreshFromModel().subscribe(new Subscriber<LoginResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getLogInResponse(loginResponse);
            }
        });
        registerSubscription(subscription);
    }

    public void getFeedFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                LogUtils.info(TAG, "****************complete***");
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.info(TAG, "*******************" + e.getMessage());
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                LogUtils.info(TAG, "*******************" + new Gson().toJson(feedResponsePojo));
                getMvpView().stopProgressBar();
                if (null != feedResponsePojo) {
                    getMvpView().getFeedListSuccess(feedResponsePojo);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getMyCommunityFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MY_COMMUNITIES);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getMyCommunityFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.info(TAG, "*******************" + e.getMessage());
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_MY_COMMUNITIES);
            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                LogUtils.info(TAG, "*******************" + new Gson().toJson(feedResponsePojo));
                getMvpView().stopProgressBar();
                if (null != feedResponsePojo ) {
                    getMvpView().getFeedListSuccess(feedResponsePojo);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getTagFromPresenter(final GetAllDataRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getTagFromModel(getAllDataRequest).subscribe(new Subscriber<GetTagData>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_TAG);
            }

            @Override
            public void onNext(GetTagData getAllData) {
                getMvpView().stopProgressBar();
                if (null != getAllData && StringUtil.isNotEmptyCollection(getAllData.getDocs()) && getAllData.getDocs().size() > AppConstants.ONE_CONSTANT) {
                    getMvpView().getTagListSuccess(getAllData.getDocs());
                } else {
                    getMvpView().getTagListSuccess(getAllData.getDocs());
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getBookMarkFromPresenter(FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOK_MARK_LIST);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getBookMarkFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_BOOK_MARK_LIST);
            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                getMvpView().stopProgressBar();
                if (null != feedResponsePojo) {
                    getMvpView().getFeedListSuccess(feedResponsePojo);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getLikesFromPresenter(LikeRequestPojo likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(e.getMessage(), ERROR_LIKE_UNLIKE);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(likeResponse.getStatus(), LIKE_UNLIKE);
            }
        });
        registerSubscription(subscription);
    }

    public void getUnLikesFromPresenter(LikeRequestPojo likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getUnLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(e.getMessage(), ERROR_LIKE_UNLIKE);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(likeResponse.getStatus(), LIKE_UNLIKE);
            }
        });
        registerSubscription(subscription);
    }

    public void addBookMarkFromPresenter(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.addBookmarkFromModel(bookmarkRequestPojo, isBookmarked).subscribe(new Subscriber<BookmarkResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(e.getMessage(), ERROR_BOOKMARK_UNBOOKMARK);
            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(bookmarkResponsePojo.getStatus(), BOOKMARK_UNBOOKMARK);
            }
        });
        registerSubscription(subscription);
    }


    public void editCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.editCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(commentResponsePojo.getStatus(), COMMENT_REACTION);
            }
        });
        registerSubscription(subscription);
    }

    public void communityJoinFromPresenter(CommunityRequest communityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.communityJoinFromModel(communityRequest).subscribe(new Subscriber<CommunityResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
            }

            @Override
            public void onNext(CommunityResponse communityResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(communityResponse.getStatus(), JOIN_INVITE);
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
        Subscription subscription = mHomeModel.deleteCommunityPostFromModel(deleteCommunityPostRequest).subscribe(new Subscriber<DeleteCommunityPostResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(deleteCommunityPostResponse.getStatus(), DELETE_COMMUNITY_POST);
            }
        });
        registerSubscription(subscription);
    }
    public void markAsSpamFromPresenter(BookmarkRequestPojo bookmarkResponsePojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.markAsSpamFromModel(bookmarkResponsePojo).subscribe(new Subscriber<BookmarkResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo1) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(bookmarkResponsePojo1.getStatus(), MARK_AS_SPAM);
            }
        });
        registerSubscription(subscription);
    }

    public void communityOwnerAdd(CommunityRequest communityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.ERROR_APP_CLOSE, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.communityOwnerFromModel(communityRequest).subscribe(new Subscriber<CommunityResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(AppConstants.ERROR_APP_CLOSE, ERROR_COMMUNITY_OWNER);
            }

            @Override
            public void onNext(CommunityResponse communityResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(communityResponse.getStatus(), COMMUNITY_OWNER);
            }
        });
        registerSubscription(subscription);
    }

    public void saveMasterDataTypes(List<RecentSearchData> recentSearchData) {
        mRecentSearchDataModel.saveRecentSearchTypes(recentSearchData);
    }


    public void fetchMasterDataTypes() {
        Subscription subscribe = mRecentSearchDataModel.getAllRecentSearch().subscribe(new Subscriber<List<RecentSearchData>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_SEARCH_DATA);
            }

            @Override
            public void onNext(List<RecentSearchData> masterDatas) {
                getMvpView().stopProgressBar();
                getMvpView().getDB(masterDatas);
            }
        });
        registerSubscription(subscribe);
    }

    public void onStop() {
        detachView();
    }
}
