package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class RequestedListResponse extends BaseResponse{

    @SerializedName("members")
    @Expose
    private List<PandingMember> members = null;

    public List<PandingMember> getMembers() {
        return members;
    }

    public void setMembers(List<PandingMember> members) {
        this.members = members;
    }

}
