package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 05/12/17.
 */

public class MentorsUserListingActivity extends BaseActivity implements HomeView {
    private static final String SCREEN_LABEL = "Mentors Listing Screen";

    //region binding view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.swipe_mentor_list)
    SwipeRefreshLayout mSwipeView;

    @Bind(R.id.rv_mentor_list)
    EmptyRecyclerView mRecyclerView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.title_toolbar)
    TextView titleToolbar;

    @Inject
    AppUtils mAppUtils;

    @Inject
    HomePresenter mHomePresenter;

    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    private FragmentListRefreshData mFragmentListRefreshData;
    private FeedDetail mFeedDetail;
    private int mPageNo;
    private UserSolrObj mUserSolrObj;
    @Bind(R.id.empty_view)
    View emptyView;

    //endregion
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.mentor_listing_layout);
        mHomePresenter.attachView(this);
        ButterKnife.bind(this);
        setupToolbarItemsColor();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MENTOR_LISTING, AppConstants.NO_REACTION_CONSTANT);
        mentorSearchInListPagination(mFragmentListRefreshData);
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        titleToolbar.setText(R.string.ID_MENTOR);
    }

    private void mentorSearchInListPagination(FragmentListRefreshData fragmentListRefreshData) {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
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
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.CAROUSEL_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeedMethod();
            }
        });
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_CHAMPION_LISTING));
    }

    private void refreshFeedMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.CAROUSEL_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        int id = view.getId();
        if (baseResponse instanceof FeedDetail) {
            switch (id) {
                case R.id.tv_mentor_follow:
                    followUnFollowRequest((UserSolrObj) baseResponse);
                    break;
                case R.id.tv_mentor_ask_question:
                    CommunityPost mentorPost = new CommunityPost();
                    mFeedDetail = (FeedDetail) baseResponse;
                    if (mFeedDetail instanceof UserSolrObj) {
                        UserSolrObj userSolrObj = (UserSolrObj) mFeedDetail;
                        mentorPost.community = new Community();
                        mentorPost.community.id = userSolrObj.getSolrIgnoreMentorCommunityId();
                        mentorPost.community.name = userSolrObj.getNameOrTitle();
                        mentorPost.createPostRequestFrom = AppConstants.MENTOR_CREATE_QUESTION;
                        mentorPost.isEdit = false;
                        CommunityPostActivity.navigateTo(this, mentorPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);

                    }
                    break;
                case R.id.li_mentor:
                    openMentorProfileDetail(baseResponse);
                    break;
                default:
            }
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
    }

    private void openMentorProfileDetail(BaseResponse baseResponse) { //form mentor card in feed only for mentor
        UserSolrObj userSolrObj = (UserSolrObj) baseResponse;
        mFeedDetail = userSolrObj;
        ProfileActivity.navigateTo(this, userSolrObj.getIdOfEntityOrParticipant(), true, 0, SCREEN_LABEL, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL, userSolrObj);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                    if (null != intent.getExtras()) {
                        UserSolrObj userSolrObj = Parcels.unwrap(intent.getParcelableExtra(AppConstants.FEED_SCREEN));
                        if (null != userSolrObj) {
                            mAdapter.setMentoreDataOnPosition(userSolrObj, userSolrObj.currentItemPosition);
                            mAdapter.notifyItemChanged(userSolrObj.currentItemPosition);
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    setResult(RESULT_OK, intent);
                    // finish();
                    break;
                default:
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
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
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
        mProgressBar.setLayoutParams(params);
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
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
            mAdapter.setSheroesGenericListData(data);
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.notifyDataSetChanged();
            mSwipeView.setRefreshing(false);
        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mAdapter != null) {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setEmptyViewWithImage(emptyView, R.string.empty_mentor_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);
        }
    }

    public void followUnFollowRequest(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        if (userSolrObj.isSolrIgnoreIsMentorFollowed()) {
            mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest, userSolrObj);
            unFollowUserEvent(userSolrObj);
        } else {
            mHomePresenter.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
            followUserEvent(userSolrObj);
        }
    }

    private void followUserEvent(UserSolrObj mUserSolarObject) {
        Event event = Event.PROFILE_FOLLOWED;
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(mUserSolarObject.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    private void unFollowUserEvent(UserSolrObj mUserSolarObject) {
        Event event = Event.PROFILE_UNFOLLOWED;
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(mUserSolarObject.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    @Override
    public void showHomeFeedList(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

        switch (feedParticipationEnum) {
            case FOLLOW_UNFOLLOW:
                mUserSolrObj = (UserSolrObj) baseResponse;
                mAdapter.notifyItemChanged(mUserSolrObj.getItemPosition(), mUserSolrObj);
                break;
            default:
        }

    }

    @Override
    public void getNotificationListSuccess(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }
}
