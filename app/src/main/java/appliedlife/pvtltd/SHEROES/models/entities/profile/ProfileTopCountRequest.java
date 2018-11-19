package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ravi on 04/01/18.
 */
@Deprecated
class ProfileTopCountRequest1 extends BaseRequest {

    @SerializedName("mentor_id")
    @Expose
    private Long mentorId;

    @SerializedName("user_id")
    @Expose
    private Long userId;

    @SerializedName("is_user_follower")
    @Expose
    private boolean isUsersFollower;

    @SerializedName("is_user_following")
    @Expose
    private boolean isUsersFollowing;

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isUsersFollower() {
        return isUsersFollower;
    }

    public void setUsersFollower(boolean usersFollower) {
        isUsersFollower = usersFollower;
    }

    public boolean isUsersFollowing() {
        return isUsersFollowing;
    }

    public void setUsersFollowing(boolean usersFollowing) {
        isUsersFollowing = usersFollowing;
    }

}
