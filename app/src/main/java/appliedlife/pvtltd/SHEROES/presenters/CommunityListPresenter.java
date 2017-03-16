package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommunityListModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;

/**
 * Created by Ajit Kumar on 23-01-2017.
 */

public class CommunityListPresenter extends BasePresenter<CommunityView> {
private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
        CommunityListModel communityListModel;
        SheroesApplication sheroesApplication;
@Inject
Preference<LoginResponse> userPreference;
@Inject
public CommunityListPresenter(CommunityListModel communityListModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
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
