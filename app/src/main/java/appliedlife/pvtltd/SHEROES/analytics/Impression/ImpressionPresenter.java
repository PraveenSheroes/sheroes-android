package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.support.v4.util.Pair;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.datamanager.AppDatabase;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
    private static boolean sIsNetworkCallInProgress = false;
    private static final int MAX_RETRY_COUNTER = 1;
    private final AppDatabase mDatabase = AppDatabase.getAppDatabase(SheroesApplication.mContext);

    //endregion

    //region constructor
    @Inject
    ImpressionPresenter(SheroesAppServiceApi sheroesAppServiceApi) {
        this.mSheroesApiEndPoints = sheroesAppServiceApi;
    }
    //endregion

    //region private method

    /**
     * Add the impressions to Database
     *
     * @param impressionData list of impression
     */
    private void sendImpressions(final int batchSize, final List<ImpressionData> impressionData, final boolean forceNetworkCall) {

        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(SingleEmitter<Integer> emitter) {
                try {
                    if (impressionData != null) {
                        List<Impression> data = new ArrayList<>();
                        for (ImpressionData impression : impressionData) {
                            Impression impressionToBeAdded = new Impression();
                            impressionToBeAdded.setGtid(impression.getGtid());
                            impressionToBeAdded.setImpressionData(impression);
                            data.add(impressionToBeAdded);
                        }
                        mDatabase.impressionDataDao().insert(data);
                    }
                    emitter.onSuccess(0);
                } catch (Throwable t) {
                    emitter.onError(t);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                flushDB(batchSize, forceNetworkCall);
            }

            @Override
            public void onError(Throwable e) {
                flushDB(batchSize, forceNetworkCall);
            }
        });

    }

    private void flushDB(final int batchSize, final boolean forceNetworkCall) {
        if (!sIsNetworkCallInProgress) {
            sIsNetworkCallInProgress = true;
            Single.just(0).map(new Function<Integer, List<Impression>>() {
                @Override
                public List<Impression> apply(Integer ignore) throws Exception {
                    List<Impression> rowsToBeSent = mDatabase.impressionDataDao().getAllImpressionsSync();
                    if (rowsToBeSent.size() > 0) {
                        return rowsToBeSent;
                    }
                    throw new Exception("No rows to send");
                }
            }).flatMap(new Function<List<Impression>, SingleSource<Pair<ImpressionResponse, List<Impression>>>>() {
                           @Override
                           public SingleSource<Pair<ImpressionResponse, List<Impression>>> apply(List<Impression> impressions) throws Exception {
                               int totalCount = impressions.size();
                               if (totalCount >= batchSize || forceNetworkCall) {

                                   return combineResponseWithImpressions(impressions);
                               }
                               throw new Exception("Updating impressions is not needed now");
                           }
                       }
            ).map(new Function<Pair<ImpressionResponse, List<Impression>>, Void>() {
                @Override
                public Void apply(Pair<ImpressionResponse, List<Impression>> pair) throws Exception {
                    if (pair == null || pair.first == null || pair.second == null) return null;

                    if (pair.first.isSuccessFul()) {
                        mDatabase.impressionDataDao().deleteImpression(pair.second);
                    } else {
                        throw new Exception("Network request is not successful");
                    }
                    return null;
                }
            })
                    .retry(MAX_RETRY_COUNTER).compose(this.<Void>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Void>() {
                        @Override
                        public void onSuccess(Void v) {
                            sIsNetworkCallInProgress = false;
                        }

                        @Override
                        public void onError(Throwable e) {
                            sIsNetworkCallInProgress = false;
                            Crashlytics.getInstance().core.logException(e);
                        }
                    });
        }
    }

    private Single<Pair<ImpressionResponse, List<Impression>>> combineResponseWithImpressions(List<Impression> impressions) {
        return Single.zip(mSheroesApiEndPoints.updateImpressionData(getRequestModel(impressions)), Single.just(impressions), new BiFunction<ImpressionResponse, List<Impression>, Pair<ImpressionResponse, List<Impression>>>() {
            @Override
            public Pair<ImpressionResponse, List<Impression>> apply(ImpressionResponse impressionResponse, List<Impression> impressions) throws Exception {
                return new Pair<>(impressionResponse, impressions);
            }
        });
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
                sendImpressions(batchSize, impressionData, forceNetworkCall); //insert in db
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
            }
        });
    }

    //endregion
}