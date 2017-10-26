package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
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
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.common.LogUtil;
import com.squareup.pollexor.ThumborUrlBuilder;

import org.parceler.Parcels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.SheroesThumbor;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Article;
import appliedlife.pvtltd.SHEROES.models.entities.post.UserProfile;
import appliedlife.pvtltd.SHEROES.presenters.ArticlePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CustomTypefaceSpan;
import appliedlife.pvtltd.SHEROES.utils.ScrimUtil;
import appliedlife.pvtltd.SHEROES.utils.VideoEnabledWebChromeClient;
import appliedlife.pvtltd.SHEROES.utils.WebViewClickListener;
import appliedlife.pvtltd.SHEROES.views.adapters.CommentListAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.VideoEnabledWebView;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleView;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.postCommentRequestBuilder;

/**
 * Created by avinash on 28/01/16.
 */
public class ArticleActivity extends BaseActivity implements IArticleView, NestedScrollView.OnScrollChangeListener {

    public static final String SCREEN_LABEL = "Article Activity";
    private static final String TAG = "ArticleActivity";
    public static final String IMAGE_WIDTH = "IMAGE_WIDTH";
    public static final String IMAGE_HEIGHT = "IMAGE_HEIGHT";
    private static final String NOTIFICATION_SCREEN = "Push Notification";
    private static final String RELATIVE_PATH_ASSETS = "file:///android_asset/";
    private static final String TRANSITION = "TRANSITION";
    int defaultUi;
    private WebViewClickListener webViewClickListener = null;
    public int mCommentCount = 0;
    //region Presenter
    private VideoEnabledWebChromeClient webChromeClient;
    private CommentListAdapter mCommentsAdapter;
    private int mImageWidth;
    private int mImageHeight;
    private int mArticleId;
    private Handler mHandler;

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
    TextView mCommentBody;

    @Bind(R.id.submit)
    Button mSubmitButton;

    @Bind(R.id.cancel)
    RelativeLayout mCancelButton;

    @Bind(R.id.more_comments)
    TextView mMoreComments;

    @Bind(R.id.border)
    View mBorder;

    @BindDimen(R.dimen.article_image_height)
    int articleImageHeight;

    //endregion

    public Article mArticle;
    private long mScrollPercentage = 0;
    boolean isScrollingDown = false;
    private boolean mIsTransition = false;

