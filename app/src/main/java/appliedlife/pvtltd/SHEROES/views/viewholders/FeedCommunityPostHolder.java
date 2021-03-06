package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.AppStatus;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.VideoPlayActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.hashTagColorInString;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by Praveen_Singh on 22-01-2017.
 */
public class FeedCommunityPostHolder extends BaseViewHolder<FeedDetail> {
    // region Constants
    private final String TAG = LogUtils.makeLogTag(FeedCommunityPostHolder.class);
    private static final String LEFT_HTML_TAG = "<font color='#3c3c3c'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    private static final float FOLLOW_BUTTON_ORIGINAL = 1.0f;
    private static final float FOLLOW_BUTTON_SEMI_TRANSPARENT = 0.3f;
    //endregion

    // region Constants
    @Inject
    DateUtil mDateUtil;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    Preference<AppConfiguration> mConfiguration;
    @Inject
    FeedUtils feedUtils;
    @Inject
    Preference<AppStatus> mInstallUpdatePreference;
    // endregion

    // region views
    @Bind(R.id.card_view_post)
    CardView rootLayout;
    @Bind(R.id.top_post_view)
    RelativeLayout topPostView;
    @Bind(R.id.fl_spam_post_ui)
    FrameLayout flSpamPostUi;
    @Bind(R.id.spam_message_container)
    LinearLayout spamMessageContainer;
    @Bind(R.id.li_approve_delete)
    LinearLayout liApproveDelete;
    @Bind(R.id.tv_delete_spam_post)
    TextView tvDeleteSpamPost;
    @Bind(R.id.tv_approve_spam_post)
    TextView tvApproveSpamPost;
    @Bind(R.id.spam_comment_ui)
    FrameLayout spamCommentContainer;
    @Bind(R.id.tv_join_conversation)
    TextView mJoinConveration;
    @Bind(R.id.li_community_post_main_layout)
    LinearLayout liCommunityPostMainLayout;
    @Bind(R.id.li_feed_community_post_user_comments)
    LinearLayout liFeedCommunityPostUserComments;
    @Bind(R.id.last_comment_container)
    LinearLayout lastCommentConatiner;
    @Bind(R.id.spam_comment_menu)
    TextView spamCommentMenu;
    @Bind(R.id.comment_like)
    TextView mCommentLike;
    @Bind(R.id.comment_like_count)
    TextView mCommentLikeCount;
    @Bind(R.id.li_feed_community_user_post_images)
    LinearLayout liFeedCommunityUserPostImages;
    @Bind(R.id.iv_feed_community_post_circle_icon)
    CircleImageView ivFeedCommunityPostCircleIcon;
    @Bind(R.id.iv_feed_community_post_login_user_pic)
    CircleImageView ivFeedCommunityPostLoginUserPic;
    @Bind(R.id.tv_feed_community_post_login_user_name)
    TextView tvFeedCommunityPostLoginUserName;
    @Bind(R.id.iv_feed_community_post_circle_icon_verified)
    ImageView ivFeedCommunityPostCircleIconVerified;
    @Bind(R.id.iv_feed_community_post_user_icon_verified)
    ImageView ivFeedCommunityPostUserIconVerified;
    @Bind(R.id.tv_feed_community_post_user_share)
    TextView tvFeedCommunityPostUserShare;
    @Bind(R.id.tv_feed_community_post_user_reaction)
    TextView tvFeedCommunityPostUserReaction;
    @Bind(R.id.tv_feed_community_post_user_comment)
    TextView tvFeedCommunityPostUserComment;
    @Bind(R.id.tv_feed_community_post_view_more)
    TextView tvFeedCommunityPostViewMore;
    @Bind(R.id.tv_feed_community_post_view_less)
    TextView tvFeedCommunityPostViewLess;
    @Bind(R.id.tv_feed_community_post_user_comment_post_view_more)
    TextView tvFeedCommunityPostUserCommentPostViewMore;
    @Bind(R.id.iv_feed_community_post_user_pic)
    CircleImageView ivFeedCommunityPostUserPic;
    @Bind(R.id.follow_button)
    TextView mFollowButton;
    @Bind(R.id.tv_feed_community_post_card_title)
    TextView tvFeedCommunityPostCardTitle;
    @Bind(R.id.tv_feed_community_post_time)
    TextView tvFeedCommunityPostTime;
    @Bind(R.id.tv_feed_community_post_text)
    TextView tvFeedCommunityPostText;
    @Bind(R.id.tv_feed_community_post_user_menu)
    TextView tvFeedCommunityPostUserMenu;
    @Bind(R.id.tv_spam_post_menu)
    TextView tvSpamPostMenu;
    @Bind(R.id.tv_feed_community_post_reaction1)
    TextView tvFeedCommunityPostReaction1;
    @Bind(R.id.tv_feed_community_post_total_reactions)
    TextView tvFeedCommunityPostTotalReactions;
    @Bind(R.id.tv_feed_community_post_total_replies)
    TextView tvFeedCommunityPostTotalReplies;
    @Bind(R.id.tv_feed_community_post_user_comment_post)
    TextView tvFeedCommunityPostUserCommentPost;
    @Bind(R.id.tv_feed_community_post_user_name)
    TextView tvFeedCommunityPostUserName;
    @Bind(R.id.line_for_no_image)
    View lineForNoImage;
    @Bind(R.id.ripple_feed_post_comment)
    RippleView rippleView;
    @Bind(R.id.rl_feed_community_post_no_reaction_comments)
    RelativeLayout rlFeedCommunityPostNoReactionComment;
    @Bind(R.id.tv_feed_community_post_user_comment_post_menu)
    TextView tvFeedCommunityPostUserCommentPostMenu;
    @Bind(R.id.tv_feed_community_post_user_comment_post_time)
    TextView tvFeedCommunityPostUserCommentPostTime;
    @Bind(R.id.progress_bar_post_link)
    ProgressBar pbLink;
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.tv_source_name)
    TextView tvSourceName;
    @Bind(R.id.fm_image_thumb)
    FrameLayout fmImageThumb;
    @Bind(R.id.iv_post_link_thumbnail)
    ImageView ivLinkThumbnail;
    @Bind(R.id.li_post_link_render)
    CardView liViewLinkRender;
    @Bind(R.id.tv_post_link_title)
    TextView tvLinkTitle;
    @Bind(R.id.tv_post_link_sub_title)
    TextView tvLinkSubTitle;
    @Bind(R.id.last_comment_user_badge)
    ImageView lastCommentUserBadge;
    @Bind(R.id.user_badge)
    ImageView badgeOnPic;
    @BindDimen(R.dimen.dp_size_30)
    int authorPicSize;
    @BindDimen(R.dimen.dp_size_40)
    int authorPicSizeFourty;
    // endregion

    // region member variables
    private boolean isWhatappShareOption = false;
    private boolean isToolTipForUser;
    private int mItemPosition;
    private int mAdminId;
    private long mUserId;
    private String mPhotoUrl;
    private String mCommentHolderText;
    private String loggedInUser;
    private BaseHolderInterface viewInterface;
    private UserPostSolrObj mUserPostObj;
    private Context mContext;
    private Handler mHandler;
    private LayoutInflater inflater = null;
    private View view = null;
    // endregion

    // region lifecycle methods
    public FeedCommunityPostHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get().getUserSummary()) {
            mUserId = userPreference.get().getUserSummary().getUserId();
            if (null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
                mPhotoUrl = userPreference.get().getUserSummary().getPhotoUrl();
            }
            String first = userPreference.get().getUserSummary().getFirstName();
            String last = userPreference.get().getUserSummary().getLastName();
            if (StringUtil.isNotNullOrEmptyString(first) || StringUtil.isNotNullOrEmptyString(last)) {
                loggedInUser = first + AppConstants.SPACE + last;
            }
        }
        isWhatappShareOption = CommonUtil.isAppInstalled(SheroesApplication.mContext, AppConstants.WHATS_APP_URI);
        if (mInstallUpdatePreference.get().isWalkThroughShown()) {
            if (CommonUtil.ensureFirstTime(AppConstants.HOME_USER_NAME_PREF)) {
                isToolTipForUser = true;
                AppStatus appStatus = mInstallUpdatePreference.get();
                appStatus.setWalkThroughShown(false);
                mInstallUpdatePreference.set(appStatus);
            }
        } else {
            isToolTipForUser = false;
        }
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
            mCommentHolderText = mConfiguration.get().configData.mCommentHolderText;
        } else {
            mCommentHolderText = new ConfigData().mCommentHolderText;
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.mUserPostObj = (UserPostSolrObj) item;
        mContext = context;
        mJoinConveration.setText(mCommentHolderText);
        if (mUserPostObj.isTopPost()) {
            topPostView.setVisibility(View.VISIBLE);
        } else {
            topPostView.setVisibility(View.GONE);
        }
        mUserPostObj.setItemPosition(position);
        showPostUiFieldsWithData();
        if (mUserPostObj.isSpamPost()) {
            handlingSpamUi(mUserId, mAdminId);
        } else {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
            liApproveDelete.setVisibility(View.GONE);
        }
        invalidateSpamComment(mUserPostObj);
        showToolTip(mContext);
    }

    @Override
    public void viewRecycled() {

    }
    // endregion

    // region onClick methods

    @OnClick(R.id.li_post_link_render)
    public void tvLinkClick() {
        if (null != mUserPostObj) {
            if (mUserPostObj.isOgVideoLinkB() && StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgRequestedUrlS())) {
                if (!mUserPostObj.getOgRequestedUrlS().contains(AppConstants.USER_YOU_TUBE) || !mUserPostObj.getOgRequestedUrlS().contains(AppConstants.CHANNEL_YOU_TUBE)) {
                    Intent youTube = new Intent(mContext, VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.YOUTUBE_VIDEO_CODE, mUserPostObj.getOgRequestedUrlS());
                    youTube.putExtras(bundle);
                    mContext.startActivity(youTube);
                }
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUserPostObj.getOgRequestedUrlS()));
                mContext.startActivity(browserIntent);
            }
        }
    }

    @OnClick({R.id.tv_feed_community_post_total_replies, R.id.li_feed_community_post_user_comments, R.id.tv_feed_community_post_user_comment_post_view_more, R.id.card_view_post})
    public void repliesClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onUserPostClicked(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserComment);
        }
    }

    @OnClick({R.id.tv_join_conversation, R.id.tv_feed_community_post_user_comment})
    public void joinConversationClick() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onUserPostCommentClicked(mUserPostObj);
                } else {
                    viewInterface.handleOnClick(mUserPostObj, mJoinConveration);
                }
            }
        });

    }

    @OnClick({R.id.tv_feed_community_post_user_comment_post})
    public void recentCommentOnPostClicked() {
        mUserPostObj.isRecentCommentClicked = true;
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onUserPostCommentClicked(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, mJoinConveration);
        }
    }

    @OnClick({R.id.tv_feed_community_post_user_comment})
    public void userCommentClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onUserPostCommentClicked(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, mJoinConveration);
        }
    }

    @OnClick(R.id.li_feed_community_user_post_images)
    public void communityPostImageClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onUserPostImageClicked(mUserPostObj);
        } else {
            feedUtils.dataOperationOnClick(mContext, mUserPostObj);
        }
    }

    @OnClick(R.id.tv_feed_community_post_user_menu)
    public void userMenuClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostMenuClicked(mUserPostObj, tvFeedCommunityPostUserMenu);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserMenu);
        }
    }

    @OnClick(R.id.tv_spam_post_menu)
    public void spamMenuClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostMenuClicked(mUserPostObj, tvSpamPostMenu);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvSpamPostMenu);
        }
    }

    @OnClick(R.id.spam_comment_menu)
    public void spamCommentMenuClick() {
        mUserPostObj.setNoOfOpenings(mItemPosition);
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onCommentMenuClicked(mUserPostObj, spamCommentMenu);
        } else {
            viewInterface.handleOnClick(mUserPostObj, spamCommentMenu);
        }

    }

    @OnClick(R.id.tv_feed_community_post_user_comment_post_menu)
    public void userCommentMenuClick() {
        mUserPostObj.setNoOfOpenings(mItemPosition);
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onCommentMenuClicked(mUserPostObj, tvFeedCommunityPostUserCommentPostMenu);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserCommentPostMenu);
        }

    }

    @OnClick(R.id.tv_feed_community_post_view_more)
    public void onViewMoreClicked() {
        if (tvFeedCommunityPostText.getLineCount() > 16) {
            if (viewInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) viewInterface).onUserPostClicked(mUserPostObj);
            } else {
                viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserComment);
            }
        } else {
            expandFeedPostText();
        }
    }

    @OnClick(R.id.tv_feed_community_post_view_less)
    public void onViewLessClicked() {
        collapseFeedPostText();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_first: {
                mUserPostObj.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                if (viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onUserPostImageClicked(mUserPostObj);
                } else {
                    feedUtils.dataOperationOnClick(mContext, mUserPostObj);
                }
                break;
            }
            case R.id.iv_second: {
                mUserPostObj.setItemPosition(AppConstants.ONE_CONSTANT);
                if (viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onUserPostImageClicked(mUserPostObj);
                } else {
                    feedUtils.dataOperationOnClick(mContext, mUserPostObj);
                }
                break;
            }
            case R.id.iv_third: {
                mUserPostObj.setItemPosition(AppConstants.TWO_CONSTANT);
                if (viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onUserPostImageClicked(mUserPostObj);
                } else {
                    feedUtils.dataOperationOnClick(mContext, mUserPostObj);
                }
                break;
            }
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

        }
    }

    @OnClick(R.id.follow_button)
    public void onFollowedButtonClick() {
        if (viewInterface instanceof FeedItemCallback) {
            followButtonVisibility(mContext, !mUserPostObj.isSolrIgnoreIsUserFollowed());
            ((FeedItemCallback) viewInterface).onPostAuthorFollowed(mUserPostObj);
        } else {
            feedUtils.dataOperationOnClick(mContext, mUserPostObj);
        }
    }

    @OnClick(R.id.tv_feed_community_post_user_share)
    public void tvShareClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostShared(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserShare);
        }
    }

    @OnClick(R.id.tv_feed_community_post_total_reactions)
    public void reactionClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onLikesCountClicked(mUserPostObj.getEntityOrParticipantId());
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostTotalReactions);
        }
    }

    @OnClick(R.id.tv_feed_community_post_reaction1)
    public void reaction1Click() {
        viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostTotalReactions);
    }

    @OnClick(R.id.tv_feed_community_post_user_reaction)
    public void userReactionClick() {
        if (viewInterface instanceof FeedItemCallback) {
            if (mUserPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                mUserPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onUserPostUnLiked(mUserPostObj);
            } else {
                mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onUserPostLiked(mUserPostObj);
            }
            likeCommentOps();
        } else {
            if ((Boolean) tvFeedCommunityPostUserReaction.getTag()) {
                userReactionWithouLongPress();
            }
        }
    }

    //Redirect to logged in user
    @OnClick({R.id.iv_feed_community_post_login_user_pic, R.id.tv_feed_community_post_login_user_name})
    public void onCommentAuthorClick() { //Open profile from feed
        if (!mUserPostObj.isAnonymous()) {
            viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL);
        }
    }

    @OnClick(R.id.iv_feed_community_post_circle_icon)
    public void onFeedCommunityPostCircleIconClick() { //Open profile from feed and community post
        if (!mUserPostObj.isAnonymous()) {
            if (mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) { //community
                viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            } else {
                viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
            }
        }
    }

    @OnClick(R.id.tv_feed_community_post_card_title)
    public void onAuthorName() { //Open profile from feed
        if (!mUserPostObj.isAnonymous()) {
            if (mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) { //community
                viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            } else if (viewInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
            }
        }
    }

    @OnClick(R.id.fl_spam_post_ui)
    public void onReviewDescriptionClick() {

    }

    @OnClick(R.id.tv_approve_spam_post)
    public void onApproveSpamPostClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onSpamPostApprove(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvApproveSpamPost);
        }
    }

    @OnClick(R.id.tv_delete_spam_post)
    public void onDeleteSpamPostClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onSpamPostDelete(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvDeleteSpamPost);
        }
    }

    //Last comment user name or user pic
    @OnClick({R.id.iv_feed_community_post_user_pic, R.id.tv_feed_community_post_user_name})
    public void onLastCommentUserClick() { //Open profile from feed
        List<Comment> lastCommentList = mUserPostObj.getLastComments();
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            Comment lastComment = lastCommentList.get(mItemPosition);
            if (!lastComment.isAnonymous()) {
                viewInterface.navigateToProfileView(lastComment, AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_USER_DETAIL);
            }
        }
    }

    @OnClick(R.id.comment_like)
    public void onCommentLikeClicked() {
        List<Comment> lastCommentList = mUserPostObj.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            lastComment.setItemPosition(mUserPostObj.getItemPosition());
            if (lastComment.isLiked) {
                lastComment.isLiked = false;
                lastComment.likeCount--;
            } else {
                lastComment.isLiked = true;
                lastComment.likeCount++;
            }
            invalidateCommentLike(lastComment);
            if (viewInterface instanceof FeedItemCallback) {
                if (lastComment.isLiked) {
                    ((FeedItemCallback) viewInterface).userCommentLikeRequest(mUserPostObj, true, getAdapterPosition());
                } else {
                    ((FeedItemCallback) viewInterface).userCommentLikeRequest(mUserPostObj, false, getAdapterPosition());
                }
            } else {
                if (!lastComment.isLiked) {
                    viewInterface.userCommentLikeRequest(lastComment, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
                } else {
                    viewInterface.userCommentLikeRequest(lastComment, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
                }
            }

        }
    }
    // endregion

    // region private methods

    private void invalidateSpamComment(UserPostSolrObj mUserPostObj) {
        if (mUserPostObj.getLastComments() != null && mUserPostObj.getLastComments().size() > 0) {
            Comment comment = mUserPostObj.getLastComments().get(0);
            if (comment.isSpamComment()) {
                spamCommentContainer.setVisibility(View.VISIBLE);
                lastCommentConatiner.setVisibility(View.GONE);
            } else {
                spamCommentContainer.setVisibility(View.GONE);
                lastCommentConatiner.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showToolTip(Context context) {
        try {
            if (isToolTipForUser) {
                if (!mUserPostObj.isAnonymous() && !mUserPostObj.isAuthorMentor()) {
                    isToolTipForUser = false;
                    inflater = LayoutInflater.from(context);
                    view = inflater.inflate(R.layout.tooltip_arrow_up_side, null);
                    FrameLayout.LayoutParams lps = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    lps.setMargins(CommonUtil.convertDpToPixel(25, context), CommonUtil.convertDpToPixel(60, context), 0, 0);
                    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(CommonUtil.convertDpToPixel(25, context), CommonUtil.convertDpToPixel(18, context));
                    imageParams.gravity = Gravity.START;
                    imageParams.setMargins(CommonUtil.convertDpToPixel(10, context), 0, 0, 0);
                    final ImageView ivArrow = view.findViewById(R.id.iv_arrow);
                    RelativeLayout.LayoutParams arrowParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    arrowParams.setMargins(CommonUtil.convertDpToPixel(20, context), 0, 0, 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
                    ivArrow.setLayoutParams(arrowParams);
                    TextView text = view.findViewById(R.id.title);
                    text.setText(R.string.tool_tip_user_profile);
                    TextView gotIt = view.findViewById(R.id.got_it);
                    gotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (view != null) {
                                rootLayout.removeView(view);
                                view = null;
                            }
                        }
                    });
                    rootLayout.addView(view, lps);
                } else {
                    if (view != null) {
                        rootLayout.removeView(view);
                        view = null;
                    }
                }
            } else {
                if (view != null) {
                    rootLayout.removeView(view);
                    view = null;
                }
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    /**
     * Show post Ui content with data
     * Different Ui formats like Spam,Video and Image rendering etc.
     * Only Normal and link rendering views are handled.
     */
    private void showPostUiFieldsWithData() {
        liCommunityPostMainLayout.setVisibility(View.VISIBLE);
        tvFeedCommunityPostUserReaction.setTag(true);
        mUserPostObj.setLastReactionValue(mUserPostObj.getReactionValue());
        imageOperations(mContext);
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgRequestedUrlS())) {
            liFeedCommunityUserPostImages.removeAllViews();
            liFeedCommunityUserPostImages.removeAllViewsInLayout();
            liFeedCommunityUserPostImages.setVisibility(View.GONE);
            liViewLinkRender.setVisibility(View.VISIBLE);
            showLinkRendering();
        } else {
            liViewLinkRender.setVisibility(View.GONE);
        }
        showPostImageURLs();
        populatePostText();
        postDataContentRendering(mContext);
        likeCommentOps();
        displayFollowedButton();
    }

    private void displayFollowedButton() {
        if (!(viewInterface instanceof FeedItemCallback) || mUserPostObj.isAnonymous() || mUserId == mUserPostObj.getAuthorId() || mUserPostObj.getEntityOrParticipantTypeId() == 13 || mUserPostObj.getEntityOrParticipantTypeId() == 15) {
            mFollowButton.setVisibility(View.GONE);
        } else {
            if (!mUserPostObj.isSolrIgnoreIsUserFollowed()) {
                mFollowButton.setVisibility(View.VISIBLE);
                followButtonVisibility(mContext, false);
            } else {
                mFollowButton.setVisibility(View.GONE);
            }
        }
    }

    //Follow/Following button in leaderboard
    private void followButtonVisibility(Context context, boolean isFollowed) {
        if (isFollowed) {
            mFollowButton.setEnabled(false);
            mFollowButton.setAlpha(FOLLOW_BUTTON_SEMI_TRANSPARENT);
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.white));
            mFollowButton.setText(context.getString(R.string.following_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_grey_winner_dialog);
        } else {
            mFollowButton.setEnabled(true);
            mFollowButton.setAlpha(FOLLOW_BUTTON_ORIGINAL);
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.footer_icon_text));
            mFollowButton.setText(context.getString(R.string.follow_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }

    private void showLinkRendering() {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgTitleS())) {
            tvLinkTitle.setText(mUserPostObj.getOgTitleS());
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgDescriptionS())) {
            tvLinkSubTitle.setText(mUserPostObj.getOgDescriptionS());
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgImageUrlS())) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mUserPostObj.getOgImageUrlS())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            fmImageThumb.setLayoutParams(params);
                            ivLinkThumbnail.setVisibility(View.VISIBLE);
                            ivLinkThumbnail.setImageBitmap(profileImage);
                            pbLink.setVisibility(View.GONE);
                            if (mUserPostObj.isOgVideoLinkB()) {
                                ivPlay.setVisibility(View.VISIBLE);
                                tvSourceName.setVisibility(View.VISIBLE);
                            } else {
                                ivPlay.setVisibility(View.GONE);
                                tvSourceName.setVisibility(View.GONE);
                            }
                        }
                    });
            liViewLinkRender.setVisibility(View.VISIBLE);
        } else {
            liViewLinkRender.setVisibility(View.GONE);
        }
    }

    private void showPostImageURLs() {
        tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        if (StringUtil.isNotEmptyCollection(mUserPostObj.getImageUrls())) {
            liFeedCommunityUserPostImages.setVisibility(View.VISIBLE);
            List<String> coverImageList = mUserPostObj.getImageUrls();
            int listSize = coverImageList.size();
            if (listSize > AppConstants.NO_REACTION_CONSTANT) {
                switch (listSize) {
                    case AppConstants.ONE_CONSTANT:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0))) {
                            feedAlbum(mContext, coverImageList.get(0), null, null, 1);
                        }
                        break;
                    case AppConstants.TWO_CONSTANT:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), null, 2);
                        }
                        break;
                    case AppConstants.THREE_CONSTANT:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), 3);
                        }
                        break;
                    case AppConstants.FOURTH_CONSTANT:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), 4);

                        }
                        break;
                    default:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), listSize);
                        }
                }
            }
        } else {
            liFeedCommunityUserPostImages.removeAllViews();
            liFeedCommunityUserPostImages.removeAllViewsInLayout();
            liFeedCommunityUserPostImages.setVisibility(View.GONE);
        }

    }

    /**
     * User post fields data rendering
     *
     * @param context Post  context
     */
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void postDataContentRendering(Context context) {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getAuthorName())) {
            String feedTitle = mUserPostObj.getAuthorName();
            String acceptPostText = mUserPostObj.getChallengeAcceptPostTextS() == null ? "" : mUserPostObj.getChallengeAcceptPostTextS();
            String communityName = mUserPostObj.communityId == 0 ? acceptPostText + " " + mContext.getString(R.string.challenge) : mUserPostObj.getPostCommunityName();
            if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
                if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                    if (mUserPostObj.isAuthorMentor()) {
                        ivFeedCommunityPostCircleIconVerified.setVisibility(View.VISIBLE);
                    } else {
                        ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                    }
                } else {
                    ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                }
                CommonUtil.showHideUserBadge(mContext, mUserPostObj.isAnonymous(), badgeOnPic, mUserPostObj.isBadgeShownOnPic(), mUserPostObj.getProfilePicBadgeUrl());

                if (mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                    String header;
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                    }
                    clickOnUserNameAndCommunityName(header, feedTitle, communityName);
                } else {
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        String header = mContext.getString(R.string.post_header_name_community, feedTitle, communityName);
                        clickOnUserNameAndCommunityName(header, feedTitle, communityName);
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        String header = mContext.getString(R.string.post_header_community, feedTitle);
                        clickOnCommunityName(header, feedTitle);
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        String header = mContext.getString(R.string.post_header_name_community, feedTitle, communityName);
                        clickOnUserNameAndCommunityName(header, feedTitle, communityName);
                    }
                }

            }
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(mUserPostObj.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvFeedCommunityPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate, mContext));
        } else {
            tvFeedCommunityPostTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }
    }

    private void clickOnUserNameAndCommunityName(String userNameAndCommunity, String userName, String communityName) {

        SpannableString spanString = new SpannableString(userNameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                if (!mUserPostObj.isAnonymous()) {
                    if (viewInterface instanceof FeedItemCallback) {
                        ((FeedItemCallback) viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    } else {
                        viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    }
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };

        ClickableSpan community = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onCommunityTitleClicked(mUserPostObj);
                } else {
                    viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostCardTitle);
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(userName)) {
            spanString.setSpan(authorTitle, 0, userName.length(), 0);
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, userName.length(), 0);
            StyleSpan boldNameSpan = new StyleSpan(Typeface.BOLD);
            spanString.setSpan(boldNameSpan, 0, userName.length(), 0);
            if (StringUtil.isNotNullOrEmptyString(userNameAndCommunity) && StringUtil.isNotNullOrEmptyString(communityName)) {
                int firstIndex = userNameAndCommunity.indexOf(communityName);
                spanString.setSpan(community, firstIndex, firstIndex + communityName.length(), 0);
                spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), firstIndex, firstIndex + communityName.length(), 0);
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                spanString.setSpan(boldSpan, firstIndex, firstIndex + communityName.length(), 0);
            }
            tvFeedCommunityPostCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvFeedCommunityPostCardTitle.setText(spanString, TextView.BufferType.SPANNABLE);
            tvFeedCommunityPostCardTitle.setSelected(true);
        }
    }

    private void clickOnCommunityName(String nameAndCommunity, String communityName) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) { //community
                    viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                } else if (!mUserPostObj.isAnonymous() && viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                } else {
                    if (!mUserPostObj.isAnonymous()) {
                        viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    }
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(communityName)) {
            SpanString.setSpan(authorTitle, 0, communityName.length(), 0);
            if (!communityName.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                SpanString.setSpan(boldSpan, 0, communityName.length(), 0);
            } else {
                StyleSpan regularSpan = new StyleSpan(Typeface.NORMAL);
                SpanString.setSpan(regularSpan, 0, communityName.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
            }
            tvFeedCommunityPostCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvFeedCommunityPostCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            tvFeedCommunityPostCardTitle.setSelected(true);

        }
    }

    private void likeCommentOps() {
        if (mUserPostObj.getNoOfLikes() < AppConstants.ONE_CONSTANT && mUserPostObj.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
            rlFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
            lineForNoImage.setVisibility(View.GONE);
        }
        tvFeedCommunityPostReaction1.setVisibility(View.VISIBLE);
        switch (mUserPostObj.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:

                if (mUserPostObj.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                    lineForNoImage.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReactions.setVisibility(View.GONE);
                    tvFeedCommunityPostReaction1.setVisibility(View.INVISIBLE);
                    tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                } else {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
                    lineForNoImage.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                userLike();
                break;
            default:
                rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                userLike();
        }
        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, mUserPostObj.getNoOfLikes());
        tvFeedCommunityPostTotalReactions.setText(String.valueOf(mUserPostObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));
        switch (mUserPostObj.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mUserPostObj.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                    lineForNoImage.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostReaction1.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReplies.setVisibility(View.INVISIBLE);
                } else {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
                    lineForNoImage.setVisibility(View.GONE);
                }
                userComments();
                break;
            case AppConstants.ONE_CONSTANT:
                tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
                userComments();
                break;
            default:
                tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
                userComments();
        }
        String pluralComments;
        if (mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfAnswers, mUserPostObj.getNoOfComments());
        } else {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, mUserPostObj.getNoOfComments());
        }

        tvFeedCommunityPostTotalReplies.setText(String.valueOf(mUserPostObj.getNoOfComments() + AppConstants.SPACE + pluralComments));
    }

    private void populatePostText() {
        if (isWhatappShareOption) {
            tvFeedCommunityPostUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.vector_share_card), null, null, null);
            tvFeedCommunityPostUserShare.setText(mContext.getString(R.string.ID_SHARE_ON_WHATS_APP));
            tvFeedCommunityPostUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.share_color));

        } else {
            tvFeedCommunityPostUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.vector_share_white_out), null, null, null);
            tvFeedCommunityPostUserShare.setText(mContext.getString(R.string.ID_SHARE));
            tvFeedCommunityPostUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.recent_post_comment));

        }
        final String listDescription = mUserPostObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            tvFeedCommunityPostText.setVisibility(View.GONE);
            tvFeedCommunityPostViewMore.setVisibility(View.GONE);
            tvFeedCommunityPostViewLess.setVisibility(View.GONE);
            return;
        } else {
            tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tvFeedCommunityPostText.setMaxLines(Integer.MAX_VALUE);
                linkifyDescriptionWithUserMention(listDescription);
                if (tvFeedCommunityPostText.getLineCount() > 4) {
                    collapseFeedPostText();
                } else {
                    tvFeedCommunityPostText.setVisibility(View.VISIBLE);
                    if (!mUserPostObj.isTextExpanded) {
                        tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                        tvFeedCommunityPostViewLess.setVisibility(View.GONE);
                    } else {
                        tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
                        tvFeedCommunityPostViewLess.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * Make post description data linkify and apply user mentions
     *
     * @param listDescription post description
     */
    private void linkifyDescriptionWithUserMention(String listDescription) {
        if (mUserPostObj.isHasMention()) {
            List<MentionSpan> mentionSpanList = mUserPostObj.getUserMentionList();
            if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
                showUserMentionName(listDescription, mentionSpanList, false);
            } else {
                setTextViewHTML(tvFeedCommunityPostText, listDescription);
            }
        } else {
            setTextViewHTML(tvFeedCommunityPostText, listDescription);
        }
        linkifyURLs(tvFeedCommunityPostText);
    }

    public void setTextViewHTML(TextView text, String html) {
        CharSequence sequence = Html.fromHtml(html);
        text.setText(sequence);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void collapseFeedPostText() {
        mUserPostObj.isTextExpanded = false;
        tvFeedCommunityPostText.setMaxLines(4);
        tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
        tvFeedCommunityPostViewLess.setVisibility(View.GONE);
    }

    private void expandFeedPostText() {
        mUserPostObj.isTextExpanded = true;
        tvFeedCommunityPostText.setMaxLines(Integer.MAX_VALUE);
        tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        tvFeedCommunityPostViewMore.setVisibility(View.GONE);
        tvFeedCommunityPostViewLess.setVisibility(View.VISIBLE);
    }

    private void userLike() {

        switch (mUserPostObj.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + mUserPostObj.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<Comment> lastCommentList = mUserPostObj.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            ivFeedCommunityPostUserPic.setCircularImage(true);
            invalidateCommentLike(lastComment);
            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    String authorThumborUrl = CommonUtil.getThumborUri(lastComment.getParticipantImageUrl(), authorPicSize, authorPicSize);
                    ivFeedCommunityPostUserPic.bindImage(authorThumborUrl);
                    tvFeedCommunityPostUserName.setText(lastComment.getParticipantName());
                    tvFeedCommunityPostUserName.setTextColor(ContextCompat.getColor(mContext, R.color.comment_text));
                    if (lastComment.isHasCommentMention()) {
                        List<MentionSpan> mentionSpanList = lastComment.getCommentUserMentionList();
                        if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
                            showUserMentionName(lastComment.getComment(), mentionSpanList, true);
                        } else {
                            tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(lastComment.getComment()));
                        }
                    } else {
                        tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(lastComment.getComment()));
                    }
                    ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                    invalidateCommentLike(lastComment);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getComment()) && StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    String authorThumborUrl = CommonUtil.getThumborUri(lastComment.getParticipantImageUrl(), authorPicSize, authorPicSize);
                    ivFeedCommunityPostUserPic.bindImage(authorThumborUrl);
                    tvFeedCommunityPostUserName.setText(lastComment.getParticipantName());
                    tvFeedCommunityPostUserName.setTextColor(ContextCompat.getColor(mContext, R.color.feed_title));

                    if (lastComment.isHasCommentMention()) {
                        List<MentionSpan> mentionSpanList = lastComment.getCommentUserMentionList();
                        if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
                            showUserMentionName(lastComment.getComment(), mentionSpanList, true);
                        } else {
                            tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(lastComment.getComment()));
                        }
                    } else {
                        tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(lastComment.getComment()));
                    }

                    if (!lastComment.getParticipantName().equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        if (lastComment.isVerifiedMentor()) {
                            ivFeedCommunityPostUserIconVerified.setVisibility(View.VISIBLE);
                        } else {
                            ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                        }
                    } else {
                        ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                    }

                }
            }
            CommonUtil.showHideUserBadge(mContext, lastComment.isAnonymous(), lastCommentUserBadge, lastComment.isBadgeShown(), lastComment.getBadgeUrl());

            linkifyURLs(tvFeedCommunityPostUserCommentPost);
            if (tvFeedCommunityPostUserCommentPost.getLineCount() > 3) {
                tvFeedCommunityPostUserCommentPostViewMore.setVisibility(View.VISIBLE);
                String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
            } else {
                tvFeedCommunityPostUserCommentPostViewMore.setVisibility(View.GONE);
            }


            if (StringUtil.isNotNullOrEmptyString(lastComment.getPostedDate())) {
                long createdDate = mDateUtil.getTimeInMillis(lastComment.getLastModifiedOn(), AppConstants.DATE_FORMAT);
                tvFeedCommunityPostUserCommentPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate, mContext));
            } else {
                tvFeedCommunityPostUserCommentPostTime.setText(mContext.getString(R.string.ID_JUST_NOW));
            }
            if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                if (null != userPreference.get().getUserSummary().getUserBO()) {
                    mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
                }
            }

            if (viewInterface instanceof FeedItemCallback) {
                tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.VISIBLE);
            } else {
                tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.GONE);
            }
        } else {
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
        }

    }

    private void invalidateCommentLike(Comment lastComment) {
        if (lastComment.likeCount > 0) {
            mCommentLikeCount.setVisibility(View.VISIBLE);
            mCommentLikeCount.setText(Integer.toString(lastComment.likeCount));
        } else {
            mCommentLikeCount.setVisibility(View.GONE);
        }
        if (lastComment.isLiked) {
            mCommentLike.setTextColor(mContext.getResources().getColor(R.color.toolbar_title_text));
        } else {
            mCommentLike.setTextColor(mContext.getResources().getColor(R.color.posted_in));
        }
    }

    private void imageOperations(Context context) {
        String authorImageUrl = mUserPostObj.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            ivFeedCommunityPostCircleIcon.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(authorImageUrl, authorPicSizeFourty, authorPicSizeFourty);
            ivFeedCommunityPostCircleIcon.bindImage(authorThumborUrl);
        }
        ivFeedCommunityPostLoginUserPic.setCircularImage(true);
        String authorThumborUrl = CommonUtil.getThumborUri(mPhotoUrl, authorPicSizeFourty, authorPicSizeFourty);
        ivFeedCommunityPostLoginUserPic.bindImage(authorThumborUrl); //todo - chk it here
        if (StringUtil.isNotNullOrEmptyString(loggedInUser)) {
            tvFeedCommunityPostLoginUserName.setVisibility(View.VISIBLE);
            tvFeedCommunityPostLoginUserName.setText(loggedInUser);
        } else {
            tvFeedCommunityPostLoginUserName.setVisibility(View.GONE);
        }

    }

    private void feedAlbum(Context context, String firstImage, String secondImage, String thirdImage, int typeOfHolder) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = LayoutInflater.from(mContext).inflate(R.layout.feed_community_post_feed_album, null);

        final LinearLayout liFeedAlbum = child.findViewById(R.id.li_feed_album);
        double imageRatio;
        if (CommonUtil.isEmpty(mUserPostObj.getImageRatio())) {
            imageRatio = AppConstants.MAX_IMAGE_RATIO;
        } else {
            imageRatio = mUserPostObj.getImageRatio().get(0);
        }
        if (imageRatio > AppConstants.MAX_IMAGE_RATIO) {
            imageRatio = AppConstants.MAX_IMAGE_RATIO;
        }
        int imageHeight = 0;
        if (CommonUtil.isNotEmpty(secondImage)) {
            imageHeight = (int) (((double) 2 / (double) 3) * CommonUtil.getWindowWidth(mContext));
        } else {
            imageHeight = (int) (imageRatio * CommonUtil.getWindowWidth(mContext));
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, imageHeight);
        liFeedAlbum.setLayoutParams(params);

        final LinearLayout liHolder = child.findViewById(R.id.li_holder);

        final ImageView ivFirst = child.findViewById(R.id.iv_first);

        final ImageView ivSecond = child.findViewById(R.id.iv_second);

        final ImageView ivThird = child.findViewById(R.id.iv_third);
        final TextView tvMoreImage = child.findViewById(R.id.tv_feed_community_more_image);
        tvMoreImage.setVisibility(View.GONE);
        switch (typeOfHolder) {
            case AppConstants.ONE_CONSTANT:
                LinearLayout.LayoutParams liHolderLayout = (LinearLayout.LayoutParams) liHolder.getLayoutParams();
                liHolderLayout.weight = 0;
                break;
            case AppConstants.TWO_CONSTANT:
                LinearLayout.LayoutParams liHolderLinear = (LinearLayout.LayoutParams) liHolder.getLayoutParams();
                liHolderLinear.setMargins(5, 0, 0, 0);
                LinearLayout.LayoutParams firstImageLayout = (LinearLayout.LayoutParams) ivFirst.getLayoutParams();
                firstImageLayout.weight = 1;
                LinearLayout.LayoutParams secondImageLayout = (LinearLayout.LayoutParams) ivSecond.getLayoutParams();
                secondImageLayout.weight = 0;
                break;
            case AppConstants.THREE_CONSTANT:
                LinearLayout.LayoutParams liHolderLayoutDefault = (LinearLayout.LayoutParams) liHolder.getLayoutParams();
                liHolderLayoutDefault.weight = 1;
                liHolderLayoutDefault.setMargins(5, 0, 0, 0);
                LinearLayout.LayoutParams firstImageLayoutDefault = (LinearLayout.LayoutParams) ivFirst.getLayoutParams();
                firstImageLayoutDefault.weight = 2;
                LinearLayout.LayoutParams secondImageLayoutDefault = (LinearLayout.LayoutParams) ivSecond.getLayoutParams();
                secondImageLayoutDefault.weight = 1;
                break;
            default:
                int count = typeOfHolder - 2;
                tvMoreImage.setText(String.valueOf("+" + count));
                tvMoreImage.setVisibility(View.VISIBLE);
        }


        ivFirst.setOnClickListener(this);

        if (StringUtil.isNotNullOrEmptyString(firstImage)) {
            String firstThumborUrl = firstImage;
            if (typeOfHolder == 1) {
                firstThumborUrl = CommonUtil.getThumborUri(firstImage, CommonUtil.getWindowWidth(context), imageHeight);
            } else {
                firstThumborUrl = CommonUtil.getThumborUri(firstImage, CommonUtil.getWindowWidth(context) / 2, imageHeight);
            }
            Glide.with(context)
                    .load(firstThumborUrl)
                    .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                    .into(ivFirst);
        }

        if (StringUtil.isNotNullOrEmptyString(secondImage)) {
            ivSecond.setOnClickListener(this);
            String secondThumborUrl = "";
            if (typeOfHolder == 2) {
                secondThumborUrl = CommonUtil.getThumborUri(secondImage, CommonUtil.getWindowWidth(context), imageHeight);
            } else {
                secondThumborUrl = CommonUtil.getThumborUri(secondImage, CommonUtil.getWindowWidth(context), imageHeight / 2);
            }
            Glide.with(context)
                    .load(secondThumborUrl)
                    .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                    .into(ivSecond);
        }
        if (StringUtil.isNotNullOrEmptyString(thirdImage)) {
            ivThird.setOnClickListener(this);
            String thirdThumborUrl = CommonUtil.getThumborUri(thirdImage, CommonUtil.getWindowWidth(context), imageHeight / 2);
            Glide.with(context)
                    .load(thirdThumborUrl)
                    .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                    .into(ivThird);
        }
        liFeedCommunityUserPostImages.addView(child);
    }

    private void userReactionWithouLongPress() {
        tvFeedCommunityPostUserReaction.setTag(false);
        mUserPostObj.setLongPress(false);
        if (mUserPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            mUserPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
        } else {
            mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
        }
        likeCommentOps();
    }

    //spam post
    private void handlingSpamUi(long userId, int adminId) {
        tvSpamPostMenu.setVisibility(View.GONE);

        if (adminId == AppConstants.TWO_CONSTANT || mUserPostObj.isCommunityOwner()) {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.VISIBLE);
            spamMessageContainer.setVisibility(View.GONE);
            tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
        } else if (mUserPostObj.getAuthorId() == userId) {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.spam_post));
            liCommunityPostMainLayout.setAlpha(.2f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.GONE);
            spamMessageContainer.setVisibility(View.VISIBLE);
        } else {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
            liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
            liApproveDelete.setVisibility(View.GONE);
            spamMessageContainer.setVisibility(View.VISIBLE);
        }

    }

    private void showUserMentionName(String description, List<MentionSpan> mentionSpanList, boolean isComment) {
        StringBuilder strWithAddExtra = new StringBuilder(description);
        for (int i = 0; i < mentionSpanList.size(); i++) {
            final MentionSpan mentionSpan = mentionSpanList.get(i);
            if (null != mentionSpan && null != mentionSpan.getMention()) {
                if (mentionSpan.getMention().getStartIndex() >= 0 && mentionSpan.getMention().getStartIndex() + i < strWithAddExtra.length()) {
                    strWithAddExtra.insert(mentionSpan.getMention().getStartIndex() + i, '@');
                }
            }
        }
        SpannableString spannableString = new SpannableString(strWithAddExtra);
        for (int i = 0; i < mentionSpanList.size(); i++) {
            final MentionSpan mentionSpan = mentionSpanList.get(i);
            if (null != mentionSpan && null != mentionSpan.getMention()) {
                final ClickableSpan postedInClick = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        UserPostSolrObj userPostSolrObj = mUserPostObj;
                        userPostSolrObj.setCreatedBy(mentionSpan.getMention().getUserId());
                        if (mentionSpan.getMention().getUserType() == AppConstants.MENTOR_USER_TYPE_FOR_TAGGING) {
                            userPostSolrObj.setAuthorMentor(true);
                        } else {
                            userPostSolrObj.setAuthorMentor(false);
                        }
                        if (viewInterface instanceof FeedItemCallback) {
                            ((FeedItemCallback) viewInterface).onChampionProfileClicked(userPostSolrObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                        } else {
                            viewInterface.navigateToProfileView(userPostSolrObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                        }
                    }

                    @Override
                    public void updateDrawState(final TextPaint textPaint) {
                        textPaint.setUnderlineText(false);
                    }
                };

                if (mentionSpan.getMention().getStartIndex() >= 0 && mentionSpan.getMention().getEndIndex() > 0 && mentionSpan.getMention().getEndIndex() + i + 1 <= spannableString.length() && mentionSpan.getMention().getStartIndex() + i <= spannableString.length()) {
                    int start = mentionSpan.getMention().getStartIndex() + i;
                    int end = mentionSpan.getMention().getEndIndex() + i;
                    spannableString.setSpan(postedInClick, start, end + 1, 0);
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.user_tagg)), start, end + 1, 0);
                }
            }
        }
        if (isComment) {
            tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(spannableString), TextView.BufferType.SPANNABLE);
        } else {
            tvFeedCommunityPostText.setMovementMethod(LinkMovementMethod.getInstance());
            tvFeedCommunityPostText.setText(hashTagColorInString(spannableString), TextView.BufferType.SPANNABLE);
        }
    }
    // endregion
}