package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import java.util.ArrayList;
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
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTopPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.UserPhoneContactsListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.UserPhoneContactsListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
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
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOK_MARK_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_CREATE_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_SEARCH_DATA;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.SPAM_POST_APPROVE;

/**
 * Created by ujjwal on 27/12/17.
 */

public class FeedPresenter extends BasePresenter<IFeedView> {
    private final String TAG = LogUtils.makeLogTag(FeedPresenter.class);
    public static final int NORMAL_REQUEST = 0;
    public static final int LOAD_MORE_REQUEST = 1;
    private static final int END_REQUEST = 2;
    HomeModel mHomeModel;
    SheroesAppServiceApi sheroesAppServiceApi;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;

    MasterDataModel mMasterDataModel;
    private String mEndpointUrl;
    private String mNextToken = "";
    private boolean mIsFeedLoading;
    private int mFeedState;
    private List<FeedDetail> mFeedDetailList = new ArrayList<>();

    @Inject
    public FeedPresenter(MasterDataModel masterDataModel, HomeModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;

        this.mMasterDataModel = masterDataModel;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;
        this.sheroesAppServiceApi = sheroesAppServiceApi;

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

    public void fetchFeed(final int feedState) {
        if(mIsFeedLoading){
            return;
        }
        Integer limit = null;
        // only load more requests should be disabled when end of feed is reached
        if (feedState == LOAD_MORE_REQUEST) {
            if (mFeedState != END_REQUEST) {
                mFeedState = feedState;
            }
        } else {
            mFeedState = feedState;
        }

        switch (mFeedState) {
            case NORMAL_REQUEST:
                mNextToken = null;
                getMvpView().startProgressBar();
                break;
            case LOAD_MORE_REQUEST:
                break;
            case END_REQUEST:
                getMvpView().startProgressBar();
                return;
        }
        mIsFeedLoading = true;

        CommunityFeedRequestPojo communityFeedRequestPojo = new CommunityFeedRequestPojo();
        communityFeedRequestPojo.setNextToken(mNextToken);

            if (!NetworkUtil.isConnected(mSheroesApplication)) {
                getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
                return;
            }
            Subscription subscription = mHomeModel.getCommunityFeedFromModel(communityFeedRequestPojo, mEndpointUrl).subscribe(new Subscriber<FeedResponsePojo>() {
                @Override
                public void onCompleted() {
                    getMvpView().stopProgressBar();
                }
                @Override
                public void onError(Throwable e) {
                    mIsFeedLoading = false;
                    getMvpView().stopProgressBar();
                    Crashlytics.getInstance().core.logException(e);
                    getMvpView().showFeedList(mFeedDetailList);

                }

                @Override
                public void onNext(FeedResponsePojo feedResponsePojo) {
                    mIsFeedLoading = false;
                    getMvpView().stopProgressBar();
                    if(feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)){
                        List<FeedDetail> feedList = feedResponsePojo.getFeedDetails();
                        mNextToken = feedResponsePojo.getNextToken();
                        switch (mFeedState) {
                            case NORMAL_REQUEST:
                                getMvpView().stopProgressBar();
                                mFeedDetailList = feedList;
                                getMvpView().setFeedEnded(false);
                                List<FeedDetail> feedDetails = new ArrayList<>(mFeedDetailList);
                                getMvpView().showFeedList(feedDetails);
                                break;
                            case LOAD_MORE_REQUEST:
                                // append in case of load more
                                if (!CommonUtil.isEmpty(feedList)) {
                                    mFeedDetailList.addAll( feedList);
                                    //getMvpView().showFeedList(mFeedDetailList);
                                    getMvpView().addAllFeed(feedList);
                                }else {
                                    getMvpView().setFeedEnded(true);
                                }
                                break;
                        }

                    }else {
                        if(!CommonUtil.isEmpty(mFeedDetailList) && mFeedDetailList.size()<5){
                            getMvpView().setFeedEnded(true);
                        }
                        getMvpView().showFeedList(mFeedDetailList);
                    }
                }
            });
            registerSubscription(subscription);
    }

    public boolean isFeedLoading() {
        return mIsFeedLoading;
    }

