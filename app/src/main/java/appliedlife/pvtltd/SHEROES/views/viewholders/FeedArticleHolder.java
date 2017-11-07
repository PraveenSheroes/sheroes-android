package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;


import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LastComment;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ArticleTextView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

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
    DateUtil mDateUtil;
    @Bind(R.id.li_feed_article_images)
    LinearLayout liFeedArticleImages;
    @Bind(R.id.li_feed_article_user_comments)
    LinearLayout liFeedArticleUserComments;
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
    @Bind(R.id.tv_feed_article_user_reaction)
    TextView tvFeedArticleUserReaction;
    @Bind(R.id.tv_feed_article_user_comment)
    TextView tvFeedArticleUserComment;
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
    @Bind(R.id.tv_feed_article_comment_post_time)
    TextView tvFeedArticleCommentPostTime;
    @Bind(R.id.iv_feed_article_login_user_pic)
    CircleImageView ivFeedArticleLoginUserPic;
    @Bind(R.id.tv_feed_article_login_user_name)
    TextView tvFeedArticleLoginUserName;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private Context mContext;
    private String mViewMoreDescription;
    private int mItemPosition;
    private long mUserId;
    private String mPhotoUrl;
    private String loggedInUser;
    private Handler mHandler;

    public FeedArticleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
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
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
        tvFeedArticleUserBookmark.setEnabled(true);
        tvFeedArticleUserReaction.setTag(true);
        dataItem.setItemPosition(position);
        dataItem.setLastReactionValue(dataItem.getReactionValue());
        dataItem.setListDescription(null);
        allTextViewStringOperations(context);
        onBookMarkClick();
        if (!dataItem.isTrending()) {
            imageOperations(context);
        }
            if (dataItem.getAuthorId() == mUserId || dataItem.isOwner()) {
                tvFeedArticleUserMenu.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleUserMenu.setVisibility(View.GONE);
            }

    }

    private void onBookMarkClick() {
        if (dataItem.isBookmarked()) {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        mViewMoreDescription = dataItem.getShortDescription();
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
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvFeedArticleCardTitle.setText(dataItem.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            if (dataItem.getCharCount() > 0) {
                stringBuilder.append(AppConstants.DOT).append(dataItem.getCharCount()).append(AppConstants.SPACE).append(context.getString(R.string.ID_MIN_READ));
            }
            tvFeedArticleTitleLabel.setText(stringBuilder);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvFeedArticleHeader.setText(dataItem.getNameOrTitle());
        }
        if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT && dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            rlFeedArticleNoReactionComment.setVisibility(View.GONE);
            lineForNoImage.setVisibility(View.GONE);
        }
        tvFeedArticleReaction1.setVisibility(View.VISIBLE);
        switch (dataItem.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (dataItem.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
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
        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, dataItem.getNoOfLikes());
        tvFeedArticleTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes() + AppConstants.SPACE + pluralLikes));
        switch (dataItem.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (dataItem.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
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
        String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, dataItem.getNoOfComments());
        tvFeedArticleTotalReplies.setText(String.valueOf(dataItem.getNoOfComments() + AppConstants.SPACE + pluralComments));
    }

    private void userLike() {

        switch (dataItem.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);

                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);

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
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<Comment> lastCommentList = dataItem.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            ivFeedArticleUserPic.setCircularImage(true);
            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    ivFeedArticleUserPic.setImageResource(R.drawable.ic_anonomous);
                    tvFeedArticleUserName.setText(lastComment.getParticipantName());
                    tvFeedArticleUserCommentPost.setText(lastComment.getComment());
                    ivFeedArticleUserVerified.setVisibility(View.GONE);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getComment()) && StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
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
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (tvFeedArticleUserCommentPost.getLineCount() > 3) {
                        ArticleTextView.doResizeTextView(tvFeedArticleUserCommentPost, 4, AppConstants.VIEW_MORE_TEXT, true);
                    }
                }
            });
            if(tvFeedArticleUserCommentPost.getLineCount()>3)
            {
                tvFeedArticleUserCommentPostViewMore.setVisibility(View.VISIBLE);
                String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedArticleUserCommentPostViewMore.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeedArticleUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
            }else
            {
                tvFeedArticleUserCommentPostViewMore.setVisibility(View.GONE);
            }
            if (StringUtil.isNotNullOrEmptyString(lastComment.getLastModifiedOn())) {
                long createdDate = mDateUtil.getTimeInMillis(lastComment.getLastModifiedOn(), AppConstants.DATE_FORMAT);
                tvFeedArticleCommentPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            }
            if (lastComment.isMyOwnParticipation()) {
                tvFeedArticleUserCommentPostMenu.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleUserCommentPostMenu.setVisibility(View.GONE);
            }
        } else {
            liFeedArticleUserComments.setVisibility(View.GONE);
            tvFeedArticleTotalReplies.setVisibility(View.GONE);
        }
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivFeedArticleCircleIcon.setCircularImage(true);
            ivFeedArticleCircleIcon.bindImage(feedCircleIconUrl);
        }
        ivFeedArticleLoginUserPic.setCircularImage(true);
        ivFeedArticleLoginUserPic.bindImage(mPhotoUrl);
        if (StringUtil.isNotNullOrEmptyString(loggedInUser)) {
            tvFeedArticleLoginUserName.setText(loggedInUser);
        }


        String backgrndImageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(backgrndImageUrl)) {
            liFeedArticleImages.removeAllViews();
            liFeedArticleImages.removeAllViewsInLayout();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvFeedArticleTotalViews = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_total_views);
            final RelativeLayout rlFeedArticleViews = (RelativeLayout) backgroundImage.findViewById(R.id.rl_gradiant);
            StringBuilder stringBuilder = new StringBuilder();
            if (dataItem.getNoOfViews() > 1) {
                stringBuilder.append(numericToThousand(dataItem.getNoOfViews())).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEWS));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else if (dataItem.getNoOfViews() == 1) {
                stringBuilder.append(dataItem.getNoOfViews()).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEW));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleTotalViews.setVisibility(View.GONE);
            }
            Glide.with(mContext)
                    .load(backgrndImageUrl).asBitmap()
                    .placeholder(R.color.photo_placeholder)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                            rlFeedArticleViews.setVisibility(View.VISIBLE);
                        }
                    });
            liFeedArticleImages.addView(backgroundImage);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_feed_article_user_menu)
    public void menuItemClick() {
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserMenu);
    }

    @OnClick(R.id.tv_feed_article_user_comment_post_menu)
    public void commentItemClick() {
        dataItem.setItemPosition(mItemPosition);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserCommentPostMenu);
    }

    @OnClick(R.id.tv_feed_article_total_reactions)
    public void reactionClick() {
        viewInterface.handleOnClick(dataItem, tvFeedArticleTotalReactions);
    }

    @OnClick(R.id.tv_feed_article_reaction1)
    public void reaction1Click() {
        viewInterface.handleOnClick(dataItem, tvFeedArticleTotalReactions);
    }

    @OnClick(R.id.tv_feed_article_user_bookmark)
    public void isBookMarkClick() {
        dataItem.setTrending(true);
        tvFeedArticleUserBookmark.setEnabled(false);
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedArticleUserBookmark);
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedArticleUserBookmark);
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        }
        if (!dataItem.isBookmarked()) {
            dataItem.setBookmarked(true);
        } else {
            dataItem.setBookmarked(false);
        }
        onBookMarkClick();
    }

    @OnClick(R.id.tv_feed_article_user_share)
    public void tvShareClick() {
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserShare);
        ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ARTICLE, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.li_feed_article_images)
    public void articleImageClick() {
        viewInterface.handleOnClick(dataItem, liFeedArticleImages);
    }

    @OnClick(R.id.tv_feed_article_user_reaction)
    public void userReactionClick() {
        if ((Boolean) tvFeedArticleUserReaction.getTag()) {
            userReactionWithOutLongPress();
        }
    }

    private void userReactionWithOutLongPress() {
        tvFeedArticleUserReaction.setTag(false);
        dataItem.setTrending(true);
        dataItem.setLongPress(false);
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ARTICLE, AppConstants.EMPTY_STRING);
        }
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            dataItem.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
        } else {
            dataItem.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
        }
        allTextViewStringOperations(mContext);

    }

    @OnClick(R.id.tv_feed_article_header_lebel)
    public void viewMoreClick() {
        viewInterface.handleOnClick(dataItem, liFeedArticleImages);
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.tv_feed_article_total_replies)
    public void openCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserComment);
    }

    @OnClick(R.id.tv_feed_article_user_comment_post)
    public void recentCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserComment);
    }

    @OnClick(R.id.tv_article_join_conversation)
    public void joinConversationClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserComment);
    }

    @OnClick(R.id.tv_feed_article_user_comment)
    public void articleUserCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserComment);
    }

    @OnClick(R.id.li_feed_article_user_comments)
    public void articleUserRecentCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserComment);
    }

}