package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobDetailFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 20-02-2017.
 */

public class JobDetailActivity extends BaseActivity implements CommentReactionFragment.HomeActivityIntractionListner, JobDetailFragment.JobDetailActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(JobDetailActivity.class);
    @Bind(R.id.app_bar_article_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_job_detail)
    ImageView ivJobDetail;
    @Bind(R.id.tv_job_detail_bookmark)
    TextView mTvJobDetailBookmark;
    @Bind(R.id.view_pager_job_detail)
    ViewPager mViewPagerJobDetail;
    ViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.toolbar_article_detail)
    Toolbar mToolbarArticleDetail;
    @Bind(R.id.tv_job_title)
    TextView mTv_job_title;
    @Bind(R.id.collapsing_toolbar_job_detail)
    public CustomCollapsingToolbarLayout mCustomCollapsingToolbarLayout;
    @Bind(R.id.tv_job_comp_nm)
    TextView mTv_job_comp_nm;
    @Bind(R.id.iv_job_comp_logo)
    ImageView mIv_job_comp_logo;
    private FeedDetail mFeedDetail;
    public View mArticlePopUp;
    TextView mTvFeedArticleDetailUserReaction;
    private FragmentOpen mFragmentOpen;
    String mdefaultCoverImage;
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
                mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_in_active, 0, 0, 0);
            }
            mCustomCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
            mCustomCollapsingToolbarLayout.setSubtitle(mFeedDetail.getAuthorName());
            mTv_job_comp_nm.setText(mFeedDetail.getAuthorName());
            mTv_job_title.setText(mFeedDetail.getNameOrTitle());
            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPagerAdapter.addFragment(JobDetailFragment.createInstance(mFeedDetail), getString(R.string.ID_FEATURED));
            mViewPagerJobDetail.setAdapter(mViewPagerAdapter);
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorImageUrl())) {
                mlogoflag = 1;
                Glide.with(this)
                        .load(mFeedDetail.getAuthorImageUrl()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                mIv_job_comp_logo.setImageBitmap(resource);
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette palette) {
                                        applyPalette(palette);
                                    }
                                });
                            }
                        });
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

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
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
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            articleDetailHandled(view, feedDetail);
        }
    }

    private void articleDetailHandled(View view, FeedDetail feedDetail) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_article_detail_user_comment:
                openCommentReactionFragment();
                break;
            case R.id.tv_article_detail_total_replies:
                openCommentReactionFragment();
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    private void openCommentReactionFragment() {
        CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
        Bundle bundleArticle = new Bundle();
        mFragmentOpen.setCommentList(true);
        mFragmentOpen.setOpen(true);
        bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
        commentReactionFragmentForArticle.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.fl_article_detail_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    private void showUserReactionOption(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    private void dismissUserReactionOption(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        viewToAnimate.clearAnimation();
        animation.setFillAfter(false);
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
    public void onErrorOccurence() {

    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {

    }


    @Override
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
        if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            mFragmentOpen.setCommentList(false);
        } else if (mFragmentOpen.isReactionList()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
        } else {
           onBackClick();
        }
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

    @Override
    public void onJobBookmarkClick(FeedDetail feedDetail) {
        if (!feedDetail.isBookmarked()) {
            feedDetail.setBookmarked(true);
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
        } else {
            feedDetail.setBookmarked(false);
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_in_active, 0, 0, 0);
        }
        mFeedDetail = feedDetail;
    }
}