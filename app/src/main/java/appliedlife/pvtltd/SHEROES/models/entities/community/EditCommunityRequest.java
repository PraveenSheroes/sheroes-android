package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 08-03-2017.
 */

public class EditCommunityRequest extends BaseRequest{
    @SerializedName("community_type_id")
    @Expose
    private Long communityTypeId;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;
    @SerializedName("logo_image")
    @Expose
    private String logo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("removed_tags")
    @Expose
    private List<Long> removedTags = null;
    @SerializedName("tags")
    @Expose
    private List<Long> tags = null;

    public Long getCommunityTypeId() {
        return communityTypeId;
    }

    public void setCommunityTypeId(Long communityTypeId) {
        this.communityTypeId = communityTypeId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<Long> getRemovedTags() {
        return removedTags;
    }

    public void setRemovedTags(List<Long> removedTags) {
        this.removedTags = removedTags;
    }

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }
}
