package appliedlife.pvtltd.SHEROES.models.entities.invitecontact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen on 16/02/18.
 */
public class ContactListSyncRequest extends BaseRequest{
    @SerializedName("next_token")
    @Expose
    private String nextToken;
    @SerializedName("users_contact_list")
    @Expose
    private List<UserContactDetail> usersContactList = null;

    public List<UserContactDetail> getUsersContactList() {
        return usersContactList;
    }

    public void setUsersContactList(List<UserContactDetail> usersContactList) {
        this.usersContactList = usersContactList;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }
}
