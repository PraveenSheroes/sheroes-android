package appliedlife.pvtltd.SHEROES.basecomponents;


import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.models.RequestedListModel;
import appliedlife.pvtltd.SHEROES.presenters.CommunityTagsPresenter;
import appliedlife.pvtltd.SHEROES.presenters.ContestPresenterImpl;
import appliedlife.pvtltd.SHEROES.presenters.CreatePostPresenter;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.presenters.RequestedPresenter;
import appliedlife.pvtltd.SHEROES.service.PushNotificationService;
import appliedlife.pvtltd.SHEROES.views.activities.AddressActivity;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestListActivity;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.activities.JobDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorInsightActivity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorsUserListingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfessionalAddEducationActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfessionalWorkExperienceActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorUserProfileDashboardActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileCommunitiesActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.AllSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOwnerSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ContestWinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.EmailVerificationFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FAQSFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ICCMemberListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.InviteCommunityOwner;
import appliedlife.pvtltd.SHEROES.views.fragments.JobDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MentorQADetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileCommunityListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileUsersCommunityListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.JobFilterDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LoginFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonalBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonalProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfessionalEditBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProffestionalProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAboutMeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAddEducationFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAddOtherFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileCityWorkFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileEditVisitingCardFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileFullViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileGoodAtFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileOpportunityTypeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileOtherFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfilePersonelHowCanLookingForFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileSearchIntrestIn;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileShareYourIntrestFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileTravelClientFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileWorkExperienceFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileWorkExperienceSelfEmploymentFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ResetPasswordFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ResetPasswordSuccessFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchArticleFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchIAmInterestIn;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchJobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchRecentFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.AllMembersDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityOptionJoinDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityRequestedDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunitySearchTagsDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityTypeDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EventDetailDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.FunctionalAreaDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.JobLocationSearchDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.OwnerRemoveDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileAddEditEducationFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileDegreeDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileImageDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileSchoolDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileSearchLanguageDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileSectoreDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStudyDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileWorkExperienceSelfEmploymentDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SearchGoodAtDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SearchProfileLocationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SelectCommunityDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SpamPostListDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.MentorProfileDetailFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.AppIntroCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ArticleCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.BellNotificationHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.BlankHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CanHelpInHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ChallengeFeedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentNewViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunityCardDetailHeader;
import appliedlife.pvtltd.SHEROES.views.viewholders.CurrentStatusHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.EducationListHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.EventCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.EventDetailHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.EventSpeakerHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.EventSponsorHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FAQViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeatureCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedArticleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedJobHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedProgressBarHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.GetAllDataBoardingSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.GoodAtHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HeaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HelplineAnswerCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HelplineQuestionCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerSelectorHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ICCMemberViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.InterestSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.InviteSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.JobDetailHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.JobLocationHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.JobLocationSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.JobSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.LookingForJobHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MemberHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MentorCard;
import appliedlife.pvtltd.SHEROES.views.viewholders.MentorSuggestedCardHorizontalView;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunitiesCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.NoCommunityHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OnBoardingCommunitiesHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OnBoardingHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OnceWelcomeCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OrgReviewCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OwnerHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OwnerListHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.PandingRequestHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.PopularTagHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileAboutMeHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileBasicDetailsHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileEducationHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileGoodAtHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileHorListHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileHorizantalListHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileHorizontalViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileIAmInterestingInHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileICanHelpWithHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileLookingForHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileOtherHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfilePersonalBasicDetailsHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileProfessionalBasicDetailsHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileWorkExperienceHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.RequestedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SearchModuleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SelectDilogHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.TagSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.TagsHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.UserHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.UserPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.WorkExperienceCardHolder;
import dagger.Component;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: All app activities,fragment and components will be inject to app.
 */
@Singleton
@Component(modules = {SheroesAppModule.class})
public interface SheroesAppComponent {
    void inject(MixpanelHelper mixpanelHelper);

    void inject(HomeActivity homeActivity);

    void inject(HomeFragment homeFragment);

