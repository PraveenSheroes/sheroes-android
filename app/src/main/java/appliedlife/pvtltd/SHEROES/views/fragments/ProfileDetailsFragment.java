package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.HashMap;
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
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.FollowingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorsUserListingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileCommunitiesActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleViewLinear;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
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

public class ProfileDetailsFragment extends BaseFragment implements ProfileView {
    private static final String SCREEN_LABEL = "Profile Details Screen";
    private final String TAG = LogUtils.makeLogTag(ProfileDetailsFragment.class);

    public static final String USER_MENTOR_ID ="USERID";
    public static final String USER_MENTOR_NAME ="USER_NAME";
    public static final String SELF_PROFILE ="SELF_PROFILE";
    private long userId;
    private boolean isSelfProfile;
    private List<CommunityFeedSolrObj> profileCommunities;
    private List<UserSolrObj> followedChampions;
    private boolean isFragmentVisible = false;

    @Bind(R.id.user_communities)
    GridLayout userCommunityLayout;

    @Bind(R.id.extra_space)
    TextView spacing;

    @Bind(R.id.mutual_community_container)
    LinearLayout mutualCommunityContainer;

    @Bind(R.id.container)
    LinearLayout followedMentor;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.followed_view_more)
    TextView viewMoreFollowedMentor;

    @Bind(R.id.community_view_more)
    TextView viewMoreCommunities;

    @Bind(R.id.mutual_community_label)
    TextView mutualCommunityLabel;

    @Bind(R.id.followed_mentors)
    LinearLayout followedMentorsListContainer;

    @Bind(R.id.empty_mentor_view)
    TextView emptyViewFollowedMentor;

    @Bind(R.id.empty_mentor_view_container)
    LinearLayout emptyFollowedMentorContainer;

    @Bind(R.id.community_view_container)
    LinearLayout communityListContainer;

    @Bind(R.id.empty_community_view)
    TextView emptyViewCommunities;

    @Bind(R.id.dotted_border_container_community)
    FrameLayout dottedCommunityEmptyView;

    @Bind(R.id.empty_community_view_container)
    LinearLayout emptyViewCommunitiesContainer;

    @Bind(R.id.dotted_border_container)
    FrameLayout emptyViewDottedBorder;

    @BindDimen(R.dimen.dp_size_12)
    public int mImageMargin;

    @Bind(R.id.progress_bar_champion)
    ProgressBar progressBarChampion;

    @Bind(R.id.progress_bar_community)
    ProgressBar progressBarCommunity;

    @BindDimen(R.dimen.dp_size_4)
    public int defaultSize;

    @BindDimen(R.dimen.dp_size_65)
    int profileSize;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @Inject
    ProfilePresenterImpl profilePresenter;

    public static ProfileDetailsFragment createInstance(long userId, String name) {
        ProfileDetailsFragment profileDetailsFragment = new ProfileDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        bundle.putString(USER_MENTOR_NAME, name);
        profileDetailsFragment.setArguments(bundle);
        return profileDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.profile_community_champion_layout, container, false);
        profilePresenter.attachView(this);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        userId = bundle.getLong(USER_MENTOR_ID);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserBO()) {
            if (mUserPreference.get().getUserSummary().getUserId() == userId) {
                isSelfProfile = true;
            }
        }

        return view;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return profilePresenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateUserProfileDetails();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
            AnalyticsManager.timeScreenView(getScreenName());
        } else {
            if (isFragmentVisible) {
                isFragmentVisible = false;
                AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        profilePresenter.detachView();
    }

    private void populateUserProfileDetails() {
        profilePresenter.getProfileTopSectionCount(mAppUtils.profileTopSectionCount(userId));

        profilePresenter.getFollowedMentors(mAppUtils.followerFollowingRequest(1, userId, FollowingEnum.FOLLOWED_CHAMPIONS.name()));

        if(isSelfProfile) {
            profilePresenter.getPublicProfileCommunity(mAppUtils.userCommunitiesRequestBuilder(1, userId));
        } else {
            profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(1, userId));

        }
    }

    @OnClick(R.id.dotted_border_container_community)
    public void openCommunityList() {
        if(isSelfProfile) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra(SheroesDeepLinkingActivity.OPEN_FRAGMENT, "Community List");
            startActivity(intent);
        }
    }

    @OnClick(R.id.dotted_border_container)
    public void openChampionList() {
        if(isSelfProfile) {
            Intent intent = new Intent(getActivity(), MentorsUserListingActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.followed_view_more)
    public void navigateToFollowedMentors() {
        if(StringUtil.isNotEmptyCollection(followedChampions)) {
            FollowingActivity.navigateTo(getActivity(), userId, isSelfProfile, getScreenName(),  FollowingEnum.FOLLOWED_CHAMPIONS, null );
        }
    }

    @OnClick(R.id.community_view_more)
    public void navigateToCommunityListing() {
        if(StringUtil.isNotEmptyCollection(profileCommunities)) {
            ProfileCommunitiesActivity.navigateTo(getActivity(), userId, isSelfProfile, getScreenName(), null);
        }
    }

    private void populateMutualCommunities(List<CommunityFeedSolrObj> communities) {

        int mutualCommunitySize = communities.size();
        if((getActivity()) == null || getActivity().isFinishing()) return;

        String name = ((ProfileActivity)getActivity()).getUserNameTitle() == null ? "User" : ((ProfileActivity)getActivity()).getUserNameTitle();
        String mutualCommunityText = getResources().getString(R.string.PLACEHOLDER_MUTUAL_COMMUNITY, name, String.valueOf(mutualCommunitySize));
        mutualCommunityLabel.setText(mutualCommunityText);

        mutualCommunityContainer.removeAllViews();

        int counter = 0;
        for (final CommunityFeedSolrObj community : communities) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_mutual_community, null);
            CircleImageView mutualCommunityImage = findById(view, R.id.mutual_community_icon);
            if (StringUtil.isNotNullOrEmptyString(community.getThumbnailImageUrl())) {
                mutualCommunityImage.setCircularImage(true);
                String authorThumborUrl = CommonUtil.getThumborUri(community.getThumbnailImageUrl(), profileSize, profileSize);
                mutualCommunityImage.bindImage(authorThumborUrl);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCommunityDetails(community);
                }
            });

            mutualCommunityContainer.addView(view);
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
            lparams.setMargins(defaultSize, defaultSize, defaultSize, defaultSize);
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
            mutualCommunityContainer.addView(remainingMutualCount);
        }
    }

    private void populateUserCommunity(List<CommunityFeedSolrObj> communities) { //other communities

        if((getActivity()) == null || getActivity().isFinishing()) return;

        int screenWidth = CommonUtil.getWindowWidth(getActivity());
        int columnSize = screenWidth / 2 - mImageMargin;
        userCommunityLayout.removeAllViews();
        int counter = 0;
        for (final CommunityFeedSolrObj community : communities) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_communities_items, null);
            RippleViewLinear container = findById(view, R.id.profile_community_container);
            CircleImageView communityImage = findById(view, R.id.community_icon);
            TextView communityName = findById(view, R.id.community_name);
            if (StringUtil.isNotNullOrEmptyString(community.getThumbnailImageUrl())) {
                communityImage.setCircularImage(true);
                String authorThumborUrl = CommonUtil.getThumborUri(community.getThumbnailImageUrl(), profileSize, profileSize);
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

            userCommunityLayout.addView(view);
            counter++;
            if (counter == 4) break;
        }

        progressBarCommunity.setVisibility(View.GONE);
    }

    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse feedResponsePojo) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if(feedResponsePojo.getNumFound() ==0) {
            //empty view
            emptyFollowedMentorContainer.setVisibility(View.VISIBLE);
            String name = "User";
            if(getActivity()!=null && !getActivity().isFinishing()) {
                name = ((ProfileActivity)getActivity()).getUserNameTitle() == null ? "User" : ((ProfileActivity)getActivity()).getUserNameTitle();
            }
            String message = getString(R.string.empty_followed_mentor, name);
            emptyViewFollowedMentor.setText(message);
            emptyViewFollowedMentor.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.vector_public_business_woman,0,0);

            followedMentorsListContainer.setVisibility(View.GONE);

            if(isSelfProfile) {
                emptyViewDottedBorder.setBackgroundResource(R.drawable.dotted_line_border);
                emptyViewFollowedMentor.setText(R.string.champions_followed);
                emptyViewDottedBorder.setVisibility(View.VISIBLE);
            } else{
                emptyViewDottedBorder.setBackgroundResource(0);
            }

        } else {
            emptyFollowedMentorContainer.setVisibility(View.GONE);
            emptyViewDottedBorder.setVisibility(View.GONE);
            followedMentorsListContainer.setVisibility(View.VISIBLE);

            List<UserSolrObj> feedDetailList = feedResponsePojo.getFeedDetails();
            if (StringUtil.isNotEmptyCollection(feedDetailList)) {
                populateFollowedMentors(feedDetailList);  //followed mentor
                followedChampions = feedDetailList;
            }
        }
        progressBarChampion.setVisibility(View.GONE);
    }

    @Override
    public void getTopSectionCount(ProfileTopSectionCountsResponse profileTopSectionCountsResponse) {

    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
        List<CommunityFeedSolrObj> mutualCommunity = userCommunities.getMutualCommunities();
        List<CommunityFeedSolrObj> otherCommunity = userCommunities.getOtherCommunities();
        if (!isSelfProfile) {
            if (StringUtil.isNotEmptyCollection(mutualCommunity)) {
                CommunityFeedSolrObj profileCommunity = mutualCommunity.get(0);
                profileCommunity.setMutualCommunityCount(mutualCommunity.size());
                profileCommunity.setMutualCommunityFirstItem(true);
                profileCommunity.setShowHeader(true);
                populateMutualCommunities(mutualCommunity); // for mutual community
                mutualCommunityContainer.setVisibility(View.VISIBLE);
                mutualCommunityLabel.setVisibility(View.VISIBLE);
                spacing.setVisibility(View.GONE);
            } else {
                spacing.setVisibility(View.GONE);
                mutualCommunityContainer.setVisibility(View.GONE);
                mutualCommunityLabel.setVisibility(View.GONE);
            }
        } else {
            spacing.setVisibility(View.GONE);
            mutualCommunityContainer.setVisibility(View.GONE);
            mutualCommunityLabel.setVisibility(View.GONE);
        }

        if (StringUtil.isNotEmptyCollection(otherCommunity)) {
            emptyViewCommunitiesContainer.setVisibility(View.GONE);
            communityListContainer.setVisibility(View.VISIBLE);
            CommunityFeedSolrObj profileCommunity = otherCommunity.get(0);
            profileCommunity.setShowHeader(true);

            if(isSelfProfile) {
                profileCommunity.setMutualCommunityCount(0);
            } else if(mutualCommunity!=null && mutualCommunity.size()>0) {
                profileCommunity.setMutualCommunityCount(mutualCommunity.size());
            }
            profileCommunity.setOtherCommunityFirstItem(true);
            populateUserCommunity(otherCommunity); //for other community

            if(mutualCommunity!=null && !isSelfProfile) { //other have both mutual and non mutual so making single lust here
                mutualCommunity.addAll(otherCommunity);
                profileCommunities = mutualCommunity;
            } else {
                profileCommunities = otherCommunity;
            }
        } else{
            //current scenrio - other hv all so change if future other don't hv mutual - empty view
            emptyViewCommunitiesContainer.setVisibility(View.VISIBLE);
            communityListContainer.setVisibility(View.GONE);
            if (getActivity() == null) {
                return;
            }
            String name = ((ProfileActivity)getActivity()).getUserNameTitle() == null ? "User" : ((ProfileActivity)getActivity()).getUserNameTitle();
            String message = getString(R.string.empty_followed_community, name);
            emptyViewCommunities.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.vector_community_member_public,0,0);
            emptyViewCommunities.setText(message);

            if(isSelfProfile) {
                dottedCommunityEmptyView.setBackgroundResource(R.drawable.dotted_line_border);
                emptyViewCommunities.setText(R.string.join_communities);
            } else{
                dottedCommunityEmptyView.setBackgroundResource(0);
            }
        }
    }

    private void populateFollowedMentors(List<UserSolrObj> followedMentors) {
        if((getActivity()) == null || getActivity().isFinishing()) return;

        int counter = 0;
        followedMentor.removeAllViewsInLayout();

        for (final UserSolrObj userSolrObj : followedMentors) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.followed_mentor_list_item, null);
            CircleImageView mutualCommunityImage = findById(view, R.id.iv_mentor_full_view_icon);
            TextView mentorName = findById(view, R.id.user_name);
            TextView expertAt = findById(view, R.id.expert_at);
            TextView follower = findById(view, R.id.follower);


            if (StringUtil.isNotNullOrEmptyString(userSolrObj.getThumbnailImageUrl())) {
                mutualCommunityImage.setCircularImage(true);
                String authorThumborUrl = CommonUtil.getThumborUri(userSolrObj.getThumbnailImageUrl(), profileSize, profileSize);
                mutualCommunityImage.bindImage(authorThumborUrl);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ProfileActivity)getActivity()).championDetailActivity(userSolrObj.getIdOfEntityOrParticipant(), true);
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
                String pluralComments = getResources().getQuantityString(R.plurals.numberOfFollowers, userSolrObj.getSolrIgnoreNoOfMentorFollowers());
                follower.setText(String.valueOf(changeNumberToNumericSuffix(userSolrObj.getSolrIgnoreNoOfMentorFollowers()) + AppConstants.SPACE + pluralComments));
            }

            followedMentor.addView(view);
            counter++;
            if (counter == 3) break;
        }

    }

    public void openCommunityDetails( CommunityFeedSolrObj communityFeedSolrObj) {
        if((getActivity()) == null || getActivity().isFinishing()) return;
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedSolrObj, getScreenName(), null, 1);
    }

    @Override
    protected boolean trackScreenTime() {
        return false;
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
            HashMap<String, Object> properties = new
                    EventProperty.Builder()
                    .id(Long.toString(userId))
                    .isMentor(false)
                    .isOwnProfile(isSelfProfile)
                    .build();
            return properties;
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
    public void onSpamPostOrCommentReported(SpamResponse spamResponse) {}

    @Override
    public void onUserDeactivation(BaseResponse baseResponse) {
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

    @Override
    public void startNextScreen() {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
}


