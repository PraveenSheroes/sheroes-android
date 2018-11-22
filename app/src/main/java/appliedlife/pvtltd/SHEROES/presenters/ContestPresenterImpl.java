package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestView;

import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;

/**
 * Created by ujjwal on 11/05/17.
 */

public class ContestPresenterImpl extends BasePresenter<IContestView>{
    private Contest mContest;

    SheroesAppServiceApi sheroesAppServiceApi;
    @Inject
    public ContestPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    public void fetchContest(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        Observable<FeedResponsePojo> observable = getFeedFromModel(feedRequestPojo);
        observable.compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), null);
            }

            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                if(feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.FAILED)){
                    getMvpView().showError("", null);
                    getMvpView().stopProgressBar();
                    return;
                }
                // LogUtils.info(TAG, "********response***********");
                FeedDetail feedDetail = feedResponsePojo.getFeedDetails().get(0);
                ChallengeSolrObj challengeSolrObj = (ChallengeSolrObj) feedDetail;
                if (null != feedResponsePojo) {
                    mContest = new Contest();
                    mContest.title = challengeSolrObj.getChallengeTitle();
                    mContest.remote_id = (int) challengeSolrObj.getIdOfEntityOrParticipant();
                    mContest.body = challengeSolrObj.getListDescription();
                    mContest.createdDateString = challengeSolrObj.getChallengeStartDate();
                    mContest.endDateString = challengeSolrObj.getChallengeEndDate();
                    mContest.hasWinner = challengeSolrObj.isChallengeHasWinner();
                    mContest.isWinner = challengeSolrObj.isChallengeIsWinner();
                    mContest.authorName = challengeSolrObj.getAuthorName();
                    mContest.authorType = challengeSolrObj.getChallengeAuthorTypeS();
                    mContest.authorImageUrl = challengeSolrObj.getAuthorImageUrl();
                    mContest.submissionCount = challengeSolrObj.getNoOfChallengeAccepted();
                    mContest.hasMyPost = challengeSolrObj.isChallengeAccepted();
                    mContest.tag = challengeSolrObj.getChallengeAcceptPostText();
                    mContest.thumbImage = challengeSolrObj.getThumbnailImageUrl();
                    mContest.createdBy = challengeSolrObj.getCreatedBy();
                    if (StringUtil.isNotNullOrEmptyString(challengeSolrObj.getPostShortBranchUrls())) {
                        mContest.shortUrl = challengeSolrObj.getPostShortBranchUrls();
                    } else {
                        mContest.shortUrl = challengeSolrObj.getDeepLinkUrl();
                    }
                    mContest.mWinnerAddress = challengeSolrObj.getWinnerAddress();
                    mContest.winnerAddressUpdated = challengeSolrObj.winnerAddressUpdated;
                    mContest.winnerAnnouncementDate = challengeSolrObj.getChallengeAnnouncementDate(); //Fix for winner announcement
                    getMvpView().showContestFromId(mContest);
                }
            }
        });
    }

    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        //  LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
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

    public void addBookMarkFromPresenter(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            return;
        }
        //getMvpView().startProgressBar();
        Observable<BookmarkResponsePojo> observable = addBookmarkFromModel(bookmarkRequestPojo, isBookmarked);
        observable.subscribe(new DisposableObserver<BookmarkResponsePojo>() {
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_BOOKMARK_UNBOOKMARK);
            }

            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                getMvpView().stopProgressBar();
                //getMvpView().getFollowUnfollowResponse(bookmarkResponsePojo, BOOKMARK_UNBOOKMARK);
            }
        });

    }

    public Observable<BookmarkResponsePojo> addBookmarkFromModel(BookmarkRequestPojo bookmarkRequestPojo, boolean isBookmarked) {
        if (!isBookmarked) {
            return sheroesAppServiceApi.addBookMarkToApi(bookmarkRequestPojo)
                    .map(new Function<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo apply(BookmarkResponsePojo bookmarkResponsePojo) {
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
    //endregion
}
