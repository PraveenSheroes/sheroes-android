package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.BadgeDetails;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopSectionCountsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.BadgesListAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RecyclerRowDivider;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileProgressDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment.SELF_PROFILE;

/**
 * Created by ravi on 12/06/18.
 * Display the user's badget closet in list
 */

public class CommunitiesBadgesActivity extends BaseActivity implements ProfileView, BadgesListAdapter.OnItemClicked{

    private static final String SCREEN_LABEL = "Badget Closet Screen";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.title_toolbar)
    TextView titleName;

    @Bind(R.id.communities)
    RecyclerView mBadgeRecyceler;

    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;

    @Inject
    AppUtils mAppUtils;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    ProfilePresenterImpl profilePresenter;

    private List<BadgeDetails> profileCommunities;
    private FragmentListRefreshData mFragmentListRefreshData;
    private BadgesListAdapter mAdapter;
    private SwipPullRefreshList mPullRefreshList;
    private long userMentorId;
    private boolean isSelfProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.badges_list_container);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            userMentorId = getIntent().getExtras().getLong(ProfileDetailsFragment.USER_MENTOR_ID);
            isSelfProfile = getIntent().getExtras().getBoolean(ProfileDetailsFragment.SELF_PROFILE);
        }

        profilePresenter.attachView(this);
        setupToolbarItemsColor();

       // mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.PROFILE_COMMUNITY_LISTING, AppConstants.NO_REACTION_CONSTANT);
       // mFragmentListRefreshData.setSelfProfile(isSelfProfile);
       // mFragmentListRefreshData.setMentorUserId(userMentorId);
        mentorSearchInListPagination();

       // Fragment followingFragment = UserJoinedCommunitiesListFragment.createInstance(userMentorId, "", isSelfProfile);
       // FragmentManager fragmentManager = getSupportFragmentManager();
       // FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
       // fragmentTransaction.replace(R.id.container, followingFragment);
        //fragmentTransaction.commit();

    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        titleName.setText("Badget Closet");
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
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return profilePresenter;
    }

    private void mentorSearchInListPagination() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mBadgeRecyceler.setLayoutManager(mLayoutManager);
        RecyclerRowDivider decoration = new RecyclerRowDivider(this, ContextCompat.getColor(this, R.color.on_board_work), 1);
        mBadgeRecyceler.addItemDecoration(decoration);
        mAdapter = new BadgesListAdapter(CommunitiesBadgesActivity.this, this);
        mBadgeRecyceler.setAdapter(mAdapter);

        List<BadgeDetails> badges = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            BadgeDetails details = new BadgeDetails();
            details.setEarnedDate("23-Nov-2017");
            details.setName("ABC" + i);
            badges.add(details);
        }

        mAdapter.setData(badges);

        stopProgressBar();

       // mPullRefreshList = new SwipPullRefreshList();
       // mPullRefreshList.setPullToRefresh(false);

      /*  mBadgeRecyceler.addOnScrollListener(new HidingScrollListener(profilePresenter, mBadgeRecyceler, mLayoutManager, fragmentListRefreshData) {
            @Override
            public void onHide() {
            }

            @Override
            public void onShow() {
            }

            @Override
            public void dismissReactions() {
            }
        });

        if (isSelfProfile) {
            profilePresenter.getPublicProfileCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));
        } else {
            profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));

        }*/

          ((SheroesApplication) getApplication()).trackScreenView(SCREEN_LABEL);
    }

    private void refreshFeedMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
      //  mPullRefreshList = new SwipPullRefreshList();
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        if (isSelfProfile) {
            profilePresenter.getPublicProfileCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));
        } else {
            profilePresenter.getUsersCommunity(mAppUtils.userCommunitiesRequestBuilder(mFragmentListRefreshData.getPageNo(), userMentorId));

        }

    }

    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse profileFeedResponsePojo) {
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void getTopSectionCount(ProfileTopSectionCountsResponse profileTopSectionCountsResponse) {
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
      /*  if(userCommunities.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
            mProgressBar.setLayoutParams(params);

            List<CommunityFeedSolrObj> otherCommunities = getUsersCommunity(userCommunities, mFragmentListRefreshData.getPageNo());
            if (StringUtil.isNotEmptyCollection(otherCommunities) && mAdapter != null) {
                int mPageNo = mFragmentListRefreshData.getPageNo();
                mFragmentListRefreshData.setPageNo(++mPageNo);
                mPullRefreshList.allListData(otherCommunities);
                List<CommunityFeedSolrObj> data = null;
                CommunityFeedSolrObj feedProgressBar = new CommunityFeedSolrObj();
                feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
                data = mPullRefreshList.getFeedResponses();
                int position = data.size() - otherCommunities.size();
                if (position > 0) {
                    data.remove(position - 1);
                }
                data.add(feedProgressBar);
                mAdapter.setData(data);

            } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mAdapter != null) {
                List<CommunityFeedSolrObj> data = mPullRefreshList.getFeedResponses();
                data.remove(data.size() - 1);

            } else {
                // mBadgeRecyceler.setEmptyViewWithImage(emptyView, R.string.empty_mentor_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);
            }
            mAdapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse communityFeedSolrObj) {}

    @Override
    public void onUserDeactivation(BaseResponse baseResponse) {
    }

    @Override
    public void onItemClick( BadgeDetails badgeDetails) {

        //Open BadgeDetail dialog
        BadgeDetailsDialogFragment badgeDetailsDialogFragment = new BadgeDetailsDialogFragment();
        badgeDetailsDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        badgeDetailsDialogFragment.show(getFragmentManager(), ProfileProgressDialog.class.getName());

    }

    //region static methods
    public static void navigateTo(Activity fromActivity, long mentorID, boolean isSelfProfile, String sourceScreen, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, CommunitiesBadgesActivity.class);
        intent.putExtra(ProfileDetailsFragment.USER_MENTOR_ID, mentorID);
        intent.putExtra(SELF_PROFILE, isSelfProfile);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, 1, null);
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
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
}
