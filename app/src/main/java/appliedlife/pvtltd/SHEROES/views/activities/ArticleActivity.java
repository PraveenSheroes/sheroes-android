package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Article;
import appliedlife.pvtltd.SHEROES.models.entities.post.UserProfile;
import appliedlife.pvtltd.SHEROES.presenters.ArticlePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ScrimUtil;
import appliedlife.pvtltd.SHEROES.utils.VideoEnabledWebChromeClient;
import appliedlife.pvtltd.SHEROES.utils.WebViewClickListener;
import appliedlife.pvtltd.SHEROES.views.adapters.CommentListAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.VideoEnabledWebView;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.postCommentRequestBuilder;

/**
 * Created by ujjwal on 28/10/17.
 */
public class ArticleActivity extends BaseActivity implements IArticleView, NestedScrollView.OnScrollChangeListener {

    public static final String SCREEN_LABEL = "Article Activity";
    public static final String IMAGE_WIDTH = "IMAGE_WIDTH";
    public static final String IMAGE_HEIGHT = "IMAGE_HEIGHT";
    private static final String NOTIFICATION_SCREEN = "Push Notification";
    private static final String RELATIVE_PATH_ASSETS = "file:///android_asset/";
    private static final String TRANSITION = "TRANSITION";
    private static final String FEED_POSITION = "Feed Position";
    int defaultUi;
    private WebViewClickListener webViewClickListener = null;
    public int mCommentCount = 0;
    public boolean mHasFocus = false;

    //region member variabe
    private VideoEnabledWebChromeClient webChromeClient;
    private CommentListAdapter mCommentsAdapter;
    private int mImageWidth;
    private int mImageHeight;
    private int mArticleId;
    private int mFeedPosition;
    public Article mArticle;
    private long mScrollPercentage = 0;
    boolean isScrollingDown = false;
    private boolean mIsTransition = false;
    private FeedDetail mFeedDetail;

    @Inject
    ArticlePresenterImpl mArticlePresenter;

    @Inject
    AppUtils mAppUtils;
    //endregion