    void inject(LoginActivity loginActivity);

    void inject(HomeSearchActivity homeSearchActivity);

    void inject(CommunitiesDetailActivity communitiesDetailActivity);

    void inject(ProfileActicity profileActicity);

    void inject(LoginFragment loginFragment);

    void inject(ArticleCategorySpinnerFragment articleCategorySpinnerFragment);

    void inject(SearchCommunitiesFragment searchCommunitiesFragment);

    void inject(ArticlesFragment articlesFragment);

    void inject(AllSearchFragment allSearchFragment);

    void inject(FeaturedFragment featuredFragment);

    void inject(MyCommunitiesFragment myCommunitiesFragment);

    void inject(CommunitiesDetailFragment communitiesDetailFragment);

    void inject(ProfileCommunitiesActivity profileCommunitiesActivity);

    void inject(ProfileUsersCommunityListFragment profileUsersCommunityListFragment);

    void inject(ProfileFullViewFragment profileFullViewFragment);

    void inject(PersonalProfileFragment personalProfileFragment);

    void inject(ProffestionalProfileFragment proffestionalProfileFragment);

    void inject(ProfileCommunityListFragment profile_communityListFragment);

    void inject(BookmarksFragment bookmarksFragment);

    void inject(BlankHolder blankHolder);

    void inject(ArticleCardHolder articleCardHolder);

    void inject(DrawerViewHolder drawerViewHolder);

    void inject(HomeSpinnerSelectorHolder homeSpinnerSelectorHolder);

    void inject(SearchModuleHolder searchModuleHolder);

    void inject(FeatureCardHolder featureCardHolder);

    void inject(FeedCommunityPostHolder feedCommunityPostHolder);

    void inject(FeedJobHolder feedJobHolder);

    void inject(FeedArticleHolder feedArticleHolder);

    void inject(NoCommunityHolder noCommunityHolder);

    void inject(MyCommunitiesCardHolder myCommunitiesCardHolder);


    void inject(ProfileViewHolder profileViewHolder);



    void inject(InterestSearchHolder interestSearchHolder);

    void inject(JobSearchHolder jobSearchHolder);

    void inject(ProfileWorkExperienceHolder profileWorkExperienceHolder);

    void inject(ProfileProfessionalBasicDetailsHolder profileProfessionalBasicDetailsHolder);

    void inject(ProfileEducationHolder profileEducationHolder);

    void inject(ProfileOtherHolder profileOtherHolder);

    void inject(ProfileHorizantalListHolder profileHorListHolder);

    void inject(ProfileHorizontalViewHolder profileHorizontalViewHolder);

    void inject(ProfileGoodAtHolder profileGoodAtHolder);

    void inject(ProfileLookingForHolder profileLookingForHolder);

    void inject(ProfileICanHelpWithHolder profileICanHelpWithHolder);

    void inject(ProfileAboutMeHolder profileAboutMeHolder);

    void inject(ProfilePersonalBasicDetailsHolder profilePersonalBasicDetailsHolder);

    void inject(ProfileIAmInterestingInHolder profileIAmInterestingInHolder);

    void inject(SelectCommunityDialogFragment selectCommunityFragment);

    void inject(ShareCommunityFragment shareCommunityFragment);

    void inject(SelectDilogHolder selectDilogHolder);

    void inject(CommunityCardDetailHeader communityCardDetailHeader);

    void inject(SearchArticleFragment searchArticleFragment);

    void inject(SearchJobFragment searchJobFragment);

    void inject(SearchRecentFragment searchRecentFragment);

    void inject(CommunityOpenAboutFragment communityOpenAboutFragment);

    void inject(OwnerPresenter ownerPresenter);

    void inject(OwnerListHolder ownerListHolder);

    void inject(AllMembersDialogFragment allMembersFragment);

    void inject(MemberHolder memberHolder);

    void inject(MembersPresenter membersPresenter);

    void inject(CommunityTagsPresenter communityTagsPresenter);

    void inject(TagsHolder tagsHolder);

    void inject(CommunityRequestedDialogFragment communityRequestedFragment);

    void inject(RequestedPresenter requestedPresenter);

