package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.SearchEnum;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.SearchPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment implements BaseHolderInterface {
    //region static variables
    public static final String SCREEN_LABEL = "Search Screen";
    public static final String TOP_SCREEN_LABEL = "Top Screen";
    public static String searchTabName = "Top";
    //endregion static variables

    //region inject variables
    @Inject
    SearchPresenter mSearchPresenter;
    @Inject
    Preference<AppConfiguration> mConfiguration;
    //endregion

    //region View variables
    @Bind(R.id.vp_search_tabs)
    ViewPager mSearchTabsPager;
    @Bind(R.id.tabLayout)
    TabLayout mSearchTabsLayout;
    @Bind(R.id.et_search)
    EditText mETSearch;
    @Bind(R.id.iv_search_close)
    ImageView closeImg;
    @Bind(R.id.iv_back)
    ImageView backImg;
    @Bind(R.id.iv_search_icon)
    ImageView searchImg;
    //endregion

    //region member variables
    private SearchPagerAdapter mSearchFragmentAdapter;
    private List<Fragment> mSearchTabFragments = new ArrayList<>();
    private List<String> mSearchTabs = new ArrayList<>();
    private String mSearchCategory;
    private String mSearchBarText;
    private String mSearchText;
    private int[] mTabIcons = {
            R.drawable.vector_search_top,
            R.drawable.vector_communities_tab,
            R.drawable.vector_search_hashtag,
            R.drawable.vector_search_articles
    };
    private HashTagFragment mHashTagFragment;
    private CommunitiesListFragment mCommunitiesListFragment;
    private FeedFragment mFeedFragment;
    private ArticlesFragment mArticlesFragment;
    private boolean mSearchStarted = false;
    public static String searchText = null;
    private String mScreenLabel;
    private String mUnSelectedFragment;

    //endregion

    //region fragment lifecycle methods
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.activity_search, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initializeSearchViews();
        searchListener();
        mSearchTabsPager.setOffscreenPageLimit(AppConstants.THREE_CONSTANT);
        mSearchText = getArguments().getString(AppConstants.SEARCH_TEXT);
        mSearchCategory = getArguments().getString(AppConstants.SEARCH_CATEGORY);

        if (mConfiguration.isSet() && mConfiguration.get().configData != null
                && !StringUtil.isNotNullOrEmptyString(mConfiguration.get().configData.searchBarText)) {
            mSearchBarText = mConfiguration.get().configData.searchBarText;
        } else {
            mSearchBarText = getString(R.string.search_hint_text);
        }

        mETSearch.setHint(mSearchBarText);
        addOnTouchListener();
        addOnClickListerner();
        addTextChangeListener();
        deeplinkRedirection();
        fireSearchOpenEvent();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        HomeFragment.PREVIOUS_SCREEN = SCREEN_LABEL;
    }
    //endregion activity lifecycle methods

    //region public methods
    public static SearchFragment createInstance(String searchText, String
            searchCategory, String nextToken) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SEARCH_TEXT, searchText);
        bundle.putString(AppConstants.SEARCH_CATEGORY, searchCategory);
        bundle.putString(AppConstants.NEXT_TOKEN, nextToken);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    public void onHashTagClicked(String query) {
        mETSearch.setText(query);
        mETSearch.setSelection(mETSearch.getText().length());
        searchProceed();
    }

    public String getInactiveTabFragmentName() {
        return mUnSelectedFragment;
    }
    //endregion public methods

    //region private methods
    private void searchListener() {
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

    private void initializeSearchViews() {
        mSearchTabs.add(getString(R.string.top));
        mSearchTabs.add(getString(R.string.community));
        mSearchTabs.add(getString(R.string.hash_tag));
        mSearchTabs.add(getString(R.string.article));
        setupViewPager(mSearchTabsPager);
        setupTabLayout();
    }

    private void setupViewPager(final ViewPager viewPager) {
        mSearchFragmentAdapter = new SearchPagerAdapter(getChildFragmentManager());
        for (String name : mSearchTabs) {
            if (name.equalsIgnoreCase(getString(R.string.top))) {
                if (mFeedFragment == null) {
                    mFeedFragment = new FeedFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=TrendingPosts");
                bundle.putBoolean(FeedFragment.IS_HOME_FEED, false);
                bundle.putString(AppConstants.SCREEN_NAME, SearchFragment.TOP_SCREEN_LABEL);
                mFeedFragment.setArguments(bundle);
                mSearchFragmentAdapter.addFragment(mFeedFragment, getString(R.string.top));
                mSearchTabFragments.add(mFeedFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.community))) {
                if (mCommunitiesListFragment == null) {
                    mCommunitiesListFragment = new CommunitiesListFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.SCREEN_NAME, CommunitiesListFragment.SCREEN_LABEL);
                mCommunitiesListFragment.setArguments(bundle);
                mSearchFragmentAdapter.addFragment(mCommunitiesListFragment, getString(R.string.community));
                mSearchTabFragments.add(mCommunitiesListFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.hash_tag))) {
                if (mHashTagFragment == null) {
                    mHashTagFragment = new HashTagFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.SCREEN_NAME, HashTagFragment.SCREEN_LABEL);
                mHashTagFragment.setArguments(bundle);
                mSearchFragmentAdapter.addFragment(mHashTagFragment, getString(R.string.hash_tag));
                mSearchTabFragments.add(mHashTagFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.article))) {
                if (mArticlesFragment == null) {
                    mArticlesFragment = new ArticlesFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.SCREEN_NAME, ArticlesFragment.SCREEN_LABEL);
                mArticlesFragment.setArguments(bundle);
                mSearchFragmentAdapter.addFragment(mArticlesFragment, getString(R.string.article));
                mSearchTabFragments.add(mArticlesFragment);
            }
        }
        viewPager.setAdapter(mSearchFragmentAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                searchTabName = getTabName(position);
                if (mETSearch.getText().toString().trim().length() > 0) {
                    searchText = mETSearch.getText().toString().startsWith("#") ? mETSearch.getText().toString().substring(1) : mETSearch.getText().toString();
                }
                if (mSearchStarted) {
                    switch (position) {
                        case 0:
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mFeedFragment.filterFeed(true, searchText, SearchEnum.TOP.toString());
                            } else {
                                mFeedFragment.setSearchParams(false, searchText, SearchEnum.TOP.toString());
                                mFeedFragment.getFeedData();
                            }
                            break;
                        case 1:
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mCommunitiesListFragment.filterCommunities(false, searchText, SearchEnum.COMMUNITIES.toString());
                            } else {
                                mCommunitiesListFragment.callCommunityApi();
                            }
                            break;
                        case 2:
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mHashTagFragment.filterFeed(searchText);
                            } else {
                                mHashTagFragment.getHashtags();
                            }
                            break;
                        case 3:
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mArticlesFragment.fetchSearchedArticles(true, searchText, SearchEnum.ARTICLES.toString());
                            } else {
                                mArticlesFragment.setFilterParams(false, "", "");
                                mArticlesFragment.categoryArticleFilter(null);
                            }
                            break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addTextChangeListener() {
        mETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0) {
                    if (mSearchStarted) {
                        mHashTagFragment.getHashtags();
                        mCommunitiesListFragment.callCommunityApi();
                        mFeedFragment.setSearchParams(false, "", "");
                        mFeedFragment.getFeedData();
                        mArticlesFragment.setFilterParams(false, "", "");
                        mArticlesFragment.categoryArticleFilter(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() == 0) {
                    searchImg.setVisibility(View.VISIBLE);
                    backImg.setVisibility(View.GONE);
                    closeImg.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addOnTouchListener() {
        mETSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.GONE);
                return false;

            }
        });
    }

    private void addOnClickListerner() {
        mETSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mETSearch.setCursorVisible(true);
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.GONE);
            }
        });
    }

    private void deeplinkRedirection() {
        if (StringUtil.isNotNullOrEmptyString(mSearchCategory)) {
            if (mSearchCategory.equalsIgnoreCase(SearchEnum.ARTICLES.toString())) {
                mSearchTabsPager.setCurrentItem(3);
            } else if (mSearchCategory.equalsIgnoreCase(SearchEnum.HASHTAGS.toString())) {
                mSearchTabsPager.setCurrentItem(2);
            } else if (mSearchCategory.equalsIgnoreCase(SearchEnum.COMMUNITIES.toString())) {
                mSearchTabsPager.setCurrentItem(1);
            } else {
                mSearchTabsPager.setCurrentItem(0);
            }
        }

        if (!CommonUtil.isNullOrEmpty(mSearchText) && !CommonUtil.isNullOrEmpty(mSearchCategory)) {
            searchText = mSearchText;
            searchingState();
            mETSearch.setText(mSearchText);
            CommonUtil.hideKeyboard(getActivity());
            int index = mSearchTabsPager.getCurrentItem();
            SearchPagerAdapter adapter = ((SearchPagerAdapter) mSearchTabsPager.getAdapter());
            Fragment fragment = adapter.getFragment(index);
            mSearchCategory = getSearchCategory(fragment);
            if (fragment instanceof FeedFragment) {
                ((FeedFragment) fragment).setSearchParams(true, mSearchText, mSearchCategory);
            } else if (fragment instanceof CommunitiesListFragment) {
                ((CommunitiesListFragment) fragment).setSearchedDeeplinkParameters(false, mSearchText, mSearchCategory);
            } else if (fragment instanceof HashTagFragment) {
                ((HashTagFragment) fragment).setSearchParamterFromDeeplink(true, mSearchText);
            } else if (fragment instanceof ArticlesFragment) {
                ((ArticlesFragment) fragment).setSearchParamterFromDeeplink(true, mSearchText, mSearchCategory);
            }
        }
    }

    private void setupTabLayout() {
        mSearchTabsLayout.setupWithViewPager(mSearchTabsPager);
        for (int i = 0; i < mSearchTabsLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mSearchTabsLayout.getTabAt(i);
            tab.setCustomView(mSearchFragmentAdapter.getTabView(i));
        }
        mSearchFragmentAdapter.setTabLabelColor();

        mSearchTabsLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mSearchFragmentAdapter.setTabLabelColor();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mUnSelectedFragment = SearchFragment.TOP_SCREEN_LABEL;
                        break;
                    case 1:
                        mUnSelectedFragment = CommunitiesListFragment.SCREEN_LABEL;
                        break;
                    case 2:
                        mUnSelectedFragment = HashTagFragment.SCREEN_LABEL;
                        break;
                    case 3:
                        mUnSelectedFragment = ArticlesFragment.SCREEN_LABEL;
                        break;

                    default:
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void searchInitState() {
        searchText = "";
        mETSearch.setCursorVisible(false);
        mETSearch.setText("");
        searchImg.setVisibility(View.VISIBLE);
        backImg.setVisibility(View.GONE);
        closeImg.setVisibility(View.GONE);
        ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
    }

    private void searchingState() {
        mETSearch.setCursorVisible(false);
        mSearchStarted = true;
        searchImg.setVisibility(View.INVISIBLE);
        backImg.setVisibility(View.VISIBLE);
        closeImg.setVisibility(View.VISIBLE);
        ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
    }

    private String getSearchCategory(Fragment fragment) {
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

    private void fireQuerySearchEvent() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .source(HomeFragment.PREVIOUS_SCREEN)
                        .searchQuery(mETSearch.getText().toString())
                        .tabTitle(searchTabName)
                        .build();

        AnalyticsManager.trackEvent(Event.QUERY_SEARCHED, getScreenName(), properties);
    }

    private void fireSearchOpenEvent() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .source(HomeFragment.PREVIOUS_SCREEN)
                        .searchQuery(mETSearch.getText().toString())
                        .tabTitle(searchTabName)
                        .build();

        AnalyticsManager.trackScreenView(SCREEN_LABEL, properties);
    }


    private String getTabName(int index) {
        return mSearchTabsLayout.getTabAt(index).getText().toString();
    }
    //endregion private methods

    //region inherited methods
    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public String getScreenName() {
        if (CommonUtil.isNotEmpty(mScreenLabel)) {
            return mScreenLabel;
        } else {
            return SCREEN_LABEL;
        }
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

    @Override
    public void showEmptyScreen(String s) {
    }
    //endregion inherited methods

    //region click methods
    @OnClick(R.id.iv_search_icon)
    public void searchProceed() {
        searchText = mETSearch.getText().toString().trim();

        if (mETSearch.getText().toString().trim().length() > 0) {
            searchingState();
            CommonUtil.hideKeyboard(getActivity());
            int index = mSearchTabsPager.getCurrentItem();
            SearchPagerAdapter adapter = ((SearchPagerAdapter) mSearchTabsPager.getAdapter());
            Fragment fragment = adapter.getFragment(index);
            String searchText = mETSearch.getText().toString().startsWith("#") ? mETSearch.getText().toString().substring(1) : mETSearch.getText().toString();
            mSearchCategory = getSearchCategory(fragment);
            if (fragment instanceof FeedFragment) {
                ((FeedFragment) fragment).filterFeed(true, searchText, mSearchCategory);
            } else if (fragment instanceof CommunitiesListFragment) {
                ((CommunitiesListFragment) fragment).filterCommunities(false, searchText, mSearchCategory);
            } else if (fragment instanceof HashTagFragment) {
                ((HashTagFragment) fragment).filterFeed(searchText);
            } else if (fragment instanceof ArticlesFragment) {
                ((ArticlesFragment) fragment).fetchSearchedArticles(true, searchText, mSearchCategory);
            }
            fireQuerySearchEvent();

        }
    }

    @OnClick(R.id.iv_search_close)
    public void resetSearch() {
        searchText = "";
        CommonUtil.hideKeyboard(getActivity());
        searchInitState();
        int index = mSearchTabsPager.getCurrentItem();
        SearchPagerAdapter adapter = ((SearchPagerAdapter) mSearchTabsPager.getAdapter());
        Fragment fragment = adapter.getFragment(index);
        if (fragment instanceof FeedFragment) {
            ((FeedFragment) fragment).setSearchParams(false, "", "");
            ((FeedFragment) fragment).getFeedData();
        } else if (fragment instanceof CommunitiesListFragment) {
            ((CommunitiesListFragment) fragment).callCommunityApi();
        } else if (fragment instanceof HashTagFragment) {
            ((HashTagFragment) fragment).getHashtags();
        } else if (fragment instanceof ArticlesFragment) {
            ((ArticlesFragment) fragment).setFilterParams(false, "", "");
            ((ArticlesFragment) fragment).categoryArticleFilter(null);
        }
    }

    @OnClick(R.id.iv_back)
    public void onBackClick() {
        resetSearch();
    }
    //endregion click methods

    //region inner class
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

        public View getTabView(int position) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.search_custom_tab_layout, null);
            TextView tv = (TextView) v.findViewById(R.id.tv_tab_title);
            tv.setText(mSearchFragmentTitles.get(position));
            ImageView img = (ImageView) v.findViewById(R.id.iv_tab_image);
            img.setImageResource(mTabIcons[position]);
            return v;
        }

        public void setTabLabelColor() {
            for (int i = 0; i < mSearchTabsLayout.getTabCount(); i++) {
                View view = mSearchTabsLayout.getTabAt(i).getCustomView();

                if (mSearchTabsLayout.getSelectedTabPosition() == i) {
                    TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
                    tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.sheroes_red));
                } else {
                    TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
                    tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.sheroes_greyish_brown));
                }
            }
        }
    }
    //endregion inner class
}


