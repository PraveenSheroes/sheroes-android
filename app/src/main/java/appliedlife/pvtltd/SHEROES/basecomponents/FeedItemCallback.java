package appliedlife.pvtltd.SHEROES.basecomponents;

import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;

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

    void onLikesCountClicked(UserPostSolrObj userPostObj);
}
