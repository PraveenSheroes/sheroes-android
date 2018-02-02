package appliedlife.pvtltd.SHEROES.views.fragments;

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

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 21-02-2017.
 */

public class BookmarksFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "BookMarks Screen";
    private final String TAG = LogUtils.makeLogTag(BookmarksFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout mSwipeView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;
    private  int mPageNo=AppConstants.ONE_CONSTANT;
    private FragmentListRefreshData mFragmentListRefreshData;
    private FeedDetail mFeedDetail;
    private int mPosition;
    private int mPressedEmoji;
    private boolean mListLoad = true;
    private boolean mIsEdit = false;
    @Bind(R.id.progress_bar_first_load)
    ProgressBar mProgressBarFirstLoad;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData=new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.BOOKMARKS,AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager,mFragmentListRefreshData) {
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
                if (null != ((HomeActivity) getActivity()).popupWindow) {
                    ((HomeActivity) getActivity()).popupWindow.dismiss();
                }
            }
        });
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mFeedDetail, mRecyclerView, mPosition, mPressedEmoji, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
        mHomePresenter.getBookMarkFromPresenter(AppUtils.getBookMarks(mFragmentListRefreshData.getPageNo()));
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setListLoadFlag(false);
                mPullRefreshList.setPullToRefresh(true);
                mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
                mPullRefreshList=new SwipPullRefreshList();
                setRefreshList(mPullRefreshList);
                mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
                mHomePresenter.getBookMarkFromPresenter(AppUtils.getBookMarks(mFragmentListRefreshData.getPageNo()));
            }
        });
        return view;
    }
    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        mProgressBarFirstLoad.setVisibility(View.GONE);
      super.getFeedListSuccess(feedResponsePojo);
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse,FeedParticipationEnum feedParticipationEnum) {
    super.getSuccessForAllResponse(baseResponse,feedParticipationEnum);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }
    public void markAsSpamCommunityPost(FeedDetail feedDetail) {
        super.markAsSpamCommunityPost(feedDetail);
    }
    public void deleteCommunityPost(FeedDetail feedDetail) {
        super.deleteCommunityPost(feedDetail);
    }
    public void bookMarkForCard(FeedDetail feedDetail, FragmentOpen fragmentOpen) {
        setFragmentData(fragmentOpen);
       super.bookMarkForCard(feedDetail);
    }


    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
       super.likeAndUnlikeRequest(baseResponse,reactionValue,position);
    }


    public void commentListRefresh(FeedDetail feedDetail, FeedParticipationEnum feedParticipationEnum) {
     super.commentListRefresh(feedDetail,feedParticipationEnum);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
