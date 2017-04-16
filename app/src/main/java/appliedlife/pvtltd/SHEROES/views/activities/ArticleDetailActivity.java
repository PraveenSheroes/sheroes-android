package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ArticleDetailActivity extends BaseActivity implements CommentReactionFragment.HomeActivityIntractionListner, AppBarLayout.OnOffsetChangedListener {
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
    CustomCollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tv_article_detail_total_views)
    TextView mTvArticleDetailTotalViews;
    @Bind(R.id.tv_article_time)
    TextView mTvArticleTime;
    @Bind(R.id.tv_article_detail_bookmark)
    TextView mTvArticleDetailBookmark;
    @Bind(R.id.tv_article_detail_title)
    TextView mTvArticleDetailTitle;
    @Bind(R.id.tv_article_detail_subtitle)
    TextView mTvArticleDetailSubTitle;
    @Bind(R.id.li_header)
    public LinearLayout mLiHeader;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    ViewPagerAdapter viewPagerAdapter;
    long mArticleId = 0;
    int feedDetailPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        mAppBarLayout.addOnOffsetChangedListener(this);
        mFragmentOpen = new FragmentOpen();
        if (null != getIntent()) {
            mFeedDetail = getIntent().getParcelableExtra(AppConstants.ARTICLE_DETAIL);
            if (null != getIntent().getExtras() && null != getIntent().getExtras().get(AppConstants.ARTICLE_ID)) {
                mArticleId = (long) getIntent().getExtras().get(AppConstants.ARTICLE_ID);
                if (mArticleId > 0) {
                    mFeedDetail = new FeedDetail();
                    mFeedDetail.setIdOfEntityOrParticipant(mArticleId);
                }
            }
        }
        setPagerAndLayouts();
    }
    @Override
    public void onStop() {
        super.onStop();
        mAppBarLayout.removeOnOffsetChangedListener(this);
    }

    private void setPagerAndLayouts() {
      //  ViewCompat.setTransitionName(mAppBarLayout, AppConstants.ARTICLE_DETAIL);
        //supportPostponeEnterTransition();
        setSupportActionBar(mToolbarArticleDetail);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        if (null != mFeedDetail) {
            feedDetailPosition=mFeedDetail.getItemPosition();
            mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
            mCollapsingToolbarLayout.setSubtitle(AppConstants.EMPTY_STRING);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(ArticleDetailFragment.createInstance(mFeedDetail), getString(R.string.ID_ARTICLE));
            mViewPagerArticleDetail.setAdapter(viewPagerAdapter);
            setBackGroundImage(mFeedDetail);
        }
    }
    public void setBackGroundImage(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
        mTvArticleDetailTitle.setText(mFeedDetail.getNameOrTitle());
        mTvArticleDetailSubTitle.setText(mFeedDetail.getAuthorName());
        mTvArticleDetailTotalViews.setVisibility(View.VISIBLE);
        if(mFeedDetail.getNoOfViews()>0) {
            mTvArticleDetailTotalViews.setVisibility(View.VISIBLE);
            mTvArticleDetailTotalViews.setText(mFeedDetail.getNoOfViews() + AppConstants.SPACE + getString(R.string.ID_VIEWS));
        }else
        {
            mTvArticleDetailTotalViews.setVisibility(View.INVISIBLE);
        }
        if(mFeedDetail.getCharCount()>0)
        {
            mTvArticleTime.setVisibility(View.VISIBLE);
            mTvArticleTime.setText(mFeedDetail.getCharCount() + AppConstants.SPACE + getString(R.string.ID_MIN_READ));

        }else
        {
            mTvArticleTime.setVisibility(View.INVISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            mCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
        }
        if (mFeedDetail.isBookmarked()) {
            mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
        } else {
            mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_detail_white, 0, 0, 0);
        }
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
        } else if (baseResponse instanceof CommentReactionDoc) {
            clickMenuItem(view, baseResponse, true);
        }
    }

    private void articleDetailHandled(View view, BaseResponse baseResponse) {
        mFeedDetail = (FeedDetail) baseResponse;
        int id = view.getId();
        switch (id) {
            case R.id.tv_article_detail_user_comment_post_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_article_detail_user_comment_post_menu_second:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_article_detail_user_comment_post_menu_third:
                clickMenuItem(view, baseResponse, false);
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
                userReactionDialogLongPress(view);
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    private void userReactionDialogLongPress(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.emoji_reaction_layout, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        TextView tvCommunityReaction = (TextView) popupView.findViewById(R.id.tv_reaction);
        TextView tvCommunityReaction1 = (TextView) popupView.findViewById(R.id.tv_reaction1);
        TextView tvCommunityReaction2 = (TextView) popupView.findViewById(R.id.tv_reaction2);
        TextView tvCommunityReaction3 = (TextView) popupView.findViewById(R.id.tv_reaction3);
        TextView tvCommunityReaction4 = (TextView) popupView.findViewById(R.id.tv_reaction4);
        tvCommunityReaction.setOnClickListener(this);
        tvCommunityReaction1.setOnClickListener(this);
        tvCommunityReaction2.setOnClickListener(this);
        tvCommunityReaction3.setOnClickListener(this);
        tvCommunityReaction4.setOnClickListener(this);
        popupWindow.showAsDropDown(view, 0, -250);
        popupView.setOnTouchListener(this);
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
                userCommentLikeRequest(mFeedDetail, AppConstants.HEART_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction1:
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FIRST_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction2:

                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_SECOND_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction3:

                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_THIRD_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction4:

                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FOURTH_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
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
        popupWindow.dismiss();
        return true;
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
            mFragmentOpen.setCommentList(false);
            getSupportFragmentManager().popBackStackImmediate();
            Fragment fragment = viewPagerAdapter.getActiveFragment(mViewPagerArticleDetail, 0);
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((ArticleDetailFragment) fragment).commentListRefresh(mFeedDetail);
            }
        } else if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
            getSupportFragmentManager().popBackStack();
        } else {
            onBackClick();
        }
    }

    protected void clickMenuItem(View view, final BaseResponse baseResponse, final boolean isCommentReaction) {
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
        popupWindow.showAsDropDown(view, -140, 0);
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
        mFeedDetail.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
        bookmarkCall();
    }

    private void bookmarkCall() {
        Fragment fragment = viewPagerAdapter.getActiveFragment(mViewPagerArticleDetail, 0);
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((ArticleDetailFragment) fragment).bookMarkForCard(mFeedDetail);
        }
    }

    @OnClick(R.id.tv_article_detail_back)
    public void onBackClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        mFeedDetail.setItemPosition(feedDetailPosition);
        bundle.putParcelable(AppConstants.HOME_FRAGMENT,mFeedDetail);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.right_to_left_anim_enter, R.anim.right_to_left_anim_exit);
    }

    @OnClick(R.id.tv_article_detail_share)
    public void shareOnClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mFeedDetail.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
    }

    public void onBookmarkClick(FeedDetail feedDetail, int successFrom) {
        if (successFrom == AppConstants.ONE_CONSTANT) {
            mTvArticleDetailBookmark.setEnabled(true);
            if (!feedDetail.isBookmarked()) {
                feedDetail.setBookmarked(true);
                mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
            } else {
                feedDetail.setBookmarked(false);
                mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_detail_white, 0, 0, 0);
            }
        }
        mFeedDetail = feedDetail;
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
        LogUtils.info(TAG,"****************offset***"+verticalOffset);
        if(verticalOffset>=AppConstants.NO_REACTION_CONSTANT)
        {
            mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
            mCollapsingToolbarLayout.setSubtitle(AppConstants.EMPTY_STRING);
            mLiHeader.setVisibility(View.INVISIBLE);
        }else
        {
            mLiHeader.setVisibility(View.VISIBLE);
        }

    }
}