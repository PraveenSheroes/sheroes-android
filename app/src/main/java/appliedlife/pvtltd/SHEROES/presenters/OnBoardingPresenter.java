package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.OnBoardingModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingInterestRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingJobAtRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingLookingForHowCanRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingTellUsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingWorkExpRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import rx.Subscriber;
import rx.Subscription;

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
    public void getInterestJobSearchToPresenter(GetAllDataRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_ON_ONBOARDING);
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
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_ON_ONBOARDING);
            }

            @Override
            public void onNext(GetInterestJobResponse getInterestJobResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getIntersetJobResponse(getInterestJobResponse);
            }
        });
        registerSubscription(subscription);
    }

    public void getCurrentDataStatusToPresenter(BoardingTellUsRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_ON_ONBOARDING);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getCurrentStatusFromModel(getAllDataRequest).subscribe(new Subscriber<BoardingDataResponse>() {
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
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getBoardingJobResponse(boardingDataResponse);
            }
        });
        registerSubscription(subscription);
    }
    public void getLookingForHowCanToPresenter(BoardingLookingForHowCanRequest boardingLookingForHowCanRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_ON_ONBOARDING);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getLookingForHowCanFromModel(boardingLookingForHowCanRequest).subscribe(new Subscriber<BoardingDataResponse>() {
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
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getBoardingJobResponse(boardingDataResponse);
            }
        });
        registerSubscription(subscription);
    }

    public void getJobAtToPresenter(BoardingJobAtRequest boardingJobAtRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_ON_ONBOARDING);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getJobAtFromModel(boardingJobAtRequest).subscribe(new Subscriber<BoardingDataResponse>() {
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
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getBoardingJobResponse(boardingDataResponse);
            }
        });
        registerSubscription(subscription);
    }
    public void getWorkExpToPresenter(BoardingWorkExpRequest boardingWorkExpRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_ON_ONBOARDING);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getWorkExpFromModel(boardingWorkExpRequest).subscribe(new Subscriber<BoardingDataResponse>() {
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
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getBoardingJobResponse(boardingDataResponse);
            }
        });
        registerSubscription(subscription);
    }
    public void getInterestToPresenter(BoardingInterestRequest boardingInterestRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_ON_ONBOARDING);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = onBoardingModel.getInterestFromModel(boardingInterestRequest).subscribe(new Subscriber<BoardingDataResponse>() {
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
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getBoardingJobResponse(boardingDataResponse);
            }
        });
        registerSubscription(subscription);
    }
}