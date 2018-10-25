package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.HashMap;

@Parcel(analyze = {BaseResponse.class})
public class BaseResponse {
    @SerializedName("fieldErrorMessageMap")
    private HashMap<String, String> fieldErrorMessageMap;
    @SerializedName("numFound")
    private int numFound;
    @SerializedName("start")
    private int start;
    @SerializedName("status")
    private String status;
    @SerializedName("screen_name")
    private String screenName;

    public HashMap<String, String> getFieldErrorMessageMap() {
        return fieldErrorMessageMap;
    }

    public void setFieldErrorMessageMap(HashMap<String, String> fieldErrorMessageMap) {
        this.fieldErrorMessageMap = fieldErrorMessageMap;
    }

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}