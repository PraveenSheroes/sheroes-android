package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.HelplineModel;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HelplineView;
import io.reactivex.observers.DisposableObserver;


import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;

/**
 * Created by Deepak on 22-05-2017.
 */

public class HelplinePresenter extends BasePresenter<HelplineView>{

    private final String TAG = LogUtils.makeLogTag(HelplinePresenter.class);
    HelplineModel helplineModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    public HelplinePresenter(HelplineModel helplineModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.helplineModel = helplineModel;
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

    public void postQuestionHelpline(HelplinePostQuestionRequest helplinePostQuestionRequest){
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        helplineModel.postHelplineQuestion(helplinePostQuestionRequest)
                .compose(this.<HelplinePostQuestionResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<HelplinePostQuestionResponse>() {
            @Override
            public void onComplete() {

            }
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(HelplinePostQuestionResponse helplinePostQuestionResponse) {
                if(null!=helplinePostQuestionResponse) {
                    getMvpView().getPostQuestionSuccess(helplinePostQuestionResponse);
                }
            }
        });
    }

    public void getHelplineChatDetails(HelplineGetChatThreadRequest helplineGetChatThreadRequest){
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        helplineModel.getHelplineChatDetails(helplineGetChatThreadRequest)
                .compose(this.<HelplineGetChatThreadResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<HelplineGetChatThreadResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(HelplineGetChatThreadResponse helplineGetChatThreadResponse) {
                if(null!=helplineGetChatThreadResponse) {
                    getMvpView().getHelpChatThreadSuccess(helplineGetChatThreadResponse);
                }
            }
        });
    }

    public void onStop() {
        detachView();
    }
}


