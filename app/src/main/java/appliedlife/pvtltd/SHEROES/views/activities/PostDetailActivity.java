package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.CommentCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.SpamContentType;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.Spam;
import appliedlife.pvtltd.SHEROES.models.SpamReasons;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.TaggedUserPojo;
import appliedlife.pvtltd.SHEROES.presenters.FeedPresenter;
import appliedlife.pvtltd.SHEROES.presenters.PostDetailViewImpl;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.UserTagSuggestionsAdapter;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.UserTagSuggestionsResult;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.interfaces.QueryTokenReceiver;
import appliedlife.pvtltd.SHEROES.usertagging.ui.RichEditorView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.SpamUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.PostDetailAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by ujjwal on 07/12/17.
 */

public class PostDetailActivity extends BaseActivity implements IPostDetailView, PostDetailCallBack, CommentCallBack, QueryTokenReceiver {
    public static final String SCREEN_LABEL = "Post Detail Screen";
    public static final String IS_POST_DELETED = "Is Post Deleted";
    public static final String SHOW_KEYBOARD = "Show Keyboard";
    private boolean mStatusBarColorEmpty = true;
    public static final int SINGLE_LINE = 1;
    public static final int MAX_LINE = 5;
    public int mPositionInFeed = -1;
    private long mLoggedInUser = -1;
    private String streamType;
    private boolean isDirty = false;
    private Comment editedComment = null;
    private Map<Integer, Comment> lastEditedComment = new HashMap<>();

    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<Configuration> mConfiguration;
    @Inject
    AppUtils mAppUtils;

    @Inject
    PostDetailViewImpl mPostDetailPresenter;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    //region binding view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.user_comment_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.title_toolbar)
    TextView mTitleToolbar;

    @Bind(R.id.input_edit_text)
    RichEditorView etView;

    @Bind(R.id.user_pic)
    CircleImageView mUserPic;

    @Bind(R.id.sendButton)
    ImageView mSendButton;

    @Bind(R.id.li_user_comment_post_type_selection)
    LinearLayout liUserPostTypeSelection;

    @Bind(R.id.tv_user_name_for_post)
    TextView tvUserNameForPost;

    @Bind(R.id.tv_anonymous_post)
    TextView tvAnonymousPost;

    @Bind(R.id.suggestions_list)
    RecyclerView mSuggestionList;

    @BindDimen(R.dimen.dp_size_36)
    int profileSize;

    long adminId =0;
    private UserPostSolrObj userPostSolrObj;

    //endregion

    //region presenter region
    private PostDetailAdapter mPostDetailListAdapter;
    private UserPostSolrObj mUserPostObj;
    private String mUserPostId;
    private boolean mIsAnonymous;
    private String mPrimaryColor = "#ffffff";
    private String mTitleTextColor = "#3c3c3c";
    private String mStatusBarColor = "#aaaaaa";
    private String mToolbarIconColor = "#90949C";

    private int mFromNotification;
    LinearLayoutManager mLayoutManager;
    private List<MentionSpan> mentionSpanList;
    List<TaggedUserPojo> mTaggedUserPojoList;
    private boolean hasMentions = false;
    private String mUserTagCommentInfoText = "You can tag community owners, your followers or people who engaged on this post";
    //endregion

    //region activity methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        mPostDetailPresenter.attachView(this);
        mUserPic.setCircularImage(true);
        setIsLoggedInUser();
        etView.setQueryTokenReceiver(this);

