package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ravi on 04/01/18.
 */

public class ProfileFollowedMentor extends BaseRequest {

    @SerializedName("id_of_entity_or_participant")
    @Expose
    protected Long idOfEntityParticipant;


    public Long getIdOfEntityParticipant() {
        return idOfEntityParticipant;
    }

    public void setIdOfEntityParticipant(Long idOfEntityParticipant) {
        this.idOfEntityParticipant = idOfEntityParticipant;
    }
}

