package appliedlife.pvtltd.SHEROES.presenters;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.ImpressionCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.datamanger.AppDatabase;
import appliedlife.pvtltd.SHEROES.datamanger.ImpressionCollection;
import appliedlife.pvtltd.SHEROES.datamanger.ImpressionData;
import appliedlife.pvtltd.SHEROES.datamanger.ImpressionDataSample;
import appliedlife.pvtltd.SHEROES.datamanger.UserEvents;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserEventsContainer;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    //Store the impression in Database

    /**
     * Add the item to Database
     *
     * @param context        context
     * @param impressionData list of impression events
     */
    private void addToDatabase(Context context, final List<ImpressionDataSample> impressionData) {
        final AppDatabase database = AppDatabase.getAppDatabase(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImpressionCollection impressionCollection = new ImpressionCollection();
                UserEvents userEvents = new UserEvents();
                userEvents.setUserEvent(impressionData);
                impressionCollection.setUserEvents(userEvents);
                database.impressionDataDao().insert(impressionCollection);
            }
        }).start();
    }

    //Filter the list and get only those item which have more than 250ms time spend on impression
    public void storeBatchInDb(final Context context, final List<ImpressionDataSample> impressionData) {
        Observable.just(impressionData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<ImpressionDataSample>, Observable<ImpressionDataSample>>() {
                    @Override
                    public Observable<ImpressionDataSample> apply(List<ImpressionDataSample> ints) {
                        return Observable.fromIterable(ints);
                    }
                }).filter(new Predicate<ImpressionDataSample>() {
            @Override
            public boolean test(ImpressionDataSample impressionDataSample) throws Exception {
                return impressionDataSample.getEngagementTime() > 0.25; //Todo - remove this time
            }
        }).toList().subscribe(new DisposableSingleObserver<List<ImpressionDataSample>>() {
            @Override
            public void onSuccess(List<ImpressionDataSample> impressionDataSampleList) {
                addToDatabase(context, impressionDataSampleList);
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
            }
        });
    }



    //Hit the network after frequency expired
    public void sendImpressionData(final Context context, final UserEventsContainer userEventsContainer) {

        Log.i("Impression hit", "Called");
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesApiEndPoints.updateImpressionData(userEventsContainer)
                .retry(1) //Retry no of times
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
                            Log.i("Impression", "responseee");
                            //clearDatabase(context, userEventsContainer.getUserEvent());
                            // getMvpView().onImpressionResponse(baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS));
                        }
                    }
                });
    }



    //clear db items
    /**
     * Add the item to Database
     *
     * @param context        context
     * @param impressionData list of impression events
     */
    public void clearDatabase(Context context, final List<ImpressionData> impressionData) {
        final AppDatabase database = AppDatabase.getAppDatabase(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
               // database.impressionDataDao().deleteImpression(impressionData);
            }
        }).start();
    }

}
