package appliedlife.pvtltd.SHEROES.basecomponents;


import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.ConfigurationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorInsightResponse;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
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
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.WinnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UpLoadImageResponse;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UploadImageRequest;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.ContactListSyncRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
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
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.models.entities.post.WinnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopCountRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareMailResponse;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareViaMail;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.DeactivateUserRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import io.reactivex.Observable;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

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
    Observable<FeedResponsePojo> getFeedFromApi(@Body FeedRequestPojo feedRequestPojo);

    @POST("participant/user/get_user_profile_top_section_count")
    Observable<ProfileTopSectionCountsResponse> getProfileTopSectionCounts(@Body ProfileTopCountRequest profileTopCountRequest);

    @POST("participant/feed/followed_mentor_list")
    Observable<UserFollowedMentorsResponse> getFollowerOrFollowing(@Body FollowersFollowingRequest profileFollowedMentor);

    @POST("participant/feed/mutual_communities")
    Observable<ProfileCommunitiesResponsePojo> getUsersCommunity(@Body ProfileUsersCommunityRequest profileUsersCommunityRequest);

    @POST("participant/feed/my_communities")
    Observable<ProfileCommunitiesResponsePojo> getPublicProfileUsersCommunity(@Body ProfileUsersCommunityRequest profileUsersCommunityRequest);

    @POST("participant/feed/")
    Observable<FeedResponsePojo> getNewFeedFromApi(@Body FeedRequestPojo feedRequestPojo);

    @POST("participant/user/get_all_details")
    Observable<UserProfileResponse> getUserDetails();

    @POST("participant/user/get_mentor_insights")
    Observable<MentorInsightResponse> getMentorInsightFromApi(@Body MentorFollowerRequest mentorFollowerRequest);

    @POST("participation/reaction/follow")
    Observable<MentorFollowUnfollowResponse> getMentorFollowFromApi(@Body PublicProfileListRequest publicProfileListRequest);

    @POST("/participation/reaction/unfollow")
    Observable<MentorFollowUnfollowResponse> getMentorUnFollowFromApi(@Body PublicProfileListRequest publicProfileListRequest);

    @POST("participant/feed/my_communities")
    Observable<FeedResponsePojo> getMyCommunityFromApi(@Body MyCommunityRequest myCommunityRequest);

    @POST("participant/feed/all_communities")
    Observable<AllCommunitiesResponse> getAllCommunityFromApi(@Body MyCommunityRequest myCommunityRequest);

    @POST("participant/feed/get_bookmarked")
    Observable<FeedResponsePojo> getBookMarkFromApi(@Body FeedRequestPojo feedRequestPojo);

    /*Participation*/
    @POST("participation/reaction/like")
    Observable<LikeResponse> getLikesFromApi(@Body LikeRequestPojo likeRequestPojo);

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

    @POST("participant/user/fbVerification")
    Observable<LoginResponse> getFBVerification(@Body LoginRequest loginRequest);

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

    @POST("entity/master/suggest_community")
    Observable<SelectedCommunityResponse> suggestedCommunity(@Body SelectCommunityRequest selectCommunityRequest);

    @POST("participant/community/edit")
    Observable<CreateCommunityResponse> postEditCommunity(@Body EditCommunityRequest editCommunityRequest);

    @POST("participation/link/render")
    Observable<LinkRenderResponse> linkRenderApi(@Body LinkRequest linkRequest);

    @POST("participation/post/add")
    Observable<CreateCommunityResponse> createCommunityPost(@Body CommunityPostCreateRequest communityPostCreateRequest);

    @POST("participation/post/edit")
    Observable<CreateCommunityResponse> editCommunityPost(@Body CommunityPostCreateRequest communityPostCreateRequest);

    @POST("participation/post/edit")
    Observable<CreateCommunityResponse> topPostCommunityPost(@Body CommunityTopPostRequest communityPostCreateRequest);

    @POST("participant/community/create_owner")
    Observable<CreateCommunityOwnerResponse> postCreateCommunityOwner(@Body CreateCommunityOwnerRequest createCommunityOwnerRequest);


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

    @POST("participant/community/invite")
    Observable<ShareMailResponse> shareCommunityViaMail(@Body ShareViaMail shareViaMail);

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

    @POST("participant/user/getUserSummarry")
    Observable<LoginResponse> googlePlusUserResponse();

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

    @GET("participant/remote_config/?config_type=AppConfig")
    Observable<ConfigurationResponse> getConfig();

    @GET("participant/feed/community_feed?")
    Observable<FeedResponsePojo> getChallengeResponse(@Query("sub_type") String subType, @Query("source_entity_id") String sourceEntityId);

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

    @POST("participation/article/image/add")
    Observable<UpLoadImageResponse> uploadImage(@Body UploadImageRequest uploadImageRequest);
}
