package appliedlife.pvtltd.SHEROES.views.activities;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.rx.preferences.Preference;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorDetailItem;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.CustomeDataList;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FAQSFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeArticleCategorySpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ICCMemberListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFeedbackFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingTermsAndConditionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ChallengeSuccessDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ChallengeUpdateProgressDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.EventDetailDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MyCommunityInviteMemberDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.PublicProfileGrowthBuddiesDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;

public class HomeActivity extends BaseActivity implements CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, CommentReactionFragment.HomeActivityIntractionListner, HomeArticleCategorySpinnerFragment.HomeSpinnerFragmentListner {
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
    @Bind(R.id.iv_footer_button_icon)
    ImageView mIvFooterButtonIcon;
    @Bind(R.id.tv_search_box)
    TextView mTvSearchBox;
    @Bind(R.id.tv_setting)
    TextView mTvSetting;
    @Bind(R.id.tv_home)
    TextView mTvHome;
     @Bind(R.id.tv_make_india_safe)
     ImageView mTvMakeIndiaSafe;
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
    @Bind(R.id.fl_feed_full_view)
    public FrameLayout flFeedFullView;
    @Bind(R.id.iv_side_drawer_profile_blur_background)
    ImageView mIvSideDrawerProfileBlurBackground;
    @Bind(R.id.iv_home_notification_icon)
    TextView mIvHomeNotification;
    @Bind(R.id.tv_notification_read_count)
    public TextView mTvNotificationReadCount;
    @Bind(R.id.fab_add_community)
    FloatingActionButton mFloatingActionButton;
    @Bind(R.id.fl_notification)
    FrameLayout mFlNotification;
    @Bind(R.id.fab_filter)
    FloatingActionButton mJobFragment;
    @Bind(R.id.li_home_community_button_layout)
    LinearLayout liHomeCommunityButtonLayout;
    @Bind(R.id.fl_notification_read_count)
    public FrameLayout flNotificationReadCount;
    @Bind(R.id.title_text)
    TextView mTitleText;
    @Bind(R.id.ic_sheroes)
    ImageView mICSheroes;
    GenericRecyclerViewAdapter mAdapter;
    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    private HomeArticleCategorySpinnerFragment mHomeArticleCategorySpinnerFragment;
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
    private long startedTime;
    private MoEngageUtills moEngageUtills;
    @Inject
    AppUtils mAppUtils;
    private Uri mImageCaptureUri;
    public ChallengeSuccessDialogFragment mChallengeSuccessDialogFragment;
    private File localImageSaveForChallenge;
    private long mChallengeId;
    private long mEventId;
    private String mHelpLineChat;
    private EventDetailDialogFragment eventDetailDialogFragment;
    private ProgressDialog mProgressDialog;
    private boolean isInviteReferral;
    private PublicProfileGrowthBuddiesDialogFragment mPublicProfileGrowthBuddiesDialogFragment;
    private BellNotificationDialogFragment bellNotificationDialogFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime = System.currentTimeMillis();
        renderHomeFragmentView();
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && false != mUserPreference.get().isSheUser() && startedFirstTime()) {
            openHelplineFragment();
            mTitleText.setText(getString(R.string.ID_APP_NAME));
            mTitleText.setVisibility(View.VISIBLE);
            mICSheroes.setVisibility(View.GONE);
            mTvMakeIndiaSafe.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mHelpLineChat) && mHelpLineChat.equalsIgnoreCase(AppConstants.HELPLINE_CHAT)) {
            handleHelpLineFragmentFromDeepLinkAndLoading();
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
        initHomeViewPagerAndTabs();
        assignNavigationRecyclerListView();
        if (mEventId > 0) {
            eventDetailDialog(mEventId);
        }
    }

    private void setProfileImage() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            //TODO: this data to be removed
            profile = mUserPreference.get().getUserSummary().getPhotoUrl(); //"https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAhNAAAAJDYwZWIyZTg5LWFmOTItNGIwYS05YjQ5LTM2YTRkNGQ2M2JlNw.jpg";
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
        Intent intent = new Intent(getApplicationContext(), JobFilterActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_JOB_FILTER);
        overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
    }

    public void logOut() {
        mUserPreference.delete();
        MoEHelper.getInstance(getApplicationContext()).logoutUser();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
        if (mFragmentOpen.isOpen()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, AppConstants.FEED_SCREEN);
        }
        if (mFragmentOpen.isArticleFragment()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, getString(R.string.ID_ARTICLE));
        }
        if (mFragmentOpen.isBookmarkFragment()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, getString(R.string.ID_BOOKMARKS));
        }
        if (mFragmentOpen.isJobFragment()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, getString(R.string.ID_JOB));
        }
        if (mFragmentOpen.isSettingFragment()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, getString(R.string.ID_SETTING));
        }
        if (mFragmentOpen.isICCMemberListFragment()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, getString(R.string.ID_ICC_MEMBERS));
        }
        if (mFragmentOpen.isFAQSFragment()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, getString(R.string.ID_FAQS));
        }
        if (mFragmentOpen.isFeedFragment()) {
            moEngageUtills.entityMoEngageLogOut(this, mMoEHelper, payloadBuilder, AppConstants.FEED_SCREEN);
        }
        ((SheroesApplication)this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOG_OUT, GoogleAnalyticsEventActions.LOG_OUT_OF_APP, AppConstants.EMPTY_STRING);
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
            feedRelatedOptions(view,baseResponse);
        } else if (baseResponse instanceof DrawerItems) {
            drawerItemOptions(view,baseResponse);
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

        } else if (baseResponse instanceof CommentReactionDoc) {
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

        } else if (baseResponse instanceof MentorDetailItem) {
            mFragmentOpen.setChampionViaCommentReaction(AppConstants.ONE_CONSTANT);
            MentorDetailItem mentorDetailItem = (MentorDetailItem) baseResponse;
            mentorItemClick(view, mentorDetailItem);
        }
    }

 private void drawerItemOptions(View view,BaseResponse baseResponse)
 {
     int drawerItem = ((DrawerItems) baseResponse).getId();
     if (mDrawer.isDrawerOpen(GravityCompat.START)) {
         mDrawer.closeDrawer(GravityCompat.START);
     }
     switch (drawerItem) {
         case AppConstants.ONE_CONSTANT:
             openProfileActivity();
             break;
         case AppConstants.TWO_CONSTANT:
             checkForAllOpenFragments();
             openArticleFragment(setCategoryIds());
             totalTimeSpentOnFeed();
             break;
         case AppConstants.THREE_CONSTANT:
             checkForAllOpenFragments();
             openJobFragment();
             totalTimeSpentOnFeed();
             break;
         case AppConstants.FOURTH_CONSTANT:
             checkForAllOpenFragments();
             openBookMarkFragment();
             totalTimeSpentOnFeed();
             break;
         case 5:
             openSettingFragment();
             totalTimeSpentOnFeed();
             break;
         case 6:
             handleHelpLineFragmentFromDeepLinkAndLoading();
             break;
         case AppConstants.SEVENTH_CONSTANT:
             if (!mFragmentOpen.isHelplineFragment()) {
                 checkForAllOpenFragments();
                 openHelplineFragment();
                 mTitleText.setText(getString(R.string.ID_APP_NAME));
                 mTitleText.setVisibility(View.VISIBLE);
                 mICSheroes.setVisibility(View.GONE);
                 totalTimeSpentOnFeed();
             }
             break;
         case AppConstants.EIGHTH_CONSTANT:
             if (!mFragmentOpen.isICCMemberListFragment()) {
                 checkForAllOpenFragments();
                 mFragmentOpen.setICCMemberListFragment(true);
                 renderICCMemberListView();
                 totalTimeSpentOnFeed();
             }
             break;
         case AppConstants.NINTH_CONSTANT:
             if (!mFragmentOpen.isFAQSFragment()) {
                 checkForAllOpenFragments();
                 mFragmentOpen.setFAQSFragment(true);
                 renderFAQSView();
                 totalTimeSpentOnFeed();
             }
             break;
         case AppConstants.TENTH_CONSTANT:
             if (!mFragmentOpen.isFeedFragment()) {
                 checkForAllOpenFragments();
                 mFragmentOpen.setFeedFragment(true);
                 renderFeedFragment();
             }
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
         default:

     }
 }

