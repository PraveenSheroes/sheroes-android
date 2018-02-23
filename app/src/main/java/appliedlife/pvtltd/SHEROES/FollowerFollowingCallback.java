package appliedlife.pvtltd.SHEROES;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by ravi on 20/02/18.
 */

public interface FollowerFollowingCallback extends BaseHolderInterface {

    void onItemClick(UserSolrObj mentor);
}
