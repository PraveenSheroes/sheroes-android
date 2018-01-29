package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.OnBoardingModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_ON_ONBOARDING;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */

public class OnBoardingPresenter extends BasePresenter<OnBoardingView> {
    private final String TAG = LogUtils.makeLogTag(OnBoardingPresenter.class);
    OnBoardingModel onBoardingModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    MasterDataModel mMasterDataModel;

    @Inject
    public OnBoardingPresenter(MasterDataModel masterDataModel, OnBoardingModel homeModel, SheroesApplication sheroesApplication, Preference<MasterDataResponse> mUserPreferenceMasterData) {
        this.mMasterDataModel = masterDataModel;
        this.onBoardingModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    public void getMasterDataToPresenter() {
        super.getMasterDataToAllPresenter(mSheroesApplication, mMasterDataModel, mUserPreferenceMasterData);
    }

    public void getFeedFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
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
                getMvpView().showDataList(feedResponsePojo.getFeedDetails());
            }
        });
        registerSubscription(subscription);
    }

    public void getOnBoardingSearchToPresenter(GetAllDataRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_ON_ONBOARDING);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getOnBoardingFromModel(getAllDataRequest).subscribe(new Subscriber<GetAllData>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_ON_ONBOARDING);
            }

            @Override
            public void onNext(GetAllData getAllData) {
                getMvpView().stopProgressBar();
                getMvpView().getAllDataResponse(getAllData);
            }
        });
        registerSubscription(subscription);
    }


    public void communityJoinFromPresenter(CommunityRequest communityRequest, final CommunityFeedSolrObj communityFeedSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.communityJoinFromModel(communityRequest).subscribe(new Subscriber<CommunityResponse>() {
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
                if (communityResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                    communityFeedSolrObj.setMember(false);
                }
                getMvpView().stopProgressBar();
                getMvpView().joinResponse(communityFeedSolrObj);
            }
        });
        registerSubscription(subscription);
    }

    public void leaveCommunityAndRemoveMemberToPresenter(RemoveMemberRequest removeMemberRequest, final CommunityFeedSolrObj communityFeedSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.removeMember(removeMemberRequest).subscribe(new Subscriber<MemberListResponse>() {
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
                if (memberListResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                    communityFeedSolrObj.setMember(true);
                }
                getMvpView().stopProgressBar();
                getMvpView().unJoinResponse(communityFeedSolrObj);
            }
        });
        registerSubscription(subscription);
    }

}