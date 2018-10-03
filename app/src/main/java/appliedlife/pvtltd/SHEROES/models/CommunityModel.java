package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.ChallengePostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareMailResponse;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareViaMail;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        this.gson = gson;
    }


    public Observable<LinkRenderResponse> linkRenderFromModel(LinkRequest linkRequest) {
        return sheroesAppServiceApi.linkRenderApi(linkRequest)
                .map(new Function<LinkRenderResponse, LinkRenderResponse>() {
                    @Override
                    public LinkRenderResponse apply(LinkRenderResponse linkRenderResponse) {
                        return linkRenderResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CreateCommunityResponse> addPostCommunity(Map uploadImageFileMap, CommunityPostCreateRequest communityPostCreateRequest) {
        return sheroesAppServiceApi.createCommunityMultiPartPost(uploadImageFileMap, communityPostCreateRequest)
                .map(new Function<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse apply(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CreateCommunityResponse> createChallengePost(ChallengePostCreateRequest challengePostCreateRequest) {
        return sheroesAppServiceApi.createChallengePost(challengePostCreateRequest)
                .map(new Function<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse apply(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CreateCommunityResponse> editPostCommunity(Map uploadImageFileMap, CommunityPostCreateRequest communityPostCreateRequest) {
        return sheroesAppServiceApi.editCommunityMultiPartPost(uploadImageFileMap, communityPostCreateRequest)
                .map(new Function<CreateCommunityResponse, CreateCommunityResponse>() {
                    @Override
                    public CreateCommunityResponse apply(CreateCommunityResponse communityTagsListResponse) {
                        return communityTagsListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    //region Private Helper methods
    public Observable<SearchUserDataResponse> getUserMentionSuggestionSearchResult(SearchUserDataRequest searchUserDataRequest) {
         return sheroesAppServiceApi.userMentionSuggestion(searchUserDataRequest)
                .map(new Function<SearchUserDataResponse, SearchUserDataResponse>() {
                    @Override
                    public SearchUserDataResponse apply(SearchUserDataResponse searchUserDataResponse) {
                        return searchUserDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
