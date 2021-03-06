package appliedlife.pvtltd.SHEROES.presenters;

import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.CommunityModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.ChallengePostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UpLoadImageResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatePollRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatePollResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.usertagging.ui.RichEditorView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.RxSearchObservable;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityPostView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_CREATE_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

/**
 * Created by ujjwal on 17/10/17.
 */

public class CreatePostPresenter extends BasePresenter<ICommunityPostView> {
    //region member variables
    public static final String CHALLENGE_POST = "challenge post";
    private SheroesAppServiceApi mSheroesAppServiceApi;
    //endregion member variables

    //region inject variables
    @Inject
    CommunityModel mCommunityModel;

    @Inject
    AppUtils mAppUtils;
    //endregion inject variables

    //region consturctor
    @Inject
    public CreatePostPresenter(AppUtils appUtils, SheroesAppServiceApi sheroesAppServiceApi) {
        mAppUtils = appUtils;
        mSheroesAppServiceApi = sheroesAppServiceApi;
    }
    //endregion consturctor

    //region public methods
    public void sendPost(Map uploadImageFileMap, CommunityPostCreateRequest communityPostCreateRequest, final boolean isSharedFromExternalApp) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mCommunityModel.addPostCommunity(uploadImageFileMap, communityPostCreateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CreateCommunityResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CreateCommunityResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                        if (communityPostCreateResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().onPostSend(communityPostCreateResponse.getFeedDetail());
                            FeedDetail feedDetail = communityPostCreateResponse.getFeedDetail();
                            if (feedDetail != null) {
                                feedDetail.setSharedFromExternalApp(isSharedFromExternalApp);
                                AnalyticsManager.trackPostAction(Event.POST_CREATED, feedDetail, CommunityPostActivity.SCREEN_LABEL);
                            }
                        } else {
                            getMvpView().showError(communityPostCreateResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_CREATE_COMMUNITY);
                            getMvpView().stopProgressBar();
                        }
                    }
                });
    }

    public void sendChallengePost(final ChallengePostCreateRequest challengePostCreateRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mCommunityModel.createChallengePost(challengePostCreateRequest).compose(this.<CreateCommunityResponse>bindToLifecycle()).subscribe(new DisposableObserver<CreateCommunityResponse>() {

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                getMvpView().stopProgressBar();
                if (communityPostCreateResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    getMvpView().finishActivity();
                } else {
                    Toast.makeText(SheroesApplication.mContext, communityPostCreateResponse.getFieldErrorMessageMap().get("error"), Toast.LENGTH_SHORT).show();
                }
                final HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(communityPostCreateResponse.getId()))
                                .challengeId(Long.toString(challengePostCreateRequest.getmChallengeId()))
                                .communityId("0")
                                .type(CHALLENGE_POST)
                                .build();
                AnalyticsManager.trackEvent(Event.POST_CREATED, CommunityPostActivity.SCREEN_LABEL, properties);
            }
        });
    }

    public void fetchLinkDetails(LinkRequest linkRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mCommunityModel.linkRenderFromModel(linkRequest).compose(this.<LinkRenderResponse>bindToLifecycle()).subscribe(new DisposableObserver<LinkRenderResponse>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(LinkRenderResponse linkRenderResponse) {
                getMvpView().stopProgressBar();
                getMvpView().linkRenderResponse(linkRenderResponse);
            }
        });
    }

    public void editPost(Map uploadImageFileMap, final CommunityPostCreateRequest communityPostCreateRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mCommunityModel.editPostCommunity(uploadImageFileMap, communityPostCreateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CreateCommunityResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CreateCommunityResponse>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(CreateCommunityResponse communityPostCreateResponse) {
                        getMvpView().stopProgressBar();
                        if (communityPostCreateResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().onPostSend(communityPostCreateResponse.getFeedDetail());
                            AnalyticsManager.trackPostAction(Event.POST_EDITED, communityPostCreateResponse.getFeedDetail(), CommunityPostActivity.SCREEN_LABEL);
                        } else {
                            Toast.makeText(SheroesApplication.mContext, communityPostCreateResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Debounce for usermention function
    public void getUserMentionSuggestion(final RichEditorView richEditorView, final CommunityPost communityPost) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        RxSearchObservable.fromView(richEditorView, getMvpView())
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMap(new Function<String, ObservableSource<SearchUserDataResponse>>() {
                    @Override
                    public ObservableSource<SearchUserDataResponse> apply(String query) throws Exception {
                        SearchUserDataRequest searchUserDataRequest = null;
                        Long communityId = null;
                        if (null != communityPost && null != communityPost.community) {
                          communityId = communityPost.community.id;
                        }
                        if (query.length() == 1) {
                            searchUserDataRequest = mAppUtils.searchUserDataRequest("", communityId, null, null, "POST");
                        } else {
                            searchUserDataRequest = mAppUtils.searchUserDataRequest(query.trim().replace("@", ""), communityId, null, null, "POST");
                        }

                        if (searchUserDataRequest == null) {
                            return Observable.empty();
                        }
                        return mCommunityModel.getUserMentionSuggestionSearchResult(searchUserDataRequest);
                    }
                })
                .compose(this.<SearchUserDataResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SearchUserDataResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                    }

                    @Override
                    public void onNext(SearchUserDataResponse searchUserDataResponse) {
                        if (null != searchUserDataResponse) {
                            if (searchUserDataResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                                getMvpView().showUserMentionSuggestionResponse(searchUserDataResponse, null);
                            } else {
                                getMvpView().showError("No user found", ERROR_CREATE_COMMUNITY);
                            }
                        }
                    }
                });
    }

    public void createPoll(CreatePollRequest createPollRequest) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_COMMUNITY_OWNER);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.createPoll(createPollRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CreatePollResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CreatePollResponse>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_CREATE_COMMUNITY);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(CreatePollResponse communityPostCreateResponse) {
                        if (communityPostCreateResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().onPostSend(communityPostCreateResponse.getFeedDetail());
                            AnalyticsManager.trackPostAction(Event.POLL_CREATED, communityPostCreateResponse.getFeedDetail(), CommunityPostActivity.SCREEN_LABEL);
                        } else {
                            getMvpView().showError(communityPostCreateResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_CREATE_COMMUNITY);
                            getMvpView().stopProgressBar();
                        }
                    }
                });
    }

    public void uploadFile(String encodedImage) {
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.uploadImageForAnyModule(mAppUtils.uploadImageRequestBuilder(encodedImage))
                .compose(this.<UpLoadImageResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpLoadImageResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_TAG);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(UpLoadImageResponse upLoadImageResponse) {
                        String finalImageUrl = upLoadImageResponse.images.get(0).uploadedImageUrl;
                        getMvpView().showImage(finalImageUrl);
                        getMvpView().stopProgressBar();
                    }
                });
    }
    //endregion public methods
}
