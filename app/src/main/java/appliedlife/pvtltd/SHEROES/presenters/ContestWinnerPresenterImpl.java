package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.WinnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.post.Winner;
import appliedlife.pvtltd.SHEROES.models.entities.post.WinnerResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestWinnerView;
import io.reactivex.Observable;


import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ujjwal on 04/05/17.
 */

public class ContestWinnerPresenterImpl extends BasePresenter<IContestWinnerView> {
    SheroesAppServiceApi sheroesAppServiceApi;

    @Inject
    AppUtils mAppUtils;
    //region Constructor
    @Inject
    public ContestWinnerPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }
    //endregion

    //region presenter methods

    public void fetchWinners(String contest_id) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        final WinnerRequest winnerRequest = mAppUtils.winnerRequestBuilder(contest_id);
        getMvpView().startProgressBar();
        getWinnersFromModel(winnerRequest).subscribe(new DisposableObserver<WinnerResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), null);

            }

            @Override
            public void onNext(WinnerResponse winnerResponse) {
                getMvpView().stopProgressBar();
                if (winnerResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS) && !CommonUtil.isEmpty(winnerResponse.winners)) {
                    List<Winner> winners = winnerResponse.winners;
                    getMvpView().showPrizes(winners);

                }
            }
        });

    }

    public Observable<WinnerResponse> getWinnersFromModel(WinnerRequest winnerRequest) {
        return sheroesAppServiceApi.getWinners(winnerRequest)
                .map(new Function<WinnerResponse, WinnerResponse>() {
                    @Override
                    public WinnerResponse apply(WinnerResponse winnerResponse) {
                        return winnerResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //endregion
}
