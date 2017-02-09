package appliedlife.pvtltd.SHEROES.models;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.InviteSearchResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ListOfSearch;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.SearchResponse;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class InviteSearchModel {
    private final String TAG = LogUtils.makeLogTag(SearchModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    Preference<SessionUser> userPreference;
    @Inject
    public InviteSearchModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<InviteSearchResponse> getSearchFromModel(ListOfInviteSearch listOfSearch){
        return sheroesAppServiceApi.getInviteSearchResponseFromApi(listOfSearch)
                .map(new Func1<InviteSearchResponse, InviteSearchResponse>() {
                    @Override
                    public InviteSearchResponse call(InviteSearchResponse searchResponse) {
                        return searchResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
