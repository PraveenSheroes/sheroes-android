package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 19-03-2017.
 */
@Parcel(analyze = {GetAllDataRequest.class,BaseRequest.class})
public class GetAllDataRequest extends BaseRequest{
    @SerializedName("master_data_type")
    @Expose
    private String masterDataType;
    @SerializedName("q")
    @Expose
    private String q;

    public String getMasterDataType() {
        return masterDataType;
    }

    public void setMasterDataType(String masterDataType) {
        this.masterDataType = masterDataType;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public GetAllDataRequest() {
    }
}
