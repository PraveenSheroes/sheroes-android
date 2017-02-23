package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by SHEROES-TECH on 20-02-2017.
 */

public class JobDetailPojo extends BaseResponse {
    private FeedDetail feedDetail;
    private int id;
    public FeedDetail getFeedDetail() {
        return feedDetail;
    }

    public void setFeedDetail(FeedDetail feedDetail) {
        this.feedDetail = feedDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
