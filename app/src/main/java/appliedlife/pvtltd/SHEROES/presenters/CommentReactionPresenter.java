package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommentReactionModel;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllCommentReactionView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentReactionPresenter extends BasePresenter<AllCommentReactionView> {
    private final String TAG = LogUtils.makeLogTag(CommentReactionPresenter.class);
    CommentReactionModel mCommentReactionModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    public CommentReactionPresenter(CommentReactionModel commentReactionModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.mCommentReactionModel = commentReactionModel;
        this.mSheroesApplication = mSheroesApplication;
        this.mUserPreference = mUserPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }


    public void getAllCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo,boolean isReaction,final int addEditOperation) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_COMMENT_REACTION);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mCommentReactionModel.getAllCommentListFromModel(commentReactionRequestPojo,isReaction).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getAllCommentsAndReactions(commentResponsePojo,addEditOperation);
            }
        });
        registerSubscription(subscription);
    }

    public void addCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo,final int operationId) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_COMMENT_REACTION);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mCommentReactionModel.addCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().commentSuccess(commentResponsePojo.getStatus(),operationId);
            }
        });
        registerSubscription(subscription);
    }
    public void editCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo, final int editDeleteId) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_COMMENT_REACTION);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mCommentReactionModel.editCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_COMMENT_REACTION);
            }
            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().commentSuccess(commentResponsePojo.getStatus(),editDeleteId);
            }
        });
        registerSubscription(subscription);
    }

    public void onStop() {
        detachView();
    }
}