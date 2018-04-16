package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;

/**
 * Created by ravi on 01/01/18.
 */

public interface ProfileView extends BaseMvpView {

    void getFollowedMentors(UserFollowedMentorsResponse profileFeedResponsePojo);

    void getFeedListSuccess(FeedResponsePojo feedResponsePojo);

    void getTopSectionCount(ProfileTopSectionCountsResponse profileTopSectionCountsResponse);

    void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities);

    void postOrCommentSpamResponse(SpamResponse communityFeedSolrObj);

}
