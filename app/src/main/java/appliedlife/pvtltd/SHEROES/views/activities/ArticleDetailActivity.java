package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MotionEvent;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 07-02-2017.
 */

public class ArticleDetailActivity extends BaseActivity implements BaseHolderInterface, View.OnClickListener, View.OnTouchListener, CommentReactionFragment.HomeActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(ArticleDetailActivity.class);
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.app_bar_article_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_article_detail)
    ImageView ivArticleDetail;
    @Bind(R.id.view_pager_article_detail)
    ViewPager mViewPagerArticleDetail;
    @Bind(R.id.toolbar_article_detail)
    Toolbar mToolbarArticleDetail;
    @Bind(R.id.collapsing_toolbar_article_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ArticleCardResponse mArticleCardResponse;
    public View mArticlePopUp;
    TextView mTvFeedArticleDetailUserReaction;
    private FragmentOpen mFragmentOpen;

    public static void navigateFromArticle(AppCompatActivity activity, View transitionImage, ArticleCardResponse articleCardResponse) {
        Intent intent = new Intent(activity, ArticleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.ARTICLE_DETAIL, articleCardResponse);
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, AppConstants.ARTICLE_DETAIL);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        initActivityTransitions();
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        mFragmentOpen = new FragmentOpen(false, false, false, false, false, false, false, false);
        if (null != getIntent()) {
            mArticleCardResponse = getIntent().getParcelableExtra(AppConstants.ARTICLE_DETAIL);
        }
        setPagerAndLayouts();
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.ARTICLE_DETAIL);
        supportPostponeEnterTransition();

        setSupportActionBar(mToolbarArticleDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle(" Testing article");
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        if (null != mArticleCardResponse) {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(ArticleDetailFragment.createInstance(mArticleCardResponse), getString(R.string.ID_FEATURED));
            mViewPagerArticleDetail.setAdapter(viewPagerAdapter);
            if (StringUtil.isNotNullOrEmptyString(mArticleCardResponse.getArticleCircleIconUrl())) {
                Glide.with(this)
                        .load(mArticleCardResponse.getArticleCircleIconUrl()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                ivArticleDetail.setImageBitmap(resource);
                                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette palette) {
                                        applyPalette(palette);
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

    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(getApplication(), R.color.colorPrimary);
        //  mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        //   mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //  updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof ArticleDetailPojo) {
            ArticleDetailPojo articleDetailPojo = (ArticleDetailPojo) baseResponse;
            articleDetailHandled(view, articleDetailPojo);
        }
    }

    private void articleDetailHandled(View view, ArticleDetailPojo articleDetailPojo) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_article_detail_user_comment:
                openCommentReactionFragment();
                break;
            case R.id.tv_article_detail_total_replies:
                openCommentReactionFragment();
                break;
            case R.id.tv_article_detail_user_reaction:
                mArticlePopUp = findViewById(R.id.li_article_detail_emoji_pop_up);
                if (mArticlePopUp.getVisibility() == View.VISIBLE) {
                    mArticlePopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mArticlePopUp);
                } else {
                    mTvFeedArticleDetailUserReaction = (TextView) findViewById(R.id.tv_article_detail_user_reaction);
                    TextView tvArticleReaction = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction);
                    TextView tvArticleReaction1 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction1);
                    TextView tvArticleReaction2 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction2);
                    TextView tvArticleReaction3 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction3);
                    TextView tvArticleReaction4 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction4);
                    mArticlePopUp.setOnTouchListener(this);
                    tvArticleReaction.setOnClickListener(this);
                    tvArticleReaction1.setOnClickListener(this);
                    tvArticleReaction2.setOnClickListener(this);
                    tvArticleReaction3.setOnClickListener(this);
                    tvArticleReaction4.setOnClickListener(this);
                    mArticlePopUp.setVisibility(View.VISIBLE);
                    showUserReactionOption(mArticlePopUp);
                }
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
    public void userCommentLikeRequest(long entityId, int reactionValue) {

    }

    @Override
    public List getListData() {
        return null;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_reaction:
                if (null != mTvFeedArticleDetailUserReaction) {
                    mTvFeedArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_heart_active), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }

                break;
            case R.id.tv_reaction1:
                if (null != mTvFeedArticleDetailUserReaction) {
                    mTvFeedArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji3_whistel), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }

                break;
            case R.id.tv_reaction2:
                if (null != mTvFeedArticleDetailUserReaction) {
                    mTvFeedArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji_xo_xo), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }

                break;
            case R.id.tv_reaction3:
                if (null != mTvFeedArticleDetailUserReaction) {
                    mTvFeedArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji2_with_you), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }

                break;
            case R.id.tv_reaction4:
                if (null != mTvFeedArticleDetailUserReaction) {
                    mTvFeedArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.ic_emoji4_face_palm), null, null, null);
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }

                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param view  The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_article_card_emoji_pop_up:
                if (null != mArticlePopUp) {
                    mArticlePopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mArticlePopUp);
                }
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
        return true;
    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen) {
        mFragmentOpen = isFragmentOpen;
        onBackPressed();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen) {
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
            finish();
        }
    }

}