private void feedRelatedOptions(View view,BaseResponse baseResponse)
{
    int id = view.getId();
    switch (id) {
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
        case  R.id.tv_approve_spam_post:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).approveSpamPost(mFeedDetail,true,false,true);
                }
            break;
        case R.id.tv_delete_spam_post:
                Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(homeFragment)) {
                    ((HomeFragment) homeFragment).approveSpamPost(mFeedDetail,true,true,false);
                }
            break;
        default:
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.ONE_CONSTANT);
            mFragmentOpen.setOwner(mFeedDetail.isCommunityOwner());
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
        mFeedDetail = new FeedDetail();
        mFeedDetail.setIdOfEntityOrParticipant(mentorDetailItem.getEntityOrParticipantId());
        //   mFeedDetail.setIdOfEntityOrParticipant(157);
        mFeedDetail.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, mentorDetailItem);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
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
        overridePendingTransition(R.anim.fade_in_dialog, R.anim.fade_out_dialog);
        totalTimeSpentOnFeed();
    }

    public void refreshHomeFragment(FeedDetail feedDetail) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((HomeFragment) fragment).commentListRefresh(feedDetail, COMMENT_REACTION);
        }
    }

    private void handleHelpLineFragmentFromDeepLinkAndLoading() {
        checkForAllOpenFragments();
        openHelplineFragment();
        totalTimeSpentOnFeed();
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

    public DialogFragment showUpdateProgressDialog(ChallengeDataItem challengeDataItem) {
        ChallengeUpdateProgressDialogFragment updateProgressDialogFragment = (ChallengeUpdateProgressDialogFragment) getFragmentManager().findFragmentByTag(ChallengeUpdateProgressDialogFragment.class.getName());
        if (updateProgressDialogFragment == null) {
            updateProgressDialogFragment = new ChallengeUpdateProgressDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.CHALLENGE_SUB_TYPE, challengeDataItem);
            updateProgressDialogFragment.setArguments(bundle);
        }
        if (!updateProgressDialogFragment.isVisible() && !updateProgressDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            updateProgressDialogFragment.show(getFragmentManager(), ChallengeUpdateProgressDialogFragment.class.getName());
        }
        return updateProgressDialogFragment;
    }

    public void updateChallengeDataWithStatus(ChallengeDataItem challengeDataItem, int percentCompleted, String imageUrl, String videoUrl) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((HomeFragment) fragment).acceptChallenge(challengeDataItem, percentCompleted, false, true, imageUrl, videoUrl);
        }
    }

    public DialogFragment challengeSuccessDialog(ChallengeDataItem challengeDataItem) {
        localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        mChallengeSuccessDialogFragment = (ChallengeSuccessDialogFragment) getFragmentManager().findFragmentByTag(ChallengeSuccessDialogFragment.class.getName());
        if (mChallengeSuccessDialogFragment == null) {
            mChallengeSuccessDialogFragment = new ChallengeSuccessDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.SUCCESS, challengeDataItem);
            mChallengeSuccessDialogFragment.setArguments(bundle);
        }
        if (!mChallengeSuccessDialogFragment.isVisible() && !mChallengeSuccessDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mChallengeSuccessDialogFragment.show(getFragmentManager(), ChallengeSuccessDialogFragment.class.getName());
        }
        return mChallengeSuccessDialogFragment;
    }

    public DialogFragment eventDetailDialog(long eventID) {
        eventDetailDialogFragment = (EventDetailDialogFragment) getFragmentManager().findFragmentByTag(EventDetailDialogFragment.class.getName());
        if (eventDetailDialogFragment == null) {
            eventDetailDialogFragment = new EventDetailDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putLong(AppConstants.EVENT_ID, eventID);
            bundle.putParcelable(AppConstants.EVENT_DETAIL, mFeedDetail);
            eventDetailDialogFragment.setArguments(bundle);
        }
        if (!eventDetailDialogFragment.isVisible() && !eventDetailDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            eventDetailDialogFragment.show(getFragmentManager(), EventDetailDialogFragment.class.getName());
        }
        return eventDetailDialogFragment;
    }



    private void totalTimeSpentOnFeed() {
        long timeSpentFeed = System.currentTimeMillis() - startedTime;
        moEngageUtills.entityMoEngageViewFeed(this, mMoEHelper, payloadBuilder, timeSpentFeed);
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
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
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
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
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

    private void initHomeViewPagerAndTabs() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        mFragmentOpen.setFeedOpen(true);
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.HOME_FRAGMENT, mFeedDetail);
        bundle.putLong(AppConstants.CHALLENGE_ID, mChallengeId);
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_feed_full_view, homeFragment, HomeFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        totalTimeSpentOnFeed();
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
            mHomeArticleCategorySpinnerFragment = new HomeArticleCategorySpinnerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT, (ArrayList<? extends Parcelable>) mHomeSpinnerItemList);
            mHomeArticleCategorySpinnerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_article_card_view, mHomeArticleCategorySpinnerFragment, HomeArticleCategorySpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
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
        if (mFragmentOpen.isHelplineFragment()) {
            onBackPressed();
            mFragmentOpen.setHelplineFragment(false);
        }
        if (mFragmentOpen.isICCMemberListFragment()) {
            onBackPressed();
            mFragmentOpen.setICCMemberListFragment(false);
        }
        if (mFragmentOpen.isFAQSFragment()) {
            onBackPressed();
            mFragmentOpen.setFAQSFragment(false);
        }
        if (mFragmentOpen.isFeedFragment()) {
            onBackPressed();
            mFragmentOpen.setFeedFragment(false);
        }
        if(null!=bellNotificationDialogFragment)
        {
            bellNotificationDialogFragment.dismiss();
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
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
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
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.VISIBLE);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        initCommunityViewPagerAndTabs();
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
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
        setAllValues(mFragmentOpen);
        ArticlesFragment articlesFragment = new ArticlesFragment();
        Bundle bundleArticle = new Bundle();
        bundleArticle.putSerializable(AppConstants.ARTICLE_FRAGMENT, (ArrayList) categoryIds);
        articlesFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .add(R.id.fl_article_card_view, articlesFragment, ArticlesFragment.class.getName()).addToBackStack(ArticlesFragment.class.getName()).commitAllowingStateLoss();
        mliArticleSpinnerIcon.setVisibility(View.VISIBLE);
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
        ((SheroesApplication)this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_INVITES, GoogleAnalyticsEventActions.OPEN_INVITE_FB_FRDZ, AppConstants.EMPTY_STRING);
      /*  myCommunityInviteMemberDialogFragment = (MyCommunityInviteMemberDialogFragment) getFragmentManager().findFragmentByTag(MyCommunityInviteMemberDialogFragment.class.getName());
        if (myCommunityInviteMemberDialogFragment == null) {
            myCommunityInviteMemberDialogFragment = new MyCommunityInviteMemberDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            myCommunityInviteMemberDialogFragment.setArguments(bundle);
        }
        if (!myCommunityInviteMemberDialogFragment.isVisible() && !myCommunityInviteMemberDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            myCommunityInviteMemberDialogFragment.show(getFragmentManager(), MyCommunityInviteMemberDialogFragment.class.getName());
        }
        return myCommunityInviteMemberDialogFragment;*/
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
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.GONE);
        mToolbar.setVisibility(View.VISIBLE);
        mFragmentOpen.setHelplineFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setVisibility(View.GONE);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        setAllValues(mFragmentOpen);
        HelplineFragment helplineFragment = new HelplineFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_article_card_view, helplineFragment, HelplineFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        mliArticleSpinnerIcon.setVisibility(View.GONE);
    }


    private void openSettingFragment() {
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFragmentOpen.setSettingFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setText(R.string.ID_SETTINGS);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
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
        mICSheroes.setVisibility(View.GONE);
        mFragmentOpen.setBookmarkFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvSearchBox.setVisibility(View.GONE);
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setText(R.string.ID_BOOKMARKS);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
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
        mTvMakeIndiaSafe.setVisibility(View.GONE);
        mJobFragment.setVisibility(View.VISIBLE);
        mTvSearchBox.setVisibility(View.VISIBLE);
        mICSheroes.setVisibility(View.GONE);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_JOBS));
        liHomeCommunityButtonLayout.setVisibility(View.GONE);
        mFlHomeFooterList.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        mFragmentOpen.setJobFragment(true);
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        flFeedFullView.setVisibility(View.GONE);
        mliArticleSpinnerIcon.setVisibility(View.GONE);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
        mTvHome.setText(getString(R.string.ID_FEED));
        mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.teg_text_color));
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
                mICSheroes.setVisibility(View.VISIBLE);
            }
        } else if (mFragmentOpen.isJobFragment()) {
            mJobFragment.setVisibility(View.GONE);
            mTvSearchBox.setVisibility(View.GONE);
            mICSheroes.setVisibility(View.VISIBLE);
            mTvMakeIndiaSafe.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStackImmediate();
            initHomeViewPagerAndTabs();
            setHomeFeedCommunityData();
            mFragmentOpen.setJobFragment(false);
        } else if (mFragmentOpen.isOpenImageViewer()) {
            mFragmentOpen.setOpenImageViewer(false);
            getSupportFragmentManager().popBackStackImmediate();
        }  else if (mFragmentOpen.isHelplineFragment()) {
            mFragmentOpen.setHelplineFragment(false);
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && false != mUserPreference.get().isSheUser()) {
                mTitleText.setText(getString(R.string.ID_APP_NAME));
                mTitleText.setVisibility(View.VISIBLE);
                mICSheroes.setVisibility(View.GONE);
            }else
            {
                getSupportFragmentManager().popBackStackImmediate();
                initHomeViewPagerAndTabs();
                setHomeFeedCommunityData();
                mFlHomeFooterList.setVisibility(View.VISIBLE);
                mTvHome.setVisibility(View.VISIBLE);
                mTitleText.setVisibility(View.GONE);
                mICSheroes.setVisibility(View.VISIBLE);
            }
        } else if (mFragmentOpen.isICCMemberListFragment()) {
            mFragmentOpen.setICCMemberListFragment(false);
            getSupportFragmentManager().popBackStackImmediate();
            openHelplineFragment();
            mTitleText.setText(getString(R.string.ID_APP_NAME));
            mTitleText.setVisibility(View.VISIBLE);
            mICSheroes.setVisibility(View.GONE);
        } else if (mFragmentOpen.isFAQSFragment()) {
            mFragmentOpen.setFAQSFragment(false);
            getSupportFragmentManager().popBackStackImmediate();
            openHelplineFragment();
            mTitleText.setText(getString(R.string.ID_APP_NAME));
            mTitleText.setVisibility(View.VISIBLE);
            mICSheroes.setVisibility(View.GONE);
        } else if (mFragmentOpen.isFeedFragment()) {
            mFragmentOpen.setFeedFragment(false);
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            if (doubleBackToExitPressedOnce) {
                getSupportFragmentManager().popBackStackImmediate();
                finish();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Snackbar.make(mCLMainLayout, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
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

    @OnClick(R.id.fl_notification)
    public void notificationClick() {
        // mDrawer.openDrawer(Gravity.LEFT);
        AppUtils.hideKeyboard(mTvUserName, TAG);
        bellNotificationDialog();
    }
    public DialogFragment bellNotificationDialog() {
         bellNotificationDialogFragment = (BellNotificationDialogFragment) getFragmentManager().findFragmentByTag(BellNotificationDialogFragment.class.getName());
        if (bellNotificationDialogFragment == null) {
            bellNotificationDialogFragment = new BellNotificationDialogFragment();
            Bundle bundle = new Bundle();
            bellNotificationDialogFragment.setArguments(bundle);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).notificationUi();
            }
        }
        if (!bellNotificationDialogFragment.isVisible() && !bellNotificationDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            bellNotificationDialogFragment.show(getFragmentManager(), BellNotificationDialogFragment.class.getName());
        }
        return bellNotificationDialogFragment;
    }


    @OnClick(R.id.tv_make_india_safe)
    public void tvOnClickMakeIndiasafe() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.ID_MAKE_INDIA_SAFE_DIALOG));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        Intent mapIntent = new Intent(this, MakeIndiaSafeMapActivity.class);
        Bundle bundle = new Bundle();
        mapIntent.putExtras(bundle);
        startActivityForResult(mapIntent, AppConstants.REQUEST_CODE_FOR_GOOGLE_MAP);
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
                case AppConstants.REQUEST_CODE_FOR_GALLERY:
                    mImageCaptureUri = intent.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING:
                    imageCropping(intent);
                    break;
                case AppConstants.REQ_CODE_SPEECH_INPUT:
                    helplineSpeechActivityResponse(intent, resultCode);
                    break;
                case AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                    checkPublicProfileMentorFollow(intent);
                    break;
                case AppConstants.REQUEST_CODE_FOR_GOOGLE_MAP:
                    checkMapData(intent);
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        } else {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
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
    private void checkMapData(Intent intent) {
            if (null != intent && null != intent.getExtras() && null != intent.getExtras().get(AppConstants.MAKE_INDIA_SAFE)) {
                String string = intent.getExtras().getString(AppConstants.MAKE_INDIA_SAFE);
                homeOnClick();
            }
    }
    private void checkPublicProfileMentorFollow(Intent intent) {
        if (null != mPublicProfileGrowthBuddiesDialogFragment) {
            if (null != intent && null != intent.getExtras() && null != intent.getExtras().get(AppConstants.GROWTH_PUBLIC_PROFILE)) {
                MentorDetailItem mentorDetailItem = (MentorDetailItem) intent.getExtras().get(AppConstants.GROWTH_PUBLIC_PROFILE);
                mPublicProfileGrowthBuddiesDialogFragment.notifyList(mentorDetailItem);
                if(mFragmentOpen.getChampionViaCommentReaction()!=AppConstants.ONE_CONSTANT)
                {
                    mFragmentOpen.setChampionViaCommentReaction(AppConstants.NO_REACTION_CONSTANT);
                    FeedDetail feedDetail = intent.getParcelableExtra(AppConstants.FEED_SCREEN);
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((HomeFragment) fragment).commentListRefresh(feedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                    }
                }

            }
        }

    }

    public void selectImageFrmCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (null == localImageSaveForChallenge && null == mImageCaptureUri) {
                    Uri imageCaptureUri;
                    File localImageSaveForChallenge;
                    localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
                    imageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                    this.localImageSaveForChallenge = localImageSaveForChallenge;
                    mImageCaptureUri = imageCaptureUri;
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);
                } else {
                    mImageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);
                }
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (null == localImageSaveForChallenge && null == mImageCaptureUri) {
                Uri imageCaptureUri;
                File localImageSaveForChallenge;
                localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
                imageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                this.localImageSaveForChallenge = localImageSaveForChallenge;
                mImageCaptureUri = imageCaptureUri;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);

            } else {
                mImageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);
            }

        }

    }

    public void selectImageFrmGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent galIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galIntent.setType("image/*");
                    startActivityForResult(galIntent, AppConstants.REQUEST_CODE_FOR_GALLERY);
                } catch (Exception e) {
                }
            }
        } else {
            try {
                Intent galIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galIntent.setType("image/*");
                startActivityForResult(galIntent, AppConstants.REQUEST_CODE_FOR_GALLERY);
            } catch (Exception e) {
            }
        }
    }

    private void cropingIMG() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List list = getPackageManager().queryIntentActivities(intent, 0);
        intent.setData(mImageCaptureUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localImageSaveForChallenge));
        if (StringUtil.isNotEmptyCollection(list)) {
            Intent i = new Intent(intent);
            ResolveInfo res = (ResolveInfo) list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING);
        }

    }

    private void imageCropping(Intent intent) {
        try {
            if (localImageSaveForChallenge.exists()) {
                Bitmap photo = decodeFile(localImageSaveForChallenge);
                if (null != mChallengeSuccessDialogFragment) {
                    mChallengeSuccessDialogFragment.setImageOnHolder(photo);
                }
            } else {
                Toast.makeText(this, "Error while save image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        }
        return null;
    }

    private void editCommunityPostResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
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
    }

    private void jobFilterActivityResponse(Intent intent) {
        FeedRequestPojo feedRequestPojo = (FeedRequestPojo) intent.getExtras().get(AppConstants.JOB_FILTER);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(JobFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((JobFragment) fragment).jobFilterIds(feedRequestPojo);
        }
    }

    private void createCommunityActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
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
    }

    private void articleDetailActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras()) {
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
    }

    private void communityDetailActivityResponse(Intent intent) {
        if (null != intent && null != intent.getExtras() && null != (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL)) {
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
                            if (null != mViewPagerAdapter) {
                                Fragment community = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
                                if (AppUtils.isFragmentUIActive(community)) {
                                    ((MyCommunitiesFragment) community).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                                }
                            } else {
                                if(mFeedDetail.isViewed())
                                {
                                   homeOnClick();
                                }else {
                                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                                    if (AppUtils.isFragmentUIActive(fragment)) {
                                        ((HomeFragment) fragment).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
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
            championDetailActivity(feedDetail.getCreatedBy(),feedDetail.getItemPosition());
        } else if (baseResponse instanceof CommentReactionDoc) {
            CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
            championDetailActivity(commentReactionDoc.getParticipantId(),commentReactionDoc.getItemPosition());
        }
    }

    private void championDetailActivity(Long userId,int position) {
        Intent intent = new Intent(this, PublicProfileGrowthBuddiesDetailActivity.class);
        Bundle bundle = new Bundle();
        mFeedDetail = new FeedDetail();
        mFeedDetail.setIdOfEntityOrParticipant(userId);
        mFeedDetail.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        mFeedDetail.setItemPosition(position);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);

        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, null);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
    }
}