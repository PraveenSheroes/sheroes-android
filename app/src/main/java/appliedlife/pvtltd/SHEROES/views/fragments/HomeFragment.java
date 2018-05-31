package appliedlife.pvtltd.SHEROES.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
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
    private String mDefaultTabKey = "My Feed";
    private List<String> homeTabs = new ArrayList<>();
    //endregion

    // region Enum
    public enum TabType {
        FEED("My Feed"),
        TRENDING("Trending");
        public String tabType;

        TabType(String tabType) {
            this.tabType = tabType;
        }

        public String getName() {
            return tabType;
        }
    }
    //endregion

    // region Public methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        initializeHomeViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeTabs.clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeTabs.add(TabType.FEED.getName());
        homeTabs.add(TabType.TRENDING.getName());
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
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
            if (name.equalsIgnoreCase(TabType.FEED.getName())) {
                FeedFragment feedFragment = new FeedFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream");
                bundle.putBoolean(FeedFragment.IS_HOME_FEED, true);
                bundle.putString(AppConstants.SCREEN_NAME, FEED_SCREEN_LABEL);
                feedFragment.setArguments(bundle);
                mFragmentAdapter.addFragment(feedFragment, TabType.FEED.getName());
                mTabFragments.add(feedFragment);

            }
            if (name.equalsIgnoreCase(TabType.TRENDING.getName())) {
                FeedFragment feedFragment = new FeedFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=TrendingPosts");
                bundle.putBoolean(FeedFragment.IS_HOME_FEED, true);
              //  bundle.putString(AppConstants.SCREEN_NAME, TRENDING_FEED_SCREEN_LABEL);
                feedFragment.setArguments(bundle);
                mFragmentAdapter.addFragment(feedFragment, TabType.TRENDING.getName());
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
                if (tabLayoutWidth < CommonUtil.getWindowWidth(getContext())) {
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
                switch (tab.getPosition()) {
                    case 1:
                        AnalyticsManager.trackScreenView(TRENDING_FEED_SCREEN_LABEL);
                        break;
                    case 2:
                        break;
                    default:
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
