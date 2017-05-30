package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.acceptChallengeRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.challengetRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.feedRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.notificationReadCountRequestBuilder;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Home fragment within Home activity perform all the UI operation .
 * Fragment will have all UI components and operate with activity .
 */
public class HomeFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(HomeFragment.class);

    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout mSwipeView;
    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    @Inject
    AppUtils mAppUtils;
    FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPosition;
    private int mPressedEmoji;
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    @Bind(R.id.progress_bar_first_load)
    ProgressBar mProgressBarFirstLoad;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    private String mGcmId;
    private FeedDetail challengeFeedDetail;
    private ChallengeDataItem mChallengeDataItem;
    private int mPercentCompleted;
    private long mChallengeId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFeedDetail = bundle.getParcelable(AppConstants.HOME_FRAGMENT);
            mChallengeId = bundle.getLong(AppConstants.CHALLENGE_ID);
        }
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.HOME_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setInitialPrefetchItemCount(1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                mListLoad = false;
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.GONE);
            }

            @Override
            public void onShow() {
                mListLoad = false;
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {
                if (null != (((HomeActivity) getActivity()).popupWindow)) {
                    ((HomeActivity) getActivity()).popupWindow.dismiss();
                }
            }
        });

        super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mFeedDetail, mRecyclerView, mPosition, mPressedEmoji, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
        if (null == mUserPreference) {
            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(loginIntent);
        } else if (null != mUserPreference.get()) {
            if (!StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(loginIntent);
            } else {
                long daysDifference = System.currentTimeMillis() - mUserPreference.get().getTokenTime();
                if (daysDifference >= AppConstants.SAVED_DAYS_TIME) {
                    mHomePresenter.getAuthTokenRefreshPresenter();
                } else {
                    mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
                }
            }
        } else {
            mHomePresenter.getAuthTokenRefreshPresenter();
        }
        mHomePresenter.getNotificationCountFromPresenter(notificationReadCountRequestBuilder(TAG));
        getGcmId();
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedMethod();
                mHomePresenter.getNotificationCountFromPresenter(notificationReadCountRequestBuilder(TAG));
            }
        });
        return view;
    }

    private void getGcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(getActivity(), AppConstants.PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                LogUtils.info(TAG, "*************Registarion" + registrationId);
                mGcmId = registrationId;
                if (StringUtil.isNotNullOrEmptyString(registrationId)) {
                    if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet() && null != mInstallUpdatePreference.get()) {
                        if (mInstallUpdatePreference.get().isFirstOpen()) {
                            LoginRequest loginRequest = loginRequestBuilder();
                            loginRequest.setGcmorapnsid(registrationId);
                            mHomePresenter.getNewGCMidFromPresenter(loginRequest);
                        } else {
                            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getGcmId())) {
                                String mOldGcmId = mUserPreference.get().getGcmId();
                                if (StringUtil.isNotNullOrEmptyString(mOldGcmId)) {
                                    if (!mOldGcmId.equalsIgnoreCase(registrationId)) {
                                        LoginRequest loginRequest = loginRequestBuilder();
                                        loginRequest.setGcmorapnsid(registrationId);
                                        mHomePresenter.getNewGCMidFromPresenter(loginRequest);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    getGcmId();
                }
            }

            @Override
            public void onFailure(String ex) {
                LogUtils.info(TAG, "*************Fail Registarion" + ex);
            }
        });
    }

    @OnClick(R.id.tv_refresh)
    public void onRefreshClick() {
        tvRefresh.setVisibility(View.GONE);
        refreshFeedMethod();
    }

    private void refreshFeedMethod() {
        setListLoadFlag(false);
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case NOTIFICATION_COUNT:
                unReadNotificationCount(baseResponse);
                break;
            case GCM_ID:
                gcmIdResponse(baseResponse);
                break;
            case CHALLENGE_LIST:
                challengeResponse(baseResponse);
                break;
            case CHALLENGE_ACCEPT:
                challengeAcceptedResponse(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    private void challengeAcceptedResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof ChallengeListResponse) {
                    if (null != challengeFeedDetail) {
                        List<ChallengeDataItem> challengeDataItemList = challengeFeedDetail.getChallengeDataItems();
                        long challengeId = mChallengeDataItem.getChallengeId();
                        for (ChallengeDataItem challengeDataItem : challengeDataItemList) {
                            if (challengeId == challengeDataItem.getChallengeId()) {
                                mChallengeDataItem.setIs_accepted(true);
                                if (mPercentCompleted == AppConstants.COMPLETE) {
                                    if (challengeDataItem.getStateChallengeAfterAccept() == AppConstants.FOURTH_CONSTANT) {
                                        Toast.makeText(getActivity(), getString(R.string.ID_CHALLENGE_COMPLETED_SUCCESS), Toast.LENGTH_SHORT).show();
                                        if (null != ((HomeActivity) getActivity()).mChallengeSuccessDialogFragment) {
                                            ((HomeActivity) getActivity()).mChallengeSuccessDialogFragment.dismiss();
                                        }
                                    } else {
                                        ((HomeActivity) getActivity()).challengeSuccessDialog(mChallengeDataItem);
                                    }
                                    mChallengeDataItem.setCompletionPercent(AppConstants.COMPLETE);
                                } else {
                                    if (mPercentCompleted == AppConstants.HALF_DONE) {
                                        mChallengeDataItem.setCompletionPercent(AppConstants.HALF_DONE);
                                    } else if (mPercentCompleted == AppConstants.ALMOST_DONE) {
                                        mChallengeDataItem.setCompletionPercent(AppConstants.ALMOST_DONE);
                                    } else {
                                        int acceptedCount = mChallengeDataItem.getTotalPeopleAccepted();
                                        if (acceptedCount == 0) {
                                            mChallengeDataItem.setTotalPeopleAccepted(1);
                                        }
                                        Toast.makeText(getActivity(), getString(R.string.ID_CHALLENGE_ACCEPT), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                challengeDataItemList.remove(mChallengeDataItem.getItemPosition());
                                challengeDataItemList.add(mChallengeDataItem.getItemPosition(), mChallengeDataItem);
                                challengeFeedDetail.setNoOfMembers(mChallengeDataItem.getItemPosition());
                                challengeFeedDetail.setChallengeDataItems(challengeDataItemList);
                                break;
                            }
                        }
                        commentListRefresh(challengeFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                    }
                }
                break;
            case AppConstants.FAILED:
                if (null != ((HomeActivity) getActivity()).mChallengeSuccessDialogFragment) {
                    ((HomeActivity) getActivity()).mChallengeSuccessDialogFragment.dismiss();
                }
                showError(getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);
                break;
            default:
        }

    }

    private void challengeResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof ChallengeListResponse) {
                    ChallengeListResponse challengeListResponse = (ChallengeListResponse) baseResponse;
                    if (StringUtil.isNotEmptyCollection(challengeListResponse.getReponseList())) {
                        challengeFeedDetail = new FeedDetail();
                        challengeFeedDetail.setSubType(AppConstants.CHALLENGE_SUB_TYPE);
                        challengeFeedDetail.setCommunityId(mChallengeId);
                        challengeFeedDetail.setNoOfMembers(0);
                        challengeFeedDetail.setChallengeDataItems(challengeListResponse.getReponseList());
                        challengeAddOnFeed(challengeFeedDetail);
                    }
                }
                break;
            case AppConstants.FAILED:
                break;
            default:
        }
    }

    private void gcmIdResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof GcmIdResponse) {
                    LoginResponse loginResponse = mUserPreference.get();
                    loginResponse.setGcmId(mGcmId);
                    mUserPreference.set(loginResponse);
                    InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                    installUpdateForMoEngage.setFirstOpen(false);
                    mInstallUpdatePreference.set(installUpdateForMoEngage);
                }
                break;
            case AppConstants.FAILED:
                break;
            default:
        }
    }

    private void unReadNotificationCount(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof NotificationReadCountResponse) {
                    NotificationReadCountResponse notificationReadCountResponse = (NotificationReadCountResponse) baseResponse;
                    StringBuilder stringBuilder = new StringBuilder();
                    if (notificationReadCountResponse.getUnread_notification_count() > 0) {
                        ((HomeActivity) getActivity()).flNotificationReadCount.setVisibility(View.VISIBLE);
                        String notification = String.valueOf(notificationReadCountResponse.getUnread_notification_count());
                        stringBuilder.append(notification);
                        ((HomeActivity) getActivity()).mTvNotificationReadCount.setText(stringBuilder.toString());
                    } else {
                        ((HomeActivity) getActivity()).flNotificationReadCount.setVisibility(View.GONE);
                    }
                }
                break;
            case AppConstants.FAILED:
                ((HomeActivity) getActivity()).flNotificationReadCount.setVisibility(View.GONE);
                break;
            default:
                ((HomeActivity) getActivity()).flNotificationReadCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        mProgressBarFirstLoad.setVisibility(View.GONE);
        super.getFeedListSuccess(feedResponsePojo);
        if (mFragmentListRefreshData.getPageNo() == AppConstants.TWO_CONSTANT) {
            mHomePresenter.getChallengeListFromPresenter(challengetRequestBuilder(TAG));
        }
    }

    public void acceptChallenge(ChallengeDataItem challengeDataItem, int completionPercent, boolean isAccepted, boolean isUpdated, String imageUrl, String videoUrl) {
        mChallengeDataItem = challengeDataItem;
        mPercentCompleted = completionPercent;
        Long challengeId = challengeDataItem.getChallengeId();
        mHomePresenter.getChallengeAcceptFromPresenter(acceptChallengeRequestBuilder(challengeId, true, false, completionPercent, isAccepted, isUpdated, imageUrl, videoUrl));
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        super.getSuccessForAllResponse(baseResponse, feedParticipationEnum);
    }

    public void commentListRefresh(FeedDetail feedDetail, FeedParticipationEnum feedParticipationEnum) {
        super.commentListRefresh(feedDetail, feedParticipationEnum);
    }

    public void markAsSpamCommunityPost(FeedDetail feedDetail) {
        super.markAsSpamCommunityPost(feedDetail);
    }

    public void deleteCommunityPost(FeedDetail feedDetail) {
        super.deleteCommunityPost(feedDetail);
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
    }

    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        super.likeAndUnlikeRequest(baseResponse, reactionValue, position);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

        if (null != loginResponse && StringUtil.isNotNullOrEmptyString(loginResponse.getToken())) {
            loginResponse.setTokenTime(System.currentTimeMillis());
            loginResponse.setTokenType(AppConstants.SHEROES_AUTH_TOKEN);
            mUserPreference.set(loginResponse);
            mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
        }
    }

    @OnClick(R.id.tv_no_result_try_again)
    public void onClickTryAgainOnError() {
        mLiNoResult.setVisibility(View.GONE);
        mHomePresenter.getFeedFromPresenter(feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
    }
}