    public void getNewHomeFeedFromPresenter(FeedRequestPojo feedRequestPojo, AppIntroScreenRequest appIntroScreenRequest,FragmentListRefreshData fragmentListRefreshData) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getNewHomeFeedFromModel(feedRequestPojo, appIntroScreenRequest,fragmentListRefreshData).subscribe(new Subscriber<List<FeedDetail>>() {
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
                    getMvpView().showFeedList(feedDetailList);
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
                if (!CommonUtil.isEmpty(feedDetailList) && feedRequestPojo.getPageNo() == 1) {
                    mFragmentListRefreshData.setPostedDate(feedDetailList.get(0).getPostedDate());
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_MY_COMMUNITIES);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                getMvpView().stopProgressBar();
                if (null != feedResponsePojo) {
                    //getMvpView().getFeedListSuccess(feedResponsePojo);
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
                }
            }
        });
        registerSubscription(subscription);
    }
    public void getFollowFromPresenter(PublicProfileListRequest publicProfileListRequest,final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getFollowFromModel(publicProfileListRequest).subscribe(new Subscriber<MentorFollowUnfollowResponse>() {
            @Override
            public void onCompleted() {
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
                if(mentorFollowUnfollowResponse.getStatus()!=AppConstants.SUCCESS)
                {
                    userSolrObj.setSolrIgnoreNoOfMentorFollowers(userSolrObj.getSolrIgnoreNoOfMentorFollowers()+1);
                    userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                    getMvpView().invalidateItem(userSolrObj);
                }else
                {
                    userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                }
            }
        });
        registerSubscription(subscription);
    }
    public void getUnFollowFromPresenter(PublicProfileListRequest publicProfileListRequest,final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getUnFollowFromModel(publicProfileListRequest).subscribe(new Subscriber<MentorFollowUnfollowResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);
                userSolrObj.setSolrIgnoreIsMentorFollowed(true);
            }

            @Override
            public void onNext(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
                getMvpView().stopProgressBar();
                if(mentorFollowUnfollowResponse.getStatus()!=AppConstants.SUCCESS)
                {
                    userSolrObj.setSolrIgnoreNoOfMentorFollowers(userSolrObj.getSolrIgnoreNoOfMentorFollowers()-1);
                    userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                    getMvpView().invalidateItem(userSolrObj);
                }else
                {
                    userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                }
            }
        });
        registerSubscription(subscription);
    }
    public void getEventInterestedFromPresenter(LikeRequestPojo likeRequestPojo, final UserPostSolrObj userPostSolrObj) {
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
                getMvpView().invalidateItem(userPostSolrObj);
            }
        });
        registerSubscription(subscription);
    }

    public void getEventNotInteresetedFromPresenter(LikeRequestPojo likeRequestPojo, final UserPostSolrObj userPostSolrObj) {
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
                getMvpView().invalidateItem(userPostSolrObj);
            }
        });
        registerSubscription(subscription);
    }

    public void getEventInterestedFromPresenter(LikeRequestPojo likeRequestPojo, final Comment comment) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = false;
            comment.likeCount--;
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

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                if (likeResponse.getStatus() == AppConstants.FAILED) {
                    comment.isLiked = false;
                    comment.likeCount--;
                }
                getMvpView().stopProgressBar();
            }
        });
        registerSubscription(subscription);
    }

    public void getEventNotInteresetedFromPresenter(LikeRequestPojo likeRequestPojo, final Comment comment) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = true;
            comment.likeCount++;
            //getMvpView().invalidateLikeUnlike(comment);
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
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if(likeResponse.getStatus() == AppConstants.FAILED){
                    comment.isLiked = true;
                    comment.likeCount++;
                }
            }
        });
        registerSubscription(subscription);
    }


    public void postBookmarked(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
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
               // //getMvpView().getSuccessForAllResponse(bookmarkResponsePojo, BOOKMARK_UNBOOKMARK);
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
                //getMvpView().getSuccessForAllResponse(communityResponse, JOIN_INVITE);
            }
        });
        registerSubscription(subscription);
    }

    public void deleteCommunityPostFromPresenter(DeleteCommunityPostRequest deleteCommunityPostRequest, final UserPostSolrObj userPostObj) {
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
                getMvpView().notifyAllItemRemoved(userPostObj);
                //getMvpView().getSuccessForAllResponse(deleteCommunityPostResponse, DELETE_COMMUNITY_POST);
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
                //getMvpView().getSuccessForAllResponse(bookmarkResponsePojo1, MARK_AS_SPAM);
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
                   // getMvpView().getNotificationListSuccess(bellNotificationResponse);
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
                    //getMvpView().getNotificationReadCountSuccess(notificationReadCountResponse,NOTIFICATION_COUNT);
                }
            }
        });
        registerSubscription(subscription);
    }
    public void getSpamPostApproveFromPresenter(final ApproveSpamPostRequest approveSpamPostRequest, final UserPostSolrObj userPostSolrObj) {
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
                    if(approveSpamPostRequest.isApproved() == false && approveSpamPostRequest.isSpam() == true){
                        // spam post was rejected
                        getMvpView().removeItem(userPostSolrObj);
                    }else if(approveSpamPostRequest.isApproved() == true && approveSpamPostRequest.isSpam() == false){
                        // spam post was approved
                        userPostSolrObj.setSpamPost(false);
                        getMvpView().invalidateItem(userPostSolrObj);
                    }
                    //getMvpView().getNotificationReadCountSuccess(approveSpamPostResponse,SPAM_POST_APPROVE);
                }
            }
        });
        registerSubscription(subscription);
    }


    public void getAppContactsResponseInPresenter(UserPhoneContactsListRequest userPhoneContactsListRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
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
                    //getMvpView().getNotificationReadCountSuccess(userPhoneContactsListResponse,USER_CONTACTS_ACCESS_SUCCESS);
                }
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
                //getMvpView().getSuccessForAllResponse(bookmarkResponsePojo, BOOKMARK_UNBOOKMARK);
            }
        });
        registerSubscription(subscription);
    }

    public void getPostLikesFromPresenter(LikeRequestPojo likeRequestPojo, final FeedDetail feedDetail) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            getMvpView().invalidateItem(feedDetail);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                getMvpView().invalidateItem(feedDetail);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if(likeResponse.getStatus() == AppConstants.FAILED){
                    feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                    feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                    getMvpView().invalidateItem(feedDetail);
                }
                AnalyticsManager.trackPostAction(Event.POST_LIKED, feedDetail, FeedFragment.SCREEN_LABEL);
                getMvpView().invalidateItem(feedDetail);
            }
        });
        registerSubscription(subscription);
    }

    public Observable<LikeResponse> getLikesFromModel(LikeRequestPojo likeRequestPojo) {
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

    public void getPostUnLikesFromPresenter(LikeRequestPojo likeRequestPojo, final FeedDetail feedDetail) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            //mBaseResponseList.set(0, userPostSolrObj);
            getMvpView().invalidateItem(feedDetail);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getUnLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
                feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
               // mBaseResponseList.set(0, userPostSolrObj);
                getMvpView().invalidateItem(feedDetail);

            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if(likeResponse.getStatus() == AppConstants.FAILED){
                    feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                    feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                  //  mBaseResponseList.set(0, userPostSolrObj);
                }
                AnalyticsManager.trackPostAction(Event.POST_UNLIKED, feedDetail, FeedFragment.SCREEN_LABEL);
                getMvpView().invalidateItem(feedDetail);
            }
        });
        registerSubscription(subscription);
    }


    public Observable<LikeResponse> getUnLikesFromModel(LikeRequestPojo likeRequestPojo) {
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

    public void getCommentUnLikesFromPresenter(final LikeRequestPojo likeRequestPojo, final Comment comment, final UserPostSolrObj userPostSolrObj) {
        //final int pos = findCommentPositionById(mBaseResponseList, comment.getId());
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = true;
            comment.likeCount++;
            userPostSolrObj.getLastComments().set(0, comment);
            getMvpView().invalidateItem(userPostSolrObj);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getUnLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
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
                userPostSolrObj.getLastComments().set(0, comment);
                getMvpView().invalidateItem(userPostSolrObj);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if(likeResponse.getStatus() == AppConstants.FAILED){
                    comment.isLiked = true;
                    comment.likeCount++;
                }
                userPostSolrObj.getLastComments().set(0, comment);
                getMvpView().invalidateItem(userPostSolrObj);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(comment.getId()))
                                .postId(Long.toString(comment.getEntityId()))
                                .postType(AnalyticsEventType.COMMUNITY.toString())
                                .body(comment.getComment())
                                .build();
                AnalyticsManager.trackEvent(Event.REPLY_UNLIKED, PostDetailActivity.SCREEN_LABEL, properties);
            }
        });
        registerSubscription(subscription);
    }

    public void getCommentLikesFromPresenter(LikeRequestPojo likeRequestPojo, final Comment comment, final UserPostSolrObj userPostSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            comment.isLiked = false;
            comment.likeCount--;
            userPostSolrObj.getLastComments().set(0, comment);
            getMvpView().invalidateItem(userPostSolrObj);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
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
                userPostSolrObj.getLastComments().set(0, comment);
                getMvpView().invalidateItem(userPostSolrObj);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                if(likeResponse.getStatus() == AppConstants.FAILED){
                    comment.isLiked = false;
                    comment.likeCount--;
                    userPostSolrObj.getLastComments().set(0, comment);
                }
                getMvpView().invalidateItem(userPostSolrObj);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(comment.getId()))
                                .postId(Long.toString(comment.getEntityId()))
                                .postType(AnalyticsEventType.COMMUNITY.toString())
                                .body(comment.getComment())
                                .build();
                AnalyticsManager.trackEvent(Event.REPLY_LIKED, PostDetailActivity.SCREEN_LABEL, properties);
            }
        });
        registerSubscription(subscription);
    }

    public void editTopPost(final CommunityTopPostRequest communityTopPostRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = editPostCommunity(communityTopPostRequest).subscribe(new Subscriber<CreateCommunityResponse>() {

            @Override
            public void onCompleted() {

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
                getMvpView().invalidateItem(communityPostCreateResponse.getFeedDetail());
            }

        });
        registerSubscription(subscription);
    }

    public Observable<CreateCommunityResponse> editPostCommunity(CommunityTopPostRequest communityPostCreateRequest){
        LogUtils.info(TAG,"***************edit community Post****"+new Gson().toJson(communityPostCreateRequest));
        return sheroesAppServiceApi.topPostCommunityPost(communityPostCreateRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void onStop() {
        detachView();
    }

    public void setEndpointUrl(String endpointUrl) {
        this.mEndpointUrl = endpointUrl;
    }
}
