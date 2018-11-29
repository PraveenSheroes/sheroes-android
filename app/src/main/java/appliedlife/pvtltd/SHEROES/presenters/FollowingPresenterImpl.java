package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.ChampionFollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowerFollowingView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;

/**
 * Created by ravi on 20/02/18.
 */

public class FollowingPresenterImpl extends BasePresenter<IFollowerFollowingView> {

    //region private variables
    private SheroesApplication mSheroesApplication;
    private SheroesAppServiceApi mSheroesAppServiceApi;
    //endregion

    //region constructor
    @Inject
    public FollowingPresenterImpl(SheroesApplication sheroesApplication, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mSheroesApplication = sheroesApplication;
        this.mSheroesAppServiceApi = sheroesAppServiceApi;
    }
    //endregion

    //region public method
    public void getFollowersFollowing(FollowersFollowingRequest profileFollowedMentor) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getFollowerOrFollowing(profileFollowedMentor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                            getMvpView().getFollowersOrFollowing(profileFeedResponsePojo);
                        }
                    }
                });
    }

    //for follow
    public void getFollowFromPresenter(PublicProfileListRequest publicProfileListRequest, final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getMentorFollowFromApi(publicProfileListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ChampionFollowedResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ChampionFollowedResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                        userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                        userSolrObj.setSolrIgnoreIsUserFollowed(false);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse mentorFollowUnfollowResponse) {
                        getMvpView().stopProgressBar();
                        if (mentorFollowUnfollowResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            userSolrObj.setFollowerCount(userSolrObj.getFollowerCount() + 1);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                            userSolrObj.setSolrIgnoreIsUserFollowed(true);
                        } else {
                            userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                            userSolrObj.setSolrIgnoreIsUserFollowed(false);
                        }
                        getMvpView().refreshItem(userSolrObj);
                    }
                });
    }
    //endregion
}
