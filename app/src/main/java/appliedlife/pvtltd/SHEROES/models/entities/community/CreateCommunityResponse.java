package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.models.entities.setting.FieldErrorMessageMap;

/**
 * Created by SHEROES-TECH on 03-03-2017.
 */

public class CreateCommunityResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fieldErrorMessageMap")
    @Expose
    private FieldErrorMessageMap fieldErrorMessageMap;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("screen_name")
    @Expose
    private Object screenName;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(Object screenName) {
        this.screenName = screenName;
    }
}
