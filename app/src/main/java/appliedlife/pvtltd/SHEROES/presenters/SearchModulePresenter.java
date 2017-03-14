package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.SearchModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SearchModuleView;

/**
 * Created by Praveen Singh on 18-01-2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 18-01-2017.
 * Title:
 */
public class SearchModulePresenter extends BasePresenter<SearchModuleView> {
    private final String TAG = LogUtils.makeLogTag(SearchModulePresenter.class);
    SearchModel mSearchModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    public SearchModulePresenter(SearchModel mSearchModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
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



/*

    public void getFeedFromPresenter(Fe listOfFeed) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mSearchModel.getSearchFromModel(listOfFeed).subscribe(new Subscriber<SearchResponse>() {
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
            public void onNext(SearchResponse searchResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSearchListSuccess(searchResponse.getListOfFeed());
            }
        });
        registerSubscription(subscription);
    }



    public void getFeature(Feature articleRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mSearchModel.getFeat(articleRequest).subscribe(new Subscriber<Feature>() {
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
            public void onNext(Feature articleListResponse) {
                getMvpView().stopProgressBar();
            }
        });
        registerSubscription(subscription);
    }*/
    public void onStop() {
        detachView();
    }
}