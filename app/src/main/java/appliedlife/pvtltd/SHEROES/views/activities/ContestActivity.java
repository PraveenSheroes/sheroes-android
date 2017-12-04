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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.ContestPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ContestInfoFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;

/**
 * Created by ujjwal on 11/20/17.
 */

public class ContestActivity extends BaseActivity implements IContestView,CommentReactionFragment.HomeActivityIntractionListner {
    private static final String SCREEN_LABEL = "Challenge Activity";
    public static final String IS_CHALLENGE = "Is Challenge";
    public static final String CHALLENGE_OBJ = "Challenge Obj";
    private static int flagActivity = 0;
    private int FRAGMENT_INFO = 0;
    private int FRAGMENT_RESPONSES = 1;
    private int FRAGMENT_WINNER = 2;

    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    private ContestInfoFragment mContestInfoFragment;

    @Inject
    AppUtils mAppUtils;

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


    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.bottom_bar)
    FrameLayout mBottomBarView;

    @Bind(R.id.btn_bottom_bar)
    Button mBottomBar;

    @Bind(R.id.bottom_view)
    View mBottomView;
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
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        Parcelable parcelable = getIntent().getParcelableExtra(Contest.CONTEST_OBJ);
        if (parcelable != null) {
            mContest = (Contest) Parcels.unwrap(parcelable);
            populateContest(mContest);
        } else {
            if (getIntent().getExtras() != null) {
                mContestId = getIntent().getExtras().getString(Contest.CONTEST_ID);
            }
            if (CommonUtil.isNotEmpty(mContestId)) {
                FeedRequestPojo feedRequestPojo = mAppUtils.feedDetailRequestBuilder(AppConstants.CHALLENGE_SUB_TYPE_NEW, 1, Long.valueOf(mContestId));
                mContestPresenter.fetchContest(feedRequestPojo);
            } else {
                finish();
            }
        }

        // Initialize ViewPager
        if(mContest!=null){
            initializeAllViews();
        }
    }

    private void initializeAllViews() {
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
        if (mContest != null) {
            toolbarTitle.setText("Challenge");
        }
        int fragmentIndex = 0;/*getIntent().getIntExtra(ContestPreviewActivity.FRAGMENT_INDEX, -1);*/
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
        if (currentPage == FRAGMENT_WINNER && mContest.isWinnerAnnounced) {
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
            Snackbar.make(mBottomBarView, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT)
                    .show();
            mContest.submissionCount++;
            mContest.hasMyPost = true;
            mTabLayout.getTabAt(FRAGMENT_RESPONSES).select();
            mContestInfoFragment.setContest(mContest);
            mHomeFragment.onRefreshClick();
            invalidateBottomBar(FRAGMENT_RESPONSES);
            Intent intent = new Intent();
            Parcelable parcelable = Parcels.wrap(mContest);
            intent.putExtra(Contest.CONTEST_OBJ, parcelable);
            setResult(RESULT_OK, intent);
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

        if (mViewPager.getCurrentItem() == 1) {
            mHomeFragment.likeAndUnlikeRequest(baseResponse, reactionValue, position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_challenge_detal, menu);
        setUpOptionMenuStates(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setUpOptionMenuStates(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void setUpOptionMenuStates(Menu menu) {
        MenuItem itemShare = menu.findItem(R.id.share);
        itemShare.setVisible(true);
    }
    //endregion

    //region private methods
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        mHomeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHALLENGE_OBJ, Parcels.wrap(mContest));
        bundle.putBoolean(IS_CHALLENGE, true);
        mHomeFragment.setArguments(bundle);
        mContestInfoFragment = (ContestInfoFragment) ContestInfoFragment.instance();
        //ContestWinnerFragment mContestWinnerFragment = new ContestWinnerFragment();
        adapter.addFragment(mContestInfoFragment, "Overview");
        adapter.addFragment(mHomeFragment, "Responses");
        /*if (mContest.hasWinner) {
            adapter.addFragment(mContestWinnerFragment, "Winner");
        }*/
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
            case R.id.share:
                String shareText = Config.COMMUNITY_POST_CHALLENGE_SHARE + System.getProperty("line.separator") + mContest.shortUrl;
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Integer.toString(mContest.remote_id))
                                .build();
                trackEvent(Event.CHALLENGE_SHARED, properties);
                ShareBottomSheetFragment.showDialog(this, shareText, mContest.thumbImage, mContest.shortUrl, SOURCE_SCREEN, true, mContest.shortUrl, true);
                break;
        }
        return true;
    }

    private void invalidateBottomBar(int position) {
        if (mContest.hasMyPost || mContest.getContestStatus() == ContestStatus.COMPLETED) {
            mBottomBar.setVisibility(View.VISIBLE);
            mBottomBarView.setVisibility(View.VISIBLE);
            mBottomView.setVisibility(View.VISIBLE);
            mBottomBar.setText(R.string.contest_status_expired);
            mBottomBar.setTextColor(getResources().getColor(R.color.gray_light));
            mBottomBarView.setBackgroundResource(R.color.theme);
        } else {
            mBottomBar.setVisibility(View.VISIBLE);
            mBottomBarView.setVisibility(View.VISIBLE);
            mBottomView.setVisibility(View.VISIBLE);
            mBottomBar.setText(R.string.submit_response);
            mBottomBar.setTextColor(getResources().getColor(R.color.white));
            mBottomBarView.setBackgroundResource(R.color.red);
            if (position == FRAGMENT_RESPONSES) {
                mBottomBar.setText(R.string.submit_response);
            } else if (position == FRAGMENT_INFO) {
                mBottomBar.setText(R.string.submit_response);
            } else if (position == FRAGMENT_WINNER) {
                if (mContest.isWinnerAnnounced) {
                }
            }
        }

    }
    //endregion

    //region static methods
    public static void navigateTo(Activity fromActivity, Contest contest, String sourceScreen, HashMap<String, Object> properties, int flagActivity, int fragmentIndex, int requestCode) {
        Intent intent = new Intent(fromActivity, ContestActivity.class);
        Parcelable parcelable = Parcels.wrap(contest);
        intent.putExtra(Contest.CONTEST_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        ContestActivity.flagActivity = flagActivity;
        intent.putExtra(/*ContestPreviewActivity.*/"FRAGMENT_INDEX", fragmentIndex);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, String contestId, String sourceScreen, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, ContestActivity.class);
        intent.putExtra(Contest.CONTEST_ID, contestId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, 1, null);
    }

    public void populateContest(Contest contest) {
        if (contest == null) {
            return;
        }
        invalidateBottomBar(FRAGMENT_RESPONSES);
    }

    @Override
    public void showContestFromId(Contest contest) {
        mContest = contest;
        initializeAllViews();
        mContestInfoFragment.setContest(contest);
        populateContest(contest);
    }

    @Override
    public void startProgressBar() {
        mBottomBarView.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        mTabLayout.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
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

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            feedRelatedOptions(view, baseResponse);
        }
        if(baseResponse instanceof Comment){
            setAllValues(mFragmentOpen);
             /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU);
        }
    }

    private void feedRelatedOptions(View view, BaseResponse baseResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_approve_spam_post:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).approveSpamPost(mFeedDetail, true, false, true);
                }
                break;
            case R.id.tv_delete_spam_post:
                Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(homeFragment)) {
                    ((HomeFragment) homeFragment).approveSpamPost(mFeedDetail, true, true, false);
                }
                break;
            case R.id.tv_feed_community_post_user_comment:
                mFragmentOpen = new FragmentOpen();
                mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FIFTH_CONSTANT);
                mFragmentOpen.setOwner(mFeedDetail.isCommunityOwner());
                setAllValues(mFragmentOpen);
                super.feedCardsHandled(view, baseResponse);
                break;
                default:
                    super.feedCardsHandled(view, baseResponse);

        }
    }

    @Override
    protected void openCommentReactionFragment(FeedDetail feedDetail) {
        clickCommentReactionFragment(feedDetail);

    }

    private void clickCommentReactionFragment(FeedDetail feedDetail) {
        mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FIFTH_CONSTANT);
        CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
        Bundle bundleArticle = new Bundle();
        bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
        bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
        commentReactionFragmentForArticle.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().replace(R.id.comment_from_contest, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPress();
    }

    public void onBackPress() {
        if (mFragmentOpen.isCommentList()) {
            mFragmentOpen.setCommentList(false);
            getSupportFragmentManager().popBackStackImmediate();
            if (mViewPager.getCurrentItem() == 1) {
                mHomeFragment.commentListRefresh(mFeedDetail, COMMENT_REACTION);
            }
        }
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setReactionList(false);
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.FIFTH_CONSTANT);
            setAllValues(mFragmentOpen);
            clickCommentReactionFragment(feedDetail);
        }
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
        if (mContest == null) {
            return;
        }
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Integer.toString(mContest.remote_id))
                        .title(mContest.title)
                        .build();
        trackEvent(Event.CHALLENGE_SUBMIT_CLICKED, properties);

        int currentPage = mViewPager.getCurrentItem();
        if (currentPage == FRAGMENT_WINNER && mContest.isWinner) {
           // AddressActivity.navigateTo(this, getScreenName(), CareServiceHelper.getUser().contestAddress, null);
        } else {
            if (mContest.hasMyPost) {
                mTabLayout.getTabAt(FRAGMENT_RESPONSES).select();
                mHomeFragment.scrollToMySubmission();
            } else {
                CommunityPost communityPost = new CommunityPost();
                communityPost.challengeId = mContest.remote_id;
                communityPost.challengeType = mContest.authorType;
                communityPost.isChallengeType = true;
                communityPost.challengeHashTag = mContest.tag;
                CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false);
            }
        }
    }
    //endregion
}
