package appliedlife.pvtltd.SHEROES.models.entities.feed;

/**
 * Created by Praveen_Singh on 10-02-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

@Parcel(analyze = {CommunityFeedRequestPojo.class,BaseRequest.class})
public class CommunityFeedRequestPojo extends BaseRequest{
    @SerializedName("next_token")
    private String nextToken;

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }
}
