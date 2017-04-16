package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.MasterDataModel;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileAddEditEducationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFLexibilityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

/**
 * Created by priyanka on 19/03/17.
 */

public class ProfilePersenter extends BasePresenter<ProfileView> {


    private final String TAG = LogUtils.makeLogTag(ProfilePersenter.class);
    ProfileModel mProfileModel;
    SheroesApplication sheroesApplication;
    @Inject
    Preference<LoginResponse> userPreference;
    MasterDataModel masterDataModel;
    Preference<MasterDataResponse> userPreferenceMasterData;
    @Inject
    public ProfilePersenter(MasterDataModel masterDataModel,ProfileModel mProfileModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference,Preference<MasterDataResponse> userPreferenceMasterData) {
        this.mProfileModel = mProfileModel;
        this.sheroesApplication=sheroesApplication;
        this.userPreference = userPreference;
        this.masterDataModel=masterDataModel;
        this.userPreferenceMasterData=userPreferenceMasterData;
    }

    @Override
    public void detachView() {

        super.detachView();
    }

    @Override
    public boolean isViewAttached() {

        return super.isViewAttached();

    }
    public void getMasterDataToPresenter() {
        super.getMasterDataToAllPresenter(sheroesApplication, masterDataModel, userPreferenceMasterData);
    }

//for profile education details

    public void getEducationDetailsAuthTokeInPresenter(ProfileAddEditEducationRequest profileAddEditEducationRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);

            return;
        }


        getMvpView().startProgressBar();

        Subscription subscription = mProfileModel.getEducationAuthTokenFromModel(profileAddEditEducationRequest).subscribe(new Subscriber<BoardingDataResponse>() {
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
            public void onNext(BoardingDataResponse boardingDataResponse) {

                getMvpView().stopProgressBar();
                getMvpView().getEducationResponse(boardingDataResponse);

            }
        });
        registerSubscription(subscription);
    }






// for profile_basic_details
public void getPersonalBasicDetailsAuthTokeInPresenter(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
    if (!NetworkUtil.isConnected(sheroesApplication)) {

        getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);

        return;
    }

    getMvpView().startProgressBar();
    Subscription subscription = mProfileModel.getPersonalBasicDetailsAuthTokenFromModel(personalBasicDetailsRequest).subscribe(new Subscriber<BoardingDataResponse>() {
        @Override
        public void onCompleted() {


        }

        @Override
        public void onError(Throwable e) {
            getMvpView().stopProgressBar();
            getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
        }

        @Override
        public void onNext(BoardingDataResponse boardingDataResponse) {
            getMvpView().stopProgressBar();
            getMvpView().getPersonalBasicDetailsResponse(boardingDataResponse);
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


        Subscription subscription = mProfileModel.getProfessionalTravelfexibilityDetailsAuthTokenFromModel(profileTravelFLexibilityRequest).subscribe(new Subscriber<BoardingDataResponse>() {
            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {



                getMvpView().getprofiletracelflexibilityResponse(boardingDataResponse);

            }
        });
        registerSubscription(subscription);
    }


    //for users SUMMARY_details
    public void getUserSummaryDetailsAuthTokeInPresenter(UserSummaryRequest userSummaryRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mProfileModel.getPersonalUserSummaryDetailsAuthTokenFromModel(userSummaryRequest).subscribe(new Subscriber<BoardingDataResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getUserSummaryResponse(boardingDataResponse);
            }
        });
        registerSubscription(subscription);
    }




    //for Professional_basic_details
    public void getProfessionalBasicDetailsAuthTokeInPresenter(ProfessionalBasicDetailsRequest professionalBasicDetailsRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);

            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mProfileModel.getProfessionalBasicDetailsAuthTokenFromModel(professionalBasicDetailsRequest).subscribe(new Subscriber<BoardingDataResponse>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getProfessionalBasicDetailsResponse(boardingDataResponse);
            }
        });
        registerSubscription(subscription);
    }








    //for Professional_work_location_details
    public void getUserWorkLocationAuthTokeInPresenter(ProfilePreferredWorkLocationRequest profilePreferredWorkLocationRequest  ) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }

        Subscription subscription = mProfileModel.getProfessionalWorkLocationAuthTokenFromModel(profilePreferredWorkLocationRequest).subscribe(new Subscriber<BoardingDataResponse>() {
            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {



                getMvpView().getProfessionalWorkLocationResponse(boardingDataResponse);


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




//for showing all data of profile listing


    public void getALLUserDetails() {
        if (!NetworkUtil.isConnected(sheroesApplication)) {

            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_AUTH_TOKEN);

            return;
        }


        Subscription subscription = mProfileModel.getAllUserDetailsromModel().subscribe(new Subscriber<UserProfileResponse>() {

            @Override
            public void onCompleted() {


            }
            @Override
            public void onError(Throwable e) {

                getMvpView().showError(e.getMessage(),ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(UserProfileResponse userProfileResponse) {




                getMvpView().getUserData(userProfileResponse);



            }
        });
        registerSubscription(subscription);
    }


    //For Good At
    public void getSkillFromPresenter(final GetAllDataRequest getAllDataRequest) {
        if (!NetworkUtil.isConnected(sheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_TAG);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mProfileModel.getSkillFromModel(getAllDataRequest).subscribe(new Subscriber<GetTagData>() {
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
                if (null != getAllData && StringUtil.isNotEmptyCollection(getAllData.getDocs()) && getAllData.getDocs().size() > AppConstants.ONE_CONSTANT) {
                    getMvpView().getProfileListSuccess(getAllData);
                } else {
                    getMvpView().getProfileListSuccess(getAllData);
                }
            }
        });
        registerSubscription(subscription);
    }







    public void onStop() {

        detachView();
    }





}
