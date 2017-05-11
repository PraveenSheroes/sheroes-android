package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
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

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.service.GcmIdReceiver;
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
    String mNewGcmId, mOldGcmId;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFeedDetail = bundle.getParcelable(AppConstants.HOME_FRAGMENT);
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

                    if (StringUtil.isNotNullOrEmptyString(mUserPreference.get().getGcmId())) {
                        mNewGcmId = GcmIdReceiver.getGcmId(getActivity());
                        mOldGcmId = mUserPreference.get().getGcmId();
                        if(!mOldGcmId.equalsIgnoreCase(mNewGcmId))
                        {
                            LoginRequest loginRequest=loginRequestBuilder();
                            loginRequest.setGcmorapnsid(mNewGcmId);
                            mHomePresenter.getNewGCMidFromPresenter(loginRequest);
                        }
                    }
                }
            }
        } else {
            mHomePresenter.getAuthTokenRefreshPresenter();
        }
        mHomePresenter.getNotificationCountFromPresenter(notificationReadCountRequestBuilder(TAG));
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedMethod();
            }
        });
        return view;
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
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
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
    private void gcmIdResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof GcmIdResponse) {
                    LoginResponse loginResponse = mUserPreference.get();
                    loginResponse.setGcmId(mNewGcmId);
                    mUserPreference.set(loginResponse);
                }
                break;
            case AppConstants.FAILED:
                break;
            default:
        }
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        mProgressBarFirstLoad.setVisibility(View.GONE);
        super.getFeedListSuccess(feedResponsePojo);
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
