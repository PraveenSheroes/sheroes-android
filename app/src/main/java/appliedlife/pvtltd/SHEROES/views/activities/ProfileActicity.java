package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
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
import android.widget.LinearLayout;

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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonnelProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProffestionalProfileFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 13-02-2017.
 */

public class ProfileActicity extends BaseActivity implements BaseHolderInterface ,AppBarLayout.OnOffsetChangedListener {
    private final String TAG = LogUtils.makeLogTag(ProfileActicity.class);
    private static final String EXTRA_IMAGE = "extraImage";
    private static final String DECRIPTION = "desc";
    @Bind(R.id.iv_profile_full_view_icon)
    RoundedImageView mProfileIcon;
    @Bind(R.id.app_bar_profile)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_profile_image)
    ImageView ivCommunitiesDetail;
    @Bind(R.id.view_pager_profile)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_profile)
     Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_profile)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tab_profile)
    public TabLayout mTabLayout;
    @Bind(R.id.li_user_profile_status)
    public LinearLayout mLytUserProfileStatus;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    public static void navigate(AppCompatActivity activity, View transitionImage, String profile) {
        Intent intent = new Intent(activity, ProfileActicity.class);
        intent.putExtra(EXTRA_IMAGE,profile);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        initActivityTransitions();
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setPagerAndLayouts();
    }
    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, EXTRA_IMAGE);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle("  ");
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(),android.R.color.transparent));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PersonnelProfileFragment.createInstance(), getString(R.string.ID_PERSONAL));
        viewPagerAdapter.addFragment(ProffestionalProfileFragment.createInstance(), getString(R.string.ID_PROFESSIONAL));
        mViewPager.setAdapter(viewPagerAdapter);
       mTabLayout.setupWithViewPager(mViewPager);
        Glide.with(this)
                .load(getIntent().getStringExtra(EXTRA_IMAGE))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(mProfileIcon);
        Glide.with(this)
                .load(getIntent().getStringExtra(EXTRA_IMAGE)).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        ivCommunitiesDetail.setImageBitmap( resource );
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                            }
                        });
                    }
                });
        mAppBarLayout.addOnOffsetChangedListener(this);

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

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public void userCommentLikeRequest(long entityId, int reactionValue) {

    }

    @Override
    public List getListData() {
        return null;
    }

    /**
     * Called when the {@link AppBarLayout}'s layout offset has been changed. This allows
     * child views to implement custom behavior based on the offset (for instance pinning a
     * view at a certain y value).
     *
     * @param appBarLayout   the {@link AppBarLayout} which offset has changed
     * @param verticalOffset the vertical offset for the parent {@link AppBarLayout}, in px
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.info("appbar","**************"+verticalOffset);
        if(verticalOffset>-500)
        {
            mLytUserProfileStatus.setVisibility(View.GONE);
        }
        else
        {
            mLytUserProfileStatus.setVisibility(View.VISIBLE);
        }
    }
    @OnClick(R.id.iv_back)
    public void backOnclick()
    {
        finish();
        overridePendingTransition(R.anim.top_to_bottom_exit,R.anim.top_bottom_exit_anim);
    }
}