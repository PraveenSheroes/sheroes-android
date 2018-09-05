package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.SpamContentType;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.Spam;
import appliedlife.pvtltd.SHEROES.models.SpamReasons;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.ArticlePresenterImpl;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.ScrimUtil;
import appliedlife.pvtltd.SHEROES.utils.SpamUtil;
import appliedlife.pvtltd.SHEROES.utils.VideoEnabledWebChromeClient;
import appliedlife.pvtltd.SHEROES.utils.WebViewClickListener;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.postCommentRequestBuilder;

/**
 * Created by ujjwal on 28/10/17.
 */
public class ArticleActivity extends BaseActivity implements IArticleView, NestedScrollView.OnScrollChangeListener, AppBarLayout.OnOffsetChangedListener {

    public static final String SCREEN_LABEL = "Article Activity";
    public static final String SCREEN_LABEL_STORY = "Story Details Screen";
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
    private long adminId = 0;
    private long currentUserId = -1;

    //region member variabe
    private VideoEnabledWebChromeClient webChromeClient;
    private CommentListAdapter mCommentsAdapter;
    private int mImageWidth;
    private int mImageHeight;
    private int mArticleId;
    private int mFeedPosition;
    public ArticleSolrObj mArticleSolrObj;
    private long mScrollPercentage = 0;
    private boolean isScrollingDown = false;

    public enum State {EXPANDED, COLLAPSED}

    private State mCurrentState = State.EXPANDED;
    private boolean mIsTransition = false;
    private FeedDetail mFeedDetail;

    @Inject
    ArticlePresenterImpl mArticlePresenter;

    @Inject
    Preference<Configuration> mConfiguration;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @Inject
    DateUtil mDateUtil;
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

    @BindColor(R.color.white)
    int mPrimaryColor;

    @BindColor(R.color.status_bar)
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

    private View articleToolTip;
    private PopupWindow popupWindowArticleTooTip;
    private String streamType;
    private List<MentionSpan> mentionSpanList;
    private boolean hasMentions = false;
    private String mSourceScreen;
    private HashMap<String, Object> mProperties;
    private boolean isUserStory;
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
        if (getIntent() != null && getIntent().getExtras() != null) {
            mSourceScreen = getIntent().getExtras().getString(BaseActivity.SOURCE_SCREEN);
            mProperties = (HashMap<String, Object>) getIntent().getExtras().getSerializable(BaseActivity.SOURCE_PROPERTIES);
            isUserStory = getIntent().getBooleanExtra(USER_STORY, false);
            Parcelable parcelable = getIntent().getParcelableExtra(ArticleSolrObj.ARTICLE_OBJ);
            mIsTransition = getIntent().getBooleanExtra(TRANSITION, false);
            mFeedPosition = getIntent().getIntExtra(FEED_POSITION, -1);
            if (parcelable != null) {
                mArticleSolrObj = Parcels.unwrap(parcelable);
                if (mArticleSolrObj != null) {
                    mCommentCount = mArticleSolrObj.getNoOfComments();
                    updateTitleCommentCountView();
                    mImageWidth = mArticleSolrObj.getHighresImageWidth();
                    mImageHeight = mArticleSolrObj.getHighresImageHeight();
                    streamType = mArticleSolrObj.getStreamType();
                }
            } else {
                String notificationId = getIntent().getExtras().getString("notificationId");
                Long i = getIntent().getExtras().getLong(AppConstants.ARTICLE_ID, -1);
                mArticleId = i.intValue();
                if (!TextUtils.isEmpty(notificationId)) {
                    setSource(NOTIFICATION_SCREEN);
                }
                mImageWidth = getIntent().getExtras().getInt(IMAGE_WIDTH);
                mImageHeight = getIntent().getExtras().getInt(IMAGE_HEIGHT);

            }
        }
        mScrimView.setBackground(ScrimUtil.makeCubicGradientScrimDrawable(
                0xaa000000, 8, Gravity.TOP));

