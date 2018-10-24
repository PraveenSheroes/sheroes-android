package appliedlife.pvtltd.SHEROES.analytics.Impression;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

public interface ImpressionCallback extends BaseMvpView {

    /**
     * Get the object on position
     * @param pos position in list
     * @return FeedDetail
     */
    FeedDetail getListItemAtPos(int pos);

    /**
     * Get the screen Name
     * @return screen Name
     */
    String getScreenName();

    /**
     * Store the impression in database
     * @param impressionData impressions
     * @param batchSize  size of batch
     * @param minEngagementTime min time spent on view
     * @param forceNetworkCall true if want to fire network call immediately
     */
    void storeInDatabase(List<ImpressionData> impressionData, int batchSize, float minEngagementTime, boolean forceNetworkCall);

    /**
     * Show the toast message
     * @param message message to be shown
     */
    void showToast(String message);

    /**
     * send impression to server
     */
    void sendImpression();

}
