package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserDetailsRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;

/**
 * Created by priyanka on 19/03/17.
 */

public class ProfilePersenter extends BasePresenter<ProfileView> {


    private final String TAG = LogUtils.makeLogTag(ProfilePersenter.class);
    ProfileModel mProfileModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;





    @Inject
    public ProfilePersenter(ProfileModel mProfileModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.mProfileModel = mProfileModel;
        this.sheroesApplication=sheroesApplication;
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


    public void getUserDetailsAuthTokeInPresenter(GetUserDetailsRequest getUserDetailsRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        Subscription subscription = mProfileModel.getEducationAuthTokenFromModel(getUserDetailsRequest).subscribe(new Subscriber<EducationResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(EducationResponse educationResponse) {

                getMvpView().stopProgressBar();
                getMvpView().getEducationResponse(educationResponse);

            }
        });
        registerSubscription(subscription);
    }

    public void onStop() {
        detachView();
    }





}
