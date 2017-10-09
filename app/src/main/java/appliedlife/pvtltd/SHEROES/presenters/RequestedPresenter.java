package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.RequestedListModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.ApproveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.RequestedView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.CommunityEnum.APPROVE_REQUEST;
import static appliedlife.pvtltd.SHEROES.enums.CommunityEnum.REMOVE_REQUEST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_REQUESTED;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class RequestedPresenter extends BasePresenter<RequestedView> {
    private final String TAG = LogUtils.makeLogTag(RequestedPresenter.class);
    RequestedListModel requestedListModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    public RequestedPresenter(RequestedListModel memberListModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.requestedListModel = memberListModel;
        this.mSheroesApplication = mSheroesApplication;
        this.mUserPreference = mUserPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }


    public void getAllPendingRequest(MemberRequest memberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = requestedListModel.getPendingList(memberRequest).subscribe(new Subscriber<RequestedListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(e.getMessage(),ERROR_REQUESTED);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(RequestedListResponse requestedListResponse) {
                getMvpView().stopProgressBar();
                if(null!=requestedListResponse) {
                    getMvpView().getAllRequest(requestedListResponse.getMembers());
                }
            }
        });
        registerSubscription(subscription);

    }
    public void onRejectMemberApi(RemoveMemberRequest removeMemberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = requestedListModel.removePandingMember(removeMemberRequest).subscribe(new Subscriber<MemberListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(e.getMessage(), ERROR_MEMBER);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(MemberListResponse memberListResponse) {
                getMvpView().stopProgressBar();
                if(null!=memberListResponse) {
                    getMvpView().removeApprovePandingMember(memberListResponse, REMOVE_REQUEST);
                }
            }
        });
        registerSubscription(subscription);

    }

    public void onApproveMember(ApproveMemberRequest approveMemberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = requestedListModel.approvePandingMember(approveMemberRequest).subscribe(new Subscriber<MemberListResponse>() {
            @Override
            public void onCompleted() {
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
                if(null!=memberListResponse) {
                    getMvpView().removeApprovePandingMember(memberListResponse, APPROVE_REQUEST);
                }
            }
        });
        registerSubscription(subscription);

    }


    public void onStop() {
        detachView();
    }
}