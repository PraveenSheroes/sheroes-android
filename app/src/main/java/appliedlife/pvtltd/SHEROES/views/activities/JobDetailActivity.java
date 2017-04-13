package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobDetailFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 20-02-2017.
 */

public class JobDetailActivity extends BaseActivity {
    private final String TAG = LogUtils.makeLogTag(JobDetailActivity.class);
    @Bind(R.id.app_bar_job_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_job_detail)
    ImageView ivJobDetail;
    @Bind(R.id.tv_job_detail_bookmark)
    TextView mTvJobDetailBookmark;
    @Bind(R.id.view_pager_job_detail)
    ViewPager mViewPagerJobDetail;
    ViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.toolbar_job_detail)
    Toolbar mToolbarJobDetail;
    @Bind(R.id.tv_job_title)
    TextView mTv_job_title;
    @Bind(R.id.tv_job_detail_share)
    TextView mTv_job_detail_share;
    @Bind(R.id.collapsing_toolbar_job_detail)
    public CustomCollapsingToolbarLayout mCustomCollapsingToolbarLayout;
    @Bind(R.id.tv_job_comp_nm)
    TextView mTv_job_comp_nm;
    @Bind(R.id.iv_job_comp_logo)
    RoundedImageView mIv_job_comp_logo;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    int mlogoflag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        initActivityTransitions();
        setContentView(R.layout.activity_job_details);
        ButterKnife.bind(this);
        mFragmentOpen = new FragmentOpen();
        if (null != getIntent()) {
            mFeedDetail = getIntent().getParcelableExtra(AppConstants.JOB_DETAIL);
        }
        setPagerAndLayouts();
    }

    @OnClick(R.id.iv_job_detail_back)
    public void onBackClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.JOB_FRAGMENT, mFeedDetail);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.fade_in_dialog, R.anim.fade_out_dialog);
    }
    @OnClick(R.id.tv_job_detail_share)
    public void onJobDetailShare() {

    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.JOB_DETAIL);
        supportPostponeEnterTransition();
        mCustomCollapsingToolbarLayout.setExpandedSubTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleMarginStart(200);
        if (null != mFeedDetail) {
            if (mFeedDetail.isBookmarked()) {
                mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
            } else {
                mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_detail_white, 0, 0, 0);
            }
            mCustomCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
            mCustomCollapsingToolbarLayout.setSubtitle(mFeedDetail.getAuthorName());
            mTv_job_comp_nm.setText(mFeedDetail.getAuthorName());
            mTv_job_title.setText(mFeedDetail.getNameOrTitle());
            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPagerAdapter.addFragment(JobDetailFragment.createInstance(mFeedDetail), getString(R.string.ID_JOB));
            mViewPagerJobDetail.setAdapter(mViewPagerAdapter);
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorImageUrl())) {
                mlogoflag = 1;
                Glide.with(this)
                        .load(mFeedDetail.getAuthorImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .into(mIv_job_comp_logo);
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getImageUrl())) {

                Glide.with(this)
                        .load(mFeedDetail.getImageUrl()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                ivJobDetail.setImageBitmap(resource);
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette palette) {
                                        applyPalette(palette);
                                    }
                                });
                            }
                        });

            } else {
                ivJobDetail.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.job_default_cover));
                mTv_job_title.setText(mFeedDetail.getNameOrTitle());
                if (mlogoflag == 0)
                    mIv_job_comp_logo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.appicon));
                supportStartPostponedEnterTransition();
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

    private void applyPalette(Palette palette) {
        supportStartPostponedEnterTransition();
    }



    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            jobDetailHandled(view, feedDetail);
        }
    }

    private void jobDetailHandled(View view, FeedDetail feedDetail) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_article_detail_user_comment:
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        if (mFragmentOpen.isReactionList()) {
            CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
            Bundle bundleArticle = new Bundle();
            bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
            commentReactionFragmentForArticle.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.fl_article_detail_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }


    @Override
    public void onBackPressed() {
           onBackClick();
    }

    @OnClick(R.id.tv_job_detail_bookmark)
    public void onBookMarkClick() {
        mFeedDetail.setItemPosition(0);
        bookmarkCall();
    }

    private void bookmarkCall() {
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPagerJobDetail, 0);
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((JobDetailFragment) fragment).bookMarkForDetailCard(mFeedDetail);
        }
    }

    public void onJobBookmarkClick(FeedDetail feedDetail) {
        if (!feedDetail.isBookmarked()) {
            feedDetail.setBookmarked(true);
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
        } else {
            feedDetail.setBookmarked(false);
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_detail_white, 0, 0, 0);
        }
        mFeedDetail = feedDetail;
    }
    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                default:
                    showNetworkTimeoutDoalog(true, false,errorReason);
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }
}