package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FollowingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserProfileTabFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 03/01/18.
 * Listing of Profile - User's champion
 */

public class FollowingActivity extends BaseActivity {

    private static final String SCREEN_LABEL = "Followed Communities";
    private long userMentorId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            userMentorId = getIntent().getExtras().getLong(UserProfileTabFragment.USER_MENTOR_ID);
        }

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
        intent.putExtra(UserProfileTabFragment.USER_MENTOR_ID, mentorID);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, 1, null);
    }

}
