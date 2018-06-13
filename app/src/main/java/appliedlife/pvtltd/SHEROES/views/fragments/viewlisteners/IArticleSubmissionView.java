package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;

/**
 * Created by ujjwal on 06/09/17.
 */

public interface IArticleSubmissionView extends BaseMvpView {
    void showImage(String finalImageUrl);

    void hideProgressBar();

    void goBack();

    void setupEditArticleView(ArticleSolrObj articleSolrObj);

    void showMessage(int stringID);

    void navigateToMyArticleList();
}
