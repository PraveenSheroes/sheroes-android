package appliedlife.pvtltd.SHEROES.models;


import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
@Singleton
public class AppInstallationModel {
    private final String TAG = LogUtils.makeLogTag(AppInstallationModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public AppInstallationModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
    }

    public Observable<AppInstallation> getAppInstallation(final AppInstallation appInstallation) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(appInstallation));
        return sheroesAppServiceApi.saveInstallation(appInstallation)
                .map(new Function<AppInstallation, AppInstallation>() {
                    @Override
                    public AppInstallation apply(AppInstallation appInstallation1) {
                        return appInstallation;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
