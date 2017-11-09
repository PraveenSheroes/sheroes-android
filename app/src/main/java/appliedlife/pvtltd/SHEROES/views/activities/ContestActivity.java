package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.ContestPresenterImpl;
import appliedlife.pvtltd.SHEROES.presenters.CreatePostPresenter;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.views.fragments.ContestInfoFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ContestWinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 01/05/17.
 */

public class ContestActivity extends BaseActivity implements IContestView {
    private static final String SCREEN_LABEL = "Contest Activity";
    private static int flagActivity = 0;
    private int FRAGMENT_RESPONSES = 0;
    private int FRAGMENT_INFO = 1;
    private int FRAGMENT_WINNER = 2;

    @Inject
    ContestPresenterImpl mContestPresenter;

    //region Bind view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbarView;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.title_toolbar)
    TextView toolbarTitle;

    @Bind(R.id.bottom_bar)
    FrameLayout mBottomBarView;

    @Bind(R.id.btn_bottom_bar)
    Button mBottomBar;
    //endregion

    //region private member variable
    private HomeFragment mHomeFragment;;
    private Contest mContest;
    private String mContestId;
    //endregion

    //region Activity methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_contest);
        ButterKnife.bind(this);
        mContestPresenter.attachView(this);
        Parcelable parcelable = getIntent().getParcelableExtra(Contest.CONTEST_OBJ);
        int fragmentIndex = 0;/*getIntent().getIntExtra(ContestPreviewActivity.FRAGMENT_INDEX, -1);*/
        if (parcelable != null) {
            mContest = Parcels.unwrap(parcelable);
            populateContest(mContest);
        } else {
            if (getIntent().getExtras() != null) {
                mContestId = getIntent().getExtras().getString(Contest.CONTEST_ID);
            }
            if (CommonUtil.isNotEmpty(mContestId)) {
                mContestPresenter.fetchContest(mContestId);
            }
        }

        // Initialize ViewPager
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(mContest.title);
        if (fragmentIndex != -1) {
            mTabLayout.getTabAt(fragmentIndex).select();
        } else {
            if (!mContest.hasMyPost) {
                mTabLayout.getTabAt(FRAGMENT_INFO).select();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int currentPage = mViewPager.getCurrentItem();
        if (currentPage == FRAGMENT_WINNER && mContest.isWinner) {
      /*      if (CareServiceHelper.getUser().contestAddress == null) {
                mBottomBar.setText(R.string.send_address);
            } else {
                mBottomBar.setText(R.string.change_address);
            }*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
             /*   case WriteSubmissionActivity.ADD_SUBMISSION:
                    Snackbar.make(mBottomBarView, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT)
                            .show();
                    mContest.submissionCount++;
                    break;
                case WriteSubmissionActivity.EDIT_SUBMISSION:
                    Snackbar.make(mBottomBarView, R.string.snackbar_submission_edited, Snackbar.LENGTH_SHORT)
                            .show();*/
            }
            mContest.hasMyPost = true;
            mTabLayout.getTabAt(FRAGMENT_RESPONSES).select();
            //mHomeFragment.setContest(mContest);
            invalidateBottomBar(FRAGMENT_RESPONSES);
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    //endregion

    //region private methods
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        mHomeFragment = new HomeFragment();
        ContestInfoFragment mContestInfoFragment = (ContestInfoFragment) ContestInfoFragment.instance();
        ContestWinnerFragment mContestWinnerFragment = new ContestWinnerFragment();
        adapter.addFragment(mHomeFragment, "Responses");
        adapter.addFragment(mContestInfoFragment, "Info");
        adapter.addFragment(mContestWinnerFragment, "Winner");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                supportInvalidateOptionsMenu();
                invalidateBottomBar(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        final EventProperty.Builder builder = new EventProperty.Builder();
        if (mContest != null) {
            builder.title(mContest.title)
                    .id(Integer.toString(mContest.remote_id));

        }
        HashMap<String, Object> properties = builder.build();
        return properties;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                    onBackPressed();
                } else {
                    if (flagActivity == 0/*ContestListActivity.CONTEST_LIST_ACTIVITY*/) {
                        finish();
                    } else {
                        NavUtils.navigateUpFromSameTask(this);
                    }
                }
                break;
        }
        return true;
    }

    private void invalidateBottomBar(int position) {
        if (position == FRAGMENT_RESPONSES) {
            if (mContest.hasMyPost) {
                mBottomBar.setText(R.string.view_response);
            } else {
                mBottomBar.setText(R.string.submit_response);
            }
        } else if (position == FRAGMENT_INFO) {
            if (mContest.hasMyPost) {
                mBottomBar.setText(R.string.view_response);
            } else {
                mBottomBar.setText(R.string.submit_response);
            }
        } else if (position == FRAGMENT_WINNER) {
            if (mContest.isWinner) {
               /* if (CareServiceHelper.getUser().contestAddress == null) {
                    mBottomBar.setText(R.string.send_address);
                } else {
                    mBottomBar.setText(R.string.change_address);
                }*/
            }
        }
    }
    //endregion

    //region static methods
    public static void navigateTo(Activity fromActivity, Contest contest, String sourceScreen, HashMap<String, Object> properties, int flagActivity, int fragmentIndex) {
        Intent intent = new Intent(fromActivity, ContestActivity.class);
        Parcelable parcelable = Parcels.wrap(contest);
        intent.putExtra(Contest.CONTEST_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        ContestActivity.flagActivity = flagActivity;
        intent.putExtra(/*ContestPreviewActivity.*/"FRAGMENT_INDEX", fragmentIndex);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivity(fromActivity, intent, null);
    }

    @Override
    public void populateContest(Contest contest) {
        mContest = contest;
        if (contest == null) {
            return;
        }
        if (CommonUtil.getContestStatus(mContest.startAt, mContest.endAt) == ContestStatus.COMPLETED) {
            if (!contest.hasMyPost) {
                mBottomBar.setVisibility(View.GONE);
                mBottomBarView.setVisibility(View.GONE);
            }
        }
        invalidateBottomBar(FRAGMENT_RESPONSES);
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    //endregion

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    //region click methods
    @OnClick({R.id.bottom_bar, R.id.btn_bottom_bar})
    public void onBottomBarClicked() {
 /*       int currentPage = mViewPager.getCurrentItem();
        if (currentPage == FRAGMENT_WINNER && mContest.isWinner) {
            AddressActivity.navigateTo(this, getScreenName(), CareServiceHelper.getUser().contestAddress, null);
        } else {
            if (mContest.hasMyPost) {
                mTabLayout.getTabAt(FRAGMENT_RESPONSES).select();
                mContestResponsesFragment.scrollToMySubmission();
            } else {
                Submission submission = new Submission();
                submission.contestId = mContest.remote_id;
                WriteSubmissionActivity.navigateTo(this, submission, -1, getScreenName(), null, true);
            }
        }*/
    }
    //endregion
}
