package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen_Singh on 04-01-2017.
 * @author Praveen Singh
 * @version 5.0
 * @since 04-01-2017.
 * Title: Login model class interact with Login presenter.
 * required response data for login activity.
 */
@Singleton
public class LoginModel { private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public LoginModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<LoginResponse> getLoginAuthTokenFromModel(LoginRequest loginRequest){
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