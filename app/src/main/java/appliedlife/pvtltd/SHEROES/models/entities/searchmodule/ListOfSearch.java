package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.RecentComment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.TotalCoverImage;
import appliedlife.pvtltd.SHEROES.models.entities.feed.TypeOfReaction;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class ListOfSearch extends BaseResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("isViewedByUser")
    @Expose
    private Boolean isViewedByUser;
    @SerializedName("jobDetail")
    @Expose
    private JobDetail jobDetail;
    @SerializedName("feedTitle")
    @Expose
    private String feedTitle;
    @SerializedName("feedType")
    @Expose
    private String feedType;
    @SerializedName("createdDateTime")
    @Expose
    private String createdDateTime;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("feedHeadline")
    @Expose
    private String feedHeadline;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("noOfReactions")
    @Expose
    private int noOfReactions;
    @SerializedName("typeOfReaction")
    @Expose
    private List<TypeOfReaction> typeOfReaction = null;
    @SerializedName("userReaction")
    @Expose
    private String userReaction;
    @SerializedName("noOfComments")
    @Expose
    private int noOfComments;
    @SerializedName("recentComment")
    @Expose
    private RecentComment recentComment;
    @SerializedName("totalCoverImages")
    @Expose
    private List<TotalCoverImage> totalCoverImages = null;
    @SerializedName("feedCircleIconUrl")
    @Expose
    private String feedCircleIconUrl;
    @SerializedName("postedBy")
    @Expose
    private String postedBy;
    @SerializedName("bookmarked")
    @Expose
    private Boolean bookmarked;
    @SerializedName("hashtags")
    @Expose
    private String hashtags;
    @SerializedName("articleLinkUrl")
    @Expose
    private String articleLinkUrl;
    @SerializedName("author")
    @Expose
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIsViewedByUser() {
        return isViewedByUser;
    }

    public void setIsViewedByUser(Boolean isViewedByUser) {
        this.isViewedByUser = isViewedByUser;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFeedHeadline() {
        return feedHeadline;
    }

    public void setFeedHeadline(String feedHeadline) {
        this.feedHeadline = feedHeadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoOfReactions() {
        return noOfReactions;
    }

    public void setNoOfReactions(int noOfReactions) {
        this.noOfReactions = noOfReactions;
    }

    public List<TypeOfReaction> getTypeOfReaction() {
        return typeOfReaction;
    }

    public void setTypeOfReaction(List<TypeOfReaction> typeOfReaction) {
        this.typeOfReaction = typeOfReaction;
    }

    public String getUserReaction() {
        return userReaction;
    }

    public void setUserReaction(String userReaction) {
        this.userReaction = userReaction;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public RecentComment getRecentComment() {
        return recentComment;
    }

    public void setRecentComment(RecentComment recentComment) {
        this.recentComment = recentComment;
    }


    public List<TotalCoverImage> getTotalCoverImages() {
        return totalCoverImages;
    }

    public void setTotalCoverImages(List<TotalCoverImage> totalCoverImages) {
        this.totalCoverImages = totalCoverImages;
    }

    public String getFeedCircleIconUrl() {
        return feedCircleIconUrl;
    }

    public void setFeedCircleIconUrl(String feedCircleIconUrl) {
        this.feedCircleIconUrl = feedCircleIconUrl;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public String getArticleLinkUrl() {
        return articleLinkUrl;
    }

    public void setArticleLinkUrl(String articleLinkUrl) {
        this.articleLinkUrl = articleLinkUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getViewedByUser() {
        return isViewedByUser;
    }

    public void setViewedByUser(Boolean viewedByUser) {
        isViewedByUser = viewedByUser;
    }
}
