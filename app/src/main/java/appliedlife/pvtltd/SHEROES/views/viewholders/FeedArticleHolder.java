package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.f2prateek.rx.preferences2.Preference;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ArticleTextView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;

/**
 * Created by Praveen_Singh on 23-01-2017.
 */

public class FeedArticleHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeedArticleHolder.class);
    private static final String LEFT_HTML_TAG = "<font color='#3c3c3c'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    Preference<AppConfiguration> mConfiguration;
    @Inject
    DateUtil mDateUtil;
    @Bind(R.id.li_feed_article_images)
    LinearLayout liFeedArticleImages;
    @Bind(R.id.li_feed_article_user_comments)
    FrameLayout liFeedArticleUserComments;
    @Bind(R.id.spam_comment_container)
    FrameLayout spamContainer;
    @Bind(R.id.last_comment_container)
    RelativeLayout lastCommentContainer;
    @Bind(R.id.iv_feed_article_card_circle_icon)
    CircleImageView ivFeedArticleCircleIcon;
    @Bind(R.id.iv_feed_article_user_pic)
    CircleImageView ivFeedArticleUserPic;
    @Bind(R.id.tv_feed_article_user_share)
    TextView tvFeedArticleUserShare;
    @Bind(R.id.tv_feed_article_user_bookmark)
    TextView tvFeedArticleUserBookmark;
    @Bind(R.id.tv_feed_article_card_title)
    TextView tvFeedArticleCardTitle;
    @Bind(R.id.tv_feed_article_title_label)
    TextView tvFeedArticleTitleLabel;
    @Bind(R.id.tv_feed_article_header)
    TextView tvFeedArticleHeader;
    @Bind(R.id.tv_feed_article_header_lebel)
    TextView tvFeedArticleHeaderLebel;
    @Bind(R.id.tv_feed_article_user_comment_post)
    TextView tvFeedArticleUserCommentPost;
    @Bind(R.id.tv_feed_article_total_replies)
    TextView tvFeedArticleTotalReplies;
    @Bind(R.id.tv_feed_article_reaction1)
    TextView tvFeedArticleReaction1;
    @Bind(R.id.tv_feed_article_total_reactions)
    TextView tvFeedArticleTotalReactions;
    @Bind(R.id.tv_feed_article_user_menu)
    TextView tvFeedArticleUserMenu;
    @Bind(R.id.spam_article_comment_menu)
    TextView spamCommentMenu;
    @Bind(R.id.iv_feed_article_card_circle_icon_verified)
    ImageView ivFeedArticleCardCircleIconVerified;
    @Bind(R.id.tv_feed_article_user_reaction)
    TextView tvFeedArticleUserReaction;
    @Bind(R.id.tv_feed_article_user_comment)
    TextView tvFeedArticleUserComment;
    @Bind(R.id.tv_article_join_conversation)
    TextView tvArticleJoinConversation;
    @Bind(R.id.line_for_no_image)
    View lineForNoImage;
    @Bind(R.id.rl_feed_article_no_reaction_comments)
    RelativeLayout rlFeedArticleNoReactionComment;
    @Bind(R.id.tv_feed_article_user_comment_post_menu)
    TextView tvFeedArticleUserCommentPostMenu;
    @Bind(R.id.tv_feed_article_user_comment_post_view_more)
    TextView tvFeedArticleUserCommentPostViewMore;
    @Bind(R.id.tv_feed_article_user_name)
    TextView tvFeedArticleUserName;
    @Bind(R.id.iv_feed_article_user_verified)
    ImageView ivFeedArticleUserVerified;
    @Bind(R.id.user_badge)
    ImageView mLastCommentUserBadgePic;
    @Bind(R.id.tv_feed_article_comment_post_time)
    TextView tvFeedArticleCommentPostTime;
    @Bind(R.id.iv_feed_article_login_user_pic)
    CircleImageView ivFeedArticleLoginUserPic;
    @Bind(R.id.tv_feed_article_login_user_name)
    TextView tvFeedArticleLoginUserName;
    @Bind(R.id.ripple_feed_article_comment)
    RippleView rippleView;

    @Bind(R.id.tv_feed_article_tag)
    TextView tvFeedArticleTag;

    @Bind(R.id.tv_feed_article_draft)
    TextView tvFeedArticleDraft;

    @Bind(R.id.rl_comment_reaction)
    RelativeLayout rlCommentReaction;

    @Bind(R.id.rl_feed_article_join_conversation)
    RelativeLayout rlFeedArticleJoinConversation;


    @BindDimen(R.dimen.dp_size_40)
    int profileSize;

    @BindDimen(R.dimen.dp_size_30)
    int profileSizeSmaller;
    BaseHolderInterface viewInterface;
    ArticleSolrObj articleObj;
    private Context mContext;
    private String mViewMoreDescription;
    private int mItemPosition;
    private long mUserId;
    private String mPhotoUrl;
    private String loggedInUser;
    private Handler mHandler;
    private boolean isWhatappShareOption = false;
    Bitmap bitmap;
    InputStream is;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    public FeedArticleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get().getUserSummary()) {
            mUserId = userPreference.get().getUserSummary().getUserId();
            if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
                mPhotoUrl = userPreference.get().getUserSummary().getPhotoUrl();
            }
            String first = userPreference.get().getUserSummary().getFirstName();
            String last = userPreference.get().getUserSummary().getLastName();
            if (StringUtil.isNotNullOrEmptyString(first) || StringUtil.isNotNullOrEmptyString(last)) {
                loggedInUser = first + AppConstants.SPACE + last;
            }
        }
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareOption = "";
            shareOption = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareOption)) {
                if (shareOption.equalsIgnoreCase("true")) {
                    isWhatappShareOption = true;
                }
            }
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        articleObj = new ArticleSolrObj();
        if (item instanceof ArticleSolrObj) {
            articleObj = (ArticleSolrObj) item;
        }
        tvArticleJoinConversation.setText(R.string.type_your_comment);
        articleObj.setItemPosition(position);
        this.mContext = context;
        tvFeedArticleUserBookmark.setEnabled(true);
        tvFeedArticleUserReaction.setTag(true);
        articleObj.setItemPosition(position);
        articleObj.setLastReactionValue(articleObj.getReactionValue());
        onBookMarkClick();
        if (!articleObj.isTrending()) {
            imageOperations(context);
        }

        UpdateUserStoryVisibility();
        allTextViewStringOperations(context);

        if (StringUtil.isNotEmptyCollection(articleObj.getTags())) {
            tvFeedArticleTag.setVisibility(View.VISIBLE);
            List<String> tags = articleObj.getTags();
            StringBuilder mergeTags = new StringBuilder(AppConstants.EMPTY_STRING);
            for (String tag : tags) {
                mergeTags.append(tag).append(AppConstants.COMMA).append(AppConstants.SPACE);
            }
            mergeTags = new StringBuilder(mergeTags.substring(0, mergeTags.length() - 2));
            String tagHeader = LEFT_HTML_TAG + mContext.getString(R.string.ID_TAGS) + AppConstants.COLON + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleTag.setText(Html.fromHtml(tagHeader + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvFeedArticleTag.setText(Html.fromHtml(tagHeader + AppConstants.SPACE + mergeTags));// or for older api
            }
        } else {
            tvFeedArticleTag.setVisibility(View.GONE);
        }
    }

    private void UpdateUserStoryVisibility() {
        if (StringUtil.isNotNullOrEmptyString(articleObj.getUserStoryStatus())) {
            if (articleObj.getUserStoryStatus().equalsIgnoreCase(ArticleStatusEnum.DRAFT.toString())) {
                tvFeedArticleUserBookmark.setVisibility(View.GONE);
                tvFeedArticleDraft.setVisibility(View.VISIBLE);
                rlCommentReaction.setVisibility(View.GONE);
                lastCommentContainer.setVisibility(View.GONE);
                lineForNoImage.setVisibility(View.GONE);
                rlFeedArticleJoinConversation.setVisibility(View.GONE);
            } else {
                tvFeedArticleUserBookmark.setVisibility(View.VISIBLE);
                tvFeedArticleDraft.setVisibility(View.GONE);
                rlCommentReaction.setVisibility(View.VISIBLE);
                lastCommentContainer.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.VISIBLE);
                rlFeedArticleJoinConversation.setVisibility(View.VISIBLE);
            }
        } else {
            tvFeedArticleUserBookmark.setVisibility(View.VISIBLE);
            tvFeedArticleDraft.setVisibility(View.GONE);
            rlCommentReaction.setVisibility(View.VISIBLE);
            lastCommentContainer.setVisibility(View.VISIBLE);
            lineForNoImage.setVisibility(View.VISIBLE);
            rlFeedArticleJoinConversation.setVisibility(View.VISIBLE);
        }
    }

    private void onBookMarkClick() {
        if (articleObj.isBookmarked()) {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vector_bookmark_active, 0);
        } else {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vector_bookmark_in_active, 0);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (articleObj.isAuthorMentor()) {
            ivFeedArticleCardCircleIconVerified.setVisibility(View.VISIBLE);
        } else {
            ivFeedArticleCardCircleIconVerified.setVisibility(View.GONE);
        }
        if (isWhatappShareOption) {
            tvFeedArticleUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.vector_share_card), null, null, null);
            tvFeedArticleUserShare.setText(mContext.getString(R.string.ID_SHARE_ON_WHATS_APP));
            tvFeedArticleUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.share_color));

        } else {
            tvFeedArticleUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.vector_share_white_out), null, null, null);
            tvFeedArticleUserShare.setText(mContext.getString(R.string.ID_SHARE));
            tvFeedArticleUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.recent_post_comment));

        }
        mViewMoreDescription = articleObj.getShortDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            tvFeedArticleHeaderLebel.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
            } else {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(mViewMoreDescription));// or for older api
            }
            ArticleTextView.doResizeTextView(tvFeedArticleHeaderLebel, 4, AppConstants.VIEW_MORE_TEXT, true);
        } else {
            tvFeedArticleHeaderLebel.setVisibility(View.GONE);
        }


        if (StringUtil.isNotNullOrEmptyString(articleObj.getAuthorName())) {
            tvFeedArticleCardTitle.setText(articleObj.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(articleObj.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(articleObj.getCreatedDate(), AppConstants.DATE_FORMAT);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate,mContext));
            if (articleObj.getCharCount() > 0) {
                stringBuilder.append(AppConstants.DOT).append(articleObj.getCharCount()).append(AppConstants.SPACE).append(context.getString(R.string.ID_MIN_READ));
            }
            tvFeedArticleTitleLabel.setText(stringBuilder);
        }
        if (StringUtil.isNotNullOrEmptyString(articleObj.getNameOrTitle())) {
            tvFeedArticleHeader.setText(articleObj.getNameOrTitle());
        }
        if (articleObj.getNoOfLikes() < AppConstants.ONE_CONSTANT && articleObj.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
            rlFeedArticleNoReactionComment.setVisibility(View.GONE);
            lineForNoImage.setVisibility(View.GONE);
        }
        tvFeedArticleReaction1.setVisibility(View.VISIBLE);
        switch (articleObj.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (articleObj.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                    lineForNoImage.setVisibility(View.GONE);
                    tvFeedArticleTotalReactions.setVisibility(View.GONE);
                    tvFeedArticleReaction1.setVisibility(View.INVISIBLE);

                    tvFeedArticleTotalReplies.setVisibility(View.VISIBLE);
                } else {
                    rlFeedArticleNoReactionComment.setVisibility(View.GONE);
                    lineForNoImage.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                tvFeedArticleTotalReactions.setVisibility(View.VISIBLE);
                rlFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.VISIBLE);
                userLike();
                break;
            default:
                tvFeedArticleTotalReactions.setVisibility(View.VISIBLE);
                rlFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.VISIBLE);
                userLike();
        }
        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, articleObj.getNoOfLikes());
        tvFeedArticleTotalReactions.setText(String.valueOf(articleObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));
        switch (articleObj.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (articleObj.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                    lineForNoImage.setVisibility(View.VISIBLE);
                    tvFeedArticleTotalReactions.setVisibility(View.VISIBLE);
                    tvFeedArticleReaction1.setVisibility(View.VISIBLE);
                    tvFeedArticleTotalReplies.setVisibility(View.INVISIBLE);
                } else {
                    rlFeedArticleNoReactionComment.setVisibility(View.GONE);
                    lineForNoImage.setVisibility(View.GONE);
                }
                userComments();
                break;
            case AppConstants.ONE_CONSTANT:
                tvFeedArticleTotalReplies.setVisibility(View.VISIBLE);
                liFeedArticleUserComments.setVisibility(View.VISIBLE);
                userComments();
                break;
            default:
                tvFeedArticleTotalReplies.setVisibility(View.VISIBLE);
                liFeedArticleUserComments.setVisibility(View.VISIBLE);
                userComments();
        }
        String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, articleObj.getNoOfComments());
        tvFeedArticleTotalReplies.setText(String.valueOf(articleObj.getNoOfComments() + AppConstants.SPACE + pluralComments));
    }


    private void userLike() {

        switch (articleObj.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);

                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);

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
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + articleObj.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<Comment> lastCommentList = articleObj.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            ivFeedArticleUserPic.setCircularImage(true);

            //tvFeedArticleUserCommentPostMenu.setVisibility(View.VISIBLE);

            if (lastComment.isSpamComment()) {
                spamCommentMenu.setVisibility(View.GONE);
                spamContainer.setVisibility(View.VISIBLE);
                lastCommentContainer.setVisibility(View.GONE);
            } else {
                spamCommentMenu.setVisibility(View.VISIBLE);
                spamContainer.setVisibility(View.GONE);
                lastCommentContainer.setVisibility(View.VISIBLE);
            }

            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    ivFeedArticleUserPic.setImageResource(R.drawable.vector_anonymous);
                    tvFeedArticleUserName.setText(lastComment.getParticipantName());
                    tvFeedArticleUserCommentPost.setText(lastComment.getComment());
                    ivFeedArticleUserVerified.setVisibility(View.GONE);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getComment()) && StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    String authorThumborUrl = CommonUtil.getThumborUri(lastComment.getParticipantImageUrl(), profileSizeSmaller, profileSizeSmaller);
                    ivFeedArticleUserPic.bindImage(lastComment.getParticipantImageUrl());
                    tvFeedArticleUserName.setText(lastComment.getParticipantName());
                    tvFeedArticleUserCommentPost.setText(lastComment.getComment());
                    if (!lastComment.getParticipantName().equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        if (lastComment.isVerifiedMentor()) {
                            ivFeedArticleUserVerified.setVisibility(View.VISIBLE);
                        } else {
                            ivFeedArticleUserVerified.setVisibility(View.GONE);
                        }
                    } else {
                        ivFeedArticleUserVerified.setVisibility(View.GONE);
                    }
                }
            }
            CommonUtil.showHideUserBadge(mContext, lastComment.isAnonymous(), mLastCommentUserBadgePic, lastComment.isBadgeShown(), lastComment.getBadgeUrl());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (tvFeedArticleUserCommentPost.getLineCount() > 3) {
                        ArticleTextView.doResizeTextView(tvFeedArticleUserCommentPost, 4, AppConstants.VIEW_MORE_TEXT, true);
                    }
                }
            });
            if (tvFeedArticleUserCommentPost.getLineCount() > 3) {
                tvFeedArticleUserCommentPostViewMore.setVisibility(View.VISIBLE);
                String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedArticleUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeedArticleUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
            } else {
                tvFeedArticleUserCommentPostViewMore.setVisibility(View.GONE);
            }
            if (StringUtil.isNotNullOrEmptyString(lastComment.getLastModifiedOn())) {
                long createdDate = mDateUtil.getTimeInMillis(lastComment.getLastModifiedOn(), AppConstants.DATE_FORMAT);
                tvFeedArticleCommentPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate,mContext));
            }
           /* if (lastComment.isMyOwnParticipation()) {
                tvFeedArticleUserCommentPostMenu.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleUserCommentPostMenu.setVisibility(View.GONE);
            }*/

        } else {
            liFeedArticleUserComments.setVisibility(View.GONE);
            tvFeedArticleTotalReplies.setVisibility(View.GONE);
        }
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = articleObj.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivFeedArticleCircleIcon.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(feedCircleIconUrl, profileSize, profileSize);
            ivFeedArticleCircleIcon.bindImage(authorThumborUrl);
        }
        ivFeedArticleLoginUserPic.setCircularImage(true);
        String authorThumborUrl = CommonUtil.getThumborUri(mPhotoUrl, profileSize, profileSize);
        ivFeedArticleLoginUserPic.bindImage(authorThumborUrl);
        if (StringUtil.isNotNullOrEmptyString(loggedInUser)) {
            tvFeedArticleLoginUserName.setVisibility(View.VISIBLE);
            tvFeedArticleLoginUserName.setText(loggedInUser);
        } else {
            tvFeedArticleLoginUserName.setVisibility(View.GONE);
        }

        String backgrndImageUrl = articleObj.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(backgrndImageUrl)) {
            liFeedArticleImages.setVisibility(View.VISIBLE);
            liFeedArticleImages.removeAllViews();
            liFeedArticleImages.removeAllViewsInLayout();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = backgroundImage.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvFeedArticleTotalViews = backgroundImage.findViewById(R.id.tv_feed_article_total_views);
            final RelativeLayout rlFeedArticleViews = backgroundImage.findViewById(R.id.rl_gradiant);
            if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null) {
                if (mConfiguration.get().configData.showArticleViews) {
                    tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
                    rlFeedArticleViews.setVisibility(View.VISIBLE);
                    StringBuilder stringBuilder = new StringBuilder();
                    if (articleObj.getNoOfViews() > 1) {
                        stringBuilder.append(changeNumberToNumericSuffix(articleObj.getNoOfViews())).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEWS));
                        tvFeedArticleTotalViews.setText(stringBuilder.toString());
                        tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
                        rlFeedArticleViews.setVisibility(View.VISIBLE);
                    } else if (articleObj.getNoOfViews() == 1) {
                        stringBuilder.append(articleObj.getNoOfViews()).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEW));
                        tvFeedArticleTotalViews.setText(stringBuilder.toString());
                        tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
                        rlFeedArticleViews.setVisibility(View.VISIBLE);
                    } else {
                        tvFeedArticleTotalViews.setVisibility(View.GONE);
                        rlFeedArticleViews.setVisibility(View.GONE);
                    }
                } else {
                    tvFeedArticleTotalViews.setVisibility(View.GONE);
                    rlFeedArticleViews.setVisibility(View.GONE);
                }
            } else {
                tvFeedArticleTotalViews.setVisibility(View.GONE);
                rlFeedArticleViews.setVisibility(View.GONE);
            }

            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.photo_placeholder)
                    .error(R.color.photo_placeholder)
                    .priority(Priority.HIGH);

            int imageWidth = CommonUtil.getWindowWidth(context);
            int imageHeight = CommonUtil.getWindowWidth(context) / 2;
            String thumborFeatureImageUrl = CommonUtil.getThumborUri(backgrndImageUrl, imageWidth, imageHeight);
            Glide.with(mContext)
                    .asBitmap()
                    .load(thumborFeatureImageUrl)
                    .apply(requestOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                        }
                    });
            liFeedArticleImages.addView(backgroundImage);
        } else {
            liFeedArticleImages.setVisibility(View.GONE);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_feed_article_user_menu)
    public void menuItemClick() {
        if (viewInterface instanceof FeedItemCallback) {
            if (StringUtil.isNotNullOrEmptyString(articleObj.getUserStoryStatus())) {
                ((FeedItemCallback) viewInterface).onHerStoryPostMenuClicked(articleObj, tvFeedArticleUserMenu);
            } else {
                ((FeedItemCallback) viewInterface).onPostMenuClicked(articleObj, tvFeedArticleUserMenu);
            }
        } else {
            viewInterface.handleOnClick(articleObj, tvFeedArticleUserMenu);
        }
    }

    @OnClick({R.id.spam_article_comment_menu})
    public void spamMenuCommentItemClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostMenuClicked(articleObj, spamCommentMenu);
        } else {
            viewInterface.handleOnClick(articleObj, spamCommentMenu);
        }
    }

    @OnClick(R.id.tv_feed_article_user_comment_post_menu)
    public void commentItemClick() {
        /*articleObj .setItemPosition(mItemPosition);
        viewInterface.handleOnClick(articleObj , tvFeedArticleUserCommentPostMenu);*/
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostMenuClicked(articleObj, tvFeedArticleUserCommentPostMenu);
        }
    }

    @OnClick(R.id.tv_feed_article_total_reactions)
    public void reactionClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onLikesCountClicked(articleObj.getEntityOrParticipantId());
        } else {
            viewInterface.handleOnClick(articleObj, tvFeedArticleTotalReactions);
        }
    }

    @OnClick(R.id.tv_feed_article_reaction1)
    public void reaction1Click() {
        viewInterface.handleOnClick(articleObj, tvFeedArticleTotalReactions);
    }

    @OnClick(R.id.tv_feed_article_user_bookmark)
    public void isBookMarkClick() {
        articleObj.setTrending(true);
        if (articleObj.isBookmarked()) {
            if (viewInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) viewInterface).onArticleUnBookMarkClicked(articleObj);
            } else {
                viewInterface.handleOnClick(articleObj, tvFeedArticleUserBookmark);

            }
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            if (viewInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) viewInterface).onArticleBookMarkClicked(articleObj);
            } else {
                viewInterface.handleOnClick(articleObj, tvFeedArticleUserBookmark);

            }
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        }
        if (!articleObj.isBookmarked()) {
            articleObj.setBookmarked(true);
        } else {
            articleObj.setBookmarked(false);
        }
        onBookMarkClick();
    }

    @OnClick(R.id.tv_feed_article_user_share)
    public void tvShareClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostShared(articleObj);
        } else {
            viewInterface.handleOnClick(articleObj, tvFeedArticleUserShare);
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ARTICLE, AppConstants.EMPTY_STRING);
        }
    }

    @OnClick({R.id.li_feed_article_images, R.id.tv_feed_article_header_lebel, R.id.tv_feed_article_header})
    public void articleImageClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onArticleItemClicked(articleObj);
        } else {
            viewInterface.handleOnClick(articleObj, liFeedArticleImages);
        }
    }

    @OnClick(R.id.tv_feed_article_user_reaction)
    public void userReactionClick() {
        if (viewInterface instanceof FeedItemCallback) {
            if (articleObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                articleObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                articleObj.setNoOfLikes(articleObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onArticlePostUnLiked(articleObj);
            } else {
                articleObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                articleObj.setNoOfLikes(articleObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onArticlePostLiked(articleObj);
            }
            allTextViewStringOperations(mContext);
        } else {
            if ((Boolean) tvFeedArticleUserReaction.getTag()) {
                userReactionWithOutLongPress();
            }
        }
    }

    private void userReactionWithOutLongPress() {
        tvFeedArticleUserReaction.setTag(false);
        articleObj.setTrending(true);
        articleObj.setLongPress(false);
        if (articleObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(articleObj, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.userCommentLikeRequest(articleObj, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ARTICLE, AppConstants.EMPTY_STRING);
        }
        if (articleObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            articleObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            articleObj.setNoOfLikes(articleObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
        } else {
            articleObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            articleObj.setNoOfLikes(articleObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
        }
        allTextViewStringOperations(mContext);

    }


    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.iv_feed_article_login_user_pic)
    public void onLoggedInUserIconClick() {
        viewInterface.navigateToProfileView(articleObj, AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL);
    }

    @OnClick({R.id.iv_feed_article_user_pic, R.id.tv_feed_article_user_name})
    public void onFeedArticleUserNameClick() { //Open profile from feed
        viewInterface.navigateToProfileView(articleObj, AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_FROM_ARTICLE);
    }

    @OnClick({R.id.tv_feed_article_total_replies, R.id.tv_feed_article_user_comment_post, R.id.tv_feed_article_user_comment, R.id.li_feed_article_user_comments})
    public void openCommentClick() {
        articleObj.setCallFromName(AppConstants.EMPTY_STRING);
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onArticleCommentClicked(articleObj);
        } else {
            viewInterface.handleOnClick(articleObj, tvFeedArticleUserComment);
        }


    }

    @OnClick(R.id.tv_article_join_conversation)
    public void openJoinConversationClicked() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                articleObj.setCallFromName(AppConstants.EMPTY_STRING);
                if (viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onArticleCommentClicked(articleObj);
                } else {
                    viewInterface.handleOnClick(articleObj, tvFeedArticleUserComment);
                }
            }
        });

    }

    enum ArticleStatusEnum {
        PUBLIC("Public"),
        DRAFT("Draft");


        private final String string;

        ArticleStatusEnum(final String string) {
            this.string = string;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return string;
        }
    }

}