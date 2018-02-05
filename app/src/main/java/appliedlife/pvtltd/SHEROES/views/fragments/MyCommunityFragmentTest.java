
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
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.CommunityListingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunityAdapter_new;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityListingView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;

/**
 * Created by ravi on 31-01-2018.
 */

public class MyCommunityFragmentTest extends BaseFragment implements ICommunityListingView, AllCommunityItemCallback {
    private static final String SCREEN_LABEL = "My Communities Screen";
    private final String TAG = LogUtils.makeLogTag(MyCommunityFragmentTest.class);

    @Inject
    CommunityListingPresenter mCommunityListingPresenter;

   // @Bind(R.id.nested_scroll)
   // NestedScrollView nestedScrollView;

    @Bind(R.id.my_communities_label)
    TextView myCommunityLabel;

    @Bind(R.id.my_communities)
    RecyclerView mMyCommunitiesListView;

    @Bind(R.id.all_communities)
    RecyclerView mAllCommunityListView;

    @Inject
    AppUtils mAppUtils;

    LinearLayoutManager mLayoutManager;
    MyCommunityAdapter_new mMyCommunityAdapter;
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

        mMyCommunityAdapter = new MyCommunityAdapter_new(getContext(), (HomeActivity) getActivity());
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


        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        LogUtils.info(TAG, "**********New Communities fragment on create*********");

        mCommunityListingPresenter.fetchMyCommunity(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mCommunityListingPresenter.fetchAllCommunity();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = null;
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        mMyCommunityAdapter.setData(feedDetailList);

        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<FeedDetail> data = null;
            FeedDetail feedProgressBar = new FeedDetail();
            feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();

            mMyCommunityAdapter.setData(data);
            if (mPageNo == AppConstants.TWO_CONSTANT) {
                mMyCommunityAdapter.notifyDataSetChanged();
            } else {
                mMyCommunityAdapter.notifyItemRangeChanged(position + 1, feedDetailList.size());
            }
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
        } else {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            if (data.size() > 0) {
                mMyCommunityAdapter.setData(data);
                mMyCommunityAdapter.notifyDataSetChanged();
            }
        }
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
    public void showMyCommunity(List<FeedDetail> feedDetails) {
        mMyCommunityAdapter.setData(feedDetails);
        mMyCommunityAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCommunity(ArrayList<FeedDetail> feedDetails) {
        mFeedAdapter.setData(feedDetails);
        mFeedAdapter.notifyDataSetChanged();
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

    public void updateItem(FeedDetail feedDetail) {
        int position = findPositionById(feedDetail.getIdOfEntityOrParticipant());
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        mFeedAdapter.setData(position, feedDetail);
    }

    public int findPositionById(long id) {
        if (mFeedAdapter == null) {
            return -1;
        }
        List<FeedDetail> feedDetails = mFeedAdapter.getDataList();

        if (CommonUtil.isEmpty(feedDetails)) {
            return -1;
        }

        for (int i = 0; i < feedDetails.size(); ++i) {
            FeedDetail feedDetail = feedDetails.get(i);
            if (feedDetail != null && feedDetail.getIdOfEntityOrParticipant() == id) {
                return i;
            }
        }
        return -1;
    }
}

