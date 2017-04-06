package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityTagsModel {
    private final String TAG = LogUtils.makeLogTag(CommunityTagsModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public CommunityTagsModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<GetTagData> getTagFromModel(GetAllDataRequest getAllDataRequest){
        LogUtils.info(TAG,"TAG FRom*******************"+new Gson().toJson(getAllDataRequest));
        return sheroesAppServiceApi.getTagFromApi(getAllDataRequest)
                .map(new Func1<GetTagData, GetTagData>() {
                    @Override
                    public GetTagData call(GetTagData getAllData) {
                        return getAllData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
