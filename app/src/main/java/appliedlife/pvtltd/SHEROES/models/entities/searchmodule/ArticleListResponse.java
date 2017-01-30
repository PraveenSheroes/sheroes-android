package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class ArticleListResponse {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("articleCardResponses")
    @Expose
    private List<ArticleCardResponse> articleCardResponses = new ArrayList<ArticleCardResponse>();


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ArticleCardResponse> getArticleCardResponses() {
        return articleCardResponses;
    }

    public void setArticleCardResponses(List<ArticleCardResponse> articleCardResponses) {
        this.articleCardResponses = articleCardResponses;
    }


}