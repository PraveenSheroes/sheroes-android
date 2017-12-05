/**
 * 
 */
package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * @author amleshsinha
 *
 */
@Parcel(analyze = {MasterDataResponse.class})
public class MasterDataResponse extends BaseResponse{
	
	HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();


	public HashMap<String, HashMap<String, ArrayList<LabelValue>>> getData() {
		return data;
	}

	public void setData(HashMap<String, HashMap<String, ArrayList<LabelValue>>> data) {
		this.data = data;
	}

}
