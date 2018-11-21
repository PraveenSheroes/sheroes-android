package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.io.File;
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
import appliedlife.pvtltd.SHEROES.basecomponents.ExpandableTextCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.ProgressbarView;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.ErrorUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashProgressBar;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ExpandableTextView;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.DeactivateProfileDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ReportUserProfileDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.UnFollowDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowCallback;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IProfileView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;
import static appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity.BIO_MAX_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog.ALL_STAR_END_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog.BEGINNER_START_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog.INTERMEDIATE_END_LIMIT;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */

public class ProfileActivity extends BaseActivity implements BaseHolderInterface, HomeView, IProfileView,
        ViewPager.OnPageChangeListener, ProgressbarView, ExpandableTextCallback, IFollowCallback {

    //region constants
    private final String TAG = LogUtils.makeLogTag(ProfileActivity.class);
    private static final String SCREEN_LABEL = "Profile Screen";

    private static final float ORIGINAL_SIZE = 1.0f;
    private static final float EXPANDED_SIZE = 1.02f;
    private static final int ANIMATION_REPEAT_DURATION = 700;
    private static final int ANIMATION_MAX_DURATION = 5000;
    private static final int ADMIN_TYPE_ID = 2;
    private static final int COMMUNITY_MODERATOR_TYPE_ID = 13;
    private static final int MAX_BADGE_COUNT = 4;
    private static final String BADGE_COUNTER_FONT_FAMILY = "sans-serif-medium";
    //endregion constants

    //region injected variable
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    ProfilePresenterImpl profilePresenter;

    @Inject
    AppUtils mAppUtils;

    @Inject
    Preference<AppConfiguration> mConfiguration;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Inject
    FeedUtils feedUtils;

    @Inject
    ErrorUtil errorUtil;
    //endregion injected variable

    //region bind variables
    @Bind(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.iv_mentor_full_view_icon)
    CircleImageView mProfileIcon;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.view_pager_mentor)
    ViewPager mViewPager;

    @Bind(R.id.toolbar_mentor)
    Toolbar mToolbar;

    @Bind(R.id.loader_gif)
    CardView loaderGif;

    @Bind(R.id.collapsing_toolbar_mentor)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.tab_mentor)
    TabLayout mTabLayout;

    @Bind(R.id.tv_mentor_name)
    TextView userName;

    @Bind(R.id.iv_public_profile_image)
    ImageView ivPublicProfileImage;

    @Bind(R.id.tv_profession)
    TextView tvProfession;

    @Bind(R.id.profession_container)
    RelativeLayout skillContainer;

    @Bind(R.id.li_loc_profession)
    LinearLayout locationViewContainer;

    @Bind(R.id.tv_loc)
    TextView tvLoc;

    @Bind(R.id.description_container)
    RelativeLayout descriptionContainer;

    @Bind(R.id.tv_mentor_description)
    TextView userDescription;

    @Bind(R.id.ll_profile)
    LinearLayout llProfile;

    @Bind(R.id.li_post)
    LinearLayout liPost;

    @Bind(R.id.tv_mentor_post_count)
    TextView userTotalPostCount;

    @Bind(R.id.tv_mentor_post)
    TextView tvMentorPost;

    @Bind(R.id.li_follower)
    LinearLayout liFollower;

    @Bind(R.id.li_following)
    LinearLayout liFollowing;

    @Bind(R.id.tv_mentor_following_title)
    TextView followingTitle;

    @Bind(R.id.tv_mentor_following_count)
    TextView followingCount;

    @Bind(R.id.tv_mentor_follower_count)
    TextView userFollowerCount;

    @Bind(R.id.tv_mentor_follower)
    TextView userFollower;

    @Bind(R.id.tv_mentor_toolbar_name)
    TextView tvMentorToolbarName;

    @Bind(R.id.tv_profile_menu)
    TextView profileToolbarMenu;

    @Bind(R.id.tv_mentor_dashboard_follow)
    TextView tvMentorDashBoardFollow;

    @Bind(R.id.edit_icon)
    ImageView editIcon;

    @Bind(R.id.user_badge)
    ImageView userBadgeIcon;

    @Bind(R.id.badges_container)
    LinearLayout badgeContainer;

    @Bind(R.id.share_profile)
    TextView shareProfile;

    @Bind(R.id.iv_mentor_verified)
    ImageView verifiedIcon;

    @Bind(R.id.profile_level)
    TextView profileLevel;

    @Bind(R.id.progress_bar_holder)
    ViewGroup progressbarContainer;

    @Bind(R.id.profile_level_container)
    LinearLayout profileStrengthViewContainer;

    @Bind(R.id.new_feature)
    TextView newFeature;

    @Bind(R.id.beginner)
    ImageView beginnerTick;

    @Bind(R.id.intermediate)
    ImageView intermediateTick;

    @Bind(R.id.all_star)
    ImageView allStarTick;

    @Bind(R.id.fab_post)
    FloatingActionButton createPost;

    @Bind(R.id.cl_story_footer)
    CardView clStoryFooter;

    @BindDimen(R.dimen.profile_badge_icon)
    int badgeIconSize;

    @BindDimen(R.dimen.profile_badge_margin_right)
    int badgeIconMargin;

    @BindDimen(R.dimen.profile_badge_counter_text)
    int badgeCounterTextSize;

    @BindDimen(R.dimen.dp_size_90)
    int profileSize;

    @Bind(R.id.rl_mentor_full_view_header)
    RelativeLayout rlMentorFullViewHeader;

    @Bind(R.id.view_profile)
    View toolTipProfile;

    @Bind(R.id.view_tool_follow)
    View viewToolTipFollow;

    @Bind(R.id.dashed_progressbar)
    DashProgressBar dashProgressBar;
    //endregion bind variables

    //region member variable
    private String mSourceName;
    private String userNameTitle;
    private String viewLessText, viewMoreText;

    private boolean isUserDeactivated;
    private boolean isProfileClicked = false;
    private boolean isOwnProfile = false;
    private boolean isChampion;
    private boolean isWriteAStory;

    private Long mChampionId;
    private long mLoggedInUserIdTypeId = -1;
    private int mFromNotification;
    private int itemPosition;
    private long mLoggedInUserId = -1;
    //endregion member variable

    //region view variable
    private FeedDetail mFeedDetail;
    private ViewPagerAdapter mViewPagerAdapter;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;
    private ProfileStrengthDialog mProfileStrengthDialog = null;
    private UnFollowDialogFragment unFollowDialogFragment;
    private DeactivateProfileDialogFragment mDeactivateProfileDialogFragment;
    private ReportUserProfileDialogFragment mReportUserProfileDialogFragment;
    private FragmentOpen mFragmentOpen;
    private UserSolrObj mUserSolarObject;
    private File localImageSaveForChallenge;
    private PopupWindow popupWindowFollowTooTip;
    private UserSolrObj followedUserSolrObj;
    private ProfileStrengthDialog.ProfileLevelType profileLevelType;
    //endregion view variable

    //region activity lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_profile_layout);
        mHomePresenter.attachView(this);
        profilePresenter.attachView(this);
        ButterKnife.bind(this);
        viewMoreText = getString(R.string.ID_VIEW_MORE_MENTOR);
        viewLessText = getString(R.string.ID_LESS);
        setupToolbarItemsColor();
        invalidateOptionsMenu();

        loaderGif.setVisibility(View.VISIBLE);
        loaderGif.bringToFront();

        mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mUserSolarObject = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.GROWTH_PUBLIC_PROFILE));
            mFeedDetail = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.MENTOR_DETAIL));
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            mChampionId = getIntent().getExtras().getLong(AppConstants.CHAMPION_ID);
            isChampion = getIntent().getExtras().getBoolean(AppConstants.IS_CHAMPION_ID);
            isWriteAStory = getIntent().getExtras().getBoolean(STORIES_TAB);
            mSourceName = getIntent().getExtras().getString(BaseActivity.SOURCE_SCREEN);
            profileLevelType = (ProfileStrengthDialog.ProfileLevelType) getIntent().getSerializableExtra(ProfileStrengthDialog.PROFILE_LEVEL);
        }
        if (null != mUserSolarObject) {
            itemPosition = mUserSolarObject.getItemPosition();
        } else if (null != mFeedDetail) {
            itemPosition = mFeedDetail.getItemPosition();
        }
        if (mChampionId > 0 && null == mUserSolarObject) {
            isProfileClicked = true;
            mUserSolarObject = new UserSolrObj();
            mUserSolarObject.setEntityOrParticipantId(mChampionId);
            mUserSolarObject.setSolrIgnoreMentorCommunityId(mChampionId);
            mUserSolarObject.setIdOfEntityOrParticipant(mChampionId);
        }

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            mLoggedInUserId = mUserPreference.get().getUserSummary().getUserId();
            mLoggedInUserIdTypeId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
        }
        String feedSubType = isChampion ? AppConstants.CAROUSEL_SUB_TYPE : AppConstants.USER_SUB_TYPE;
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(feedSubType, AppConstants.ONE_CONSTANT, mChampionId));

        feedUtils.setConfigurableShareOption(isWhatsAppShare());
        ((SheroesApplication) getApplication()).trackScreenView(AppConstants.PUBLIC_PROFILE);
    }

    @Override
    protected void onStop() {

        if (mProfileStrengthDialog != null && mProfileStrengthDialog.isVisible()) {
            mProfileStrengthDialog.dismiss();
        }

        if (popupWindowFollowTooTip != null && popupWindowFollowTooTip.isShowing()) {
            popupWindowFollowTooTip.dismiss();
        }

        if (unFollowDialogFragment != null && unFollowDialogFragment.isVisible()) {
            unFollowDialogFragment.dismiss();
        }

        if (mReportUserProfileDialogFragment != null && mReportUserProfileDialogFragment.isVisible()) {
            mReportUserProfileDialogFragment.dismiss();
        }

        if (mDeactivateProfileDialogFragment != null && mDeactivateProfileDialogFragment.isVisible()) {
            mDeactivateProfileDialogFragment.dismiss();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        profilePresenter.detachView();
        mHomePresenter.detachView();
        super.onDestroy();
    }
    //endregion activity lifecycle methods

    //region override methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, position);
        if (fragment instanceof FeedFragment) {
            if (isOwnProfile) {
                clStoryFooter.setVisibility(View.VISIBLE);
            } else {
                clStoryFooter.setVisibility(View.GONE);
            }
            createPost.setVisibility(View.GONE);
        } else {
            clStoryFooter.setVisibility(View.GONE);
            createPost.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
    public void onBackPressed() {
        onBackClick();
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        errorUtil.onShowErrorDialog(this, s, feedParticipationEnum);
        loaderGif.setVisibility(View.GONE);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
    }

    @Override
    public void getFollowedMentors(FollowedUsersResponse profileFeedResponse) {
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mUserSolarObject = (UserSolrObj) feedDetailList.get(0);
            String screenName = AppConstants.GROWTH_PUBLIC_PROFILE;
            mUserSolarObject.setCallFromName(screenName);

            setProfileDetails(mUserSolarObject);
            loaderGif.setVisibility(View.GONE);

            if (profileLevelType != null)
                profileStrengthDialog(profileLevelType);
        }
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse spamResponse) {
        if (spamResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {

            if (ProfileActivity.this.isFinishing()) return;

            if (spamResponse.isSpammed()) {
                CommonUtil.createDialog(ProfileActivity.this, getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_marked_dialog_message, spamResponse.getModelType().toLowerCase()));
            } else if (!spamResponse.isSpamAlreadyReported()) {
                CommonUtil.createDialog(ProfileActivity.this, getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.spam_confirmation_dialog_message));
            } else {
                CommonUtil.createDialog(ProfileActivity.this, getResources().getString(R.string.reported_spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_confirmation_dialog_message, spamResponse.getModelType().toLowerCase()));
            }
        }
    }

    @Override
    public void onUserDeactivation(BaseResponse baseResponse) {
        isUserDeactivated = true;
        onBackClick();
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case FOLLOW_UNFOLLOW:
                UserSolrObj userSolrObj = (UserSolrObj) baseResponse;
                onProfileFollowed(userSolrObj);
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
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
        if (mValue == REQUEST_CODE_FOR_SELF_PROFILE_DETAIL && mLoggedInUserId != -1) {
            championDetailActivity(mLoggedInUserId, 1, isChampion, AppConstants.FEED_SCREEN); //self profile
        } else if (mValue == REQUEST_CODE_CHAMPION_TITLE) {
            UserPostSolrObj feedDetail = (UserPostSolrObj) baseResponse;
            ProfileActivity.navigateTo(this, feedDetail.getAuthorParticipantId(), isChampion, PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {
        if (boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {
            LoginResponse userDetailsResponse = null;
            if (mUserPreference != null && mUserPreference.isSet()) {
                userDetailsResponse = mUserPreference.get();
            }
            if (userDetailsResponse != null) {
                try {
                    if (boardingDataResponse.getResponse() != null && boardingDataResponse.getResponse().contains("img.") && boardingDataResponse.getResponse().startsWith("http")) {
                        setProfileNameData(boardingDataResponse.getResponse());

                        //update progress bar
                        if (mUserSolarObject != null) {
                            mUserSolarObject.setFilledProfileFields(boardingDataResponse.getUserSolrObj().getFilledProfileFields());
                            mUserSolarObject.setUnfilledProfileFields(boardingDataResponse.getUserSolrObj().getUnfilledProfileFields());
                            mUserSolarObject.setProfileCompletionWeight(boardingDataResponse.getUserSolrObj().getProfileCompletionWeight());
                            dashProgressBar.setProgress(boardingDataResponse.getUserSolrObj().getProfileCompletionWeight(), false);
                            setProfileStrength();
                        }

                        //Save image
                        userDetailsResponse.getUserSummary().setPhotoUrl(boardingDataResponse.getResponse());
                        mUserPreference.get().getUserSummary().setPhotoUrl(boardingDataResponse.getResponse());
                        mUserPreference.set(userDetailsResponse);
                        refreshImageView(boardingDataResponse.getResponse());
                    }
                } catch (Exception e) {
                    LogUtils.info(TAG, e.getMessage());
                }
            }
        }
    }

    @Override
    public void onViewRendered(float dashWidth) {

        ConfigData configData = new ConfigData();
        int beginnerTickIndex = configData.beginnerStartIndex;
        int intermediateTickIndex = configData.intermediateStartIndex;

        if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
            beginnerTickIndex = mConfiguration.get().configData.beginnerStartIndex;
            intermediateTickIndex = mConfiguration.get().configData.intermediateStartIndex;
        }
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins((int) (dashWidth * beginnerTickIndex), 0, 0, 0);
        beginnerTick.setLayoutParams(buttonLayoutParams);

        RelativeLayout.LayoutParams intermediateLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        intermediateLayoutParams.setMargins((int) (dashWidth * intermediateTickIndex), 0, 0, 0);
        intermediateTick.setLayoutParams(intermediateLayoutParams);

        animationOnProgressBar();
    }

    @Override
    public void onTextResize(boolean isCollapsed) {
        if (!isCollapsed) {
            progressbarContainer.setVisibility(View.VISIBLE);
            profileStrengthViewContainer.setVisibility(View.VISIBLE);
        } else {
            progressbarContainer.setVisibility(View.GONE);
            profileStrengthViewContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProfileFollowed(UserSolrObj userSolrObj) {
        followedUserSolrObj = userSolrObj;
        userFollowerCount.setText(String.valueOf(changeNumberToNumericSuffix(userSolrObj.getFollowerCount())));
        updateFollowedButton();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        return new EventProperty.Builder()
                .id(String.valueOf(mChampionId))
                .isMentor(isChampion)
                .isOwnProfile(isOwnProfile)
                .build();
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {

            if (resultCode == REQUEST_CODE_FOR_EDIT_PROFILE) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    invalidateUserInformation(bundle.getString(EditUserProfileActivity.USER_NAME), bundle.getString(EditUserProfileActivity.LOCATION), bundle.getString(EditUserProfileActivity.USER_DESCRIPTION), bundle.getString(EditUserProfileActivity.IMAGE_URL),
                            bundle.getString(EditUserProfileActivity.FILLED_FIELDS), bundle.getString(EditUserProfileActivity.UNFILLED_FIELDS), bundle.getFloat(EditUserProfileActivity.PROFILE_COMPLETION_WEIGHT));
                }
            }

            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_CAMERA:
                case AppConstants.REQUEST_CODE_FOR_GALLERY:
                    mImageCaptureUri = intent.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        croppingIMG();
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING:
                    imageCropping(intent);
                    break;

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        try {
                            File file = new File(result.getUri().getPath());
                            Bitmap photo = CompressImageUtil.decodeFile(file);
                            mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mHomePresenter.getUserSummaryDetails(mAppUtils.getUserProfileRequestBuilder(AppConstants.PROFILE_PIC_SUB_TYPE, AppConstants.PROFILE_PIC_TYPE, mEncodeImageUrl));
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                    }

                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        }
    }
    //endregion override methods

    //region onclick methods
    @OnClick({R.id.beginner})
    protected void openBeginnerDialog() {
        profileStrengthDialog(ProfileStrengthDialog.ProfileLevelType.BEGINNER);
    }

    @OnClick(R.id.intermediate)
    protected void openIntermediateProgressDialog() {
        profileStrengthDialog(ProfileStrengthDialog.ProfileLevelType.INTERMEDIATE);
    }

    @OnClick(R.id.all_star)
    protected void openAllStarProgressDialog() {
        profileStrengthDialog(ProfileStrengthDialog.ProfileLevelType.ALLSTAR);
    }

    @OnClick(R.id.new_feature)
    protected void openUserProfileLevelDialog() {
        if (mUserSolarObject != null) {
            CommonUtil.setPrefValue(AppConstants.PROFILE_OFFER_PREF);
            ProfileStrengthDialog.ProfileLevelType profileLevelType = userLevel(mUserSolarObject);
            profileStrengthDialog(profileLevelType);
            newFeature.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_profile_menu)
    public void onMenuClick() {
        onProfileMenuClick(mUserSolarObject, profileToolbarMenu);
    }

    @OnClick(R.id.fab_post)
    public void createNewPost() {
        CommunityPost communityPost = new CommunityPost();
        communityPost.createPostRequestFrom = AppConstants.CREATE_POST;
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
    }

    @OnClick(R.id.cl_story_footer)
    public void writeAStory() {
        CreateStoryActivity.navigateTo(this, 1, getScreenName(), null);
    }

    @OnClick(R.id.li_follower)
    public void followerClick() {
        if (StringUtil.isNotNullOrEmptyString(userFollowerCount.getText().toString()) && !userFollowerCount.getText().toString().equalsIgnoreCase("0")) {
            FollowingActivity.navigateTo(this, mChampionId, isOwnProfile, getScreenName(), FollowingEnum.FOLLOWERS, null);
        }
    }

    @OnClick(R.id.li_following)
    public void followingClick() {
        if (StringUtil.isNotNullOrEmptyString(followingCount.getText().toString()) && !followingCount.getText().toString().equalsIgnoreCase("0")) {
            FollowingActivity.navigateTo(this, mChampionId, isOwnProfile, getScreenName(), FollowingEnum.FOLLOWING, null);
        }
    }

    @OnClick(R.id.li_post)
    public void selectScrollPstTab() {
        if (isChampion) {
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setCurrentItem(1);
        }
        mAppBarLayout.setExpanded(false);
    }

    @OnClick(R.id.share_profile)
    public void shareProfile() {
        shareCardViaSocial();
    }

    @OnClick({R.id.tv_mentor_name, R.id.tv_loc})
    public void navigateToProfileEditing() {
        if (isOwnProfile) {
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
                EditUserProfileActivity.navigateTo(ProfileActivity.this, SCREEN_LABEL, mUserSolarObject.getImageUrl(), null, 1);
            }
        }
    }

    @OnClick(R.id.iv_mentor_full_view_icon)
    public void onImageEditClicked() {
        if (isOwnProfile) {
            addAnalyticsEvents(Event.PROFILE_PIC_EDIT_CLICKED);
            CameraBottomSheetFragment.showDialog(this, SCREEN_LABEL);
        }
    }

    @OnClick(R.id.tv_mentor_dashboard_follow)
    public void mentorFollowClick() {
        if (isOwnProfile) {
            navigateToProfileEditing();
        } else {
            PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
            publicProfileListRequest.setIdOfEntityParticipant(mUserSolarObject.getIdOfEntityOrParticipant());

            if (mUserSolarObject.isSolrIgnoreIsMentorFollowed() || mUserSolarObject.isSolrIgnoreIsUserFollowed()) {
                unFollowDialog(publicProfileListRequest);
            } else {
                mHomePresenter.getFollowFromPresenter(publicProfileListRequest, mUserSolarObject);
                addAnalyticsEvents(Event.PROFILE_FOLLOWED);
            }
        }
    }
    //endregion onclick methods

    //region private methods
    private boolean isWhatsAppShare() {
        boolean isWhatsAppShare = false;
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareText = "";
            shareText = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareText)) {
                if (shareText.equalsIgnoreCase("true")) {
                    isWhatsAppShare = true;
                }
            }
        }
        return isWhatsAppShare;
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    private void updateProfileInfo() {

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getCityName())) {
            tvLoc.setText(mUserSolarObject.getCityName());
            tvLoc.setVisibility(View.VISIBLE);
        } else {
            if (isOwnProfile) {
                tvLoc.setVisibility(View.VISIBLE);
                tvLoc.setText(R.string.add_location);
            } else {
                tvLoc.setVisibility(View.GONE);
                //For public profile if location is unfilled hide the container
                if (!isChampion) {
                    locationViewContainer.setVisibility(View.GONE);
                } else {
                    locationViewContainer.setVisibility(View.VISIBLE);
                }
            }
        }
        if (StringUtil.isNotEmptyCollection(mUserSolarObject.getCanHelpIns())) {
            tvProfession.setText(mUserSolarObject.getCanHelpIns().get(0)); //skills
            skillContainer.setVisibility(View.VISIBLE);
        } else {
            if (isOwnProfile && isChampion) {
                tvProfession.setText(R.string.add_skills);
                skillContainer.setVisibility(View.VISIBLE);
            } else {
                skillContainer.setVisibility(View.GONE);
            }
        }
        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getDescription())) {
            //For public profile if description is unfilled hide the container
            String description = mUserSolarObject.getDescription();
            description = StringUtil.fromHtml(description).toString();
            if (description.length() > BIO_MAX_LIMIT) {
                description = description.substring(0, BIO_MAX_LIMIT);
            }

            if (isOwnProfile) {
                userDescription.setText(description);
                ExpandableTextView expandableTextView = ExpandableTextView.getInstance();
                expandableTextView.makeTextViewResizable(userDescription, 1, getString(R.string.ID_VIEW_MORE_MENTOR), true, this, viewMoreText, viewLessText);
            } else {
                userDescription.setText(description);
            }
        } else {
            if (isOwnProfile) {
                userDescription.setText(R.string.add_desc);
            } else {
                descriptionContainer.setVisibility(View.GONE);
            }
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getImageUrl())) {
            if (!isFinishing()) {
                String profilePic = CommonUtil.getThumborUri(mUserSolarObject.getImageUrl(), profileSize, profileSize);
                mProfileIcon.setCircularImage(true);
                mProfileIcon.bindImage(profilePic);
            }
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getNameOrTitle())) {
            String userNameTitle = CommonUtil.camelCaseString(mUserSolarObject.getNameOrTitle());
            userName.setText(userNameTitle);
            tvMentorToolbarName.setText(userNameTitle);
            setUserNameTitle(userNameTitle);
        }

        setUsersPostCount(mUserSolarObject.getPostCount());
        setUsersFollowerCount(mUserSolarObject.getFollowerCount());
        setUsersFollowingCount(mUserSolarObject.getFollowingCount());

        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_PUBLIC_PROFILE));
    }

    private void toolTipForFollowUser() {
        try {
            viewToolTipFollow.setVisibility(View.INVISIBLE);
            final View popupFollowToolTip = LayoutInflater.from(this).inflate(R.layout.tooltip_arrow_up_side, null);
            popupWindowFollowTooTip = new PopupWindow(popupFollowToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindowFollowTooTip.setOutsideTouchable(true);
            popupWindowFollowTooTip.showAsDropDown(viewToolTipFollow, -50, 0);
            final ImageView ivArrow = popupFollowToolTip.findViewById(R.id.iv_arrow);
            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(20, ProfileActivity.this), 0);
            imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            ivArrow.setLayoutParams(imageParams);
            final LinearLayout llToolTipBg = popupFollowToolTip.findViewById(R.id.ll_tool_tip_bg);
            RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(300, ProfileActivity.this), LinearLayout.LayoutParams.WRAP_CONTENT);
            llParams.setMargins(CommonUtil.convertDpToPixel(20, ProfileActivity.this), 0, 0, 0);
            llParams.addRule(RelativeLayout.BELOW, R.id.iv_arrow);
            llToolTipBg.setLayoutParams(llParams);
            final TextView tvGotIt = popupFollowToolTip.findViewById(R.id.got_it);
            final TextView tvTitle = popupFollowToolTip.findViewById(R.id.title);
            tvTitle.setText(getString(R.string.tool_tip_follower));
            tvGotIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindowFollowTooTip.dismiss();
                    viewToolTipFollow.setVisibility(View.GONE);
                }
            });
        } catch (WindowManager.BadTokenException e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    private void setProfileStrength() {
        if (mUserSolarObject.getProfileCompletionWeight() > BEGINNER_START_LIMIT && mUserSolarObject.getProfileCompletionWeight() < INTERMEDIATE_END_LIMIT) {
            profileLevel.setText(R.string.progress_status_beginner);

            if (mUserSolarObject.getProfileCompletionWeight() >= ProfileStrengthDialog.BEGINNER_END_LIMIT) {
                beginnerTick.setImageResource(R.drawable.vector_level_complete);
            } else {
                beginnerTick.setImageResource(R.drawable.vector_level_incomplete);
            }
            intermediateTick.setImageResource(R.drawable.vector_level_incomplete);
            allStarTick.setImageResource(R.drawable.vector_all_level_incomplete);

        } else if (mUserSolarObject.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolarObject.getUnfilledProfileFields())) {
            profileLevel.setText(R.string.progress_status_all_star);

            if (mUserSolarObject.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolarObject.getUnfilledProfileFields())) {
                allStarTick.setImageResource(R.drawable.vector_all_level_complete);
            } else {
                allStarTick.setImageResource(R.drawable.vector_all_level_incomplete);
            }
            beginnerTick.setImageResource(R.drawable.vector_level_complete);
            intermediateTick.setImageResource(R.drawable.vector_level_complete);

        } else {
            profileLevel.setText(R.string.progress_level_status_intermediate);

            if (mUserSolarObject.getProfileCompletionWeight() >= INTERMEDIATE_END_LIMIT) {
                intermediateTick.setImageResource(R.drawable.vector_level_complete);
            } else {
                intermediateTick.setImageResource(R.drawable.vector_level_incomplete);
            }
            allStarTick.setImageResource(R.drawable.vector_all_level_incomplete);
            beginnerTick.setImageResource(R.drawable.vector_level_complete);

        }
    }

    private void updateFollowedButton() {
        if (mUserSolarObject.isSolrIgnoreIsMentorFollowed() || mUserSolarObject.isSolrIgnoreIsUserFollowed()) {
            tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvMentorDashBoardFollow.setText(this.getString(R.string.following_user));
            tvMentorDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.footer_icon_text));
            tvMentorDashBoardFollow.setText(this.getString(R.string.follow_user));
            tvMentorDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }

        if (CommonUtil.ensureFirstTime(AppConstants.FOLLOWER_SHARE_PREF)) {
            toolTipForFollowUser();
        }
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbar);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        if (!isChampion) {
            mViewPagerAdapter.addFragment(ProfileDetailsFragment.createInstance(mChampionId, mUserSolarObject.getNameOrTitle()), getString(R.string.ID_PROFILE));
        }
        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        String screenName;
        if (mLoggedInUserId != mUserSolarObject.getIdOfEntityOrParticipant()) {
            screenName = getString(R.string.stories);
            bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=UserStoryStream&userId=" + mUserSolarObject.getIdOfEntityOrParticipant());
        } else {
            screenName = getString(R.string.my_stories);
            bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=UserStoryStream&myStory=true");
        }
        bundle.putString(AppConstants.SCREEN_NAME, "Profile Stories Screen");
        bundle.putBoolean(FeedFragment.IS_HOME_FEED, false);
        bundle.putString(FeedFragment.STREAM_NAME, AppConstants.STORY_STREAM);
        feedFragment.setArguments(bundle);
        mViewPagerAdapter.addFragment(feedFragment, screenName);
        mViewPager.setAdapter(mViewPagerAdapter);

        if (isWriteAStory) {
            clStoryFooter.setVisibility(View.VISIBLE);
            mViewPager.setCurrentItem(mViewPagerAdapter.getCount() - 1);
        } else {
            if (isOwnProfile) {
                createPost.setVisibility(View.VISIBLE);
            } else {
                createPost.setVisibility(View.GONE);
            }
            if (!isChampion) { //for user make post as default tab
                mViewPager.setCurrentItem(1);
            }
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(this);
    }

    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    private void onFollowedActivityResultOfParentRefresh(UserSolrObj userSolrObj) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (mUserSolarObject != null) {
            mUserSolarObject.currentItemPosition = itemPosition;
            Parcelable parcelable = Parcels.wrap(userSolrObj);
            bundle.putParcelable(AppConstants.USER_FOLLOWED_DETAIL, parcelable);
            intent.putExtras(bundle);
            setResult(AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED, intent);
        }
    }

    private void onActivityResultOfParentRefresh() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (mUserSolarObject != null) {
            mUserSolarObject.currentItemPosition = itemPosition;
            Parcelable parcelableMentorDetail = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentorDetail);
            Parcelable parcelable = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.FEED_SCREEN, parcelable);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
    }

    private void shareCardViaSocial() {
        String branchPostDeepLink;
        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getPostShortBranchUrls())) {
            branchPostDeepLink = mUserSolarObject.getPostShortBranchUrls();
        } else {
            branchPostDeepLink = isChampion ? mUserSolarObject.getChampionDeepLinkUrl() : mUserSolarObject.getDeepLinkUrl();
        }
        addAnalyticsEvents(Event.PROFILE_SHARED);
        createShareImage(branchPostDeepLink);
    }

    private void setupProfileBadges(final UserSolrObj userSolrObj) {

        if (userSolrObj == null || !StringUtil.isNotEmptyCollection(userSolrObj.getUserBadgesList()))
            return;

        badgeContainer.setVisibility(View.VISIBLE);

        int length = userSolrObj.getUserBadgesList().size();
        int counter = 0;
        for (final BadgeDetails badgeDetails : userSolrObj.getUserBadgesList()) {
            final ImageView badge = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(badgeIconSize, badgeIconSize);
            layoutParams.setMargins(0, 0, badgeIconMargin, 0);
            badge.setLayoutParams(layoutParams);
            if (StringUtil.isNotNullOrEmptyString(badgeDetails.getImageUrl())) {
                Glide.with(badge.getContext())
                        .load(badgeDetails.getImageUrl())
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(userBadgeIcon.getContext())))
                        .into(badge);
            }

            if (badgeDetails.isActive()) {
                badge.setBackgroundResource(R.drawable.circular_background_yellow);
            } else {
                badge.setBackgroundResource(R.drawable.circular_background_grey);
            }
            badgeContainer.addView(badge);

            badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BadgeDetailsDialogFragment.showDialog(ProfileActivity.this, mUserSolarObject, badgeDetails, SCREEN_LABEL, false);
                }
            });
            counter++;

            if (counter == MAX_BADGE_COUNT) break;
        }

        if (length > MAX_BADGE_COUNT) {
            TextView badgeCount = new TextView(this);
            badgeCount.setTypeface(Typeface.create(BADGE_COUNTER_FONT_FAMILY, Typeface.NORMAL));
            badgeCount.setTextSize(badgeCounterTextSize);
            badgeCount.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.badge_counter)));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(badgeIconSize, badgeIconSize);
            badgeCount.setLayoutParams(layoutParams);
            badgeCount.setText(getString(R.string.BadgeCounter, (length - MAX_BADGE_COUNT)));
            badgeCount.setBackground(getResources().getDrawable(R.drawable.circular_background_red));
            badgeCount.setGravity(Gravity.CENTER);
            badgeContainer.addView(badgeCount);

            badgeCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BadgeClosetActivity.navigateTo(ProfileActivity.this, userSolrObj.getUserBadgesList(), userSolrObj, SCREEN_LABEL);
                }
            });
        }
    }

    private void setupSheBadge(UserSolrObj userSolrObj) {
        if (!TextUtils.isEmpty(userSolrObj.getProfileBadgeUrl())) {
            userBadgeIcon.setVisibility(View.VISIBLE);
            Glide.with(userBadgeIcon.getContext())
                    .load(userSolrObj.getProfileBadgeUrl())
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(userBadgeIcon.getContext())))
                    .into(userBadgeIcon);
        } else {
            userBadgeIcon.setVisibility(View.GONE);
        }
    }

    private void createShareImage(final String branchPostDeepLink) {
        if (mUserSolarObject != null) {
            View view;
            view = LayoutInflater.from(this).inflate(R.layout.layout_user_share, null, false);

            final LinearLayout userDetailContainer = ButterKnife.findById(view, R.id.user_share_container);
            LinearLayout followingContainer = ButterKnife.findById(view, R.id.followings_count_view);
            final ImageView imageIcon = ButterKnife.findById(view, R.id.profile_pic);
            ImageView verifiedMentorIcon = ButterKnife.findById(view, R.id.badge);

            TextView name = ButterKnife.findById(view, R.id.user_name);
            TextView postCountNo = ButterKnife.findById(view, R.id.post_count);
            TextView followersCountNo = ButterKnife.findById(view, R.id.followers_count);
            TextView followingCountNo = ButterKnife.findById(view, R.id.following_count);
            TextView location = ButterKnife.findById(view, R.id.location_user);
            TextView description = ButterKnife.findById(view, R.id.description);

            if (isChampion) {
                verifiedMentorIcon.setVisibility(View.VISIBLE);
            } else {
                verifiedMentorIcon.setVisibility(View.GONE);
            }

            if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getNameOrTitle())) {
                name.setText(mUserSolarObject.getNameOrTitle());
            }

            followersCountNo.setText(userFollowerCount.getText());
            postCountNo.setText(userTotalPostCount.getText());

            followingContainer.setVisibility(View.VISIBLE);
            followingCountNo.setText(followingCount.getText());

            if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getDescription())) {
                Spanned descriptionSpan = StringUtil.fromHtml(mUserSolarObject.getDescription());
                description.setText(descriptionSpan);
            }

            if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getCityName())) {
                location.setVisibility(View.VISIBLE);
                location.setText(mUserSolarObject.getCityName());
            } else {
                location.setVisibility(View.INVISIBLE);
            }

            if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getImageUrl())) {
                Glide.with(this)
                        .asBitmap()
                        .load(mUserSolarObject.getImageUrl())
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(this)))
                        .into(new BitmapImageViewTarget(imageIcon) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                super.onResourceReady(resource, transition);
                                imageIcon.setImageBitmap(resource);
                                Bitmap bitmap = CommonUtil.getViewBitmap(userDetailContainer);
                                Uri contentUri = CommonUtil.getContentUriFromBitmap(ProfileActivity.this, bitmap);
                                if (contentUri != null) {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType(AppConstants.SHARE_MENU_TYPE);
                                    String profileSharedText = new ConfigData().mProfileSharedText;
                                    if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                                        profileSharedText = mConfiguration.get().configData.mProfileSharedText;
                                    }
                                    intent.putExtra(Intent.EXTRA_TEXT, profileSharedText + "\n\nLink : " + branchPostDeepLink);
                                    intent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                    intent.setType("image/*");
                                    startActivity(Intent.createChooser(intent, AppConstants.SHARE));
                                }
                            }
                        });
            }
        }
    }

    private void croppingIMG() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List list = getPackageManager().queryIntentActivities(intent, 0);
        intent.setData(mImageCaptureUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localImageSaveForChallenge));
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        if (StringUtil.isNotEmptyCollection(list)) {
            Intent i = new Intent(intent);
            ResolveInfo res = (ResolveInfo) list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING);
        }
    }

    private void invalidateProfileButton() {
        int mButtonSize = 31;
        if (isOwnProfile) {
            shareProfile.setVisibility(View.VISIBLE);
            if (mUserSolarObject.getProfileCompletionWeight() >= 85) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params.addRule(RelativeLayout.LEFT_OF, R.id.tv_mentor_dashboard_follow);
                shareProfile.setLayoutParams(params);
                shareProfile.setPadding(0, 0, 0, 2);
                shareProfile.setText(getString(R.string.SHARE_PROFILE));
                shareProfile.setBackgroundResource(R.drawable.selecter_invite_friend);

                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mButtonSize, this),
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params1.setMargins(CommonUtil.convertDpToPixel(8, this), 0, CommonUtil.convertDpToPixel(13, this), 0);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tvMentorDashBoardFollow.setText("");
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.vector_profile_edit_icon);
                tvMentorDashBoardFollow.setLayoutParams(params1);
            } else {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params.addRule(RelativeLayout.LEFT_OF, R.id.share_profile);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                tvMentorDashBoardFollow.setLayoutParams(params);
                tvMentorDashBoardFollow.setPadding(0, 0, 0, 2);
                tvMentorDashBoardFollow.setText(getString(R.string.ID_EDIT_PROFILE));
                tvMentorDashBoardFollow.setBackgroundResource(0);
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.selecter_invite_friend);

                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mButtonSize, this),
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params1.setMargins(CommonUtil.convertDpToPixel(8, this), 0, CommonUtil.convertDpToPixel(13, this), 0);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                shareProfile.setText("");
                shareProfile.setBackgroundResource(R.drawable.vector_share_profile);
                shareProfile.setLayoutParams(params1);
            }
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.convertDpToPixel(mButtonSize, this));
            params.setMargins(0, 0, 20, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tvMentorDashBoardFollow.setLayoutParams(params);
            shareProfile.setVisibility(View.GONE);
        }
    }

    private void profileStrengthDialog(ProfileStrengthDialog.ProfileLevelType profileLevelType) {
        if (mUserSolarObject == null) return;

        if (mProfileStrengthDialog != null && mProfileStrengthDialog.isVisible()) {
            mProfileStrengthDialog.dismiss();
        }

        mProfileStrengthDialog = new ProfileStrengthDialog();
        mProfileStrengthDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mProfileStrengthDialog.isVisible() && !mIsDestroyed) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.USER, parcelable);
            bundle.putSerializable(ProfileStrengthDialog.PROFILE_LEVEL, profileLevelType);
            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            mProfileStrengthDialog.setArguments(bundle);
            mProfileStrengthDialog.show(getFragmentManager(), ProfileStrengthDialog.class.getName());
        }
    }

    private void unFollowDialog(final PublicProfileListRequest unFollowRequest) {
        if (mUserSolarObject == null) return;

        if (unFollowDialogFragment != null && unFollowDialogFragment.isVisible()) {
            unFollowDialogFragment.dismiss();
        }

        unFollowDialogFragment = new UnFollowDialogFragment();
        unFollowDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!unFollowDialogFragment.isVisible() && !mIsDestroyed) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.USER, parcelable);

            Parcelable unFollowRequestParcelable = Parcels.wrap(unFollowRequest);
            bundle.putParcelable(AppConstants.UNFOLLOW, unFollowRequestParcelable);

            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, isChampion);
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, isOwnProfile);
            unFollowDialogFragment.setArguments(bundle);
            unFollowDialogFragment.show(getFragmentManager(), UnFollowDialogFragment.class.getName());
        }
    }

    private void deactivateUserDialog(final UserSolrObj userSolrObj) {

        if (mUserSolarObject == null || ProfileActivity.this.isFinishing()) return;

        if (mDeactivateProfileDialogFragment != null && mDeactivateProfileDialogFragment.isVisible()) {
            mDeactivateProfileDialogFragment.dismiss();
        }

        mDeactivateProfileDialogFragment = new DeactivateProfileDialogFragment();
        mDeactivateProfileDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mDeactivateProfileDialogFragment.isVisible() && !mIsDestroyed) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(userSolrObj);
            bundle.putParcelable(AppConstants.USER, parcelable);

            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, isChampion);
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, isOwnProfile);
            mDeactivateProfileDialogFragment.setArguments(bundle);
            mDeactivateProfileDialogFragment.show(getFragmentManager(), DeactivateProfileDialogFragment.class.getName());
        }
    }

    private void reportSpamDialog(final UserSolrObj userSolrObj) { //Add other type as parameterised object
        if (mUserSolarObject == null || ProfileActivity.this.isFinishing()) return;

        if (mReportUserProfileDialogFragment != null && mReportUserProfileDialogFragment.isVisible()) {
            mReportUserProfileDialogFragment.dismiss();
        }
        mReportUserProfileDialogFragment = new ReportUserProfileDialogFragment();
        mReportUserProfileDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mReportUserProfileDialogFragment.isVisible() && !mIsDestroyed) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(userSolrObj);
            bundle.putParcelable(AppConstants.USER, parcelable);

            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, isChampion);
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, isOwnProfile);
            bundle.putLong(AppConstants.LOGGED_IN_USER, mLoggedInUserId);
            mReportUserProfileDialogFragment.setArguments(bundle);
            mReportUserProfileDialogFragment.show(getFragmentManager(), ReportUserProfileDialogFragment.class.getName());
        }
    }

    private ProfileStrengthDialog.ProfileLevelType userLevel(UserSolrObj userSolrObj) {
        ProfileStrengthDialog.ProfileLevelType profileType;

        if (userSolrObj.getProfileCompletionWeight() > BEGINNER_START_LIMIT && userSolrObj.getProfileCompletionWeight() <= INTERMEDIATE_END_LIMIT) {
            profileType = ProfileStrengthDialog.ProfileLevelType.BEGINNER;
        } else if (userSolrObj.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT) {
            profileType = ProfileStrengthDialog.ProfileLevelType.ALLSTAR;
        } else {
            profileType = ProfileStrengthDialog.ProfileLevelType.INTERMEDIATE;
        }

        return profileType;
    }

    private void animationOnProgressBar() {
        if (!CommonUtil.getPrefValue(AppConstants.PROFILE_OFFER_PREF)) {
            final ScaleAnimation scaleAnimation = new ScaleAnimation(ORIGINAL_SIZE, EXPANDED_SIZE, ORIGINAL_SIZE, EXPANDED_SIZE, beginnerTick.getWidth() / 2, beginnerTick.getHeight() / 2);
            scaleAnimation.setDuration(ANIMATION_REPEAT_DURATION); // scale to 1.04 times as big in 700 milli-seconds
            scaleAnimation.setRepeatCount(Animation.INFINITE);
            scaleAnimation.setRepeatMode(Animation.INFINITE);
            scaleAnimation.setInterpolator(this, android.R.interpolator.accelerate_decelerate);

            beginnerTick.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.circle));
            intermediateTick.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.circle));
            allStarTick.setBackground(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.circle));

            beginnerTick.startAnimation(scaleAnimation);
            intermediateTick.startAnimation(scaleAnimation);
            allStarTick.startAnimation(scaleAnimation);

            beginnerTick.postDelayed(new Runnable() {
                public void run() {
                    scaleAnimation.cancel();
                    scaleAnimation.setAnimationListener(null);

                    beginnerTick.setBackground(null);
                    beginnerTick.clearAnimation();

                    intermediateTick.setBackground(null);
                    intermediateTick.clearAnimation();

                    allStarTick.setBackground(null);
                    allStarTick.clearAnimation();
                }
            }, ANIMATION_MAX_DURATION);
        }
    }

    private void setUsersFollowingCount(int numFound) {
        followingTitle.setText(getResources().getString(R.string.following));
        mUserSolarObject.setUserFollowing(numFound);
        followingCount.setText(String.valueOf(numFound));
        liFollowing.setVisibility(View.VISIBLE);
    }

    private void setUsersFollowerCount(int numFound) {
        String pluralFollower = getResources().getQuantityString(R.plurals.numberOfFollowers, numFound);
        mUserSolarObject.setFollowerCount(numFound);
        userFollowerCount.setText(String.valueOf(changeNumberToNumericSuffix(numFound)));
        userFollower.setText(pluralFollower);
        liFollower.setVisibility(View.VISIBLE);
    }

    private void setUsersPostCount(int postCount) {
        String pluralAnswer = getResources().getQuantityString(R.plurals.numberOfPosts, postCount);
        mUserSolarObject.setPostCount(postCount);
        userTotalPostCount.setText(String.valueOf(changeNumberToNumericSuffix(postCount)));
        tvMentorPost.setText(pluralAnswer);
        liPost.setVisibility(View.VISIBLE);
    }

    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void invalidateUserInformation(String name, String location, String userBio, String imageUrl, String filledFields, String unfilledFields, float progressPercentage) {
        if (StringUtil.isNotNullOrEmptyString(name)) {
            name = CommonUtil.camelCaseString(name);
            userName.setText(name);
            tvMentorToolbarName.setText(name);
        }

        if (StringUtil.isNotNullOrEmptyString(location)) {
            tvLoc.setText(location);
        }

        if (StringUtil.isNotNullOrEmptyString(userBio)) {
            String description = userBio;
            description = StringUtil.fromHtml(description).toString();
            if (description.length() > BIO_MAX_LIMIT) {
                description = description.substring(0, BIO_MAX_LIMIT);
            }
            userDescription.setText(description);
            userDescription.setTag(null);
            ExpandableTextView expandableTextView = ExpandableTextView.getInstance();
            expandableTextView.makeTextViewResizable(userDescription, 1, getString(R.string.ID_VIEW_MORE_MENTOR), true, this, viewMoreText, viewLessText);

            mUserSolarObject.setDescription(description);
        }

        if (progressPercentage != -1) {
            mUserSolarObject.setProfileCompletionWeight(progressPercentage);
            dashProgressBar.setProgress(progressPercentage, false);

            mUserSolarObject.setFilledProfileFields(filledFields);
            mUserSolarObject.setUnfilledProfileFields(unfilledFields);
            setProfileStrength();
        }

        if (imageUrl != null) {
            refreshImageView(imageUrl);
        }
    }

    private void imageCropping(Intent intent) {
        try {
            if (localImageSaveForChallenge.exists()) {
                Bitmap photo = CompressImageUtil.decodeFile(localImageSaveForChallenge);
                mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
            } else {
                Toast.makeText(this, R.string.error_while_save, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            e.printStackTrace();
        }
    }
    //endregion private methods

    //region public methods
    public String getUserNameTitle() {
        return userNameTitle;
    }

    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        selectImageFrmGallery();
    }

    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    public void setProfileDetails(UserSolrObj userSolrObj) {
        rlMentorFullViewHeader.setVisibility(View.VISIBLE);
        mFeedDetail = userSolrObj;

        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            if (mUserPreference.get().getUserSummary().getUserBO().getParticipantId() == mUserSolarObject.getEntityOrParticipantId()) {
                isOwnProfile = true;
                tvMentorDashBoardFollow.setText(getString(R.string.ID_EDIT_PROFILE));
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.selecter_invite_friend);

                progressbarContainer.setVisibility(View.VISIBLE);
                setProfileStrength();
                profileLevel.setVisibility(View.VISIBLE);
                newFeature.setVisibility(View.VISIBLE);

                dashProgressBar.setListener(this);
                if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                    dashProgressBar.setTotalDash(mConfiguration.get().configData.maxDash);
                } else {
                    dashProgressBar.setTotalDash(new ConfigData().maxDash);
                }

                dashProgressBar.setProgress(userSolrObj.getProfileCompletionWeight(), false);
                //hide menu dots
                profileToolbarMenu.setVisibility(View.GONE);

                //Hide the offer icon
                if (!CommonUtil.getPrefValue(AppConstants.PROFILE_OFFER_PREF)) {
                    newFeature.setVisibility(View.VISIBLE);
                } else {
                    newFeature.setVisibility(View.GONE);
                }
            } else {
                newFeature.setVisibility(View.GONE);
                progressbarContainer.setVisibility(View.GONE);
                profileLevel.setVisibility(View.GONE);
                profileToolbarMenu.setVisibility(View.VISIBLE);

                updateFollowedButton();
            }
        } else {
            updateFollowedButton();
        }

        if ((mUserSolarObject.getUserSubType() != null && mUserSolarObject.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor()) {
            isChampion = true;
        }

        if (isChampion) {
            verifiedIcon.setVisibility(View.VISIBLE);
        } else {
            verifiedIcon.setVisibility(View.INVISIBLE);
        }

        if (isOwnProfile) {
            verifiedIcon.setVisibility(View.GONE);
            editIcon.setVisibility(View.VISIBLE);
        } else {
            editIcon.setVisibility(View.GONE);
        }

        invalidateProfileButton();

        updateProfileInfo();

        setupSheBadge(userSolrObj); //setup She badge
        setupProfileBadges(userSolrObj); //horizontal badge list on profile

        setPagerAndLayouts();

        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_PUBLIC_PROFILE));
    }

    public void onProfileMenuClick(final UserSolrObj userPostObj, final TextView tvFeedCommunityPostUserCommentPostMenu) {
        PopupMenu popup = new PopupMenu(this, tvFeedCommunityPostUserCommentPostMenu);

        //admin and community moderator have feature to share profile and deactivate user
        if (mLoggedInUserId != userPostObj.getIdOfEntityOrParticipant() && (mLoggedInUserIdTypeId == ADMIN_TYPE_ID || mLoggedInUserIdTypeId == COMMUNITY_MODERATOR_TYPE_ID)) {
            popup.getMenu().add(0, R.id.share, 1, menuIconWithText(getResources().getDrawable(R.drawable.vector_share_black), getResources().getString(R.string.SHARE_PROFILE)));
            popup.getMenu().add(0, R.id.deactivate_user, 2, menuIconWithText(getResources().getDrawable(R.drawable.vector_deactivate_user), getResources().getString(R.string.deactivate_user)));
        } else if (mLoggedInUserId != userPostObj.getIdOfEntityOrParticipant()) {
            popup.getMenu().add(0, R.id.report_spam, 1, menuIconWithText(getResources().getDrawable(R.drawable.vector_report_spam), getResources().getString(R.string.REPORT_SPAM)));
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deactivate_user:
                        deactivateUserDialog(userPostObj);
                        return true;
                    case R.id.share:
                        shareProfile();
                        return true;
                    case R.id.report_spam:
                        reportSpamDialog(userPostObj);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    public void addAnalyticsEvents(Event event) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isChampion)
                        .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    public void onBackClick() {
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
            } else {
                if (isUserDeactivated) {
                    isUserDeactivated = false;
                    setResult(AppConstants.RESULT_CODE_FOR_DEACTIVATION);
                    finish();
                } else if (followedUserSolrObj != null) {
                    onFollowedActivityResultOfParentRefresh(followedUserSolrObj);
                } else if (!isProfileClicked) {
                    onActivityResultOfParentRefresh();
                }
            }
        }
        super.onBackPressed();
    }

    public void championDetailActivity(Long userId, boolean isMentor) {
        mUserSolarObject = new UserSolrObj();
        mUserSolarObject.setIdOfEntityOrParticipant(userId);
        mUserSolarObject.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        ProfileActivity.navigateTo(this, mUserSolarObject, userId, isMentor, AppConstants.PROFILE_CHAMPION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    public void setProfileNameData(String imageUrl) {
        /*if (null != imageUrl) {
            userImage.setCircularImage(true);
            userImage.bindImage(imageUrl);
        }*/
        File localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        setLocalImageSaveForChallenge(localImageSaveForChallenge);
    }

    public void setUserNameTitle(String userNameTitle) {
        this.userNameTitle = userNameTitle;
    }

    public void setLocalImageSaveForChallenge(File localImageSaveForChallenge) {
        this.localImageSaveForChallenge = localImageSaveForChallenge;
    }

    public void refreshImageView(String imageUrl) {
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            mProfileIcon.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(imageUrl, profileSize, profileSize);
            mProfileIcon.bindImage(authorThumborUrl);
            mUserSolarObject.setImageUrl(imageUrl);
        }

        invalidateProfileButton();
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, long userId, boolean isMentor, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        intent.putExtra(AppConstants.CHAMPION_ID, userId);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, CommunityFeedSolrObj dataItem, long mChampionId, boolean isMentor, int position, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        Bundle bundle = new Bundle();
        dataItem.setIdOfEntityOrParticipant(mChampionId);
        dataItem.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        dataItem.setItemPosition(position);
        Parcelable parcelable = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, null);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (notificationId != -1) {
            intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        }
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode, boolean isWriteAStory) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(BaseActivity.STORIES_TAB, isWriteAStory);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (notificationId != -1) {
            intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        }
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, ProfileStrengthDialog.ProfileLevelType profileLevelType, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (profileLevelType != null) {
            intent.putExtra(ProfileStrengthDialog.PROFILE_LEVEL, profileLevelType);
        }
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    //endregion public methods
}