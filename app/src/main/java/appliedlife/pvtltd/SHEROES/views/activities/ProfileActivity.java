package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
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
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MentorQADetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.CommunityEnum.MY_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.FOLLOWERS_COUNT_CLICK;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.FOLLOWING_COUNT_CLICK;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */

public class ProfileActivity extends BaseActivity implements HomeView, AppBarLayout.OnOffsetChangedListener, ViewPager.OnPageChangeListener {

    private final String TAG = LogUtils.makeLogTag(ProfileActivity.class);
    private static final String SCREEN_LABEL = "Profile Screen";
    private final int mButtonSize = 31;

    private String screenName = AppConstants.GROWTH_PUBLIC_PROFILE;
    private Long mChampionId;
    private boolean isMentor;
    private int mFromNotification;
    private FeedDetail mFeedDetail;
    private int askingQuestionCode;
    boolean isOwnProfile = false;
    ViewPagerAdapter mViewPagerAdapter;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;

    private Dialog dialog = null;
    private CommunityEnum communityEnum = MY_COMMUNITY;
    private long mCommunityPostId = 1;
    private long loggedInUserId = -1;
    private FragmentOpen mFragmentOpen;
    private Fragment mFragment;
    private UserPostSolrObj mUserPostForCommunity;
    private UserSolrObj mUserSolarObject;
    private int itemPosition;
    boolean isFollowEvent;
    private String mSourceName;
    private String userNameTitle;
    private boolean isProfileClicked = false;
    private File localImageSaveForChallenge;

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

    @Bind(R.id.tv_loc)
    TextView tvLoc;

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

    @Bind(R.id.tv_mentor_dashboard_follow)
    TextView tvMentorDashBoardFollow;

    @Bind(R.id.edit_overlay_container)
    LinearLayout editProfileOverlayContainer;

    @Bind(R.id.edit_icon)
    ImageView editIcon;

    @Bind(R.id.share_profile)
    TextView shareProfile;

    @Bind(R.id.tv_mentor_see_insight)
    TextView tvMentorSeeInsight;

    @Bind(R.id.iv_mentor_verified)
    ImageView verifiedIcon;

    @Bind(R.id.tv_mentor_ask_question)
    TextView tvMentorAskQuestion;

    @Bind(R.id.fab_post)
    FloatingActionButton createPost;

    @Bind(R.id.view_footer)
    View viewFooter;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @Inject
    Preference<Configuration> mConfiguration;

    @Inject
    HomePresenter mHomePresenter;

    @Bind(R.id.rl_mentor_full_view_header)
    RelativeLayout rlMentorFullViewHeader;

    @Bind(R.id.view_profile)
    View toolTipProfile;
    @Bind(R.id.view_tool_follow)
    View viewToolTipFollow;


