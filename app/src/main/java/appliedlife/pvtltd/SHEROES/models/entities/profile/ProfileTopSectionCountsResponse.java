package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by ravi on 01/01/18.
 */

@Deprecated
@Parcel(analyze = {ProfileTopSectionCountsResponse.class,BaseResponse.class})
public class ProfileTopSectionCountsResponse extends BaseResponse {

    @SerializedName("user_post_count")
    @Expose
    private int postCount;

    @SerializedName("following_count")
    @Expose
    private int followingCount;

    @SerializedName("followers_count")
    @Expose
    private int followerCount;

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

}