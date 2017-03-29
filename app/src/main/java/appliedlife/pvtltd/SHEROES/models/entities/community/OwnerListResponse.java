package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SHEROES-TECH on 03-02-2017.
 */

public class OwnerListResponse {


    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_owner")
    @Expose
    private boolean isOWner;


    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
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

    public boolean getIsOWner() {
        return isOWner;
    }

    public void setStatus(boolean isOWner) {
        this.isOWner = isOWner;
    }
}

