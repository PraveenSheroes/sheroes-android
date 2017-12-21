package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MentorQADetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.MentorProfileDetailFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.CommunityEnum.MY_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */

public class MentorUserProfileDashboardActivity extends BaseActivity implements CommentReactionFragment.HomeActivityIntractionListner, HomeView, AppBarLayout.OnOffsetChangedListener, ViewPager.OnPageChangeListener {
    private final String TAG = LogUtils.makeLogTag(MentorUserProfileDashboardActivity.class);
    private static final String SCREEN_LABEL = "Public Profile Growth Screen";
    @Bind(R.id.iv_mentor_full_view_icon)
    CircleImageView mProfileIcon;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.view_pager_mentor)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_mentor)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_mentor)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tab_mentor)
    public TabLayout mTabLayout;
    @Bind(R.id.tv_mentor_name)
    TextView mTvMentorName;
    @Bind(R.id.iv_public_profile_image)
    ImageView ivPublicProfileImage;
    @Bind(R.id.tv_profession)
    TextView tvProfession;
    @Bind(R.id.tv_loc)
    TextView tvLoc;
    @Bind(R.id.tv_mentor_description)
    TextView tvMentorDescription;
    @Bind(R.id.cl_home_footer_list)
    public CardView clHomeFooterList;
    @Bind(R.id.li_post)
    LinearLayout liPost;
    @Bind(R.id.tv_mentor_post_count)
    TextView tvMentorPostCount;
    @Bind(R.id.tv_mentor_post)
    TextView tvMentorPost;

    @Bind(R.id.li_follower)
    LinearLayout liFollower;
    @Bind(R.id.tv_mentor_follower_count)
    TextView tvMentorFollowerCount;
    @Bind(R.id.tv_mentor_follower)
    TextView tvMentorFollower;

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

    @Bind(R.id.tv_mentor_ask_question)
    TextView tvMentorAskQuestion;

    @Bind(R.id.view_footer)
    View viewFooter;
    @Inject
    Preference<LoginResponse> mUserPreference;
    ViewPagerAdapter mViewPagerAdapter;
    private CommunityEnum communityEnum = MY_COMMUNITY;
    private long mCommunityPostId = 1;
    private FragmentOpen mFragmentOpen;
    private Fragment mFragment;
    private UserPostSolrObj mUserPostForCommunity;
    private UserSolrObj mMentorUserItem;
    private int itemPosition;
    @Inject
    AppUtils mAppUtils;
    @Inject
    HomePresenter mHomePresenter;
    private String screenName = AppConstants.GROWTH_PUBLIC_PROFILE;
    private Long mChampionId;
    private int mFromNotification;
    private FeedDetail mFeedDetail;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private int askingQuestionCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.mentor_user_dashboard_layout);
        mHomePresenter.attachView(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mAppBarLayout.addOnOffsetChangedListener(this);
        mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mMentorUserItem = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.GROWTH_PUBLIC_PROFILE));
            mFeedDetail = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.MENTOR_DETAIL));
            mFromNotification = getIntent().getExtras().getInt(AppConstants.BELL_NOTIFICATION);
            askingQuestionCode= getIntent().getExtras().getInt(AppConstants.ASKING_QUESTION);
            mChampionId = getIntent().getExtras().getLong(AppConstants.CHAMPION_ID);
            if(null!=mMentorUserItem) {
                itemPosition = mMentorUserItem.getItemPosition();
            }
            if (mChampionId > 0) {
                mMentorUserItem = new UserSolrObj();
                mMentorUserItem.setEntityOrParticipantId(mChampionId);
                mMentorUserItem.setSolrIgnoreMentorCommunityId(mChampionId);
                mMentorUserItem.setIdOfEntityOrParticipant(mChampionId);
                mFeedDetail = mMentorUserItem;
            }
        }
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.MENTOR_SUB_TYPE, AppConstants.ONE_CONSTANT, mMentorUserItem.getIdOfEntityOrParticipant()));
        setPagerAndLayouts();
        ((SheroesApplication) getApplication()).trackScreenView(AppConstants.PUBLIC_PROFILE);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    public void setProfileNameData() {
        if(StringUtil.isNotNullOrEmptyString(mMentorUserItem.getCityName())) {
            tvLoc.setText(mMentorUserItem.getCityName());
            tvLoc.setVisibility(View.VISIBLE);
        }else
        {
            tvLoc.setVisibility(View.GONE);
        }
        if(StringUtil.isNotEmptyCollection(mMentorUserItem.getCanHelpIns())) {
            tvProfession.setText(mMentorUserItem.getCanHelpIns().get(0));
            tvProfession.setVisibility(View.VISIBLE);
        }else
        {
            tvProfession.setVisibility(View.GONE);
        }
        if(StringUtil.isNotNullOrEmptyString(mMentorUserItem.getListDescription())) {
            Spanned description = StringUtil.fromHtml(mMentorUserItem.getListDescription());
            tvMentorDescription.setText(description);
        }
        if(StringUtil.isNotNullOrEmptyString(mMentorUserItem.getNameOrTitle())) {
            mTvMentorName.setText(mMentorUserItem.getNameOrTitle());
        }

        if (StringUtil.isNotNullOrEmptyString(mMentorUserItem.getImageUrl())) {
            mProfileIcon.setCircularImage(true);
            mProfileIcon.bindImage(mMentorUserItem.getImageUrl());
            Glide.with(this)
                    .load(mMentorUserItem.getImageUrl())
                    .into(ivPublicProfileImage);

        }
        if (StringUtil.isNotNullOrEmptyString(mMentorUserItem.getNameOrTitle())) {
            mTvMentorName.setText(mMentorUserItem.getNameOrTitle());
            tvMentorToolbarName.setText(mMentorUserItem.getNameOrTitle());
        }

        if (mMentorUserItem.getSolrIgnoreNoOfMentorPosts() > 0) {
            String pluralAnswer = getResources().getQuantityString(R.plurals.numberOfPosts, mMentorUserItem.getSolrIgnoreNoOfMentorAnswers());
            tvMentorPostCount.setText(String.valueOf(numericToThousand(mMentorUserItem.getSolrIgnoreNoOfMentorPosts())));
            tvMentorPost.setText(pluralAnswer);
            liPost.setVisibility(View.VISIBLE);
        } else {
            liPost.setVisibility(View.GONE);
        }
        if (mMentorUserItem.getSolrIgnoreNoOfMentorAnswers() > 0) {
            String pluralAnswer = getResources().getQuantityString(R.plurals.numberOfAnswers, mMentorUserItem.getSolrIgnoreNoOfMentorAnswers());
            tvMentorAnswerCount.setText(String.valueOf(numericToThousand(mMentorUserItem.getSolrIgnoreNoOfMentorAnswers())));
            tvMentorAnswer.setText(pluralAnswer);
            liAnswer.setVisibility(View.VISIBLE);
        } else {
            liAnswer.setVisibility(View.GONE);
        }
        if (mMentorUserItem.getSolrIgnoreNoOfMentorFollowers() > 0) {
            String pluralFollower = getResources().getQuantityString(R.plurals.numberOfFollowers, mMentorUserItem.getSolrIgnoreNoOfMentorFollowers());
            tvMentorFollowerCount.setText(String.valueOf(numericToThousand(mMentorUserItem.getSolrIgnoreNoOfMentorFollowers())));
            tvMentorFollower.setText(pluralFollower);
            liFollower.setVisibility(View.VISIBLE);
        } else {
            liFollower.setVisibility(View.GONE);
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            if (mUserPreference.get().getUserSummary().getUserId() == mMentorUserItem.getEntityOrParticipantId()) {
                tvMentorDashBoardFollow.setTextColor(ContextCompat.getColor(this, R.color.footer_icon_text));
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

        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_PUBLIC_PROFILE));
    }

    private void followUnFollowMentor() {
        if (mMentorUserItem.getSolrIgnoreNoOfMentorFollowers() > 0) {
            String pluralFollower = getResources().getQuantityString(R.plurals.numberOfFollowers, mMentorUserItem.getSolrIgnoreNoOfMentorFollowers());
            tvMentorFollowerCount.setText(String.valueOf(numericToThousand(mMentorUserItem.getSolrIgnoreNoOfMentorFollowers())));
            tvMentorFollower.setText(pluralFollower);
            liFollower.setVisibility(View.VISIBLE);
        } else {
            liFollower.setVisibility(View.GONE);
        }
        if (mMentorUserItem.isSolrIgnoreIsMentorFollowed()) {
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
        //  mViewPagerAdapter.addFragment(MentorProfileDetailFragment.createInstance(), getString(R.string.ID_PROFILE));
        mViewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId), getString(R.string.ID_MENTOR_POST));
        mViewPagerAdapter.addFragment(MentorQADetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId), getString(R.string.ID_MENTOR_Q_A));
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        if(askingQuestionCode==AppConstants.ASKING_QUESTION_CALL)
        {
            mViewPager.setCurrentItem(AppConstants.ONE_CONSTANT);
            mAppBarLayout.setExpanded(false);
        }
    }

    @OnClick(R.id.tv_mentor_see_insight)
    public void mentorSeeInsightClick() {
         Intent intent = new Intent(this, MentorInsightActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }
    @OnClick(R.id.tv_mentor_ask_question)
    public void mentorAskQuestionClick() {
        if(tvMentorAskQuestion.getText().toString().equalsIgnoreCase(getString(R.string.ID_ANSWER_QUESTION))) {
            mViewPager.setCurrentItem(AppConstants.ONE_CONSTANT);
            mAppBarLayout.setExpanded(false);
        }else {
            if (null != mMentorUserItem) {
                CommunityPost mentorPost = new CommunityPost();
                mentorPost.community = new Community();
                mentorPost.community.id = mMentorUserItem.getSolrIgnoreMentorCommunityId();
                mentorPost.community.name = mMentorUserItem.getNameOrTitle();
                mentorPost.createPostRequestFrom = AppConstants.MENTOR_CREATE_QUESTION;
                mentorPost.isEdit = false;
                CommunityPostActivity.navigateTo(this, mentorPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false);
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
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
                String profile = mUserPreference.get().getUserSummary().getPhotoUrl();
                Intent intent = new Intent(this, ProfileActicity.class);
                intent.putExtra(AppConstants.EXTRA_IMAGE, profile);
                startActivity(intent);
            }
        } else {
            tvMentorDashBoardFollow.setEnabled(false);
            PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
            publicProfileListRequest.setIdOfEntityParticipant(mMentorUserItem.getEntityOrParticipantId());
            if (mMentorUserItem.isSolrIgnoreIsMentorFollowed()) {
                mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest,mMentorUserItem);
            } else {
                mHomePresenter.getFollowFromPresenter(publicProfileListRequest,mMentorUserItem);
            }
            if (mMentorUserItem.isSolrIgnoreIsMentorFollowed()) {
                mMentorUserItem.setSolrIgnoreIsMentorFollowed(false);
            } else {
                mMentorUserItem.setSolrIgnoreIsMentorFollowed(true);
            }
            followUnFollowMentor();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, position);
        if (fragment instanceof MentorProfileDetailFragment) {
            if (AppUtils.isFragmentUIActive(fragment)) {
                // ((AllSearchFragment) fragment).setEditText(mSearchEditText.getText().toString());
            }
        } else if (fragment instanceof CommunitiesDetailFragment) {

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
        mMentorUserItem.currentItemPosition=itemPosition;
        Parcelable parcelableMentorDetail = Parcels.wrap(mMentorUserItem);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentorDetail);
        Parcelable parcelable = Parcels.wrap(mMentorUserItem);
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
        mFragment = mViewPagerAdapter.getActiveFragment(mViewPager,mViewPager.getCurrentItem());
        setFragment(mFragment);
        mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FOURTH_CONSTANT);
        mFragmentOpen.setOwner(userPostSolrObj.isCommunityOwner());
        setAllValues(mFragmentOpen);
        super.feedCardsHandled(view, baseResponse);
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mUserPostForCommunity = (UserPostSolrObj) feedDetail;
        onBackPressed();
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        if (mFragmentOpen.isCommentList()) {
            CommentReactionFragment commentReactionFragment = (CommentReactionFragment) getSupportFragmentManager().findFragmentByTag(CommentReactionFragment.class.getName());
            if (commentReactionFragment != null) {
                commentReactionFragment.likeAndUnlikeRequest(baseResponse, reactionValue, position);
            } else {
                mFragmentOpen.setCommentList(false);
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (fragment != null && fragment instanceof CommunitiesDetailFragment)
                    ((CommunitiesDetailFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        } else {
            Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
            if (fragment instanceof CommunitiesDetailFragment)
                ((CommunitiesDetailFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
        }
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mUserPostForCommunity = (UserPostSolrObj) feedDetail;
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FOURTH_CONSTANT);
            setAllValues(mFragmentOpen);
            super.openCommentReactionFragment(mUserPostForCommunity);
        }
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
        if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            if (AppUtils.isFragmentUIActive(mFragment)) {
                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mUserPostForCommunity, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
            mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.MENTOR_SUB_TYPE, AppConstants.ONE_CONSTANT, mMentorUserItem.getIdOfEntityOrParticipant()));
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
            mMentorUserItem = (UserSolrObj) feedDetailList.get(0);
            mMentorUserItem.setCallFromName(screenName);
            setProfileNameData();
            clHomeFooterList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showHomeFeedList(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum)
        {
            case FOLLOW_UNFOLLOW:
                tvMentorDashBoardFollow.setEnabled(true);
                UserSolrObj userSolrObj=(UserSolrObj)baseResponse;
               /* if(tvMentorDashBoardFollow.getText().toString().equalsIgnoreCase(getString(R.string.ID_GROWTH_BUDDIES_FOLLOWING)))
                {
                    if(!userSolrObj.isSolrIgnoreIsMentorFollowed())
                    {
                            mMentorUserItem.setSolrIgnoreNoOfMentorFollowers(mMentorUserItem.getSolrIgnoreNoOfMentorFollowers()-1);
                    }
                }else
                {
                    if(userSolrObj.isSolrIgnoreIsMentorFollowed())
                    {
                        mMentorUserItem.setSolrIgnoreNoOfMentorFollowers(mMentorUserItem.getSolrIgnoreNoOfMentorFollowers()+1);

                    }
                }*/

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

    private void championDetailActivity(Long userId) {
        Intent intent = new Intent(this, MentorUserProfileDashboardActivity.class);
        Bundle bundle = new Bundle();
        mMentorUserItem = new UserSolrObj();
        mMentorUserItem.setIdOfEntityOrParticipant(userId);
        mMentorUserItem.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        Parcelable parcelable = Parcels.wrap(mMentorUserItem);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, null);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public void championProfile(BaseResponse baseResponse, int championValue) {
        if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            championDetailActivity(comment.getParticipantId());
        }
    }
    private void shareCardViaSocial() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mMentorUserItem.getMentorDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST:
                    Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        if (fragment instanceof CommunitiesDetailFragment) {
                            ((CommunitiesDetailFragment) fragment).updateUiAccordingToFeedDetail(mMentorUserItem);
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
                            if(mFragment instanceof CommunitiesDetailFragment) {
                                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.DELETE_COMMUNITY_POST);
                            }else
                            {
                                ((MentorQADetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.DELETE_COMMUNITY_POST);

                            }
                        } else {
                            if(mFragment instanceof CommunitiesDetailFragment) {
                                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mFeedDetail, FeedParticipationEnum.COMMENT_REACTION);
                            }else
                            {
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.info(TAG, "****************offset***" + verticalOffset);
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
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST, false);
    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
