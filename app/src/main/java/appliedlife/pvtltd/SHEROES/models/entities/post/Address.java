package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

/**
 * Created by ujjwal on 03/05/17.
 */
@Parcel(analyze = {Address.class})
public class Address {
    public static final String ADDRESS_OBJ = "ADDRESS_OBJ";
    public String fullName;
    public String mobileNumber;
    public String fullAddress;
    public String emailAddress;
    public String pinCode;
}
