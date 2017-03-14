package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.LoginModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import rx.Subscriber;
import rx.Subscription;

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
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    public LoginPresenter(LoginModel mLoginModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.mLoginModel = mLoginModel;
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


    public void getLoginAuthTokeInPresenter(LoginRequest loginRequest,boolean isSignUp) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION);
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
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getLogInResponse(loginResponse);
            }
        });
        registerSubscription(subscription);
    }


    public void onStop() {
        detachView();
    }
}
