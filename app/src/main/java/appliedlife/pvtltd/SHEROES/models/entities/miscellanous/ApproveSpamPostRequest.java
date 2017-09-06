package appliedlife.pvtltd.SHEROES.models.entities.miscellanous;

import com.google.android.gms.common.api.BatchResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen on 06/09/17.
 */

public class ApproveSpamPostRequest extends BaseRequest {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("is_active")
    @Expose
    private boolean isActive =true;
    @SerializedName("is_spam")
    @Expose
    private boolean isSpam;
    @SerializedName("is_approved")
    @Expose
    private boolean isApproved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
