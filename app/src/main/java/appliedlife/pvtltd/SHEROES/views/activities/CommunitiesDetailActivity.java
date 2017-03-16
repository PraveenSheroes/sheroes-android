package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
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

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.AllMembersFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityInviteSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOwnerSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityRequestedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OwnerRemoveDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareCommunityFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommunitiesDetailActivity extends BaseActivity implements CommunityOwnerSearchFragment.InviteOwnerActivityIntractionListner, ShareCommunityFragment.ShareCommunityActivityIntractionListner, CommunityInviteSearchFragment.InviteSearchActivityIntractionListner, CommunityRequestedFragment.RequestHomeActivityIntractionListner, AllMembersFragment.MembersHomeActivityIntractionListner, CommunityOpenAboutFragment.AboutCommunityActivityIntractionListner, CommentReactionFragment.HomeActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(CommunitiesDetailActivity.class);
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
    CustomCollapsingToolbarLayout mCollapsingToolbarLayout;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    ViewPagerAdapter viewPagerAdapter;
    private Fragment mFragment;

    public static void navigate(AppCompatActivity activity, View transitionImage, FeedDetail feedDetail) {
        Intent intent = new Intent(activity, CommunitiesDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, feedDetail);
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, AppConstants.COMMUNITY_DETAIL);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        initActivityTransitions();
        setContentView(R.layout.activity_communities_detail);
        ButterKnife.bind(this);
        if (null != getIntent()) {
            mFeedDetail = getIntent().getParcelableExtra(AppConstants.COMMUNITY_DETAIL);
        }
        setPagerAndLayouts();
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
        supportPostponeEnterTransition();

        setSupportActionBar(mToolbarCommunitiesDetail);
        mCollapsingToolbarLayout.setExpandedSubTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCollapsingToolbarLayout.setExpandedTitleMarginStart(200);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        if (null != mFeedDetail) {
            mCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
            mCollapsingToolbarLayout.setSubtitle(mFeedDetail.getNameOrTitle());
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(mFeedDetail), getString(R.string.ID_COMMUNITIES));
            mViewPagerCommunitiesDetail.setAdapter(viewPagerAdapter);
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getImageUrl())) {
                Glide.with(this)
                        .load(mFeedDetail.getImageUrl()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                ivCommunitiesDetail.setImageBitmap(resource);
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette palette) {
                                        supportStartPostponedEnterTransition();
                                        updateBackground(mFloatingActionButton, palette);
                                    }
                                });
                            }
                        });
            }

        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
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
        if (baseResponse instanceof FeedDetail) {
            communityDetailHandled(view, baseResponse);
        } else if (baseResponse instanceof OwnerList) {
            OwnerRemoveDialog newFragment = new OwnerRemoveDialog(this);
            newFragment.show(this.getFragmentManager(), getString(R.string.ID_DAILOG));
        }
    }

    private void communityDetailHandled(View view, BaseResponse baseResponse) {
        int id = view.getId();
        if (id == R.id.card_community_detail) {
            CommunityOpenAboutFragment communityOpenAboutFragment = new CommunityOpenAboutFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.about_community_container, communityOpenAboutFragment).addToBackStack(null).commitAllowingStateLoss();
        } else {
            mFragment = viewPagerAdapter.getActiveFragment(mViewPagerCommunitiesDetail, AppConstants.NO_REACTION_CONSTANT);
            setFragment(mFragment);
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.THREE_CONSTANT);
            setAllValues(mFragmentOpen);
            super.feedCardsHandled(view, baseResponse);
        }
    }

    @Override
    public void memberClick() {

        AllMembersFragment frag = new AllMembersFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void inviteClick() {

        CommunityInviteSearchFragment frag = new CommunityInviteSearchFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void ownerClick() {

        CommunityOwnerSearchFragment frag = new CommunityOwnerSearchFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void requestClick() {
        CommunityRequestedFragment frag = new CommunityRequestedFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void createCommunityClick() {
        Intent intent = new Intent(this, CreateCommunityActivity.class);
        startActivity(intent);
    }

    @Override
    public void shareClick() {
        ShareCommunityFragment frag = new ShareCommunityFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void onErrorOccurence() {
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPressed();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.THREE_CONSTANT);
            setAllValues(mFragmentOpen);
            super.openCommentReactionFragment(mFeedDetail);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();

            if (AppUtils.isFragmentUIActive(mFragment)) {
                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mFeedDetail);
            }
            mFragmentOpen.setCommentList(false);
        } else if (mFragmentOpen.isReactionList()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
        } else {
            onBackClick();
        }
    }

    @Override
    public void closeOwner() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onShareClose() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeInvite() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeRequestFragment() {
        getSupportFragmentManager().popBackStack();
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
        ivCommunitiesDetail.setVisibility(View.VISIBLE);
        mToolbarCommunitiesDetail.setVisibility(View.VISIBLE);
        mFloatingActionButton.setVisibility(View.VISIBLE);
        getSupportFragmentManager().popBackStack();

    }

    @OnClick(R.id.iv_community_detail_back)
    public void onBackClick() {
        finish();
    }
}
