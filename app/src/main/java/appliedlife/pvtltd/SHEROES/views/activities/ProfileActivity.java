package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import appliedlife.pvtltd.SHEROES.basecomponents.ProgressbarView;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.ExpandableTextCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.SpamContentType;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.DeactivationReason;
import appliedlife.pvtltd.SHEROES.models.DeactivationReasons;
import appliedlife.pvtltd.SHEROES.models.Spam;
import appliedlife.pvtltd.SHEROES.models.SpamReasons;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.DeactivateUserRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.SpamUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashProgressBar;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ExpandableTextView;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MentorQADetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileProgressDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.CommunityEnum.MY_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;
import static appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity.BIO_MAX_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileProgressDialog.ALL_STAR_END_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileProgressDialog.BEGINNER_START_LIMIT;
import static appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileProgressDialog.INTERMEDIATE_END_LIMIT;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */

public class ProfileActivity extends BaseActivity implements HomeView, ProfileView, AppBarLayout.OnOffsetChangedListener, ViewPager.OnPageChangeListener, ProgressbarView, ExpandableTextCallback {

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

    private Long mChampionId;
    private boolean isMentor;
    private boolean isWriteAStory;
    private int mFromNotification;
    private FeedDetail mFeedDetail;
    private int askingQuestionCode;
    boolean isOwnProfile = false;
    ViewPagerAdapter mViewPagerAdapter;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;

    private Dialog dialog = null;
    private ProfileProgressDialog mProfileProgressDialog = null;
    private CommunityEnum communityEnum = MY_COMMUNITY;
    private long mCommunityPostId = 1;
    private long mLoggedInUserId = -1;
    private long mLoggedInUserIdTypeId = -1;
    private FragmentOpen mFragmentOpen;
    private Fragment mFragment;
    private UserSolrObj mUserSolarObject;
    private int itemPosition;
    boolean isFollowEvent;
    private String mSourceName;
    private String userNameTitle;
    private boolean isProfileClicked = false;
    private File localImageSaveForChallenge;
    private boolean isMentorQARefresh = false;
    private PopupWindow popupWindowFollowTooTip;
    private ProfileProgressDialog.ProfileLevelType profileLevelType;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    ProfilePresenterImpl profilePresenter;

    @Inject
    AppUtils mAppUtils;

    @Inject
    Preference<Configuration> mConfiguration;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

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
    public TabLayout mTabLayout;

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

    @Bind(R.id.cl_home_footer_list)
    public CardView clHomeFooterList;

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

    @Bind(R.id.li_answer)
    LinearLayout liAnswer;

    @Bind(R.id.tv_mentor_answer_count)
    TextView tvMentorAnswerCount;

    @Bind(R.id.tv_mentor_answer)
    TextView tvMentorAnswer;

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

    @Bind(R.id.tv_mentor_see_insight)
    TextView tvMentorSeeInsight;

    @Bind(R.id.iv_mentor_verified)
    ImageView verifiedIcon;

    @Bind(R.id.profile_level)
    TextView profileLevel;

    @Bind(R.id.progress_bar_holder)
    ViewGroup progressbarContainer;

    @Bind(R.id.profile_level_container)
    LinearLayout profileStrengthViewContainer;

    @Bind(R.id.tv_mentor_ask_question)
    TextView tvMentorAskQuestion;

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

    @Bind(R.id.view_footer)
    View viewFooter;

    @Bind(R.id.cl_story_footer)
    CardView clStoryFooter;

    @BindDimen(R.dimen.profile_badge_icon)
    int badgeIconSize;

    @BindDimen(R.dimen.profile_badge_margin_right)
    int bageIconMargin;

    @BindDimen(R.dimen.profile_badge_counter_text)
    int badgeCounterTextSize;

    @BindDimen(R.dimen.dp_size_90)
    int profileSize;

    @BindDimen(R.dimen.dp_size_87)
    int profileSizeSmall;

    @Bind(R.id.rl_mentor_full_view_header)
    RelativeLayout rlMentorFullViewHeader;

    @Bind(R.id.view_profile)
    View toolTipProfile;

    @Bind(R.id.view_tool_follow)
    View viewToolTipFollow;

