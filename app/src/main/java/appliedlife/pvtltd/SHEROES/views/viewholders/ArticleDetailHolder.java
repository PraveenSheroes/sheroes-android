package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.URLSpanNoUnderline;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Praveen_Singh on 08-02-2017.
 */

public class ArticleDetailHolder extends BaseViewHolder<ArticleDetailPojo> implements Html.ImageGetter {
    private final String TAG = LogUtils.makeLogTag(ArticleCardHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_HTML_TAG = "<b><font color='#333333'>";
    private static final String RIGHT_HTML_TAG = "<font color='#868788'>";
    private static final String END_HTML_TAG = "</font></b>";
    private static final String END_HTML_FONT_TAG = "</font>";
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    DateUtil mDateUtil;
    @Bind(R.id.iv_article_detail_card_circle_icon)
    CircleImageView ivArticleDetailCardCircleIcon;
    @Bind(R.id.tv_article_detail_header)
    TextView tvArticleDetailHeader;
    @Bind(R.id.tv_article_detail_reaction1)
    TextView tvFeedArticleDetailReaction1;
    @Bind(R.id.tv_article_detail_reaction2)
    TextView tvFeedArticleDetailReaction2;
    @Bind(R.id.tv_article_detail_reaction3)
    TextView tvFeedArticleDetailReaction3;
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
    Bitmap bitmap;
    InputStream is;

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
        tvArticleDetailUserReactionText.setEnabled(true);
        if (null != mFeedDetail) {
            mFeedDetail.setLastReactionValue(mFeedDetail.getReactionValue());
            imageOperations(context);
            allTextViewStringOperations(context);
        }

        if(dataItem != null && null != dataItem.getFeedDetail() && StringUtil.isNotNullOrEmptyString(dataItem.getFeedDetail().getNameOrTitle()) && null != userPreference && userPreference.isSet() && null != userPreference.get() && userPreference.get().getUserSummary() !=null){
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(AppConstants.IMPRESSIONS,AppConstants.ARTICLE_IMPRSSION, dataItem.getId() + AppConstants.DASH +userPreference.get().getUserSummary().getUserId() + AppConstants.DASH + dataItem.getFeedDetail().getNameOrTitle() );
        }

    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void imageOperations(Context context) {

        String feedCircleIconUrl = mFeedDetail.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivArticleDetailCardCircleIcon.setCircularImage(true);
            ivArticleDetailCardCircleIcon.bindImage(feedCircleIconUrl);
        }
        String description = mFeedDetail.getDescription();
        if (StringUtil.isNotNullOrEmptyString(description)) {
            Spanned spanned = Html.fromHtml(description, this, null);
            tvHtmlData.setText(spanned);
            tvHtmlData.setMovementMethod(LinkMovementMethod.getInstance());
        }
     //   stripUnderlines(tvHtmlData);
    }
    private void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            tvArticleDetailHeader.setText(mFeedDetail.getNameOrTitle());
        }
        if (StringUtil.isNotEmptyCollection(mFeedDetail.getTags())) {
            List<String> tags = mFeedDetail.getTags();
            //String mergeTags = AppConstants.EMPTY_STRING;
            StringBuilder mergeTags = new StringBuilder();
            boolean isFirst = true;
            for (String tag : tags) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    mergeTags.append(AppConstants.COMMA).append(AppConstants.SPACE);
                }
                mergeTags.append(tag);
            }
            StringBuilder tagHeader = new StringBuilder();
            tagHeader.append(LEFT_HTML_TAG).append(mContext.getString(R.string.ID_TAGS)).
                    append(END_HTML_TAG).append(RIGHT_HTML_TAG).append(AppConstants.COLON).append(AppConstants.SPACE).append(mergeTags).append(END_HTML_FONT_TAG);
            //   String tagHeader = LEFT_HTML_TAG + mContext.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleDetailTag.setText(Html.fromHtml(tagHeader.toString(), 0)); // for 24 api and more
            } else {
                tvArticleDetailTag.setText(Html.fromHtml(tagHeader.toString()));// or for older api
            }
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorName())) {
            tvArticleDetailIconName.setText(mFeedDetail.getAuthorName());
        }
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
            ivArticleDetailRegisterUserPic.setCircularImage(true);
            ivArticleDetailRegisterUserPic.bindImage(userPreference.get().getUserSummary().getPhotoUrl());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getPostedDate())) {
            //  long createdDate = mDateUtil.getTimeInMillis(mFeedDetail.getCreatedDate(), AppConstants.DATE_FORMAT);
            //    tvArticleDetailTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            tvArticleDetailTime.setText(mFeedDetail.getPostedDate());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorShortDescription())) {
            String description = mFeedDetail.getAuthorShortDescription().trim();
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleDetailDescription.setText(Html.fromHtml(description, 0)); // for 24 api and more
            } else {
                tvArticleDetailDescription.setText(Html.fromHtml(description));// or for older api
            }
        }
        if (mFeedDetail.getNoOfLikes() < AppConstants.ONE_CONSTANT && mFeedDetail.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flArticleDetailNoReactionComment.setVisibility(View.GONE);
        }
        if (mFeedDetail.getNoOfComments() > AppConstants.THREE_CONSTANT) {
            tvArticleDetailViewMore.setVisibility(View.VISIBLE);
        } else {
            tvArticleDetailViewMore.setVisibility(View.GONE);
        }
        tvFeedArticleDetailReaction1.setVisibility(View.VISIBLE);
        tvFeedArticleDetailReaction2.setVisibility(View.VISIBLE);
        tvFeedArticleDetailReaction3.setVisibility(View.VISIBLE);

        switch (mFeedDetail.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mFeedDetail.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    flArticleDetailNoReactionComment.setVisibility(View.VISIBLE);
                    tvArticleDetailTotalReaction.setVisibility(View.GONE);
                    tvFeedArticleDetailReaction1.setVisibility(View.INVISIBLE);
                    tvFeedArticleDetailReaction2.setVisibility(View.INVISIBLE);
                    tvFeedArticleDetailReaction3.setVisibility(View.INVISIBLE);
                    tvArticleDetailTotalReplies.setVisibility(View.VISIBLE);
                } else {
                    flArticleDetailNoReactionComment.setVisibility(View.GONE);
                }
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
        switch (mFeedDetail.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mFeedDetail.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    flArticleDetailNoReactionComment.setVisibility(View.VISIBLE);
                    tvArticleDetailTotalReaction.setVisibility(View.VISIBLE);
                    tvFeedArticleDetailReaction1.setVisibility(View.VISIBLE);
                    tvFeedArticleDetailReaction2.setVisibility(View.VISIBLE);
                    tvFeedArticleDetailReaction3.setVisibility(View.VISIBLE);
                    tvArticleDetailTotalReplies.setVisibility(View.INVISIBLE);
                } else {
                    flArticleDetailNoReactionComment.setVisibility(View.GONE);
                }
                userComments();
                break;
            case AppConstants.ONE_CONSTANT:
                tvArticleDetailTotalReplies.setText(String.valueOf(mFeedDetail.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                tvArticleDetailTotalReplies.setVisibility(View.VISIBLE);
                liArticleDetailUserComments.setVisibility(View.VISIBLE);
                userComments();
                break;
            default:
                tvArticleDetailTotalReplies.setText(String.valueOf(mFeedDetail.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                tvArticleDetailTotalReplies.setVisibility(View.VISIBLE);
                liArticleDetailUserComments.setVisibility(View.VISIBLE);
                userComments();
        }
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
            List<Comment> lastCommentList = mFeedDetail.getLastComments();
            for (int index = 0; index < lastCommentList.size(); index++) {
                switch (index) {
                    case AppConstants.NO_REACTION_CONSTANT:
                        ivArticleDetailUserPic.setCircularImage(true);
                        liArticleDetailUserComments.setVisibility(View.VISIBLE);
                        liArticleDetailUserCommentsSecond.setVisibility(View.GONE);
                        liArticleDetailUserCommentsThird.setVisibility(View.GONE);
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
                        liArticleDetailUserCommentsThird.setVisibility(View.GONE);
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
        userReactionLongPress();
        return true;
    }

    @OnLongClick(R.id.tv_feed_article_detail_reaction_text)
    public boolean userReactionLongByTextClick() {
        userReactionLongPress();
        return true;
    }

    private void userReactionLongPress() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        mFeedDetail.setLongPress(true);
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserReaction);
        ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_CHANGED_REACTIONS, GoogleAnalyticsEventActions.CHANGED_REACTIONS_ON_ARTICLE, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.tv_article_detail_user_reaction)
    public void userReactionClick() {
        userReactionWithoutLong();
    }

    @OnClick(R.id.tv_feed_article_detail_reaction_text)
    public void userReactionByTextClick() {
        userReactionWithoutLong();
    }

    private void userReactionWithoutLong() {
        tvArticleDetailUserReaction.setEnabled(false);
        tvArticleDetailUserReactionText.setEnabled(false);
        mFeedDetail.setLongPress(false);
        mFeedDetail.setItemPosition(getAdapterPosition());
        if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(mFeedDetail, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.userCommentLikeRequest(mFeedDetail, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ARTICLE, AppConstants.EMPTY_STRING);
        }
        if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            tvArticleDetailUserReactionText.setText(AppConstants.EMPTY_STRING);
        } else {
            mFeedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvArticleDetailUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
            tvArticleDetailUserReactionText.setText(mContext.getString(R.string.ID_LOVE));
        }
        allTextViewStringOperations(mContext);
    }

    @OnClick(R.id.tv_article_detail_user_comment)
    public void userCommentClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        mFeedDetail.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(mFeedDetail, liArticleDetailJoinConversation);
    }

    @OnClick(R.id.tv_article_detail_total_reactions)
    public void reactionClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        mFeedDetail.setCallFromName(mContext.getString(R.string.ID_REPLY));
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
        mFeedDetail.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(mFeedDetail, liArticleDetailJoinConversation);
    }

    @OnClick(R.id.tv_article_detail_view_more)
    public void viewMoreClick() {
        mFeedDetail.setItemPosition(getAdapterPosition());
        mFeedDetail.setCallFromName(AppConstants.EMPTY_STRING);
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

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = mContext.getResources().getDrawable(R.drawable.article_default);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }


    class LoadImage extends AsyncTask<Object, Void, Bitmap> {


        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                LogUtils.info("url=", source);
                is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (OutOfMemoryError oom) {
                // System.gc();
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inSampleSize = 2;
                    Bitmap bitmapFactory = BitmapFactory.decodeStream(is, null, options);
                    return BitmapFactory.decodeStream(is, null, options);
                } catch (Exception e) {

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = tvHtmlData.getText();
                tvHtmlData.setText(t);
            }
        }

    }
}
