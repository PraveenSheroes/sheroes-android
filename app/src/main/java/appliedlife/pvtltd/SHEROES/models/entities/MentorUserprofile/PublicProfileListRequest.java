package appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 03-08-2017.
 */

public class PublicProfileListRequest extends BaseRequest {
    @SerializedName("id_of_entity_or_participant")
    @Expose
    protected Long idOfEntityParticipant;

    public void setIdOfEntityParticipant(Long idOfEntityParticipant) {
        this.idOfEntityParticipant = idOfEntityParticipant;
    }
}
