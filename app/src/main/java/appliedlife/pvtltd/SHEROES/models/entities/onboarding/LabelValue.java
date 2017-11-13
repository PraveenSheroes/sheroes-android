/**
 * 
 */
package appliedlife.pvtltd.SHEROES.models.entities.onboarding;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * @author amleshsinha
 *
 */
public class LabelValue extends BaseResponse  {
	@SerializedName("value")
	long value;
	@SerializedName("label")
	String label;
	@SerializedName("desc")
	String desc;
	@SerializedName("imgUrl")
	String imgUrl;

	private boolean isSelected;

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
		dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
	}

	public LabelValue() {
	}

	protected LabelValue(Parcel in) {
		super(in);
		this.value = in.readLong();
		this.label = in.readString();
		this.desc = in.readString();
		this.imgUrl = in.readString();
		this.isSelected = in.readByte() != 0;
	}

	public static final Creator<LabelValue> CREATOR = new Creator<LabelValue>() {
		@Override
		public LabelValue createFromParcel(Parcel source) {
			return new LabelValue(source);
		}

		@Override
		public LabelValue[] newArray(int size) {
			return new LabelValue[size];
		}
	};
}
