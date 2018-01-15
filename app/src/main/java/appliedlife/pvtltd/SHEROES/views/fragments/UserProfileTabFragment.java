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

import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.FollowingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorUserProfileActvity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorsUserListingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileCommunitiesActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileNewView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Ravi on 31/12/17.
 */

public class UserProfileTabFragment extends BaseFragment implements ProfileNewView {
    private static final String SCREEN_LABEL = "Profile Details Screen";
    private final String TAG = LogUtils.makeLogTag(UserProfileTabFragment.class);

    public static final String USER_MENTOR_ID ="USERID";
    public static final String USER_MENTOR_NAME ="USER_NAME";
    public static final String SELF_PROFILE ="SELF_PROFILE";
    private long userId;
    private String userName = "User";
    private boolean isSelfProfile;
    private List<CommunityFeedSolrObj> profileCommunities;
    private List<UserSolrObj> followedChampions;

    @Bind(R.id.user_communities)
    GridLayout userCommunityLayout;

    @Bind(R.id.extra_space)
    TextView spacing;

    @Bind(R.id.mutual_community_container)
    LinearLayout mutualCommunityContainer;

    @Bind(R.id.followed_mentor_container)
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

    @BindDimen(R.dimen.dp_size_4)
    public int defaultSize;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @Inject
    ProfilePresenterImpl profilePresenter;

