package appliedlife.pvtltd.SHEROES.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 30/05/18.
 */

public class HomeFragment extends BaseFragment {
    public static String TRENDING_FEED_SCREEN_LABEL = "Trending Feed Screen";
    public static String FEED_SCREEN_LABEL = "Feed Screen";
    // region View variables
    @Bind(R.id.home_view_pager)
    ViewPager mViewPager;

    @Bind(R.id.home_tabs)
    TabLayout mTabLayout;
    //endregion

    // region member variables
    private Adapter mFragmentAdapter;
    private String mCommunityPrimaryColor = "#ffffff";
    private String mCommunityTitleTextColor = "#3c3c3c";
    private List<Fragment> mTabFragments = new ArrayList<>();
    private String mDefaultTabKey;
    private List<String> homeTabs = new ArrayList<>();
    private String mUnSelectedFragment;
    //endregion


    // region Public methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        mDefaultTabKey=getString(R.string.my_feed);
        initializeHomeViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeTabs.clear();
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }


    public void invalidateItem(FeedDetail feedDetail) {
        Fragment activeFragment = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.home_view_pager + ":" + mViewPager.getCurrentItem());
        if (AppUtils.isFragmentUIActive(activeFragment)) {
            ((FeedFragment) activeFragment).updateItem(feedDetail);
        }
    }

    public void removeItem(FeedDetail feedDetail) {
        Fragment activeFragment = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.home_view_pager + ":" + mViewPager.getCurrentItem());
        if (AppUtils.isFragmentUIActive(activeFragment)) {
            ((FeedFragment) activeFragment).removeItem(feedDetail);
        }
    }

    public void refreshCurrentFragment() {
        Fragment activeFragment = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.home_view_pager + ":" + mViewPager.getCurrentItem());
        if (AppUtils.isFragmentUIActive(activeFragment)) {
            ((FeedFragment) activeFragment).refreshList();
        }
    }


    public void refreshAtPosition(FeedDetail feedDetail, long id) {
        Fragment activeFragment = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.home_view_pager + ":" + mViewPager.getCurrentItem());
        if (AppUtils.isFragmentUIActive(activeFragment)) {
            ((FeedFragment) activeFragment).findPositionAndUpdateItem(feedDetail, id);
        }

    }

    public String getInactiveTabFragmentName() {
        return mUnSelectedFragment;
    }
    //endregion

    // region Protected methods
    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }
    //endregion

    // region Private methods
    private void initializeHomeViews() {
        homeTabs.add(getString(R.string.my_feed));
        homeTabs.add(getString(R.string.ID_TRENDING));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor(mCommunityTitleTextColor));
        String alphaColor = mCommunityTitleTextColor;
        alphaColor = alphaColor.replace("#", "#" + "BF");
        mTabLayout.setTabTextColors(Color.parseColor(alphaColor), Color.parseColor(mCommunityTitleTextColor));
        mTabLayout.setBackgroundColor(Color.parseColor(mCommunityPrimaryColor));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor(mCommunityTitleTextColor));
        setupViewPager(mViewPager);
        setupTabLayout();
    }

    private int getDefaultTabPosition() {
        int position = 0;
        if (CommonUtil.isNotEmpty(mDefaultTabKey)) {
            for (String homeTab : homeTabs) {
                if (homeTab.equalsIgnoreCase(mDefaultTabKey)) {
                    return position;
                }
                position++;
            }

        }
        return 0;
    }

    private void setupViewPager(final ViewPager viewPager) {
        mFragmentAdapter = new Adapter(getChildFragmentManager());
        for (String name : homeTabs) {
            if (name.equalsIgnoreCase(getString(R.string.my_feed))) {
                FeedFragment feedFragment = new FeedFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream");
                bundle.putBoolean(FeedFragment.IS_HOME_FEED, true);
                bundle.putString(AppConstants.SCREEN_NAME, FEED_SCREEN_LABEL);
                feedFragment.setArguments(bundle);
                mFragmentAdapter.addFragment(feedFragment, getString(R.string.my_feed));
                mTabFragments.add(feedFragment);

            }
            if (name.equalsIgnoreCase(getString(R.string.ID_TRENDING))) {
                FeedFragment feedFragment = new FeedFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=TrendingPosts");
                bundle.putBoolean(FeedFragment.IS_HOME_FEED, false);
                bundle.putString(AppConstants.SCREEN_NAME, TRENDING_FEED_SCREEN_LABEL);
                feedFragment.setArguments(bundle);
                mFragmentAdapter.addFragment(feedFragment,getString(R.string.ID_TRENDING));
                mTabFragments.add(feedFragment);
            }
        }

        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(getDefaultTabPosition());
        final ViewPager.OnPageChangeListener pageChangeListener;
        viewPager.addOnPageChangeListener(pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                pageChangeListener.onPageSelected(viewPager.getCurrentItem());
            }
        });

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                int tabLayoutWidth = mTabLayout.getWidth();
                if (getActivity() != null && tabLayoutWidth < CommonUtil.getWindowWidth(getActivity().getApplicationContext())) {
                    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                    ViewGroup.LayoutParams mParams = mTabLayout.getLayoutParams();
                    mParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    mTabLayout.setLayoutParams(mParams);
                } else {
                    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
            }
        });
    }

    private void setupTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (getActivity() instanceof HomeActivity) {
                    ((HomeActivity) getActivity()).removeTrendingFAB(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setInactiveTabFragmentName(FEED_SCREEN_LABEL);
                        break;
                    case 1:
                        setInactiveTabFragmentName(TRENDING_FEED_SCREEN_LABEL);
                        break;
                    default:
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment activeFragment = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.home_view_pager + ":" + mViewPager.getCurrentItem());
                if (AppUtils.isFragmentUIActive(activeFragment)) {
                    ((FeedFragment) activeFragment).scrollToTopInList();
                }
            }
        });
    }

    private void setInactiveTabFragmentName(String active) {
        mUnSelectedFragment = active;
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void onConfigFetched() {

    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
    //endregion

    // region Static innerclass
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
    //endregion
}