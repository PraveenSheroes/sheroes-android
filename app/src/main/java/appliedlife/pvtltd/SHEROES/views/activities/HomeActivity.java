package appliedlife.pvtltd.SHEROES.views.activities;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorDetailItem;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.CustomeDataList;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.JobFilterDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FAQSFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ICCMemberListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ChallengeSuccessDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ChallengeUpdateProgressDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EventDetailDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.JobLocationSearchDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MyCommunityInviteMemberDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.PublicProfileGrowthBuddiesDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;

public class HomeActivity extends BaseActivity implements CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, CommentReactionFragment.HomeActivityIntractionListner, ArticleCategorySpinnerFragment.HomeSpinnerFragmentListner {
    private final String TAG = LogUtils.makeLogTag(HomeActivity.class);
    private static final String SCREEN_LABEL = "Home Screen";
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Bind(R.id.iv_drawer_profile_circle_icon)
    RoundedImageView ivDrawerProfileCircleIcon;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.tv_user_location)
    TextView mTvUserLocation;
    @Bind(R.id.cl_main_layout)
    CoordinatorLayout mCLMainLayout;
    @Bind(R.id.home_toolbar)
    public Toolbar mToolbar;
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
    @Bind(R.id.tv_search_box)
    TextView mTvSearchBox;
    @Bind(R.id.tv_setting)
    TextView mTvSetting;
    @Bind(R.id.tv_job_home)
    TextView mTvJob;
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
    @Bind(R.id.appbar_layout)
    public AppBarLayout mAppBarLayout;
    @Bind(R.id.fl_article_card_view)
    public FrameLayout flFeedFullView;
    @Bind(R.id.iv_side_drawer_profile_blur_background)
    ImageView mIvSideDrawerProfileBlurBackground;
    @Bind(R.id.iv_home_notification_icon)
    TextView mIvHomeNotification;
    @Bind(R.id.tv_notification_read_count)
    public TextView mTvNotificationReadCount;
    @Bind(R.id.fl_notification)
    FrameLayout mFlNotification;
    @Bind(R.id.fab_filter)
    public FloatingActionButton mFloatActionBtn;
    @Bind(R.id.fl_notification_read_count)
    public FrameLayout flNotificationReadCount;
    @Bind(R.id.title_text)
    TextView mTitleText;
    @Bind(R.id.ic_sheroes)
    ImageView mICSheroes;
    private JobFilterDialogFragment jobFilterDailogFragment;
    GenericRecyclerViewAdapter mAdapter;
    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    private ArticleCategorySpinnerFragment mArticleCategorySpinnerFragment;
    private FragmentOpen mFragmentOpen;
    private CustiomActionBarToggle mCustiomActionBarToggle;
    private FeedDetail mFeedDetail;
    private String profile;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private ViewPagerAdapter mViewPagerAdapter;
    private MyCommunityInviteMemberDialogFragment myCommunityInviteMemberDialogFragment;
    boolean doubleBackToExitPressedOnce = false;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    @Inject
    AppUtils mAppUtils;
    public ChallengeSuccessDialogFragment mChallengeSuccessDialogFragment;
    private long mChallengeId;
    private long mEventId;
    private String mHelpLineChat;
    private EventDetailDialogFragment eventDetailDialogFragment;
    private ProgressDialog mProgressDialog;
    private boolean isInviteReferral;
    private PublicProfileGrowthBuddiesDialogFragment mPublicProfileGrowthBuddiesDialogFragment;
    private BellNotificationDialogFragment bellNotificationDialogFragment;
    private JobLocationSearchDialogFragment jobLocationSearchDialogFragment;
    private List<String> mJobLocationList = new ArrayList<>();
    public List<String> mListOfOpportunity = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        long timeSpentFeed = System.currentTimeMillis();
        moEngageUtills.entityMoEngageViewFeed(this, mMoEHelper, payloadBuilder, timeSpentFeed);
        renderHomeFragmentView();
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && false != mUserPreference.get().isSheUser() && startedFirstTime()) {
            openHelplineFragment();
            mTitleText.setText(getString(R.string.ID_APP_NAME));
            mTitleText.setVisibility(View.VISIBLE);
            mICSheroes.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mHelpLineChat) && mHelpLineChat.equalsIgnoreCase(AppConstants.HELPLINE_CHAT)) {
            handleHelpLineFragmentFromDeepLinkAndLoading();
        }

        if (getIntent() != null) {
            if (CommonUtil.isNotEmpty(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(ArticlesFragment.SCREEN_LABEL)){
                    openArticleFragment(setCategoryIds(), false);
                }

                if(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(JobFragment.SCREEN_LABEL)){
                    openJobFragment();
                }
            }
            if (CommonUtil.isNotEmpty(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase("Community List")){
                    communityOnClick();
                }
            }

            if (CommonUtil.isNotEmpty(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if(getIntent().getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(PublicProfileGrowthBuddiesDialogFragment.SCREEN_LABEL)){
                    growthBuddiesInPublicProfile();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInviteReferral) {
            if (null != mProgressDialog) {
                mProgressDialog.dismiss();
            }
            isInviteReferral = false;
        } else {
            setProfileImage();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(ArticlesFragment.SCREEN_LABEL)){
                    if(mFragmentOpen.isCommentList()){
                        getSupportFragmentManager().popBackStack();
                    }
                    openArticleFragment(setCategoryIds(), false);
                }
            }
            if (CommonUtil.isNotEmpty(intent.getStringExtra(AppConstants.HELPLINE_CHAT)) && intent.getStringExtra(AppConstants.HELPLINE_CHAT).equalsIgnoreCase(AppConstants.HELPLINE_CHAT)) {
                if(mFragmentOpen.isCommentList()){
                    getSupportFragmentManager().popBackStack();
                }
                handleHelpLineFragmentFromDeepLinkAndLoading();
            }

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase("Community List")){
                    if(mFragmentOpen.isCommentList()){
                        getSupportFragmentManager().popBackStack();
                    }
                    communityOnClick();
                }
            }

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(JobFragment.SCREEN_LABEL)){
                    if(mFragmentOpen.isCommentList()){
                        getSupportFragmentManager().popBackStack();
                    }
                    openJobFragment();
                }
            }

            if (CommonUtil.isNotEmpty(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT))) {
                if(intent.getStringExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT).equalsIgnoreCase(PublicProfileGrowthBuddiesDialogFragment.SCREEN_LABEL)){
                    if(mFragmentOpen.isCommentList()){
                        getSupportFragmentManager().popBackStack();
                    }
                    growthBuddiesInPublicProfile();
                }
            }
        }
    }

    private boolean startedFirstTime() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            if (getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void renderHomeFragmentView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mICSheroes.setVisibility(View.VISIBLE);
        mTitleText.setVisibility(View.GONE);
        mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);
        mDrawer.addDrawerListener(mCustiomActionBarToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        if (null != getIntent() && null != getIntent().getExtras()) {
            if (getIntent().getExtras().get(AppConstants.CHALLENGE_ID) != null) {
                mChallengeId = getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID);
            }
            if (getIntent().getExtras().get(AppConstants.HELPLINE_CHAT) != null) {
                mHelpLineChat = getIntent().getExtras().getString(AppConstants.HELPLINE_CHAT);
            }
            if (getIntent().getExtras().get(AppConstants.EVENT_ID) != null) {
                mEventId = getIntent().getExtras().getLong(AppConstants.EVENT_ID);
            }
        }
        mFloatActionBtn.setTag(AppConstants.FEED_SUB_TYPE);
        initHomeViewPagerAndTabs();
        assignNavigationRecyclerListView();
        if (mEventId > 0) {
            eventDetailDialog(mEventId);
        }
    }

    private void setProfileImage() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            profile = mUserPreference.get().getUserSummary().getPhotoUrl();
            Glide.with(this)
                    .load(profile)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivDrawerProfileCircleIcon);
            mTvUserName.setText(mUserPreference.get().getUserSummary().getFirstName() + AppConstants.SPACE + mUserPreference.get().getUserSummary().getLastName());
            if (null != mUserPreference.get().getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getUserBO().getCityMaster())) {
                mTvUserLocation.setText(mUserPreference.get().getUserSummary().getUserBO().getCityMaster());
            }
            if (false != mUserPreference.get().isSheUser() && mUserPreference.get().getUserSummary().getEmailId() != null) {
                mTvUserLocation.setText(mUserPreference.get().getUserSummary().getEmailId());
            }
            Glide.with(this)
                    .load(profile)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(mIvSideDrawerProfileBlurBackground);
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
        String fabString=(String)mFloatActionBtn.getTag();
        if(fabString.equalsIgnoreCase(AppConstants.FEED_SUB_TYPE))
        {
            createCommunityPostOnClick();

        }else
        {
            jobFilterDialog();
        }

    }
    private void jobFilterDialog()
    {
       jobFilterDailogFragment = (JobFilterDialogFragment) getFragmentManager().findFragmentByTag(JobFilterDialogFragment.class.getName());
        if (jobFilterDailogFragment == null) {
            jobFilterDailogFragment = new JobFilterDialogFragment();
            Bundle bundle = new Bundle();
            jobFilterDailogFragment.setArguments(bundle);
        }
        if (!jobFilterDailogFragment.isVisible() && !jobFilterDailogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            jobFilterDailogFragment.show(getFragmentManager(), JobFilterDialogFragment.class.getName());
        }
    }
    public void jobFilterActivityResponse(FeedRequestPojo feedRequestPojo) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(JobFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((JobFragment) fragment).jobFilterIds(feedRequestPojo);
            if(null!=jobFilterDailogFragment)
            {
                jobFilterDailogFragment.dismiss();
            }
        }
    }
    public void searchLocationData(String masterDataSkill, OnBoardingEnum onBoardingEnum) {
        jobLocationSearchDialogFragment = (JobLocationSearchDialogFragment) getFragmentManager().findFragmentByTag(JobLocationSearchDialogFragment.class.getName());
        if (jobLocationSearchDialogFragment == null) {
            jobLocationSearchDialogFragment = new JobLocationSearchDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.MASTER_SKILL, masterDataSkill);
            jobLocationSearchDialogFragment.setArguments(bundle);
        }
        if (!jobLocationSearchDialogFragment.isVisible() && !jobLocationSearchDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            jobLocationSearchDialogFragment.show(getFragmentManager(), JobLocationSearchDialogFragment.class.getName());
        }
    }
    public void saveJobLocation() {
        if (null != jobLocationSearchDialogFragment) {
            jobLocationSearchDialogFragment.dismiss();
        }
        if (null != jobFilterDailogFragment) {
            jobFilterDailogFragment.locationData(mJobLocationList);
        }
    }

    public void logOut() {
        mUserPreference.delete();
        MoEHelper.getInstance(getApplicationContext()).logoutUser();
        MixpanelHelper.clearMixpanel(SheroesApplication.mContext);
        ((NotificationManager) SheroesApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOG_OUT, GoogleAnalyticsEventActions.LOG_OUT_OF_APP, AppConstants.EMPTY_STRING);
        finish();
    }

    private void challengeIdHandle(String urlOfSharedCard) {
        if (urlOfSharedCard.contains(AppConstants.CHALLENGE_URL) || urlOfSharedCard.contains(AppConstants.CHALLENGE_URL_COM)) {
            try {
                int indexOfFirstEqual = AppUtils.findNthIndexOf(urlOfSharedCard, "=", 1);
                String challengeUrl = urlOfSharedCard.substring(indexOfFirstEqual + 1, urlOfSharedCard.length());
                if (StringUtil.isNotNullOrEmptyString(challengeUrl)) {
                    String ChallengeId = challengeUrl;
                    byte[] challengeBytes = Base64.decode(ChallengeId, Base64.DEFAULT);
                    String newChallengeId = new String(challengeBytes, AppConstants.UTF_8);
                    mChallengeId = Long.parseLong(newChallengeId);
                    homeOnClick();
                }
            } catch (UnsupportedEncodingException e) {
                Crashlytics.getInstance().core.logException(e);
                e.printStackTrace();
            }
        } else {
            Uri url = Uri.parse(urlOfSharedCard);
            Intent intent = new Intent(this, SheroesDeepLinkingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.BELL_NOTIFICATION, AppConstants.ONE_CONSTANT);
            intent.putExtras(bundle);
            intent.setData(url);
            startActivity(intent);
        }
    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            feedRelatedOptions(view, baseResponse);
        }
        else if (baseResponse instanceof DrawerItems) {
            drawerItemOptions(view, baseResponse);
        } else if (baseResponse instanceof ChallengeDataItem) {
            int id = view.getId();
            switch (id) {
                case R.id.tv_update_progress:
                    showUpdateProgressDialog((ChallengeDataItem) baseResponse);
                    break;
                case R.id.tv_accept_challenge:
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((HomeFragment) fragment).acceptChallenge((ChallengeDataItem) baseResponse, 0, true, false, AppConstants.EMPTY_STRING, AppConstants.EMPTY_STRING);
                    }
                    break;
                case R.id.tv_timer_count_challenge:
                    updateChallengeDataWithStatus((ChallengeDataItem) baseResponse, AppConstants.COMPLETE, AppConstants.EMPTY_STRING, AppConstants.EMPTY_STRING);
                    break;
                case R.id.tv_timer_count_challenge_update_status:
                    updateChallengeDataWithStatus((ChallengeDataItem) baseResponse, AppConstants.COMPLETE, AppConstants.EMPTY_STRING, AppConstants.EMPTY_STRING);
                    break;
                case R.id.iv_fb_share:
                    sharePostOnFacebook((ChallengeDataItem) baseResponse);
                    break;

                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
            }

        } else if (baseResponse instanceof Comment) {
            setAllValues(mFragmentOpen);
             /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU);
        } else if (baseResponse instanceof FAQS) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(FAQSFragment.class.getName());
            ((FAQSFragment) fragment).setDataChange((FAQS) baseResponse);
        } else if (baseResponse instanceof BellNotificationResponse) {
            BellNotificationResponse bellNotificationResponse = (BellNotificationResponse) baseResponse;
            if (StringUtil.isNotNullOrEmptyString(bellNotificationResponse.getScreenName())) {
                if (StringUtil.isNotNullOrEmptyString(bellNotificationResponse.getSolrIgnoreDeepLinkUrl())) {
                    String urlStr = bellNotificationResponse.getSolrIgnoreDeepLinkUrl();
                    challengeIdHandle(urlStr);
                } else if (bellNotificationResponse.getScreenName().contains(AppConstants.COMMUNITY_URL)) {
                    communityOnClick();
                } else {
                    homeOnClick();
                }
            }
            if(null!=bellNotificationDialogFragment) {
                bellNotificationDialogFragment.dismiss();
            }
        } else if (baseResponse instanceof MentorDetailItem) {
            mFragmentOpen.setChampionViaCommentReaction(AppConstants.ONE_CONSTANT);
            MentorDetailItem mentorDetailItem = (MentorDetailItem) baseResponse;
            mentorItemClick(view, mentorDetailItem);
        }else if (baseResponse instanceof GetAllDataDocument) {
            GetAllDataDocument getAllDataDocument = (GetAllDataDocument) baseResponse;
            if (StringUtil.isNotNullOrEmptyString(getAllDataDocument.getTitle())) {
                if (!getAllDataDocument.isChecked()) {
                    mJobLocationList.add(getAllDataDocument.getTitle());
                } else {
                    if (StringUtil.isNotEmptyCollection(mJobLocationList)) {
                        mJobLocationList.remove(getAllDataDocument.getTitle());
                    }
                }
            }
        }else  if (baseResponse instanceof OnBoardingData) {
            OnBoardingData onBoardingData = (OnBoardingData) baseResponse;
            if (null != onBoardingData && StringUtil.isNotNullOrEmptyString(onBoardingData.getFragmentName())) {
                LabelValue labelValue = (LabelValue) view.getTag();
                if (onBoardingData.getFragmentName().equalsIgnoreCase(AppConstants.JOB_DATA_OPPORTUNITY_KEY)) {
                    if (labelValue.isSelected()) {
                        if (StringUtil.isNotEmptyCollection(mListOfOpportunity)) {
                            mListOfOpportunity.remove(labelValue.getLabel());
                        }
                    } else {
                        mListOfOpportunity.add(labelValue.getLabel());
                    }
                }
            }

        }
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge){
        ContestActivity.navigateTo(this, mContest, SCREEN_LABEL, null,0,0, AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL);
    }

    public void openJobFragment() {
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        JobFragment jobFragment = new JobFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(JobFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Bundle jobBookMarks = new Bundle();
        // jobBookMarks.putSerializable(AppConstants.JOB_FRAGMENT, (ArrayList) categoryIds);
        jobFragment.setArguments(jobBookMarks);
        fm.beginTransaction().replace(R.id.fl_article_card_view, jobFragment, JobFragment.class.getName()).addToBackStack(JobFragment.class.getName()).commitAllowingStateLoss();

    }
    public void jobUi()
    {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mTvSearchBox.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_JOBS));
    }
    private void drawerItemOptions(View view, BaseResponse baseResponse) {
        int drawerItem = ((DrawerItems) baseResponse).getId();
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        switch (drawerItem) {
            case AppConstants.ONE_CONSTANT:
                openProfileActivity();
                break;
            case AppConstants.TWO_CONSTANT:
                openArticleFragment(setCategoryIds(),true);
                break;
            case AppConstants.THREE_CONSTANT:
                //Job
                openJobFragment();

                break;
            case AppConstants.FOURTH_CONSTANT:
                openBookMarkFragment();
                break;
            case 6:
                handleHelpLineFragmentFromDeepLinkAndLoading();
                break;
            case AppConstants.SEVENTH_CONSTANT:
                    openHelplineFragment();
                    mTitleText.setText(getString(R.string.ID_APP_NAME));
                    mTitleText.setVisibility(View.VISIBLE);
                    mICSheroes.setVisibility(View.GONE);
                break;
            case AppConstants.EIGHTH_CONSTANT:
                    mFragmentOpen.setICCMemberListFragment(true);
                    renderICCMemberListView();
                break;
            case AppConstants.NINTH_CONSTANT:
                    mFragmentOpen.setFAQSFragment(true);
                    renderFAQSView();
                break;
            case AppConstants.TENTH_CONSTANT:
                    renderFeedFragment();
                break;
            case AppConstants.ELEVENTH_CONSTANT:
                logOut();
                break;
            case 12:
                inviteMyCommunityDialog();
                break;
            case 13:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.ID_INVITE_REFERRAL_FRIEND));
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get()) {
                    LoginResponse loginResponse = mUserPreference.get();
                    if (null != loginResponse)
                        referralUserAttribute(this, loginResponse);
                }
                isInviteReferral = true;
                break;
            case 14:
                growthBuddiesInPublicProfile();
                break;
            case 15 :
                ContestListActivity.navigateTo(this, SCREEN_LABEL, null);
                break;
            default:

        }
    }

    private void feedRelatedOptions(View view, BaseResponse baseResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.card_header_view:
                createCommunityPostOnClick();
                break;
            case R.id.tv_community_detail_invite:
                inviteMyCommunityDialog();
                break;
            case R.id.tv_add_invite:
                if (null != mFeedDetail) {
                    if (null != myCommunityInviteMemberDialogFragment) {
                        myCommunityInviteMemberDialogFragment.onAddMemberClick(mFeedDetail);
                    }
                }
                break;
            case R.id.li_event_card_main_layout:
                eventDetailDialog(0);
                break;
            case R.id.share:
                String shareText = Config.COMMUNITY_POST_CHALLENGE_SHARE + System.getProperty("line.separator") + ((FeedDetail) baseResponse).getDeepLinkUrl();
                String sourceId = "";
                if(baseResponse instanceof UserPostSolrObj){
                    sourceId = Long.toString(((UserPostSolrObj)baseResponse).getUserPostSourceEntityId());
                }else if(baseResponse instanceof ChallengeSolrObj){
                    sourceId = Long.toString(((ChallengeSolrObj)baseResponse).getIdOfEntityOrParticipant());
                }
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(sourceId)
                                .build();
                trackEvent(Event.CHALLENGE_SHARED, properties);
                ShareBottomSheetFragment.showDialog(this, shareText,((FeedDetail) baseResponse).getThumbnailImageUrl(),  ((FeedDetail) baseResponse).getDeepLinkUrl(), SOURCE_SCREEN, true,  ((FeedDetail) baseResponse).getDeepLinkUrl(), true);
                break;
            case R.id.tv_event_detail_interested_btn:
                if (null != eventDetailDialogFragment) {
                    eventDetailDialogFragment.eventInterestedListData(mFeedDetail);
                }
                break;
            case R.id.tv_event_detail_going_btn:
                if (null != eventDetailDialogFragment) {
                    eventDetailDialogFragment.eventGoingListData(mFeedDetail);
                }
                break;
            case R.id.tv_approve_spam_post:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).approveSpamPost(mFeedDetail, true, false, true);
                }
                break;
            case R.id.tv_delete_spam_post:
                Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(homeFragment)) {
                    ((HomeFragment) homeFragment).approveSpamPost(mFeedDetail, true, true, false);
                }
                break;
            default:
                mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.ONE_CONSTANT);
                if (mFeedDetail instanceof UserPostSolrObj) {
                    mFragmentOpen.setOwner(((UserPostSolrObj) mFeedDetail).isCommunityOwner());
                }
                setAllValues(mFragmentOpen);
                setViewPagerAndViewAdapter(mViewPagerAdapter, mViewPager);
                super.feedCardsHandled(view, baseResponse);

        }

    }

    private void mentorItemClick(View view, MentorDetailItem mentorDetailItem) {
        int id = view.getId();
        switch (id) {
            case R.id.li_growth_buddies_layout:
                openMentorProfileDetail(mentorDetailItem);
                break;
            case R.id.tv_growth_buddies_follow:
                if (null != mPublicProfileGrowthBuddiesDialogFragment) {
                    mPublicProfileGrowthBuddiesDialogFragment.followUnfollowRequest(mentorDetailItem);
                }
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    private void openMentorProfileDetail(MentorDetailItem mentorDetailItem) {
        Intent intent = new Intent(this, PublicProfileGrowthBuddiesDetailActivity.class);
        Bundle bundle = new Bundle();
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(mentorDetailItem.getEntityOrParticipantId());
        //   mFeedDetail.setIdOfEntityOrParticipant(157);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        mFeedDetail = communityFeedSolrObj;
        Parcelable parcelable = Parcels.wrap(mFeedDetail);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
        Parcelable parcelableMentor = Parcels.wrap(mentorDetailItem);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    public void referralUserAttribute(Context context, LoginResponse loginResponse) {
        if (null != loginResponse.getUserSummary() && loginResponse.getUserSummary().getUserId() > 0) {
            StringBuilder fullName = new StringBuilder();
            StringBuilder emailId = new StringBuilder();
            StringBuilder mobile = new StringBuilder();
            StringBuilder subscriptionID = new StringBuilder();
            StringBuilder customValues = new StringBuilder();
            // If you have first and last name separately
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getFirstName())) {
                fullName.append(loginResponse.getUserSummary().getFirstName()).append(AppConstants.SPACE);
            }
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getLastName())) {
                fullName.append(loginResponse.getUserSummary().getLastName());
            }
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getEmailId())) {
                emailId.append(loginResponse.getUserSummary().getEmailId());
            }
            if (null != loginResponse.getUserSummary().getUserBO()) {
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getMobile())) {
                    mobile.append(loginResponse.getUserSummary().getUserBO().getMobile());
                }
            }
            //InviteReferralsApi.getInstance(context).userDetails(fullName.toString(), emailId.toString(), mobile.toString(), AppConstants.CAMPAIGN_ID, subscriptionID.toString(), customValues.toString());
        }
    }

    private void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActicity.class);
        intent.putExtra(AppConstants.EXTRA_IMAGE, profile);
        startActivity(intent);
    }

    public void refreshHomeFragment(FeedDetail feedDetail) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((HomeFragment) fragment).commentListRefresh(feedDetail, COMMENT_REACTION);
        }
    }

    private void handleHelpLineFragmentFromDeepLinkAndLoading() {
        openHelplineFragment();
    }

    private void renderFeedFragment() {
        mICSheroes.setVisibility(View.VISIBLE);
        mTitleText.setVisibility(View.GONE);
        mTitleText.setText(AppConstants.EMPTY_STRING);
        homeOnClick();
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mFragmentOpen.setFeedFragment(true);
    }

    private void sharePostOnFacebook(ChallengeDataItem challengeDataItem) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(challengeDataItem.getChallengeId()))
                        .build();
        trackEvent(Event.CHALLENGE_SHARED, properties);
        String urlToShare = challengeDataItem.getDeepLinkUrl();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
