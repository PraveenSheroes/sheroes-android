
package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.CommunitiesListPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.EndlessNestedScrollViewListener;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CollectionActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunitiesAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunitiesListView;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.removeMemberRequestBuilder;

/**
 * Created by ravi on 31-01-2018.
 */

public class CommunitiesListFragment extends BaseFragment implements ICommunitiesListView, AllCommunityItemCallback {

    //region private variables and constants
    private static final String SCREEN_LABEL = "Communities Screen";
    private final String TAG = LogUtils.makeLogTag(CommunitiesListFragment.class);
    private static final int MAX_COMMUNITIES_LIMIT = 6;
    private MyCommunitiesAdapter mMyCommunitiesAdapter;
    private FeedAdapter mFeedAdapter;
    int mPageNo = AppConstants.ONE_CONSTANT;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList mPullRefreshList;
    private boolean showMyCommunities = true;
    private EndlessNestedScrollViewListener mEndlessRecyclerViewScrollListener;
    String mSearchText, mSearchCategory;
    private FragmentOpen fragmentOpen;
    //endregion

    //region Bind view variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    CommunitiesListPresenter mCommunitiesListPresenter;

    @Bind(R.id.container)
    LinearLayout communitiesContainer;

    @Bind(R.id.title)
    TextView mMyCommunitiesLabel;

    @Bind(R.id.my_communities)
    RecyclerView mMyCommunitiesListView;

    @Bind(R.id.all_communities)
    RecyclerView mAllCommunitiesListView;

    @Bind(R.id.loader_gif)
    CardView loaderGif;

    @Bind(R.id.no_internet)
    CardView noInternet;

    @Bind(R.id.tv_goto_setting)
    TextView tvGoToSetting;

    @Bind(R.id.community_card_view)
    CardView myCommunityCardView;

    @Bind(R.id.rl_empty)
    RelativeLayout emptyLayout;

    @Bind(R.id.tv_no_results_title)TextView noResultsTitleTxt;

    @Bind(R.id.tv_no_results_subtitle)TextView noResultsSubTitleTxt;

    @Bind(R.id.iv_image)ImageView noResultsImage;

    @Bind(R.id.nested_scroll)
    NestedScrollView nestedScrollView;

    @Inject
    AppUtils mAppUtils;

    @Inject
    Preference<LoginResponse> userPreference;
    //endregion
    private String mScreenLabel;
    private boolean isActiveTabFragment;

    //region Fragment lifecycle method
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communitues_carousel, container, false);
        ButterKnife.bind(this, view);

        mCommunitiesListPresenter.attachView(this);
        fragmentOpen = new FragmentOpen();

        if(getArguments() != null){
            mScreenLabel = getArguments().getString(AppConstants.SCREEN_NAME);
        }

        mMyCommunitiesAdapter = new MyCommunitiesAdapter(getContext(), this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mMyCommunitiesListView.setAdapter(mMyCommunitiesAdapter);
        mMyCommunitiesListView.setLayoutManager(mLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mAllCommunitiesListView.setLayoutManager(linearLayoutManager);
        mFeedAdapter = new FeedAdapter(getContext(), this, true);
        mAllCommunitiesListView.setAdapter(mFeedAdapter);

        mMyCommunitiesListView.setNestedScrollingEnabled(false);
        mAllCommunitiesListView.setNestedScrollingEnabled(false);

        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);

        mMyCommunitiesListView.setVisibility(View.GONE);
        mMyCommunitiesLabel.setVisibility(View.GONE);

        communitiesContainer.setVisibility(View.GONE);
        loaderGif.setVisibility(View.VISIBLE);

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
//        mCommunitiesListPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));

        if (showMyCommunities)
            callCommunityApi();
        else
            filterCommunities(showMyCommunities, mSearchText, mSearchCategory);

        mEndlessRecyclerViewScrollListener = new EndlessNestedScrollViewListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (mCommunitiesListPresenter.isCommunityFeedLoading() || mCommunitiesListPresenter.getCommunityFeedEnded() || showMyCommunities) {
                    return;
                }

                mAllCommunitiesListView.post(new Runnable() {
                    public void run() {
                        if (!showMyCommunities)
                            mFeedAdapter.feedStartedLoading();
                    }
                });

                if (!showMyCommunities) {
                    mCommunitiesListPresenter.fetchSearchedCommunity(mSearchText, mSearchCategory);
                }
            }
        };
        nestedScrollView.setOnScrollChangeListener(mEndlessRecyclerViewScrollListener);

        mMyCommunitiesListView.addOnScrollListener(new HidingScrollListener(mCommunitiesListPresenter, mMyCommunitiesListView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {

            }

            @Override
            public void onShow() {
            }

            @Override
            public void dismissReactions() {
            }
        });

        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).communityButton();
        }
        String underLineData = getString(R.string.setting);
        SpannableString content = new SpannableString(underLineData);
        content.setSpan(new UnderlineSpan(), 0, underLineData.length(), 0);
        tvGoToSetting.setText(content);

