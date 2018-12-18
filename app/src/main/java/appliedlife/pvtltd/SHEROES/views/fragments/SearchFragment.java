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

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.SearchEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.SearchPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment.TRENDING_FEED_SCREEN_LABEL;

public class SearchFragment extends BaseFragment implements BaseHolderInterface {
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
    private CommunitiesListFragment communitiesListFragment;
    private FeedFragment feedFragment;
    private ArticlesFragment articlesFragment;
    @Bind(R.id.iv_search_close)
    ImageView closeImg;
    @Bind(R.id.iv_back)
    ImageView backImg;
    @Bind(R.id.iv_search_icon)
    ImageView searchImg;
    private boolean searchStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.activity_search, container, false);
        ButterKnife.bind(this, view);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ((HomeActivity)getActivity()).mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        initializeSearchViews();
        searchListener();
        mSearchTabsPager.setOffscreenPageLimit(3);


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


        mETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0) {
                    if (searchStarted) {
                        hashTagFragment.callHashTagApi();
                        communitiesListFragment.callCommunityApi();
                        feedFragment.paramsToFilterFeed(false, "", "");
                        feedFragment.callFeedApi();
                        articlesFragment.setFilterParams(false, "", "");
                        articlesFragment.categoryArticleFilter(null);
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

                if(searchStarted) {
                    if (position == 0) {
                        if (mETSearch.getText().toString().trim().length() > 0) {
                            feedFragment.filterFeed(true, mETSearch.getText().toString(), SearchEnum.TOP.toString());
                        } else {
                            feedFragment.paramsToFilterFeed(false, mETSearch.getText().toString(), SearchEnum.TOP.toString());
                            feedFragment.callFeedApi();
                        }
                    } else if (position == 2) {
                        if (mETSearch.getText().toString().trim().length() > 0) {
                            hashTagFragment.filterFeed(mETSearch.getText().toString().startsWith("#")?mETSearch.getText().toString().substring(1):mETSearch.getText().toString());
                        } else {
                            hashTagFragment.callHashTagApi();
                        }
                    } else if (position == 1) {
                        if (mETSearch.getText().toString().trim().length() > 0) {
                            communitiesListFragment.filterCommunities(false, mETSearch.getText().toString(), SearchEnum.COMMUNITIES.toString());
                        } else {
                            communitiesListFragment.callCommunityApi();
                        }
                    } else if (position == 3) {
                        if (mETSearch.getText().toString().trim().length() > 0) {
                            articlesFragment.fetchSearchedArticles(true, mETSearch.getText().toString(), SearchEnum.ARTICLES.toString());
                        } else {
                            articlesFragment.setFilterParams(false, "", "");
                            articlesFragment.categoryArticleFilter(null);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
//        setupTabIcons();
    }

    private void setupTabIcons() {
        mSearchTabsLayout.getTabAt(0).setIcon(tabIcons[0]);
        mSearchTabsLayout.getTabAt(1).setIcon(tabIcons[1]);
        mSearchTabsLayout.getTabAt(2).setIcon(tabIcons[2]);
        mSearchTabsLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(final ViewPager viewPager) {
        mSearchFragmentAdapter = new SearchPagerAdapter(getChildFragmentManager());
        for (String name : mSearchTabs) {
            if (name.equalsIgnoreCase(getString(R.string.top))) {
                if (feedFragment == null) {
                    feedFragment = new FeedFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.END_POINT_URL, "participant/feed/stream?setOrderKey=TrendingPosts");
                bundle.putBoolean(FeedFragment.IS_HOME_FEED, false);
                bundle.putString(AppConstants.SCREEN_NAME, TRENDING_FEED_SCREEN_LABEL);
                feedFragment.setArguments(bundle);
                mSearchFragmentAdapter.addFragment(feedFragment, getString(R.string.top));
                mSearchTabFragments.add(feedFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.community))) {
                if (communitiesListFragment == null) {
                    communitiesListFragment = new CommunitiesListFragment();
                }
                mSearchFragmentAdapter.addFragment(communitiesListFragment, getString(R.string.community));
                mSearchTabFragments.add(communitiesListFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.hash_tag))) {
                if (hashTagFragment == null) {
                    hashTagFragment = new HashTagFragment();
                }
                mSearchFragmentAdapter.addFragment(hashTagFragment, getString(R.string.hash_tag));
                mSearchTabFragments.add(hashTagFragment);
            } else if (name.equalsIgnoreCase(getString(R.string.article))) {
                if (articlesFragment == null) {
                    articlesFragment = new ArticlesFragment();
                }
                mSearchFragmentAdapter.addFragment(articlesFragment, getString(R.string.article));
                mSearchTabFragments.add(articlesFragment);
            }
        }
        viewPager.setAdapter(mSearchFragmentAdapter);

    }


    private void setupTabLayout() {
        mSearchTabsLayout.setupWithViewPager(mSearchTabsPager);
        for (int i = 0; i < mSearchTabsLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mSearchTabsLayout.getTabAt(i);
            tab.setCustomView(mSearchFragmentAdapter.getTabView(i));
        }
        mSearchFragmentAdapter.setTabLabelColor();

//        apply(mSearchTabsLayout);
//        changeTabsFont();
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
        if (mETSearch.getText().toString().trim().length() > 0) {
            searchingState();
            CommonUtil.hideKeyboard(getActivity());
            int index = mSearchTabsPager.getCurrentItem();
            SearchPagerAdapter adapter = ((SearchPagerAdapter) mSearchTabsPager.getAdapter());
            Fragment fragment = adapter.getFragment(index);
            mSearchCategory = getSearchCategory(fragment);
            if (fragment instanceof FeedFragment) {
                ((FeedFragment) fragment).filterFeed(true, mETSearch.getText().toString(), mSearchCategory);
            } else if (fragment instanceof CommunitiesListFragment) {
                ((CommunitiesListFragment) fragment).filterCommunities(false, mETSearch.getText().toString(), mSearchCategory);
            } else if (fragment instanceof HashTagFragment) {
                ((HashTagFragment) fragment).filterFeed(mETSearch.getText().toString().startsWith("#")?mETSearch.getText().toString().substring(1):mETSearch.getText().toString());
            } else if (fragment instanceof ArticlesFragment) {
                ((ArticlesFragment) fragment).fetchSearchedArticles(true, mETSearch.getText().toString(), mSearchCategory);
            }
        }
    }

    @OnClick(R.id.iv_search_close)
    public void resetSearch() {
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

    private void searchInitState() {
        mETSearch.setCursorVisible(false);
        mETSearch.setText("");
        searchImg.setVisibility(View.VISIBLE);
        backImg.setVisibility(View.GONE);
        closeImg.setVisibility(View.GONE);

        ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
    }

    private void searchingState() {
        mETSearch.setCursorVisible(false);
        searchStarted = true;
        searchImg.setVisibility(View.INVISIBLE);
        backImg.setVisibility(View.VISIBLE);
        closeImg.setVisibility(View.VISIBLE);
        ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
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
        mETSearch.setText(query);
        mETSearch.setSelection(mETSearch.getText().length());
        searchProceed();
    }

    @Override
    public void showEmptyScreen(String s) {

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

        public View getTabView(int position) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.search_custom_tab_layout, null);
            TextView tv = (TextView) v.findViewById(R.id.tv_tab_title);
            tv.setText(mSearchFragmentTitles.get(position));
            ImageView img = (ImageView) v.findViewById(R.id.iv_tab_image);
            img.setImageResource(tabIcons[position]);

            return v;
        }


        public void setTabLabelColor(){
            for (int i = 0; i < mSearchTabsLayout.getTabCount(); i++) {
                View view = mSearchTabsLayout.getTabAt(i).getCustomView();

                if(mSearchTabsLayout.getSelectedTabPosition() ==  i){
                    TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
                    tv.setTextColor(Color.parseColor("#dc4541"));
                }else{
                    TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
                    tv.setTextColor(Color.parseColor("#3c3c3c"));
                }
            }
        }
    }
}
