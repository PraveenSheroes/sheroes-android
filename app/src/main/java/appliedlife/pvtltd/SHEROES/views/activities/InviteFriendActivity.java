package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ContactListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SuggestedFriendFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 13/02/18.
 */

public class InviteFriendActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final String SCREEN_LABEL = "Invite Friend Screen";
    //region View variables
    @Bind(R.id.title_toolbar)
    TextView toolbarTitle;

    @Bind(R.id.toolbar)
    Toolbar mToolbarView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.viewpager_invite_friend)
    ViewPager mViewPager;

    @Bind(R.id.tabs_invite_friend)
    TabLayout mTabLayout;

    @Bind(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;

    //endregion

    //region Member variables
    private int mFromNotification;
    private ViewPagerAdapter mViewPagerAdapter;
    //endregion

    //region Activity method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }
    //endregion

    //region private methods
    private void initViews() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            Contest mContest = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.INVITE_FRIEND));
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
        }
        setPagerAndLayouts();
        setupToolbar();
        invalidateOptionsMenu();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbarTitle.setText(getString(R.string.invite_friend));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_invite_friend, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.share);
        menuItem.getIcon().mutate();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search_contact:
                break;
            case R.id.share:
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            if (mFromNotification > 0) {
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            }
        }
        finish();
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout,SCREEN_LABEL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbarView);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(SuggestedFriendFragment.createInstance(getString(R.string.suggested_friend)),getString(R.string.suggested_friend));
        mViewPagerAdapter.addFragment(ContactListFragment.createInstance(getString(R.string.contact_list_friend)),getString(R.string.contact_list_friend));
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //endregion

    //region Static methods
    public static void navigateTo(Activity fromActivity, int notificationId,String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, InviteFriendActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        intent.putExtras(bundle);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    //endregion
    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }
}
