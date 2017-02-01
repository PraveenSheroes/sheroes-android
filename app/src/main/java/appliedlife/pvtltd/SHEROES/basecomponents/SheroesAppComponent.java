package appliedlife.pvtltd.SHEROES.basecomponents;


import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.DetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ShareCommunityActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.AllSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeSpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LoginFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SelectCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.ArticleCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeatureCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedArticleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedJobHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FooterViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerFooterHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerSelectorHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunitiesCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ReactionHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SearchModuleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SelectDilogHolder;
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
    void inject(DetailActivity detailActivity);
    void inject(LoginFragment loginFragment);
    void inject(HomeSpinnerFragment homeSpinnerFragment);
    void inject(CommunitiesFragment communitiesFragment);
    void inject(ArticlesFragment articlesFragment);
    void inject(AllSearchFragment allSearchFragment);
    void inject(CommentReactionFragment commentReactionFragment);
    void inject(FeaturedFragment featuredFragment);
    void inject(MyCommunitiesFragment myCommunitiesFragment);
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
    void inject(CreateCommunityFragment createCommunityFragment);
    void inject(CreateCommunityActivity createCommunityActivity);
    void inject(CreateCommunityPostActivity createCommunityPostActivity);
    void inject(CreateCommunityPostFragment createCommunityPostFragment);
    void inject(SelectCommunityFragment selectCommunityFragment);
    void inject(ShareCommunityActivity shareCommunityActivity);
    void inject(ShareCommunityFragment shareCommunityFragment);
    void inject(SelectDilogHolder selectDilogHolder);
}
