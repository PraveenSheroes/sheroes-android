package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 08-03-2017.
 */

public class CommunityRequest extends BaseRequest {
    @SerializedName("user_ids")
    private List<Long> userId;

    public List<Long> getUserId() {
        return userId;
    }

    public void setUserId(List<Long> userId) {
        this.userId = userId;
    }
}
