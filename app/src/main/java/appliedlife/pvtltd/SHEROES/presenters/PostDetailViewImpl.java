package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeListResponseNew;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestListView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;


/**
 * Created by ujjwal on 28/04/17.
 */

public class PostDetailViewImpl extends BasePresenter<IPostDetailView> {
    SheroesAppServiceApi sheroesAppServiceApi;
    private UserPostSolrObj mUserPostObj;
    private String mUserPostId;
    private List<BaseResponse> mBaseResponseList;
    private SheroesApplication mSheroesApplication;

    @Inject
    public PostDetailViewImpl(SheroesAppServiceApi sheroesAppServiceApi, SheroesApplication sheroesApplication) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.mBaseResponseList = new ArrayList<>();
        this.mSheroesApplication = sheroesApplication;
    }

    //region Presenter methods

    public void setUserPost(UserPostSolrObj userPostSolrObj, String userPostId) {
        this.mUserPostObj = userPostSolrObj;
        this.mUserPostId = userPostId;
    }

    public void fetchUserPost() {
        if(mUserPostObj == null){
            fetchUserPostFromServer();
        }else {
            mBaseResponseList.add(mUserPostObj);
            getMvpView().showUserPost(mBaseResponseList);
        }
        getAllCommentFromPresenter(getCommentRequestBuilder(mUserPostObj.getEntityOrParticipantId(), 1));
    }

    private void fetchUserPostFromServer() {

    }

    public void getAllCommentFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getAllCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_SERVER_PROBLEM),ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                mBaseResponseList.addAll(commentResponsePojo.getCommentList());
                getMvpView().showUserPost(mBaseResponseList);
            }
        });
        registerSubscription(subscription);
    }

    private Observable<CommentReactionResponsePojo> getAllCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo) {
        return sheroesAppServiceApi.getCommentFromApi(commentReactionRequestPojo)
                .map(new Func1<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                    @Override
                    public CommentReactionResponsePojo call(CommentReactionResponsePojo commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //endregion
}
