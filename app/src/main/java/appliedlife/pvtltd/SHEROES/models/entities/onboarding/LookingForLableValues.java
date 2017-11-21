package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen on 16/11/17.
 */

public class LookingForLableValues extends BaseResponse {
    @SerializedName("value")
    long value;
    @SerializedName("label")
    String label;
    @SerializedName("desc")
    String desc;
    @SerializedName("imgUrl")
    String imgUrl;
    int position;
    boolean isSelected;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public LookingForLableValues() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.value);
        dest.writeString(this.label);
        dest.writeString(this.desc);
        dest.writeString(this.imgUrl);
        dest.writeInt(this.position);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected LookingForLableValues(Parcel in) {
        super(in);
        this.value = in.readLong();
        this.label = in.readString();
        this.desc = in.readString();
        this.imgUrl = in.readString();
        this.position = in.readInt();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<LookingForLableValues> CREATOR = new Creator<LookingForLableValues>() {
        @Override
        public LookingForLableValues createFromParcel(Parcel source) {
            return new LookingForLableValues(source);
        }

        @Override
        public LookingForLableValues[] newArray(int size) {
            return new LookingForLableValues[size];
        }
    };
}
