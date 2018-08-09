package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(analyze = {PollSolarObj.class, FeedDetail.class})
public class PollSolarObj extends FeedDetail {
    public static final String POLL_OBJ = "POLL_OBJ";
    @SerializedName("is_anonymous_b")
    private boolean isAnonymous;
    public boolean isAnonymous() {
        return isAnonymous;
    }
    @SerializedName("community_i")
    public Long communityId;

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
