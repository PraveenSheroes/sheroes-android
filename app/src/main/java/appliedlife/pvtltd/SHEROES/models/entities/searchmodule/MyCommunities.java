package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 30-01-2017.
 */

public class MyCommunities extends BaseResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("isViewedByUser")
    @Expose
    private Boolean isViewedByUser;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("articleTitle")
    @Expose
    private String articleTitle;
    @SerializedName("createdDateTime")
    @Expose
    private String createdDateTime;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("articleHeadline")
    @Expose
    private String articleHeadline;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("articleCircleIconUrl")
    @Expose
    private String articleCircleIconUrl;
    @SerializedName("articleCoverImageUrl")
    @Expose
    private String articleCoverImageUrl;
    @SerializedName("trending")
    @Expose
    private Boolean trending;
    @SerializedName("hashtags")
    @Expose
    private String hashtags;
    @SerializedName("articleLinkUrl")
    @Expose
    private String articleLinkUrl;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("totalViews")
    @Expose
    private int totalViews;
    @SerializedName("articleReadTime")
    @Expose
    private int articleReadTime;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
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

    public String getArticleHeadline() {
        return articleHeadline;
    }

    public void setArticleHeadline(String articleHeadline) {
        this.articleHeadline = articleHeadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleCircleIconUrl() {
        return articleCircleIconUrl;
    }

    public void setArticleCircleIconUrl(String articleCircleIconUrl) {
        this.articleCircleIconUrl = articleCircleIconUrl;
    }

    public String getArticleCoverImageUrl() {
        return articleCoverImageUrl;
    }

    public void setArticleCoverImageUrl(String articleCoverImageUrl) {
        this.articleCoverImageUrl = articleCoverImageUrl;
    }

    public Boolean getTrending() {
        return trending;
    }

    public void setTrending(Boolean trending) {
        this.trending = trending;
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

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public int getArticleReadTime() {
        return articleReadTime;
    }

    public void setArticleReadTime(int articleReadTime) {
        this.articleReadTime = articleReadTime;
    }
}
