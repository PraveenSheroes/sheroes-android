package appliedlife.pvtltd.SHEROES.models;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroData;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

    public Observable<MentorFollowUnfollowResponse> getFollowFromModel(PublicProfileListRequest publicProfileListRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(publicProfileListRequest));
        return sheroesAppServiceApi.getMentorFollowFromApi(publicProfileListRequest)
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
        LogUtils.info(TAG, "*******************" + new Gson().toJson(publicProfileListRequest));
        return sheroesAppServiceApi.getMentorUnFollowFromApi(publicProfileListRequest)
                .map(new Function<MentorFollowUnfollowResponse, MentorFollowUnfollowResponse>() {
                    @Override
                    public MentorFollowUnfollowResponse apply(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                        return mentorFollowUnfollowResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FeedResponsePojo> getCommunityFeedFromModel(CommunityFeedRequestPojo communityFeedRequestPojo, String endpoint) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(communityFeedRequestPojo));
        return sheroesAppServiceApi.getCommunityFeed(endpoint, communityFeedRequestPojo)
                .map(new Function<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo apply(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
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


    public Observable<AllCommunitiesResponse> getAllCommunityFromModel(MyCommunityRequest myCommunityRequest) {

        return sheroesAppServiceApi.getAllCommunityFromApi(myCommunityRequest).map(new Function<AllCommunitiesResponse, AllCommunitiesResponse>() {
            @Override
            public AllCommunitiesResponse apply(AllCommunitiesResponse allCommunitiesResponse) {

                return allCommunitiesResponse;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FeedResponsePojo> getBookMarkFromModel(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getBookMarkFromApi(feedRequestPojo)
                .map(new Function<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo apply(FeedResponsePojo feedResponsePojo) {
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
                    .map(new Function<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo apply(BookmarkResponsePojo bookmarkResponsePojo) throws Exception {
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

    public Observable<LikeResponse> getLikesFromModel(LikeRequestPojo likeRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(likeRequestPojo));
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
        LogUtils.info(TAG, "*******************" + new Gson().toJson(likeRequestPojo));
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

    public Observable<CommunityResponse> communityJoinFromModel(CommunityRequest communityRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(communityRequest));
        return sheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .map(new Function<CommunityResponse, CommunityResponse>() {
                    @Override
                    public CommunityResponse apply(CommunityResponse communityResponse) {
                        return communityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DeleteCommunityPostResponse> deleteCommunityPostFromModel(DeleteCommunityPostRequest deleteCommunityPostRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(deleteCommunityPostRequest));
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

    public Observable<BookmarkResponsePojo> markAsSpamFromModel(BookmarkRequestPojo bookmarkResponsePojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(bookmarkResponsePojo));
        return sheroesAppServiceApi.markAsSpam(bookmarkResponsePojo)
                .map(new Function<BookmarkResponsePojo, BookmarkResponsePojo>() {
                    @Override
                    public BookmarkResponsePojo apply(BookmarkResponsePojo bookmarkResponsePojo1) {
                        return bookmarkResponsePojo1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CommunityResponse> communityOwnerFromModel(CommunityRequest communityRequest) {
        LogUtils.info("Community Join req: ", gson.toJson(communityRequest));

        return sheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .map(new Function<CommunityResponse, CommunityResponse>() {
                    @Override
                    public CommunityResponse apply(CommunityResponse communityResponse) {
                        LogUtils.info("Community Join res: ", gson.toJson(communityResponse));
                        return communityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public Observable<BelNotificationListResponse> getNotificationFromModel(BellNotificationRequest bellNotificationRequest) {
        LogUtils.info(TAG, "Bell notification request" + new Gson().toJson(bellNotificationRequest));
        return sheroesAppServiceApi.bellNotification(bellNotificationRequest)
                .map(new Function<BelNotificationListResponse, BelNotificationListResponse>() {
                    @Override
                    public BelNotificationListResponse apply(BelNotificationListResponse bellNotificationResponse) {
                        return bellNotificationResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NotificationReadCountResponse> getNotificationReadCountFromModel(NotificationReadCount notificationReadCount) {
        LogUtils.info(TAG, " notification read count request" + new Gson().toJson(notificationReadCount));
        return sheroesAppServiceApi.notificationReadCount(notificationReadCount)
                .map(new Function<NotificationReadCountResponse, NotificationReadCountResponse>() {
                    @Override
                    public NotificationReadCountResponse apply(NotificationReadCountResponse notificationReadCountResponse) {
                        return notificationReadCountResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ApproveSpamPostResponse> getSpamPostApproveFromModel(ApproveSpamPostRequest approveSpamPostRequest) {
        LogUtils.info(TAG, " Spam post  request" + new Gson().toJson(approveSpamPostRequest));
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

    public Observable<AppIntroScreenResponse> getAppIntroFromModel(AppIntroScreenRequest appIntroScreenRequest) {
        LogUtils.info(TAG, " **********Appintro  request" + new Gson().toJson(appIntroScreenRequest));
        return sheroesAppServiceApi.appIntroScreen(appIntroScreenRequest)
                .map(new Function<AppIntroScreenResponse, AppIntroScreenResponse>() {
                    @Override
                    public AppIntroScreenResponse apply(AppIntroScreenResponse appIntroScreenResponse) {
                        return appIntroScreenResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GcmIdResponse> getNewGCMidFromModel(LoginRequest loginRequest) {
        LogUtils.info(TAG, " Gcm id  request" + new Gson().toJson(loginRequest));
        return sheroesAppServiceApi.getNewGCMidFromApi(loginRequest)
                .map(new Function<GcmIdResponse, GcmIdResponse>() {
                    @Override
                    public GcmIdResponse apply(GcmIdResponse gcmIdResponse) {
                        return gcmIdResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
