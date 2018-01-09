package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileFollowedMentor;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserFollowerOrFollowingRequest;
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

    public Observable<BaseResponse> getFollowerOrFollowing(UserFollowerOrFollowingRequest userFollowerOrFollowingRequest) {
          return sheroesAppServiceApi.getUsersFollowerOrFollowing(userFollowerOrFollowingRequest)

                .map(new Func1<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse call(BaseResponse followerOrFollowingCount) {
                        return followerOrFollowingCount;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserFollowedMentorsResponse> getFollowedMentor(ProfileFollowedMentor profileFollowedMentor) {

        //LogUtils.error("user_get_preference_request req: ",gson.toJson(profileAddEditEducationRequest));
        return sheroesAppServiceApi.getFollowedMentorFromApiTest(profileFollowedMentor)

                .map(new Func1<UserFollowedMentorsResponse, UserFollowedMentorsResponse>() {
                    @Override
                    public UserFollowedMentorsResponse call(UserFollowedMentorsResponse feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FeedResponsePojo> getFeedFromModelForTestProfile(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
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

                .map(new Func1<BoardingDataResponse, BoardingDataResponse>() {

                    @Override
                    public BoardingDataResponse call(BoardingDataResponse boardingDataResponse) {

                        return boardingDataResponse;

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*for  user personal Summary details details */

    public Observable<BoardingDataResponse> getPersonalUserSummaryDetails(UserSummaryRequest userSummaryRequest) {
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

    //for profile listing
    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ProfileCommunitiesResponsePojo> getUserCommunity(ProfileUsersCommunityRequest profileUsersCommunityRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(profileUsersCommunityRequest));
        return sheroesAppServiceApi.getUsersCommunity(profileUsersCommunityRequest)
                .map(new Func1<ProfileCommunitiesResponsePojo, ProfileCommunitiesResponsePojo>() {
                    @Override
                    public ProfileCommunitiesResponsePojo call(ProfileCommunitiesResponsePojo profileCommunitiesResponsePojo) {
                        return profileCommunitiesResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
