package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.LoginModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;

/**
 * Created by Praveen_Singh on 04-01-2017.
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Login presenter perform required response data for Login activity.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    private final String TAG = LogUtils.makeLogTag(LoginPresenter.class);
    LoginModel mLoginModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;
    MasterDataModel mMasterDataModel;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    public LoginPresenter(MasterDataModel masterDataModel,LoginModel mLoginModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData) {
      this.mMasterDataModel=masterDataModel;
        this.mLoginModel = mLoginModel;
        this.mSheroesApplication = mSheroesApplication;
        this.userPreference=userPreference;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;
    }

    public void getMasterDataToPresenter() {
        super.getMasterDataToAllPresenter(mSheroesApplication,mMasterDataModel,mUserPreferenceMasterData);
    }
    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }
    public void getLoginAuthTokeInPresenter(LoginRequest loginRequest,boolean isSignUp) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mLoginModel.getLoginAuthTokenFromModel(loginRequest,isSignUp).subscribe(new Subscriber<LoginResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getLogInResponse(loginResponse);
            }
        });
        registerSubscription(subscription);
    }
    public void getFBVerificationInPresenter(LoginRequest loginRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mLoginModel.getFBVerificationFromModel(loginRequest).subscribe(new Subscriber<LoginResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                getMvpView().stopProgressBar();
                if(null!=loginResponse)
                getMvpView().getLogInResponse(loginResponse);
            }
        });
        registerSubscription(subscription);
    }

    public void onStop() {
        detachView();
    }
}
