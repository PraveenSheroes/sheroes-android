package appliedlife.pvtltd.SHEROES.models.entities.poll;

import com.google.gson.annotations.SerializedName;

public class PollOptionRequestModel {
    @SerializedName("description")
    private String description;

    @SerializedName("is_active")
    private Boolean isActive;

    @SerializedName("image_url")
    private String imageUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
