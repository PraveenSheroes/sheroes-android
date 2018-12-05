package appliedlife.pvtltd.SHEROES.presenters;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.DeactivateUserRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IProfileView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

/**
 * Created by ravi on 01/01/18.
 */

public class ProfilePresenterImpl extends BasePresenter<IProfileView> {

    //region constants
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    //endregion constants

    //region private variable
    private ProfileModel mProfileModel;
    private SheroesApplication mSheroesApplication;
    //endregion private variable

    //region injected variable
    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;
    //endregion injected variable

    //region constructor
    @Inject
    public ProfilePresenterImpl(ProfileModel profileModel, SheroesApplication sheroesApplication) {
        this.mProfileModel = profileModel;
        this.mSheroesApplication = sheroesApplication;
    }
    //endregion constructor

    //region public methods
    //Get followers or following of users/champions
    public void getFollowedMentors(FollowersFollowingRequest profileFollowedMentor) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        mProfileModel.getFollowerFollowing(profileFollowedMentor)
                .compose(this.<FollowedUsersResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<FollowedUsersResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
               getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(FollowedUsersResponse profileFeedResponsePojo) {
                getMvpView().stopProgressBar();
                if (null != profileFeedResponsePojo) {
                    getMvpView().getFollowedMentors(profileFeedResponsePojo);
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

        mProfileModel.getUserCommunity(profileUsersCommunityRequest)
                .compose(this.<ProfileCommunitiesResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<ProfileCommunitiesResponsePojo>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(ProfileCommunitiesResponsePojo userCommunities) {
                getMvpView().stopProgressBar();
                if (null != userCommunities) {
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

        mProfileModel.getPublicProfileUserCommunity(profileUsersCommunityRequest)
                .compose(this.<ProfileCommunitiesResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<ProfileCommunitiesResponsePojo>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
            }

            @Override
            public void onNext(ProfileCommunitiesResponsePojo userCommunities) {
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

        mProfileModel.reportSpam(spamPostRequest)
                .subscribeOn(Schedulers.io())
                .compose(this.<SpamResponse>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SpamResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(SpamResponse spamPostOrCommentResponse) {
                        getMvpView().onSpamPostOrCommentReported(spamPostOrCommentResponse);
                        getMvpView().stopProgressBar();
                    }
                });
    }

    //deactivate user
    public void deactivateUser(DeactivateUserRequest deactivateUserRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            return;
        }
        getMvpView().startProgressBar();

        mProfileModel.deactivateUser(deactivateUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(BaseResponse spamPostOrCommentResponse) {
                        getMvpView().stopProgressBar();
                        if(spamPostOrCommentResponse!=null && spamPostOrCommentResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().onUserDeactivation(spamPostOrCommentResponse, true);
                        } else {
                            getMvpView().onUserDeactivation(spamPostOrCommentResponse, false);
                        }
                    }
                });
    }
    //endregion public methods
}
