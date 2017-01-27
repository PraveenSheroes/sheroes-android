package appliedlife.pvtltd.SHEROES.basecomponents;


import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.views.activities.DetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.AllSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeSpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LoginFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.CollectionHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeatureHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedArticleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedJobHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FooterViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerFooterHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeSpinnerSelectorHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ReactionHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SearchModuleHolder;
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
    void inject(JobsFragment jobsFragment);
    void inject(AllSearchFragment allSearchFragment);
    void inject(CommentReactionFragment commentReactionFragment);
    void inject(CollectionHolder collectionHolder);
    void inject(FooterViewHolder footerViewHolder);
    void inject(DrawerViewHolder drawerViewHolder);
    void inject(HomeSpinnerSelectorHolder homeSpinnerSelectorHolder);
    void inject(HomeSpinnerFooterHolder homeSpinnerFooterHolder);
    void inject(SearchModuleHolder searchModuleHolder);
    void inject(FeatureHolder featureHolder);
    void inject(FeedCommunityPostHolder feedCommunityPostHolder);
    void inject(FeedCommunityHolder feedCommunityHolder);
    void inject(FeedJobHolder feedJobHolder);
    void inject(FeedArticleHolder feedArticleHolder);
    void inject(CommentHolder commentHolder);
    void inject(ReactionHolder reactionHolder);
}
