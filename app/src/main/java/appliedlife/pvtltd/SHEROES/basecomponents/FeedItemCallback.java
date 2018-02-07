package appliedlife.pvtltd.SHEROES.basecomponents;

import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;

/**
 * Created by ujjwal on 28/12/17.
 */

public interface FeedItemCallback extends BaseHolderInterface {
    void onArticleUnBookMarkClicked(ArticleSolrObj articleSolrObj);

    void onArticleBookMarkClicked(ArticleSolrObj articleSolrObj);

    void onArticleItemClicked(ArticleSolrObj articleSolrObj);

    void onPostShared(FeedDetail feedDetail);

    void onUserPostClicked(UserPostSolrObj mUserPostObj);

    void onUserPostCommentClicked(UserPostSolrObj userPostObj);

    void onUserPostImageClicked(UserPostSolrObj userPostObj);

    void onPostMenuClicked(UserPostSolrObj userPostObj, View tvFeedCommunityPostUserMenu);

    void onCommentMenuClicked(UserPostSolrObj userPostObj, TextView tvFeedCommunityPostUserCommentPostMenu);

    void onPostBookMarkedClicked(UserPostSolrObj userPostObj);

    void onLikesCountClicked(long postId);

    void onUserPostLiked(UserPostSolrObj userPostObj);

    void onUserPostUnLiked(UserPostSolrObj userPostObj);

    void onChampionProfileClicked(UserPostSolrObj userPostObj, int requestCodeForMentorProfileDetail);

    void onCommunityTitleClicked(UserPostSolrObj userPostObj);

    void userCommentLikeRequest(UserPostSolrObj comment, boolean isLikedAction, int adapterPosition);

    void onJobPostClicked(JobFeedSolrObj jobFeedObj);

    void onChallengeClicked(Contest contest);

    void onChallengePostShared(BaseResponse baseResponse);

    void onEventPostClicked(UserPostSolrObj userPostSolrObj);

    void onEventInterestedClicked(UserPostSolrObj userPostSolrObj);

    void onEventNotInterestedClicked(UserPostSolrObj userPostSolrObj);

    void onEventGoingClicked(UserPostSolrObj userPostSolrObj);

    void onOrgTitleClicked(UserPostSolrObj userPostObj);

    void onMentorFollowClicked(UserSolrObj userSolrObj);

    void onMentorAskQuestionClicked(UserSolrObj userSolrObj);

    void onMentorProfileClicked(UserSolrObj userSolrObj);

    void onArticleCommentClicked(ArticleSolrObj articleObj);

    void onArticlePostLiked(ArticleSolrObj articleSolrObj);

    void onArticlePostUnLiked(ArticleSolrObj articleSolrObj);

    void onSpamPostApprove(UserPostSolrObj userPostObj);

    void onSpamPostDelete(UserPostSolrObj userPostObj);

    void onCommunityClicked(CommunityFeedSolrObj communityFeedObj);

    void onAskQuestionClicked();
}
