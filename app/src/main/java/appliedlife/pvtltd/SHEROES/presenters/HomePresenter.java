package appliedlife.pvtltd.SHEROES.presenters;


import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItemResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.CommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.preferences.Token;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Hotel presenter perform required response data for Home activity.
 */
public class HomePresenter extends BasePresenter<HomeView>  {
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    HomeModel mHomeModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<Token> mUserPreference;
    @Inject
    public HomePresenter(HomeModel homeModel, SheroesApplication sheroesApplication,Preference<Token> userPreference) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication =sheroesApplication;
        this.mUserPreference =userPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }


    public void getFeedFromPresenter(ListOfFeed listOfFeed) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getFeedFromModel(listOfFeed).subscribe(new Subscriber<FeedResponse>() {
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
            public void onNext(FeedResponse feedResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getFeedListSuccess(feedResponse.getListOfFeed());
            }
        });
        registerSubscription(subscription);
    }
    public void getSpinnerListFromPresenter() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getSpinnerListFromModel().subscribe(new Subscriber<HomeSpinnerItemResponse>() {
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
            public void onNext(HomeSpinnerItemResponse homeSpinnerItemResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getHomeSpinnerListSuccess(homeSpinnerItemResponse.getHomeSpinnerItem());
            }
        });
        registerSubscription(subscription);
    }
    public void getHomePresenterArticleList(ArticleCardResponse articleCardResponse) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getHomeModelArticleList(articleCardResponse).subscribe(new Subscriber<ArticleListResponse>() {
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
                getMvpView().getArticleListSuccess(articleListResponse.getArticleCardResponses());
            }
        });
        registerSubscription(subscription);
    }
    public void getHomePresenterCommunitiesList(Feature feature) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getHomeModelCommnutiesList(feature).subscribe(new Subscriber<CommunitiesResponse>() {
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
            public void onNext(CommunitiesResponse communitiesResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getAllCommunitiesSuccess(communitiesResponse.getMyCommunitiesCardResponses(),communitiesResponse.getFeatureCardResponses());
            }
        });
        registerSubscription(subscription);
    }
    public void onStop() {
        detachView();
    }
}