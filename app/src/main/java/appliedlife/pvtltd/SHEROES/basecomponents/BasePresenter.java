package appliedlife.pvtltd.SHEROES.basecomponents;

import android.support.v4.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MASTER_DATA;

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
    private CompositeDisposable compositeDisposables;
    private T mMvpView;
    private BehaviorSubject<ActivityEvent> lifecycleSubject = null;
    private BehaviorSubject<FragmentEvent> lifecycleFragmentSubject = null;


    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return lifecycleSubject != null ? RxLifecycleAndroid.<T>bindActivity(lifecycleSubject) : RxLifecycleAndroid.<T>bindFragment(lifecycleFragmentSubject);
    }

    public final <T> LifecycleTransformer<T> bindUntilDestroy() {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, ActivityEvent.DESTROY);
    }

    public final <T> LifecycleTransformer<T> bindUntilStop() {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, ActivityEvent.STOP);
    }

    public final <T> LifecycleTransformer<T> bindUntilPause() {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, ActivityEvent.PAUSE);
    }

    @Override
    public void onCreate() {
        sendLifecycleEvent(ActivityEvent.CREATE, FragmentEvent.CREATE);
    }

    private void sendLifecycleEvent(ActivityEvent activityEvent, FragmentEvent fragmentEvent) {
        if (lifecycleSubject != null) {
            lifecycleSubject.onNext(activityEvent);
        } else {
            if (lifecycleFragmentSubject != null) {
                lifecycleFragmentSubject.onNext(fragmentEvent);
            }
        }
    }

    @Override
    public void onDestroy() {
        sendLifecycleEvent(ActivityEvent.DESTROY, FragmentEvent.DESTROY);
    }

    @Override
    public void onStart() {
        sendLifecycleEvent(ActivityEvent.START, FragmentEvent.START);
    }

    @Override
    public void onStop() {
        sendLifecycleEvent(ActivityEvent.STOP, FragmentEvent.STOP);
    }

    @Override
    public void onPause() {
        sendLifecycleEvent(ActivityEvent.PAUSE, FragmentEvent.PAUSE);
    }

    @Override
    public void onResume() {
        sendLifecycleEvent(ActivityEvent.RESUME, FragmentEvent.RESUME);
    }

    @Override
    public void onAttach() {
        sendLifecycleEvent(null, FragmentEvent.ATTACH);
    }

    @Override
    public void onDetach() {
        sendLifecycleEvent(null, FragmentEvent.DETACH);
    }


    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
        if (mMvpView instanceof Fragment) {
            lifecycleFragmentSubject = BehaviorSubject.create();
            onAttach();
        } else {
            lifecycleSubject = BehaviorSubject.create();
        }
        onCreate();
    }

    @Override
    public void detachView() {
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

    public void getMasterDataToAllPresenter(SheroesApplication mSheroesApplication, MasterDataModel masterDataModel, final Preference<MasterDataResponse> mUserPreferenceMasterData) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MASTER_DATA);
            return;
        }
        masterDataModel.getMasterDataFromModel()
                .compose(this.<MasterDataResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MasterDataResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                    }

                    @Override
                    public void onNext(MasterDataResponse masterDataResponse) {
                        mUserPreferenceMasterData.delete();
                        mUserPreferenceMasterData.set(masterDataResponse);
                    }
                });
    }
}
