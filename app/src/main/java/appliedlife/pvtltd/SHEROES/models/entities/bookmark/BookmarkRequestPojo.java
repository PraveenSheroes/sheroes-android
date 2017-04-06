package appliedlife.pvtltd.SHEROES.models.entities.bookmark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 16-02-2017.
 */

public class BookmarkRequestPojo extends BaseRequest {
    @SerializedName("entity_id")
    @Expose
    private long entityId;

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }
}
