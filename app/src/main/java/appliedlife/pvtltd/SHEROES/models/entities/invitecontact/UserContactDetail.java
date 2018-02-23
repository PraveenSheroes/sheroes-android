package appliedlife.pvtltd.SHEROES.models.entities.invitecontact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen on 14/02/18.
 */
@Parcel(analyze = {UserContactDetail.class,BaseResponse.class})
public class UserContactDetail extends BaseResponse {
    private int itemPosition;
    @SerializedName("contact_name")
    @Expose
    private String name;

    @SerializedName("emails_list")
    @Expose
    private List<String> emailId=new ArrayList<>();
    @SerializedName("mobile_numbers_list")
    @Expose
    private List<String> phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmailId() {
        return emailId;
    }

    public void setEmailId(List<String> emailId) {
        this.emailId = emailId;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }
}
