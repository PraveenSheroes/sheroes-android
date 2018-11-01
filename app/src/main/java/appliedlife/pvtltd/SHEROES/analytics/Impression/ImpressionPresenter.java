package appliedlife.pvtltd.SHEROES.analytics.Impression;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.datamanager.AppDatabase;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private static volatile boolean mIsNetworkCallRunning = false;
    private static int retryCounter;
    private static final int MAX_RETRY_COUNTER = 1;
    private final AppDatabase mDatabase = AppDatabase.getAppDatabase(SheroesApplication.mContext);

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
                    List<Long> rowsAffected = mDatabase.impressionDataDao().insert(data);
                    if (rowsAffected.size() > 0) {
                        emitter.onSuccess(rowsAffected);
                    }
                } catch (Throwable t) {
                    emitter.onError(t);
                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<Long>>() {
                    @Override
                    public void onSuccess(List<Long> rowsAffected) {
                        impressionObserver(batchSize, forceNetworkCall);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                    }
                });
    }

    //Get impression data from database
    void impressionObserver(final int batchSize, final boolean forceNetworkCall) {

        mDatabase.impressionDataDao().getAllImpressions()
                .subscribeOn(Schedulers.io())
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
        if (!mIsNetworkCallRunning) {
            int totalCount = impressions.size();
            if ((totalCount >= batchSize && !forceNetworkCall) || (forceNetworkCall)) {
                mIsNetworkCallRunning = true;
                sendImpressionData(getRequestModel(impressions), impressions);
            }
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
     * @param impressions data that need to clear from db on success
     */
    private void sendImpressionData(final UserEvents userEvents, final List<Impression> impressions) {

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            mIsNetworkCallRunning = false;
            return;
        }

        mSheroesApiEndPoints.updateImpressionData(userEvents)
                .subscribeOn(Schedulers.io())
                .compose(this.<ImpressionResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ImpressionResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        retryCounter ++;
                        if(retryCounter <= MAX_RETRY_COUNTER){
                            sendImpressionData(userEvents, impressions);
                        }

                        Crashlytics.getInstance().core.logException(e);
                        mIsNetworkCallRunning = false;
                    }

                    @Override
                    public void onNext(ImpressionResponse impressionResponse) {
                        if (null != impressionResponse && impressionResponse.isSuccessFul()) {
                            clearSentImpressions(impressions);
                        } else {
                            mIsNetworkCallRunning = false;
                        }
                    }
                });
    }

    /**
     * clear  successful impression
     *
     * @param clearImpressionItem list of impression events
     */
    private void clearSentImpressions(final List<Impression> clearImpressionItem) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> subscriber) {
                try {
                    mDatabase.impressionDataDao().deleteImpression(clearImpressionItem);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                        mIsNetworkCallRunning = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        mIsNetworkCallRunning = false;
                    }

                    @Override
                    public void onNext(String id) {
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