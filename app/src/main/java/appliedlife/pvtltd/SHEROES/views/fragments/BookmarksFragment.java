package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 21-02-2017.
 */

public class BookmarksFragment extends BaseFragment {
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
    private FragmentIntractionWithActivityListner mHomeActivityFragmentIntractionWithActivityListner;
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof FragmentIntractionWithActivityListner) {
                mHomeActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData=new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.BOOKMARKS,AppConstants.EMPTY_STRING);
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
                if (null != ((HomeActivity) getActivity()).mArticlePopUp) {
                    ((HomeActivity) getActivity()).mArticlePopUp.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    ((HomeActivity) getActivity()).mArticlePopUp.clearAnimation();
                    animation.setFillAfter(false);
                }
                if (null != ((HomeActivity) getActivity()).mCommunityPopUp) {
                    ((HomeActivity) getActivity()).mCommunityPopUp.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    ((HomeActivity) getActivity()).mCommunityPopUp.clearAnimation();
                    animation.setFillAfter(false);
                }
                if (null != ((HomeActivity) getActivity()).mCommunityPostPopUp) {
                    ((HomeActivity) getActivity()).mCommunityPostPopUp.setVisibility(View.GONE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    ((HomeActivity) getActivity()).mCommunityPostPopUp.clearAnimation();
                    animation.setFillAfter(false);
                }
            }
        });
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mPullRefreshList, mAdapter, mLayoutManager, mPageNo, mSwipeView, mLiNoResult, mFeedDetail, mRecyclerView, mPosition, mPressedEmoji, mListLoad, mIsEdit, mHomePresenter, mAppUtils, mProgressBar);
        mHomePresenter.getBookMarkFromPresenter(mAppUtils.getBookMarks(mFragmentListRefreshData.getPageNo()));
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                mPullRefreshList.setPullToRefresh(true);
               mHomePresenter.getBookMarkFromPresenter(mAppUtils.getBookMarks(mFragmentListRefreshData.getPageNo()));
            }
        });
        return view;
    }
    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
      super.getFeedListSuccess(feedDetailList);
    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {
    super.getSuccessForAllResponse(success,successFrom);
    }

    @Override
    public void showError(String errorMsg) {
        mHomeActivityFragmentIntractionWithActivityListner.onShowErrorDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
       super.bookMarkForCard(feedDetail);
    }

    public void editDeleteRecentComment(FeedDetail feedDetail, boolean isEdit) {
        super.editDeleteRecentComment(feedDetail,isEdit);
    }

    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
       super.likeAndUnlikeRequest(baseResponse,reactionValue,position);
    }


    public void commentListRefresh(FeedDetail feedDetail) {
     super.commentListRefresh(feedDetail);
    }
}
