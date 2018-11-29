package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FollowerFollowingCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.FollowerFollowingAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.UnFollowDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowCallback;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;

/**
 * Created by Praveen on 05/12/17.
 */

public class ChampionListingActivity extends BaseActivity implements BaseHolderInterface, HomeView , FollowerFollowingCallback, IFollowCallback {
    //region constant
    private static final String SCREEN_LABEL = "Mentors Listing Screen";
    //endregion constant

    //region binding view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.swipe_mentor_list)
    SwipeRefreshLayout mSwipeView;

    @Bind(R.id.title_toolbar)
    TextView mTitleToolbar;

    @Bind(R.id.empty_view)
    View mEmptyView;

    @Bind(R.id.rv_mentor_list)
    EmptyRecyclerView mRecyclerView;

    @BindDimen(R.dimen.imagesize_unfollow_dialog)
    int mProfileSizeSmall;
    //endregion binding view variables

    //region injected variable
    @Inject
    AppUtils mAppUtils;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    Preference<LoginResponse> mUserPreference;
    //endregion injected variable

    //region member variable
    private SwipPullRefreshList mPullRefreshList;
    private FragmentListRefreshData mFragmentListRefreshData;
    private FollowerFollowingAdapter mAdapter;
    private UnFollowDialogFragment mUnFollowDialogFragment;
    //endregion member variable

    //region activity lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.champion_listing_layout);
        mHomePresenter.attachView(this);
        ButterKnife.bind(this);
        setupToolbarItemsColor();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MENTOR_LISTING, AppConstants.NO_REACTION_CONSTANT);
        mentorSearchInListPagination(mFragmentListRefreshData);
    }

    @Override
    protected void onStop() {
        if (mUnFollowDialogFragment != null && mUnFollowDialogFragment.isVisible()) {
            mUnFollowDialogFragment.dismiss();
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            if (resultCode == AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED) {
                Parcelable parcelable = intent.getParcelableExtra(AppConstants.USER_FOLLOWED_DETAIL);
                if (parcelable != null) {
                    UserSolrObj userSolrObj = Parcels.unwrap(parcelable);
                    if (mAdapter == null) {
                        return;
                    }
                    mAdapter.findPositionAndUpdateItem(userSolrObj, userSolrObj.getIdOfEntityOrParticipant());
                }
            } else {
                switch (requestCode) {
                    case AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                        if (null != intent.getExtras()) {
                            UserSolrObj userSolrObj = Parcels.unwrap(intent.getParcelableExtra(AppConstants.FEED_SCREEN));
                            if (null != userSolrObj) {
                                mAdapter.updateData(userSolrObj, userSolrObj.currentItemPosition);
                                mAdapter.notifyItemChanged(userSolrObj.currentItemPosition);
                            }
                        }
                        break;
                    case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                        setResult(RESULT_OK, intent);
                        break;
                    default:
                }
            }
        }
    }
    //endregion activity lifecycle methods

    //region override methods
    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        int id = view.getId();
        if (baseResponse instanceof FeedDetail) {
            switch (id) {
                case R.id.tv_follower_count:
                    followUnFollowRequest((UserSolrObj) baseResponse);
                    break;
                default:
            }
        }
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
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
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
    }

    @Override
    public void stopProgressBar() {
    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            int mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<UserSolrObj> data = null;
            UserSolrObj userSolrObj = new UserSolrObj();
            userSolrObj.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            if (position > 0) {
                data.remove(position - 1);
            }
            data.add(userSolrObj);
            mAdapter.setData(data);
            mSwipeView.setRefreshing(false);
        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mAdapter != null) {
            List<UserSolrObj> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mAdapter.notifyDataSetChanged();
            mSwipeView.setRefreshing(false);
        } else {
            mRecyclerView.setEmptyViewWithImage(mEmptyView, R.string.empty_mentor_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case FOLLOW_UNFOLLOW:
                UserSolrObj userSolrObj = (UserSolrObj) baseResponse;
                mAdapter.notifyItemChanged(userSolrObj.getItemPosition(), userSolrObj);
                break;
            default:
        }
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
    public void onItemClick(UserSolrObj userSolrObj) {
        boolean isChampion = (userSolrObj.getUserSubType() != null && userSolrObj.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor();
        long id = userSolrObj.getIdOfEntityOrParticipant();
        ProfileActivity.navigateTo(this, id, isChampion, PROFILE_NOTIFICATION_ID, AppConstants.PROFILE_FOLLOWED_CHAMPION,
                null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, false);
    }

    @Override
    public void onFollowFollowingClick(UserSolrObj userSolrObj, String followFollowingBtnText) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                        .name(userSolrObj.getNameOrTitle())
                        .isMentor((userSolrObj.getUserSubType() != null && userSolrObj.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor())
                        .build();
        if (userSolrObj.isSolrIgnoreIsUserFollowed() || userSolrObj.isSolrIgnoreIsMentorFollowed()) {
            unFollowConfirmation(userSolrObj);
        } else {
            AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWED, getScreenName(), properties);
            mHomePresenter.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
        }
    }

    @Override
    public void onProfileFollowed(UserSolrObj userSolrObj) {
        mAdapter.notifyItemChanged(userSolrObj.getItemPosition());
    }
    //endregion override methods

    //region public methods
    public void followUnFollowRequest(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        if (userSolrObj.isSolrIgnoreIsUserFollowed() || userSolrObj.isSolrIgnoreIsMentorFollowed()) {
            mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest, userSolrObj);
        } else {
            mHomePresenter.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
            followUserEvent(userSolrObj);
        }
    }
    //endregion public methods

    //region private methods
    private void unFollowConfirmation(final UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());

        if (mUnFollowDialogFragment != null && mUnFollowDialogFragment.isVisible()) {
            mUnFollowDialogFragment.dismiss();
        }
        mUnFollowDialogFragment = new UnFollowDialogFragment();
        mUnFollowDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mUnFollowDialogFragment.isVisible() && !mIsDestroyed) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(userSolrObj);
            bundle.putParcelable(AppConstants.USER, parcelable);
            Parcelable unFollowRequestParcelable = Parcels.wrap(publicProfileListRequest);
            bundle.putParcelable(AppConstants.UNFOLLOW, unFollowRequestParcelable);
            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, userSolrObj.isAuthorMentor());
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, false);
            mUnFollowDialogFragment.setArguments(bundle);
            mUnFollowDialogFragment.show(getFragmentManager(), UnFollowDialogFragment.class.getName());
        }
    }

    private void followUserEvent(UserSolrObj mUserSolarObject) {
        Event event = Event.PROFILE_FOLLOWED;
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor((mUserSolarObject.getUserSubType() != null && mUserSolarObject.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || mUserSolarObject.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        mTitleToolbar.setText(R.string.ID_MENTOR);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    private void mentorSearchInListPagination(FragmentListRefreshData fragmentListRefreshData) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FollowerFollowingAdapter(this, this, mUserPreference);
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
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
    }

    private void refreshFeedMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.CAROUSEL_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
    }
    //endregion private methods
}