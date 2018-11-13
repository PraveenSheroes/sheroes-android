package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.CommunityDetailPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.CommunityDetailAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunitiesDrawerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityDetailView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.myCommunityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.removeMemberRequestBuilder;

/**
 * Created by ujjwal on 27/12/17.
 */

public class CommunityDetailActivity extends BaseActivity implements BaseHolderInterface, ICommunityDetailView, CustomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener {

    //region static constants
    public static final String SCREEN_LABEL = "Community Screen Activity";
    public static final String TAB_KEY = "tab_key";
    //endregion static constants

    //region enum
    public enum TabType {
        NATIVE("native"),
        WEB("web"),
        HTML("html"),
        WEB_CUSTOM_TAB("web_custom_tab"),
        FRAGMENT("fragment");

        private String tabType;

        TabType(String tabType) {
            this.tabType = tabType;
        }

        public String getName() {
            return tabType;
        }
    }
    //endregion enum

    //region injected variables
    @Inject
    CommunityDetailPresenterImpl mCommunityDetailPresenter;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    FeedUtils mFeedUtils;
    //endregion injected variables

    //region bind view variables
    @Bind(R.id.pb_communities_drawer)
    ProgressBar mCommunitiesDrawerProgress;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.title_toolbar)
    TextView mTitleToolbar;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFabButton;
    @Bind(R.id.bottom_bar)
    FrameLayout mBottomBar;
    @Bind(R.id.view_tool_tip_invite)
    View mInviteToolTip;
    @Bind(R.id.drawer_community_layout)
    DrawerLayout mCommunityDrawerLayout;
    @Bind(R.id.nav_view_right_drawer_community_detail)
    NavigationView mRightDrawerNavigation;
    @Bind(R.id.rv_right_drawer_community_detail)
    RecyclerView mCommunitiesRecycler;
    //endregion bind view variables

    //region member variables
    private String mStreamType;
    private String mDefaultTabKey = "";
    private String mCommunityPrimaryColor = "#ffffff";
    private String mCommunitySecondaryColor = "#dc4541";
    private String mCommunityTitleTextColor = "#3c3c3c";

    private boolean mIsFromAds = false;

    private final int mWidthPixel = 300;
    private final int mMarginLeft = 20;
    private final int mMarginLeftToolTip = 10;
    private final int mScreenWidthMdpi = 600;
    private final int mScreenWidthHdpi = 750;
    private int mFromNotification;
    //endregion member variables

    //region view variables
    private View mInviteFriendToolTip;
    private PopupWindow mInviteFriendPopUp;
    private CommunityFeedSolrObj mCommunityFeedSolrObj;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList<FeedDetail> mPullRefreshList;
    private CommunityDetailAdapter mCommunityDetailAdapter;
    private MyCommunitiesDrawerAdapter mMyCommunitiesAdapter;
    //endregion

    //region lifecycle methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_community_detail);
        ButterKnife.bind(this);
        mCommunityDetailPresenter.attachView(this);

        CustomActionBarToggle mCustomActionBarToggle = new CustomActionBarToggle(this, mCommunityDrawerLayout, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mCommunityDrawerLayout.addDrawerListener(mCustomActionBarToggle);

        if (getIntent() != null && getIntent().getExtras() != null) {
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            Parcelable parcelable = getIntent().getParcelableExtra(CommunityFeedSolrObj.COMMUNITY_OBJ);
            if (parcelable != null) {
                mCommunityFeedSolrObj = Parcels.unwrap(parcelable);
                mStreamType = mCommunityFeedSolrObj.getStreamType();
            } else {
                String communityId = getIntent().getExtras().getString(AppConstants.COMMUNITY_ID);
                mDefaultTabKey = getIntent().getExtras().getString(TAB_KEY, "");
                mIsFromAds = getIntent().getExtras().getBoolean(AppConstants.IS_FROM_ADVERTISEMENT);
                if (CommonUtil.isNotEmpty(communityId)) {
                    mCommunityDetailPresenter.fetchCommunity(communityId);
                } else {
                    finish();
                }
            }
        } else {
            finish();
        }

        if (mCommunityFeedSolrObj != null) {
            initializeLayout();
        }
        if (CommonUtil.forGivenCountOnly(AppConstants.INVITE_FRIEND_SESSION_PREF, AppConstants.INVITE_FRIEND_SESSION) == AppConstants.INVITE_FRIEND_SESSION) {
            if (CommonUtil.ensureFirstTime(AppConstants.INVITE_FRIEND_PREF)) {
                toolTipForInviteFriends();
            }
        }

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCommunityDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mCommunityDrawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    @Override
    protected void onDestroy() {
        if (mInviteFriendPopUp != null && mInviteFriendPopUp.isShowing()) {
            mInviteFriendPopUp.dismiss();
        }
        super.onDestroy();
    }
    //endregion lifecycle methods

    //region override methods
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onDrawerOpened() {
        if (mCommunityDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            AnalyticsManager.trackScreenView(AppConstants.RIGHT_SWIPE_NAVIGATION, getScreenName(), null);
        }
    }

    @Override
    public void onDrawerClosed() {
    }

    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (upIntent == null) return;
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            if (mFromNotification > 0) {
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            }
        }
        finish();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mCommunityDetailPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_community, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.share);
        menuItem.getIcon().mutate();
        menuItem.getIcon().setColorFilter(Color.parseColor(mCommunityTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        MenuItem item = menu.findItem(R.id.nav_communities);
        item.getIcon().mutate();
        item.getIcon().setColorFilter(Color.parseColor(mCommunityTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.leave_join:
                boolean isOwnerOrMember = mCommunityFeedSolrObj.isMember() || mCommunityFeedSolrObj.isOwner();
                if (isOwnerOrMember) {
                    onLeaveClicked();
                } else {
                    onJoinClicked();
                }
                break;
            case R.id.share:
                String deepLinkUrl;
                if (mCommunityFeedSolrObj == null) {
                    break;
                }
                if (StringUtil.isNotNullOrEmptyString(mCommunityFeedSolrObj.getPostShortBranchUrls())) {
                    deepLinkUrl = mCommunityFeedSolrObj.getPostShortBranchUrls();
                } else {
                    deepLinkUrl = mCommunityFeedSolrObj.getDeepLinkUrl();
                }
                HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).name(mCommunityFeedSolrObj.getNameOrTitle()).streamType(mCommunityFeedSolrObj.getStreamType()).build();
                AnalyticsManager.trackEvent(Event.COMMUNITY_INVITE_CLICKED, getScreenName(), properties);
                ShareBottomSheetFragment.showDialog(this, deepLinkUrl, null, deepLinkUrl, SCREEN_LABEL, false, deepLinkUrl, false, true, false, Event.COMMUNITY_INVITE, properties);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.nav_communities:
                mCommunityDrawerLayout.openDrawer(GravityCompat.END);
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
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
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
    public void onCommunityJoined() {
        mCommunityFeedSolrObj.setMember(true);
        invalidateBottomBar();
    }

    @Override
    public void onCommunityLeft() {
        mCommunityFeedSolrObj.setMember(false);
        invalidateBottomBar();
    }

    @Override
    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        final EventProperty.Builder builder = new EventProperty.Builder();
        if (mCommunityFeedSolrObj != null) {
            builder.title(mCommunityFeedSolrObj.getNameOrTitle())
                    .id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant()))
                    .streamType(mCommunityFeedSolrObj.getStreamType())
                    .communityId(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant()));
        }
        return builder.build();
    }

    @Override
    public void setCommunity(CommunityFeedSolrObj communityFeedSolrObj) {
        if (CommonUtil.isNotEmpty(mStreamType)) {
            communityFeedSolrObj.setStreamType(mStreamType);
        }
        mCommunityFeedSolrObj = communityFeedSolrObj;

        //Auto join Community if its coming through ads for new users
        boolean isOwnerOrMember = mCommunityFeedSolrObj.isMember() || mCommunityFeedSolrObj.isOwner();
        if (mIsFromAds && !isOwnerOrMember) {
            onJoinClicked();
        }
        initializeLayout();
    }

    @Override
    public void showMyCommunities(FeedResponsePojo myCommunityResponse) {
        List<FeedDetail> feedDetailList = myCommunityResponse.getFeedDetails();
        mCommunitiesDrawerProgress.setVisibility(View.GONE);
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mMyCommunitiesAdapter != null) {
            int mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);

            FeedDetail feedProgressBar = new FeedDetail();
            feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            if (position > 0) {
                data.remove(position - 1);
            }
            data.add(feedProgressBar);

            mMyCommunitiesAdapter.setData(data);
            mMyCommunitiesAdapter.notifyItemRangeInserted(position, data.size());

        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mMyCommunitiesAdapter != null) {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mMyCommunitiesAdapter.notifyDataSetChanged();
        }
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
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    Snackbar.make(mFabButton, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT).show();
                    if (mCommunityFeedSolrObj == null) {
                        return;
                    }
                    if (!mCommunityFeedSolrObj.isMember()) {
                        onCommunityJoined();
                    }
                    refreshCurrentFragment();
                    break;

                case AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL:
                    refreshCurrentFragment();
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL:
                    this.finish();
                    break;
                case AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL:
                    Parcelable parcelableArticlePost = data.getParcelableExtra(AppConstants.HOME_FRAGMENT);
                    ArticleSolrObj articleSolrObj = null;
                    if (parcelableArticlePost != null && Parcels.unwrap(parcelableArticlePost) instanceof ArticleSolrObj) {
                        articleSolrObj = Parcels.unwrap(parcelableArticlePost);
                    }
                    if (articleSolrObj != null) {
                        invalidateItem(articleSolrObj, false);
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                    boolean isPostDeleted = false;
                    FeedDetail feedDetail = null;
                    Parcelable parcelableFeedObj = data.getParcelableExtra(FeedDetail.FEED_COMMENTS);
                    if (parcelableFeedObj != null) {
                        feedDetail = Parcels.unwrap(parcelableFeedObj);
                        isPostDeleted = data.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                    }
                    if (feedDetail == null) {
                        break;
                    }
                    if (isPostDeleted) {
                        invalidateItem(feedDetail, true);
                    } else {
                        invalidateItem(feedDetail, false);
                    }
            }
        } else if (resultCode == AppConstants.RESULT_CODE_FOR_DEACTIVATION) {
            refreshCurrentFragment();
        } else if (resultCode == AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED) {
            Parcelable parcelable = data.getParcelableExtra(AppConstants.USER_FOLLOWED_DETAIL);
            if (parcelable != null) {
                UserSolrObj userSolrObj = Parcels.unwrap(parcelable);
                refreshAtPosition(userSolrObj, userSolrObj.getIdOfEntityOrParticipant());
            }
        }
    }
    //endregion

    //region instance methods
    public String getCommunityId() {
        if (mCommunityFeedSolrObj == null) {
            return "";
        } else {
            return Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant());
        }
    }

    private void refreshAtPosition(FeedDetail feedDetail, long id) {
        for (int i = 0; i < mCommunityDetailAdapter.getCount(); i++) {
            Fragment fragment = mCommunityDetailAdapter.getItem(i);
            if (fragment instanceof FeedFragment) {
                if (fragment.isVisible()) {
                    ((FeedFragment) fragment).findPositionAndUpdateItem(feedDetail, id);
                }
            }
        }
    }

    private void toolTipForInviteFriends() {
        final Handler handler = new Handler();
        int mDelayToolTip = 1000;
        handler.postDelayed(new Runnable() {
            @SuppressLint("InflateParams")
            @Override
            public void run() {
                try {
                    int width = AppUtils.getWindowWidth(CommunityDetailActivity.this);
                    mInviteFriendToolTip = LayoutInflater.from(CommunityDetailActivity.this).inflate(R.layout.tooltip_arrow_up_side, null);
                    mInviteFriendPopUp = new PopupWindow(mInviteFriendToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    mInviteFriendPopUp.setOutsideTouchable(false);

                    if (width < mScreenWidthMdpi) {
                        mInviteFriendPopUp.showAsDropDown(mInviteToolTip, -(width * 2), 0);
                        final LinearLayout llToolTipBg = mInviteFriendToolTip.findViewById(R.id.ll_tool_tip_bg);
                        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mWidthPixel, CommunityDetailActivity.this), LinearLayout.LayoutParams.WRAP_CONTENT);
                        llParams.setMargins(CommonUtil.convertDpToPixel(mMarginLeft, CommunityDetailActivity.this), 0, 0, 0);
                        llParams.addRule(RelativeLayout.BELOW, R.id.iv_arrow);
                        llToolTipBg.setLayoutParams(llParams);
                    } else {
                        if (width < mScreenWidthHdpi) {
                            mInviteFriendPopUp.showAsDropDown(mInviteToolTip, -(width * 2), 0);
                        } else {
                            mInviteFriendPopUp.showAsDropDown(mInviteToolTip, -width, 0);
                        }
                    }

                    final ImageView ivArrow = mInviteFriendToolTip.findViewById(R.id.iv_arrow);
                    RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(mMarginLeftToolTip, CommunityDetailActivity.this), 0);
                    imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                    ivArrow.setLayoutParams(imageParams);
                    final TextView tvGotIt = mInviteFriendToolTip.findViewById(R.id.got_it);
                    final TextView tvTitle = mInviteFriendToolTip.findViewById(R.id.title);
                    tvTitle.setText(getString(R.string.tool_tip_invite_friend));
                    tvGotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mInviteFriendPopUp.dismiss();
                        }
                    });
                } catch (WindowManager.BadTokenException e) {
                    Crashlytics.getInstance().core.logException(e);
                }
            }
        }, mDelayToolTip);
    }

    private void initializeLayout() {
        setUpMyCommunitiesList();
        setAllColor();
        setupToolbarItemsColor();
        setupColorTheme();
        setupViewPager(mViewPager);
        setupTabLayout();
        invalidateBottomBar();
        setupToolBar();
        invalidateOptionsMenu();
    }

    private void setUpMyCommunitiesList() {
        mMyCommunitiesAdapter = new MyCommunitiesDrawerAdapter(this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mCommunitiesRecycler.setLayoutManager(gridLayoutManager);
        mCommunitiesRecycler.setAdapter(mMyCommunitiesAdapter);
        //For right navigation drawer communities items
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.COMMUNITY_DEATIL_DRAWER, AppConstants.NO_REACTION_CONSTANT);
        mCommunitiesDrawerProgress.setVisibility(View.VISIBLE);
        mPullRefreshList = new SwipPullRefreshList<>();
        mPullRefreshList.setPullToRefresh(false);
        mCommunityDetailPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mCommunitiesRecycler.addOnScrollListener(new HidingScrollListener(mCommunityDetailPresenter, mCommunitiesRecycler, gridLayoutManager, mFragmentListRefreshData) {
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
        ((SimpleItemAnimator) mCommunitiesRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void setupToolBar() {
        mTitleToolbar.setText(mCommunityFeedSolrObj.getNameOrTitle());
        mBottomBar.setBackgroundColor(Color.parseColor(mCommunitySecondaryColor));
    }

    private void setAllColor() {
        if (mCommunityFeedSolrObj == null) {
            mCommunityPrimaryColor = "#ffffff";
            mCommunitySecondaryColor = "#dc4541";
            mCommunityTitleTextColor = "#3c3c3c";
            return;
        }
        if (CommonUtil.isNotEmpty(mCommunityFeedSolrObj.communityPrimaryColor)) {
            mCommunityPrimaryColor = mCommunityFeedSolrObj.communityPrimaryColor;
        } else {
            mCommunityPrimaryColor = "#ffffff";
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedSolrObj.communitySecondaryColor)) {
            mCommunitySecondaryColor = mCommunityFeedSolrObj.communitySecondaryColor;
        } else {
            mCommunitySecondaryColor = "#dc4541";
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedSolrObj.titleTextColor)) {
            mCommunityTitleTextColor = mCommunityFeedSolrObj.titleTextColor;
        } else {
            mCommunityTitleTextColor = "#3c3c3c";
        }
    }

    private void setupToolbarItemsColor() {
        Drawable drawable = mToolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), Color.parseColor(mCommunityTitleTextColor));
            mToolbar.setOverflowIcon(drawable);
        }
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        upArrow.mutate();
        upArrow.setColorFilter(Color.parseColor(mCommunityTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor(mCommunityTitleTextColor));

        if(getSupportActionBar() == null) return;
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void invalidateBottomBar() {
        if (mCommunityFeedSolrObj.isMember() || mCommunityFeedSolrObj.isOwner()) {
            mBottomBar.setVisibility(View.GONE);
        } else {
            mBottomBar.setVisibility(View.VISIBLE);
        }
    }

    private void onLeaveClicked() {
        LoginResponse loginResponse = mUserPreference.get();
        UserSummary userSummary = loginResponse.getUserSummary();
        RemoveMemberRequest removeMemberRequest = removeMemberRequestBuilder(mCommunityFeedSolrObj.getIdOfEntityOrParticipant(), userSummary.getUserId());
        HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).streamType(mCommunityFeedSolrObj.getStreamType()).name(mCommunityFeedSolrObj.getNameOrTitle()).build();
        AnalyticsManager.trackEvent(Event.COMMUNITY_LEFT, getScreenName(), properties);
        mCommunityDetailPresenter.communityLeft(removeMemberRequest);
    }

    private void setupTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private int getDefaultTabPosition() {
        int position = 0;
        if (mCommunityFeedSolrObj == null || CommonUtil.isEmpty(mCommunityFeedSolrObj.communityTabs)) {
            return position;
        }
        if (CommonUtil.isNotEmpty(mDefaultTabKey)) {
            for (CommunityTab communityTab : mCommunityFeedSolrObj.communityTabs) {
                if (communityTab.key.equalsIgnoreCase(mDefaultTabKey)) {
                    return position;
                }
                position++;
            }
        } else {
            for (CommunityTab communityTab : mCommunityFeedSolrObj.communityTabs) {
                if (mCommunityFeedSolrObj.isMember()) {
                    if (communityTab.key.equalsIgnoreCase(mCommunityFeedSolrObj.defaultTabJoinedKey)) {
                        return position;
                    }
                }
                if (!mCommunityFeedSolrObj.isMember()) {
                    if (communityTab.key.equalsIgnoreCase(mCommunityFeedSolrObj.defaultTabKey)) {
                        return position;
                    }
                }
                position++;
            }
        }
        return 0;
    }

    private void setupColorTheme() {
        mTitleToolbar.setTextColor(Color.parseColor(mCommunityTitleTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mCommunityPrimaryColor)));
        }
        mToolbar.setBackgroundColor(Color.parseColor(mCommunityPrimaryColor));
        String alphaColor = mCommunityTitleTextColor;
        alphaColor = alphaColor.replace("#", "#" + "BF");
        mTabLayout.setTabTextColors(Color.parseColor(alphaColor), Color.parseColor(mCommunityTitleTextColor));
        mFabButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(mCommunitySecondaryColor)));
        mTabLayout.setBackgroundColor(Color.parseColor(mCommunityPrimaryColor));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor(mCommunityTitleTextColor));
    }

    private void setupViewPager(final ViewPager viewPager) {
        mCommunityDetailAdapter = new CommunityDetailAdapter(getSupportFragmentManager());
        mCommunityDetailAdapter.addCommunityTabs(mCommunityFeedSolrObj);
        viewPager.setAdapter(mCommunityDetailAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(getDefaultTabPosition());
        final ViewPager.OnPageChangeListener pageChangeListener;
        viewPager.addOnPageChangeListener(pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mCommunityFeedSolrObj == null || mCommunityFeedSolrObj.communityTabs == null || CommonUtil.isEmpty(mCommunityFeedSolrObj.communityTabs)
                        || mCommunityFeedSolrObj.communityTabs.size() <= position) {
                    return;
                }
                CommunityTab communityTab = mCommunityFeedSolrObj.communityTabs.get(position);
                HashMap<String, Object> properties = new EventProperty.Builder()
                                .id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant()))
                                .communityId(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant()))
                                .title(mCommunityFeedSolrObj.getNameOrTitle())
                                .tabTitle(communityTab.title)
                                .tabKey(communityTab.key)
                                .streamType(mCommunityFeedSolrObj.getStreamType())
                                .build();
                AnalyticsManager.trackScreenView(SCREEN_LABEL, getPreviousScreenName(), properties);
                if (communityTab.showFabButton && CommonUtil.isNotEmpty(communityTab.fabUrl)) {
                    mFabButton.setVisibility(View.VISIBLE);
                    if (CommonUtil.isValidContextForGlide(mFabButton.getContext())) {
                        Glide.with(mFabButton.getContext())
                                .load(communityTab.fabIconUrl)
                                .into(mFabButton);
                    }
                } else {
                    mFabButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                pageChangeListener.onPageSelected(viewPager.getCurrentItem());
            }
        });

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                int tabLayoutWidth = mTabLayout.getWidth();

                DisplayMetrics metrics = new DisplayMetrics();
                CommunityDetailActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int deviceWidth = metrics.widthPixels;

                if (tabLayoutWidth < deviceWidth) {
                    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                    ViewGroup.LayoutParams mParams = mTabLayout.getLayoutParams();
                    mParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    mTabLayout.setLayoutParams(mParams);
                } else {
                    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
            }
        });
    }

    public void invalidateItem(FeedDetail feedDetail, boolean isRemoved) {
        if (mCommunityDetailAdapter != null) {
            for (int i = 0; i < mCommunityDetailAdapter.getCount(); i++) {
                Fragment fragment = mCommunityDetailAdapter.getItem(i);
                if (fragment instanceof FeedFragment) {
                    if(isRemoved) {
                        ((FeedFragment) fragment).removeItem(feedDetail);
                    } else {
                        ((FeedFragment) fragment).updateItem(feedDetail);
                    }
                }
            }
        }
    }

    private void refreshCurrentFragment() {
        for (int i = 0; i < mCommunityDetailAdapter.getCount(); i++) {
            Fragment fragment = mCommunityDetailAdapter.getItem(i);
            if (fragment instanceof FeedFragment) {
                if (fragment.isVisible()) {
                    ((FeedFragment) fragment).refreshList();
                }
            }
        }
    }

    public static void navigateTo(Activity fromActivity, CommunityFeedSolrObj communityFeedSolrObj, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, CommunityDetailActivity.class);
        Parcelable parcelable = Parcels.wrap(communityFeedSolrObj);
        intent.putExtra(CommunityFeedSolrObj.COMMUNITY_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long communityId, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, CommunityDetailActivity.class);
        intent.putExtra(AppConstants.COMMUNITY_ID, Long.toString(communityId));
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    //endregion instance method

    //region onclick methods
    @OnClick({R.id.bottom_bar, R.id.btn_bottom_bar})
    public void onJoinClicked() {
        if (mCommunityFeedSolrObj == null) {
            return;
        }

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList<>();
            userIdList.add(mUserPreference.get().getUserSummary().getUserId());
            HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).name(mCommunityFeedSolrObj.getNameOrTitle()).streamType(mCommunityFeedSolrObj.getStreamType()).build();
            AnalyticsManager.trackEvent(Event.COMMUNITY_JOINED, getScreenName(), properties);
            mCommunityDetailPresenter.joinCommunity(AppUtils.communityRequestBuilder(userIdList, mCommunityFeedSolrObj.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY));
        }
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        CommunityTab communityTab = mCommunityFeedSolrObj.communityTabs.get(mTabLayout.getSelectedTabPosition());
        String url = communityTab.fabUrl;
        if (url.equalsIgnoreCase(AppConstants.COMMUNITY_POST_URL) || url.equalsIgnoreCase(AppConstants.COMMUNITY_POST_URL_COM)) {
            if (mCommunityFeedSolrObj == null) {
                return;
            }
            CommunityPost communityPost = new CommunityPost();
            communityPost.community = new Community();
            communityPost.community.id = mCommunityFeedSolrObj.getIdOfEntityOrParticipant();
            communityPost.community.name = mCommunityFeedSolrObj.getNameOrTitle();
            communityPost.isMyPost = mCommunityFeedSolrObj.isOwner();
            HashMap<String, Object> screenProperties = new EventProperty.Builder()
                    .sourceScreenId(getCommunityId())
                    .sourceTabKey(communityTab.key)
                    .sourceTabTitle(communityTab.title)
                    .build();
            CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, true, mCommunityFeedSolrObj.communityPrimaryColor, mCommunityFeedSolrObj.titleTextColor, screenProperties);
        } else {
            if (StringUtil.isNotNullOrEmptyString(url)) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
    //endregion
}
