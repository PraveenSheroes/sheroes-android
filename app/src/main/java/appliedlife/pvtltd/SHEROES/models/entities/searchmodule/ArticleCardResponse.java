package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class ArticleCardResponse extends BaseResponse implements Parcelable {
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
    private Integer totalViews;
    @SerializedName("articleReadTime")
    @Expose
    private Integer articleReadTime;

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

    public Integer getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(Integer totalViews) {
        this.totalViews = totalViews;
    }

    public Integer getArticleReadTime() {
        return articleReadTime;
    }

    public void setArticleReadTime(Integer articleReadTime) {
        this.articleReadTime = articleReadTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeValue(this.isViewedByUser);
        dest.writeString(this.groupName);
        dest.writeString(this.articleTitle);
        dest.writeString(this.createdDateTime);
        dest.writeString(this.tags);
        dest.writeString(this.articleHeadline);
        dest.writeString(this.description);
        dest.writeString(this.articleCircleIconUrl);
        dest.writeString(this.articleCoverImageUrl);
        dest.writeValue(this.trending);
        dest.writeString(this.hashtags);
        dest.writeString(this.articleLinkUrl);
        dest.writeString(this.author);
        dest.writeValue(this.totalViews);
        dest.writeValue(this.articleReadTime);
    }

    public ArticleCardResponse() {
    }

    protected ArticleCardResponse(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.isViewedByUser = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.groupName = in.readString();
        this.articleTitle = in.readString();
        this.createdDateTime = in.readString();
        this.tags = in.readString();
        this.articleHeadline = in.readString();
        this.description = in.readString();
        this.articleCircleIconUrl = in.readString();
        this.articleCoverImageUrl = in.readString();
        this.trending = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.hashtags = in.readString();
        this.articleLinkUrl = in.readString();
        this.author = in.readString();
        this.totalViews = (Integer) in.readValue(Integer.class.getClassLoader());
        this.articleReadTime = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ArticleCardResponse> CREATOR = new Parcelable.Creator<ArticleCardResponse>() {
        @Override
        public ArticleCardResponse createFromParcel(Parcel source) {
            return new ArticleCardResponse(source);
        }

        @Override
        public ArticleCardResponse[] newArray(int size) {
            return new ArticleCardResponse[size];
        }
    };
}