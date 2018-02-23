package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FollowingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.FOLLOWED_CHAMPION_LABEL;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.FOLLOWERS;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.FOLLOWING;

/**
 * Created by ravi on 03/01/18.
 * Listing of Followed Champions
 */

public class FollowingActivity extends BaseActivity {

    public static final String MEMBERS_TYPE = "TYPE";
    private long userMentorId;
    private boolean isSelfProfile;
    private FollowingEnum mMembersType;

    @Bind(R.id.toolbar_name)
    TextView titleName;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            userMentorId = getIntent().getExtras().getLong(ProfileDetailsFragment.USER_MENTOR_ID);
            isSelfProfile = getIntent().getExtras().getBoolean(ProfileDetailsFragment.SELF_PROFILE);
            mMembersType = (FollowingEnum)getIntent().getSerializableExtra(MEMBERS_TYPE);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        if (mMembersType == null) return;

        String type = mMembersType.name();
        if (type.equalsIgnoreCase(AppConstants.FOLLOWED_CHAMPION)) {
            titleName.setText(R.string.champions_followed);
        } else if (type.equalsIgnoreCase(AppConstants.FOLLOWERS)) {
            if(isSelfProfile) {
                titleName.setText(R.string.follower_toolbar_title);
            } else{
                titleName.setText(R.string.follower_public_profile_toolbar_title);
            }
        }
        else if (type.equalsIgnoreCase(AppConstants.FOLLOWING)) {
            titleName.setText(R.string.following_toolbar_title);

            if(isSelfProfile) {
                titleName.setText(R.string.following_toolbar_title);
            } else{
                titleName.setText(R.string.following_public_profile_toolbar_title);
            }
        }

        Fragment followingFragment = FollowingFragment.createInstance(userMentorId, isSelfProfile, mMembersType.name());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, followingFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
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

    private void setResult() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    public String getScreenName() {
        String screenLabel = "";
        if(mMembersType!=null) {
            String type = mMembersType.name();
            if (type.equalsIgnoreCase(AppConstants.FOLLOWED_CHAMPION)) {
                screenLabel = FOLLOWED_CHAMPION_LABEL;
            } else if (type.equalsIgnoreCase(AppConstants.FOLLOWERS)) {
                screenLabel = FOLLOWERS;
            } else {
                screenLabel = FOLLOWING;
            }
        }
        return screenLabel;
    }

    //region static methods
    public static void navigateTo(Activity fromActivity, long mentorID, boolean isOwnProfile, String sourceScreen, FollowingEnum followingEnum, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, FollowingActivity.class);
        intent.putExtra(ProfileDetailsFragment.USER_MENTOR_ID, mentorID);
        intent.putExtra(ProfileDetailsFragment.SELF_PROFILE, isOwnProfile);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }

        intent.putExtra(MEMBERS_TYPE, followingEnum);
        ActivityCompat.startActivityForResult(fromActivity, intent, 1, null);
    }

}
