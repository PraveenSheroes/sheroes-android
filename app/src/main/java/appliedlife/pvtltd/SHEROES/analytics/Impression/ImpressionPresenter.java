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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter class for Impressions
 *
 * @author ravi
 */
public class ImpressionPresenter extends BasePresenter<ImpressionCallback> {

    //region private variables
    private SheroesAppServiceApi mSheroesApiEndPoints;
    private SheroesApplication mSheroesApplication;

    private boolean mInNetworkCall = true;
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
    private void insertImpressionsInDb(final int batchSize, final List<ImpressionData> impressionData, final boolean forceNetworkCall) {
        Single.create(new SingleOnSubscribe<List<Long>>() {
            @Override
            public void subscribe(SingleEmitter<List<Long>> emitter) {
                try {
                    List<Impression> data = new ArrayList<>();
                    for (ImpressionData impression : impressionData) {
                        Impression impression1 = new Impression();
                        impression1.setGtid(impression.getGtid());
                        impression1.setImpressionData(impression);
                        data.add(impression1);
                    }
                    final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
                    List<Long> rowsAffected = database.impressionDataDao().insert(data);
                    if (rowsAffected.size() > 0) {
                        emitter.onSuccess(rowsAffected);
                    }
                } catch (Throwable t) {
                    emitter.onError(t);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<Long>>() {
                    @Override
                    public void onSuccess(List<Long> rowsAffected) {
                        Log.i("Inserted", "Successfully");
                        impressionObserver(batchSize, forceNetworkCall);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Inserted", "failed");
                        Crashlytics.getInstance().core.logException(e);
                    }
                });
    }

    //Get impression data from database
    void impressionObserver(final int batchSize, final boolean forceNetworkCall) {
        final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);

        database.impressionDataDao().getAllImpressions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<Impression>>bindToLifecycle())
                .subscribe(new DisposableSingleObserver<List<Impression>>() {
                    @Override
                    public void onSuccess(List<Impression> impressions) {
                        sendImpressions(impressions, batchSize, forceNetworkCall);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                    }
                });
    }

    //Send impression to backend after getting value from request model
    private void sendImpressions(List<Impression> impressions, int batchSize, boolean forceNetworkCall) {
        if (impressions == null || impressions.size() <= 0) return;

        int totalCount = impressions.size();
        if ((totalCount >= batchSize && !forceNetworkCall && mInNetworkCall) || (forceNetworkCall && mInNetworkCall)) {
            Log.i("Db value", "250 ms >" + totalCount);
            LogUtils.info("hit 1", "77");
            sendImpressionData(getRequestModel(impressions), impressions);
        }
    }

    //Request model to send impression to backend
    private UserEvents getRequestModel(List<Impression> impressions) {
        UserEvents userEvents = new UserEvents();
        List<ImpressionData> impressionData = new ArrayList<>();
        for (int i = 0; i < impressions.size(); i++) {
            impressionData.add(impressions.get(i).getImpressionData());
        }
        userEvents.setUserEvent(impressionData);
        return userEvents;
    }

    /**
     * Send the impression to server
     *
     * @param userEvents  request format data
     * @param Impressions data that need to clear from db on success
     */
    private void sendImpressionData(final UserEvents userEvents, final List<Impression> Impressions) {

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            mInNetworkCall = true;
            return;
        }
        LogUtils.info("hit", "network call happening");

        mSheroesApiEndPoints.updateImpressionData(userEvents)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        retryFailedImpression(userEvents, Impressions);
                    }
                })
                .subscribe(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        mInNetworkCall = true;
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (null != baseResponse) {
                            clearDatabase(Impressions);
                        }
                        mInNetworkCall = true;
                    }
                });
    }

    //Retry this on error
    private void retryFailedImpression(final UserEvents userEvents, final List<Impression> impressions) {

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            mInNetworkCall = true;
            return;
        }
        mSheroesApiEndPoints.updateImpressionData(userEvents)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        mInNetworkCall = true;
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (null != baseResponse) {
                            clearDatabase(impressions);
                        }
                        mInNetworkCall = true;
                    }
                });
    }

    /**
     * clear db items
     *
     * @param clearImpressionItem list of impression events
     */
    private void clearDatabase(final List<Impression> clearImpressionItem) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> subscriber) {
                final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
                database.impressionDataDao().deleteImpression(clearImpressionItem);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                    }

                    @Override
                    public void onNext(String id) {
                        Log.i("hit", "cleared");
                    }
                });
    }
    //endregion

    //region public method
    //Filter the list and get only those item which have more than 250ms time spend on impression
    void storeBatchInDb(final float minEngagementTime, final int batchSize, final List<ImpressionData> impressionData, final boolean forceNetworkCall) {
        Observable.fromIterable(impressionData).filter(new Predicate<ImpressionData>() { //iterate it
            @Override
            public boolean test(ImpressionData impressionData) { //Filter impression which satisfy min engagement time
                return impressionData.getEngagementTime() >= minEngagementTime;
            }
        }).toList().subscribe(new DisposableSingleObserver<List<ImpressionData>>() {
            @Override
            public void onSuccess(List<ImpressionData> impressionData) {
                insertImpressionsInDb(batchSize, impressionData, forceNetworkCall); //insert in db
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
            }
        });
    }

    //endregion
}