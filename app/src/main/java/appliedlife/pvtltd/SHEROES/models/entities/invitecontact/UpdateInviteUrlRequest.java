package appliedlife.pvtltd.SHEROES.models.entities.invitecontact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen on 20/02/18.
 */

public class UpdateInviteUrlRequest extends BaseRequest {
    @SerializedName("app_invite_url")
    @Expose
    private String appInviteUrl;

    public String getAppInviteUrl() {
        return appInviteUrl;
    }

    public void setAppInviteUrl(String appInviteUrl) {
        this.appInviteUrl = appInviteUrl;
    }
}
