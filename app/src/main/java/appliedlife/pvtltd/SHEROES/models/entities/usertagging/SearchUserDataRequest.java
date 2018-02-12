package appliedlife.pvtltd.SHEROES.models.entities.usertagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen on 02/02/18.
 */

public class SearchUserDataRequest extends BaseRequest {
    @SerializedName("participating_entity_or_participant_id")
    @Expose
    private long participatingEntityOrParticipantId;
    @SerializedName("list_type")
    @Expose
    private String listTypeForUserTagging;
    @SerializedName("search_text")
    @Expose
    private String searchNameOfUserForTagging;

    public long getParticipatingEntityOrParticipantId() {
        return participatingEntityOrParticipantId;
    }

    public void setParticipatingEntityOrParticipantId(long participatingEntityOrParticipantId) {
        this.participatingEntityOrParticipantId = participatingEntityOrParticipantId;
    }

    public String getListTypeForUserTagging() {
        return listTypeForUserTagging;
    }

    public void setListTypeForUserTagging(String listTypeForUserTagging) {
        this.listTypeForUserTagging = listTypeForUserTagging;
    }

    public String getSearchNameOfUserForTagging() {
        return searchNameOfUserForTagging;
    }

    public void setSearchNameOfUserForTagging(String searchNameOfUserForTagging) {
        this.searchNameOfUserForTagging = searchNameOfUserForTagging;
    }
}
