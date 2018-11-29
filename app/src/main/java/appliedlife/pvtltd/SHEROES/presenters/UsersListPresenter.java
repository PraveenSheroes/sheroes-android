package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.ChampionFollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFeedView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;

/**
 * Created by Ravi on 11/09/18
 * Presenter for user in carousel
 */

public class UsersListPresenter extends BasePresenter<IFeedView> {
    //region constants
    private static final int NORMAL_REQUEST = 0;
    private static final int LOAD_MORE_REQUEST = 1;
    private static final int END_REQUEST = 2;
    //endregion constants

    //region member variable
    private HomeModel mHomeModel;
    private SheroesApplication mSheroesApplication;
    private SheroesAppServiceApi mSheroesAppServiceApi;
    private String mEndpointUrl;
    private boolean mIsHomeFeed;
    private String mNextToken = "";
    private boolean mIsFeedLoading;
    private int mFeedState;
    private List<FeedDetail> mFeedDetailList = new ArrayList<>();
    //endregion member variable

    //region constructor
    @Inject
    public UsersListPresenter(HomeModel homeModel, SheroesApplication sheroesApplication, SheroesAppServiceApi mSheroesAppServiceApi) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mSheroesAppServiceApi = mSheroesAppServiceApi;
    }
    //endregion constructor

    //region override methods
    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }
    //endregion override methods

    //region public methods
    public void fetchUserFeed(final int feedState, final String streamName) {
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
                            List<FeedDetail> feedList = feedResponsePojo.getFeedDetails();
                            mNextToken = feedResponsePojo.getNextToken();
                            switch (mFeedState) {
                                case NORMAL_REQUEST:
                                    getMvpView().stopProgressBar();
                                    mFeedDetailList = feedList;
                                    getMvpView().setFeedEnded(false);
                                    List<FeedDetail> feedDetails = new ArrayList<>(mFeedDetailList);
                                    getMvpView().showFeedList(feedDetails);
                                    getMvpView().updateFeedConfigDataToMixpanel(feedResponsePojo);
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
                        } else {
                            if (feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                                getMvpView().setFeedEnded(true);
                            } else if (!CommonUtil.isEmpty(mFeedDetailList) && mFeedDetailList.size() < 5) {
                                getMvpView().setFeedEnded(true);
                            }
                            getMvpView().showFeedList(mFeedDetailList);
                        }
                    }
                });
    }

    public boolean isFeedLoading() {
        return mIsFeedLoading;
    }

    public void getFollowFromPresenter(PublicProfileListRequest publicProfileListRequest, final UserSolrObj userSolrObj) {
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
                        userSolrObj.setSolrIgnoreIsUserFollowed(false);
                        userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse championFollowedResponse) {
                        getMvpView().stopProgressBar();
                        if (championFollowedResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            userSolrObj.setFollowerCount(userSolrObj.getFollowingCount() + 1);
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
                            userSolrObj.setSolrIgnoreIsUserFollowed(true);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                        } else {
                            userSolrObj.setSolrIgnoreIsUserFollowed(false);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                        }
                        getMvpView().invalidateItem(userSolrObj);
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
    //endregion public methods
}
