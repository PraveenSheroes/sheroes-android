package appliedlife.pvtltd.SHEROES.models.entities.poll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

public class PollVote extends BaseRequest {
    @SerializedName("poll_id")
    @Expose
    private Long pollId;

    @SerializedName("poll_option_id")
    @Expose
    public Long pollOptionId;

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getPollOptionId() {
        return pollOptionId;
    }

    public void setPollOptionId(Long pollOptionId) {
        this.pollOptionId = pollOptionId;
    }
}
