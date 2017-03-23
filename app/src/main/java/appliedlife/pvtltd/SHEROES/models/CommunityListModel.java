package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;

/**
 * Created by Ajit Kumar on 23-01-2017.
 */

public class CommunityListModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public CommunityListModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }

}
