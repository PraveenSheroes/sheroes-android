package appliedlife.pvtltd.SHEROES.basecomponents;

import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ImageSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;

/**
 * Created by ujjwal on 28/12/17.
 */

public interface FeedItemCallback extends BaseHolderInterface {
    void onArticleUnBookMarkClicked(ArticleSolrObj articleSolrObj);

    void onArticleBookMarkClicked(ArticleSolrObj articleSolrObj);

    void onArticleItemClicked(ArticleSolrObj articleSolrObj);

    void onPostShared(FeedDetail feedDetail);

    void onUserPostClicked(FeedDetail feedDetail);

    void onUserPostCommentClicked(UserPostSolrObj userPostObj);

    void onUserPostImageClicked(UserPostSolrObj userPostObj);

    void onPostMenuClicked(UserPostSolrObj userPostObj, View tvFeedCommunityPostUserMenu);

    void onPollMenuClicked(PollSolarObj pollSolarObj, View tvFeedCommunityPollMenu);

    void onCommentMenuClicked(UserPostSolrObj userPostObj, TextView tvFeedCommunityPostUserCommentPostMenu);

    void onPostBookMarkedClicked(UserPostSolrObj userPostObj);

    void onLikesCountClicked(long postId);

    void onUserPostLiked(UserPostSolrObj userPostObj);

    void onPollLiked(PollSolarObj pollSolarObj);

    void onPollVote(PollSolarObj pollSolarObj, PollOptionModel pollOptionModel);

    void onUserPostUnLiked(UserPostSolrObj userPostObj);

    void onPollUnLiked(PollSolarObj pollSolarObj);

    void onChampionProfileClicked(FeedDetail feedDetail, int requestCodeForMentorProfileDetail);

    void onCommunityTitleClicked(FeedDetail feedDetail);

    void userCommentLikeRequest(UserPostSolrObj comment, boolean isLikedAction, int adapterPosition);

    void onChallengeClicked(Contest contest);

    void onChallengePostShared(BaseResponse baseResponse);

    void onFollowClicked(UserSolrObj userSolrObj);

    void onMentorProfileClicked(UserSolrObj userSolrObj);

    void onMentorProfileClicked(UserPostSolrObj userSolrObj);

    void onFeedLastCommentUserClicked(UserPostSolrObj userSolrObj);

    void onArticleCommentClicked(ArticleSolrObj articleObj);

    void onArticlePostLiked(ArticleSolrObj articleSolrObj);

    void onArticlePostUnLiked(ArticleSolrObj articleSolrObj);

    void onSpamPostApprove(UserPostSolrObj userPostObj);

    void onSpamPostDelete(UserPostSolrObj userPostObj);

    void onCommunityClicked(CommunityFeedSolrObj communityFeedObj);

    void onCommunityClicked(long communityId);

    void onHeaderCreatePostClicked();

    void onCommunityJoinOrLeave(CommunityFeedSolrObj communityFeedSolrObj);

    void onSeeMoreClicked(CarouselDataObj carouselDataObj);

    void onImagePostClicked(ImageSolrObj imageSolrObj);

    void onUserHeaderClicked(CommunityFeedSolrObj communityFeedSolrObj, boolean authorMentor);

    void onPostMenuClicked(ArticleSolrObj articleObj, View view);

    void onHerStoryPostMenuClicked(ArticleSolrObj articleObj, View view);

    void onUpdateNowClicked();

    void onUpdateLaterClicked();

    void onLeaderBoardItemClick(LeaderBoardUserSolrObj leaderBoardUserSolrObj, String screenName);

    void onLeaderBoardHeaderClick(LeaderBoardUserSolrObj leaderBoardUserSolrObj, String screenName);

    void onLeaderBoardUserClick(long userId, String screenName);

    void onPostAuthorFollowed(UserPostSolrObj userPostSolrObj);
}
