package appliedlife.pvtltd.SHEROES.presenters;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
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
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    private ProfileModel profileModel;
    private SheroesApplication mSheroesApplication;
    //endregion

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    //region constructor
    @Inject
    public FollowingPresenterImpl(ProfileModel profileModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, Preference<MasterDataResponse> mUserPreferenceMasterData) {
        this.profileModel = profileModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
        this.mUserPreferenceMasterData = mUserPreferenceMasterData;
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
                .compose(this.<UserFollowedMentorsResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<UserFollowedMentorsResponse>() {
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
                    public void onNext(UserFollowedMentorsResponse profileFeedResponsePojo) {
                        LogUtils.info(TAG, "********response***********");
                        getMvpView().stopProgressBar();
                        if (null != profileFeedResponsePojo) {
                            Log.i(TAG, profileFeedResponsePojo.getStatus());
                            getMvpView().getFollowersOrFollowing(profileFeedResponsePojo);
                        }
                    }
                });
    }
    //endregion
}
