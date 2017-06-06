package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.SignupRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.GooglePlusRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.GooglePlusResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
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
            return sheroesAppServiceApi.getFbSignUpToken(loginRequest)
                    .map(new Func1<LoginResponse, LoginResponse>() {
                        @Override
                        public LoginResponse call(LoginResponse loginResponse) {
                            return loginResponse;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
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
}
