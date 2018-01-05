package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ravi on 04/01/18.
 */

public class UserFollowerOrFollowingRequest extends BaseRequest {
    @SerializedName("mentor_id")
    @Expose
    private Long mentorId;

    @SerializedName("is_user_follower")
    @Expose
    private boolean isUsersFollower;


    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public boolean getIsUserAFollower() {
        return isUsersFollower;
    }

    public void setIsUserAFollower(boolean isUsersFollower) {
        this.isUsersFollower = isUsersFollower;
    }
}
