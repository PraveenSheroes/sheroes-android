package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;

@Parcel(analyze = LeaderBoardUserRankObj.class)
public class LeaderBoardUserRankObj {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("community_id")
    @Expose
    private Integer communityId;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("community_leaderboard_rank")
    @Expose
    private Integer communityLeaderboardRank;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCommunityLeaderboardRank() {
        return communityLeaderboardRank;
    }

    public void setCommunityLeaderboardRank(Integer communityLeaderboardRank) {
        this.communityLeaderboardRank = communityLeaderboardRank;
    }
}
