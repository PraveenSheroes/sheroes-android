package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by Ravi on 01/01/18.
 */
@Parcel(analyze = {ProfileFeedResponsePojo1.class, BaseResponse.class})
public class ProfileFeedResponsePojo1 extends BaseResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("numFound")
    @Expose
    private Integer numFound;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("docs")
    @Expose
    private List<UserSolrObj> userSolrObjs = null;
    @SerializedName("screen_name")
    @Expose
    private String screenName;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    @Override
    public int getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<UserSolrObj> getUserSolrObjs() {
        return userSolrObjs;
    }

    public void setUserSolrObjs(List<UserSolrObj> userSolrObjs) {
        this.userSolrObjs = userSolrObjs;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }



}
