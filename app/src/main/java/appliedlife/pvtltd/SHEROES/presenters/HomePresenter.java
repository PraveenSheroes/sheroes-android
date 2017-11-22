package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeAcceptRequest;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
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
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.FollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorFollowerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.CHALLENGE_ACCEPT;
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
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.GCM_ID;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.GROWTH_BUDDIES_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.MARK_AS_SPAM;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.NOTIFICATION_COUNT;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.SPAM_POST_APPROVE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.USER_CONTACTS_ACCESS_SUCCESS;

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
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;

    MasterDataModel mMasterDataModel;

    @Inject
    public HomePresenter(MasterDataModel masterDataModel, HomeModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;

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
                Crashlytics.getInstance().core.logException(e);
            //    getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);

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
                Crashlytics.getInstance().core.logException(e);
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
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (null != feedResponsePojo) {
                    getMvpView().getFeedListSuccess(feedResponsePojo);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getHomeFeedFromPresenter(FeedRequestPojo feedRequestPojo, ChallengeRequest challengeRequest, AppIntroScreenRequest appIntroScreenRequest,FragmentListRefreshData fragmentListRefreshData) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getHomeFeedFromModel(feedRequestPojo, challengeRequest, appIntroScreenRequest,fragmentListRefreshData).subscribe(new Subscriber<List<FeedDetail>>() {
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
            public void onNext(List<FeedDetail> feedDetailList) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (StringUtil.isNotEmptyCollection(feedDetailList)) {
                    getMvpView().showHomeFeedList(feedDetailList);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getChallengeResponse(final FeedRequestPojo feedRequestPojo, final FragmentListRefreshData mFragmentListRefreshData) {
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
                if (!CommonUtil.isEmpty(feedDetailList)) {
                    mFragmentListRefreshData.setPostedDate(feedDetailList.get(0).getPostingDate());
                }
                getMvpView().showHomeFeedList(feedDetailList);
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_MY_COMMUNITIES);

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

    public void getAllCommunities(final MyCommunityRequest myCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MY_COMMUNITIES);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getAllCommunityFromModel(myCommunityRequest).subscribe(new Subscriber<AllCommunitiesResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_MY_COMMUNITIES);

            }

            @Override
            public void onNext(AllCommunitiesResponse allCommunitiesResponse) {
                getMvpView().stopProgressBar();
                if (null != allCommunitiesResponse) {
                    mAllCommunities.set(allCommunitiesResponse);
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOK_MARK_LIST);

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
    public void getCountOfFollowerFromPresenter(MentorFollowerRequest mentorFollowerRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getCountOfFollowerFromModel(mentorFollowerRequest).subscribe(new Subscriber<PublicProfileListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);

            }

            @Override
            public void onNext(PublicProfileListResponse publicProfileListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(publicProfileListResponse, FOLLOW_UNFOLLOW);
            }
        });
        registerSubscription(subscription);
    }
    public void getFollowedFromPresenter(MentorFollowerRequest mentorFollowerRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getFollowedFromModel(mentorFollowerRequest).subscribe(new Subscriber<FollowedResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);

            }

            @Override
            public void onNext(FollowedResponse followedResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(followedResponse, FOLLOW_UNFOLLOW);
            }
        });
        registerSubscription(subscription);
    }
    public void getFollowFromPresenter(PublicProfileListRequest likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getFollowFromModel(likeRequestPojo).subscribe(new Subscriber<MentorFollowUnfollowResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);

            }

            @Override
            public void onNext(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(mentorFollowUnfollowResponse, FOLLOW_UNFOLLOW);
            }
        });
        registerSubscription(subscription);
    }
    public void getUnFollowFromPresenter(PublicProfileListRequest likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getUnFollowFromModel(likeRequestPojo).subscribe(new Subscriber<MentorFollowUnfollowResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);

            }

            @Override
            public void onNext(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(mentorFollowUnfollowResponse, FOLLOW_UNFOLLOW);
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);

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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(likeResponse, LIKE_UNLIKE);
            }
        });
        registerSubscription(subscription);
    }

    public void getLikesFromPresenter(LikeRequestPojo likeRequestPojo, final Comment comment) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = false;
            comment.likeCount--;
            getMvpView().invalidateLikeUnlike(comment);
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                comment.isLiked = false;
                comment.likeCount--;
                getMvpView().invalidateLikeUnlike(comment);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                if (likeResponse.getStatus() == AppConstants.FAILED) {
                    comment.isLiked = false;
                    comment.likeCount--;
                }
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(likeResponse, LIKE_UNLIKE);
            }
        });
        registerSubscription(subscription);
    }

    public void getUnLikesFromPresenter(LikeRequestPojo likeRequestPojo, final Comment comment) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = true;
            comment.likeCount++;
            getMvpView().invalidateLikeUnlike(comment);
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                comment.isLiked = true;
                comment.likeCount++;
                getMvpView().invalidateLikeUnlike(comment);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if(likeResponse.getStatus() == AppConstants.FAILED){
                    comment.isLiked = true;
                    comment.likeCount++;
                }
                getMvpView().invalidateLikeUnlike(comment);
               // getMvpView().getSuccessForAllResponse(likeResponse, LIKE_UNLIKE);
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);

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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);

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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_SEARCH_DATA);

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
                Crashlytics.getInstance().core.logException(e);
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
    public void getSpamPostApproveFromPresenter(ApproveSpamPostRequest approveSpamPostRequest) {
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getSpamPostApproveFromModel(approveSpamPostRequest).subscribe(new Subscriber<ApproveSpamPostResponse>() {

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
                    getMvpView().getNotificationReadCountSuccess(approveSpamPostResponse,SPAM_POST_APPROVE);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getChallengeAcceptFromPresenter(ChallengeAcceptRequest challengeAcceptRequest) {
        Subscription subscription = mHomeModel.getChallengeAcceptFromModel(challengeAcceptRequest).subscribe(new Subscriber<ChallengeListResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
            }

            @Override
            public void onNext(ChallengeListResponse challengeListResponse) {
                if (null != challengeListResponse) {
                    getMvpView().getNotificationReadCountSuccess(challengeListResponse,CHALLENGE_ACCEPT);
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getPublicProfileMentorListFromPresenter(final PublicProfileListRequest publicProfileListRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getPublicProfileMentorListFromModel(publicProfileListRequest).subscribe(new Subscriber<PublicProfileListResponse>() {
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
            public void onNext(PublicProfileListResponse publicProfileListResponse) {
                LogUtils.info(TAG, "********Public Profile response***********");
                getMvpView().stopProgressBar();
                if (null != publicProfileListResponse) {
                    getMvpView().getSuccessForAllResponse(publicProfileListResponse, GROWTH_BUDDIES_LIST);
                }
            }
        });
        registerSubscription(subscription);
    }



    public void getAppContactsResponseInPresenter(UserPhoneContactsListRequest userPhoneContactsListRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        };
        Subscription subscription = mHomeModel.getAppContactsResponseInModel(userPhoneContactsListRequest).subscribe(new Subscriber<UserPhoneContactsListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);

                    }

            @Override
            public void onNext(UserPhoneContactsListResponse userPhoneContactsListResponse) {
                getMvpView().stopProgressBar();
                if (null != userPhoneContactsListResponse) {
                    getMvpView().getNotificationReadCountSuccess(userPhoneContactsListResponse,USER_CONTACTS_ACCESS_SUCCESS);
                }
            }
        });
        registerSubscription(subscription);
    }



    public void onStop() {
        detachView();
    }
}
