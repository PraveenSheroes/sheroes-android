package appliedlife.pvtltd.SHEROES.models.entities.comment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */

public class LastComment implements Parcelable {

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
    @SerializedName("participant_name")
    @Expose
    private String participantName;
    @SerializedName("participant_image_url")
    @Expose
    private String participantImageUrl;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.entityId);
        dest.writeInt(this.participantId);
        dest.writeInt(this.commentsId);
        dest.writeInt(this.participationTypeId);
        dest.writeString(this.createdOn);
        dest.writeString(this.lastModifiedOn);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAnonymous ? (byte) 1 : (byte) 0);
        dest.writeInt(this.likeValue);
        dest.writeString(this.comment);
        dest.writeString(this.participantName);
        dest.writeString(this.participantImageUrl);
    }

    public LastComment() {
    }

    protected LastComment(Parcel in) {
        this.id = in.readInt();
        this.entityId = in.readInt();
        this.participantId = in.readInt();
        this.commentsId = in.readInt();
        this.participationTypeId = in.readInt();
        this.createdOn = in.readString();
        this.lastModifiedOn = in.readString();
        this.isActive = in.readByte() != 0;
        this.isAnonymous = in.readByte() != 0;
        this.likeValue = in.readInt();
        this.comment = in.readString();
        this.participantName = in.readString();
        this.participantImageUrl = in.readString();
    }

    public static final Parcelable.Creator<LastComment> CREATOR = new Parcelable.Creator<LastComment>() {
        @Override
        public LastComment createFromParcel(Parcel source) {
            return new LastComment(source);
        }

        @Override
        public LastComment[] newArray(int size) {
            return new LastComment[size];
        }
    };
}
