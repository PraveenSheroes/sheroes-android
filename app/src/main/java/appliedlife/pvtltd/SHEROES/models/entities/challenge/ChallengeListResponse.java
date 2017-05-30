package appliedlife.pvtltd.SHEROES.models.entities.challenge;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 25-05-2017.
 */

public class ChallengeListResponse extends BaseResponse  {
    @SerializedName("reponseList")
    @Expose
    private List<ChallengeDataItem> reponseList = null;

    public List<ChallengeDataItem> getReponseList() {
        return reponseList;
    }

    public void setReponseList(List<ChallengeDataItem> reponseList) {
        this.reponseList = reponseList;
    }

    public ChallengeListResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.reponseList);
    }

    protected ChallengeListResponse(Parcel in) {
        super(in);
        this.reponseList = in.createTypedArrayList(ChallengeDataItem.CREATOR);
    }

    public static final Creator<ChallengeListResponse> CREATOR = new Creator<ChallengeListResponse>() {
        @Override
        public ChallengeListResponse createFromParcel(Parcel source) {
            return new ChallengeListResponse(source);
        }

        @Override
        public ChallengeListResponse[] newArray(int size) {
            return new ChallengeListResponse[size];
        }
    };
}
