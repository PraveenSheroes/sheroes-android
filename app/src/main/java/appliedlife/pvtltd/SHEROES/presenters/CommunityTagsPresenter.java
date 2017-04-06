package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommunityTagsModel;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityTagsView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityTagsPresenter extends BasePresenter<CommunityTagsView> {
    private final String TAG = LogUtils.makeLogTag(CommunityTagsPresenter.class);
    CommunityTagsModel communityListModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;
    MasterDataModel masterDataModel;
    Preference<MasterDataResponse> userPreferenceMasterData;
    @Inject
    public CommunityTagsPresenter(MasterDataModel masterDataModel,CommunityTagsModel communityListModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> userPreferenceMasterData) {
        this.masterDataModel=masterDataModel;
        this.communityListModel = communityListModel;
        this.sheroesApplication=sheroesApplication;
        this.userPreference=userPreference;
        this.userPreferenceMasterData=userPreferenceMasterData;
    }
    public void getMasterDataToPresenter() {
        super.getMasterDataToAllPresenter(sheroesApplication, masterDataModel, userPreferenceMasterData);
    }


    public void getTagFromPresenter(final GetAllDataRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityListModel.getTagFromModel(getAllDataRequest).subscribe(new Subscriber<GetTagData>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_TAG);
            }

            @Override
            public void onNext(GetTagData getAllData) {
                getMvpView().stopProgressBar();
                if(null!=getAllData) {
                    getMvpView().getTagListSuccess(getAllData);
                }
            }
        });
        registerSubscription(subscription);
    }



    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }



    public void onStop() {
        detachView();
    }
}
