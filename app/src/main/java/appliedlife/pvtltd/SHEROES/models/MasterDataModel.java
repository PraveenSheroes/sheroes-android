package appliedlife.pvtltd.SHEROES.models;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Praveen_Singh on 25-03-2017.
 */
@Singleton
public class MasterDataModel {
    private final String TAG = LogUtils.makeLogTag(MasterDataModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    @Inject
    public MasterDataModel(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }
    public Observable<MasterDataResponse> getMasterDataFromModel(){
        return sheroesAppServiceApi.getOnBoardingMasterDataFromApi()
                .map(new Function<MasterDataResponse, MasterDataResponse>() {
                    @Override
                    public MasterDataResponse apply(MasterDataResponse masterDataResponse) {
                        return masterDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
