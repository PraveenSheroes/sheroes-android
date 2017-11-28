package appliedlife.pvtltd.SHEROES.models.entities.challenge;

import org.parceler.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.post.Post;

/**
 * Created by Praveen_Singh on 25-05-2017.
 */

    @Parcel(analyze = {ChallengeListResponseNew.class, BaseResponse.class})
    public class ChallengeListResponseNew extends BaseResponse  {
    @SerializedName("total_people_completed")
    private int totalPeopleCompleted;
    @SerializedName("total_people_completed_delhi")
    private int totalPeopleCompletedDelhi;
    @SerializedName("reponseList")
    public List<Contest> contests = null;

    public List<Contest> getReponseList() {
        return contests;
    }

    public void setReponseList(List<Contest> reponseList) {
        this.contests = reponseList;
    }

    public ChallengeListResponseNew() {
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
