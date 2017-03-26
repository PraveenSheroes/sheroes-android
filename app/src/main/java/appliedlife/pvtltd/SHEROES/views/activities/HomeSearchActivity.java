package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.AllSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchArticleFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchJobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchRecentFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSearchActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private final String TAG = LogUtils.makeLogTag(HomeSearchActivity.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.search_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.search_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.search_tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.iv_search_icon)
    ImageView mIvSearchIcon;
    @Bind(R.id.et_search_edit_text)
    public EditText mSearchEditText;
    private ViewPagerAdapter mViewPagerAdapter;
    private FragmentOpen mFragmenOpen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mFragmenOpen = getIntent().getParcelableExtra(AppConstants.ALL_SEARCH);
        }
        renderSearchFragmentView();
    }

    public void renderSearchFragmentView() {
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        initHomeViewPagerAndTabs();
    }

    private void initHomeViewPagerAndTabs() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (mFragmenOpen.isFeedOpen()) {
            String search = getString(R.string.ID_SEARCH_IN_FEED);
            mSearchEditText.setHint(search);
            mViewPagerAdapter.addFragment(AllSearchFragment.createInstance(), getString(R.string.ID_ALL));
            mViewPagerAdapter.addFragment(SearchRecentFragment.createInstance(), getString(R.string.ID_RECENT));
            mViewPagerAdapter.addFragment(SearchArticleFragment.createInstance(), getString(R.string.ID_ARTICLE));
            mViewPagerAdapter.addFragment(SearchCommunitiesFragment.createInstance(), getString(R.string.ID_COMMUNITIES));
            mViewPagerAdapter.addFragment(SearchJobFragment.createInstance(), getString(R.string.ID_JOBS));
        } else {
            mSearchEditText.setHint(getString(R.string.ID_SEARCH_IN_COMMUNITIES));
            mTabLayout.setVisibility(View.GONE);
            mViewPagerAdapter.addFragment(SearchCommunitiesFragment.createInstance(), getString(R.string.ID_COMMUNITIES));
        }
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            searchCardsHandled(view, baseResponse);
        }
    }

    private void searchCardsHandled(View view, BaseResponse baseResponse) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        feedDetail.setFromHome(true);
        Fragment fragment;
        String searchTag = (String) view.getTag();
        switch (searchTag) {
            case AppConstants.FEED_ARTICLE:
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.TWO_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof SearchArticleFragment) {
                        ((SearchArticleFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof AllSearchFragment) {
                        ((AllSearchFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                mFragmenOpen.setImageBlur(true);
             //   ArticleDetailActivity.navigateFromArticle(this, view, feedDetail);
                break;
            case AppConstants.FEED_COMMUNITY:
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.THREE_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof SearchCommunitiesFragment) {
                        ((SearchCommunitiesFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof AllSearchFragment) {
                        ((AllSearchFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                mFragmenOpen.setImageBlur(true);
            //    CommunitiesDetailActivity.navigate(this, view, feedDetail);
                break;
            case AppConstants.FEED_COMMUNITY_POST:
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.THREE_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof SearchCommunitiesFragment) {
                        ((SearchCommunitiesFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof AllSearchFragment) {
                        ((AllSearchFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                mFragmenOpen.setImageBlur(true);
               // CommunitiesDetailActivity.navigate(this, view, feedDetail);
                break;
            case AppConstants.FEED_JOB:
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.FOURTH_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof SearchJobFragment) {
                        ((SearchJobFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof AllSearchFragment) {
                        ((AllSearchFragment) fragment).saveRecentSearchData(feedDetail);
                    }
                }
                mFragmenOpen.setImageBlur(true);
               JobDetailActivity.navigateFromJob(this, view, feedDetail);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + searchTag);
        }
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, position);
        if (fragment instanceof AllSearchFragment) {
            mSearchEditText.setHint(getString(R.string.ID_SEARCH_IN_FEED));
            mSearchEditText.setEnabled(true);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof AllSearchFragment) {
                    ((AllSearchFragment) fragment).setEditText(mSearchEditText.getText().toString());
                }
            }
        } else if (fragment instanceof SearchRecentFragment) {
            mSearchEditText.setHint(getString(R.string.ID_RECENT_SEARCH));
            mSearchEditText.setEnabled(false);
            ((SearchRecentFragment) fragment).updateUiAfterSwip();
        } else if (fragment instanceof SearchArticleFragment) {
            String string = getString(R.string.ID_SEARCH) + AppConstants.SPACE + getString(R.string.ID_ARTICLE);
            mSearchEditText.setHint(string);
            mSearchEditText.setEnabled(true);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof SearchArticleFragment) {
                    ((SearchArticleFragment) fragment).setEditText(mSearchEditText.getText().toString());
                }
            }
        } else if (fragment instanceof SearchCommunitiesFragment) {
            String string = getString(R.string.ID_SEARCH) + AppConstants.SPACE + getString(R.string.ID_COMMUNITIES) ;
            mSearchEditText.setHint(string);
            mSearchEditText.setEnabled(true);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof SearchCommunitiesFragment) {
                    ((SearchCommunitiesFragment) fragment).setEditText(mSearchEditText.getText().toString());
                }
            }
        } else if (fragment instanceof SearchJobFragment) {
            String string = getString(R.string.ID_SEARCH) + AppConstants.SPACE + getString(R.string.ID_JOB) + AppConstants.S;
            mSearchEditText.setHint(string);
            mSearchEditText.setEnabled(true);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof SearchJobFragment) {
                    ((SearchJobFragment) fragment).setEditText(mSearchEditText.getText().toString());
                }
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.iv_search_back)
    public void searchOnBackClick() {
        finish();
        overridePendingTransition(R.anim.fade_in_dialog, R.anim.fade_out_dialog);
    }
}
