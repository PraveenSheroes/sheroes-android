package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommentReactionModel;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentResponse;
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


    public void getAllCommentListFromPresenter(CommentRequest commentRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mCommentReactionModel.getAllCommentListFromModel(commentRequest).subscribe(new Subscriber<CommentResponse>() {
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
            public void onNext(CommentResponse commentResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getAllComments(commentResponse.getListOfComment());
            }
        });
        registerSubscription(subscription);
    }
    public void getAllReactionListFromPresenter(CommentRequest commentRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mCommentReactionModel.getAllReactionListFromModel(commentRequest).subscribe(new Subscriber<CommentResponse>() {
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
            public void onNext(CommentResponse commentResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getAllReactions(commentResponse.getListOfReaction());
            }
        });
        registerSubscription(subscription);
    }
    public void onStop() {
        detachView();
    }
}