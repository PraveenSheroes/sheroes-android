package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IUserLikedListView;
import io.reactivex.Observable;


import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;

/**
 * Created by ujjwal on 04/05/17.
 */

public class UserLikedListPresenterImpl extends BasePresenter<IUserLikedListView> {
    SheroesApplication mSheroesApplication;
    SheroesAppServiceApi sheroesAppServiceApi;
    //region Constructor
    @Inject
    public UserLikedListPresenterImpl(SheroesApplication sheroesApplication, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mSheroesApplication = sheroesApplication;
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }
    //endregion

    //region presenter methods

    public void getUserLikedList(CommentReactionRequestPojo commentReactionRequestPojo, boolean isReaction, int noReactionConstant) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMENT_REACTION);
            return;
        }
        getMvpView().startProgressBar();
        getAllCommentListFromModel(commentReactionRequestPojo,isReaction)
                .compose(this.<CommentReactionResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<CommentReactionResponsePojo>() {
            @Override
            public void onComplete() {
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
                List<Comment> comments = new ArrayList<>();
                comments = commentResponsePojo.getCommentList();
                getMvpView().showUserLikedList(comments);
            }
        });

    }

    public Observable<CommentReactionResponsePojo> getAllCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo, boolean isReaction){
        if(!isReaction) {
            return sheroesAppServiceApi.getCommentFromApi(commentReactionRequestPojo)
                    .map(new Function<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                        @Override
                        public CommentReactionResponsePojo apply(CommentReactionResponsePojo commentReactionResponsePojo) {
                            return commentReactionResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        else
        {
            return sheroesAppServiceApi.getAllReactionFromApi(commentReactionRequestPojo)
                    .map(new Function<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                        @Override
                        public CommentReactionResponsePojo apply(CommentReactionResponsePojo commentReactionResponsePojo) {
                            return commentReactionResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    //endregion
}
