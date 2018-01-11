package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunity;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileCommunityAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.FollowingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserMentorCommunity;
import appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment.SELF_PROFILE;
import static appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment.USER_MENTOR_ID;

/**
 * Created by ravi on 03/01/18.
 * Listing of Profile - User's Communities
 */

public class ProfileCommunitiesActivity extends BaseActivity {

    private static final String SCREEN_LABEL = "ProfileCommunitiesActivity Screen";

    @Inject
    AppUtils mAppUtils;

    @Inject
    ProfilePresenterImpl profilePresenter;

    private long userMentorId;

    private boolean isSelfProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            userMentorId = getIntent().getExtras().getLong(UserProfileTabFragment.USER_MENTOR_ID);
            isSelfProfile = getIntent().getExtras().getBoolean(UserProfileTabFragment.SELF_PROFILE);
        }


        Fragment followingFragment = UserMentorCommunity.createInstance(userMentorId, "", isSelfProfile);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.followed_mentor_container, followingFragment);
        fragmentTransaction.commit();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    //region static methods
    public static void navigateTo(Activity fromActivity, long mentorID, boolean isSelfProfile,  String sourceScreen, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, ProfileCommunitiesActivity.class);
        intent.putExtra(UserProfileTabFragment.USER_MENTOR_ID, mentorID);
        intent.putExtra(SELF_PROFILE, isSelfProfile);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, 1, null);
    }
}
