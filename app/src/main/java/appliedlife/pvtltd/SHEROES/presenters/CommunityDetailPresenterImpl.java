package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityDetailView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;

/**
 * Created by ujjwal on 27/12/17.
 */

public class CommunityDetailPresenterImpl extends BasePresenter<ICommunityDetailView>{

    SheroesAppServiceApi sheroesAppServiceApi;
    SheroesApplication mSheroesApplication;
    @Inject
    public CommunityDetailPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi, SheroesApplication sheroesApplication) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.mSheroesApplication = sheroesApplication;
    }

    public void communityJoinFromPresenter(CommunityRequest communityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            getMvpView().stopProgressBar();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityJoinFromModel(communityRequest).subscribe(new Subscriber<CommunityResponse>() {
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
                if(communityResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)){
                    getMvpView().onCommunityJoined();
                }
            }
        });
        registerSubscription(subscription);
    }

    public Observable<CommunityResponse> communityJoinFromModel(CommunityRequest communityRequest) {
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

    public void leaveCommunityAndRemoveMemberToPresenter(RemoveMemberRequest removeMemberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = removeMember(removeMemberRequest).subscribe(new Subscriber<MemberListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_MEMBER);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(MemberListResponse memberListResponse) {
                getMvpView().stopProgressBar();
                if(memberListResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)){
                    getMvpView().onCommunityLeft();
                }
            }
        });
        registerSubscription(subscription);
    }

    public rx.Observable<MemberListResponse> removeMember(RemoveMemberRequest removeMemberRequest){
        return sheroesAppServiceApi.removeMember(removeMemberRequest)
                .map(new Func1<MemberListResponse, MemberListResponse>() {
                    @Override
                    public MemberListResponse call(MemberListResponse memberListResponse) {
                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void fetchContest(FeedRequestPojo feedRequestPojo) {
       /* if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
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
                    mContest.winnerAnnouncementDate = challengeSolrObj.getChallengeAnnouncementDate(); //Fix for winner announcement
                    getMvpView().showContestFromId(mContest);
                }
            }
        });
        registerSubscription(subscription);*/
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
