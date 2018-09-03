package appliedlife.pvtltd.SHEROES.models.entities.poll;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

public class DeletePollRequest extends BaseRequest {
    @SerializedName("id")
    private Long pollId;

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }
}
