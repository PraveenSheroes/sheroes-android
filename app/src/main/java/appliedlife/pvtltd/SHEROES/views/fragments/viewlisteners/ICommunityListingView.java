package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;

/**
 * Created by ujjwal on 04/05/17.
 */

public interface ICommunityListingView extends BaseMvpView {
    void showAllCommunity(ArrayList<FeedDetail> feedDetails);

    void showMyCommunity(FeedResponsePojo feedResponse);

    void showCommunityJoinResponse(final CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder);

    void showCommunityUnJoinedResponse(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder);
}
