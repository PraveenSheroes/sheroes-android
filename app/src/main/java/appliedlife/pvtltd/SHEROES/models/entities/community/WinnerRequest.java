package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ujjwal on 11/12/17.
 */

public class WinnerRequest extends BaseRequest {
    @SerializedName("challenge_id")
    public String challengeId;
}
