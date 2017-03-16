package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.InviteSearchModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.InviteSearchView;

/**
 * Created by SHEROES-TECH on 08-02-2017.
 */

public class InvitePresenter extends BasePresenter<InviteSearchView> {
    private final String TAG = LogUtils.makeLogTag(SearchModulePresenter.class);
    InviteSearchModel mSearchModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    public InvitePresenter(InviteSearchModel mSearchModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.mSearchModel = mSearchModel;
        this.mSheroesApplication = mSheroesApplication;
        this.mUserPreference = mUserPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }








    public void onStop() {
        detachView();
    }
}