    void inject(RequestedListModel tagsHolder);

    void inject(RequestedHolder requestedHolder);

    void inject(CommunityOwnerSearchFragment communityOwnerSearchFragment);

    void inject(InviteSearchHolder inviteSearchHolder);


    void inject(JobFilterDialogFragment jobFilterDialogFragment);


    void inject(JobLocationHolder jobLocationHolder);


    void inject(ProfessionalWorkExperienceActivity professional_workExperience_activity);

    void inject(JobFragment jobFragment);

    void inject(JobDetailActivity jobDetailActivity);

    void inject(JobDetailFragment jobDetailFragment);

    void inject(JobDetailHolder jobDetailHolder);

    void inject(OnBoardingActivity onBoardingActivity);

    void inject(OnBoardingFragment onBoardingFragment);

    void inject(GetAllDataBoardingSearchHolder getAllDataBoardingSearchHolder);

    void inject(OnBoardingHolder onBoardingHolder);

    void inject(ProfileTravelClientFragment profileTravelClientFragment);

    void inject(ProfileCityWorkFragment profileCityWorkFragment);

    void inject(CurrentStatusHolder currentStatusHolder);

    void inject(ProfileAddEducationFragment profileAddEducationFragment);

    void inject(ProfileWorkExperienceFragment profileWorkExperienceFragment);


    void inject(ProfileOtherFragment profileOtherFragment);

    void inject(ProfessionalAddEducationActivity professionalAddEducationActivity);

    void inject(ProfileAddOtherFragment profileAddOtherFragment);

    void inject(EditUserProfileActivity editUserProfileActivity);

    void inject(ProfessionalEditBasicDetailsFragment professionalEditBasicDetailsFragment);

    void inject(ProfileAboutMeFragment profileAboutMeFragment);

    void inject(JobLocationSearchHolder jobLocationSearchHolder);

    void inject(ProfileBasicDetailsHolder profileBasicDetailsHolder);

    void inject(ProfileHorListHolder profileHorListHolder);

    void inject(ProfileWorkExperienceSelfEmploymentFragment profileWorkExperienceSelfEmploymentFragment);

    void inject(ProfileWorkExperienceSelfEmploymentDialogFragment profileWorkExperienceSelfEmploymentDialogFragment);

    void inject(InviteCommunityOwner inviteCommunityMember);

    void inject(UserHolder userHolder);

    void inject(OwnerHolder ownerHolder);

    void inject(OwnerRemoveDialog ownerRemoveDialog);


    void inject(TagSearchHolder tagSearchHolder);

    void inject(CommunitySearchTagsDialogFragment communitySearchTagsDialogFragment);


    void inject(ProfilePersenter profilePersenter);

    void inject(WelcomeActivity welcomeActivity);


    void inject(CommunityTypeDialogFragment communityTypeFragment);

    void inject(CommunityOptionJoinDialog communityOptionJoinDialog);

    void inject(PopularTagHolder popularTagHolder);

    void inject(PersonalBasicDetailsFragment personalBasicDetailsFragment);

    void inject(ProfileEditVisitingCardFragment profileEditVisitingCardFragment);

    void inject(ProfileGoodAtFragment profileGoodAtFragment);

    void inject(ProfileOpportunityTypeFragment profileOpportunityTypeFragment);

    void inject(ProfileShareYourIntrestFragment profileShareYourIntrestFragment);

    void inject(SearchGoodAtDialogFragment searchGoodAt);

    void inject(GoodAtHolder goodAtHolder);

    void inject(JobLocationSearchDialogFragment jobLocationSearchDialogFragment);

    void inject(PandingRequestHolder pandingRequestHolder);
    // void inject(CommunityJoinFromeAboutDailog communityJoinFromeAboutDailog);


    void inject(ProfileSearchIntrestIn profileSearchIntrestIn);

    void inject(SearchIAmInterestIn searchIAmInterestIn);

    void inject(CanHelpInHolder canHelpInHolder);

    void inject(SearchProfileLocationDialogFragment searchProfileLocation);

    void inject(ProfileAddEditEducationFragment profileAddEditEducationFragment);

