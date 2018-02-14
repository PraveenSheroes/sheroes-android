package appliedlife.pvtltd.SHEROES.models.entities.contactdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen on 14/02/18.
 */
@Parcel(analyze = {UserContactDetail.class,BaseResponse.class})
public class UserContactDetail extends BaseResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("contact_name")
    @Expose
    private String name;

    @SerializedName("contact_email_id")
    @Expose
    private String emailId;
    @SerializedName("contact_phone_number")
    @Expose
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object obj){
        if (obj instanceof UserContactDetail) {
            UserContactDetail userContactDetail = (UserContactDetail) obj;
            return (userContactDetail.getPhoneNumber().equals(this.getPhoneNumber()));
        } else {
            return false;
        }
    }
}
