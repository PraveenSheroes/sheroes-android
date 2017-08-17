package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.SettingFeedbackModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_SETTING;

/**
 * Created by priyanka on 20/02/17.
 */

public class SettingFeedbackPresenter extends BasePresenter<SettingFeedbackView> {
    private final String TAG = LogUtils.makeLogTag(SettingFeedbackPresenter.class);
    SettingFeedbackModel mSettingFeedbackModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;

    @Inject
    public SettingFeedbackPresenter(SettingFeedbackModel mSettingFeedbackModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.mSettingFeedbackModel = mSettingFeedbackModel;
        this.sheroesApplication = sheroesApplication;
        this.userPreference = userPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }




    /*function for user_feedback */
    public void getFeedbackAuthTokeInPresenter(SettingFeedbackRequest feedbackRequest) {

        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();


        Subscription subscription = mSettingFeedbackModel.getFeedbackAuthTokenFromModel(feedbackRequest).subscribe(new Subscriber<SettingFeedbackResponce>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_SETTING);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(SettingFeedbackResponce feedbackResponce) {
                getMvpView().stopProgressBar();
                getMvpView().getFeedbackResponse(feedbackResponce);
            }
        });
        registerSubscription(subscription);
    }





    /*function for user_rating */


    public void getUserRatingAuthTokeInPresenter(SettingRatingRequest ratingRequest) {

        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();


        Subscription subscription = mSettingFeedbackModel.getUserRatingAuthTokenFromModel(ratingRequest).subscribe(new Subscriber<SettingRatingResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_SETTING);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(SettingRatingResponse ratingResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getUserRatingResponse(ratingResponse);
            }
        });
        registerSubscription(subscription);
    }


    /*function for User_deactive_rating */


    public void getUserDeactiveAuthTokeInPresenter(SettingDeActivateRequest deActivateRequest) {

        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();


        Subscription subscription = mSettingFeedbackModel.getUserUserDeactiveAuthTokenFromModel(deActivateRequest).subscribe(new Subscriber<SettingDeActivateResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_SETTING);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(SettingDeActivateResponse deActivateResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getUserDeactiveResponse(deActivateResponse);
            }
        });
        registerSubscription(subscription);
    }

      /*function for User_Get_Preferences */


    public void getUserPreferenceAuthTokeInPresenter(UserPreferenceRequest userPreferenceRequest) {

        if (!NetworkUtil.isConnected(sheroesApplication)) {


            getMvpView().showNwError();

            return;
        }
        getMvpView().startProgressBar();


        Subscription subscription = mSettingFeedbackModel.getUserUserPreferenceAuthTokenFromModel(userPreferenceRequest).subscribe(new Subscriber<UserpreferenseResponse>() {
            @Override
            public void onCompleted() {

                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_SETTING);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(UserpreferenseResponse userpreferenseResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getUserPreferenceResponse(userpreferenseResponse);
            }
        });
        registerSubscription(subscription);
    }


    public void getUserChangePreferenceAuthTokeInPresenter(SettingChangeUserPreferenceRequest settingChangeUserPreferenceRequest) {

        if (!NetworkUtil.isConnected(sheroesApplication)) {


            getMvpView().showNwError();

            return;
        }


        Subscription subscription = mSettingFeedbackModel.getUserChangePreferenceAuthTokenFromModel(settingChangeUserPreferenceRequest).subscribe(new Subscriber<SettingChangeUserPreferenseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_SETTING);
                getMvpView().showNwError();
            }

            @Override
            public void onNext(SettingChangeUserPreferenseResponse settingChangeUserPreferenseResponse) {

                getMvpView().getUserChangePreferenceResponse(settingChangeUserPreferenseResponse);


            }
        });
        registerSubscription(subscription);
    }

    public void onStop() {

        detachView();
    }



}
