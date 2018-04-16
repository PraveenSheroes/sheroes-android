package appliedlife.pvtltd.SHEROES.models.entities.spam;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by ravi on 11/04/18.
 */

public class SpamResponse extends BaseResponse {

    @SerializedName("isReported")
    private boolean isSpamAlreadyReported;

    public boolean isSpamAlreadyReported() {
        return isSpamAlreadyReported;
    }

    public void setSpamAlreadyReported(boolean spamAlreadyReported) {
        isSpamAlreadyReported = spamAlreadyReported;
    }
}
