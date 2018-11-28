package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.ContestPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.ContestInfoFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ContestWinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BellNotificationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ChallengeWinnerPopUpDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;

/**
 * Created by ujjwal on 11/20/17.
 */

public class ContestActivity extends BaseActivity implements IContestView, BaseHolderInterface {
    private static final String SCREEN_LABEL = "Challenge Activity";
    public static final String IS_CHALLENGE = "Is Challenge";
    public static final String CHALLENGE_OBJ = "Challenge Obj";
    private static int flagActivity = 0;
    private int FRAGMENT_INFO = 0;
    private int FRAGMENT_RESPONSES = 1;
    private int FRAGMENT_WINNER = 2;

    private FeedDetail mFeedDetail;
    //private FragmentOpen mFragmentOpen;
    private ContestInfoFragment mContestInfoFragment;

    @Inject
    AppUtils mAppUtils;

    @Inject
    ContestPresenterImpl mContestPresenter;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    FeedUtils feedUtils;

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
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    //endregion

    //region private member variable
    private FeedFragment mFeedFragment;
    private Contest mContest;
    private String mContestId;
    private long mUserId = -1L;
    boolean isMentor;
    private int mFromNotification;
    private ChallengeWinnerPopUpDialog mChallengeWinnerPopUpDialog = null;
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
        if (parcelable != null) {
            mContest = Parcels.unwrap(parcelable);
            populateContest(mContest);
        } else {
            if (getIntent() != null && getIntent().getExtras() != null) {
                mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
                mContestId = getIntent().getExtras().getString(Contest.CONTEST_ID);
            }
            if (CommonUtil.isNotEmpty(mContestId)) {
                FeedRequestPojo feedRequestPojo = mAppUtils.feedDetailRequestBuilder(AppConstants.CHALLENGE_SUB_TYPE_NEW, 1, Long.valueOf(mContestId));
                mContestPresenter.fetchContest(feedRequestPojo);
            } else {
                finish();
            }
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserId()) {
            mUserId = mUserPreference.get().getUserSummary().getUserId();

            if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                isMentor = true;
            }
        }
        DrawerViewHolder.selectedOptionName = AppConstants.NAV_CHALLENGE;
        // Initialize ViewPager
        if (mContest != null) {
            initializeAllViews();
        }
        setupToolbarItemsColor();
        feedUtils.setConfigurableShareOption(isWhatsAppShare());
    }

    private boolean isWhatsAppShare() {
        boolean isWhatsappShare = false;
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareText = "";
            shareText = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareText)) {
                if (shareText.equalsIgnoreCase("true")) {
                    isWhatsappShare = true;
                }
            }
        }
        return isWhatsappShare;
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

        int fragmentIndex = 0;/*getIntent().getIntExtra(ContestPreviewActivity.FRAGMENT_INDEX, -1);*/
        if (fragmentIndex != -1) {
            mTabLayout.getTabAt(fragmentIndex).select();
        } else {
            if (!mContest.hasMyPost) {
                mTabLayout.getTabAt(FRAGMENT_INFO).select();
            }
        }
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        toolbarTitle.setText(R.string.challenge);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int currentPage = mViewPager.getCurrentItem();
        if (currentPage == FRAGMENT_WINNER && mContest.isWinnerAnnounced) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    if (null != mContest) {
                        mContest.submissionCount++;
                        mContest.hasMyPost = true;
                        mTabLayout.getTabAt(FRAGMENT_RESPONSES).select();
                        mContestInfoFragment.setContest(mContest);
                        mFeedFragment.refreshList();
                        invalidateBottomBar(FRAGMENT_RESPONSES);
                        Intent intent = new Intent();
                        Parcelable parcelable = Parcels.wrap(mContest);
                        intent.putExtra(Contest.CONTEST_OBJ, parcelable);
                        setResult(RESULT_OK, intent);
                        ChallengeGratificationActivity.navigateTo(this, mContest, getScreenName(), null, requestCode);
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_ADDRESS:
                    Snackbar.make(mBottomBarView, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT).show();
                    if (null != mContest) {
                        mContest.mWinnerAddress = getString(R.string.not_empty);
                        mTabLayout.getTabAt(FRAGMENT_RESPONSES).select();
                        mContestInfoFragment.setContest(mContest);
                        mFeedFragment.refreshList();
                        invalidateBottomBar(FRAGMENT_WINNER);
                        Intent intentContest = new Intent();
                        Parcelable parcelableContest = Parcels.wrap(mContest);
                        intentContest.putExtra(Contest.CONTEST_OBJ, parcelableContest);
                        setResult(RESULT_OK, intentContest);
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                    boolean isPostDeleted = false;
                    UserPostSolrObj userPostSolrObj = null;
                    Parcelable parcelableUserPost = data.getParcelableExtra(FeedDetail.FEED_COMMENTS);
                    if (parcelableUserPost != null) {
                        userPostSolrObj = Parcels.unwrap(parcelableUserPost);
                        isPostDeleted = data.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                    }
                    if (userPostSolrObj == null) {
                        break;
                    }

                    if (mFeedFragment != null) {
                        if (isPostDeleted) {
                            mFeedFragment.removeItem(userPostSolrObj);
                        } else {
                            mFeedFragment.updateItem(userPostSolrObj);
                        }
                    }
                    break;
            }
        } else if (resultCode == AppConstants.RESULT_CODE_FOR_DEACTIVATION) {
            if (mFeedFragment != null) {
                mFeedFragment.refreshList();
            }
        } else if (resultCode == AppConstants.RESULT_CODE_FOR_PROFILE_FOLLOWED) {
            Parcelable parcelable = data.getParcelableExtra(AppConstants.USER_FOLLOWED_DETAIL);
            if (parcelable != null && mFeedFragment != null) {
                UserSolrObj userSolrObj = Parcels.unwrap(parcelable);
                mFeedFragment.findPositionAndUpdateItem(userSolrObj, userSolrObj.getIdOfEntityOrParticipant());
            }
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
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
        mFeedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Contest.CONTEST_OBJ, Parcels.wrap(mContest));
        bundle.putBoolean(IS_CHALLENGE, true);
        bundle.putString(AppConstants.SCREEN_NAME, "Challenge Responses");
        String endPointUrl = "participant/feed/community_feed?sub_type=P&source_entity_id=" + mContest.remote_id;
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Integer.toString(mContest.remote_id))
                        .challengeId(Integer.toString(mContest.remote_id))
                        .title(mContest.title)
                        .url(endPointUrl)
                        .build();
        bundle.putSerializable(FeedFragment.SCREEN_PROPERTIES, properties);
        bundle.putString(AppConstants.END_POINT_URL, endPointUrl);
        mFeedFragment.setArguments(bundle);
        mContestInfoFragment = (ContestInfoFragment) ContestInfoFragment.instance();
        mContestInfoFragment.setArguments(bundle);
        adapter.addFragment(mContestInfoFragment, getString(R.string.overview));
        adapter.addFragment(mFeedFragment, getString(R.string.responses));
        if (mContest.hasWinner) {
            ContestWinnerFragment mContestWinnerFragment = new ContestWinnerFragment();
            mContestWinnerFragment.setArguments(bundle);
            adapter.addFragment(mContestWinnerFragment, getString(R.string.winner));
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
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
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
            super.onBackPressed();
        } else {
            if (mFromNotification > 0) {
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            }
            if (flagActivity == 0/*ContestListActivity.CONTEST_LIST_ACTIVITY*/) {
                finish();
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mContestPresenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.share:
                if (mContest != null && mContest.shortUrl != null) {
                    String shareText = Config.COMMUNITY_POST_CHALLENGE_SHARE + System.getProperty("line.separator") + mContest.shortUrl;
                    HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .challengeId(Integer.toString(mContest.remote_id))
                                    .title(mContest.title)
                                    .build();
                    trackEvent(Event.CHALLENGE_SHARED_CLICKED, properties);
                    ShareBottomSheetFragment.showDialog(this, shareText, mContest.thumbImage, mContest.shortUrl, getScreenName(), true, mContest.shortUrl, true, Event.CHALLENGE_SHARED, properties);
                }
                break;
        }
        return true;
    }

    private void invalidateBottomBar(int position) {
        if (position == FRAGMENT_RESPONSES) {
            if (!mContest.hasMyPost && mContest.getContestStatus() == ContestStatus.ONGOING) {
                mBottomBar.setVisibility(View.VISIBLE);
                mBottomBarView.setVisibility(View.VISIBLE);
                mBottomView.setVisibility(View.VISIBLE);
                mBottomBar.setText(R.string.submit_response);
                mBottomBar.setTextColor(getResources().getColor(R.color.white));
                mBottomBarView.setBackgroundResource(R.color.red);
                mBottomBar.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                mBottomBar.setVisibility(View.GONE);
                mBottomBarView.setVisibility(View.GONE);
                mBottomView.setVisibility(View.GONE);
            }
        } else if (position == FRAGMENT_WINNER) {
            if (mContest.isWinner) {
                if (CommonUtil.isNotEmpty(mContest.mWinnerAddress)) {
                    mBottomBar.setVisibility(View.GONE);
                    mBottomBarView.setVisibility(View.GONE);
                    mBottomView.setVisibility(View.GONE);
                } else {
                    mBottomBar.setVisibility(View.VISIBLE);
                    mBottomBarView.setVisibility(View.VISIBLE);
                    mBottomView.setVisibility(View.VISIBLE);
                    mBottomBar.setTextColor(getResources().getColor(R.color.white));
                    mBottomBarView.setBackgroundResource(R.color.red);
                    mBottomBar.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mBottomBar.setText(R.string.title_send_address);
                }
            }
        } else {
            if (mContest.getContestStatus() == ContestStatus.COMPLETED) {
                mBottomBar.setVisibility(View.VISIBLE);
                mBottomBarView.setVisibility(View.VISIBLE);
                mBottomView.setVisibility(View.VISIBLE);
                mBottomBar.setText(R.string.contest_status_expired);
                mBottomBar.setTextColor(getResources().getColor(R.color.gray_light));
                mBottomBarView.setBackgroundResource(R.color.theme);
                mBottomBar.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else if (mContest.hasMyPost && mContest.getContestStatus() == ContestStatus.ONGOING) {
                mBottomBar.setVisibility(View.VISIBLE);
                mBottomBarView.setVisibility(View.VISIBLE);
                mBottomView.setVisibility(View.VISIBLE);
                mBottomBar.setText(R.string.completed);
                mBottomBar.setTextColor(getResources().getColor(R.color.light_green));
                mBottomBar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_contest_completed, 0, 0, 0);
                mBottomBarView.setBackgroundResource(R.color.theme);
            } else if (!mContest.hasMyPost && mContest.getContestStatus() == ContestStatus.ONGOING) {
                mBottomBar.setVisibility(View.VISIBLE);
                mBottomBarView.setVisibility(View.VISIBLE);
                mBottomView.setVisibility(View.VISIBLE);
                mBottomBar.setText(R.string.submit_response);
                mBottomBar.setTextColor(getResources().getColor(R.color.white));
                mBottomBarView.setBackgroundResource(R.color.red);
                mBottomBar.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                mBottomBar.setVisibility(View.GONE);
                mBottomBarView.setVisibility(View.GONE);
                mBottomView.setVisibility(View.GONE);
                mBottomBar.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
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
        invalidateBottomBar(FRAGMENT_INFO);
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            feedRelatedOptions(view, baseResponse);
        }
        if (baseResponse instanceof Comment) {
            // setAllValues(mFragmentOpen);
            /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            feedUtils.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU, this, getScreenName());
        }
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    private void feedRelatedOptions(View view, BaseResponse baseResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_feed_community_post_user_comment:
                feedUtils.feedCardsHandled(view, baseResponse, this, getScreenName());
                break;
            default:
                feedUtils.feedCardsHandled(view, baseResponse, this, getScreenName());

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

        int currentPage = mViewPager.getCurrentItem();
        if (currentPage == FRAGMENT_WINNER && mContest.isWinner) {
            Address address = new Address();
            address.challengeId = Integer.toString(mContest.remote_id);
            boolean isAddressUpdated = false;
            if (!CommonUtil.isNotEmpty(mContest.mWinnerAddress)) {
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Integer.toString(mContest.remote_id))
                                .title(mContest.title)
                                .build();
                trackEvent(Event.SEND_ADDRESS_CLICKED, properties);
                AddressActivity.navigateTo(this, getScreenName(), address, AppConstants.REQUEST_CODE_FOR_ADDRESS, isAddressUpdated, null);
            }
        } else if (currentPage == FRAGMENT_RESPONSES) {
            if (!mContest.hasMyPost && mContest.getContestStatus() == ContestStatus.ONGOING) {
                CommunityPost communityPost = new CommunityPost();
                communityPost.challengeId = mContest.remote_id;
                communityPost.challengeType = mContest.authorType;
                communityPost.isChallengeType = true;
                communityPost.challengeHashTag = mContest.tag;
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Integer.toString(mContest.remote_id))
                                .title(mContest.title)
                                .build();
                trackEvent(Event.CHALLENGE_SUBMIT_CLICKED, properties);
                CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
            }
        } else {
            if (!(mContest.hasMyPost || mContest.getContestStatus() == ContestStatus.COMPLETED)) {
                CommunityPost communityPost = new CommunityPost();
                communityPost.challengeId = mContest.remote_id;
                communityPost.challengeType = mContest.authorType;
                communityPost.isChallengeType = true;
                communityPost.challengeHashTag = mContest.tag;
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Integer.toString(mContest.remote_id))
                                .title(mContest.title)
                                .build();
                trackEvent(Event.CHALLENGE_SUBMIT_CLICKED, properties);
                CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false, null);
            }
        }
    }

    public void bookmarkPost(FeedDetail feedDetail) {
        mContestPresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(feedDetail.getEntityOrParticipantId()), feedDetail.isBookmarked());
    }
    //endregion

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
        if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            if (!comment.isAnonymous()) {
                championDetailActivity(comment.getParticipantId(), 0, comment.isVerifiedMentor(), SOURCE_SCREEN);
            }
        } else if (mValue == AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL) {
            if (mUserId != -1) {
                championDetailActivity(mUserId, 1, isMentor, SOURCE_SCREEN); //self profile
            }
        } else if (baseResponse instanceof UserPostSolrObj) {
            UserPostSolrObj postDetails = (UserPostSolrObj) baseResponse;
            if (!postDetails.isAnonymous()) {
                championDetailActivity(postDetails.getCreatedBy(), 0, postDetails.isAuthorMentor(), SOURCE_SCREEN);
            }
        }
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }

    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    public void openChallengeWinnerPopUpDialog(FeedDetail feedDetail) {
        mChallengeWinnerPopUpDialog = (ChallengeWinnerPopUpDialog) getFragmentManager().findFragmentByTag(ChallengeWinnerPopUpDialog.class.getName());
        if (mChallengeWinnerPopUpDialog == null) {
            mChallengeWinnerPopUpDialog = new ChallengeWinnerPopUpDialog();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(feedDetail);
            bundle.putParcelable(BaseDialogFragment.CHALLENGE_WINNER, parcelable);
            mChallengeWinnerPopUpDialog.setArguments(bundle);
        }
        if (!mChallengeWinnerPopUpDialog.isVisible() && !mChallengeWinnerPopUpDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            mChallengeWinnerPopUpDialog.show(getFragmentManager(), BellNotificationDialogFragment.class.getName());
        }
    }
}
