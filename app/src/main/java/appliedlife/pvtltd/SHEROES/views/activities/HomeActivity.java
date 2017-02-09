package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.communities.CommunitySuggestion;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.CustomeDataList;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ImageFullViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.Blurry;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeSpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageFullViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFeedbackFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingTermsAndConditionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeFragment.HomeActivityIntractionListner, SettingView, BaseHolderInterface, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, CommentReactionFragment.HomeActivityIntractionListner, View.OnTouchListener, View.OnClickListener, ImageFullViewAdapter.HomeActivityIntraction {
    private final String TAG = LogUtils.makeLogTag(HomeActivity.class);
    private static final String SPINNER_FRAGMENT = "spinnerFragment";
    @Bind(R.id.iv_drawer_profile_circle_icon)
    CircleImageView ivDrawerProfileCircleIcon;
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
    @Bind(R.id.tv_join_view)
    TabLayout mTabLayout;
    @Bind(R.id.fl_home_footer_list)
    public FrameLayout mFlHomeFooterList;
    @Bind(R.id.iv_home_search_icon)
    ImageView mIvSearchIcon;
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
    @Bind(R.id.fab_add_community)
    FloatingActionButton mFloatingActionButton;
    @Bind(R.id.li_home_community_button_layout)
    LinearLayout liHomeCommunityButtonLayout;
    TextView mTvFeedArticleUserReaction, mTvFeedCommunityUserReaction, mTvFeedCommunityPostUserReaction;
    GenericRecyclerViewAdapter mAdapter;
    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    private HomeSpinnerFragment mHomeSpinnerFragment;
    private FragmentOpen mFragmentOpen;
    private CustiomActionBarToggle mCustiomActionBarToggle;
    public View mArticlePopUp, mCommunityPopUp, mCommunityPostPopUp;
    Spinner mSpaboutCommunityspn;
    String[] arraySpinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderHomeFragmentView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        assignNavigationRecyclerListView();
    }

    public void renderHomeFragmentView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustiomActionBarToggle);
        ivDrawerProfileCircleIcon.setCircularImage(true);
        //TODO: this data to be removed
        String profile = "https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAhNAAAAJDYwZWIyZTg5LWFmOTItNGIwYS05YjQ5LTM2YTRkNGQ2M2JlNw.jpg";
        ivDrawerProfileCircleIcon.bindImage(profile);
        mTvUserName.setText("Praveen Singh");
        mTvUserLocation.setText("Delhi, India");
        mCustiomActionBarToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mFragmentOpen = new FragmentOpen(false, false, false, false, false, false, false, false);
        initHomeViewPagerAndTabs();
        mHomeSpinnerItemList = CustomeDataList.makeSpinnerListRequest();
        Glide.with(this)
                .load(profile)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(mIvSideDrawerProfileBlurBackground);
        //  HomeSpinnerFragment frag = new HomeSpinnerFragment();
        //  callFirstFragment(R.id.fl_fragment_container, frag);
    }

    @Override
    public void onErrorOccurence() {
        showNetworkTimeoutDoalog(true);
    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof ListOfFeed) {
            ListOfFeed listOfFeed = (ListOfFeed) baseResponse;
            feedCardsHandled(view, listOfFeed);
        } else if (baseResponse instanceof HomeSpinnerItem) {
            String spinnerHeaderName = ((HomeSpinnerItem) baseResponse).getName();
            if (StringUtil.isNotNullOrEmptyString(spinnerHeaderName)) {
                mTvSpinnerIcon.setText(spinnerHeaderName);
                mFragmentOpen.setOpen(true);
            }
            onBackPressed();
        } else if (baseResponse instanceof DrawerItems) {
            int drawerItem = ((DrawerItems) baseResponse).getId();
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            }
            switch (drawerItem) {
                case 1:
                    break;
                case 2:
                    openArticleFragment();

                    break;
                case 5:
                    openSettingFragment();

                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + drawerItem);
            }
        } else if (baseResponse instanceof CommunitySuggestion) {

        } else if (baseResponse instanceof  ArticleCardResponse) {
            ArticleCardResponse articleCardResponse = (ArticleCardResponse) baseResponse;
            articleCardsHandled(view, articleCardResponse);
        }
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
        if (baseResponse instanceof ListOfFeed) {
            ListOfFeed listOfFeed = (ListOfFeed) baseResponse;
            openImageFullViewFragment(listOfFeed);
        }
    }
    private void articleCardsHandled(View view, ArticleCardResponse articleCardResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_feed_article_single_image:
                ArticleDetailActivity.navigateFromArticle(this, view, articleCardResponse);
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    private void feedCardsHandled(View view, ListOfFeed listOfFeed) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_article_join_conversation:
                openCommentReactionFragment();
                break;
            case R.id.li_feed_community_join_conversation:
                openCommentReactionFragment();
                break;
            case R.id.li_feed_community_post_join_conversation:
                openCommentReactionFragment();
                break;
            case R.id.tv_feed_article_user_reaction:
                mArticlePopUp = findViewById(R.id.li_feed_article_card_emoji_pop_up);
                if (mArticlePopUp.getVisibility() == View.VISIBLE) {
                    mArticlePopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mArticlePopUp);
                } else {
                    mTvFeedArticleUserReaction = (TextView) findViewById(R.id.tv_feed_article_user_reaction);
                    TextView tvArticleReaction = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction);
                    TextView tvArticleReaction1 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction1);
                    TextView tvArticleReaction2 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction2);
                    TextView tvArticleReaction3 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction3);
                    TextView tvArticleReaction4 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction4);
                    mArticlePopUp.setOnTouchListener(this);
                    tvArticleReaction.setOnClickListener(this);
                    tvArticleReaction1.setOnClickListener(this);
                    tvArticleReaction2.setOnClickListener(this);
                    tvArticleReaction3.setOnClickListener(this);
                    tvArticleReaction4.setOnClickListener(this);
                    mArticlePopUp.setVisibility(View.VISIBLE);
                    showUserReactionOption(mArticlePopUp);
                }
                break;
            case R.id.tv_feed_community_user_reaction:
                mCommunityPopUp = findViewById(R.id.li_feed_community_emoji_pop_up);
                if (mCommunityPopUp.getVisibility() == View.VISIBLE) {
                    mCommunityPopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mCommunityPopUp);
                } else {
                    mTvFeedCommunityUserReaction = (TextView) findViewById(R.id.tv_feed_community_user_reaction);
                    TextView tvCommunityReaction = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction);
                    TextView tvCommunityReaction1 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction1);
                    TextView tvCommunityReaction2 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction2);
                    TextView tvCommunityReaction3 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction3);
                    TextView tvCommunityReaction4 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction4);
                    mCommunityPopUp.setOnTouchListener(this);
                    tvCommunityReaction.setOnClickListener(this);
                    tvCommunityReaction1.setOnClickListener(this);
                    tvCommunityReaction2.setOnClickListener(this);
                    tvCommunityReaction3.setOnClickListener(this);
                    tvCommunityReaction4.setOnClickListener(this);
                    mCommunityPopUp.setVisibility(View.VISIBLE);
                    showUserReactionOption(mCommunityPopUp);
                }
                break;
            case R.id.tv_feed_community_post_user_reaction:
                mCommunityPostPopUp = findViewById(R.id.li_feed_community_user_post_emoji_pop_up);
                if (mCommunityPostPopUp.getVisibility() == View.VISIBLE) {
                    mCommunityPostPopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mCommunityPostPopUp);
                } else {
                    mTvFeedCommunityPostUserReaction = (TextView) findViewById(R.id.tv_feed_community_post_user_reaction);
                    mCommunityPostPopUp = findViewById(R.id.li_feed_community_user_post_emoji_pop_up);
                    TextView tvCommunityPostReaction = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction);
                    TextView tvCommunityPostReaction1 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction1);
                    TextView tvCommunityPostReaction2 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction2);
                    TextView tvCommunityPostReaction3 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction3);
                    TextView tvCommunityPostReaction4 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction4);
                    mCommunityPostPopUp.setOnTouchListener(this);
                    tvCommunityPostReaction.setOnClickListener(this);
                    tvCommunityPostReaction1.setOnClickListener(this);
                    tvCommunityPostReaction2.setOnClickListener(this);
                    tvCommunityPostReaction3.setOnClickListener(this);
                    tvCommunityPostReaction4.setOnClickListener(this);
                    mCommunityPostPopUp.setVisibility(View.VISIBLE);
                    showUserReactionOption(mCommunityPostPopUp);
                }
                break;
            case R.id.tv_feed_article_user_comment:
                openCommentReactionFragment();
                break;
            case R.id.tv_feed_community_user_comment:
                openCommentReactionFragment();
                break;
            case R.id.tv_feed_community_post_user_comment:
                openCommentReactionFragment();
                break;
            case R.id.li_feed_article_images:
                CommunitiesDetailActivity.navigate(this, view, listOfFeed);
                break;
            case R.id.li_feed_community_images:
                CommunitiesDetailActivity.navigate(this, view, listOfFeed);
                break;
            case R.id.li_feed_community_user_post_images:
                CommunitiesDetailActivity.navigate(this, view, listOfFeed);
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }


    private void openImageFullViewFragment(ListOfFeed listOfFeed ) {
        ImageFullViewFragment imageFullViewFragment = new ImageFullViewFragment();
        Bundle bundle = new Bundle();
        mFragmentOpen.setCommentList(true);
        bundle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
        bundle.putParcelable(AppConstants.IMAGE_FULL_VIEW,listOfFeed);
        imageFullViewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.fl_feed_comments, imageFullViewFragment, SPINNER_FRAGMENT).addToBackStack(null).commitAllowingStateLoss();

    }
    private void openCommentReactionFragment() {
        CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
        Bundle bundleArticle = new Bundle();
        mFragmentOpen.setCommentList(true);
        bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
        commentReactionFragmentForArticle.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.fl_feed_comments, commentReactionFragmentForArticle, SPINNER_FRAGMENT).addToBackStack(null).commitAllowingStateLoss();

    }

    private void showUserReactionOption(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    private void dismissUserReactionOption(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        viewToAnimate.clearAnimation();
        animation.setFillAfter(false);
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
    public List getListData() {
        return mHomeSpinnerItemList;
    }


    @Override
    public void onDrawerOpened() {
        if (!mFragmentOpen.isImageBlur()) {
            Blurry.with(HomeActivity.this)
                    .radius(25)
                    .sampling(2)
                    .async()
                    .capture(mIvSideDrawerProfileBlurBackground)
                    .into(mIvSideDrawerProfileBlurBackground);
            mFragmentOpen.setImageBlur(true);
        }
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
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setSheroesGenericListData(CustomeDataList.makeDrawerItemList());
    }

    private void initHomeViewPagerAndTabs() {
        String search = getString(R.string.ID_SEARCH) + AppConstants.FEED_ARTICLE + AppConstants.S + AppConstants.COMMA + AppConstants.FEED_COMMUNITY + AppConstants.COMMA + AppConstants.FEED_JOB;
        mTvSearchBox.setText(search);
        mFragmentOpen.setFeedOpen(true);
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        mTvHome.setText(getString(R.string.ID_FEED));
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_feed_full_view, homeFragment, SPINNER_FRAGMENT).addToBackStack(null).commitAllowingStateLoss();

    }

    private void initCommunityViewPagerAndTabs() {
        mFragmentOpen.setCommunityOpen(true);
        mTabLayout.setVisibility(View.VISIBLE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIY));
        mTvHome.setText(AppConstants.EMPTY_STRING);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(FeaturedFragment.createInstance(4), getString(R.string.ID_FEATURED));
        viewPagerAdapter.addFragment(MyCommunitiesFragment.createInstance(4), getString(R.string.ID_MY_COMMUNITIES));
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.rl_search_box)
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
        if (!mFragmentOpen.isOpen()) {
            mHomeSpinnerFragment = new HomeSpinnerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT, (ArrayList<? extends Parcelable>) mHomeSpinnerItemList);
            mHomeSpinnerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fragment_home_spinner, mHomeSpinnerFragment, SPINNER_FRAGMENT).addToBackStack(null).commitAllowingStateLoss();
            mFragmentOpen.setOpen(true);
        } else {
            onBackPressed();
        }
    }

    @OnClick(R.id.tv_home)
    public void homeOnClick() {
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        if (mFragmentOpen.isArticleFragment()) {
            onBackPressed();
            mFragmentOpen.setArticleFragment(false);
        }
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
        String search = getString(R.string.ID_SEARCH) + AppConstants.FEED_ARTICLE + AppConstants.S + AppConstants.COMMA + AppConstants.FEED_COMMUNITY + AppConstants.COMMA + AppConstants.FEED_JOB;
        mTvSearchBox.setText(search);
    }

    @OnClick(R.id.tv_communities)
    public void communityOnClick() {
        liHomeCommunityButtonLayout.setVisibility(View.VISIBLE);
        String search = getString(R.string.ID_SEARCH) + AppConstants.SPACE + getString(R.string.ID_COMMUNITIES);
        mTvSearchBox.setText(search);
        if (mFragmentOpen.isArticleFragment()) {
            onBackPressed();
            mFragmentOpen.setArticleFragment(false);
        }
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

        mFragmentOpen.setArticleFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mTvSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        ArticlesFragment articlesFragment = new ArticlesFragment();
        Bundle bundle = new Bundle();
        articlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, articlesFragment, SPINNER_FRAGMENT).addToBackStack(null).commitAllowingStateLoss();
        mTvSpinnerIcon.setVisibility(View.VISIBLE);
    }


    private void openSettingFragment() {

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

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isOpen()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setOpen(false);
        } else if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            mFragmentOpen.setCommentList(false);
        } else if (mFragmentOpen.isReactionList()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
        } else if (mFragmentOpen.isArticleFragment()) {
            getSupportFragmentManager().popBackStack();
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
            mFragmentOpen.setArticleFragment(false);


        } else if (mFragmentOpen.isSettingFragment()) {
            getSupportFragmentManager().popBackStack();
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
            mFragmentOpen.setSettingFragment(false);

        } else {
            finish();
        }
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen) {
        mFragmentOpen = isFragmentOpen;
        onBackPressed();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen) {
        mFragmentOpen = isFragmentOpen;
        if (mFragmentOpen.isReactionList()) {
            CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
            Bundle bundleArticle = new Bundle();
            bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
            commentReactionFragmentForArticle.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.fl_feed_comments, commentReactionFragmentForArticle, SPINNER_FRAGMENT).addToBackStack(null).commitAllowingStateLoss();
        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_article_card_emoji_pop_up:
                if (null != mArticlePopUp) {
                    mArticlePopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mArticlePopUp);
                }
                break;
            case R.id.li_feed_community_emoji_pop_up:
                if (null != mCommunityPopUp) {
                    mCommunityPopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mCommunityPopUp);
                }
                break;
            case R.id.li_feed_community_user_post_emoji_pop_up:
                if (null != mCommunityPostPopUp) {
                    mCommunityPostPopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mCommunityPostPopUp);
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_reaction:
                if (null != mTvFeedArticleUserReaction) {
                    mTvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_heart_active), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                if (null != mTvFeedCommunityUserReaction) {
                    mTvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_heart_active), null, null, null);
                    if (null != mCommunityPopUp) {
                        mCommunityPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPopUp);
                    }
                }
                if (null != mTvFeedCommunityPostUserReaction) {
                    mTvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_heart_active), null, null, null);
                    if (null != mCommunityPostPopUp) {
                        mCommunityPostPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPostPopUp);
                    }
                }

                break;
            case R.id.tv_reaction1:
                if (null != mTvFeedArticleUserReaction) {
                    mTvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji3_whistel), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                if (null != mTvFeedCommunityUserReaction) {
                    mTvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji3_whistel), null, null, null);
                    if (null != mCommunityPopUp) {
                        mCommunityPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPopUp);
                    }
                }
                if (null != mTvFeedCommunityPostUserReaction) {
                    mTvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji3_whistel), null, null, null);
                    if (null != mCommunityPostPopUp) {
                        mCommunityPostPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPostPopUp);
                    }
                }

                break;
            case R.id.tv_reaction2:
                if (null != mTvFeedArticleUserReaction) {
                    mTvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji_xo_xo), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                if (null != mTvFeedCommunityUserReaction) {
                    mTvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji_xo_xo), null, null, null);
                    if (null != mCommunityPopUp) {
                        mCommunityPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPopUp);
                    }
                }
                if (null != mTvFeedCommunityPostUserReaction) {
                    mTvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji_xo_xo), null, null, null);
                    if (null != mCommunityPostPopUp) {
                        mCommunityPostPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPostPopUp);
                    }
                }
                break;
            case R.id.tv_reaction3:
                if (null != mTvFeedArticleUserReaction) {
                    mTvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji2_with_you), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                if (null != mTvFeedCommunityUserReaction) {
                    mTvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji2_with_you), null, null, null);
                    if (null != mCommunityPopUp) {
                        mCommunityPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPopUp);
                    }
                }
                if (null != mTvFeedCommunityPostUserReaction) {
                    mTvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji2_with_you), null, null, null);
                    if (null != mCommunityPostPopUp) {
                        mCommunityPostPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPostPopUp);
                    }
                }
                break;
            case R.id.tv_reaction4:
                if (null != mTvFeedArticleUserReaction) {
                    mTvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji4_face_palm), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                if (null != mTvFeedCommunityUserReaction) {
                    mTvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji4_face_palm), null, null, null);
                    if (null != mCommunityPopUp) {
                        mCommunityPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPopUp);
                    }
                }
                if (null != mTvFeedCommunityPostUserReaction) {
                    mTvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji4_face_palm), null, null, null);
                    if (null != mCommunityPostPopUp) {
                        mCommunityPostPopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mCommunityPostPopUp);
                    }
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
    }


    @Override
    public void showNwError() {

    }

    @Override
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
    public void showError(String s) {

    }
    @OnClick(R.id.fab_add_community)
    public void createCommunityButton() {
        Intent intent = new Intent(getApplicationContext(), CreateCommunityActivity.class);
        startActivity(intent);
    }
}
