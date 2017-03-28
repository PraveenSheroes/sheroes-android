package appliedlife.pvtltd.SHEROES.presenters;

import android.util.Log;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFLexibilityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
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


    public void getUserDetailsAuthTokeInPresenter(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);

            return;
        }
        getMvpView().startProgressBar();

        Subscription subscription = mProfileModel.getEducationAuthTokenFromModel(personalBasicDetailsRequest).subscribe(new Subscriber<EducationResponse>() {
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


// for profile_basic_details
    public void getPersonalBasicDetailsAuthTokeInPresenter(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }


        Subscription subscription = mProfileModel.getPersonalBasicDetailsAuthTokenFromModel(personalBasicDetailsRequest).subscribe(new Subscriber<PersonalBasicDetailsResponse>() {
            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(PersonalBasicDetailsResponse getUserDetailsResponse) {



                getMvpView().getPersonalBasicDetailsResponse(getUserDetailsResponse);

            }
        });
        registerSubscription(subscription);
    }





//for usrs travel_details
    public void getUserTravelDetailsAuthTokeInPresenter(ProfileTravelFLexibilityRequest profileTravelFLexibilityRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }


        Subscription subscription = mProfileModel.getProfessionalTravelfexibilityDetailsAuthTokenFromModel(profileTravelFLexibilityRequest).subscribe(new Subscriber<ProfileTravelFlexibilityResponse>() {
            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(ProfileTravelFlexibilityResponse profileTravelFlexibilityResponse) {



                getMvpView().getprofiletracelflexibilityResponse(profileTravelFlexibilityResponse);

            }
        });
        registerSubscription(subscription);
    }


    //for usrs SUMMARY_details
    public void getUserSummaryDetailsAuthTokeInPresenter(UserSummaryRequest userSummaryRequest  ) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }


        Subscription subscription = mProfileModel.getPersonalUserSummaryDetailsAuthTokenFromModel(userSummaryRequest).subscribe(new Subscriber<UserSummaryResponse>() {
            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(UserSummaryResponse userSummaryResponse) {



                getMvpView().getUserSummaryResponse(userSummaryResponse);


            }
        });
        registerSubscription(subscription);
    }




    //for Professional_basic_details
    public void getProfessionalBasicDetailsAuthTokeInPresenter(ProfessionalBasicDetailsRequest professionalBasicDetailsRequest  ) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }

        Subscription subscription = mProfileModel.getProfessionalBasicDetailsAuthTokenFromModel(professionalBasicDetailsRequest).subscribe(new Subscriber<ProfessionalBasicDetailsResponse>() {
            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(ProfessionalBasicDetailsResponse professionalBasicDetailsResponse) {



                getMvpView().getProfessionalBasicDetailsResponse(professionalBasicDetailsResponse);


            }
        });
        registerSubscription(subscription);

    }








    //for Professional_basic_details
    public void getUserWorkLocationAuthTokeInPresenter(ProfilePreferredWorkLocationRequest profilePreferredWorkLocationRequest  ) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }

        Subscription subscription = mProfileModel.getProfessionalWorkLocationAuthTokenFromModel(profilePreferredWorkLocationRequest).subscribe(new Subscriber<ProfilePreferredWorkLocationResponse>() {
            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(ProfilePreferredWorkLocationResponse profilePreferredWorkLocationResponse) {



                getMvpView().getProfessionalWorkLocationResponse(profilePreferredWorkLocationResponse);


            }
        });
        registerSubscription(subscription);

    }









    //for Profile get_visiting_card


    public void getVisitingCardDetailsAuthTokeInPresenter(GetUserVisitingCardRequest getUserVisitingCardRequest  ) {




        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }

        Subscription subscription = mProfileModel.getProfileVisitingCardDetailsAuthTokenFromModel(getUserVisitingCardRequest).subscribe(new Subscriber<ProfileEditVisitingCardResponse>() {


            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {



                getMvpView().getProfileVisitingCardResponse(profileEditVisitingCardResponse);


            }
        });
        registerSubscription(subscription);

    }







    //for Profile Edit visiting_card
    public void getEditVisitingCardDetailsAuthTokeInPresenter(  ) {




        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }

        Subscription subscription = mProfileModel.getProfileEditVisitingCardDetailsAuthTokenFromModel().subscribe(new Subscriber<ProfileEditVisitingCardResponse>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {



                getMvpView().getProfileVisitingCardResponse(profileEditVisitingCardResponse);


            }
        });
        registerSubscription(subscription);

    }











    public void onStop() {

        detachView();
    }





}