    private boolean isMentorQARefresh = false;
    private PopupWindow popupWindowFollowTooTip;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.mentor_user_dashboard_layout);
        mHomePresenter.attachView(this);
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
            mSourceName = getIntent().getExtras().getString(BaseActivity.SOURCE_SCREEN);
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
        String feedSubType = isMentor ? AppConstants.CAROUSEL_SUB_TYPE : AppConstants.USER_SUB_TYPE;
        // long profileOwnerId = isMentor ? mUserSolarObject.getIdOfEntityOrParticipant() : mUserSolarObject.getEntityOrParticipantId();
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(feedSubType, AppConstants.ONE_CONSTANT, mChampionId));
        setConfigurableShareOption(isWhatsAppShare());
        ((SheroesApplication) getApplication()).trackScreenView(AppConstants.PUBLIC_PROFILE);
    }

    public void onResume() {
        super.onResume();
    }

    private boolean isWhatsAppShare() {
        boolean isWhatsappShare = false;
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
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
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void setProfileNameData(UserSolrObj userSolrObj) {
        rlMentorFullViewHeader.setVisibility(View.VISIBLE);
        mFeedDetail = userSolrObj;

        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            if (mUserPreference.get().getUserSummary().getUserBO().getParticipantId() == mUserSolarObject.getEntityOrParticipantId()) {
                isOwnProfile = true;
                loggedInUserId = mUserPreference.get().getUserSummary().getUserId();
                tvMentorDashBoardFollow.setText(getString(R.string.ID_EDIT_PROFILE));
                tvMentorAskQuestion.setText(getString(R.string.ID_ANSWER_QUESTION));
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.selecter_invite_friend);
                viewFooter.setVisibility(View.VISIBLE);
            } else {
                followUnFollowMentor();
            }
        } else {
            followUnFollowMentor();
        }

        if (isOwnProfile) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToolTip();
                }
            }, 1000);
        }

        if (userSolrObj.isAuthorMentor()) isMentor = true;

        if (isMentor) {
            verifiedIcon.setVisibility(View.VISIBLE);
        } else {
            verifiedIcon.setVisibility(View.INVISIBLE);
        }

        invalidateProfileButton();

        if(isOwnProfile) {
            verifiedIcon.setVisibility(View.GONE);
            editProfileOverlayContainer.setVisibility(View.GONE);
            editIcon.setVisibility(View.VISIBLE);
        } else {
            editIcon.setVisibility(View.GONE);
            editProfileOverlayContainer.setVisibility(View.GONE);
        }

        updateProfileInfo();
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
            Spanned description = StringUtil.fromHtml(mUserSolarObject.getDescription());
            userDescription.setText(description);
        } else {
            if (isOwnProfile) {
                userDescription.setText(R.string.add_desc);
            }
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getImageUrl())) {
            if(!isFinishing()) {
                mProfileIcon.setCircularImage(true);
                mProfileIcon.bindImage(mUserSolarObject.getImageUrl());
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
        try {
            toolTipProfile.setVisibility(View.INVISIBLE);
            final View askQuesToolTip;
            final PopupWindow popupWindowAnswerQuestionTooTip;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            askQuesToolTip = layoutInflater.inflate(R.layout.tool_tip_arrow_down_side, null);
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
            final View popupFollowToolTip;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupFollowToolTip = layoutInflater.inflate(R.layout.tooltip_arrow_up_side, null);
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
            llParams.setMargins(CommonUtil.convertDpToPixel(20, ProfileActivity.this), 0,0,0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
            llParams.addRule(RelativeLayout.BELOW, R.id.iv_arrow);
            llToolTipBg.setLayoutParams(llParams);
            final TextView tvGotIt =  popupFollowToolTip.findViewById(R.id.got_it);
            final TextView tvTitle =  popupFollowToolTip.findViewById(R.id.title);
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
            if(isOwnProfile) {
                createPost.setVisibility(View.VISIBLE);
            } else {
                createPost.setVisibility(View.GONE);
            }
            mViewPagerAdapter.addFragment(UserPostFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId, getString(R.string.ID_PROFILE_POST)), getString(R.string.ID_MENTOR_POST));
            mViewPagerAdapter.addFragment(MentorQADetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId), getString(R.string.ID_MENTOR_Q_A));
        } else {
            mViewPagerAdapter.addFragment(ProfileDetailsFragment.createInstance(mChampionId, mUserSolarObject.getNameOrTitle()), getString(R.string.ID_PROFILE));
            mViewPagerAdapter.addFragment(UserPostFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId, getString(R.string.ID_PROFILE_POST)), getString(R.string.ID_MENTOR_POST));
        }

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        if (askingQuestionCode == AppConstants.ASKING_QUESTION_CALL) {
            mViewPager.setCurrentItem(AppConstants.ONE_CONSTANT);
            mAppBarLayout.setExpanded(false);
        }
    }

    @OnClick(R.id.fab_post)
    public void createNewPost() {

        if (mFeedDetail instanceof UserSolrObj) {
            UserSolrObj userPostSolrObj = (UserSolrObj) mFeedDetail;
            CommunityPost mentorPost = new CommunityPost();
            mentorPost.community = new Community();
            mentorPost.community.id = userPostSolrObj.getSolrIgnoreMentorCommunityId();
            mentorPost.community.name = userPostSolrObj.getNameOrTitle();
            mentorPost.isEdit = false;
            mentorPost.isCompanyAdmin =  userPostSolrObj.getCompanyAdmin();
            CommunityPostActivity.navigateTo(this, mentorPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
        }
    }

    @OnClick(R.id.li_follower)
    public void followerClick() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isMentor)
                        .name(FOLLOWERS_COUNT_CLICK)
                        .sourceScreenId(mSourceName)
                        .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWER_COUNT, getScreenName(), properties);

        if(StringUtil.isNotNullOrEmptyString(userFollowerCount.getText().toString()) && !userFollowerCount.getText().toString().equalsIgnoreCase("0")) {
            FollowingActivity.navigateTo(this, mChampionId, isOwnProfile, getScreenName(), FollowingEnum.FOLLOWERS, null);
        }
    }

    @OnClick(R.id.li_following)
    public void followingClick() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .name(FOLLOWING_COUNT_CLICK)
                        .isMentor(isMentor)
                        .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWING_COUNT, getScreenName(), properties);
        if(StringUtil.isNotNullOrEmptyString(followingCount.getText().toString()) && !followingCount.getText().toString().equalsIgnoreCase("0")) {
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

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isMentor)
                        .sourceScreenId(SCREEN_LABEL)
                        .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_POST_COUNT, getScreenName(), properties);
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
                mentorPost.isCompanyAdmin =  mUserSolarObject.getCompanyAdmin();
                CommunityPostActivity.navigateTo(this, mentorPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
            }
        }
    }

    @OnClick({R.id.tv_mentor_name, R.id.tv_loc, R.id.tv_mentor_description})
    public void navigateToProfileEditing() {
        if (isOwnProfile) {
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
                EditUserProfileActivity.navigateTo(ProfileActivity.this, SOURCE_SCREEN, mUserSolarObject.getImageUrl(), null, 1);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                                .name(mUserSolarObject.getNameOrTitle())
                                .isMentor(isMentor)
                                .sourceScreenId(mSourceName)
                                .isOwnProfile(isOwnProfile)
                                .build();
                AnalyticsManager.trackEvent(Event.PROFILE_EDIT_CLICKED, getScreenName(), properties);
            }
        }
    }

    @OnClick(R.id.iv_mentor_full_view_icon)
    public void onImageEditClicked() {
        CameraBottomSheetFragment.showDialog(this, SOURCE_SCREEN);
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
            }

            Event event = isFollowEvent ? Event.PROFILE_FOLLOWED : Event.PROFILE_UNFOLLOWED;
            addAnalyticsEvents(event);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        String tabName = "";
        if (isMentor) {
            if (position == 0) tabName = "Profile - Posts";
            if (position == 1) tabName = "Profile - Q&A";
        } else {
            if (position == 0) tabName = "Profile - Profile";
            if (position == 1) tabName = "Profile - Posts";
        }

        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                .isMentor(isMentor)
                .title(tabName)
                .tabTitle(tabName)
                .isOwnProfile(isOwnProfile)
                .build();
        AnalyticsManager.trackScreenView(SCREEN_LABEL, properties);
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, position);
        if (fragment instanceof UserPostFragment) {
        if (tabName.equalsIgnoreCase("Profile - Posts") && isOwnProfile) {
            createPost.setVisibility(View.VISIBLE);
        } else {
            createPost.setVisibility(View.GONE);
        }
        } else if (fragment instanceof MentorQADetailFragment) {
            createPost.setVisibility(View.GONE);
        } else {
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
                if (!isProfileClicked) {
                    onActivtyResultOfParentRefresh();
                }
            }
        }
        super.onBackPressed();
    }

    private void onActivtyResultOfParentRefresh() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if(mUserSolarObject!=null){
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
        int id = view.getId();
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
        mUserPostForCommunity = userPostSolrObj;
        int id = view.getId();
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
        super.onDestroy();
        if (popupWindowFollowTooTip != null && popupWindowFollowTooTip.isShowing()) {
            popupWindowFollowTooTip.dismiss();
        }
        mHomePresenter.detachView();
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
        loaderGif.setVisibility(View.GONE);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mUserSolarObject = (UserSolrObj) feedDetailList.get(0);
            mUserSolarObject.setCallFromName(screenName);

            if (isMentor) {
                clHomeFooterList.setVisibility(View.VISIBLE);
            }
            if (isMentorQARefresh) {
                profileActivitiesRefresh();
            } else {
                setProfileNameData(mUserSolarObject);
            }

            if(isMentor) {
                loaderGif.setVisibility(View.GONE);
            }
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
    public void getNotificationListSuccess(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
        setAllValues(mFragmentOpen);
        super.dataOperationOnClick(baseResponse);
    }

    private void showToolTip() {
        if (CommonUtil.ensureFirstTime(AppConstants.PROFILE_SHARE_PREF)) {
            try {
                LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
                final View view = inflater.inflate(R.layout.tooltip_arrow_up_side, null);
                RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                lps.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.share_profile);
                lps.setMargins(CommonUtil.convertDpToPixel(40, ProfileActivity.this), CommonUtil.convertDpToPixel(160, ProfileActivity.this), 0, 0);
                final ImageView ivArrow = view.findViewById(R.id.iv_arrow);
                RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                if(isUserOrChampionDetailsFilled()) {
                    imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(150, ProfileActivity.this), 0);
                } else {
                    imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(18, ProfileActivity.this), 0);
                }
                imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                ivArrow.setLayoutParams(imageParams);
                TextView title = view.findViewById(R.id.title);
                title.setText(R.string.tool_tip_user_share);
                rootLayout.addView(view, lps);
                TextView gotIt = view.findViewById(R.id.got_it);
                gotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rootLayout.removeView(view);
                    }
                });
            } catch (IllegalArgumentException e) {
                Crashlytics.getInstance().core.logException(e);
            }
        }
    }

    public void championDetailActivity(Long userId, boolean isMentor) {
        mUserSolarObject = new UserSolrObj();
        mUserSolarObject.setIdOfEntityOrParticipant(userId);
        mUserSolarObject.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        ProfileActivity.navigateTo(this, mUserSolarObject, userId, true, AppConstants.PROFILE_CHAMPION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    private void shareCardViaSocial() {
        String branchPostDeepLink;
        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getPostShortBranchUrls())) {
            branchPostDeepLink = mUserSolarObject.getPostShortBranchUrls();
        } else {
            if(isMentor) {
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
                        .sourceScreenId(SCREEN_LABEL)
                        .isOwnProfile(isOwnProfile)
                        .sharedTo(AppConstants.SHARE_CHOOSER)
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
                championDetailActivity(comment.getParticipantId(), comment.isVerifiedMentor());
            }
        } else if (mValue == REQUEST_CODE_FOR_SELF_PROFILE_DETAIL) {
            if (loggedInUserId != -1) {
                championDetailActivity(loggedInUserId, 1, isMentor, AppConstants.FEED_SCREEN); //self profile
            }
        } else if (mValue == REQUEST_CODE_CHAMPION_TITLE) {
            UserPostSolrObj feedDetail = (UserPostSolrObj) baseResponse;
            championLinkHandle(feedDetail);
        } else if (baseResponse instanceof UserPostSolrObj && mValue == AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_USER_DETAIL) {
            UserPostSolrObj postDetails = (UserPostSolrObj) baseResponse;
            if (StringUtil.isNotEmptyCollection(postDetails.getLastComments())) {
                Comment comment = postDetails.getLastComments().get(0);
                if (!comment.isAnonymous()) {
                    championDetailActivity(comment.getParticipantUserId(), comment.getItemPosition(), comment.isVerifiedMentor(), AppConstants.COMMENT_REACTION_FRAGMENT);
                }
            }
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
                    refreshUserDetails(bundle.getString("NAME"), bundle.getString("LOCATION"), bundle.getString("BIO"), bundle.getString("IMAGE_URL"));
                }
            }

            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_LISTING:
                    //  refetchCommunity()
                    break;
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST:
                    if(null!=mViewPagerAdapter) {
                        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                        if (AppUtils.isFragmentUIActive(fragment)) {
                            if (fragment instanceof UserPostFragment) {
                                ((UserPostFragment) fragment).swipeToRefreshList();
                            }
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    if(null!=mViewPagerAdapter) {
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
                    if (AppUtils.isFragmentUIActive(mFragment)) {
                        Parcelable parcelable = intent.getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
                        if (parcelable != null) {
                            UserPostSolrObj userPostSolrObj = Parcels.unwrap(parcelable);
                            isPostDeleted = intent.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                            mFeedDetail = userPostSolrObj;
                        }
                        if (isPostDeleted) {
                            if (mFragment instanceof UserPostFragment) {
                                ((UserPostFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.DELETE_COMMUNITY_POST);
                            } else {
                                ((MentorQADetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.DELETE_COMMUNITY_POST);

                            }
                        } else {
                            if (mFragment instanceof UserPostFragment) {
                                ((UserPostFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.COMMENT_REACTION);
                            } else {
                                ((MentorQADetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.COMMENT_REACTION);
                                isMentorQARefresh = true;
                                mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.CAROUSEL_SUB_TYPE, AppConstants.ONE_CONSTANT, mChampionId));
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
            if(mUserPreference!=null && mUserPreference.isSet()){
                userDetailsResponse = mUserPreference.get();
            }
            if (userDetailsResponse != null) {
                try {
                    if (boardingDataResponse.getResponse() != null && boardingDataResponse.getResponse().contains("img.") && boardingDataResponse.getResponse().startsWith("http")) {
                        setProfileNameData(boardingDataResponse.getResponse());
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

    private void refreshUserDetails(String name, String location, String userBio, String imageUrl) {
        if(StringUtil.isNotNullOrEmptyString(name)) {
            name = CommonUtil.camelCaseString(name);
            userName.setText(name);
            tvMentorToolbarName.setText(name);
        }

        if(StringUtil.isNotNullOrEmptyString(location)) {
            tvLoc.setText(location);
        }

        if(StringUtil.isNotNullOrEmptyString(userBio)) {
            userDescription.setText(userBio);
            mUserSolarObject.setDescription(userBio);
        }
    }

    public void refreshImageView(String imageUrl){
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            mProfileIcon.setCircularImage(true);
            mProfileIcon.bindImage(imageUrl);
            mUserSolarObject.setImageUrl(imageUrl);
        }
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
                .sourceScreenId(mSourceName)
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
        if(profileTopSectionCount.getStatus().equals(AppConstants.SUCCESS)) {
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
                circleImageView.setCircularImage(true);
                circleImageView.bindImage(mUserSolarObject.getImageUrl());
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

    private void championLinkHandle(UserPostSolrObj userPostSolrObj) {
        ProfileActivity.navigateTo(this, userPostSolrObj.getAuthorParticipantId(), isMentor, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, String extraImage, boolean isMentor, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);

        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(AppConstants.EXTRA_IMAGE, extraImage);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, String extraImage, long userId, boolean isMentor, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);

        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtra(AppConstants.CHAMPION_ID, userId);
        intent.putExtras(bundle);
        intent.putExtra(AppConstants.EXTRA_IMAGE, extraImage);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, long userId, boolean isMentor, String source, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);

        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.CHAMPION_ID, userId);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, source);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, long id, boolean isMentor, int askinQuestion, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);

        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        intent.putExtra(AppConstants.CHAMPION_ID, id);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.ASKING_QUESTION, askinQuestion);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, boolean isMentor, int askingQuestion, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);

        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.ASKING_QUESTION, askingQuestion);
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

    public static void navigateTo(Activity fromActivity, CommunityFeedSolrObj dataItem, long mChampionId, boolean isMentor, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);

        Bundle bundle = new Bundle();
        dataItem.setIdOfEntityOrParticipant(mChampionId);
        dataItem.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
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

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
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
        intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, int askingQuestionCode, String sourceScreen, HashMap<String, Object> properties, int requestCode, UserSolrObj userSolrObj) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        Parcelable parcelableMentor = Parcels.wrap(userSolrObj);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.ASKING_QUESTION, askingQuestionCode);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtras(bundle);
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
            String postCount = userTotalPostCount.getText().toString();
            int postNumbers = Integer.parseInt(postCount);
            postNumbers = postNumbers > 0 ? postNumbers - 1 : 0;
            setUsersPostCount(postNumbers);
        }
    }

    private void invalidateProfileButton() {
        if(isOwnProfile) {
            shareProfile.setVisibility(View.VISIBLE);
            if (isUserOrChampionDetailsFilled()) {

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params.addRule(RelativeLayout.LEFT_OF, R.id.tv_mentor_dashboard_follow);
                shareProfile.setLayoutParams(params);
                shareProfile.setPadding(0, 0, 0, 2);
                shareProfile.setText(AppConstants.SHARE_PROFILE);

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

                shareProfile.setBackgroundResource(R.drawable.ic_share_profile);
                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(mButtonSize, this),
                        CommonUtil.convertDpToPixel(mButtonSize, this));
                params1.setMargins(CommonUtil.convertDpToPixel(8, this), 0, CommonUtil.convertDpToPixel(13, this), 0);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                shareProfile.setText("");
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

    //Check if user have filled its details
    private boolean isUserOrChampionDetailsFilled() {
        return StringUtil.isNotNullOrEmptyString(mUserSolarObject.getCityName()) && StringUtil.isNotNullOrEmptyString(mUserSolarObject.getDescription()) &&
                StringUtil.isNotNullOrEmptyString(mUserSolarObject.getNameOrTitle());
    }

}
