package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.OwnerListModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;

/**
 * Created by SHEROES-TECH on 03-02-2017.
 */

public class OwnerPresenter extends BasePresenter<CommunityView> {
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    OwnerListModel ownerListModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    public OwnerPresenter(OwnerListModel communityListModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.ownerListModel = communityListModel;
        this.sheroesApplication=sheroesApplication;
        this.userPreference=userPreference;
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