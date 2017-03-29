package appliedlife.pvtltd.SHEROES.models.entities.postdelete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 28-03-2017.
 */

public class DeleteCommunityPostRequest extends BaseRequest{
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
