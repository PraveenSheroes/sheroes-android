package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.MyCommunities;

public interface HomeView extends BaseMvpView {
    void getFeedListSuccess(List<FeedDetail> feedDetailList);
    void getHomeSpinnerListSuccess(List<HomeSpinnerItem> data);
    void getArticleListSuccess(List<ArticleCardResponse> data);
    void getAllCommunitiesSuccess(List<MyCommunities> myCommunities,List<Feature> features);
    void getDB(List<MasterData> masterDatas);
    void showNwError();
}
