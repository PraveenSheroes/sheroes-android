package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserGridFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 04/09/18.
 */

public class UsersCollectionActivity extends BaseActivity {

    //region Private Variables
    private static String SCREEN_LABEL = "User Collection Activity";
    private String mEndPointUrl;
    private String mTitle;
    private List<FeedDetail> mFeedDetailList = null;
    private List<FeedDetail> mDirtyListItems;
    private HashMap<String, Object> properties = null;
    //endregion

    //region Bind view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.title_toolbar)
    TextView titleName;
    //endregion

    //region Activity methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            mEndPointUrl = getIntent().getExtras().getString(AppConstants.END_POINT_URL);
            mTitle = getIntent().getExtras().getString(AppConstants.TOOLBAR_TITTE);
            properties =
                    new EventProperty.Builder()
                            .collectionName(mTitle)
                            .url(mEndPointUrl)
                            .build();
        }

        setupToolbar(mTitle);

        UserGridFragment feedFragment = new UserGridFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.END_POINT_URL, mEndPointUrl);
        bundle.putSerializable(FeedFragment.SCREEN_PROPERTIES, properties);
        bundle.putString(AppConstants.SCREEN_NAME, mTitle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        feedFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, feedFragment, UserGridFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDirtyListItems != null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.USER_FOLLOWED_DETAIL, Parcels.wrap(mDirtyListItems));
            intent.putExtras(bundle);
            setResult(AppConstants.REQUEST_CODE_FOR_USER_LISTING, intent);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED) {
            Parcelable parcelable = intent.getParcelableExtra(AppConstants.USER_FOLLOWED_DETAIL);
            if (parcelable != null) {
                UserSolrObj userSolrObj = Parcels.unwrap(parcelable);
                invalidateItem(userSolrObj);
            }
        }
    }

    private void invalidateItem(UserSolrObj userSolrObj) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(UserGridFragment.class.getName());
        if (fragment != null) {
            ((UserGridFragment) fragment).invalidateItem(userSolrObj);
        }
    }


    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    protected boolean trackScreenTime() {
        return false;
    }

    //endregion

    //region private method
    private void setupToolbar(String toolbarTitle) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        if (StringUtil.isNotNullOrEmptyString(toolbarTitle)) {
            titleName.setText(toolbarTitle);
        }
    }

    public void setData(List<FeedDetail> feedDetails) {
        if (StringUtil.isNotEmptyCollection(feedDetails)) {
            mFeedDetailList = feedDetails;
        }
    }

    public void updateTopCarouselUsers(Set<FeedDetail> feedDetails) {
        if (StringUtil.isNotEmptyCollection(feedDetails)) {
            if (mDirtyListItems == null) {
                mDirtyListItems = new ArrayList<>();
            }
            mDirtyListItems.addAll(feedDetails);
        }
    }

    //endregion

    //region static method
    public static void navigateTo(Activity fromActivity, String endPointUrl, String toolbarTitle, String sourceScreen, String screenName, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, UsersCollectionActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.END_POINT_URL, endPointUrl);
        intent.putExtra(AppConstants.TOOLBAR_TITTE, toolbarTitle);
        intent.putExtra(AppConstants.SCREEN_NAME, screenName);

        if (CommonUtil.isNotEmpty(screenName)) {
            SCREEN_LABEL = screenName;
        }

        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    //endregion

}
