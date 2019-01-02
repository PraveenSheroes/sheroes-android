package appliedlife.pvtltd.SHEROES.basecomponents;


import java.util.List;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.analytics.Impression.ImpressionResponse;
import appliedlife.pvtltd.SHEROES.analytics.Impression.UserEvents;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.ConfigurationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.ChampionFollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleTagResponse;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.ChallengePostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTopPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.WinnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UpLoadImageResponse;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UploadImageRequest;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.ContactListSyncRequest;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UpdateInviteUrlRequest;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UpdateInviteUrlResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationDrawerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationItems;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatePollRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatePollResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.DeletePollRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollVote;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollVoteResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.models.entities.post.WinnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.DeactivateUserRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.vernacular.LanguageUpdateRequest;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: All network calls api will be register here and all get ,post will be redirect .
 */
public interface SheroesAppServiceApi {
    /*Participant*/
    @POST("participant/feed/")
    Observable<FeedResponsePojo> getFeedFromApi(@Body FeedRequestPojo feedRequestPojo);

    @POST("participant/feed/followed_mentor_list")
    Observable<FollowedUsersResponse> getFollowerOrFollowing(@Body FollowersFollowingRequest profileFollowedMentor);

    @POST("participant/feed/mutual_communities")
    Observable<ProfileCommunitiesResponsePojo> getUsersCommunity(@Body ProfileUsersCommunityRequest profileUsersCommunityRequest);

    @POST("participant/feed/my_communities")
    Observable<ProfileCommunitiesResponsePojo> getPublicProfileUsersCommunity(@Body ProfileUsersCommunityRequest profileUsersCommunityRequest);

    @POST("participant/user/get_all_details")
    Observable<UserProfileResponse> getUserDetails();

    @POST("participation/reaction/follow")
    Observable<ChampionFollowedResponse> getMentorFollowFromApi(@Body PublicProfileListRequest publicProfileListRequest);

    @POST("/participation/reaction/unfollow")
    Observable<ChampionFollowedResponse> getMentorUnFollowFromApi(@Body PublicProfileListRequest publicProfileListRequest);

    @POST("participant/feed/my_communities")
    Observable<FeedResponsePojo> getMyCommunityFromApi(@Body MyCommunityRequest myCommunityRequest);

    @POST("participant/feed/all_communities")
    Observable<AllCommunitiesResponse> getAllCommunityFromApi(@Body MyCommunityRequest myCommunityRequest);

    @POST("participant/feed/get_bookmarked")
    Observable<FeedResponsePojo> getBookMarkFromApi(@Body FeedRequestPojo feedRequestPojo);

    /*Participation*/
    @POST("participation/reaction/like")
    Observable<LikeResponse> getLikesFromApi(@Body LikeRequestPojo likeRequestPojo);

    @POST("participation/reaction/poll/vote")
    Observable<PollVoteResponse> getPollVoteFromApi(@Body PollVote pollVote);

    @POST("participation/reaction/unlike")
    Observable<LikeResponse> getUnLikesFromApi(@Body LikeRequestPojo likeRequestPojo);

    @POST("participant/user/sync_app_user_contacts")
    Observable<AllContactListResponse> getAllFriendsInSyncResponseFromApi(@Body ContactListSyncRequest contactListSyncRequest);

