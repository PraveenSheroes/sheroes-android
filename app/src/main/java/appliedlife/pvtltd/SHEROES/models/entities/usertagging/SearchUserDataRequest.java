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
    private long participatingEntityParticipantId;
    @SerializedName("list_type")
    @Expose
    private String listType;
    @SerializedName("search_text")
    @Expose
    private String searchText;

    public long getParticipatingEntityParticipantId() {
        return participatingEntityParticipantId;
    }

    public void setParticipatingEntityParticipantId(long participatingEntityParticipantId) {
        this.participatingEntityParticipantId = participatingEntityParticipantId;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
