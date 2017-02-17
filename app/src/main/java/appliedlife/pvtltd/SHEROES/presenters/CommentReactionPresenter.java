package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommentReactionModel;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.preferences.Token;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllCommentReactionView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentReactionPresenter extends BasePresenter<AllCommentReactionView> {
    private final String TAG = LogUtils.makeLogTag(SearchModulePresenter.class);
    CommentReactionModel mCommentReactionModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<Token> mUserPreference;
    @Inject
    public CommentReactionPresenter(CommentReactionModel commentReactionModel, SheroesApplication mSheroesApplication, Preference<Token> mUserPreference) {
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


    public void getAllCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo,boolean isReaction) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
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
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getAllCommentsAndReactions(commentResponsePojo);
            }
        });
        registerSubscription(subscription);
    }

    public void addCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
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
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().addCommentSuccess(commentResponsePojo.getStatus());
            }
        });
        registerSubscription(subscription);
    }
    public void editCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
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
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().addCommentSuccess(commentResponsePojo.getStatus());
            }
        });
        registerSubscription(subscription);
    }

    public void onStop() {
        detachView();
    }
}