package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 22-03-2017.
 */

public class CommunityPostCreateRequest extends BaseRequest{

    @SerializedName("entity_start_date")
    @Expose
    private String entityStartDate;
    @SerializedName("og_link_url")
    @Expose
    private String linkUrl;

    @SerializedName("id")
    @Expose
    private Long id=null;
    @SerializedName("community_id")
    @Expose
    private Long communityId=null;
    @SerializedName("creator_type")
    @Expose
    private String creatorType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("delete_image_id")
    @Expose
    private List<Long> deleteImagesIds = null;
    @SerializedName("is_active")
    @Expose
    private boolean isActive =true;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(String creatorType) {
        this.creatorType = creatorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Long> getDeleteImagesIds() {
        return deleteImagesIds;
    }

    public void setDeleteImagesIds(List<Long> deleteImagesIds) {
        this.deleteImagesIds = deleteImagesIds;
    }

    public String getEntityStartDate() {
        return entityStartDate;
    }

    public void setEntityStartDate(String entityStartDate) {
        this.entityStartDate = entityStartDate;
    }


    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
