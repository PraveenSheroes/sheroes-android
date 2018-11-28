package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

public interface IFollowCallback {

    /**
     * get callaback on follow/un-follow
     * @param userSolrObj userSolrObj
     */
    void onProfileFollowed(UserSolrObj userSolrObj);
}
