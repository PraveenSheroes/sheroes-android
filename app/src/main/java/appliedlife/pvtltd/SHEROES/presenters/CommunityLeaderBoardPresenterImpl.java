package appliedlife.pvtltd.SHEROES.presenters;


import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityLeaderBoardUser;

/**
 * Created by Ravi on 25-06-18
 */

public class CommunityLeaderBoardPresenterImpl extends BasePresenter<ICommunityLeaderBoardUser> {

    SheroesAppServiceApi sheroesAppServiceApi;

    @Inject
    AppUtils mAppUtils;

    //region Constructor
    @Inject
    public CommunityLeaderBoardPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }
    //endregion

    //region presenter methods

   /* public void fetchLeaderBoardUsers(int communityId) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        final LeaderBoardRequest leaderBoardRequest = mAppUtils.leaderBoardRequestBuilder(communityId);
        getMvpView().startProgressBar();
        getLeaderBoardUsersModel(leaderBoardRequest)
                .compose(this.<LeaderBoardResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<LeaderBoardResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), null);

                    }

                    @Override
                    public void onNext(LeaderBoardResponse leaderBoardResponse) {
                        getMvpView().stopProgressBar();
                          if (leaderBoardResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS) && !CommonUtil.isEmpty(leaderBoardResponse.getLeaderBoardUserDetailList())) {
                              getMvpView().showUsersInLeaderBoard(leaderBoardResponse.getLeaderBoardUserDetailList());

                         }
                    }
                });

    }

    public Observable<LeaderBoardResponse> getLeaderBoardUsersModel(LeaderBoardRequest leaderBoardRequest) {
        return sheroesAppServiceApi.communityLeaderBoardUsersList(leaderBoardRequest)
                .map(new Function<LeaderBoardResponse, LeaderBoardResponse>() {
                    @Override
                    public LeaderBoardResponse apply(LeaderBoardResponse winnerResponse) {
                        return winnerResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/
    //endregion
}
