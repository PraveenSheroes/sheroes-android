package appliedlife.pvtltd.SHEROES.basecomponents;

import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;

/**
 * Created by ujjwal on 28/12/17.
 */

public interface AllCommunityItemCallback extends BaseHolderInterface {

    void onMyCommunityClicked(CommunityFeedSolrObj communityFeedObj);

    void onCommunityClicked(CommunityFeedSolrObj communityFeedObj);

    void joinRequestForOpenCommunity(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder);

    void unJoinCommunity(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder);

    void onSeeMoreClicked(CarouselDataObj carouselDataObj);

    void openChampionListingScreen(CarouselDataObj carouselDataObj);
}
