package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SHEROES-TECH on 08-03-2017.
 */

public class EditCommunityRequest {
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("community_type_id")
    @Expose
    private Integer communityTypeId;
    @SerializedName("cover_image_url")
    @Expose
    private String coverImageUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;
    @SerializedName("last_screen_name")
    @Expose
    private String lastScreenName;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("removed_tags")
    @Expose
    private List<Integer> removedTags = null;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("tags")
    @Expose
    private List<Integer> tags = null;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCloudMessagingId() {
        return cloudMessagingId;
    }

    public void setCloudMessagingId(String cloudMessagingId) {
        this.cloudMessagingId = cloudMessagingId;
    }

    public Integer getCommunityTypeId() {
        return communityTypeId;
    }

    public void setCommunityTypeId(Integer communityTypeId) {
        this.communityTypeId = communityTypeId;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceUniqueId() {
        return deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getLastScreenName() {
        return lastScreenName;
    }

    public void setLastScreenName(String lastScreenName) {
        this.lastScreenName = lastScreenName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    public List<Integer> getRemovedTags() {
        return removedTags;
    }

    public void setRemovedTags(List<Integer> removedTags) {
        this.removedTags = removedTags;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

}
