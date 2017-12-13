package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
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
import android.widget.EditText;
import android.widget.ImageView;
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
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.presenters.PostDetailViewImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.PostDetailAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 07/12/17.
 */

public class PostDetailActivity extends BaseActivity implements IPostDetailView, PostDetailCallBack, CommentCallBack {
    public static final String SCREEN_LABEL = "Post Detail Screen";
    public static final String IS_POST_DELETED = "Is Post Deleted";
    public static final int SINGLE_LINE = 1;
    public static final int MAX_LINE = 5;
    public int mPositionInFeed = -1;
    @Inject
    Preference<LoginResponse> userPreference;

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

    //endregion

    //region presenter region
    private PostDetailAdapter mPostDetailListAdapter;
    private UserPostSolrObj mUserPostObj;
    private String mUserPostId;
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
        } else {
            mUserPostId = getIntent().getStringExtra(UserPostSolrObj.USER_POST_ID);
            if (!CommonUtil.isNotEmpty(mUserPostId)) {
                return;
            }
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        initAdapter();

        mPostDetailPresenter.setUserPost(mUserPostObj, mUserPostId);
        mPostDetailPresenter.fetchUserPost();
        mUserPic.bindImage(mUserPostObj.getAuthorImageUrl());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitleToolbar.setText(R.string.title_question);
        setupEditInputText();
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
                    finish();
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
    public static void navigateTo(Activity fromActivity, String sourceScreen, UserPostSolrObj userPostSolrObj, int requestCode, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, PostDetailActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        Parcelable parcelable = Parcels.wrap(userPostSolrObj);
        intent.putExtra(UserPostSolrObj.USER_POST_OBJ, parcelable);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
       // ActivityCompat.startActivity(fromActivity, intent, null);
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
                } else {
                    mInputText.setMaxLines(MAX_LINE);
                    mSendButton.setColorFilter(getResources().getColor(R.color.email), android.graphics.PorterDuff.Mode.MULTIPLY);
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
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != userPreference.get().getUserSummary().getUserBO()) {
                adminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
            if (adminId == AppConstants.TWO_CONSTANT) {
                popup.getMenu().findItem(R.id.edit).setEnabled(false);
            } else {
                popup.getMenu().findItem(R.id.edit).setEnabled(true);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            CommunityPostActivity.navigateTo(PostDetailActivity.this, userPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST);
                            return true;
                        case R.id.delete:
                            AnalyticsManager.trackPostAction(Event.POST_DELETED, userPostObj);
                            mPostDetailPresenter.deleteCommunityPostFromPresenter(mAppUtils.deleteCommunityPostRequest(userPostObj.getIdOfEntityOrParticipant()));
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
        popup.show();
    }

    @Override
    public void onSpamMenuClicked(UserPostSolrObj userPostObj, TextView spamPostView) {

    }

    @Override
    public void onShareButtonClicked(UserPostSolrObj userPostObj, TextView shareView) {
        AnalyticsManager.trackPostAction(Event.POST_SHARED, userPostObj);
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

        Intent intent = new Intent(this, PublicProfileGrowthBuddiesDetailActivity.class);
        Bundle bundle = new Bundle();
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        Parcelable parcelable = Parcels.wrap(communityFeedSolrObj);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, null);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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
                if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
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
                Intent intentFromCommunityPost = new Intent(this, CommunitiesDetailActivity.class);
                Bundle bundleFromPost = new Bundle();
                bundleFromPost.putBoolean(AppConstants.COMMUNITY_POST_ID, true);
                Parcelable parcelablesss = Parcels.wrap(userPostObj);
                bundleFromPost.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelablesss);
                bundleFromPost.putLong(AppConstants.COMMUNITY_ID, userPostObj.getCommunityId());
                bundleFromPost.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.MY_COMMUNITY);
                intentFromCommunityPost.putExtras(bundleFromPost);
                startActivityForResult(intentFromCommunityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            }
        }
    }

    //endregion

    //region onclick methods
    @OnClick(R.id.sendButton)
    public void onSendButtonClicked() {
        String message = mInputText.getText().toString().trim();

        if (!TextUtils.isEmpty(message)) {
            mPostDetailPresenter.addComment(message);
            mInputText.setText("");
        }
    }

    @Override
    public void onCommentMenuClicked(final Comment comment, ImageView userCommentListMenu) {
        PopupMenu popup = new PopupMenu(PostDetailActivity.this, userCommentListMenu);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            int adminId = 0;
            if (null != userPreference.get().getUserSummary().getUserBO()) {
                adminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            popup.getMenuInflater().inflate(R.menu.menu_edit_delete, popup.getMenu());
            if (adminId == AppConstants.TWO_CONSTANT) {
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
        AnalyticsManager.trackEvent(Event.REPLY_DELETED, propertiesDelete);
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
        AnalyticsManager.trackEvent(Event.REPLY_EDITED, properties);
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
    //endregion
}
