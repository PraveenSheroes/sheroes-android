package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserFollowerOrFollowingRequest;

/**
 * Created by ravi on 01/01/18.
 */

public interface ProfileNewView extends BaseMvpView {

    void getFollowedMentors(UserFollowedMentorsResponse profileFeedResponsePojo);

    void getFeedListSuccess(FeedResponsePojo feedResponsePojo);

    void getUsersFollowerCount(BaseResponse userFollowerOrFollowingCountResponse);

    void getUsersFollowingCount(BaseResponse userFollowerOrFollowingCountResponse);

    void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities);

    void getUsersPostCount(int totalPost);

}
