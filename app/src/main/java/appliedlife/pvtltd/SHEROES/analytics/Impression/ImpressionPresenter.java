package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.datamanager.AppDatabase;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

/**
 * Presenter class for Impressions
 */
public class ImpressionPresenter extends BasePresenter<ImpressionCallback> {

    //region private variables
    private SheroesAppServiceApi mSheroesApiEndPoints;
    private SheroesApplication mSheroesApplication;
    //endregion

    //region constructor
    @Inject
    ImpressionPresenter(SheroesApplication sheroesApplication, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mSheroesApiEndPoints = sheroesAppServiceApi;
        this.mSheroesApplication = sheroesApplication;
    }
    //endregion

    //region private method

    /**
     * Add the impressions to Database
     *
     * @param impressionData list of impression
     */
    private void insertImpressionsInDb(final int batchSize, final ImpressionData impressionData, final boolean forceNetworkCall) {
        Single.create(new SingleOnSubscribe<Long>() {
            @Override
            public void subscribe(SingleEmitter<Long> emitter) {
                try {
                    Impression impression = new Impression();
                    impression.setGtid(impressionData.getGtid());
                    impression.setImpressionData(impressionData);
                    final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
                    long id = database.impressionDataDao().insert(impression);
                    if (id > -1) {
                        List<Impression> impressions = database.impressionDataDao().getAll();
                        int totalCount = impressions.size();
                        if (totalCount >= batchSize && !forceNetworkCall) {
                            Log.i("Db value", "250 ms >" + totalCount);
                            hitNetworkCall();
                        } else {
                            emitter.onSuccess(id);
                        }
                    }
                } catch (Throwable t) {
                    emitter.onError(t);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        Log.i("Inserted", "Successfully");
                        if (forceNetworkCall) {
                            hitNetworkCall();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Inserted", "failed");
                        Crashlytics.getInstance().core.logException(e);
                    }
                });
    }

    /**
     * Send the impression to server
     * @param userEvents request format data
     * @param fetchedRowIndex data that need to clear from db on success
     */
    private void sendImpressionData(final UserEvents userEvents, final List<Impression> fetchedRowIndex) {

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesApiEndPoints.updateImpressionData(userEvents)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                         retryFailedImpression(userEvents, fetchedRowIndex);
                    }
                })
                .subscribe(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        getMvpView().stopProgressBar();
                        if (null != baseResponse) {
                            clearDatabase(fetchedRowIndex);
                        }
                    }
                });
    }

    //Retry this on error
    private void retryFailedImpression(final UserEvents userEvents, final List<Impression> impressions) {

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesApiEndPoints.updateImpressionData(userEvents)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        getMvpView().stopProgressBar();
                        if (null != baseResponse) {
                            clearDatabase(impressions);
                        }
                    }
                });
    }

    /**
     * clear db items
     *
     * @param clearImpressionItem list of impression events
     */
    private void clearDatabase(final List<Impression> clearImpressionItem) {
        final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.impressionDataDao().deleteImpression(clearImpressionItem);
            }
        }).start();
    }
    //endregion

    //region public method
    //Filter the list and get only those item which have more than 250ms time spend on impression
    public void storeBatchInDb(final float minEngagementTime, final int batchSize, final List<ImpressionData> impressionData, final boolean forceNetworkCall) {
        Observable.just(impressionData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<ImpressionData>, Observable<ImpressionData>>() {
                    @Override
                    public Observable<ImpressionData> apply(List<ImpressionData> ints) {
                        return Observable.fromIterable(ints);
                    }
                }).filter(new Predicate<ImpressionData>() {
            @Override
            public boolean test(ImpressionData impressionData) throws Exception { //Filter impression which satisfy min engagement time
                return impressionData.getEngagementTime() >= minEngagementTime;
            }
        }).subscribe(new Observer<ImpressionData>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ImpressionData impressionData) {
                System.out.println("onNext: " + impressionData.getPostId() + "::" + impressionData.getEngagementTime());
                insertImpressionsInDb(batchSize, impressionData, forceNetworkCall);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    //Handle the Request for Network call
    public void hitNetworkCall() {

        synchronized (this) {
            final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Impression> impressions = database.impressionDataDao().getAll();
                    if (impressions == null || impressions.size() <= 0) return;

                    UserEvents userEvents = new UserEvents();
                    List<ImpressionData> impressionData = new ArrayList<>();
                    for (int i = 0; i < impressions.size(); i++) {
                        impressionData.add(impressions.get(i).getImpressionData());
                    }
                    userEvents.setUserEvent(impressionData);
                    sendImpressionData(userEvents, impressions);
                }
            }).start();
        }
    }
    //endregion
}