package appliedlife.pvtltd.SHEROES.views.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
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

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.LANGUAGE_KEY;
import static appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment.TRENDING_FEED_SCREEN_LABEL;

public class SearchFragment extends BaseFragment implements BaseHolderInterface {
    //region static variables
    public static final String SCREEN_LABEL = "Search Screen";
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
            R.drawable.search_tab_top,
            R.drawable.search_tab_communities,
            R.drawable.search_tab_hashtag,
            R.drawable.search_tab_articles
    };
    private HashTagFragment mHashTagFragment;
    private CommunitiesListFragment mCommunitiesListFragment;
    private FeedFragment mFeedFragment;
    private ArticlesFragment mArticlesFragment;
    private boolean mSearchStarted = false;
    public static String searchText = null;
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
        mSearchTabsPager.setOffscreenPageLimit(3);
        mSearchText = getArguments().getString(AppConstants.SEARCH_TEXT);
        mSearchCategory = getArguments().getString(AppConstants.SEARCH_CATEGORY);

        mETSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.GONE);
                return false;

            }
        });

        mETSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mETSearch.setCursorVisible(true);
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.GONE);
            }
        });

        if (mConfiguration.isSet() && mConfiguration.get().configData != null
                && !StringUtil.isNotNullOrEmptyString(mConfiguration.get().configData.searchBarText)) {
            mSearchBarText = mConfiguration.get().configData.searchBarText;
        } else {
            mSearchBarText = getString(R.string.search_hint_text);
        }

        mETSearch.setHint(mSearchBarText);

        mETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0) {
                    if (mSearchStarted) {
                        mHashTagFragment.callHashTagApi();
                        mCommunitiesListFragment.callCommunityApi();
                        mFeedFragment.paramsToFilterFeed(false, "", "");
                        mFeedFragment.callFeedApi();
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

        mSearchTabsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSearchFragmentAdapter.setTabLabelColor();
                searchTabName = getTabName(position);
                if (mETSearch.getText().toString().trim().length() > 0) {
                    searchText = mETSearch.getText().toString().startsWith("#") ? mETSearch.getText().toString().substring(1) : mETSearch.getText().toString();
                }
                switch (position) {
                    case 0:
                        if (mSearchStarted) {
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mFeedFragment.filterFeed(true, searchText, SearchEnum.TOP.toString());
                            } else {
                                mFeedFragment.paramsToFilterFeed(false, searchText, SearchEnum.TOP.toString());
                                mFeedFragment.callFeedApi();
                            }
                        }
                        mFeedFragment.trackScreenEvent(searchText);
                        break;
                    case 1:
                        if (mSearchStarted) {
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mCommunitiesListFragment.filterCommunities(false, searchText, SearchEnum.COMMUNITIES.toString());
                            } else {
                                mCommunitiesListFragment.callCommunityApi();
                            }
                        }
                        mCommunitiesListFragment.trackScreenEvent(searchText);
                        break;
                    case 2:
                        if (mSearchStarted) {
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mHashTagFragment.filterFeed(searchText);
                            } else {
                                mHashTagFragment.callHashTagApi();
                            }
                        }
                        mHashTagFragment.trackScreenEvent(searchText);
                        break;
                    case 3:
                        if (mSearchStarted) {
                            if (mETSearch.getText().toString().trim().length() > 0) {
                                mArticlesFragment.fetchSearchedArticles(true, searchText, SearchEnum.ARTICLES.toString());
                            } else {
                                mArticlesFragment.setFilterParams(false, "", "");
                                mArticlesFragment.categoryArticleFilter(null);
                            }
                        }
                        mArticlesFragment.trackScreenEvent(searchText);
                        break;
                }
//                fireSearchOpenEvent();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        deeplinkRedirection();

        fireSearchOpenEvent();
        mFeedFragment.trackScreenEvent(searchText);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        AppConstants.PREVIOUS_SCREEN = SCREEN_LABEL;
    }
    //endregion activity lifecycle methods

    //region public methods
    public static SearchFragment createInstance(String searchText, String searchCategory, String nextToken) {
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
                bundle.putString(AppConstants.SCREEN_NAME, getScreenName());
                mFeedFragment.setArguments(bundle);
                mSearchFragmentAdapter.addFragment(mFeedFragment, getString(R.string.top));
                mSearchTabFragments.add(mFeedFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.community))) {
                if (mCommunitiesListFragment == null) {
                    mCommunitiesListFragment = new CommunitiesListFragment();
                }
                mSearchFragmentAdapter.addFragment(mCommunitiesListFragment, getString(R.string.community));
                mSearchTabFragments.add(mCommunitiesListFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.hash_tag))) {
                if (mHashTagFragment == null) {
                    mHashTagFragment = new HashTagFragment();
                }
                mSearchFragmentAdapter.addFragment(mHashTagFragment, getString(R.string.hash_tag));
                mSearchTabFragments.add(mHashTagFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.article))) {
                if (mArticlesFragment == null) {
                    mArticlesFragment = new ArticlesFragment();
                }
                mSearchFragmentAdapter.addFragment(mArticlesFragment, getString(R.string.article));
                mSearchTabFragments.add(mArticlesFragment);
            }
        }
        viewPager.setAdapter(mSearchFragmentAdapter);
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
            searchingState();
            mETSearch.setText(mSearchText);
            CommonUtil.hideKeyboard(getActivity());
            int index = mSearchTabsPager.getCurrentItem();
            SearchPagerAdapter adapter = ((SearchPagerAdapter) mSearchTabsPager.getAdapter());
            Fragment fragment = adapter.getFragment(index);
            mSearchCategory = getSearchCategory(fragment);
            if (fragment instanceof FeedFragment) {
                ((FeedFragment) fragment).paramsToFilterFeed(true, mSearchText, mSearchCategory);
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
                        .source(AppConstants.PREVIOUS_SCREEN)
                        .searchQuery(mETSearch.getText().toString())
                        .tabTitle(searchTabName)
                        .build();

        AnalyticsManager.trackEvent(Event.QUERY_SEARCHED, getScreenName(), properties);

    }

    private void fireSearchOpenEvent() {


        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .source(AppConstants.PREVIOUS_SCREEN)
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
        return SCREEN_LABEL;
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
            ((FeedFragment) fragment).paramsToFilterFeed(false, "", "");
            ((FeedFragment) fragment).callFeedApi();
        } else if (fragment instanceof CommunitiesListFragment) {
            ((CommunitiesListFragment) fragment).callCommunityApi();
        } else if (fragment instanceof HashTagFragment) {
            ((HashTagFragment) fragment).callHashTagApi();
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
                    tv.setTextColor(Color.parseColor("#dc4541"));
                } else {
                    TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
                    tv.setTextColor(Color.parseColor("#3c3c3c"));
                }
            }
        }
    }
    //endregion inner class

}


