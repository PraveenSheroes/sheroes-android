package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.FollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorDetailItem;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorFollowUnfollowResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.PublicProfileListResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ResizableCustomView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.CommunityEnum.MY_COMMUNITY;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */

public class PublicProfileGrowthBuddiesDetailActivity extends BaseActivity implements CommentReactionFragment.HomeActivityIntractionListner, HomeView {
    private final String TAG = LogUtils.makeLogTag(PublicProfileGrowthBuddiesDetailActivity.class);
    @Bind(R.id.iv_public_profile_full_view_icon)
    RoundedImageView mProfileIcon;
    @Bind(R.id.app_bar_public_profile)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.view_pager_public_profile)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_public_profile)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_public_profile)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tab_public_profile)
    public TabLayout mTabLayout;
    @Bind(R.id.tv_mentor_name)
    TextView mTvMentorName;
    @Bind(R.id.tv_mentor_expertise)
    TextView tvMentorExpertise;
    @Bind(R.id.tv_mentor_city_name)
    TextView tvMentorCityName;
    @Bind(R.id.tv_web_link)
    TextView tvMentorWebLink;
    @Bind(R.id.tv_mentor_description)
    TextView tvMentorDescription;
    @Bind(R.id.tv_followers_count)
    TextView tvFollowersCount;
    @Bind(R.id.tv_follower_lable)
    TextView tvFollowersLable;
    @Bind(R.id.tv_post_count)
    public TextView tvPostCount;
    @Bind(R.id.tv_post_lable)
    public TextView tvPostCountLable;
    @Bind(R.id.view_line1)
    public View viewLine1;
    @Bind(R.id.view_line2)
    public View viewLine2;
    @Bind(R.id.iv_public_profile_image)
    ImageView ivPublicProfileImage;
    @Bind(R.id.tv_follow_unfollow_public_profile)
    TextView tvFollowUnfollowPublicProfile;
    @Inject
    Preference<LoginResponse> mUserPreference;
    ViewPagerAdapter mViewPagerAdapter;
    private CommunityEnum communityEnum = MY_COMMUNITY;
    private long mCommunityPostId = 1;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    private Fragment mFragment;
    private FeedDetail mMyCommunityPostFeedDetail;
    private MentorDetailItem mMentorDetailItem;
    @Bind(R.id.pb_mentor_detail_progress_bar)
    ProgressBar mProgressBar;
    @Inject
    AppUtils mAppUtils;
    @Inject
    HomePresenter mHomePresenter;
    private String mViewMoreDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.public_profile_growth_buddies_activity);
        mHomePresenter.attachView(this);
        ButterKnife.bind(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mFeedDetail = getIntent().getParcelableExtra(AppConstants.COMMUNITY_DETAIL);
            mMentorDetailItem = getIntent().getParcelableExtra(AppConstants.GROWTH_PUBLIC_PROFILE);
        }
        if (null != mFeedDetail) {
            mFragmentOpen = new FragmentOpen();
            setAllValues(mFragmentOpen);
            mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.USER_SUB_TYPE, AppConstants.ONE_CONSTANT, mFeedDetail.getIdOfEntityOrParticipant()));
            setPagerAndLayouts();
            mHomePresenter.getCountOfFollowerFromPresenter(mAppUtils.countFollowerRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant()));
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    public void setProfileNameData() {
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getImageUrl())) {
            Glide.with(this)
                    .load(mFeedDetail.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(mProfileIcon);
            Glide.with(this)
                    .load(mFeedDetail.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivPublicProfileImage);
        }
        if (null != mMentorDetailItem) {
            isFollowUnfollow();
        } else {
            mHomePresenter.getFollowedFromPresenter(mAppUtils.countFollowerRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant()));
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            mTvMentorName.setText(mFeedDetail.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCityName())) {
            tvMentorCityName.setText(mFeedDetail.getCityName());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDescription())) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvMentorDescription.setText(Html.fromHtml(mFeedDetail.getDescription(), 0)); // for 24 api and more
            } else {
                tvMentorDescription.setText(Html.fromHtml(mFeedDetail.getDescription()));// or for older api
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\"").append(tvMentorDescription.getText().toString()).append("\"");
            mViewMoreDescription = stringBuilder.toString();
            tvMentorDescription.setText(mViewMoreDescription);
            ResizableCustomView.doResizeTextView(tvMentorDescription, 2, AppConstants.VIEW_MORE, true);
        }
        if (StringUtil.isNotEmptyCollection(mFeedDetail.getCanHelpIns())) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : mFeedDetail.getCanHelpIns()) {
                stringBuilder.append(str).append(AppConstants.COMMA).append(AppConstants.SPACE);
                tvMentorExpertise.setText(stringBuilder.toString().substring(0, stringBuilder.toString().trim().length() - 1));
            }
        }
    }


    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbar);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(mFeedDetail, communityEnum, mCommunityPostId), getString(R.string.ID_GROWTH_BUDDIES_PUBLIC_PROFILE));
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.iv_back_public_profile)
    public void onBackClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, mMentorDetailItem);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.tv_follow_unfollow_public_profile)
    public void onFollowUnfollowClick() {
        if (null != mMentorDetailItem) {
            tvFollowUnfollowPublicProfile.setEnabled(false);
            followUnfollowRequest(mMentorDetailItem);
            if (!mMentorDetailItem.isFollowed()) {
                mMentorDetailItem.setFollowed(true);
            } else {
                mMentorDetailItem.setFollowed(false);
            }
            isFollowUnfollow();
        }
    }

    private void isFollowUnfollow() {
        if (mMentorDetailItem.isFollowed()) {
            tvFollowUnfollowPublicProfile.setTextColor(ContextCompat.getColor(this, R.color.growth_followed));
            tvFollowUnfollowPublicProfile.setText(getString(R.string.ID_GROWTH_BUDDIES_FOLLOWING));
            tvFollowUnfollowPublicProfile.setBackgroundResource(R.drawable.rectangle_growth_followed);
        } else {
            tvFollowUnfollowPublicProfile.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvFollowUnfollowPublicProfile.setText(getString(R.string.ID_GROWTH_BUDDIES_UNFOLLOW));
            tvFollowUnfollowPublicProfile.setBackgroundResource(R.drawable.rectangle_follow_unfollow);
        }
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        int id = view.getId();
        if (baseResponse instanceof FeedDetail) {
            communityDetailHandled(view, baseResponse);
        } else if (baseResponse instanceof CommentReactionDoc) {
            setAllValues(mFragmentOpen);
             /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU);
        }
    }

    private void communityDetailHandled(View view, BaseResponse baseResponse) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        mMyCommunityPostFeedDetail = feedDetail;
        int id = view.getId();
        mFragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
        setFragment(mFragment);
        mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FOURTH_CONSTANT);
        mFragmentOpen.setOwner(mFeedDetail.isOwner());
        setAllValues(mFragmentOpen);
        super.feedCardsHandled(view, baseResponse);
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        mFragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof CommunitiesDetailFragment) {
                ((CommunitiesDetailFragment) mFragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        }
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mMyCommunityPostFeedDetail = feedDetail;
        onBackPressed();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mMyCommunityPostFeedDetail = feedDetail;
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FOURTH_CONSTANT);
            setAllValues(mFragmentOpen);
            super.openCommentReactionFragment(mMyCommunityPostFeedDetail);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            if (AppUtils.isFragmentUIActive(mFragment)) {
                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mMyCommunityPostFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
            mFragmentOpen.setCommentList(false);
        } else if (mFragmentOpen.isReactionList()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
        } else if (mFragmentOpen.isOpenImageViewer()) {
            mFragmentOpen.setOpenImageViewer(false);
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            onBackClick();
        }
    }

    public void followUnfollowRequest(MentorDetailItem mentorDetailItem) {
        mMentorDetailItem = mentorDetailItem;
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(mentorDetailItem.getEntityOrParticipantId());
        if (mMentorDetailItem.isFollowed()) {
            mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest);
        } else {
            mHomePresenter.getFollowFromPresenter(publicProfileListRequest);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomePresenter.detachView();
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
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
            mFeedDetail = feedDetailList.get(0);
            setProfileNameData();
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        if (baseResponse instanceof MentorFollowUnfollowResponse) {
            MentorFollowUnfollowResponse mentorFollowUnfollowResponse = (MentorFollowUnfollowResponse) baseResponse;
            tvFollowUnfollowPublicProfile.setEnabled(true);
            mentorFollowUnFollowSuccess(mentorFollowUnfollowResponse);
        } else if (baseResponse instanceof PublicProfileListResponse) {
            PublicProfileListResponse publicProfileListResponse = (PublicProfileListResponse) baseResponse;
            countFollowerSuccess(publicProfileListResponse);
        } else if (baseResponse instanceof FollowedResponse) {
            FollowedResponse followedResponse = (FollowedResponse) baseResponse;
            followedSuccess(followedResponse);
        }
    }

    private void followedSuccess(FollowedResponse followedResponse) {
        switch (followedResponse.getStatus()) {
            case AppConstants.SUCCESS:
                MentorDetailItem mentorDetailItem = new MentorDetailItem();
                mentorDetailItem.setEntityOrParticipantId(mFeedDetail.getIdOfEntityOrParticipant());
                mentorDetailItem.setFollowed(followedResponse.isFollowed());
                mMentorDetailItem = mentorDetailItem;
                isFollowUnfollow();
                break;
            case AppConstants.FAILED:

                break;
            default:
        }

    }

    private void countFollowerSuccess(PublicProfileListResponse publicProfileListResponse) {
        switch (publicProfileListResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (publicProfileListResponse.getNumFound() > 0) {
                    viewLine1.setVisibility(View.VISIBLE);
                    viewLine2.setVisibility(View.VISIBLE);
                    tvFollowersLable.setVisibility(View.VISIBLE);
                    tvFollowersCount.setVisibility(View.VISIBLE);
                    tvFollowersCount.setText(String.valueOf(publicProfileListResponse.getNumFound()));
                } else {
                    tvFollowersLable.setVisibility(View.GONE);
                    tvFollowersCount.setVisibility(View.GONE);
                }
                break;
            case AppConstants.FAILED:
                tvFollowersLable.setVisibility(View.GONE);
                tvFollowersCount.setVisibility(View.GONE);
                break;
            default:
        }

    }

    protected void mentorFollowUnFollowSuccess(MentorFollowUnfollowResponse mentorFollowUnfollowResponse) {
        switch (mentorFollowUnfollowResponse.getStatus()) {
            case AppConstants.SUCCESS:
                mHomePresenter.getCountOfFollowerFromPresenter(mAppUtils.countFollowerRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant()));
                break;
            case AppConstants.FAILED:
                if (!mMentorDetailItem.isFollowed()) {
                    mMentorDetailItem.setFollowed(true);
                } else {
                    mMentorDetailItem.setFollowed(false);
                }
                isFollowUnfollow();
                showError(mentorFollowUnfollowResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), FOLLOW_UNFOLLOW);
                break;
            default:
                showError(getString(R.string.ID_GENERIC_ERROR), FOLLOW_UNFOLLOW);
        }

    }


    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

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
}
