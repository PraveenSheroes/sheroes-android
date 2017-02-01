package appliedlife.pvtltd.SHEROES.models;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityListResponse;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ajit Kumar on 23-01-2017.
 */

public class CommunityListModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    Preference<SessionUser> userPreference;
    @Inject
    public CommunityListModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<CommunityListResponse> getCommunityList(){
        return sheroesAppServiceApi.getCommunityList()
                .map(new Func1<CommunityListResponse, CommunityListResponse>() {
                    @Override
                    public CommunityListResponse call(CommunityListResponse communityListResponse) {
                        return communityListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
