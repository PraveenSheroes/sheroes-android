package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.MyCommunities;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.AllMembersFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import butterknife.Bind;
import butterknife.ButterKnife;


public class CommunitiesDetailActivity extends BaseActivity implements AllMembersFragment.MembersHomeActivityIntractionListner,BaseHolderInterface,CommunityOpenAboutFragment.AboutCommunityActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(CommunitiesDetailActivity.class);
    private static final String EXTRA_IMAGE = "extraImage";
    private static final String DECRIPTION = "desc";
    private static final String HEADER = "header";
    private static final String TIME = "time";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.app_bar_coomunities_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_communities_detail)
    ImageView ivCommunitiesDetail;
    @Bind(R.id.view_pager_communities_detail)
    ViewPager mViewPagerCommunitiesDetail;
    @Bind(R.id.fab_communities_detail)
    public FloatingActionButton mFloatingActionButton;
    @Bind(R.id.toolbar_communities_detail)
    Toolbar mToolbarCommunitiesDetail;
    @Bind(R.id.collapsing_toolbar_communities_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    public static void navigate(AppCompatActivity activity, View transitionImage, ListOfFeed  listOfFeed) {
        Intent intent = new Intent(activity, CommunitiesDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, listOfFeed.getFeedCircleIconUrl());
        intent.putExtra(DECRIPTION, listOfFeed.getDescription());
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        initActivityTransitions();
        setContentView(R.layout.activity_communities_detail);
        ButterKnife.bind(this);
        setPagerAndLayouts();
    }
    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar(mToolbarCommunitiesDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle(" ");
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(),android.R.color.transparent));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(4), getString(R.string.ID_FEATURED));
        mViewPagerCommunitiesDetail.setAdapter(viewPagerAdapter);
        Glide.with(this)
                .load(getIntent().getStringExtra(EXTRA_IMAGE)).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        LogUtils.info("swipe", "*****************image height******* "+ resource.getHeight());
                        ivCommunitiesDetail.setImageBitmap( resource );
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                                updateBackground(mFloatingActionButton,palette);
                            }
                        });
                    }
                });

    }
    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(getApplication(), R.color.colorPrimary);
      //  mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
     //   mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //  updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorPrimary));
        fab.setRippleColor(lightVibrantColor);
       fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }
    @Override
    public void startActivityFromHolder(Intent intent) {

    }



    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof MyCommunities) {
            MyCommunities myCommunities = (MyCommunities) baseResponse;
            LogUtils.info("detail","**********Community header click********");
        }
        else
        {
            mFloatingActionButton.setVisibility(View.GONE);
            CommunityOpenAboutFragment communityOpenAboutFragment = new CommunityOpenAboutFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.about_community_container, communityOpenAboutFragment).addToBackStack(null).commitAllowingStateLoss();

        }
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public List getListData() {
        return null;
    }

    @Override
    public void memberClick() {
        AllMembersFragment frag = new AllMembersFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void closeMembersFragment() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen) {

    }

    @Override
    public void onClose() {
        getSupportFragmentManager().popBackStack();

    }
}
