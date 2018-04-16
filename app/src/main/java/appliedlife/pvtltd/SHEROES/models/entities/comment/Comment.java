package appliedlife.pvtltd.SHEROES.models.entities.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 15-02-2017.
 */
@Parcel(analyze = {Comment.class,BaseResponse.class})
public class Comment extends BaseResponse{
    int byDefaultMenuOpen;
    boolean isEdit;
    int itemPosition;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("comments_id")
    @Expose
    private long commentsId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("entity_id")
    @Expose
    private long entityId;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("is_anonymous")
    @Expose
    private boolean isAnonymous;
    @SerializedName("last_modified_on")
    @Expose
    private String lastModifiedOn;
    @SerializedName("like_value")
    @Expose
    private int likeValue;
    @SerializedName("participant_id")
    @Expose
    private long participantId;
    @SerializedName("solr_ignore_participant_image_url")
    @Expose
    private String participantImageUrl;
    @SerializedName("solr_ignore_participant_name")
    @Expose
    private String participantName;
    @SerializedName("participation_type_id")
    @Expose
    private long participationTypeId;
    @SerializedName("solr_ignore_my_own_participation")
    @Expose
    private boolean myOwnParticipation;
    @SerializedName("solr_ignore_created_on")
    @Expose
    private String postedDate;

    @SerializedName("solr_ignore_city")
    @Expose
    private String city;

    @SerializedName("entity_author_user_id_l")
    @Expose
    private Long entityAuthorUserId;


    @SerializedName("solr_ignore_is_participant_active")
    @Expose
    private boolean participantActive = false;
    @SerializedName("solr_ignore_is_mentor")
    @Expose
    private boolean isVerifiedMentor;

    @SerializedName("solr_ignore_number_of_likes_on_comment")
    @Expose
    public int likeCount;

    @SerializedName("solr_ignore_is_my_own_like_on_comment")
    @Expose
    public boolean isLiked;

    @SerializedName("solr_ignore_participant_user_id")
    private Long participantUserId;

    @SerializedName("particitipating_entity_community_id_l")
    private String communityId;

    @SerializedName("is_spam_comment_b")
    private boolean isSpamComment;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(long commentsId) {
        this.commentsId = commentsId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public int getLikeValue() {
        return likeValue;
    }

    public void setLikeValue(int likeValue) {
        this.likeValue = likeValue;
    }

    public long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(long participantId) {
        this.participantId = participantId;
    }

    public String getParticipantImageUrl() {
        return participantImageUrl;
    }

    public void setParticipantImageUrl(String participantImageUrl) {
        this.participantImageUrl = participantImageUrl;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public long getParticipationTypeId() {
        return participationTypeId;
    }

    public void setParticipationTypeId(long participationTypeId) {
        this.participationTypeId = participationTypeId;
    }

    public Comment() {
    }

    public boolean isMyOwnParticipation() {
        return myOwnParticipation;
    }

    public void setMyOwnParticipation(boolean myOwnParticipation) {
        this.myOwnParticipation = myOwnParticipation;
    }

    public int getByDefaultMenuOpen() {
        return byDefaultMenuOpen;
    }

    public void setByDefaultMenuOpen(int byDefaultMenuOpen) {
        this.byDefaultMenuOpen = byDefaultMenuOpen;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getEntityAuthorUserId() {
        return entityAuthorUserId;
    }

    public void setEntityAuthorUserId(Long entityAuthorUserId) {
        this.entityAuthorUserId = entityAuthorUserId;
    }

    public boolean isParticipantActive() {
        return participantActive;
    }

    public void setParticipantActive(boolean participantActive) {
        this.participantActive = participantActive;
    }

    public boolean isVerifiedMentor() {
        return isVerifiedMentor;
    }

    public void setVerifiedMentor(boolean verifiedMentor) {
        isVerifiedMentor = verifiedMentor;
    }

    public Long getParticipantUserId() {
        return participantUserId;
    }

    public void setParticipantUserId(Long participantUserId) {
        this.participantUserId = participantUserId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public boolean isSpamComment() {
        return isSpamComment;
    }

    public void setSpamComment(boolean spamComment) {
        isSpamComment = spamComment;
    }
}
