package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeAcceptRequest;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.post.Article;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.post.Post;
import appliedlife.pvtltd.SHEROES.models.entities.post.UserProfile;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.CHALLENGE_ACCEPT;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.acceptChallengeRequestBuilder;

/**
 * Created by ujjwal on 11/05/17.
 */

public class ContestPresenterImpl extends BasePresenter<IContestView>{
    private IContestView mContestView;
    private long mChallengeId;
    private Contest mContest;

    SheroesAppServiceApi sheroesAppServiceApi;
    @Inject
    public ContestPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    public void getChallengeAcceptFromPresenter() {
        ChallengeAcceptRequest challengeAcceptRequest = acceptChallengeRequestBuilder(mChallengeId, true, false, 100, false, true, "", "");
        Subscription subscription = getChallengeAcceptFromModel(challengeAcceptRequest).subscribe(new Subscriber<ChallengeListResponse>() {
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
                  //  getMvpView().getNotificationReadCountSuccess(challengeListResponse,CHALLENGE_ACCEPT);
                }
            }
        });
        registerSubscription(subscription);
    }

    public Observable<ChallengeListResponse> getChallengeAcceptFromModel(ChallengeAcceptRequest challengeAcceptRequest) {
       // LogUtils.info(TAG, " **********challenge request" + new Gson().toJson(challengeAcceptRequest));
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

    public void fetchContest(FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), null);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                getMvpView().stopProgressBar();
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
                    mContest.shortUrl = challengeSolrObj.getDeepLinkUrl();
                    mContest.mWinnerAddress = challengeSolrObj.getWinnerAddress();
                    mContest.winnerAddressUpdated = challengeSolrObj.winnerAddressUpdated;
                    getMvpView().showContestFromId(mContest);
                }
            }
        });
        registerSubscription(subscription);
    }

    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        //  LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
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
    //endregion
}
