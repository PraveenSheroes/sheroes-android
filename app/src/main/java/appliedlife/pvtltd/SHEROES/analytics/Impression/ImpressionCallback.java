package appliedlife.pvtltd.SHEROES.analytics.Impression;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Callback for impression helper
 *
 * @author ravi
 */
public interface ImpressionCallback extends BaseMvpView {

    /**
     * Get the object on position
     * @param pos position in list
     * @return FeedDetail
     */
    FeedDetail getListItemAtPos(int pos);

    /**
     * Return the position of particular post
     * @param postId post id
     * @return position in recycler
     */
    int findPositionById(long postId);

    /**
     * Get the screen Name
     * @return screen Name
     */
    String getScreenName();
}
