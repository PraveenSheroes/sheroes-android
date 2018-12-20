package appliedlife.pvtltd.SHEROES.presenters;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.common.Common;

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
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.ChampionFollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTopPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatePollResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.DeletePollRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollVote;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollVoteResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_CREATE_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;

/**
 * Created by ujjwal on 27/12/17.
 */

public class FeedPresenter extends BasePresenter<IFeedView> {
    public static final int NORMAL_REQUEST = 0;
    public static final int LOAD_MORE_REQUEST = 1;
    private static final int END_REQUEST = 2;
    private final String TAG = LogUtils.makeLogTag(FeedPresenter.class);
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;

    private String mEndpointUrl;
    private boolean mIsHomeFeed;
    private String mNextToken = "";
    private boolean mIsFeedLoading;
    private int mFeedState;
    private HomeModel mHomeModel;
    private SheroesAppServiceApi mSheroesAppServiceApi;
    private SheroesApplication mSheroesApplication;
    private List<FeedDetail> mFeedDetailList = new ArrayList<>();

    @Inject
    public FeedPresenter(HomeModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
        this.mSheroesAppServiceApi = sheroesAppServiceApi;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    public void fetchFeed(final int feedState, final String streamName) {
        if (mIsFeedLoading) {
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
                if (mIsHomeFeed) {
                    if (CommonUtil.isEmpty(mFeedDetailList)) {
                        List<FeedDetail> feedList = new ArrayList<>();
                        FeedDetail homeFeedHeader = new FeedDetail();
                        homeFeedHeader.setSubType(AppConstants.HOME_FEED_HEADER);
                        feedList.add(0, homeFeedHeader);
                        getMvpView().showFeedList(feedList);
                    }
                }
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
        mHomeModel.getCommunityFeedFromModel(communityFeedRequestPojo, mEndpointUrl)
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {
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
                        getMvpView().hideGifLoader();
                        if (feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (feedResponsePojo.getFeedDetails() != null && feedResponsePojo.getFeedDetails().size() > 0) {
                                List<FeedDetail> feedList = feedResponsePojo.getFeedDetails();
                                mNextToken = feedResponsePojo.getNextToken();
                                switch (mFeedState) {
                                    case NORMAL_REQUEST:
                                        getMvpView().stopProgressBar();
                                        if (mIsHomeFeed) {
                                            FeedDetail homeFeedHeader = new FeedDetail();
                                            homeFeedHeader.setSubType(AppConstants.HOME_FEED_HEADER);
                                            feedList.add(0, homeFeedHeader);
                                        } else if (StringUtil.isNotNullOrEmptyString(streamName)) {
                                            if (streamName.equalsIgnoreCase(AppConstants.STORY_STREAM) || streamName.equalsIgnoreCase(AppConstants.POST_STREAM)) {
                                                if (!StringUtil.isNotEmptyCollection(feedList)) {
                                                    FeedDetail noStoryFeed = new FeedDetail();
                                                    noStoryFeed.setNameOrTitle(streamName.equalsIgnoreCase(AppConstants.POST_STREAM) ? mSheroesApplication.getString(R.string.empty_post) : mSheroesApplication.getString(R.string.empty_stories));
                                                    noStoryFeed.setSubType(AppConstants.TYPE_EMPTY_VIEW);
                                                    feedList.add(noStoryFeed);
                                                }
                                            }
                                        }
                                        mFeedDetailList = feedList;
                                        getMvpView().setFeedEnded(false);
                                        List<FeedDetail> feedDetails = new ArrayList<>(mFeedDetailList);
                                        getMvpView().updateFeedConfigDataToMixpanel(feedResponsePojo);
                                        getMvpView().showFeedList(feedDetails);
                                        break;
                                    case LOAD_MORE_REQUEST:
                                        // append in case of load more
                                        if (!CommonUtil.isEmpty(feedList)) {
                                            mFeedDetailList.addAll(feedList);
                                            //getMvpView().showFeedList(mFeedDetailList);
                                            getMvpView().addAllFeed(feedList);
                                        } else {
                                            getMvpView().setFeedEnded(true);
                                        }
                                        break;
                                }
                            }
//                            else{
//                                if(feedResponsePojo.getFieldErrorMessageMap() != null) {
//                                    if(feedResponsePojo.getFieldErrorMessageMap().containsKey("info")) {
//                                        getMvpView().showEmptyScreen(feedResponsePojo.getFieldErrorMessageMap().get("info"));
//                                    }else{
//                                        getMvpView().showEmptyScreen("Sorry, no documents found for this search result");
//                                    }
//                                }else{
//                                    getMvpView().showEmptyScreen("Sorry, no documents found for this search result");
//                                }
//                            }
                        }else {
                            if (feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.FAILED)) { //TODO -chk with ujjwal
                                getMvpView().setFeedEnded(true);
                            } else if (!CommonUtil.isEmpty(mFeedDetailList) && mFeedDetailList.size() < 5) {
                                getMvpView().setFeedEnded(true);
                            }
                            getMvpView().showFeedList(mFeedDetailList);
                        }
                    }
                });
    }

    public void getFeeds(final int feedState, final String streamName, String searchText, String searchCategory) {
        if (mIsFeedLoading) {
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
                mNextToken = "";
                if (mIsHomeFeed) {
                    if (CommonUtil.isEmpty(mFeedDetailList)) {
                        List<FeedDetail> feedList = new ArrayList<>();
                        FeedDetail homeFeedHeader = new FeedDetail();
                        homeFeedHeader.setSubType(AppConstants.HOME_FEED_HEADER);
                        feedList.add(0, homeFeedHeader);
                        getMvpView().showFeedList(feedList);
                    }
                }
                break;
            case LOAD_MORE_REQUEST:
                break;
            case END_REQUEST:
                getMvpView().startProgressBar();
                return;
        }
        mIsFeedLoading = true;

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }

        mSheroesAppServiceApi.getSearchResponse("participant/search/?search_text=" + searchText + "&search_category=" + searchCategory + "&next_token=" + mNextToken)
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
                        mIsFeedLoading = false;
                        getMvpView().stopProgressBar();
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showFeedList(mFeedDetailList);
                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        mIsFeedLoading = false;
                        getMvpView().stopProgressBar();
                        getMvpView().hideGifLoader();
                        if (feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (feedResponsePojo.getFeedDetails() != null && feedResponsePojo.getFeedDetails().size() > 0) {
                                List<FeedDetail> feedList = feedResponsePojo.getFeedDetails();
                                mNextToken = feedResponsePojo.getNextToken();
                                switch (mFeedState) {
                                    case NORMAL_REQUEST:
                                        getMvpView().stopProgressBar();
                                        if (mIsHomeFeed) {
                                            FeedDetail homeFeedHeader = new FeedDetail();
                                            homeFeedHeader.setSubType(AppConstants.HOME_FEED_HEADER);
                                            feedList.add(0, homeFeedHeader);
                                        } else if (StringUtil.isNotNullOrEmptyString(streamName)) {
                                            if (streamName.equalsIgnoreCase(AppConstants.STORY_STREAM) || streamName.equalsIgnoreCase(AppConstants.POST_STREAM)) {
                                                if (!StringUtil.isNotEmptyCollection(feedList)) {
                                                    FeedDetail noStoryFeed = new FeedDetail();
                                                    noStoryFeed.setNameOrTitle(streamName.equalsIgnoreCase(AppConstants.POST_STREAM) ? mSheroesApplication.getString(R.string.empty_post) : mSheroesApplication.getString(R.string.empty_stories));
                                                    noStoryFeed.setSubType(AppConstants.TYPE_EMPTY_VIEW);
                                                    feedList.add(noStoryFeed);
                                                }
                                            }
                                        }
                                        mFeedDetailList = feedList;
                                        if (!CommonUtil.isNotEmpty(mNextToken)) {
                                            getMvpView().setFeedEnded(true);
                                        } else {
                                            getMvpView().setFeedEnded(false);
                                        }
                                        List<FeedDetail> feedDetails = new ArrayList<>(mFeedDetailList);
                                        getMvpView().updateFeedConfigDataToMixpanel(feedResponsePojo);
                                        getMvpView().showFeedList(feedDetails);
                                        break;
                                    case LOAD_MORE_REQUEST:
                                        // append in case of load more
                                        if (!CommonUtil.isNotEmpty(mNextToken)) {
                                            getMvpView().setFeedEnded(true);
                                        } else {
                                            getMvpView().setFeedEnded(true);
                                        }
                                        if (!CommonUtil.isEmpty(feedList)) {
                                            mFeedDetailList.addAll(feedList);
                                            getMvpView().addAllFeed(feedList);
                                        }
                                        break;
                                }
                            } else {
                                if (mFeedState == NORMAL_REQUEST) {
                                    if (feedResponsePojo.getFieldErrorMessageMap() != null) {
                                        if (feedResponsePojo.getFieldErrorMessageMap().containsKey("info")) {
                                            getMvpView().showEmptyScreen(feedResponsePojo.getFieldErrorMessageMap().get("info"));
                                        } else {
                                            getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                                        }
                                    } else {
                                        getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                                    }
                                }
                            }
                        } else {
                            if (feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.FAILED)) { //TODO -chk with ujjwal
                                getMvpView().setFeedEnded(true);
                                if (feedResponsePojo.getFieldErrorMessageMap() != null) {
                                    if (feedResponsePojo.getFieldErrorMessageMap().containsKey("info")) {
                                        getMvpView().showEmptyScreen(feedResponsePojo.getFieldErrorMessageMap().get("info"));
                                    } else {
                                        getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                                    }
                                }
                            }
                            getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                        }
                    }
                });
    }

    public boolean isFeedLoading() {
        return mIsFeedLoading;
    }

    public void setmIsFeedLoading(boolean mIsFeedLoading) {
        this.mIsFeedLoading = mIsFeedLoading;
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

    //Post Follow /Following
    public void getPostAuthorFollowed(PublicProfileListRequest publicProfileListRequest, final UserPostSolrObj userPostSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getMentorFollowFromApi(publicProfileListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse mentorFollowUnfollowResponse) {
                        getMvpView().stopProgressBar();
                        if (mentorFollowUnfollowResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            userPostSolrObj.setSolrIgnoreIsUserFollowed(true);
                        } else {
                            if (mentorFollowUnfollowResponse.isAlreadyFollowed()) {
                                userPostSolrObj.setSolrIgnoreIsUserFollowed(true);
                            } else {
                                userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                            }
                        }
                        getMvpView().invalidateItem(userPostSolrObj);
                    }
                });
    }

    //Unfollowed
    public void getPostAuthorUnfollowed(PublicProfileListRequest publicProfileListRequest, final UserPostSolrObj userPostSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getMentorUnFollowFromApi(publicProfileListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse championFollowedResponse) {
                        getMvpView().stopProgressBar();
                        if (championFollowedResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (userPostSolrObj.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                                userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                            } else {
                                userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                            }
                        } else {
                            userPostSolrObj.setSolrIgnoreIsUserFollowed(false);
                        }
                        getMvpView().invalidateItem(userPostSolrObj);
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
                            if (championFollowedResponse.isAlreadyFollowed()) {
                                userSolrObj.setSolrIgnoreIsUserFollowed(true);
                                userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                            } else {
                                userSolrObj.setSolrIgnoreIsUserFollowed(false);
                                userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                            }
                        }
                        getMvpView().invalidateItem(userSolrObj);
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
                        userSolrObj.setSolrIgnoreIsUserFollowed(true);
                        userSolrObj.setSolrIgnoreIsMentorFollowed(true);
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
                            userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                            userSolrObj.setSolrIgnoreIsUserFollowed(true);
                        }
                        getMvpView().invalidateItem(userSolrObj);
                    }
                });
    }


    public void postBookmarked(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
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
                    }
                });
    }

    public void deleteCommunityPostFromPresenter(DeleteCommunityPostRequest deleteCommunityPostRequest, final FeedDetail feedDetail) {
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
                        getMvpView().notifyAllItemRemoved(feedDetail);
                    }
                });
    }

    public void deletePollFromPresenter(DeletePollRequest deletePollRequest, final PollSolarObj pollSolarObj) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.deletePoll(deletePollRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CreatePollResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CreatePollResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(CreatePollResponse communityPostCreateResponse) {
                        if (communityPostCreateResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().notifyAllItemRemoved(pollSolarObj);
                        }
                        getMvpView().stopProgressBar();
                    }
                });
    }

    public void getSpamPostApproveFromPresenter(final ApproveSpamPostRequest approveSpamPostRequest, final UserPostSolrObj userPostSolrObj) {
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
                        getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                    }

                    @Override
                    public void onNext(ApproveSpamPostResponse approveSpamPostResponse) {
                        getMvpView().stopProgressBar();
                        if (null != approveSpamPostResponse) {
                            if (!approveSpamPostRequest.isApproved() && approveSpamPostRequest.isSpam()) {
                                // spam post was rejected
                                getMvpView().removeItem(userPostSolrObj);
                            } else if (approveSpamPostRequest.isApproved() && !approveSpamPostRequest.isSpam()) {
                                // spam post was approved
                                userPostSolrObj.setSpamPost(false);
                                getMvpView().invalidateItem(userPostSolrObj);
                            }
                        }
                    }
                });
    }


    public void addBookMarkFromPresenter(final BookmarkRequestPojo bookmarkRequestPojo, final boolean isBookmarked, final UserPostSolrObj userSolrObj) {
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

                        if (bookmarkResponsePojo.getStatus() != null && bookmarkResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            userSolrObj.setBookmarked(!isBookmarked);
                            getMvpView().bookmarkedUnBookMarkedResponse(userSolrObj);
                        }
                    }
                });
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
        mSheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                        feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                        feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                        getMvpView().invalidateItem(feedDetail);
                    }

                    @Override
                    public void onNext(LikeResponse likeResponse) {
                        getMvpView().stopProgressBar();
                        if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            feedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                            feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                            getMvpView().invalidateItem(feedDetail);
                        }
                        getMvpView().invalidateItem(feedDetail);
                        getMvpView().likeUnlikeResponse(feedDetail, true);
                    }
                });
    }

    public void getPollVoteFromPresenter(PollVote pollVote, final FeedDetail feedDetail, final long pollOptionId) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            PollSolarObj pollSolarObj = (PollSolarObj) feedDetail;
            pollSolarObj.setTotalNumberOfResponsesOnPoll(pollSolarObj.getTotalNumberOfResponsesOnPoll() - AppConstants.ONE_CONSTANT);
            getMvpView().invalidateItem(pollSolarObj);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getPollVoteFromApi(pollVote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<PollVoteResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<PollVoteResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                        PollSolarObj pollSolarObj = (PollSolarObj) feedDetail;
                        pollSolarObj.setTotalNumberOfResponsesOnPoll(pollSolarObj.getTotalNumberOfResponsesOnPoll() - AppConstants.ONE_CONSTANT);
                        getMvpView().invalidateItem(pollSolarObj);
                    }

                    @Override
                    public void onNext(PollVoteResponse voteResponse) {
                        getMvpView().stopProgressBar();
                        PollSolarObj pollSolarObj = null;
                        if (voteResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            pollSolarObj = voteResponse.getPollReactionModel().getPollSolrObj();
                            pollSolarObj.setStreamType(feedDetail.getStreamType());
                            pollSolarObj.setItemPosition(feedDetail.getItemPosition());
                        } else if (voteResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            pollSolarObj = (PollSolarObj) feedDetail;
                            pollSolarObj.setTotalNumberOfResponsesOnPoll(pollSolarObj.getTotalNumberOfResponsesOnPoll() - AppConstants.ONE_CONSTANT);
                        }
                        if (pollSolarObj != null) {
                            getMvpView().invalidateItem(pollSolarObj);
                            getMvpView().pollVoteResponse(pollSolarObj, pollOptionId);
                        }
                    }
                });
    }

    public void getPostUnLikesFromPresenter(LikeRequestPojo likeRequestPojo, final FeedDetail feedDetail) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_LIKE_UNLIKE);
            feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            getMvpView().invalidateItem(feedDetail);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getUnLikesFromApi(likeRequestPojo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                        feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                        feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                        getMvpView().invalidateItem(feedDetail);
                    }

                    @Override
                    public void onNext(LikeResponse likeResponse) {
                        getMvpView().stopProgressBar();
                        if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            feedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                            feedDetail.setNoOfLikes(feedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                        }
                        getMvpView().invalidateItem(feedDetail);
                        getMvpView().likeUnlikeResponse(feedDetail, false);
                    }
                });
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
        mSheroesAppServiceApi.getUnLikesFromApi(likeRequestPojo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                        comment.isLiked = true;
                        comment.likeCount++;
                        userPostSolrObj.getLastComments().set(0, comment);
                        getMvpView().invalidateItem(userPostSolrObj);
                    }

                    @Override
                    public void onNext(LikeResponse likeResponse) {
                        getMvpView().stopProgressBar();
                        if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
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
                                        .communityId(comment.getCommunityId())
                                        .streamType((userPostSolrObj != null && CommonUtil.isNotEmpty(userPostSolrObj.getStreamType())) ? userPostSolrObj.getStreamType() : "")
                                        .build();
                        AnalyticsManager.trackEvent(Event.REPLY_UNLIKED, PostDetailActivity.SCREEN_LABEL, properties);
                    }
                });
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
        mSheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        getMvpView().showError(e.getMessage(), ERROR_LIKE_UNLIKE);
                        comment.isLiked = false;
                        comment.likeCount--;
                        userPostSolrObj.getLastComments().set(0, comment);
                        getMvpView().invalidateItem(userPostSolrObj);
                    }

                    @Override
                    public void onNext(LikeResponse likeResponse) {
                        getMvpView().stopProgressBar();
                        if (likeResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
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
                                        .communityId(comment.getCommunityId())
                                        .streamType((userPostSolrObj != null && CommonUtil.isNotEmpty(userPostSolrObj.getStreamType())) ? userPostSolrObj.getStreamType() : "")
                                        .build();
                        AnalyticsManager.trackEvent(Event.REPLY_LIKED, PostDetailActivity.SCREEN_LABEL, properties);
                    }
                });
    }

    public void editTopPost(final CommunityTopPostRequest communityTopPostRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.topPostCommunityPost(communityTopPostRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CreateCommunityResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CreateCommunityResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                        getMvpView().stopProgressBar();
                        getMvpView().invalidateItem(communityPostCreateResponse.getFeedDetail());
                    }
                });
    }

    public void onStop() {
        detachView();
    }

    public void setEndpointUrl(String endpointUrl) {
        this.mEndpointUrl = endpointUrl;
    }

    public void setIsHomeFeed(boolean isHomeFeed) {
        this.mIsHomeFeed = isHomeFeed;
    }

    public void joinCommunity(CommunityRequest communityRequest, final CommunityFeedSolrObj communityFeedSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
            communityFeedSolrObj.setMember(false);
            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
                        communityFeedSolrObj.setMember(false);
                        getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                    }

                    @Override
                    public void onNext(CommunityResponse communityResponse) {
                        if (communityResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
                            communityFeedSolrObj.setMember(false);
                            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                        } else {
                            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                        }
                        getMvpView().stopProgressBar();
                    }
                });
    }


    public void leaveCommunity(RemoveMemberRequest removeMemberRequest, final CommunityFeedSolrObj communityFeedSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
            communityFeedSolrObj.setMember(true);
            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.removeMember(removeMemberRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<MemberListResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MemberListResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
                        communityFeedSolrObj.setMember(true);
                        getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(MemberListResponse memberListResponse) {
                        if (memberListResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
                            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
                            communityFeedSolrObj.setMember(true);
                        }
                        getMvpView().invalidateCommunityLeft(communityFeedSolrObj);
                        getMvpView().stopProgressBar();
                    }
                });
    }


    public void reportSpamPostOrComment(SpamPostRequest spamPostRequest, final UserPostSolrObj userPostSolrObj) { //add the comment object here when handle article comment
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.reportSpamPostOrComment(spamPostRequest)
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
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(SpamResponse spamResponse) {
                        getMvpView().onSpamPostOrCommentReported(spamResponse, userPostSolrObj);
                        getMvpView().stopProgressBar();
                    }
                });
    }

    public void deleteArticle(final ArticleSubmissionRequest articleSubmissionRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.deleteArticle(articleSubmissionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ArticleSubmissionResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ArticleSubmissionResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(ArticleSubmissionResponse articleSubmissionResponse) {
                        getMvpView().stopProgressBar();
                        if (null != articleSubmissionResponse) {
                            getMvpView().removeItem(articleSubmissionResponse.getArticleSolrObj());
                        }
                    }
                });
    }
}
