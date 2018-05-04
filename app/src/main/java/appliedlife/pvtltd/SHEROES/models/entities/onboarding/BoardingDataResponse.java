package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by Praveen_Singh on 01-04-2017.
 */

public class BoardingDataResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("docs")
    @Expose
    private UserSolrObj feedDetails = null;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public UserSolrObj getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(UserSolrObj feedDetails) {
        this.feedDetails = feedDetails;
    }
}
