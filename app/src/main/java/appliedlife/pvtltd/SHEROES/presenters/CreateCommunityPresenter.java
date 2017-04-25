package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommunityModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_CREATE_COMMUNITY;

/**
 * Created by Ajit Kumar on 03-03-2017.
 */

public class CreateCommunityPresenter extends BasePresenter<CommunityView> {
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    CommunityModel communityModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;

    @Inject
    public CreateCommunityPresenter(CommunityModel communityModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.communityModel = communityModel;
        this.sheroesApplication = sheroesApplication;
        this.userPreference = userPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }


    public void postCreateCommunityList(CreateCommunityRequest createCommunityRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityModel.postCreateCommunity(createCommunityRequest).subscribe(new Subscriber<CreateCommunityResponse>() {

            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(sheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse createCommunityResponse) {
                getMvpView().stopProgressBar();
                getMvpView().createCommunitySuccess(createCommunityResponse);
            }

        });
        registerSubscription(subscription);
    }

    public void postCommunityList(EditCommunityRequest editCommunityRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityModel.postEditCommunity(editCommunityRequest).subscribe(new Subscriber<CreateCommunityResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(sheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse createCommunityResponse) {
                getMvpView().stopProgressBar();
                getMvpView().createCommunitySuccess(createCommunityResponse);
            }

        });
        registerSubscription(subscription);
    }

    public void postCommunityList(CommunityPostCreateRequest communityPostCreateRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityModel.addPostCommunity(communityPostCreateRequest).subscribe(new Subscriber<CreateCommunityResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(sheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                getMvpView().stopProgressBar();
                getMvpView().createCommunitySuccess(communityPostCreateResponse);
            }

        });
        registerSubscription(subscription);
    }
    public void editCommunityPost(CommunityPostCreateRequest communityPostCreateRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityModel.editPostCommunity(communityPostCreateRequest).subscribe(new Subscriber<CreateCommunityResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(sheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                getMvpView().stopProgressBar();
                getMvpView().createCommunitySuccess(communityPostCreateResponse);
            }

        });
        registerSubscription(subscription);
    }

    public void getSelectCommunityFromPresenter(SelectCommunityRequest selectCommunityRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_CREATE_COMMUNITY);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityModel.getSelectedFromModel(selectCommunityRequest).subscribe(new Subscriber<SelectedCommunityResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(sheroesApplication.getString(R.string.ID_GENERIC_ERROR),ERROR_CREATE_COMMUNITY);
            }

            @Override
            public void onNext(SelectedCommunityResponse selectedcommunityresponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSelectedCommunityListSuccess(selectedcommunityresponse.getDocs());


            }
        });
        registerSubscription(subscription);
    }

    public void postCreateCommunityOwner(CreateCommunityOwnerRequest createCommunityOwnerRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = communityModel.postCreateCommunityOwner(createCommunityOwnerRequest).subscribe(new Subscriber<CreateCommunityOwnerResponse>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(sheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityOwnerResponse createCommunityOwnerResponse) {
                getMvpView().stopProgressBar();
                getMvpView().postCreateCommunityOwner(createCommunityOwnerResponse);
            }

        });
        registerSubscription(subscription);
    }

    public void onStop() {
        detachView();
    }
}
