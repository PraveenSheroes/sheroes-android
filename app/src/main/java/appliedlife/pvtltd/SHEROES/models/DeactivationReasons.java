package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ravi on 23/05/18.
 */

public class DeactivationReasons {

    @SerializedName("deactivationReasons")
    @Expose
    private List<DeactivationReason> deactivationReasons = null;

    public List<DeactivationReason> getDeactivationReasons() {
        return deactivationReasons;
    }
}