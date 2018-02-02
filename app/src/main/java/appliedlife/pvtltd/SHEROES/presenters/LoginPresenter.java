package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.LoginModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserFromReferralRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserFromReferralResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import io.reactivex.observers.DisposableObserver;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;



import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;

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
    @Inject
    Preference<LoginResponse> userPreference;
    MasterDataModel mMasterDataModel;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Inject
    public LoginPresenter(MasterDataModel masterDataModel, LoginModel mLoginModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData) {
        this.mMasterDataModel = masterDataModel;
        this.mLoginModel = mLoginModel;
        this.mSheroesApplication = mSheroesApplication;
        this.userPreference = userPreference;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;
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
        mLoginModel.getLoginAuthTokenFromModel(loginRequest, isSignUp).subscribe(new DisposableObserver<LoginResponse>() {
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

    public void getFBVerificationInPresenter(LoginRequest loginRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mLoginModel.getFBVerificationFromModel(loginRequest).subscribe(new DisposableObserver<LoginResponse>() {
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
            public void onNext(LoginResponse loginResponse) {
                getMvpView().stopProgressBar();
                if (null != loginResponse)
                    getMvpView().getLogInResponse(loginResponse);
            }
        });

    }


    public void googleTokenExpireInFromPresenter(String tokenExpireUrl) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mLoginModel.getGoogleTokenExpireInFromModel(tokenExpireUrl).subscribe(new DisposableObserver<ExpireInResponse>() {
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
        mLoginModel.sendForgetPasswordLinkFromModel(forgotPasswordRequest).subscribe(new DisposableObserver<ForgotPasswordResponse>() {
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
        mLoginModel.getEmailVerificationFromModel(emailVerificationRequest).subscribe(new DisposableObserver<EmailVerificationResponse>() {
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

    public void updateUserReferralInPresenter(UserFromReferralRequest userFromReferralRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        mLoginModel.updateUserReferralInModel(userFromReferralRequest).subscribe(new DisposableObserver<UserFromReferralResponse>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);

            }

            @Override
            public void onNext(UserFromReferralResponse userFromReferralResponse) {
                LogUtils.info(TAG, "************updateUserReferralInModel Response*******" + new Gson().toJson(userFromReferralResponse));
            }
        });

    }

    public void onStop() {
        detachView();
    }
}
