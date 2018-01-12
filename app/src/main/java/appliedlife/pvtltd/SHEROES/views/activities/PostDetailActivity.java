package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.CommentCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.PostDetailViewImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.PostDetailAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import butterknife.Bind;
import butterknife.BindDimen;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 07/12/17.
 */

public class PostDetailActivity extends BaseActivity implements IPostDetailView, PostDetailCallBack, CommentCallBack {
    public static final String SCREEN_LABEL = "Post Detail Screen";
    public static final String IS_POST_DELETED = "Is Post Deleted";
    public static final String SHOW_KEYBOARD = "Show Keyboard";
    public static final int SINGLE_LINE = 1;
    public static final int MAX_LINE = 5;
    public int mPositionInFeed = -1;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;

    @Inject
    PostDetailViewImpl mPostDetailPresenter;

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
    EditText mInputText;

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

    //endregion

    //region presenter region
    private PostDetailAdapter mPostDetailListAdapter;
    private UserPostSolrObj mUserPostObj;
    private String mUserPostId;
    private boolean mIsAnonymous;
    private String mPrimaryColor = "#6e2f95";
    private String mTitleTextColor = "#ffffff";
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
        Parcelable parcelable = getIntent().getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
        if (parcelable != null) {
            mUserPostObj = Parcels.unwrap(parcelable);
            mPositionInFeed = mUserPostObj.getItemPosition();
            boolean showKeyboard = getIntent().getExtras().getBoolean(SHOW_KEYBOARD, false);
            if(showKeyboard){
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        } else {
            mUserPostId = getIntent().getStringExtra(UserPostSolrObj.USER_POST_ID);
            if (!CommonUtil.isNotEmpty(mUserPostId)) {
                return;
            }
        }

        if (null != getIntent() && getIntent().getExtras()!=null) {
            mPrimaryColor = getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR, "#6e2f95");
            mTitleTextColor = getIntent().getExtras().getString(FeedFragment.TITLE_TEXT_COLOR, "#ffffff");
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        initAdapter();

        mPostDetailPresenter.setUserPost(mUserPostObj, mUserPostId);
        mPostDetailPresenter.fetchUserPost();
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getFirstName())) {
            tvUserNameForPost.setText(mUserPreference.get().getUserSummary().getFirstName());
            mUserPic.setCircularImage(true);
            mUserPic.bindImage(mUserPreference.get().getUserSummary().getPhotoUrl());
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitleToolbar.setText(R.string.ID_COMMENTS);
        setupEditInputText();
        setupToolbarItemsColor();
    }

    @OnClick(R.id.tv_user_name_for_post)
    public void userNamePostForComment() {
        mIsAnonymous = false;
        tvUserNameForPost.setTextColor(ContextCompat.getColor(this, R.color.blue));
        tvAnonymousPost.setTextColor(ContextCompat.getColor(this, R.color.searchbox_text_color));
    }

    @OnClick(R.id.tv_anonymous_post)
    public void anonymousPostForComment() {
        mIsAnonymous = true;
        tvUserNameForPost.setTextColor(ContextCompat.getColor(this, R.color.searchbox_text_color));
        tvAnonymousPost.setTextColor(ContextCompat.getColor(this, R.color.blue));
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
        setResult();
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CommonUtil.hideKeyboard(this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                    onBackPressed();
                } else {
                    onBackPressed();
                   // finish();
                        //NavUtils.navigateUpFromSameTask(this);
                }
                break;
        }
        return true;
    }

    private void setResult() {
        UserPostSolrObj userPostSolrObj = mPostDetailPresenter.getUserPostObj();
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
        if(comment!=null){
            onEditMenuClicked(comment);
        }
    }

    @Override
    public void deleteLastComment() {
        Comment comment = mPostDetailPresenter.getLastComment();
        if(comment!=null){
            onDeleteMenuClicked(comment);
        }
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
                if (null != intent && null != intent.getExtras()) {
                    UserPostSolrObj userPostSolrObj = (UserPostSolrObj) Parcels.unwrap(intent.getParcelableExtra(AppConstants.COMMUNITY_POST_FRAGMENT));
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
    private void setupEditInputText() {
        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mInputText.getText().toString().length() == 0) {
                    mInputText.setMaxLines(SINGLE_LINE);
                    mSendButton.setColorFilter(getResources().getColor(R.color.red_opacity), android.graphics.PorterDuff.Mode.MULTIPLY);
                    liUserPostTypeSelection.setVisibility(View.GONE);
                } else {
                    mInputText.setMaxLines(MAX_LINE);
                    mSendButton.setColorFilter(getResources().getColor(R.color.email), android.graphics.PorterDuff.Mode.MULTIPLY);
                    liUserPostTypeSelection.setVisibility(View.VISIBLE);
                }
            }
        });
    }

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
        mPostDetailPresenter.fetchMoreComments();
    }

    @Override
    public void onPostImageClicked(UserPostSolrObj userPostObj) {
        AlbumActivity.navigateTo(this, userPostObj, SCREEN_LABEL, null);
    }

    @Override
    public void onPostMenuClicked(final UserPostSolrObj userPostObj, TextView view) {
        PopupMenu popup = new PopupMenu(PostDetailActivity.this, view);
        long currentUserId = -1;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            currentUserId = mUserPreference.get().getUserSummary().getUserId();
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
            if (currentUserId!=userPostObj.getAuthorId() && adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }
            if (adminId == AppConstants.TWO_CONSTANT || userPostObj.isCommunityOwner()) {
                popup.getMenu().findItem(R.id.top_post).setVisible(true);
                if(userPostObj.isTopPost()){
                    popup.getMenu().findItem(R.id.top_post).setTitle(R.string.UNFEATURE_POST);
                }else {
                    popup.getMenu().findItem(R.id.top_post).setTitle(R.string.FEATURE_POST);
                }
            } else {
                popup.getMenu().findItem(R.id.top_post).setVisible(false);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            CommunityPostActivity.navigateTo(PostDetailActivity.this, userPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, null);
                            return true;
                        case R.id.delete:
                            AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostObj, getScreenName());
                            mPostDetailPresenter.deleteCommunityPostFromPresenter(mAppUtils.deleteCommunityPostRequest(userPostObj.getIdOfEntityOrParticipant()));
                            return true;
                        case R.id.top_post:
                            AnalyticsManager.trackPostAction(Event.POST_TOP_POST, userPostObj, getScreenName());
                            mPostDetailPresenter.editTopPost(AppUtils.topCommunityPostRequestBuilder(userPostObj.communityId, getCreatorType(userPostObj), userPostObj.getListDescription(), userPostObj.getIdOfEntityOrParticipant(),!userPostObj.isTopPost()));
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    private String getCreatorType(UserPostSolrObj userPostSolrObj) {
        if (userPostSolrObj.getEntityOrParticipantTypeId() == 15) {
            return AppConstants.COMMUNITY_OWNER;
        } else if (userPostSolrObj.isAnonymous()) {
            return AppConstants.ANONYMOUS;
        } else {
            return AppConstants.USER;
        }
    }

    @Override
    public void onSpamMenuClicked(UserPostSolrObj userPostObj, TextView spamPostView) {

    }

    @Override
    public void onShareButtonClicked(UserPostSolrObj userPostObj, TextView shareView) {
        AnalyticsManager.trackPostAction(Event.POST_SHARED, userPostObj, getScreenName());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, userPostObj.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
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

        MentorUserProfileActvity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, AppConstants.COMMUNITY_POST_FRAGMENT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public void onSpamApprovedClicked(UserPostSolrObj userPostObj, TextView view) {

    }

    @Override
    public void onSpamPostDeleteClicked(UserPostSolrObj userPostObj, TextView view) {

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
                    .id(Integer.toString(userPostSolrObj.getEntityOrParticipantTypeId()));

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
        mInputText.requestFocus();
    }

    @Override
    public void onCommunityTitleClicked(UserPostSolrObj userPostObj) {
        if(userPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID){
            if(null!=userPostObj) {
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                    if(StringUtil.isNotNullOrEmptyString(userPostObj.getDeepLinkUrl())) {
                        Uri url = Uri.parse(userPostObj.getDeepLinkUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(url);
                        startActivity(intent);
                    }
                }
            }
        }else {
            if (userPostObj.getCommunityTypeId() == 0) {
                ContestActivity.navigateTo(this, Long.toString(userPostObj.getUserPostSourceEntityId()), userPostObj.getScreenName(), null);

            }else {
                CommunityDetailActivity.navigateTo(this, ((UserPostSolrObj) userPostObj).getCommunityId(), getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            }
        }
    }

    @Override
    public void onLikeCountClicked(UserPostSolrObj userPostObj) {
        LikeListBottomSheetFragment.showDialog(this, "", userPostObj.getEntityOrParticipantId());
    }

    //endregion

    //region onclick methods
    @OnClick(R.id.sendButton)
    public void onSendButtonClicked() {
        String message = mInputText.getText().toString().trim();
        if (!TextUtils.isEmpty(message)) {
            mPostDetailPresenter.addComment(message,mIsAnonymous);
            mInputText.setText("");
        }
    }

    @Override
    public void onCommentMenuClicked(final Comment comment, ImageView userCommentListMenu) {
        PopupMenu popup = new PopupMenu(PostDetailActivity.this, userCommentListMenu);
        long currentUserId = -1;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            currentUserId = mUserPreference.get().getUserSummary().getUserId();
        }
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            popup.getMenuInflater().inflate(R.menu.menu_edit_delete_comment, popup.getMenu());
            if (currentUserId !=comment.getEntityAuthorUserId() && adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            onEditMenuClicked(comment);
                            return true;
                        case R.id.delete:
                            onDeleteMenuClicked(comment);
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
                        .body(comment.getComment())
                        .build();
        trackEvent(Event.REPLY_DELETED, propertiesDelete);
        mPostDetailPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(comment.getEntityId(), comment.getComment(), false, false, comment.getId()), AppConstants.ONE_CONSTANT);
    }

    private void onEditMenuClicked(Comment comment) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(comment.getId()))
                        .postId(Long.toString(comment.getEntityId()))
                        .postType(AnalyticsEventType.COMMUNITY.toString())
                        .body(comment.getComment())
                        .build();
        trackEvent(Event.REPLY_EDITED, properties);
        mInputText.setText(comment.getComment());
        mInputText.setSelection(comment.getComment().length());
        mPostDetailPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(comment.getEntityId(), comment.getComment(), false, false, comment.getId()), AppConstants.ONE_CONSTANT);
    }

    @Override
    public void userCommentLikeRequest(Comment comment, boolean isLikedAction, int adapterPosition) {
        if(isLikedAction){
            mPostDetailPresenter.getCommentLikesFromPresenter(mAppUtils.likeRequestBuilder(comment.getEntityId(), AppConstants.HEART_REACTION_CONSTANT, comment.getCommentsId()), comment);
        }else {
            mPostDetailPresenter.getCommentUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(comment.getEntityId(), comment.getCommentsId()), comment);
        }
    }

    @Override
    public void userProfileNameClick(Comment comment, View view) {
        if(comment.getParticipationTypeId() == 7 || comment.getParticipationTypeId() ==1) {
            CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
            communityFeedSolrObj.setIdOfEntityOrParticipant(comment.getEntityAuthorUserId());
            communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
            Parcelable parcelable = Parcels.wrap(communityFeedSolrObj);
            MentorUserProfileActvity.navigateTo(this, communityFeedSolrObj, comment.getEntityAuthorUserId(), comment.isVerifiedMentor(), 0, AppConstants.COMMUNITY_POST_FRAGMENT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    @Override
    public void userProfilePicClick(Comment comment, View view) {
        userProfileNameClick(comment, view);
    }
    //endregion

    private void setupToolbarItemsColor() {
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor(mTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mTitleToolbar.setTextColor(Color.parseColor(mTitleTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mPrimaryColor)));
        }
        mToolbar.setBackgroundColor(Color.parseColor(mPrimaryColor));
    }
}
