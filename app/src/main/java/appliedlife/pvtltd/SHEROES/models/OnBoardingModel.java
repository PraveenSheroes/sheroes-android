package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingInterestRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingJobAtRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingLookingForHowCanRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingTellUsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingWorkExpRequest;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */
@Singleton
public class OnBoardingModel {
    private final String TAG = LogUtils.makeLogTag(OnBoardingModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public OnBoardingModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<GetAllData> getOnBoardingFromModel(GetAllDataRequest getAllDataRequest){
        return sheroesAppServiceApi.getOnBoardingSearchFromApi(getAllDataRequest)
                .map(new Func1<GetAllData, GetAllData>() {
                    @Override
                    public GetAllData call(GetAllData getAllData) {
                        return getAllData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<GetInterestJobResponse> getInterestjobFromModel(GetAllDataRequest getAllDataRequest){
        return sheroesAppServiceApi.getInterestJobSearchFromApi(getAllDataRequest)
                .map(new Func1<GetInterestJobResponse, GetInterestJobResponse>() {
                    @Override
                    public GetInterestJobResponse call(GetInterestJobResponse getAllData) {
                        return getAllData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BoardingDataResponse> getCurrentStatusFromModel(BoardingTellUsRequest boardingTellUsRequest){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(boardingTellUsRequest));
        return sheroesAppServiceApi.getCurrentStatusFromApi(boardingTellUsRequest)
                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {
                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {
                        return boardingDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BoardingDataResponse> getLookingForHowCanFromModel(BoardingLookingForHowCanRequest boardingLookingForHowCanRequest){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(boardingLookingForHowCanRequest));
        return sheroesAppServiceApi.getLookingForHowCanFromApi(boardingLookingForHowCanRequest)
                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {
                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {
                        return boardingDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BoardingDataResponse> getJobAtFromModel(BoardingJobAtRequest boardingJobAtRequest){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(boardingJobAtRequest));
        return sheroesAppServiceApi.getJobAtFromApi(boardingJobAtRequest)
                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {
                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {
                        return boardingDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BoardingDataResponse> getWorkExpFromModel(BoardingWorkExpRequest boardingWorkExpRequest){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(boardingWorkExpRequest));
        return sheroesAppServiceApi.getWorkExpFromApi(boardingWorkExpRequest)
                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {
                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {
                        return boardingDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BoardingDataResponse> getInterestFromModel(BoardingInterestRequest boardingInterestRequest){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(boardingInterestRequest));
        return sheroesAppServiceApi.getInterestFromApi(boardingInterestRequest)
                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {
                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {
                        return boardingDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