    public static UserProfileTabFragment createInstance(long userId, String name) {
        UserProfileTabFragment userProfileTabFragment = new UserProfileTabFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(USER_MENTOR_ID, userId);
        bundle.putString(USER_MENTOR_NAME, name);
        userProfileTabFragment.setArguments(bundle);
        return userProfileTabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.profile_community_champion_layout, container, false);
        profilePresenter.attachView(this);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        userId = bundle.getLong(USER_MENTOR_ID);
        userName = bundle.getString(USER_MENTOR_NAME);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserBO()) {
            if (mUserPreference.get().getUserSummary().getUserId() == userId) {
                isSelfProfile = true;
                userName = mUserPreference.get().getUserSummary().getFirstName()!=null ? mUserPreference.get().getUserSummary().getFirstName() : "User";
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateUserProfileDetails();
    }

    private void populateUserProfileDetails() {
        boolean hideAnnonymousPost = !isSelfProfile;
        profilePresenter.getUserPostCountFromPresenter(mAppUtils.usersFeedDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, AppConstants.ONE_CONSTANT, userId, hideAnnonymousPost));

        profilePresenter.getUsersFollowerOrFollowingCount(mAppUtils.countUserFollowersOrFollowing(userId, true)); //to get follower count

        profilePresenter.getUsersFollowerOrFollowingCount(mAppUtils.countUserFollowersOrFollowing(userId, false)); //to get follower count

        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(1, userId));

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
            FollowingActivity.navigateTo(getActivity(), userId,  getScreenName(), null );
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
        String name = communities.get(0).getAuthorName() == null ? "User" : communities.get(0).getAuthorName();
        name = ((MentorUserProfileActvity)getActivity()).getUserNameTitle();
        mutualCommunityLabel.setText(name + " & you share "+ mutualCommunitySize +" mutual communities");

        mutualCommunityContainer.removeAllViews();

        int counter = 0;
        for (final CommunityFeedSolrObj community : communities) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.profile_mutual_community, null);
            CircleImageView mutualCommunityImage = ButterKnife.findById(view, R.id.mutual_community_icon);
            if (StringUtil.isNotNullOrEmptyString(community.getThumbnailImageUrl())) {
                mutualCommunityImage.setCircularImage(true);
                mutualCommunityImage.setPlaceHolderId(R.drawable.default_img);
                mutualCommunityImage.setErrorPlaceHolderId(R.drawable.default_img);
                mutualCommunityImage.bindImage(community.getThumbnailImageUrl());
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

        int screenWidth = CommonUtil.getWindowWidth(getContext());
        float aspectRatio = (float) 124 / 160;
        int columnSize = screenWidth / 2 - mImageMargin;
        userCommunityLayout.removeAllViews();
        int counter = 0;
        for (final CommunityFeedSolrObj community : communities) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.profile_communities_items, null);
            LinearLayout container = ButterKnife.findById(view, R.id.profile_community_container);
            CircleImageView communityImage = ButterKnife.findById(view, R.id.community_icon);
            TextView communityName = ButterKnife.findById(view, R.id.community_name);
            if (StringUtil.isNotNullOrEmptyString(community.getThumbnailImageUrl())) {
                communityImage.setCircularImage(true);
                communityImage.setPlaceHolderId(R.drawable.default_img);
                communityImage.setErrorPlaceHolderId(R.drawable.default_img);
                communityImage.bindImage(community.getThumbnailImageUrl());

            }
            communityName.setText(community.getNameOrTitle());

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCommunityDetails(community);
                }
            });

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    columnSize, (int) (columnSize * aspectRatio));
            view.setLayoutParams(layoutParams);

            userCommunityLayout.addView(view);
            counter++;
            if (counter == 4) break;
        }
    }

    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse feedResponsePojo) {

        if(feedResponsePojo.getNumFound() ==0) {
            //empty view

            emptyFollowedMentorContainer.setVisibility(View.VISIBLE);
            userName = userName ==null ? "User" : userName;
            String message = getString(R.string.empty_followed_mentor, userName);
            emptyViewFollowedMentor.setText(message);
            followedMentorsListContainer.setVisibility(View.GONE);

            if(isSelfProfile) {
                emptyViewDottedBorder.setBackgroundResource(R.drawable.dotted_line_border);
                emptyViewFollowedMentor.setText("Follow Champions");
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

    }

    @Override
    public void getUsersFollowerCount(BaseResponse userFollowerOrFollowingCountResponse) {
        LogUtils.info(TAG, "Follower count:" + userFollowerOrFollowingCountResponse.getNumFound());
        ((MentorUserProfileActvity) getActivity()).setUsersFollowerCount(userFollowerOrFollowingCountResponse.getNumFound());

    }

    @Override
    public void getUsersFollowingCount(BaseResponse userFollowerOrFollowingCountResponse) {
        LogUtils.info(TAG, "Following count:" + userFollowerOrFollowingCountResponse.getNumFound());
        ((MentorUserProfileActvity) getActivity()).setUsersFollowingCount(userFollowerOrFollowingCountResponse.getNumFound());

    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
        LogUtils.info(TAG, "Community count:" + userCommunities.getStatus());

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
            spacing.setVisibility(View.VISIBLE);
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
            String name = userName!=null ? userName : "User";
            String message = getString(R.string.empty_followed_community, name);
            emptyViewCommunities.setText(message);

            if(isSelfProfile) {
                dottedCommunityEmptyView.setBackgroundResource(R.drawable.dotted_line_border);
                emptyViewCommunities.setText("Join Communities & Connect");
            } else{
                dottedCommunityEmptyView.setBackgroundResource(0);
            }
        }
    }

    @Override
    public void getUsersPostCount(int totalPost) {
        LogUtils.info(TAG, "Post count:" + totalPost);
        ((MentorUserProfileActvity) getActivity()).setUsersPostCount(totalPost);
    }

    private void populateFollowedMentors(List<UserSolrObj> followedMentors) {

        int counter = 0;
        followedMentor.removeAllViewsInLayout();

        for (final UserSolrObj userSolrObj : followedMentors) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.followed_mentor_list_item, null);
            CircleImageView mutualCommunityImage = ButterKnife.findById(view, R.id.iv_mentor_full_view_icon);
            TextView mentorName = ButterKnife.findById(view, R.id.user_name);
            TextView expertAt = ButterKnife.findById(view, R.id.expert_at);
            TextView follower = ButterKnife.findById(view, R.id.follower);


            if (StringUtil.isNotNullOrEmptyString(userSolrObj.getThumbnailImageUrl())) {
                mutualCommunityImage.setCircularImage(true);
                mutualCommunityImage.setPlaceHolderId(R.drawable.default_img);
                mutualCommunityImage.setErrorPlaceHolderId(R.drawable.default_img);
                mutualCommunityImage.bindImage(userSolrObj.getThumbnailImageUrl());
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MentorUserProfileActvity)getActivity()).championDetailActivity(userSolrObj.getIdOfEntityOrParticipant(), true);
                }
            });

            mentorName.setText(userSolrObj.getNameOrTitle());

            List<String> canHelpInArea = userSolrObj.getCanHelpIns();
            if (StringUtil.isNotEmptyCollection(canHelpInArea)) {
                StringBuilder expertFields = new StringBuilder();
                for (int i = 0; i < canHelpInArea.size(); i++) {
                    if (i > 0) {
                        expertFields.append(AppConstants.COMMA);
                    }
                    expertFields.append(canHelpInArea.get(i));
                }
                expertAt.setText(expertFields.toString());
            }

            if (follower != null) {
                String pluralComments = getResources().getQuantityString(R.plurals.numberOfFollowers, userSolrObj.getSolrIgnoreNoOfMentorFollowers());
                follower.setText(String.valueOf(numericToThousand(userSolrObj.getSolrIgnoreNoOfMentorFollowers()) + AppConstants.SPACE + pluralComments));
            }

            followedMentor.addView(view);
            counter++;
            if (counter == 3) break;
        }

    }

    public void openCommunityDetails( CommunityFeedSolrObj communityFeedSolrObj) {
        CommunityDetailActivity.navigateTo(getActivity(), communityFeedSolrObj, getScreenName(), null, 1);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

}


