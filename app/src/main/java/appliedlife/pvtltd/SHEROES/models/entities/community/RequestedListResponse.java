package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class RequestedListResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("screen_name")
    @Expose
    private Object screenName;
    @SerializedName("members")
    @Expose
    private List<PandingMember> members = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(Object screenName) {
        this.screenName = screenName;
    }

    public List<PandingMember> getMembers() {
        return members;
    }

    public void setMembers(List<PandingMember> members) {
        this.members = members;
    }

}
