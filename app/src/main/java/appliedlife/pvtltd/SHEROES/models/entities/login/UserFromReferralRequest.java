package appliedlife.pvtltd.SHEROES.models.entities.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Deepak on 06-07-2017.
 */

public class UserFromReferralRequest extends BaseRequest {

    @SerializedName("app_user_contact_table_id")
    @Expose
    private long appUserContactTableId;

    public long getAppUserContactTableId() {
        return appUserContactTableId;
    }

    public void setAppUserContactTableId(long appUserContactTableId) {
        this.appUserContactTableId = appUserContactTableId;
    }
}
