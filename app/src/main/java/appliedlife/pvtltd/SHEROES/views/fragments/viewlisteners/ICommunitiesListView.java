package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.ArrayList;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;

/**
 * Created by ujjwal on 04/05/17.
 */

public interface ICommunitiesListView extends BaseMvpView {

    void showAllCommunity(ArrayList<FeedDetail> feedDetails);

    void showMyCommunities(FeedResponsePojo feedResponse);

    void setCommunity(CommunityFeedSolrObj communityFeedSolrObj);

    void invalidateCommunityJoin(CommunityFeedSolrObj communityFeedSolrObj);

    void invalidateCommunityLeft(CommunityFeedSolrObj communityFeedSolrObj);

    void onCommunityJoined(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder);

    void onCommunityLeft(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder);
}
