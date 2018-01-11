package appliedlife.pvtltd.SHEROES.presenters;


import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileFollowedMentor;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserFollowerOrFollowingRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileNewView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;

/**
 * Created by ravi on 01/01/18.
 */

public class ProfilePresenterImpl extends BasePresenter<ProfileNewView> {

    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);

    private ProfileModel profileModel;
    SheroesApplication mSheroesApplication;

    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;

    @Inject
    public ProfilePresenterImpl(ProfileModel profileModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData) {
        this.profileModel = profileModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;

        this.mUserPreferenceMasterData = mUserPreferenceMasterData;

    }

    //post count

    public void getUserPostCountFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = profileModel.getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (null != feedResponsePojo) {
                    getMvpView().getUsersPostCount(feedResponsePojo.getNumFound());
                }
            }
        });
        registerSubscription(subscription);
    }

    //followed mentor
    public void getFollowedMentors(ProfileFollowedMentor profileFollowedMentor) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        Subscription subscription = profileModel.getFollowedMentor(profileFollowedMentor ).subscribe(new Subscriber<UserFollowedMentorsResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Crashlytics.getInstance().core.logException(throwable);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(UserFollowedMentorsResponse profileFeedResponsePojo) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (null != profileFeedResponsePojo) {
                    Log.i(TAG, profileFeedResponsePojo.getStatus());
                    getMvpView().getFollowedMentors(profileFeedResponsePojo);
                }
            }
        });
        registerSubscription(subscription);

    }

    //get follower/following
    public void getUsersFollowerOrFollowingCount(final UserFollowerOrFollowingRequest userFollowerOrFollowingRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        Subscription subscription = profileModel.getFollowerOrFollowing(userFollowerOrFollowingRequest).subscribe(new Subscriber<BaseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Crashlytics.getInstance().core.logException(throwable);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(BaseResponse userFollowerOrFollowingCount) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (null != userFollowerOrFollowingCount) {
                    Log.i(TAG, userFollowerOrFollowingCount.getStatus());
                    if (userFollowerOrFollowingRequest.getIsUserAFollower())
                        getMvpView().getUsersFollowerCount(userFollowerOrFollowingCount);
                    else
                        getMvpView().getUsersFollowingCount(userFollowerOrFollowingCount);

                }
            }
        });
        registerSubscription(subscription);

    }

    public void getUsersCommunity(ProfileUsersCommunityRequest profileUsersCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        Subscription subscription = profileModel.getUserCommunity(profileUsersCommunityRequest).subscribe(new Subscriber<ProfileCommunitiesResponsePojo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Crashlytics.getInstance().core.logException(throwable);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(ProfileCommunitiesResponsePojo userCommunities) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (null != userCommunities) {
                    Log.i(TAG, userCommunities.getStatus());
                    getMvpView().getUsersCommunities(userCommunities);
                }
            }
        });
        registerSubscription(subscription);
    }

    //Community For public profile
    public void getPublicProfileCommunity(ProfileUsersCommunityRequest profileUsersCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        Subscription subscription = profileModel.getPublicProfileUserCommunity(profileUsersCommunityRequest).subscribe(new Subscriber<ProfileCommunitiesResponsePojo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Crashlytics.getInstance().core.logException(throwable);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(ProfileCommunitiesResponsePojo userCommunities) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if(userCommunities.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    getMvpView().getUsersCommunities(userCommunities);
                }
            }
        });
        registerSubscription(subscription);
    }

}
