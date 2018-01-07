package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunity;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileCommunityAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 03/01/18.
 * Listing of Profile - User's Communities
 */

public class ProfileCommunitiesActivity extends BaseActivity implements ProfileCommunityAdapter.OnItemClicked{

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

    private ProfileCommunityAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        toolbarTitle.setText(R.string.ID_COMMUNITIES);

        Parcelable parcelable = getIntent().getParcelableExtra(AppConstants.COMMUNITY_ID);
        if (parcelable != null) {
            List<ProfileCommunity> profileCommunities = Parcels.unwrap(parcelable);
            if (StringUtil.isNotEmptyCollection(profileCommunities)) {
                setupCommunityListAdapter(profileCommunities);
            }
        }
    }

    @OnClick(R.id.iv_back_profile)
    public void backOnclick() {
        finish();
    }

    private void setupCommunityListAdapter(List<ProfileCommunity> profileCommunities) {
        mLayoutManager = new LinearLayoutManager(this);
        mCommunityRecycler.setLayoutManager(mLayoutManager);

        mAdapter = new ProfileCommunityAdapter(this, profilePresenter, this);
        mCommunityRecycler.setHasFixedSize(true);

        mAdapter.setData(profileCommunities);
        mCommunityRecycler.setAdapter(mAdapter);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void onItemClick( ProfileCommunity profileCommunity) {
        Intent intent = new Intent(ProfileCommunitiesActivity.this, CommunitiesDetailActivity.class);
        Bundle bundle = new Bundle();
        Parcelable parcelables = Parcels.wrap(profileCommunity);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelables);
        bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.MY_COMMUNITY);
        intent.putExtras(bundle);

        intent.putExtra(AppConstants.COMMUNITY_ID, profileCommunity.getEntityOrParticipantId());
        intent.putExtra(AppConstants.FROM_DEEPLINK, true);
        startActivity(intent);
    }
}
