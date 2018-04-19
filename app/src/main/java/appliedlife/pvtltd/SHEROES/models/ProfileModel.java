package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopCountRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
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

    public Observable<ProfileTopSectionCountsResponse> getProfileTopSectionCount(ProfileTopCountRequest userFollowerOrFollowingRequest) {
          return sheroesAppServiceApi.getProfileTopSectionCounts(userFollowerOrFollowingRequest)

                .map(new Function<ProfileTopSectionCountsResponse, ProfileTopSectionCountsResponse>() {
                    @Override
                    public ProfileTopSectionCountsResponse apply(ProfileTopSectionCountsResponse followerOrFollowingCount) {
                        return followerOrFollowingCount;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserFollowedMentorsResponse> getFollowerFollowing(FollowersFollowingRequest followersFollowingRequest) {
        return sheroesAppServiceApi.getFollowerOrFollowing(followersFollowingRequest)

                .map(new Function<UserFollowedMentorsResponse, UserFollowedMentorsResponse>() {
                    @Override
                    public UserFollowedMentorsResponse apply(UserFollowedMentorsResponse feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FeedResponsePojo> getFeedFromModelForTestProfile(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Function<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo apply(FeedResponsePojo feedResponsePojo) {
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
    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Function<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo apply(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

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

    public Observable<SpamResponse> reportSpam(SpamPostRequest spamPostRequest) {
        return sheroesAppServiceApi.reportSpamPostOrComment(spamPostRequest).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
