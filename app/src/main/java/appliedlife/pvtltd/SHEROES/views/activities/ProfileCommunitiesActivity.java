package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileFollowedMentorAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileNewView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 03/01/18.
 */

public class ProfileCommunitiesActivity extends BaseActivity implements ProfileNewView {

    private static final String SCREEN_LABEL = "ProfileCommunitiesActivity Screen";

    @Inject
    ProfilePresenterImpl profilePresenter;

     @Bind(R.id.communities)
     RecyclerView mCommunityRecycler;

    @Inject
    AppUtils mAppUtils;

    long userId;

    LinearLayoutManager mLayoutManager;
    ProfileFollowedMentorAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        long userId = getIntent().getLongExtra("USERID", -1);

        profilePresenter.attachView(this);

        setupCommunityListAdapter();
    }


    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse feedResponsePojo) {
        List<UserSolrObj> feedDetailList = feedResponsePojo.getFeedDetails();

        mAdapter.setData(feedDetailList);
        mAdapter.notifyDataSetChanged();

    }

    private void setupCommunityListAdapter() {
         mLayoutManager = new LinearLayoutManager(this);
         mCommunityRecycler.setLayoutManager(mLayoutManager);

        mAdapter = new ProfileFollowedMentorAdapter(this, profilePresenter, this);
        mCommunityRecycler.setHasFixedSize(true);
        mCommunityRecycler.setAdapter(mAdapter);

        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(1, userId));
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void getUsersFollowerCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersFollowingCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersPostCount(int totalPost) {

    }

    @Override
    public void getUsersCommunities(FeedResponsePojo userCommunities) {

    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
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


}
