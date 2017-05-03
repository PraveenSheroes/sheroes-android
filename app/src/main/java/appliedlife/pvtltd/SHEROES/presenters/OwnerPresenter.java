package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.OwnerListModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;

/**
 * Created by SHEROES-TECH on 03-02-2017.
 */

public class OwnerPresenter extends BasePresenter<CommunityView> {
    private final String TAG = LogUtils.makeLogTag(OwnerPresenter.class);
    OwnerListModel ownerListModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    public OwnerPresenter(OwnerListModel communityListModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.ownerListModel = communityListModel;
        this.sheroesApplication=sheroesApplication;
        this.userPreference=userPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }


    public void getCommunityOwnerList(OwnerListRequest ownerListRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = ownerListModel.getCommunityList(ownerListRequest).subscribe(new Subscriber<OwnerListResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(e.getMessage(), ERROR_COMMUNITY_OWNER);
                getMvpView().stopProgressBar();
            }
            @Override
            public void onNext(OwnerListResponse ownerListResponse) {
                getMvpView().stopProgressBar();
                if(null!=ownerListResponse) {
                    getMvpView().getOwnerListSuccess(ownerListResponse);
                }
            }

        });
        registerSubscription(subscription);
    }

    public void getCommunityOwnerDeactive(DeactivateOwnerRequest deactivateOwnerRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = ownerListModel.getCommunityOwnerDeactivate(deactivateOwnerRequest).subscribe(new Subscriber<DeactivateOwnerResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(e.getMessage(),ERROR_COMMUNITY_OWNER);
                getMvpView().stopProgressBar();
            }
            @Override
            public void onNext(DeactivateOwnerResponse deactivateOwnerResponse) {
                getMvpView().stopProgressBar();
                if(null!=deactivateOwnerResponse) {
                    getMvpView().getOwnerListDeactivateSuccess(deactivateOwnerResponse);
                }
            }

        });
        registerSubscription(subscription);
    }


    public void onStop() {
        detachView();
    }
}