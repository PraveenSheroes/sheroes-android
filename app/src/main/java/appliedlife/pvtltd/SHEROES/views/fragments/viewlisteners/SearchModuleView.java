package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleRequest;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.FeatResponse;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public interface SearchModuleView extends BaseMvpView {
    void getArticleListSuccess(List<ArticleRequest> data);
    void getSuccess(List<FeatResponse> data);
    void showNwError();
}
