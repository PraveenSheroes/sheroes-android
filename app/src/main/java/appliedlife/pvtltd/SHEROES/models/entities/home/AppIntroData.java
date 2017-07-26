package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 26-07-2017.
 */

public class AppIntroData implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value);
        dest.writeString(this.label);
        dest.writeString(this.category);
        dest.writeString(this.designation);
        dest.writeString(this.description);
        dest.writeString(this.imageLinkUrl);
    }

    public AppIntroData() {
    }

    protected AppIntroData(Parcel in) {
        this.value = in.readInt();
        this.label = in.readString();
        this.category = in.readString();
        this.designation = in.readString();
        this.description = in.readString();
        this.imageLinkUrl = in.readString();
    }

    public static final Parcelable.Creator<AppIntroData> CREATOR = new Parcelable.Creator<AppIntroData>() {
        @Override
        public AppIntroData createFromParcel(Parcel source) {
            return new AppIntroData(source);
        }

        @Override
        public AppIntroData[] newArray(int size) {
            return new AppIntroData[size];
        }
    };
}
