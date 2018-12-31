package appliedlife.pvtltd.SHEROES.presenters;


import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunitiesListView;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * communities presenter to perform action like join, left , fetch my/all community, fetch community details.
 */
public class CommunitiesListPresenter extends BasePresenter<ICommunitiesListView> {
    //region Constants
    private final String INFO = "info";
    //endregion Constants

    //region injected variable
    @Inject
    AppUtils mAppUtils;
    //endregion

    //region private member variable
    private SheroesApplication mSheroesApplication;
    private SheroesAppServiceApi mSheroesAppServiceApi;
    private String mNextToken;
    private ArrayList<FeedDetail> mCommunitiesList = new ArrayList<>();
    private boolean mIsCommunityFeedLoading;
    private boolean mHasCommunityFeedEnded;
    //endregion private member variable

    //region constructor
    @Inject
    public CommunitiesListPresenter(SheroesApplication sheroesApplication, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mSheroesApplication = sheroesApplication;
        this.mSheroesAppServiceApi = sheroesAppServiceApi;
    }
    //endregion

    //region public method
    public void fetchAllCommunities() {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.fetchAllCommunities(new BaseRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), null);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        getMvpView().stopProgressBar();
                        if (feedResponsePojo.getStatus().equals(AppConstants.SUCCESS)) {
                            ArrayList<FeedDetail> feedDetails = new ArrayList<>(feedResponsePojo.getFeedDetails());
                            getMvpView().showAllCommunity(feedDetails);
                        } else {
                            getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), null);
                        }
                    }
                });
    }

    public void setHasFeedEnded(boolean hasFeedEnded) {
        this.mHasCommunityFeedEnded = hasFeedEnded;
    }

    public boolean isCommunityFeedLoading() {
        return mIsCommunityFeedLoading;
    }

    public boolean getCommunityFeedEnded() {
        return mHasCommunityFeedEnded;
    }

    public void fetchSearchedCommunity(String searchText, String searchCategory) {
        if (mIsCommunityFeedLoading) {
            return;
        }
        String URL = AppConstants.SEARCH + AppConstants.SEARCH_QUERY + searchText + AppConstants.SEARCH_TAB + searchCategory;
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        if (mNextToken != null) {
            URL = URL + AppConstants.SEARCH_NEXT_TOKEN + mNextToken;
        }
        mIsCommunityFeedLoading = true;

        mSheroesAppServiceApi.getSearchResponse(URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), null);
                        mIsCommunityFeedLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        mIsCommunityFeedLoading = false;
                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        getMvpView().stopProgressBar();
                        if (feedResponsePojo.getStatus().equals(AppConstants.SUCCESS)) {
                            if (feedResponsePojo.getFeedDetails() != null && feedResponsePojo.getFeedDetails().size() > 0) {
                                mNextToken = feedResponsePojo.getNextToken();
                                mIsCommunityFeedLoading = false;
                                if (!CommonUtil.isNotEmpty(mNextToken)) {
                                    mHasCommunityFeedEnded = true;
                                }
                                ArrayList<FeedDetail> feedDetails = new ArrayList<>(feedResponsePojo.getFeedDetails());
                                mCommunitiesList.addAll(feedDetails);
                                getMvpView().showAllCommunity(mCommunitiesList);
                            } else if (feedResponsePojo.getFieldErrorMessageMap() != null) {
                                if (feedResponsePojo.getFieldErrorMessageMap().containsKey(INFO)) {
                                    getMvpView().showEmptyScreen(feedResponsePojo.getFieldErrorMessageMap().get(INFO));
                                } else {
                                    getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                                }
                            }
                        } else if (feedResponsePojo.getFieldErrorMessageMap() != null) {
                            if (feedResponsePojo.getFieldErrorMessageMap().containsKey(INFO)) {
                                getMvpView().showEmptyScreen(feedResponsePojo.getFieldErrorMessageMap().get(INFO));
                            } else {
                                getMvpView().showEmptyScreen(mSheroesApplication.getString(R.string.empty_search_result));
                            }
                        }
                    }
                });
    }

    public void resetState() {
        mCommunitiesList.clear();
        mNextToken = null;
        mHasCommunityFeedEnded = false;
    }

    //get my communities
    public void fetchMyCommunities(MyCommunityRequest myCommunityRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_MY_COMMUNITIES);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getMyCommunityFromApi(myCommunityRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_MY_COMMUNITIES);
                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        getMvpView().stopProgressBar();
                        if (null != feedResponsePojo) {
                            getMvpView().showMyCommunities(feedResponsePojo);
                        }
                    }
                });
    }

    //Join community
    public void joinCommunity(CommunityRequest communityRequest, final CommunityFeedSolrObj communityFeedSolrObj, final CarouselViewHolder carouselViewHolder) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
            communityFeedSolrObj.setMember(false);
            getMvpView().onCommunityJoined(communityFeedSolrObj, carouselViewHolder);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CommunityResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CommunityResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
                        communityFeedSolrObj.setMember(false);
                        getMvpView().onCommunityJoined(communityFeedSolrObj, carouselViewHolder);
                    }

                    @Override
                    public void onNext(CommunityResponse communityResponse) {
                        if (communityResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
                            communityFeedSolrObj.setMember(false);
                            getMvpView().onCommunityJoined(communityFeedSolrObj, carouselViewHolder);
                        } else {
                            getMvpView().onCommunityJoined(communityFeedSolrObj, carouselViewHolder);
                        }
                        getMvpView().stopProgressBar();
                    }
                });
    }

    public void joinCommunity(CommunityRequest communityRequest, final CommunityFeedSolrObj communityFeedSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
            communityFeedSolrObj.setMember(false);
            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<CommunityResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<CommunityResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
                        communityFeedSolrObj.setMember(false);
                        getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                    }

                    @Override
                    public void onNext(CommunityResponse communityResponse) {
                        if (communityResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() - 1);
                            communityFeedSolrObj.setMember(false);
                            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                        } else {
                            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                        }
                        getMvpView().stopProgressBar();
                    }
                });
    }

    public void leaveCommunity(RemoveMemberRequest removeMemberRequest, final CommunityFeedSolrObj communityFeedSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
            communityFeedSolrObj.setMember(true);
            getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.removeMember(removeMemberRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<MemberListResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MemberListResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
                        communityFeedSolrObj.setMember(true);
                        getMvpView().invalidateCommunityJoin(communityFeedSolrObj);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(MemberListResponse memberListResponse) {
                        if (memberListResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
                            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
                            communityFeedSolrObj.setMember(true);
                        }
                        getMvpView().invalidateCommunityLeft(communityFeedSolrObj);
                        getMvpView().stopProgressBar();
                    }
                });
    }

    //community left
    public void communityLeft(RemoveMemberRequest removeMemberRequest, final CommunityFeedSolrObj communityFeedSolrObj, final CarouselViewHolder carouselViewHolder) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
            communityFeedSolrObj.setMember(true);
            getMvpView().onCommunityJoined(communityFeedSolrObj, carouselViewHolder);
            return;
        }
        getMvpView().startProgressBar();

        mSheroesAppServiceApi.removeMember(removeMemberRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<MemberListResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<MemberListResponse>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().showError(e.getMessage(), ERROR_JOIN_INVITE);
                        communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
                        communityFeedSolrObj.setMember(true);
                        getMvpView().onCommunityJoined(communityFeedSolrObj, carouselViewHolder);
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onNext(MemberListResponse memberListResponse) {
                        if (memberListResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
                            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_JOIN_INVITE);
                            communityFeedSolrObj.setNoOfMembers(communityFeedSolrObj.getNoOfMembers() + 1);
                            communityFeedSolrObj.setMember(true);
                        }
                        getMvpView().onCommunityLeft(communityFeedSolrObj, carouselViewHolder);
                        getMvpView().stopProgressBar();
                    }
                });
    }

    //Get community details
    public void fetchCommunity(String communityId) {
        if (!TextUtils.isDigitsOnly(communityId)) return;
        FeedRequestPojo feedRequestPojo = mAppUtils.userCommunityDetailRequestBuilder(AppConstants.FEED_COMMUNITY, 1, Long.valueOf(communityId));

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_FEED_RESPONSE);
                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        if (null != feedResponsePojo && feedResponsePojo.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().setCommunity((CommunityFeedSolrObj) feedResponsePojo.getFeedDetails().get(0));
                        }
                    }
                });
    }
    //endregion

}
