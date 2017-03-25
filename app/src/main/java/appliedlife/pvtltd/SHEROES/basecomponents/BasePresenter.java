package appliedlife.pvtltd.SHEROES.basecomponents;

import com.f2prateek.rx.preferences.Preference;

import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base presenter use in all SHEROES presenters.
 * all subclasses will use extend this class.
 */
public class BasePresenter<T extends BaseMvpView> implements SheroesPresenter<T> {
    private final String TAG = LogUtils.makeLogTag(BasePresenter.class);
    private CompositeSubscription subscriptions;
    private T mMvpView;
    protected void registerSubscription(Subscription subscription) {
        if (subscriptions == null) {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(subscription);
    }

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (subscriptions != null && !subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super(AppConstants.VIEW_NOT_ATTACHED_EXCEPTION);
        }
    }

    public void getMasterDataToAllPresenter(SheroesApplication mSheroesApplication, MasterDataModel masterDataModel, final  Preference<MasterDataResponse> mUserPreferenceMasterData) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, AppConstants.THREE_CONSTANT);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = masterDataModel.getMasterDataFromModel().subscribe(new Subscriber<MasterDataResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), AppConstants.THREE_CONSTANT);
            }

            @Override
            public void onNext(MasterDataResponse masterDataResponse) {
                getMvpView().stopProgressBar();
                mUserPreferenceMasterData.set(masterDataResponse);
                getMvpView().getMasterDataResponse(masterDataResponse.getData());
            }
        });
        registerSubscription(subscription);
    }
}
