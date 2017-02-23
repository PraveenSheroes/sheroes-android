package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 19-02-2017.
 */

public class JobFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(HomeFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout swipeView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private HomeActivityIntractionListner mHomeActivityIntractionListner;
    private SwipPullRefreshList mPullRefreshList;
    private AppUtils mAppUtils;
    FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    int position;
    int pressedEmoji;
    boolean listLoad = true;

    public static HomeFragment createInstance(int itemsCount) {
        HomeFragment partThreeFragment = new HomeFragment();
        // Bundle bundle = new Bundle();
        //  bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        //   partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityIntractionListner) {
                mHomeActivityIntractionListner = (HomeActivityIntractionListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.jobfragment, container, false);
        //   forceCrash();
        // TODO: Move this to where you establish a user session
        // logUser();

        //  GoogleAnalyticsTracing.screenNameTracking(getActivity(),SCREEN_NAME);

        ButterKnife.bind(this, view);
        mAppUtils = AppUtils.getInstance();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.JOB_FRAGMENT, AppConstants.EMPTY_STRING);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);

        //  mHomePresenter.saveMasterDataTypes();

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                listLoad = true;
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
                listLoad = true;
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
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_JOB, mFragmentListRefreshData.getPageNo()));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listLoad = true;
                LogUtils.info("swipe", "*****************end called");
                mPullRefreshList.setPullToRefresh(true);
                mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_JOB, mFragmentListRefreshData.getPageNo() + 1));
            }
        });
        return view;
    }
    @OnClick(R.id.fab_filter)
    public void clickFilter()
    {
        mHomeActivityIntractionListner.openFilter();
    }
    public void bookMarkForCard(FeedDetail feedDetail) {
        listLoad = false;
        mHomePresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(feedDetail.getEntityOrParticipantId()), feedDetail.isBookmarked());
    }

    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        listLoad = false;
        mFeedDetail = (FeedDetail) baseResponse;
        this.position = position;
        this.pressedEmoji = reactionValue;
        if (null != mFeedDetail && mFeedDetail.isLongPress()) {
            mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), reactionValue));
        } else {
            if (reactionValue == AppConstants.NO_REACTION_CONSTANT) {
                mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(mFeedDetail.getEntityOrParticipantId()));
            } else {
                mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), reactionValue));
            }
        }
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("ajit@sheroies.in");
        Crashlytics.setUserName("Test User");
    }

    public void forceCrash() {
        throw new RuntimeException(AppConstants.APP_CRASHED);
    }

    private void setCustomAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.setCallForRecycler(AppConstants.FEED_JOB);
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - feedDetailList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
            swipeView.setRefreshing(false);
        }
    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {
        switch (successFrom) {
            case AppConstants.ONE_CONSTANT:
                likeSuccess(success);
                break;
            case AppConstants.TWO_CONSTANT:
                commentSuccess(success);
                break;
            case AppConstants.THREE_CONSTANT:
                bookMarkSuccess(success);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + successFrom);
        }

        LogUtils.info("likes", "*********************like success******" + success);
    }

    private void commentSuccess(String success) {

    }

    private void bookMarkSuccess(String success) {
        if (success.equalsIgnoreCase(AppConstants.SUCCESS)) {
            if (!mFeedDetail.isBookmarked()) {
                mFeedDetail.setBookmarked(true);
            } else {
                mFeedDetail.setBookmarked(false);
            }
            mAdapter.setDataOnPosition(mFeedDetail, position);
        } else {
            mAdapter.setDataOnPosition(mFeedDetail, position);
        }

    }

    private void likeSuccess(String success) {

        if (success.equalsIgnoreCase(AppConstants.SUCCESS)) {

            if (null != mFeedDetail && mFeedDetail.isLongPress()) {
                if (mFeedDetail.getReactionValue() == AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(pressedEmoji);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(pressedEmoji);
                }

            } else {

                if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                }
            }
            mAdapter.setDataOnPosition(mFeedDetail, position);
        }
    }


    @Override
    public void getDB(List<MasterData> masterDatas) {
        for (MasterData master : masterDatas) {
            LogUtils.info("db", "*********************List master******" + master);
        }
        mHomePresenter.fetchMasterDataTypes();
    }




    @Override
    public void startProgressBar() {
        if (listLoad) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public interface HomeActivityIntractionListner {
        void onErrorOccurence();
        void openFilter();

    }

}
