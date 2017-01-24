package appliedlife.pvtltd.SHEROES.basecomponents;


import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItemResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleRequest;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: All network calls api whill be register here and all get ,post will be redirect .
 */
public interface SheroesAppServiceApi {

    //@GET("v1/city")
    @POST("v2/588604790f0000cd37ff6684")
    Observable<FeedResponse> getFeedFromApi(@Body ListOfFeed listOfFeed );

    @GET("v2/587877da0f0000231d0d49b1")
    Observable<HomeSpinnerItemResponse> getHomeSpinnerList();

    @POST("auth/signin")
    Observable<LoginResponse> getLoginAuthToken(@Body LoginRequest loginRequest);

    @POST("v2/587fb45c270000490af0dd7a")
    Observable<ArticleListResponse> getAricleList(@Body ArticleRequest articleRequest );
    @POST("v2/587fc963270000010df0ddac")
    Observable<ArticleListResponse> getOnlyAricleList(@Body ArticleRequest articleRequest );
    @POST("v2/587fb49c2700004f0af0dd7c")
    Observable<ArticleListResponse> getOnlyJobList(@Body ArticleRequest articleRequest );
    @POST("v2/587fc963270000010df0ddac")
    Observable<Feature> getFeature(@Body Feature articleRequest );
    @POST("v2/58871aaf100000e51b25e15e")
    Observable<CommentResponse> getCommentFromApi(@Body CommentRequest commentRequest );
}
