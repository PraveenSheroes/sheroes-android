package appliedlife.pvtltd.SHEROES.models.entities.postdelete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class DeleteCommunityPostResponse extends BaseResponse {
    @SerializedName("id")
    @Expose
    private long idOfEntityOrParticipant;

    public long getIdOfEntityOrParticipant() {
        return idOfEntityOrParticipant;
    }

    public void setIdOfEntityOrParticipant(long idOfEntityOrParticipant) {
        this.idOfEntityOrParticipant = idOfEntityOrParticipant;
    }
}
