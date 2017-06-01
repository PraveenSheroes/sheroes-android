package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title:Token use for user login
 * This token will remain throughout user login.
 */
public class LoginResponse extends BaseResponse implements Parcelable {
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
    private String gcmId;
    @SerializedName("next_screen")
    @Expose
    private String nextScreen;
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

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public boolean isSheUser() {
        return sheUser;
    }

    public void setSheUser(boolean sheUser) {
        this.sheUser = sheUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.token);
        dest.writeParcelable(this.userSummary, flags);
        dest.writeString(this.tokenType);
        dest.writeLong(this.tokenTime);
        dest.writeString(this.gcmId);
        dest.writeString(this.nextScreen);
        dest.writeByte((byte) (this.sheUser ? 1 : 0));
    }

    protected LoginResponse(Parcel in) {
        super(in);
        this.token = in.readString();
        this.userSummary = in.readParcelable(UserSummary.class.getClassLoader());
        this.tokenType = in.readString();
        this.tokenTime = in.readLong();
        this.gcmId = in.readString();
        this.nextScreen = in.readString();
        this.sheUser = in.readByte() != 0;
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
