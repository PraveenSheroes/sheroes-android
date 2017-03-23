package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.OnBoardingModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */

public class OnBoardingPresenter extends BasePresenter<OnBoardingView> {
    private final String TAG = LogUtils.makeLogTag(OnBoardingPresenter.class);
    OnBoardingModel onBoardingModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    public OnBoardingPresenter(OnBoardingModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.onBoardingModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    public void getOnBoardingMasterDataToPresenter() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, AppConstants.THREE_CONSTANT);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getOnBoardingFromModel().subscribe(new Subscriber<MasterDataResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), AppConstants.THREE_CONSTANT);
            }

            @Override
            public void onNext(MasterDataResponse masterDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getMasterDataResponse(masterDataResponse.getData());
            }
        });
        registerSubscription(subscription);
    }
}