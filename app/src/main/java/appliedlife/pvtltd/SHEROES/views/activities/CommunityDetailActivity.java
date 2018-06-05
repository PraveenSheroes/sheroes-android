package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Context;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.presenters.CommunityDetailPresenterImpl;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.MyCommunitiesDrawerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;
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

public class CommunityDetailActivity extends BaseActivity implements ICommunityDetailView, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener {
    public static final String SCREEN_LABEL = "Community Screen Activity";
    public static final String TAB_KEY = "tab_key";
    private String streamType;

    public enum TabType {
        NAVTIVE("native"),
        WEB("web"),
        HTML("html"),
        WEB_CUSTOM_TAB("web_custom_tab"),
        FRAGMENT("fragment");

        public String tabType;
        private String mCommS;

        TabType(String tabType) {
            this.tabType = tabType;
        }

        public String getName() {
            return tabType;
        }
    }

    @Inject
    CommunityDetailPresenterImpl mCommunityDetailPresenter;

    @Inject
    Preference<LoginResponse> userPreference;

    @Bind(R.id.pb_communities_drawer)
    ProgressBar pbCommunitiesDrawer;

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
    View viewToolTipInvite;

    @Bind(R.id.drawer_community_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.nav_view_right_drawer_community_detail)
    NavigationView mNavigationViewRightDrawerWithCommunity_detail;

    @Bind(R.id.rv_right_drawer_community_detail)
    RecyclerView mRecyclerViewDrawerCommunities;


    private CommunityFeedSolrObj mCommunityFeedSolrObj;
    private List<Fragment> mTabFragments = new ArrayList<>();
    private Adapter mAdapter;
    private String mDefaultTabKey = "";
    private boolean isFromAds = false;

    private String mCommunityPrimaryColor = "#ffffff";
    private String mCommunitySecondaryColor = "#dc4541";
    private String mCommunityTitleTextColor = "#3c3c3c";

    private int mFromNotification;
    private View inviteFriendToolTip;
    private PopupWindow popupWindowInviteFriendTooTip;
    private CustiomActionBarToggle mCustiomActionBarToggle;

