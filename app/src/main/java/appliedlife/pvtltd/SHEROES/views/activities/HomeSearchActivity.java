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
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.AllSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobsFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeSearchActivity extends BaseActivity implements BaseHolderInterface,ViewPager.OnPageChangeListener {
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
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
        mViewPagerAdapter.addFragment(AllSearchFragment.createInstance(20), getString(R.string.ID_ALL));
      //  mViewPagerAdapter.addFragment(CommunitiesFragment.createInstance(20), getString(R.string.ID_COMMUNITIES));
        mViewPagerAdapter.addFragment(ArticlesFragment.createInstance(4), getString(R.string.ID_ARTICLES));
        mViewPagerAdapter.addFragment(JobsFragment.createInstance(4), getString(R.string.ID_JOBS));
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
    public void setListData(BaseResponse data, boolean flag) {
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
        else if(mViewPagerAdapter.getActiveFragment(mViewPager,position)instanceof CommunitiesFragment)
        {
           // Toast.makeText(this, "-----Community fragment----", Toast.LENGTH_SHORT).show();
        }
        else if(mViewPagerAdapter.getActiveFragment(mViewPager,position)instanceof JobsFragment)
        {
           // Toast.makeText(this, "-----Job fragment----", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
