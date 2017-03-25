package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 19-03-2017.
 */

public class GetAllData extends BaseResponse implements Parcelable {
    @SerializedName("docs")
    @Expose
    private List<GetAllDataDocument> getAllDataDocuments = null;


    public List<GetAllDataDocument> getGetAllDataDocuments() {
        return getAllDataDocuments;
    }

    public void setGetAllDataDocuments(List<GetAllDataDocument> getAllDataDocuments) {
        this.getAllDataDocuments = getAllDataDocuments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.getAllDataDocuments);
    }

    public GetAllData() {
    }

    protected GetAllData(Parcel in) {
        this.getAllDataDocuments = in.createTypedArrayList(GetAllDataDocument.CREATOR);
    }

    public static final Creator<GetAllData> CREATOR = new Creator<GetAllData>() {
        @Override
        public GetAllData createFromParcel(Parcel source) {
            return new GetAllData(source);
        }

        @Override
        public GetAllData[] newArray(int size) {
            return new GetAllData[size];
        }
    };
}
