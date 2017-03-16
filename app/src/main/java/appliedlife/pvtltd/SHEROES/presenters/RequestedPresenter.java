package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.RequestedListModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.RequestedView;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class RequestedPresenter extends BasePresenter<RequestedView> {
    private final String TAG = LogUtils.makeLogTag(SearchModulePresenter.class);
    RequestedListModel requestedListModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    public RequestedPresenter(RequestedListModel memberListModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.requestedListModel = memberListModel;
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