package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowerFollowingView;
import io.reactivex.observers.DisposableObserver;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;

/**
 * Created by ravi on 20/02/18.
 */

public class FollowingPresenterImpl extends BasePresenter<IFollowerFollowingView> {

    //region private variables
    private ProfileModel profileModel;
    private SheroesApplication mSheroesApplication;
    //endregion

    //region constructor
    @Inject
    FollowingPresenterImpl(ProfileModel profileModel, SheroesApplication sheroesApplication) {
        this.profileModel = profileModel;
        this.mSheroesApplication = sheroesApplication;
    }
    //endregion

    //region public method
    public void getFollowersFollowing(FollowersFollowingRequest profileFollowedMentor) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();

        profileModel.getFollowerFollowing(profileFollowedMentor)
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
    //endregion
}
