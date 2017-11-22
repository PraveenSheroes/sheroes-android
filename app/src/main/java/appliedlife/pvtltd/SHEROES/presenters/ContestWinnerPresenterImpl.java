package appliedlife.pvtltd.SHEROES.presenters;


import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityPostView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestWinnerView;

/**
 * Created by ujjwal on 04/05/17.
 */

public class ContestWinnerPresenterImpl extends BasePresenter<IContestWinnerView> {
    //region Constructor
    @Inject
    public ContestWinnerPresenterImpl() {

    }
    //endregion

    //region presenter methods

    public void fetchPrizes(String contest_id) {
    /*    ICareAPIService careApiService = CareServiceHelper.getCareServiceInstance();
        careApiService.getPrizes(contest_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<Prize>>bindToLifecycle())
                .subscribe(new RxUtil.OnNextSubscriber<List<Prize>>() {
                    @Override
                    public void onNext(List<Prize> prizeList) {
                        mContestWinnerView.showPrizes(prizeList);
                    }

                });*/
    }
    //endregion
}
