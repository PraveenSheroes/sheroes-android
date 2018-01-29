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
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FollowingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 03/01/18.
 * Listing of Followed Champions
 */

public class FollowingActivity extends BaseActivity {

    private static final String SCREEN_LABEL = "Followed Champions Screen";
    private long userMentorId;

    @Bind(R.id.tv_mentor_toolbar_name)
    TextView titleName;

    @Bind(R.id.community_toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            userMentorId = getIntent().getExtras().getLong(ProfileDetailsFragment.USER_MENTOR_ID);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        titleName.setText(R.string.champions_followed);

        Fragment followingFragment = FollowingFragment.createInstance(userMentorId, "");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.followed_mentor_container, followingFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
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
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    //region static methods
    public static void navigateTo(Activity fromActivity, long mentorID, String sourceScreen, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, FollowingActivity.class);
        intent.putExtra(ProfileDetailsFragment.USER_MENTOR_ID, mentorID);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, 1, null);
    }

}
