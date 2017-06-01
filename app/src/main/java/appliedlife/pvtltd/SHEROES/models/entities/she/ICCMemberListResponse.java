package appliedlife.pvtltd.SHEROES.models.entities.she;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SHEROES 005 on 27-May-17.
 */

public class ICCMemberListResponse {

    @SerializedName("iccMembers")
    @Expose
    public List<ICCMember> listOfICCMembers;

    public List<ICCMember> getListOfICCMembers() {
        return listOfICCMembers;
    }

    public void setListOfICCMembers(List<ICCMember> listOfICCMembers) {
        this.listOfICCMembers = listOfICCMembers;
    }
}
