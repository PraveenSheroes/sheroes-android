package appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 10-08-2017.
 */

public class FollowedResponse extends BaseResponse {
    @SerializedName("is_followed")
    @Expose
    private boolean isFollowed;

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }
}
