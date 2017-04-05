package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 03-03-2017.
 */

public class CreateCommunityRequest extends BaseRequest {

    @SerializedName("community_type_id")
    @Expose
    private Long communityTypeId;

    @SerializedName("cover_image")
    @Expose
    private String coverImage;

    @SerializedName("description")
    @Expose
    private String description;

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

    @SerializedName("tags")
    @Expose
    private List<Long> tags = null;

    @SerializedName("type")
    @Expose

    private String Type;


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

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
