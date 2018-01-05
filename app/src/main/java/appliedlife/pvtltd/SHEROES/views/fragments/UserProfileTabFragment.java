package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.MentorUserProfileDashboardActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileCommunitiesActivity;
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

    @Bind(R.id.mutual_community_label)
    TextView mutualCommunityLabel;

    @Bind(R.id.followed_mentors)
    LinearLayout followedMentorsListContainer;

    @Bind(R.id.empty_mentor_view)
    TextView emptyViewFollowedMentor;

    @Bind(R.id.community_view_container)
    LinearLayout communityListContainer;

    @Bind(R.id.empty_community_view)
    TextView emptyViewCommunities;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @BindDimen(R.dimen.dp_size_12)
    public int mImageMargin;

    @BindDimen(R.dimen.dp_size_4)
    public int defaultSize;

    long userId;
    boolean isSelfProfile;

    @Inject
    ProfilePresenterImpl profilePresenter;

    public static UserProfileTabFragment createInstance(long userId) {
        UserProfileTabFragment userProfileTabFragment = new UserProfileTabFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("USERID", userId); //todo -profile- change here
        userProfileTabFragment.setArguments(bundle);
        return userProfileTabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.followed_mentor_listing_layout, container, false);
        profilePresenter.attachView(this);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        userId = bundle.getLong("USERID");

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserBO()) {
            if (mUserPreference.get().getUserSummary().getUserId() == userId) {
                Log.i(TAG, "its mentor");
                isSelfProfile = true;
            }
        }

        pupulateUserProfileDetails();

        return view;
    }


    private void pupulateUserProfileDetails() {

        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(1));

        profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(1, userId));

        boolean hideAnnonymousPost = !isSelfProfile;
        profilePresenter.getUserPostCountFromPresenter(mAppUtils.usersFeedDetailRequestBuilder(AppConstants.FEED_COMMUNITY_POST, AppConstants.ONE_CONSTANT, userId, hideAnnonymousPost));

        profilePresenter.getUsersFollowerOrFollowing(mAppUtils.countUserFollowersOrFollowing(userId, true)); //to get follower count

        profilePresenter.getUsersFollowerOrFollowing(mAppUtils.countUserFollowersOrFollowing(userId, false)); //to get follower count
    }

    @OnClick(R.id.followed_view_more)
    public void navigateToFollowedMentors() {
        Intent intent = new Intent(getContext(), ProfileCommunitiesActivity.class);
        intent.putExtra("USERID", userId);
        intent.putExtra("isSelfProfile", isSelfProfile);
        startActivity(intent);

    }

    private void populateMutualCommunities(List<FeedDetail> communities) {

        int mutualCommunitySize = communities.size();

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

    private void populateUserCommunity(List<FeedDetail> communities) { //other communities

        int screenWidth = CommonUtil.getWindowWidth(getContext());
        float aspectRatio = (float) 124 / 160;
        int columnSize = screenWidth / 2 - mImageMargin;

        int counter = 0;
        for (final FeedDetail community : communities) {
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
                    Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
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
            emptyViewFollowedMentor.setVisibility(View.VISIBLE);
            emptyViewFollowedMentor.setText("User is not following any mentor yet");
            followedMentorsListContainer.setVisibility(View.GONE);

        } else {
            emptyViewFollowedMentor.setVisibility(View.GONE);
            followedMentorsListContainer.setVisibility(View.VISIBLE);

            List<UserSolrObj> feedDetailList = feedResponsePojo.getFeedDetails();
            if (StringUtil.isNotEmptyCollection(feedDetailList)) {
                populateFollowedMentors(feedDetailList);  //followed mentor
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
    public void getUsersCommunities(FeedResponsePojo userCommunities) {
        LogUtils.info(TAG, "Community count:" + userCommunities.getStatus());

        List<FeedDetail> mutualCommunity = userCommunities.getFeaturedDocs();
        List<FeedDetail> otherCommunity = userCommunities.getFeedDetails();
        if (!isSelfProfile) {
            if (StringUtil.isNotEmptyCollection(mutualCommunity)) {
                populateMutualCommunities(mutualCommunity); // for mutual community
            }
            mutualCommunityLabel.setVisibility(View.VISIBLE);
        } else {
            mutualCommunityLabel.setVisibility(View.GONE);
        }

        if (StringUtil.isNotEmptyCollection(otherCommunity)) {
            emptyViewCommunities.setVisibility(View.GONE);
            communityListContainer.setVisibility(View.VISIBLE);
            populateUserCommunity(otherCommunity); //for other community
        } else{
            //current scenrio - other hv all so change if future other don't hv mutual - empty view

            emptyViewCommunities.setText("User has not joined any community yet");
            emptyViewCommunities.setVisibility(View.VISIBLE);
            communityListContainer.setVisibility(View.GONE);

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

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }


}


