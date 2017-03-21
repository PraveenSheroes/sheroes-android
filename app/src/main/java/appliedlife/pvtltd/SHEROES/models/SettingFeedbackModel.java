package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by priyanka on 20/02/17.
 */

public class SettingFeedbackModel {

    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public SettingFeedbackModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
    }
/*for User_feedback */

    public Observable<SettingFeedbackResponce> getFeedbackAuthTokenFromModel(SettingFeedbackRequest feedbackRequest) {

        return sheroesAppServiceApi.getSettingAuthToken(feedbackRequest)

                .map(new Func1<SettingFeedbackResponce, SettingFeedbackResponce>() {

                    @Override
                    public SettingFeedbackResponce call(SettingFeedbackResponce feedbackResponse) {

                        return feedbackResponse;
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


/*for User_Rating */

    public Observable<SettingRatingResponse> getUserRatingAuthTokenFromModel(SettingRatingRequest ratingRequest) {

        return sheroesAppServiceApi.getUserRatingAuthToken(ratingRequest)

                .map(new Func1<SettingRatingResponse, SettingRatingResponse>() {

                    @Override
                    public SettingRatingResponse call(SettingRatingResponse ratingResponse) {

                        return ratingResponse;
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*for User_User_Deactivatted */

    public Observable<SettingDeActivateResponse> getUserUserDeactiveAuthTokenFromModel(SettingDeActivateRequest deActivateRequest) {

        return sheroesAppServiceApi.getUserDeactiveAuthToken(deActivateRequest)

                .map(new Func1<SettingDeActivateResponse, SettingDeActivateResponse>() {

                    @Override
                    public SettingDeActivateResponse call(SettingDeActivateResponse deActivateResponse) {

                        return deActivateResponse;
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*for User_User_Prefence */
    public Observable<UserpreferenseResponse> getUserUserPreferenceAuthTokenFromModel(UserPreferenceRequest preferenceRequest) {


        LogUtils.error("user_get_preference_request req: ",gson.toJson(preferenceRequest));


        return sheroesAppServiceApi.getUserPreferenceAuthToken(preferenceRequest)

                .map(new Func1<UserpreferenseResponse, UserpreferenseResponse>() {

                    @Override
                    public UserpreferenseResponse call(UserpreferenseResponse userpreferenseResponse) {

                        LogUtils.error("user_get_preference_response req: ",gson.toJson(userpreferenseResponse));

                        return userpreferenseResponse;
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    /*for User_Change_Prefence */
    public Observable<SettingChangeUserPreferenseResponse> getUserChangePreferenceAuthTokenFromModel(SettingChangeUserPreferenceRequest changeUserPreferenceRequest) {


        LogUtils.error("user_get_preference_request req: ",gson.toJson(changeUserPreferenceRequest));


        return sheroesAppServiceApi.getUserChangePreferenceAuthToken(changeUserPreferenceRequest)

                .map(new Func1<SettingChangeUserPreferenseResponse, SettingChangeUserPreferenseResponse>() {

                    @Override
                    public SettingChangeUserPreferenseResponse call(SettingChangeUserPreferenseResponse settingChangeUserPreferenseResponse) {

                        LogUtils.error("user_get_preference_response req: ",gson.toJson(settingChangeUserPreferenseResponse));

                        return settingChangeUserPreferenseResponse;
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }





}
