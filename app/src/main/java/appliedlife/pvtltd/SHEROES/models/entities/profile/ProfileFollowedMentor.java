package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ravi on 04/01/18.
 */

public class ProfileFollowedMentor extends BaseRequest {

    @SerializedName("user_id")
    @Expose
    protected Long userId;

    public Long getIdOfEntityParticipant() {
        return userId;
    }

    public void setIdOfEntityParticipant(Long idOfEntityParticipant) {
        this.userId = idOfEntityParticipant;
    }
}

