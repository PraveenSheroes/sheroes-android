
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
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
import appliedlife.pvtltd.SHEROES.presenters.CommunityListingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CollectionActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunityAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityListingView;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.removeMemberRequestBuilder;

/**
 * Created by ravi on 31-01-2018.
 *
 */

public class CommunityListFragment extends BaseFragment implements ICommunityListingView, AllCommunityItemCallback {
    private static final String SCREEN_LABEL = "Communities List Screen";
    private final String TAG = LogUtils.makeLogTag(CommunityListFragment.class);

    @Inject
    CommunityListingPresenter mCommunityListingPresenter;

    @Bind(R.id.my_communities_label)
    TextView myCommunityLabel;

    @Bind(R.id.my_communities)
    RecyclerView mMyCommunitiesListView;

    @Bind(R.id.all_communities)
    RecyclerView mAllCommunityListView;

    @Bind(R.id.progress_bar_community)
    ProgressBar progressBar;

    @Inject
    AppUtils mAppUtils;

    @Inject
    Preference<LoginResponse> userPreference;

    LinearLayoutManager mLayoutManager;
    MyCommunityAdapter mMyCommunityAdapter;
    FeedAdapter mFeedAdapter;

    int mPageNo = AppConstants.ONE_CONSTANT;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList mPullRefreshList;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communitues_carousel, container, false);
        ButterKnife.bind(this, view);

        mCommunityListingPresenter.attachView(this);

        mMyCommunityAdapter = new MyCommunityAdapter(getContext(), this);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mMyCommunitiesListView.setAdapter(mMyCommunityAdapter);
        mMyCommunitiesListView.setLayoutManager(mLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAllCommunityListView.setLayoutManager(linearLayoutManager);
        mFeedAdapter = new FeedAdapter(getContext(), this);
        mAllCommunityListView.setAdapter(mFeedAdapter);

        mMyCommunitiesListView.setNestedScrollingEnabled(false);
        mAllCommunityListView.setNestedScrollingEnabled(false);

        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);

        mMyCommunitiesListView.setVisibility(View.GONE);
        myCommunityLabel.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        LogUtils.info(TAG, "**********New Communities fragment on create*********");

        mCommunityListingPresenter.fetchMyCommunity(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mCommunityListingPresenter.fetchAllCommunity();

        mMyCommunitiesListView.addOnScrollListener(new HidingScrollListener(mCommunityListingPresenter, mMyCommunitiesListView, mLayoutManager, mFragmentListRefreshData) {
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
        mCommunityListingPresenter.detachView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }


    @Override
    public void showMyCommunity(FeedResponsePojo myCommunityResponse) {

        if(myCommunityLabel.getVisibility() != View.VISIBLE) {
            mMyCommunitiesListView.setVisibility(View.VISIBLE);
            myCommunityLabel.setVisibility(View.VISIBLE);
        }

        List<FeedDetail> feedDetailList = myCommunityResponse.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mMyCommunityAdapter != null) {
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

            mMyCommunityAdapter.setData(data);
            mMyCommunityAdapter.notifyDataSetChanged();

        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mMyCommunityAdapter != null) {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mMyCommunityAdapter.notifyDataSetChanged();
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

        progressBar.setVisibility(View.GONE);
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
    public void onCommunityClicked(CommunityFeedSolrObj communityFeedObj) {
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedObj, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }

    @Override
    public void joinRequestForOpenCommunity(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add(userPreference.get().getUserSummary().getUserId());
            mCommunityListingPresenter.joinCommunity(AppUtils.communityRequestBuilder(userIdList, communityFeedSolrObj.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY), communityFeedSolrObj, carouselViewHolder);
        }
    }

    @Override
    public void unJoinCommunity(CommunityFeedSolrObj communityFeedSolrObj, CarouselViewHolder carouselViewHolder) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            mCommunityListingPresenter.leaveCommunity(removeMemberRequestBuilder(communityFeedSolrObj.getIdOfEntityOrParticipant(), userPreference.get().getUserSummary().getUserId()), communityFeedSolrObj, carouselViewHolder);
        }
    }

    @Override
    public void onSeeMoreClicked(CarouselDataObj carouselDataObj) {
        if(carouselDataObj!= null && carouselDataObj.getEndPointUrl()!=null) {
            CollectionActivity.navigateTo(getActivity(), carouselDataObj.getEndPointUrl(), carouselDataObj.getScreenTitle(), SCREEN_LABEL, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
        } else{
            LogUtils.info(TAG, "End Point Url is Null");
        }
    }

    @Override
    public void openChampionListingScreen(CarouselDataObj carouselDataObj) {

    }
}

