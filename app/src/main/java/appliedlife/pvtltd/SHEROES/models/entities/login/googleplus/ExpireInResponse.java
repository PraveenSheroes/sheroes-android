package appliedlife.pvtltd.SHEROES.models.entities.login.googleplus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 05-06-2017.
 */

public class ExpireInResponse {
    GooglePlusResponse googlePlusResponse;
    @SerializedName("issued_to")
    @Expose
    private String issuedTo;
    @SerializedName("audience")
    @Expose
    private String audience;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("expires_in")
    @Expose
    private int expiresIn;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("verified_email")
    @Expose
    private boolean verifiedEmail;
    @SerializedName("access_type")
    @Expose
    private String accessType;

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getVerifiedEmail() {
        return verifiedEmail;
    }

    public void setVerifiedEmail(boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public GooglePlusResponse getGooglePlusResponse() {
        return googlePlusResponse;
    }

    public void setGooglePlusResponse(GooglePlusResponse googlePlusResponse) {
        this.googlePlusResponse = googlePlusResponse;
    }

    public boolean isVerifiedEmail() {
        return verifiedEmail;
    }
}
