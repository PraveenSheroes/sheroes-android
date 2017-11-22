package appliedlife.pvtltd.SHEROES.models.entities.like;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */

public class LikeRequestPojo extends BaseRequest {
    @SerializedName("entity_id")
    @Expose
    private Long entityId;
    @SerializedName("value")
    @Expose
    private Integer reactionValue;

    @SerializedName("parent_participation_id")
    @Expose
    public Long commentId;

    public Integer getReactionValue() {
        return reactionValue;
    }

    public void setReactionValue(Integer reactionValue) {
        this.reactionValue = reactionValue;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
