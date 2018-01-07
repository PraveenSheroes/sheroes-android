package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunity;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorUserProfileDashboardActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileCommunitiesActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileFollowedChampionActivity;
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
    private static final String SCREEN_LABEL = "Mentor Profile Detail Screen";
    private final String TAG = LogUtils.makeLogTag(UserProfileTabFragment.class);

    private long userId;
    private String userName = "User";
    private boolean isSelfProfile;
    private List<ProfileCommunity> profileCommunities;
    private List<UserSolrObj> followedChampions;

    @Bind(R.id.user_communities)
    GridLayout userCommunityLayout;

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

    @Bind(R.id.empty_community_view_container)
    LinearLayout emptyViewCommunitiesContainer;

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
        bundle.putLong("USERID", userId);
        bundle.putString("USER_NAME", name);
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
        userId = bundle.getLong("USERID");
        userName = bundle.getString("USER_NAME");

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserBO()) {
            if (mUserPreference.get().getUserSummary().getUserId() == userId) {
                isSelfProfile = true;
            }
        }

        pupulateUserProfileDetails();

        return view;
    }


    private void pupulateUserProfileDetails() {

        boolean hideAnnonymousPost = !isSelfProfile;
        profilePresenter.getUserPostCountFromPresenter(mAppUtils.usersFeedDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, AppConstants.ONE_CONSTANT, userId, hideAnnonymousPost));

        profilePresenter.getUsersFollowerOrFollowing(mAppUtils.countUserFollowersOrFollowing(userId, true)); //to get follower count

        profilePresenter.getUsersFollowerOrFollowing(mAppUtils.countUserFollowersOrFollowing(userId, false)); //to get follower count

        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(1, userId));

        profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(1, userId));

    }

    @OnClick(R.id.followed_view_more)
    public void navigateToFollowedMentors() {

        if(StringUtil.isNotEmptyCollection(followedChampions)) {
            Intent intent = new Intent(getContext(), ProfileFollowedChampionActivity.class);
            Parcelable parcelableContest = Parcels.wrap(followedChampions);
            intent.putExtra(AppConstants.CHAMPION_ID, parcelableContest);
            intent.putExtra("USERID", userId);
            intent.putExtra("isSelfProfile", isSelfProfile);
            startActivity(intent);
        }
    }

    @OnClick(R.id.community_view_more)
    public void navigateToCommunityListing() {
        if(StringUtil.isNotEmptyCollection(profileCommunities)) {
            Intent intent = new Intent(getContext(), ProfileCommunitiesActivity.class);
            Parcelable parcelableContest = Parcels.wrap(profileCommunities);
            intent.putExtra(AppConstants.COMMUNITY_ID, parcelableContest);
            intent.putExtra("USERID", userId);
            intent.putExtra("isSelfProfile", isSelfProfile);
            startActivity(intent);
        }
    }

    private void populateMutualCommunities(List<ProfileCommunity> communities) {

        int mutualCommunitySize = communities.size();
        mutualCommunityLabel.setText(communities.get(0).getNameOrTitle() + " & you share "+ mutualCommunitySize +" mutual communities");

        int counter = 0;
        for (final FeedDetail community : communities) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.profile_mutual_community, null);
            CircleImageView mutualCommunityImage = ButterKnife.findById(view, R.id.mutual_community_icon);
            if (StringUtil.isNotNullOrEmptyString(community.getThumbnailImageUrl())) {
                mutualCommunityImage.setCircularImage(true);
                mutualCommunityImage.setPlaceHolderId(R.drawable.default_img);
                mutualCommunityImage.setErrorPlaceHolderId(R.drawable.default_img);
                mutualCommunityImage.bindImage(community.getThumbnailImageUrl());
            }

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
            mutualCommunityContainer.addView(remainingMutualCount);
        }

    }

    private void populateUserCommunity(List<ProfileCommunity> communities) { //other communities

        int screenWidth = CommonUtil.getWindowWidth(getContext());
        float aspectRatio = (float) 124 / 160;
        int columnSize = screenWidth / 2 - mImageMargin;

        int counter = 0;
        for (final ProfileCommunity community : communities) {
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
            String message = getString(R.string.empty_followed_mentor, userName);
            emptyViewFollowedMentor.setText(message);
            followedMentorsListContainer.setVisibility(View.GONE);

        } else {
            emptyFollowedMentorContainer.setVisibility(View.GONE);
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
        ((MentorUserProfileDashboardActivity) getActivity()).setUsersFollowerCount(userFollowerOrFollowingCountResponse.getNumFound());

    }

    @Override
    public void getUsersFollowingCount(BaseResponse userFollowerOrFollowingCountResponse) {
        LogUtils.info(TAG, "Following count:" + userFollowerOrFollowingCountResponse.getNumFound());
        ((MentorUserProfileDashboardActivity) getActivity()).setUsersFollowingCount(userFollowerOrFollowingCountResponse.getNumFound());

    }

    @Override
    public void getUsersPostCount(int totalPost) {
        LogUtils.info(TAG, "Following count:" + totalPost);
        ((MentorUserProfileDashboardActivity) getActivity()).setUsersPostCount(totalPost);
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
        LogUtils.info(TAG, "Community count:" + userCommunities.getStatus());

        List<ProfileCommunity> mutualCommunity = userCommunities.getMutualCommunities();
        List<ProfileCommunity> otherCommunity = userCommunities.getOtherCommunities();
        if (!isSelfProfile) {
            if (StringUtil.isNotEmptyCollection(mutualCommunity)) {
                ProfileCommunity profileCommunity = mutualCommunity.get(0);
                profileCommunity.setMutualCommunityCount(mutualCommunity.size());
                profileCommunity.setMutualCommunityFirstItem(true);
                profileCommunity.setShowHeader(true);
                populateMutualCommunities(mutualCommunity); // for mutual community
            }
            mutualCommunityContainer.setVisibility(View.VISIBLE);
            mutualCommunityLabel.setVisibility(View.VISIBLE);
        } else {
            mutualCommunityContainer.setVisibility(View.GONE);
            mutualCommunityLabel.setVisibility(View.GONE);
        }

        if (StringUtil.isNotEmptyCollection(otherCommunity)) {
            emptyViewCommunitiesContainer.setVisibility(View.GONE);
            communityListContainer.setVisibility(View.VISIBLE);
            ProfileCommunity profileCommunity = otherCommunity.get(0);
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
            String message = getString(R.string.empty_followed_community, userName);
            emptyViewCommunities.setText(message);
            if(isSelfProfile) {
                //join community
            }
        }


    }


    private void populateFollowedMentors(List<UserSolrObj> followedMentors) {

        int counter = 0;
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
                    ((MentorUserProfileDashboardActivity)getActivity()).championDetailActivity(userSolrObj.getEntityOrParticipantId(), true);
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

    public void openCommunityDetails( ProfileCommunity profileCommunity) {
        Intent intent = new Intent(getActivity(), CommunitiesDetailActivity.class);
        Bundle bundle = new Bundle();
        Parcelable parcelables = Parcels.wrap(profileCommunity);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelables);
        bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.MY_COMMUNITY);
        intent.putExtras(bundle);

        intent.putExtra(AppConstants.COMMUNITY_ID, profileCommunity.getEntityOrParticipantId());
        intent.putExtra(AppConstants.FROM_DEEPLINK, true);
        startActivity(intent);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }




}


