package appliedlife.pvtltd.SHEROES.models.entities.spam;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by ravi on 11/04/18.
 */

public class SpamResponse extends BaseResponse {

    @SerializedName("isReported")
    private boolean isSpamAlreadyReported;

    @SerializedName("modelType")
    private String modelType;

    @SerializedName("isMarkedSpam")
    private boolean isSpammed;

    public boolean isSpamAlreadyReported() {
        return isSpamAlreadyReported;
    }

    public void setSpamAlreadyReported(boolean spamAlreadyReported) {
        isSpamAlreadyReported = spamAlreadyReported;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public boolean isSpammed() {
        return isSpammed;
    }

    public void setSpammed(boolean spammed) {
        isSpammed = spammed;
    }
}
