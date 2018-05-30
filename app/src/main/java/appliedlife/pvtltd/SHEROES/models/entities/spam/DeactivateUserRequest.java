package appliedlife.pvtltd.SHEROES.models.entities.spam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ravi on 09/04/18.
 * Deactivate the user
 */

public class DeactivateUserRequest extends BaseRequest {

    @SerializedName("reason_for_inactive")
    @Expose
    private String reasonForInactive = "Ignore this field ,deprecated";

    @SerializedName("user_id")
    @Expose
    private long userId;

    @SerializedName("reactivate")
    @Expose
    private boolean reactivateUser;

    @SerializedName("modify_posts")
    @Expose
    private boolean isRemovePostByUser;

    @SerializedName("modify_comments")
    @Expose
    private boolean isRemoveCommentByUser;

    @SerializedName("deactivation_reason")
    @Expose
    private int deactivationReason;

    @SerializedName("deactivation_reason_details")
    @Expose
    private String reasonForDeactivationDetails;

    public String getReasonForInactive() {
        return reasonForInactive;
    }

    public void setReasonForInactive(String reasonForInactive) {
        this.reasonForInactive = reasonForInactive;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isReactivateUser() {
        return reactivateUser;
    }

    public void setReactivateUser(boolean reactivateUser) {
        this.reactivateUser = reactivateUser;
    }

    public boolean isRemovePostByUser() {
        return isRemovePostByUser;
    }

    public void setRemovePostByUser(boolean removePostByUser) {
        isRemovePostByUser = removePostByUser;
    }

    public boolean isRemoveCommentByUser() {
        return isRemoveCommentByUser;
    }

    public void setRemoveCommentByUser(boolean removeCommentByUser) {
        isRemoveCommentByUser = removeCommentByUser;
    }

    public int getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(int deactivationReason) {
        this.deactivationReason = deactivationReason;
    }

    public String getReasonForDeactivationDetails() {
        return reasonForDeactivationDetails;
    }

    public void setReasonForDeactivationDetails(String reasonForDeactivationDetails) {
        this.reasonForDeactivationDetails = reasonForDeactivationDetails;
    }
}
