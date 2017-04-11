package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
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
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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
    private FragmentOpen mFragmentOpen;
    private FeedDetail mFeedDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mFragmentOpen = getIntent().getParcelableExtra(AppConstants.ALL_SEARCH);
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
        if (mFragmentOpen.isFeedOpen()) {
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
        setFragmentLocation();
        mViewPager.addOnPageChangeListener(this);
    }

    private void setFragmentLocation() {
        if (mFragmentOpen.isArticleFragment()) {
            mViewPager.setCurrentItem(AppConstants.TWO_CONSTANT);
        } else if (mFragmentOpen.isJobFragment()) {
            mViewPager.setCurrentItem(AppConstants.FOURTH_CONSTANT);
        } else {
            mViewPager.setCurrentItem(AppConstants.NO_REACTION_CONSTANT);
        }
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            searchCardsHandled(view, baseResponse);
        }
    }

    private void searchCardsHandled(View view, BaseResponse baseResponse) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        Fragment fragment;
        String searchTag = (String) view.getTag();
        switch (searchTag) {
            case AppConstants.FEED_ARTICLE:
                mFragmentOpen.setImageBlur(true);
                Intent intentArticle = new Intent(this, ArticleDetailActivity.class);
                intentArticle.putExtra(AppConstants.ARTICLE_DETAIL, feedDetail);
                startActivityForResult(intentArticle, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
                overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
                break;
            case AppConstants.FEED_COMMUNITY:
                mFragmentOpen.setImageBlur(true);
                Intent intetFeature = new Intent(this, CommunitiesDetailActivity.class);
                Bundle bundleFeature = new Bundle();
                bundleFeature.putParcelable(AppConstants.COMMUNITY_DETAIL, feedDetail);
                bundleFeature.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.SEARCH_COMMUNITY);
                intetFeature.putExtras(bundleFeature);
                startActivityForResult(intetFeature, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
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
                mFragmentOpen.setImageBlur(true);
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
                mFragmentOpen.setImageBlur(true);
                Intent intentJob = new Intent(this, JobDetailActivity.class);
                intentJob.putExtra(AppConstants.JOB_DETAIL, feedDetail);
                startActivityForResult(intentJob, AppConstants.REQUEST_CODE_FOR_JOB_DETAIL);
                overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
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
                    ((AllSearchFragment) fragment).setEditText(mSearchEditText.getText().toString());
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
                    ((SearchArticleFragment) fragment).setEditText(mSearchEditText.getText().toString());
            }
        } else if (fragment instanceof SearchCommunitiesFragment) {
            String string = getString(R.string.ID_SEARCH) + AppConstants.SPACE + getString(R.string.ID_COMMUNITIES);
            mSearchEditText.setHint(string);
            mSearchEditText.setEnabled(true);
            if (AppUtils.isFragmentUIActive(fragment)) {
                    ((SearchCommunitiesFragment) fragment).setEditText(mSearchEditText.getText().toString());
            }
        } else if (fragment instanceof SearchJobFragment) {
            String string = getString(R.string.ID_SEARCH) + AppConstants.SPACE + getString(R.string.ID_JOB) + AppConstants.S;
            mSearchEditText.setHint(string);
            mSearchEditText.setEnabled(true);
            if (AppUtils.isFragmentUIActive(fragment)) {
                    ((SearchJobFragment) fragment).setEditText(mSearchEditText.getText().toString());
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.tv_search_back)
    public void searchOnBackClick() {
      finish();
        overridePendingTransition(R.anim.fade_in_dialog, R.anim.fade_out_dialog);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (requestCode == AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL && null != intent) {
            mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.HOME_FRAGMENT);
            Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.TWO_CONSTANT);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof SearchArticleFragment) {
                    ((SearchArticleFragment) fragment).saveRecentSearchData(mFeedDetail);
                }
            }
            fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof AllSearchFragment) {
                    ((AllSearchFragment) fragment).saveRecentSearchData(mFeedDetail);
                }
            }
        } else if (requestCode == AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL && null != intent) {
            mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL);
            Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.THREE_CONSTANT);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof SearchCommunitiesFragment) {
                    ((SearchCommunitiesFragment) fragment).setEditText(mSearchEditText.getText().toString());
                    ((SearchCommunitiesFragment) fragment).saveRecentSearchData(mFeedDetail);
                }
            }
            fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof AllSearchFragment) {
                    ((AllSearchFragment) fragment).setEditText(mSearchEditText.getText().toString());
                    ((AllSearchFragment) fragment).saveRecentSearchData(mFeedDetail);
                }else if(fragment instanceof SearchCommunitiesFragment)
                {
                    ((SearchCommunitiesFragment) fragment).setEditText(mSearchEditText.getText().toString());
                    ((SearchCommunitiesFragment) fragment).saveRecentSearchData(mFeedDetail);
                }
            }
        } else if (requestCode == AppConstants.REQUEST_CODE_FOR_JOB_DETAIL && null != intent) {
            mFeedDetail = (FeedDetail) intent.getExtras().get(AppConstants.JOB_FRAGMENT);
            Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.FOURTH_CONSTANT);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof SearchJobFragment) {
                    ((SearchJobFragment) fragment).saveRecentSearchData(mFeedDetail);
                }
            }
            fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
            if (AppUtils.isFragmentUIActive(fragment)) {
                if (fragment instanceof AllSearchFragment) {
                    ((AllSearchFragment) fragment).saveRecentSearchData(mFeedDetail);
                }
            }
        }

    }
    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if(StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                default:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
            }
        }else
        {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

}
