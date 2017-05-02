package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 02-04-2017.
 */

public class BoardingJobAtRequest extends BaseRequest {
    @SerializedName("skill_ids")
    Set<Long> skillIds;
    @SerializedName("subType")
    @Expose
    private String subtype;
    @SerializedName("type")
    @Expose
    private String type;


    public Set<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<Long> skillIds) {
        this.skillIds = skillIds;
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
