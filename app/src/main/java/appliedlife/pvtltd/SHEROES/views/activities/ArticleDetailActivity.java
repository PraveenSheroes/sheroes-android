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
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorDetailItem;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
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
    private final String SCREEN_LABEL = "Article Detail Activity Screen";
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
    public TextView mTvArticleDetailBookmark;
    @Bind(R.id.tv_article_detail_title)
    TextView mTvArticleDetailTitle;
    @Bind(R.id.tv_article_detail_subtitle)
    TextView mTvArticleDetailSubTitle;
    @Bind(R.id.li_header)
    public LinearLayout mLiHeader;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    private ViewPagerAdapter viewPagerAdapter;
    private long mArticleId = 0;
    private int feedDetailPosition;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private long startedTime;
    private int mFromNotification;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime=System.currentTimeMillis();
        mAppBarLayout.addOnOffsetChangedListener(this);
        mFragmentOpen = new FragmentOpen();
        if (null != getIntent()&&null!=getIntent().getExtras()) {
            mFeedDetail = getIntent().getParcelableExtra(AppConstants.ARTICLE_DETAIL);
                mFromNotification = getIntent().getExtras().getInt(AppConstants.BELL_NOTIFICATION);
                mArticleId = getIntent().getExtras().getLong(AppConstants.ARTICLE_ID);
                if (mArticleId > 0) {
                    mFeedDetail = new FeedDetail();
                    mFeedDetail.setIdOfEntityOrParticipant(mArticleId);
                }
        }
        setPagerAndLayouts();
        if(mFeedDetail!=null) {
            moEngageData(mFeedDetail);
        }
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_VIEW_ARTICLE));
    }

    @Override
    public void onStop() {
        super.onStop();
        mAppBarLayout.removeOnOffsetChangedListener(this);
    }

    private void setPagerAndLayouts() {
        setSupportActionBar(mToolbarArticleDetail);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        if (null != mFeedDetail) {
            feedDetailPosition = mFeedDetail.getItemPosition();
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
        if (mFeedDetail.getNoOfViews() > 1) {
            mTvArticleDetailTotalViews.setVisibility(View.VISIBLE);
            mTvArticleDetailTotalViews.setText(mFeedDetail.getNoOfViews() + AppConstants.SPACE + getString(R.string.ID_VIEWS));
        } else if (mFeedDetail.getNoOfViews() == 1) {
            mTvArticleDetailTotalViews.setVisibility(View.VISIBLE);
            mTvArticleDetailTotalViews.setText(mFeedDetail.getNoOfViews() + AppConstants.SPACE + getString(R.string.ID_VIEW));
        } else {
            mTvArticleDetailTotalViews.setVisibility(View.INVISIBLE);
        }
        if (mFeedDetail.getCharCount() > 0) {
            mTvArticleTime.setVisibility(View.VISIBLE);
            mTvArticleTime.setText(mFeedDetail.getCharCount() + AppConstants.SPACE + getString(R.string.ID_MIN_READ));

        } else {
            mTvArticleTime.setVisibility(View.INVISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            mCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
        }
        setBookMarkImage();
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

    private void setBookMarkImage() {
        if (mFeedDetail.isBookmarked()) {
            mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
        } else {
            mTvArticleDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_detail_white, 0, 0, 0);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_detail_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_article_detail_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
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
        if (!mFeedDetail.isBookmarked()) {
            mFeedDetail.setBookmarked(true);
            ((SheroesApplication)this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            mFeedDetail.setBookmarked(false);
            ((SheroesApplication)this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        }
        setBookMarkImage();
    }

    private void bookmarkCall() {
        Fragment fragment = viewPagerAdapter.getActiveFragment(mViewPagerArticleDetail, 0);
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((ArticleDetailFragment) fragment).bookMarkForCard(mFeedDetail);
        }
    }

    @OnClick(R.id.tv_article_detail_back)
    public void onBackClick() {
        if(mArticleId>0) {
            if (mFromNotification == AppConstants.NO_REACTION_CONSTANT) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }else {
                deepLinkPressHandle();
            }
        }else {
            deepLinkPressHandle();
        }
        finish();
    }
    private void deepLinkPressHandle()
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        mFeedDetail.setItemPosition(feedDetailPosition);
        bundle.putParcelable(AppConstants.HOME_FRAGMENT, mFeedDetail);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }
    private void moEngageData(FeedDetail feedDetail) {
        StringBuilder mergeTags =new StringBuilder();
        if (StringUtil.isNotEmptyCollection(feedDetail.getTags())) {
            List<String> tags = feedDetail.getTags();
            for (String tag : tags) {
                mergeTags.append(tag).append(AppConstants.COMMA);
            }
        }
        long timeSpent=System.currentTimeMillis()-startedTime;
        moEngageUtills.entityMoEngageArticleDetail(this, mMoEHelper, payloadBuilder,timeSpent,feedDetail.getNameOrTitle(), feedDetail.getIdOfEntityOrParticipant(), feedDetail.getArticleCategoryNameS(), mergeTags.toString(),feedDetail.getAuthorName(),feedDetail.getCharCount());
    }

    @OnClick(R.id.tv_article_detail_share)
    public void shareOnClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mFeedDetail.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        ((SheroesApplication)this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ARTICLE, AppConstants.EMPTY_STRING);
        moEngageUtills.entityMoEngageCardShareVia(getApplicationContext(),mMoEHelper,payloadBuilder,mFeedDetail, MoEngageConstants.SHARE_VIA_SOCIAL);
        AnalyticsManager.trackPostAction(Event.POST_SHARED, mFeedDetail);
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
        LogUtils.info(TAG, "****************offset***" + verticalOffset);
        if (verticalOffset >= AppConstants.NO_REACTION_CONSTANT) {
            mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
            mCollapsingToolbarLayout.setSubtitle(AppConstants.EMPTY_STRING);
            mLiHeader.setVisibility(View.INVISIBLE);
        } else {
            mLiHeader.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void championProfile(BaseResponse baseResponse, int championValue) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            championDetailActivity(feedDetail.getCreatedBy());
        } else if (baseResponse instanceof CommentReactionDoc) {
            CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
            championDetailActivity(commentReactionDoc.getParticipantId());
        }
    }

    private void championDetailActivity(Long userId) {
        Intent intent = new Intent(this, PublicProfileGrowthBuddiesDetailActivity.class);
        Bundle bundle = new Bundle();
        mFeedDetail = new FeedDetail();
        mFeedDetail.setIdOfEntityOrParticipant(userId);
        mFeedDetail.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        MentorDetailItem mentorDetailItem=new MentorDetailItem();
        mentorDetailItem.setEntityOrParticipantId(userId);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);

        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, mentorDetailItem);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant()))
                .build();
        return properties;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }
}