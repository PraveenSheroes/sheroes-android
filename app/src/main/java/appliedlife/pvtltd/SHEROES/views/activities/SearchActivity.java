package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.SearchPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesListFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HashTagFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ISearchView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment.TRENDING_FEED_SCREEN_LABEL;

public class SearchActivity extends BaseActivity implements ISearchView {

    private static final String SCREEN_LABEL = "Search Screen";

    @Inject
    SearchPresenter mSearchPresenter;

    @Bind(R.id.vp_search_tabs)
    ViewPager mSearchTabsPager;
    @Bind(R.id.tabLayout)
    TabLayout mSearchTabsLayout;
    private SearchActivity.SearchPagerAdapter mSearchFragmentAdapter;
    private List<Fragment> mSearchTabFragments = new ArrayList<>();
    private List<String> mSearchTabs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSearchPresenter.attachView(this);
        initializeSearchViews();
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

    public static void navigateTo(Activity fromActivity, int requestCode) {
        Intent intent = new Intent(fromActivity, SearchActivity.class);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    private void initializeSearchViews() {
        mSearchTabs.add(getString(R.string.top));
        mSearchTabs.add(getString(R.string.community));
        mSearchTabs.add(getString(R.string.hash_tag));
        mSearchTabs.add(getString(R.string.article));
        setupViewPager(mSearchTabsPager);
        setupTabLayout();
    }

    private void setupViewPager(final ViewPager viewPager) {
        mSearchFragmentAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        for (String name : mSearchTabs) {
            if (name.equalsIgnoreCase(getString(R.string.top))) {
                FeedFragment feedFragment = new FeedFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=TrendingPosts");
                bundle.putBoolean(FeedFragment.IS_HOME_FEED, false);
                bundle.putString(AppConstants.SCREEN_NAME, TRENDING_FEED_SCREEN_LABEL);
                feedFragment.setArguments(bundle);
                mSearchFragmentAdapter.addFragment(feedFragment, getString(R.string.top));
                mSearchTabFragments.add(feedFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.community))) {
                CommunitiesListFragment communitiesListFragment = new CommunitiesListFragment();
                mSearchFragmentAdapter.addFragment(communitiesListFragment, getString(R.string.community));
                mSearchTabFragments.add(communitiesListFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.hash_tag))) {
                HashTagFragment hashTagFragment = new HashTagFragment();
                mSearchFragmentAdapter.addFragment(hashTagFragment, getString(R.string.hash_tag));
                mSearchTabFragments.add(hashTagFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.article))) {
                ArticlesFragment articlesFragment = new ArticlesFragment();
                mSearchFragmentAdapter.addFragment(articlesFragment, getString(R.string.article));
                mSearchTabFragments.add(articlesFragment);
            }
        }
        viewPager.setAdapter(mSearchFragmentAdapter);
    }


    private void setupTabLayout() {
        mSearchTabsLayout.setupWithViewPager(mSearchTabsPager);
    }

    public class SearchPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mSearchFragments = new ArrayList<>();
        private final List<String> mSearchFragmentTitles = new ArrayList<>();

        public SearchPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mSearchFragments.add(fragment);
            mSearchFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mSearchFragments.get(position);
        }

        @Override
        public int getCount() {
            return mSearchFragments.size();
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mSearchFragmentTitles.get(position);

        }
    }
}

