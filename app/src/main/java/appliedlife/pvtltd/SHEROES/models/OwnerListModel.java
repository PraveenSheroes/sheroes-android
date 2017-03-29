package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class OwnerListModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public OwnerListModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }

    public Observable<OwnerListResponse> getCommunityList(OwnerListRequest ownerListRequest){
        LogUtils.error("Community Owner list req: ",gson.toJson(ownerListRequest));

        return sheroesAppServiceApi.getOwnerList(ownerListRequest)
                .map(new Func1<OwnerListResponse, OwnerListResponse>() {
                    @Override
                    public OwnerListResponse call(OwnerListResponse ownerListResponse) {
                        LogUtils.error("Community Owner list res: ",gson.toJson(ownerListResponse));

                        return ownerListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<DeactivateOwnerResponse> getCommunityOwnerDeactivate(DeactivateOwnerRequest deactivateOwnerRequest){
        LogUtils.error("Community Deactivate Owner list req: ",gson.toJson(deactivateOwnerRequest));

        return sheroesAppServiceApi.getOwnerDeactivate(deactivateOwnerRequest)
                .map(new Func1<DeactivateOwnerResponse, DeactivateOwnerResponse>() {
                    @Override
                    public DeactivateOwnerResponse call(DeactivateOwnerResponse deactivateOwnerResponse) {
                        return deactivateOwnerResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

