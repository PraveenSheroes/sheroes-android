package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommunityModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.ChallengePostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityPostView;
import io.reactivex.observers.DisposableObserver;


import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_CREATE_COMMUNITY;

/**
 * Created by ujjwal on 17/10/17.
 */

public class CreatePostPresenter extends BasePresenter<ICommunityPostView>{
    @Inject
    CommunityModel communityModel;

    @Inject
    public CreatePostPresenter() {

    }

    public void sendPost(CommunityPostCreateRequest communityPostCreateRequest){
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        communityModel.addPostCommunity(communityPostCreateRequest).subscribe(new DisposableObserver<CreateCommunityResponse>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                getMvpView().onPostSend(communityPostCreateResponse.getFeedDetail());
                AnalyticsManager.trackPostAction(Event.POST_CREATED, communityPostCreateResponse.getFeedDetail(), CommunityPostActivity.SCREEN_LABEL);
            }

        });

    }

    public void sendChallengePost(final ChallengePostCreateRequest challengePostCreateRequest){
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        communityModel.createChallengePost(challengePostCreateRequest).subscribe(new DisposableObserver<CreateCommunityResponse>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                getMvpView().stopProgressBar();
                if(communityPostCreateResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)){
                    getMvpView().finishActivity();
                }
                    final HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .id(Long.toString(communityPostCreateResponse.getId()))
                                    .challengeId(Long.toString(challengePostCreateRequest.getmChallengeId()))
                                    .type(MoEngageConstants.CHALLENGE_POST)
                                    .build();
                    AnalyticsManager.trackEvent(Event.POST_CREATED, CommunityPostActivity.SCREEN_LABEL, properties);
            }

        });

    }

    public void fetchLinkDetails(LinkRequest linkRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        communityModel.linkRenderFromModel(linkRequest).subscribe(new DisposableObserver<LinkRenderResponse>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(LinkRenderResponse linkRenderResponse) {
                getMvpView().stopProgressBar();
                getMvpView().linkRenderResponse(linkRenderResponse);
            }

        });

    }

    public void editPost(final CommunityPostCreateRequest communityPostCreateRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION,ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        communityModel.editPostCommunity(communityPostCreateRequest).subscribe(new DisposableObserver<CreateCommunityResponse>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                getMvpView().stopProgressBar();
                getMvpView().onPostSend(communityPostCreateResponse.getFeedDetail());
                AnalyticsManager.trackPostAction(Event.POST_EDITED, communityPostCreateResponse.getFeedDetail(), CommunityPostActivity.SCREEN_LABEL);
            }

        });

    }
}
