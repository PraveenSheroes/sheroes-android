package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.RequestedListModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.RequestedView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class RequestedPresenter extends BasePresenter<RequestedView> {
    private final String TAG = LogUtils.makeLogTag(SearchModulePresenter.class);
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


    public void getAllMembers(MemberRequest memberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = requestedListModel.getMemberList(memberRequest).subscribe(new Subscriber<RequestedListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.ERROR_APP_CLOSE,0);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(RequestedListResponse requestedListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getAllRequest(requestedListResponse.getMembers());
            }
        });
        registerSubscription(subscription);

    }
    public void onStop() {
        detachView();
    }
}