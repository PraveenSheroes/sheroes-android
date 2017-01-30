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

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.feed.TotalCoverImage;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 22-01-2017.
 */

public class FeedCommunityPostHolder extends BaseViewHolder<ListOfFeed> implements View.OnLongClickListener {
    private final String TAG = LogUtils.makeLogTag(FeedCommunityPostHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    @Bind(R.id.li_feed_community_user_post_images)
    LinearLayout liFeedCommunityUserPostImages;
    @Bind(R.id.li_feed_community_post_join_conversation)
    LinearLayout liFeedCommunityPostJoinConversation;
    @Bind(R.id.li_feed_community_user_post_emoji_pop_up)
    LinearLayout liFeedCommunityUserPostEmojiPopUp;
    @Bind(R.id.iv_feed_community_post_circle_icon)
    CircleImageView ivFeedCommunityPostCircleIcon;
    @Bind(R.id.tv_feed_community_post_user_reaction)
    TextView tvFeedCommunityPostUserReaction;
    @Bind(R.id.tv_feed_community_post_user_comment)
    TextView tvFeedCommunityPostUserComment;

    @Bind(R.id.iv_feed_community_post_user_pic)
    CircleImageView ivFeedCommunityPostUserPic;
    @Bind(R.id.iv_feed_community_post_register_user_pic)
    CircleImageView ivFeedCommunityPostRegisterUserPic;
    @Bind(R.id.tv_feed_community_post_user_bookmark)
    TextView tvFeedCommunityPostUserBookmark;
    @Bind(R.id.tv_feed_community_post_card_title)
    TextView tvFeedCommunityPostCardTitle;
    @Bind(R.id.tv_feed_community_post_time)
    TextView tvFeedCommunityPostTime;
    @Bind(R.id.iv_feed_community_post_menu)
    ImageView ivFeedCommunityPostMenu;
    @Bind(R.id.tv_feed_community_post_text)
    TextView tvFeedCommunityPostText;
    @Bind(R.id.tv_feed_community_post_user_menu)
    TextView tvFeedCommunityPostUserMenu;
    @Bind(R.id.tv_feed_community_post_reaction1)
    TextView tvFeedCommunityPostReaction1;
    @Bind(R.id.tv_feed_community_post_reaction2)
    TextView tvFeedCommunityPostReaction2;
    @Bind(R.id.tv_feed_community_post_reaction3)
    TextView tvFeedCommunityPostReaction3;
    @Bind(R.id.tv_feed_community_post_total_reactions)
    TextView tvFeedCommunityPostTotalReactions;
    @Bind(R.id.tv_feed_community_post_total_replies)
    TextView tvFeedCommunityPostTotalReplies;
    @Bind(R.id.tv_feed_community_post_user_comment_post)
    TextView tvFeedCommunityPostUserCommentPost;
    @Bind(R.id.tv_feed_community_post_register_user_comment)
    TextView tvFeedCommunityPostRegisterUserComment;
    @Bind(R.id.fl_feed_community_post_no_reaction_comments)
    FrameLayout flFeedCommunityPostNoReactionComment;
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;

    public FeedCommunityPostHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        liFeedCommunityPostJoinConversation.setOnClickListener(this);
        tvFeedCommunityPostUserReaction.setOnLongClickListener(this);
        tvFeedCommunityPostUserReaction.setOnClickListener(this);
        tvFeedCommunityPostUserComment.setOnClickListener(this);
        tvFeedCommunityPostUserBookmark.setOnClickListener(this);
        tvFeedCommunityPostUserMenu.setOnClickListener(this);
        imageOperations(context);
        allTextViewStringOperations(context);
        if (StringUtil.isNotEmptyCollection(dataItem.getTotalCoverImages())) {
            List<TotalCoverImage> coverImageList = dataItem.getTotalCoverImages();
            if (coverImageList.size() > 0) {
                switch (coverImageList.size()) {
                    case 1:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0).getFeedDetailImageUrl())) {
                            oneImagesSetting(context, coverImageList.get(0).getFeedDetailImageUrl());
                        }

                        break;
                    case 2:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1).getFeedDetailImageUrl())) {
                            twoImagesSetting(context, coverImageList.get(0).getFeedDetailImageUrl(), coverImageList.get(1).getFeedDetailImageUrl());
                        }

                        break;
                    case 3:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2).getFeedDetailImageUrl())) {
                            if (coverImageList.get(0).getWidth() > coverImageList.get(0).getHeight()) {
                                feedFirstLandscapWIthTwoImageModeSetting(context, coverImageList.get(0).getFeedDetailImageUrl(), coverImageList.get(1).getFeedDetailImageUrl(), coverImageList.get(2).getFeedDetailImageUrl());
                            } else {
                                feedFirstPortraitWithTwoImageModeSetting(context, coverImageList.get(0).getFeedDetailImageUrl(), coverImageList.get(1).getFeedDetailImageUrl(), coverImageList.get(2).getFeedDetailImageUrl());
                            }
                        }
                        break;
                    case 4:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3).getFeedDetailImageUrl())) {
                            if (coverImageList.get(0).getWidth() > coverImageList.get(0).getHeight()) {
                                feedFirstLandscapImageModeSetting(context, coverImageList.get(0).getFeedDetailImageUrl(), coverImageList.get(1).getFeedDetailImageUrl(), coverImageList.get(2).getFeedDetailImageUrl(), coverImageList.get(3).getFeedDetailImageUrl());
                            } else {
                                feedFirstPortraitImageModeSetting(context, coverImageList.get(0).getFeedDetailImageUrl(), coverImageList.get(1).getFeedDetailImageUrl(), coverImageList.get(2).getFeedDetailImageUrl(), coverImageList.get(3).getFeedDetailImageUrl());
                            }
                        }
                        break;
                    case 5:
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + coverImageList.size());
                }
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFeedTitle())) {
            tvFeedCommunityPostCardTitle.setText(dataItem.getFeedTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDateTime())) {
            tvFeedCommunityPostTime.setText(dataItem.getCreatedDateTime());
        }

        if (StringUtil.isNotNullOrEmptyString(dataItem.getDescription())) {
            String description = dataItem.getDescription();
            if (description.length() > AppConstants.WORD_LENGTH) {
                description = description.substring(0, AppConstants.WORD_COUNT);
            }
            String changeDate = LEFT_HTML_VEIW_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityPostText.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + changeDate, 0)); // for 24 api and more
            } else {
                tvFeedCommunityPostText.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + changeDate));// or for older api
            }
        }
        if (dataItem.getUserReaction().equalsIgnoreCase(AppConstants.HEART_REACTION)) {
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
        }
        if (dataItem.getBookmarked()) {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        }
        if (dataItem.getNoOfReactions() < 1 && dataItem.getNoOfComments() < 1) {
            flFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
        }
        switch (dataItem.getNoOfReactions()) {
            case 0:
                tvFeedCommunityPostReaction1.setVisibility(View.GONE);
                tvFeedCommunityPostTotalReactions.setVisibility(View.GONE);
                break;
            case 1:
                tvFeedCommunityPostTotalReactions.setText(String.valueOf(dataItem.getNoOfReactions()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                tvFeedCommunityPostReaction2.setVisibility(View.GONE);
                tvFeedCommunityPostReaction3.setVisibility(View.GONE);
                break;
            case 2:
                tvFeedCommunityPostTotalReactions.setText(String.valueOf(dataItem.getNoOfReactions()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                tvFeedCommunityPostReaction3.setVisibility(View.GONE);
                break;
            case 3:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getNoOfReactions());
        }
        switch (dataItem.getNoOfComments()) {
            case 0:
                tvFeedCommunityPostTotalReplies.setVisibility(View.GONE);
                break;
            case 1:
                tvFeedCommunityPostTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                break;
            case 2:
                tvFeedCommunityPostTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getNoOfReactions());
        }
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getFeedCircleIconUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivFeedCommunityPostCircleIcon.setCircularImage(true);
            ivFeedCommunityPostCircleIcon.bindImage(feedCircleIconUrl);
            ivFeedCommunityPostRegisterUserPic.setCircularImage(true);
            ivFeedCommunityPostRegisterUserPic.bindImage(feedCircleIconUrl);
        }
        if (null != dataItem.getRecentComment() && StringUtil.isNotNullOrEmptyString(dataItem.getRecentComment().getCommentDescription()) && StringUtil.isNotNullOrEmptyString(dataItem.getRecentComment().getUserName())) {
            String feedUserIconUrl = dataItem.getRecentComment().getUserProfilePicUrl();
            ivFeedCommunityPostUserPic.setCircularImage(true);
            ivFeedCommunityPostUserPic.bindImage(feedUserIconUrl);
            String userName = LEFT_HTML_TAG_FOR_COLOR + dataItem.getRecentComment().getUserName() + RIGHT_HTML_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + dataItem.getRecentComment().getCommentDescription(), 0)); // for 24 api and more
            } else {
                tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + dataItem.getRecentComment().getCommentDescription()));// or for older api
            }
        }
    }

    private void oneImagesSetting(Context context, String firstImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_single_image, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_single);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);

        liFeedCommunityUserPostImages.addView(child);
    }

    private void twoImagesSetting(Context context, String firstImage, String secondImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_two_images, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_comunity_post_second);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        liFeedCommunityUserPostImages.addView(child);
    }

    private void feedFirstPortraitImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage, String fourthImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_portrait_with_multiple, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_portrait);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_portrait);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_portrait);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);
        ImageView ivFourth = (ImageView) child.findViewById(R.id.iv_feed_community_post_fourth_portrait);
        Glide.with(context)
                .load(fourthImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFourth);
        liFeedCommunityUserPostImages.addView(child);
    }

    private void feedFirstPortraitWithTwoImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_portrait_with_two_images, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_portrait_side_two_image);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_portrait_side_two_image);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_portrait_side_two_image);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);
        liFeedCommunityUserPostImages.addView(child);
    }

    private void feedFirstLandscapWIthTwoImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_landscape_with_two_images, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_landscape_with_two_images);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape_with_two_images);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape_with_two_images);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);

        liFeedCommunityUserPostImages.addView(child);
    }

    private void feedFirstLandscapImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage, String fourthImage) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_landscape_with_multiple, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_landscape);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);
        ImageView ivFourth = (ImageView) child.findViewById(R.id.iv_feed_community_post_fourth_image_landscape);
        Glide.with(context)
                .load(fourthImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFourth);
        liFeedCommunityUserPostImages.addView(child);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_community_user_post_images:
                viewInterface.handleOnClick(dataItem, liFeedCommunityUserPostImages);
                break;
            case R.id.li_feed_community_post_join_conversation:
                viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
                break;
            case R.id.tv_feed_community_post_user_reaction:
                if (dataItem.getUserReaction().equalsIgnoreCase(AppConstants.HEART_REACTION)) {
                    tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                    dataItem.setUserReaction(AppConstants.NO_REACTION);
                } else {
                    tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                    dataItem.setUserReaction(AppConstants.HEART_REACTION);
                }
                break;
            case R.id.tv_feed_community_post_user_comment:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
                break;
            case R.id.tv_feed_community_post_user_bookmark:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserBookmark);
                break;
            case R.id.tv_feed_community_post_user_menu:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserMenu);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.tv_feed_community_post_user_reaction:
                viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserReaction);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
        return true;
    }
}