package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class MemberListResponse  extends BaseResponse{

    @SerializedName("members")
    @Expose
    private List<MembersList> members = null;

    public List<MembersList> getMembers() {
        return members;
    }

    public void setMembers(List<MembersList> members) {
        this.members = members;
    }
}
