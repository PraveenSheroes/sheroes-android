package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ajit Kumar on 06-03-2017.
 */

public class CommunityModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public CommunityModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<CreateCommunityResponse> postCreateCommunity(CreateCommunityRequest createCommunityRequest){
        LogUtils.error("Create Community req: ",gson.toJson(createCommunityRequest));
        return sheroesAppServiceApi.postCreateCommunity(createCommunityRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {
                        LogUtils.error("Create Community req: ",gson.toJson(communityTagsListResponse));

                        return communityTagsListResponse;

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CreateCommunityResponse> postEditCommunity(EditCommunityRequest editCommunityRequest){
        LogUtils.error("Edit Community req: ",gson.toJson(editCommunityRequest));

        return sheroesAppServiceApi.postEditCommunity(editCommunityRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {
                        LogUtils.error("Edit Community res: ",gson.toJson(communityTagsListResponse));

                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CreateCommunityOwnerResponse> postCreateCommunityOwner(CreateCommunityOwnerRequest createCommunityOwnerRequest){
        LogUtils.error("Create Community owner req: ",gson.toJson(createCommunityOwnerRequest));

        return sheroesAppServiceApi.postCreateCommunityOwner(createCommunityOwnerRequest)
                .map(new Func1<CreateCommunityOwnerResponse, CreateCommunityOwnerResponse>() {
                    @Override
                    public CreateCommunityOwnerResponse call(CreateCommunityOwnerResponse createCommunityOwnerResponse) {
                        LogUtils.error("Create Community owner res: ",gson.toJson(createCommunityOwnerResponse));

                        return createCommunityOwnerResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}