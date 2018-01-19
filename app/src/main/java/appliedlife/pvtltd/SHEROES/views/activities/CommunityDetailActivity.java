package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences.Preference;

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
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.presenters.CommunityDetailPresenterImpl;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityDetailView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.removeMemberRequestBuilder;

/**
 * Created by ujjwal on 27/12/17.
 */

public class CommunityDetailActivity extends BaseActivity implements ICommunityDetailView {
    public static final String SCREEN_LABEL = "Community Screen Activity";
    public static final String TAB_KEY = "tab_key";

    public enum TabType {
        NAVTIVE("native"),
        WEB("web"),
        HTML("html");

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

    private CommunityFeedSolrObj mCommunityFeedSolrObj;
    private List<Fragment> mTabFragments = new ArrayList<>();
    private Adapter mAdapter;
    private String mDefaultTabKey = "";

    private String mCommunityPrimaryColor = "#6e2f95";
    private String mCommunitySecondaryColor = "#dc4541";
    private String mCommunityTitleTextColor = "#ffffff";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_community_detail);
        ButterKnife.bind(this);
        mCommunityDetailPresenter.attachView(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Parcelable parcelable = getIntent().getParcelableExtra(CommunityFeedSolrObj.COMMUNITY_OBJ);
            if (parcelable != null) {
                mCommunityFeedSolrObj = Parcels.unwrap(parcelable);
            }else {
                String communityId = getIntent().getExtras().getString(AppConstants.COMMUNITY_ID);
                mDefaultTabKey =  getIntent().getExtras().getString(TAB_KEY, "");
                if(CommonUtil.isNotEmpty(communityId)){
                    mCommunityDetailPresenter.fetchCommunity(communityId);
                }else {
                    finish();
                }
            }
        }else {
            finish();
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mCommunityFeedSolrObj != null) {
            initializeLayout();
        }
    }

    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        }
            finish();
    }

    private void initializeLayout() {
        setAllColor();
        setupToolbarItemsColor();
        setupColorTheme();
        setupViewPager(mViewPager);
        setupTabLayout();
        invalidateBottomBar();
        setupToolBar();
        invalidateOptionsMenu();
    }

    private void setupToolBar() {
        mTitleToolbar.setText(mCommunityFeedSolrObj.getNameOrTitle());
        mBottomBar.setBackgroundColor(Color.parseColor(mCommunitySecondaryColor));
    }

    private void setAllColor() {
        if (mCommunityFeedSolrObj == null) {
            mCommunityPrimaryColor = "#6e2f95";
            mCommunitySecondaryColor = "#dc4541";
            mCommunityTitleTextColor = "#ffffff";
            return;
        }
        if (CommonUtil.isNotEmpty(mCommunityFeedSolrObj.communityPrimaryColor)) {
            mCommunityPrimaryColor = mCommunityFeedSolrObj.communityPrimaryColor;
        } else {
            mCommunityPrimaryColor = "#6e2f95";
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedSolrObj.communitySecondaryColor)) {
            mCommunitySecondaryColor = mCommunityFeedSolrObj.communitySecondaryColor;
        } else {
            mCommunitySecondaryColor = "#dc4541";
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedSolrObj.titleTextColor)) {
            mCommunityTitleTextColor = mCommunityFeedSolrObj.titleTextColor;
        } else {
            mCommunityTitleTextColor = "#ffffff";
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
        upArrow.setColorFilter(Color.parseColor(mCommunityTitleTextColor), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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
                    if(mCommunityFeedSolrObj.isMember() == false){
                        onCommunityJoined();
                    }
                    refreshCurrentFragment();
                    break;

                case AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                    if (null != data.getExtras()) {
                        UserSolrObj userSolrObj = (UserSolrObj) Parcels.unwrap(data.getParcelableExtra(AppConstants.FEED_SCREEN));
                        if (null != userSolrObj) {
                            Fragment fragmentMentor = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                            if (AppUtils.isFragmentUIActive(fragmentMentor)) {
                                invalidateItem(userSolrObj);
                            }
                        }
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_JOB_DETAIL:
                    if (null != data && null != data.getExtras()) {
                        JobFeedSolrObj jobFeedSolrObj = null;
                        jobFeedSolrObj = (JobFeedSolrObj) Parcels.unwrap(data.getParcelableExtra(AppConstants.JOB_FRAGMENT));
                        invalidateItem(jobFeedSolrObj);
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL:
                    if (resultCode == Activity.RESULT_OK) {
                        refreshCurrentFragment();
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL:
                    Parcelable parcelableArticlePost = data.getParcelableExtra(AppConstants.HOME_FRAGMENT);
                    ArticleSolrObj articleSolrObj = null;
                    if (parcelableArticlePost != null && Parcels.unwrap(parcelableArticlePost) instanceof ArticleSolrObj) {
                        articleSolrObj = (ArticleSolrObj) Parcels.unwrap(parcelableArticlePost);
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_community, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mCommunityFeedSolrObj!=null){
            boolean isOwnerOrMember = mCommunityFeedSolrObj.isMember() || mCommunityFeedSolrObj.isOwner();
            if(mCommunityFeedSolrObj!=null){
                menu.findItem(R.id.leave_join).setTitle(isOwnerOrMember ? R.string.ID_LEAVE : R.string.ID_JOIN);
            }
            if(mCommunityFeedSolrObj!=null && mCommunityFeedSolrObj.getIdOfEntityOrParticipant() == AppConstants.SHEROES_COMMUNITY_ID){
                menu.findItem(R.id.leave_join).setVisible(false);
            }
        }
        MenuItem menuItem = menu.findItem(R.id.share);
        menuItem.getIcon().setColorFilter(Color.parseColor(mCommunityTitleTextColor), PorterDuff.Mode.SRC_ATOP);
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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(AppConstants.SHARE_MENU_TYPE);
                intent.putExtra(Intent.EXTRA_TEXT, mCommunityFeedSolrObj.getDeepLinkUrl());
                startActivity(Intent.createChooser(intent, AppConstants.SHARE));
                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_LINK, AppConstants.EMPTY_STRING);
                HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).name(mCommunityFeedSolrObj.getNameOrTitle()).build();
                AnalyticsManager.trackEvent(Event.COMMUNITY_SHARED, getScreenName(), properties);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onLeaveClicked() {
        LoginResponse loginResponse = userPreference.get();
        UserSummary userSummary = loginResponse.getUserSummary();
        RemoveMemberRequest removeMemberRequest = removeMemberRequestBuilder(mCommunityFeedSolrObj.getIdOfEntityOrParticipant(), userSummary.getUserId());
        HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).name(mCommunityFeedSolrObj.getNameOrTitle()).build();
        AnalyticsManager.trackEvent(Event.COMMUNITY_LEFT, getScreenName(), properties);
        mCommunityDetailPresenter.leaveCommunityAndRemoveMemberToPresenter(removeMemberRequest);
    }

    private void setupTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        if(mCommunityFeedSolrObj==null || CommonUtil.isEmpty(mCommunityFeedSolrObj.communityTabs)){
            return position;
        }
        if(CommonUtil.isNotEmpty(mDefaultTabKey)){
            for (CommunityTab communityTab : mCommunityFeedSolrObj.communityTabs) {
                    if (communityTab.key.equalsIgnoreCase(mDefaultTabKey)) {
                        return position;
                    }
                position++;
            }

        }else {
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
                CommunityTab communityTab = mCommunityFeedSolrObj.communityTabs.get(position);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant()))
                                .title(mCommunityFeedSolrObj.getNameOrTitle())
                                .tabTitle(communityTab.title)
                                .tabKey(communityTab.key)
                                .build();
                AnalyticsManager.trackScreenView(SCREEN_LABEL, getPreviousScreenName(), properties);
                if (communityTab.showFabButton && CommonUtil.isNotEmpty(communityTab.fabUrl)) {
                    mFabButton.setVisibility(View.VISIBLE);
                    mFabButton.setImageResource(R.drawable.challenge_placeholder);
                    Glide.with(CommunityDetailActivity.this)
                            .load(communityTab.fabIconUrl)
                            .into(mFabButton);
                } else {
                    mFabButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.post(new Runnable()
        {
            @Override
            public void run() {
                pageChangeListener.onPageSelected(viewPager.getCurrentItem());
            }
        });

        mTabLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                int tabLayoutWidth = mTabLayout.getWidth();

                DisplayMetrics metrics = new DisplayMetrics();
                CommunityDetailActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int deviceWidth = metrics.widthPixels;

                if (tabLayoutWidth < deviceWidth)
                {
                    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                    ViewGroup.LayoutParams mParams = mTabLayout.getLayoutParams();
                    mParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    mTabLayout.setLayoutParams(mParams);
                } else
                {
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
                    .id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant()));
        }

        HashMap<String, Object> properties = builder.build();
        return properties;
    }

    @Override
    public void setCommunity(CommunityFeedSolrObj communityFeedSolrObj) {
        mCommunityFeedSolrObj = communityFeedSolrObj;
        initializeLayout();
    }

    public void invalidateItem(FeedDetail feedDetail) {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Fragment fragment = mAdapter.getItem(i);
            if (fragment instanceof FeedFragment) {
                ((FeedFragment) fragment).updateItem(feedDetail);
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
                if(fragment.isVisible()){
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
        if (mCommunityFeedSolrObj.isClosedCommunity()) {
            mCommunityFeedSolrObj.setFromHome(true);
            showCommunityJoinReason(mCommunityFeedSolrObj);
            ((SheroesApplication) ((BaseActivity) this).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
        } else {
            if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                List<Long> userIdList = new ArrayList();
                userIdList.add((long) userPreference.get().getUserSummary().getUserId());
                HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant())).name(mCommunityFeedSolrObj.getNameOrTitle()).build();
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
            HashMap<String, Object> screenProperties = new EventProperty.Builder()
                    .sourceScreenId(getCommunityId())
                    .sourceTabKey(communityTab.key)
                    .sourceTabTitle(communityTab.title)
                    .build();
            CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, true, mCommunityFeedSolrObj.communityPrimaryColor, mCommunityFeedSolrObj.titleTextColor, screenProperties);
        } else {
            if (null != url && StringUtil.isNotNullOrEmptyString(url)) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
    public String getCommunityId() {
        return Long.toString(mCommunityFeedSolrObj.getIdOfEntityOrParticipant());
    }
}
