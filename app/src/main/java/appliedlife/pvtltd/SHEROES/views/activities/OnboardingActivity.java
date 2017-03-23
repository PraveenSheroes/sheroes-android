package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingHowCanSheroesHelpYouFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingTellUsAboutFragment;
import butterknife.Bind;
import butterknife.ButterKnife;


public class OnboardingActivity extends BaseActivity implements OnBoardingTellUsAboutFragment.OnBoardingActivityIntractionListner {

    private final String TAG = LogUtils.makeLogTag(OnboardingActivity.class);
    @Bind(R.id.app_bar_onboarding)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_onboarding)
    ImageView ivOnBoarding;
    @Bind(R.id.view_pager_onboarding)
    ViewPager mViewPagerOnBoarding;
    ViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.collapsing_toolbar_onboarding)
    public CustomCollapsingToolbarLayout mCustomCollapsingToolbarLayout;
    @Bind(R.id.tv_strip_for_add_item)
    public LinearLayout mLiStripForAddItem;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        setPagerAndLayouts();
    }

    private void setPagerAndLayouts() {
        supportPostponeEnterTransition();
        mCustomCollapsingToolbarLayout.setExpandedSubTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleMarginStart(200);
        // mCustomCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
        //  mCustomCollapsingToolbarLayout.setSubtitle(mFeedDetail.getAuthorName());
        OnBoardingTellUsAboutFragment onBoardingTellUsAboutFragment = new OnBoardingTellUsAboutFragment();
        Bundle bundleArticle = new Bundle();
        onBoardingTellUsAboutFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in_dialog, 0, 0, R.anim.fade_out_dialog)
                .replace(R.id.fl_onboarding_fragment, onBoardingTellUsAboutFragment, ArticlesFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    private void setViewPager() {
        getSupportFragmentManager().popBackStackImmediate();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(OnBoardingHowCanSheroesHelpYouFragment.createInstance(mMasterDataResult), getString(R.string.ID_TELL_US_ABOUT));
        mViewPagerOnBoarding.setAdapter(mViewPagerAdapter);
       // ivOnBoarding.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.how_can_sheroes_help));
        supportStartPostponedEnterTransition();
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof OnBoardingData) {
            OnBoardingData onBoardingData = (OnBoardingData) baseResponse;
        }
    }


    private void applyPalette(Palette palette) {
        supportStartPostponedEnterTransition();
    }


    @Override
    public void close() {

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        mMasterDataResult = masterDataResult;
        setViewPager();
    }


}
