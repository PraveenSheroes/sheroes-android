package appliedlife.pvtltd.SHEROES.models.entities.invitecontact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen on 20/02/18.
 */
@Parcel(analyze = {UpdateInviteUrlResponse.class,BaseResponse.class})
public class UpdateInviteUrlResponse extends BaseResponse {
    @SerializedName("updated_app_invited_url")
    @Expose
    private String updatedAppInviteUrl;

    public String getUpdatedAppInviteUrl() {
        return updatedAppInviteUrl;
    }

    public void setUpdatedAppInviteUrl(String updatedAppInviteUrl) {
        this.updatedAppInviteUrl = updatedAppInviteUrl;
    }
}
