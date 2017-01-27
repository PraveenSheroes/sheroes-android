package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

public class FeedCommunityHolder extends BaseViewHolder<ListOfFeed> implements View.OnLongClickListener,View.OnTouchListener{
    private final String TAG = LogUtils.makeLogTag(FeedCommunityHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    @Bind(R.id.li_feed_community_images)
    LinearLayout liFeedCommunityImages;
    @Bind(R.id.li_feed_community_join_conversation)
    LinearLayout liFeedCommunityJoinConversation;
    @Bind(R.id.li_feed_community_emoji_pop_up)
    LinearLayout liFeedCommunityEmojiPopUp;
    @Bind(R.id.iv_feed_community_card_circle_icon)
    CircleImageView ivFeedCommunityCircleIcon;
    @Bind(R.id.tv_feed_community_user_reaction)
    TextView tvFeedCommunityUserReaction;
    @Bind(R.id.tv_feed_community_user_comment)
    TextView tvFeedCommunityUserComment;
    @Bind(R.id.iv_feed_community_user_pic)
    CircleImageView ivFeedCommunityUserPic;
    @Bind(R.id.iv_feed_community_register_user_pic)
    CircleImageView ivFeedCommunityRegisterUserPic;
    @Bind(R.id.tv_feed_community_user_bookmark)
    TextView tvFeedCommunityUserBookmark;
    @Bind(R.id.tv_feed_community_card_title)
    TextView tvFeedCommunityCardTitle;
    @Bind(R.id.tv_feed_community_time)
    TextView tvFeedCommunityTime;
    @Bind(R.id.iv_feed_community_menu)
    ImageView ivFeedCommunityMenu;
    @Bind(R.id.tv_feed_community_text)
    TextView tvFeedCommunityText;
    @Bind(R.id.tv_feed_community_tag_lable)
    TextView tvFeedCommunityTagLable;
    @Bind(R.id.tv_feed_community_join)
    TextView tvFeedCommunityJoin;
    @Bind(R.id.tv_feed_community_user_menu)
    TextView tvFeedCommunityUserMenu;
    @Bind(R.id.tv_feed_community_total_replies)
    TextView tvFeedCommunityTotalReplies;
    @Bind(R.id.tv_feed_community_reaction1)
    TextView tvFeedCommunityReaction1;
    @Bind(R.id.tv_feed_community_reaction2)
    TextView tvFeedCommunityReaction2;
    @Bind(R.id.tv_feed_community_reaction3)
    TextView tvFeedCommunityReaction3;
    @Bind(R.id.tv_feed_community_total_reactions)
    TextView tvFeedCommunityTotalReactions;
    @Bind(R.id.tv_feed_community_user_comment_post)
    TextView tvFeedCommunityUserCommentPost;
    @Bind(R.id.tv_feed_community_register_user_comment)
    TextView tvFeedCommunityRegisterUserComment;
    @Bind(R.id.fl_feed_community_no_reaction_comments)
    FrameLayout flFeedCommunityNoReactionComment;
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;
    public FeedCommunityHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        liFeedCommunityJoinConversation.setOnClickListener(this);
        tvFeedCommunityUserReaction.setOnLongClickListener(this);
        tvFeedCommunityUserReaction.setOnClickListener(this);
        tvFeedCommunityUserComment.setOnClickListener(this);
        tvFeedCommunityUserBookmark.setOnClickListener(this);
        tvFeedCommunityUserMenu.setOnClickListener(this);
        tvFeedCommunityJoin.setOnClickListener(this);
        liFeedCommunityImages.removeAllViewsInLayout();
        liFeedCommunityImages.removeAllViews();
        allTextViewStringOperations(context);
        imageOperations(context);

    }
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFeedTitle())) {
            tvFeedCommunityCardTitle.setText(dataItem.getFeedTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDateTime())) {
            tvFeedCommunityTime.setText(dataItem.getCreatedDateTime());
        }

        if (StringUtil.isNotNullOrEmptyString(dataItem.getDescription())) {
            tvFeedCommunityText.setText(dataItem.getDescription());
        }
        if (dataItem.getUserReaction().equalsIgnoreCase(AppConstants.HEART_REACTION)) {
            tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
        }
        if (dataItem.getBookmarked()) {
            tvFeedCommunityUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        }
        if (dataItem.getNoOfReactions() < 1 && dataItem.getNoOfComments() < 1) {
            flFeedCommunityNoReactionComment.setVisibility(View.GONE);
        }
        switch (dataItem.getNoOfReactions()) {
            case 0:
                tvFeedCommunityReaction1.setVisibility(View.GONE);
                tvFeedCommunityTotalReactions.setVisibility(View.GONE);
                break;
            case 1:
                tvFeedCommunityTotalReactions.setText(String.valueOf(dataItem.getNoOfReactions()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                tvFeedCommunityReaction2.setVisibility(View.GONE);
                tvFeedCommunityReaction3.setVisibility(View.GONE);
                break;
            case 2:
                tvFeedCommunityTotalReactions.setText(String.valueOf(dataItem.getNoOfReactions()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                tvFeedCommunityReaction3.setVisibility(View.GONE);
                break;
            case 3:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getNoOfReactions());
        }
        switch (dataItem.getNoOfComments()) {
            case 0:
                tvFeedCommunityTotalReplies.setVisibility(View.GONE);
                break;
            case 1:
                tvFeedCommunityTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                break;
            case 2:
                tvFeedCommunityTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getNoOfReactions());
        }
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getFeedCircleIconUrl();
        if(StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivFeedCommunityCircleIcon.setCircularImage(true);
            ivFeedCommunityCircleIcon.bindImage(feedCircleIconUrl);
            ivFeedCommunityRegisterUserPic.setCircularImage(true);
            ivFeedCommunityRegisterUserPic.bindImage(feedCircleIconUrl);
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_article_single_image);
            TextView tvFeedCommunityTimeLabel = (TextView) child.findViewById(R.id.tv_feed_article_time_label);
            tvFeedCommunityTimeLabel.setVisibility(View.GONE);
            TextView tvFeedCommunityTotalViews = (TextView) child.findViewById(R.id.tv_feed_article_total_views);
            tvFeedCommunityTotalViews.setText(dataItem.getNoOfComments()+AppConstants.SPACE+context.getString(R.string.ID_VIEWS));
            Glide.with(context)
                    .load(feedCircleIconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            liFeedCommunityImages.addView(child);
        }
        if (null != dataItem.getRecentComment() && StringUtil.isNotNullOrEmptyString(dataItem.getRecentComment().getCommentDescription()) && StringUtil.isNotNullOrEmptyString(dataItem.getRecentComment().getUserName())) {
            String feedUserIconUrl = dataItem.getRecentComment().getUserProfilePicUrl();
            ivFeedCommunityUserPic.setCircularImage(true);
            ivFeedCommunityUserPic.bindImage(feedUserIconUrl);
            String userName = LEFT_HTML_TAG_FOR_COLOR + dataItem.getRecentComment().getUserName() + RIGHT_HTML_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + dataItem.getRecentComment().getCommentDescription(), 0)); // for 24 api and more
            } else {
                tvFeedCommunityUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + dataItem.getRecentComment().getCommentDescription()));// or for older api
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
            case R.id.li_feed_community_images:
                viewInterface.handleOnClick(dataItem, liFeedCommunityImages);
                break;
            case R.id.li_feed_community_join_conversation:
                viewInterface.handleOnClick(dataItem, liFeedCommunityJoinConversation);
                break;
            case R.id.tv_feed_community_user_reaction:
                if(dataItem.getUserReaction().equalsIgnoreCase(AppConstants.HEART_REACTION)) {
                    tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                    dataItem.setUserReaction(AppConstants.NO_REACTION);
                }
                else
                {
                    tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                    dataItem.setUserReaction(AppConstants.HEART_REACTION);
                }
                break;
            case R.id.tv_feed_community_user_comment:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityUserComment);
                break;
            case R.id.tv_feed_community_user_bookmark:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityUserBookmark);
                break;
            case R.id.tv_feed_community_user_menu:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityUserMenu);
                break;
            case R.id.tv_feed_community_join:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityJoin);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }
    @Override
    public boolean onLongClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_feed_community_user_reaction:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityUserReaction);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}