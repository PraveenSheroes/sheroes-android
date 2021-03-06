package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;

/**
 * Created by ravi on 01/12/17.
 * Callbacks for navigation drawer items
 */

public interface INavDrawerCallback extends BaseMvpView {
    void getNavigationDrawerItemsSuccess(List<NavMenuItem> navigationItems);
    void getNavigationDrawerItemsFailed();
    void showMyCommunities(FeedResponsePojo feedResponse);
}