    @POST("participation/reaction/get_comments")
    Observable<CommentReactionResponsePojo> getCommentFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);

    @POST("participation/reaction/get_reactions")
    Observable<CommentReactionResponsePojo> getAllReactionFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);

    @POST("participation/reaction/add_comment")
    Observable<CommentAddDelete> addCommentFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);

    @POST("participation/reaction/edit_comment")
    Observable<CommentAddDelete> editCommentFromApi(@Body CommentReactionRequestPojo commentReactionRequestPojo);

    @POST("participation/reaction/bookmark")
    Observable<BookmarkResponsePojo> addBookMarkToApi(@Body BookmarkRequestPojo bookmarkResponsePojo);

    @POST("participation/reaction/unbookmark")
    Observable<BookmarkResponsePojo> UnBookMarkToApi(@Body BookmarkRequestPojo bookmarkResponsePojo);

    @POST("participant/auth/signin")
    Observable<LoginResponse> getLoginAuthToken(@Body LoginRequest loginRequest);

    @POST("participant/user/fbsignup")
    Observable<LoginResponse> getFbSignUpToken(@Body LoginRequest loginRequest);

    @POST("participant/user/gpsignup")
    Observable<LoginResponse> getGpSignUpToken(@Body LoginRequest loginRequest);

    @GET("participant/auth/refresh")
    Observable<LoginResponse> getRefreshToken();

    @POST("participant/community/join")
    Observable<CommunityResponse> getCommunityJoinResponse(@Body CommunityRequest communityRequest);

    @POST("participation/post/delete")
    Observable<DeleteCommunityPostResponse> getCommunityPostDeleteResponse(@Body DeleteCommunityPostRequest deleteCommunityPostRequest);

    @POST("participation/reaction/mark_spam")
    Observable<BookmarkResponsePojo> markAsSpam(@Body BookmarkRequestPojo bookmarkResponsePojo);

    @POST("participation/link/render")
    Observable<LinkRenderResponse> linkRenderApi(@Body LinkRequest linkRequest);

    @Multipart
    @POST("participation/post/add_post_with_multipart_image")
    Observable<CreateCommunityResponse> createCommunityMultiPartPost(@PartMap Map<String, RequestBody> params, @Part("postContent") CommunityPostCreateRequest communityPostCreateRequest);

    @Multipart
    @POST("participation/post/edit_post_with_multipart_image")
    Observable<CreateCommunityResponse> editCommunityMultiPartPost(@PartMap Map<String, RequestBody> params, @Part("postContent") CommunityPostCreateRequest communityPostCreateRequest);

    @POST("participation/poll/add")
    Observable<CreatePollResponse> createPoll(@Body CreatePollRequest createPollRequest);

    @POST("participation/poll/delete")
    Observable<CreatePollResponse> deletePoll(@Body DeletePollRequest deletePollRequest);

    @POST("participation/post/edit")
    Observable<CreateCommunityResponse> topPostCommunityPost(@Body CommunityTopPostRequest communityPostCreateRequest);

    @POST("entity/master/all_data")
    Observable<MasterDataResponse> getOnBoardingMasterDataFromApi();

    @POST("entity/master/get_data")
    Observable<GetAllData> getOnBoardingSearchFromApi(@Body GetAllDataRequest getAllDataRequest);

    @POST("participant/user/gcmIdChange")
    Observable<GcmIdResponse> getNewGCMidFromApi(@Body LoginRequest loginRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getPersonalBasicDetailsAuthToken(@Body PersonalBasicDetailsRequest personalBasicDetailsRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getPersonalUserSummaryDetailsAuthToken(@Body UserSummaryRequest userSummaryRequest);

    @POST("participant/community/unjoin")
    Observable<MemberListResponse> removeMember(@Body RemoveMemberRequest removeMemberRequest);

    @POST("participation/notification/bell")
    Observable<BelNotificationListResponse> bellNotification(@Body BellNotificationRequest bellNotificationRequest);

    @POST("participation/notification/bell/unread")
    Observable<NotificationReadCountResponse> notificationReadCount(@Body NotificationReadCount notificationReadCount);

    @POST("participation/post/approve_or_delete")
    Observable<ApproveSpamPostResponse> spamPostApprove(@Body ApproveSpamPostRequest approveSpamPostRequest);

    @GET
    Observable<ExpireInResponse> getGoogleTokenExpire(@Url String url);

    @POST("participation/helpline/post_question")
    Observable<HelplinePostQuestionResponse> postHelplineQuestion(@Body HelplinePostQuestionRequest helplinePostQuestionRequest);

    @POST("participation/helpline/get_thread_details")
    Observable<HelplineGetChatThreadResponse> getHelplineChatDetails(@Body HelplineGetChatThreadRequest helplineGetChatThreadRequest);

    @POST("/participation/helpline/rated")
    Observable<BaseResponse> postHelplineRating(@Body HelplinePostRatingRequest helplinePostRatingRequest);

    @POST("entity/she/all_faqs")
    Observable<FAQSResponse> getAllSHEFAQS(@Body FAQSRequest faqsRequest);

    @POST("entity/she/all_icc_members")
    Observable<ICCMemberListResponse> getAllSHEICCMemberList(@Body ICCMemberRequest iccMemberRequest);

    @POST("participant/user/email_verification_resend")
    Observable<EmailVerificationResponse> emailVerificationResponse(@Body EmailVerificationRequest emailVerificationRequest);

    @POST("participant/user/reset_password")
    Observable<ForgotPasswordResponse> forgotPasswordResponse(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("/entity/master/app_intro_screen")
    Observable<AppIntroScreenResponse> appIntroScreen(@Body AppIntroScreenRequest appIntroScreenRequest);

    @POST("participation/challenge/v2/response")
    Observable<CreateCommunityResponse> createChallengePost(@Body ChallengePostCreateRequest challengePostCreateRequest);

    @POST("/participant/appmenu/")
    Observable<NavigationItems> getNavigationDrawerItems(@Body NavigationDrawerRequest navItems);

    @POST("participation/challenge/get/winners")
    Observable<WinnerResponse> getWinners(@Body WinnerRequest winnerRequest);

    @POST("participation/challenge/add/winner/address")
    Observable<BaseResponse> updateAddress(@Body Address address);

    @POST()
    Observable<AllContactListResponse> getUserDetailList(@Url String url, @Body ContactListSyncRequest co);

    @POST()
    Observable<FeedResponsePojo> getCommunityFeed(@Url String url, @Body CommunityFeedRequestPojo communityFeedRequestPojo);

    @POST("participant/feed/community_category_home")
    Observable<FeedResponsePojo> fetchAllCommunities(@Body BaseRequest baseRequest);

    @GET("participant/remote_config/AppConfig")
    Observable<ConfigurationResponse> getConfig();

    @POST("participant/user/refresh_user_app_invite_url")
    Observable<UpdateInviteUrlResponse> updateInviteUrl(@Body UpdateInviteUrlRequest updateInviteUrlRequest);

    @POST("participant/user/update_user_device")
    Observable<AppInstallation> saveInstallation(@Body AppInstallation appInstallation);

    //Spam Post
    @POST("participation/post/spam_report")
    Observable<SpamResponse> reportSpamPostOrComment(@Body SpamPostRequest spamPostRequest);

    @POST("participant/user/spam_report")
    Observable<SpamResponse> reportProfile(@Body SpamPostRequest spamPostRequest);

    @POST("participation/post/approve_or_delete_comment")
    Observable<SpamResponse> approveSpamComment(@Body ApproveSpamPostRequest approveSpamPostRequest);

    @POST("entity/master/user_mention_suggestions")
    Observable<SearchUserDataResponse> userMentionSuggestion(@Body SearchUserDataRequest searchUserDataRequest);

    //deactivate user - for admin, community moderators
    @POST("participant/user/deactivate_or_reactivate")
    Observable<BaseResponse> deactivateUser(@Body DeactivateUserRequest deactivateUserRequest);

    @POST("participation/image/add")
    Observable<UpLoadImageResponse> uploadImage(@Body UploadImageRequest uploadImageRequest);

    @POST("participation/article/story/add")
    Observable<ArticleSubmissionResponse> submitArticle(@Body ArticleSubmissionRequest articleSubmissionRequest);

    @POST("participation/article/story/edit")
    Observable<ArticleSubmissionResponse> editArticle(@Body ArticleSubmissionRequest articleSubmissionRequest);

    @POST("participation/article/story/delete")
    Observable<ArticleSubmissionResponse> deleteArticle(@Body ArticleSubmissionRequest articleSubmissionRequest);

    @GET("entity/master/get_tags")
    Observable<ArticleTagResponse> getArticleTags();

    @POST("participant/feed/stream?setOrderKey=UserStoryStream")
    Observable<FeedResponsePojo> getUserStory(@Query("id") String article_id, @Body FeedRequestPojo feedRequestPojo);

    @POST("participant/feed/stream?setOrderKey=PollStream")
    Observable<FeedResponsePojo> getPollDetail(@Query("poll_id") String poll_id, @Body FeedRequestPojo feedRequestPojo);

    @POST("participation/global/image/add")
    Observable<UpLoadImageResponse> uploadImageForAnyModule(@Body UploadImageRequest uploadImageRequest);

    @Headers("X-Producer-Authorization: " + BuildConfig.IMPRESSION_AUTH)
    @POST(BuildConfig.IMPRESSION_URL + "user/event/producer")
    Single<ImpressionResponse> updateImpressionData(@Body UserEvents userEventsContainer);

    @POST("participant/user/update_user_preference")
    Observable<BaseResponse> updateSelectedLanguage(@Body LanguageUpdateRequest languageUpdateRequest);

    @GET
    Observable<FeedResponsePojo> getSearchResponse(@Url String url);

    @GET("participant/search/getHashtags")
    Observable<List<String>> fetchTrendingHashtags();
}
