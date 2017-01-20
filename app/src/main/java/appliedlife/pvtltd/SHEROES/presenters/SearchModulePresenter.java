package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.SearchModel;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleRequest;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.preferences.Token;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SearchModuleView;
import rx.Subscriber;
import rx.Subscription;

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
    Preference<Token> mUserPreference;
    @Inject
    public SearchModulePresenter(SearchModel mSearchModel, SheroesApplication mSheroesApplication, Preference<Token> mUserPreference) {
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


    public void getSearchPresenterArticleList(ArticleRequest articleRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mSearchModel.getSearchModelArticleList(articleRequest).subscribe(new Subscriber<ArticleListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(ArticleListResponse articleListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getArticleListSuccess(articleListResponse.getData());
            }
        });
        registerSubscription(subscription);
    }
    public void getSearchPresenterOnlyArticleList(ArticleRequest articleRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mSearchModel.getSearchModelOnlyArticleList(articleRequest).subscribe(new Subscriber<ArticleListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(ArticleListResponse articleListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getArticleListSuccess(articleListResponse.getData());
            }
        });
        registerSubscription(subscription);
    }
    public void getSearchPresenterOnlyJobList(ArticleRequest articleRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mSearchModel.getSearchModelOnluJobList(articleRequest).subscribe(new Subscriber<ArticleListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(ArticleListResponse articleListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getArticleListSuccess(articleListResponse.getData());
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
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(Feature articleListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccess(articleListResponse.getData());
            }
        });
        registerSubscription(subscription);
    }
    public void onStop() {
        detachView();
    }
}