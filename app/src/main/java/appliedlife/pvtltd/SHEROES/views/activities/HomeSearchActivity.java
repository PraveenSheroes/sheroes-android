package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.AllSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchArticleFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchJobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchRecentFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeSearchActivity extends BaseActivity implements BaseHolderInterface,ViewPager.OnPageChangeListener,FragmentIntractionWithActivityListner {
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
        if(null!=getIntent()&&null!=getIntent().getExtras())
        {
            mFragmenOpen=getIntent().getParcelableExtra(AppConstants.ALL_SEARCH);
        }
        renderSearchFragmentView();
    }
    public void renderSearchFragmentView() {
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initHomeViewPagerAndTabs();
    }
    private void initHomeViewPagerAndTabs() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(mFragmenOpen.isFeedOpen()) {
            String search =  getString(R.string.ID_SEARCH_IN_FEED);
            mSearchEditText.setHint(search);
            mViewPagerAdapter.addFragment(AllSearchFragment.createInstance(), getString(R.string.ID_ALL));
            mViewPagerAdapter.addFragment(SearchRecentFragment.createInstance(), getString(R.string.ID_RECENT));
            mViewPagerAdapter.addFragment(SearchArticleFragment.createInstance(), getString(R.string.ID_ARTICLE) + AppConstants.S);
            mViewPagerAdapter.addFragment(SearchCommunitiesFragment.createInstance(), getString(R.string.ID_COMMUNITIES));
            mViewPagerAdapter.addFragment(SearchJobFragment.createInstance(), getString(R.string.ID_JOBS));
        }
        else
        {
            mSearchEditText.setHint(getString(R.string.ID_SEARCH_IN_COMMUNITIES));
            mTabLayout.setVisibility(View.GONE);
            mViewPagerAdapter.addFragment(SearchCommunitiesFragment.createInstance(), getString(R.string.ID_COMMUNITIES));
        }
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }
    @Override
    public void startActivityFromHolder(Intent intent) {

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
    public List getListData() {
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


        if(mViewPagerAdapter.getActiveFragment(mViewPager,position)instanceof AllSearchFragment)
        {
           // Toast.makeText(this, "-----All----", Toast.LENGTH_SHORT).show();
        }
        else if(mViewPagerAdapter.getActiveFragment(mViewPager,position)instanceof ArticlesFragment)
        {
           // Toast.makeText(this, "-----Article fragment----", Toast.LENGTH_SHORT).show();
        }
        else if(mViewPagerAdapter.getActiveFragment(mViewPager,position)instanceof SearchCommunitiesFragment)
        {
           // Toast.makeText(this, "-----Community fragment----", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onShowErrorDialog() {
        getSupportFragmentManager().popBackStack();
    }
}
