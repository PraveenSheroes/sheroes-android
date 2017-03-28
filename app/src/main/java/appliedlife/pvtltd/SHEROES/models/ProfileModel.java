package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by priyanka on 19/03/17.
 */

public class ProfileModel {

    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public ProfileModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
      }


    /*for  user profile education details */

    public Observable<EducationResponse> getEducationAuthTokenFromModel(PersonalBasicDetailsRequest personalBasicDetailsRequest) {


        LogUtils.error("user_get_preference_request req: ",gson.toJson(personalBasicDetailsRequest));


        return sheroesAppServiceApi.getEducationAuthToken(personalBasicDetailsRequest)

                .map(new Func1<EducationResponse, EducationResponse>() {

                    @Override
                    public EducationResponse call(EducationResponse educationResponse) {

                        return educationResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




     /*for  user personal basic details details */

    public Observable<PersonalBasicDetailsResponse> getPersonalBasicDetailsAuthTokenFromModel(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
        LogUtils.error("personal" +
                "-basic_detailsl req: ",gson.toJson(personalBasicDetailsRequest));

        return sheroesAppServiceApi.getPersonalBasicDetailsAuthToken(personalBasicDetailsRequest)

                .map(new Func1<PersonalBasicDetailsResponse, PersonalBasicDetailsResponse>() {

                    @Override
                    public PersonalBasicDetailsResponse call(PersonalBasicDetailsResponse personalBasicDetailsResponse) {

                        return personalBasicDetailsResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }





 /*for  user professional travel fexibility details details */

    public Observable<ProfileTravelFlexibilityResponse> getProfessionalTravelfexibilityDetailsAuthTokenFromModel(ProfileTravelFLexibilityRequest profileTravelFLexibilityRequest) {
        LogUtils.error("user_get_travel_request req: ",gson.toJson(profileTravelFLexibilityRequest));

        return sheroesAppServiceApi.getProfessionalTravelDetailsAuthToken(profileTravelFLexibilityRequest)

                .map(new Func1<ProfileTravelFlexibilityResponse, ProfileTravelFlexibilityResponse>() {

                    @Override
                    public ProfileTravelFlexibilityResponse call(ProfileTravelFlexibilityResponse profileTravelFlexibilityResponse) {

                        return profileTravelFlexibilityResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




 /*for  user personal Summary details details */

    public Observable<UserSummaryResponse> getPersonalUserSummaryDetailsAuthTokenFromModel(UserSummaryRequest userSummaryRequest) {
        LogUtils.error("user_get_preference_request req: ",gson.toJson(userSummaryRequest));
        return sheroesAppServiceApi.getPersonalUserSummaryDetailsAuthToken(userSummaryRequest)



                .map(new Func1<UserSummaryResponse, UserSummaryResponse>() {

                    @Override
                    public UserSummaryResponse call(UserSummaryResponse userSummaryResponse) {

                        return userSummaryResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

     /*for  user professonal_basic_details details */

    public Observable<ProfessionalBasicDetailsResponse> getProfessionalBasicDetailsAuthTokenFromModel(ProfessionalBasicDetailsRequest professionalBasicDetailsRequest) {
        LogUtils.error("user_get_preference_request req: ",gson.toJson(professionalBasicDetailsRequest));
        return sheroesAppServiceApi.getProfessionalDetailsAuthToken(professionalBasicDetailsRequest)



                .map(new Func1<ProfessionalBasicDetailsResponse, ProfessionalBasicDetailsResponse>() {

                    @Override
                    public ProfessionalBasicDetailsResponse call(ProfessionalBasicDetailsResponse professionalBasicDetailsResponse) {

                        return professionalBasicDetailsResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

/*for work location in professional section*/

    public Observable<ProfilePreferredWorkLocationResponse> getProfessionalWorkLocationAuthTokenFromModel(ProfilePreferredWorkLocationRequest profilePreferredWorkLocationRequest) {
        LogUtils.error("user_work_location_request req: ",gson.toJson(profilePreferredWorkLocationRequest));


        return sheroesAppServiceApi.getWorkLocationDetailsAuthToken(profilePreferredWorkLocationRequest)

                .map(new Func1<ProfilePreferredWorkLocationResponse, ProfilePreferredWorkLocationResponse>() {

                    @Override
                    public ProfilePreferredWorkLocationResponse call(ProfilePreferredWorkLocationResponse profilePreferredWorkLocationResponse) {

                        return profilePreferredWorkLocationResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }





    //*for profile_visiting_card*//*

    public Observable<ProfileEditVisitingCardResponse>getProfileVisitingCardDetailsAuthTokenFromModel(GetUserVisitingCardRequest getUserVisitingCardRequest) {


        LogUtils.error("visiting_card_request req: ",gson.toJson(getUserVisitingCardRequest));


        return sheroesAppServiceApi.getSaveVisitingCardDetailsAuthToken(getUserVisitingCardRequest)


                .map(new Func1<ProfileEditVisitingCardResponse, ProfileEditVisitingCardResponse>() {

                    @Override
                    public ProfileEditVisitingCardResponse call(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

                        return profileEditVisitingCardResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }










    //for edit visiting card

    //*for  edit_profile_visiting_card_details*//*

    public Observable<ProfileEditVisitingCardResponse>getProfileEditVisitingCardDetailsAuthTokenFromModel() {

        return sheroesAppServiceApi.getEditVisitingCardDetailsAuthToken()

                .map(new Func1<ProfileEditVisitingCardResponse, ProfileEditVisitingCardResponse>() {

                    @Override
                    public ProfileEditVisitingCardResponse call(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

                        return profileEditVisitingCardResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
