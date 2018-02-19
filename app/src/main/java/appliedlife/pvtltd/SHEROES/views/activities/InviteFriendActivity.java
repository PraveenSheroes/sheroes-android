package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.ContactListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
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
    public SearchView etInviteSearchBox = null;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.viewpager_invite_friend)
    ViewPager mViewPager;

    @Bind(R.id.tabs_invite_friend)
    TabLayout mTabLayout;

    @Bind(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;

    @Inject
    Preference<LoginResponse> mUserPreference;
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

    public void dataRequestForFragment(AllContactListResponse allContactListResponse) {
            Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, 0);
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((SuggestedFriendFragment) fragment).getAllSuggestedContacts();
            }
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
        upArrow.mutate();
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbarTitle.setText(getString(R.string.invite_friend));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_invite_friend, menu);

        MenuItem searchItem = menu.findItem(R.id.search_contact);

        SearchManager searchManager = (SearchManager) InviteFriendActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            etInviteSearchBox = (SearchView) searchItem.getActionView();
            String LEFT_HTML_TAG = "<font color='#ffffff'>";
            String RIGHT_HTML_TAG = "</font>";
            String search = LEFT_HTML_TAG + getString(R.string.ID_SEARCH) + AppConstants.DOTS + RIGHT_HTML_TAG;
            etInviteSearchBox.setQueryHint(Html.fromHtml(search));
            etInviteSearchBox.setIconifiedByDefault(false);
            ImageView searchIcon = etInviteSearchBox.findViewById(R.id.search_mag_icon);
            searchIcon.setImageDrawable(null);
            View v = etInviteSearchBox.findViewById(R.id.search_plate);
            v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.fully_transparent));
        }
        if (etInviteSearchBox != null) {
            etInviteSearchBox.setSearchableInfo(searchManager.getSearchableInfo(InviteFriendActivity.this.getComponentName()));
            editTextWatcher();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share:
                String appShareUrl;
                if (CommonUtil.isNotEmpty(mUserPreference.get().getUserSummary().getAppShareUrl())) {
                    appShareUrl = mUserPreference.get().getUserSummary().getAppShareUrl();
                } else {
                    appShareUrl = AppConstants.APP_SHARE_LINK;
                }
                ShareBottomSheetFragment.showDialog(this, appShareUrl, null, appShareUrl, SCREEN_LABEL, false, appShareUrl, false, true, true);
                AnalyticsManager.trackEvent(Event.APP_INVITE, getScreenName(), null);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void editTextWatcher() {
        etInviteSearchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String inputSearch) {
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, 1);
                mViewPager.setCurrentItem(1);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((ContactListFragment) fragment).searchContactInList(inputSearch);
                }
                return true;
            }
        });
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
        ViewCompat.setTransitionName(mAppBarLayout, SCREEN_LABEL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbarView);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(SuggestedFriendFragment.createInstance(getString(R.string.suggested_friend)), getString(R.string.suggested_friend));
        mViewPagerAdapter.addFragment(ContactListFragment.createInstance(getString(R.string.contact_list_friend)), getString(R.string.contact_list_friend));
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
    public static void navigateTo(Activity fromActivity, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
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
