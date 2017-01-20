package appliedlife.pvtltd.SHEROES.models;


import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.home.CityListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItemResponse;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Hotel model class interact with Hotel presenter.
 * required response data for Home activity.
 */
@Singleton
public class HomeModel {
    private final String TAG = LogUtils.makeLogTag(HomeModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    Preference<SessionUser> userPreference;
    @Inject
    public HomeModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<CityListResponse>getCityList(CityListResponse cityListResponse){
        return sheroesAppServiceApi.getCityList(cityListResponse)
                .map(new Func1<CityListResponse, CityListResponse>() {
                    @Override
                    public CityListResponse call(CityListResponse cityListResponse) {
                        return cityListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<HomeSpinnerItemResponse>getSpinnerListFromModel(){
        return sheroesAppServiceApi.getHomeSpinnerList()
                .map(new Func1<HomeSpinnerItemResponse, HomeSpinnerItemResponse>() {
                    @Override
                    public HomeSpinnerItemResponse call(HomeSpinnerItemResponse homeSpinnerItemResponse) {
                        return homeSpinnerItemResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
