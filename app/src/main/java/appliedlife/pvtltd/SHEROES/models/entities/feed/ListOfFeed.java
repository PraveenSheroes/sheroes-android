
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class ListOfFeed extends BaseResponse implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.groupName);
        dest.writeValue(this.isViewedByUser);
        dest.writeParcelable(this.jobDetail, flags);
        dest.writeString(this.feedTitle);
        dest.writeString(this.feedType);
        dest.writeString(this.createdDateTime);
        dest.writeString(this.tags);
        dest.writeString(this.feedHeadline);
        dest.writeString(this.description);
        dest.writeInt(this.noOfReactions);
        dest.writeList(this.typeOfReaction);
        dest.writeString(this.userReaction);
        dest.writeInt(this.noOfComments);
        dest.writeParcelable(this.recentComment, flags);
        dest.writeList(this.totalCoverImages);
        dest.writeString(this.feedCircleIconUrl);
        dest.writeString(this.postedBy);
        dest.writeValue(this.bookmarked);
        dest.writeString(this.hashtags);
        dest.writeString(this.articleLinkUrl);
        dest.writeString(this.author);
    }

    public ListOfFeed() {
    }

    protected ListOfFeed(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.groupName = in.readString();
        this.isViewedByUser = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.jobDetail = in.readParcelable(JobDetail.class.getClassLoader());
        this.feedTitle = in.readString();
        this.feedType = in.readString();
        this.createdDateTime = in.readString();
        this.tags = in.readString();
        this.feedHeadline = in.readString();
        this.description = in.readString();
        this.noOfReactions = in.readInt();
        this.typeOfReaction = new ArrayList<TypeOfReaction>();
        in.readList(this.typeOfReaction, TypeOfReaction.class.getClassLoader());
        this.userReaction = in.readString();
        this.noOfComments = in.readInt();
        this.recentComment = in.readParcelable(RecentComment.class.getClassLoader());
        this.totalCoverImages = new ArrayList<TotalCoverImage>();
        in.readList(this.totalCoverImages, TotalCoverImage.class.getClassLoader());
        this.feedCircleIconUrl = in.readString();
        this.postedBy = in.readString();
        this.bookmarked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.hashtags = in.readString();
        this.articleLinkUrl = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<ListOfFeed> CREATOR = new Parcelable.Creator<ListOfFeed>() {
        @Override
        public ListOfFeed createFromParcel(Parcel source) {
            return new ListOfFeed(source);
        }

        @Override
        public ListOfFeed[] newArray(int size) {
            return new ListOfFeed[size];
        }
    };
}
