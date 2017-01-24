package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 23-01-2017.
 */

public class ListOfFeed extends BaseResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("noOfLikes")
    @Expose
    private int noOfLikes;
    @SerializedName("noOfComments")
    @Expose
    private int noOfComments;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("postedBy")
    @Expose
    private String postedBy;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("bookmarked")
    @Expose
    private boolean bookmarked;
    @SerializedName("hashtags")
    @Expose
    private String hashtags;
    @SerializedName("ifNew")
    @Expose
    private boolean ifNew;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("skillsRequired")
    @Expose
    private String skillsRequired;
    @SerializedName("ifApplied")
    @Expose
    private boolean ifApplied;
    @SerializedName("jobType")
    @Expose
    private String jobType;
    @SerializedName("opportunityType")
    @Expose
    private String opportunityType;
    @SerializedName("ifViewed")
    @Expose
    private boolean ifViewed;
    @SerializedName("ifOpen")
    @Expose
    private boolean ifOpen;
    @SerializedName("coverUrl")
    @Expose
    private String coverUrl;
    @SerializedName("communityType")
    @Expose
    private String communityType;
    @SerializedName("owner")
    @Expose
    private String owner;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public boolean getIfNew() {
        return ifNew;
    }

    public void setIfNew(boolean ifNew) {
        this.ifNew = ifNew;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public boolean getIfApplied() {
        return ifApplied;
    }

    public void setIfApplied(boolean ifApplied) {
        this.ifApplied = ifApplied;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        this.opportunityType = opportunityType;
    }

    public boolean getIfViewed() {
        return ifViewed;
    }

    public void setIfViewed(boolean ifViewed) {
        this.ifViewed = ifViewed;
    }

    public boolean getIfOpen() {
        return ifOpen;
    }

    public void setIfOpen(boolean ifOpen) {
        this.ifOpen = ifOpen;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void setCommunityType(String communityType) {
        this.communityType = communityType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
}
