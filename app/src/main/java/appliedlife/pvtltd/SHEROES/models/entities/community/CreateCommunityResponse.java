package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by SHEROES-TECH on 03-03-2017.
 */

public class CreateCommunityResponse extends BaseResponse {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("doc")
    @Expose
    private FeedDetail feedDetail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FeedDetail getFeedDetail() {
        return feedDetail;
    }

    public void setFeedDetail(FeedDetail feedDetail) {
        this.feedDetail = feedDetail;
    }
}
