package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IEditProfileView;
import io.reactivex.observers.DisposableObserver;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;

/**
 * Created by ravi on 01/01/18.
 * Presenter for Edit profile Activity
 */

public class EditProfilePresenterImpl extends BasePresenter<IEditProfileView> {

    private final String TAG = LogUtils.makeLogTag(EditProfilePresenterImpl.class);
    private ProfileModel profileModel;

    private SheroesApplication mSheroesApplication;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;

    @Inject
    public EditProfilePresenterImpl(ProfileModel profileModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.profileModel = profileModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
    }

    //for showing all data of profile listing
    public void getALLUserDetails() {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        profileModel.getAllUserDetailsromModel()
                .compose(this.<UserProfileResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<UserProfileResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(UserProfileResponse userProfileResponse) {
                getMvpView().stopProgressBar();
                if (null != userProfileResponse) {
                    getMvpView().getUserData(userProfileResponse);
                }
            }
        });

    }

    // Update User Basic Details
    public void getPersonalBasicDetails(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }

        getMvpView().startProgressBar();
        profileModel.getPersonalBasicDetails(personalBasicDetailsRequest)
                .compose(this.<BoardingDataResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BoardingDataResponse>() {
            @Override
            public void onComplete() {


            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                if (null != boardingDataResponse) {
                    if(boardingDataResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED))  {
                        if(boardingDataResponse.getFieldErrorMessageMap().containsKey(AppConstants.INAVLID_DATA)) {
                            String errorMessage = boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA);
                            getMvpView().errorMessage(errorMessage);
                        }
                    } else if(boardingDataResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                        getMvpView().getPersonalBasicDetailsResponse(boardingDataResponse);
                    }
                }
            }
        });

    }

    //Update - About me/User Image
    public void getUserSummaryDetails(UserSummaryRequest userSummaryRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        profileModel.getPersonalUserSummaryDetails(userSummaryRequest)
                .compose(this.<BoardingDataResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BoardingDataResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                //getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                if (null != boardingDataResponse) {
                    getMvpView().getUserSummaryResponse(boardingDataResponse);
                }
            }
        });

    }
}
