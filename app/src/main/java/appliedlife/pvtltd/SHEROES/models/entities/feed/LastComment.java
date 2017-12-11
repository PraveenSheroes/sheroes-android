package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */
@Parcel(analyze = {LastComment.class})
public class LastComment{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("entity_id")
    @Expose
    private int entityId;
    @SerializedName("participant_id")
    @Expose
    private int participantId;
    @SerializedName("comments_id")
    @Expose
    private int commentsId;
    @SerializedName("participation_type_id")
    @Expose
    private int participationTypeId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("last_modified_on")
    @Expose
    private String lastModifiedOn;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("is_anonymous")
    @Expose
    private boolean isAnonymous;
    @SerializedName("like_value")
    @Expose
    private int likeValue;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("solr_ignore_participant_name")
    @Expose
    private String participantName;
    @SerializedName("solr_ignore_participant_image_url")
    @Expose
    private String participantImageUrl;

    @SerializedName("entity_author_user_id_l")
    @Expose
    private int entityAuthorUserIdL;
    @SerializedName("solr_ignore_my_own_participation")
    @Expose
    private boolean myOwnParticipation;

    @SerializedName("solr_ignore_is_mentor")
    @Expose
    private boolean isVerifiedMentor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public int getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(int commentsId) {
        this.commentsId = commentsId;
    }

    public int getParticipationTypeId() {
        return participationTypeId;
    }

    public void setParticipationTypeId(int participationTypeId) {
        this.participationTypeId = participationTypeId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getLikeValue() {
        return likeValue;
    }

    public void setLikeValue(int likeValue) {
        this.likeValue = likeValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantImageUrl() {
        return participantImageUrl;
    }

    public void setParticipantImageUrl(String participantImageUrl) {
        this.participantImageUrl = participantImageUrl;
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

    public LastComment() {
    }

    public int getEntityAuthorUserIdL() {
        return entityAuthorUserIdL;
    }

    public void setEntityAuthorUserIdL(int entityAuthorUserIdL) {
        this.entityAuthorUserIdL = entityAuthorUserIdL;
    }

    public boolean isMyOwnParticipation() {
        return myOwnParticipation;
    }

    public void setMyOwnParticipation(boolean myOwnParticipation) {
        this.myOwnParticipation = myOwnParticipation;
    }

    public boolean isVerifiedMentor() {
        return isVerifiedMentor;
    }

    public void setVerifiedMentor(boolean verifiedMentor) {
        isVerifiedMentor = verifiedMentor;
    }

}
