package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ravi on 21/05/18.
 */

public class DeactivationReason {

    @SerializedName("deactivationReason")
    @Expose
    private String deactivationReason;

    @SerializedName("deactivationReasonId")
    @Expose
    private int deactivationReasonId;

    public String getDeactivationReason() {
        return deactivationReason;
    }

    public int getDeactivationReasonId() {
        return deactivationReasonId;
    }
}