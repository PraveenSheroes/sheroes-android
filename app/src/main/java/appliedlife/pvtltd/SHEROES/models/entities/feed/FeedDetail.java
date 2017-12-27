package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroData;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {FeedDetail.class, BaseResponse.class})
public class FeedDetail extends BaseResponse implements Cloneable{
    public static final String FEED_DETAIL_OBJ = "FEED_DETAIL_OBJ";
    public static final String FEED_DETAIL_ID = "FEED_DETAIL_ID";
    @SerializedName(value = "id")
    private String id;

    @SerializedName(value = "entity_or_participant_id")
    private long entityOrParticipantId;

    @SerializedName(value = "entity_or_participant_type_id_i")
    private Integer entityOrParticipantTypeId;

    @SerializedName(value = "display_id_profile_id")
    private long profileId;

    @SerializedName(value = "created_by_l")
    private long createdBy;

    @SerializedName(value = "id_of_entity_or_participant")
    private long idOfEntityOrParticipant;

    @SerializedName(value = "type")
    private String type;

    @SerializedName(value = "sub_type")
    public String subType;

    @SerializedName(value = "name")
    private String nameOrTitle;

    @SerializedName(value = "image_url")
    private String imageUrl;

    @SerializedName(value = "thumbnail_image_url")
    private String thumbnailImageUrl;

    @SerializedName(value = "short_description")
    private String shortDescription;

    @SerializedName(value = "description")
    private String description;

    @SerializedName(value = "list_short_description")
    private String listShortDescription;

    @SerializedName(value = "list_description")
    private String listDescription;

    @SerializedName(value = "p_is_deleted")
    private boolean isDeleted;

    @SerializedName(value = "p_is_active")
    private boolean isActive;

    @SerializedName(value = "p_crdt")
    private String createdDate;


    @SerializedName(value = "posting_date_dt")
    private String postingDate;

    @SerializedName(value = "posting_date_only_dt")
    private Date postingDateOnly;

    @SerializedName(value = "is_expired")
    private boolean isExpired;

    @SerializedName(value = "p_last_modified_on")
    private Date lastModifiedDate;

    @SerializedName(value = "author_participant_id")
    private long authorParticipantId;

    @SerializedName(value = "author_id")
    private long authorId;

    @SerializedName(value = "is_author_confidential")
    private boolean isAuthorConfidential;

    @SerializedName(value = "author_participant_type")
    private String authorParticipantType;

    @SerializedName(value = "author_first_name")
    private String authorFirstName;

    @SerializedName(value = "author_last_name")
    private String authorLastName;

    @SerializedName(value = "author_name")
    private String authorName;

    @SerializedName(value = "author_image_url")
    private String authorImageUrl;

    @SerializedName(value = "is_author_image_public")
    private boolean isAuthorImagePublic;

    @SerializedName(value = "author_city_id")
    private String authorCityId;

    @SerializedName(value = "author_city_name")
    private String authorCityName;

    @SerializedName(value = "author_short_description")
    private String authorShortDescription;

    @SerializedName(value = "is_featured")
    private boolean isFeatured;

    @SerializedName(value = "solr_ignore_no_of_likes")
    private int noOfLikes = 0;

    @SerializedName(value = "solr_ignore_no_of_comments")
    private int noOfComments = 0;

    @SerializedName(value = "solr_ignore_last_comments")
    private List<Comment> lastComments;

    @SerializedName(value = "solr_ignore_reacted_value")
    private int reactedValue;

    @SerializedName(value = "solr_ignore_is_bookmarked")
    private boolean isBookmarked;

    @SerializedName(value = "solr_ignore_no_of_bookmarks")
    private int noOfBookmarks = 0;

    @SerializedName(value = "solr_ignore_no_of_views")
    private int noOfViews = 0;

    @SerializedName(value = "solr_ignore_no_of_challenge_accepted")
    private int noOfChallengeAccepted = 0;

    @SerializedName(value = "solr_ignore_is_challenge_accepted")
    private boolean isChallengeAccepted;

    @SerializedName("solr_ignore_deep_link_url")
    private String deepLinkUrl;

    @SerializedName(value = "solr_ignore_is_applied")
    private boolean isApplied;

    @SerializedName(value = "solr_ignore_is_viewed")
    private boolean isViewed;

    @SerializedName(value = "solr_ignore_no_of_applies")
    private int noOfApplied;

    @SerializedName(value = "tag_ids")
    private List<Long> tag_ids;

