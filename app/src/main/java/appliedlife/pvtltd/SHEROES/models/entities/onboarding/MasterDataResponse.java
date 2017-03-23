/**
 * 
 */
package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * @author amleshsinha
 *
 */
public class MasterDataResponse extends BaseResponse implements Parcelable {
	
	HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();


	public HashMap<String, HashMap<String, ArrayList<LabelValue>>> getData() {
		return data;
	}

	public void setData(HashMap<String, HashMap<String, ArrayList<LabelValue>>> data) {
		this.data = data;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.data);
	}

	public MasterDataResponse() {
	}

	protected MasterDataResponse(Parcel in) {
		this.data = (HashMap<String, HashMap<String, ArrayList<LabelValue>>>) in.readSerializable();
	}

	public static final Creator<MasterDataResponse> CREATOR = new Creator<MasterDataResponse>() {
		@Override
		public MasterDataResponse createFromParcel(Parcel source) {
			return new MasterDataResponse(source);
		}

		@Override
		public MasterDataResponse[] newArray(int size) {
			return new MasterDataResponse[size];
		}
	};
}
