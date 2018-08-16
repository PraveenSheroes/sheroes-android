package appliedlife.pvtltd.SHEROES.basecomponents;

import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;

/**
 * Created by ujjwal on 07/12/17.
 */

public interface PostDetailCallBack {
    void loadMoreComments();

    void onPostImageClicked(UserPostSolrObj userPostObj);

    void onPostMenuClicked(UserPostSolrObj userPostObj, TextView view);

    void onSpamMenuClicked(UserPostSolrObj userPostObj, TextView spamPostView);

    void onShareButtonClicked(UserPostSolrObj userPostObj, TextView shareView);

    void onPostLikeClicked(UserPostSolrObj userPostObj);

    void onPostUnLikeClicked(UserPostSolrObj userPostObj);

    void onChampionProfileClicked(UserPostSolrObj userPostObj, int requestCodeForMentorProfileDetail);

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
}
