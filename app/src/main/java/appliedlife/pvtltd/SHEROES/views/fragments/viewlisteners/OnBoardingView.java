package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */

public interface OnBoardingView extends BaseMvpView {
    void getAllDataResponse(GetAllData getAllData);
    void showDataList(List<FeedDetail> feedDetailList);
    void joinResponse(CommunityFeedSolrObj communityFeedSolrObj);
    void unJoinResponse(CommunityFeedSolrObj communityFeedSolrObj);

    void onConfigFetched();
}

