package appliedlife.pvtltd.SHEROES.basecomponents;


import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeAcceptRequest;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.ApproveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventDetailPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyRequest;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.SignupRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.GooglePlusRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.GooglePlusResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingInterestRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingJobAtRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingLookingForHowCanRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingTellUsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingWorkExpRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileAddEditEducationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFLexibilityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserPreferenceRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareMailResponse;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareViaMail;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
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
    Observable<FeedResponsePojo> getFeedFromApi(@Body FeedRequestPojo feedRequestPojo);

    @POST("participant/feed/my_communities")
    Observable<FeedResponsePojo> getMyCommunityFromApi(@Body MyCommunityRequest myCommunityRequest);

    @POST("participant/feed/get_bookmarked")
    Observable<FeedResponsePojo> getBookMarkFromApi(@Body FeedRequestPojo feedRequestPojo);

    /*Participation*/
    @POST("participation/reaction/like")
    Observable<LikeResponse> getLikesFromApi(@Body LikeRequestPojo likeRequestPojo);

    @POST("participation/reaction/unlike")
    Observable<LikeResponse> getUnLikesFromApi(@Body LikeRequestPojo likeRequestPojo);

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

    @POST("participant/community/create")
    Observable<CreateCommunityResponse> postCreateCommunity(@Body CreateCommunityRequest createCommunityRequest);

    @POST("entity/master/suggest_community")
    Observable<SelectedCommunityResponse> suggestedCommunity(@Body SelectCommunityRequest selectCommunityRequest);

    @POST("participant/community/edit")
    Observable<CreateCommunityResponse> postEditCommunity(@Body EditCommunityRequest editCommunityRequest);

    @POST("participation/post/add")
    Observable<CreateCommunityResponse> createCommunityPost(@Body CommunityPostCreateRequest communityPostCreateRequest);

    @POST("participation/post/edit")
    Observable<CreateCommunityResponse> editCommunityPost(@Body CommunityPostCreateRequest communityPostCreateRequest);

    @POST("participant/community/create_owner")
    Observable<CreateCommunityOwnerResponse> postCreateCommunityOwner(@Body CreateCommunityOwnerRequest createCommunityOwnerRequest);

    @POST("participant/community/owners_list")
    Observable<OwnerListResponse> getOwnerList(@Body OwnerListRequest ownerListResponse);

    @POST("participant/community/deactivate_owner")
    Observable<DeactivateOwnerResponse> getOwnerDeactivate(@Body DeactivateOwnerRequest deactivateOwnerRequest);

    @POST("participant/community/member_list")
    Observable<MemberListResponse> getMemberList(@Body MemberRequest membersList);

    @POST("participant/job/apply")
    Observable<JobApplyResponse> getJobApply(@Body JobApplyRequest jobApplyRequest);

    @POST("participant/community/pending_request")
    Observable<RequestedListResponse> getRequestList(@Body MemberRequest memberRequest);

    @POST("entity/master/get_data")
    Observable<GetTagData> getTagFromApi(@Body GetAllDataRequest getAllDataRequest);

    @POST("participant/settings/save_feedback")
    Observable<SettingFeedbackResponce> getSettingAuthToken(@Body SettingFeedbackRequest feedbackRequest);

    @POST("participant/settings/save_rating")
    Observable<SettingRatingResponse> getUserRatingAuthToken(@Body SettingRatingRequest ratingRequest);

    @POST("participant/settings/deactivate_account")
    Observable<SettingDeActivateResponse> getUserDeactiveAuthToken(@Body SettingDeActivateRequest deActivateRequest);

    @POST("participant/settings/get_user_preferences")
    Observable<UserpreferenseResponse> getUserPreferenceAuthToken(@Body UserPreferenceRequest userPreferenceRequest);

    @POST("entity/master/all_data")
    Observable<MasterDataResponse> getOnBoardingMasterDataFromApi();

    @POST("entity/master/get_data")
    Observable<GetAllData> getOnBoardingSearchFromApi(@Body GetAllDataRequest getAllDataRequest);

    @POST("entity/master/get_data")
    Observable<GetInterestJobResponse> getInterestJobSearchFromApi(@Body GetAllDataRequest getAllDataRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getCurrentStatusFromApi(@Body BoardingTellUsRequest boardingTellUsRequest);

    @POST("participant/user/gcmIdChange")
    Observable<GcmIdResponse> getNewGCMidFromApi(@Body LoginRequest loginRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getLookingForHowCanFromApi(@Body BoardingLookingForHowCanRequest boardingLookingForHowCanRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getJobAtFromApi(@Body BoardingJobAtRequest boardingJobAtRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getWorkExpFromApi(@Body BoardingWorkExpRequest boardingJobAtRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getInterestFromApi(@Body BoardingInterestRequest boardingInterestRequest);

    @POST("participant/settings/change_user_preference")
    Observable<SettingChangeUserPreferenseResponse> getUserChangePreferenceAuthToken(@Body SettingChangeUserPreferenceRequest settingChangeUserPreferenceRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getEducationAuthToken(@Body ProfileAddEditEducationRequest profileAddEditEducationRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getPersonalBasicDetailsAuthToken(@Body PersonalBasicDetailsRequest personalBasicDetailsRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getProfessionalTravelDetailsAuthToken(@Body ProfileTravelFLexibilityRequest profileTravelFLexibilityRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getPersonalUserSummaryDetailsAuthToken(@Body UserSummaryRequest userSummaryRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getProfessionalDetailsAuthToken(@Body ProfessionalBasicDetailsRequest professionalBasicDetailsRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getWorkLocationDetailsAuthToken(@Body ProfilePreferredWorkLocationRequest profilePreferredWorkLocationRequest);

    @POST("participant/user/get_visiting_card_details")
    Observable<ProfileEditVisitingCardResponse> getEditVisitingCardDetailsAuthToken();

    @POST("participant/user/participant/user/get_visiting_card")
    Observable<ProfileEditVisitingCardResponse> getSaveVisitingCardDetailsAuthToken(@Body GetUserVisitingCardRequest getUserVisitingCardRequest);

    @POST("participant/user/get_all_details")
    Observable<UserProfileResponse> getUserDetails();

    @POST("participant/community/unjoin")
    Observable<MemberListResponse> removeMember(@Body RemoveMemberRequest removeMemberRequest);

    @POST("participant/community/reject_joining_request")
    Observable<MemberListResponse> removePandingMember(@Body RemoveMemberRequest removeMemberRequest);

    @POST("participant/community/approve_joining_request")
    Observable<MemberListResponse> approvePandingMember(@Body ApproveMemberRequest approveMemberRequest);

    @POST("participant/community/invite")
    Observable<ShareMailResponse> shareCommunityViaMail(@Body ShareViaMail shareViaMail);

    @POST("participation/notification/bell")
    Observable<BelNotificationListResponse> bellNotification(@Body BellNotificationRequest bellNotificationRequest);

    @POST("participant/user/add_or_edit")
    Observable<BoardingDataResponse> getWorkExpAddEditResponse(@Body ExprienceEntity exprienceEntity);

    @POST("participation/notification/bell/unread")
    Observable<NotificationReadCountResponse> notificationReadCount(@Body NotificationReadCount notificationReadCount);

    @POST("participation/challenge/get/challenges")
    Observable<ChallengeListResponse> challengeList(@Body ChallengeRequest challengeRequest);

    @POST("participation/challenge/accept")
    Observable<ChallengeListResponse> challengeAccept(@Body ChallengeAcceptRequest challengeAcceptRequest);

    @POST("http://www.mocky.io/v2/59450b4113000053055b6f4b")
    Observable<EventDetailPojo> getEventData(@Body EventRequest eventRequest);

    @POST(BuildConfig.GOOGLE_PLUS_URL)
    Observable<GooglePlusResponse> getUserGoogleLogin(@Body GooglePlusRequest loginRequest);

    @GET
    Observable<ExpireInResponse> getGoogleTokenExpire(@Url String url);

    @POST("participation/helpline/post_question")
    Observable<HelplinePostQuestionResponse> postHelplineQuestion(@Body HelplinePostQuestionRequest helplinePostQuestionRequest);

    @POST("participation/helpline/get_thread_details")
    Observable<HelplineGetChatThreadResponse> getHelplineChatDetails(@Body HelplineGetChatThreadRequest helplineGetChatThreadRequest);

    @POST("participant/user/signup")
    Observable<LoginResponse> userSignup(@Body SignupRequest signupRequest);
    @POST("participant/user/getUserSummarry")
    Observable<LoginResponse> googlePlusUserResponse();

    @POST("entity/she/all_faqs")
    Observable<FAQSResponse> getAllSHEFAQS(@Body FAQSRequest faqsRequest);

    @POST("entity/she/all_icc_members")
    Observable<ICCMemberListResponse> getAllSHEICCMemberList(@Body ICCMemberRequest iccMemberRequest);
}
