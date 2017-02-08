package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 08-02-2017.
 */

public class ArticleDetailPojo extends BaseResponse {
    private ArticleCardResponse articleCardResponse;

    public ArticleCardResponse getArticleCardResponse() {
        return articleCardResponse;
    }

    public void setArticleCardResponse(ArticleCardResponse articleCardResponse) {
        this.articleCardResponse = articleCardResponse;
    }
}
