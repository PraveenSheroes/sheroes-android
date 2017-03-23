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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.value);
		dest.writeString(this.label);
	}

	protected LabelValue(Parcel in) {
		this.value = in.readLong();
		this.label = in.readString();
	}

	public static final Parcelable.Creator<LabelValue> CREATOR = new Parcelable.Creator<LabelValue>() {
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
