package appliedlife.pvtltd.SHEROES.models.entities.challenge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.article.CommonFeedResponse;

/**
 * Created by Praveen_Singh on 25-05-2017.
 */
@Parcel(analyze = {ChallengeListResponse.class,BaseResponse.class})
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
}
