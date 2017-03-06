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
 * Title:User summary
 */
public class UserSummary implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.emailId);
        dest.writeString(this.mobile);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.photoUrl);
    }

    public UserSummary() {
    }

    protected UserSummary(Parcel in) {
        this.userId = in.readInt();
        this.emailId = in.readString();
        this.mobile = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Parcelable.Creator<UserSummary> CREATOR = new Parcelable.Creator<UserSummary>() {
        @Override
        public UserSummary createFromParcel(Parcel source) {
            return new UserSummary(source);
        }

        @Override
        public UserSummary[] newArray(int size) {
            return new UserSummary[size];
        }
    };
}
