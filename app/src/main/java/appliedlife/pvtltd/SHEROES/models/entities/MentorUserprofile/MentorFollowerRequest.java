package appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 08-08-2017.
 */

public class MentorFollowerRequest extends BaseRequest{
    @SerializedName("mentor_id")
    @Expose
    private Long mentorId;


    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }


}
