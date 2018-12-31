package appliedlife.pvtltd.SHEROES.views.fragments;

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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.parceler.Parcels;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
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
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
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
import appliedlife.pvtltd.SHEROES.views.activities.BadgeClosetActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateStoryActivity;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.FollowingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashProgressBar;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ExpandableTextView;
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

import static android.app.Activity.RESULT_OK;
import static appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity.STORIES_TAB;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;
import static appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity.BIO_MAX_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog.ALL_STAR_END_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog.BEGINNER_START_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog.INTERMEDIATE_END_LIMIT;

public class ProfileFragment  extends BaseFragment implements BaseHolderInterface, HomeView, IProfileView,
        ViewPager.OnPageChangeListener, ProgressbarView, ExpandableTextCallback, IFollowCallback {

    private View view;
    //region constants
    private final String TAG = LogUtils.makeLogTag(ProfileActivity.class);
    private static final String SCREEN_LABEL = "Profile Screen";
    private static final String PROFILE_POST_SCREEN_LABEL = "Profile Post Screen";
    private static final String PROFILE_STORY_SCREEN_LABEL = "Profile Stories Screen";
    private static final String BADGE_COUNTER_FONT_FAMILY = "sans-serif-medium";
    private static final float ORIGINAL_SIZE = 1.0f;
    private static final float EXPANDED_SIZE = 1.02f;
    private static final int ANIMATION_REPEAT_DURATION = 700;
    private static final int ANIMATION_MAX_DURATION = 5000;
    private static final int ADMIN_TYPE_ID = 2;
    private static final int COMMUNITY_MODERATOR_TYPE_ID = 13;
    private static final int MAX_BADGE_COUNT = 4;
    private static final int BASIC_COMPLETE_PROFILE_PERCENT = 85;
    //endregion constants

    //region injected variable
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    ProfilePresenterImpl mProfilePresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<AppConfiguration> mConfiguration;
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    FeedUtils mFeedUtils;
    @Inject
    ErrorUtil mErrorUtil;
    //endregion injected variable

    //region bind variables
    @Bind(R.id.root_layout)
    CoordinatorLayout mRootLayout;
    @Bind(R.id.iv_profile_full_view_icon)
    CircleImageView mProfileIcon;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.view_pager_mentor)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_mentor)
    Toolbar mToolbar;
    @Bind(R.id.loader_gif)
    CardView mLoaderGif;
    @Bind(R.id.collapsing_toolbar_mentor)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tab_mentor)
    TabLayout mTabLayout;
    @Bind(R.id.tv_profile_name)
    TextView mUserName;
    @Bind(R.id.iv_public_profile_image)
    ImageView mPublicProfileImage;
    @Bind(R.id.tv_profession)
    TextView mTvProfession;
    @Bind(R.id.profession_container)
    RelativeLayout mSkillContainer;
    @Bind(R.id.li_loc_profession)
    LinearLayout mLocationViewContainer;
    @Bind(R.id.tv_loc)
    TextView mTvLocation;
    @Bind(R.id.description_container)
    RelativeLayout mDescriptionContainer;
    @Bind(R.id.tv_description)
    TextView mUserDescription;
    @Bind(R.id.ll_profile)
    LinearLayout mLinearProfile;
    @Bind(R.id.li_post)
    LinearLayout mLinearPost;
    @Bind(R.id.tv_post_count)
    TextView mUserTotalPostCount;
    @Bind(R.id.tv_post)
    TextView mTvPost;
    @Bind(R.id.li_follower)
    LinearLayout mFollower;
    @Bind(R.id.li_following)
    LinearLayout mFollowing;
    @Bind(R.id.tv_following_title)
    TextView mFollowingTitle;
    @Bind(R.id.tv_following_count)
    TextView mFollowingCount;
    @Bind(R.id.tv_follower_count)
    TextView mFollowerCount;
    @Bind(R.id.tv_follower)
    TextView mUserFollower;
    @Bind(R.id.tv_mentor_toolbar_name)
    TextView mToolbarName;
    @Bind(R.id.tv_profile_menu)
    TextView mToolbarMenu;
    @Bind(R.id.tv_dashboard_follow)
    TextView mDashBoardFollow;
    @Bind(R.id.edit_icon)
    ImageView mEditIcon;
    @Bind(R.id.user_badge)
    ImageView mUserBadgeIcon;
    @Bind(R.id.badges_container)
    LinearLayout mBadgeContainer;
    @Bind(R.id.share_profile)
    TextView mShareProfile;
    @Bind(R.id.iv_champion_verified)
    ImageView verifiedIcon;
    @Bind(R.id.profile_level)
    TextView mProfileLevel;
    @Bind(R.id.progress_bar_holder)
    ViewGroup mProgressbarContainer;
    @Bind(R.id.profile_level_container)
    LinearLayout mProfileStrengthContainer;
    @Bind(R.id.new_feature)
    TextView mNewFeature;
    @Bind(R.id.beginner)
    ImageView mBeginnerTick;
    @Bind(R.id.intermediate)
    ImageView mIntermediateTick;
    @Bind(R.id.all_star)
    ImageView mAllStarTick;
    @Bind(R.id.fab_post)
    FloatingActionButton mCreatePost;
    @Bind(R.id.cl_story_footer)
    CardView mStoryFooter;
    @BindDimen(R.dimen.profile_badge_icon)
    int mBadgeIconSize;
    @BindDimen(R.dimen.profile_badge_margin_right)
    int mBadgeIconMargin;
    @BindDimen(R.dimen.profile_badge_counter_text)
    int mBadgeCounterSize;
    @BindDimen(R.dimen.dp_size_90)
    int mProfileSize;
    @Bind(R.id.rl_champion_full_view_header)
    RelativeLayout mFullViewHeader;
    @Bind(R.id.view_profile)
    View mToolTipProfile;
    @Bind(R.id.view_tool_follow)
    View mToolTipFollow;
    @Bind(R.id.dashed_progressbar)
    DashProgressBar mDashProgressBar;
    @Bind(R.id.tv_drawer_navigation)ImageView backNavigationImg;
    //endregion bind variables

    //region member variable
    private String userNameTitle, viewLessText, viewMoreText;
    private boolean isUserDeactivated, isProfileClicked, isOwnProfile, isChampion, isWriteAStory;
    private long mChampionId, mLoggedInUserIdTypeId, mLoggedInUserId = -1;
    private int mFromNotification, itemPosition;
    //endregion member variable

    //region view variable
    private FeedDetail mFeedDetail;
    private ViewPagerAdapter mViewPagerAdapter;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;
    private ProfileStrengthDialog mProfileStrengthDialog = null;
    private UnFollowDialogFragment mUnFollowDialogFragment;
    private DeactivateProfileDialogFragment mDeactivateProfileDialogFragment;
    private ReportUserProfileDialogFragment mReportUserProfileDialogFragment;
    private UserSolrObj mUserSolarObject;
    private File mLocalImageSaveForChallenge;
    private PopupWindow mPopupWindowFollowTooTip;
    private UserSolrObj mFollowedUserSolrObj;
    private ProfileStrengthDialog.ProfileStrengthType mProfileStrengthType;
    //endregion view variable
    //region activity lifecycle methods

    public static ProfileFragment createInstance(UserSolrObj userSolrObj, FeedDetail feedDetail, ProfileStrengthDialog.ProfileStrengthType profileStrengthType, long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode, boolean isWriteAStory) {
        HomeFragment.PREVIOUS_SCREEN = SCREEN_LABEL;

        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();/*
        if(isWriteAStory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }*/
        if (notificationId != -1) {
            bundle.putInt(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        }
        if (!CommonUtil.isEmpty(properties)) {
            try {
                mapToBundle(properties, bundle);
            } catch (Exception e){
            }
        }
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, Parcels.wrap(userSolrObj));
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, Parcels.wrap(feedDetail));
        bundle.putSerializable(ProfileStrengthDialog.PROFILE_LEVEL, profileStrengthType);
        bundle.putBoolean(BaseActivity.STORIES_TAB, isWriteAStory);
        bundle.putLong(AppConstants.CHAMPION_ID, mChampionId);
        bundle.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        bundle.putBoolean(AppConstants.IS_CHAMPION_ID, isMentor);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    public static Bundle mapToBundle(Map<String, Object> data, Bundle bundle) throws Exception {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (entry.getValue() instanceof String) {
                    bundle.putString(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Double) {
                    bundle.putDouble(entry.getKey(), ((Double) entry.getValue()));
                } else if (entry.getValue() instanceof Integer) {
                    bundle.putInt(entry.getKey(), (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Float) {
                    bundle.putFloat(entry.getKey(), ((Float) entry.getValue()));
                }
        }
        return bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        mProfilePresenter.attachView(this);
        viewMoreText = getString(R.string.ID_VIEW_MORE_MENTOR);
        viewLessText = getString(R.string.ID_LESS);
      //  setupToolbarItemsColor();
        /*invalidateOptionsMenu();*/

        if(getActivity() instanceof ProfileActivity){
            backNavigationImg.setVisibility(View.VISIBLE);

            backNavigationImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ProfileActivity) getActivity()).onBackPressed();
                }
            });
        } else {
            backNavigationImg.setVisibility(View.GONE);
        }



        mLoaderGif.setVisibility(View.VISIBLE);
        mLoaderGif.bringToFront();
        mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
        mUserSolarObject = Parcels.unwrap(getArguments().getParcelable(AppConstants.GROWTH_PUBLIC_PROFILE));
        mFeedDetail = Parcels.unwrap(getArguments().getParcelable(AppConstants.MENTOR_DETAIL));
        mFromNotification = getArguments().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
        mChampionId = getArguments().getLong(AppConstants.CHAMPION_ID);
        isChampion = getArguments().getBoolean(AppConstants.IS_CHAMPION_ID);
        isWriteAStory = getArguments().getBoolean(STORIES_TAB);
        mProfileStrengthType = (ProfileStrengthDialog.ProfileStrengthType) getArguments().getSerializable(ProfileStrengthDialog.PROFILE_LEVEL);
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

        mFeedUtils.setConfigurableShareOption(isWhatsAppShare());

        return view;
    }

    @Override
    public void onStop() {

        if (mProfileStrengthDialog != null && mProfileStrengthDialog.isVisible()) {
            mProfileStrengthDialog.dismiss();
        }

        if (mPopupWindowFollowTooTip != null && mPopupWindowFollowTooTip.isShowing()) {
            mPopupWindowFollowTooTip.dismiss();
        }

        if (mUnFollowDialogFragment != null && mUnFollowDialogFragment.isVisible()) {
            mUnFollowDialogFragment.dismiss();
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
        mProfilePresenter.detachView();
        mHomePresenter.detachView();
        super.onDestroy();
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
            String title = (String) mViewPagerAdapter.getPageTitle(position);
            if (StringUtil.isNotNullOrEmptyString(title) && title.equalsIgnoreCase(getString(R.string.ID_MENTOR_POST))) {
                if (isOwnProfile) {
                    mCreatePost.setVisibility(View.VISIBLE);
                } else {
                    mCreatePost.setVisibility(View.GONE);
                }
                mStoryFooter.setVisibility(View.GONE);
            } else {
                if (isOwnProfile) {
                    mStoryFooter.setVisibility(View.VISIBLE);
                } else {
                    mStoryFooter.setVisibility(View.GONE);
                }
                mCreatePost.setVisibility(View.GONE);
            }
        } else {
            mStoryFooter.setVisibility(View.GONE);
            mCreatePost.setVisibility(View.GONE);
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        if (getActivity() == null) return;
        mErrorUtil.onShowErrorDialog(getActivity(), s, feedParticipationEnum);
        mLoaderGif.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyScreen(String s) {

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
            mLoaderGif.setVisibility(View.GONE);

            if (mProfileStrengthType != null)
                profileStrengthDialog(mProfileStrengthType);
        }
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse spamResponse) {
        if (spamResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            if (getActivity() != null && getActivity().isFinishing()) return;

            if (spamResponse.isSpammed()) {
                CommonUtil.createDialog(getActivity(), getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_marked_dialog_message, spamResponse.getModelType().toLowerCase()));
            } else if (!spamResponse.isSpamAlreadyReported()) {
                CommonUtil.createDialog(getActivity(), getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.spam_confirmation_dialog_message));
            } else {
                CommonUtil.createDialog(getActivity(), getResources().getString(R.string.reported_spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_confirmation_dialog_message, spamResponse.getModelType().toLowerCase()));
            }
        }
    }

    @Override
    public void onUserDeactivation(BaseResponse baseResponse, boolean isUserDeactivated) {
        this.isUserDeactivated = isUserDeactivated;
        if (isUserDeactivated) {
            onBackClick();
        }
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
            if (getActivity() != null) return;
            ProfileActivity.navigateTo(getActivity(), feedDetail.getAuthorParticipantId(), isChampion,
                    PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL, false);
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
                            mDashProgressBar.setProgress(boardingDataResponse.getUserSolrObj().getProfileCompletionWeight(), false);
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
        mBeginnerTick.setLayoutParams(buttonLayoutParams);
        RelativeLayout.LayoutParams intermediateLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        intermediateLayoutParams.setMargins((int) (dashWidth * intermediateTickIndex), 0, 0, 0);
        mIntermediateTick.setLayoutParams(intermediateLayoutParams);

        animationOnProgressBar();
    }

    @Override
    public void onTextResize(boolean isCollapsed) {
        if (!isCollapsed) {
            mProgressbarContainer.setVisibility(View.VISIBLE);
            mProfileStrengthContainer.setVisibility(View.VISIBLE);
        } else {
            mProgressbarContainer.setVisibility(View.GONE);
            mProfileStrengthContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProfileFollowed(UserSolrObj userSolrObj) {
        mFollowedUserSolrObj = userSolrObj;
        mFollowerCount.setText(String.valueOf(changeNumberToNumericSuffix(userSolrObj.getFollowerCount())));
        updateFollowedButton();
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, mViewPager.getCurrentItem());
        String title = (String) mViewPagerAdapter.getPageTitle(mViewPager.getCurrentItem());
        if (fragment instanceof FeedFragment && StringUtil.isNotNullOrEmptyString(title) && title.equalsIgnoreCase(getString(R.string.ID_MENTOR_POST))) { //refresh if current tab is post to change follow button visibility
            ((FeedFragment) fragment).refreshList();
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
    }

    public Map<String, Object> getExtraPropertiesToTrack() {
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            if (resultCode == REQUEST_CODE_FOR_EDIT_PROFILE) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    updateProfileDetails(bundle.getString(EditUserProfileActivity.USER_NAME), bundle.getString(EditUserProfileActivity.LOCATION), bundle.getString(EditUserProfileActivity.USER_DESCRIPTION), bundle.getString(EditUserProfileActivity.IMAGE_URL),
                            bundle.getString(EditUserProfileActivity.FILLED_FIELDS), bundle.getString(EditUserProfileActivity.UNFILLED_FIELDS), bundle.getFloat(EditUserProfileActivity.PROFILE_COMPLETION_WEIGHT));
                }
            } else {
                switch (requestCode) {
                    case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    case AppConstants.REQUEST_CODE_FOR_GALLERY:
                        mImageCaptureUri = intent.getData();
                        if (resultCode == RESULT_OK) {
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
                                if (result != null && result.getUri() != null && result.getUri().getPath() != null) {
                                    File file = new File(result.getUri().getPath());
                                    Bitmap photo = CompressImageUtil.decodeFile(file);
                                    mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getUserSummaryDetails(mAppUtils.getUserProfileRequestBuilder(AppConstants.PROFILE_PIC_SUB_TYPE, AppConstants.PROFILE_PIC_TYPE, mEncodeImageUrl));
                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                            Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
                }
            }
        }
    }

    public void getUserSummaryDetails(UserSummaryRequest userSummaryRequest) {
        mHomePresenter.getUserSummaryDetails(userSummaryRequest);
    }
    //endregion override methods

    //region onclick methods
    @OnClick({R.id.beginner})
    protected void openBeginnerDialog() {
        profileStrengthDialog(ProfileStrengthDialog.ProfileStrengthType.BEGINNER);
    }

    @OnClick(R.id.intermediate)
    protected void openIntermediateProgressDialog() {
        profileStrengthDialog(ProfileStrengthDialog.ProfileStrengthType.INTERMEDIATE);
    }

    @OnClick(R.id.all_star)
    protected void openAllStarProgressDialog() {
        profileStrengthDialog(ProfileStrengthDialog.ProfileStrengthType.ALLSTAR);
    }

    @OnClick(R.id.new_feature)
    protected void openUserProfileLevelDialog() {
        if (mUserSolarObject != null) {
            CommonUtil.setPrefValue(AppConstants.PROFILE_OFFER_PREF);
            ProfileStrengthDialog.ProfileStrengthType profileStrengthType = userLevel(mUserSolarObject);
            profileStrengthDialog(profileStrengthType);
            mNewFeature.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_profile_menu)
    public void onMenuClick() {
        onProfileMenuClick(mUserSolarObject, mToolbarMenu);
    }

    @OnClick(R.id.fab_post)
    public void createNewPost() {
        CommunityPost communityPost = new CommunityPost();
        communityPost.createPostRequestFrom = AppConstants.CREATE_POST;
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(getActivity(), communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
    }

    @OnClick(R.id.cl_story_footer)
    public void writeAStory() {
        CreateStoryActivity.navigateTo(getActivity(), 1, getScreenName(), null);
    }

    @OnClick(R.id.li_follower)
    public void followerClick() {
        if (StringUtil.isNotNullOrEmptyString(mFollowerCount.getText().toString()) && !mFollowerCount.getText().toString().equalsIgnoreCase("0")) {
            FollowingActivity.navigateTo(getActivity(), mChampionId, isOwnProfile, getScreenName(), FollowingEnum.FOLLOWERS, null);
        }
    }

    @OnClick(R.id.li_following)
    public void followingClick() {
        if (StringUtil.isNotNullOrEmptyString(mFollowingCount.getText().toString()) && !mFollowingCount.getText().toString().equalsIgnoreCase("0")) {
            FollowingActivity.navigateTo(getActivity(), mChampionId, isOwnProfile, getScreenName(), FollowingEnum.FOLLOWING, null);
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

    @OnClick({R.id.tv_profile_name, R.id.tv_loc})
    public void navigateToProfileEditing() {
        if (isOwnProfile && null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            EditUserProfileActivity.navigateTo(getActivity(), SCREEN_LABEL, mUserSolarObject.getImageUrl(), null, 1);
        }
    }

    @OnClick(R.id.iv_profile_full_view_icon)
    public void onImageEditClicked() {
        if (isOwnProfile) {
            addAnalyticsEvents(Event.PROFILE_PIC_EDIT_CLICKED);
            if (getActivity() != null && getActivity() instanceof HomeActivity)
                CameraBottomSheetFragment.showDialog((HomeActivity) getActivity(), SCREEN_LABEL);
            else if (getActivity() != null && getActivity() instanceof ProfileActivity)
                CameraBottomSheetFragment.showDialog((ProfileActivity) getActivity(), SCREEN_LABEL);
        }
    }

    @OnClick(R.id.tv_dashboard_follow)
    public void mentorFollowClick() {
        if (isOwnProfile) {
            navigateToProfileEditing();
        } else {
            PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
            publicProfileListRequest.setIdOfEntityParticipant(mUserSolarObject.getIdOfEntityOrParticipant());

            if (mUserSolarObject.isSolrIgnoreIsUserFollowed() || mUserSolarObject.isSolrIgnoreIsMentorFollowed()) {
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
            String shareText;
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
        /*setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }*/
    }

    private void updateProfileInfo() {
        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getCityName())) { //Location
            mTvLocation.setText(mUserSolarObject.getCityName());
            mTvLocation.setVisibility(View.VISIBLE);
        } else {
            if (isOwnProfile) {
                mTvLocation.setVisibility(View.VISIBLE);
                mTvLocation.setText(R.string.add_location);
            } else {
                mTvLocation.setVisibility(View.GONE);
                if (!isChampion) {
                    mLocationViewContainer.setVisibility(View.GONE);
                } else {
                    mLocationViewContainer.setVisibility(View.VISIBLE);
                }
            }
        }
        if (StringUtil.isNotEmptyCollection(mUserSolarObject.getCanHelpIns())) { //skills or Expert At
            mTvProfession.setText(mUserSolarObject.getCanHelpIns().get(0));
            mSkillContainer.setVisibility(View.VISIBLE);
        } else {
            if (isOwnProfile && isChampion) {
                mTvProfession.setText(R.string.add_skills);
                mSkillContainer.setVisibility(View.VISIBLE);
            } else {
                mSkillContainer.setVisibility(View.GONE);
            }
        }
        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getDescription())) { //Bio or Description
            String description = mUserSolarObject.getDescription();
            description = StringUtil.fromHtml(description).toString();
            if (description.length() > BIO_MAX_LIMIT) {
                description = description.substring(0, BIO_MAX_LIMIT);
            }
            if (isOwnProfile) {
                mUserDescription.setText(description);
                ExpandableTextView expandableTextView = ExpandableTextView.getInstance();
                expandableTextView.makeTextViewResizable(mUserDescription, 1, getString(R.string.ID_VIEW_MORE_MENTOR), true, this, viewMoreText, viewLessText);
            } else {
                mUserDescription.setText(description);
            }
        } else {
            if (isOwnProfile) {
                mUserDescription.setText(R.string.add_desc);
            } else {
                mDescriptionContainer.setVisibility(View.GONE);
            }
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getImageUrl())) { //profile pic
            if (getActivity() != null && !getActivity().isFinishing()) {
                String profilePic = CommonUtil.getThumborUri(mUserSolarObject.getImageUrl(), mProfileSize, mProfileSize);
                mProfileIcon.setCircularImage(true);
                mProfileIcon.bindImage(profilePic);
            }
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getNameOrTitle())) { //Name
            String userNameTitle = CommonUtil.camelCaseString(mUserSolarObject.getNameOrTitle());
            mUserName.setText(userNameTitle);
            mToolbarName.setText(userNameTitle);
            setUserNameTitle(userNameTitle);
        }
        setPostCount(mUserSolarObject.getPostCount());
        setFollowerCount(mUserSolarObject.getFollowerCount());
        setFollowingCount(mUserSolarObject.getFollowingCount());
    }

    private void toolTipForFollowUser() {
        if (getActivity() == null) return;
        try {
            mToolTipFollow.setVisibility(View.INVISIBLE);
            final View popupFollowToolTip = LayoutInflater.from(getActivity()).inflate(R.layout.tooltip_arrow_up_side, null);
            mPopupWindowFollowTooTip = new PopupWindow(popupFollowToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindowFollowTooTip.setOutsideTouchable(true);
            mPopupWindowFollowTooTip.showAsDropDown(mToolTipFollow, -50, 0);
            final ImageView ivArrow = popupFollowToolTip.findViewById(R.id.iv_arrow);
            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(20, getActivity()), 0);
            imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            ivArrow.setLayoutParams(imageParams);
            final LinearLayout llToolTipBg = popupFollowToolTip.findViewById(R.id.ll_tool_tip_bg);
            RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(300, getActivity()), LinearLayout.LayoutParams.WRAP_CONTENT);
            llParams.setMargins(CommonUtil.convertDpToPixel(20, getActivity()), 0, 0, 0);
            llParams.addRule(RelativeLayout.BELOW, R.id.iv_arrow);
            llToolTipBg.setLayoutParams(llParams);
            final TextView tvGotIt = popupFollowToolTip.findViewById(R.id.got_it);
            final TextView tvTitle = popupFollowToolTip.findViewById(R.id.title);
            tvTitle.setText(getString(R.string.tool_tip_follower));
            tvGotIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindowFollowTooTip.dismiss();
                    mToolTipFollow.setVisibility(View.GONE);
                }
            });
        } catch (WindowManager.BadTokenException e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    private void setProfileStrength() {
        if (mUserSolarObject.getProfileCompletionWeight() > BEGINNER_START_LIMIT && mUserSolarObject.getProfileCompletionWeight() < INTERMEDIATE_END_LIMIT) {
            mProfileLevel.setText(R.string.progress_status_beginner);
            if (mUserSolarObject.getProfileCompletionWeight() >= ProfileStrengthDialog.BEGINNER_END_LIMIT) {
                mBeginnerTick.setImageResource(R.drawable.vector_level_complete);
            } else {
                mBeginnerTick.setImageResource(R.drawable.vector_level_incomplete);
            }
            mIntermediateTick.setImageResource(R.drawable.vector_level_incomplete);
            mAllStarTick.setImageResource(R.drawable.vector_all_level_incomplete);
        } else if (mUserSolarObject.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolarObject.getUnfilledProfileFields())) {
            mProfileLevel.setText(R.string.progress_status_all_star);
            if (mUserSolarObject.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolarObject.getUnfilledProfileFields())) {
                mAllStarTick.setImageResource(R.drawable.vector_all_level_complete);
            } else {
                mAllStarTick.setImageResource(R.drawable.vector_all_level_incomplete);
            }
            mBeginnerTick.setImageResource(R.drawable.vector_level_complete);
            mIntermediateTick.setImageResource(R.drawable.vector_level_complete);
        } else {
            mProfileLevel.setText(R.string.progress_level_status_intermediate);
            if (mUserSolarObject.getProfileCompletionWeight() >= INTERMEDIATE_END_LIMIT) {
                mIntermediateTick.setImageResource(R.drawable.vector_level_complete);
            } else {
                mIntermediateTick.setImageResource(R.drawable.vector_level_incomplete);
            }
            mAllStarTick.setImageResource(R.drawable.vector_all_level_incomplete);
            mBeginnerTick.setImageResource(R.drawable.vector_level_complete);
        }
    }

    public void updateFollowOnAuthorFollowed(boolean isFollowed) {
        if(mUserSolarObject!=null) {
            mUserSolarObject.setSolrIgnoreIsMentorFollowed(isFollowed);
            mUserSolarObject.setSolrIgnoreIsUserFollowed(isFollowed);
            updateFollowedButton();
        }
    }

    private void updateFollowedButton() {
        if (mUserSolarObject.isSolrIgnoreIsUserFollowed() || mUserSolarObject.isSolrIgnoreIsMentorFollowed()) {
            mDashBoardFollow.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            mDashBoardFollow.setText(this.getString(R.string.following_user));
            mDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            mDashBoardFollow.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_icon_text));
            mDashBoardFollow.setText(this.getString(R.string.follow_user));
            mDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }

        if (CommonUtil.ensureFirstTime(AppConstants.FOLLOWER_SHARE_PREF)) {
            toolTipForFollowUser();
        }
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
//        supportPostponeEnterTransition();
//        setSupportActionBar(mToolbar);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        if (!isChampion) {
            mViewPagerAdapter.addFragment(ProfileDetailsFragment.createInstance(mChampionId, mUserSolarObject.getNameOrTitle()), getString(R.string.ID_PROFILE));
        }
        FeedFragment profilePostScreen = new FeedFragment();
        Bundle bundlePostScreen = new Bundle();
        String postScreenName = getString(R.string.ID_MENTOR_POST);
        if (mLoggedInUserId != mUserSolarObject.getIdOfEntityOrParticipant()) {
            bundlePostScreen.putString(AppConstants.END_POINT_URL, AppConstants.OTHER_USER_POST_STREAM + mUserSolarObject.getIdOfEntityOrParticipant());
        } else {
            bundlePostScreen.putString(AppConstants.END_POINT_URL, AppConstants.USER_MY_POST_STREAM);
        }
        bundlePostScreen.putString(AppConstants.SCREEN_NAME, PROFILE_POST_SCREEN_LABEL);
        bundlePostScreen.putBoolean(FeedFragment.IS_HOME_FEED, false);
        bundlePostScreen.putString(FeedFragment.STREAM_NAME, AppConstants.POST_STREAM);
        profilePostScreen.setArguments(bundlePostScreen);
        mViewPagerAdapter.addFragment(profilePostScreen, postScreenName);

        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        String screenName;
        if (mLoggedInUserId != mUserSolarObject.getIdOfEntityOrParticipant()) {
            screenName = getString(R.string.stories);
            bundle.putString(AppConstants.END_POINT_URL, AppConstants.OTHER_USER_STORIES_STREAM + mUserSolarObject.getIdOfEntityOrParticipant());
        } else {
            screenName = getString(R.string.my_stories);
            bundle.putString(AppConstants.END_POINT_URL, AppConstants.USER_MY_STORIES_STREAM);
        }
        bundle.putString(AppConstants.SCREEN_NAME, PROFILE_STORY_SCREEN_LABEL);
        bundle.putBoolean(FeedFragment.IS_HOME_FEED, false);
        bundle.putString(FeedFragment.STREAM_NAME, AppConstants.STORY_STREAM);
        feedFragment.setArguments(bundle);
        mViewPagerAdapter.addFragment(feedFragment, screenName);
        mViewPager.setAdapter(mViewPagerAdapter);

        if (isWriteAStory) {
            mStoryFooter.setVisibility(View.VISIBLE);
            mViewPager.setCurrentItem(mViewPagerAdapter.getCount() - 1);
        } else {
            if (isOwnProfile) {
                mCreatePost.setVisibility(View.VISIBLE);
            } else {
                mCreatePost.setVisibility(View.GONE);
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
            getActivity().setResult(AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED, intent);
        }
    }

    private void onActivityResultOfParentRefresh() {
        if (mUserSolarObject != null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            mUserSolarObject.currentItemPosition = itemPosition;
            Parcelable parcelableMentorDetail = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentorDetail);
            Parcelable parcelable = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.FEED_SCREEN, parcelable);
            intent.putExtras(bundle);
            getActivity().setResult(RESULT_OK, intent);
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
        if (getActivity() == null || userSolrObj == null || !StringUtil.isNotEmptyCollection(userSolrObj.getUserBadgesList()))
            return;

        mBadgeContainer.setVisibility(View.VISIBLE);
        int length = userSolrObj.getUserBadgesList().size();
        int counter = 0;
        for (final BadgeDetails badgeDetails : userSolrObj.getUserBadgesList()) {
            final ImageView badge = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mBadgeIconSize, mBadgeIconSize);
            layoutParams.setMargins(0, 0, mBadgeIconMargin, 0);
            badge.setLayoutParams(layoutParams);
            if (StringUtil.isNotNullOrEmptyString(badgeDetails.getImageUrl())) {
                Glide.with(badge.getContext())
                        .load(badgeDetails.getImageUrl())
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mUserBadgeIcon.getContext())))
                        .into(badge);
            }
            if (badgeDetails.isActive()) {
                badge.setBackgroundResource(R.drawable.circular_background_yellow);
            } else {
                badge.setBackgroundResource(R.drawable.circular_background_grey);
            }
            mBadgeContainer.addView(badge);
            badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BadgeDetailsDialogFragment.showDialog(getActivity(), mUserSolarObject, badgeDetails, SCREEN_LABEL, false);
                }
            });
            counter++;
            if (counter == MAX_BADGE_COUNT) break;
        }

        if (length > MAX_BADGE_COUNT) {
            TextView badgeCount = new TextView(getActivity());
            badgeCount.setTypeface(Typeface.create(BADGE_COUNTER_FONT_FAMILY, Typeface.NORMAL));
            badgeCount.setTextSize(mBadgeCounterSize);
            badgeCount.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.badge_counter)));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mBadgeIconSize, mBadgeIconSize);
            badgeCount.setLayoutParams(layoutParams);
            badgeCount.setText(getString(R.string.BadgeCounter, (length - MAX_BADGE_COUNT)));
            badgeCount.setBackground(getResources().getDrawable(R.drawable.circular_background_red));
            badgeCount.setGravity(Gravity.CENTER);
            mBadgeContainer.addView(badgeCount);
            badgeCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BadgeClosetActivity.navigateTo(getActivity(), userSolrObj.getUserBadgesList(), userSolrObj, SCREEN_LABEL);
                }
            });
        }
    }

    private void setupSheBadge(UserSolrObj userSolrObj) {
        if (!TextUtils.isEmpty(userSolrObj.getProfileBadgeUrl())) {
            mUserBadgeIcon.setVisibility(View.VISIBLE);
            verifiedIcon.setVisibility(View.GONE);
            Glide.with(mUserBadgeIcon.getContext())
                    .load(userSolrObj.getProfileBadgeUrl())
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mUserBadgeIcon.getContext())))
                    .into(mUserBadgeIcon);
        } else {
            if (isChampion) {
                verifiedIcon.setVisibility(View.VISIBLE);
            }
            mUserBadgeIcon.setVisibility(View.GONE);
        }
    }

    private void createShareImage(final String branchPostDeepLink) {
        if (mUserSolarObject != null) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_user_share, null, false);

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

            followersCountNo.setText(mFollowerCount.getText());
            postCountNo.setText(mUserTotalPostCount.getText());

            followingContainer.setVisibility(View.VISIBLE);
            followingCountNo.setText(mFollowingCount.getText());

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
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(getActivity())))
                        .into(new BitmapImageViewTarget(imageIcon) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                super.onResourceReady(resource, transition);
                                imageIcon.setImageBitmap(resource);
                                Bitmap bitmap = CommonUtil.getViewBitmap(userDetailContainer);
                                Uri contentUri = CommonUtil.getContentUriFromBitmap(getActivity(), bitmap);
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

    public void croppingIMG() {
        if (getActivity() == null) return;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(mImageCaptureUri, "image/*");
        List list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mLocalImageSaveForChallenge));
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
        if (getActivity() == null) return;
        int mButtonSize = 31;
        if (isOwnProfile) {
            mShareProfile.setVisibility(View.VISIBLE);
            if (mUserSolarObject.getProfileCompletionWeight() >= BASIC_COMPLETE_PROFILE_PERCENT) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtil.convertDpToPixel(mButtonSize, getActivity()));
                params.addRule(RelativeLayout.LEFT_OF, R.id.tv_dashboard_follow);
                mShareProfile.setLayoutParams(params);
                mShareProfile.setPadding(0, 0, 0, 2);
                mShareProfile.setText(getString(R.string.SHARE_PROFILE));
                mShareProfile.setBackgroundResource(R.drawable.selecter_invite_friend);

                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mButtonSize, getActivity()),
                        CommonUtil.convertDpToPixel(mButtonSize, getActivity()));
                params1.setMargins(CommonUtil.convertDpToPixel(8, getActivity()), 0, CommonUtil.convertDpToPixel(13, getActivity()), 0);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mDashBoardFollow.setText("");
                mDashBoardFollow.setBackgroundResource(R.drawable.vector_profile_edit_icon);
                mDashBoardFollow.setLayoutParams(params1);
            } else {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtil.convertDpToPixel(mButtonSize, getActivity()));
                params.addRule(RelativeLayout.LEFT_OF, R.id.share_profile);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                mDashBoardFollow.setLayoutParams(params);
                mDashBoardFollow.setPadding(0, 0, 0, 2);
                mDashBoardFollow.setText(getString(R.string.ID_EDIT_PROFILE));
                mDashBoardFollow.setBackgroundResource(0);
                mDashBoardFollow.setBackgroundResource(R.drawable.selecter_invite_friend);

                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mButtonSize, getActivity()),
                        CommonUtil.convertDpToPixel(mButtonSize, getActivity()));
                params1.setMargins(CommonUtil.convertDpToPixel(8, getActivity()), 0, CommonUtil.convertDpToPixel(13, getActivity()), 0);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mShareProfile.setText("");
                mShareProfile.setBackgroundResource(R.drawable.vector_share_profile);
                mShareProfile.setLayoutParams(params1);
            }
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.convertDpToPixel(mButtonSize, getActivity()));
            params.setMargins(0, 0, 20, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mDashBoardFollow.setLayoutParams(params);
            mShareProfile.setVisibility(View.GONE);
        }
    }

    private void profileStrengthDialog(ProfileStrengthDialog.ProfileStrengthType profileStrengthType) { //Profile strength dilaog
        if (mUserSolarObject == null) return;

        if (mProfileStrengthDialog != null && mProfileStrengthDialog.isVisible()) {
            mProfileStrengthDialog.dismiss();
        }
        mProfileStrengthDialog = new ProfileStrengthDialog();
        mProfileStrengthDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mProfileStrengthDialog.isVisible() && getActivity() != null && isDetached() || !isAdded()) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.USER, parcelable);
            bundle.putSerializable(ProfileStrengthDialog.PROFILE_LEVEL, profileStrengthType);
            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            mProfileStrengthDialog.setArguments(bundle);
            mProfileStrengthDialog.show(getActivity().getFragmentManager(), ProfileStrengthDialog.class.getName());
        }
    }

    private void unFollowDialog(final PublicProfileListRequest unFollowRequest) { //Unfollow confirmation dialog
        if (mUserSolarObject == null) return;

        if (mUnFollowDialogFragment != null && mUnFollowDialogFragment.isVisible()) {
            mUnFollowDialogFragment.dismiss();
        }
        mUnFollowDialogFragment = new UnFollowDialogFragment();
        mUnFollowDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mUnFollowDialogFragment.isVisible() && getActivity() != null || !isAdded()) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.USER, parcelable);

            Parcelable unFollowRequestParcelable = Parcels.wrap(unFollowRequest);
            bundle.putParcelable(AppConstants.UNFOLLOW, unFollowRequestParcelable);

            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, isChampion);
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, isOwnProfile);
            mUnFollowDialogFragment.setArguments(bundle);
            mUnFollowDialogFragment.show(getActivity().getFragmentManager(), UnFollowDialogFragment.class.getName());
        }
    }

    private void deactivateUserDialog(final UserSolrObj userSolrObj) { //Deactivation Profile (Only admin have access of this feature)
        if (mUserSolarObject == null || getActivity().isFinishing()) return;

        if (mDeactivateProfileDialogFragment != null && mDeactivateProfileDialogFragment.isVisible()) {
            mDeactivateProfileDialogFragment.dismiss();
        }
        mDeactivateProfileDialogFragment = new DeactivateProfileDialogFragment();
        mDeactivateProfileDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mDeactivateProfileDialogFragment.isVisible() && getActivity() != null|| !isAdded()) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(userSolrObj);
            bundle.putParcelable(AppConstants.USER, parcelable);

            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, isChampion);
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, isOwnProfile);
            mDeactivateProfileDialogFragment.setArguments(bundle);
            mDeactivateProfileDialogFragment.show(getActivity().getFragmentManager(), DeactivateProfileDialogFragment.class.getName());
        }
    }

    private void reportSpamDialog(final UserSolrObj userSolrObj) { //Report Profile Dialog
        if (mUserSolarObject == null || getActivity().isFinishing()) return;

        if (mReportUserProfileDialogFragment != null && mReportUserProfileDialogFragment.isVisible()) {
            mReportUserProfileDialogFragment.dismiss();
        }
        mReportUserProfileDialogFragment = new ReportUserProfileDialogFragment();
        mReportUserProfileDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mReportUserProfileDialogFragment.isVisible() && getActivity() != null || !isAdded()) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(userSolrObj);
            bundle.putParcelable(AppConstants.USER, parcelable);

            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, isChampion);
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, isOwnProfile);
            bundle.putLong(AppConstants.LOGGED_IN_USER, mLoggedInUserId);
            mReportUserProfileDialogFragment.setArguments(bundle);
            mReportUserProfileDialogFragment.show(getActivity().getFragmentManager(), ReportUserProfileDialogFragment.class.getName());
        }
    }

    private ProfileStrengthDialog.ProfileStrengthType userLevel(UserSolrObj userSolrObj) {
        ProfileStrengthDialog.ProfileStrengthType profileType;

        if (userSolrObj.getProfileCompletionWeight() > BEGINNER_START_LIMIT && userSolrObj.getProfileCompletionWeight() <= INTERMEDIATE_END_LIMIT) {
            profileType = ProfileStrengthDialog.ProfileStrengthType.BEGINNER;
        } else if (userSolrObj.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT) {
            profileType = ProfileStrengthDialog.ProfileStrengthType.ALLSTAR;
        } else {
            profileType = ProfileStrengthDialog.ProfileStrengthType.INTERMEDIATE;
        }
        return profileType;
    }

    private void animationOnProgressBar() {
        if (!CommonUtil.getPrefValue(AppConstants.PROFILE_OFFER_PREF)) {
            final ScaleAnimation scaleAnimation = new ScaleAnimation(ORIGINAL_SIZE, EXPANDED_SIZE, ORIGINAL_SIZE, EXPANDED_SIZE, mBeginnerTick.getWidth() / 2, mBeginnerTick.getHeight() / 2);
            scaleAnimation.setDuration(ANIMATION_REPEAT_DURATION); // scale to 1.04 times as big in 700 milli-seconds
            scaleAnimation.setRepeatCount(Animation.INFINITE);
            scaleAnimation.setRepeatMode(Animation.INFINITE);
            scaleAnimation.setInterpolator(getActivity(), android.R.interpolator.accelerate_decelerate);

            mBeginnerTick.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.circle));
            mIntermediateTick.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.circle));
            mAllStarTick.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.circle));

            mBeginnerTick.startAnimation(scaleAnimation);
            mIntermediateTick.startAnimation(scaleAnimation);
            mAllStarTick.startAnimation(scaleAnimation);

            mBeginnerTick.postDelayed(new Runnable() {
                public void run() {
                    scaleAnimation.cancel();
                    scaleAnimation.setAnimationListener(null);

                    mBeginnerTick.setBackground(null);
                    mBeginnerTick.clearAnimation();

                    mIntermediateTick.setBackground(null);
                    mIntermediateTick.clearAnimation();

                    mAllStarTick.setBackground(null);
                    mAllStarTick.clearAnimation();
                }
            }, ANIMATION_MAX_DURATION);
        }
    }

    private void setFollowingCount(int numFound) { //Following count
        mFollowingTitle.setText(getResources().getString(R.string.following));
        mUserSolarObject.setUserFollowing(numFound);
        mFollowingCount.setText(String.valueOf(numFound));
        mFollowing.setVisibility(View.VISIBLE);
    }

    private void setFollowerCount(int numFound) { //Follower count
        String pluralFollower = getResources().getQuantityString(R.plurals.numberOfFollowers, numFound);
        mUserSolarObject.setFollowerCount(numFound);
        mFollowerCount.setText(String.valueOf(changeNumberToNumericSuffix(numFound)));
        mUserFollower.setText(pluralFollower);
        mFollower.setVisibility(View.VISIBLE);
    }

    private void setPostCount(int postCount) { //Post count
        String pluralAnswer = getResources().getQuantityString(R.plurals.numberOfPosts, postCount);
        mUserSolarObject.setPostCount(postCount);
        mUserTotalPostCount.setText(String.valueOf(changeNumberToNumericSuffix(postCount)));
        mTvPost.setText(pluralAnswer);
        mLinearPost.setVisibility(View.VISIBLE);
    }

    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        //ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    public void updateProfileDetails(String name, String location, String userBio, String imageUrl, String filledFields, String unfilledFields, float progressPercentage) {
        if (StringUtil.isNotNullOrEmptyString(name)) { //Name
            name = CommonUtil.camelCaseString(name);
            mUserName.setText(name);
            mToolbarName.setText(name);
        }

        if (StringUtil.isNotNullOrEmptyString(location)) { //Location
            mTvLocation.setText(location);
        }

        if (StringUtil.isNotNullOrEmptyString(userBio)) { //Description
            String description = userBio;
            description = StringUtil.fromHtml(description).toString();
            if (description.length() > BIO_MAX_LIMIT) {
                description = description.substring(0, BIO_MAX_LIMIT);
            }
            mUserDescription.setText(description);
            mUserDescription.setTag(null);
            ExpandableTextView expandableTextView = ExpandableTextView.getInstance();
            expandableTextView.makeTextViewResizable(mUserDescription, 1, getString(R.string.ID_VIEW_MORE_MENTOR), true, this, viewMoreText, viewLessText);
            mUserSolarObject.setDescription(description);
        }

        if (progressPercentage != -1) { //profile strength
            mUserSolarObject.setProfileCompletionWeight(progressPercentage);
            mDashProgressBar.setProgress(progressPercentage, false);
            mUserSolarObject.setFilledProfileFields(filledFields);
            mUserSolarObject.setUnfilledProfileFields(unfilledFields);
            setProfileStrength();
        }

        if (imageUrl != null) {
            refreshImageView(imageUrl);
        }
    }

    public void imageCropping(Intent intent) {
        try {
            if (mLocalImageSaveForChallenge.exists()) {
                Bitmap photo = CompressImageUtil.decodeFile(mLocalImageSaveForChallenge);
                mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
            } else {
                Toast.makeText(getActivity(), R.string.error_while_save, Toast.LENGTH_SHORT).show();
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
                .start(getActivity());
    }

    public void setProfileDetails(UserSolrObj userSolrObj) {
        mFullViewHeader.setVisibility(View.VISIBLE);
        mFeedDetail = userSolrObj;

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            if (mUserPreference.get().getUserSummary().getUserBO().getParticipantId() == mUserSolarObject.getEntityOrParticipantId()) {
                isOwnProfile = true;
                mDashBoardFollow.setText(getString(R.string.ID_EDIT_PROFILE));
                mDashBoardFollow.setBackgroundResource(R.drawable.selecter_invite_friend);

                mProgressbarContainer.setVisibility(View.VISIBLE);
                setProfileStrength();
                mProfileLevel.setVisibility(View.VISIBLE);
                mNewFeature.setVisibility(View.VISIBLE);
                mDashProgressBar.setListener(this);

                if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                    mDashProgressBar.setTotalDash(mConfiguration.get().configData.maxDash);
                } else {
                    mDashProgressBar.setTotalDash(new ConfigData().maxDash);
                }
                mDashProgressBar.setProgress(userSolrObj.getProfileCompletionWeight(), false);
                mToolbarMenu.setVisibility(View.GONE);

                if (!CommonUtil.getPrefValue(AppConstants.PROFILE_OFFER_PREF)) { //Hide the offer icon
                    mNewFeature.setVisibility(View.VISIBLE);
                } else {
                    mNewFeature.setVisibility(View.GONE);
                }
            } else {
                mNewFeature.setVisibility(View.GONE);
                mProgressbarContainer.setVisibility(View.GONE);
                mProfileLevel.setVisibility(View.GONE);
                mToolbarMenu.setVisibility(View.VISIBLE);
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
            mEditIcon.setVisibility(View.VISIBLE);
        } else {
            mEditIcon.setVisibility(View.GONE);
        }
        invalidateProfileButton();
        updateProfileInfo();
        setupSheBadge(userSolrObj); //setup She badge
        setupProfileBadges(userSolrObj); //horizontal badge list on profile
        setPagerAndLayouts();
    }

    public void onProfileMenuClick(final UserSolrObj userPostObj, final TextView tvFeedCommunityPostUserCommentPostMenu) {
        PopupMenu popup = new PopupMenu(getActivity(), tvFeedCommunityPostUserCommentPostMenu);
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
        Intent upIntent = NavUtils.getParentActivityIntent(getActivity());
        if (NavUtils.shouldUpRecreateTask(getActivity(), upIntent)) {
            TaskStackBuilder.create(getActivity())
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            if (mFromNotification > 0) {
                TaskStackBuilder.create(getActivity())
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            } else {
                if (isUserDeactivated) {
                    isUserDeactivated = false;
                    getActivity().setResult(AppConstants.RESULT_CODE_FOR_DEACTIVATION);
                    getActivity().finish();
                } else if (mFollowedUserSolrObj != null) {
                    onFollowedActivityResultOfParentRefresh(mFollowedUserSolrObj);
                } else if (!isProfileClicked) {
                    onActivityResultOfParentRefresh();
                }
            }
        }
        onBackPressed();
    }

    public void championDetailActivity(Long userId, boolean isMentor) {
        mUserSolarObject = new UserSolrObj();
        mUserSolarObject.setIdOfEntityOrParticipant(userId);
        mUserSolarObject.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        ProfileActivity.navigateTo(getActivity(), mUserSolarObject, userId, isMentor, AppConstants.PROFILE_CHAMPION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    public void setProfileNameData(String imageUrl) {
        File localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        setLocalImageSaveForChallenge(localImageSaveForChallenge);
    }

    public void setUserNameTitle(String userNameTitle) {
        this.userNameTitle = userNameTitle;
    }

    public void setLocalImageSaveForChallenge(File mLocalImageSaveForChallenge) {
        this.mLocalImageSaveForChallenge = mLocalImageSaveForChallenge;
    }

    public void refreshImageView(String imageUrl) {
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            mProfileIcon.setCircularImage(true);
            String profileUrl = CommonUtil.getThumborUri(imageUrl, mProfileSize, mProfileSize);
            mProfileIcon.bindImage(profileUrl);
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

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode, boolean isWriteAStory) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        if(isWriteAStory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        if (notificationId != -1) {
            intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        }
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        intent.putExtra(BaseActivity.STORIES_TAB, isWriteAStory);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    //endregion public methods


    @Override
    public void onPause() {
        super.onPause();
        HomeFragment.PREVIOUS_SCREEN = SCREEN_LABEL;
    }
}
