package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 02-04-2017.
 */

public class BoardingLookingForHowCanRequest extends BaseRequest{
    @SerializedName("opportunity_ids")
    Set<Long> opportunityIds;
    @SerializedName("subType")
    @Expose
    private String subtype;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("source")
    @Expose
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<Long> getOpportunityIds() {
        return opportunityIds;
    }

    public void setOpportunityIds(Set<Long> opportunityIds) {
        this.opportunityIds = opportunityIds;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
