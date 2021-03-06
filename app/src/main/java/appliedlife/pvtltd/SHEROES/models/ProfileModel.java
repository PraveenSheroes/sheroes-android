package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.DeactivateUserRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

    public Observable<FollowedUsersResponse> getFollowerFollowing(FollowersFollowingRequest followersFollowingRequest) {
        return sheroesAppServiceApi.getFollowerOrFollowing(followersFollowingRequest)

                .map(new Function<FollowedUsersResponse, FollowedUsersResponse>() {
                    @Override
                    public FollowedUsersResponse apply(FollowedUsersResponse feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
     /*for  user personal basic details details */


    public Observable<BoardingDataResponse> getPersonalBasicDetails(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
        LogUtils.error("personal" +
                "-basic_detailsl req: ",gson.toJson(personalBasicDetailsRequest));

        return sheroesAppServiceApi.getPersonalBasicDetailsAuthToken(personalBasicDetailsRequest)

                .map(new Function<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse apply(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse;

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*for  user personal Summary details details */

    public Observable<BoardingDataResponse> getPersonalUserSummaryDetails(UserSummaryRequest userSummaryRequest) {
        return sheroesAppServiceApi.getPersonalUserSummaryDetailsAuthToken(userSummaryRequest)
                .map(new Function<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse apply(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse ;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //for profile listing
    public Observable<ProfileCommunitiesResponsePojo> getUserCommunity(ProfileUsersCommunityRequest profileUsersCommunityRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(profileUsersCommunityRequest));
        return sheroesAppServiceApi.getUsersCommunity(profileUsersCommunityRequest)
                .map(new Function<ProfileCommunitiesResponsePojo, ProfileCommunitiesResponsePojo>() {
                    @Override
                    public ProfileCommunitiesResponsePojo apply(ProfileCommunitiesResponsePojo profileCommunitiesResponsePojo) {
                        return profileCommunitiesResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ProfileCommunitiesResponsePojo> getPublicProfileUserCommunity(ProfileUsersCommunityRequest profileUsersCommunityRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(profileUsersCommunityRequest));
        return sheroesAppServiceApi.getPublicProfileUsersCommunity(profileUsersCommunityRequest)
                .map(new Function<ProfileCommunitiesResponsePojo, ProfileCommunitiesResponsePojo>() {
                    @Override
                    public ProfileCommunitiesResponsePojo apply(ProfileCommunitiesResponsePojo profileCommunitiesResponsePojo) {
                        return profileCommunitiesResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserProfileResponse> getAllUserDetailsromModel() {

        return sheroesAppServiceApi.getUserDetails()
                .map(new Function<UserProfileResponse, UserProfileResponse>() {

                    @Override
                    public UserProfileResponse apply(UserProfileResponse userProfileResponse) {
                        return userProfileResponse;

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    //Spam request
    public Observable<SpamResponse> reportSpam(SpamPostRequest spamPostRequest) {
        return sheroesAppServiceApi.reportProfile(spamPostRequest).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //deactivation Request
    public Observable<BaseResponse> deactivateUser(DeactivateUserRequest deactivateUserRequest) {
        return sheroesAppServiceApi.deactivateUser(deactivateUserRequest).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