    @Bind(R.id.dashed_progressbar)
    DashProgressBar dashProgressBar;
    private boolean isUserDeactivated;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.mentor_user_dashboard_layout);
        mHomePresenter.attachView(this);
        profilePresenter.attachView(this);
        ButterKnife.bind(this);
        setupToolbarItemsColor();
        invalidateOptionsMenu();
        mAppBarLayout.addOnOffsetChangedListener(this);
        clHomeFooterList.setVisibility(View.GONE);

        loaderGif.setVisibility(View.VISIBLE);
        loaderGif.bringToFront();

        mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mUserSolarObject = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.GROWTH_PUBLIC_PROFILE));
            mFeedDetail = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.MENTOR_DETAIL));
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            askingQuestionCode = getIntent().getExtras().getInt(AppConstants.ASKING_QUESTION);
            mChampionId = getIntent().getExtras().getLong(AppConstants.CHAMPION_ID);
            isMentor = getIntent().getExtras().getBoolean(AppConstants.IS_MENTOR_ID);
            isWriteAStory = getIntent().getExtras().getBoolean(STORIES_TAB);
            mSourceName = getIntent().getExtras().getString(BaseActivity.SOURCE_SCREEN);
            profileLevelType = (ProfileProgressDialog.ProfileLevelType) getIntent().getSerializableExtra("PROFILE_LEVEL");
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
        String feedSubType = isMentor ? AppConstants.CAROUSEL_SUB_TYPE : AppConstants.USER_SUB_TYPE;
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(feedSubType, AppConstants.ONE_CONSTANT, mChampionId));

        setConfigurableShareOption(isWhatsAppShare());
        ((SheroesApplication) getApplication()).trackScreenView(AppConstants.PUBLIC_PROFILE);
    }

    public void onResume() {
        super.onResume();
    }

    private boolean isWhatsAppShare() {
        boolean isWhatsappShare = false;
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareText = "";
            shareText = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareText)) {
                if (shareText.equalsIgnoreCase("true")) {
                    isWhatsappShare = true;
                }
            }
        }
        return isWhatsappShare;
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    public void profileActivitiesRefresh() {
        String pluralAnswer = getResources().getQuantityString(R.plurals.numberOfAnswers, mUserSolarObject.getSolrIgnoreNoOfMentorAnswers());
        tvMentorAnswerCount.setText(String.valueOf(numericToThousand(mUserSolarObject.getSolrIgnoreNoOfMentorAnswers())));
        tvMentorAnswer.setText(pluralAnswer);
    }

    @Override
    protected void onStop() {
        if (dialog != null) {
            dialog.dismiss();
        }

        if (mProfileProgressDialog != null && mProfileProgressDialog.isVisible()) {
            mProfileProgressDialog.dismiss();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
                tvMentorAskQuestion.setText(getString(R.string.ID_ANSWER_QUESTION));
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.selecter_invite_friend);
                viewFooter.setVisibility(View.VISIBLE);

                progressbarContainer.setVisibility(View.VISIBLE);
                setProfileLevel();
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

                followUnFollowMentor();
            }
        } else {
            followUnFollowMentor();
        }

        if ((mUserSolarObject.getUserSubType()!=null && mUserSolarObject.getUserSubType().equalsIgnoreCase(MentorsUserListingActivity.CHAMPION_SUBTYPE)) || userSolrObj.isAuthorMentor())  {
            isMentor = true;
        }

        if (isMentor) {
            verifiedIcon.setVisibility(View.VISIBLE);
        } else {
            verifiedIcon.setVisibility(View.INVISIBLE);
        }

        invalidateProfileButton();

        if (isOwnProfile) {
            verifiedIcon.setVisibility(View.GONE);
            editIcon.setVisibility(View.VISIBLE);
        } else {
            editIcon.setVisibility(View.GONE);
        }

        updateProfileInfo();

        invalidateProfileBadge(userSolrObj);

        setPagerAndLayouts();

        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_PUBLIC_PROFILE));
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
                if(!isMentor) {
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
            if (isOwnProfile && isMentor) {
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
                expandableTextView.makeTextViewResizable(userDescription, 1, ExpandableTextView.VIEW_MORE_TEXT, true, this);
            } else {
                userDescription.setText(description);
            }
        } else {
            if (isOwnProfile) {
                userDescription.setText(R.string.add_desc);
            }  else {
                descriptionContainer.setVisibility(View.GONE);
            }
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getImageUrl())) {
            if (!isFinishing()) {
                String authorThumborUrl = CommonUtil.getThumborUri(mUserSolarObject.getImageUrl(), profileSize, profileSize);
                mProfileIcon.setCircularImage(true);
                mProfileIcon.bindImage(authorThumborUrl);
            }
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getNameOrTitle())) {
            String userNameTitle = CommonUtil.camelCaseString(mUserSolarObject.getNameOrTitle());

            userName.setText(userNameTitle);
            tvMentorToolbarName.setText(userNameTitle);
            setUserNameTitle(userNameTitle);
        }

        String pluralAnswer = getResources().getQuantityString(R.plurals.numberOfPosts, mUserSolarObject.getSolrIgnoreNoOfMentorAnswers());
        tvMentorPost.setText(pluralAnswer);
        if (isMentor) {
            userTotalPostCount.setText(String.valueOf(numericToThousand(mUserSolarObject.getSolrIgnoreNoOfMentorPosts())));
        }
        liPost.setVisibility(View.VISIBLE);

        String pluralFollower = getResources().getQuantityString(R.plurals.numberOfFollowers, mUserSolarObject.getSolrIgnoreNoOfMentorFollowers());
        userFollower.setText(pluralFollower);
        if (isMentor) {
            userFollowerCount.setText(String.valueOf(numericToThousand(mUserSolarObject.getSolrIgnoreNoOfMentorFollowers())));
        }

        if (isMentor) {
            liFollowing.setVisibility(View.GONE);
            pluralAnswer = getResources().getQuantityString(R.plurals.numberOfAnswers, mUserSolarObject.getSolrIgnoreNoOfMentorAnswers());
            tvMentorAnswerCount.setText(String.valueOf(numericToThousand(mUserSolarObject.getSolrIgnoreNoOfMentorAnswers())));
            tvMentorAnswer.setText(pluralAnswer);
            liAnswer.setVisibility(View.VISIBLE);
        } else {
            liAnswer.setVisibility(View.GONE);
            followingTitle.setText(getResources().getString(R.string.following));
            liFollowing.setVisibility(View.VISIBLE);
        }

        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_PUBLIC_PROFILE));
    }

    private void toolTipForAskQuestion() {
        if(ProfileActivity.this.isFinishing()) return;

        try {
            toolTipProfile.setVisibility(View.INVISIBLE);
            final View askQuesToolTip;
            final PopupWindow popupWindowAnswerQuestionTooTip;
            askQuesToolTip = LayoutInflater.from(this).inflate(R.layout.tool_tip_arrow_down_side, null);
            popupWindowAnswerQuestionTooTip = new PopupWindow(askQuesToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindowAnswerQuestionTooTip.setOutsideTouchable(true);
            popupWindowAnswerQuestionTooTip.showAsDropDown(toolTipProfile, 0, 0);
            final TextView tvGotIt = askQuesToolTip.findViewById(R.id.got_it);
            final TextView tvTitle = askQuesToolTip.findViewById(R.id.title);
            tvTitle.setText(getString(R.string.tool_tip_ask_question));
            tvGotIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindowAnswerQuestionTooTip.dismiss();
                    toolTipProfile.setVisibility(View.GONE);
                }
            });
        } catch (WindowManager.BadTokenException e) {
            Crashlytics.getInstance().core.logException(e);
        }
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
            imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(20, ProfileActivity.this), 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
            imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            ivArrow.setLayoutParams(imageParams);
            final LinearLayout llToolTipBg = popupFollowToolTip.findViewById(R.id.ll_tool_tip_bg);
            RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(300, ProfileActivity.this), LinearLayout.LayoutParams.WRAP_CONTENT);
            llParams.setMargins(CommonUtil.convertDpToPixel(20, ProfileActivity.this), 0, 0, 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
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

    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }


    private void setProfileLevel() {
        if (mUserSolarObject.getProfileCompletionWeight() > BEGINNER_START_LIMIT && mUserSolarObject.getProfileCompletionWeight() < INTERMEDIATE_END_LIMIT) {
            profileLevel.setText(R.string.progress_status_beginner);

            if (mUserSolarObject.getProfileCompletionWeight() >= ProfileProgressDialog.BEGINNER_END_LIMIT) {
                beginnerTick.setImageResource(R.drawable.ic_level_complete);
            } else {
                beginnerTick.setImageResource(R.drawable.ic_level_incomplete);
            }
            intermediateTick.setImageResource(R.drawable.ic_level_incomplete);
            allStarTick.setImageResource(R.drawable.ic_all_level_incomplete);

        } else if (mUserSolarObject.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolarObject.getUnfilledProfileFields())) {
            profileLevel.setText(R.string.progress_status_all_star);

            if (mUserSolarObject.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolarObject.getUnfilledProfileFields())) {
                allStarTick.setImageResource(R.drawable.ic_all_level_complete);
            } else {
                allStarTick.setImageResource(R.drawable.ic_all_level_incomplete);
            }
            beginnerTick.setImageResource(R.drawable.ic_level_complete);
            intermediateTick.setImageResource(R.drawable.ic_level_complete);

        } else {
            profileLevel.setText(R.string.progress_level_status_intermediate);

            if (mUserSolarObject.getProfileCompletionWeight() >= INTERMEDIATE_END_LIMIT) {
                intermediateTick.setImageResource(R.drawable.ic_level_complete);
            } else {
                intermediateTick.setImageResource(R.drawable.ic_level_incomplete);
            }
            allStarTick.setImageResource(R.drawable.ic_all_level_incomplete);
            beginnerTick.setImageResource(R.drawable.ic_level_complete);

        }
    }

    @OnClick({R.id.beginner})
    protected void openBeginnerDialog() {
        openProfileProfileLevelDialog(ProfileProgressDialog.ProfileLevelType.BEGINNER);
    }


    @OnClick(R.id.intermediate)
    protected void openIntermediateProgressDialog() {
        openProfileProfileLevelDialog(ProfileProgressDialog.ProfileLevelType.INTERMEDIATE);
    }


    @OnClick(R.id.all_star)
    protected void openAllStarProgressDialog() {
        openProfileProfileLevelDialog(ProfileProgressDialog.ProfileLevelType.ALLSTAR);
    }

    @OnClick(R.id.new_feature)
    protected void openUserProfileLevelDialog() {
        if (mUserSolarObject != null) {
            CommonUtil.setPrefValue(AppConstants.PROFILE_OFFER_PREF);
            ProfileProgressDialog.ProfileLevelType profileLevelType = userLevel(mUserSolarObject);
            openProfileProfileLevelDialog(profileLevelType);
            newFeature.setVisibility(View.GONE);
        }
    }

    private void openProfileProfileLevelDialog(ProfileProgressDialog.ProfileLevelType profileLevelType) {

        if (mUserSolarObject == null) return;

        if (mProfileProgressDialog != null && mProfileProgressDialog.isVisible()) {
            mProfileProgressDialog.dismiss();
        }

        mProfileProgressDialog = new ProfileProgressDialog();
        mProfileProgressDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mProfileProgressDialog.isVisible() && !mIsDestroyed) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mUserSolarObject);
            bundle.putParcelable(AppConstants.USER, parcelable);
            bundle.putSerializable(ProfileProgressDialog.PROFILE_LEVEL, profileLevelType);
            bundle.putString(AppConstants.SOURCE_NAME, SCREEN_LABEL);
            mProfileProgressDialog.setArguments(bundle);
            mProfileProgressDialog.show(getFragmentManager(), ProfileProgressDialog.class.getName());
        }
    }

    private void followUnFollowMentor() {

        if (mUserSolarObject.isSolrIgnoreIsMentorFollowed() || mUserSolarObject.isSolrIgnoreIsUserFollowed()) {
            tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvMentorDashBoardFollow.setText(this.getString(R.string.following_user));
            tvMentorDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.footer_icon_text));
            tvMentorDashBoardFollow.setText(this.getString(R.string.follow_user));
            tvMentorDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
        LinearLayout.LayoutParams insight = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        insight.setMargins(0, 0, 0, 0);
        tvMentorSeeInsight.setLayoutParams(insight);
        LinearLayout.LayoutParams firstImageLayout = (LinearLayout.LayoutParams) tvMentorSeeInsight.getLayoutParams();
        firstImageLayout.weight = 0;
        viewFooter.setVisibility(View.GONE);
        LinearLayout.LayoutParams ask = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        ask.setMargins(0, 0, 0, 0);
        tvMentorAskQuestion.setLayoutParams(ask);
        LinearLayout.LayoutParams secondImageLayout = (LinearLayout.LayoutParams) tvMentorAskQuestion.getLayoutParams();
        secondImageLayout.weight = 1;
        if (isMentor) {
            if (CommonUtil.forGivenCountOnly(AppConstants.ASK_QUESTION_SESSION_SHARE_PREF, AppConstants.ASK_QUESTION_SESSION) == AppConstants.ASK_QUESTION_SESSION) {
                if (CommonUtil.ensureFirstTime(AppConstants.ASK_QUESTION_SHARE_PREF)) {
                    toolTipForAskQuestion();
                }
            }
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
        if (isMentor) {
            mViewPagerAdapter.addFragment(UserPostFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId, getString(R.string.ID_PROFILE_POST)), getString(R.string.ID_MENTOR_POST));
            mViewPagerAdapter.addFragment(MentorQADetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId), getString(R.string.ID_MENTOR_Q_A));
        } else {
            mViewPagerAdapter.addFragment(ProfileDetailsFragment.createInstance(mChampionId, mUserSolarObject.getNameOrTitle()), getString(R.string.ID_PROFILE));
            mViewPagerAdapter.addFragment(UserPostFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId, getString(R.string.ID_PROFILE_POST)), getString(R.string.ID_MENTOR_POST));
        }
        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        String screenName;
        if (mLoggedInUserId != mUserSolarObject.getIdOfEntityOrParticipant()) {
            screenName = "Stories";
            bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=UserStoryStream&userId=" + mUserSolarObject.getIdOfEntityOrParticipant());
        } else {
            screenName = "My Stories";
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
            if (!isMentor) { //for user make post as default tab
                mViewPager.setCurrentItem(1);
            }
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(this);
        if (askingQuestionCode == AppConstants.ASKING_QUESTION_CALL) {
            mViewPager.setCurrentItem(AppConstants.ONE_CONSTANT);
            mAppBarLayout.setExpanded(false);
        }
    }

    @OnClick(R.id.tv_profile_menu)
    public void onMenuClick() {
        onProfileMenuClick(mUserSolarObject, profileToolbarMenu);
    }

    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public void onProfileMenuClick(final UserSolrObj userPostObj, final TextView tvFeedCommunityPostUserCommentPostMenu) {
        PopupMenu popup = new PopupMenu(this, tvFeedCommunityPostUserCommentPostMenu);

        //admin and community moderator have feature to share profile and deactivate user
        if (mLoggedInUserId != userPostObj.getIdOfEntityOrParticipant() && (mLoggedInUserIdTypeId == ADMIN_TYPE_ID || mLoggedInUserIdTypeId == COMMUNITY_MODERATOR_TYPE_ID)) {
            popup.getMenu().add(0, R.id.share, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_share_black), getResources().getString(R.string.SHARE_PROFILE)));
            popup.getMenu().add(0, R.id.deactivate_user, 2, menuIconWithText(getResources().getDrawable(R.drawable.deactivate_user), getResources().getString(R.string.deactivate_user)));
        } else if (mLoggedInUserId != userPostObj.getIdOfEntityOrParticipant()) {
            popup.getMenu().add(0, R.id.report_spam, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_report_spam), getResources().getString(R.string.REPORT_SPAM)));
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deactivate_user:
                        deactivateUser(userPostObj);
                        return true;
                    case R.id.share:
                        shareProfile();
                        return true;
                    case R.id.report_spam:
                        reportSpamDialog(SpamContentType.USER, userPostObj);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    @OnClick(R.id.fab_post)
    public void createNewPost() {
        /*if (mFeedDetail instanceof UserSolrObj) {
            UserSolrObj userSolrObj = (UserSolrObj) mFeedDetail;
            CommunityPost mentorPost = new CommunityPost();
            mentorPost.community = new Community();
            mentorPost.community.id = userSolrObj.getSolrIgnoreMentorCommunityId();
            mentorPost.community.name =userSolrObj.getNameOrTitle();
            mentorPost.isEdit = false;
            mentorPost.isCompanyAdmin =  userSolrObj.getCompanyAdmin();
            CommunityPostActivity.navigateTo(this, mentorPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
        }*/
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

    public void addAnalyticsEvents(Event event) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isMentor)
                        .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    @OnClick(R.id.li_post)
    public void selectScrollPstTab() {
        if (isMentor) {
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setCurrentItem(1);
        }
        mAppBarLayout.setExpanded(false);
    }

    @OnClick(R.id.tv_mentor_see_insight)
    public void mentorSeeInsightClick() {
        Intent intent = new Intent(this, MentorInsightActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @OnClick(R.id.share_profile)
    public void shareProfile() {
        shareCardViaSocial();
    }

    @OnClick(R.id.tv_mentor_ask_question)
    public void mentorAskQuestionClick() {
        if (tvMentorAskQuestion.getText().toString().equalsIgnoreCase(getString(R.string.ID_ANSWER_QUESTION))) {
            mViewPager.setCurrentItem(AppConstants.ONE_CONSTANT);
            mAppBarLayout.setExpanded(false);
        } else {
            if (null != mUserSolarObject) {
                CommunityPost mentorPost = new CommunityPost();
                mentorPost.community = new Community();
                mentorPost.community.id = mUserSolarObject.getSolrIgnoreMentorCommunityId();
                mentorPost.community.name = mUserSolarObject.getNameOrTitle();
                mentorPost.createPostRequestFrom = AppConstants.MENTOR_CREATE_QUESTION;
                mentorPost.isEdit = false;
                mentorPost.isCompanyAdmin = mUserSolarObject.getCompanyAdmin();
                CommunityPostActivity.navigateTo(this, mentorPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
            }
        }
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
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(mUserPreference.get().getUserSummary().getUserId()))
                            .name(mUserPreference.get().getUserSummary().getFirstName())
                            .build();
            trackEvent(Event.PROFILE_PIC_EDIT_CLICKED, properties);
            CameraBottomSheetFragment.showDialog(this, SCREEN_LABEL);
        }
    }

    @OnClick(R.id.tv_mentor_dashboard_follow)
    public void mentorFollowClick() {
        if (isOwnProfile) {
            navigateToProfileEditing();
        } else {
            tvMentorDashBoardFollow.setEnabled(false);
            PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
            publicProfileListRequest.setIdOfEntityParticipant(mUserSolarObject.getIdOfEntityOrParticipant());

            if (mUserSolarObject.isSolrIgnoreIsMentorFollowed() || mUserSolarObject.isSolrIgnoreIsUserFollowed()) {
                //Unfollow event
                isFollowEvent = false;
                unFollowConfirmation(publicProfileListRequest);
            } else {
                isFollowEvent = true;
                mHomePresenter.getFollowFromPresenter(publicProfileListRequest, mUserSolarObject);
                addAnalyticsEvents(Event.PROFILE_FOLLOWED);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, position);
        if (fragment instanceof UserPostFragment) {
            if (isOwnProfile) {
                createPost.setVisibility(View.VISIBLE);
            } else {
                createPost.setVisibility(View.GONE);
            }
            clStoryFooter.setVisibility(View.GONE);
        } else if (fragment instanceof MentorQADetailFragment) {
            createPost.setVisibility(View.GONE);
            clStoryFooter.setVisibility(View.GONE);
        } else if (fragment instanceof FeedFragment) {
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
                } else if (!isProfileClicked) {
                    onActivtyResultOfParentRefresh();
                }
            }
        }
        super.onBackPressed();
    }

    private void onActivtyResultOfParentRefresh() {
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

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            communityDetailHandled(view, baseResponse);
        } else if (baseResponse instanceof Comment) {
            setAllValues(mFragmentOpen);
             /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU);
        }
    }

    private void communityDetailHandled(View view, BaseResponse baseResponse) {
        UserPostSolrObj userPostSolrObj = (UserPostSolrObj) baseResponse;
        mFragment = mViewPagerAdapter.getActiveFragment(mViewPager, mViewPager.getCurrentItem());
        setFragment(mFragment);
        mFragmentOpen.setOwner(userPostSolrObj.isCommunityOwner());
        setAllValues(mFragmentOpen);
        super.feedCardsHandled(view, baseResponse);
    }


    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        int fragmentPosition = isMentor ? AppConstants.NO_REACTION_CONSTANT : AppConstants.ONE_CONSTANT;
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, fragmentPosition);
        if (fragment instanceof UserPostFragment)
            ((UserPostFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
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
    public void onBackPressed() {
        onBackClick();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
    }


    @Override
    public void onDestroy() {
        if (popupWindowFollowTooTip != null && popupWindowFollowTooTip.isShowing()) {
            popupWindowFollowTooTip.dismiss();
        }
        profilePresenter.detachView();
        mHomePresenter.detachView();
        super.onDestroy();
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
        onShowErrorDialog(s, feedParticipationEnum);
        loaderGif.setVisibility(View.GONE);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse profileFeedResponsePojo) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mUserSolarObject = (UserSolrObj) feedDetailList.get(0);
            String screenName = AppConstants.GROWTH_PUBLIC_PROFILE;
            mUserSolarObject.setCallFromName(screenName);

            if (isMentor) {
                clHomeFooterList.setVisibility(View.VISIBLE);
            }
            if (isMentorQARefresh) {
                profileActivitiesRefresh();
            } else {
                setProfileDetails(mUserSolarObject);
            }

            if (isMentor) {
                loaderGif.setVisibility(View.GONE);
            }

            if (profileLevelType != null)
                openProfileProfileLevelDialog(profileLevelType);
        }
    }

    @Override
    public void getTopSectionCount(ProfileTopSectionCountsResponse profileTopSectionCountsResponse) {

    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {

    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse spamResponse) {
        if (spamResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {

            if (ProfileActivity.this.isFinishing()) return;

            if (!spamResponse.isSpamAlreadyReported()) {
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

    public void invalidateProfileBadge(UserSolrObj userSolrObj) {
        setupSheBadge(userSolrObj); //setup She badge

        setupProfileBadges(userSolrObj); //horizontal badge list on profile
    }

    private void setupProfileBadges(final UserSolrObj userSolrObj) {

        if (userSolrObj == null || !StringUtil.isNotEmptyCollection(userSolrObj.getUserBadgesList()))
            return;

        badgeContainer.setVisibility(View.VISIBLE);

        int length = userSolrObj.getUserBadgesList().size();
        int counter = 0;
        for (final BadgeDetails badgeDetails : userSolrObj.getUserBadgesList()) {
            final ImageView badge = new ImageView(this);
            LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(badgeIconSize, badgeIconSize);
            layoutParams.setMargins(0, 0, bageIconMargin, 0);
            badge.setLayoutParams(layoutParams);
            if(StringUtil.isNotNullOrEmptyString(badgeDetails.getImageUrl())) {
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

            if(counter == MAX_BADGE_COUNT) break;
        }

        if (length > MAX_BADGE_COUNT) {
            TextView badgeCount = new TextView(this);
            badgeCount.setTypeface(Typeface.create(BADGE_COUNTER_FONT_FAMILY, Typeface.NORMAL));
            badgeCount.setTextSize(badgeCounterTextSize);
            badgeCount.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.badge_counter)));
            LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(badgeIconSize,badgeIconSize);
            badgeCount.setLayoutParams(layoutParams);
            badgeCount.setText(getString(R.string.BadgeCounter, (length - MAX_BADGE_COUNT)));
            badgeCount.setBackground(getResources().getDrawable(R.drawable.circular_background_red));
            badgeCount.setGravity(Gravity.CENTER);
            badgeContainer.addView(badgeCount);

            badgeCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BadgeClosetActivity.navigateTo(ProfileActivity.this, userSolrObj.getUserBadgesList() , userSolrObj, SCREEN_LABEL);
                }
            });
        }
    }

    private void setupSheBadge(UserSolrObj userSolrObj) {

        if(userSolrObj.isSheBadgeActive() && !TextUtils.isEmpty(userSolrObj.getProfileBadgeUrl())) {
                    userBadgeIcon.setVisibility(View.VISIBLE);
                    Glide.with(userBadgeIcon.getContext())
                    .load(userSolrObj.getProfileBadgeUrl())
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(userBadgeIcon.getContext())))
                    .into(userBadgeIcon);
        } else if(!userSolrObj.isSheBadgeActive() && !TextUtils.isEmpty(userSolrObj.getProfileBadgeUrl())) {
            userBadgeIcon.setVisibility(View.VISIBLE);
            Glide.with(userBadgeIcon.getContext())
                    .load(userSolrObj.getProfileBadgeUrl())
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(userBadgeIcon.getContext())))
                    .into(userBadgeIcon);
        } else {
            userBadgeIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public void showHomeFeedList(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case FOLLOW_UNFOLLOW:
                UserSolrObj userSolrObj = (UserSolrObj) baseResponse;
                tvMentorDashBoardFollow.setEnabled(true);
                userFollowerCount.setText(String.valueOf(numericToThousand(userSolrObj.getSolrIgnoreNoOfMentorFollowers())));
                followUnFollowMentor();
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
    public void dataOperationOnClick(BaseResponse baseResponse) {
        setAllValues(mFragmentOpen);
        super.dataOperationOnClick(baseResponse);
    }

    public void championDetailActivity(Long userId, boolean isMentor) {
        mUserSolarObject = new UserSolrObj();
        mUserSolarObject.setIdOfEntityOrParticipant(userId);
        mUserSolarObject.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        ProfileActivity.navigateTo(this, mUserSolarObject, userId, isMentor, -1, AppConstants.PROFILE_CHAMPION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    private void shareCardViaSocial() {
        String branchPostDeepLink;
        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getPostShortBranchUrls())) {
            branchPostDeepLink = mUserSolarObject.getPostShortBranchUrls();
        } else {
            if (isMentor) {
                branchPostDeepLink = mUserSolarObject.getMentorDeepLinkUrl();
            } else {
                branchPostDeepLink = mUserSolarObject.getDeepLinkUrl();
            }
        }

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isMentor)
                        .isOwnProfile(isOwnProfile)
                        .build();
        trackEvent(Event.PROFILE_SHARED, properties);

        createShareImage(branchPostDeepLink);

    }


    private void createShareImage(final String branchPostDeepLink) {

        if (mUserSolarObject != null) {
            View view;
            view = LayoutInflater.from(this).inflate(R.layout.layout_user_share, null, false);

            final LinearLayout userDetailContainer = ButterKnife.findById(view, R.id.user_share_container);
            LinearLayout answerCountContainer = ButterKnife.findById(view, R.id.answers_count_container);
            LinearLayout followingContainer = ButterKnife.findById(view, R.id.followings_count_view);
            final ImageView imageIcon = ButterKnife.findById(view, R.id.profile_pic);
            ImageView verifiedMentorIcon = ButterKnife.findById(view, R.id.badge);

            TextView name = ButterKnife.findById(view, R.id.user_name);
            TextView postCountNo = ButterKnife.findById(view, R.id.post_count);
            TextView followersCountNo = ButterKnife.findById(view, R.id.followers_count);
            TextView answerCountNo = ButterKnife.findById(view, R.id.answers_count);
            TextView followingCountNo = ButterKnife.findById(view, R.id.following_count);
            TextView location = ButterKnife.findById(view, R.id.location_user);
            TextView description = ButterKnife.findById(view, R.id.description);

            if (isMentor) {
                verifiedMentorIcon.setVisibility(View.VISIBLE);
            } else {
                verifiedMentorIcon.setVisibility(View.GONE);
            }

            if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getNameOrTitle())) {
                name.setText(mUserSolarObject.getNameOrTitle());
            }

            followersCountNo.setText(userFollowerCount.getText());
            postCountNo.setText(userTotalPostCount.getText());

            if (isMentor) {
                answerCountContainer.setVisibility(View.VISIBLE);
                followingContainer.setVisibility(View.GONE);
                answerCountNo.setText(tvMentorAnswerCount.getText());
            } else {
                answerCountContainer.setVisibility(View.GONE);
                followingContainer.setVisibility(View.VISIBLE);
                followingCountNo.setText(followingCount.getText());
            }

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

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
        if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            if (!comment.isAnonymous()) {
                championDetailActivity(comment.getParticipantUserId(), comment.isVerifiedMentor());
            }
        } else if (mValue == REQUEST_CODE_FOR_SELF_PROFILE_DETAIL) {
            if (mLoggedInUserId != -1) {
                championDetailActivity(mLoggedInUserId, 1, isMentor, AppConstants.FEED_SCREEN); //self profile
            }
        } else if (mValue == REQUEST_CODE_CHAMPION_TITLE) {
            UserPostSolrObj feedDetail = (UserPostSolrObj) baseResponse;
            ProfileActivity.navigateTo(this, feedDetail.getAuthorParticipantId(), isMentor, PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        } else if (mValue == REQUEST_CODE_FOR_COMMUNITY_DETAIL) {
            UserPostSolrObj postDetails = (UserPostSolrObj) baseResponse;
            CommunityDetailActivity.navigateTo(this, postDetails.getCommunityId(), getScreenName(), null, 1);
        } else if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            if (feedDetail.getEntityOrParticipantTypeId() != 15) {
                championDetailActivity(feedDetail.getCreatedBy(), feedDetail.getItemPosition(), feedDetail.isAuthorMentor(), AppConstants.FEED_SCREEN);
            }
        }
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
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_LISTING:
                    //  refetchCommunity()
                    break;
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST:
                    if (null != mViewPagerAdapter) {
                        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                        if (AppUtils.isFragmentUIActive(fragment)) {
                            if (fragment instanceof UserPostFragment) {
                                ((UserPostFragment) fragment).swipeToRefreshList();
                            }
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    if (null != mViewPagerAdapter) {
                        Fragment activeFragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
                        if (AppUtils.isFragmentUIActive(activeFragment)) {
                            if (activeFragment instanceof MentorQADetailFragment) {
                                ((MentorQADetailFragment) activeFragment).swipeToRefreshList();
                            }
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                    boolean isPostDeleted = false;
                    FeedDetail feedDetailObj = null;
                    if (AppUtils.isFragmentUIActive(mFragment)) {
                        Parcelable parcelable = intent.getParcelableExtra(FeedDetail.FEED_COMMENTS);
                        if (parcelable != null) {
                            UserPostSolrObj userPostSolrObj = Parcels.unwrap(parcelable);
                            isPostDeleted = intent.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                            feedDetailObj = userPostSolrObj;
                        }
                        if (null != feedDetailObj) {
                            if (isPostDeleted) {
                                if (mFragment instanceof UserPostFragment) {
                                    ((UserPostFragment) mFragment).commentListRefresh(feedDetailObj, FeedParticipationEnum.DELETE_COMMUNITY_POST);
                                } else if(mFragment instanceof MentorQADetailFragment){
                                    ((MentorQADetailFragment) mFragment).commentListRefresh(feedDetailObj, FeedParticipationEnum.DELETE_COMMUNITY_POST);

                                }
                            } else {
                                if (mFragment instanceof UserPostFragment) {
                                    ((UserPostFragment) mFragment).commentListRefresh(feedDetailObj, FeedParticipationEnum.COMMENT_REACTION);
                                } else {
                                    if (mFragment instanceof MentorQADetailFragment) {
                                        ((MentorQADetailFragment) mFragment).commentListRefresh(feedDetailObj, FeedParticipationEnum.COMMENT_REACTION);
                                        isMentorQARefresh = true;
                                    }
                                    mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.CAROUSEL_SUB_TYPE, AppConstants.ONE_CONSTANT, mChampionId));
                                }
                            }
                        }
                    }

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
                            setProfileLevel();
                        }

                        //Save image
                        userDetailsResponse.getUserSummary().getUserBO().setPhotoUrlPath(boardingDataResponse.getResponse());
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


    private void imageCropping(Intent intent) {
        try {
            if (localImageSaveForChallenge.exists()) {
                Bitmap photo = CompressImageUtil.decodeFile(localImageSaveForChallenge);
                mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
            } else {
                Toast.makeText(this, "Error while save image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            e.printStackTrace();
        }
    }

    public void setProfileNameData(String imageUrl) {
        /*if (null != imageUrl) {
            userImage.setCircularImage(true);
            userImage.bindImage(imageUrl);
        }*/
        File localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        setLocalImageSaveForChallenge(localImageSaveForChallenge);
    }

    public void setLocalImageSaveForChallenge(File localImageSaveForChallenge) {
        this.localImageSaveForChallenge = localImageSaveForChallenge;
    }

    private void cropingIMG() {
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
            expandableTextView.makeTextViewResizable(userDescription, 1, ExpandableTextView.VIEW_MORE_TEXT, true, this);

            mUserSolarObject.setDescription(description);
        }

        if (progressPercentage != -1) {
            mUserSolarObject.setProfileCompletionWeight(progressPercentage);
            dashProgressBar.setProgress(progressPercentage, false);

            mUserSolarObject.setFilledProfileFields(filledFields);
            mUserSolarObject.setUnfilledProfileFields(unfilledFields);
            setProfileLevel();
        }

        if (imageUrl != null) {
            refreshImageView(imageUrl);
        }
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .id(String.valueOf(mChampionId))
                .isMentor(isMentor)
                .isOwnProfile(isOwnProfile)
                .build();
        return properties;
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
    public String getScreenName() {
        return SCREEN_LABEL;
    }


    public void setProfileTopSectionCount(ProfileTopSectionCountsResponse profileTopSectionCount) {
        if (profileTopSectionCount.getStatus().equals(AppConstants.SUCCESS)) {
            setUsersPostCount(profileTopSectionCount.getPostCount());
            setUsersFollowerCount(profileTopSectionCount.getFollowerCount());
            setUsersFollowingCount(profileTopSectionCount.getFollowingCount());
        }
        loaderGif.setVisibility(View.GONE);
    }

    private void setUsersFollowingCount(int numFound) {
        followingTitle.setText(getResources().getString(R.string.following));
        mUserSolarObject.setUserFollowing(numFound);
        followingCount.setText(String.valueOf(numFound));
        liFollowing.setVisibility(View.VISIBLE);
    }

    private void setUsersFollowerCount(int numFound) {
        String pluralFollower = getResources().getQuantityString(R.plurals.numberOfFollowers, numFound);
        mUserSolarObject.setSolrIgnoreNoOfMentorFollowers(numFound);
        userFollowerCount.setText(String.valueOf(numericToThousand(numFound)));
        userFollower.setText(pluralFollower);
        liFollower.setVisibility(View.VISIBLE);

    }

    private void setUsersPostCount(int postCount) {
        String pluralAnswer = getResources().getQuantityString(R.plurals.numberOfPosts, postCount);
        mUserSolarObject.setSolrIgnoreNoOfMentorPosts(postCount);
        userTotalPostCount.setText(String.valueOf(numericToThousand(postCount)));
        tvMentorPost.setText(pluralAnswer);
        liPost.setVisibility(View.VISIBLE);
    }

    protected final void unFollowConfirmation(final PublicProfileListRequest publicProfileListRequest) {

        if (mUserSolarObject != null) {
            if (dialog != null) {
                dialog.dismiss();
            }

            dialog = new Dialog(ProfileActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.unfollow_confirmation_dialog);

            CircleImageView circleImageView = dialog.findViewById(R.id.user_img_icon);
            if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getImageUrl())) {
                String authorThumborUrl = CommonUtil.getThumborUri(mUserSolarObject.getImageUrl(), profileSizeSmall, profileSizeSmall);
                circleImageView.setCircularImage(true);
                circleImageView.bindImage(authorThumborUrl);
            }

            TextView text = dialog.findViewById(R.id.title);
            text.setText("Unfollow " + mUserSolarObject.getNameOrTitle());

            TextView dialogButton = dialog.findViewById(R.id.cancel);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvMentorDashBoardFollow.setEnabled(true);
                    dialog.dismiss();
                }
            });

            TextView unFollowButton = dialog.findViewById(R.id.unfollow);
            unFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                                    .name(mUserSolarObject.getNameOrTitle())
                                    .isMentor(isMentor)
                                    .isOwnProfile(isOwnProfile)
                                    .build();
                    AnalyticsManager.trackEvent(Event.PROFILE_UNFOLLOWED, getScreenName(), properties);

                    tvMentorDashBoardFollow.setEnabled(true);
                    mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest, mUserSolarObject);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }


    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, long userId, boolean isMentor, int askinQuestion, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        intent.putExtra(AppConstants.CHAMPION_ID, userId);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        if (askinQuestion != -1) {
            intent.putExtra(AppConstants.ASKING_QUESTION, askinQuestion);
        }
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
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
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
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
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode, boolean isWriteAStory) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(BaseActivity.STORIES_TAB, isWriteAStory);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (notificationId != -1) {
            intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        }
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, ProfileProgressDialog.ProfileLevelType profileLevelType, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (profileLevelType != null) {
            intent.putExtra(ProfileProgressDialog.PROFILE_LEVEL, profileLevelType);
        }
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public String getUserNameTitle() {
        return userNameTitle;
    }

    public void setUserNameTitle(String userNameTitle) {
        this.userNameTitle = userNameTitle;
    }

    public void refreshPostCount(boolean isPostDeleted) {
        if (isPostDeleted) {
            int postNumbers = mUserSolarObject.getSolrIgnoreNoOfMentorPosts();
            postNumbers = postNumbers > 0 ? postNumbers - 1 : 0;
            setUsersPostCount(postNumbers);
        }
    }

    private void invalidateProfileButton() {
        int mButtonSize = 31;
        if (isOwnProfile) {
            shareProfile.setVisibility(View.VISIBLE);
            if (isUserOrChampionDetailsFilled()) {

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params.addRule(RelativeLayout.LEFT_OF, R.id.tv_mentor_dashboard_follow);
                shareProfile.setLayoutParams(params);
                shareProfile.setPadding(0, 0, 0, 2);
                shareProfile.setText(AppConstants.SHARE_PROFILE);
                shareProfile.setBackgroundResource(R.drawable.selecter_invite_friend);

                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mButtonSize, this),
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params1.setMargins(CommonUtil.convertDpToPixel(8, this), 0, CommonUtil.convertDpToPixel(13, this), 0);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tvMentorDashBoardFollow.setText("");
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.ic_profile_edit_icon);
                tvMentorDashBoardFollow.setLayoutParams(params1);
            } else {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params.addRule(RelativeLayout.LEFT_OF, R.id.share_profile);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                tvMentorDashBoardFollow.setLayoutParams(params);
                tvMentorDashBoardFollow.setPadding(0, 0, 0, 2);
                tvMentorDashBoardFollow.setText(AppConstants.EDIT_PROFILE);
                tvMentorDashBoardFollow.setBackgroundResource(0);
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.selecter_invite_friend);

                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mButtonSize, this),
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params1.setMargins(CommonUtil.convertDpToPixel(8, this), 0, CommonUtil.convertDpToPixel(13, this), 0);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                shareProfile.setText("");
                shareProfile.setBackgroundResource(R.drawable.ic_share_profile);
                shareProfile.setLayoutParams(params1);
            }
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    CommonUtil.convertDpToPixel(mButtonSize, this));
            params.setMargins(0, 0, 20, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tvMentorDashBoardFollow.setLayoutParams(params);
            shareProfile.setVisibility(View.GONE);
        }
    }

    //Check if user have filled its details in profile
    private boolean isUserOrChampionDetailsFilled() {
        return mUserSolarObject.getProfileCompletionWeight() >= 85;
    }

    private void deactivateUser(final UserSolrObj userSolrObj) {

        if (ProfileActivity.this.isFinishing()) return;

        List<DeactivationReason> deactivationReasons = null;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.deactivationReasons != null && mConfiguration.get().configData.deactivationReasons.getDeactivationReasons() != null) {
            deactivationReasons = mConfiguration.get().configData.deactivationReasons.getDeactivationReasons();
        } else {
            String deactivateReasonsContent = AppUtils.getStringContent(AppConstants.DEACTIVATE_REASONS_FILE); //read user deactivation reasons from local file
            DeactivationReasons reasons = AppUtils.parseUsingGSONFromJSON(deactivateReasonsContent, DeactivationReasons.class.getName());
            deactivationReasons = reasons != null ? reasons.getDeactivationReasons() : null;
        }

        if (deactivationReasons == null || deactivationReasons.size() <= 0) return;

        final Dialog userDeactivationDialog = new Dialog(ProfileActivity.this);
        userDeactivationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        userDeactivationDialog.setCancelable(true);
        userDeactivationDialog.setContentView(R.layout.dialog_deactivate_user);

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.convertDpToPixel(16, ProfileActivity.this), CommonUtil.convertDpToPixel(10, ProfileActivity.this), 0, 0);

        TextView reasonTitle = userDeactivationDialog.findViewById(R.id.reason_title);
        TextView reasonSubTitle = userDeactivationDialog.findViewById(R.id.reason_sub_title);
        reasonTitle.setLayoutParams(layoutParams);
        reasonSubTitle.setLayoutParams(layoutParams);

        final CheckBox deleteUserActivityCheck = userDeactivationDialog.findViewById(R.id.delete_user_activity);
        final RadioGroup deactivationOptions = userDeactivationDialog.findViewById(R.id.options_container);
        SpamUtil.addDeactivationReasonsToRadioGroup(ProfileActivity.this, deactivationReasons, deactivationOptions);
        final ScrollView scrollView = userDeactivationDialog.findViewById(R.id.scroll_container);
        LinearLayout.LayoutParams scrollviewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.convertDpToPixel(250, ProfileActivity.this));
        scrollView.setLayoutParams(scrollviewParams);
        Button submit = userDeactivationDialog.findViewById(R.id.submit);
        final EditText reason = userDeactivationDialog.findViewById(R.id.edit_text_reason);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deactivationOptions.getCheckedRadioButtonId() != -1) {

                    DeactivateUserRequest deactivateUserRequest = new DeactivateUserRequest();
                    deactivateUserRequest.setUserId(userSolrObj.getIdOfEntityOrParticipant());
                    deactivateUserRequest.setReactivateUser(false);
                    deactivateUserRequest.setReasonForDeactivationDetails("");

                    if (deleteUserActivityCheck.isChecked()) {
                        deactivateUserRequest.setRemoveCommentByUser(true);
                        deactivateUserRequest.setRemovePostByUser(true);
                    } else {
                        deactivateUserRequest.setRemoveCommentByUser(false);
                        deactivateUserRequest.setRemovePostByUser(false);
                    }

                    RadioButton radioButton = deactivationOptions.findViewById(deactivationOptions.getCheckedRadioButtonId());
                    DeactivationReason deactivationReason = (DeactivationReason) radioButton.getTag();
                    if (deactivationReason != null) {
                        deactivateUserRequest.setDeactivationReason(deactivationReason.getDeactivationReasonId());
                        if (deactivationReason.getDeactivationReason().equalsIgnoreCase(getString(R.string.other))) {

                            if (reason.getVisibility() == View.VISIBLE) {
                                if (reason.getText().length() > 0 && reason.getText().toString().trim().length() > 0) {
                                    deactivateUserRequest.setReasonForDeactivationDetails(reason.getText().toString());
                                    profilePresenter.deactivateUser(deactivateUserRequest); //submit
                                    userDeactivationDialog.dismiss();

                                    onProfileDeactivation(userSolrObj); //add profile deactivation analytics
                                } else {
                                    reason.setVisibility(View.VISIBLE);
                                    reason.setError(getResources().getString(R.string.add_reason));
                                }
                            } else {
                                reason.setVisibility(View.VISIBLE);
                                SpamUtil.hideSpamReason(deactivationOptions, deactivationOptions.getCheckedRadioButtonId());
                                LinearLayout.LayoutParams scrollviewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.convertDpToPixel(50, ProfileActivity.this));
                                scrollView.setLayoutParams(scrollviewParams);
                            }
                        } else {
                            profilePresenter.deactivateUser(deactivateUserRequest);
                            userDeactivationDialog.dismiss();
                            onProfileDeactivation(userSolrObj); //add profile deactivation analytics
                        }
                    }
                }
            }
        });

        userDeactivationDialog.show();
    }

    private void reportSpamDialog(final SpamContentType spamContentType, final UserSolrObj userSolrObj) { //Add other type as parameterised object

        if (ProfileActivity.this == null || ProfileActivity.this.isFinishing()) return;

        SpamReasons spamReasons;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.reasonOfSpamCategory != null) {
            spamReasons = mConfiguration.get().configData.reasonOfSpamCategory;
        } else {
            String spamReasonsContent = AppUtils.getStringContent(AppConstants.SPAM_REASONS_FILE); //read spam reasons from local file
            spamReasons = AppUtils.parseUsingGSONFromJSON(spamReasonsContent, SpamReasons.class.getName());
        }

        if (spamReasons == null) return;

        final Dialog spamReasonsDialog = new Dialog(ProfileActivity.this);
        spamReasonsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        spamReasonsDialog.setCancelable(true);
        spamReasonsDialog.setContentView(R.layout.dialog_spam_options);

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.convertDpToPixel(16, ProfileActivity.this), CommonUtil.convertDpToPixel(10, ProfileActivity.this), 0, 0);

        TextView reasonTitle = spamReasonsDialog.findViewById(R.id.reason_title);
        TextView reasonSubTitle = spamReasonsDialog.findViewById(R.id.reason_sub_title);
        reasonTitle.setLayoutParams(layoutParams);
        reasonSubTitle.setLayoutParams(layoutParams);

        final RadioGroup spamOptions = spamReasonsDialog.findViewById(R.id.options_container);

        List<Spam> spamList = null;
        SpamPostRequest spamRequest = null;
        if (spamContentType == SpamContentType.USER) {
            spamList = spamReasons.getUserTypeSpams();
            spamRequest = SpamUtil.createProfileSpamByUser(userSolrObj, mLoggedInUserId);
        }

        if (spamRequest == null || spamList == null) return;

        SpamUtil.addRadioToView(ProfileActivity.this, spamList, spamOptions);

        Button submit = spamReasonsDialog.findViewById(R.id.submit);
        final EditText reason = spamReasonsDialog.findViewById(R.id.edit_text_reason);

        final SpamPostRequest finalSpamRequest = spamRequest;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spamOptions.getCheckedRadioButtonId() != -1) {

                    RadioButton radioButton = spamOptions.findViewById(spamOptions.getCheckedRadioButtonId());
                    Spam spam = (Spam) radioButton.getTag();
                    if (spam != null) {
                        finalSpamRequest.setSpamReason(spam.getReason());
                        finalSpamRequest.setScore(spam.getScore());

                        if (spam.getLabel().equalsIgnoreCase(getString(R.string.others))) {
                            if (reason.getVisibility() == View.VISIBLE) {

                                if (reason.getText().length() > 0 && reason.getText().toString().trim().length() > 0) {
                                    finalSpamRequest.setSpamReason(spam.getReason().concat(":" + reason.getText().toString()));
                                    profilePresenter.reportSpamPostOrComment(finalSpamRequest); //submit
                                    spamReasonsDialog.dismiss();

                                    if (spamContentType == SpamContentType.USER) {
                                        onProfileReported(userSolrObj);   //report the profile
                                    }
                                } else {
                                    reason.setError(getString(R.string.add_reason));
                                }

                            } else {
                                reason.setVisibility(View.VISIBLE);
                                SpamUtil.hideSpamReason(spamOptions, spamOptions.getCheckedRadioButtonId());
                            }
                        } else {
                            profilePresenter.reportSpamPostOrComment(finalSpamRequest);  //submit request
                            spamReasonsDialog.dismiss();

                            if (spamContentType == SpamContentType.USER) {
                                onProfileReported(userSolrObj);   //report the profile
                            }
                        }
                    }
                }
            }
        });

        spamReasonsDialog.show();
    }

    private void onProfileDeactivation(UserSolrObj userSolrObj) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                        .name(userSolrObj.getNameOrTitle())
                        .isMentor(userSolrObj.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_DEACTIVATE, getScreenName(), properties);
    }

    private void onProfileReported(UserSolrObj userSolrObj) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                        .name(userSolrObj.getNameOrTitle())
                        .isMentor(userSolrObj.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_REPORTED, getScreenName(), properties);
    }

    private ProfileProgressDialog.ProfileLevelType userLevel(UserSolrObj userSolrObj) {
        ProfileProgressDialog.ProfileLevelType profileType;

        if (userSolrObj.getProfileCompletionWeight() > BEGINNER_START_LIMIT && userSolrObj.getProfileCompletionWeight() <= INTERMEDIATE_END_LIMIT) {
            profileType = ProfileProgressDialog.ProfileLevelType.BEGINNER;
        } else if (userSolrObj.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT) {
            profileType = ProfileProgressDialog.ProfileLevelType.ALLSTAR;
        } else {
            profileType = ProfileProgressDialog.ProfileLevelType.INTERMEDIATE;
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


}
