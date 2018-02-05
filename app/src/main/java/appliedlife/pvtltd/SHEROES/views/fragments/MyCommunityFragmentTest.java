
package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.TestCommunityActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunityAdapter_new;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;

/**
 * Created by ravi on 31-01-2018.
 */

public class MyCommunityFragmentTest extends BaseFragment implements HomeView {
    private static final String SCREEN_LABEL = "My Communities Screen";
    private final String TAG = LogUtils.makeLogTag(MyCommunityFragmentTest.class);

    @Inject
    HomePresenter mHomePresenter;

   // @Bind(R.id.nested_scroll)
   // NestedScrollView nestedScrollView;

    @Bind(R.id.my_communities_label)
    TextView myCommunityLabel;

    @Bind(R.id.my_communities)
    RecyclerView mMyCommunities;

    @Bind(R.id.carousel_container)
    FrameLayout mCarouselContainer;

    @Inject
    AppUtils mAppUtils;

    LinearLayoutManager mLayoutManager;
    MyCommunityAdapter_new mAdapter;

    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;

    int mPageNo = AppConstants.ONE_CONSTANT;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList mPullRefreshList;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_communitues_carousel, container, false);
        ButterKnife.bind(this, view);

        mHomePresenter.attachView(this);

        mAdapter = new MyCommunityAdapter_new(getContext(), (HomeActivity) getActivity());
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mMyCommunities.setLayoutManager(mLayoutManager);
        mMyCommunities.setAdapter(mAdapter);

        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);

        // feedAdapter = new FeedAdapter(getContext(), (HomeActivity) getActivity());
        //  mLayoutManager1 = new LinearLayoutManager(getContext());
        //  mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        //  mOtherCommunities.setLayoutManager(mLayoutManager1);
        //  mOtherCommunities.setAdapter(feedAdapter);


        Intent intent = new Intent(getContext(), TestCommunityActivity.class);
        startActivity(intent);

       // FeedFragment feedFragment = new FeedFragment();
       // Bundle bundle = new Bundle();
       // bundle.putString(FeedFragment.END_POINT_URL , "participant/feed/community_category_home");
       // feedFragment.setArguments(bundle);
       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.carousel_container, feedFragment, MyCommunityFragmentTest.class.getName()).commitAllowingStateLoss();

        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
     /*   mOtherCommunities.addOnScrollListener(new HidingScrollListener(mHomePresenter, mMyCommunities, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
                //mMyCommunities.setVisibility(View.GONE);
                //myCommunityLabel.setVisibility(View.GONE);
            }

            @Override
            public void onShow() {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
                //mMyCommunities.setVisibility(View.VISIBLE);
                //myCommunityLabel.setVisibility(View.GONE);
            }

            @Override
            public void dismissReactions() {

            }
        });*/
        //super.setAllInitializationForFeeds(mFragmentListRefreshData, mAdapter, mLayoutManager, mMyCommunities, mHomePresenter, mAppUtils, mProgressBar);
        LogUtils.info(TAG, "**********New Communities fragment on create*********");

        mHomePresenter.getMyCommunityFromPresenter(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));

       /* loaderGif.setVisibility(View.VISIBLE);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime=System.currentTimeMillis();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MY_COMMUNITIES_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {

            }
        });
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mAdapter, mLayoutManager, mRecyclerView, mHomePresenter, mAppUtils, mProgressBar);
        LogUtils.info(TAG, "**********Mycommunities fragment on create*********");
        mHomePresenter.getMyCommunityFromPresenter(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        ((HomeActivity)getActivity()).communityButton();
        long timeSpent=System.currentTimeMillis()-startedTime;
       // moEngageUtills.entityMoEngageMyCommunity(getActivity(),mMoEHelper,payloadBuilder,timeSpent);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_COMMUNITY_LISTING_MY_COMMUNITIES));
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeAndRefreshList();
            }
        });*/

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
        mAdapter.setData(feedDetailList);
        // feedAdapter.setData(feedDetailList);
        // mProgressBarFirstLoad.setVisibility(View.GONE);
        // loaderGif.setVisibility(View.GONE);

       /* List<FeedDetail> feedDetails = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CarouselDataObj dataObj = new CarouselDataObj();
            dataObj.setMentorParticipantModel(feedDetailList);
            feedDetails.add(dataObj);
        }*/

       // FeedFragment feedFragment = new FeedFragment();
       // ..
        //feedFragment.showFeedList(feedDetails);
       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.carousel_container, feedFragment, MyCommunityFragmentTest.class.getName()).commitAllowingStateLoss();


        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<FeedDetail> data = null;
            FeedDetail feedProgressBar = new FeedDetail();
            feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            //  if(position>0) {
            //      data.remove(position-1);
            //  }
            //  data.add(feedProgressBar);

            mAdapter.setData(data);
            if (mPageNo == AppConstants.TWO_CONSTANT) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemRangeChanged(position + 1, feedDetailList.size());
            }

           /* feedAdapter.setData(data);
            if (mPageNo == AppConstants.TWO_CONSTANT) {
                feedAdapter.notifyDataSetChanged();
            }else
            {
                feedAdapter.notifyItemRangeChanged(position+1, feedDetailList.size());
            }*/

        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            // mLiNoResult.setVisibility(View.VISIBLE);
        } else {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            if (data.size() > 0) {
                // data.remove(data.size() - 1);
                mAdapter.setData(data);
                mAdapter.notifyDataSetChanged();

                //feedAdapter.setData(feedDetailList);
                //feedAdapter.notifyDataSetChanged();
            }
        }
        // mSwipeView.setRefreshing(false);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
        //  long timeSpent=System.currentTimeMillis()-startedTime;
        //  moEngageUtills.entityMoEngageMyCommunity(getActivity(),mMoEHelper,payloadBuilder,timeSpent);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}

