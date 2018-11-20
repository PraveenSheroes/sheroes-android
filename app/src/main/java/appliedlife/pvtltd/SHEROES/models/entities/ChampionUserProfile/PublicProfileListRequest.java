package appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by Praveen_Singh on 03-08-2017.
 */

@Parcel(analyze = {PublicProfileListRequest.class})
public class PublicProfileListRequest extends BaseRequest {
    @SerializedName("id_of_entity_or_participant")
    @Expose
    protected Long idOfEntityParticipant;

    public void setIdOfEntityParticipant(Long idOfEntityParticipant) {
        this.idOfEntityParticipant = idOfEntityParticipant;
    }
}
