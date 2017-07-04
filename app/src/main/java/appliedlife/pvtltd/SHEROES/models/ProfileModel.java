package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileAddEditEducationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFLexibilityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by priyanka on 19/03/17.
 */

public class ProfileModel {
    private final String TAG = LogUtils.makeLogTag(ProfileModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public ProfileModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
      }


    /*for  user profile education details */

    public Observable<BoardingDataResponse> getEducationAuthTokenFromModel(ProfileAddEditEducationRequest profileAddEditEducationRequest) {


        LogUtils.error("user_get_preference_request req: ",gson.toJson(profileAddEditEducationRequest));


        return sheroesAppServiceApi.getEducationAuthToken(profileAddEditEducationRequest)

                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse  boardingDataResponse) {

                        return boardingDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




     /*for  user personal basic details details */


    public Observable<BoardingDataResponse> getPersonalBasicDetailsAuthTokenFromModel(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
        LogUtils.error("personal" +
                "-basic_detailsl req: ",gson.toJson(personalBasicDetailsRequest));

        return sheroesAppServiceApi.getPersonalBasicDetailsAuthToken(personalBasicDetailsRequest)

                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse;

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }





 /*for  user professional travel fexibility details details */

    public Observable<BoardingDataResponse> getProfessionalTravelfexibilityDetailsAuthTokenFromModel(ProfileTravelFLexibilityRequest profileTravelFLexibilityRequest) {
        LogUtils.error("user_get_travel_request req: ",gson.toJson(profileTravelFLexibilityRequest));

        return sheroesAppServiceApi.getProfessionalTravelDetailsAuthToken(profileTravelFLexibilityRequest)

                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




 /*for  user personal Summary details details */

    public Observable<BoardingDataResponse> getPersonalUserSummaryDetailsAuthTokenFromModel(UserSummaryRequest userSummaryRequest) {
        return sheroesAppServiceApi.getPersonalUserSummaryDetailsAuthToken(userSummaryRequest)
                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

     /*for  user professonal_basic_details details */

    public Observable<BoardingDataResponse> getProfessionalBasicDetailsAuthTokenFromModel(ProfessionalBasicDetailsRequest professionalBasicDetailsRequest) {
        LogUtils.error("user_get_preference_request req: ",gson.toJson(professionalBasicDetailsRequest));
        return sheroesAppServiceApi.getProfessionalDetailsAuthToken(professionalBasicDetailsRequest)



                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

/*for work location in professional section*/

    public Observable<BoardingDataResponse> getProfessionalWorkLocationAuthTokenFromModel(ProfilePreferredWorkLocationRequest profilePreferredWorkLocationRequest) {
        LogUtils.error("user_work_location_request req: ",gson.toJson(profilePreferredWorkLocationRequest));


        return sheroesAppServiceApi.getWorkLocationDetailsAuthToken(profilePreferredWorkLocationRequest)

                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse ;
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



    //*for  edit_profile_visiting_card_details*//*

    public Observable<ProfileEditVisitingCardResponse>getProfileEditVisitingCardDetailsAuthTokenFromModel() {

        return sheroesAppServiceApi.getEditVisitingCardDetailsAuthToken()

                .map(new Func1<ProfileEditVisitingCardResponse, ProfileEditVisitingCardResponse>() {

                    @Override
                    public ProfileEditVisitingCardResponse call(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {
                        LogUtils.info("Profile Response",gson.toJson(profileEditVisitingCardResponse));

                        return profileEditVisitingCardResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }



//for profile listing

    public Observable<UserProfileResponse> getAllUserDetailsromModel() {

        return sheroesAppServiceApi.getUserDetails()
                .map(new Func1<UserProfileResponse, UserProfileResponse>() {

                    @Override
                    public UserProfileResponse call(UserProfileResponse userProfileResponse) {
                        LogUtils.info("Profile Response",gson.toJson(userProfileResponse));

                        return userProfileResponse;

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    // For Good At
    public Observable<GetTagData> getSkillFromModel(GetAllDataRequest getAllDataRequest){
        LogUtils.info("Skill","TAG FRom*******************"+new Gson().toJson(getAllDataRequest));
        return sheroesAppServiceApi.getTagFromApi(getAllDataRequest)
                .map(new Func1<GetTagData, GetTagData>() {
                    @Override
                    public GetTagData call(GetTagData getAllData) {
                        return getAllData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BoardingDataResponse> getWorkExpFromModel(ExprienceEntity exprienceEntity) {
        LogUtils.info(TAG,"********** Work exp request"+gson.toJson(exprienceEntity));
        return sheroesAppServiceApi.getWorkExpAddEditResponse(exprienceEntity)
                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse  boardingDataResponse) {
                        return boardingDataResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
