package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.InviteSearchModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.InviteSearchResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.InviteSearchView;
import rx.Subscriber;
import rx.Subscription;

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





    public void getFeedFromPresenter(ListOfInviteSearch listOfFeed) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mSearchModel.getSearchFromModel(listOfFeed).subscribe(new Subscriber<InviteSearchResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(InviteSearchResponse searchResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSearchListSuccess(searchResponse.getListOfFeed());
            }
        });
        registerSubscription(subscription);
    }



    public void onStop() {
        detachView();
    }
}