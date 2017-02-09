package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 08-02-2017.
 */

public class ArticleDetailPojo extends BaseResponse {
    private ArticleCardResponse articleCardResponse;
    private String id;
    public ArticleCardResponse getArticleCardResponse() {
        return articleCardResponse;
    }

    public void setArticleCardResponse(ArticleCardResponse articleCardResponse) {
        this.articleCardResponse = articleCardResponse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
