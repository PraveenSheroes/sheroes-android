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

    public BadgeDetails getSolrIgnoreBadgeDetails() {
        return solrIgnoreBadgeDetails;
    }

    public void setSolrIgnoreBadgeDetails(BadgeDetails solrIgnoreBadgeDetails) {
        this.solrIgnoreBadgeDetails = solrIgnoreBadgeDetails;
    }

    public int getSolrIgnoreNoOfLikesOnUserPost() {
        return solrIgnoreNoOfLikesOnUserPost;
    }

    public void setSolrIgnoreNoOfLikesOnUserPost(int solrIgnoreNoOfLikesOnUserPost) {
        this.solrIgnoreNoOfLikesOnUserPost = solrIgnoreNoOfLikesOnUserPost;
    }

    public int getSolrIgnoreNoOfCommentsOnUserPost() {
        return solrIgnoreNoOfCommentsOnUserPost;
    }

    public void setSolrIgnoreNoOfCommentsOnUserPost(int solrIgnoreNoOfCommentsOnUserPost) {
        this.solrIgnoreNoOfCommentsOnUserPost = solrIgnoreNoOfCommentsOnUserPost;
    }
}
