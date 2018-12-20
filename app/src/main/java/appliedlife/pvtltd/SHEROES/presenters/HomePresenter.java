package appliedlife.pvtltd.SHEROES.presenters;

import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigurationResponse;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.ChampionFollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.vernacular.LanguageUpdateRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOK_MARK_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_SEARCH_DATA;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FCM_ID;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;
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
    //region constant
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    //endregion constant

    //region injected variable
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;
    @Inject
    Preference<AppConfiguration> mConfiguration;
    //endregion injected variable

    //region private member variable
    private HomeModel mHomeModel;
    private MasterDataModel mMasterDataModel;
    private SheroesApplication mSheroesApplication;
    private SheroesAppServiceApi mSheroesAppServiceApi;
    private String mNextToken = "";
    //endregion private member variable

    //region constructor
    @Inject
    public HomePresenter(MasterDataModel masterDataModel, HomeModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData, Preference<AppConfiguration> mConfiguration,SheroesAppServiceApi sheroesAppServiceApi) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
        this.mConfiguration = mConfiguration;
        this.mMasterDataModel = masterDataModel;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;
        this.mSheroesAppServiceApi= sheroesAppServiceApi;
    }
    //endregion constructor

    //region public methods
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

    public void getNewFCMidFromPresenter(LoginRequest loginRequest) {
        mHomeModel.getNewGCMidFromModel(loginRequest)
                .compose(this.<GcmIdResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<GcmIdResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        //    getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);

                    }

                    @Override
                    public void onNext(GcmIdResponse fcmIdResponse) {
                        if (null != fcmIdResponse) {
                            getMvpView().getNotificationReadCountSuccess(fcmIdResponse, FCM_ID);
                        }
                    }
                });

    }

    public void getFeedFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getFeedFromModel(feedRequestPojo)
                .compose(this.<FeedResponsePojo>bindToLifecycle())
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
                        LogUtils.info(TAG, "********response***********");
                        getMvpView().stopProgressBar();
                        if (null != feedResponsePojo) {
                            getMvpView().getFeedListSuccess(feedResponsePojo);
                        }
                    }
                });
    }

    public void getArticleFeeds(String searchText, String searchCategory, boolean pullToRefresh, boolean initialCall) {
        if (initialCall) {
            mNextToken = "";
        }

        String URL = "participant/search/?search_text=" + searchText + "&search_category=" + searchCategory;

        if (!pullToRefresh && mNextToken != null) {
            URL = URL + "&next_token=" + mNextToken;
        }
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        mSheroesAppServiceApi.getSearchResponse(URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().stopProgressBar();
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        getMvpView().stopProgressBar();

                        if (null != feedResponsePojo) {
                            if (feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                                if (feedResponsePojo.getFeedDetails() != null && feedResponsePojo.getFeedDetails().size() > 0) {
                                    getMvpView().getFeedListSuccess(feedResponsePojo);
                                    mNextToken = feedResponsePojo.getNextToken();
                                } else if (feedResponsePojo.getFieldErrorMessageMap() != null) {
                                    if (feedResponsePojo.getFieldErrorMessageMap().containsKey("info")) {
                                        getMvpView().showEmptyScreen(feedResponsePojo.getFieldErrorMessageMap().get("info"));
                                    } else {
                                        getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                                    }
                                }
                            } else if (feedResponsePojo.getFieldErrorMessageMap() != null) {
                                if (feedResponsePojo.getFieldErrorMessageMap().containsKey("info")) {
                                    getMvpView().showEmptyScreen(feedResponsePojo.getFieldErrorMessageMap().get("info"));
                                } else {
                                    getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                                }
                            }
                        }
                    }
                });
    }

    public void getAllCommunities(final MyCommunityRequest myCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MY_COMMUNITIES);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getAllCommunityFromModel(myCommunityRequest)
                .compose(this.<AllCommunitiesResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<AllCommunitiesResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_MY_COMMUNITIES);

                    }

                    @Override
                    public void onNext(AllCommunitiesResponse allCommunitiesResponse) {
                        getMvpView().stopProgressBar();
                        if (null != allCommunitiesResponse) {
                            mAllCommunities.set(allCommunitiesResponse);
                        }
                    }
                });
    }

    public void getBookMarkFromPresenter(FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOK_MARK_LIST);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getBookMarkFromModel(feedRequestPojo)
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
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

    }

    public void getFollowFromPresenter(PublicProfileListRequest publicProfileListRequest, final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getFollowFromModel(publicProfileListRequest)
                .compose(this.<ChampionFollowedResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ChampionFollowedResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                        userSolrObj.setSolrIgnoreIsUserFollowed(false);
                        userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse championFollowedResponse) {
                        getMvpView().stopProgressBar();
                        if (championFollowedResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            userSolrObj.setFollowerCount(userSolrObj.getFollowerCount() + 1);
                            userSolrObj.setSolrIgnoreIsUserFollowed(true);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                        } else {
                            if(championFollowedResponse.isAlreadyFollowed()) {
                                userSolrObj.setSolrIgnoreIsUserFollowed(true);
                                userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                            } else {
                                userSolrObj.setSolrIgnoreIsUserFollowed(false);
                                userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                            }
                        }
                        getMvpView().getSuccessForAllResponse(userSolrObj, FOLLOW_UNFOLLOW);
                    }
         });
    }

    public void getUnFollowFromPresenter(PublicProfileListRequest publicProfileListRequest, final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getUnFollowFromModel(publicProfileListRequest)
                .compose(this.<ChampionFollowedResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ChampionFollowedResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse championFollowedResponse) {
                        getMvpView().stopProgressBar();
                        if (championFollowedResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (userSolrObj.getFollowerCount() > 0) {
                                userSolrObj.setFollowerCount(userSolrObj.getFollowerCount() - 1);
                            }
                            userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                            userSolrObj.setSolrIgnoreIsUserFollowed(false);
                        } else {
                            userSolrObj.setSolrIgnoreIsUserFollowed(true);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                        }
                        getMvpView().getSuccessForAllResponse(userSolrObj, FOLLOW_UNFOLLOW);
                    }
                });
    }

    public void getUserSummaryDetails(UserSummaryRequest userSummaryRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getPersonalUserSummaryDetailsAuthToken(userSummaryRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BoardingDataResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BoardingDataResponse>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                if (null != boardingDataResponse) {
                    getMvpView().getUserSummaryResponse(boardingDataResponse);
                }
            }
        });
    }

    public void addBookMarkFromPresenter(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.addBookmarkFromModel(bookmarkRequestPojo, isBookmarked)
                .compose(this.<BookmarkResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<BookmarkResponsePojo>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_BOOKMARK_UNBOOKMARK);

                    }

                    @Override
                    public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                        getMvpView().stopProgressBar();
                        getMvpView().getSuccessForAllResponse(bookmarkResponsePojo, BOOKMARK_UNBOOKMARK);
                    }
                });

    }

    public void deleteCommunityPostFromPresenter(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.deleteCommunityPostFromModel(deleteCommunityPostRequest)
                .compose(this.<DeleteCommunityPostResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<DeleteCommunityPostResponse>() {
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
                        getMvpView().getSuccessForAllResponse(deleteCommunityPostResponse, DELETE_COMMUNITY_POST);
                    }
                });

    }

    public void markAsSpamFromPresenter(BookmarkRequestPojo bookmarkResponsePojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.markAsSpamFromModel(bookmarkResponsePojo)
                .compose(this.<BookmarkResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<BookmarkResponsePojo>() {
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
                    public void onNext(BookmarkResponsePojo bookmarkResponsePojo1) {
                        getMvpView().stopProgressBar();
                        getMvpView().getSuccessForAllResponse(bookmarkResponsePojo1, MARK_AS_SPAM);
                    }
                });

    }

    public void getBellNotificationFromPresenter(BellNotificationRequest bellNotificationRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }

        mHomeModel.getNotificationFromModel(bellNotificationRequest)
                .compose(this.<BelNotificationListResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BelNotificationListResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_SEARCH_DATA);

                    }

                    @Override
                    public void onNext(BelNotificationListResponse bellNotificationResponse) {
                        if (null != bellNotificationResponse) {
                            getMvpView().showNotificationList(bellNotificationResponse);
                        }
                    }
                });

    }

    public void getNotificationCountFromPresenter(NotificationReadCount notificationReadCount) {
        mHomeModel.getNotificationReadCountFromModel(notificationReadCount)
                .compose(this.<NotificationReadCountResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<NotificationReadCountResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                    }

                    @Override
                    public void onNext(NotificationReadCountResponse notificationReadCountResponse) {
                        if (null != notificationReadCountResponse) {
                            getMvpView().getNotificationReadCountSuccess(notificationReadCountResponse, NOTIFICATION_COUNT);
                        }
                    }
                });

    }

    public void queryConfig() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        mSheroesAppServiceApi.getConfig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ConfigurationResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ConfigurationResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                          Crashlytics.getInstance().core.logException(e);
                    }

                    @Override
                    public void onNext(ConfigurationResponse configurationResponse) {
                        if (configurationResponse != null && configurationResponse.status.equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (configurationResponse.appConfiguration != null) {
                                mConfiguration.set(configurationResponse.appConfiguration);
                                if (configurationResponse.appConfiguration.configData != null && CommonUtil.isNotEmpty(configurationResponse.appConfiguration.configData.thumborKey)) {
                                    SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(AppConstants.THUMBOR_KEY, configurationResponse.appConfiguration.configData.thumborKey);
                                    editor.apply();
                                }
                                getMvpView().onConfigFetched();
                            }
                        }
                    }
                });
    }

    public void updateSelectedLanguage(LanguageUpdateRequest languageUpdateRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        mSheroesAppServiceApi.updateSelectedLanguage(languageUpdateRequest)
                .subscribeOn(Schedulers.io())
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                    }

                    @Override
                    public void onNext(BaseResponse languageUpdateResponse) {
                    }
                });
    }

    public void onStop() {
        detachView();
    }
    //endregion public methods
}
