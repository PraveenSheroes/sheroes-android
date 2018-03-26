package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Article;
import appliedlife.pvtltd.SHEROES.models.entities.post.UserProfile;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;

/**
 * Created by avinash on 28/01/16.
 */
public class ArticleSubmissionPresenterImpl extends BasePresenter<IArticleSubmissionView> {
    SheroesAppServiceApi sheroesAppServiceApi;

    //region Constructor

    @Inject
    public ArticleSubmissionPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    public void prepareArticle(String articleTitle, String articleBody, String articleStatus) {

    }

    //endregion

    //region IArticlePresenter methods

}
