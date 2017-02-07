
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentComment implements Parcelable {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userProfilePicUrl")
    @Expose
    private String userProfilePicUrl;
    @SerializedName("commentDescription")
    @Expose
    private String commentDescription;

    public String getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public void setUserProfilePicUrl(String userProfilePicUrl) {
        this.userProfilePicUrl = userProfilePicUrl;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userProfilePicUrl);
        dest.writeString(this.commentDescription);
    }

    public RecentComment() {
    }

    protected RecentComment(Parcel in) {
        this.userName = in.readString();
        this.userProfilePicUrl = in.readString();
        this.commentDescription = in.readString();
    }

    public static final Parcelable.Creator<RecentComment> CREATOR = new Parcelable.Creator<RecentComment>() {
        @Override
        public RecentComment createFromParcel(Parcel source) {
            return new RecentComment(source);
        }

        @Override
        public RecentComment[] newArray(int size) {
            return new RecentComment[size];
        }
    };
}
