package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SHEROES-TECH on 09-03-2017.
 */

public class DeactivateOwnerResponse {
    @SerializedName("fieldErrorMessageMap")
    @Expose
    private FieldErrorMessageMap fieldErrorMessageMap;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("status")
    @Expose
    private String status;

    public FieldErrorMessageMap getFieldErrorMessageMap() {
        return fieldErrorMessageMap;
    }

    public void setFieldErrorMessageMap(FieldErrorMessageMap fieldErrorMessageMap) {
        this.fieldErrorMessageMap = fieldErrorMessageMap;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
