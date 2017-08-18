package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Vibrator;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
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
import butterknife.OnLongClick;

/**
 * Created by Praveen_Singh on 23-01-2017.
 */

public class FeedArticleHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeedArticleHolder.class);
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    DateUtil mDateUtil;
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_VIEW_MORE = "<font color='#323840'>";
    private static final String RIGHT_VIEW_MORE = "</font>";
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    @Bind(R.id.li_feed_article_images)
    LinearLayout liFeedArticleImages;
    @Bind(R.id.li_feed_article_user_comments)
    LinearLayout liFeedArticleUserComments;
    @Bind(R.id.li_feed_article_join_conversation)
    LinearLayout liFeedArticleJoinConversation;
    @Bind(R.id.iv_feed_article_card_circle_icon)
    CircleImageView ivFeedArticleCircleIcon;
    @Bind(R.id.iv_feed_article_user_pic)
    CircleImageView ivFeedArticleUserPic;
    @Bind(R.id.iv_feed_article_register_user_pic)
    CircleImageView ivFeedArticleRegisterUserPic;
    @Bind(R.id.tv_feed_article_user_share)
    TextView tvFeedArticleUserShare;
    @Bind(R.id.tv_feed_article_user_bookmark)
    TextView tvFeedArticleUserBookmark;
    @Bind(R.id.tv_feed_article_card_title)
    TextView tvFeedArticleCardTitle;
    @Bind(R.id.tv_feed_article_title_label)
    TextView tvFeedArticleTitleLabel;
    @Bind(R.id.iv_feed_article_menu)
    ImageView ivFeedArticleMenu;
    @Bind(R.id.tv_feed_article_header)
    TextView tvFeedArticleHeader;
    @Bind(R.id.tv_feed_article_header_lebel)
    TextView tvFeedArticleHeaderLebel;
    @Bind(R.id.tv_feed_article_user_comment_post)
    TextView tvFeedArticleUserCommentPost;
    @Bind(R.id.tv_feed_article_register_user_comment)
    TextView tvFeedArticleRegisterUserComment;
    @Bind(R.id.tv_feed_article_total_replies)
    TextView tvFeedArticleTotalReplies;
    @Bind(R.id.tv_feed_article_reaction1)
    TextView tvFeedArticleReaction1;
    @Bind(R.id.tv_feed_article_reaction2)
    TextView tvFeedArticleReaction2;
    @Bind(R.id.tv_feed_article_reaction3)
    TextView tvFeedArticleReaction3;
    @Bind(R.id.tv_feed_article_total_reactions)
    TextView tvFeedArticleTotalReactions;
    @Bind(R.id.tv_feed_article_user_menu)
    TextView tvFeedArticleUserMenu;
    @Bind(R.id.tv_feed_article_user_reaction)
    TextView tvFeedArticleUserReaction;
    @Bind(R.id.tv_feed_article_user_reaction_text)
    TextView tvFeedArticleUserReactionText;
    @Bind(R.id.tv_feed_article_user_comment)
    TextView tvFeedArticleUserComment;
    @Bind(R.id.fl_feed_article_no_reaction_comments)
    FrameLayout flFeedArticleNoReactionComment;
    @Bind(R.id.tv_feed_article_user_comment_post_menu)
    TextView tvFeedArticleUserCommentPostMenu;
    // @Bind(R.id.tv_feed_article_view_more)
    // TextView tvFeedArticleView;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private Context mContext;
    private String mViewMoreDescription;
    private int mItemPosition;
    public FeedArticleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
        tvFeedArticleUserBookmark.setEnabled(true);
        tvFeedArticleUserReaction.setEnabled(true);
        tvFeedArticleUserReactionText.setEnabled(true);
        dataItem.setItemPosition(position);
        dataItem.setLastReactionValue(dataItem.getReactionValue());
        allTextViewStringOperations(context);
        onBookMarkClick();
        if (!dataItem.isTrending()) {
            imageOperations(context);
        }
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            if (dataItem.getAuthorId() == userPreference.get().getUserSummary().getUserId() || dataItem.isOwner()) {
                tvFeedArticleUserMenu.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleUserMenu.setVisibility(View.GONE);
            }
            if(dataItem != null&&StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())){
                ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(AppConstants.IMPRESSIONS,AppConstants.ARTICLE_IMPRSSION, dataItem.getIdOfEntityOrParticipant() + AppConstants.DASH +userPreference.get().getUserSummary().getUserId() + AppConstants.DASH + dataItem.getNameOrTitle() );
            }
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
        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            Document documentString = Jsoup.parse(mViewMoreDescription);
            String text = documentString.text().trim();
            tvFeedArticleHeaderLebel.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(text, 0)); // for 24 api and more
                // Spanned html = Html.fromHtml(documentString.text(),0);
                //  tvFeedArticleHeaderLebel.setText(html);
            } else {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(text));// or for older api
                //  Spanned html = Html.fromHtml(documentString.text());
                // tvFeedArticleHeaderLebel.setText(html);
            }
            //   String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
            //   StringBuilder dots=new StringBuilder();
            //   dots.append(LEFT_VIEW_MORE).append(AppConstants.DOTS).append(RIGHT_VIEW_MORE).append(mContext.getString(R.string.ID_VIEW_MORE));
            //  StringBuilder viewColor=new StringBuilder();
            //  viewColor.append(LEFT_HTML_VEIW_TAG_FOR_COLOR).append(mViewMore).append(RIGHT_HTML_VIEW_TAG_FOR_COLOR);
            // tvFeedArticleHeaderLebel.setText(mViewMoreDescription);
            ArticleTextView.doResizeTextView(tvFeedArticleHeaderLebel, 2, AppConstants.VIEW_MORE, true);
           /* if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleView.setText(Html.fromHtml(dots.toString(), 0)); // for 24 api and more
            } else {
                tvFeedArticleView.setText(Html.fromHtml(dots.toString()));// or for older api
            }*/
        } else {
            //tvFeedArticleView.setVisibility(View.GONE);
            tvFeedArticleHeaderLebel.setVisibility(View.GONE);
        }
        //TODO:: change for UI
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvFeedArticleCardTitle.setText(dataItem.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvFeedArticleTitleLabel.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvFeedArticleHeader.setText(dataItem.getNameOrTitle());
        }
        if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT && dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flFeedArticleNoReactionComment.setVisibility(View.GONE);
        }
        tvFeedArticleReaction1.setVisibility(View.VISIBLE);
        tvFeedArticleReaction2.setVisibility(View.VISIBLE);
        tvFeedArticleReaction3.setVisibility(View.VISIBLE);
        switch (dataItem.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (dataItem.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    flFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                    tvFeedArticleTotalReactions.setVisibility(View.GONE);
                    tvFeedArticleReaction1.setVisibility(View.INVISIBLE);
                    tvFeedArticleReaction2.setVisibility(View.INVISIBLE);
                    tvFeedArticleReaction3.setVisibility(View.INVISIBLE);
                    tvFeedArticleTotalReplies.setVisibility(View.VISIBLE);
                } else {
                    flFeedArticleNoReactionComment.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                tvFeedArticleTotalReactions.setVisibility(View.VISIBLE);
                flFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                tvFeedArticleTotalReactions.setText(AppConstants.ONE_CONSTANT + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                tvFeedArticleUserReactionText.setText(AppConstants.EMPTY_STRING);
                userLike();
                break;
            default:
                tvFeedArticleTotalReactions.setVisibility(View.VISIBLE);
                flFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                tvFeedArticleTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                tvFeedArticleUserReactionText.setText(AppConstants.EMPTY_STRING);
                userLike();
        }

        switch (dataItem.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (dataItem.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    flFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
                    tvFeedArticleTotalReactions.setVisibility(View.VISIBLE);
                    tvFeedArticleReaction1.setVisibility(View.VISIBLE);
                    tvFeedArticleReaction2.setVisibility(View.VISIBLE);
                    tvFeedArticleReaction3.setVisibility(View.VISIBLE);
                    tvFeedArticleTotalReplies.setVisibility(View.INVISIBLE);
                } else {
                    flFeedArticleNoReactionComment.setVisibility(View.GONE);
                }
                userComments();
                break;
            case AppConstants.ONE_CONSTANT:
                tvFeedArticleTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                tvFeedArticleTotalReplies.setVisibility(View.VISIBLE);
                liFeedArticleUserComments.setVisibility(View.VISIBLE);
                userComments();
                break;
            default:
                tvFeedArticleTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                tvFeedArticleTotalReplies.setVisibility(View.VISIBLE);
                liFeedArticleUserComments.setVisibility(View.VISIBLE);
                userComments();
        }
    }

    private void userLike() {

        switch (dataItem.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                tvFeedArticleUserReactionText.setText(AppConstants.EMPTY_STRING);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                tvFeedArticleUserReactionText.setText(mContext.getString(R.string.ID_LOVE));
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji3_whistel, 0, 0, 0);
                tvFeedArticleUserReactionText.setText(mContext.getString(R.string.ID_WISHTLE));
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji_xo_xo, 0, 0, 0);
                tvFeedArticleUserReactionText.setText(mContext.getString(R.string.ID_XOXO));
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji2_with_you, 0, 0, 0);
                tvFeedArticleUserReactionText.setText(mContext.getString(R.string.ID_LIKE));
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji4_face_palm, 0, 0, 0);
                tvFeedArticleUserReactionText.setText(mContext.getString(R.string.ID_FACE_PALM));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<LastComment> lastCommentList = dataItem.getLastComments();
        LastComment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            String feedUserIconUrl = lastComment.getParticipantImageUrl();
            ivFeedArticleUserPic.setCircularImage(true);
            // String userName;
            StringBuilder userName = new StringBuilder();
            if (lastComment.isAnonymous()) {
                //  userName = LEFT_HTML_TAG_FOR_COLOR + mContext.getString(R.string.ID_ANONYMOUS) + RIGHT_HTML_TAG_FOR_COLOR;
                userName.append(LEFT_HTML_TAG_FOR_COLOR).append(mContext.getString(R.string.ID_ANONYMOUS)).append(RIGHT_HTML_TAG_FOR_COLOR).append(AppConstants.SPACE).append(lastComment.getComment());
                ivFeedArticleUserPic.setImageResource(R.drawable.ic_anonomous);
            } else {
                //  userName = LEFT_HTML_TAG_FOR_COLOR + lastComment.getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                userName.append(LEFT_HTML_TAG_FOR_COLOR).append(lastComment.getParticipantName()).append(RIGHT_HTML_TAG_FOR_COLOR).append(AppConstants.SPACE).append(lastComment.getComment());
                ivFeedArticleUserPic.bindImage(feedUserIconUrl);
            }
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName.toString(), 0)); // for 24 api and more
            } else {
                tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName.toString()));// or for older api
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
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
            ivFeedArticleRegisterUserPic.setCircularImage(true);
            ivFeedArticleRegisterUserPic.bindImage(userPreference.get().getUserSummary().getPhotoUrl());
        }
        String backgrndImageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(backgrndImageUrl)) {
            liFeedArticleImages.removeAllViews();
            liFeedArticleImages.removeAllViewsInLayout();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvFeedArticleTimeLabel = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_time_label);
            final TextView tvFeedArticleTotalViews = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_total_views);
            final RelativeLayout rlFeedArticleViews = (RelativeLayout) backgroundImage.findViewById(R.id.rl_gradiant);
            StringBuilder stringBuilder = new StringBuilder();
            if (dataItem.getNoOfViews() > 1) {
                stringBuilder.append(dataItem.getNoOfViews()).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEWS));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else if (dataItem.getNoOfViews() == 1) {
                stringBuilder.append(dataItem.getNoOfViews()).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEW));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleTotalViews.setVisibility(View.GONE);
            }
            stringBuilder = new StringBuilder();
            if (dataItem.getCharCount() > 0) {
                stringBuilder.append(dataItem.getCharCount()).append(AppConstants.SPACE).append(context.getString(R.string.ID_MIN_READ));
                tvFeedArticleTimeLabel.setText(stringBuilder.toString());
                tvFeedArticleTimeLabel.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleTimeLabel.setVisibility(View.INVISIBLE);
            }

            Glide.with(mContext)
                    .load(backgrndImageUrl).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
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
    @OnClick(R.id.tv_feed_article_reaction2)
    public void reaction2Click() {
        viewInterface.handleOnClick(dataItem, tvFeedArticleTotalReactions);
    }
    @OnClick(R.id.tv_feed_article_reaction3)
    public void reaction3Click() {
        viewInterface.handleOnClick(dataItem, tvFeedArticleTotalReactions);
    }

    @OnClick(R.id.tv_feed_article_total_replies)
    public void repliesClick() {
        dataItem.setCallFromName(mContext.getString(R.string.ID_REPLY));
        viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
    }


    @OnClick(R.id.tv_feed_article_user_bookmark)
    public void isBookMarkClick() {
        dataItem.setTrending(true);
        tvFeedArticleUserBookmark.setEnabled(false);
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedArticleUserBookmark);
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedArticleUserBookmark);
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
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
        ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ARTICLE, AppConstants.EMPTY_STRING);
    }
    @OnClick(R.id.li_feed_article_join_conversation)
    public void joinConversationClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
    }

    @OnClick(R.id.li_feed_article_user_comments)
    public void openCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
    }

    @OnClick(R.id.tv_feed_article_user_comment)
    public void articleUserCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
    }

    @OnClick(R.id.li_feed_article_images)
    public void articleImageClick() {
        viewInterface.handleOnClick(dataItem, liFeedArticleImages);
    }

    @OnClick(R.id.tv_feed_article_user_reaction)
    public void userReactionClick() {
        userReactionWithOutLongPress();
    }

    @OnClick(R.id.tv_feed_article_user_reaction_text)
    public void userReactionByText() {
        userReactionWithOutLongPress();
    }

    private void userReactionWithOutLongPress() {
        tvFeedArticleUserReactionText.setEnabled(false);
        tvFeedArticleUserReaction.setEnabled(false);
        dataItem.setTrending(true);
        dataItem.setLongPress(false);
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ARTICLE, AppConstants.EMPTY_STRING);
        }
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            dataItem.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            tvFeedArticleUserReactionText.setText(AppConstants.EMPTY_STRING);
        } else {
            dataItem.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
            tvFeedArticleUserReactionText.setText(mContext.getString(R.string.ID_LOVE));
        }
        allTextViewStringOperations(mContext);

    }


    @OnLongClick(R.id.tv_feed_article_user_reaction)
    public boolean userReactionLongClick() {
        userReactionLongPress();
        return true;
    }

    @OnLongClick(R.id.tv_feed_article_user_reaction_text)
    public boolean userReactionLongPressByText() {
        userReactionLongPress();
        return true;
    }

    private void userReactionLongPress() {
        dataItem.setTrending(true);
        Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        dataItem.setLongPress(true);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserReaction);
        ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_CHANGED_REACTIONS, GoogleAnalyticsEventActions.CHANGED_REACTIONS_ON_ARTICLE, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.tv_feed_article_header_lebel)
    public void viewMoreClick() {
        viewInterface.handleOnClick(dataItem, liFeedArticleImages);
    }

    @Override
    public void onClick(View view) {

    }


}