package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class SwipPullRefreshList<T extends Object>  {
    @SerializedName("feedResponses")
    @Expose
    private List<T> feedResponses = new ArrayList<>();
    @SerializedName("isPullToRefresh")
    @Expose
    private boolean isPullToRefresh;
    public List<T> getFeedResponses() {
        return feedResponses;
    }

    public void setFeedResponses(List<T> feedResponses) {
        this.feedResponses = feedResponses;
    }

    public void allListData(List<T> feedResponses) {
        this.feedResponses.addAll(feedResponses);
    }

    public boolean isPullToRefresh() {
        return isPullToRefresh;
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        isPullToRefresh = pullToRefresh;
    }
}
