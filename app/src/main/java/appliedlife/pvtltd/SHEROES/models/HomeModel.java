package appliedlife.pvtltd.SHEROES.models;


import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItemResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.CommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Hotel model class interact with Hotel presenter.
 * required response data for Home activity.
 */
@Singleton
public class HomeModel {
    private final String TAG = LogUtils.makeLogTag(HomeModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    Preference<SessionUser> userPreference;
    @Inject
    public HomeModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo  feedRequestPojo){
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<FeedResponsePojo> getBookMarkFromModel(FeedRequestPojo feedRequestPojo){
        return sheroesAppServiceApi.getBookMarkFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BookmarkResponsePojo> addBookmarkFromModel(BookmarkRequestPojo bookmarkRequestPojo,boolean isBookmarked){
        if(!isBookmarked) {
            return sheroesAppServiceApi.addBookMarkToApi(bookmarkRequestPojo)
                    .map(new Func1<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo call(BookmarkResponsePojo bookmarkResponsePojo) {
                            return bookmarkResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        else
        {
            return sheroesAppServiceApi.UnBookMarkToApi(bookmarkRequestPojo)
                    .map(new Func1<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo call(BookmarkResponsePojo bookmarkResponsePojo) {
                            return bookmarkResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public Observable<LikeResponse> getLikesFromModel(LikeRequestPojo  likeRequestPojo){
        return sheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                .map(new Func1<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse call(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<LikeResponse> getUnLikesFromModel(LikeRequestPojo  likeRequestPojo){
        return sheroesAppServiceApi.getUnLikesFromApi(likeRequestPojo)
                .map(new Func1<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse call(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<HomeSpinnerItemResponse>getSpinnerListFromModel(){
        return sheroesAppServiceApi.getHomeSpinnerList()
                .map(new Func1<HomeSpinnerItemResponse, HomeSpinnerItemResponse>() {
                    @Override
                    public HomeSpinnerItemResponse call(HomeSpinnerItemResponse homeSpinnerItemResponse) {
                        return homeSpinnerItemResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<ArticleListResponse> getHomeModelArticleList(ArticleCardResponse articleCardResponse){
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
    public Observable<CommunitiesResponse> getHomeModelCommnutiesList(Feature feature){
        return sheroesAppServiceApi.getAllCommunities(feature)
                .map(new Func1<CommunitiesResponse, CommunitiesResponse>() {
                    @Override
                    public CommunitiesResponse call(CommunitiesResponse communitiesResponse1) {
                        return communitiesResponse1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
