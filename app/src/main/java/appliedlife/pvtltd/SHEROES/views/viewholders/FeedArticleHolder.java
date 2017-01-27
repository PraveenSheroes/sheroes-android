package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 23-01-2017.
 */

public class FeedArticleHolder extends BaseViewHolder<ListOfFeed> implements View.OnLongClickListener {
    private final String TAG = LogUtils.makeLogTag(FeedArticleHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    @Bind(R.id.li_feed_article_images)
    LinearLayout liFeedArticleImages;
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
    @Bind(R.id.tv_feed_article_user_comment)
    TextView tvFeedArticleUserComment;
    @Bind(R.id.fl_feed_article_no_reaction_comments)
    FrameLayout flFeedArticleNoReactionComment;
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;

    public FeedArticleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        liFeedArticleJoinConversation.setOnClickListener(this);
        tvFeedArticleUserReaction.setOnLongClickListener(this);
        tvFeedArticleUserReaction.setOnClickListener(this);
        tvFeedArticleUserComment.setOnClickListener(this);
        liFeedArticleImages.removeAllViews();
        liFeedArticleImages.removeAllViewsInLayout();
        imageOperations(context);
        allTextViewStringOperations(context);

    }

    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFeedTitle())) {
            tvFeedArticleCardTitle.setText(dataItem.getFeedTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getGroupName())) {
            tvFeedArticleTitleLabel.setText(dataItem.getGroupName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFeedHeadline())) {
            tvFeedArticleHeader.setText(dataItem.getFeedHeadline());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getDescription())) {
            tvFeedArticleHeaderLebel.setText(dataItem.getDescription());
        }
        if (dataItem.getUserReaction().equalsIgnoreCase(AppConstants.HEART_REACTION)) {
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
        }
        if (dataItem.getBookmarked()) {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        }
        if (dataItem.getNoOfReactions() < 1 && dataItem.getNoOfComments() < 1) {
            flFeedArticleNoReactionComment.setVisibility(View.GONE);
        }
        switch (dataItem.getNoOfReactions()) {
            case 0:
                tvFeedArticleReaction1.setVisibility(View.GONE);
                tvFeedArticleTotalReactions.setVisibility(View.GONE);
                break;
            case 1:
                tvFeedArticleTotalReactions.setText(String.valueOf(dataItem.getNoOfReactions()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                tvFeedArticleReaction2.setVisibility(View.GONE);
                tvFeedArticleReaction3.setVisibility(View.GONE);
                break;
            default:
                tvFeedArticleTotalReactions.setText(String.valueOf(dataItem.getNoOfReactions()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                tvFeedArticleReaction3.setVisibility(View.GONE);

        }
        switch (dataItem.getNoOfComments()) {
            case 0:
                tvFeedArticleTotalReplies.setVisibility(View.GONE);
                break;
            case 1:
                tvFeedArticleTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                break;
            default:
                tvFeedArticleTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                ;
        }
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getFeedCircleIconUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {

            ivFeedArticleCircleIcon.setCircularImage(true);
            ivFeedArticleCircleIcon.bindImage(feedCircleIconUrl);
            ivFeedArticleRegisterUserPic.setCircularImage(true);
            ivFeedArticleRegisterUserPic.bindImage(feedCircleIconUrl);
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_article_single_image);
            TextView tvFeedArticleTimeLabel = (TextView) child.findViewById(R.id.tv_feed_article_time_label);
            TextView tvFeedArticleTotalViews = (TextView) child.findViewById(R.id.tv_feed_article_total_views);
            tvFeedArticleTotalViews.setText(dataItem.getNoOfComments() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            Glide.with(context)
                    .load(feedCircleIconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            liFeedArticleImages.addView(child);
        }
        if (null != dataItem.getRecentComment() && StringUtil.isNotNullOrEmptyString(dataItem.getRecentComment().getCommentDescription()) && StringUtil.isNotNullOrEmptyString(dataItem.getRecentComment().getUserName())) {
            String feedUserIconUrl = dataItem.getRecentComment().getUserProfilePicUrl();
            ivFeedArticleUserPic.setCircularImage(true);
            ivFeedArticleUserPic.bindImage(feedUserIconUrl);
            String userName = LEFT_HTML_TAG_FOR_COLOR + dataItem.getRecentComment().getUserName() + RIGHT_HTML_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + dataItem.getRecentComment().getCommentDescription(), 0)); // for 24 api and more
            } else {
                tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + dataItem.getRecentComment().getCommentDescription()));// or for older api
            }
        }
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_article_images:
                viewInterface.handleOnClick(dataItem, liFeedArticleImages);
                break;
            case R.id.tv_feed_article_user_reaction:
                if (dataItem.getUserReaction().equalsIgnoreCase(AppConstants.HEART_REACTION)) {
                    tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                    dataItem.setUserReaction(AppConstants.NO_REACTION);
                } else {
                    tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                    dataItem.setUserReaction(AppConstants.HEART_REACTION);
                }
                break;
            case R.id.li_feed_article_join_conversation:
                viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
                break;
            case R.id.tv_feed_article_user_comment:
                viewInterface.handleOnClick(dataItem, tvFeedArticleUserComment);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
    }


    @Override
    public boolean onLongClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.tv_feed_article_user_reaction:
                viewInterface.handleOnClick(dataItem, tvFeedArticleUserReaction);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
        return true;
    }
}