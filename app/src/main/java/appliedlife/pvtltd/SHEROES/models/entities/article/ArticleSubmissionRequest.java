package appliedlife.pvtltd.SHEROES.models.entities.article;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen on 13/06/18.
 */

public class ArticleSubmissionRequest extends BaseRequest {
    @SerializedName("id")
    public Long articleId;

    @SerializedName("article_category_id")
    public Long articleCategoryId;

    @SerializedName("is_public_story")
    public boolean isPublicStory;

    @SerializedName("is_publish")
    public boolean isPublish;

    @SerializedName("is_active")
    public boolean isActive;

    @SerializedName("story_title")
    public String storyTitle;

    @SerializedName("story_content")
    public String storyContent;

    @SerializedName("thumbnail_image_url")
    public String thumbImageUrl;

    @SerializedName("cover_image_url")
    public String coverImageUrl;

    @SerializedName("tag_ids")
    public List<Long> tagIds = null;

    @SerializedName("deleted_tag_ids")
    public List<Long> deletedTagIds;

    @SerializedName("community_ids")
    public List<Long> communityIds = null;
}
