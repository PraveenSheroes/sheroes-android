
package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class HomeSpinnerItem extends BaseResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("ischecked")
    @Expose
    private boolean isChecked;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public HomeSpinnerItem() {
    }

    protected HomeSpinnerItem(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<HomeSpinnerItem> CREATOR = new Parcelable.Creator<HomeSpinnerItem>() {
        @Override
        public HomeSpinnerItem createFromParcel(Parcel source) {
            return new HomeSpinnerItem(source);
        }

        @Override
        public HomeSpinnerItem[] newArray(int size) {
            return new HomeSpinnerItem[size];
        }
    };
}
