package appliedlife.pvtltd.SHEROES.basecomponents;


import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.models.InviteSearchModel;
import appliedlife.pvtltd.SHEROES.models.RequestedListModel;
import appliedlife.pvtltd.SHEROES.presenters.CommunityTagsPresenter;
import appliedlife.pvtltd.SHEROES.presenters.InvitePresenter;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.presenters.RequestedPresenter;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.Feedback_ThankyouActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.activities.Professional_WorkExperience_Activity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.activities.SettingPreferencesActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.AllMembersFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.AllSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityInviteSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityRequestedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitySearchTagsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeSpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageFullViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LoginFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonnelProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProffestionalProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileFullViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchArticleFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchJobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchRecentFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SelectCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFeedbackFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesDeactiveAccountFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesEducationDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencesWorkExperienceFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingPreferencsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingTermsAndConditionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.ArticleCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ArticleDetailHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ArticleDetailSuggestedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ArticleDetailWithInSuggestedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.BlankHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunityCardDetailHeader;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunitySuggestedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunityWithInSggestedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeatureCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedArticleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedJobHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FooterViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerFooterHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerSelectorHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.InviteSearchHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MemberHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunitiesCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.OwnerListHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ProfileViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ReactionHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.RequestedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SearchModuleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SelectDilogHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.TagsHolder;
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
    void inject(HomeActivity homeActivity);
    void inject(HomeFragment homeFragment);
    void inject(LoginActivity loginActivity);
    void inject(HomeSearchActivity homeSearchActivity);
    void inject(CommunitiesDetailActivity communitiesDetailActivity);
    void inject(ArticleDetailActivity articleDetailActivity);
    void inject(ProfileActicity profileActicity);
    void inject(LoginFragment loginFragment);
    void inject(HomeSpinnerFragment homeSpinnerFragment);
    void inject(SearchCommunitiesFragment searchCommunitiesFragment);
    void inject(ArticlesFragment articlesFragment);
    void inject(AllSearchFragment allSearchFragment);
    void inject(CommentReactionFragment commentReactionFragment);
    void inject(FeaturedFragment featuredFragment);
    void inject(MyCommunitiesFragment myCommunitiesFragment);
    void inject(CommunitiesDetailFragment communitiesDetailFragment);
    void inject(ImageFullViewFragment imageFullViewFragment);
    void inject(ArticleDetailFragment articleDetailFragment);
    void inject(ProfileFullViewFragment profileFullViewFragment);
    void inject(PersonnelProfileFragment personnelProfileFragment);
    void inject(ProffestionalProfileFragment proffestionalProfileFragment);
    void inject(BookmarksFragment bookmarksFragment);
    void inject(BlankHolder blankHolder);
    void inject(ArticleDetailHolder articleDetailHolder);
    void inject(ArticleCardHolder articleCardHolder);
    void inject(FooterViewHolder footerViewHolder);
    void inject(DrawerViewHolder drawerViewHolder);
    void inject(HomeSpinnerSelectorHolder homeSpinnerSelectorHolder);
    void inject(HomeSpinnerFooterHolder homeSpinnerFooterHolder);
    void inject(SearchModuleHolder searchModuleHolder);
    void inject(FeatureCardHolder featureCardHolder);
    void inject(FeedCommunityPostHolder feedCommunityPostHolder);
    void inject(FeedCommunityHolder feedCommunityHolder);
    void inject(FeedJobHolder feedJobHolder);
    void inject(FeedArticleHolder feedArticleHolder);
    void inject(CommentHolder commentHolder);
    void inject(ReactionHolder reactionHolder);
    void inject(MyCommunitiesCardHolder myCommunitiesCardHolder);
    void inject(ArticleDetailSuggestedHolder articleDetailSuggestedHolder);
    void inject(ArticleDetailWithInSuggestedHolder articleDetailWithInSuggestedHolder);
    void inject(ProfileViewHolder profileViewHolder);

    void inject(SettingFragment settingFragment);
    void inject(SettingFeedbackFragment settingFeedbackFragment);
    void inject(SettingPreferencsFragment settingPreferencsFragment);
    void inject(SettingAboutFragment settingAboutFragment);
    void inject(SettingTermsAndConditionFragment settingTermsAndConditionFragment);
    void inject(SettingPreferencesBasicDetailsFragment setting_preferences_basicDetailsFragment);
    void inject(SettingPreferencesActivity settingPreferencesActivity);
    void inject(SettingPreferencesEducationDetailsFragment settingPreferencesEducationDetailsFragment);
    void inject(SettingPreferencesWorkExperienceFragment settingPreferencesWorkExperienceFragment);
    void inject(SettingPreferencesDeactiveAccountFragment settingPreferencesDeactiveAccountFragment);







    void inject(CreateCommunityFragment createCommunityFragment);
    void inject(CreateCommunityActivity createCommunityActivity);
    void inject(CreateCommunityPostActivity createCommunityPostActivity);
    void inject(CreateCommunityPostFragment createCommunityPostFragment);
    void inject(SelectCommunityFragment selectCommunityFragment);
    void inject(ShareCommunityFragment shareCommunityFragment);
    void inject(SelectDilogHolder selectDilogHolder);
    void inject(CommunityCardDetailHeader communityCardDetailHeader);
    void inject(CommunitySuggestedHolder communitySuggestedHolder);
    void inject(CommunityWithInSggestedHolder communityWithInSggestedHolder);
    void inject(SearchArticleFragment searchArticleFragment);
    void inject(SearchJobFragment searchJobFragment);
    void inject(SearchRecentFragment searchRecentFragment);

    void inject(CommunityOpenAboutFragment communityOpenAboutFragment);
    void inject(OwnerPresenter ownerPresenter);
    void inject(OwnerListHolder ownerListHolder);
    void inject(AllMembersFragment allMembersFragment);
    void inject(MemberHolder memberHolder);
    void inject(MembersPresenter membersPresenter);
    void inject(Feedback_ThankyouActivity feedback_thankyouActivity);

    void inject(CommunitySearchTagsFragment communitySearchTagsFragment);
    void inject(CommunityTagsPresenter communityTagsPresenter);
    void inject(TagsHolder tagsHolder);
    void inject(CommunityRequestedFragment communityRequestedFragment);
    void inject(RequestedPresenter requestedPresenter);
    void inject(RequestedListModel tagsHolder);
    void inject(RequestedHolder requestedHolder);
    void inject(CommunityInviteSearchFragment requestedHolder);
    void inject(InvitePresenter requestedHolder);
    void inject(InviteSearchModel requestedHolder);
    void inject(InviteSearchHolder inviteSearchHolder);
    void inject(Professional_WorkExperience_Activity professional_workExperience_activity);
}