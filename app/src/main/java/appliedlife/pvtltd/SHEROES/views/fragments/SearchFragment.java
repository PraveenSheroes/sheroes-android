package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.IHashTagCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.SearchEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.SearchPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ISearchView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment.TRENDING_FEED_SCREEN_LABEL;

public class SearchFragment extends BaseFragment implements ISearchView, BaseHolderInterface {
    private static final String SCREEN_LABEL = "Search Screen";

    @Inject
    SearchPresenter mSearchPresenter;

    @Bind(R.id.vp_search_tabs)
    ViewPager mSearchTabsPager;
    @Bind(R.id.tabLayout)
    TabLayout mSearchTabsLayout;
    @Bind(R.id.et_search)
    EditText mETSearch;
    private SearchPagerAdapter mSearchFragmentAdapter;
    private List<Fragment> mSearchTabFragments = new ArrayList<>();
    private List<String> mSearchTabs = new ArrayList<>();
    private String mSearchCategory;
    private int[] tabIcons = {
            R.drawable.search_tab_top,
            R.drawable.search_tab_communities,
            R.drawable.search_tab_hashtag,
            R.drawable.search_tab_articles
    };
    private HashTagFragment hashTagFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.activity_search, container, false);
        ButterKnife.bind(this, view);

        mSearchPresenter.attachView(this);
        initializeSearchViews();
        searchListener();

        mETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0){
                    hashTagFragment.populateTrendingHashTags();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    public void searchListener() {
        mETSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchProceed();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    private void initializeSearchViews() {
        mSearchTabs.add(getString(R.string.top));
        mSearchTabs.add(getString(R.string.community));
        mSearchTabs.add(getString(R.string.hash_tag));
        mSearchTabs.add(getString(R.string.article));
        setupViewPager(mSearchTabsPager);
        setupTabLayout();
        setupTabIcons();
    }

    private void setupTabIcons() {
        mSearchTabsLayout.getTabAt(0).setIcon(tabIcons[0]);
        mSearchTabsLayout.getTabAt(1).setIcon(tabIcons[1]);
        mSearchTabsLayout.getTabAt(2).setIcon(tabIcons[2]);
        mSearchTabsLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(final ViewPager viewPager) {
        mSearchFragmentAdapter = new SearchPagerAdapter(getActivity().getSupportFragmentManager());
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
                if(hashTagFragment == null) {
                    hashTagFragment = new HashTagFragment();
                }
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
    public void handleOnClick(BaseResponse baseResponse, View view) {

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

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }

    @OnClick(R.id.iv_search_icon)
    public void searchProceed() {
        CommonUtil.hideKeyboard(getActivity());
        int index = mSearchTabsPager.getCurrentItem();
        SearchPagerAdapter adapter = ((SearchPagerAdapter) mSearchTabsPager.getAdapter());
        Fragment fragment = adapter.getFragment(index);
        mSearchCategory = getSearchCategory(fragment);
        mSearchPresenter.searchQuery(mETSearch.getText().toString(), mSearchCategory);
    }

    @Override
    public void onSearchRespose(FeedResponsePojo feedResponsePojo) {
        int index = mSearchTabsPager.getCurrentItem();
        SearchPagerAdapter adapter = ((SearchPagerAdapter) mSearchTabsPager.getAdapter());
        Fragment fragment = adapter.getFragment(index);
        if (fragment instanceof FeedFragment) {
            // ((FeedFragment)fragment).addAllFeed(feedResponsePojo.getFeedDetails());
        } else if (fragment instanceof CommunitiesListFragment) {
            ((CommunitiesListFragment)fragment).showAllCommunity((ArrayList<FeedDetail>) feedResponsePojo.getFeedDetails());
        } else if (fragment instanceof HashTagFragment) {
            ((HashTagFragment)fragment).showAllHashTags((ArrayList<FeedDetail>) feedResponsePojo.getFeedDetails());
        } else if (fragment instanceof ArticlesFragment) {
            //((Articlefragment)fragment).addAllFeed(feedResponsePojo.getFeedDetails());
        }
    }

    public String getSearchCategory(Fragment fragment) {
        if (fragment instanceof FeedFragment) {
            mSearchCategory = SearchEnum.TOP.toString();
        } else if (fragment instanceof CommunitiesListFragment) {
            mSearchCategory = SearchEnum.COMMUNITIES.toString();
        } else if (fragment instanceof HashTagFragment) {
            mSearchCategory = SearchEnum.HASHTAGS.toString();
        } else if (fragment instanceof ArticlesFragment) {
            mSearchCategory = SearchEnum.ARTICLES.toString();
        }
        return mSearchCategory;
    }

    public void onHashTagClicked(String query) {
        mSearchPresenter.searchQuery(query, SearchEnum.HASHTAGS.toString());
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

        public Fragment getFragment(int key) {
            return mSearchFragments.get(key);
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
