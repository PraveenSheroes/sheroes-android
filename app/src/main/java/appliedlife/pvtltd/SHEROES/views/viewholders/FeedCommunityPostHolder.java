package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LastComment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Praveen_Singh on 22-01-2017.
 */

public class FeedCommunityPostHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeedCommunityPostHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    private static final String LEFT_HTML_COMMUNITY_TITLE_FOR_COLOR = "<b><font color='#f2403c'>";
    private static final String RIGHT_HTML_COMMUNITY_TITLE_FOR_COLOR = "</font></b>";
    @Bind(R.id.li_feed_community_post_user_comments)
    LinearLayout liFeedCommunityPostUserComments;
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
    @Bind(R.id.tv_feed_community_post_user_comment_post_menu)
    TextView tvFeedCommunityPostUserCommentPostMenu;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    Context mContext;

    public FeedCommunityPostHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        liFeedCommunityPostJoinConversation.setOnClickListener(this);
        tvFeedCommunityPostUserReaction.setOnClickListener(this);
        tvFeedCommunityPostUserComment.setOnClickListener(this);
        tvFeedCommunityPostUserBookmark.setOnClickListener(this);
        tvFeedCommunityPostUserMenu.setOnClickListener(this);
        imageOperations(context);
        allTextViewStringOperations(context);
        if (StringUtil.isNotEmptyCollection(dataItem.getImageUrls())) {
            List<String> coverImageList = dataItem.getImageUrls();
            if (coverImageList.size() > 0) {
                switch (coverImageList.size()) {
                    case 1:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0))) {
                            oneImagesSetting(context, coverImageList.get(0));
                        }

                        break;
                  /*  case 2:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1).getFeedDetailImageUrl())) {
                            twoImagesSetting(context, coverImageList.get(0), coverImageList.get(1));
                        }
                        break;*/
                    case 3:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2))) {
                            boolean isHeightGreater = getCoverImageHeightWidth(coverImageList.get(0));
                            if (isHeightGreater) {
                                feedFirstLandscapWIthTwoImageModeSetting(context, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                            } else {
                                feedFirstPortraitWithTwoImageModeSetting(context, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                            }
                        }
                        break;
                 /*   case 4:
                        liFeedCommunityUserPostImages.removeAllViews();
                        liFeedCommunityUserPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2).getFeedDetailImageUrl()) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3).getFeedDetailImageUrl())) {
                            if (coverImageList.get(0).getWidth() > coverImageList.get(0).getHeight()) {
                                feedFirstLandscapImageModeSetting(context, coverImageList.get(0).getFeedDetailImageUrl(), coverImageList.get(1).getFeedDetailImageUrl(), coverImageList.get(2).getFeedDetailImageUrl(), coverImageList.get(3).getFeedDetailImageUrl());
                            } else {
                                feedFirstPortraitImageModeSetting(context, coverImageList.get(0).getFeedDetailImageUrl(), coverImageList.get(1).getFeedDetailImageUrl(), coverImageList.get(2).getFeedDetailImageUrl(), coverImageList.get(3).getFeedDetailImageUrl());
                            }
                        }
                        break;*/
                    case 5:
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + coverImageList.size());
                }
            }
        }
    }

    private boolean getCoverImageHeightWidth(String imagePath) {
        final boolean[] isHeightGreater = new boolean[1];
        Glide.with(mContext)
                .load(imagePath).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        LogUtils.info("swipe", "*****************image height******* " + resource.getHeight());
                        if (resource.getHeight() > resource.getWidth()) {
                            isHeightGreater[0] = true;
                        } else {
                            isHeightGreater[0] = false;
                        }
                    }
                });
        return isHeightGreater[0];
    }

    @Override
    public void viewRecycled() {

    }

    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {

            String feedTitle = dataItem.getAuthorName();
            String feedCommunityName = dataItem.getCommunityType();
            if (feedTitle.length() > AppConstants.WORD_LENGTH) {
                feedTitle = feedTitle.substring(0, AppConstants.WORD_COUNT);
            }
            String coloredFeedCommunityName = LEFT_HTML_COMMUNITY_TITLE_FOR_COLOR + feedCommunityName + RIGHT_HTML_COMMUNITY_TITLE_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityPostCardTitle.setText(Html.fromHtml(feedTitle + AppConstants.SPACE + coloredFeedCommunityName)); // for 24 api and more
            } else {
                tvFeedCommunityPostCardTitle.setText(Html.fromHtml(feedTitle + AppConstants.SPACE + coloredFeedCommunityName));// or for older api
            }

        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            // tvFeedCommunityPostTime.setText(dataItem.getCreatedDate());
        }

        if (StringUtil.isNotNullOrEmptyString(dataItem.getDescription())) {
            String description = dataItem.getDescription();
            if (description.length() > AppConstants.WORD_LENGTH) {
                description = description.substring(0, AppConstants.WORD_COUNT);
            }
            String viewMore = LEFT_HTML_VEIW_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityPostText.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
            } else {
                tvFeedCommunityPostText.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
            }
        }
        if (dataItem.isBookmarked()) {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }
        if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT && dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
        } else if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT) {
            flFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
            tvFeedCommunityPostTotalReactions.setVisibility(View.GONE);
        } else {
            flFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
            switch (dataItem.getNoOfLikes()) {
                case AppConstants.ONE_CONSTANT:
                    tvFeedCommunityPostTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                    userLike();
                    break;
                default:
                    tvFeedCommunityPostTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                    userLike();
            }
        }
        if (dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedCommunityPostTotalReplies.setVisibility(View.GONE);
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
        } else {
            switch (dataItem.getNoOfComments()) {
                case AppConstants.ONE_CONSTANT:
                    tvFeedCommunityPostTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                    userComments();
                    break;
                default:
                    tvFeedCommunityPostTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                    userComments();
            }
        }
    }

    private void userLike() {

        switch (dataItem.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                tvFeedCommunityPostUserReaction.setText(AppConstants.EMPTY_STRING);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                tvFeedCommunityPostUserReaction.setText(mContext.getString(R.string.ID_LOVE));
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji3_whistel, 0, 0, 0);
                tvFeedCommunityPostUserReaction.setText(mContext.getString(R.string.ID_WISHTLE));
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji_xo_xo, 0, 0, 0);
                tvFeedCommunityPostUserReaction.setText(mContext.getString(R.string.ID_XOXO));
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji2_with_you, 0, 0, 0);
                tvFeedCommunityPostUserReaction.setText(mContext.getString(R.string.ID_LIKE));
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji4_face_palm, 0, 0, 0);
                tvFeedCommunityPostUserReaction.setText(mContext.getString(R.string.ID_FACE_PALM));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getReactionValue());
        }
    }

    private void userComments() {
        LastComment lastComment = dataItem.getLastComment();
        if (null != lastComment) {
            String feedUserIconUrl = lastComment.getParticipantImageUrl();
            ivFeedCommunityPostUserPic.setCircularImage(true);
            ivFeedCommunityPostUserPic.bindImage(feedUserIconUrl);
            String userName = LEFT_HTML_TAG_FOR_COLOR + lastComment.getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + lastComment.getComment(), 0)); // for 24 api and more
            } else {
                tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + lastComment.getComment()));// or for older api
            }
            if (lastComment.getIsAnonymous()) {
                tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.GONE);
            }
        }
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivFeedCommunityPostCircleIcon.setCircularImage(true);
            ivFeedCommunityPostCircleIcon.bindImage(feedCircleIconUrl);
            ivFeedCommunityPostRegisterUserPic.setCircularImage(true);
            ivFeedCommunityPostRegisterUserPic.bindImage(feedCircleIconUrl);
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
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_comunity_post_second);
        ivSecond.setOnClickListener(this);
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
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_portrait);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_portrait);
        ivThird.setOnClickListener(this);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);
        ImageView ivFourth = (ImageView) child.findViewById(R.id.iv_feed_community_post_fourth_portrait);
        ivFourth.setOnClickListener(this);
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
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_portrait_side_two_image);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_portrait_side_two_image);
        ivThird.setOnClickListener(this);
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
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape_with_two_images);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape_with_two_images);
        ivThird.setOnClickListener(this);
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
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape);
        ivThird.setOnClickListener(this);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);
        ImageView ivFourth = (ImageView) child.findViewById(R.id.iv_feed_community_post_fourth_image_landscape);
        ivFourth.setOnClickListener(this);
        Glide.with(context)
                .load(fourthImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFourth);
        liFeedCommunityUserPostImages.addView(child);
    }

    @OnClick(R.id.tv_feed_community_post_total_replies)
    public void repliesClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostTotalReplies);
    }

    @OnClick(R.id.li_feed_community_user_post_images)
    public void communityPostImageClick() {
        viewInterface.handleOnClick(dataItem, liFeedCommunityUserPostImages);
    }

    @OnClick(R.id.li_feed_community_post_join_conversation)
    public void joinConversationClick() {
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }

    @OnClick(R.id.tv_feed_community_post_user_comment)
    public void userCommentClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
    }

    @OnClick(R.id.tv_feed_community_post_user_menu)
    public void userMenuClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserMenu);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_feed_community_post_first:
                LogUtils.info("img", "**********Image first******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_comunity_post_second:
                LogUtils.info("img", "**********Image seocnd******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_first_portrait:
                LogUtils.info("img", "**********Image iv_feed_community_post_first_portrait******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_second_portrait:
                LogUtils.info("img", "**********Image iv_feed_community_post_second_portrait******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_third_portrait:
                LogUtils.info("img", "**********Image iv_feed_community_post_third_portrait******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_fourth_portrait:
                LogUtils.info("img", "**********Image iv_feed_community_post_first_portrait******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_first_portrait_side_two_image:
                LogUtils.info("img", "**********Image iv_feed_community_post_first_portrait_side_two_image******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_second_portrait_side_two_image:
                LogUtils.info("img", "**********Image iv_feed_community_post_second_portrait_side_two_image******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_third_portrait_side_two_image:
                LogUtils.info("img", "**********Image iv_feed_community_post_second_portrait_side_two_image******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_first_landscape_with_two_images:
                LogUtils.info("img", "**********Image iv_feed_community_post_first_landscape_with_two_images******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_second_image_landscape_with_two_images:
                LogUtils.info("img", "**********Image iv_feed_community_post_second_image_landscape_with_two_images******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_third_image_landscape_with_two_images:
                LogUtils.info("img", "**********Image iv_feed_community_post_second_image_landscape_with_two_images******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_first_landscape:
                LogUtils.info("img", "**********Image iv_feed_community_post_first_landscape******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_second_image_landscape:
                LogUtils.info("img", "**********Image iv_feed_community_post_second_image_landscape******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_third_image_landscape:
                LogUtils.info("img", "**********Image iv_feed_community_post_third_image_landscape******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            case R.id.iv_feed_community_post_fourth_image_landscape:
                LogUtils.info("img", "**********Image iv_feed_community_post_fourth_image_landscape******");
                viewInterface.dataOperationOnClick(dataItem);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    @OnLongClick(R.id.tv_feed_community_post_user_reaction)
    public boolean userReactionLongClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserReaction);
        return true;
    }

}