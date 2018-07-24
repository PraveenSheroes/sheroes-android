package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBadgesList {

    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("badge_id")
    @Expose
    private int badgeId;
    @SerializedName("badge_name")
    @Expose
    private String badgeName;
    @SerializedName("community_id")
    @Expose
    private int communityId;
    @SerializedName("community_name")
    @Expose
    private String communityName;
    @SerializedName("badge_description")
    @Expose
    private String badgeDescription;
    @SerializedName("badge_image_url")
    @Expose
    private String badgeImageUrl;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("badge_count")
    @Expose
    private int badgeCount;
    @SerializedName("badge_primary_color")
    @Expose
    private String badgePrimaryColor;
    @SerializedName("badge_secondary_color")
    @Expose
    private String badgeSecondaryColor;
    @SerializedName("badge_priority")
    @Expose
    private int badgePriority;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.badgeDescription = badgeDescription;
    }

    public String getBadgeImageUrl() {
        return badgeImageUrl;
    }

    public void setBadgeImageUrl(String badgeImageUrl) {
        this.badgeImageUrl = badgeImageUrl;
    }

    public boolean isBadgeActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }

    public String getBadgePrimaryColor() {
        return badgePrimaryColor;
    }

    public void setBadgePrimaryColor(String badgePrimaryColor) {
        this.badgePrimaryColor = badgePrimaryColor;
    }

    public String getBadgeSecondaryColor() {
        return badgeSecondaryColor;
    }

    public void setBadgeSecondaryColor(String badgeSecondaryColor) {
        this.badgeSecondaryColor = badgeSecondaryColor;
    }

    public int getBadgePriority() {
        return badgePriority;
    }

    public void setBadgePriority(int badgePriority) {
        this.badgePriority = badgePriority;
    }

}
