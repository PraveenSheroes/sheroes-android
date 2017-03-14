package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 10-03-2017.
 */

public class CommunityResponse extends BaseResponse {
    @Override
    public HashMap<String, String> getFieldErrorMessageMap() {
        return super.getFieldErrorMessageMap();
    }

    @Override
    public void setFieldErrorMessageMap(HashMap<String, String> fieldErrorMessageMap) {
        super.setFieldErrorMessageMap(fieldErrorMessageMap);
    }
    @SerializedName("members")
    @Expose
    private List<Member> members;
    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }
}
