package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.BaseFragmentUtil;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ErrorUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.BranchDeepLink;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base fragment for all child fragment.
 * all the common behaviour.
 */
public abstract class BaseFragment extends Fragment implements EventInterface, HomeView {
    //region constant variables
    private final String TAG = LogUtils.makeLogTag(BaseFragment.class);
    //endregion

    //region member variables
    public FragmentActivity mActivity;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList mPullRefreshList;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeView;
    private FeedDetail mFeedDetail;
    private RecyclerView mRecyclerView;
    private int mPosition;
    private int mPressedEmoji;
    private boolean mListLoad = true;
    private boolean mIsEdit;
    private HomePresenter mHomePresenter;
    private AppUtils mAppUtils;
    private ProgressBar mProgressBar;
    public FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;
    private FragmentOpen mFragmentOpen = new FragmentOpen();
    //endregion

    //region injected variables
    @Inject
    BaseFragmentUtil mBaseFragmentUtil;
    @Inject
    ErrorUtil mErrorUtil;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LocaleManager.setLocale(getContext());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    public void setAllInitializationForFeeds(FragmentListRefreshData mFragmentListRefreshData, SwipPullRefreshList mPullRefreshList, GenericRecyclerViewAdapter mAdapter, LinearLayoutManager mLayoutManager, int mPageNo, SwipeRefreshLayout swipeView, LinearLayout liNoResult, FeedDetail mFeedDetail, RecyclerView mRecyclerView, int mPosition, int mPressedEmoji, boolean mListLoad, boolean mIsEdit, HomePresenter mHomePresenter, AppUtils mAppUtils, ProgressBar mProgressBar) {
        this.mFragmentListRefreshData = mFragmentListRefreshData;
        this.mPullRefreshList = mPullRefreshList;
        this.mAdapter = mAdapter;
        this.mLayoutManager = mLayoutManager;
        int mPageNo1 = mPageNo;
        this.mSwipeView = swipeView;
        LinearLayout mLiNoResult = liNoResult;
        this.mFeedDetail = mFeedDetail;
        this.mRecyclerView = mRecyclerView;
        this.mPosition = mPosition;
        this.mPressedEmoji = mPressedEmoji;
        this.mListLoad = mListLoad;
        this.mIsEdit = mIsEdit;
        this.mHomePresenter = mHomePresenter;
        this.mAppUtils = mAppUtils;
        this.mProgressBar = mProgressBar;
    }

    public void setListLoadFlag(boolean mListLoad) {
        this.mListLoad = mListLoad;
    }

    public void setFeedDetail(FeedDetail feedDetail) {
        this.mFeedDetail = feedDetail;
    }

    public void setRefreshList(SwipPullRefreshList mPullRefreshList) {
        this.mPullRefreshList = mPullRefreshList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPresenter() != null) {
            getPresenter().onCreate();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            mActivity = getActivity();
        }
        try {
            if (mActivity instanceof FragmentIntractionWithActivityListner) {
                mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
        if (getPresenter() != null) {
            getPresenter().onAttach();
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case LIKE_UNLIKE:
                mBaseFragmentUtil.likeSuccess(getActivity(), baseResponse, mFeedDetail, mAdapter, mSwipeView, getScreenName(), mPressedEmoji);
                break;
            case COMMENT_REACTION:
                mBaseFragmentUtil.recentCommentEditDelete(getActivity(), mSwipeView, getScreenName(), mFeedDetail, baseResponse, mAdapter);
                break;
            case BOOKMARK_UNBOOKMARK:
                mBaseFragmentUtil.bookMarkSuccess(getActivity(), baseResponse, mFeedDetail, mAdapter, mSwipeView, getScreenName());
                break;
            case JOIN_INVITE:
                mBaseFragmentUtil.joinInviteResponse(getActivity(), mAdapter, mLayoutManager, mFeedDetail, getScreenName(), baseResponse);
                break;
            case DELETE_COMMUNITY_POST:
                mBaseFragmentUtil.deleteCommunityPostRespose(mAdapter, mLayoutManager, getActivity(), mFeedDetail, getScreenName(), baseResponse);
                break;
            case MARK_AS_SPAM:
                mBaseFragmentUtil.reportAsSpamPostRespose(getContext(), mFeedDetail, getScreenName(), baseResponse, mAdapter, mLayoutManager);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    public void markAsSpamCommunityPost(FeedDetail feedDetail) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mHomePresenter.markAsSpamFromPresenter(mAppUtils.bookMarkRequestBuilder(feedDetail.getEntityOrParticipantId()));
    }

    public void deleteCommunityPost(FeedDetail feedDetail) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mHomePresenter.deleteCommunityPostFromPresenter(AppUtils.deleteCommunityPostRequest(feedDetail.getIdOfEntityOrParticipant()));
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        mListLoad = false;
        mFeedDetail = feedDetail;
        mHomePresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(feedDetail.getEntityOrParticipantId()), feedDetail.isBookmarked());
    }

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        mBaseFragmentUtil.showError(mSwipeView, errorMsg, feedParticipationEnum);
        mErrorUtil.onShowErrorDialog(getActivity(), errorMsg, feedParticipationEnum);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (trackScreenTime()) {
            AnalyticsManager.timeScreenView(getScreenName());
        }
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getPresenter() != null) {
            getPresenter().onDetach();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        boolean handled = false;
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW)) {
            if (CommonUtil.isSheoresAppLink(Uri.parse(intent.getDataString()))) {
                intent.setClass(getActivity(), SheroesDeepLinkingActivity.class);
                super.startActivity(intent);
                return;
            }
            if (CommonUtil.isBranchLink(Uri.parse(intent.getDataString()))) {
                intent.setClass(getActivity(), BranchDeepLink.class);
                super.startActivityForResult(intent, BaseActivity.BRANCH_REQUEST_CODE);
                return;
            }
            if (AppUtils.matchesWebsiteURLPattern(intent.getDataString())) {
                Uri url = Uri.parse(intent.getDataString());
                AppUtils.openChromeTab(getActivity(), url);
                handled = true;
            }
        }
        if (!handled) {
            try {
                super.startActivity(intent);
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (shouldTrackScreen()) {
            AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
        }
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    public boolean shouldTrackScreen() {
        return true;
    }

    protected boolean trackScreenTime() {
        return false;
    }

    protected Map<String, Object> getExtraProperties() {
        return null;
    }

    @Override
    public void trackEvent(final Event event, final Map<String, Object> properties) {
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    protected abstract SheroesPresenter getPresenter();

}
