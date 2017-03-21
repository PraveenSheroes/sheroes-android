package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHEROES-TECH on 03-02-2017.
 */

public class OwnerListResponse {

    @SerializedName("fieldErrorMessageMap")
    @Expose
    private FieldErrorMessageMap fieldErrorMessageMap;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("status")
    @Expose
    private String status;

    public FieldErrorMessageMap getFieldErrorMessageMap() {
        return fieldErrorMessageMap;
    }

    public void setFieldErrorMessageMap(FieldErrorMessageMap fieldErrorMessageMap) {
        this.fieldErrorMessageMap = fieldErrorMessageMap;
    }

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
}

