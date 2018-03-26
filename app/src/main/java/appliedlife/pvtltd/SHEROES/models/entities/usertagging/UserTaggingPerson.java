package appliedlife.pvtltd.SHEROES.models.entities.usertagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen on 01/02/18.
 */

public class UserTaggingPerson {
    @SerializedName("user_id")
    @Expose
    public int userId;

    @SerializedName("fullname")
    @Expose
    public String fullName;
    @SerializedName("user_profile_url")
    @Expose
    public String userProfileDeepLinkUrl;
    @SerializedName("user_profile_image_url")
    @Expose
    public String authorImageUrl;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserProfileDeepLinkUrl() {
        return userProfileDeepLinkUrl;
    }

    public void setUserProfileDeepLinkUrl(String userProfileDeepLinkUrl) {
        this.userProfileDeepLinkUrl = userProfileDeepLinkUrl;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }
}
