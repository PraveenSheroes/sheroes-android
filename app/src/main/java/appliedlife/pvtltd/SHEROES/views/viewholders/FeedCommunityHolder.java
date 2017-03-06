package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
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
import android.widget.Toast;

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

public class FeedCommunityHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeedCommunityHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    private static final String LEFT_HTML_TAG = "<font color='#333333'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    @Bind(R.id.li_feed_community_user_comments)
    LinearLayout liFeedCommunityUserComments;
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
    @Bind(R.id.tv_feed_community_user_comment_post_menu)
    TextView tvFeedCommunityUserCommentPostMenu;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    String mViewMoreDescription;
    String mViewMore, mLess;
    Context mContext;
    @Inject
    Preference<LoginResponse> userPreference;
    public FeedCommunityHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        mViewMore = context.getString(R.string.ID_VIEW_MORE);
        mLess = context.getString(R.string.ID_LESS);
        tvFeedCommunityText.setTag(mViewMore);
        liFeedCommunityImages.removeAllViewsInLayout();
        liFeedCommunityImages.removeAllViews();
        allTextViewStringOperations(context);
        imageOperations(context);
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if(!dataItem.isApplied())
        {
            tvFeedCommunityJoin.setVisibility(View.VISIBLE);
        }else
        {
            tvFeedCommunityJoin.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvFeedCommunityCardTitle.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityType())) {
            tvFeedCommunityTime.setText(dataItem.getCommunityType());
        }
        mViewMoreDescription=dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
            }
            String changeDate = LEFT_HTML_VEIW_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + changeDate, 0)); // for 24 api and more
            } else {
                tvFeedCommunityText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + changeDate));// or for older api
            }
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getTags())) {
            List<String> tags = dataItem.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA;
            }
            String tagHeader = LEFT_HTML_TAG + context.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityTagLable.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvFeedCommunityTagLable.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }

        if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT && dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flFeedCommunityNoReactionComment.setVisibility(View.GONE);
        } else if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT) {
            flFeedCommunityNoReactionComment.setVisibility(View.GONE);
            tvFeedCommunityTotalReactions.setVisibility(View.GONE);
        } else {
            flFeedCommunityNoReactionComment.setVisibility(View.VISIBLE);
            switch (dataItem.getNoOfLikes()) {
                case AppConstants.ONE_CONSTANT:
                    tvFeedCommunityTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                    tvFeedCommunityUserReaction.setText(AppConstants.EMPTY_STRING);
                    userLike();
                    break;
                default:
                    tvFeedCommunityTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                    tvFeedCommunityUserReaction.setText(AppConstants.EMPTY_STRING);
                    userLike();
            }
        }
            switch (dataItem.getNoOfComments()) {
                case AppConstants.NO_REACTION_CONSTANT:
                    break;
                case AppConstants.ONE_CONSTANT:
                    tvFeedCommunityTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                    tvFeedCommunityTotalReplies.setVisibility(View.VISIBLE);
                    liFeedCommunityUserComments.setVisibility(View.VISIBLE);
                    userComments();
                    break;
                default:
                    tvFeedCommunityTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                    tvFeedCommunityTotalReplies.setVisibility(View.VISIBLE);
                    liFeedCommunityUserComments.setVisibility(View.VISIBLE);
                    userComments();
        }
    }

    private void userLike() {

        switch (dataItem.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                tvFeedCommunityUserReaction.setText(AppConstants.EMPTY_STRING);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                tvFeedCommunityUserReaction.setText(mContext.getString(R.string.ID_LOVE));
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji3_whistel, 0, 0, 0);
                tvFeedCommunityUserReaction.setText(mContext.getString(R.string.ID_WISHTLE));
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji_xo_xo, 0, 0, 0);
                tvFeedCommunityUserReaction.setText(mContext.getString(R.string.ID_XOXO));
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji2_with_you, 0, 0, 0);
                tvFeedCommunityUserReaction.setText(mContext.getString(R.string.ID_LIKE));
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                tvFeedCommunityUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji4_face_palm, 0, 0, 0);
                tvFeedCommunityUserReaction.setText(mContext.getString(R.string.ID_FACE_PALM));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getReactionValue());
        }
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<LastComment> lastCommentList = dataItem.getLastComments();
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            for(LastComment lastComment:lastCommentList) {
                String feedUserIconUrl = lastComment.getParticipantImageUrl();
                ivFeedCommunityUserPic.setCircularImage(true);
                if(lastComment.isAnonymous())
                {
                    String userName = LEFT_HTML_TAG_FOR_COLOR + mContext.getString(R.string.ID_ANONYMOUS) + RIGHT_HTML_TAG_FOR_COLOR;
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        tvFeedCommunityUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment(), 0)); // for 24 api and more
                    } else {
                        tvFeedCommunityUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment()));// or for older api
                    }
                    ivFeedCommunityUserPic.setImageResource(R.drawable.ic_add_city_icon);
                }
                else
                {
                    String userName = LEFT_HTML_TAG_FOR_COLOR + lastComment.getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        tvFeedCommunityUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment(), 0)); // for 24 api and more
                    } else {
                        tvFeedCommunityUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment()));// or for older api
                    }
                    ivFeedCommunityUserPic.bindImage(feedUserIconUrl);
                }

                if (lastComment.isMyOwnParticipation()) {
                    tvFeedCommunityUserCommentPostMenu.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void imageOperations(Context context) {
        String authorImageUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            ivFeedCommunityCircleIcon.setCircularImage(true);
            ivFeedCommunityCircleIcon.bindImage(authorImageUrl);
        }
        if (null != userPreference && userPreference.isSet() && null != userPreference.get()&&null!=userPreference.get().getUserSummary()&& StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
            ivFeedCommunityRegisterUserPic.setCircularImage(true);
            ivFeedCommunityRegisterUserPic.bindImage(userPreference.get().getUserSummary().getPhotoUrl());
        }
        String backgroundImageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(backgroundImageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
           final ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvFeedCommunityTimeLabel = (TextView) child.findViewById(R.id.tv_feed_article_time_label);
            final TextView tvFeedCommunityTotalViews = (TextView) child.findViewById(R.id.tv_feed_article_total_views);
            tvFeedCommunityTotalViews.setText(dataItem.getNoOfViews()+ AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            Glide.with(mContext)
                    .load(backgroundImageUrl).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                            tvFeedCommunityTotalViews.setVisibility(View.VISIBLE);
                        }
                    });

            liFeedCommunityImages.addView(child);

        }

    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_feed_community_total_replies)
    public void repliesClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedCommunityTotalReplies);
    }

    @OnClick(R.id.tv_feed_community_user_menu)
    public void menuItemClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedCommunityUserMenu);
    }

    @OnClick(R.id.tv_feed_community_user_comment_post_menu)
    public void commentItemClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedCommunityUserCommentPostMenu);
    }

    @OnClick(R.id.li_feed_community_images)
    public void communityImageClick() {
        viewInterface.handleOnClick(dataItem, liFeedCommunityImages);
    }

    @OnClick(R.id.li_feed_community_join_conversation)
    public void joinConversationClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, liFeedCommunityJoinConversation);
    }

    @OnClick(R.id.tv_feed_community_user_reaction)
    public void userReactionClick() {
        dataItem.setLongPress(false);
        dataItem.setItemPosition(getAdapterPosition());
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
        }
        if (liFeedCommunityEmojiPopUp.getVisibility() == View.VISIBLE) {
            viewInterface.handleOnClick(dataItem, tvFeedCommunityUserReaction);
        }
    }

    @OnClick(R.id.tv_feed_community_user_comment)
    public void communityUserCommentClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, liFeedCommunityJoinConversation);
    }

    @OnClick(R.id.tv_feed_community_join)
    public void communityJoinClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedCommunityJoin);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @OnClick(R.id.tv_feed_community_text)
    public void viewMoreClick() {
        if(StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (tvFeedCommunityText.getTag().toString().equalsIgnoreCase(mViewMore)) {
                String lessWithColor = LEFT_HTML_VEIW_TAG_FOR_COLOR + mLess + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                } else {
                    tvFeedCommunityText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + lessWithColor));// or for older api
                }
                tvFeedCommunityText.setTag(mLess);
            } else {
                tvFeedCommunityText.setTag(mViewMore);
                String viewMore = LEFT_HTML_VEIW_TAG_FOR_COLOR + mViewMore + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityText.setText(Html.fromHtml(mViewMoreDescription.substring(0, AppConstants.WORD_COUNT) + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
                } else {
                    tvFeedCommunityText.setText(Html.fromHtml(mViewMoreDescription.substring(0, AppConstants.WORD_COUNT) + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
                }
            }
        }
    }



    @Override
    public void onClick(View view) {

    }

    @OnLongClick(R.id.tv_feed_community_user_reaction)
    public boolean userReactionLongClick() {
        dataItem.setItemPosition(getAdapterPosition());
        dataItem.setLongPress(true);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityUserReaction);
        return true;
    }

    @OnClick(R.id.tv_feed_community_join)
    public void joinClick()
    {
        Toast.makeText(mContext,"Clciekd",Toast.LENGTH_SHORT).show();
    }
}