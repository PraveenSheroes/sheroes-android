package appliedlife.pvtltd.SHEROES.presenters;


import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestListView;


/**
 * Created by ujjwal on 28/04/17.
 */

public class ContestListPresenterImpl extends BasePresenter<IContestListView> {
    private IContestListView mContestListView;

    @Inject
    public ContestListPresenterImpl() {

    }

    //region Presenter methods

    public void fetchContests() {
      /*  mContestListView.showProgressBar();
        CareServiceHelper.getCareServiceInstance().getContestsList()
                .compose(this.<List<Contest>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Contest>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mContestListView.hideProgressBar();
                        mContestListView.showError(R.string.snackbar_network_error);
                    }

                    @Override
                    public void onNext(List<Contest> contests) {
                        mContestListView.hideProgressBar();
                        mContestListView.showContests(contests);
                    }
                });*/
    }
    //endregion
}
