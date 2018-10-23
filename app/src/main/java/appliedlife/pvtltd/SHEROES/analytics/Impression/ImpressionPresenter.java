package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.content.Context;
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
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    private SheroesAppServiceApi mSheroesApiEndPoints;
    private SheroesApplication mSheroesApplication;

    @Inject
    ImpressionPresenter(SheroesApplication sheroesApplication, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mSheroesApiEndPoints = sheroesAppServiceApi;
        this.mSheroesApplication = sheroesApplication;
    }

    //Filter the list and get only those item which have more than 250ms time spend on impression
    public void storeBatchInDb(final Context context, final float minEngagementTime, final List<ImpressionData> impressionData, final boolean forceNetworkCall) {
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
            public boolean test(ImpressionData impressionData) {
                return impressionData.getEngagementTime() >= minEngagementTime;
            }
        }).toList().subscribe(new DisposableSingleObserver<List<ImpressionData>>() {
            @Override
            public void onSuccess(List<ImpressionData> impressionDataList) {
                if (impressionDataList.size() > 0) {
                    insertImpressionsInDb(context, impressionDataList, forceNetworkCall); //convert into Rx
                } else {
                    Log.i("No item", "Have engagement more than 250 ms");
                }
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
            }
        });
    }

    /**
     * Add the impressions to Database
     *
     * @param context
     * @param impressionData list of impression
     */
    private void insertImpressionsInDb(final Context context, final List<ImpressionData> impressionData, final boolean forceNetworkCall) {
        Single.create(new SingleOnSubscribe<Long>() {
            @Override
            public void subscribe(SingleEmitter<Long> emitter) {
                try {
                    Impression impression = new Impression();
                    impression.setImpressionDataList(impressionData);
                    final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
                    long id = database.impressionDataDao().insert(impression);
                    if (id > -1) {
                        emitter.onSuccess(id);
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
                        if(forceNetworkCall) {
                            hitNetworkCall(context);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Inserted", "failed");
                    }
                });
    }

    //Hit the network after frequency expired
    private void sendImpressionData(final Context context, final UserEvents userEvents, final List<Impression> fetchedRowIndex) {

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        // Log.i("Impression hit", "Called");
        getMvpView().startProgressBar();
        mSheroesApiEndPoints.updateImpressionData(userEvents)
                //Retry no of times
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        retryNetworkCall(context, userEvents, fetchedRowIndex);
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
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        getMvpView().stopProgressBar();
                        if (null != baseResponse) {
                            //Log.i("Impression", "responseee");
                            clearDatabase(context, fetchedRowIndex);
                            // getMvpView().onImpressionResponse(baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS));
                        }
                    }
                });
    }


    //Retry this on error
    private void retryNetworkCall(final Context context, final UserEvents userEvents, final List<Impression> fetchedRowIndex) {

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        // Log.i("Impression hit", "Called");
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
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        getMvpView().stopProgressBar();
                        if (null != baseResponse) {
                            //Log.i("Impression", "responseee");
                            clearDatabase(context, fetchedRowIndex);
                            // getMvpView().onImpressionResponse(baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS));
                        }
                    }
                });
    }


    //clear db items

    /**
     * Add the item to Database
     *
     * @param context             context
     * @param clearImpressionItem list of impression events
     */
    public void clearDatabase(Context context, final List<Impression> clearImpressionItem) {
        final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Log.i("Removed the record", "Yes");
                database.impressionDataDao().deleteImpression(clearImpressionItem);
            }
        }).start();
    }

    //Handle the Request for Network call
    public void hitNetworkCall(final Context context) {

        synchronized (this) {
            final AppDatabase database = AppDatabase.getAppDatabase(SheroesApplication.mContext);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Impression> impressionData = database.impressionDataDao().getAll();
                    List<Impression> rowIndex = new ArrayList<>();
                    if (impressionData != null && impressionData.size() > 0) {
                        UserEvents userEvents = new UserEvents();
                        List<ImpressionData> data = new ArrayList<>();
                        for (int i = 0; i < impressionData.size(); i++) {
                            rowIndex.add(impressionData.get(i));
                            if (impressionData.get(i).getImpressionDataList().size() > 0) {
                                data.addAll(impressionData.get(i).getImpressionDataList());
                            }
                            if (data.size() >= 100) break;
                        }
                        userEvents.setUserEvent(data);
                        sendImpressionData(context, userEvents, rowIndex);
                    } else {
                        //  Log.i("###Impression", "No Record Found");
                    }
                }
            }).start();
        }
    }
}