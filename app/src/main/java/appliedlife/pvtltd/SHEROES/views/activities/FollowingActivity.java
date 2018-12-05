package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FollowingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.UnFollowDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowCallback;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.FOLLOWED_CHAMPION_LABEL;

/**
 * Created by ravi on 03/01/18.
 * Listing of Followed Champions
 */

public class FollowingActivity extends BaseActivity implements IFollowCallback {
    // region Constants
    public static final String Followers_Screen = "Followers Screen";
    public static final String Following_Screen = "Following Screen";
    public static final String MEMBERS_TYPE = "TYPE";
    //endregion

    // region views
    @Bind(R.id.title_toolbar)
    TextView mTitleName;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    //endregion

    // region member variables
    private long mUserMentorId;
    private boolean mIsSelfProfile;
    private FollowingEnum mMembersType;
    FollowingFragment mFollowingFragment;
    private UnFollowDialogFragment mUnFollowDialogFragment;
    //endregion

    // region lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            mUserMentorId = getIntent().getExtras().getLong(ProfileDetailsFragment.USER_MENTOR_ID);
            mIsSelfProfile = getIntent().getExtras().getBoolean(ProfileDetailsFragment.SELF_PROFILE);
            mMembersType = (FollowingEnum) getIntent().getSerializableExtra(MEMBERS_TYPE);
        }
        setupToolbarItemsColor();
        if (mMembersType == null) return;
        if (mMembersType == FollowingEnum.FOLLOWED_CHAMPIONS) {
            mTitleName.setText(R.string.champions_followed);
        } else if (mMembersType == FollowingEnum.FOLLOWERS) {
            if (mIsSelfProfile) {
                mTitleName.setText(R.string.follower_toolbar_title);
            } else {
                mTitleName.setText(R.string.follower_public_profile_toolbar_title);
            }
        } else if (mMembersType == FollowingEnum.FOLLOWING) {
            if (mIsSelfProfile) {
                mTitleName.setText(R.string.following_toolbar_title);
            } else {
                mTitleName.setText(R.string.following_public_profile_toolbar_title);
            }
        }
        mFollowingFragment = FollowingFragment.createInstance(mUserMentorId, mIsSelfProfile, mMembersType.name());
        addNewFragment(mFollowingFragment, R.id.container, FollowingFragment.class.getName(), null, false);
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /* 2:- For refresh list */
        if (null != intent) {
            if (resultCode == AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED) {
                Parcelable parcelable = intent.getParcelableExtra(AppConstants.USER_FOLLOWED_DETAIL);
                if (parcelable != null) {
                    UserSolrObj userSolrObj = Parcels.unwrap(parcelable);

                    if (mFollowingFragment == null) {
                        return;
                    }
                    mFollowingFragment.invalidateItem(userSolrObj);
                }
            }
        }
    }
    //endregion

    // region public methods
    @Override
    public String getScreenName() {
        String screenLabel = "";
        if (mMembersType != null) {
            if (mMembersType == FollowingEnum.FOLLOWED_CHAMPIONS) {
                screenLabel = FOLLOWED_CHAMPION_LABEL;
            } else if (mMembersType == FollowingEnum.FOLLOWERS) {
                screenLabel = Followers_Screen;
            } else if (mMembersType == FollowingEnum.FOLLOWING) {
                screenLabel = Following_Screen;
            }
        }
        return screenLabel;
    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
    }
    //endregion

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
    //endregion

    // region protected methods
    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }
    //endregion

    // region private methods
    private void setResult() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onProfileFollowed(UserSolrObj userSolrObj) {
        if(mFollowingFragment!=null && mFollowingFragment.isVisible()) {
            mFollowingFragment.refreshItem(userSolrObj);
        }
    }

    public void unFollowConfirmation(PublicProfileListRequest publicProfileListRequest, final UserSolrObj userSolrObj, String screenName) {
        if (mUnFollowDialogFragment != null && mUnFollowDialogFragment.isVisible()) {
            mUnFollowDialogFragment.dismiss();
        }
        mUnFollowDialogFragment = new UnFollowDialogFragment();
        mUnFollowDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        if (!mUnFollowDialogFragment.isVisible() && !mIsDestroyed) {
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(userSolrObj);
            bundle.putParcelable(AppConstants.USER, parcelable);
            Parcelable unFollowRequestParcelable = Parcels.wrap(publicProfileListRequest);
            bundle.putParcelable(AppConstants.UNFOLLOW, unFollowRequestParcelable);
            bundle.putString(AppConstants.SOURCE_NAME, screenName);
            bundle.putBoolean(AppConstants.IS_CHAMPION_ID, userSolrObj.isAuthorMentor());
            bundle.putBoolean(AppConstants.IS_SELF_PROFILE, false);
            mUnFollowDialogFragment.setArguments(bundle);
            mUnFollowDialogFragment.show(getFragmentManager(), UnFollowDialogFragment.class.getName());
        }
    }
    //endregion
}
