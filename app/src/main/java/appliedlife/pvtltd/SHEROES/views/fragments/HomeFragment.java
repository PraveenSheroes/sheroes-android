package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MentorDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.home.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.UserPhoneContactsListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.GcmIdResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ShowcaseManager;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.SPAM_POST_APPROVE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PERMISSIONS_REQUEST_READ_CONTACTS;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.loginRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
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
    private static final String SCREEN_LABEL = "Feed Screen";
    private final String TAG = LogUtils.makeLogTag(HomeFragment.class);

    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    public EmptyRecyclerView mRecyclerView;
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
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    @Bind(R.id.progress_bar_first_load)
    ProgressBar mProgressBarFirstLoad;
    @Bind(R.id.loader_gif)
    CardView loaderGif;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.empty_view)
    View emptyView;
    @Bind(R.id.root_layout)
    FrameLayout rootLayout;
    private String mGcmId;
    private long mChallengeId;
    private boolean mIsSpam;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private List<FeedDetail> mfeedDetailList = new ArrayList<>();
    private long mUserId;
    private boolean isChallenge;
    public Contest mContest;
    private ShowcaseManager showcaseManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home_feed, container, false);
        ButterKnife.bind(this, view);
        loaderGif.setVisibility(View.VISIBLE);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isChallenge = bundle.getBoolean(ContestActivity.IS_CHALLENGE, false);
            mFeedDetail = Parcels.unwrap(bundle.getParcelable(AppConstants.HOME_FRAGMENT));
            mChallengeId = bundle.getLong(AppConstants.CHALLENGE_ID);
        }
        if(isChallenge){
            Parcelable parcelable = getArguments().getParcelable(Contest.CONTEST_OBJ);
            mContest = Parcels.unwrap(parcelable);
        }
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.HOME_FRAGMENT, AppConstants.NO_REACTION_CONSTANT, isChallenge);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setInitialPrefetchItemCount(1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(isChallenge){
            mRecyclerView.setEmptyViewWithImage(emptyView, R.string.empty_challenge_response_text, R.drawable.vector_no_challenge_response, R.string.empty_challenge_response_subtext);
        }
        if(getActivity() instanceof HomeActivity){
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        }

        if(getActivity() instanceof ContestActivity){
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (ContestActivity) getActivity());
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                if(!isChallenge){
                    mListLoad = false;
                    ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.GONE);
                    ((HomeActivity) getActivity()).mFloatActionBtn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onShow() {
                if(!isChallenge){
                    mListLoad = false;
                    ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
                    ((HomeActivity) getActivity()).mFloatActionBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void dismissReactions() {
                if(!isChallenge){
                    if (null != (((HomeActivity) getActivity()).popupWindow)) {
                        ((HomeActivity) getActivity()).popupWindow.dismiss();
                    }
                }
            }

        });
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mFeedDetail, mRecyclerView, 0, 0, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
        if (null == mUserPreference) {
            ((HomeActivity) getActivity()).logOut();
        } else if (null != mUserPreference.get()) {

            if (!StringUtil.isNotNullOrEmptyString(mUserPreference.get().getToken())) {
                ((HomeActivity) getActivity()).logOut();
            } else {
                long daysDifference = System.currentTimeMillis() - mUserPreference.get().getTokenTime();
                if (daysDifference >= AppConstants.SAVED_DAYS_TIME) {
                    mHomePresenter.getAuthTokenRefreshPresenter();
                } else {
                    mHomePresenter.getAuthTokenRefreshPresenter();
                    if(isChallenge){
                        mFragmentListRefreshData.setChallenge(true);
                        mFragmentListRefreshData.setSourceEntity(mContest.remote_id);
                        mFragmentListRefreshData.setSubType(AppConstants.FEED_COMMUNITY_POST);
                        FeedRequestPojo feedRequestPojo = AppUtils.makeChallengeResponseRequest(AppConstants.FEED_COMMUNITY_POST,mContest.remote_id, mFragmentListRefreshData.getPageNo());
                        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
                        mHomePresenter.getChallengeResponse(feedRequestPojo, mFragmentListRefreshData);
                    }else {
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        params.setMargins(0, 190, 0, 0);
                        loaderGif.setLayoutParams(params);
                        showHeaderOnFeed();
                        FeedRequestPojo feedRequestPojo = mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo());
                        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
                        mHomePresenter.getNewHomeFeedFromPresenter(feedRequestPojo, mAppUtils.appIntroRequestBuilder(AppConstants.APP_INTRO),mFragmentListRefreshData);
                        mHomePresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
                    }
                }
            }
            if (null != mUserPreference.get().getUserSummary()) {
                mUserId = mUserPreference.get().getUserSummary().getUserId();
                AppsFlyerLib.getInstance().setCustomerUserId(String.valueOf(mUserId));
                AppsFlyerLib.getInstance().startTracking(SheroesApplication.mContext, getString(R.string.ID_APPS_FLYER_DEV_ID));
                ((SheroesApplication) getActivity().getApplication()).trackUserId(String.valueOf(mUserId));
            }
        } else {
            mHomePresenter.getAuthTokenRefreshPresenter();
        }
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity)getActivity()).homeButtonUi();
        }
        mHomePresenter.getNotificationCountFromPresenter(notificationReadCountRequestBuilder(TAG));
        try {
            getGcmId();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }

        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        moEngageUtills.entityMoEngageViewFeed(getActivity(), mMoEHelper, payloadBuilder, 0);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_FEED_IMPRESSION));
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedMethod();
            }
        });
        return view;
    }
    private void showHeaderOnFeed()
    {
        List<FeedDetail> data=new ArrayList<>();
        FeedDetail header = new FeedDetail();
        header.setSubType(AppConstants.HEADER);
        data.add(0, header);
        mPullRefreshList.allListData(data);
        mAdapter.setSheroesGenericListData(data);
        mAdapter.setUserId(mUserId);
        mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
        mAdapter.notifyDataSetChanged();
    }
    public void showCaseDesign() {
        ((HomeActivity)getActivity()).mIsFirstTimeOpen=false;
        showcaseManager = new ShowcaseManager(getActivity(),((HomeActivity)getActivity()).mFloatActionBtn,((HomeActivity)getActivity()).mTvHome,((HomeActivity)getActivity()).mTvCommunities,((HomeActivity)getActivity()).tvDrawerNavigation,mRecyclerView);
        showcaseManager.showFirstMainActivityShowcase();
        InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
        installUpdateForMoEngage.setAppInstallFirstTime(true);
        mInstallUpdatePreference.set(installUpdateForMoEngage);
    }

    private void getGcmId() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (null != getActivity() && isAdded()) {
            GCMClientManager pushClientManager = new GCMClientManager(getActivity(), getString(R.string.ID_PROJECT_ID));
            pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
                @Override
                public void onSuccess(String registrationId, boolean isNewRegistration) {
                    mGcmId = registrationId;
                    PushManager.getInstance().refreshToken(getActivity(), mGcmId);
                    if (StringUtil.isNotNullOrEmptyString(registrationId)) {
                        if (null != mInstallUpdatePreference && mInstallUpdatePreference.isSet() && null != mInstallUpdatePreference.get()) {
                            if (mInstallUpdatePreference.get().isFirstOpen()) {
                                LoginRequest loginRequest = loginRequestBuilder();
                                loginRequest.setGcmorapnsid(registrationId);
                                if (mInstallUpdatePreference.get().isWelcome()) {
                                    InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                                    installUpdateForMoEngage.setWelcome(false);
                                    mInstallUpdatePreference.set(installUpdateForMoEngage);
                                }
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
                                if (mInstallUpdatePreference.get().isWelcome()) {
                                    InstallUpdateForMoEngage installUpdateForMoEngage = mInstallUpdatePreference.get();
                                    installUpdateForMoEngage.setWelcome(false);
                                    mInstallUpdatePreference.set(installUpdateForMoEngage);
                                }
                            }
                        }
                    } else {
                        getGcmId();
                    }
                }

                @Override
                public void onFailure(String ex) {

                }
            });
        }
    }

    @OnClick(R.id.tv_refresh)
    public void onRefreshClick() {
        tvRefresh.setVisibility(View.GONE);
        refreshFeedMethod();
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = null;
    }

    private void refreshFeedMethod() {
        setListLoadFlag(false);
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        if(isChallenge){
            FeedRequestPojo feedRequestPojo = AppUtils.makeChallengeResponseRequest(AppConstants.FEED_COMMUNITY_POST,mContest.remote_id, mFragmentListRefreshData.getPageNo());
            feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
            mHomePresenter.getChallengeResponse(feedRequestPojo, mFragmentListRefreshData);
        }else {
            List<FeedDetail> data=new ArrayList<>();
            FeedDetail header = new FeedDetail();
            header.setSubType(AppConstants.HEADER);
            data.add(0, header);
            mPullRefreshList.allListData(data);
            FeedRequestPojo feedRequestPojo =mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo());
            feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
            mHomePresenter.getNewHomeFeedFromPresenter(feedRequestPojo, mAppUtils.appIntroRequestBuilder(AppConstants.APP_INTRO),mFragmentListRefreshData);
            mHomePresenter.getAllCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, 1));
            mHomePresenter.getNotificationCountFromPresenter(notificationReadCountRequestBuilder(TAG));
        }
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
            case USER_CONTACTS_ACCESS_SUCCESS:
                getAppContactsListSuccess(baseResponse);
                break;
            case SPAM_POST_APPROVE:
                setProgressBar(mProgressBar);
                approveSpamPostResponse(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    @Override
    public void invalidateLikeUnlike(Comment comment) {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        List<FeedDetail> feedDetailList = mPullRefreshList.getFeedResponses();
        FeedDetail feedDetail = feedDetailList.get(comment.getItemPosition());
        feedDetail.setLastComments(commentList);
        mAdapter.notifyItemChanged(comment.getItemPosition(), feedDetail);
    }

    private void approveSpamPostResponse(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (baseResponse instanceof ApproveSpamPostResponse) {
                    try {
                        FeedDetail feedDetail = (FeedDetail) mFeedDetail.clone();
                        if (mIsSpam) {
                            commentListRefresh(feedDetail, DELETE_COMMUNITY_POST);
                        } else {
                            feedDetail.setSpamPost(false);
                            commentListRefresh(feedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), SPAM_POST_APPROVE);
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
                  int  notificationCount=notificationReadCountResponse.getUnread_notification_count();
                    if (notificationReadCountResponse.getUnread_notification_count() > 0) {
                        ((HomeActivity) getActivity()).flNotificationReadCount.setVisibility(View.VISIBLE);
                        String notification = String.valueOf(notificationCount);
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
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        showHomeFeedList(feedDetailList);
    }

    @Override
    public void showHomeFeedList(List<FeedDetail> feedDetailList) {
        loaderGif.setVisibility(View.GONE);
        mLiNoResult.setVisibility(View.GONE);
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            if(getActivity() instanceof HomeActivity && ((HomeActivity)getActivity()).mIsFirstTimeOpen) {
                showCaseDesign();
            }
            mPageNo = mFragmentListRefreshData.getPageNo();
            if (mPageNo == AppConstants.ONE_CONSTANT && mFragmentListRefreshData.getSwipeToRefresh() == AppConstants.ONE_CONSTANT) {
                if (StringUtil.isNotEmptyCollection(mfeedDetailList) && StringUtil.isNotNullOrEmptyString(mfeedDetailList.get(0).getId()) && StringUtil.isNotNullOrEmptyString(feedDetailList.get(0).getId())) {
                    if (mfeedDetailList.get(0).getId().equalsIgnoreCase(feedDetailList.get(0).getId())) {
                        Toast.makeText(getContext(), getString(R.string.ID_FEED_ALREADY_REFRESH), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.ID_FEED_REFRESH), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (mPageNo == AppConstants.ONE_CONSTANT) {
                mfeedDetailList = feedDetailList;
            }
            LogUtils.info(TAG, "**************position *****" +mPageNo );
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<FeedDetail> data=null;
            FeedDetail feedProgressBar=new FeedDetail();
            feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data=mPullRefreshList.getFeedResponses();
            int position=data.size()- feedDetailList.size();
            if (position > 1) {
                data.remove(position - 1);
            }
            data.add(feedProgressBar);
            mAdapter.setSheroesGenericListData(data);
            mAdapter.setUserId(mUserId);
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            if (mPageNo == AppConstants.TWO_CONSTANT) {
                mfeedDetailList = feedDetailList;
                mAdapter.notifyDataSetChanged();
            }else {
                mAdapter.notifyItemRangeChanged(position+1, feedDetailList.size());
            }
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            // mLiNoResult.setVisibility(View.VISIBLE);
        } else {
            mLiNoResult.setVisibility(View.GONE);
            List<FeedDetail> data=mPullRefreshList.getFeedResponses();
            data.remove(data.size()-1);
            mAdapter.notifyDataSetChanged();
        }
        mSwipeView.setRefreshing(false);
       /* if (null != mUserPreference && !mUserPreference.get().isAppContactAccessed()) {
            getUserContacts();
        }*/
    }

    public void followUnFollowRequest(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        if (userSolrObj.isSolrIgnoreIsMentorFollowed()) {
            mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest,userSolrObj);
        } else {
            mHomePresenter.getFollowFromPresenter(publicProfileListRequest,userSolrObj);
        }
    }
    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case FOLLOW_UNFOLLOW:

                if(mPullRefreshList == null || mPullRefreshList.getFeedResponses() == null || mPullRefreshList.getFeedResponses().size()<=0)  //fix for crash
                    return;

                List<FeedDetail> feedDetailList=mPullRefreshList.getFeedResponses();
                MentorDataObj mentorDataObj=(MentorDataObj) feedDetailList.get(((UserSolrObj)baseResponse).currentItemPosition);
                if(((UserSolrObj) baseResponse).isSuggested())
                {
                    mentorDataObj.setItemPosition(((UserSolrObj)baseResponse).getItemPosition());
                    UserSolrObj userPassedObject=(UserSolrObj)baseResponse;
                    List<UserSolrObj> mentorDataObjList=new ArrayList<>();
                    for(UserSolrObj userSolrObjLocal:mentorDataObj.getMentorParticipantModel())
                    {
                        if(userPassedObject.getItemPosition()==userSolrObjLocal.getItemPosition())
                        {
                            mentorDataObjList.add(userPassedObject);
                        }else
                        {
                            mentorDataObjList.add(userSolrObjLocal);
                        }
                    }
                    mentorDataObj.setMentorParticipantModel(mentorDataObjList);
                    mAdapter.notifyItemChanged(((UserSolrObj)baseResponse).currentItemPosition,mentorDataObj );
                }else
                {
                    mentorDataObj.setItemPosition(((UserSolrObj)baseResponse).getItemPosition());
                    mAdapter.notifyItemChanged(((UserSolrObj)baseResponse).currentItemPosition,mentorDataObj );
                }
                break;
            default:
                super.getSuccessForAllResponse(baseResponse, feedParticipationEnum);
        }
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

    public void approveSpamPost(FeedDetail feedDetail, boolean isActive, boolean isSpam, boolean isApproved) {
        setProgressBar(mProgressBarFirstLoad);
        mFeedDetail = feedDetail;
        mIsSpam = isSpam;
        mHomePresenter.getSpamPostApproveFromPresenter(mAppUtils.spamPostApprovedRequestBuilder(feedDetail, isActive, isSpam, isApproved));
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
            mUserPreference.delete();
            mUserPreference.set(loginResponse);
            mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
        }
    }

    @OnClick(R.id.tv_no_result_try_again)
    public void onClickTryAgainOnError() {
        mLiNoResult.setVisibility(View.GONE);
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
    }

    private Observable<Void> getUserPhoneContactsAndSend() {

        return Observable.create(new Observable.OnSubscribe<Void>() {

            @Override
            public void call(Subscriber<? super Void> subscriber) {
                UserPhoneContactsListRequest userPhoneContactsListRequest = new UserPhoneContactsListRequest();
                List<UserContactDetail> userContactDetailsList = new ArrayList<>();
                UserContactDetail userContactDetail = null;
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                Cursor cursor = getActivity().getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                if (cursor != null && cursor.getCount() > 0) {
                    try {
                        cursor.moveToFirst();
                        while (cursor.isAfterLast() == false) {
                            String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                            userContactDetail = new UserContactDetail(contactName, contactNumber);
                            if (userContactDetail != null) {
                                userContactDetailsList.add(userContactDetail);
                            }
                            userContactDetail = null;
                            cursor.moveToNext();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    } finally {
                        cursor.close();
                    }
                }
                userPhoneContactsListRequest.setContactDetailList(userContactDetailsList);
                mHomePresenter.getAppContactsResponseInPresenter(userPhoneContactsListRequest);
                subscriber.onCompleted();
            }
        });
    }

    private void getAppContactsListSuccess(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                LoginResponse loginResponse = mUserPreference.get();
                loginResponse.setAppContactAccessed(true);
                mUserPreference.set(loginResponse);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserPhoneContactsAndSend().subscribeOn(Schedulers.newThread()).subscribe();
                }
                return;
            }
        }
    }

    private void getUserContacts() {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                getUserPhoneContactsAndSend().subscribeOn(Schedulers.newThread()).subscribe();

            }
        }
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public void scrollToMySubmission(){
        if (CommonUtil.isEmpty(mfeedDetailList)) {
            return;
        }
        int position = -1;
        for(int pos=0; pos < mfeedDetailList.size(); pos++){
            FeedDetail feedDetail = mfeedDetailList.get(pos);
            if(feedDetail.getAuthorId() == mUserPreference.get().getUserSummary().getUserId()){
                position = pos;
                break;
            }
        }
        if(position >= 0 && position!=RecyclerView.NO_POSITION){
            mRecyclerView.smoothScrollToPosition(position + 1);
        }
    }
}
