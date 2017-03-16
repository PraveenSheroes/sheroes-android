package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Praveen_Singh on 08-02-2017.
 */

public class ArticleDetailHolder extends BaseViewHolder<ArticleDetailPojo> {
    private final String TAG = LogUtils.makeLogTag(ArticleCardHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    DateUtil mDateUtil;
    @Bind(R.id.iv_article_detail_card_circle_icon)
    CircleImageView ivArticleDetailCardCircleIcon;
    @Bind(R.id.tv_html_data)
    TextView tvHtmlData;
    @Bind(R.id.tv_article_detail_tag)
    TextView tvArticleDetailTag;
    @Bind(R.id.tv_article_detail_time)
    TextView tvArticleDetailTime;
    @Bind(R.id.tv_article_detail_icon_name)
    TextView tvArticleDetailIconName;
    @Bind(R.id.tv_article_detail_description)
    TextView tvArticleDetailDescription;
    @Bind(R.id.tv_article_detail_user_reaction)
    TextView tvArticleDetailUserReaction;
    @Bind(R.id.tv_feed_article_detail_reaction_text)
    TextView tvArticleDetailUserReactionText;
    @Bind(R.id.tv_article_detail_user_comment)
    TextView tvArticleDetailUserComment;
    @Bind(R.id.tv_article_detail_total_reactions)
    TextView tvArticleDetailTotalReaction;
    @Bind(R.id.tv_article_detail_total_replies)
    TextView tvArticleDetailTotalReplies;
    @Bind(R.id.li_article_detail_join_conversation)
    LinearLayout liArticleDetailJoinConversation;
    @Bind(R.id.iv_article_detail_register_user_pic)
    CircleImageView ivArticleDetailRegisterUserPic;
    @Bind(R.id.iv_article_detail_user_pic)
    CircleImageView ivArticleDetailUserPic;
    @Bind(R.id.li_article_detail_emoji_pop_up)
    LinearLayout liArticleDetailEmojiPopUp;
    @Bind(R.id.tv_article_detail_view_more)
    TextView tvArticleDetailViewMore;
    @Bind(R.id.tv_article_detail_user_comment_post_menu)
    TextView tvArticleDetailUserCommentPostMenu;
    @Bind(R.id.tv_article_detail_user_comment_post_menu_second)
    TextView tvArticleDetailUserCommentPostMenuSecond;
    @Bind(R.id.tv_article_detail_user_comment_post_menu_third)
    TextView tvArticleDetailUserCommentPostMenuThird;
    @Bind(R.id.tv_article_detail_user_comment_post)
    TextView tvArticleDetailUserCommentPost;
    @Bind(R.id.tv_article_detail_user_comment_post_second)
    TextView tvArticleDetailUserCommentPostSecond;
    @Bind(R.id.tv_article_detail_user_comment_post_third)
    TextView tvArticleDetailUserCommentPostThird;
    BaseHolderInterface viewInterface;
    private ArticleDetailPojo dataItem;
    private FeedDetail mFeedDetail;
    private Context mContext;
    @Bind(R.id.fl_article_detail_no_reaction_comments)
    FrameLayout flArticleDetailNoReactionComment;
    @Bind(R.id.li_article_detail_user_comments)
    LinearLayout liArticleDetailUserComments;
    @Bind(R.id.li_article_detail_user_comments_second)
    LinearLayout liArticleDetailUserCommentsSecond;
    @Bind(R.id.iv_article_detail_user_pic_second)
    CircleImageView ivArticleDetailUserPicSecond;
    @Bind(R.id.li_article_detail_user_comments_third)
    LinearLayout liArticleDetailUserCommentsThird;
    @Bind(R.id.iv_article_detail_user_pic_third)
    CircleImageView ivArticleDetailUserPicThird;

    public ArticleDetailHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;

    }

