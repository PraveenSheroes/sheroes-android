package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_COMMUNITY_LISTING;

/**
 * Created by ravi on 05/02/18.
 */

public class CollectionActivity extends BaseActivity {

    private static String SCREEN_LABEL = "Collection Activity";
    private String endPointUrl;
    private String title;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_name)
    TextView titleName;

    private List<FeedDetail> feedDetailList;
    private int position = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            endPointUrl = getIntent().getExtras().getString(AppConstants.END_POINT_URL);
            title = getIntent().getExtras().getString(AppConstants.TOOLBAR_TITTE);
        }

        setupToolbar(title);

        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.END_POINT_URL, endPointUrl);
        bundle.putString(AppConstants.SCREEN_NAME, SCREEN_LABEL);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        feedFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, feedFragment);
        fragmentTransaction.commit();

        ((SheroesApplication) getApplication()).trackScreenView(SCREEN_LABEL);
    }

    private void setupToolbar(String toolbarTitle) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        if(StringUtil.isNotNullOrEmptyString(toolbarTitle)) {
            titleName.setText(toolbarTitle);
        }
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


    public void setData(List<FeedDetail> feedDetails) {
        feedDetailList = feedDetails;
    }

    @Override
    public void onBackPressed() {
        if(feedDetailList!=null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, Parcels.wrap(feedDetailList));
            intent.putExtras(bundle);
            setResult(REQUEST_CODE_FOR_COMMUNITY_LISTING, intent);
        }
        finish();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public static void navigateTo(Activity fromActivity, String endPointUrl, String toolbarTitle, String sourceScreen, String screenName, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, CollectionActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.END_POINT_URL, endPointUrl);
        intent.putExtra(AppConstants.TOOLBAR_TITTE, toolbarTitle);
        intent.putExtra(AppConstants.SCREEN_NAME, screenName);

        if(CommonUtil.isNotEmpty(screenName)) {
            SCREEN_LABEL = screenName;
        }

        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
}
