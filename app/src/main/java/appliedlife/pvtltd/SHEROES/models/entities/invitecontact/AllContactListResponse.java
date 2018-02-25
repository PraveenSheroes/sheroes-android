package appliedlife.pvtltd.SHEROES.models.entities.invitecontact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by Praveen on 16/02/18.
 */
@Parcel(analyze = {AllContactListResponse.class,BaseResponse.class})
public class AllContactListResponse extends BaseResponse {
    @SerializedName("next_token")
    @Expose
    private String nextToken;
    @SerializedName("sheroes_contacts")
    @Expose
    private List<UserSolrObj> sheroesContacts ;
    @SerializedName("non_sheroes_contacts")
    @Expose
    private List<UserContactDetail> nonSheroesContacts ;

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public List<UserSolrObj> getSheroesContacts() {
        return sheroesContacts;
    }

    public void setSheroesContacts(List<UserSolrObj> sheroesContacts) {
        this.sheroesContacts = sheroesContacts;
    }

    public List<UserContactDetail> getNonSheroesContacts() {
        return nonSheroesContacts;
    }

    public void setNonSheroesContacts(List<UserContactDetail> nonSheroesContacts) {
        this.nonSheroesContacts = nonSheroesContacts;
    }
}
