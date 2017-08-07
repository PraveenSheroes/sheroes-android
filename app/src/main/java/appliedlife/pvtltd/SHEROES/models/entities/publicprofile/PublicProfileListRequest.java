package appliedlife.pvtltd.SHEROES.models.entities.publicprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 03-08-2017.
 */

public class PublicProfileListRequest extends BaseRequest {
    @SerializedName("id_of_entity_or_participant")
    @Expose
    protected Integer idOfEntityParticipant;

    public Integer getIdOfEntityParticipant() {
        return idOfEntityParticipant;
    }

    public void setIdOfEntityParticipant(Integer idOfEntityParticipant) {
        this.idOfEntityParticipant = idOfEntityParticipant;
    }
}