//        trackScreenEvent();

        return view;
    }

    public void callCommunityApi(){
        mCommunitiesListPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        emptyLayout.setVisibility(View.GONE);
        loaderGif.setVisibility(View.VISIBLE);
        this.showMyCommunities = true;
        mCommunitiesListPresenter.fetchAllCommunities();
        mCommunitiesListPresenter.resetState();
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = null;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mCommunitiesListPresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCommunitiesListPresenter.detachView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
    //endregion

    //region public method
    @Override
    public void showMyCommunities(FeedResponsePojo myCommunityResponse) {

        if (mMyCommunitiesLabel.getVisibility() != View.VISIBLE) {
            mMyCommunitiesListView.setVisibility(View.VISIBLE);
            mMyCommunitiesLabel.setVisibility(View.VISIBLE);
        }

        List<FeedDetail> feedDetailList = myCommunityResponse.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mMyCommunitiesAdapter != null) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);

            List<FeedDetail> data = null;
            FeedDetail feedProgressBar = new FeedDetail();
            feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            if (position > 0) {
                data.remove(position - 1);
            }
            data.add(feedProgressBar);

            mMyCommunitiesAdapter.setData(data);

        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mMyCommunitiesAdapter != null) {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
        }
        mMyCommunitiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCommunity(CommunityFeedSolrObj communityFeedSolrObj) {
    }

    @Override
    public void stopProgressBar() {
        mEndlessRecyclerViewScrollListener.finishLoading();
        mFeedAdapter.feedFinishedLoading();
    }

    @Override
    public void onCommunityJoined(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        carouselViewHolder.mAdapter.setData(communityFeedSolrObj);
    }

    @Override
    public void invalidateCommunityJoin(CommunityFeedSolrObj communityFeedSolrObj) {
        findPositionAndUpdateItem(communityFeedSolrObj, communityFeedSolrObj.getIdOfEntityOrParticipant());
        mFeedAdapter.setData(mFeedAdapter.getDataList());
    }

    @Override
    public void invalidateCommunityLeft(CommunityFeedSolrObj communityFeedSolrObj) {
        findPositionAndUpdateItem(communityFeedSolrObj, communityFeedSolrObj.getIdOfEntityOrParticipant());
        mFeedAdapter.setData(mFeedAdapter.getDataList());
    }

    @Override
    public void onCommunityLeft(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        carouselViewHolder.mAdapter.setData(communityFeedSolrObj);
    }

    @Override
    public void showAllCommunity(ArrayList<FeedDetail> feedDetails) {
        mFeedAdapter.feedFinishedLoading();
        emptyLayout.setVisibility(View.GONE);
        mFeedAdapter.setData(feedDetails);
        mFeedAdapter.notifyDataSetChanged();
        loaderGif.setVisibility(View.GONE);
        communitiesContainer.setVisibility(View.VISIBLE);

        if (showMyCommunities) {
            mMyCommunitiesListView.setVisibility(View.VISIBLE);
            mMyCommunitiesLabel.setVisibility(View.VISIBLE);
        } else {
            mMyCommunitiesListView.setVisibility(View.GONE);
            mMyCommunitiesLabel.setVisibility(View.GONE);
        }
    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {
    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    @Override
    public void onMyCommunityClicked(CommunityFeedSolrObj communityFeedObj) {
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedObj, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }

    @Override
    public void onCommunityClicked(CommunityFeedSolrObj communityFeedObj) {
        HashMap<String, Object> screenProperties = new HashMap<>();
        screenProperties.put(EventProperty.POSITION_IN_LIST.toString(), Integer.toString(communityFeedObj.getItemPosition()));
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedObj.getIdOfEntityOrParticipant(), getScreenName(), screenProperties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }

    @Override
    public void onCommunityJoinOrUnjoin(CommunityFeedSolrObj mCommunityFeedObj) {
        if (mCommunityFeedObj.isMember()) {
            mCommunityFeedObj.setMember(false);
            mCommunityFeedObj.setNoOfMembers(mCommunityFeedObj.getNoOfMembers() - 1);
            mCommunityFeedObj.setSearchText(SearchFragment.searchText);
            AnalyticsManager.trackCommunityAction(Event.COMMUNITY_LEFT, mCommunityFeedObj, getScreenName());

            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                mCommunitiesListPresenter.leaveCommunity(removeMemberRequestBuilder(mCommunityFeedObj.getIdOfEntityOrParticipant(), mUserPreference.get().getUserSummary().getUserId()), mCommunityFeedObj);
            }
        } else {
            mCommunityFeedObj.setMember(true);
            mCommunityFeedObj.setNoOfMembers(mCommunityFeedObj.getNoOfMembers() + 1);
            mCommunityFeedObj.setSearchText(SearchFragment.searchText);
            AnalyticsManager.trackCommunityAction(Event.COMMUNITY_JOINED, mCommunityFeedObj, getScreenName());

            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                List<Long> userIdList = new ArrayList();
                userIdList.add(mUserPreference.get().getUserSummary().getUserId());
                mCommunitiesListPresenter.joinCommunity(AppUtils.communityRequestBuilder(userIdList, mCommunityFeedObj.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY), mCommunityFeedObj);
            }
        }
    }

    public void findPositionAndUpdateItem(FeedDetail updatedFeedDetail, long id) { //TODO - move to presenter
        if (mFeedAdapter == null) {
            return;
        }
        List<FeedDetail> feedDetails = mFeedAdapter.getDataList();

        if (CommonUtil.isEmpty(feedDetails)) {
            return;
        }

        for (int i = 0; i < feedDetails.size(); ++i) {
            FeedDetail feedDetail = feedDetails.get(i);
            if (feedDetail != null && feedDetail.getIdOfEntityOrParticipant() == id) {
                mFeedAdapter.setData(i, updatedFeedDetail);
            } else if (feedDetail instanceof CarouselDataObj) {
                for (int j = 0; j < ((CarouselDataObj) feedDetail).getFeedDetails().size(); j++) {
                    FeedDetail innerFeedDetail = ((CarouselDataObj) feedDetail).getFeedDetails().get(j);
                    if (innerFeedDetail != null && innerFeedDetail.getIdOfEntityOrParticipant() == id) {
                        if (updatedFeedDetail instanceof UserSolrObj && innerFeedDetail instanceof UserSolrObj) { //Since community name not available in user solr object
                            UserSolrObj updatedUserSolrObj = (UserSolrObj) updatedFeedDetail;
                            if (!StringUtil.isNotNullOrEmptyString(updatedUserSolrObj.getmSolarIgnoreCommunityName())) {
                                UserSolrObj userSolrObj = (UserSolrObj) innerFeedDetail;
                                updatedUserSolrObj.setmSolarIgnoreCommunityName(userSolrObj.getmSolarIgnoreCommunityName());
                                updatedFeedDetail = updatedUserSolrObj;
                            }
                        }
                        ((CarouselDataObj) feedDetail).getFeedDetails().set(j, updatedFeedDetail);
                        mFeedAdapter.setData(i, feedDetail);
                    }
                }
            } else if (feedDetail != null && feedDetail instanceof LeaderBoardUserSolrObj) {
                if (((LeaderBoardUserSolrObj) feedDetail).getUserSolrObj() != null) {
                    mFeedAdapter.setData(i, feedDetail);
                }
            } else if (feedDetail != null && feedDetail instanceof UserPostSolrObj && updatedFeedDetail instanceof UserSolrObj && feedDetail.getAuthorId() == id) {
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) feedDetail;
                UserSolrObj userSolrObj = (UserSolrObj) updatedFeedDetail;
                userPostSolrObj.setSolrIgnoreIsUserFollowed(userSolrObj.isSolrIgnoreIsUserFollowed());
                mFeedAdapter.setData(i, userPostSolrObj);
            }
        }
        return;
    }

    @Override
    public void joinRequestForOpenCommunity(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add(userPreference.get().getUserSummary().getUserId());
            //JOin Click get position
            String position[] = getCommunityPositionInCarousel(communityFeedSolrObj);
            if (position != null)
                AnalyticsManager.trackCommunityAction(Event.COMMUNITY_JOINED, communityFeedSolrObj, getScreenName(), position[0], position[1]);
            mCommunitiesListPresenter.joinCommunity(AppUtils.communityRequestBuilder(userIdList, communityFeedSolrObj.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY), communityFeedSolrObj, carouselViewHolder);
        }
    }

    @Override
    public void unJoinCommunity(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            //Leave Click get position
            String position[] = getCommunityPositionInCarousel(communityFeedSolrObj);
            if (position != null)
                AnalyticsManager.trackCommunityAction(Event.COMMUNITY_LEFT, communityFeedSolrObj, getScreenName(), position[0], position[1]);
            mCommunitiesListPresenter.communityLeft(removeMemberRequestBuilder(communityFeedSolrObj.getIdOfEntityOrParticipant(), userPreference.get().getUserSummary().getUserId()), communityFeedSolrObj, carouselViewHolder);
        }
    }

    @Override
    public void onSeeMoreClicked(CarouselDataObj carouselDataObj) {
        if (carouselDataObj != null && carouselDataObj.getEndPointUrl() != null) {

            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .name(getString(R.string.ID_CAROUSEL_SEE_MORE))
                            .communityCategory(carouselDataObj.getScreenTitle())
                            .build();

            CollectionActivity.navigateTo(getActivity(), carouselDataObj.getEndPointUrl(), carouselDataObj.getScreenTitle(), SCREEN_LABEL, getString(R.string.ID_COMMUNITIES_CATEGORY), properties, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
        } else {
            LogUtils.info(TAG, "End Point Url is Null");
        }
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    public void refreshList() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        mCommunitiesListPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mCommunitiesListPresenter.fetchAllCommunities();
    }

    public void setSearchedDeeplinkParameters(boolean showMyCommunities, String searchText, String searchCategory) {
        this.showMyCommunities = showMyCommunities;
        mSearchText = searchText;
        mSearchCategory = searchCategory;
    }

    public void filterCommunities(boolean showMyCommunities, String searchText, String searchCategory) {
        emptyLayout.setVisibility(View.GONE);
        loaderGif.setVisibility(View.VISIBLE);
        this.showMyCommunities = showMyCommunities;
        mSearchText = searchText;
        mSearchCategory = searchCategory;
//        mMyCommunitiesListView.setVisibility(View.GONE);
        mCommunitiesListPresenter.setHasFeedEnded(false);
        mCommunitiesListPresenter.resetState();
        mCommunitiesListPresenter.fetchSearchedCommunity(searchText, searchCategory);
    }

    public String[] getCommunityPositionInCarousel(FeedDetail updatedFeedDetail) {
        String position[] = new String[2];
        if (mFeedAdapter == null) {
            return null;
        }
        List<FeedDetail> feedDetails = mFeedAdapter.getDataList();

        if (CommonUtil.isEmpty(feedDetails)) {
            return null;
        }

        for (int i = 0; i < feedDetails.size(); i++) {
            FeedDetail feedDetail = feedDetails.get(i);
            if (feedDetail instanceof CarouselDataObj) {
                int size = ((CarouselDataObj) feedDetail).getFeedDetails().size();
                for (int j = 0; j < size; ++j) {
                    FeedDetail innerFeedDetail = ((CarouselDataObj) feedDetail).getFeedDetails().get(j);
                    if (innerFeedDetail.getIdOfEntityOrParticipant() == updatedFeedDetail.getIdOfEntityOrParticipant()) {
                        LogUtils.info(TAG, "Row :" + i + "Seq" + j + "::" + innerFeedDetail.getNameOrTitle());
                        position[0] = String.valueOf(i);
                        position[1] = String.valueOf(j);
                    }
                }
            }
        }
        return position;
    } //todo - move to presenter

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorMsg) && errorMsg.equalsIgnoreCase(AppConstants.CHECK_NETWORK_CONNECTION)) {
            noInternet.setVisibility(View.VISIBLE);
            communitiesContainer.setVisibility(View.GONE);
            loaderGif.setVisibility(View.GONE);
        } else {
            super.showError(errorMsg, feedParticipationEnum);
        }

    }

    @Override
    public void showEmptyScreen(String s) {
        loaderGif.setVisibility(View.GONE);
        communitiesContainer.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        noResultsSubTitleTxt.setText(s);
        noResultsTitleTxt.setText(getString(R.string.empty_communities));
        noResultsImage.setImageResource(R.drawable.communities_vector_layout);
    }

    @OnClick({R.id.tv_retry_for_internet})
    public void onRetryClick() {
        noInternet.setVisibility(View.GONE);
        communitiesContainer.setVisibility(View.VISIBLE);
        loaderGif.setVisibility(View.VISIBLE);
        if (null != getActivity() && getActivity() instanceof HomeActivity) {
            if(fragmentOpen.isFeedFragment())
            ((HomeActivity) getActivity()).communityOnClick();
            else
                callCommunityApi();
        }
    }

    @OnClick({R.id.tv_goto_setting})
    public void onSettingClick() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void onConfigFetched() {

    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {  //When UI is visible to user

            isActiveTabFragment = true;

            if (getParentFragment() instanceof SearchFragment) {
                String screenName = ((SearchFragment) getParentFragment()).getInactiveTabFragmentName();
                if (mScreenLabel != null && screenName != null && !mScreenLabel.equalsIgnoreCase(screenName)) {
                    //Send event of previous selected tab with duration, and start the time capture for current selected tab
                    AnalyticsManager.trackScreenView(screenName, getExtraProperties());
                    AnalyticsManager.timeScreenView(mScreenLabel);
                }
            }
        } else { //When UI is not visible to user

            //Capture the screen event of the tab got unselected
            if (isActiveTabFragment && mScreenLabel != null && !(getActivity() instanceof HomeActivity)) {
                AnalyticsManager.trackScreenView(mScreenLabel, getExtraProperties());
            }

            isActiveTabFragment = false;
        }
    }
    //endregion

    public void trackScreenEvent(String searchQuery){
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .source(AppConstants.PREVIOUS_SCREEN)
                        .searchQuery(searchQuery)
                        .tabTitle(SearchFragment.searchTabName)
                        .build();

        AnalyticsManager.trackScreenView(SCREEN_LABEL, properties);
    }
}

