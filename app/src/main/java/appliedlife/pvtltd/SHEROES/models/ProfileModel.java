package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserDetailsRequest;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sheroes on 19/03/17.
 */

public class ProfileModel {

    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public ProfileModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
      }


    /*for  user profile education details */

    public Observable<EducationResponse> getEducationAuthTokenFromModel(GetUserDetailsRequest getUserDetailsRequest) {

        return sheroesAppServiceApi.getEducationAuthToken(getUserDetailsRequest)

                .map(new Func1<EducationResponse, EducationResponse>() {

                    @Override
                    public EducationResponse call(EducationResponse educationResponse) {

                        return educationResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }








}
