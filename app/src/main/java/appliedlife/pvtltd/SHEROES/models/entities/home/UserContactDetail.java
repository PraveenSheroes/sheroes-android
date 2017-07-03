package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 28-06-2017.
 */

public class UserContactDetail extends BaseRequest {

    @SerializedName("name")
    @Expose
    String contactName;

    @SerializedName("phone_no")
    @Expose
    String phoneNo;

    @SerializedName("email")
    @Expose
    String email;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public UserContactDetail(){}

    public UserContactDetail(String contactName, String phoneNo){
        this.contactName = contactName;
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
