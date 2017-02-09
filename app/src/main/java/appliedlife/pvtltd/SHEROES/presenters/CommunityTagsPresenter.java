package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommunityListModel;
import appliedlife.pvtltd.SHEROES.models.CommunityTagsModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTagsListResponse;
import appliedlife.pvtltd.SHEROES.preferences.Token;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityTagsView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityTagsPresenter extends BasePresenter<CommunityTagsView> {
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    CommunityTagsModel communityListModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<Token> userPreference;
    @Inject
    public CommunityTagsPresenter(CommunityTagsModel communityListModel, SheroesApplication sheroesApplication, Preference<Token> userPreference) {
        this.communityListModel = communityListModel;
        this.sheroesApplication=sheroesApplication;
        this.userPreference=userPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }


    public void getCommunityList() {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityListModel.getCommunityList().subscribe(new Subscriber<CommunityTagsListResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CommunityTagsListResponse communityListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getCommunityTagsList(communityListResponse.getData());
            }

        });
        registerSubscription(subscription);
    }
    public void onStop() {
        detachView();
    }
}
