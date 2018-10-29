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
     * Show the toast message
     * @param message message to be shown
     */
    @Deprecated
    void showToast(String message);

}
