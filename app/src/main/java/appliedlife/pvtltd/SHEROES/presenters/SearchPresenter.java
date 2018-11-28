package appliedlife.pvtltd.SHEROES.presenters;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ISearchView;

public class SearchPresenter extends BasePresenter<ISearchView> {

    SheroesApplication mSheroesApplication;
    private SheroesAppServiceApi mSheroesAppServiceApi;
    @Inject
    AppUtils mAppUtils;

    @Inject
    public SearchPresenter(AppUtils appUtils, SheroesAppServiceApi sheroesAppServiceApi) {
        mAppUtils = appUtils;
        mSheroesAppServiceApi = sheroesAppServiceApi;
    }
}
