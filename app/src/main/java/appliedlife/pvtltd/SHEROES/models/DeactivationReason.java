package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ravi on 21/05/18.
 */

public class DeactivationReason {

    @SerializedName("deactivationReasonId")
    @Expose
    private Integer deactivationReasonId;
    @SerializedName("deactivationReason")
    @Expose
    private String deactivationReason;

    public Integer getDeactivationReasonId() {
        return deactivationReasonId;
    }

    public void setDeactivationReasonId(Integer deactivationReasonId) {
        this.deactivationReasonId = deactivationReasonId;
    }

    public String getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(String deactivationReason) {
        this.deactivationReason = deactivationReason;
    }
}
