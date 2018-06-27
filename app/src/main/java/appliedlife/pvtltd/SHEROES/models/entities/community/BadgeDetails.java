package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ravi on 26/06/18.
 */

@Parcel
public class BadgeDetails {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("communityId")
    @Expose
    private int communityId;
    @SerializedName("communityName")
    @Expose
    private String communityName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("isDeleted")
    @Expose
    private boolean isDeleted;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("primaryColor")
    @Expose
    private String primaryColor;
    @SerializedName("secondaryColor")
    @Expose
    private String secondaryColor;
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("crdt")
    @Expose
    private int crdt;
    @SerializedName("lastModifiedOn")
    @Expose
    private int lastModifiedOn;
    @SerializedName("lastModifiedBy")
    @Expose
    private int lastModifiedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getCrdt() {
        return crdt;
    }

    public void setCrdt(int crdt) {
        this.crdt = crdt;
    }

    public int getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(int lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public int getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(int lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
