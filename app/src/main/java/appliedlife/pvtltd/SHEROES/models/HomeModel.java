package appliedlife.pvtltd.SHEROES.models;


import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeAcceptRequest;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroData;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.UserPhoneContactsListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.UserPhoneContactsListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.FollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorFollowerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Hotel model class interact with Hotel presenter.
 * required response data for Home activity.
 */
@Singleton
public class HomeModel {
    private final String TAG = LogUtils.makeLogTag(HomeModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public HomeModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
    }

    public Observable<PublicProfileListResponse> getPublicProfileMentorListFromModel(PublicProfileListRequest publicProfileListRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(publicProfileListRequest));
        return sheroesAppServiceApi.getPublicProfileListFromApi(publicProfileListRequest)
                .map(new Func1<PublicProfileListResponse, PublicProfileListResponse>() {
                    @Override
                    public PublicProfileListResponse call(PublicProfileListResponse publicProfileListResponse) {
                        return publicProfileListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<PublicProfileListResponse> getCountOfFollowerFromModel(MentorFollowerRequest mentorFollowerRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(mentorFollowerRequest));

        return sheroesAppServiceApi.getCountOfFollowerFromApi(mentorFollowerRequest)
                .map(new Func1<PublicProfileListResponse, PublicProfileListResponse>() {
                    @Override
                    public PublicProfileListResponse call(PublicProfileListResponse publicProfileListResponse) {
                        return publicProfileListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FollowedResponse> getFollowedFromModel(MentorFollowerRequest mentorFollowerRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(mentorFollowerRequest));
        return sheroesAppServiceApi.isFollowedCheckFromApi(mentorFollowerRequest)
                .map(new Func1<FollowedResponse, FollowedResponse>() {
                    @Override
                    public FollowedResponse call(FollowedResponse followedResponse) {
                        return followedResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<MentorFollowUnfollowResponse> getFollowFromModel(PublicProfileListRequest publicProfileListRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(publicProfileListRequest));
        return sheroesAppServiceApi.getMentorFollowFromApi(publicProfileListRequest)
                .map(new Func1<MentorFollowUnfollowResponse, MentorFollowUnfollowResponse>() {
                    @Override
                    public MentorFollowUnfollowResponse call(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                        return mentorFollowUnfollowResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MentorFollowUnfollowResponse> getUnFollowFromModel(PublicProfileListRequest publicProfileListRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(publicProfileListRequest));
        return sheroesAppServiceApi.getMentorUnFollowFromApi(publicProfileListRequest)
                .map(new Func1<MentorFollowUnfollowResponse, MentorFollowUnfollowResponse>() {
                    @Override
                    public MentorFollowUnfollowResponse call(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                        return mentorFollowUnfollowResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
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

    public Observable<List<FeedDetail>> getHomeFeedFromModel(final FeedRequestPojo feedRequestPojo, ChallengeRequest challengeRequest, AppIntroScreenRequest appIntroScreenRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));

        Observable<FeedResponsePojo> feedResponsePojoObservable = getFeedFromModel(feedRequestPojo);
        Observable<ChallengeListResponse> challengeListResponseObservable = getChallengeListFromModel(challengeRequest);
        Observable<AppIntroScreenResponse> appIntroScreenResponseObservable = getAppIntroFromModel(appIntroScreenRequest);

        Observable<List<FeedDetail>> combined = Observable.zip(feedResponsePojoObservable, challengeListResponseObservable, appIntroScreenResponseObservable, new Func3<FeedResponsePojo, ChallengeListResponse, AppIntroScreenResponse, List<FeedDetail>>() {
            @Override
            public List<FeedDetail> call(FeedResponsePojo feedResponsePojo, ChallengeListResponse challengeListResponse, AppIntroScreenResponse appIntroScreenResponse) {
                ArrayList<FeedDetail> feedDetails = new ArrayList<>();
                if (StringUtil.isNotEmptyCollection(appIntroScreenResponse.getData())) {
                    FeedDetail appIntroFeedCard = new FeedDetail();
                    appIntroFeedCard.setSubType(AppConstants.APP_INTRO_SUB_TYPE);
                    AppIntroData appIntroData = appIntroScreenResponse.getData().get(0);
                    appIntroFeedCard.setAppIntroDataItems(appIntroData);
                    feedDetails.add(appIntroFeedCard);
                }
                if (StringUtil.isNotEmptyCollection(challengeListResponse.getReponseList())) {
                    FeedDetail challengeFeedDetail = new FeedDetail();
                    challengeFeedDetail.setSubType(AppConstants.CHALLENGE_SUB_TYPE);
                    challengeFeedDetail.setChallengeDataItems(challengeListResponse.getReponseList());
                    feedDetails.add(challengeFeedDetail);
                }

                if (StringUtil.isNotEmptyCollection(feedResponsePojo.getFeedDetails())) {
                    List<FeedDetail> feedDetailsFromServer = new ArrayList<>(feedResponsePojo.getFeedDetails());
                    feedDetails.addAll(feedDetailsFromServer);
                }

                return  feedDetails;
            }
        });
        return combined;
    }

    public Observable<FeedResponsePojo> getMyCommunityFromModel(MyCommunityRequest myCommunityRequest) {

        return sheroesAppServiceApi.getMyCommunityFromApi(myCommunityRequest).map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
            @Override
            public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {

                return feedResponsePojo;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FeedResponsePojo> getBookMarkFromModel(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getBookMarkFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BookmarkResponsePojo> addBookmarkFromModel(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(bookmarkRequestPojo));
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

    public Observable<LikeResponse> getLikesFromModel(LikeRequestPojo likeRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(likeRequestPojo));
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
        LogUtils.info(TAG, "*******************" + new Gson().toJson(likeRequestPojo));
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

    public Observable<CommunityResponse> communityJoinFromModel(CommunityRequest communityRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(communityRequest));
        return sheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .map(new Func1<CommunityResponse, CommunityResponse>() {
                    @Override
                    public CommunityResponse call(CommunityResponse communityResponse) {
                        return communityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DeleteCommunityPostResponse> deleteCommunityPostFromModel(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(deleteCommunityPostRequest));
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

    public Observable<BookmarkResponsePojo> markAsSpamFromModel(BookmarkRequestPojo bookmarkResponsePojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(bookmarkResponsePojo));
        return sheroesAppServiceApi.markAsSpam(bookmarkResponsePojo)
                .map(new Func1<BookmarkResponsePojo, BookmarkResponsePojo>() {
                    @Override
                    public BookmarkResponsePojo call(BookmarkResponsePojo bookmarkResponsePojo1) {
                        return bookmarkResponsePojo1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CommunityResponse> communityOwnerFromModel(CommunityRequest communityRequest) {
        LogUtils.info("Community Join req: ", gson.toJson(communityRequest));

        return sheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .map(new Func1<CommunityResponse, CommunityResponse>() {
                    @Override
                    public CommunityResponse call(CommunityResponse communityResponse) {
                        LogUtils.info("Community Join res: ", gson.toJson(communityResponse));
                        return communityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginResponse> getAuthTokenRefreshFromModel() {
        return sheroesAppServiceApi.getRefreshToken()
                .map(new Func1<LoginResponse, LoginResponse>() {
                    @Override
                    public LoginResponse call(LoginResponse loginResponse) {
                        return loginResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<BelNotificationListResponse> getNotificationFromModel(BellNotificationRequest bellNotificationRequest) {
        LogUtils.info(TAG, "Bell notification request" + new Gson().toJson(bellNotificationRequest));
        return sheroesAppServiceApi.bellNotification(bellNotificationRequest)
                .map(new Func1<BelNotificationListResponse, BelNotificationListResponse>() {
                    @Override
                    public BelNotificationListResponse call(BelNotificationListResponse bellNotificationResponse) {
                        return bellNotificationResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NotificationReadCountResponse> getNotificationReadCountFromModel(NotificationReadCount notificationReadCount) {
        LogUtils.info(TAG, " notification read count request" + new Gson().toJson(notificationReadCount));
        return sheroesAppServiceApi.notificationReadCount(notificationReadCount)
                .map(new Func1<NotificationReadCountResponse, NotificationReadCountResponse>() {
                    @Override
                    public NotificationReadCountResponse call(NotificationReadCountResponse notificationReadCountResponse) {
                        return notificationReadCountResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ApproveSpamPostResponse> getSpamPostApproveFromModel(ApproveSpamPostRequest approveSpamPostRequest) {
        LogUtils.info(TAG, " Spam post  request" + new Gson().toJson(approveSpamPostRequest));
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

    public Observable<ChallengeListResponse> getChallengeListFromModel(ChallengeRequest challengeRequest) {
        LogUtils.info(TAG, " **********challenge request" + new Gson().toJson(challengeRequest));
        return sheroesAppServiceApi.challengeList(challengeRequest)
                .map(new Func1<ChallengeListResponse, ChallengeListResponse>() {
                    @Override
                    public ChallengeListResponse call(ChallengeListResponse challengeListResponse) {
                        return challengeListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ChallengeListResponse> getChallengeAcceptFromModel(ChallengeAcceptRequest challengeAcceptRequest) {
        LogUtils.info(TAG, " **********challenge request" + new Gson().toJson(challengeAcceptRequest));
        return sheroesAppServiceApi.challengeAccept(challengeAcceptRequest)
                .map(new Func1<ChallengeListResponse, ChallengeListResponse>() {
                    @Override
                    public ChallengeListResponse call(ChallengeListResponse challengeListResponse) {
                        return challengeListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AppIntroScreenResponse> getAppIntroFromModel(AppIntroScreenRequest appIntroScreenRequest) {
        LogUtils.info(TAG, " **********Appintro  request" + new Gson().toJson(appIntroScreenRequest));
        return sheroesAppServiceApi.appIntroScreen(appIntroScreenRequest)
                .map(new Func1<AppIntroScreenResponse, AppIntroScreenResponse>() {
                    @Override
                    public AppIntroScreenResponse call(AppIntroScreenResponse appIntroScreenResponse) {
                        return appIntroScreenResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GcmIdResponse> getNewGCMidFromModel(LoginRequest loginRequest) {
        LogUtils.info(TAG, " Gcm id  request" + new Gson().toJson(loginRequest));
        return sheroesAppServiceApi.getNewGCMidFromApi(loginRequest)
                .map(new Func1<GcmIdResponse, GcmIdResponse>() {
                    @Override
                    public GcmIdResponse call(GcmIdResponse gcmIdResponse) {
                        return gcmIdResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserPhoneContactsListResponse> getAppContactsResponseInModel(UserPhoneContactsListRequest userPhoneContactsListRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(userPhoneContactsListRequest));
        return sheroesAppServiceApi.getPhoneContactListResponse(userPhoneContactsListRequest)
                .map(new Func1<UserPhoneContactsListResponse, UserPhoneContactsListResponse>() {
                    @Override
                    public UserPhoneContactsListResponse call(UserPhoneContactsListResponse userPhoneContactsListResponse) {
                        return userPhoneContactsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


}
