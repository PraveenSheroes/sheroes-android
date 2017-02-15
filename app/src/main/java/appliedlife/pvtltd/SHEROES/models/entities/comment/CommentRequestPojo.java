package appliedlife.pvtltd.SHEROES.models.entities.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */

public class CommentRequestPojo extends BaseRequest {

    @SerializedName("comment")
    @Expose
    private String userComment;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("is_anonymous")
    @Expose
    private String isAnonymous;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("anonymous")
    @Expose
    private boolean anonymous;
    @SerializedName("participation_id")
    @Expose
    private int participationId;

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }
}
