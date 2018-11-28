package appliedlife.pvtltd.SHEROES.basecomponents;

import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by ravi on 20/02/18.
 */

public interface FollowerFollowingCallback extends BaseHolderInterface {

    void onItemClick(UserSolrObj mentor);

    /**
     * Click event on follower/Following list
     *
     * @param userSolrObj user/ champion
     */
    void onFollowFollowingClick(UserSolrObj userSolrObj, String followFollowingBtnText);
}
