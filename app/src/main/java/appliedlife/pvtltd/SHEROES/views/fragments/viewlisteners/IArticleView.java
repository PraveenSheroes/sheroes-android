package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;

/**
 * Created by ujjwal on 24/10/17.
 */
public interface IArticleView extends BaseMvpView {

    void showArticle(ArticleSolrObj article, boolean imageLoaded);

    void invalidateLike(ArticleSolrObj mArticle);

    void invalidateBookmark(ArticleSolrObj mArticle);

    void showComments(List<Comment> comments, int commentsCount);

    void addAndNotifyComment(Comment comment);

    void removeAndNotifyComment(int position);

    void showMessage(int commentDeleted);

    void setAndNotify(int position, Comment comment);

    void setFeedDetail(FeedDetail feedDetail);

    void trackEvent(Event postLiked);

    String getStreamType();

    void onSpamPostOrCommentReported(SpamResponse spamResponse, Comment comment, int pos);
}
