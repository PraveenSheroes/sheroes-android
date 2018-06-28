package appliedlife.pvtltd.SHEROES.models.entities.article;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;

/**
 * Created by Praveen on 13/06/18.
 */
@Parcel(analyze = {ArticleSubmissionResponse.class,BaseResponse.class})
public class ArticleSubmissionResponse extends BaseResponse {
    @SerializedName("story")
    private ArticleSolrObj articleSolrObj;

    public ArticleSolrObj getArticleSolrObj() {
        return articleSolrObj;
    }

    public void setArticleSolrObj(ArticleSolrObj articleSolrObj) {
        this.articleSolrObj = articleSolrObj;
    }
}
