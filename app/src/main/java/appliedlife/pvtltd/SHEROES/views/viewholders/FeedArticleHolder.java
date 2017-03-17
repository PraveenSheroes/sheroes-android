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

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LastComment;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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
    @Bind(R.id.li_feed_article_images)
    LinearLayout liFeedArticleImages;
    @Bind(R.id.li_feed_article_user_comments)
    LinearLayout liFeedArticleUserComments;
    @Bind(R.id.li_feed_article_join_conversation)
    LinearLayout liFeedArticleJoinConversation;
    @Bind(R.id.li_feed_article_card_emoji_pop_up)
    LinearLayout liFeedArticlCardEmojiPopUp;
    @Bind(R.id.iv_feed_article_card_circle_icon)
    CircleImageView ivFeedArticleCircleIcon;
    @Bind(R.id.iv_feed_article_user_pic)
    CircleImageView ivFeedArticleUserPic;
    @Bind(R.id.iv_feed_article_register_user_pic)
    CircleImageView ivFeedArticleRegisterUserPic;
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
    @Bind(R.id.tv_feed_article_view_more)
    TextView tvFeedArticleView;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    Context mContext;
    String mViewMoreDescription;
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
        imageOperations(context);
        tvFeedArticleUserBookmark.setEnabled(true);
        tvFeedArticleUserReaction.setEnabled(true);
        allTextViewStringOperations(context);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvFeedArticleView.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
        } else {
            tvFeedArticleView.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE) ));// or for older api
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
        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(mViewMoreDescription , 0)); // for 24 api and more
            } else {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(mViewMoreDescription ));// or for older api
            }
        }
        if (dataItem.isBookmarked()) {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }

        if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT && dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flFeedArticleNoReactionComment.setVisibility(View.GONE);
        }

        switch (dataItem.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:
                flFeedArticleNoReactionComment.setVisibility(View.GONE);
                tvFeedArticleTotalReactions.setVisibility(View.GONE);
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
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            for (LastComment lastComment : lastCommentList) {
                String feedUserIconUrl = lastComment.getParticipantImageUrl();
                ivFeedArticleUserPic.setCircularImage(true);
                if (lastComment.isAnonymous()) {
                    String userName = LEFT_HTML_TAG_FOR_COLOR + mContext.getString(R.string.ID_ANONYMOUS) + RIGHT_HTML_TAG_FOR_COLOR;
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment(), 0)); // for 24 api and more
                    } else {
                        tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment()));// or for older api
                    }
                    ivFeedArticleUserPic.setImageResource(R.drawable.ic_anonomous);
                } else {
                    String userName = LEFT_HTML_TAG_FOR_COLOR + lastComment.getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment(), 0)); // for 24 api and more
                    } else {
                        tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment()));// or for older api
                    }
                    ivFeedArticleUserPic.bindImage(feedUserIconUrl);
                }

                if (lastComment.isMyOwnParticipation()) {
                    tvFeedArticleUserCommentPostMenu.setVisibility(View.VISIBLE);
                } else {
                    tvFeedArticleUserCommentPostMenu.setVisibility(View.GONE);
                }
            }
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
            tvFeedArticleTotalViews.setText(dataItem.getNoOfViews() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            tvFeedArticleTimeLabel.setText(dataItem.getCharCount()+ AppConstants.SPACE + context.getString(R.string.ID_MIN_READ));
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
        dataItem.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserMenu);
    }

    @OnClick(R.id.tv_feed_article_user_comment_post_menu)
    public void commentItemClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserCommentPostMenu);
    }

    @OnClick(R.id.tv_feed_article_total_reactions)
    public void reactionClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedArticleTotalReactions);
    }

    @OnClick(R.id.tv_feed_article_total_replies)
    public void repliesClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
    }


    @OnClick(R.id.tv_feed_article_user_bookmark)
    public void isBookMarkClick() {
        tvFeedArticleUserBookmark.setEnabled(false);
        dataItem.setItemPosition(getAdapterPosition());
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedArticleUserBookmark);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedArticleUserBookmark);
        }
    }

    @OnClick(R.id.li_feed_article_join_conversation)
    public void joinConversationClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
    }

    @OnClick(R.id.tv_feed_article_user_comment)
    public void articleUserCommentClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
    }

    @OnClick(R.id.li_feed_article_images)
    public void articleImageClick() {
        viewInterface.handleOnClick(dataItem, liFeedArticleImages);
    }

    @OnClick(R.id.tv_feed_article_user_reaction)
    public void userReactionClick() {
        tvFeedArticleUserReaction.setEnabled(false);
        dataItem.setLongPress(false);
        dataItem.setItemPosition(getAdapterPosition());
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
        }
        if (liFeedArticlCardEmojiPopUp.getVisibility() == View.VISIBLE) {
            viewInterface.handleOnClick(dataItem, tvFeedArticleUserReaction);
        }
    }

    @OnLongClick(R.id.tv_feed_article_user_reaction)
    public boolean userReactionLongClick() {
        Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE) ;
        vibe.vibrate(100);
        dataItem.setItemPosition(getAdapterPosition());
        dataItem.setLongPress(true);
        viewInterface.handleOnClick(dataItem, tvFeedArticleUserReaction);
        return true;
    }
    @OnClick(R.id.tv_feed_article_header_lebel)
    public void viewMoreClick() {
        viewInterface.handleOnClick(dataItem, liFeedArticleImages);
    }

    @Override
    public void onClick(View view) {

    }
}