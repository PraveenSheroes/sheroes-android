package appliedlife.pvtltd.SHEROES.presenters;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.post.Post;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestView;

/**
 * Created by ujjwal on 11/05/17.
 */

public class ContestPresenterImpl extends BasePresenter<IContestView>{
    private IContestView mContestView;

    @Inject
    public ContestPresenterImpl() {

    }

    public void fetchContest(String contestId) {
   /*     HashMap<String, String> optionsMap = new HashMap<>();
        ICareAPIService careApiService = CareServiceHelper.getCareServiceInstance();
        careApiService.getPost(Contest.URL_PATH_STRING, contestId, optionsMap)
                .compose(this.<Post>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxUtil.OnNextSubscriber<Post>() {
                    @Override
                    public void onNext(Post post) {
                        Contest contest = (Contest) post;
                        getMvpView().populateContest(contest);
                    }

                });*/
    }
    //endregion
}
