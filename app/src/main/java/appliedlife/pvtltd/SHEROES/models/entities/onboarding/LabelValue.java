/**
 * 
 */
package appliedlife.pvtltd.SHEROES.models.entities.onboarding;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * @author amleshsinha
 *
 */
@Parcel(analyze = {LabelValue.class,BaseResponse.class})
public class LabelValue extends BaseResponse  {
	@SerializedName("value")
	long value;
	@SerializedName("label")
	String label;
	@SerializedName("desc")
	String desc;

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

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
