package appliedlife.pvtltd.SHEROES.models.entities.usertagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen on 02/02/18.
 */

public class SearchUserDataResponse extends BaseResponse {
    @SerializedName("user_mention_suggestions")
    @Expose
    private List<UserTaggingPerson> participantList;


    public List<UserTaggingPerson> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<UserTaggingPerson> participantList) {
        this.participantList = participantList;
    }
}
