package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 19-03-2017.
 */

public class GetAllDataRequest extends BaseRequest implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.masterDataType);
        dest.writeString(this.q);
    }

    public GetAllDataRequest() {
    }

    protected GetAllDataRequest(Parcel in) {
        this.masterDataType = in.readString();
        this.q = in.readString();
    }

    public static final Creator<GetAllDataRequest> CREATOR = new Creator<GetAllDataRequest>() {
        @Override
        public GetAllDataRequest createFromParcel(Parcel source) {
            return new GetAllDataRequest(source);
        }

        @Override
        public GetAllDataRequest[] newArray(int size) {
            return new GetAllDataRequest[size];
        }
    };
}
