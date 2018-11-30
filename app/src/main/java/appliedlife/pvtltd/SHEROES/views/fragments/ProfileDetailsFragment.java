package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ChampionListingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.FollowingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileCommunitiesActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleViewLinear;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IProfileView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;
import static butterknife.ButterKnife.findById;

/**
 * Created by Ravi on 31/12/17.
 * Profile Details - Contain Followed Champions and Communities
 */

public class ProfileDetailsFragment extends BaseFragment implements IProfileView {

    public static final String USER_MENTOR_ID = "USERID";
    public static final String USER_MENTOR_NAME = "USER_NAME";
    public static final String SELF_PROFILE = "SELF_PROFILE";
    //region constants
    private static final String SCREEN_LABEL = "Profile Details Screen";
    //endregion constants
    @BindDimen(R.dimen.dp_size_12)
    public int mImageMargin;
    @BindDimen(R.dimen.dp_size_4)
    public int mDefaultSize;
    @Bind(R.id.user_communities)
    GridLayout mUserCommunityLayout;
    @Bind(R.id.extra_space)
    TextView mSpacing;
    @Bind(R.id.mutual_community_container)
    LinearLayout mMutualCommunityContainer;
    @Bind(R.id.container)
    LinearLayout mFollowedMentor;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.mutual_community_label)
    TextView mMutualCommunityLabel;
    @Bind(R.id.followed_mentors)
    LinearLayout mFollowedChampionContainer;
    @Bind(R.id.empty_mentor_view)
    TextView mEmptyViewFollowedChampion;
    @Bind(R.id.empty_mentor_view_container)
    LinearLayout mEmptyFollowedMentorContainer;
    @Bind(R.id.community_view_container)
    LinearLayout mCommunityListContainer;
    @Bind(R.id.empty_community_view)
    TextView mEmptyViewCommunities;
    @Bind(R.id.dotted_border_container_community)
    FrameLayout mCommunityEmptyView;
    @Bind(R.id.empty_community_view_container)
    LinearLayout mEmptyViewCommunitiesContainer;
    @Bind(R.id.dotted_border_container)
    FrameLayout mEmptyViewDottedBorder;
    @Bind(R.id.progress_bar_champion)
    ProgressBar mProgressBarChampion;
    @Bind(R.id.progress_bar_community)
    ProgressBar mProgressBarCommunity;
    @BindDimen(R.dimen.dp_size_65)
    int mProfileSize;
    //endregion bind variable

    //region injected variable
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @Inject
    ProfilePresenterImpl mProfilePresenter;
    //endregion injected variable

    //region private variable
    private long mUserId;
    private boolean mIsSelfProfile, mIsFragmentVisible;
    private List<CommunityFeedSolrObj> mCommunities;
    private List<UserSolrObj> mFollowedChampions;
    private FragmentManager manager;
    private ProfileFragment fragment;
    //endregion private variable

    //region fragment lifecycle methods
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.profile_community_champion_layout, container, false);
        mProfilePresenter.attachView(this);
        ButterKnife.bind(this, view);
         manager = getActivity().getSupportFragmentManager();
         fragment =(ProfileFragment) manager.findFragmentById(R.id.fl_article_card_view);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(USER_MENTOR_ID)) {
            mUserId = bundle.getLong(USER_MENTOR_ID);
        }

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserBO()) {
            if (mUserPreference.get().getUserSummary().getUserId() == mUserId) {
                mIsSelfProfile = true;
            }
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateUserProfileDetails();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mProfilePresenter.detachView();
    }
    //endregion fragment lifecycle methods

    //region inherited methods
    @Override
    protected SheroesPresenter getPresenter() {
        return mProfilePresenter;
    }

    @Override
    public void getFollowedMentors(FollowedUsersResponse feedResponsePojo) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (feedResponsePojo.getNumFound() == 0) {
            //empty view
            mEmptyFollowedMentorContainer.setVisibility(View.VISIBLE);
            String name = "User";
            if (getActivity() != null && !getActivity().isFinishing()) {
                if (getActivity() instanceof ProfileActivity) {
                    name = ((ProfileActivity) getActivity()).getUserNameTitle() == null ? "User" : ((ProfileActivity) getActivity()).getUserNameTitle();
                } else {
                    name = fragment.getUserNameTitle() == null ? "User" : fragment.getUserNameTitle();
                }
            }
            String message = getString(R.string.empty_followed_mentor, name);
            mEmptyViewFollowedChampion.setText(message);
            mEmptyViewFollowedChampion.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.vector_public_business_woman, 0, 0);

            mFollowedChampionContainer.setVisibility(View.GONE);

            if (mIsSelfProfile) {
                mEmptyViewDottedBorder.setBackgroundResource(R.drawable.dotted_line_border);
                mEmptyViewFollowedChampion.setText(R.string.champions_followed);
                mEmptyViewDottedBorder.setVisibility(View.VISIBLE);
            } else {
                mEmptyViewDottedBorder.setBackgroundResource(0);
            }

        } else {
            mEmptyFollowedMentorContainer.setVisibility(View.GONE);
            mEmptyViewDottedBorder.setVisibility(View.GONE);
            mFollowedChampionContainer.setVisibility(View.VISIBLE);

            List<UserSolrObj> feedDetailList = feedResponsePojo.getFeedDetails();
            if (StringUtil.isNotEmptyCollection(feedDetailList)) {
                populateFollowedMentors(feedDetailList);  //followed mentor
                mFollowedChampions = feedDetailList;
            }
        }
        mProgressBarChampion.setVisibility(View.GONE);
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
        List<CommunityFeedSolrObj> mutualCommunity = userCommunities.getMutualCommunities();
        List<CommunityFeedSolrObj> otherCommunity = userCommunities.getOtherCommunities();
        if (!mIsSelfProfile) {
            if (StringUtil.isNotEmptyCollection(mutualCommunity)) {
                CommunityFeedSolrObj profileCommunity = mutualCommunity.get(0);
                profileCommunity.setMutualCommunityCount(mutualCommunity.size());
                profileCommunity.setMutualCommunityFirstItem(true);
                profileCommunity.setShowHeader(true);
                populateMutualCommunities(mutualCommunity); // for mutual community
                mMutualCommunityContainer.setVisibility(View.VISIBLE);
                mMutualCommunityLabel.setVisibility(View.VISIBLE);
                mSpacing.setVisibility(View.GONE);
            } else {
                mSpacing.setVisibility(View.GONE);
                mMutualCommunityContainer.setVisibility(View.GONE);
                mMutualCommunityLabel.setVisibility(View.GONE);
            }
        } else {
            mSpacing.setVisibility(View.GONE);
            mMutualCommunityContainer.setVisibility(View.GONE);
            mMutualCommunityLabel.setVisibility(View.GONE);
        }

        if (StringUtil.isNotEmptyCollection(otherCommunity)) {
            mEmptyViewCommunitiesContainer.setVisibility(View.GONE);
            mCommunityListContainer.setVisibility(View.VISIBLE);
            CommunityFeedSolrObj profileCommunity = otherCommunity.get(0);
            profileCommunity.setShowHeader(true);

            if (mIsSelfProfile) {
                profileCommunity.setMutualCommunityCount(0);
            } else if (mutualCommunity != null && mutualCommunity.size() > 0) {
                profileCommunity.setMutualCommunityCount(mutualCommunity.size());
            }
            profileCommunity.setOtherCommunityFirstItem(true);
            populateUserCommunity(otherCommunity); //for other community

            if (mutualCommunity != null && !mIsSelfProfile) { //other have both mutual and non mutual so making single lust here
                mutualCommunity.addAll(otherCommunity);
                mCommunities = mutualCommunity;
            } else {
                mCommunities = otherCommunity;
            }
        } else {
            //current scenrio - other hv all so change if future other don't hv mutual - empty view
            mEmptyViewCommunitiesContainer.setVisibility(View.VISIBLE);
            mCommunityListContainer.setVisibility(View.GONE);
            if (getActivity() == null) {
                return;
            }
            String name;
            if (getActivity() instanceof ProfileActivity) {
                name = ((ProfileActivity) getActivity()).getUserNameTitle() == null ? "User" : fragment.getUserNameTitle();
            } else {
                name = fragment.getUserNameTitle() == null ? "User" : fragment.getUserNameTitle();
            }
            String message = getString(R.string.empty_followed_community, name);
            mEmptyViewCommunities.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.vector_community_member_public, 0, 0);
            mEmptyViewCommunities.setText(message);

            if (mIsSelfProfile) {
                mCommunityEmptyView.setBackgroundResource(R.drawable.dotted_line_border);
                mEmptyViewCommunities.setText(R.string.join_communities);
            } else {
                mCommunityEmptyView.setBackgroundResource(0);
            }
        }
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse communityFeedSolrObj) {

    }

    @Override
    public void onUserDeactivation(BaseResponse baseResponse, boolean isDeactivation) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsFragmentVisible = true;
            AnalyticsManager.timeScreenView(getScreenName());
        } else {
            if (mIsFragmentVisible) {
                mIsFragmentVisible = false;
                AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
            }
        }
    }

    protected boolean trackScreenTime() {
        return false;
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        return new EventProperty.Builder()
                .id(Long.toString(mUserId))
                .isMentor(false)
                .isOwnProfile(mIsSelfProfile)
                .build();
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

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
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }
    //endregion inherited methods

    //region onclick methods
    @OnClick(R.id.dotted_border_container_community)
    public void openCommunityList() {
        if (mIsSelfProfile) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT, "Community List");
            startActivity(intent);
        }
    }

    @OnClick(R.id.dotted_border_container)
    public void openChampionList() {
        if (mIsSelfProfile) {
            Intent intent = new Intent(getActivity(), ChampionListingActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.followed_view_more)
    public void navigateToFollowedMentors() {
        if (StringUtil.isNotEmptyCollection(mFollowedChampions)) {
            FollowingActivity.navigateTo(getActivity(), mUserId, mIsSelfProfile, getScreenName(), FollowingEnum.FOLLOWED_CHAMPIONS, null);
        }
    }

    @OnClick(R.id.community_view_more)
    public void navigateToCommunityListing() {
        if (StringUtil.isNotEmptyCollection(mCommunities)) {
            ProfileCommunitiesActivity.navigateTo(getActivity(), mUserId, mIsSelfProfile, getScreenName(), null);
        }
    }
    //endregion onclick methods

    //region private methods
    private void populateUserProfileDetails() {
        mProfilePresenter.getFollowedMentors(mAppUtils.followerFollowingRequest(1, mUserId, FollowingEnum.FOLLOWED_CHAMPIONS.name()));

        if (mIsSelfProfile) {
            mProfilePresenter.getPublicProfileCommunity(mAppUtils.userCommunitiesRequestBuilder(1, mUserId));
        } else {
            mProfilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(1, mUserId));

        }
    }

    private void populateMutualCommunities(List<CommunityFeedSolrObj> communities) {

        int mutualCommunitySize = communities.size();
        if ((getActivity()) == null || getActivity().isFinishing()) return;

        String name = ((ProfileActivity) getActivity()).getUserNameTitle() == null ? "User" : ((ProfileActivity) getActivity()).getUserNameTitle();
        String mutualCommunityText = getResources().getString(R.string.PLACEHOLDER_MUTUAL_COMMUNITY, name, String.valueOf(mutualCommunitySize));
        mMutualCommunityLabel.setText(mutualCommunityText);

        mMutualCommunityContainer.removeAllViews();

        int counter = 0;
        for (final CommunityFeedSolrObj community : communities) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_mutual_community, null);
            CircleImageView mutualCommunityImage = findById(view, R.id.mutual_community_icon);
            if (StringUtil.isNotNullOrEmptyString(community.getThumbnailImageUrl())) {
                mutualCommunityImage.setCircularImage(true);
                String authorThumborUrl = CommonUtil.getThumborUri(community.getThumbnailImageUrl(), mProfileSize, mProfileSize);
                mutualCommunityImage.bindImage(authorThumborUrl);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCommunityDetails(community);
                }
            });

            mMutualCommunityContainer.addView(view);
            counter++;
            if (counter == 3) break;
        }

        //add the listener to mutual community
        if (mutualCommunitySize > 3) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView remainingMutualCount = new TextView(getContext());
            remainingMutualCount.setTextColor(getResources().getColor(R.color.gray_light));
            remainingMutualCount.setBackgroundResource(R.drawable.circular_background);
            lparams.setMargins(mDefaultSize, mDefaultSize, mDefaultSize, mDefaultSize);
            remainingMutualCount.setLayoutParams(lparams);
            remainingMutualCount.setGravity(Gravity.CENTER);
            remainingMutualCount.setTypeface(remainingMutualCount.getTypeface(), Typeface.BOLD);
            int finalLeft = mutualCommunitySize - 3;
            remainingMutualCount.setText("+" + finalLeft);

            remainingMutualCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToCommunityListing();
                }
            });
            mMutualCommunityContainer.addView(remainingMutualCount);
        }
    }

    private void populateUserCommunity(List<CommunityFeedSolrObj> communities) { //other communities

        if ((getActivity()) == null || getActivity().isFinishing()) return;

        int screenWidth = CommonUtil.getWindowWidth(getActivity());
        int columnSize = screenWidth / 2 - mImageMargin;
        mUserCommunityLayout.removeAllViews();
        int counter = 0;
        for (final CommunityFeedSolrObj community : communities) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_communities_items, null);
            RippleViewLinear container = findById(view, R.id.profile_community_container);
            CircleImageView communityImage = findById(view, R.id.community_icon);
            TextView communityName = findById(view, R.id.community_name);
            if (StringUtil.isNotNullOrEmptyString(community.getThumbnailImageUrl())) {
                communityImage.setCircularImage(true);
                String authorThumborUrl = CommonUtil.getThumborUri(community.getThumbnailImageUrl(), mProfileSize, mProfileSize);
                communityImage.bindImage(authorThumborUrl);

            }
            communityName.setText(community.getNameOrTitle());
            container.setOnRippleCompleteListener(new RippleViewLinear.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleViewLinear rippleView) {
                    openCommunityDetails(community);
                }
            });

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    columnSize, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);

            mUserCommunityLayout.addView(view);
            counter++;
            if (counter == 4) break;
        }

        mProgressBarCommunity.setVisibility(View.GONE);
    }

    private void populateFollowedMentors(List<UserSolrObj> followedMentors) {
        if ((getActivity()) == null || getActivity().isFinishing()) return;

        int counter = 0;
        mFollowedMentor.removeAllViewsInLayout();

        for (final UserSolrObj userSolrObj : followedMentors) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_champion_list_item, null);
            CircleImageView mutualCommunityImage = findById(view, R.id.iv_profile_full_view_icon);
            TextView mentorName = findById(view, R.id.user_name);
            TextView expertAt = findById(view, R.id.expert_at);
            TextView follower = findById(view, R.id.follower);
            Button followFollowingBtn = findById(view, R.id.follow_following_btn);

            if (StringUtil.isNotNullOrEmptyString(userSolrObj.getThumbnailImageUrl())) {
                mutualCommunityImage.setCircularImage(true);
                String authorThumborUrl = CommonUtil.getThumborUri(userSolrObj.getThumbnailImageUrl(), mProfileSize, mProfileSize);
                mutualCommunityImage.bindImage(authorThumborUrl);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() instanceof ProfileActivity) {
                        ((ProfileActivity) getActivity()).championDetailActivity(userSolrObj.getIdOfEntityOrParticipant(), true);
                    } else {
                        fragment.championDetailActivity(userSolrObj.getIdOfEntityOrParticipant(), true);
                    }
                }
            });

            mentorName.setText(StringUtil.toTitleCase(userSolrObj.getNameOrTitle()));

            List<String> canHelpInArea = userSolrObj.getCanHelpIns();
            if (StringUtil.isNotEmptyCollection(canHelpInArea)) {
                StringBuilder expertFields = new StringBuilder();
                for (int i = 0; i < canHelpInArea.size(); i++) {
                    if (i > 0) {
                        expertFields.append(AppConstants.COMMA);
                        expertFields.append(AppConstants.SPACE);
                    }
                    expertFields.append(canHelpInArea.get(i));
                }
                expertAt.setText(expertFields.toString());
            }

            if (follower != null) {
                String pluralComments = getResources().getQuantityString(R.plurals.numberOfFollowers, userSolrObj.getFollowerCount());
                follower.setText(String.valueOf(changeNumberToNumericSuffix(userSolrObj.getFollowerCount()) + AppConstants.SPACE + pluralComments));
            }

            followFollowingBtn.setVisibility(View.GONE);
            mFollowedMentor.addView(view);
            counter++;
            if (counter == 3) break;
        }

    }
    //endregion private methods

    //region public methods
    public void openCommunityDetails(CommunityFeedSolrObj communityFeedSolrObj) {
        if ((getActivity()) == null || getActivity().isFinishing()) return;
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedSolrObj, getScreenName(), null, 1);
    }

    public static ProfileDetailsFragment createInstance(long userId, String name) {
        ProfileDetailsFragment profileDetailsFragment = new ProfileDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        bundle.putString(USER_MENTOR_NAME, name);
        profileDetailsFragment.setArguments(bundle);
        return profileDetailsFragment;
    }
    //endregion private methods
}