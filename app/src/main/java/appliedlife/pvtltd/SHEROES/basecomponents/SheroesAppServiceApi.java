package appliedlife.pvtltd.SHEROES.basecomponents;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.Response;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTagsListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.InviteSearchResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItemResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.CommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
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
    /*Participant*/
    @POST("participant/feed/")
    Observable<FeedResponsePojo> getFeedFromApi(@Body FeedRequestPojo feedRequestPojo );
    @POST("participant/feed/get_bookmarked")
    Observable<FeedResponsePojo> getBookMarkFromApi(@Body FeedRequestPojo feedRequestPojo);

    /*Participation*/
    @POST("participation/reaction/like")
    Observable<LikeResponse> getLikesFromApi(@Body LikeRequestPojo likeRequestPojo );
    @POST("participation/reaction/unlike")
    Observable<LikeResponse> getUnLikesFromApi(@Body LikeRequestPojo likeRequestPojo );
    @POST("participation/reaction/get_comments")
    Observable<CommentReactionResponsePojo> getCommentFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);
    @POST("participation/reaction/get_reactions")
    Observable<CommentReactionResponsePojo> getAllReactionFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);
    @POST("participation/reaction/add_comment")
    Observable<CommentReactionResponsePojo> addCommentFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);
    @POST("participation/reaction/edit_comment")
    Observable<CommentReactionResponsePojo> editCommentFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);
    @POST("participation/reaction/bookmark")
    Observable<BookmarkResponsePojo> addBookMarkToApi(@Body BookmarkRequestPojo bookmarkResponsePojo );
    @POST("participation/reaction/unbookmark")
    Observable<BookmarkResponsePojo> UnBookMarkToApi(@Body BookmarkRequestPojo bookmarkResponsePojo );


    @GET("v2/587877da0f0000231d0d49b1")
    Observable<HomeSpinnerItemResponse> getHomeSpinnerList();

    @GET("/v2/587fb45c270000490af0dd7a")
    Observable<CommunityListResponse> getCommunityList();
    @GET("/v2/587fb45c270000490af0dd7a")
    Observable<CommunityTagsListResponse> getCommunityTagList();

    @GET("/v2/587fb45c270000490af0dd7a")
    Observable<OwnerListResponse> getOwnerList();

    @GET("/v2/587fb45c270000490af0dd7a")
    Observable<MemberListResponse> getMemberList();

    @GET("/v2/587fb45c270000490af0dd7a")
    Observable<RequestedListResponse> getRequestList();

    @POST("participant/auth/signin")
    Observable<LoginResponse> getLoginAuthToken(@Body LoginRequest loginRequest);

    @POST("v2/588eef663f00007412dde331")
    Observable<ArticleListResponse> getAricleList(@Body ArticleCardResponse articleCardResponse);
    @POST("v2/588eef663f00007412dde331")
    Observable<ArticleListResponse> getOnlyArticleList(@Body ArticleCardResponse articleCardResponse);
    @POST("v2/588eef663f00007412dde331")
    Observable<ArticleListResponse> getOnlyJobList(@Body ArticleCardResponse articleCardResponse);
    @POST("v2/587fc963270000010df0ddac")
    Observable<Feature> getFeature(@Body Feature articleRequest );

    @POST("v2/588f43133f0000d81adde412")
    Observable<CommunitiesResponse> getAllCommunities(@Body Feature feature );

    @GET("v2/589874931100000e07038a52")
    Observable<Response<List<RecentSearchData>>> getMasterData();

    @POST("v2/58940613260000a11200a97f")
    Observable<InviteSearchResponse> getInviteSearchResponseFromApi(@Body ListOfInviteSearch listOfInviteSearch );


    @POST("settings/saveFeedback")
    Observable<SettingFeedbackResponce> getSettingAuthToken(@Body SettingFeedbackRequest feedbackRequest);
    @POST("settings/saveRating")
    Observable<SettingRatingResponse> getUserRatingAuthToken(@Body SettingRatingRequest ratingRequest);
    @POST("settings/deactivateAccount")
    Observable<SettingDeActivateResponse> getUserDeactiveAuthToken(@Body SettingDeActivateRequest deActivateRequest);
    @POST("settings/changeUserPreference")
    Observable<UserpreferenseResponse> getUserPreferenceAuthToken(@Body UserPreferenceRequest userPreferenceRequest);
}
