package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Winner;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;

/**
 * Created by ujjwal on 04/05/17.
 */

public interface IFeedView extends BaseMvpView {
    void showFeedList(List<FeedDetail> feedDetailList);

    void setData(int i, FeedDetail feedDetail);

    void invalidateItem(FeedDetail feedDetail);

    void notifyAllItemRemoved(FeedDetail feedDetail);

    void addAllFeed(List<FeedDetail> feedList);

    void setFeedEnded(boolean feedEnded);

    void removeItem(FeedDetail feedDetail);

    void invalidateCommunityJoin(CommunityFeedSolrObj communityFeedSolrObj);

    void invalidateCommunityLeft(CommunityFeedSolrObj communityFeedSolrObj);

    void showGifLoader();

    void hideGifLoader();

    void updateFeedConfigDataToMixpanel(FeedResponsePojo feedResponsePojo);

    void onSpamPostOrCommentReported(SpamResponse baseResponse, UserPostSolrObj userPostSolrObj);

    void likeUnlikeResponse(FeedDetail feedDetail,boolean isLike);

    void bookmarkedUnBookMarkedResponse(UserPostSolrObj userPostSolrObj);

    void pollVoteResponse(FeedDetail feedDetail,long pollOptionId);
}
