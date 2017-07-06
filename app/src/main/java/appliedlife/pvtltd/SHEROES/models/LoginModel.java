package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.SignupRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserFromReferralRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserFromReferralResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.GooglePlusRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.GooglePlusResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen_Singh on 04-01-2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 04-01-2017.
 * Title: Login model class interact with Login presenter.
 * required response data for login activity.
 */
@Singleton
public class LoginModel {
    private final String TAG = LogUtils.makeLogTag(LoginModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public LoginModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
    }

    public Observable<LoginResponse> getLoginAuthTokenFromModel(LoginRequest loginRequest, boolean isSignUp) {
        LogUtils.info(TAG,"*******************"+new Gson().toJson(loginRequest));
        if (isSignUp) {
            if(StringUtil.isNotNullOrEmptyString(loginRequest.getCallForSignUp())&&loginRequest.getCallForSignUp().equalsIgnoreCase(AppConstants.GOOGLE_PLUS))
            {
                return sheroesAppServiceApi.getGpSignUpToken(loginRequest)
                        .map(new Func1<LoginResponse, LoginResponse>() {
                            @Override
                            public LoginResponse call(LoginResponse loginResponse) {
                                return loginResponse;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }else {
                return sheroesAppServiceApi.getFbSignUpToken(loginRequest)
                        .map(new Func1<LoginResponse, LoginResponse>() {
                            @Override
                            public LoginResponse call(LoginResponse loginResponse) {
                                return loginResponse;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        } else {
            return sheroesAppServiceApi.getLoginAuthToken(loginRequest)
                    .map(new Func1<LoginResponse, LoginResponse>() {
                        @Override
                        public LoginResponse call(LoginResponse loginResponse) {
                            return loginResponse;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    }
    public Observable<LoginResponse> getFBVerificationFromModel(LoginRequest loginRequest) {
        LogUtils.info(TAG,"*******************"+new Gson().toJson(loginRequest));
            return sheroesAppServiceApi.getFBVerification(loginRequest)
                    .map(new Func1<LoginResponse, LoginResponse>() {
                        @Override
                        public LoginResponse call(LoginResponse loginResponse) {
                            return loginResponse;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<GooglePlusResponse> getGoogleLoginFromModel(GooglePlusRequest loginRequest) {
        return sheroesAppServiceApi.getUserGoogleLogin(loginRequest)
                .map(new Func1<GooglePlusResponse, GooglePlusResponse>() {
                    @Override
                    public GooglePlusResponse call(GooglePlusResponse googlePlusResponse) {
                        return googlePlusResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<ExpireInResponse> getGoogleTokenExpireInFromModel(String expireInUrl) {
        return sheroesAppServiceApi.getGoogleTokenExpire(expireInUrl)
                .map(new Func1<ExpireInResponse, ExpireInResponse>() {
                    @Override
                    public ExpireInResponse call(ExpireInResponse expireInResponse) {
                        return expireInResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<LoginResponse> getAuthTokenSignupFromModel(SignupRequest signupRequest) {
        return sheroesAppServiceApi.userSignup(signupRequest)
                .map(new Func1<LoginResponse, LoginResponse>() {
                    @Override
                    public LoginResponse call(LoginResponse loginResponse) {
                        return loginResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<LoginResponse> getGooglePlusUserResponseFromModel() {
        return sheroesAppServiceApi.googlePlusUserResponse()
                .map(new Func1<LoginResponse, LoginResponse>() {
                    @Override
                    public LoginResponse call(LoginResponse loginResponse) {
                        return loginResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<ForgotPasswordResponse> sendForgetPasswordLinkFromModel(ForgotPasswordRequest forgotPasswordRequest) {
        LogUtils.info(TAG,"*******************"+new Gson().toJson(forgotPasswordRequest));
        return sheroesAppServiceApi.forgotPasswordResponse(forgotPasswordRequest)
                .map(new Func1<ForgotPasswordResponse, ForgotPasswordResponse>() {
                    @Override
                    public ForgotPasswordResponse call(ForgotPasswordResponse forgotPasswordResponse) {
                        return forgotPasswordResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<EmailVerificationResponse> getEmailVerificationFromModel(EmailVerificationRequest emailVerificationRequest) {
        LogUtils.info(TAG,"*******************"+new Gson().toJson(emailVerificationRequest));
        return sheroesAppServiceApi.emailVerificationResponse(emailVerificationRequest)
                .map(new Func1<EmailVerificationResponse, EmailVerificationResponse>() {
                    @Override
                    public EmailVerificationResponse call(EmailVerificationResponse emailVerificationResponse) {
                        return emailVerificationResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<UserFromReferralResponse> updateUserReferralInModel(UserFromReferralRequest userFromReferralRequest) {
        LogUtils.info(TAG,"*******************"+new Gson().toJson(userFromReferralRequest));
        return sheroesAppServiceApi.updateUserReferral(userFromReferralRequest)
                .map(new Func1<UserFromReferralResponse, UserFromReferralResponse>() {
                    @Override
                    public UserFromReferralResponse call(UserFromReferralResponse userFromReferralResponse) {
                        return userFromReferralResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
