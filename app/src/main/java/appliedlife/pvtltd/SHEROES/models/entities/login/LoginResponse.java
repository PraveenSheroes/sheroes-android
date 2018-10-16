package appliedlife.pvtltd.SHEROES.models.entities.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title:Token use for user login
 * This token will remain throughout user login.
 */
@Parcel(analyze = {LoginResponse.class,BaseResponse.class})
public class LoginResponse extends BaseResponse{
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_summary")
    @Expose
    private UserSummary userSummary;
    @SerializedName("is_she_user")
    @Expose
    private boolean sheUser;
    private String tokenType;
    private long tokenTime;
    private String fcmId;
    @SerializedName("next_screen")
    @Expose
    private String nextScreen;
    @SerializedName("is_app_contact_accessed")
    @Expose
    private boolean isAppContactAccessed;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserSummary getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(UserSummary userSummary) {
        this.userSummary = userSummary;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(long tokenTime) {
        this.tokenTime = tokenTime;
    }

    public LoginResponse() {
    }

    public String getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(String nextScreen) {
        this.nextScreen = nextScreen;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public boolean isSheUser() {
        return sheUser;
    }

    public void setSheUser(boolean sheUser) {
        this.sheUser = sheUser;
    }

    public boolean isAppContactAccessed() {
        return isAppContactAccessed;
    }

    public void setAppContactAccessed(boolean appContactAccessed) {
        isAppContactAccessed = appContactAccessed;
    }
}