    void inject(BellNotificationDialogFragment bellNotificationDialogFragment);

    void inject(BellNotificationHolder bellNotificationHolder);

    void inject(ProfileSearchLanguageDialogFragment profileSearchLanguage);

    void inject(ProfileDegreeDialog profileDegreeDialog);

    void inject(ProfileSectoreDialog profileSectoreDialog);

    void inject(ProfileStudyDialog profileStudyDialog);

    void inject(ProfileSchoolDialog profileSchoolDialog);

    void inject(WorkExperienceCardHolder workExperienceCardHolder);

    void inject(FunctionalAreaDialogFragment functionalAreaDialogFragment);

    void inject(EducationListHolder educationListHolder);

    void inject(ProfilePersonelHowCanLookingForFragment profilePersonelHowCanLookingForFragment);

    void inject(HelplineFragment helplineFragment);

    void inject(HelplineQuestionCardHolder helplineQuestionCardHolder);

    void inject(HelplineAnswerCardHolder helplineAnswerCardHolder);


    void inject(ICCMemberViewHolder iccMemberViewHolder);

    void inject(ICCMemberListFragment iccMemberListFragment);

    void inject(FAQSFragment faqsFragment);

    void inject(FAQViewHolder faqViewHolder);

    void inject(OnceWelcomeCardHolder onceWelcomeCardHolder);

    void inject(EventDetailDialogFragment eventDetailDialogFragment);

    void inject(EventDetailHolder eventDetailHolder);

    void inject(EventSpeakerHolder eventSpeakerHolder);

    void inject(EventSponsorHolder eventSponsorHolder);

    void inject(EmailVerificationFragment emailVerificationFragment);

    void inject(ResetPasswordFragment resetPasswordFragment);

    void inject(ResetPasswordSuccessFragment resetPasswordSuccessFragment);

    void inject(ProfileImageDialogFragment profileImageDialogFragment);

    void inject(AppIntroCardHolder appIntroCardHolder);


    void inject(MentorUserProfileDashboardActivity mentorUserProfileDashboardActivity);

    void inject(UserProfileTabFragment userProfileTabFragment);

    void inject(SpamPostListDialogFragment spamPostListDialogFragment);

    void inject(FeedProgressBarHolder feedProgressBarHolder);

    void inject(EventCardHolder eventCardHolder);

    void inject(SheroesDeepLinkingActivity sheroesDeepLinkingActivity);

    void inject(OrgReviewCardHolder orgReviewCardHolder);

    void inject(AlbumActivity albumActivity);

    void inject(ArticleActivity articleActivity);

    void inject(CommunityPostActivity communityPostActivity);

    void inject(CreatePostPresenter createPostPresenter);

    void inject(PostBottomSheetFragment postBottomSheetFragment);

    void inject(HeaderViewHolder headerViewHolder);

    void inject(PushNotificationService pushNotificationService);

    void inject(LookingForJobHolder lookingForJobHolder);

    void inject(ContestActivity contestActivity);

    void inject(ContestPresenterImpl contestPresenter);

    void inject(ContestListActivity contestListActivity);

    void inject(ContestWinnerFragment contestWinnerFragment);

    void inject(ChallengeFeedHolder challengeFeedHolder);

    void inject(AddressActivity addressActivity);

    void inject(LikeListBottomSheetFragment likeListBottomSheetFragment);

    void inject(PostDetailActivity postDetailActivity);

    void inject(UserPostHolder userPostHolder);

    void inject(CommentNewViewHolder commentNewViewHolder);

    void inject(NavigateToWebViewFragment webUrlFragment);

    void inject(MentorSuggestedCardHorizontalView mentorSuggestedCardHorizontalView);

    void inject(MentorCard mentorCard);

    void inject(MentorsUserListingActivity mentorsUserListingActivity);

    void inject(MentorProfileDetailFragment mentorProfileDetailFragment);

    void inject(MentorQADetailFragment mentorQADetailFragment);

    void inject(MentorInsightActivity mentorInsightActivity);

    void inject(OnBoardingCommunitiesHolder onBoardingCommunitiesHolder);

}