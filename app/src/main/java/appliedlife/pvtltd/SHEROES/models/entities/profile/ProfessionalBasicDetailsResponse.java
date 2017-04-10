package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.models.entities.setting.FieldErrorMessageMap;

/**
 * Created by priyanka on 24/03/17.
 */

public class ProfessionalBasicDetailsResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fieldErrorMessageMap")
    @Expose
    private FieldErrorMessageMap fieldErrorMessageMap;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("screen_name")
    @Expose
    private String screenName;

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public FieldErrorMessageMap getFieldErrorMessageMap() {

        return fieldErrorMessageMap;
    }

    public void setFieldErrorMessageMap(FieldErrorMessageMap fieldErrorMessageMap) {
        this.fieldErrorMessageMap = fieldErrorMessageMap;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }


}
