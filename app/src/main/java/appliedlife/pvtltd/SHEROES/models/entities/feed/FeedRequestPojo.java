package appliedlife.pvtltd.SHEROES.models.entities.feed;

/**
 * Created by Praveen_Singh on 10-02-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

public class FeedRequestPojo extends BaseRequest {

    @SerializedName("id")
    @Expose
    private String idForFeedDetail;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("q")
    @Expose
    private String question;
    @SerializedName("screen_name")
    @Expose
    private String screenName;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getIdForFeedDetail() {
        return idForFeedDetail;
    }

    public void setIdForFeedDetail(String idForFeedDetail) {
        this.idForFeedDetail = idForFeedDetail;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
