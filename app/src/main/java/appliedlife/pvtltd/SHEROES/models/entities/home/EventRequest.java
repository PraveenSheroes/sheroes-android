package appliedlife.pvtltd.SHEROES.models.entities.home;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 17-06-2017.
 */

public class EventRequest extends BaseRequest {
    Long idOfEntityParticipant;

    public Long getIdOfEntityParticipant() {
        return idOfEntityParticipant;
    }

    public void setIdOfEntityParticipant(Long idOfEntityParticipant) {
        this.idOfEntityParticipant = idOfEntityParticipant;
    }
}
