package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.post.Winner;

/**
 * Created by ujjwal on 04/05/17.
 */

public interface IFeedView extends BaseMvpView {
    void showFeedList(List<FeedDetail> feedDetailList);
}
