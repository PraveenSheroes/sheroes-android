package appliedlife.pvtltd.SHEROES.models.entities.comment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 15-02-2017.
 */

public class Comment extends BaseResponse implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.byDefaultMenuOpen);
        dest.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        dest.writeInt(this.itemPosition);
        dest.writeString(this.comment);
        dest.writeLong(this.commentsId);
        dest.writeString(this.createdOn);
        dest.writeLong(this.entityId);
        dest.writeInt(this.id);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAnonymous ? (byte) 1 : (byte) 0);
        dest.writeString(this.lastModifiedOn);
        dest.writeInt(this.likeValue);
        dest.writeLong(this.participantId);
        dest.writeString(this.participantImageUrl);
        dest.writeString(this.participantName);
        dest.writeLong(this.participationTypeId);
        dest.writeByte(this.myOwnParticipation ? (byte) 1 : (byte) 0);
        dest.writeString(this.postedDate);
        dest.writeString(this.city);
        dest.writeValue(this.entityAuthorUserId);
        dest.writeByte(this.participantActive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isVerifiedMentor ? (byte) 1 : (byte) 0);
    }

    protected Comment(Parcel in) {
        super(in);
        this.byDefaultMenuOpen = in.readInt();
        this.isEdit = in.readByte() != 0;
        this.itemPosition = in.readInt();
        this.comment = in.readString();
        this.commentsId = in.readLong();
        this.createdOn = in.readString();
        this.entityId = in.readLong();
        this.id = in.readInt();
        this.isActive = in.readByte() != 0;
        this.isAnonymous = in.readByte() != 0;
        this.lastModifiedOn = in.readString();
        this.likeValue = in.readInt();
        this.participantId = in.readLong();
        this.participantImageUrl = in.readString();
        this.participantName = in.readString();
        this.participationTypeId = in.readLong();
        this.myOwnParticipation = in.readByte() != 0;
        this.postedDate = in.readString();
        this.city = in.readString();
        this.entityAuthorUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.participantActive = in.readByte() != 0;
        this.isVerifiedMentor = in.readByte() != 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
