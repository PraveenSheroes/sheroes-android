package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.communities.CommunitySuggestion;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
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
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunityInviteMemberFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFeedbackFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingTermsAndConditionFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;

public class HomeActivity extends BaseActivity implements CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, CommentReactionFragment.HomeActivityIntractionListner, HomeSpinnerFragment.HomeSpinnerFragmentListner {
    //  implements CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener
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
    @Bind(R.id.li_article_spinner_icon)
    public RelativeLayout mliArticleSpinnerIcon;
    @Bind(R.id.tv_catagory_text)
    public TextView mTvCategoryText;
    @Bind(R.id.tv_catagory_choose)
    public TextView mTvCategoryChoose;
    @Bind(R.id.iv_spinner_icon)
    public ImageView mIvSpinner;
    @Bind(R.id.fl_feed_full_view)
    public FrameLayout flFeedFullView;
    @Bind(R.id.iv_side_drawer_profile_blur_background)
    ImageView mIvSideDrawerProfileBlurBackground;
    @Bind(R.id.iv_home_notification_icon)
    TextView mIvHomeNotification;
    @Bind(R.id.fab_add_community)
    FloatingActionButton mFloatingActionButton;
    @Bind(R.id.fab_filter)
    FloatingActionButton mJobFragment;
    @Bind(R.id.li_home_community_button_layout)
    LinearLayout liHomeCommunityButtonLayout;
    GenericRecyclerViewAdapter mAdapter;
    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    private HomeSpinnerFragment mHomeSpinnerFragment;
    private FragmentOpen mFragmentOpen;
    private CustiomActionBarToggle mCustiomActionBarToggle;
    private FeedDetail mFeedDetail;
    private String profile;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private ViewPagerAdapter mViewPagerAdapter;
    private MyCommunityInviteMemberFragment myCommunityInviteMemberFragment;
    private int backPressClickCount = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderHomeFragmentView();
    }

    public void renderHomeFragmentView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustiomActionBarToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        initHomeViewPagerAndTabs();
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
           if(null != mUserPreference.get().getUserSummary().getUserBO()&&StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getUserBO().getCityMaster())) {
               mTvUserLocation.setText(mUserPreference.get().getUserSummary().getUserBO().getCityMaster());
           }
            Glide.with(this)
                    .load(profile).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            Bitmap blurred = BlurrImage.blurRenderScript(HomeActivity.this, profileImage, 10);
                            mIvSideDrawerProfileBlurBackground.setImageBitmap(blurred);
                        }
                    });
        }
    }

    private void setArticleCategoryFilterValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult = mUserPreferenceMasterData.get().getData();
            if (null != masterDataResult && null != masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY)) {
                {
                    HashMap<String, ArrayList<LabelValue>> hashMap = masterDataResult.get(AppConstants.MASTER_DATA_ARTICLE_KEY);
                    List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
                    if (StringUtil.isNotEmptyCollection(labelValueArrayList)) {
                        List<HomeSpinnerItem> homeSpinnerItemList = new ArrayList<>();
                        HomeSpinnerItem first = new HomeSpinnerItem();
                        first.setName(AppConstants.FOR_ALL);
                        homeSpinnerItemList.add(first);
                        for (LabelValue lookingFor : labelValueArrayList) {

                            HomeSpinnerItem homeSpinnerItem = new HomeSpinnerItem();
                            homeSpinnerItem.setId(lookingFor.getValue());
                            homeSpinnerItem.setName(lookingFor.getLabel());
                            homeSpinnerItemList.add(homeSpinnerItem);
                        }
                        mHomeSpinnerItemList = homeSpinnerItemList;
                    }
                }
            }
        }
    }


    private Runnable openDrawerRunnable() {
        return new Runnable() {

            @Override
            public void run() {
                //  mDrawer.openDrawer(Gravity.LEFT);
            }
        };
    }

    @OnClick(R.id.fab_filter)
    public void openJobFilterActivity() {
        Intent intent = new Intent(getApplicationContext(), JobFilterActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_JOB_FILTER);
        overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            int id = view.getId();
            if (id == R.id.tv_community_detail_invite) {
                inviteMyCommunityDialog();
            } else if (id == R.id.tv_add_invite) {
                if (null != mFeedDetail) {
                    if (null != myCommunityInviteMemberFragment) {
                        myCommunityInviteMemberFragment.onAddMemberClick(mFeedDetail);
                    }
                }
            } else {
                mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.ONE_CONSTANT);
                setAllValues(mFragmentOpen);
                setViewPagerAndViewAdapter(mViewPagerAdapter, mViewPager);
                super.feedCardsHandled(view, baseResponse);
            }
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
                    checkForAllOpenFragments();
                    openArticleFragment(setCategoryIds());
                    break;
                case AppConstants.THREE_CONSTANT:
                    checkForAllOpenFragments();
                    openJobFragment();
                    break;
                case AppConstants.FOURTH_CONSTANT:
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
             /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU);
        }
    }


    @Override
    public List getListData() {
        return mHomeSpinnerItemList;
    }


    @Override
    public void onDrawerOpened() {
       /* if (!mFragmentOpen.isImageBlur()) {
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
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new FeaturedFragment(), getString(R.string.ID_FEATURED));
        mViewPagerAdapter.addFragment(new MyCommunitiesFragment(), getString(R.string.ID_MY_COMMUNITIES));
        mViewPager.setAdapter(mViewPagerAdapter);
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
    }

    @OnClick(R.id.li_article_spinner_icon)
    public void openSpinnerOnClick() {
        if (!mFragmentOpen.isOpen()) {
            mFlHomeFooterList.setVisibility(View.GONE);
            if (!StringUtil.isNotEmptyCollection(mHomeSpinnerItemList) && !StringUtil.isNotEmptyCollection(mFragmentOpen.getHomeSpinnerItemList())) {
                setArticleCategoryFilterValues();
                mFragmentOpen.setHomeSpinnerItemList(mHomeSpinnerItemList);
            } else if (StringUtil.isNotEmptyCollection(mFragmentOpen.getHomeSpinnerItemList())) {
                mHomeSpinnerItemList = mFragmentOpen.getHomeSpinnerItemList();
            }
            mFragmentOpen.setArticleFragment(false);
            mHomeSpinnerFragment = new HomeSpinnerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT, (ArrayList<? extends Parcelable>) mHomeSpinnerItemList);
            mHomeSpinnerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_article_card_view, mHomeSpinnerFragment, HomeSpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
            mFragmentOpen.setOpen(true);

        }
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
        flFeedFullView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        //   didTapButton(mTvHome);
        initHomeViewPagerAndTabs();
    }

    @OnClick(R.id.tv_communities)
    public void communityOnClick() {
        // liHomeCommunityButtonLayout.setVisibility(View.VISIBLE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_COMMUNITIES));
        checkForAllOpenFragments();
        mFragmentOpen.setFeedOpen(false);
        flFeedFullView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.VISIBLE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(AppConstants.EMPTY_STRING);
        //  didTapButton(mTvCommunities);
        // if (!mFragmentOpen.isCommunityOpen()) {
        //     mFragmentOpen.setCommunityOpen(true);
        initCommunityViewPagerAndTabs();
        //  }
    }

    public void didTapButton(View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top_slide_anim);
        view.startAnimation(myAnim);
    }

    @OnClick(R.id.iv_footer_button_icon)
    public void createCommunityPostOnClick() {
        // Snackbar.make(mCLMainLayout, "Comming soon", Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), CreateCommunityPostActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST);
        overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
    }


    private void openArticleFragment(List<Long> categoryIds) {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_ARTICLES));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        mFragmentOpen.setArticleFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        setAllValues(mFragmentOpen);
        ArticlesFragment articlesFragment = new ArticlesFragment();
        Bundle bundleArticle = new Bundle();
        bundleArticle.putSerializable(AppConstants.ARTICLE_FRAGMENT, (ArrayList) categoryIds);
        articlesFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .add(R.id.fl_article_card_view, articlesFragment, ArticlesFragment.class.getName()).addToBackStack(ArticlesFragment.class.getName()).commitAllowingStateLoss();
        mliArticleSpinnerIcon.setVisibility(View.VISIBLE);

    }

    public DialogFragment inviteMyCommunityDialog() {
        myCommunityInviteMemberFragment = (MyCommunityInviteMemberFragment) getFragmentManager().findFragmentByTag(MyCommunityInviteMemberFragment.class.getName());
        if (myCommunityInviteMemberFragment == null) {
            myCommunityInviteMemberFragment = new MyCommunityInviteMemberFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            myCommunityInviteMemberFragment.setArguments(bundle);
        }
        if (!myCommunityInviteMemberFragment.isVisible() && !myCommunityInviteMemberFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            myCommunityInviteMemberFragment.show(getFragmentManager(), MyCommunityInviteMemberFragment.class.getName());
        }
        return myCommunityInviteMemberFragment;
    }

    public void updateMyCommunitiesFragment(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
        if (null != myCommunityInviteMemberFragment) {
            myCommunityInviteMemberFragment.dismiss();
        }
        Fragment community = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
        if (AppUtils.isFragmentUIActive(community)) {
            ((MyCommunitiesFragment) community).commentListRefresh(feedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
        }
    }

    private void openSettingFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFragmentOpen.setSettingFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvSearchBox.setVisibility(View.GONE);
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setText(R.string.ID_SETTINGS);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        setAllValues(mFragmentOpen);
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        settingFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, settingFragment).addToBackStack(null).commitAllowingStateLoss();
        mliArticleSpinnerIcon.setVisibility(View.GONE);


    }

    private void openBookMarkFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFragmentOpen.setBookmarkFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
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
        mliArticleSpinnerIcon.setVisibility(View.GONE);
    }

    public void openJobFragment() {
        mJobFragment.setVisibility(View.VISIBLE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_JOBS));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        mFragmentOpen.setJobFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        setAllValues(mFragmentOpen);
        JobFragment jobFragment = new JobFragment();
        Bundle jobBookMarks = new Bundle();
        // jobBookMarks.putSerializable(AppConstants.JOB_FRAGMENT, (ArrayList) categoryIds);
        jobFragment.setArguments(jobBookMarks);
        jobFragment.setArguments(jobBookMarks);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, jobFragment, JobFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        mliArticleSpinnerIcon.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isOpen()) {
            mFlHomeFooterList.setVisibility(View.VISIBLE);
            mFragmentOpen.setOpen(false);
            mFragmentOpen.setArticleFragment(true);
            getSupportFragmentManager().popBackStack();
        }
         /*1:- For refresh list if value pass One Home activity means its comment section changes of activity*/
        else if (mFragmentOpen.isCommentList()) {
            mFragmentOpen.setCommentList(false);
            getSupportFragmentManager().popBackStackImmediate();
            if (mFragmentOpen.isBookmarkFragment()) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((BookmarksFragment) fragment).commentListRefresh(mFeedDetail, COMMENT_REACTION);
                }
            } else {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).commentListRefresh(mFeedDetail, COMMENT_REACTION);
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
            initHomeViewPagerAndTabs();
            setHomeFeedCommunityData();
            mFragmentOpen.setSettingFragment(false);

        } else if (mFragmentOpen.isBookmarkFragment()) {
            if (mFragmentOpen.isOpenImageViewer()) {
                mFragmentOpen.setOpenImageViewer(false);
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                getSupportFragmentManager().popBackStackImmediate();
                initHomeViewPagerAndTabs();
                setHomeFeedCommunityData();
                mFragmentOpen.setBookmarkFragment(false);
            }
        } else if (mFragmentOpen.isJobFragment()) {
            mJobFragment.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStackImmediate();
            initHomeViewPagerAndTabs();
            setHomeFeedCommunityData();
            mFragmentOpen.setJobFragment(false);
        } else if (mFragmentOpen.isOpenImageViewer()) {
            mFragmentOpen.setOpenImageViewer(false);
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            if (backPressClickCount == 0) {
                finish();
            } else {
                backPressClickCount--;
                Snackbar.make(mCLMainLayout, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void setHomeFeedCommunityData() {
        if (mFragmentOpen.isFeedOpen()) {
            flFeedFullView.setVisibility(View.VISIBLE);
            mTvHome.setText(getString(R.string.ID_FEED));
            mliArticleSpinnerIcon.setVisibility(View.GONE);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
            mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        } else {
            mViewPager.setVisibility(View.VISIBLE);
            mTabLayout.setVisibility(View.VISIBLE);
            mliArticleSpinnerIcon.setVisibility(View.GONE);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
            mTvCommunities.setText(getString(R.string.ID_COMMUNITIY));
            mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        }
      //  mTvSearchBox.setVisibility(View.VISIBLE);
        mTvSetting.setVisibility(View.GONE);
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPressed();
    }

    @OnClick(R.id.fab_add_community)
    public void createCommunityButton() {
        Intent intent = new Intent(getApplicationContext(), CreateCommunityActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY);
        overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
    }

    @OnClick(R.id.iv_home_notification_icon)
    public void notificationClick() {
        // mDrawer.openDrawer(Gravity.LEFT);
        Snackbar.make(mCLMainLayout, "Work in progress", Snackbar.LENGTH_SHORT).show();
    }


    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        //  mDrawer.openDrawer(Gravity.LEFT);
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
            if (passedHomeItem.getName().equalsIgnoreCase(AppConstants.FOR_ALL)) {
                for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                    homeSpinnerItem.setChecked(passedHomeItem.isChecked());
                    localList.add(homeSpinnerItem);
                }
            } else {
                for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                    if (homeSpinnerItem.getId() == (passedHomeItem.getId())) {
                        homeSpinnerItem.setChecked(passedHomeItem.isChecked());
                        localList.add(homeSpinnerItem);
                    } else {
                        localList.add(homeSpinnerItem);
                    }
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

    public void settingListItemSelected(int id) {
        switch (id) {
            case R.id.tv_setting_feedback:
                SettingFeedbackFragment articlesFragment = new SettingFeedbackFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fl_feed_comments, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
                break;
            case R.id.tv_setting_preferences:
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
                    Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent1);
                    finish();
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL:
                    articleDetailActivityResponse(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL:
                    communityDetailActivityResponse(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_JOB_DETAIL:
                    jobDetailActivityResponse(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY:
                    createCommunityActivityResponse(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_JOB_FILTER:
                    jobFilterActivityResponse(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    editCommunityPostResponse(intent);
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        }

    }

    private void editCommunityPostResponse(Intent intent) {
        mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITY_POST_FRAGMENT);
        if (null != mFeedDetail) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (mFeedDetail.isFromHome()) {
                    homeOnClick();
                } else {
                    ((HomeFragment) fragment).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                }
            }
        } else {
            homeOnClick();
        }
    }

    private void jobFilterActivityResponse(Intent intent) {
        FeedRequestPojo feedRequestPojo = (FeedRequestPojo) intent.getExtras().get(AppConstants.JOB_FILTER);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(JobFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((JobFragment) fragment).jobFilterIds(feedRequestPojo);
        }
    }

    private void createCommunityActivityResponse(Intent intent) {
        mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL);
        Fragment community = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
        if (AppUtils.isFragmentUIActive(community)) {
            if (null != mFeedDetail) {
                ((MyCommunitiesFragment) community).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            } else {
                communityOnClick();
            }
        }
    }

    private void articleDetailActivityResponse(Intent intent) {
        mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.HOME_FRAGMENT);
        if (mFragmentOpen.isArticleFragment()) {
            Fragment fragmentArticle = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragmentArticle)) {
                ((ArticlesFragment) fragmentArticle).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
        }
    }

    private void jobDetailActivityResponse(Intent intent) {
        mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.JOB_FRAGMENT);
        if (mFragmentOpen.isJobFragment()) {
            Fragment fragmentJob = getSupportFragmentManager().findFragmentByTag(JobFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragmentJob)) {
                ((JobFragment) fragmentJob).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
        }
    }

    private void communityDetailActivityResponse(Intent intent) {
        mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL);
        CommunityEnum communityEnum = (CommunityEnum) intent.getExtras().get(AppConstants.MY_COMMUNITIES_FRAGMENT);
        if (null != communityEnum) {
            switch (communityEnum) {
                case FEATURE_COMMUNITY:
                    Fragment feature = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(feature)) {
                        if (mFeedDetail.isFeatured() && mFeedDetail.isMember()) {
                            communityOnClick();
                        } else {
                            ((FeaturedFragment) feature).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                        }
                    }
                    break;
                case MY_COMMUNITY:
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCallFromName()) && mFeedDetail.getCallFromName().equalsIgnoreCase(AppConstants.FEATURE_FRAGMENT)) {
                        communityOnClick();
                    } else {
                        Fragment community = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
                        if (AppUtils.isFragmentUIActive(community)) {
                            ((MyCommunitiesFragment) community).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                        }
                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
            }
        }
    }


    @Override
    public void onCancelDone(int pressedEvent) {
        if (AppConstants.ONE_CONSTANT == pressedEvent) {
            getSupportFragmentManager().popBackStack(ArticlesFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            onBackPressed();
            mTvCategoryChoose.setVisibility(View.GONE);
            StringBuilder stringBuilder = new StringBuilder();
            mFragmentOpen.setHomeSpinnerItemList(mHomeSpinnerItemList);
            if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
                List<Long> categoryIds = new ArrayList<>();
                List<HomeSpinnerItem> localList = new ArrayList<>();
                for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                    if (homeSpinnerItem.isChecked()) {
                        categoryIds.add(homeSpinnerItem.getId());
                        if (!homeSpinnerItem.getName().equalsIgnoreCase(AppConstants.FOR_ALL)) {
                            stringBuilder.append(homeSpinnerItem.getName());
                            stringBuilder.append(AppConstants.COMMA);
                        }
                        homeSpinnerItem.setDone(true);
                    } else {
                        homeSpinnerItem.setDone(false);
                    }
                    localList.add(homeSpinnerItem);
                }
                if (StringUtil.isNotEmptyCollection(localList)) {
                    mHomeSpinnerItemList.clear();
                    mHomeSpinnerItemList.addAll(localList);
                }
                if (StringUtil.isNotNullOrEmptyString(stringBuilder.toString())) {
                    String total = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
                    if (total.length() > 25) {
                        total = stringBuilder.toString().substring(0, 25);
                        mTvCategoryText.setText(total + AppConstants.DOTS);
                    } else {
                        mTvCategoryText.setText(total);
                    }
                } else {
                    mTvCategoryText.setText(AppConstants.EMPTY_STRING);
                    mTvCategoryChoose.setVisibility(View.VISIBLE);
                }
                openArticleFragment(categoryIds);
            }
        } else {
            mHomeSpinnerItemList = mFragmentOpen.getHomeSpinnerItemList();
            List<HomeSpinnerItem> localList = new ArrayList<>();
            for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                if (homeSpinnerItem.isDone()) {
                    homeSpinnerItem.setChecked(true);
                } else {
                    homeSpinnerItem.setChecked(false);
                }
                localList.add(homeSpinnerItem);
            }
            if (StringUtil.isNotEmptyCollection(localList)) {
                mHomeSpinnerItemList.clear();
                mHomeSpinnerItemList.addAll(localList);
            }
            onBackPressed();
        }
    }

    private List<Long> setCategoryIds() {
        List<Long> categoryIds = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
            for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                if (homeSpinnerItem.isChecked()) {
                    categoryIds.add(homeSpinnerItem.getId());
                }
            }
        }
        return categoryIds;
    }


    public void onJoinEventSuccessResult(String result, FeedDetail feedDetail) {
        if (StringUtil.isNotNullOrEmptyString(result)) {
            if (result.equalsIgnoreCase(AppConstants.SUCCESS)) {
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (fragment instanceof FeaturedFragment) {
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((FeaturedFragment) fragment).setJoinStatus(result, feedDetail);
                    }
                }
            } else {
                onShowErrorDialog(result, JOIN_INVITE);
            }
        }
    }

}