    //region View variables
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.progress_bar_web_view)
    ProgressBar mWebViewProgressBar;

    @Bind(R.id.text)
    VideoEnabledWebView webViewText;

    @Bind(R.id.video_layout)
    RelativeLayout videoLayout;

    @Bind(R.id.author_pic)
    ImageView authorPic;

    @Bind(R.id.author_description__pic)
    ImageView authorDesPic;

    @Bind(R.id.author_description_view)
    RelativeLayout mAuthorDesView;

    @Bind(R.id.author_description_name)
    TextView authorDesName;

    @Bind(R.id.author_description)
    TextView authorDescription;

    @Bind(R.id.author)
    TextView author;

    @Bind(R.id.likes_views_comments)
    TextView mLikesViewsComments;

    @BindDimen(R.dimen.authorPicSize)
    int authorPicSize;

    @Bind(R.id.scrim_view)
    View mScrimView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.layout_article)
    NestedScrollView mArticleLayout;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @BindColor(R.color.colorPrimary)
    int mPrimaryColor;

    @BindColor(R.color.colorPrimaryDark)
    int mPrimaryDarkColor;

    @Bind(R.id.home)
    CoordinatorLayout rootLayout;

    @Bind(R.id.comments)
    RelativeLayout mComments;

    @Bind(R.id.title_comment)
    TextView mTitleComment;

    @Bind(R.id.comment_list)
    RecyclerView mCommentList;

    @Bind(R.id.comment_body)
    EditText mCommentBody;

    @Bind(R.id.submit)
    Button mSubmitButton;

    @Bind(R.id.cancel)
    RelativeLayout mCancelButton;

    @Bind(R.id.more_comments)
    TextView mMoreComments;

    @Bind(R.id.like_count)
    TextView mLikeCount;

    @Bind(R.id.border)
    View mBorder;

    @BindDimen(R.dimen.article_image_height_tmp)
    int articleImageHeight;

    private   View articleToolTip;
    private PopupWindow popupWindowArticleTooTip;
    //endregion

    //region Activity methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_article);
        ActivityCompat.postponeEnterTransition(ArticleActivity.this);
        ButterKnife.bind(this);
        mArticlePresenter.attachView(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Parcelable parcelable = getIntent().getParcelableExtra(Article.ARTICLE_OBJ);
        mIsTransition = getIntent().getBooleanExtra(TRANSITION, false);
        mFeedPosition = getIntent().getIntExtra(FEED_POSITION, -1);
        if (parcelable != null) {
            mArticle = Parcels.unwrap(parcelable);
            if (mArticle != null) {
                mCommentCount = mArticle.commentsCount;
                updateTitleCommentCountView();
                mImageWidth = mArticle.featureImageWidth;
                mImageHeight = mArticle.featureImageHeight;
            }
        } else {
            if (getIntent().getExtras() != null) {
                String notificationId = getIntent().getExtras().getString("notificationId");
                Long i = getIntent().getExtras().getLong(AppConstants.ARTICLE_ID, -1);
                mArticleId = i.intValue();
                if (!TextUtils.isEmpty(notificationId)) {
                    setSource(NOTIFICATION_SCREEN);
                }
                mImageWidth = getIntent().getExtras().getInt(IMAGE_WIDTH);
                mImageHeight = getIntent().getExtras().getInt(IMAGE_HEIGHT);
            } else {
                finish();
            }
        }

        mScrimView.setBackground(ScrimUtil.makeCubicGradientScrimDrawable(
                0xaa000000, 8, Gravity.TOP));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        defaultUi = getWindow().getDecorView().getSystemUiVisibility();
        mArticleLayout.setOnScrollChangeListener(this);

        initializeCommentsAdapter();

        if (mArticle != null) {
            loadArticleImage(mArticle);
        }
        fetchArticle(mArticle == null ? mArticleId : (int) mArticle.id, mArticle != null);

        mCommentBody.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mHasFocus = true;
                    fab.hide();
                    mSubmitButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.VISIBLE);
                    mArticleLayout.smoothScrollTo(0, mAuthorDesView.getBottom());

                } else {
                    mHasFocus = false;
                    mSubmitButton.setVisibility(View.GONE);
                    mCancelButton.setVisibility(View.GONE);
                }
            }
        });

        applyPalette();
        if (CommonUtil.forGivenCountOnly(AppConstants.ARTICLE_SHARE_SESSION_PREF, AppConstants.ARTICLE_SESSION) == AppConstants.ARTICLE_SESSION) {
            if (CommonUtil.ensureFirstTime(AppConstants.ARTICLE_SHARE_PREF)) {
                toolTipForShareArticle();
            }
        }
    }

    private void toolTipForShareArticle() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    int width = AppUtils.getWindowWidth(ArticleActivity.this);
                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    articleToolTip = layoutInflater.inflate(R.layout.tool_tip_arrow_down_side, null);
                    popupWindowArticleTooTip = new PopupWindow(articleToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindowArticleTooTip.setOutsideTouchable(true);
                    if (width < 750) {
                        popupWindowArticleTooTip.showAsDropDown(fab, -450, -300);
                    } else {
                        popupWindowArticleTooTip.showAsDropDown(fab, -700, -400);
                    }
                    final ImageView ivArrow = articleToolTip.findViewById(R.id.iv_arrow);
                    RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(10, ArticleActivity.this), 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
                    imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                    imageParams.addRule(RelativeLayout.BELOW, R.id.ll_tool_tip_bg);
                    ivArrow.setLayoutParams(imageParams);

                    final TextView tvGotIt = articleToolTip.findViewById(R.id.got_it);
                    final TextView tvTitle = articleToolTip.findViewById(R.id.title);
                    tvTitle.setText(getString(R.string.tool_tip_article_share));
                    tvGotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindowArticleTooTip.dismiss();
                        }
                    });
                } catch (WindowManager.BadTokenException e) {
                    Crashlytics.getInstance().core.logException(e);
                }
            }
        }, 1000);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
        setUpOptionMenuStates(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setUpOptionMenuStates(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = AppConstants.NAV_ARTICLES;
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onResume", (Class[]) null)
                    .invoke(webViewText, (Object[]) null);

        } catch (Exception exception) {
            Crashlytics.getInstance().core.logException(exception);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(webViewText, (Object[]) null);
        } catch (Exception exception) {
            Crashlytics.getInstance().core.logException(exception);
        }
    }

    private void setUpOptionMenuStates(Menu menu) {
        MenuItem itemLike = menu.findItem(R.id.like);
        MenuItem itemBookmark = menu.findItem(R.id.bookmark);
        if (itemLike != null && itemBookmark != null) {
            menu.findItem(R.id.like).setIcon(mArticlePresenter.getLikeDrawable(mArticle));
            menu.findItem(R.id.bookmark).setIcon(mArticlePresenter.getBookmarkDrawable(mArticle));
            itemLike.setVisible(mArticlePresenter.getMenuItemsVisibility(mArticle));
            itemBookmark.setVisible(mArticlePresenter.getMenuItemsVisibility(mArticle));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                destroyWebView();
                onBackPressed();
                break;
            case R.id.like:
                mArticlePresenter.prepareLike(mArticle);
                break;
            case R.id.bookmark:
                mArticlePresenter.prepareBookmark(mArticle);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        }
        if (webChromeClient != null && !webChromeClient.onBackPressed()) {
            if (webViewText.canGoBack()) {
                webViewText.goBack();
            } else {
                destroyWebView();
                setResult();
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mArticlePresenter;
    }

    private void setResult() {
        if (mFeedDetail == null) {
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        mFeedDetail.setItemPosition(mFeedPosition);
        Parcelable parcelable = Parcels.wrap(mFeedDetail);
        bundle.putParcelable(AppConstants.HOME_FRAGMENT, parcelable);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        final EventProperty.Builder builder = new EventProperty.Builder();
        if (mArticle != null) {
            builder.title(mArticle.title)
                    .id(Integer.toString(mArticle.remote_id));

        }

        HashMap<String, Object> properties = builder.build();
        return properties;
    }
    //endregion

    //region Private Helper methods
    private void initializeCommentsAdapter() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        mCommentList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) mCommentList.getItemAnimator()).setSupportsChangeAnimations(false);
        mCommentList.setLayoutManager(mLinearLayoutManager);
        mCommentList.setNestedScrollingEnabled(false);
        mCommentList.setFocusable(false);
        mCommentsAdapter = new CommentListAdapter(this, mArticlePresenter, new View.OnClickListener() {
            @Override
            public void onClick(View deleteItem) {
                View recyclerViewItem = (View) deleteItem.getParent();
                final int position = mCommentList.getChildAdapterPosition(recyclerViewItem);
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                switch (deleteItem.getId()) {
                    case R.id.author_pic:
                    case R.id.author:

                        Comment comment = mCommentsAdapter.getComment(position);
                        if (!comment.isAnonymous()) {
                            openProfile(comment.getParticipantUserId(), comment.isVerifiedMentor(), SCREEN_LABEL);
                        }
                        break;

                    case R.id.delete:
                        PopupMenu popup = new PopupMenu(ArticleActivity.this, deleteItem);
                        popup.getMenuInflater().inflate(R.menu.menu_delete, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                Comment comment = mCommentsAdapter.getComment(position);
                                if (comment == null) {
                                    return true;
                                }
                                mArticlePresenter.onDeleteCommentClicked(position, mAppUtils.editCommentRequestBuilder(comment.getEntityId(), comment.getComment(), false, false, comment.getId()));
                                return true;
                            }
                        });
                        popup.show();
                        break;
                }
            }
        });
        mCommentList.setAdapter(mCommentsAdapter);
    }

    private void fetchArticle(int articleId, boolean isImageLoaded) {
        mArticlePresenter.fetchArticle(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_ARTICLE, AppConstants.ONE_CONSTANT, articleId), isImageLoaded);
    }

    private void openProfile(Long userId, boolean isMentor, String source) {
        ProfileActivity.navigateTo(this, userId, isMentor, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void updateTitleCommentCountView() {
        String pluralComment = this.getResources().getQuantityString(R.plurals.numberOfComments, mCommentCount);
        mTitleComment.setText(mCommentCount + " " + pluralComment);
    }

    private boolean validateData() {
        if (mArticle == null) {
            Crashlytics.getInstance().core.logException(new IllegalStateException("Article is null"));
            return false;
        }
        return true;
    }

    private void applyPalette() {
        mCollapsingToolbarLayout.setContentScrimColor(mPrimaryColor);
        mCollapsingToolbarLayout.setStatusBarScrimColor(mPrimaryDarkColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CommonUtil.colorBurn(mPrimaryDarkColor));
        }
    }
    //endregion

    //region ButterKnife Bindings
    @OnClick(R.id.fab)
    void onFabClick() {
        if (mArticle != null) {
            AnalyticsManager.trackPostAction(Event.POST_SHARED_CLICKED, mFeedDetail, getScreenName());
            ShareBottomSheetFragment.showDialog(this, mArticle.deepLink, null, mArticle.deepLink, SCREEN_LABEL, false, mArticle.deepLink, false, Event.POST_SHARED, MixpanelHelper.getPostProperties(mFeedDetail, getScreenName()));
        }
    }

    @OnClick(R.id.submit)
    public void onSubmitClicked() {
        String commentBody = mCommentBody.getText().toString().trim();
        if (CommonUtil.isNotEmpty(commentBody)) {
            mArticlePresenter.postComment(postCommentRequestBuilder(mArticle.remote_id, commentBody, false));
        }
        mCommentBody.setText("");
        mCommentBody.clearFocus();
        CommonUtil.hideKeyboard(ArticleActivity.this);
    }

    @OnClick(R.id.cancel)
    public void onCancelClick() {
        mCommentBody.setText("");
        mCommentBody.clearFocus();
        CommonUtil.hideKeyboard(ArticleActivity.this);
    }

    //endregion

    //region IArticleView methods

    @Override
    public void startProgressBar() {
        mAuthorDesView.setVisibility(View.GONE);
        webViewText.setVisibility(View.VISIBLE);
        mComments.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mArticleLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mArticleLayout.setVisibility(View.VISIBLE);
        mAuthorDesView.setVisibility(View.VISIBLE);
        webViewText.setVisibility(View.VISIBLE);
        mComments.setVisibility(View.VISIBLE);
    }

    @Override
    public void showArticle(final Article article, boolean imageLoaded) {
        mImageHeight = article.featureImageHeight;
        mImageWidth = article.featureImageWidth;

        mArticle = article;
        invalidateOptionsMenu();
        mCommentCount = article.commentsCount;
        if (!validateData()) {
            onBackPressed();
        }
        if (mArticle.isThreadClosed) {
            mCommentBody.setVisibility(View.GONE);
        } else {
            mCommentBody.setVisibility(View.VISIBLE);
        }
        if (mArticle.showComments) {
            mComments.setVisibility(View.VISIBLE);
            mCommentList.setVisibility(View.VISIBLE);
        } else {
            mComments.setVisibility(View.GONE);
            mCommentList.setVisibility(View.GONE);
        }

        if (!imageLoaded) {
            loadArticleImage(article);
        }

        loadUserViews(article);
        if (!CommonUtil.isNotEmpty(article.body)) {
            return;
        }

        webChromeClient = new VideoEnabledWebChromeClient(rootLayout, videoLayout, null, webViewText);
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        });

        if (!CommonUtil.isNotEmpty(article.body)) {
            return;
        }
        final String webViewStyle = getStyleFromConfig();
        String htmlData = article.body == null ? "" : article.body;
        htmlData = "<style>" + webViewStyle + " </style> <body> " + getJavaScriptFromConfig() + htmlData + " </body>";
        webViewText.getSettings().setJavaScriptEnabled(true);
        webViewText.setWebChromeClient(webChromeClient);
        webViewText.setVerticalScrollBarEnabled(false);
        webViewText.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                stopProgressBar();
                webViewText.loadUrl("javascript:initials()");
                view.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });

        webViewText.setFocusable(false);
        WebSettings webSettings = webViewText.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (webViewClickListener == null) {
            webViewClickListener = new WebViewClickListener(this);
            webViewText.addJavascriptInterface(webViewClickListener, "image");
            webViewText.addJavascriptInterface(webViewClickListener, "video");
        }
        webViewText.loadDataWithBaseURL(RELATIVE_PATH_ASSETS, htmlData, "text/html", "UTF-8", null);
    }


    private void loadUserViews(Article article) {
        if (article.author != null) {
            author.setText(article.author.name);
            String pluralLikes = getResources().getQuantityString(R.plurals.numberOfLikes, article.likesCount);
            mLikeCount.setText(CommonUtil.getRoundedMetricFormat(article.likesCount) + " " + pluralLikes);
            String pluralViews = getResources().getQuantityString(R.plurals.numberOfViews, article.totalViews);
            mLikesViewsComments.setText(article.createdAt + " " + "\u2022" + " " + article.getReadingTime() + " " + "\u2022" + " " + CommonUtil.getRoundedMetricFormat(article.totalViews) + " " + pluralViews);
            if (article.author.thumbUrl != null && CommonUtil.isNotEmpty(article.author.thumbUrl)) {
                String authorImage = CommonUtil.getImgKitUri(article.author.thumbUrl, authorPicSize, authorPicSize);
                Glide.with(this)
                        .load(authorImage)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(this)))
                        .into(authorPic);

            }
            if (article.author.thumbUrl != null && CommonUtil.isNotEmpty(article.author.thumbUrl)) {
                String authorImage = CommonUtil.getImgKitUri(article.author.thumbUrl, authorPicSize, authorPicSize);
                Glide.with(this)
                        .load(authorImage)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(this)))
                        .into(authorDesPic);

            }
            authorDesName.setText(article.author.name);
            authorDescription.setText(Html.fromHtml(article.author.shortDescription));

        }
        title.setText(article.title);
    }

    private void loadArticleImage(Article article) {
        String imageUri = article.featureImage;
        int imageNewHeight;
        if (mImageWidth != 0 && mImageHeight != 0) {
            imageNewHeight = (int) (((float) mImageHeight / (float) mImageWidth) * CommonUtil.getWindowWidth(this));
        } else {
            imageNewHeight = articleImageHeight;
        }
        image.getLayoutParams().height = imageNewHeight;
        if (CommonUtil.isNotEmpty(article.featureImage)) {
            String finalImageUri = CommonUtil.getImgKitUri(imageUri, CommonUtil.getWindowWidth(this), imageNewHeight);
            Glide.with(ArticleActivity.this)
                    .asBitmap()
                    .load(finalImageUri)
                    .into(image);
        }
        loadUserViews(article);
    }

    @Override
    public void invalidateBookmark(Article article) {
        if (mFeedDetail != null) {
            mFeedDetail.setBookmarked(article.isBookmarked);
        }
        mArticle = article;
        invalidateOptionsMenu();
    }

    @Override
    public void invalidateLike(Article article) {
        if (mFeedDetail != null) {
            mFeedDetail.setReactionValue(article.isLiked ? AppConstants.HEART_REACTION_CONSTANT : AppConstants.NO_REACTION_CONSTANT);
        }
        mArticle = article;
        String pluralLikes = getResources().getQuantityString(R.plurals.numberOfLikes, article.likesCount);
        mLikeCount.setText(CommonUtil.getRoundedMetricFormat(article.likesCount) + " " + pluralLikes);
        invalidateOptionsMenu();
    }

    @Override
    public void showComments(ArrayList<Comment> comments, int commentsCount) {
        mCommentCount = commentsCount;
        updateTitleCommentCountView();
        if (CommonUtil.isEmpty(comments)) {
            return;
        }
        ArrayList<Comment> commentsList = new ArrayList<>(comments);
        if (commentsList.size() < commentsCount && commentsCount > CommentListAdapter.INITIAL_ITEM_COUNT) {
            showMoreCommentView(commentsCount - CommentListAdapter.INITIAL_ITEM_COUNT);
        }
        mCommentsAdapter.setData(commentsList);
    }

    private void showMoreCommentView(int loadMoreItemCount) {
        String countInfo = getString(R.string.more_text_info, Integer.toString(loadMoreItemCount));
        String loadMore = getString(R.string.load_more).toUpperCase();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                CommonUtil.hideKeyboard(ArticleActivity.this);
                mCommentsAdapter.showMoreItem(true);
                mArticlePresenter.fetchAllComments(getCommentRequestBuilder(mArticle.remote_id, 1, 100));
                mMoreComments.setVisibility(View.GONE);
                mBorder.setVisibility(View.GONE);
            }
        };
        SpannableString formattedSpan = CommonUtil.combineStringsStyles(this, countInfo, R.style.LoadMoreInfoStyle, loadMore, R.style.UnBlockTextStyle, " ");
        formattedSpan.setSpan(clickableSpan, countInfo.length() + 1, formattedSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mMoreComments.setText(formattedSpan, TextView.BufferType.SPANNABLE);
        mBorder.setVisibility(View.VISIBLE);
        mMoreComments.setVisibility(View.VISIBLE);
        mMoreComments.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void addAndNotifyComment(Comment comment) {
        mCommentCount++;
        updateTitleCommentCountView();
        mCommentsAdapter.addDataAndNotify(comment);
    }

    @Override
    public void removeAndNotifyComment(int position) {
        mCommentCount--;
        updateTitleCommentCountView();
        mCommentsAdapter.removeAndNotify(position);
    }


    @Override
    public void showMessage(int stringRes) {
        Snackbar.make(mCommentList, stringRes, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setAndNotify(int position, Comment comment) {
        mCommentsAdapter.setData(comment, position);
    }

    @Override
    public void setFeedDetail(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
    }

    @Override
    public void trackEvent(Event event) {
        if (mFeedDetail == null) {
            return;
        } else {
            AnalyticsManager.trackPostAction(event, mFeedDetail, getScreenName());
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
    //endregion

    //region OnScroll methods
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (popupWindowArticleTooTip != null) {
            popupWindowArticleTooTip.dismiss();
        }
        int totalHeight = mArticleLayout.getChildAt(0).getHeight();
        int articleHeight = totalHeight;

        //If scrolled below article content we should ignore it.As he has already read the full article.
        if ((((double) scrollY + mArticleLayout.getHeight()) > (double) articleHeight)) {
            mScrollPercentage = 100;
        } else {
            mScrollPercentage = (long) ((((double) scrollY + mArticleLayout.getHeight()) / (double) articleHeight) * 100);
        }

        // If scroll is less than 95% only then go to lights off mode
        isScrollingDown = scrollY > oldScrollY && mScrollPercentage < 95;

        if (isScrollingDown) {
            fab.hide();
            int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
            getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        } else {
            if (!mHasFocus) {
                fab.show();
            }
            getSupportActionBar().show();
            getWindow().getDecorView().setSystemUiVisibility(defaultUi);
        }
    }
    //endregion

    // region Static methods

    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ArticleActivity.class);
        Article article = new Article();
        ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
        article.title = articleSolrObj.getNameOrTitle();
        article.id = articleSolrObj.getIdOfEntityOrParticipant();
        article.featureImage = articleSolrObj.getImageUrl();
        article.commentsCount = articleSolrObj.getNoOfComments();
        article.likesCount = articleSolrObj.getNoOfLikes();
        article.author = new UserProfile();
        article.creatorId = articleSolrObj.getAuthorId();
        article.isCreatorMentor = articleSolrObj.isAuthorMentor();
        article.author.name = articleSolrObj.getAuthorName();
        article.author.shortDescription = articleSolrObj.getAuthorShortDescription();
        article.author.thumbUrl = articleSolrObj.getAuthorImageUrl();
        article.isBookmarked = articleSolrObj.isBookmarked();
        article.isLiked = articleSolrObj.getLastReactionValue() > 0;
        article.thumbImageWidth = articleSolrObj.getThumbImageWidth();
        article.thumbImageHeight = articleSolrObj.getThumbImageHeight();
        article.featureImageHeight = articleSolrObj.getHighresImageHeight();
        article.featureImageWidth = articleSolrObj.getHighresImageWidth();
        article.createdAt = articleSolrObj.getPostedDate();
        article.totalViews = articleSolrObj.getNoOfViews();
        article.readingTime = articleSolrObj.getCharCount();
        article.entityId = articleSolrObj.getEntityOrParticipantId();
        Parcelable parcelable = Parcels.wrap(article);
        intent.putExtra(Article.ARTICLE_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        intent.putExtra(FEED_POSITION, feedDetail.getItemPosition());
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    // endregion

    //region private methods
    private String getStyleFromConfig() {
        String styleFromConfig;
        styleFromConfig = AppConstants.webstyle;
        return styleFromConfig;
    }

    private void destroyWebView() {
        if (webViewText != null) {
            webViewText.destroy();
        }
    }

    private String getJavaScriptFromConfig() {
        String javaScriptFromConfig;
        javaScriptFromConfig = AppConstants.javascriptcode;
        return javaScriptFromConfig;
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        switch (errorMsg) {
            case AppConstants.CHECK_NETWORK_CONNECTION:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                break;
            case AppConstants.HTTP_401_UNAUTHORIZED:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_INVALID_USER_PASSWORD));
                break;
            default:
                showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @OnClick(R.id.like_count)
    public void onLikeCountClicked() {
        LikeListBottomSheetFragment.showDialog(this, "", mArticle.entityId);
    }
    //endregion
}