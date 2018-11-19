package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;

/**
 * Created by ravi on 01/01/18.
 */

public interface IFollowerFollowingView extends BaseMvpView {

    void getFollowersOrFollowing(UserFollowedMentorsResponse profileFeedResponsePojo);
}
