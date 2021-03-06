package appliedlife.pvtltd.SHEROES.basecomponents;


import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.analytics.CleverTapHelper;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.presenters.ContestPresenterImpl;
import appliedlife.pvtltd.SHEROES.presenters.CreatePostPresenter;
import appliedlife.pvtltd.SHEROES.service.PushNotificationService;
import appliedlife.pvtltd.SHEROES.utils.ErrorUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.LogOutUtils;
import appliedlife.pvtltd.SHEROES.viewholder.ContestFlatViewHolder;
import appliedlife.pvtltd.SHEROES.viewholder.HeaderTaggedUserViewHolder;
import appliedlife.pvtltd.SHEROES.viewholder.UserPostCompactViewHolder;
import appliedlife.pvtltd.SHEROES.views.activities.AddressActivity;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.AllContactActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.BadgeClosetActivity;
import appliedlife.pvtltd.SHEROES.views.activities.BranchDeepLink;
import appliedlife.pvtltd.SHEROES.views.activities.ChallengeGratificationActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ChampionListingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CollectionActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestListActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateStoryActivity;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.FollowingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.LanguageSelectionActivity;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileCommunitiesActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.UsersCollectionActivity;
import appliedlife.pvtltd.SHEROES.views.activities.WebViewActivity;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkAndApiErrorDialog;
import appliedlife.pvtltd.SHEROES.views.errorview.OnBoardingMsgDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ContactListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ContestWinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FAQSFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FollowedCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FollowingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.GenderInputFormDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HashTagFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ICCMemberListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SuggestedFriendFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SuperSheroesCriteriaFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserGridFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ChallengeWinnerPopUpDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.DeactivateProfileDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EmailVerificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MaleErrorDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileImageDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ReportUserProfileDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ResetPasswordDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ResetPasswordSuccessDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SearchProfileLocationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SelectLanguageDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.UnFollowDialogFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.AppIntroCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ArticleCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.BellNotificationHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.BlankHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ChallengeFeedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentNewViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ContactCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FAQViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedArticleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedPollCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedProgressBarHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.GetAllDataBoardingSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HelplineAnswerCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HelplineQuestionCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeHeaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerSelectorHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ICCMemberViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ImageViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.LeaderBoardViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunitiesDrawerViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunitiesViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.NoCommunityHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.NoStoriesHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OnBoardingCommunitiesHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OnceWelcomeCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.PollTypesViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SuggestedContactCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.UserMentionCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.UserPostHolder;
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

    void inject(LoginActivity loginActivity);

    void inject(ArticleCategorySpinnerFragment articleCategorySpinnerFragment);

    void inject(ArticlesFragment articlesFragment);

    void inject(CommunitiesListFragment myCommunitiesFragment);

    void inject(FollowedCommunitiesFragment communitiesFragment);

    void inject(FollowingFragment followingFragment);

    void inject(ProfileCommunitiesActivity profileCommunitiesActivity);

    void inject(BookmarksFragment bookmarksFragment);

    void inject(BlankHolder blankHolder);

    void inject(ArticleCardHolder articleCardHolder);

    void inject(DrawerViewHolder drawerViewHolder);

    void inject(HomeSpinnerSelectorHolder homeSpinnerSelectorHolder);

    void inject(MyCommunitiesViewHolder myCommunitiesViewHolder);

    void inject(CollectionActivity collectionActivity);

    void inject(FeedCommunityPostHolder feedCommunityPostHolder);

    void inject(FeedArticleHolder feedArticleHolder);

    void inject(NoCommunityHolder noCommunityHolder);

    void inject(OnBoardingActivity onBoardingActivity);

    void inject(FollowingActivity followingActivity);

    void inject(EditUserProfileActivity editUserProfileActivity);

    void inject(WelcomeActivity welcomeActivity);

    void inject(SearchProfileLocationDialogFragment searchProfileLocation);

    void inject(BellNotificationDialogFragment bellNotificationDialogFragment);

    void inject(BellNotificationHolder bellNotificationHolder);

    void inject(HelplineFragment helplineFragment);

    void inject(HelplineQuestionCardHolder helplineQuestionCardHolder);

    void inject(HelplineAnswerCardHolder helplineAnswerCardHolder);

    void inject(ICCMemberViewHolder iccMemberViewHolder);

    void inject(ICCMemberListFragment iccMemberListFragment);

    void inject(FAQSFragment faqsFragment);

    void inject(FAQViewHolder faqViewHolder);

    void inject(GetAllDataBoardingSearchHolder getAllDataBoardingSearchHolder);

    void inject(OnceWelcomeCardHolder onceWelcomeCardHolder);

    void inject(EmailVerificationDialogFragment emailVerificationDialogFragment);

    void inject(ResetPasswordDialogFragment resetPasswordDialogFragment);

    void inject(ResetPasswordSuccessDialogFragment resetPasswordSuccessDialogFragment);

    void inject(ProfileImageDialogFragment profileImageDialogFragment);

    void inject(AppIntroCardHolder appIntroCardHolder);

    void inject(FeedProgressBarHolder feedProgressBarHolder);

    void inject(SheroesDeepLinkingActivity sheroesDeepLinkingActivity);

    void inject(AlbumActivity albumActivity);

    void inject(ArticleActivity articleActivity);

    void inject(CommunityPostActivity communityPostActivity);

    void inject(CreatePostPresenter createPostPresenter);

    void inject(PostBottomSheetFragment postBottomSheetFragment);

    void inject(HomeHeaderViewHolder homeHeaderViewHolder);

    void inject(PushNotificationService pushNotificationService);

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

    void inject(CarouselViewHolder mentorSuggestedCardHorizontalView);

    void inject(ChampionListingActivity championListingActivity);

    void inject(ProfileActivity profileActivity);

    void inject(ProfileDetailsFragment profileDetailsFragment);

    void inject(CommunityDetailActivity communityDetailActivity);

    void inject(FeedFragment feedFragment);

    void inject(OnBoardingCommunitiesHolder onBoardingCommunitiesHolder);

    void inject(OnBoardingFragment onBoardingFragment);

    void inject(ShareBottomSheetFragment shareBottomSheetFragment);

    void inject(ChallengeGratificationActivity challengeGratificationActivity);

    void inject(UserPostCompactViewHolder userPostCompactViewHolder);

    void inject(ImageViewHolder imageViewHolder);

    void inject(AllContactActivity allContactActivity);

    void inject(SuggestedFriendFragment suggestedFriendFragment);

    void inject(ContactListFragment contactListFragment);

    void inject(ContactCardHolder contactCardHolder);

    void inject(SuggestedContactCardHolder suggestedContactCardHolder);

    void inject(UserMentionCardHolder userMentionCardHolder);

    void inject(HeaderTaggedUserViewHolder headerTaggedUserViewHolder);

    void inject(ContestFlatViewHolder contestFlatViewHolder);

    void inject(AppInstallation appInstallation);

    void inject(AppInstallationHelper appInstallationHelper);

    void inject(CreateStoryActivity createStoryActivity);

    void inject(FeedAdapter feedAdapter);

    void inject(GenderInputFormDialogFragment genderInputFormDialogFragment);

    void inject(ProfileStrengthDialog profileStrengthDialog);

    void inject(MyCommunitiesDrawerViewHolder myCommunitiesDrawerViewHolder);

    void inject(HomeFragment homeFragment);

    void inject(WebViewActivity webViewActivity);

    void inject(CleverTapHelper cleverTapHelper);

    void inject(BadgeClosetActivity badgeClosetActivity);

    void inject(BadgeDetailsDialogFragment badgeDetailsDialogFragment);

    void inject(SuperSheroesCriteriaFragment superSheroesCriteriaFragment);

    void inject(LeaderBoardViewHolder leaderBoardViewHolder);

    void inject(NoStoriesHolder noStoriesHolder);

    void inject(ChallengeWinnerPopUpDialog challengeWinnerPopUpDialog);

    void inject(PollTypesViewHolder pollTypesViewHolder);

    void inject(FeedPollCardHolder feedPollCardHolder);

    void inject(UsersCollectionActivity usersCollectionActivity);

    void inject(UserGridFragment userGridFragment);

    void inject(LanguageSelectionActivity languageSelectionActivity);

    void inject(SelectLanguageDialog selectLanguageDialog);

    void inject(MaleErrorDialog maleErrorDialog);

    void inject(FeedUtils feedUtils);

    void inject(LogOutUtils logOutUtils);

    void inject(ErrorUtil errorUtil);

    void inject(NetworkAndApiErrorDialog networkAndApiErrorDialog);

    void inject(OnBoardingMsgDialog onBoardingMsgDialog);

    void inject(UnFollowDialogFragment unFollowDialogFragment);

    void inject(DeactivateProfileDialogFragment deactivateProfileDialogFragment);

    void inject(ReportUserProfileDialogFragment reportUserProfileDialogFragment);

    void inject(SearchFragment searchFragment);

    void inject(HashTagFragment hashTagFragment);

    void inject(ProfileFragment profileFragment);

    void inject(BranchDeepLink branchDeepLink);
}


