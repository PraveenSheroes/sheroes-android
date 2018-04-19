package appliedlife.pvtltd.SHEROES.presenters;


import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopCountRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

/**
 * Created by ravi on 01/01/18.
 */

public class ProfilePresenterImpl extends BasePresenter<ProfileView> {

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

    //Get followers or following of users/champions
    public void getFollowedMentors(FollowersFollowingRequest profileFollowedMentor) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        profileModel.getFollowerFollowing(profileFollowedMentor)
                .compose(this.<UserFollowedMentorsResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<UserFollowedMentorsResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable throwable) {
                Crashlytics.getInstance().core.logException(throwable);
                getMvpView().stopProgressBar();
               // getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
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
    }

    //get profile Top section Count
    public void getProfileTopSectionCount(final ProfileTopCountRequest profileTopCountRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        profileModel.getProfileTopSectionCount(profileTopCountRequest)
                .compose(this.<ProfileTopSectionCountsResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ProfileTopSectionCountsResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable throwable) {
                Crashlytics.getInstance().core.logException(throwable);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(ProfileTopSectionCountsResponse userFollowerOrFollowingCount) {
                LogUtils.info(TAG, "********response***********");
                getMvpView().stopProgressBar();
                if (null != userFollowerOrFollowingCount) {
                    Log.i(TAG, userFollowerOrFollowingCount.getStatus());
                    getMvpView().getTopSectionCount(userFollowerOrFollowingCount);

                }
            }
        });
    }

    public void getUsersCommunity(ProfileUsersCommunityRequest profileUsersCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        profileModel.getUserCommunity(profileUsersCommunityRequest)
                .compose(this.<ProfileCommunitiesResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<ProfileCommunitiesResponsePojo>() {
            @Override
            public void onComplete() {

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

    }

    //Community For public profile
    public void getPublicProfileCommunity(ProfileUsersCommunityRequest profileUsersCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        profileModel.getPublicProfileUserCommunity(profileUsersCommunityRequest)
                .compose(this.<ProfileCommunitiesResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<ProfileCommunitiesResponsePojo>() {
            @Override
            public void onComplete() {

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
    }

    //spam
    public void reportSpamPostOrComment(SpamPostRequest spamPostRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            return;
        }
        getMvpView().startProgressBar();

        profileModel.reportSpam(spamPostRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SpamResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(SpamResponse spamPostOrCommentResponse) {
                        getMvpView().onSpamPostOrCommentReported(spamPostOrCommentResponse);
                        getMvpView().stopProgressBar();
                    }
                });

    }

}
