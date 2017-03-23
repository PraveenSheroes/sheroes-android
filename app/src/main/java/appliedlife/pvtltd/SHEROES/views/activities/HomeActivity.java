package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.communities.CommunitySuggestion;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Section;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.CustomeDataList;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.BlurrImage;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeSpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageFullViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobLocationFilter;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunityInviteMemberFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFeedbackFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingTermsAndConditionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements SettingView, JobFragment.HomeActivityIntractionListner, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, CommentReactionFragment.HomeActivityIntractionListner, ImageFullViewFragment.HomeActivityIntraction {
    private final String TAG = LogUtils.makeLogTag(HomeActivity.class);
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Bind(R.id.iv_drawer_profile_circle_icon)
    RoundedImageView ivDrawerProfileCircleIcon;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.tv_user_location)
    TextView mTvUserLocation;
    @Bind(R.id.cl_main_layout)
    View mCLMainLayout;
    @Bind(R.id.home_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.rv_drawer)
    RecyclerView mRecyclerView;
    @Bind(R.id.home_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tab_community_view)
    TabLayout mTabLayout;
    @Bind(R.id.fl_home_footer_list)
    public FrameLayout mFlHomeFooterList;
    @Bind(R.id.iv_footer_button_icon)
    ImageView mIvFooterButtonIcon;
    @Bind(R.id.tv_search_box)
    TextView mTvSearchBox;
    @Bind(R.id.tv_setting)
    TextView mTvSetting;
    @Bind(R.id.tv_home)
    TextView mTvHome;
    @Bind(R.id.tv_communities)
    TextView mTvCommunities;
    @Bind(R.id.tv_spinner_icon)
    public TextView mTvSpinnerIcon;
    @Bind(R.id.fl_feed_full_view)
    public FrameLayout flFeedFullView;
    @Bind(R.id.iv_side_drawer_profile_blur_background)
    ImageView mIvSideDrawerProfileBlurBackground;
    @Bind(R.id.iv_home_notification_icon)
    ImageView mIvHomeNotification;
    @Bind(R.id.fab_add_community)
    FloatingActionButton mFloatingActionButton;
    @Bind(R.id.li_home_community_button_layout)
    LinearLayout liHomeCommunityButtonLayout;
    GenericRecyclerViewAdapter mAdapter;
    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    private HomeSpinnerFragment mHomeSpinnerFragment;
    private FragmentOpen mFragmentOpen;
    private CustiomActionBarToggle mCustiomActionBarToggle;
    private FeedDetail mFeedDetail;
    private String profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderHomeFragmentView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void renderHomeFragmentView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustiomActionBarToggle);
        // mCustiomActionBarToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        initHomeViewPagerAndTabs();
        mHomeSpinnerItemList = CustomeDataList.makeSpinnerListRequest();
        assignNavigationRecyclerListView();
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            //TODO: this data to be removed
            profile = mUserPreference.get().getUserSummary().getPhotoUrl(); //"https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAhNAAAAJDYwZWIyZTg5LWFmOTItNGIwYS05YjQ5LTM2YTRkNGQ2M2JlNw.jpg";
            Glide.with(this)
                    .load(profile)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivDrawerProfileCircleIcon);
            mTvUserName.setText(mUserPreference.get().getUserSummary().getFirstName() + AppConstants.SPACE + mUserPreference.get().getUserSummary().getLastName());
            //mTvUserLocation.setText("Delhi, India");
            Glide.with(this)
                    .load(profile).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {

                            Bitmap blurred = BlurrImage.blurRenderScript(HomeActivity.this, profileImage, 20);
                            mIvSideDrawerProfileBlurBackground.setImageBitmap(blurred);
                        }
                    });
        }
        //  HomeSpinnerFragment frag = new HomeSpinnerFragment();
        //  callFirstFragment(R.id.fl_fragment_container, frag);
        //   new Handler().postDelayed(openDrawerRunnable(), 200);
    }

    private Runnable openDrawerRunnable() {
        return new Runnable() {

            @Override
            public void run() {
                //  mDrawer.openDrawer(Gravity.LEFT);
            }
        };
    }

    @Override
    public void onErrorOccurence() {
        getSupportFragmentManager().popBackStack();
        //showNetworkTimeoutDoalog(true);
    }

    @Override
    public void openFilter() {
        Intent intent = new Intent(getApplicationContext(), JobFilterActivity.class);
        startActivity(intent);
    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            int id = view.getId();
            if (id == R.id.tv_community_join) {
                //  openInviteSearch(mFeedDetail);
                showCommunityJoinReason(mFeedDetail);
            } else if (id == R.id.tv_add_invite) {
                if (null != mFeedDetail) {
                    Fragment fragmentMyCommunityInviteMember = getSupportFragmentManager().findFragmentByTag(MyCommunityInviteMemberFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragmentMyCommunityInviteMember)) {
                        ((MyCommunityInviteMemberFragment) fragmentMyCommunityInviteMember).onAddMemberClick(mFeedDetail);
                    }
                }
            } else {
                mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.ONE_CONSTANT);
                setAllValues(mFragmentOpen);
                super.feedCardsHandled(view, baseResponse);
            }
        } else if (baseResponse instanceof HomeSpinnerItem) {
            HomeSpinnerItem homeSpinnerItem = (HomeSpinnerItem) baseResponse;
            String spinnerHeaderName = homeSpinnerItem.getName();
            if (StringUtil.isNotNullOrEmptyString(spinnerHeaderName)) {
                if (spinnerHeaderName.equalsIgnoreCase(AppConstants.FEED_ARTICLE)) {
                    mFragmentOpen.setOpen(true);
                } else {
                    for (int i = 0; i < mHomeSpinnerItemList.size(); i++) {
                        mHomeSpinnerItemList.get(i).setDone(true);
                    }
                    mTvSpinnerIcon.setText(spinnerHeaderName);
                    mFragmentOpen.setOpen(true);
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((ArticlesFragment) fragment).categorySearchInArticle(homeSpinnerItem);
                    }
                }
            } else {
                mHomeSpinnerItemList.clear();
                mHomeSpinnerItemList = CustomeDataList.makeSpinnerListRequest();
            }
            onBackPressed();
        } else if (baseResponse instanceof DrawerItems) {
            int drawerItem = ((DrawerItems) baseResponse).getId();
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            }
            switch (drawerItem) {
                case AppConstants.ONE_CONSTANT:
                    ProfileActicity.navigate(this, view, profile);
                    break;
                case AppConstants.TWO_CONSTANT:
                    //   initHomeViewPagerAndTabs();
                    checkForAllOpenFragments();
                    openArticleFragment();
                    break;
                case AppConstants.THREE_CONSTANT:
                    //  initHomeViewPagerAndTabs();
                    checkForAllOpenFragments();
                    openJobFragment();
                    break;
                case AppConstants.FOURTH_CONSTANT:
                    //  initHomeViewPagerAndTabs();
                    checkForAllOpenFragments();
                    openBookMarkFragment();
                    break;
                default:
                    //   checkForAllOpenFragments();
                    openSettingFragment();
            }
        } else if (baseResponse instanceof CommunitySuggestion) {

        } else if (baseResponse instanceof CommentReactionDoc) {
            setAllValues(mFragmentOpen);
             /* Comment fragment list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, AppConstants.ONE_CONSTANT);
        }
    }


    @Override
    public List getListData() {
        return mHomeSpinnerItemList;
    }


    @Override
    public void onDrawerOpened() {
      /*  if (!mFragmentOpen.isImageBlur()) {
            assignNavigationRecyclerListView();
            mFragmentOpen.setImageBlur(true);
        }*/
    }

    @Override
    public void onDrawerClosed() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void assignNavigationRecyclerListView() {
        mAdapter = new GenericRecyclerViewAdapter(this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.setSheroesGenericListData(CustomeDataList.makeDrawerItemList());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initHomeViewPagerAndTabs() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        mFragmentOpen.setFeedOpen(true);
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        mTvHome.setText(getString(R.string.ID_FEED));
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.HOME_FRAGMENT, mFeedDetail);
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_feed_full_view, homeFragment, HomeFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    private void initCommunityViewPagerAndTabs() {
        mFragmentOpen.setCommunityOpen(true);
        mTabLayout.setVisibility(View.VISIBLE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(AppConstants.EMPTY_STRING);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(FeaturedFragment.createInstance(), getString(R.string.ID_FEATURED));
        viewPagerAdapter.addFragment(MyCommunitiesFragment.createInstance(), getString(R.string.ID_MY_COMMUNITIES));
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.tv_search_box)
    public void searchButtonClick() {
        Intent intent = new Intent(this, HomeSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.ALL_SEARCH, mFragmentOpen);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_dialog, R.anim.fade_out_dialog);
        //Snackbar.make(mCLMainLayout, "Work in progress", Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_spinner_icon)
    public void openSpinnerOnClick() {
      /*  if (!mFragmentOpen.isOpen()) {
            mHomeSpinnerFragment = new HomeSpinnerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT, (ArrayList<? extends Parcelable>) mHomeSpinnerItemList);
            mHomeSpinnerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_article_card_view, mHomeSpinnerFragment, HomeSpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
            mFragmentOpen.setOpen(true);
        } else {
            onBackPressed();
        }*/
    }

    private void checkForAllOpenFragments() {
        if (mFragmentOpen.isOpen()) {
            onBackPressed();
            mFragmentOpen.setOpen(false);
        }
        if (mFragmentOpen.isArticleFragment()) {
            onBackPressed();
            mFragmentOpen.setArticleFragment(false);
        }
        if (mFragmentOpen.isBookmarkFragment()) {
            onBackPressed();
            mFragmentOpen.setBookmarkFragment(false);
        }
        if (mFragmentOpen.isJobFragment()) {
            onBackPressed();
            mFragmentOpen.setJobFragment(false);
        }
        if (mFragmentOpen.isSettingFragment()) {
            onBackPressed();
            mFragmentOpen.setSettingFragment(false);
        }
    }

    @OnClick(R.id.tv_home)
    public void homeOnClick() {

        checkForAllOpenFragments();
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFragmentOpen.setFeedOpen(true);
        mFragmentOpen.setCommunityOpen(false);
        flFeedFullView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.removeAllTabs();
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
        mTvSpinnerIcon.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        initHomeViewPagerAndTabs();
    }

    @OnClick(R.id.tv_communities)
    public void communityOnClick() {
        liHomeCommunityButtonLayout.setVisibility(View.VISIBLE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_COMMUNITIES));
        checkForAllOpenFragments();
        mFragmentOpen.setCommunityOpen(true);
        mFragmentOpen.setFeedOpen(false);
        flFeedFullView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.removeAllTabs();
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
        mTvSpinnerIcon.setVisibility(View.GONE);
        initCommunityViewPagerAndTabs();
    }

    @OnClick(R.id.iv_footer_button_icon)
    public void commingOnClick() {
        // Snackbar.make(mCLMainLayout, "Comming soon", Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), CreateCommunityPostActivity.class);

        startActivity(intent);
    }


    private void openArticleFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_ARTICLES));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        mFragmentOpen.setArticleFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mTvSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        setAllValues(mFragmentOpen);
        ArticlesFragment articlesFragment = new ArticlesFragment();
        Bundle bundleArticle = new Bundle();
        articlesFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, articlesFragment, ArticlesFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        mTvSpinnerIcon.setVisibility(View.VISIBLE);
    }

    private void openInviteSearch(FeedDetail feedDetail) {
        mFragmentOpen = new FragmentOpen();
        mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FOURTH_CONSTANT);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mTvSpinnerIcon.setVisibility(View.GONE);
        MyCommunityInviteMemberFragment myCommunityInviteMemberFragment = new MyCommunityInviteMemberFragment();
        Bundle bundleInvite = new Bundle();
        bundleInvite.putParcelable(AppConstants.COMMUNITIES_DETAIL, feedDetail);
        myCommunityInviteMemberFragment.setArguments(bundleInvite);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_feed_comments, myCommunityInviteMemberFragment, MyCommunityInviteMemberFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }


    private void openSettingFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        mFragmentOpen.setSettingFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mTvSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvSearchBox.setVisibility(View.GONE);
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setText(R.string.ID_SETTINGS);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        SettingFragment articlesFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        articlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
        mTvSpinnerIcon.setVisibility(View.GONE);
    }

    private void openBookMarkFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFragmentOpen.setBookmarkFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mTvSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvSearchBox.setVisibility(View.GONE);
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setText(R.string.ID_BOOKMARKS);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        setAllValues(mFragmentOpen);
        BookmarksFragment bookmarksFragment = new BookmarksFragment();
        Bundle bundleBookMarks = new Bundle();
        bundleBookMarks.putParcelable(AppConstants.BOOKMARKS, mFeedDetail);
        bookmarksFragment.setArguments(bundleBookMarks);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, bookmarksFragment, BookmarksFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        mTvSpinnerIcon.setVisibility(View.GONE);
    }

    public void openJobFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_JOBS));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        mFragmentOpen.setJobFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mTvSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        setAllValues(mFragmentOpen);
        JobFragment jobFragment = new JobFragment();
        Bundle jobBookMarks = new Bundle();
        jobBookMarks.putParcelable(AppConstants.JOB_FRAGMENT, mFeedDetail);
        jobFragment.setArguments(jobBookMarks);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, jobFragment, JobFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        mTvSpinnerIcon.setVisibility(View.GONE);
    }

    public void openJobLocationFragment() {
        mFragmentOpen.setSettingFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mTvSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvSearchBox.setVisibility(View.GONE);
        mTvSetting.setVisibility(View.GONE);
        mTvSetting.setText(R.string.ID_JOBS);
        mFlHomeFooterList.setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        JobLocationFilter articlesFragment = new JobLocationFilter();
        Bundle bundle = new Bundle();
        articlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
        mTvSpinnerIcon.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isOpen()) {
            mFragmentOpen.setOpen(false);
            getSupportFragmentManager().popBackStackImmediate();
        } else if (mFragmentOpen.isCommentList()) {
            mFragmentOpen.setCommentList(false);
            getSupportFragmentManager().popBackStackImmediate();
            if (mFragmentOpen.isBookmarkFragment()) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((BookmarksFragment) fragment).commentListRefresh(mFeedDetail, AppConstants.ONE_CONSTANT);
                }
            } else {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).commentListRefresh(mFeedDetail, AppConstants.ONE_CONSTANT);
                }
            }
        } else if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
            getSupportFragmentManager().popBackStackImmediate();
        } else if (mFragmentOpen.isArticleFragment()) {
            getSupportFragmentManager().popBackStackImmediate();
            initHomeViewPagerAndTabs();
            setHomeFeedCommunityData();
            mFragmentOpen.setArticleFragment(false);

        } else if (mFragmentOpen.isSettingFragment()) {
            getSupportFragmentManager().popBackStackImmediate();
            setHomeFeedCommunityData();
            mFragmentOpen.setSettingFragment(false);

        } else if (mFragmentOpen.isBookmarkFragment()) {
            getSupportFragmentManager().popBackStackImmediate();
            initHomeViewPagerAndTabs();
            setHomeFeedCommunityData();
            mFragmentOpen.setBookmarkFragment(false);
        } else if (mFragmentOpen.isJobFragment()) {
            getSupportFragmentManager().popBackStackImmediate();
            initHomeViewPagerAndTabs();
            setHomeFeedCommunityData();
            mFragmentOpen.setJobFragment(false);
        } else if (mFragmentOpen.getOpenCommentReactionFragmentFor() == AppConstants.FOURTH_CONSTANT) {
            getSupportFragmentManager().popBackStackImmediate();
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.NO_REACTION_CONSTANT);
        } else {
            finish();
        }
    }

    private void setHomeFeedCommunityData() {
        if (mFragmentOpen.isFeedOpen()) {
            flFeedFullView.setVisibility(View.VISIBLE);
            mTvHome.setText(getString(R.string.ID_FEED));
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
            mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        } else {
            mViewPager.setVisibility(View.VISIBLE);
            mTabLayout.setVisibility(View.VISIBLE);
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
            mTvCommunities.setText(getString(R.string.ID_COMMUNITIY));
            mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        }
        mTvSearchBox.setVisibility(View.VISIBLE);
        mTvSetting.setVisibility(View.GONE);
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPressed();
    }

  /*  @Override
    public void backListener(int id) {

        switch (id) {
            case R.id.id_setting_feedback:
                SettingFeedbackFragment articlesFragment = new SettingFeedbackFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fl_feed_comments, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.id_setting_preferences:
                Intent intent = new Intent(getApplicationContext(), SettingPreferencesActivity.class);
                startActivity(intent);
                break;
            case R.id.id_setting_terms_and_condition:

                SettingTermsAndConditionFragment settingTermsAndConditionFragment = new SettingTermsAndConditionFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fl_feed_comments, settingTermsAndConditionFragment).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.id_setting_about:

                SettingAboutFragment settingAboutFragmentFragment = new SettingAboutFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fl_feed_comments, settingAboutFragmentFragment).addToBackStack(null).commitAllowingStateLoss();
                break;

            default:
                getSupportFragmentManager().popBackStack();

        }
    }*/


    @OnClick(R.id.fab_add_community)
    public void createCommunityButton() {
        Intent intent = new Intent(getApplicationContext(), CreateCommunityActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_home_notification_icon)
    public void notificationClick() {
        // mDrawer.openDrawer(Gravity.LEFT);
        Snackbar.make(mCLMainLayout, "Work in progress", Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen) {
        mFragmentOpen = isFragmentOpen;
        onBackPressed();

    }

    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        mDrawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
        setAllValues(mFragmentOpen);
        super.dataOperationOnClick(baseResponse);
    }

    @Override
    public void setListData(BaseResponse data, boolean isCheked) {
        List<HomeSpinnerItem> localList = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
            HomeSpinnerItem passedHomeItem = (HomeSpinnerItem) data;
            for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                if (homeSpinnerItem.getId().equalsIgnoreCase(passedHomeItem.getId())) {
                    homeSpinnerItem.setChecked(passedHomeItem.isChecked());
                    localList.add(homeSpinnerItem);
                } else {
                    localList.add(homeSpinnerItem);
                }
            }
        }
        mHomeSpinnerItemList.clear();
        mHomeSpinnerItemList.addAll(localList);
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.ONE_CONSTANT);
            setAllValues(mFragmentOpen);
            super.openCommentReactionFragment(mFeedDetail);
        }
    }

    @Override
    public void showNwError() {

    }

    @Override
    public void backListener(int id) {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void settingpreference(int id, List<Section> sections) {

        switch (id) {
            case R.id.tv_setting_feedback:
                SettingFeedbackFragment articlesFragment = new SettingFeedbackFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fl_feed_comments, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
                break;

            case R.id.tv_setting_preferences:

              /*  Gson gson = new Gson();
                String jsonSections = gson.toJson(sections);
                intent.putExtra("Setting_preferences",jsonSections);*/

                Intent intent = new Intent(this, SettingPreferencesActivity.class);
                startActivity(intent);


                break;
            case R.id.tv_setting_terms_and_condition:
                SettingTermsAndConditionFragment settingTermsAndConditionFragment = new SettingTermsAndConditionFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fl_feed_comments, settingTermsAndConditionFragment).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.tv_setting_about:
                SettingAboutFragment settingAboutFragmentFragment = new SettingAboutFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fl_feed_comments, settingAboutFragmentFragment).addToBackStack(null).commitAllowingStateLoss();
                break;

            case R.id.tv_logout:
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
                    mUserPreference.delete();
                }
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
                finish();
                break;

            default:
                getSupportFragmentManager().popBackStack();

        }

    }


    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onShowErrorDialog(String errorReason, int errorFor) {
        switch (errorReason) {
            case AppConstants.CHECK_NETWORK_CONNECTION:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                break;
            default:
                showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }

    @Override
    public void showError(String s, int e) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL && null != intent) {
            mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.HOME_FRAGMENT);
            if (mFragmentOpen.isArticleFragment()) {
                Fragment fragmentArticle = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragmentArticle)) {
                    ((ArticlesFragment) fragmentArticle).commentListRefresh(mFeedDetail, AppConstants.TWO_CONSTANT);
                }
            } else {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).commentListRefresh(mFeedDetail, AppConstants.TWO_CONSTANT);
                }
            }


        }

    }
}