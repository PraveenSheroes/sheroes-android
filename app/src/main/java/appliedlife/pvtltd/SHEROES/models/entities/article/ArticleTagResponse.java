package appliedlife.pvtltd.SHEROES.models.entities.article;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen on 19/06/18.
 */

public class ArticleTagResponse extends BaseResponse {
    @SerializedName("tags")
    private List<ArticleTagName> tags = null;

    public List<ArticleTagName> getTags() {
        return tags;
    }

    public void setTags(List<ArticleTagName> tags) {
        this.tags = tags;
    }
}
