package appliedlife.pvtltd.SHEROES.basecomponents;

import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;

/**
 * Created by ujjwal on 07/12/17.
 */

public interface PostDetailCallBack {
    void loadMoreComments();

    void onPostImageClicked(UserPostSolrObj userPostObj);

    void onPostMenuClicked(UserPostSolrObj userPostObj, TextView view);

    void onSpamMenuClicked(UserPostSolrObj userPostObj, TextView spamPostView);

    void onShareButtonClicked(UserPostSolrObj userPostObj, TextView shareView);

    void onPollVote(PollSolarObj pollSolarObj, PollOptionModel pollOptionModel);

    void onPostLikeClicked(UserPostSolrObj userPostObj);

    void onPostUnLikeClicked(UserPostSolrObj userPostObj);

    void onChampionProfileClicked(FeedDetail feedDetail, int requestCodeForMentorProfileDetail);

    void onSpamApprovedClicked(UserPostSolrObj userPostObj, TextView view);

    void onSpamPostDeleteClicked(UserPostSolrObj userPostObj, TextView view);

    void onCommentButtonClicked();

    void onCommunityTitleClicked(UserPostSolrObj userPostObj);

    void onLikeCountClicked(UserPostSolrObj userPostObj);

    void onPollMenuClicked(PollSolarObj pollSolarObj, TextView view);

    void onCommunityTitleClicked(PollSolarObj pollSolarObj);

    void onLikeCountClicked(PollSolarObj pollSolarObj);

    void onShareButtonClicked(PollSolarObj pollSolarObj, TextView shareView);

    void onPollUnLikeClicked(PollSolarObj pollSolarObj);

    void onPollLikeClicked(PollSolarObj pollSolarObj);

    void onPostDetailsAuthorFollow(UserPostSolrObj userPostSolrObj);
}
