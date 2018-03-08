package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.ConfigurationResponse;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
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
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import io.reactivex.observers.DisposableObserver;


import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOK_MARK_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_SEARCH_DATA;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.GCM_ID;
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

    @Inject
    Preference<Configuration> mConfiguration;

    MasterDataModel mMasterDataModel;
    @Inject
    ProfileModel profileModel;

    @Inject
    public HomePresenter(MasterDataModel masterDataModel, HomeModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData,Preference<Configuration> mConfiguration) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
        this.mConfiguration=mConfiguration;
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
            public void onNext(GcmIdResponse gcmIdResponse) {
                if (null != gcmIdResponse) {
                    getMvpView().getNotificationReadCountSuccess(gcmIdResponse,GCM_ID);
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

    }


    //followed profile
    public void getFeedForProfileFromPresenter(final FeedRequestPojo feedRequestPojo) { //todo - profile - public profile
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        profileModel.getFeedFromModelForTestProfile(feedRequestPojo)
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

    }

    public void getNewHomeFeedFromPresenter(FeedRequestPojo feedRequestPojo, AppIntroScreenRequest appIntroScreenRequest,FragmentListRefreshData fragmentListRefreshData) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getNewHomeFeedFromModel(feedRequestPojo, appIntroScreenRequest,fragmentListRefreshData)
                .compose(this.<List<FeedDetail>>bindToLifecycle())
                .subscribe(new DisposableObserver<List<FeedDetail>>() {
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
            public void onNext(List<FeedDetail> feedDetailList) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (StringUtil.isNotEmptyCollection(feedDetailList)) {
                    getMvpView().showHomeFeedList(feedDetailList);
                }
            }
        });

    }

    public void getChallengeResponse(final FeedRequestPojo feedRequestPojo, final FragmentListRefreshData mFragmentListRefreshData) {
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
                if (!CommonUtil.isEmpty(feedDetailList) && feedRequestPojo.getPageNo() == 1) {
                    mFragmentListRefreshData.setPostedDate(feedDetailList.get(0).getPostedDate());
                }
                getMvpView().showHomeFeedList(feedDetailList);
            }
        });

    }

    public void getMyCommunityFromPresenter(final MyCommunityRequest myCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MY_COMMUNITIES);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getMyCommunityFromModel(myCommunityRequest)
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

    }
    public void getFollowFromPresenter(PublicProfileListRequest publicProfileListRequest,final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getFollowFromModel(publicProfileListRequest)
                .compose(this.<MentorFollowUnfollowResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MentorFollowUnfollowResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);
                userSolrObj.setSolrIgnoreIsMentorFollowed(false);
            }

            @Override
            public void onNext(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                getMvpView().stopProgressBar();
                if(mentorFollowUnfollowResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    userSolrObj.setSolrIgnoreNoOfMentorFollowers(userSolrObj.getSolrIgnoreNoOfMentorFollowers()+1);
                    userSolrObj.setSolrIgnoreIsUserFollowed(true);
                    userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                }else {
                    userSolrObj.setSolrIgnoreIsUserFollowed(false);
                    userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                }
                getMvpView().getSuccessForAllResponse(userSolrObj, FOLLOW_UNFOLLOW);
            }
        });

    }
    public void getUnFollowFromPresenter(PublicProfileListRequest publicProfileListRequest,final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getUnFollowFromModel(publicProfileListRequest)
                .compose(this.<MentorFollowUnfollowResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MentorFollowUnfollowResponse>() {
            @Override
            public void onComplete() {
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
                if(mentorFollowUnfollowResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    userSolrObj.setSolrIgnoreNoOfMentorFollowers(userSolrObj.getSolrIgnoreNoOfMentorFollowers()-1);
                    userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                    userSolrObj.setSolrIgnoreIsUserFollowed(false);
                }else  {
                    userSolrObj.setSolrIgnoreIsUserFollowed(true);
                    userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                }
                getMvpView().getSuccessForAllResponse(userSolrObj, FOLLOW_UNFOLLOW);
            }
        });

    }
    public void getLikesFromPresenter(LikeRequestPojo likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getLikesFromModel(likeRequestPojo)
                .compose(this.<LikeResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
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

    }

    public void getUnLikesFromPresenter(LikeRequestPojo likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getUnLikesFromModel(likeRequestPojo)
                .compose(this.<LikeResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LikeResponse>() {
            @Override
            public void onComplete() {
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
        mHomeModel.getLikesFromModel(likeRequestPojo)
                .compose(this.<LikeResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LikeResponse>() {
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
                getMvpView().invalidateLikeUnlike(comment);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                if (likeResponse.getStatus() .equalsIgnoreCase( AppConstants.FAILED)) {
                    comment.isLiked = false;
                    comment.likeCount--;
                }
                getMvpView().stopProgressBar();
                getMvpView().invalidateLikeUnlike(comment);
               // getMvpView().getFollowUnfollowResponse(likeResponse, LIKE_UNLIKE);
            }
        });

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
        mHomeModel.getUnLikesFromModel(likeRequestPojo)
                .compose(this.<LikeResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LikeResponse>() {
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
                getMvpView().invalidateLikeUnlike(comment);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if(likeResponse.getStatus() .equalsIgnoreCase( AppConstants.FAILED)){
                    comment.isLiked = true;
                    comment.likeCount++;
                }
                getMvpView().invalidateLikeUnlike(comment);
               // getMvpView().getFollowUnfollowResponse(likeResponse, LIKE_UNLIKE);
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);

            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(bookmarkResponsePojo, BOOKMARK_UNBOOKMARK);
            }
        });

    }

    public void communityJoinFromPresenter(CommunityRequest communityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.communityJoinFromModel(communityRequest)
                .compose(this.<CommunityResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CommunityResponse>() {
            @Override
            public void onComplete() {
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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

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
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

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
        getMvpView().startProgressBar();
        mHomeModel.getNotificationFromModel(bellNotificationRequest)
                .compose(this.<BelNotificationListResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BelNotificationListResponse>() {
            @Override
            public void onComplete() {
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
                    getMvpView().getNotificationReadCountSuccess(notificationReadCountResponse,NOTIFICATION_COUNT);
                }
            }
        });

    }
    public void getSpamPostApproveFromPresenter(ApproveSpamPostRequest approveSpamPostRequest) {
        getMvpView().startProgressBar();
        mHomeModel.getSpamPostApproveFromModel(approveSpamPostRequest)
                .compose(this.<ApproveSpamPostResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ApproveSpamPostResponse>() {

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
                    getMvpView().getNotificationReadCountSuccess(approveSpamPostResponse,SPAM_POST_APPROVE);
                }
            }
        });

    }



    public void queryConfig() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        getMvpView().startProgressBar();
        mHomeModel.getConfig().subscribe(new DisposableObserver<ConfigurationResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_MEMBER);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(ConfigurationResponse configurationResponse) {
                if (configurationResponse != null && configurationResponse.status.equalsIgnoreCase(AppConstants.SUCCESS)) {
                    if(configurationResponse.configuration!=null){
                        mConfiguration.set(configurationResponse.configuration);
                        getMvpView().onConfigFetched();
                    }
                }
                getMvpView().stopProgressBar();
            }
        });
    }

    public void onStop() {
        detachView();
    }

    public void fetchAllCommunity() {

    }
}
