
package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.CommunitiesListPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CollectionActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunitiesAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunitiesListView;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

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
    //endregion

    //region Bind view variables
    @Inject
    CommunitiesListPresenter mCommunitiesListPresenter;

    @Bind(R.id.title)
    TextView mMyCommunitiesLabel;

    @Bind(R.id.my_communities)
    RecyclerView mMyCommunitiesListView;

    @Bind(R.id.all_communities)
    RecyclerView mAllCommunitiesListView;

    @Bind(R.id.loader_gif)
    CardView loaderGif;

    @Inject
    AppUtils mAppUtils;

    @Inject
    Preference<LoginResponse> userPreference;
    //endregion

    //region Fragment lifecycle method
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communitues_carousel, container, false);
        ButterKnife.bind(this, view);

        mCommunitiesListPresenter.attachView(this);

        mMyCommunitiesAdapter = new MyCommunitiesAdapter(getContext(), this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mMyCommunitiesListView.setAdapter(mMyCommunitiesAdapter);
        mMyCommunitiesListView.setLayoutManager(mLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAllCommunitiesListView.setLayoutManager(linearLayoutManager);
        mFeedAdapter = new FeedAdapter(getContext(), this);
        mAllCommunitiesListView.setAdapter(mFeedAdapter);

        mMyCommunitiesListView.setNestedScrollingEnabled(false);
        mAllCommunitiesListView.setNestedScrollingEnabled(false);

        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);

        mMyCommunitiesListView.setVisibility(View.GONE);
        mMyCommunitiesLabel.setVisibility(View.GONE);

        loaderGif.setVisibility(View.VISIBLE);
        loaderGif.bringToFront();

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);

        mCommunitiesListPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mCommunitiesListPresenter.fetchAllCommunities();

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

        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.communities_listing));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = null;
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
            mMyCommunitiesAdapter.notifyDataSetChanged();

        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mMyCommunitiesAdapter != null) {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mMyCommunitiesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCommunityJoinResponse(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        carouselViewHolder.mAdapter.setData(communityFeedSolrObj);
    }

    @Override
    public void showCommunityUnJoinedResponse(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        carouselViewHolder.mAdapter.setData(communityFeedSolrObj);
    }

    @Override
    public void showAllCommunity(ArrayList<FeedDetail> feedDetails) {
        mFeedAdapter.setData(feedDetails);
        mFeedAdapter.notifyDataSetChanged();

        loaderGif.setVisibility(View.GONE);
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

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
    public List getListData() {
        return null;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }

    @Override
    public void onMyCommunityClicked(CommunityFeedSolrObj communityFeedObj) {
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedObj, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_MY_COMMUNITY_DETAIL);
    }

    @Override
    public void onCommunityClicked(CommunityFeedSolrObj communityFeedObj) {
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedObj, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
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
            mCommunitiesListPresenter.leaveCommunity(removeMemberRequestBuilder(communityFeedSolrObj.getIdOfEntityOrParticipant(), userPreference.get().getUserSummary().getUserId()), communityFeedSolrObj, carouselViewHolder);
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
    public void openChampionListingScreen(CarouselDataObj carouselDataObj) {

    }

    //Update on Community Category changes
    public void updateCommunityCarousel(List<FeedDetail> updatedFeedDetail) { //todo - move to presenter
        if (mFeedAdapter == null) {
            return;
        }
        List<FeedDetail> feedDetails = mFeedAdapter.getDataList();
        if (CommonUtil.isEmpty(feedDetails) && CommonUtil.isEmpty(updatedFeedDetail)) {
            return;
        }

        if (updatedFeedDetail.size() > MAX_COMMUNITIES_LIMIT) {
            List<FeedDetail> newList = new ArrayList<>();
            for (int k = 0; k < MAX_COMMUNITIES_LIMIT; k++) {
                newList.add(updatedFeedDetail.get(k));
            }
            updatedFeedDetail = newList;
        }

        CommunityFeedSolrObj community = (CommunityFeedSolrObj) updatedFeedDetail.get(0);
        String type = community.getCommunityType();

        for (int i = 0; i < feedDetails.size(); i++) {
            FeedDetail feedDetail = feedDetails.get(i);
            if (feedDetail instanceof CarouselDataObj) {

                List<FeedDetail> data = ((CarouselDataObj) feedDetail).getFeedDetails();
                int size = data.size() - 1;
                for (int j = 0; j < size; ++j) {

                    CommunityFeedSolrObj innerFeedDetail = (CommunityFeedSolrObj) (data.get(j));
                    String names = innerFeedDetail.getCommunityType();
                    if (type.equalsIgnoreCase(names)) {
                        data = updatedFeedDetail;
                        ((CarouselDataObj) feedDetail).setFeedDetails(data);
                        break;
                    }
                    ((CarouselDataObj) feedDetail).setFeedDetails(data);
                }
            }
            feedDetails.set(i, feedDetail);
        }
        mFeedAdapter.setData(feedDetails);
        mFeedAdapter.notifyDataSetChanged();
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
    //endregion

}
