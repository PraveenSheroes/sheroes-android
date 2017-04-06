package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 21-03-2017.
 */

public class SelectedCommunityResponse extends BaseResponse implements Parcelable {

    @SerializedName("docs")
    @Expose
    private List<Docs> docs = null;


    protected SelectedCommunityResponse(Parcel in) {
    }

    public static final Creator<SelectedCommunityResponse> CREATOR = new Creator<SelectedCommunityResponse>() {
        @Override
        public SelectedCommunityResponse createFromParcel(Parcel in) {
            return new SelectedCommunityResponse(in);
        }

        @Override
        public SelectedCommunityResponse[] newArray(int size) {
            return new SelectedCommunityResponse[size];
        }
    };

    public List<Docs> getDocs() {
        return docs;
    }

    public void setDocs(List<Docs> docs) {
        this.docs = docs;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


}

