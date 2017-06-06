package appliedlife.pvtltd.SHEROES.models.entities.login.googleplus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 05-06-2017.
 */

public class GooglePlusResponse {

    @SerializedName("jauthtoken")
    @Expose
    private String googlePlusLogInAuthToken;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("token")
    @Expose
    private String token;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGooglePlusLogInAuthToken() {
        return googlePlusLogInAuthToken;
    }

    public void setGooglePlusLogInAuthToken(String googlePlusLogInAuthToken) {
        this.googlePlusLogInAuthToken = googlePlusLogInAuthToken;
    }
}
