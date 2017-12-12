package appliedlife.pvtltd.SHEROES.models.entities.post;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ujjwal on 03/05/17.
 */
@Parcel(analyze = {Address.class})
public class Address extends BaseRequest{
    public static final String ADDRESS_OBJ = "ADDRESS_OBJ";

    @SerializedName("user_full_name")
    public String fullName;

    @SerializedName("user_phone_number")
    public String mobileNumber;

    @SerializedName("user_contact_address")
    public String fullAddress;

    @SerializedName("user_email_id")
    public String emailAddress;

    @SerializedName("user_address_pincode")
    public String pinCode;

    @SerializedName("challenge_id")
    public String challengeId;

}