// See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith(AppConstants.FACEBOOK_SHARE)) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }
// As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = AppConstants.FACEBOOK_SHARE_VIA_BROSWER + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }
        startActivity(intent);
    }

    public void showUpdateProgressDialog(ChallengeDataItem challengeDataItem) {
        ChallengeUpdateProgressDialogFragment updateProgressDialogFragment = (ChallengeUpdateProgressDialogFragment) getFragmentManager().findFragmentByTag(ChallengeUpdateProgressDialogFragment.class.getName());
        if (updateProgressDialogFragment == null) {
            updateProgressDialogFragment = new ChallengeUpdateProgressDialogFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(challengeDataItem);
            bundle.putParcelable(AppConstants.CHALLENGE_SUB_TYPE, parcelable);
            updateProgressDialogFragment.setArguments(bundle);
        }
        if (!updateProgressDialogFragment.isVisible() && !updateProgressDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            updateProgressDialogFragment.show(getFragmentManager(), ChallengeUpdateProgressDialogFragment.class.getName());
        }
    }

    public void updateChallengeDataWithStatus(ChallengeDataItem challengeDataItem, int percentCompleted, String imageUrl, String videoUrl) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((HomeFragment) fragment).acceptChallenge(challengeDataItem, percentCompleted, false, true, imageUrl, videoUrl);
        }
    }

    public  void challengeSuccessDialog(ChallengeDataItem challengeDataItem) {
        mChallengeSuccessDialogFragment = (ChallengeSuccessDialogFragment) getFragmentManager().findFragmentByTag(ChallengeSuccessDialogFragment.class.getName());
        if (mChallengeSuccessDialogFragment == null) {
            mChallengeSuccessDialogFragment = new ChallengeSuccessDialogFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(challengeDataItem);
            bundle.putParcelable(AppConstants.SUCCESS, parcelable);
            mChallengeSuccessDialogFragment.setArguments(bundle);
        }
        if (!mChallengeSuccessDialogFragment.isVisible() && !mChallengeSuccessDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mChallengeSuccessDialogFragment.show(getFragmentManager(), ChallengeSuccessDialogFragment.class.getName());
        }
    }

    public void eventDetailDialog(long eventID) {
        eventDetailDialogFragment = (EventDetailDialogFragment) getFragmentManager().findFragmentByTag(EventDetailDialogFragment.class.getName());
        if (eventDetailDialogFragment == null) {
            eventDetailDialogFragment = new EventDetailDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putLong(AppConstants.EVENT_ID, eventID);
            Parcelable parcelable = Parcels.wrap(mFeedDetail);
            bundle.putParcelable(AppConstants.EVENT_DETAIL, parcelable);
            eventDetailDialogFragment.setArguments(bundle);
        }
        if (!eventDetailDialogFragment.isVisible() && !eventDetailDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            eventDetailDialogFragment.show(getFragmentManager(), EventDetailDialogFragment.class.getName());
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
        boolean isSheUser = false;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && false != mUserPreference.get().isSheUser()) {
            isSheUser = true;
        } else {
            isSheUser = false;
        }
        mAdapter.setSheroesGenericListData(CustomeDataList.makeDrawerItemList(isSheUser));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void renderFAQSView() {

        FAQSFragment faqsFragment = new FAQSFragment();
        mToolbar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.GONE);
        mTitleText.setText(getString(R.string.ID_FAQS));
        mTitleText.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_article_card_view, faqsFragment, FAQSFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }


    private void renderICCMemberListView() {

        setAllValues(mFragmentOpen);
        ICCMemberListFragment iccMemberListFragment = new ICCMemberListFragment();
        mToolbar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.GONE);
        mTitleText.setText(getString(R.string.ID_ICC_MEMBERS));
        mTitleText.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_article_card_view, iccMemberListFragment, ICCMemberListFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    public void changeFragmentWithCommunities() {
        mFragmentOpen.setCommunityOpen(false);
        mFragmentOpen.setFeedFragment(false);
        mTabLayout.setVisibility(View.GONE);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvJob.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_job_unselected), null, null);

        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvJob.setText(getString(R.string.ID_CARRIER));

        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvJob.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));


        flFeedFullView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
    }

    private void initHomeViewPagerAndTabs() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        mFragmentOpen.setFeedOpen(true);
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(mFeedDetail);
        bundle.putParcelable(AppConstants.HOME_FRAGMENT, parcelable);
        bundle.putLong(AppConstants.CHALLENGE_ID, mChallengeId);
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, homeFragment, HomeFragment.class.getName()).commitAllowingStateLoss();
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
        Parcelable parcelable = Parcels.wrap(mFragmentOpen);
        bundle.putParcelable(AppConstants.ALL_SEARCH, parcelable);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.li_article_spinner_icon)
    public void openSpinnerOnClick() {
            mFlHomeFooterList.setVisibility(View.GONE);
            if (!StringUtil.isNotEmptyCollection(mHomeSpinnerItemList) && !StringUtil.isNotEmptyCollection(mFragmentOpen.getHomeSpinnerItemList())) {
                setArticleCategoryFilterValues();
                mFragmentOpen.setHomeSpinnerItemList(mHomeSpinnerItemList);
            } else if (StringUtil.isNotEmptyCollection(mFragmentOpen.getHomeSpinnerItemList())) {
                mHomeSpinnerItemList = mFragmentOpen.getHomeSpinnerItemList();
            }
            mArticleCategorySpinnerFragment = new ArticleCategorySpinnerFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mHomeSpinnerItemList);
            bundle.putParcelable(AppConstants.HOME_SPINNER_FRAGMENT, parcelable);
            mArticleCategorySpinnerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, mArticleCategorySpinnerFragment, ArticleCategorySpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    @OnClick(R.id.tv_home)
    public void homeOnClick() {
        mFragmentOpen.setFeedOpen(true);
        flFeedFullView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        initHomeViewPagerAndTabs();
    }

    public void homeButtonUi() {
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mFragmentOpen.setFeedFragment(true);
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));

        mTvJob.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_job_unselected), null, null);
        mTvJob.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvJob.setText(getString(R.string.ID_CARRIER));

        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mFloatActionBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.email)));
        mFloatActionBtn.setImageResource(R.drawable.ic_pencil);
        mFloatActionBtn.setTag(AppConstants.FEED_SUB_TYPE);
        mTvSearchBox.setVisibility(View.GONE);
        mICSheroes.setVisibility(View.VISIBLE);

    }
    public void  jobButtonUI() {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mFloatActionBtn.setTag(AppConstants.FEED_JOB);
        mFloatActionBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        mFloatActionBtn.setImageResource(R.drawable.ic_fab_icon);
        mTvSearchBox.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_JOBS));

        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mFragmentOpen.setJobFragment(true);
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));

        mTvJob.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_job_selected), null, null);
        mTvJob.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvJob.setText(getString(R.string.ID_CARRIER));
        mliArticleSpinnerIcon.setVisibility(View.GONE);
    }
    @OnClick(R.id.tv_job_home)
    public void jobOnClick() {
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        JobFragment jobFragment = new JobFragment();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Bundle bundle = new Bundle();
        jobFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, jobFragment, JobFragment.class.getName()).commitAllowingStateLoss();
        mJobLocationList.clear();
        mListOfOpportunity.clear();
    }

    @OnClick(R.id.tv_communities)
    public void communityOnClick() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_COMMUNITIES));
        flFeedFullView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.VISIBLE);
        initCommunityViewPagerAndTabs();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        communityButton();
    }

    public void communityButton() {
        mFragmentOpen.setCommunityOpen(true);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvJob.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_job_unselected), null, null);
        mTvJob.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvJob.setText(getString(R.string.ID_CARRIER));

        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        mICSheroes.setVisibility(View.VISIBLE);
    }

    public void createCommunityPostOnClick() {
        CommunityPost communityPost = new CommunityPost();
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false);
      //  PostBottomSheetFragment.showDialog(this, SCREEN_LABEL);
    }


    private void openArticleFragment(List<Long> categoryIds,boolean fromDrawer) {
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        ArticlesFragment articlesFragment = new ArticlesFragment();
        FragmentManager fm = getSupportFragmentManager();
        if(fromDrawer) {
            fm.popBackStackImmediate(ArticlesFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Bundle bundleArticle = new Bundle();
        bundleArticle.putSerializable(AppConstants.ARTICLE_FRAGMENT, (ArrayList) categoryIds);
        articlesFragment.setArguments(bundleArticle);
        fm.beginTransaction().replace(R.id.fl_article_card_view, articlesFragment, ArticlesFragment.class.getName()).addToBackStack(ArticlesFragment.class.getName()).commitAllowingStateLoss();
    }
    public void articleUi()
    {
        mliArticleSpinnerIcon.setVisibility(View.VISIBLE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        mICSheroes.setVisibility(View.VISIBLE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
    }

    public void inviteMyCommunityDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.ID_INVITE_WOMEN_FRIEND));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        LoginManager.getInstance().logOut();
        String appLinkUrl, previewImageUrl;
        appLinkUrl = AppConstants.FB_APP_LINK_URL;
        previewImageUrl = AppConstants.FB_APP_LINK_URL_PREVIEW_IMAGE;
        if (AppInviteDialog.canShow()) {
            AppEventsLogger logger = AppEventsLogger.newLogger(this);
            logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT);
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_INVITES, GoogleAnalyticsEventActions.OPEN_INVITE_FB_FRDZ, AppConstants.EMPTY_STRING);

    }

    public void updateMyCommunitiesFragment(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
        if (null != myCommunityInviteMemberDialogFragment) {
            myCommunityInviteMemberDialogFragment.dismiss();
        }
        Fragment community = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
        if (AppUtils.isFragmentUIActive(community)) {
            ((MyCommunitiesFragment) community).commentListRefresh(feedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
        }
    }

    private void openHelplineFragment() {
        changeFragmentWithCommunities();
        setAllValues(mFragmentOpen);
        HelplineFragment helplineFragment = new HelplineFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(HelplineFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction().replace(R.id.fl_article_card_view, helplineFragment, HelplineFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }
    public void helplineUi()
    {
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.GONE);
        mFloatActionBtn.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        mICSheroes.setVisibility(View.VISIBLE);
    }

    private void openBookMarkFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        mICSheroes.setVisibility(View.GONE);
        mFragmentOpen.setBookmarkFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setText(R.string.ID_BOOKMARKS);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.feed_card_time));
        setAllValues(mFragmentOpen);
        BookmarksFragment bookmarksFragment = new BookmarksFragment();
        Bundle bundleBookMarks = new Bundle();
        Parcelable parcelable = Parcels.wrap(mFeedDetail);
        bundleBookMarks.putParcelable(AppConstants.BOOKMARKS, parcelable);
        bookmarksFragment.setArguments(bundleBookMarks);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_card_view, bookmarksFragment, BookmarksFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        mliArticleSpinnerIcon.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {

        if (mFragmentOpen.isFeedFragment() || mFragmentOpen.isCommunityOpen()||mFragmentOpen.isJobFragment()) {
            if (mFragmentOpen.isCommentList()) {
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
                getSupportFragmentManager().popBackStackImmediate();
            }else {
                if (doubleBackToExitPressedOnce) {
                    getSupportFragmentManager().popBackStackImmediate();
                    finish();
                    return;
                }
                doubleBackToExitPressedOnce = true;
                homeOnClick();
              //  Snackbar.make(mCLMainLayout, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        } else {
                super.onBackPressed();
        }
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPressed();
    }

    @OnClick(R.id.fl_notification)
    public void notificationClick() {
        // mDrawer.openDrawer(Gravity.LEFT);
        AppUtils.hideKeyboard(mTvUserName, TAG);
        bellNotificationDialog();
    }

    public void bellNotificationDialog() {
        bellNotificationDialogFragment = (BellNotificationDialogFragment) getFragmentManager().findFragmentByTag(BellNotificationDialogFragment.class.getName());
        if (bellNotificationDialogFragment == null) {
            bellNotificationDialogFragment = new BellNotificationDialogFragment();
            Bundle bundle = new Bundle();
            bellNotificationDialogFragment.setArguments(bundle);
            flNotificationReadCount.setVisibility(View.GONE);
        }
        if (!bellNotificationDialogFragment.isVisible() && !bellNotificationDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            bellNotificationDialogFragment.show(getFragmentManager(), BellNotificationDialogFragment.class.getName());
        }
    }

    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        AppUtils.hideKeyboard(mTvUserName, TAG);
        mDrawer.openDrawer(Gravity.LEFT);
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_DRAWER_NAVIGATION));
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

                    case AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL:
                        if (resultCode == Activity.RESULT_OK) {
                            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                            if (AppUtils.isFragmentUIActive(fragment)) {
                                ((HomeFragment) fragment).onRefreshClick();
                            }
                        }
                        break;
                case AppConstants.REQUEST_CODE_FOR_JOB_DETAIL:
                    jobDetailActivityResponse(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY:
                    createCommunityActivityResponse(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    editCommunityPostResponse(intent);
                    break;
                case AppConstants.REQ_CODE_SPEECH_INPUT:
                    helplineSpeechActivityResponse(intent, resultCode);
                    break;
                case AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                    checkPublicProfileMentorFollow(intent);
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        try {
                            File file = new File(result.getUri().getPath());
                            Bitmap photo = decodeFile(file);
                            if (null != mChallengeSuccessDialogFragment) {
                                mChallengeSuccessDialogFragment.setImageOnHolder(photo);
                            }

                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                            e.printStackTrace();
                        }

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        }
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    private void checkPublicProfileMentorFollow(Intent intent) {
        if (null != mPublicProfileGrowthBuddiesDialogFragment) {
            if (null != intent && null != intent.getExtras() && null != intent.getExtras().get(AppConstants.GROWTH_PUBLIC_PROFILE)) {
                MentorDetailItem mentorDetailItem = (MentorDetailItem)Parcels.unwrap(intent.getParcelableExtra(AppConstants.GROWTH_PUBLIC_PROFILE));
                mPublicProfileGrowthBuddiesDialogFragment.notifyList(mentorDetailItem);
                if (mFragmentOpen.getChampionViaCommentReaction() != AppConstants.ONE_CONSTANT) {
                    mFragmentOpen.setChampionViaCommentReaction(AppConstants.NO_REACTION_CONSTANT);
                    FeedDetail feedDetail = Parcels.unwrap(intent.getParcelableExtra(AppConstants.FEED_SCREEN));
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((HomeFragment) fragment).commentListRefresh(feedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                    }
                }

            }
        }

    }

    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return null;
    }

    private void editCommunityPostResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            mFeedDetail = (FeedDetail) Parcels.unwrap(intent.getParcelableExtra(AppConstants.COMMUNITY_POST_FRAGMENT));
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
    }



    private void createCommunityActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            mFeedDetail = (FeedDetail) Parcels.unwrap(intent.getParcelableExtra(AppConstants.COMMUNITIES_DETAIL));
            //mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL);
            Fragment community = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
            if (AppUtils.isFragmentUIActive(community)) {
                if (null != mFeedDetail) {
                    ((MyCommunitiesFragment) community).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                } else {
                    communityOnClick();
                }
            }
        }
    }

    private void articleDetailActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            mFeedDetail =(FeedDetail) Parcels.unwrap(intent.getParcelableExtra(AppConstants.HOME_FRAGMENT));
            //mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.HOME_FRAGMENT);
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
    }

    private void helplineSpeechActivityResponse(Intent intent, int resultCode) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HelplineFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            if (resultCode == Activity.RESULT_OK && null != intent) {
                ArrayList<String> result = intent
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ((HelplineFragment) fragment).getSpeechText(result.get(0));
            }
        }
    }

    private void jobDetailActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            mFeedDetail = (FeedDetail) Parcels.unwrap(intent.getParcelableExtra(AppConstants.JOB_FRAGMENT));
            //mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.JOB_FRAGMENT);
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
    }

    private void communityDetailActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras() && null != intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL)) {
            CommunityFeedSolrObj communityFeedSolrObj = Parcels.unwrap(intent.getParcelableExtra(AppConstants.COMMUNITIES_DETAIL));
            CommunityEnum communityEnum = (CommunityEnum) intent.getExtras().get(AppConstants.MY_COMMUNITIES_FRAGMENT);
            if (null != communityEnum) {
                switch (communityEnum) {
                    case FEATURE_COMMUNITY:
                        if (mViewPagerAdapter == null) {
                            return;
                        }
                        Fragment feature = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                        if (AppUtils.isFragmentUIActive(feature)) {
                            if (communityFeedSolrObj.isFeatured() && communityFeedSolrObj.isMember()) {
                                communityOnClick();
                            } else {
                                ((FeaturedFragment) feature).commentListRefresh(communityFeedSolrObj, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                            }
                        }
                        break;
                    case MY_COMMUNITY:
                        if (StringUtil.isNotNullOrEmptyString(communityFeedSolrObj.getCallFromName()) && communityFeedSolrObj.getCallFromName().equalsIgnoreCase(AppConstants.FEATURE_FRAGMENT)) {
                            communityOnClick();
                        } else {
                            if (null != mViewPagerAdapter) {
                                Fragment community = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
                                if (AppUtils.isFragmentUIActive(community)) {
                                    ((MyCommunitiesFragment) community).commentListRefresh(communityFeedSolrObj, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                                }
                            } else {
                                if (communityFeedSolrObj.isViewed()) {
                                    homeOnClick();
                                } else {
                                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                                    if (AppUtils.isFragmentUIActive(fragment)) {
                                        ((HomeFragment) fragment).commentListRefresh(communityFeedSolrObj, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
                }
            }
        }
    }


    @Override
    public void onCancelDone(int pressedEvent) {
        if (AppConstants.ONE_CONSTANT == pressedEvent) {
            getSupportFragmentManager().popBackStack(ArticlesFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
                        total = stringBuilder.toString().substring(0, 25)+ AppConstants.DOTS;
                        mTvCategoryText.setText(total);
                    } else {
                        mTvCategoryText.setText(total);
                    }
                } else {
                    mTvCategoryText.setText(AppConstants.EMPTY_STRING);
                    mTvCategoryChoose.setVisibility(View.VISIBLE);
                }
                openArticleFragment(categoryIds,false);
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

    @OnClick(R.id.profile_link)
    public void onClickProfile() {
        openProfileActivity();
    }


    public DialogFragment growthBuddiesInPublicProfile() {
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_CHAMPION_LISTING));
        mPublicProfileGrowthBuddiesDialogFragment = (PublicProfileGrowthBuddiesDialogFragment) getFragmentManager().findFragmentByTag(PublicProfileGrowthBuddiesDialogFragment.class.getName());
        if (mPublicProfileGrowthBuddiesDialogFragment == null) {
            mPublicProfileGrowthBuddiesDialogFragment = new PublicProfileGrowthBuddiesDialogFragment();
            Bundle bundle = new Bundle();
            // bundle.putSerializable(AppConstants.BOARDING_SEARCH, onBoardingEnum);
            // bundle.putString(AppConstants.MASTER_SKILL, masterDataSkill);
            mPublicProfileGrowthBuddiesDialogFragment.setArguments(bundle);
        }
        if (!mPublicProfileGrowthBuddiesDialogFragment.isVisible() && !mPublicProfileGrowthBuddiesDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mPublicProfileGrowthBuddiesDialogFragment.show(getFragmentManager(), PublicProfileGrowthBuddiesDialogFragment.class.getName());
        }
        return mPublicProfileGrowthBuddiesDialogFragment;
    }

    @Override
    public void championProfile(BaseResponse baseResponse, int championValue) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            championDetailActivity(feedDetail.getCreatedBy(), feedDetail.getItemPosition());
        } else if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            championDetailActivity(comment.getParticipantId(), comment.getItemPosition());
        }
    }

    private void championDetailActivity(Long userId, int position) {
        Intent intent = new Intent(this, PublicProfileGrowthBuddiesDetailActivity.class);
        Bundle bundle = new Bundle();
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        Parcelable parcelable = Parcels.wrap(mFeedDetail);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);

        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, null);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}