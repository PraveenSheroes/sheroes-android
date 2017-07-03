package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 28-06-2017.
 */

public class UserPhoneContactsListRequest extends BaseRequest {

    @SerializedName("user_phone_contacts_list")
    @Expose
    private List<UserContactDetail> contactDetailList;

    public List<UserContactDetail> getContactDetailList() {
        return contactDetailList;
    }

    public void setContactDetailList(List<UserContactDetail> contactDetailList) {
        this.contactDetailList = contactDetailList;
    }
}
