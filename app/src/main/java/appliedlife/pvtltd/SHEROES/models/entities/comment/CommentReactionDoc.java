package appliedlife.pvtltd.SHEROES.models.entities.comment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 15-02-2017.
 */

public class CommentReactionDoc extends BaseResponse implements Parcelable {
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
    @SerializedName("participant_image_url")
    @Expose
    private String participantImageUrl;
    @SerializedName("participant_name")
    @Expose
    private String participantName;
    @SerializedName("participation_type_id")
    @Expose
    private long participationTypeId;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
    }

    public CommentReactionDoc() {
    }

    protected CommentReactionDoc(Parcel in) {
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
    }

    public static final Parcelable.Creator<CommentReactionDoc> CREATOR = new Parcelable.Creator<CommentReactionDoc>() {
        @Override
        public CommentReactionDoc createFromParcel(Parcel source) {
            return new CommentReactionDoc(source);
        }

        @Override
        public CommentReactionDoc[] newArray(int size) {
            return new CommentReactionDoc[size];
        }
    };
}
