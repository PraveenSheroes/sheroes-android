package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunity;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileFollowedMentorAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 03/01/18.
 * Listing of Profile - User's champion
 */

public class ProfileFollowedChampionActivity extends BaseActivity implements ProfileFollowedMentorAdapter.OnItemClicked{

    private static final String SCREEN_LABEL = "ProfileCommunitiesActivity Screen";

    @Bind(R.id.tv_profile_tittle)
    TextView toolbarTitle;

    @Bind(R.id.communities)
    RecyclerView mCommunityRecycler;

    @Inject
    AppUtils mAppUtils;

    @Inject
    ProfilePresenterImpl profilePresenter;

    private LinearLayoutManager mLayoutManager;

    private ProfileFollowedMentorAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        toolbarTitle.setText(R.string.ID_CHAMPION);

        Parcelable parcelable = getIntent().getParcelableExtra(AppConstants.CHAMPION_ID);
        if (parcelable != null) {
            List<UserSolrObj> profileCommunities = Parcels.unwrap(parcelable);
            if (StringUtil.isNotEmptyCollection(profileCommunities)) {
                setupCommunityListAdapter(profileCommunities);
            }
        }
    }

    @OnClick(R.id.iv_back_profile)
    public void backOnclick() {
        finish();
    }

    private void setupCommunityListAdapter(List<UserSolrObj> champions) {
        mLayoutManager = new LinearLayoutManager(this);
        mCommunityRecycler.setLayoutManager(mLayoutManager);

        mAdapter = new ProfileFollowedMentorAdapter(this, profilePresenter, this);
        mCommunityRecycler.setHasFixedSize(true);

        mAdapter.setData(champions);
        mCommunityRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(UserSolrObj mentor) {

       /* Intent intent = new Intent(ProfileFollowedChampionActivity.this, ProfileDashboardActivity.class);
        Bundle bundle = new Bundle();
        //Parcelable parcelableFeedDetail = Parcels.wrap(mentor);
        //bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        bundle.putLong(AppConstants.CHAMPION_ID, mentor.getIdOfEntityOrParticipant());
        bundle.putBoolean(AppConstants.IS_MENTOR_ID, true);
        intent.putExtras(bundle);
        startActivity(intent);*/


        /*Intent intent = new Intent(this, ProfileDashboardActivity.class);
        Bundle bundle = new Bundle();
        FeedDetail mFeedDetail = mentor;
        Parcelable parcelableFeedDetail = Parcels.wrap(mFeedDetail);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(mentor);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        bundle.putLong(AppConstants.CHAMPION_ID, mentor.getIdOfEntityOrParticipant());
        intent.putExtra(AppConstants.IS_MENTOR_ID, true);
        intent.putExtras(bundle);
        startActivity(intent);*/

    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

}
