package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommunityTagsModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityTagsView;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityTagsPresenter extends BasePresenter<CommunityTagsView> {
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    CommunityTagsModel communityListModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    public CommunityTagsPresenter(CommunityTagsModel communityListModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.communityListModel = communityListModel;
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
