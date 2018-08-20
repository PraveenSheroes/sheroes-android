package appliedlife.pvtltd.SHEROES.models.entities.poll;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

@Parcel(analyze = {PollVoteResponse.class, BaseResponse.class})
public class PollVoteResponse extends BaseResponse {
    @SerializedName("poll_reaction_model")
    private PollReactionModel pollReactionModel;

    public PollReactionModel getPollReactionModel() {
        return pollReactionModel;
    }

    public void setPollReactionModel(PollReactionModel pollReactionModel) {
        this.pollReactionModel = pollReactionModel;
    }
}
