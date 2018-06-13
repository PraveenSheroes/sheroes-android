package appliedlife.pvtltd.SHEROES.models.entities.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen on 13/06/18.
 */

public class ArticleSubmissionRequest extends BaseRequest {
    @SerializedName("article_category_id")
    @Expose
    public Long articleCategoryId;

    @SerializedName("is_public_story")
    @Expose
    public boolean isPublicStory;

    @SerializedName("is_publish")
    @Expose
    public boolean isPublish;

    @SerializedName("story_title")
    @Expose
    public String storyTitle;

    @SerializedName("story_content")
    @Expose
    public String storyContent;

    @SerializedName("thumbnail_image_url")
    @Expose
    public String thumbImageUrl;

    @SerializedName("cover_image_url")
    @Expose
    public String coverImageUrl;

    @SerializedName("tag_ids")
    @Expose
    public List<Integer> tagIds = null;
    @SerializedName("community_ids")
    @Expose
    public List<Integer> communityIds = null;
}