    @Override
    public void bindData(ArticleDetailPojo item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
        mFeedDetail = dataItem.getFeedDetail();
        tvArticleDetailUserReaction.setEnabled(true);
        if (null != mFeedDetail) {
            imageOperations(context);
            allTextViewStringOperations(context);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void imageOperations(Context context) {

        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            ((ArticleDetailActivity) mContext).mCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
        }
        String feedCircleIconUrl = mFeedDetail.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivArticleDetailCardCircleIcon.setCircularImage(true);
            ivArticleDetailCardCircleIcon.bindImage(feedCircleIconUrl);
        }
        String description = mFeedDetail.getDescription();
        if (StringUtil.isNotNullOrEmptyString(description)) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvHtmlData.setText(Html.fromHtml(description, 0)); // for 24 api and more
            } else {
                tvHtmlData.setText(Html.fromHtml(description));// or for older api
            }
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorName())) {
            tvArticleDetailIconName.setText(mFeedDetail.getAuthorName());
        }
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
            ivArticleDetailRegisterUserPic.setCircularImage(true);
            ivArticleDetailRegisterUserPic.bindImage(userPreference.get().getUserSummary().getPhotoUrl());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(mFeedDetail.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvArticleDetailTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDescription())) {
            String description = mFeedDetail.getDescription().trim();
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleDetailDescription.setText(Html.fromHtml(description, 0)); // for 24 api and more
            } else {
                tvArticleDetailDescription.setText(Html.fromHtml(description));// or for older api
            }
        }
        if (mFeedDetail.getNoOfLikes() < AppConstants.ONE_CONSTANT && mFeedDetail.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flArticleDetailNoReactionComment.setVisibility(View.GONE);
        } else if (mFeedDetail.getNoOfLikes() < AppConstants.ONE_CONSTANT) {
            flArticleDetailNoReactionComment.setVisibility(View.GONE);
            tvArticleDetailTotalReaction.setVisibility(View.GONE);
        }

        switch (mFeedDetail.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                flArticleDetailNoReactionComment.setVisibility(View.VISIBLE);
                tvArticleDetailTotalReaction.setText(String.valueOf(mFeedDetail.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                tvArticleDetailUserReactionText.setText(AppConstants.EMPTY_STRING);
                userLike();
                break;
            default:
                flArticleDetailNoReactionComment.setVisibility(View.VISIBLE);
                tvArticleDetailTotalReaction.setText(String.valueOf(mFeedDetail.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                tvArticleDetailUserReactionText.setText(AppConstants.EMPTY_STRING);
                userLike();
        }
        userComments();
    }

    private void userLike() {

        switch (mFeedDetail.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                tvArticleDetailUserReactionText.setText(AppConstants.EMPTY_STRING);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                tvArticleDetailUserReactionText.setText(mContext.getString(R.string.ID_LOVE));
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji3_whistel, 0, 0, 0);
                tvArticleDetailUserReactionText.setText(mContext.getString(R.string.ID_WISHTLE));
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji_xo_xo, 0, 0, 0);
                tvArticleDetailUserReactionText.setText(mContext.getString(R.string.ID_XOXO));
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji2_with_you, 0, 0, 0);
                tvArticleDetailUserReactionText.setText(mContext.getString(R.string.ID_LIKE));
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji4_face_palm, 0, 0, 0);
                tvArticleDetailUserReactionText.setText(mContext.getString(R.string.ID_FACE_PALM));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + mFeedDetail.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {

        if (StringUtil.isNotEmptyCollection(mFeedDetail.getLastComments())) {
            List<LastComment> lastCommentList = mFeedDetail.getLastComments();
            if (lastCommentList.size() > AppConstants.ONE_CONSTANT) {
                tvArticleDetailTotalReplies.setText(String.valueOf(mFeedDetail.getNoOfComments()) + AppConstants.SPACE + mContext.getString(R.string.ID_REPLIES));
            } else {
                tvArticleDetailTotalReplies.setText(String.valueOf(mFeedDetail.getNoOfComments()) + AppConstants.SPACE + mContext.getString(R.string.ID_REPLY));
            }
            for (int index = 0; index < lastCommentList.size(); index++) {
                //   String feedUserIconUrl = lastCommentList.get(index).getParticipantImageUrl();
                //   String userName = LEFT_HTML_TAG_FOR_COLOR + lastCommentList.get(index).getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                switch (index) {
                    case AppConstants.NO_REACTION_CONSTANT:
                        ivArticleDetailUserPic.setCircularImage(true);
                        liArticleDetailUserComments.setVisibility(View.VISIBLE);
                        if (lastCommentList.get(index).isAnonymous()) {
                            String userName = LEFT_HTML_TAG_FOR_COLOR + mContext.getString(R.string.ID_ANONYMOUS) + RIGHT_HTML_TAG_FOR_COLOR;
                            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                                tvArticleDetailUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment(), 0)); // for 24 api and more
                            } else {
                                tvArticleDetailUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment()));// or for older api
                            }
                            ivArticleDetailUserPic.setImageResource(R.drawable.ic_anonomous);
                        } else {
                            String userName = LEFT_HTML_TAG_FOR_COLOR + lastCommentList.get(index).getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                            String feedUserIconUrl = lastCommentList.get(index).getParticipantImageUrl();
                            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                                tvArticleDetailUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment(), 0)); // for 24 api and more
                            } else {
                                tvArticleDetailUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment()));// or for older api
                            }
                            ivArticleDetailUserPic.bindImage(feedUserIconUrl);
                        }
                        if (lastCommentList.get(index).isMyOwnParticipation()) {
                            tvArticleDetailUserCommentPostMenu.setVisibility(View.VISIBLE);
                        } else {
                            tvArticleDetailUserCommentPostMenu.setVisibility(View.GONE);
                        }
                        break;
                    case AppConstants.ONE_CONSTANT:
                        ivArticleDetailUserPicSecond.setCircularImage(true);
                        liArticleDetailUserCommentsSecond.setVisibility(View.VISIBLE);

                        if (lastCommentList.get(index).isAnonymous()) {
                            String userName = LEFT_HTML_TAG_FOR_COLOR + mContext.getString(R.string.ID_ANONYMOUS) + RIGHT_HTML_TAG_FOR_COLOR;
                            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                                tvArticleDetailUserCommentPostSecond.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment(), 0)); // for 24 api and more
                            } else {
                                tvArticleDetailUserCommentPostSecond.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment()));// or for older api
                            }
                            ivArticleDetailUserPicSecond.setImageResource(R.drawable.ic_anonomous);
                        } else {
                            String userName = LEFT_HTML_TAG_FOR_COLOR + lastCommentList.get(index).getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                            String feedUserIconUrl = lastCommentList.get(index).getParticipantImageUrl();
                            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                                tvArticleDetailUserCommentPostSecond.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment(), 0)); // for 24 api and more
                            } else {
                                tvArticleDetailUserCommentPostSecond.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment()));// or for older api
                            }
                            ivArticleDetailUserPicSecond.bindImage(feedUserIconUrl);
                        }
                        if (lastCommentList.get(index).isMyOwnParticipation()) {
                            tvArticleDetailUserCommentPostMenuSecond.setVisibility(View.VISIBLE);
                        } else {
                            tvArticleDetailUserCommentPostMenuSecond.setVisibility(View.GONE);
                        }

                        break;
                    case AppConstants.TWO_CONSTANT:
                        ivArticleDetailUserPicThird.setCircularImage(true);
                        liArticleDetailUserCommentsThird.setVisibility(View.VISIBLE);
                        if (lastCommentList.get(index).isAnonymous()) {
                            String userName = LEFT_HTML_TAG_FOR_COLOR + mContext.getString(R.string.ID_ANONYMOUS) + RIGHT_HTML_TAG_FOR_COLOR;
                            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                                tvArticleDetailUserCommentPostThird.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment(), 0)); // for 24 api and more
                            } else {
                                tvArticleDetailUserCommentPostThird.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment()));// or for older api
                            }
                            ivArticleDetailUserPicThird.setImageResource(R.drawable.ic_anonomous);
                        } else {
                            String userName = LEFT_HTML_TAG_FOR_COLOR + lastCommentList.get(index).getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                            String feedUserIconUrl = lastCommentList.get(index).getParticipantImageUrl();
                            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                                tvArticleDetailUserCommentPostThird.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment(), 0)); // for 24 api and more
                            } else {
                                tvArticleDetailUserCommentPostThird.setText(Html.fromHtml(userName + AppConstants.SPACE + lastCommentList.get(index).getComment()));// or for older api
                            }
                            ivArticleDetailUserPicThird.bindImage(feedUserIconUrl);
                        }

                        if (lastCommentList.get(index).isMyOwnParticipation()) {
                            tvArticleDetailUserCommentPostMenuThird.setVisibility(View.VISIBLE);
                        } else {
                            tvArticleDetailUserCommentPostMenuThird.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + index);
                }
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnLongClick(R.id.tv_article_detail_user_reaction)
    public boolean userReactionLongClick() {
        tvArticleDetailUserReaction.setEnabled(false);
        mFeedDetail.setItemPosition(getAdapterPosition());
        mFeedDetail.setLongPress(true);
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserReaction);
        return true;
    }

    @OnClick(R.id.tv_article_detail_user_reaction)
    public void userReactionClick() {
        mFeedDetail.setLongPress(false);
        mFeedDetail.setItemPosition(getAdapterPosition());
        if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(mFeedDetail, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
        } else {
            viewInterface.userCommentLikeRequest(mFeedDetail, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
        }
        if (liArticleDetailEmojiPopUp.getVisibility() == View.VISIBLE) {
            viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserReaction);
        }
    }

    @OnClick(R.id.tv_article_detail_user_comment)
    public void userCommentClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(mFeedDetail, liArticleDetailJoinConversation);
    }

    @OnClick(R.id.tv_article_detail_total_reactions)
    public void reactionClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailTotalReaction);
    }

    @OnClick(R.id.tv_article_detail_total_replies)
    public void userRepliesClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(mFeedDetail, liArticleDetailJoinConversation);
    }

    @OnClick(R.id.li_article_detail_join_conversation)
    public void joinConversationClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(mFeedDetail, liArticleDetailJoinConversation);
    }

    @OnClick(R.id.tv_article_detail_view_more)
    public void viewMoreClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(mFeedDetail, liArticleDetailJoinConversation);
    }

    @OnClick(R.id.tv_article_detail_user_comment_post_menu)
    public void menuClick() {
        mFeedDetail.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserCommentPostMenu);
    }

    @OnClick(R.id.tv_article_detail_user_comment_post_menu_second)
    public void menuSecondClick() {
        mFeedDetail.setItemPosition(AppConstants.ONE_CONSTANT);
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserCommentPostMenuSecond);
    }

    @OnClick(R.id.tv_article_detail_user_comment_post_menu_third)
    public void menuThirdClick() {
        mFeedDetail.setItemPosition(AppConstants.TWO_CONSTANT);
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserCommentPostMenuThird);
    }

    @Override
    public void onClick(View view) {

    }

}
