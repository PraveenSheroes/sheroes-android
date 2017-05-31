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
    @SerializedName("total_people_completed")
    @Expose
    private int totalPeopleCompleted;
    @SerializedName("total_people_completed_delhi")
    @Expose
    private int totalPeopleCompletedDelhi;
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

    public int getTotalPeopleCompleted() {
        return totalPeopleCompleted;
    }

    public void setTotalPeopleCompleted(int totalPeopleCompleted) {
        this.totalPeopleCompleted = totalPeopleCompleted;
    }

    public int getTotalPeopleCompletedDelhi() {
        return totalPeopleCompletedDelhi;
    }

    public void setTotalPeopleCompletedDelhi(int totalPeopleCompletedDelhi) {
        this.totalPeopleCompletedDelhi = totalPeopleCompletedDelhi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.totalPeopleCompleted);
        dest.writeInt(this.totalPeopleCompletedDelhi);
        dest.writeTypedList(this.reponseList);
    }

    protected ChallengeListResponse(Parcel in) {
        super(in);
        this.totalPeopleCompleted = in.readInt();
        this.totalPeopleCompletedDelhi = in.readInt();
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
