package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
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
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.presenters.EditProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MentorQADetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.CommunityEnum.MY_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */

public class MentorUserProfileActvity extends BaseActivity implements HomeView, AppBarLayout.OnOffsetChangedListener, ViewPager.OnPageChangeListener {

    private final String TAG = LogUtils.makeLogTag(MentorUserProfileActvity.class);
    private static final String SCREEN_LABEL = "Profile Screen";

    private String screenName = AppConstants.GROWTH_PUBLIC_PROFILE;
    private Long mChampionId;
    private boolean isMentor;
    private int mFromNotification;
    private FeedDetail mFeedDetail;
    private int askingQuestionCode;
    boolean isOwnProfile = false;
    ViewPagerAdapter mViewPagerAdapter;

    private Dialog dialog = null;
    private CommunityEnum communityEnum = MY_COMMUNITY;
    private long mCommunityPostId = 1;
    private FragmentOpen mFragmentOpen;
    private Fragment mFragment;
    private UserPostSolrObj mUserPostForCommunity;
    private UserSolrObj mUserSolarObject;
    private int itemPosition;
    boolean isFollowEvent;
    private String mSourceName;
    private String userNameTitle;

    @Bind(R.id.iv_mentor_full_view_icon)
    CircleImageView mProfileIcon;

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.iv_mentor_share)
    ImageView shareProfile;

    @Bind(R.id.view_pager_mentor)
    ViewPager mViewPager;

    @Bind(R.id.toolbar_mentor)
    Toolbar mToolbar;

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

    @Bind(R.id.tv_loc)
    TextView tvLoc;

    @Bind(R.id.tv_mentor_description)
    TextView userDescription;

    @Bind(R.id.cl_home_footer_list)
    public CardView clHomeFooterList;

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

    @Bind(R.id.tv_mentor_see_insight)
    TextView tvMentorSeeInsight;

    @Bind(R.id.iv_mentor_verified)
    ImageView verifiedIcon;

    @Bind(R.id.tv_mentor_ask_question)
    TextView tvMentorAskQuestion;

    @Bind(R.id.view_footer)
    View viewFooter;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    ProfilePresenterImpl profilePresenter;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    EditProfilePresenterImpl editProfilePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.mentor_user_dashboard_layout);
        mHomePresenter.attachView(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mAppBarLayout.addOnOffsetChangedListener(this);
        clHomeFooterList.setVisibility(View.GONE);

        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();

        mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mUserSolarObject = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.GROWTH_PUBLIC_PROFILE));
            mFeedDetail = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.MENTOR_DETAIL));
            mFromNotification = getIntent().getExtras().getInt(AppConstants.BELL_NOTIFICATION);
            askingQuestionCode = getIntent().getExtras().getInt(AppConstants.ASKING_QUESTION);
            mChampionId = getIntent().getExtras().getLong(AppConstants.CHAMPION_ID);
            isMentor = getIntent().getExtras().getBoolean(AppConstants.IS_MENTOR_ID);
            mSourceName = getIntent().getExtras().getString(BaseActivity.SOURCE_SCREEN);

        }
        if (null != mUserSolarObject) {
            itemPosition = mUserSolarObject.getItemPosition();
        }else if(null!=mFeedDetail)
        {
            itemPosition = mFeedDetail.getItemPosition();
        }
        if (mChampionId > 0) {
            mUserSolarObject = new UserSolrObj();
            mUserSolarObject.setEntityOrParticipantId(mChampionId);
            mUserSolarObject.setSolrIgnoreMentorCommunityId(mChampionId);
            mUserSolarObject.setIdOfEntityOrParticipant(mChampionId);
        }
        String feedSubType = isMentor ? AppConstants.MENTOR_SUB_TYPE : AppConstants.USER_SUB_TYPE;
        // long profileOwnerId = isMentor ? mUserSolarObject.getIdOfEntityOrParticipant() : mUserSolarObject.getEntityOrParticipantId();
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(feedSubType, AppConstants.ONE_CONSTANT, mChampionId));
        ((SheroesApplication) getApplication()).trackScreenView(AppConstants.PUBLIC_PROFILE);
    }

    @Override
    protected void onStop() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onStop();
    }

    public void setProfileNameData(UserSolrObj userSolrObj) {

        mFeedDetail = userSolrObj;

        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            if (mUserPreference.get().getUserSummary().getUserBO().getParticipantId() == mUserSolarObject.getEntityOrParticipantId()) {
                tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.footer_icon_text));
                isOwnProfile = true;
                tvMentorDashBoardFollow.setText(getString(R.string.ID_EDIT_PROFILE));
                tvMentorAskQuestion.setText(getString(R.string.ID_ANSWER_QUESTION));
                tvMentorDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                viewFooter.setVisibility(View.VISIBLE);
            } else {
                followUnFollowMentor();
            }
        } else {
            followUnFollowMentor();
        }

        if (userSolrObj.isAuthorMentor()) isMentor = true;

        if (isMentor) {
            verifiedIcon.setVisibility(View.VISIBLE);
        } else {
            verifiedIcon.setVisibility(View.INVISIBLE);
        }

        if (isOwnProfile) {
            shareProfile.setVisibility(View.VISIBLE);
        } else {
            shareProfile.setVisibility(View.GONE);
        }
        setPagerAndLayouts();

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getCityName()) && StringUtil.isNotNullOrEmptyString(mUserSolarObject.getCityName())) {
            tvLoc.setText(mUserSolarObject.getCityName());
            tvLoc.setVisibility(View.VISIBLE);
        } else {
            if (isOwnProfile) {
                tvLoc.setText(R.string.add_location);
            } else {
                tvLoc.setVisibility(View.GONE);
            }
        }
        if (StringUtil.isNotEmptyCollection(mUserSolarObject.getCanHelpIns())) {
            tvProfession.setText(mUserSolarObject.getCanHelpIns().get(0)); //skills
            tvProfession.setVisibility(View.VISIBLE);
        } else {
            if (isOwnProfile && isMentor) {
                tvProfession.setText(R.string.add_skills);
            } else {
                tvProfession.setVisibility(View.GONE);
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
            mProfileIcon.setCircularImage(true);
            mProfileIcon.bindImage(mUserSolarObject.getImageUrl());
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getNameOrTitle())) {
            userName.setText(mUserSolarObject.getNameOrTitle());
            tvMentorToolbarName.setText(mUserSolarObject.getNameOrTitle());
            setUserNameTitle(mUserSolarObject.getNameOrTitle());
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

    private void followUnFollowMentor() {

        if (mUserSolarObject.isSolrIgnoreIsMentorFollowed() || mUserSolarObject.isSolrIgnoreIsUserFollowed()) {
            tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvMentorDashBoardFollow.setText(this.getString(R.string.ID_GROWTH_BUDDIES_FOLLOWING));
            tvMentorDashBoardFollow.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.footer_icon_text));
            tvMentorDashBoardFollow.setText(this.getString(R.string.ID_GROWTH_BUDDIES_FOLLOW));
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
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbar);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (isMentor) {
            mViewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId, getString(R.string.ID_PROFILE_POST)), getString(R.string.ID_MENTOR_POST));
            mViewPagerAdapter.addFragment(MentorQADetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId), getString(R.string.ID_MENTOR_Q_A));
        } else {
            mViewPagerAdapter.addFragment(UserProfileTabFragment.createInstance(mChampionId, mUserSolarObject.getNameOrTitle()), getString(R.string.ID_PROFILE));
            mViewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId, getString(R.string.ID_PROFILE_POST)), getString(R.string.ID_MENTOR_POST));
        }

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        if (askingQuestionCode == AppConstants.ASKING_QUESTION_CALL) {
            mViewPager.setCurrentItem(AppConstants.ONE_CONSTANT);
            mAppBarLayout.setExpanded(false);
        }
    }

    @OnClick(R.id.li_follower)
    public void followerClick() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isMentor)
                        .name("Followers Count Click")
                        .sourceScreenId(mSourceName)
                        .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWER_COUNT, getScreenName(), properties);
    }

    @OnClick(R.id.li_follower)
    public void followingClick() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isMentor)
                        .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_FOLLOWING_COUNT, getScreenName(), properties);
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
                CommunityPostActivity.navigateTo(this, mentorPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
            }
        }
    }

    @OnClick({R.id.iv_mentor_full_view_icon, R.id.tv_mentor_name, R.id.tv_loc, R.id.tv_mentor_description})
    public void navigateToProfileEditing() {
        if (isOwnProfile) {
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
                EditUserProfileActivity.navigateTo(MentorUserProfileActvity.this, SOURCE_SCREEN, mUserSolarObject.getImageUrl(), null, 1);
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

    @OnClick(R.id.iv_mentor_share)
    public void mentorShareClick() {
        shareCardViaSocial();
    }

    @OnClick(R.id.tv_mentor_dashboard_follow)
    public void mentorFollowClick() {
        if (tvMentorDashBoardFollow.getText().toString().equalsIgnoreCase(getString(R.string.ID_EDIT_PROFILE))) {
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
        if (fragment instanceof CommunitiesDetailFragment) {

        } else if (fragment instanceof MentorQADetailFragment) {

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onBackClick() {
        if (mChampionId > 0) {
            if (mFromNotification == AppConstants.NO_REACTION_CONSTANT) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                deepLinkPressHandle();
            }
        } else {
            deepLinkPressHandle();
        }
        finish();
    }

    private void deepLinkPressHandle() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        mUserSolarObject.currentItemPosition = itemPosition;
        Parcelable parcelableMentorDetail = Parcels.wrap(mUserSolarObject);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentorDetail);
        Parcelable parcelable = Parcels.wrap(mUserSolarObject);
        bundle.putParcelable(AppConstants.FEED_SCREEN, parcelable);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
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
        mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FOURTH_CONSTANT);
        mFragmentOpen.setOwner(userPostSolrObj.isCommunityOwner());
        setAllValues(mFragmentOpen);
        super.feedCardsHandled(view, baseResponse);
    }


    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        int fragmentPosition = isMentor ? AppConstants.NO_REACTION_CONSTANT : AppConstants.ONE_CONSTANT;
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, fragmentPosition);
        if (fragment instanceof CommunitiesDetailFragment)
            ((CommunitiesDetailFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
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
        if (mFragmentOpen != null) {
            if (mFragmentOpen.isCommentList()) {
                getSupportFragmentManager().popBackStackImmediate();
                if (AppUtils.isFragmentUIActive(mFragment)) {
                    ((CommunitiesDetailFragment) mFragment).commentListRefresh(mUserPostForCommunity, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
                }
                mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.MENTOR_SUB_TYPE, AppConstants.ONE_CONSTANT, mUserSolarObject.getIdOfEntityOrParticipant()));
                mFragmentOpen.setCommentList(false);
            } else if (mFragmentOpen.isReactionList()) {
                getSupportFragmentManager().popBackStack();
                mFragmentOpen.setReactionList(false);
            } else if (mFragmentOpen.isOpenImageViewer()) {
                mFragmentOpen.setOpenImageViewer(false);
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                onBackClick();
            }
        } else {
            onBackClick();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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
        mProgressBar.setVisibility(View.GONE);
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
            setProfileNameData(mUserSolarObject);
        }
        mProgressBar.setVisibility(View.GONE);
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
        mFragmentOpen.setOpenImageViewer(true);
        setAllValues(mFragmentOpen);
        super.dataOperationOnClick(baseResponse);
    }

    public void championDetailActivity(Long userId, boolean isMentor) {
        mUserSolarObject = new UserSolrObj();
        mUserSolarObject.setIdOfEntityOrParticipant(userId);
        mUserSolarObject.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        MentorUserProfileActvity.navigateTo(this, mUserSolarObject, userId, true, AppConstants.PROFILE_CHAMPION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    @Override
    public void championProfile(BaseResponse baseResponse, int championValue) {
        if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            championDetailActivity(comment.getParticipantId(), comment.isVerifiedMentor());
        }
    }

    private void shareCardViaSocial() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor(isMentor)
                        .sourceScreenId(SCREEN_LABEL)
                        .isOwnProfile(isOwnProfile)
                        .build();
        trackEvent(Event.PROFILE_SHARED, properties);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        String deepLink = isMentor ? mUserSolarObject.getMentorDeepLinkUrl() : mUserSolarObject.getDeepLinkUrl();
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
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
                    Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        if (fragment instanceof CommunitiesDetailFragment) {
                            ((CommunitiesDetailFragment) fragment).updateUiAccordingToFeedDetail(mUserSolarObject);
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    Fragment activeFragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
                    if (AppUtils.isFragmentUIActive(activeFragment)) {
                        if (activeFragment instanceof MentorQADetailFragment) {
                            ((MentorQADetailFragment) activeFragment).swipeToRefreshList();
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
                            if (mFragment instanceof CommunitiesDetailFragment) {
                                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.DELETE_COMMUNITY_POST);
                            } else {
                                ((MentorQADetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.DELETE_COMMUNITY_POST);

                            }
                        } else {
                            if (mFragment instanceof CommunitiesDetailFragment) {
                                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.COMMENT_REACTION);
                            } else {
                                ((MentorQADetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.COMMENT_REACTION);
                            }
                        }
                    }

                    break;
                default:

                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        }

    }

    private void refreshUserDetails(String name, String location, String userBio, String imageUrl) {
        userName.setText(name);
        tvMentorToolbarName.setText(name);
        tvLoc.setText(location);
        userDescription.setText(userBio);

        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            mProfileIcon.setCircularImage(true);
            mProfileIcon.bindImage(imageUrl);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //LogUtils.info(TAG, "****************offset***" + verticalOffset);
        /*if (verticalOffset >= AppConstants.NO_REACTION_CONSTANT) {
            mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
            mLiHeader.setVisibility(View.INVISIBLE);
        } else {
            mLiHeader.setVisibility(View.VISIBLE);
        }*/

    }

    public void createCommunityPostClick(FeedDetail feedDetail) {
        CommunityPost communityPost = new CommunityPost();
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST, false, null);
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
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public void setUsersFollowingCount(int numFound) {
        followingTitle.setText(getResources().getString(R.string.following));
        mUserSolarObject.setUserFollowing(numFound);
        followingCount.setText(String.valueOf(numFound));
        liFollowing.setVisibility(View.VISIBLE);
    }

    public void setUsersFollowerCount(int numFound) {
        String pluralFollower = getResources().getQuantityString(R.plurals.numberOfFollowers, numFound);
        mUserSolarObject.setSolrIgnoreNoOfMentorFollowers(numFound);
        userFollowerCount.setText(String.valueOf(numericToThousand(numFound)));
        userFollower.setText(pluralFollower);
        liFollower.setVisibility(View.VISIBLE);

    }

    public void setUsersPostCount(int postCount) {
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

            dialog = new Dialog(MentorUserProfileActvity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.unfollow_confirmation_dialog);

            CircleImageView circleImageView = (CircleImageView) dialog.findViewById(R.id.user_img_icon);
            if (StringUtil.isNotNullOrEmptyString(mUserSolarObject.getImageUrl())) {
                circleImageView.setCircularImage(true);
                circleImageView.bindImage(mUserSolarObject.getImageUrl());
            }

            TextView text = (TextView) dialog.findViewById(R.id.title);
            text.setText("Unfollow " + mUserSolarObject.getNameOrTitle());

            TextView dialogButton = (TextView) dialog.findViewById(R.id.cancel);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvMentorDashBoardFollow.setEnabled(true);
                    dialog.dismiss();
                }
            });

            TextView unFollowButton = (TextView) dialog.findViewById(R.id.unfollow);
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

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, String extraImage, boolean isMentor, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);

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
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);

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
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);

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

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, long id, boolean isMentor, int bellNotificationCall, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);

        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        intent.putExtra(AppConstants.CHAMPION_ID, id);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.ASKING_QUESTION, bellNotificationCall);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, boolean isMentor, int bellNotificationCall, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);

        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.ASKING_QUESTION, bellNotificationCall);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, CommunityFeedSolrObj dataItem, long mChampionId, boolean isMentor, int position, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);

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
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);

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
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity,long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.BELL_NOTIFICATION, notificationId);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    public static void navigateTo(Activity fromActivity,long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode,UserSolrObj userSolrObj) {
        Intent intent = new Intent(fromActivity, MentorUserProfileActvity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        Parcelable parcelableMentor = Parcels.wrap(userSolrObj);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.BELL_NOTIFICATION, notificationId);
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
}
