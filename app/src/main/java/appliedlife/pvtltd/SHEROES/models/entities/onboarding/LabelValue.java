/**
 * 
 */
package appliedlife.pvtltd.SHEROES.models.entities.onboarding;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.OrganizationFeedObj;

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
}
