package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ISearchView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MEMBER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

public class SearchPresenter extends BasePresenter<ISearchView> {
    //region member variables
    SheroesApplication mSheroesApplication;
    private SheroesAppServiceApi mSheroesAppServiceApi;
    //endregion member variables

    //region injected variables
    @Inject
    AppUtils mAppUtils;
    //endregion injected variables

    //region constructor
    @Inject
    public SearchPresenter(AppUtils appUtils, SheroesAppServiceApi sheroesAppServiceApi, SheroesApplication sheroesApplication) {
        mAppUtils = appUtils;
        mSheroesAppServiceApi = sheroesAppServiceApi;
        mSheroesApplication = sheroesApplication;
    }
    //endregion constructor

    //region public methods
    public void getTrendingHashtags() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MEMBER);
            return;
        }
        mSheroesAppServiceApi.fetchTrendingHashtags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<List<String>>bindToLifecycle())
                .subscribe(new DisposableObserver<List<String>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                    }

                    @Override
                    public void onNext(List<String> hashTagsList) {
                        getMvpView().onHashTagsResponse(hashTagsList);
                    }
                });
    }
    //endregion public methods
}
