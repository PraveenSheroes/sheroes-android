package appliedlife.pvtltd.SHEROES.models;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ListOfSearch;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.SearchResponse;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * Created by Praveen Singh on 18-01-2017.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 18-01-2017.
 * Title: .
 */
public class SearchModel {
    private final String TAG = LogUtils.makeLogTag(SearchModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    Preference<SessionUser> userPreference;
    @Inject
    public SearchModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<SearchResponse> getSearchFromModel(ListOfSearch listOfSearch){
        return sheroesAppServiceApi.getSearchResponseFromApi(listOfSearch)
                .map(new Func1<SearchResponse, SearchResponse>() {
                    @Override
                    public SearchResponse call(SearchResponse searchResponse) {
                        return searchResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ArticleListResponse> getSearchModelArticleList(ArticleCardResponse articleCardResponse){
        return sheroesAppServiceApi.getAricleList(articleCardResponse)
                .map(new Func1<ArticleListResponse, ArticleListResponse>() {
                    @Override
                    public ArticleListResponse call(ArticleListResponse articleListResponse) {
                        return articleListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ArticleListResponse> getSearchModelOnlyArticleList(ArticleCardResponse articleCardResponse){
        return sheroesAppServiceApi.getOnlyArticleList(articleCardResponse)
                .map(new Func1<ArticleListResponse, ArticleListResponse>() {
                    @Override
                    public ArticleListResponse call(ArticleListResponse articleListResponse) {
                        return articleListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ArticleListResponse> getSearchModelOnluJobList(ArticleCardResponse articleCardResponse){
        return sheroesAppServiceApi.getOnlyJobList(articleCardResponse)
                .map(new Func1<ArticleListResponse, ArticleListResponse>() {
                    @Override
                    public ArticleListResponse call(ArticleListResponse articleListResponse) {
                        return articleListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<Feature> getFeat(Feature articleRequest){
        return sheroesAppServiceApi.getFeature(articleRequest)
                .map(new Func1<Feature, Feature>() {
                    @Override
                    public Feature call(Feature articleListResponse) {
                        return articleListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}