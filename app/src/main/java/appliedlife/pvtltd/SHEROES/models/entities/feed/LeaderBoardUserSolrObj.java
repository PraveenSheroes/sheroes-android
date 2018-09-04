package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;

/**
 * Created by Ravi on 26-06-18.
 */
@Parcel(analyze = {LeaderBoardUserSolrObj.class, UserSolrObj.class})
public class LeaderBoardUserSolrObj extends UserSolrObj {

    @SerializedName("solr_ignore_no_of_likes_on_user_post")
    @Expose
    private int solrIgnoreNoOfLikesOnUserPost;

    @SerializedName("solr_ignore_no_of_comments_on_user_post")
    @Expose
    private int solrIgnoreNoOfCommentsOnUserPost;

    @SerializedName("solr_ignore_badge_details")
    @Expose
    private BadgeDetails solrIgnoreBadgeDetails;

    @SerializedName("solr_ignore_user_obj")
    @Expose
    private UserSolrObj userSolrObj;

    @SerializedName("solr_ignore_user_badge_start_date")
    @Expose
    private String solrIgnoreStartDate;

    @SerializedName("solr_ignore_user_badge_end_date")
    @Expose
    private String solrIgnoreEndDate;

    @SerializedName("solr_ignore_user_rank_model")
    @Expose
    private LeaderBoardUserRankObj leaderBoardUserRankObj;

    public BadgeDetails getSolrIgnoreBadgeDetails() {
        return solrIgnoreBadgeDetails;
    }

    public int getSolrIgnoreNoOfLikesOnUserPost() {
        return solrIgnoreNoOfLikesOnUserPost;
    }

    public int getSolrIgnoreNoOfCommentsOnUserPost() {
        return solrIgnoreNoOfCommentsOnUserPost;
    }

    public UserSolrObj getUserSolrObj() {
        return userSolrObj;
    }

    public void setUserSolrObj(UserSolrObj userSolrObj) {
        this.userSolrObj = userSolrObj;
    }

    public String getSolrIgnoreStartDate() {
        return solrIgnoreStartDate;
    }

    public String getSolrIgnoreEndDate() {
        return solrIgnoreEndDate;
    }

    public LeaderBoardUserRankObj getLeaderBoardUserRankObj() {
        return leaderBoardUserRankObj;
    }

    public void setLeaderBoardUserRankObj(LeaderBoardUserRankObj leaderBoardUserRankObj) {
        this.leaderBoardUserRankObj = leaderBoardUserRankObj;
    }
}
