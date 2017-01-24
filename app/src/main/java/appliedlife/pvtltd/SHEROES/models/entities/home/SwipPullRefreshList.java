package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class SwipPullRefreshList {
    @SerializedName("feedResponses")
    @Expose
    private List<ListOfFeed> feedResponses = new ArrayList<ListOfFeed>();
    @SerializedName("isPullToRefresh")
    @Expose
    private boolean isPullToRefresh;
    public List<ListOfFeed> getFeedResponses() {
        return feedResponses;
    }

    public void setFeedResponses(List<ListOfFeed> feedResponses) {
        this.feedResponses = feedResponses;
    }

    public void allListData(List<ListOfFeed> feedResponses) {
        this.feedResponses.addAll(feedResponses);
    }

    public boolean isPullToRefresh() {
        return isPullToRefresh;
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        isPullToRefresh = pullToRefresh;
    }
}
