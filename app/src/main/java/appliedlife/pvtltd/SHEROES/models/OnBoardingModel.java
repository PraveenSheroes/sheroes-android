package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */
@Singleton
public class OnBoardingModel {
    private final String TAG = LogUtils.makeLogTag(OnBoardingModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public OnBoardingModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<MasterDataResponse> getOnBoardingMasterDataFromModel(){
        return sheroesAppServiceApi.getOnBoardingMasterDataFromApi()
                .map(new Func1<MasterDataResponse, MasterDataResponse>() {
                    @Override
                    public MasterDataResponse call(MasterDataResponse masterDataResponse) {
                        return masterDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<GetAllData> getOnBoardingFromModel(GetAllDataRequest getAllDataRequest){
        return sheroesAppServiceApi.getOnBoardingSearchFromApi(getAllDataRequest)
                .map(new Func1<GetAllData, GetAllData>() {
                    @Override
                    public GetAllData call(GetAllData getAllData) {
                        return getAllData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
