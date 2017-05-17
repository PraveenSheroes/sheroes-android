package appliedlife.pvtltd.SHEROES.presenters;


import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.RecentSearchDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.Tracking.GoogleAnalyticsTracing;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOK_MARK_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_SEARCH_DATA;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.GCM_ID;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.MARK_AS_SPAM;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.NOTIFICATION_COUNT;

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
    public void getNewGCMidFromPresenter(LoginRequest loginRequest) {
        Subscription subscription = mHomeModel.getNewGCMidFromModel(loginRequest).subscribe(new Subscriber<GcmIdResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            //    getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.GCM_ID).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(GcmIdResponse gcmIdResponse) {
                if (null != gcmIdResponse) {
                    getMvpView().getNotificationReadCountSuccess(gcmIdResponse,GCM_ID);
                }
            }
        });
        registerSubscription(subscription);
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
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.SHEROES_AUTH_TOKEN).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
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
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.FEED_SCREEN).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (null != feedResponsePojo) {
                    getMvpView().getFeedListSuccess(feedResponsePojo);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getMyCommunityFromPresenter(final MyCommunityRequest myCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MY_COMMUNITIES);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getMyCommunityFromModel(myCommunityRequest).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_MY_COMMUNITIES);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.MY_COMMUNITIES_FRAGMENT).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOK_MARK_LIST);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.BOOKMARKS).append(AppConstants.FRAGMENT).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.REACTION_ON_CARD).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(likeResponse, LIKE_UNLIKE);
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.REACTION_ON_CARD).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(likeResponse, LIKE_UNLIKE);
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.BOOKMARKS).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(bookmarkResponsePojo, BOOKMARK_UNBOOKMARK);
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.MY_COMMUNITIES_FRAGMENT).append(mSheroesApplication.getString(R.string.ID_JOIN)).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(CommunityResponse communityResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(communityResponse, JOIN_INVITE);
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.COMMUNITY_POST_FRAGMENT).append(mSheroesApplication.getString(R.string.ID_DELETE)).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(DeleteCommunityPostResponse deleteCommunityPostResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(deleteCommunityPostResponse, DELETE_COMMUNITY_POST);
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.FEED_SCREEN).append(mSheroesApplication.getString(R.string.ID_REPORTED_AS_SPAM)).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo1) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(bookmarkResponsePojo1, MARK_AS_SPAM);
            }
        });
        registerSubscription(subscription);
    }

    public void getBellNotificationFromPresenter(BellNotificationRequest bellNotificationRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getNotificationFromModel(bellNotificationRequest).subscribe(new Subscriber<BelNotificationListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_SEARCH_DATA);
                if(null!=e&&StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(mSheroesApplication.getString(R.string.ID_NOTIFICATION)).append(AppConstants.SPACE).append( e.getMessage());
                    GoogleAnalyticsTracing.screenNameTracking(mSheroesApplication,stringBuilder.toString());
                }
            }

            @Override
            public void onNext(BelNotificationListResponse bellNotificationResponse) {
                getMvpView().stopProgressBar();
                if (null != bellNotificationResponse) {
                    getMvpView().getNotificationListSuccess(bellNotificationResponse);
                }
            }
        });
        registerSubscription(subscription);
    }
    public void getNotificationCountFromPresenter(NotificationReadCount notificationReadCount) {
        Subscription subscription = mHomeModel.getNotificationReadCountFromModel(notificationReadCount).subscribe(new Subscriber<NotificationReadCountResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(NotificationReadCountResponse notificationReadCountResponse) {
                if (null != notificationReadCountResponse) {
                    getMvpView().getNotificationReadCountSuccess(notificationReadCountResponse,NOTIFICATION_COUNT);
                }
            }
        });
        registerSubscription(subscription);
    }


    public void saveMasterDataTypes(List<RecentSearchData> recentSearchData) {
        Subscription subscribe = mRecentSearchDataModel.saveRecentSearchTypes(recentSearchData).subscribe(new Subscriber<List<RecentSearchData>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_SEARCH_DATA);
            }

            @Override
            public void onNext(List<RecentSearchData> masterDatas) {
                getMvpView().stopProgressBar();
            }
        });
        registerSubscription(subscribe);
    }


    public void fetchMasterDataTypes() {
        Subscription subscribe = mRecentSearchDataModel.getAllRecentSearch().subscribe(new Subscriber<List<RecentSearchData>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_SEARCH_DATA);
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
