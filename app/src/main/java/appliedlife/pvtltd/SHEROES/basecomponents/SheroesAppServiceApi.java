package appliedlife.pvtltd.SHEROES.basecomponents;


import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItemResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.CommunitiesResponse;
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
    @POST("v2/588b0544300000e216fa8cfb")
    Observable<FeedResponse> getFeedFromApi(@Body ListOfFeed listOfFeed );

    @GET("v2/587877da0f0000231d0d49b1")
    Observable<HomeSpinnerItemResponse> getHomeSpinnerList();

    @GET("/v2/587fb45c270000490af0dd7a")
    Observable<CommunityListResponse> getCommunityList();

    @POST("auth/signin")
    Observable<LoginResponse> getLoginAuthToken(@Body LoginRequest loginRequest);

    @POST("v2/588eef663f00007412dde331")
    Observable<ArticleListResponse> getAricleList(@Body ArticleCardResponse articleCardResponse);
    @POST("v2/588eef663f00007412dde331")
    Observable<ArticleListResponse> getOnlyArticleList(@Body ArticleCardResponse articleCardResponse);
    @POST("v2/588eef663f00007412dde331")
    Observable<ArticleListResponse> getOnlyJobList(@Body ArticleCardResponse articleCardResponse);
    @POST("v2/587fc963270000010df0ddac")
    Observable<Feature> getFeature(@Body Feature articleRequest );
    @POST("v2/588748de100000e11f25e1ec")
    Observable<CommentResponse> getCommentFromApi(@Body CommentRequest commentRequest );
    @POST("v2/588748de100000e11f25e1ec")
    Observable<CommentResponse> getReactionFromApi(@Body CommentRequest commentRequest );

    @POST("v2/588f43133f0000d81adde412")
    Observable<CommunitiesResponse> getAllCommunities(@Body Feature feature );
}
