package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.HelplineModel;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HelplineView;
import rx.Subscriber;
import rx.Subscription;

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
        Subscription subscription = helplineModel.postHelplineQuestion(helplinePostQuestionRequest).subscribe(new Subscriber<HelplinePostQuestionResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
                if(null!=e&& StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.HELPLINE_SCREEN).append(AppConstants.SPACE).append( e.getMessage());
                    SheroesApplication.mContext.trackScreenView(stringBuilder.toString());
                }
            }

            @Override
            public void onNext(HelplinePostQuestionResponse helplinePostQuestionResponse) {
                if(null!=helplinePostQuestionResponse) {
                    getMvpView().getPostQuestionSuccess(helplinePostQuestionResponse);
                }
            }
        });
        registerSubscription(subscription);

    }

    public void getHelplineChatDetails(HelplineGetChatThreadRequest helplineGetChatThreadRequest){
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = helplineModel.getHelplineChatDetails(helplineGetChatThreadRequest).subscribe(new Subscriber<HelplineGetChatThreadResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
                if(null!=e&& StringUtil.isNotNullOrEmptyString(e.getMessage())) {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(AppConstants.HELPLINE_SCREEN).append(AppConstants.SPACE).append( e.getMessage());
                    SheroesApplication.mContext.trackScreenView(stringBuilder.toString());
                }
            }

            @Override
            public void onNext(HelplineGetChatThreadResponse helplineGetChatThreadResponse) {
                if(null!=helplineGetChatThreadResponse) {
                    getMvpView().getHelpChatThreadSuccess(helplineGetChatThreadResponse);
                }
            }
        });
        registerSubscription(subscription);

    }




    public void onStop() {
        detachView();
    }
}


