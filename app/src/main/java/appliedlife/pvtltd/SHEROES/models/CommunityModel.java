package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareMailResponse;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareViaMail;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ajit Kumar on 06-03-2017.
 */

public class CommunityModel {
    private final String TAG = LogUtils.makeLogTag(CommunityModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public CommunityModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<CreateCommunityResponse> postCreateCommunity(CreateCommunityRequest createCommunityRequest){
        LogUtils.info(TAG,"***************create community****"+new Gson().toJson(createCommunityRequest));
        return sheroesAppServiceApi.postCreateCommunity(createCommunityRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {

                        return communityTagsListResponse;

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<SelectedCommunityResponse> getSelectedFromModel(SelectCommunityRequest selectCommunityRequest){
        LogUtils.info(TAG,"***************suggested community****"+new Gson().toJson(selectCommunityRequest));
        return sheroesAppServiceApi.suggestedCommunity(selectCommunityRequest)
                .map(new Func1<SelectedCommunityResponse, SelectedCommunityResponse>() {
                    @Override
                    public SelectedCommunityResponse call(SelectedCommunityResponse selectedCommunityResponse) {
                        return selectedCommunityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CreateCommunityResponse> postEditCommunity(EditCommunityRequest editCommunityRequest){
        LogUtils.info(TAG,"***************edit community****"+new Gson().toJson(editCommunityRequest));

        return sheroesAppServiceApi.postEditCommunity(editCommunityRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<LinkRenderResponse> linkRenderFromModel(LinkRequest linkRequest){
        LogUtils.info(TAG,"***************Link Render****"+new Gson().toJson(linkRequest));
        return sheroesAppServiceApi.linkRenderApi(linkRequest)
                .map(new Func1<LinkRenderResponse, LinkRenderResponse>() {
                    @Override
                    public LinkRenderResponse call(LinkRenderResponse linkRenderResponse) {
                        return linkRenderResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CreateCommunityResponse> addPostCommunity(CommunityPostCreateRequest communityPostCreateRequest){
        LogUtils.info(TAG,"***************Post****"+new Gson().toJson(communityPostCreateRequest));
        return sheroesAppServiceApi.createCommunityPost(communityPostCreateRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CreateCommunityResponse> editPostCommunity(CommunityPostCreateRequest communityPostCreateRequest){
        LogUtils.info(TAG,"***************edit community Post****"+new Gson().toJson(communityPostCreateRequest));
        return sheroesAppServiceApi.editCommunityPost(communityPostCreateRequest)
                .map(new Func1<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse call(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CreateCommunityOwnerResponse> postCreateCommunityOwner(CreateCommunityOwnerRequest createCommunityOwnerRequest){
        LogUtils.info(TAG,"***************Post Owner****"+new Gson().toJson(createCommunityOwnerRequest));
        return sheroesAppServiceApi.postCreateCommunityOwner(createCommunityOwnerRequest)
                .map(new Func1<CreateCommunityOwnerResponse, CreateCommunityOwnerResponse>() {
                    @Override
                    public CreateCommunityOwnerResponse call(CreateCommunityOwnerResponse createCommunityOwnerResponse) {
                        return createCommunityOwnerResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ShareMailResponse> shareViaEmailModel(ShareViaMail shareViaMail){
        LogUtils.info(TAG,"***************share community post****"+new Gson().toJson(shareViaMail));
        return sheroesAppServiceApi.shareCommunityViaMail(shareViaMail)
                .map(new Func1<ShareMailResponse, ShareMailResponse>() {
                    @Override
                    public ShareMailResponse call(ShareMailResponse shareMailResponse) {
                        return shareMailResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