        etView.setEditTextShouldWrapContent(true);
        Parcelable parcelable = getIntent().getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
        if (parcelable != null) {
            mUserPostObj = Parcels.unwrap(parcelable);
            mPositionInFeed = mUserPostObj.getItemPosition();
            streamType = mUserPostObj.getStreamType();
            boolean showKeyboard = getIntent().getExtras().getBoolean(SHOW_KEYBOARD, false);
            if (showKeyboard) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        } else {
            mUserPostId = getIntent().getStringExtra(UserPostSolrObj.USER_POST_ID);
            if (!CommonUtil.isNotEmpty(mUserPostId)) {
                return;
            }
        }

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
        }

        if (null != getIntent() && getIntent().getExtras() != null) {
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            mPrimaryColor = getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR, mPrimaryColor);
            mTitleTextColor = getIntent().getExtras().getString(FeedFragment.TITLE_TEXT_COLOR, mTitleTextColor);

            if (getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR) == null) {
                mStatusBarColorEmpty = true;
            }

        }
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        initAdapter();

        mPostDetailPresenter.setUserPost(mUserPostObj, mUserPostId);
        mPostDetailPresenter.fetchUserPost();
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getFirstName())) {
            tvUserNameForPost.setText(mUserPreference.get().getUserSummary().getFirstName());
            mUserPic.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(mUserPreference.get().getUserSummary().getPhotoUrl(), profileSize, profileSize);
            mUserPic.bindImage(authorThumborUrl);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mUserPostObj != null && StringUtil.isNotNullOrEmptyString(mUserPostObj.getAuthorName())) {
            if (mUserPostObj.getAuthorName().equalsIgnoreCase(getString(R.string.ID_ADMIN))) {
                mTitleToolbar.setText(mUserPostObj.getPostCommunityName() + " post");
            } else {
                mTitleToolbar.setText(mUserPostObj.getAuthorName() + "'s" + " post");
            }
        }
        if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData!=null && CommonUtil.isNotEmpty(mConfiguration.get().configData.mCommentHolderText)) {
            etView.getEditText().setHint(mConfiguration.get().configData.mCommentHolderText);
            mUserTagCommentInfoText = mConfiguration.get().configData.mUserTagCommentInfoText;
        } else {
            etView.getEditText().setHint("Type your comment here...");
        }
        setupToolbarItemsColor();
        etView.onReceiveSuggestionsListView(mSuggestionList);

        etView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!keyboardShown(etView.getRootView())) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mSuggestionList.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean keyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    private boolean isWhatsAppShare() {
        boolean isWhatsappShare = false;
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareText = "";
            shareText = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareText)) {
                if (shareText.equalsIgnoreCase("true")) {
                    isWhatsappShare = true;
                }
            }
        }
        return isWhatsappShare;
    }

    private void setIsLoggedInUser() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserId()) {
            mLoggedInUser = mUserPreference.get().getUserSummary().getUserId();
        }
    }

    @OnClick(R.id.tv_user_name_for_post)
    public void userNamePostForComment() {
        mIsAnonymous = false;
        tvUserNameForPost.setTextColor(ContextCompat.getColor(this, R.color.red));
        tvAnonymousPost.setTextColor(ContextCompat.getColor(this, R.color.searchbox_text_color));
    }

    @OnClick(R.id.tv_anonymous_post)
    public void anonymousPostForComment() {
        mIsAnonymous = true;
        tvUserNameForPost.setTextColor(ContextCompat.getColor(this, R.color.searchbox_text_color));
        tvAnonymousPost.setTextColor(ContextCompat.getColor(this, R.color.red));
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(@StringRes int stringRes) {
        Snackbar.make(mRecyclerView, stringRes, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            if (mFromNotification > 0) {
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            }
        }
        setResult();
        super.onBackPressed();

    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mPostDetailPresenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CommonUtil.hideKeyboard(this);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void setResult() {
        UserPostSolrObj userPostSolrObj = mPostDetailPresenter.getUserPostObj();
        if (userPostSolrObj == null) {
            return;
        }
        userPostSolrObj.setItemPosition(mPositionInFeed);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(userPostSolrObj);
        bundle.putParcelable(UserPostSolrObj.USER_POST_OBJ, parcelable);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void addAllPost(int startIndex, List<Comment> commentList) {
        mPostDetailListAdapter.addDatas(startIndex, commentList);
    }

    @Override
    public void addData(int index, BaseResponse baseResponse) {
        mPostDetailListAdapter.addData(baseResponse, index);
    }

    @Override
    public void removeData(int index) {
        mPostDetailListAdapter.removeData(index);
    }

    @Override
    public void setData(int index, BaseResponse baseResponse) {
        mPostDetailListAdapter.setData(index, baseResponse);
    }

    @Override
    public void updateComment(Comment comment) {
        if (isDirty && !lastEditedComment.isEmpty()) {
            isDirty = false;
            Map.Entry<Integer, Comment> entry = lastEditedComment.entrySet().iterator().next();
            lastEditedComment.clear();
            mPostDetailListAdapter.addData(comment, entry.getKey());
            mLayoutManager.scrollToPositionWithOffset(entry.getKey(), 0);
        }
    }

    @Override
    public void onPostDeleted() {
        UserPostSolrObj userPostSolrObj = mPostDetailPresenter.getUserPostObj();
        userPostSolrObj.setItemPosition(mPositionInFeed);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(userPostSolrObj);
        bundle.putParcelable(UserPostSolrObj.USER_POST_OBJ, parcelable);
        bundle.putBoolean(IS_POST_DELETED, true);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void editLastComment() {
        Comment comment = mPostDetailPresenter.getLastComment();
        if (comment != null) {
            isDirty = true;
            onEditMenuClicked(comment);
        }
    }

    @Override
    public void deleteLastComment() {
        Comment comment = mPostDetailPresenter.getLastComment();
        if (comment != null) {
            if (adminId == AppConstants.TWO_CONSTANT && !comment.isMyOwnParticipation()) {
                // if admin deletes the comment it also need to send reason
                reportSpamDialog(SpamContentType.COMMENT, null, comment);
            } else {
                // if own comment directly delete the comment
                onDeleteMenuClicked(comment);
            }
        }
    }


    @Override
    public String getStreamType() {
        return streamType;
    }

    @Override
    public void addData(BaseResponse baseResponse) {
        mPostDetailListAdapter.addData(baseResponse);
    }

    @Override
    public void setHasMoreComments(boolean b) {
        mPostDetailListAdapter.setHasMoreComments(b);
    }

    @Override
    public void smoothScrollToBottom() {
        mRecyclerView.smoothScrollToPosition(mPostDetailListAdapter.getItemCount() - 1);
        if (null == mUserPostObj) {
            UserPostSolrObj userPostSolrObj = mPostDetailPresenter.getUserPostObj();
            if (userPostSolrObj != null && StringUtil.isNotNullOrEmptyString(userPostSolrObj.getAuthorName())) {
                mTitleToolbar.setText(userPostSolrObj.getAuthorName() + "'s" + " post");
            }
        }
    }

    @Override
    public void commentStartedLoading() {
        mPostDetailListAdapter.commentStartedLoading();
    }

    @Override
    public void commentFinishedLoading() {
        mPostDetailListAdapter.commentFinishedLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            if (intent == null) {
                return;
            }
            if (requestCode == AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST) {
                if (null != intent.getExtras()) {
                    UserPostSolrObj userPostSolrObj = Parcels.unwrap(intent.getParcelableExtra(AppConstants.COMMUNITY_POST_FRAGMENT));
                    mPostDetailPresenter.updateUserPost(userPostSolrObj);
                }
            }
        }
    }

    //endregion

    //region static methods
    public static void navigateTo(Activity fromActivity, String sourceScreen, UserPostSolrObj userPostSolrObj, int requestCode, HashMap<String, Object> properties, boolean showKeyboard) {
        Intent intent = new Intent(fromActivity, PostDetailActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        Parcelable parcelable = Parcels.wrap(userPostSolrObj);
        intent.putExtra(UserPostSolrObj.USER_POST_OBJ, parcelable);
        intent.putExtra(SHOW_KEYBOARD, showKeyboard);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        // ActivityCompat.startActivity(fromActivity, intent, null);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, String sourceScreen, UserPostSolrObj userPostSolrObj, int requestCode, HashMap<String, Object> properties, boolean showKeyboard, String primaryColor, String titleTextColor) {
        Intent intent = new Intent(fromActivity, PostDetailActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        Parcelable parcelable = Parcels.wrap(userPostSolrObj);
        intent.putExtra(UserPostSolrObj.USER_POST_OBJ, parcelable);
        intent.putExtra(SHOW_KEYBOARD, showKeyboard);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        intent.putExtra(FeedFragment.PRIMARY_COLOR, primaryColor);
        intent.putExtra(FeedFragment.TITLE_TEXT_COLOR, titleTextColor);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    //endregion

    //region private methods


    private void initAdapter() {
        mPostDetailListAdapter = new PostDetailAdapter(this, this, this);
        mRecyclerView.setAdapter(mPostDetailListAdapter);
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        switch (s) {
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

    // user post detail callbacks
    @Override
    public void loadMoreComments() {
        if (mUserPostObj == null) {
            mUserPostObj = mPostDetailPresenter.getUserPostObj();
        }
        if (mUserPostObj != null) {
            MixpanelHelper.getPostProperties(mUserPostObj, getScreenName());
        }
        HashMap<String, Object> properties = MixpanelHelper.getPostProperties(mUserPostObj, getScreenName());
        AnalyticsManager.trackEvent(Event.POST_SHARED_CLICKED, getScreenName(), properties);
        mPostDetailPresenter.fetchMoreComments();
    }

    @Override
    public void onPostImageClicked(UserPostSolrObj userPostObj) {
        AlbumActivity.navigateTo(this, userPostObj, SCREEN_LABEL, null);
    }

    @Override
    public void onPostMenuClicked(final UserPostSolrObj userPostObj, final TextView view) {
        PopupMenu popup = new PopupMenu(PostDetailActivity.this, view);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            // popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
            Menu menu = popup.getMenu();
            menu.add(0, R.id.share, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_share_black), getResources().getString(R.string.ID_SHARE)));
            menu.add(0, R.id.edit, 2, menuIconWithText(getResources().getDrawable(R.drawable.ic_create), getResources().getString(R.string.ID_EDIT)));
            menu.add(0, R.id.delete, 3, menuIconWithText(getResources().getDrawable(R.drawable.ic_delete), getResources().getString(R.string.ID_DELETE)));
            menu.add(0, R.id.report_spam, 4, menuIconWithText(getResources().getDrawable(R.drawable.ic_report_spam), getResources().getString(R.string.REPORT_SPAM)));

            if (adminId == AppConstants.TWO_CONSTANT || userPostObj.isCommunityOwner()) {
                if (userPostObj.isTopPost()) {
                    popup.getMenu().add(0, R.id.top_post, 5, menuIconWithText(getResources().getDrawable(R.drawable.ic_feature_post), getResources().getString(R.string.UNFEATURE_POST)));
                } else {
                    popup.getMenu().add(0, R.id.top_post, 5, menuIconWithText(getResources().getDrawable(R.drawable.ic_feature_post), getResources().getString(R.string.FEATURE_POST)));
                }
            }
            //****   Hide/show options according to user
            if (userPostObj.getAuthorId() == mLoggedInUser || userPostObj.isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.delete).setVisible(true);
                if (userPostObj.isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
                    if (userPostObj.getAuthorId() == mLoggedInUser) {
                        popup.getMenu().findItem(R.id.edit).setVisible(true);
                    } else {
                        popup.getMenu().findItem(R.id.edit).setVisible(false);
                    }
                } else {
                    popup.getMenu().findItem(R.id.edit).setVisible(true);
                }

            } else {
                popup.getMenu().findItem(R.id.delete).setVisible(false);
                popup.getMenu().findItem(R.id.edit).setVisible(false);
            }
            popup.getMenu().findItem(R.id.share).setVisible(true);

            if (mLoggedInUser != userPostObj.getAuthorId() && adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }

            if (userPostObj.communityId == 0) {
                popup.getMenu().findItem(R.id.delete).setVisible(false);
            }
            if (userPostObj.isSpamPost()) {
                popup.getMenu().findItem(R.id.share).setVisible(false);
            }

            if (userPostObj.getAuthorId() == mLoggedInUser ||  adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.report_spam).setVisible(false);
            } else {
                popup.getMenu().findItem(R.id.report_spam).setVisible(true);
            }

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            CommunityPostActivity.navigateTo(PostDetailActivity.this, userPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, null);
                            return true;
                        case R.id.delete:

                            userPostSolrObj = userPostObj;
                            if(mLoggedInUser != userPostObj.getAuthorId() && adminId == AppConstants.TWO_CONSTANT) {
                                reportSpamDialog(SpamContentType.POST, userPostObj, null);
                            } else{
                                AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostSolrObj, getScreenName());
                                mPostDetailPresenter.deleteCommunityPostFromPresenter(AppUtils.deleteCommunityPostRequest(userPostSolrObj.getIdOfEntityOrParticipant()));
                            }
                            return true;
                        case R.id.top_post:
                            AnalyticsManager.trackPostAction(Event.POST_TOP_POST, userPostObj, getScreenName());
                            mPostDetailPresenter.editTopPost(AppUtils.topCommunityPostRequestBuilder(userPostObj.communityId, getCreatorType(userPostObj), userPostObj.getListDescription(), userPostObj.getIdOfEntityOrParticipant(), !userPostObj.isTopPost()));
                            return true;
                        case R.id.share:
                            shareWithMultipleOption(userPostObj);
                            return true;
                        case R.id.report_spam:
                            reportSpamDialog(SpamContentType.POST, userPostObj, null);
                            return true;


                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    public void shareWithMultipleOption(BaseResponse baseResponse) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getPostShortBranchUrls())) {
            deepLinkUrl = feedDetail.getPostShortBranchUrls();
        } else {
            deepLinkUrl = feedDetail.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        HashMap<String, Object> properties = MixpanelHelper.getPostProperties(feedDetail, getScreenName());
        properties.put(EventProperty.SHARED_TO.getString(), AppConstants.SHARE_CHOOSER);
        AnalyticsManager.trackEvent(Event.POST_SHARED, getScreenName(), properties);
    }

    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    private String getCreatorType(UserPostSolrObj userPostSolrObj) {
        if (userPostSolrObj.isAnonymous()) {
            return AppConstants.ANONYMOUS;
        } else if (userPostSolrObj.getEntityOrParticipantTypeId() == 15) {
            return AppConstants.COMMUNITY_OWNER;
        } else {
            return AppConstants.USER;
        }
    }

    @Override
    public void onSpamMenuClicked(UserPostSolrObj userPostObj, TextView spamPostView) {

    }

    @Override
    public void onShareButtonClicked(UserPostSolrObj userPostObj, TextView shareView) {

        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(userPostObj.getPostShortBranchUrls())) {
            deepLinkUrl = userPostObj.getPostShortBranchUrls();
        } else {
            deepLinkUrl = userPostObj.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        if (isWhatsAppShare()) {
            if (CommonUtil.isAppInstalled(this, "com.whatsapp")) {
                intent.setPackage(AppConstants.WHATS_APP);
                intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
                startActivity(intent);
            }
        } else {
            intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
            startActivity(Intent.createChooser(intent, AppConstants.SHARE));

        }
        HashMap<String, Object> properties = MixpanelHelper.getPostProperties(userPostObj, getScreenName());
        properties.put(EventProperty.SHARED_TO.getString(), AppConstants.SHARE_CHOOSER);
        AnalyticsManager.trackEvent(Event.POST_SHARED, getScreenName(), properties);
    }

    @Override
    public void onPostLikeClicked(UserPostSolrObj userPostObj) {
        mPostDetailPresenter.getPostLikesFromPresenter(mAppUtils.likeRequestBuilder(userPostObj.getEntityOrParticipantId(), AppConstants.HEART_REACTION_CONSTANT), userPostObj);
    }

    @Override
    public void onPostUnLikeClicked(UserPostSolrObj userPostObj) {
        mPostDetailPresenter.getPostUnLikesFromPresenter(mAppUtils.likeRequestBuilder(userPostObj.getEntityOrParticipantId(), AppConstants.NO_REACTION_CONSTANT), userPostObj);
    }

    @Override
    public void onChampionProfileClicked(UserPostSolrObj userPostObj, int requestCodeForMentorProfileDetail) {
        long userId = userPostObj.getCreatedBy();
        int position = userPostObj.getItemPosition();
        boolean isMentor = userPostObj.isAuthorMentor();
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);

        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, AppConstants.COMMUNITY_POST_FRAGMENT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public void onSpamApprovedClicked(UserPostSolrObj userPostObj, TextView view) {
        mPostDetailPresenter.getSpamPostApproveFromPresenter(mAppUtils.spamPostApprovedRequestBuilder(userPostObj, true, false, true), userPostObj);
    }

    @Override
    public void onSpamPostDeleteClicked(UserPostSolrObj userPostObj, TextView view) {
        mPostDetailPresenter.getSpamPostApproveFromPresenter(mAppUtils.spamPostApprovedRequestBuilder(userPostObj, true, true, false), userPostObj);
    }

    @Override
    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        final EventProperty.Builder builder = new EventProperty.Builder();
        UserPostSolrObj userPostSolrObj = mPostDetailPresenter.getUserPostObj();
        if (userPostSolrObj != null) {
            builder.title(userPostSolrObj.getNameOrTitle())
                    .communityId(Long.toString(userPostSolrObj.getCommunityId()))
                    .id(Long.toString(userPostSolrObj.getIdOfEntityOrParticipant()))
                    .streamType(userPostSolrObj.getStreamType());
        }
        HashMap<String, Object> properties = builder.build();
        return properties;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    public void onCommentButtonClicked() {
        etView.getEditText().requestFocus();
    }

    @Override
    public void onCommunityTitleClicked(UserPostSolrObj userPostObj) {
        if (null != userPostObj) {
            if (userPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {

                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                    if (StringUtil.isNotNullOrEmptyString(userPostObj.getDeepLinkUrl())) {
                        Uri url = Uri.parse(userPostObj.getDeepLinkUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(url);
                        startActivity(intent);
                    }
                }
            } else {
                if (userPostObj.getCommunityId() == 0) {
                    ContestActivity.navigateTo(this, Long.toString(userPostObj.getUserPostSourceEntityId()), userPostObj.getScreenName(), null);

                } else {
                    CommunityDetailActivity.navigateTo(this, userPostObj.getCommunityId(), getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                }
            }
        }
    }

    @Override
    public void onLikeCountClicked(UserPostSolrObj userPostObj) {
        LikeListBottomSheetFragment.showDialog(this, "", userPostObj.getEntityOrParticipantId());
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse spamResponse, UserPostSolrObj userPostSolrObj, Comment comment) {
        if (spamResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            if(adminId == AppConstants.TWO_CONSTANT) {
                if (comment != null) {
                    mPostDetailPresenter.getSpamCommentApproveFromPresenter(mAppUtils.spamCommentApprovedRequestBuilder(comment, true, true, false), comment);
                } else if(userPostSolrObj!=null) {
                    AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostSolrObj, getScreenName());
                    mPostDetailPresenter.getSpamPostApproveFromPresenter(mAppUtils.spamPostApprovedRequestBuilder(userPostSolrObj, true, true, false), userPostSolrObj);
                }
            }

            if(!spamResponse.isSpamAlreadyReported()) {
                CommonUtil.createDialog(PostDetailActivity.this, getResources().getString(R.string.spam_confirmation_dialog_title), getResources().getString(R.string.spam_confirmation_dialog_message));
            } else {
                CommonUtil.createDialog(PostDetailActivity.this, getResources().getString(R.string.reported_spam_confirmation_dialog_title), getResources().getString(R.string.reported_spam_confirmation_dialog_message, spamResponse.getModelType()));
            }
        }
    }

    //endregion

    //region onclick methods
    @OnClick(R.id.sendButton)
    public void onSendButtonClicked() {
        if(isDirty && editedComment!=null) {
           // mPostDetailPresenter.editCommentListFromPresenter(AppUtils.editCommentRequestBuilder(editedComment.getEntityId(), etView.getEditText().getText().toString(), mIsAnonymous, true, editedComment.getId()), AppConstants.TWO_CONSTANT);
            mPostDetailPresenter.editCommentListFromPresenter(AppUtils.editCommentRequestBuilder(editedComment.getEntityId(), etView.getEditText().getText().toString(), mIsAnonymous, true, editedComment.getId(), hasMentions, mentionSpanList), AppConstants.TWO_CONSTANT);
        } else {
            String message = etView.getEditText().getText().toString().trim();
            if (!TextUtils.isEmpty(message)) {
                lastEditedComment.clear();
                mPostDetailPresenter.addComment(message, mIsAnonymous, hasMentions, mentionSpanList);
            }
        }
        etView.getEditText().setText("");
        CommonUtil.hideKeyboard(this);
    }

    @Override
    public void onCommentMenuClicked(final Comment comment, final ImageView userCommentListMenu) {
        final PopupMenu popup = new PopupMenu(PostDetailActivity.this, userCommentListMenu);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
           // popup.getMenuInflater().inflate(R.menu.menu_edit_delete_comment, popup.getMenu());
            Menu menu = popup.getMenu();
            menu.add(0, R.id.edit, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_create), getResources().getString(R.string.ID_EDIT)));
            menu.add(0, R.id.delete, 2, menuIconWithText(getResources().getDrawable(R.drawable.ic_delete), getResources().getString(R.string.ID_DELETE)));
            menu.add(0, R.id.report_spam, 3, menuIconWithText(getResources().getDrawable(R.drawable.ic_report_spam), getResources().getString(R.string.REPORT_SPAM)));

            if (comment.isMyOwnParticipation() ||  adminId == AppConstants.TWO_CONSTANT) {
               if(comment.isMyOwnParticipation()) {
                   popup.getMenu().findItem(R.id.edit).setVisible(true);
               } else {
                   popup.getMenu().findItem(R.id.edit).setVisible(false);
               }
                popup.getMenu().findItem(R.id.delete).setVisible(true);
                popup.getMenu().findItem(R.id.report_spam).setVisible(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setVisible(false);
                popup.getMenu().findItem(R.id.delete).setVisible(false);
                if(!comment.isSpamComment()) {
                    popup.getMenu().findItem(R.id.report_spam).setVisible(true);
                } else{
                    popup.getMenu().findItem(R.id.report_spam).setVisible(false);
                }
            }

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            onEditMenuClicked(comment);
                            return true;
                        case R.id.delete:
                            if(!comment.isMyOwnParticipation() && adminId == AppConstants.TWO_CONSTANT) {
                                popup.dismiss();
                                reportSpamDialog(SpamContentType.COMMENT, null, comment);
                            } else {
                                onDeleteMenuClicked(comment);
                            }
                            return true;
                        case R.id.report_spam:
                            reportSpamDialog(SpamContentType.COMMENT, null, comment);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    private void onDeleteMenuClicked(Comment comment) {
        HashMap<String, Object> propertiesDelete =
                new EventProperty.Builder()
                        .id(Long.toString(comment.getId()))
                        .postId(Long.toString(comment.getEntityId()))
                        .postType(AnalyticsEventType.COMMUNITY.toString())
                        .communityId(comment.getCommunityId())
                        .body(comment.getComment())
                        .streamType(streamType)
                        .build();
        trackEvent(Event.REPLY_DELETED, propertiesDelete);
        mPostDetailPresenter.editCommentListFromPresenter(AppUtils.editCommentRequestBuilder(comment.getEntityId(), comment.getComment(), false, false, comment.getId(), hasMentions, mentionSpanList), AppConstants.ONE_CONSTANT);
    }

    private void onEditMenuClicked(Comment comment) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(comment.getId()))
                        .postId(Long.toString(comment.getEntityId()))
                        .postType(AnalyticsEventType.COMMUNITY.toString())
                        .communityId(comment.getCommunityId())
                        .body(comment.getComment())
                        .streamType(streamType)
                        .build();
        trackEvent(Event.REPLY_EDITED, properties);
        editedComment = comment;
        if (comment.isHasCommentMention()) {
            hasMentions=comment.isHasCommentMention();
            mentionSpanList = comment.getCommentUserMentionList();
            editUserMentionWithCommentText(mentionSpanList, comment.getComment());
        } else {
            etView.getEditText().setText(comment.getComment());
            etView.getEditText().setSelection(comment.getComment().length());
        }
        int pos = PostDetailViewImpl.findCommentPositionById(mPostDetailListAdapter.getItems(), comment.getId());

        if (mPostDetailListAdapter.getItemCount() > pos) {
            if (pos != RecyclerView.NO_POSITION) {

                if (isDirty && !lastEditedComment.isEmpty()) {
                    Map.Entry<Integer, Comment> entry = lastEditedComment.entrySet().iterator().next();
                    lastEditedComment.clear();
                    mPostDetailListAdapter.addData(entry.getValue(), entry.getKey());
                    pos = PostDetailViewImpl.findCommentPositionById(mPostDetailListAdapter.getItems(), comment.getId());
                }

                mPostDetailListAdapter.removeData(pos);

                lastEditedComment.put(pos, comment);
                isDirty = true;
            }
        }
        etView.setEditTextShouldWrapContent(true);
    }

    private void editUserMentionWithCommentText(@NonNull List<MentionSpan> mentionSpanList, String editDescText) {
        if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
            for (int i = 0; i < mentionSpanList.size(); i++) {
                final MentionSpan mentionSpan = mentionSpanList.get(i);
                editDescText = editDescText.replace(mentionSpan.getDisplayString(), " ");
            }

            etView.getEditText().setText(editDescText);
            for (int i = 0; i < mentionSpanList.size(); i++) {
                final MentionSpan mentionSpan = mentionSpanList.get(i);
                TaggedUserPojo userMention = mentionSpan.getMention();
                int index = userMention.getStartIndex();
                etView.setMentionSelectionText(userMention, index, index + 1);
            }
            etView.getEditText().setSelection(etView.getEditText().length());
        }
    }

    private void reportSpamDialog(final SpamContentType spamContentType, final UserPostSolrObj userPostSolrObj, final Comment comment) {

        if(PostDetailActivity.this == null || PostDetailActivity.this.isFinishing()) return;

        SpamReasons spamReasons;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.reasonOfSpamCategory != null) {
            spamReasons = mConfiguration.get().configData.reasonOfSpamCategory;
        } else {
            String spamReasonsContent = AppUtils.getStringContent(AppConstants.SPAM_REASONS_FILE); //read spam reasons from local file
            spamReasons = AppUtils.parseUsingGSONFromJSON(spamReasonsContent, SpamReasons.class.getName());
        }

        if(spamReasons == null) return;

        final Dialog spamReasonsDialog = new Dialog(PostDetailActivity.this);
        spamReasonsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        spamReasonsDialog.setCancelable(true);
        spamReasonsDialog.setContentView(R.layout.dialog_spam_options);

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.convertDpToPixel(16, PostDetailActivity.this), CommonUtil.convertDpToPixel(10, PostDetailActivity.this), 0, 0);

        TextView reasonTitle = spamReasonsDialog.findViewById(R.id.reason_title);
        TextView reasonSubTitle = spamReasonsDialog.findViewById(R.id.reason_sub_title);
        reasonTitle.setLayoutParams(layoutParams);
        reasonSubTitle.setLayoutParams(layoutParams);

        final RadioGroup spamOptions = spamReasonsDialog.findViewById(R.id.options_container);

        List<Spam> spamList =null;
        SpamPostRequest spamRequest = null;
        if(spamContentType == SpamContentType.POST) {
            spamList = spamReasons.getPostTypeSpams();
            spamRequest = SpamUtil.createSpamPostRequest(userPostSolrObj, false, mLoggedInUser);
        } else if(spamContentType == SpamContentType.COMMENT) {
            spamList = spamReasons.getCommentTypeSpams();
            spamRequest = SpamUtil.spamCommentRequestBuilder(comment, mLoggedInUser);
        }

        if(spamRequest == null || spamList == null) return;
        SpamUtil.addRadioToView(PostDetailActivity.this, spamList , spamOptions);

        Button submit = spamReasonsDialog.findViewById(R.id.submit);
        final EditText reason = spamReasonsDialog.findViewById(R.id.edit_text_reason);

        final SpamPostRequest finalSpamRequest = spamRequest;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spamOptions.getCheckedRadioButtonId()!=-1) {

                    RadioButton radioButton = spamOptions.findViewById(spamOptions.getCheckedRadioButtonId());
                    Spam spam = (Spam) radioButton.getTag();
                    if (spam != null) {
                        finalSpamRequest.setSpamReason(spam.getReason());
                        finalSpamRequest.setScore(spam.getScore());

                        if (spam.getLabel().equalsIgnoreCase("Others")) { //If reason "other" is selected
                            if (reason.getVisibility() == View.VISIBLE) {

                                if(reason.getText().length() > 0 && reason.getText().toString().trim().length()>0) {
                                    finalSpamRequest.setSpamReason(spam.getReason().concat(":"+reason.getText().toString()));
                                    mPostDetailPresenter.reportSpamPostOrComment(finalSpamRequest, userPostSolrObj, comment); //submit
                                    spamReasonsDialog.dismiss();

                                    if(spamContentType == SpamContentType.POST) {
                                        AnalyticsManager.trackPostAction(Event.POST_REPORTED, userPostSolrObj, getScreenName());
                                    } else if(spamContentType == SpamContentType.COMMENT) {
                                        AnalyticsManager.trackPostAction(Event.REPLY_REPORTED, userPostSolrObj, getScreenName());
                                    }

                                } else {
                                    reason.setError("Add the reason");
                                }

                            } else {
                                reason.setVisibility(View.VISIBLE);
                                SpamUtil.hideSpamReason(spamOptions, spamOptions.getCheckedRadioButtonId());
                            }
                        } else {
                            mPostDetailPresenter.reportSpamPostOrComment(finalSpamRequest, userPostSolrObj, comment);  //submit request
                            spamReasonsDialog.dismiss();

                            if(spamContentType == SpamContentType.POST) {
                                AnalyticsManager.trackPostAction(Event.POST_REPORTED, userPostSolrObj, getScreenName());
                            } else if(spamContentType == SpamContentType.COMMENT) {
                                AnalyticsManager.trackPostAction(Event.REPLY_REPORTED, userPostSolrObj, getScreenName());
                            }
                        }
                    }
                }
            }
        });

        spamReasonsDialog.show();
    }


    @Override
    public void userCommentLikeRequest(Comment comment, boolean isLikedAction, int adapterPosition) {
        if (isLikedAction) {
            mPostDetailPresenter.getCommentLikesFromPresenter(mAppUtils.likeRequestBuilder(comment.getEntityId(), AppConstants.HEART_REACTION_CONSTANT, comment.getCommentsId()), comment);
        } else {
            mPostDetailPresenter.getCommentUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(comment.getEntityId(), comment.getCommentsId()), comment);
        }
    }

    @Override
    public void userProfileNameClick(Comment comment, View view) {
        if (!comment.isAnonymous() && comment.getParticipantUserId() != null) {
            CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
            communityFeedSolrObj.setIdOfEntityOrParticipant(comment.getParticipantUserId());
            communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
            ProfileActivity.navigateTo(this, communityFeedSolrObj, comment.getParticipantUserId(), comment.isVerifiedMentor(), 0, AppConstants.COMMUNITY_POST_FRAGMENT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    @Override
    public void userProfilePicClick(Comment comment, View view) {
        userProfileNameClick(comment, view);
    }
    //endregion

    private void setupToolbarItemsColor() {
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
        if (upArrow != null) {
            upArrow.mutate();
            upArrow.setColorFilter(Color.parseColor(mTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        }

        mTitleToolbar.setTextColor(Color.parseColor(mTitleTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mStatusBarColorEmpty) {
                if (upArrow != null) {
                    upArrow.setColorFilter(Color.parseColor(mToolbarIconColor), PorterDuff.Mode.SRC_ATOP);
                }
                getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mStatusBarColor)));
            } else {
                getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mPrimaryColor)));
            }
        }

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mToolbar.setBackgroundColor(Color.parseColor(mPrimaryColor));
    }

    public void onDestroy() {
        super.onDestroy();
        mPostDetailPresenter.detachView();
    }

    @Override
    public void userTagResponse(SearchUserDataResponse searchUserDataResponse, QueryToken queryToken) {
        if(StringUtil.isNotEmptyCollection(mTaggedUserPojoList)) {
            if (StringUtil.isNotEmptyCollection(searchUserDataResponse.getParticipantList())) {
                mTaggedUserPojoList = searchUserDataResponse.getParticipantList();
                List<TaggedUserPojo> taggedUserPojoList = searchUserDataResponse.getParticipantList();
                taggedUserPojoList.add(0, new TaggedUserPojo(1, mUserTagCommentInfoText, "", "", 0));
                hasMentions = true;
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mSuggestionList.setLayoutManager(layoutManager);
                mSuggestionList.setAdapter(etView.notifyAdapterOnData(mTaggedUserPojoList));
            } else {
                hasMentions = false;
                mentionSpanList = null;
                List<TaggedUserPojo> taggedUserPojoList = new ArrayList<>();
                taggedUserPojoList.add(0, new TaggedUserPojo(1, mUserTagCommentInfoText, "", "", 0));
                taggedUserPojoList.add(1, new TaggedUserPojo(0, "", "", "", 0));
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mSuggestionList.setLayoutManager(layoutManager);
                mSuggestionList.setAdapter(etView.notifyAdapterOnData(taggedUserPojoList));
            }
        }
    }

    @Override
    public List<String> onQueryReceived(@NonNull final QueryToken queryToken) {
       final String searchText=queryToken.getTokenString();
        if (searchText.contains("@")) {
            hasMentions = false;
            mentionSpanList = null;
            List<TaggedUserPojo> taggedUserPojoList = new ArrayList<>();
            taggedUserPojoList.add(0, new TaggedUserPojo(1, mUserTagCommentInfoText, "", "", 0));
            taggedUserPojoList.add(1, new TaggedUserPojo(0,getString(R.string.searching),"","",0));

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mSuggestionList.setLayoutManager(layoutManager);
            mSuggestionList.setAdapter(etView.notifyAdapterOnData(taggedUserPojoList));
            mTaggedUserPojoList=taggedUserPojoList;

            //UserTagSuggestionsResult result = new UserTagSuggestionsResult(queryToken, taggedUserPojoList);
           // etView.onReceiveSuggestionsResult(result, "data");
            mProgressBar.setVisibility(View.VISIBLE);
            if (searchText.length() <= 3) {
                mPostDetailPresenter.userTaggingSearchEditText(queryToken, searchText, mUserPostObj);
            } else {
                Timer timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                mPostDetailPresenter.userTaggingSearchEditText(queryToken, searchText, mUserPostObj);
                            }
                        },
                        200
                );
            }
        }
        List<String> buckets = Collections.singletonList("user-history");
        return buckets;
    }

    @Override
    public List<MentionSpan> onMentionReceived(@NonNull List<MentionSpan> mentionSpanList, String allText) {
        this.mentionSpanList = mentionSpanList;
        return null;
    }

    @Override
    public UserTagSuggestionsAdapter onSuggestedList(@NonNull UserTagSuggestionsAdapter userTagSuggestionsAdapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSuggestionList.setLayoutManager(layoutManager);
        mSuggestionList.setAdapter(userTagSuggestionsAdapter);
        return null;
    }

    @Override
    public Suggestible onUserTaggedClick(@NonNull Suggestible suggestible, View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_social_user:
                mTaggedUserPojoList.clear();
                etView.displayHide();
                TaggedUserPojo taggedUserPojo = (TaggedUserPojo) suggestible;
                etView.setInsertion(taggedUserPojo);
                etView.setEditTextShouldWrapContent(true);
                if(null!=mUserPostObj) {
                    final HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .postCommentId(Long.toString(mUserPostObj.getIdOfEntityOrParticipant()))
                                    .taggedIn("COMMENT")
                                    .taggedUserId(Integer.toString(taggedUserPojo.getUserId()))
                                    .build();
                    AnalyticsManager.trackEvent(Event.USER_TAGGED, getScreenName(), properties);
                }
                break;
            default:
        }

        return null;
    }

    @Override
    public void textChangeListner(Editable editText) {
        etView.setEditTextShouldWrapContent(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        if (editText.length() > 0) {
            if (editText.toString().length() == 0) {
                //   etView.setMaxLines(SINGLE_LINE);
                mSendButton.setColorFilter(getResources().getColor(R.color.red_opacity), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                //  etView.setMaxLines(MAX_LINE);
                mSendButton.setColorFilter(getResources().getColor(R.color.email), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }
    }
}
