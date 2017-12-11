package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.models.entities.feed.LastComment;

/**
 * Created by Praveen_Singh on 26-07-2017.
 */
@Parcel(analyze = {AppIntroData.class})
public class AppIntroData{
    @SerializedName("value")
    @Expose
    private int value;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageLinkUrl")
    @Expose
    private String imageLinkUrl;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLinkUrl() {
        return imageLinkUrl;
    }

    public void setImageLinkUrl(String imageLinkUrl) {
        this.imageLinkUrl = imageLinkUrl;
    }
}