    private FragmentListRefreshData mFragmentListRefreshData;
    private MyCommunitiesDrawerAdapter mMyCommunitiesAdapter;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private SwipPullRefreshList mPullRefreshList;
    private boolean isDrawerOpen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_community_detail);
        ButterKnife.bind(this);
        mCommunityDetailPresenter.attachView(this);
        mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustiomActionBarToggle);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            Parcelable parcelable = getIntent().getParcelableExtra(CommunityFeedSolrObj.COMMUNITY_OBJ);
            if (parcelable != null) {
                mCommunityFeedSolrObj = Parcels.unwrap(parcelable);
                streamType = mCommunityFeedSolrObj.getStreamType();
            } else {
                String communityId = getIntent().getExtras().getString(AppConstants.COMMUNITY_ID);
                mDefaultTabKey = getIntent().getExtras().getString(TAB_KEY, "");
                isFromAds = getIntent().getExtras().getBoolean(AppConstants.IS_FROM_ADVERTISEMENT);
                if (CommonUtil.isNotEmpty(communityId)) {
                    mCommunityDetailPresenter.fetchCommunity(communityId);
                } else {
                    finish();
                }
            }
        } else {
            finish();
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mCommunityFeedSolrObj != null) {
            initializeLayout();
        }
        if (CommonUtil.forGivenCountOnly(AppConstants.INVITE_FRIEND_SESSION_PREF, AppConstants.INVITE_FRIEND_SESSION) == AppConstants.INVITE_FRIEND_SESSION) {
            if (CommonUtil.ensureFirstTime(AppConstants.INVITE_FRIEND_PREF)) {
                toolTipForInviteFriends();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onDrawerOpened() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            if (isDrawerOpen) {
                isDrawerOpen = false;
                AnalyticsManager.trackScreenView(getString(R.string.ID_DRAWER_NAVIGATION_COMMUNITIES));
            }
        }

    }

    @Override
    public void onDrawerClosed() {

    }

    private void toolTipForInviteFriends() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    int width = AppUtils.getWindowWidth(CommunityDetailActivity.this);
                    if (width < 600) {
                        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inviteFriendToolTip = layoutInflater.inflate(R.layout.tooltip_arrow_up_side, null);
                        popupWindowInviteFriendTooTip = new PopupWindow(inviteFriendToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindowInviteFriendTooTip.setOutsideTouchable(false);
                        popupWindowInviteFriendTooTip.showAsDropDown(viewToolTipInvite, -(width * 2), 0);
                        final LinearLayout llToolTipBg = inviteFriendToolTip.findViewById(R.id.ll_tool_tip_bg);
                        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(300, CommunityDetailActivity.this), LinearLayout.LayoutParams.WRAP_CONTENT);
                        llParams.setMargins(CommonUtil.convertDpToPixel(20, CommunityDetailActivity.this), 0, 0, 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
                        llParams.addRule(RelativeLayout.BELOW, R.id.iv_arrow);
                        llToolTipBg.setLayoutParams(llParams);
                    } else {
                        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        inviteFriendToolTip = layoutInflater.inflate(R.layout.tooltip_arrow_up_side, null);
                        popupWindowInviteFriendTooTip = new PopupWindow(inviteFriendToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindowInviteFriendTooTip.setOutsideTouchable(false);
                        if (width < 750) {
                            popupWindowInviteFriendTooTip.showAsDropDown(viewToolTipInvite, -(width * 2), 0);
                        } else {
                            popupWindowInviteFriendTooTip.showAsDropDown(viewToolTipInvite, -width, 0);
                        }
                    }

                    final ImageView ivArrow = inviteFriendToolTip.findViewById(R.id.iv_arrow);
                    RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(10, CommunityDetailActivity.this), 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
                    imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                    ivArrow.setLayoutParams(imageParams);
                    final TextView tvGotIt = inviteFriendToolTip.findViewById(R.id.got_it);
                    final TextView tvTitle = inviteFriendToolTip.findViewById(R.id.title);
                    tvTitle.setText(getString(R.string.tool_tip_invite_friend));
                    tvGotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindowInviteFriendTooTip.dismiss();
                        }
                    });
                } catch (WindowManager.BadTokenException e) {
                    Crashlytics.getInstance().core.logException(e);
                }
            }
        }, 1000);


    }

    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
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
        mRecyclerViewDrawerCommunities.setLayoutManager(gridLayoutManager);
        mRecyclerViewDrawerCommunities.setAdapter(mMyCommunitiesAdapter);
        //For right navigation drawer communities items
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.COMMUNITY_DEATIL_DRAWER, AppConstants.NO_REACTION_CONSTANT);
        pbCommunitiesDrawer.setVisibility(View.VISIBLE);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mCommunityDetailPresenter.fetchMyCommunities(myCommunityRequestBuilder(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo()));
        mRecyclerViewDrawerCommunities.addOnScrollListener(new HidingScrollListener(mCommunityDetailPresenter, mRecyclerViewDrawerCommunities, gridLayoutManager, mFragmentListRefreshData) {
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
        ((SimpleItemAnimator) mRecyclerViewDrawerCommunities.getItemAnimator()).setSupportsChangeAnimations(false);
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

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor(mCommunityTitleTextColor));
    }

    private void invalidateBottomBar() {
        if (mCommunityFeedSolrObj.isMember() || mCommunityFeedSolrObj.isOwner()) {
            mBottomBar.setVisibility(View.GONE);
        } else {
            mBottomBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    Snackbar.make(mFabButton, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT)
                            .show();
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
                        invalidateItem(articleSolrObj);
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                    boolean isPostDeleted = false;
                    UserPostSolrObj userPostSolrObj = null;
                    Parcelable parcelableUserPost = data.getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
                    if (parcelableUserPost != null) {
                        userPostSolrObj = Parcels.unwrap(parcelableUserPost);
                        isPostDeleted = data.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                    }
                    if (userPostSolrObj == null) {
                        break;
                    }
                    if (isPostDeleted) {
                        notifyAllItemRemoved(userPostSolrObj);
                    } else {
                        invalidateItem(userPostSolrObj);
                    }
            }
        } else if (resultCode == AppConstants.RESULT_CODE_FOR_DEACTIVATION) {
            refreshCurrentFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_community, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mCommunityFeedSolrObj != null) {
            boolean isOwnerOrMember = mCommunityFeedSolrObj.isMember() || mCommunityFeedSolrObj.isOwner();
            if (mCommunityFeedSolrObj != null) {
                menu.findItem(R.id.leave_join).setTitle(isOwnerOrMember ? R.string.ID_LEAVE : R.string.ID_JOIN);
            }
            if (mCommunityFeedSolrObj != null && mCommunityFeedSolrObj.getIdOfEntityOrParticipant() == AppConstants.SHEROES_COMMUNITY_ID) {
                menu.findItem(R.id.leave_join).setVisible(false);
            }
        }
        MenuItem menuItem = menu.findItem(R.id.share);
        menuItem.getIcon().mutate();
        menuItem.getIcon().setColorFilter(Color.parseColor(mCommunityTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        }
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
                isDrawerOpen = true;
                mDrawer.openDrawer(GravityCompat.END);
                break;
        }
        return true;
    }

    private void onLeaveClicked() {
        LoginResponse loginResponse = userPreference.get();
        UserSummary userSummary = loginResponse.getUserSummary();
        RemoveMemberRequest removeMemberRequest = removeMemberRequestBuilder(mCommunityFeedSolrObj.getIdOfEntityOrParticipant(), userSummary.getUserId());
        HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).streamType(mCommunityFeedSolrObj.getStreamType()).name(mCommunityFeedSolrObj.getNameOrTitle()).build();
        AnalyticsManager.trackEvent(Event.COMMUNITY_LEFT, getScreenName(), properties);
        mCommunityDetailPresenter.leaveCommunityAndRemoveMemberToPresenter(removeMemberRequest);
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
        mAdapter = new Adapter(getSupportFragmentManager());
        if (!CommonUtil.isEmpty(mCommunityFeedSolrObj.communityTabs)) {
            List<CommunityTab> communityTabs = new ArrayList<>();
            communityTabs = mCommunityFeedSolrObj.communityTabs;
            for (CommunityTab communityTab : communityTabs) {
                if (communityTab.type.equalsIgnoreCase(TabType.NAVTIVE.getName())) {
                    FeedFragment feedFragment = new FeedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CommunityTab.COMMUNITY_TAB_OBJ, Parcels.wrap(communityTab));
                    bundle.putString(FeedFragment.PRIMARY_COLOR, mCommunityFeedSolrObj.communityPrimaryColor);
                    bundle.putString(FeedFragment.TITLE_TEXT_COLOR, mCommunityFeedSolrObj.titleTextColor);
                    feedFragment.setArguments(bundle);
                    mAdapter.addFragment(feedFragment, communityTab.title);
                    mTabFragments.add(feedFragment);
                }
                if (communityTab.type.equalsIgnoreCase(TabType.HTML.getName())) {
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(null, communityTab.dataHtml, "", false);
                    mAdapter.addFragment(webViewFragment, communityTab.title);
                    mTabFragments.add(webViewFragment);
                }

                if (communityTab.type.equalsIgnoreCase(TabType.WEB.getName())) {
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(communityTab.dataUrl, null, "", false);
                    mAdapter.addFragment(webViewFragment, communityTab.title);
                    mTabFragments.add(webViewFragment);
                }

                if (communityTab.type.equalsIgnoreCase(TabType.WEB_CUSTOM_TAB.getName())) {
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(communityTab.dataUrl, null, "", false, true);
                    mAdapter.addFragment(webViewFragment, communityTab.title);
                    mTabFragments.add(webViewFragment);
                }

                if (communityTab.type.equalsIgnoreCase(TabType.FRAGMENT.getName())) {
                    if (communityTab.dataUrl.equalsIgnoreCase(AppConstants.HELPLINE_URL) || communityTab.dataUrl.equalsIgnoreCase(AppConstants.HELPLINE_URL_COM)) {

                        HelplineFragment helplineFragment = HelplineFragment.createInstance(mCommunityFeedSolrObj.getNameOrTitle());
                        mAdapter.addFragment(helplineFragment, communityTab.title);
                        mTabFragments.add(helplineFragment);
                    }
                }
            }
        }

        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(getDefaultTabPosition());
        final ViewPager.OnPageChangeListener pageChangeListener;
        viewPager.addOnPageChangeListener(pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mCommunityFeedSolrObj == null && mCommunityFeedSolrObj.communityTabs == null && mCommunityFeedSolrObj.communityTabs.size() <= position) {
                    return;
                }
                CommunityTab communityTab = mCommunityFeedSolrObj.communityTabs.get(position);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
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
                    mFabButton.setImageResource(R.drawable.challenge_placeholder);
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

        HashMap<String, Object> properties = builder.build();
        return properties;
    }

    @Override
    public void setCommunity(CommunityFeedSolrObj communityFeedSolrObj) {
        if (CommonUtil.isNotEmpty(streamType)) {
            communityFeedSolrObj.setStreamType(streamType);
        }
        mCommunityFeedSolrObj = communityFeedSolrObj;

        //Auto join Community if its coming through ads for new users
        boolean isOwnerOrMember = mCommunityFeedSolrObj.isMember() || mCommunityFeedSolrObj.isOwner();
        if (isFromAds && !isOwnerOrMember) {
            onJoinClicked();
        }

        initializeLayout();
    }

    @Override
    public void showMyCommunities(FeedResponsePojo myCommunityResponse) {
        List<FeedDetail> feedDetailList = myCommunityResponse.getFeedDetails();
        pbCommunitiesDrawer.setVisibility(View.GONE);
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mMyCommunitiesAdapter != null) {
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

            mMyCommunitiesAdapter.setData(data);

        } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mMyCommunitiesAdapter != null) {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
        }
        mMyCommunitiesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        if (popupWindowInviteFriendTooTip != null && popupWindowInviteFriendTooTip.isShowing()) {
            popupWindowInviteFriendTooTip.dismiss();
        }
        super.onDestroy();
    }

    public void invalidateItem(FeedDetail feedDetail) {
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                Fragment fragment = mAdapter.getItem(i);
                if (fragment instanceof FeedFragment) {
                    ((FeedFragment) fragment).updateItem(feedDetail);
                }
            }
        }
    }

    public void notifyAllItemRemoved(FeedDetail feedDetail) {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Fragment fragment = mAdapter.getItem(i);
            if (fragment instanceof FeedFragment) {
                ((FeedFragment) fragment).removeItem(feedDetail);
            }
        }
    }

    private void refreshCurrentFragment() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Fragment fragment = mAdapter.getItem(i);
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

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @OnClick({R.id.bottom_bar, R.id.btn_bottom_bar})
    public void onJoinClicked() {
        if (mCommunityFeedSolrObj == null) {
            return;
        }
        if (mCommunityFeedSolrObj.isClosedCommunity()) {
            mCommunityFeedSolrObj.setFromHome(true);
            showCommunityJoinReason(mCommunityFeedSolrObj);
            ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
        } else {
            if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                List<Long> userIdList = new ArrayList();
                userIdList.add(userPreference.get().getUserSummary().getUserId());
                HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).name(mCommunityFeedSolrObj.getNameOrTitle()).streamType(mCommunityFeedSolrObj.getStreamType()).build();
                AnalyticsManager.trackEvent(Event.COMMUNITY_JOINED, getScreenName(), properties);
                mCommunityDetailPresenter.communityJoinFromPresenter(AppUtils.communityRequestBuilder(userIdList, mCommunityFeedSolrObj.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY));
            }
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

    public String getCommunityId() {
        if (mCommunityFeedSolrObj == null) {
            return "";
        } else {
            return Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant());
        }
    }
}
