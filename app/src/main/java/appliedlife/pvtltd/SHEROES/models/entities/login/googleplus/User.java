package appliedlife.pvtltd.SHEROES.models.entities.login.googleplus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 05-06-2017.
 */

public class User {
    @SerializedName("gp_access_token")
    @Expose
    private String gpAccessToken;
    @SerializedName("expires_in")
    @Expose
    private String expiresIn;
    @SerializedName("created")
    @Expose
    private String created;

    public String getGpAccessToken() {
        return gpAccessToken;
    }

    public void setGpAccessToken(String gpAccessToken) {
        this.gpAccessToken = gpAccessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
