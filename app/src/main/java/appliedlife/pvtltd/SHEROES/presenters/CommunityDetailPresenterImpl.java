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
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import io.reactivex.observers.DisposableObserver;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityDetailView;
import io.reactivex.Observable;
import io.reactivex.functions.Function;


import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;

/**
 * Created by ujjwal on 27/12/17.
 */

public class CommunityDetailPresenterImpl extends BasePresenter<ICommunityDetailView>{

    SheroesAppServiceApi sheroesAppServiceApi;
    SheroesApplication mSheroesApplication;

    @Inject
    AppUtils mAppUtils;
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
        communityJoinFromModel(communityRequest).subscribe(new DisposableObserver<CommunityResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);

            }

            @Override
            public void onNext(CommunityResponse communityResponse) {
                getMvpView().stopProgressBar();
                if(communityResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)){
                    getMvpView().onCommunityJoined();
                }
            }
        });

    }

    public Observable<CommunityResponse> communityJoinFromModel(CommunityRequest communityRequest) {
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

    public void leaveCommunityAndRemoveMemberToPresenter(RemoveMemberRequest removeMemberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        getMvpView().startProgressBar();
        removeMember(removeMemberRequest).subscribe(new DisposableObserver<MemberListResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(e.getMessage(), ERROR_MEMBER);
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

    }

    public Observable<MemberListResponse> removeMember(RemoveMemberRequest removeMemberRequest){
        return sheroesAppServiceApi.removeMember(removeMemberRequest)
                .map(new Function<MemberListResponse, MemberListResponse>() {
                    @Override
                    public MemberListResponse apply(MemberListResponse memberListResponse) {
                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void fetchCommunity(String communityId) {
        FeedRequestPojo feedRequestPojo =mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY, 1, Long.valueOf(communityId));
        getFeedFromPresenter(feedRequestPojo);
    }

    public void getFeedFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        getFeedFromModel(feedRequestPojo).subscribe(new DisposableObserver<FeedResponsePojo>() {
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
            public void onNext(FeedResponsePojo feedResponsePojo) {
                if (null != feedResponsePojo && feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    getMvpView().setCommunity((CommunityFeedSolrObj)feedResponsePojo.getFeedDetails().get(0));
                }
            }
        });

    }


    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
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

    public void fetchMyCommunities(MyCommunityRequest myCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MY_COMMUNITIES);
            return;
        }
        sheroesAppServiceApi.getMyCommunityFromApi(myCommunityRequest).map(new Function<FeedResponsePojo, FeedResponsePojo>() {
            @Override
            public FeedResponsePojo apply(FeedResponsePojo feedResponsePojo) {
                return feedResponsePojo;
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<FeedResponsePojo>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);

                getMvpView().showError(e.getMessage(), ERROR_MY_COMMUNITIES);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {

                if (null != feedResponsePojo) {
                    getMvpView().showMyCommunities(feedResponsePojo);
                }
            }
        });

    }
    //endregion
}
