package appliedlife.pvtltd.SHEROES.basecomponents;

import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by ujjwal on 28/12/17.
 */

public interface AllCommunityItemCallback extends BaseHolderInterface {

    void onCommunityClicked(CommunityFeedSolrObj communityFeedObj);

    void onCommunityJoinUnjoin(CommunityFeedSolrObj communityFeedSolrObj);

    void onSeeMoreClicked(CarouselDataObj carouselDataObj);

    void openChampionListingScreen(CarouselDataObj carouselDataObj);
}
