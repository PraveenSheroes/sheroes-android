package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.OnBoardingModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
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
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    MasterDataModel mMasterDataModel;
    @Inject
    public OnBoardingPresenter(MasterDataModel masterDataModel,OnBoardingModel homeModel, SheroesApplication sheroesApplication, Preference<MasterDataResponse> mUserPreferenceMasterData) {
       this.mMasterDataModel=masterDataModel;
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
     super.getMasterDataToAllPresenter(mSheroesApplication,mMasterDataModel,mUserPreferenceMasterData);
    }
    public void getOnBoardingSearchToPresenter(GetAllDataRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, AppConstants.THREE_CONSTANT);
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
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), AppConstants.THREE_CONSTANT);
            }

            @Override
            public void onNext(GetAllData getAllData) {
                getMvpView().stopProgressBar();
                getMvpView().getAllDataResponse(getAllData);
            }
        });
        registerSubscription(subscription);
    }
    public void getInterestJobSearchToPresenter(GetAllDataRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, AppConstants.THREE_CONSTANT);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getInterestjobFromModel(getAllDataRequest).subscribe(new Subscriber<GetInterestJobResponse>() {
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
            public void onNext(GetInterestJobResponse getInterestJobResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getIntersetJobResponse(getInterestJobResponse);
            }
        });
        registerSubscription(subscription);
    }
}