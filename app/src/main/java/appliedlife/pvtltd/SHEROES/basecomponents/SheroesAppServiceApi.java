package appliedlife.pvtltd.SHEROES.basecomponents;


import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
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
    @POST("participant/auth/signin")
    Observable<LoginResponse> getLoginAuthToken(@Body LoginRequest loginRequest);
    @POST("participant/user/fbsignup")
    Observable<LoginResponse> getFbSignUpToken(@Body LoginRequest loginRequest);
    @GET("participant/auth/refresh")
    Observable<LoginResponse> getRefreshToken();
    @POST("participant/community/join")
    Observable<CommunityResponse> getCommunityJoinResponse(@Body CommunityRequest communityRequest);
    @POST("participant/settings/save_feedback")
    Observable<SettingFeedbackResponce> getSettingAuthToken(@Body SettingFeedbackRequest feedbackRequest);
    @POST("participant/settings/save_rating")
    Observable<SettingRatingResponse> getUserRatingAuthToken(@Body SettingRatingRequest ratingRequest);
    @POST("participant/settings/deactivate_account")
    Observable<SettingDeActivateResponse> getUserDeactiveAuthToken(@Body SettingDeActivateRequest deActivateRequest);
    @POST("participant/settings/get_user_preferences")
    Observable<UserpreferenseResponse>getUserPreferenceAuthToken(@Body UserPreferenceRequest userPreferenceRequest);




}
