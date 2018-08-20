package appliedlife.pvtltd.SHEROES.models.entities.imageUpload;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ujjwal on 12/06/18.
 */

public class UploadImageRequest extends BaseRequest {
    @SerializedName("images")
    public List<String> images;

    @SerializedName("sheroes_page_id")
    public Long sheroesPageId;

    @SerializedName("title")
    public String title;

    @SerializedName("designation")
    public String designation;

    @SerializedName("fb_link_url")
    public String fbLinkUrl;

    @SerializedName("twitter_link_url")
    public String twitterLinkUrl;

    @SerializedName("linked_in_url")
    public String linkedInUrl;

    @SerializedName("gp_link_url")
    public String gpLinkUrl;

    @SerializedName("is_active")
    public Boolean isActive;

    @SerializedName("image_link_url")
    public String imageLinkUrl;

    @SerializedName("description")
    public String description;

    @SerializedName("faq_category_id")
    public Long faqCategoryId;

    @SerializedName("join_date")
    public String joinDate;

    @SerializedName("image_priority")
    public Integer imagePriority;

    @SerializedName("community_id")
    public Long communityId;
}