    //region Activity methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_article);
        ActivityCompat.postponeEnterTransition(ArticleActivity.this);
        ButterKnife.bind(this);
        mHandler = new Handler();
        setupWebView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Parcelable parcelable = getIntent().getParcelableExtra(Article.ARTICLE_OBJ);
        mIsTransition = getIntent().getBooleanExtra(TRANSITION, false);
      //  webViewText.loadDataWithBaseURL(RELATIVE_PATH_ASSETS, "", "text/html", "UTF-8", null);
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
                mArticleId = getIntent().getExtras().getInt(AppConstants.ARTICLE_ID, -1);
                if (!TextUtils.isEmpty(notificationId)) {
                    setSource(NOTIFICATION_SCREEN);
                }
                mImageWidth = getIntent().getExtras().getInt(IMAGE_WIDTH);
                mImageHeight = getIntent().getExtras().getInt(IMAGE_HEIGHT);
            } else {
                finish();
            }
        }
        if(mImageWidth!=0 && mImageHeight!=0){
            int imageHeight = (int)(((float)mImageHeight/(float)mImageWidth) * CommonUtil.getWindowWidth(this));
            image.getLayoutParams().height = imageHeight;
        }
        mArticlePresenter.attachView(this);
        mScrimView.setBackground(ScrimUtil.makeCubicGradientScrimDrawable(
                0xaa000000, 8, Gravity.TOP));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        defaultUi = getWindow().getDecorView().getSystemUiVisibility();
        mArticleLayout.setOnScrollChangeListener(this);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        mCommentList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) mCommentList.getItemAnimator()).setSupportsChangeAnimations(false);
        mCommentList.setLayoutManager(mLinearLayoutManager);
        mCommentList.setNestedScrollingEnabled(false);
        mCommentList.setFocusable(false);
        initializeCommentsAdapter();
        if (mArticle != null) {
            loadArticleImage(mArticle);
        }
        fetchArticle(mArticle == null ? mArticleId : (int)mArticle.id);
        mCommentBody.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSubmitButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.VISIBLE);
                   // mArticleLayout.smoothScrollTo(0, mTitleComment.getBottom());

                } else {
                    mSubmitButton.setVisibility(View.GONE);
                    mCancelButton.setVisibility(View.GONE);
                }
            }
        });
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
        if (webChromeClient != null && !webChromeClient.onBackPressed()) {
            if (webViewText.canGoBack()) {
                webViewText.goBack();
            } else {
                destroyWebView();
                if(mIsTransition){
                    setResult(RESULT_OK, null);
                    ActivityCompat.finishAfterTransition(ArticleActivity.this);
                }else {
                    finish();
                }
            }
        }
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
                  //  .topics(mArticle.topics)
                   // .readingTime(mArticle.readingTime)
                   // .readPercentage(mScrollPercentage);

        }

        HashMap<String, Object> properties = builder.build();
        return properties;
    }
    //endregion

    //region Private Helper methods
    private void initializeCommentsAdapter() {
        mCommentsAdapter = new CommentListAdapter(this, mArticlePresenter,new View.OnClickListener() {
            @Override
            public void onClick(View deleteItem) {
                View recyclerViewItem = (View) deleteItem.getParent();
                final int position = mCommentList.getChildAdapterPosition(recyclerViewItem);
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                PopupMenu popup = new PopupMenu(ArticleActivity.this, deleteItem);
                popup.getMenuInflater().inflate(R.menu.menu_delete, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Comment comment = mCommentsAdapter.getComment(position);
                        if(comment == null){
                            return true;
                        }
                        mArticlePresenter.onDeleteCommentClicked(position, mAppUtils.editCommentRequestBuilder(comment.getEntityId(), comment.getComment(), false, false, comment.getId()));
                        return true;
                    }
                });
                popup.show();
            }
        });
        mCommentList.setAdapter(mCommentsAdapter);
    }

    private void fetchArticle(int articleId) {
        mArticlePresenter.fetchArticle(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_ARTICLE, AppConstants.ONE_CONSTANT, articleId));
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

    private void applyPalette(Palette palette) {
        mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(mPrimaryColor));
        mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(mPrimaryDarkColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CommonUtil.colorBurn(palette.getMutedColor(mPrimaryDarkColor)));
        }
    }
    //endregion

    //region ButterKnife Bindings
    @OnClick(R.id.fab)
    void onFabClick() {
       ShareBottomSheetFragment.showDialog(this, mArticle.deepLink, null, mArticle.deepLink, SCREEN_LABEL, false);
    }

    @OnClick(R.id.submit)
    public void onSubmitClicked() {
        String commentBody = mCommentBody.getText().toString();
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
        webViewText.setVisibility(View.INVISIBLE);
        mComments.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mArticleLayout.setVisibility(View.GONE);
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mArticleLayout.setVisibility(View.VISIBLE);
        mAuthorDesView.setVisibility(View.VISIBLE);
        webViewText.setVisibility(View.VISIBLE);
        mComments.setVisibility(View.VISIBLE);
    }

    public void setupWebView(){
        webChromeClient = new VideoEnabledWebChromeClient(rootLayout, videoLayout, null, webViewText){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           // stopProgressBar();
                        }
                    });
               //     webViewText.loadUrl("javascript:initials()");
                 //   view.setVisibility(View.VISIBLE);
                }
            }
        };
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

        webViewText.getSettings().setJavaScriptEnabled(true);
        webViewText.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webViewText.setWebChromeClient(webChromeClient);
        webViewText.setVerticalScrollBarEnabled(false);
        webViewText.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                stopProgressBar();
                view.setVisibility(View.VISIBLE);
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

        if(!imageLoaded){
            loadArticleImage(article);
        }

        if (article.author != null) {
            author.setText(article.author.name);
            String pluralComments = getResources().getQuantityString(R.plurals.numberOfComments, article.commentsCount);
            String pluralLikes = getResources().getQuantityString(R.plurals.numberOfLikes, article.likesCount);
            String pluralViews = getResources().getQuantityString(R.plurals.numberOfViews, article.totalViews);
            mLikesViewsComments.setText(article.createdAt + " " + "\u2022" + " " + article.getReadingTime() + " " + "\u2022" + " " + CommonUtil.getRoundedMetricFormat(article.totalViews) + " " + pluralViews);
           // mLikesViewsComments.setText(Integer.toString(article.commentsCount) + " " + pluralComments + " " + "\u2022" + " " + Integer.toString(article.likesCount) + " " + pluralLikes + " " + "\u2022" + " " + CommonUtil.getRoundedMetricFormat(article.totalViews) + " " + pluralViews);
            if (article.author.thumbUrl != null && CommonUtil.isNotEmpty(article.author.thumbUrl)) {
                String authorImage = CommonUtil.getThumborUri(article.author.thumbUrl, authorPicSize, authorPicSize);
                Glide.with(this)
                        .load(authorImage)
                        .bitmapTransform(new CommunityOpenAboutFragment.CircleTransform(this))
                        .into(authorPic);

            } else {
              /*  Glide.with(this)
                        .load(R.drawable.vector_user_female)
                        .override(authorPicSize, authorPicSize)
                        .into(authorPic);*/
            }

            if (article.author.thumbUrl != null && CommonUtil.isNotEmpty(article.author.thumbUrl)) {
                String authorImage = CommonUtil.getThumborUri(article.author.thumbUrl, authorPicSize, authorPicSize);
                Glide.with(this)
                        .load(authorImage)
                        .bitmapTransform(new CommunityOpenAboutFragment.CircleTransform(this))
                        .into(authorDesPic);

            }
            authorDesName.setText(article.author.name);
            authorDescription.setText(article.author.shortDescription);

        }
        title.setText(article.title);
        if (!CommonUtil.isNotEmpty(article.body)) {
            return;
        }
        final String webViewStyle = getStyleFromConfig();
        String htmlData = article.body == null ? "" : article.body;
        htmlData = "<style>" + webViewStyle + " </style> <body> " + htmlData + " </body>";
        webViewText.loadDataWithBaseURL(RELATIVE_PATH_ASSETS, htmlData, "text/html", "UTF-8", null);
        invalidateOptionsMenu();
    }

    private void loadArticleImage(Article article) {
        String imageUri = article.featureImage;
        if (article.featureImage != null) {
            try {
                Uri image = Uri.parse(imageUri);
                int size = image.getPathSegments().size();
                image.getPathSegments().add(size - 2, "tr:h-400,w-400,fo-auto");
                imageUri = SheroesThumbor.getInstance()
                        .buildImage(URLEncoder.encode(article.featureImage, "UTF-8"))
                        .resize(CommonUtil.getWindowWidth(this), 400)
                        .filter(ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                        .toUrl();
            } catch (UnsupportedEncodingException e) {
                Crashlytics.getInstance().core.logException(e);
            }
            final String finalImageUri = imageUri;
            final int imageHeight = (int)(((float)mImageHeight/(float)mImageWidth) * CommonUtil.getWindowWidth(this));
           // image.getLayoutParams().height = imageHeight;
            image.getLayoutParams().height = 400;
            Glide.with(ArticleActivity.this)
                    .load(finalImageUri)
                    .asBitmap()
                    .into(new BitmapImageViewTarget(image) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            super.onResourceReady(bitmap, anim);
                            Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    // Here's your generated palette
                                    applyPalette(palette);
                                    mAppBarLayout.setExpanded(true);
                                    ActivityCompat.startPostponedEnterTransition(ArticleActivity.this);
                                }
                            });
                        }
                    });
        }
    }

    @Override
    public void invalidateBookmark(Article article) {
        mArticle = article;
        invalidateOptionsMenu();
    }

    @Override
    public void invalidateLike(Article article) {
        mArticle = article;
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
    public void showWebViewProgressBar() {
 /*       mAuthorDesView.setVisibility(View.GONE);
        mWebViewProgressBar.setVisibility(View.VISIBLE);
        webViewText.setVisibility(View.GONE);
        mComments.setVisibility(View.GONE);*/
    }

    @Override
    public void hideWebViewProgressBar() {
  /*      mAuthorDesView.setVisibility(View.VISIBLE);
        mWebViewProgressBar.setVisibility(View.GONE);
        webViewText.setVisibility(View.VISIBLE);
        mComments.setVisibility(View.VISIBLE);*/
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
    //endregio

    //region OnScroll methods
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int totalHeight = mArticleLayout.getChildAt(0).getHeight();
        int articleHeight = totalHeight;

        //If scrolled below article content we should ignore it.As he has already read the full article.
        if ((((double) scrollY + mArticleLayout.getHeight()) > (double) articleHeight)) {
            mScrollPercentage = 100;
        } else {
            mScrollPercentage = (long) ((((double) scrollY + mArticleLayout.getHeight()) / (double) articleHeight) * 100);
        }

        // If scroll is less than 95% only then go to lights off mode
        if (scrollY > oldScrollY && mScrollPercentage < 95) {
            isScrollingDown = true;
        } else {
            isScrollingDown = false;
        }

        if (isScrollingDown) {
            fab.hide();
            int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
            getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        } else {
            fab.show();
            getSupportActionBar().show();
            getWindow().getDecorView().setSystemUiVisibility(defaultUi);
        }
    }
    //endregion

    // region Static methods

    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, String sourceScreen, HashMap<String, Object> properties) {
       // article.comments = null;
        Intent intent = new Intent(fromActivity, ArticleActivity.class);
        Article article = new Article();
        article.title = feedDetail.getNameOrTitle();
        article.id = feedDetail.getIdOfEntityOrParticipant();
        article.featureImage = feedDetail.getImageUrl();
        article.commentsCount = feedDetail.getNoOfComments();
        article.likesCount = feedDetail.getNoOfLikes();
        article.author = new UserProfile();
        article.author.name = feedDetail.getAuthorName();
        article.author.shortDescription = feedDetail.getAuthorShortDescription();
        article.author.thumbUrl = feedDetail.getAuthorImageUrl();
        article.isBookmarked = feedDetail.isBookmarked();
        article.isLiked = feedDetail.getLastReactionValue() > 0;
        Parcelable parcelable = Parcels.wrap(article);
        intent.putExtra(Article.ARTICLE_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivity(fromActivity, intent, null);
    }

    // endregion

    //region private methods
    private String getStyleFromConfig() {
        String styleFromConfig;
       /* Config config = Config.getConfig();
        if (config != null && config.webViewStyle != null) {
            styleFromConfig = config.webViewStyle;
        } else {*/
            styleFromConfig = AppConstants.webstyle;
      //  }
        return styleFromConfig;
    }

    private void destroyWebView() {
        if (webViewText != null) {
            webViewText.destroy();
        }
    }

    private String getJavaScriptFromConfig() {
        String javaScriptFromConfig;
       /* Config config = Config.getConfig();
        if (config != null && config.webJavaScript != null) {
            javaScriptFromConfig = config.webJavaScript;
        } else {*/
            javaScriptFromConfig = AppConstants.javascriptcode;
     //   }
        return javaScriptFromConfig;
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
    //endregion
}
