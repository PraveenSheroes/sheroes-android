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

    @SerializedName("is_fetch_following")
    private  boolean isFetchFollowing;

    @SerializedName("is_listing")
    private  boolean isListing;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getIsUser() {
        return isFetchFollowing;
    }

    public void setIsUser(boolean isUser) {
        this.isFetchFollowing = isUser;
    }

    public boolean getIsListing() {
        return isListing;
    }

    public void setIsListing(boolean isListing) {
        this.isListing = isListing;
    }
}

