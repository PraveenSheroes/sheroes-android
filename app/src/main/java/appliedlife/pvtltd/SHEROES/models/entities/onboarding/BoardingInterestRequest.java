package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 02-04-2017.
 */

public class BoardingInterestRequest extends BaseRequest{
    @SerializedName("interest_ids")
    Set<Long> interestIds;
    @SerializedName("subType")
    @Expose
    private String subtype;
    @SerializedName("type")
    @Expose
    private String type;

    public Set<Long> getInterestIds() {
        return interestIds;
    }

    public void setInterestIds(Set<Long> interestIds) {
        this.interestIds = interestIds;
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
