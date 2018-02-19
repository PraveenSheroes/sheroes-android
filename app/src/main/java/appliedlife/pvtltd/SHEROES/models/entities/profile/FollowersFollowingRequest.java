package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ravi on 04/01/18.
 */

public class FollowersFollowingRequest extends BaseRequest {

    @SerializedName("user_id")
    @Expose
    protected Long userId;

    @SerializedName("is_user")
    private  boolean isUser;

    @SerializedName("is_listing")
    private  boolean isListing;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public boolean getIsListing() {
        return isListing;
    }

    public void setIsListing(boolean isListing) {
        this.isListing = isListing;
    }
}

