package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.CommunityDetailPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityDetailView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 27/12/17.
 */

public class CommunityDetailActivity extends BaseActivity implements ICommunityDetailView{
    public static final String SCREEN_LABEL = "Community Detail Activity";

    public enum TabType {
        NAVTIVE("native"),
        WEB("web"),
        HTML("html");

        public String tabType;

        TabType(String tabType) {
            this.tabType = tabType;
        }

        public String getName() {
            return tabType;
        }
    }

    @Inject
    CommunityDetailPresenterImpl mCommunityDetailPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.title_toolbar)
    TextView mTitleToolbar;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.fab)
    FloatingActionButton mFabButton;

    private CommunityFeedSolrObj mCommunityFeedSolrObj;
    private List<Fragment> mTabFragments = new ArrayList<>();
    private Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_community_detail);
        ButterKnife.bind(this);
        mCommunityDetailPresenter.attachView(this);

        if(getIntent()!=null && getIntent().getExtras()!=null){
            Parcelable parcelable = getIntent().getParcelableExtra(CommunityFeedSolrObj.COMMUNITY_OBJ);
            if(parcelable!=null){
                mCommunityFeedSolrObj = Parcels.unwrap(parcelable);
            }
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitleToolbar.setText(mCommunityFeedSolrObj.getNameOrTitle());

        setupColorTheme();
        setupViewPager(mViewPager);
        setupTabLayout();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    Snackbar.make(mFabButton, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT)
                            .show();
                   refreshAllFragment();
                    break;

                case AppConstants.REQUEST_CODE_FOR_JOB_DETAIL:
                    if (null != data && null != data.getExtras()) {
                        JobFeedSolrObj jobFeedSolrObj = null;
                        jobFeedSolrObj = (JobFeedSolrObj) Parcels.unwrap(data.getParcelableExtra(AppConstants.JOB_FRAGMENT));
                        invalidateItem(jobFeedSolrObj);
                    }
                    break;

                case AppConstants.REQUEST_CODE_FOR_CHALLENGE_DETAIL:
                    if (resultCode == Activity.RESULT_OK) {
                       refreshAllFragment();
                    }
                    break;
/*
                case AppConstants.REQUEST_CODE_FOR_ADDRESS:
                    Snackbar.make(mBottomBarView, R.string.snackbar_submission_submited, Snackbar.LENGTH_SHORT)
                            .show();
                    mContest.mWinnerAddress = "not empty";
                    mTabLayout.getTabAt(FRAGMENT_RESPONSES).select();
                    mContestInfoFragment.setContest(mContest);
                    mHomeFragment.onRefreshClick();
                    invalidateBottomBar(FRAGMENT_WINNER);
                    Intent intentContest = new Intent();
                    Parcelable parcelableContest = Parcels.wrap(mContest);
                    intentContest.putExtra(Contest.CONTEST_OBJ, parcelableContest);
                    setResult(RESULT_OK, intentContest);
                    break;*/

                case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                    boolean isPostDeleted = false;
                    UserPostSolrObj userPostSolrObj = null;
                        Parcelable parcelableUserPost = data.getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
                        if (parcelableUserPost != null) {
                            userPostSolrObj = Parcels.unwrap(parcelableUserPost);
                            isPostDeleted = data.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                        }
                        if(userPostSolrObj == null) {
                            break;
                        }
                        if(isPostDeleted){
                         notifyAllItemRemoved(userPostSolrObj);
                        }else {
                            invalidateItem(userPostSolrObj);
                        }
                    }
            }
    }

    private void setupTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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
               /* if (tab.getPosition() == FRAGMENT_NEWSFEED) {
                    mNewsfeedFragment.scrollToTopPosition();
                } else if (tab.getPosition() == FRAGMENT_CHAT) {
                    mChatsListFragment.scrollToTopPosition();
                }*/
            }
        });
    }

    private void setupColorTheme() {
        mCommunityFeedSolrObj.titleTextColor = "#ffffff";
        if (CommonUtil.isNotEmpty(mCommunityFeedSolrObj.titleTextColor)) {
            mTitleToolbar.setTextColor(Color.parseColor(mCommunityFeedSolrObj.titleTextColor));
        } else {
            mTitleToolbar.setTextColor(getResources().getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mCommunityFeedSolrObj.communityPrimaryColor)));
        }
        mToolbar.setBackgroundColor(Color.parseColor(mCommunityFeedSolrObj.communityPrimaryColor));
        String alphaColor = mCommunityFeedSolrObj.titleTextColor;
        alphaColor = alphaColor.replace("#", "#" + "BF");
        mTabLayout.setTabTextColors(Color.parseColor(alphaColor), Color.parseColor(mCommunityFeedSolrObj.titleTextColor));
        mFabButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(mCommunityFeedSolrObj.communitySecondaryColor)));
        mTabLayout.setBackgroundColor(Color.parseColor(mCommunityFeedSolrObj.communityPrimaryColor));
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new Adapter(getSupportFragmentManager());
        if(!CommonUtil.isEmpty(mCommunityFeedSolrObj.communityTabs)){
            List<CommunityTab> communityTabs = new ArrayList<>();
            communityTabs = mCommunityFeedSolrObj.communityTabs;
            for (CommunityTab communityTab : communityTabs){
                if(communityTab.type.equalsIgnoreCase(TabType.NAVTIVE.getName())){
                    FeedFragment feedFragment = new FeedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(FeedFragment.ENDPOINT, communityTab.dataUrl);
                    feedFragment.setArguments(bundle);
                    mAdapter.addFragment(feedFragment, communityTab.title);
                    mTabFragments.add(feedFragment);
                }
                if(communityTab.type.equalsIgnoreCase(TabType.HTML.getName())){
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(null, communityTab.dataHtml, "");
                    mAdapter.addFragment(webViewFragment, communityTab.title);
                    mTabFragments.add(webViewFragment);
                }

                if(communityTab.type.equalsIgnoreCase(TabType.WEB.getName())){
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(communityTab.dataUrl, null, "");
                    mAdapter.addFragment(webViewFragment, communityTab.title);
                    mTabFragments.add(webViewFragment);
                }
            }
        }

      /*  mNewsfeedFragment = (NewsfeedFragment) NewsfeedFragment.instance();
        LearnFragment mLearnFragment = new LearnFragment();
        mChatsListFragment = new ConversationsListFragment();
        final ShopFragment mShopFragment = new ShopFragment();

        if(CommonUtil.isEmpty(mTabList)){
            adapter.addFragment(mNewsfeedFragment, "Feed");
            adapter.addFragment(mLearnFragment, "Learn");
            adapter.addFragment(mShopFragment, "Shop");
            adapter.addFragment(mChatsListFragment, "Chat");
        }else {
            for (int i = 0; i < mTabList.size() ; i++){
                switch (mTabList.get(i)){
                    case NEWSFEED:
                        FRAGMENT_NEWSFEED = i;
                        adapter.addFragment(mNewsfeedFragment, "Feed");
                        break;
                    case SHOP:
                        FRAGMENT_SHOP = i;
                        adapter.addFragment(mShopFragment, "Shop");
                        break;
                    case LEARN:
                        FRAGMENT_LEARN = i;
                        adapter.addFragment(mLearnFragment, "Learn");
                        break;
                    case CHAT:
                        FRAGMENT_CHAT = i;
                        adapter.addFragment(mChatsListFragment, "Chat");
                        break;
                }
            }
        }*/

        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
               /* supportInvalidateOptionsMenu();
                if (position == FRAGMENT_NEWSFEED) {
                    mFab.show();
                    ((TextView) mTabLayout.getTabAt(FRAGMENT_CHAT).getCustomView().findViewById(R.id.tab)).setTextColor(getResources().getColor(R.color.text_grey));
                } else if (position == FRAGMENT_LEARN) {
                    mFab.hide();
                    ((TextView) mTabLayout.getTabAt(FRAGMENT_CHAT).getCustomView().findViewById(R.id.tab)).setTextColor(getResources().getColor(R.color.text_grey));
                    mShopFragment.fetchUrl();
                } else if (position == FRAGMENT_CHAT) {
                    mFab.hide();
                    ((TextView) mTabLayout.getTabAt(FRAGMENT_CHAT).getCustomView().findViewById(R.id.tab)).setTextColor(getResources().getColor(R.color.accent));
                } else if (position == FRAGMENT_SHOP) {
                    mFab.hide();
                    ((TextView) mTabLayout.getTabAt(FRAGMENT_CHAT).getCustomView().findViewById(R.id.tab)).setTextColor(getResources().getColor(R.color.text_grey));
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public String getScreenName() {
        return null;
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

    public void invalidateItem(FeedDetail feedDetail) {
        for(int i = 0 ; i < mAdapter.getCount(); i++){
            Fragment fragment = mAdapter.getItem(i);
            if(fragment instanceof FeedFragment){
                ((FeedFragment)fragment).updateItem(feedDetail);
            }
        }
    }

    public void notifyAllItemRemoved(FeedDetail feedDetail) {
        for(int i = 0 ; i < mAdapter.getCount(); i++){
            Fragment fragment = mAdapter.getItem(i);
            if(fragment instanceof FeedFragment){
                ((FeedFragment)fragment).removeItem(feedDetail);
            }
        }
    }

    private void refreshAllFragment() {
        for(int i = 0 ; i < mAdapter.getCount(); i++){
            Fragment fragment = mAdapter.getItem(i);
            if(fragment instanceof FeedFragment){
                ((FeedFragment)fragment).refreshAllList();
            }
        }
    }

    public static void navigateTo(Activity fromActivity, CommunityFeedSolrObj communityFeedSolrObj, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, CommunityDetailActivity.class);
        Parcelable parcelable = Parcels.wrap(communityFeedSolrObj);
        intent.putExtra(CommunityFeedSolrObj.COMMUNITY_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

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

    @OnClick(R.id.fab)
    public void onFabClicked() {
        CommunityPost communityPost = new CommunityPost();
        communityPost.createPostRequestFrom = AppConstants.CREATE_POST;
        communityPost.isEdit = false;
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, false);
    }
}
