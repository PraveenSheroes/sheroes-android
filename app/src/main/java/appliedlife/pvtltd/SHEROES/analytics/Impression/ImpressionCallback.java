package appliedlife.pvtltd.SHEROES.analytics.Impression;

import java.util.List;

import appliedlife.pvtltd.SHEROES.analytics.Impression.ImpressionData;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

public interface ImpressionCallback extends BaseMvpView {

    void onNetworkCall();

    FeedDetail getListItemAtPos(int pos);


    String getScreenName();

    /**
     * Store the impression in database
     * @param impressionData impressions
     */
    void storeInDatabase(List<ImpressionData> impressionData);

    /**
     * Show the toast message
     * @param message message to be shown
     */
    void showToast(String message);

}
