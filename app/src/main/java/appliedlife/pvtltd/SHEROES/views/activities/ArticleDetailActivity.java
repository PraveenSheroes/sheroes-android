package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticleDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 07-02-2017.
 */

public class ArticleDetailActivity extends BaseActivity implements CommentReactionFragment.HomeActivityIntractionListner,ArticleDetailFragment.ArticleDetailActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(ArticleDetailActivity.class);
    @Bind(R.id.app_bar_article_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_article_detail)
    ImageView ivArticleDetail;
    @Bind(R.id.view_pager_article_detail)
    ViewPager mViewPagerArticleDetail;
    @Bind(R.id.toolbar_article_detail)
    Toolbar mToolbarArticleDetail;
    @Bind(R.id.collapsing_toolbar_article_detail)
    public CustomCollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tv_article_detail_total_views)
    TextView mTvArticleDetailTotalViews;
    @Bind(R.id.tv_article_detail_bookmark)
    TextView mTvArticleDetailBookmark;
    private FeedDetail mFeedDetail;
    public View mArticlePopUp;
    TextView mTvFeedArticleDetailUserReaction;
    private FragmentOpen mFragmentOpen;
    ViewPagerAdapter viewPagerAdapter;

    public static void navigateFromArticle(AppCompatActivity activity, View transitionImage, FeedDetail feedDetail) {
        Intent intent = new Intent(activity, ArticleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.ARTICLE_DETAIL, feedDetail);
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
        mFragmentOpen = new FragmentOpen();
        if (null != getIntent()) {
            mFeedDetail = getIntent().getParcelableExtra(AppConstants.ARTICLE_DETAIL);
        }
        setPagerAndLayouts();
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.ARTICLE_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbarArticleDetail);
        mCollapsingToolbarLayout.setExpandedSubTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        if (null != mFeedDetail) {
            if(mFeedDetail.isBookmarked()) {
                mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
            }
            else
            {
                mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_in_active, 0, 0, 0);
            }
            mCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
            mCollapsingToolbarLayout.setSubtitle(mFeedDetail.getAuthorName());
            mTvArticleDetailTotalViews.setText(mFeedDetail.getNoOfViews() + AppConstants.SPACE + getString(R.string.ID_VIEWS));
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(ArticleDetailFragment.createInstance(mFeedDetail), getString(R.string.ID_ARTICLE));
            mViewPagerArticleDetail.setAdapter(viewPagerAdapter);
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getImageUrl())) {
                Glide.with(this)
                        .load(mFeedDetail.getImageUrl()).asBitmap()
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
        supportStartPostponedEnterTransition();
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

        if (baseResponse instanceof FeedDetail) {
            articleDetailHandled(view, baseResponse);
        }else if (baseResponse instanceof CommentReactionDoc) {
            clickMenuItem(view, baseResponse, true);
        }
    }

    private void articleDetailHandled(View view, BaseResponse baseResponse) {
        mFeedDetail = (FeedDetail) baseResponse;
        int id = view.getId();
        switch (id) {
            case R.id.tv_article_detail_user_comment_post_menu:
                clickMenuItem(view, baseResponse,false);
                break;
            case R.id.tv_article_detail_user_comment_post_menu_second:
                clickMenuItem(view, baseResponse,false);
                break;
            case R.id.tv_article_detail_user_comment_post_menu_third:
                clickMenuItem(view, baseResponse,false);
                break;
            case R.id.li_article_detail_join_conversation:
                mFragmentOpen.setCommentList(true);
                mFragmentOpen.setReactionList(false);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.tv_article_detail_total_reactions:
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
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

    protected void openCommentReactionFragment(FeedDetail feedDetail) {
        if (null != feedDetail && null != mFragmentOpen) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.TWO_CONSTANT);
            CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
            Bundle bundleArticle = new Bundle();
            bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
            bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
            commentReactionFragmentForArticle.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.fl_article_detail_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
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
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        Fragment fragment = viewPagerAdapter.getActiveFragment(mViewPagerArticleDetail, 0);
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((ArticleDetailFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
        }
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
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                userCommentLikeRequest(mFeedDetail, AppConstants.HEART_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                break;
            case R.id.tv_reaction1:
                if (null != mTvFeedArticleDetailUserReaction) {
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FIRST_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                break;
            case R.id.tv_reaction2:
                if (null != mTvFeedArticleDetailUserReaction) {
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_SECOND_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                break;
            case R.id.tv_reaction3:
                if (null != mTvFeedArticleDetailUserReaction) {
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_THIRD_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                break;
            case R.id.tv_reaction4:
                if (null != mTvFeedArticleDetailUserReaction) {
                    if (null != mArticlePopUp) {
                        mArticlePopUp.setVisibility(View.GONE);
                        dismissUserReactionOption(mArticlePopUp);
                    }
                }
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FOURTH_REACTION_CONSTANT, mFeedDetail.getItemPosition());
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
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPressed();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        if (null != isFragmentOpen && null != feedDetail) {
            mFragmentOpen = isFragmentOpen;
            if (mFragmentOpen.isReactionList()) {
                CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
                Bundle bundleArticle = new Bundle();
                bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
                bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
                commentReactionFragmentForArticle.setArguments(bundleArticle);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                        .replace(R.id.fl_article_detail_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            Fragment fragment = viewPagerAdapter.getActiveFragment(mViewPagerArticleDetail, 0);
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((ArticleDetailFragment) fragment).commentListRefresh(mFeedDetail);
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

    protected void clickMenuItem(View view, final BaseResponse baseResponse,final boolean isCommentReaction) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.menu_option_layout, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        final TextView tvEdit = (TextView) popupView.findViewById(R.id.tv_article_menu_edit);
        final TextView tvDelete = (TextView) popupView.findViewById(R.id.tv_article_menu_delete);
        final TextView tvShare = (TextView) popupView.findViewById(R.id.tv_article_menu_share);
        final TextView tvReport = (TextView) popupView.findViewById(R.id.tv_article_menu_report);
        final Fragment fragmentCommentReaction = getSupportFragmentManager().findFragmentByTag(CommentReactionFragment.class.getName());
        popupWindow.showAsDropDown(view);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCommentReaction) {
                    CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
                    if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                        commentReactionDoc.setActive(true);
                        commentReactionDoc.setEdit(true);
                        ((CommentReactionFragment) fragmentCommentReaction).editCommentInList(commentReactionDoc);
                    }
                } else {
                    if (null != mFeedDetail) {
                        mFragmentOpen.setOpen(true);
                        mFragmentOpen.setCommentList(true);
                        mFeedDetail.setTrending(true);
                        mFeedDetail.setExperienceFromI(AppConstants.ONE_CONSTANT);
                        openCommentReactionFragment(mFeedDetail);
                    }
                }
                popupWindow.dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCommentReaction) {
                    CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
                    if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                        commentReactionDoc.setActive(false);
                        commentReactionDoc.setEdit(false);
                        ((CommentReactionFragment) fragmentCommentReaction).deleteCommentFromList(commentReactionDoc);
                    }
                } else {
                    if (null != mFeedDetail) {
                        mFragmentOpen.setOpen(true);
                        mFragmentOpen.setCommentList(true);
                        mFeedDetail.setTrending(true);
                        mFeedDetail.setExperienceFromI(AppConstants.TWO_CONSTANT);
                        openCommentReactionFragment(mFeedDetail);
                    }
                }
                popupWindow.dismiss();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tvEdit.setVisibility(View.VISIBLE);
        tvDelete.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.tv_article_detail_bookmark)
    public void onBookMarkClick() {
        mTvArticleDetailBookmark.setEnabled(false);
        mFeedDetail.setItemPosition(0);
        bookmarkCall();
    }
    private void bookmarkCall() {
        Fragment fragment = viewPagerAdapter.getActiveFragment(mViewPagerArticleDetail, 0);
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((ArticleDetailFragment) fragment).bookMarkForCard(mFeedDetail);
        }
    }
    @OnClick(R.id.iv_article_detail_back)
    public void onBackClick() {

        if (!mFeedDetail.isFromHome()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        finish();
        overridePendingTransition(R.anim.fade_in_dialog, R.anim.fade_out_dialog);
    }
    @OnClick(R.id.tv_article_detail_share)
    public void shareOnClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, "Testing Text From SHEROES2.0");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this site!");
        startActivity(Intent.createChooser(intent,AppConstants.SHARE));
    }

    @Override
    public void onBookmarkClick(FeedDetail feedDetail) {
        mTvArticleDetailBookmark.setEnabled(true);
        if (!feedDetail.isBookmarked()) {
            feedDetail.setBookmarked(true);
          mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
        } else {
            feedDetail.setBookmarked(false);
         mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_in_active, 0, 0, 0);
        }
        mFeedDetail=feedDetail;
    }
}