package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.ConfigurationResponse;
import appliedlife.pvtltd.SHEROES.models.LoginModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import io.reactivex.observers.DisposableObserver;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;

/**
 * Created by Praveen_Singh on 04-01-2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Login presenter perform required response data for Login activity.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    private final String TAG = LogUtils.makeLogTag(LoginPresenter.class);
    LoginModel mLoginModel;
    SheroesApplication mSheroesApplication;
    SheroesAppServiceApi mSheroesAppServiceApi;
    @Inject
    Preference<LoginResponse> userPreference;
    MasterDataModel mMasterDataModel;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    Preference<Configuration> mConfiguration;


    @Inject
    public LoginPresenter(MasterDataModel masterDataModel, LoginModel mLoginModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData, SheroesAppServiceApi sheroesAppServiceApi, Preference<Configuration> mConfiguration) {
        this.mMasterDataModel = masterDataModel;
        this.mLoginModel = mLoginModel;
        this.mSheroesApplication = mSheroesApplication;
        this.userPreference = userPreference;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;
        this.mSheroesAppServiceApi = sheroesAppServiceApi;
        this.mConfiguration = mConfiguration;
    }

    public void getMasterDataToPresenter() {
        super.getMasterDataToAllPresenter(mSheroesApplication, mMasterDataModel, mUserPreferenceMasterData);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    public void getLoginAuthTokeInPresenter(LoginRequest loginRequest, boolean isSignUp) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        // getMvpView().startProgressBar();
        mLoginModel.getLoginAuthTokenFromModel(loginRequest, isSignUp)
                .compose(this.<LoginResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                // getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                // getMvpView().stopProgressBar();
                getMvpView().getLogInResponse(loginResponse);
            }
        });

    }

    public void queryConfig() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        mSheroesAppServiceApi.
                getConfig()
                .compose(this.<ConfigurationResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ConfigurationResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                    }

                    @Override
                    public void onNext(ConfigurationResponse configurationResponse) {
                        if (configurationResponse != null && configurationResponse.status.equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (configurationResponse.configuration != null) {
                                mConfiguration.set(configurationResponse.configuration);
                            }
                        }
                    }
                });
    }


    public void googleTokenExpireInFromPresenter(String tokenExpireUrl) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mLoginModel.getGoogleTokenExpireInFromModel(tokenExpireUrl)
                .compose(this.<ExpireInResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ExpireInResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(ExpireInResponse expireInResponse) {
                getMvpView().stopProgressBar();
                if (null != expireInResponse) {
                    getMvpView().getGoogleExpireInResponse(expireInResponse);

                }
            }
        });

    }

    public void getForgetPasswordResponseInPresenter(ForgotPasswordRequest forgotPasswordRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mLoginModel.sendForgetPasswordLinkFromModel(forgotPasswordRequest)
                .compose(this.<ForgotPasswordResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(ForgotPasswordResponse forgotPasswordResponse) {
                getMvpView().stopProgressBar();
                getMvpView().sendForgotPasswordEmail(forgotPasswordResponse);
            }
        });

    }

    public void getEmailVerificationResponseInPresenter(EmailVerificationRequest emailVerificationRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mLoginModel.getEmailVerificationFromModel(emailVerificationRequest)
                .compose(this.<EmailVerificationResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<EmailVerificationResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);

            }

            @Override
            public void onNext(EmailVerificationResponse emailVerificationResponse) {
                getMvpView().stopProgressBar();
                getMvpView().sendVerificationEmailSuccess(emailVerificationResponse);
            }
        });

    }

    public void getAuthTokenRefreshPresenter() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mLoginModel.getAuthTokenRefreshFromModel().
                compose(this.<LoginResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(AppConstants.LOGOUT_USER, ERROR_AUTH_TOKEN);
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        getMvpView().stopProgressBar();
                        if(loginResponse == null){
                            getMvpView().showError(AppConstants.LOGOUT_USER, ERROR_AUTH_TOKEN);
                        }else {
                            getMvpView().getLogInResponse(loginResponse);
                        }
                    }
                });

    }
    public void onStop() {
        detachView();
    }
}