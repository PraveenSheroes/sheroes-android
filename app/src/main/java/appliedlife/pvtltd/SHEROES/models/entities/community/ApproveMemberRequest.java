package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 01-04-2017.
 */

public class ApproveMemberRequest extends BaseRequest {

    @SerializedName("community_id")
    @Expose
    private Long communityId;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("user_id")
    @Expose
    private Long userId;


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