    @SerializedName(value = "tag_names")
    private List<String> tags;

    @SerializedName(value = "solr_ignore_participant_deep_link_url")
    private String participantDeepLinkingUrl;

    @SerializedName(value = "solr_ignore_entity_deep_link_url")
    private String entityDeepLinkingUrl;

    @SerializedName(value = "og_image_url_s")
    private String ogImageUrlS;

    @SerializedName(value = "og_title_s")
    private String ogTitleS;

    @SerializedName(value = "og_description_s")
    private String ogDescriptionS;

    @SerializedName(value = "og_requested_url_s")
    private String ogRequestedUrlS;

    @SerializedName(value = "is_og_video_link_b")
    private boolean isOgVideoLinkB;

    @SerializedName("solr_ignore_is_author_mentor")
    private boolean isAuthorMentor;

    @SerializedName(value = "solr_ignore_mentor_deep_link_url")
    private String mentorDeepLinkUrl;

    @SerializedName(value = "blog_link_s")
    private String blogLinkUrl;

    @SerializedName("user_sub_type_s")
    private String userSubType;

    @SerializedName("is_spam_post_b")
    private boolean isSpamPost;

    //These fields are not from API response
    private int itemPosition;
    private boolean isLongPress;
    private String callFromName;
    private boolean isTrending;
    private boolean isFromHome;
    private AppIntroData appIntroDataItems = null;
    private int lastReactionValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(long entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    public Integer getEntityOrParticipantTypeId() {
        return entityOrParticipantTypeId;
    }

    public void setEntityOrParticipantTypeId(Integer entityOrParticipantTypeId) {
        this.entityOrParticipantTypeId = entityOrParticipantTypeId;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public long getIdOfEntityOrParticipant() {
        return idOfEntityOrParticipant;
    }

    public void setIdOfEntityOrParticipant(long idOfEntityOrParticipant) {
        this.idOfEntityOrParticipant = idOfEntityOrParticipant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getNameOrTitle() {
        return nameOrTitle;
    }

    public void setNameOrTitle(String nameOrTitle) {
        this.nameOrTitle = nameOrTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListShortDescription() {
        return listShortDescription;
    }

    public void setListShortDescription(String listShortDescription) {
        this.listShortDescription = listShortDescription;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPostedDate() {
        return postingDate;
    }

    public void setPostedDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public Date getPostingDateOnly() {
        return postingDateOnly;
    }

    public void setPostingDateOnly(Date postingDateOnly) {
        this.postingDateOnly = postingDateOnly;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getAuthorParticipantId() {
        return authorParticipantId;
    }

    public void setAuthorParticipantId(long authorParticipantId) {
        this.authorParticipantId = authorParticipantId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public boolean isAuthorConfidential() {
        return isAuthorConfidential;
    }

    public void setAuthorConfidential(boolean authorConfidential) {
        isAuthorConfidential = authorConfidential;
    }

    public String getAuthorParticipantType() {
        return authorParticipantType;
    }

    public void setAuthorParticipantType(String authorParticipantType) {
        this.authorParticipantType = authorParticipantType;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public boolean isAuthorImagePublic() {
        return isAuthorImagePublic;
    }

    public void setAuthorImagePublic(boolean authorImagePublic) {
        isAuthorImagePublic = authorImagePublic;
    }

    public String getAuthorCityId() {
        return authorCityId;
    }

    public void setAuthorCityId(String authorCityId) {
        this.authorCityId = authorCityId;
    }

    public String getAuthorCityName() {
        return authorCityName;
    }

    public void setAuthorCityName(String authorCityName) {
        this.authorCityName = authorCityName;
    }

    public String getAuthorShortDescription() {
        return authorShortDescription;
    }

    public void setAuthorShortDescription(String authorShortDescription) {
        this.authorShortDescription = authorShortDescription;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
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

    public List<Comment> getLastComments() {
        return lastComments;
    }

    public void setLastComments(List<Comment> lastComments) {
        this.lastComments = lastComments;
    }

    public int getReactionValue() {
        return reactedValue;
    }

    public void setReactionValue(int reactedValue) {
        this.reactedValue = reactedValue;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public int getNoOfBookmarks() {
        return noOfBookmarks;
    }

    public void setNoOfBookmarks(int noOfBookmarks) {
        this.noOfBookmarks = noOfBookmarks;
    }

    public int getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(int noOfViews) {
        this.noOfViews = noOfViews;
    }

    public int getNoOfChallengeAccepted() {
        return noOfChallengeAccepted;
    }

    public void setNoOfChallengeAccepted(int noOfChallengeAccepted) {
        this.noOfChallengeAccepted = noOfChallengeAccepted;
    }

    public boolean isChallengeAccepted() {
        return isChallengeAccepted;
    }

    public void setChallengeAccepted(boolean challengeAccepted) {
        isChallengeAccepted = challengeAccepted;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public boolean isLongPress() {
        return isLongPress;
    }

    public void setLongPress(boolean longPress) {
        isLongPress = longPress;
    }

    public String getCallFromName() {
        return callFromName;
    }

    public void setCallFromName(String callFromName) {
        this.callFromName = callFromName;
    }

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }

    public boolean isFromHome() {
        return isFromHome;
    }

    public void setFromHome(boolean fromHome) {
        isFromHome = fromHome;
    }

    public AppIntroData getAppIntroDataItems() {
        return appIntroDataItems;
    }

    public void setAppIntroDataItems(AppIntroData appIntroDataItems) {
        this.appIntroDataItems = appIntroDataItems;
    }

    public String getDeepLinkUrl() {
        return deepLinkUrl;
    }

    public void setDeepLinkUrl(String deepLinkUrl) {
        this.deepLinkUrl = deepLinkUrl;
    }

    public int getLastReactionValue() {
        return lastReactionValue;
    }

    public void setLastReactionValue(int lastReactionValue) {
        this.lastReactionValue = lastReactionValue;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public int getReactedValue() {
        return reactedValue;
    }

    public void setReactedValue(int reactedValue) {
        this.reactedValue = reactedValue;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public int getNoOfApplied() {
        return noOfApplied;
    }

    public void setNoOfApplied(int noOfApplied) {
        this.noOfApplied = noOfApplied;
    }

    public List<Long> getTag_ids() {
        return tag_ids;
    }

    public void setTag_ids(List<Long> tag_ids) {
        this.tag_ids = tag_ids;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getParticipantDeepLinkingUrl() {
        return participantDeepLinkingUrl;
    }

    public void setParticipantDeepLinkingUrl(String participantDeepLinkingUrl) {
        this.participantDeepLinkingUrl = participantDeepLinkingUrl;
    }

    public String getEntityDeepLinkingUrl() {
        return entityDeepLinkingUrl;
    }

    public void setEntityDeepLinkingUrl(String entityDeepLinkingUrl) {
        this.entityDeepLinkingUrl = entityDeepLinkingUrl;
    }

    public String getOgImageUrlS() {
        return ogImageUrlS;
    }

    public void setOgImageUrlS(String ogImageUrlS) {
        this.ogImageUrlS = ogImageUrlS;
    }

    public String getOgTitleS() {
        return ogTitleS;
    }

    public void setOgTitleS(String ogTitleS) {
        this.ogTitleS = ogTitleS;
    }

    public String getOgDescriptionS() {
        return ogDescriptionS;
    }

    public void setOgDescriptionS(String ogDescriptionS) {
        this.ogDescriptionS = ogDescriptionS;
    }

    public String getOgRequestedUrlS() {
        return ogRequestedUrlS;
    }

    public void setOgRequestedUrlS(String ogRequestedUrlS) {
        this.ogRequestedUrlS = ogRequestedUrlS;
    }

    public boolean isOgVideoLinkB() {
        return isOgVideoLinkB;
    }

    public void setOgVideoLinkB(boolean ogVideoLinkB) {
        isOgVideoLinkB = ogVideoLinkB;
    }

    public boolean isAuthorMentor() {
        return isAuthorMentor;
    }

    public void setAuthorMentor(boolean authorMentor) {
        isAuthorMentor = authorMentor;
    }

    public String getMentorDeepLinkUrl() {
        return mentorDeepLinkUrl;
    }

    public void setMentorDeepLinkUrl(String mentorDeepLinkUrl) {
        this.mentorDeepLinkUrl = mentorDeepLinkUrl;
    }

    public String getBlogLinkUrl() {
        return blogLinkUrl;
    }

    public void setBlogLinkUrl(String blogLinkUrl) {
        this.blogLinkUrl = blogLinkUrl;
    }

    public String getUserSubType() {
        return userSubType;
    }

    public void setUserSubType(String userSubType) {
        this.userSubType = userSubType;
    }

    public boolean isSpamPost() {
        return isSpamPost;
    }

    public void setSpamPost(boolean spamPost) {
        isSpamPost = spamPost;
    }
}
