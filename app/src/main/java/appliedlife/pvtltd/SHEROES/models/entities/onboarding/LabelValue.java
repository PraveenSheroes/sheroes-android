/**
 * 
 */
package appliedlife.pvtltd.SHEROES.models.entities.onboarding;


import android.os.Parcel;
import android.os.Parcelable;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * @author amleshsinha
 *
 */
public class LabelValue extends BaseResponse implements Parcelable {
	

	long value;
	String label;
	private boolean isSelected;
	public LabelValue(String id, long value, String label) {
		super();
		this.value = value;
		this.label = label;
	}

	/**
	 * 
	 */
	public LabelValue() {
		// TODO Auto-generated constructor stub
	}


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
		dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
	}

	protected LabelValue(Parcel in) {
		super(in);
		this.value = in.readLong();
		this.label = in.readString();
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
