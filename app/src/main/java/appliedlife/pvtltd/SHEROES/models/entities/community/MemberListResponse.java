package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class MemberListResponse {

    @SerializedName("members")
    @Expose
    private List<MembersList> members = null;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("status")
    @Expose
    private String status;



    public List<MembersList> getMembers() {
        return members;
    }

    public void setMembers(List<MembersList> members) {
        this.members = members;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