        setupToolbarItemsColor();

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
        }

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            currentUserId = mUserPreference.get().getUserSummary().getUserId();
        }


        defaultUi = getWindow().getDecorView().getSystemUiVisibility();
        mArticleLayout.setOnScrollChangeListener(this);

        initializeCommentsAdapter();
        mAppBarLayout.addOnOffsetChangedListener(this);
        if (mArticleSolrObj != null) {
            loadArticleImage(mArticleSolrObj);
        }
        fetchArticle(mArticleSolrObj == null ? mArticleId : (int) mArticleSolrObj.getIdOfEntityOrParticipant(), mArticleSolrObj != null, isUserStory);

        mCommentBody.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mHasFocus = true;
                    fab.hide();
                    mSubmitButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.VISIBLE);
                    //mArticleLayout.smoothScrollTo(0, mAuthorDesView.getBottom());

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

    private void setupToolbarItemsColor() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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
            menu.findItem(R.id.like).setIcon(mArticlePresenter.getLikeDrawable(mArticleSolrObj));
            menu.findItem(R.id.bookmark).setIcon(mArticlePresenter.getBookmarkDrawable(mArticleSolrObj));

            if (mCurrentState == State.COLLAPSED) {
                menu.findItem(R.id.like).getIcon().setColorFilter(getResources().getColor(R.color.menu_icon), PorterDuff.Mode.SRC_IN);
                menu.findItem(R.id.bookmark).getIcon().setColorFilter(getResources().getColor(R.color.menu_icon), PorterDuff.Mode.SRC_IN);

                final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);

            } else if (mCurrentState == State.EXPANDED) {
                menu.findItem(R.id.like).getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                menu.findItem(R.id.bookmark).getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_white);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }

            itemLike.setVisible(mArticlePresenter.getMenuItemsVisibility(mArticleSolrObj));
            itemBookmark.setVisible(mArticlePresenter.getMenuItemsVisibility(mArticleSolrObj));
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
                mArticlePresenter.prepareLike(mArticleSolrObj);
                if (mArticleSolrObj.isUserStory()) {
                    HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(mArticleSolrObj, getScreenName());
                    if (mArticleSolrObj.isLiked) {
                        AnalyticsManager.trackEvent(Event.STORY_LIKED, getScreenName(), properties);
                    } else {
                        AnalyticsManager.trackEvent(Event.STORY_UN_LIKED, getScreenName(), properties);
                    }
                }
                break;
            case R.id.bookmark:
                mArticlePresenter.prepareBookmark(mArticleSolrObj);
                if (mArticleSolrObj.isUserStory()) {
                    HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(mArticleSolrObj, getScreenName());
                    if (mArticleSolrObj.isBookmarked()) {
                        AnalyticsManager.trackEvent(Event.STORY_BOOKMARKED, getScreenName(), properties);
                    } else {
                        AnalyticsManager.trackEvent(Event.STORY_UN_BOOKMARKED, getScreenName(), properties);
                    }
                }
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
        if (mArticleSolrObj != null) {
            builder.title(mArticleSolrObj.getNameOrTitle())
                    .id(Long.toString(mArticleSolrObj.getEntityOrParticipantId()))
                    .streamType(mArticleSolrObj.getStreamType());

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
            public void onClick(final View view) {
                View recyclerViewItem = (View) view.getParent();
                final int position = mCommentList.getChildAdapterPosition(recyclerViewItem);
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.author_pic_container:
                    case R.id.author:
                        if(mArticleSolrObj.isUserStory()) {
                            Comment comment = mCommentsAdapter.getComment(position);
                            if (!comment.isAnonymous() && !comment.isSpamComment()) {
                                openProfile(comment.getParticipantUserId(), comment.isVerifiedMentor(), SCREEN_LABEL);
                            }
                        }
                        break;

                    case R.id.delete:
                        final PopupMenu popup = new PopupMenu(ArticleActivity.this, view);

                        popup.getMenu().add(0, R.id.delete, 1, menuIconWithText(getResources().getDrawable(R.drawable.vector_delete), getResources().getString(R.string.ID_DELETE)));
                        popup.getMenu().add(0, R.id.report_spam, 2, menuIconWithText(getResources().getDrawable(R.drawable.vector_report_spam), getResources().getString(R.string.REPORT_SPAM)));

                        final Comment selectedComment = mCommentsAdapter.getComment(position);
                        if (selectedComment == null) return;

                        if (selectedComment.isMyOwnParticipation() || adminId == AppConstants.TWO_CONSTANT) {
                            popup.getMenu().findItem(R.id.delete).setVisible(true);
                            popup.getMenu().findItem(R.id.report_spam).setVisible(false);
                        } else {
                            popup.getMenu().findItem(R.id.delete).setVisible(false);
                            if (selectedComment.isSpamComment()) {
                                popup.getMenu().findItem(R.id.report_spam).setVisible(false);
                            } else {
                                popup.getMenu().findItem(R.id.report_spam).setVisible(true);
                            }
                        }

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.report_spam) {
                                    reportSpamDialog(SpamContentType.ARTICLE_COMMENT, selectedComment, position);

                                    return true;
                                } else {
                                    final long adminID = adminId;
                                    if (!selectedComment.isMyOwnParticipation() && adminID == AppConstants.TWO_CONSTANT) {
                                        reportSpamDialog(SpamContentType.ARTICLE_COMMENT, selectedComment, position);
                                    } else {
                                        mArticlePresenter.onDeleteCommentClicked(position, AppUtils.editCommentRequestBuilder(selectedComment.getEntityId(), selectedComment.getComment(), false, false, selectedComment.getId(), hasMentions, mentionSpanList));
                                    }
                                    return true;
                                }
                            }
                        });
                        popup.show();
                        break;
                }
            }
        });
        mCommentList.setAdapter(mCommentsAdapter);
    }

    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    private void onCommentReported(Comment comment) {
        HashMap<String, Object> propertiesDelete =
                new EventProperty.Builder()
                        .id(Long.toString(comment.getId()))
                        .postType(getStreamType())
                        .postId(Long.toString(comment.getEntityId()))
                        .communityId(comment.getCommunityId())
                        .body(comment.getComment())
                        .build();
        trackEvent(Event.REPLY_REPORTED, propertiesDelete);
    }


    private void reportSpamDialog(final SpamContentType spamContentType, final Comment comment, final int commentPos) {

        if (ArticleActivity.this == null || ArticleActivity.this.isFinishing()) return;

        SpamReasons spamReasons;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.reasonOfSpamCategory != null) {
            spamReasons = mConfiguration.get().configData.reasonOfSpamCategory;
        } else {
            String spamReasonsContent = AppUtils.getStringContent(AppConstants.SPAM_REASONS_FILE); //read spam reasons from local file
            spamReasons = AppUtils.parseUsingGSONFromJSON(spamReasonsContent, SpamReasons.class.getName());
        }

        if (spamReasons == null) return;

        final Dialog spamReasonsDialog = new Dialog(ArticleActivity.this);
        spamReasonsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        spamReasonsDialog.setCancelable(true);
        spamReasonsDialog.setContentView(R.layout.dialog_spam_options);

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.convertDpToPixel(16, ArticleActivity.this), CommonUtil.convertDpToPixel(10, ArticleActivity.this), 0, 0);

        TextView reasonTitle = spamReasonsDialog.findViewById(R.id.reason_title);
        TextView reasonSubTitle = spamReasonsDialog.findViewById(R.id.reason_sub_title);
        reasonTitle.setLayoutParams(layoutParams);
        reasonSubTitle.setLayoutParams(layoutParams);

        final RadioGroup spamOptions = spamReasonsDialog.findViewById(R.id.options_container);

        List<Spam> spamList = null;
        SpamPostRequest spamRequest = null;
        if (spamContentType == SpamContentType.ARTICLE_COMMENT) {
            spamList = spamReasons.getCommentTypeSpams();
            spamRequest = SpamUtil.spamArticleCommentRequestBuilder(comment, currentUserId);
        }

        if (spamRequest == null || spamList == null) return;

        SpamUtil.addRadioToView(ArticleActivity.this, spamList, spamOptions);

        Button submit = spamReasonsDialog.findViewById(R.id.submit);
        final EditText reason = spamReasonsDialog.findViewById(R.id.edit_text_reason);

        final SpamPostRequest finalSpamRequest = spamRequest;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spamOptions.getCheckedRadioButtonId() != -1) {

                    RadioButton radioButton = spamOptions.findViewById(spamOptions.getCheckedRadioButtonId());
                    Spam spam = (Spam) radioButton.getTag();
                    if (spam != null) {
                        finalSpamRequest.setSpamReason(spam.getReason());
                        finalSpamRequest.setScore(spam.getScore());

                        if (spam.getLabel().equalsIgnoreCase("Others")) {
                            if (reason.getVisibility() == View.VISIBLE) {

                                if (reason.getText().length() > 0 && reason.getText().toString().trim().length() > 0) {
                                    finalSpamRequest.setSpamReason(spam.getReason().concat(":" + reason.getText().toString()));
                                    mArticlePresenter.reportSpamPostOrComment(finalSpamRequest, comment, commentPos); //submit
                                    spamReasonsDialog.dismiss();

                                    if (spamContentType == SpamContentType.ARTICLE_COMMENT) {
                                        onCommentReported(comment);   //report the article comment
                                    }

                                } else {
                                    reason.setError("Add the reason");
                                }

                            } else {
                                reason.setVisibility(View.VISIBLE);
                                SpamUtil.hideSpamReason(spamOptions, spamOptions.getCheckedRadioButtonId());
                            }
                        } else {
                            mArticlePresenter.reportSpamPostOrComment(finalSpamRequest, comment, commentPos);  //submit request
                            spamReasonsDialog.dismiss();

                            if (spamContentType == SpamContentType.ARTICLE_COMMENT) {
                                onCommentReported(comment);   //report the article comment
                            }
                        }
                    }
                }
            }
        });

        spamReasonsDialog.show();
    }

    private void fetchArticle(int articleId, boolean isImageLoaded, boolean isUserStory) {
        mArticlePresenter.fetchArticle(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_ARTICLE, AppConstants.ONE_CONSTANT, articleId), isImageLoaded, isUserStory);
    }

    private void openProfile(Long userId, boolean isMentor, String source) {
        ProfileActivity.navigateTo(this, userId, isMentor, PROFILE_NOTIFICATION_ID, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void updateTitleCommentCountView() {
        String pluralComment = this.getResources().getQuantityString(R.plurals.numberOfComments, mCommentCount);
        mTitleComment.setText(mCommentCount + " " + pluralComment);
    }

    private boolean validateData() {
        if (mArticleSolrObj == null) {
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
        if (mFeedDetail != null) {
            AnalyticsManager.trackPostAction(Event.POST_SHARED_CLICKED, mFeedDetail, getScreenName());
            ShareBottomSheetFragment.showDialog(this, mArticleSolrObj.getDeepLinkUrl(), null, mArticleSolrObj.getDeepLinkUrl(), SCREEN_LABEL, false, mArticleSolrObj.getDeepLinkUrl(), false, Event.POST_SHARED, MixpanelHelper.getPostProperties(mFeedDetail, getScreenName()));
        }
    }

    @OnClick({R.id.author_pic, R.id.author, R.id.author_description__pic, R.id.author_description_name})
    public void onUserDetailClick() {
        if(mArticleSolrObj.isUserStory()) {
            boolean isMentor = false;
            if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.MENTOR_TYPE_ID) {
                isMentor = true;
            }
            CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
            communityFeedSolrObj.setIdOfEntityOrParticipant(mArticleSolrObj.getCreatedBy());
            communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
            mFeedDetail = communityFeedSolrObj;
            ProfileActivity.navigateTo(this, communityFeedSolrObj, mArticleSolrObj.getCreatedBy(), isMentor, mArticleSolrObj.getItemPosition(), getScreenName(), null, REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    @OnClick(R.id.submit)
    public void onSubmitClicked() {
        String commentBody = mCommentBody.getText().toString().trim();
        if (CommonUtil.isNotEmpty(commentBody)) {
            mArticlePresenter.postComment(postCommentRequestBuilder(mArticleSolrObj.getEntityOrParticipantId(), commentBody, false, hasMentions, mentionSpanList), mArticleSolrObj);
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
    public void showArticle(final ArticleSolrObj articleSolrObj, boolean imageLoaded) {
        streamType = articleSolrObj.getStreamType();
        mImageHeight = articleSolrObj.getHighresImageHeight();
        mImageWidth = articleSolrObj.getHighresImageWidth();
        mArticleSolrObj = articleSolrObj;
        invalidateOptionsMenu();
        mCommentCount = articleSolrObj.getNoOfComments();
        if (!validateData()) {
            onBackPressed();
        }
        if (mArticleSolrObj.isThreadClosed) {
            mCommentBody.setVisibility(View.GONE);
        } else {
            mCommentBody.setVisibility(View.VISIBLE);
        }
        if (mArticleSolrObj.showComments) {
            mComments.setVisibility(View.VISIBLE);
            mCommentList.setVisibility(View.VISIBLE);
        } else {
            mComments.setVisibility(View.GONE);
            mCommentList.setVisibility(View.GONE);
        }

        if (!imageLoaded) {
            loadArticleImage(articleSolrObj);
        }

        loadUserViews(articleSolrObj);
        if (articleSolrObj.isUserStory()) {
            if (!CommonUtil.isNotEmpty(articleSolrObj.getDescription())) {
                return;
            }
        } else {
            if (!CommonUtil.isNotEmpty(articleSolrObj.getListDescription())) {
                return;
            }
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
        String htmlData;
        if (articleSolrObj.isUserStory()) {
            if (!CommonUtil.isNotEmpty(articleSolrObj.getDescription())) {
                return;
            }
            final String webViewStyle = getStyleFromConfig();
            htmlData = articleSolrObj.getDescription() == null ? "" : articleSolrObj.getDescription();
            htmlData = "<style>" + webViewStyle + " </style> <body> " + getJavaScriptFromConfig() + htmlData + " </body>";
        } else {
            if (!CommonUtil.isNotEmpty(articleSolrObj.getListDescription())) {
                return;
            }
            final String webViewStyle = getStyleFromConfig();
            htmlData = articleSolrObj.getListDescription() == null ? "" : articleSolrObj.getListDescription();
            htmlData = "<style>" + webViewStyle + " </style> <body> " + getJavaScriptFromConfig() + htmlData + " </body>";
        }

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

        if (null != mArticleSolrObj && mArticleSolrObj.isUserStory()) {
            mProperties = MixpanelHelper.getArticleOrStoryProperties(mArticleSolrObj, mSourceScreen);
            AnalyticsManager.trackScreenView(SCREEN_LABEL_STORY, mSourceScreen, mProperties);
        } else {
            AnalyticsManager.trackScreenView(SCREEN_LABEL, mSourceScreen, mProperties);
        }
    }


    private void loadUserViews(ArticleSolrObj articleSolrObj) {
        author.setText(articleSolrObj.getAuthorName());
        String pluralLikes = getResources().getQuantityString(R.plurals.numberOfLikes, articleSolrObj.likesCount);
        mLikeCount.setText(CommonUtil.getRoundedMetricFormat(articleSolrObj.likesCount) + " " + pluralLikes);
        String pluralViews = getResources().getQuantityString(R.plurals.numberOfViews, articleSolrObj.getNoOfViews());
        long createdDate = mDateUtil.getTimeInMillis(articleSolrObj.getPostedDate(), AppConstants.DATE_FORMAT);
        String dateInWord = mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate);
        if (!dateInWord.equalsIgnoreCase(getString(R.string.ID_JUST_NOW))) {
            dateInWord = dateInWord + " ago ";
        }
        String minRead = "";
        if (articleSolrObj.getCharCount() > 0) {
            minRead = articleSolrObj.getCharCount() + " " + getString(R.string.ID_MIN_READ);
        }
        String likesViews = "";
        if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null) {
            if (mConfiguration.get().configData.showArticleViews) {
                likesViews = dateInWord + "\u2022" + " " + minRead + " " + "\u2022" + " " + CommonUtil.getRoundedMetricFormat(articleSolrObj.getNoOfViews()) + " " + pluralViews;
            } else {
                likesViews = dateInWord + "\u2022" + " " + minRead;
            }
        } else {
            likesViews = dateInWord + "\u2022" + " " + minRead;
        }
        mLikesViewsComments.setText(likesViews);
        if (CommonUtil.isNotEmpty(articleSolrObj.getAuthorImageUrl())) {
            String authorImage = CommonUtil.getThumborUri(articleSolrObj.getAuthorImageUrl(), authorPicSize, authorPicSize);
            Glide.with(this)
                    .load(authorImage)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(this)))
                    .into(authorPic);
            Glide.with(this)
                    .load(authorImage)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(this)))
                    .into(authorDesPic);

        }

        authorDesName.setText(articleSolrObj.getAuthorName());
        if (StringUtil.isNotNullOrEmptyString(articleSolrObj.getAuthorShortDescription())) {
            authorDescription.setText(Html.fromHtml(articleSolrObj.getAuthorShortDescription()));
        }

        title.setText(articleSolrObj.getNameOrTitle());
    }

    private void loadArticleImage(ArticleSolrObj articleSolrObj) {
        String imageUri = articleSolrObj.getImageUrl();
        int imageNewHeight;
        if (mImageWidth != 0 && mImageHeight != 0) {
            imageNewHeight = (int) (((float) mImageHeight / (float) mImageWidth) * CommonUtil.getWindowWidth(this));
        } else {
            imageNewHeight = articleImageHeight;
        }
        image.getLayoutParams().height = imageNewHeight;
        if (CommonUtil.isNotEmpty(articleSolrObj.getImageUrl())) {
            String finalImageUri = CommonUtil.getThumborUri(imageUri, CommonUtil.getWindowWidth(this), imageNewHeight);
            Glide.with(ArticleActivity.this)
                    .asBitmap()
                    .load(finalImageUri)
                    .into(image);
        }
        loadUserViews(articleSolrObj);
    }

    @Override
    public void invalidateBookmark(ArticleSolrObj articleSolrObj) {
        if (mFeedDetail != null) {
            mFeedDetail.setBookmarked(articleSolrObj.isBookmarked());
        }
        mArticleSolrObj = articleSolrObj;
        invalidateOptionsMenu();
    }

    @Override
    public void invalidateLike(ArticleSolrObj articleSolrObj) {
        if (mFeedDetail != null) {
            mFeedDetail.setReactionValue(articleSolrObj.isLiked ? AppConstants.HEART_REACTION_CONSTANT : AppConstants.NO_REACTION_CONSTANT);
        }
        mArticleSolrObj = articleSolrObj;
        String pluralLikes = getResources().getQuantityString(R.plurals.numberOfLikes, articleSolrObj.likesCount);
        mLikeCount.setText(CommonUtil.getRoundedMetricFormat(articleSolrObj.likesCount) + " " + pluralLikes);
        invalidateOptionsMenu();
    }

    @Override
    public void showComments(List<Comment> comments, int commentsCount) {
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
                mArticlePresenter.fetchAllComments(getCommentRequestBuilder(mArticleSolrObj.getEntityOrParticipantId(), 1, 100));
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
        if (null != mFeedDetail) {
            if (mFeedDetail instanceof ArticleSolrObj) {
                if (CommonUtil.isEmpty(mFeedDetail.getLastComments())) {
                    List<Comment> comments = new ArrayList<>();
                    comments.add(comment);
                    mFeedDetail.setLastComments(comments);
                } else {
                    mFeedDetail.getLastComments().add(comment);
                }
            }
        }
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
    public String getStreamType() {
        return streamType;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
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

    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, String sourceScreen, HashMap<String, Object> properties, int requestCode, boolean isUserStory) {
        Intent intent = new Intent(fromActivity, ArticleActivity.class);
        ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
        Parcelable parcelable = Parcels.wrap(articleSolrObj);
        intent.putExtra(ArticleSolrObj.ARTICLE_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(BaseActivity.USER_STORY, isUserStory);
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
    public void onSpamPostOrCommentReported(SpamResponse spamResponse, Comment comment, int position) {
        if (spamResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {

            if (comment != null && !comment.isMyOwnParticipation() && adminId == AppConstants.TWO_CONSTANT) {
                mArticlePresenter.getSpamCommentApproveOrDeleteByAdmin(mAppUtils.spamCommentApprovedRequestBuilder(comment, true, true, false), position, comment);
            }

            if (ArticleActivity.this.isFinishing()) return;

            if(spamResponse.isSpammed()) {
                CommonUtil.createDialog(ArticleActivity.this, getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_marked_dialog_message, SpamContentType.COMMENT.name()));
            } else if (!spamResponse.isSpamAlreadyReported()) {
                CommonUtil.createDialog(ArticleActivity.this, getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.spam_confirmation_dialog_message));
            } else {
                CommonUtil.createDialog(ArticleActivity.this, getResources().getString(R.string.reported_spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_confirmation_dialog_message, SpamContentType.COMMENT.name()));
            }
        }
    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @OnClick(R.id.like_count)
    public void onLikeCountClicked() {
        LikeListBottomSheetFragment.showDialog(this, "", mArticleSolrObj.getEntityOrParticipantId());
    }

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (offset == 0) {
            if (mCurrentState != State.EXPANDED) {
                invalidateOptionsMenu();
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(offset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                invalidateOptionsMenu();
            }
            mCurrentState = State.COLLAPSED;
        }
    }
    //endregion
}