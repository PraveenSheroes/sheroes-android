package appliedlife.pvtltd.SHEROES.models.entities.home;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by Praveen_Singh on 16-06-2017.
 */

public class EventDetailPojo  extends BaseResponse {
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