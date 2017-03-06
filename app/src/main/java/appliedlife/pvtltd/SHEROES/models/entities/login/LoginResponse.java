package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title:Token use for user login
 * This token will remain throughout user login.
 */
public class LoginResponse implements Parcelable {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_summary")
    @Expose
    private UserSummary userSummary;
    private String tokenType;
    private long tokenTime;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.tokenType);
        dest.writeLong(this.tokenTime);
        dest.writeParcelable(this.userSummary, flags);
    }

    public LoginResponse() {
    }

    protected LoginResponse(Parcel in) {
        this.token = in.readString();
        this.tokenType = in.readString();
        this.tokenTime = in.readLong();
        this.userSummary = in.readParcelable(UserSummary.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {
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
