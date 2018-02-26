package appliedlife.pvtltd.SHEROES.viewholder;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.f2prateek.rx.preferences2.Preference;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.hashTagColorInString;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by ujjwal on 02/02/18.
 */

public class UserPostCompactViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    @Inject
    DateUtil mDateUtil;

    @Inject
    Preference<LoginResponse> userPreference;

    // region ButterKnife Bindings
    @Bind(R.id.user_post_compact_card)
    CardView mUserCompactCard;

    @Bind(R.id.post_author_image)
    CircleImageView mPostAuthorImage;

    @Bind(R.id.author_verified_icon)
    ImageView mAuthorVerifiedIcon;

    @Bind(R.id.post_title)
    TextView mTitle;

    @Bind(R.id.post_relative_time)
    TextView mPostRelativeTime;

    @Bind(R.id.post_description)
    TextView mPostDescription;

    @Bind(R.id.image_container)
    RelativeLayout mImageContainer;

    @Bind(R.id.image_first)
    ImageView mImageFirst;

    @Bind(R.id.image_second)
    ImageView mImageSecond;

    @Bind(R.id.second_image_container)
    RelativeLayout mSecondImageContainer;

    @Bind(R.id.more_image_count)
    TextView mMoreImageCount;

    @Bind(R.id.link_detail_container)
    RelativeLayout mLinkContainer;

    @Bind(R.id.link_image)
    ImageView mLinkImage;

    @Bind(R.id.play_icon)
    ImageView mPlayIcon;

    @Bind(R.id.link_description)
    TextView mLinkDescription;

    @Bind(R.id.link_sub_title)
    TextView mLinkSubTitle;

    @Bind(R.id.post_like_count)
    TextView mPostLikeCount;

    @Bind(R.id.post_comments_count)
    TextView mPostCommentsCount;

    @Bind(R.id.comment_container)
    RelativeLayout mCommentContainer;

    @Bind(R.id.post_like_button)
    TextView mPostLikeButton;

    @Bind(R.id.post_comment_button)
    TextView mPostCommentButton;

    @Bind(R.id.post_share_button)
    TextView mPostShareButton;

    @Bind(R.id.last_comment_container)
    RelativeLayout mLastCommentContainer;

    @Bind(R.id.comment_author_image)
    CircleImageView mCommentAuthorImage;

    @Bind(R.id.comment_author_verified_icon)
    ImageView mCommentAuthorVerifiedIcon;

    @Bind(R.id.comment_author_name)
    TextView mCommentAuthorName;

    @Bind(R.id.comment_description)
    TextView mCommentDescription;

    @Bind(R.id.comment_relative_time)
    TextView mCommentRelativeTime;

    @Bind(R.id.comment_like)
    TextView mCommentLike;

    @Bind(R.id.join_conversation_container)
    RelativeLayout mJoinConversationContainer;

    @Bind(R.id.conversation_author_image_conversation)
    CircleImageView mConversationAuthorImageConversation;

    @Bind(R.id.conversation_author_verified_icon)
    ImageView mConversationAuthorVerifiedIcon;

    @Bind(R.id.join_conversation)
    TextView mJoinConversation;

    @BindDimen(R.dimen.dp_size_150)
    int mAuthorPicSize;

    @BindDimen(R.dimen.dp_size_100)
    int mLinkImageHeight;


    private BaseHolderInterface viewInterface;
    private UserPostSolrObj mUserPostObj;

    // endregion

    public UserPostCompactViewHolder(View itemView, Context context, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        this.mContext = context;
        this.viewInterface = baseHolderInterface;
    }

    public void bindData(UserPostSolrObj userPostSolrObj, Context context) {
        if (mDateUtil == null) {
            mDateUtil = DateUtil.getInstance();
        }
        mUserPostObj = userPostSolrObj;
        if (CommonUtil.isNotEmpty(userPostSolrObj.getAuthorImageUrl())) {
            mPostAuthorImage.setCircularImage(true);
            mPostAuthorImage.bindImage(userPostSolrObj.getAuthorImageUrl());
        }


        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, userPostSolrObj.getNoOfLikes());
        mPostLikeCount.setText(String.valueOf(userPostSolrObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));

        String pluralComments;
        if (userPostSolrObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfAnswers, userPostSolrObj.getNoOfComments());
        } else {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, userPostSolrObj.getNoOfComments());
        }
        mPostCommentsCount.setText(String.valueOf(userPostSolrObj.getNoOfComments() + AppConstants.SPACE + pluralComments));

        final String listDescription = userPostSolrObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            mPostDescription.setText("");
            mPostDescription.setVisibility(View.VISIBLE);
        } else {
            mPostDescription.setText(hashTagColorInString(listDescription));
            mPostDescription.setVisibility(View.VISIBLE);
        }
        linkifyURLs(mPostDescription);

        allTextViewStringOperations(mContext);

        if (!CommonUtil.isEmpty(mUserPostObj.getImageUrls())) {
            mLinkContainer.setVisibility(View.GONE);
            mImageContainer.setVisibility(View.VISIBLE);
            mCommentContainer.setVisibility(View.GONE);
            mPostDescription.setMaxLines(1);
            setImage();
        } else if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgRequestedUrlS())) {
            mLinkContainer.setVisibility(View.VISIBLE);
            mImageContainer.setVisibility(View.GONE);
            mCommentContainer.setVisibility(View.GONE);
            mPostDescription.setMaxLines(1);
            setLinkData();
        } else {
            mPostDescription.setMaxLines(2);
            mLinkContainer.setVisibility(View.GONE);
            mImageContainer.setVisibility(View.GONE);
            mCommentContainer.setVisibility(View.VISIBLE);
            userComments();

        }

        invalidatePostLike(userPostSolrObj);

    }


    private void setImage() {
        mPostDescription.setLines(1);
        if (!CommonUtil.isEmpty(mUserPostObj.getImageUrls())) {
            mImageContainer.setVisibility(View.VISIBLE);
            if (mUserPostObj.getImageUrls().size() >= 2) {
                mImageFirst.setVisibility(View.VISIBLE);
                mImageSecond.setVisibility(View.VISIBLE);
                mSecondImageContainer.setVisibility(View.VISIBLE);
                String imageFirstKitUrl = CommonUtil.getImgKitUri(mUserPostObj.getImageUrls().get(0), CommonUtil.getWindowWidth(mContext)/2, mAuthorPicSize);
                Glide.with(mContext)
                        .asBitmap()
                        .load(imageFirstKitUrl)
                        .into(mImageFirst);

                mImageFirst.setVisibility(View.VISIBLE);
                String imageSecondKitUrl = CommonUtil.getImgKitUri(mUserPostObj.getImageUrls().get(1), CommonUtil.getWindowWidth(mContext)/2, mAuthorPicSize);
                Glide.with(mContext)
                        .asBitmap()
                        .load(imageSecondKitUrl)
                        .into(mImageSecond);

                if (mUserPostObj.getImageUrls().size() > 2) {
                    mMoreImageCount.setVisibility(View.VISIBLE);
                    mMoreImageCount.setText("+ " + Integer.toString(mUserPostObj.getImageUrls().size() - 1));
                    mImageSecond.setBackgroundColor(mContext.getResources().getColor(R.color.feed_article_label));
                } else {
                    mMoreImageCount.setVisibility(View.GONE);
                    mImageSecond.setBackgroundColor(Color.TRANSPARENT);
                }
            } else {
                mImageSecond.setVisibility(View.GONE);
                mSecondImageContainer.setVisibility(View.GONE);
                mImageFirst.setVisibility(View.VISIBLE);
                String imageKitUrl = CommonUtil.getImgKitUri(mUserPostObj.getImageUrls().get(0), CommonUtil.getWindowWidth(mContext), mAuthorPicSize);
                Glide.with(mContext)
                        .asBitmap()
                        .load(imageKitUrl)
                        .into(mImageFirst);
            }
        } else {
            mImageContainer.setVisibility(View.GONE);
            mCommentContainer.setVisibility(View.VISIBLE);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getAuthorName())) {
            StringBuilder posted = new StringBuilder();
            String feedTitle = mUserPostObj.getAuthorName();
            String acceptPostText = mUserPostObj.getChallengeAcceptPostTextS() == null ? "" : mUserPostObj.getChallengeAcceptPostTextS();
            String feedCommunityName = mUserPostObj.communityId == 0 ? acceptPostText + " " + "Challenge" : mUserPostObj.getPostCommunityName();
            if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
                if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                    if (mUserPostObj.isAuthorMentor()) {
                        mAuthorVerifiedIcon.setVisibility(View.VISIBLE);
                    } else {
                        mAuthorVerifiedIcon.setVisibility(View.GONE);
                    }
                } else {
                    mAuthorVerifiedIcon.setVisibility(View.GONE);
                }

                if (mUserPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                        clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                        clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                    }

                } else if (mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_ASKED)).append(AppConstants.SPACE);
                        posted.append(feedCommunityName);
                        clickOnMentorName(posted.toString(), feedTitle, mContext.getString(R.string.ID_ASKED));
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_ASKED)).append(AppConstants.SPACE);
                        clickOnMentorName(posted.toString(), feedTitle, mContext.getString(R.string.ID_ASKED));
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_ASKED)).append(AppConstants.SPACE);
                        posted.append(feedCommunityName);
                        clickOnMentorName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_IN));
                    }
                } else {
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_IN)).append(AppConstants.SPACE);
                        posted.append(feedCommunityName);
                        clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_IN));
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED)).append(AppConstants.SPACE);
                        clickOnCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED));
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_IN)).append(AppConstants.SPACE);
                        posted.append(feedCommunityName);
                        clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_IN));
                    }
                }

            }
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(mUserPostObj.getCreatedDate(), AppConstants.DATE_FORMAT);
            mPostRelativeTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        } else {
            mPostRelativeTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }
    }

    private void clickOnMentorAndCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                if (!mUserPostObj.isAnonymous()) {
                    // mPostDetailCallback.onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        ClickableSpan postedInClick = new ClickableSpan() {
            @Override
            public void onClick(View textView) {


            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };

        ClickableSpan community = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //  mPostDetailCallback.onCommunityTitleClicked(mUserPostObj);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };

        if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (mUserPostObj.isAuthorMentor()) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                } else {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                }
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                SpanString.setSpan(typefaceSpan, 0, feedTitle.length(), 0);
            } else {
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                SpanString.setSpan(typefaceSpan, 0, feedTitle.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, feedTitle.length(), 0);
            }

            if (StringUtil.isNotNullOrEmptyString(postedIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(postedInClick, feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                SpanString.setSpan(typefaceSpan, feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                TypefaceSpan typefaceSpanCommunity = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                SpanString.setSpan(typefaceSpanCommunity, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);

                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);

        }
    }

    private void clickOnMentorName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (!mUserPostObj.isAnonymous()) {
                    // mPostDetailCallback.onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };

        ClickableSpan community = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (mUserPostObj.isAuthorMentor()) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                } else {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                }
                TypefaceSpan typefaceSpanCommunity = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                SpanString.setSpan(typefaceSpanCommunity, 0, feedTitle.length(), 0);
            } else {
                TypefaceSpan typefaceSpanCommunity = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                SpanString.setSpan(typefaceSpanCommunity, 0, feedTitle.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, feedTitle.length(), 0);
            }

            if (StringUtil.isNotNullOrEmptyString(postedIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length(), feedTitle.length() + postedIn.length() + 1, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);

                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                SpanString.setSpan(typefaceSpan, feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);

                TypefaceSpan typefaceSpanCommunity = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                SpanString.setSpan(typefaceSpanCommunity, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);

                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);

        }
    }

    private void clickOnCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                // TODO : ujjwal
                // viewInterface.handleOnClick(mUserPostObj, mTitle);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        ClickableSpan postedInClick = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };


        if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            if (StringUtil.isNotNullOrEmptyString(postedIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(postedInClick, feedTitle.length(), nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length(), nameAndCommunity.length(), 0);
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                SpanString.setSpan(typefaceSpan, feedTitle.length(), nameAndCommunity.length(), 0);
            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);
        }
    }

    private void setLinkData() {
        mPostDescription.setLines(1);
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgTitleS())) {
            mLinkDescription.setText(mUserPostObj.getOgTitleS());
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgDescriptionS())) {
            mLinkSubTitle.setText(mUserPostObj.getOgDescriptionS());
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgImageUrlS())) {
            String linkImageUrl = CommonUtil.getImgKitUri(mUserPostObj.getOgImageUrlS(), CommonUtil.getWindowWidth(mContext), mLinkImageHeight);
            Glide.with(mContext)
                    .asBitmap()
                    .load(linkImageUrl)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            //fmImageThumb.setLayoutParams(params);
                            mLinkImage.setVisibility(View.VISIBLE);
                            mLinkImage.setImageBitmap(profileImage);
                            //  pbLink.setVisibility(View.GONE);
                            if (mUserPostObj.isOgVideoLinkB()) {
                                mPlayIcon.getBackground().setAlpha(75);
                                mPlayIcon.setVisibility(View.VISIBLE);
                            } else {
                                mPlayIcon.setVisibility(View.GONE);
                            }
                        }
                    });
            mLinkContainer.setVisibility(View.VISIBLE);
        } else {
            mLinkContainer.setVisibility(View.GONE);
        }
    }

    private void userComments() {
        List<Comment> lastCommentList = mUserPostObj.getLastComments();
        Comment lastComment;
        mPostDescription.setLines(2);
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mLastCommentContainer.setVisibility(View.VISIBLE);
            mJoinConversationContainer.setVisibility(View.GONE);
            int mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            mCommentAuthorImage.setCircularImage(true);
            invalidateCommentLike(lastComment);
            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    mCommentAuthorImage.bindImage(lastComment.getParticipantImageUrl());
                    mCommentAuthorName.setText(lastComment.getParticipantName());
                    mCommentDescription.setText(hashTagColorInString(lastComment.getComment()));
                    mCommentAuthorVerifiedIcon.setVisibility(View.GONE);
                    invalidateCommentLike(lastComment);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getComment()) && StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    mCommentAuthorImage.bindImage(lastComment.getParticipantImageUrl());
                    mCommentAuthorName.setText(lastComment.getParticipantName());
                    mCommentDescription.setText(hashTagColorInString(lastComment.getComment()));
                    if (!lastComment.getParticipantName().equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        if (lastComment.isVerifiedMentor()) {
                            mCommentAuthorVerifiedIcon.setVisibility(View.VISIBLE);
                        } else {
                            mCommentAuthorVerifiedIcon.setVisibility(View.GONE);
                        }
                    } else {
                        mCommentAuthorVerifiedIcon.setVisibility(View.GONE);
                    }

                }
            }
            linkifyURLs(mCommentDescription);
            if (StringUtil.isNotNullOrEmptyString(lastComment.getLastModifiedOn())) {
                long createdDate = mDateUtil.getTimeInMillis(lastComment.getLastModifiedOn(), AppConstants.DATE_FORMAT);
                mCommentRelativeTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            } else {
                mCommentRelativeTime.setText(mContext.getString(R.string.ID_JUST_NOW));
            }
            if (lastComment.isMyOwnParticipation()) {
                mCommentRelativeTime.setVisibility(View.VISIBLE);
            } else {
                mCommentRelativeTime.setVisibility(View.GONE);
            }
        } else {
            mLastCommentContainer.setVisibility(View.GONE);
            mJoinConversationContainer.setVisibility(View.VISIBLE);
            if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
                mConversationAuthorImageConversation.setCircularImage(true);
                mConversationAuthorImageConversation.bindImage(userPreference.get().getUserSummary().getPhotoUrl());
            }
        }

    }


    private void invalidateCommentLike(Comment lastComment) {
        mCommentLike.setVisibility(View.VISIBLE);
        if (lastComment.likeCount == 0) {
            mCommentLike.setText("");
        } else {
            mCommentLike.setText(Integer.toString(lastComment.likeCount));
        }
        if (lastComment.isLiked) {
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active_16dp, 0, 0, 0);
        } else {
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_inactive_16dp, 0, 0, 0);
        }
    }


    private void invalidatePostLike(UserPostSolrObj userPostSolrObj) {
        if (mUserPostObj.getReactedValue() == AppConstants.NO_REACTION_CONSTANT) {
            mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
        } else {
            mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
        }

    }

    @OnClick(R.id.user_post_compact_card)
    public void onUserCardClicked() {
        ((FeedItemCallback) viewInterface).onUserPostClicked(mUserPostObj);
    }

    @OnClick(R.id.post_like_button)
    public void userReactionClick() {
        if (viewInterface instanceof FeedItemCallback) {
            if (mUserPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                mUserPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onUserPostUnLiked(mUserPostObj);
            } else {
                mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onUserPostLiked(mUserPostObj);
            }
            String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, mUserPostObj.getNoOfLikes());
            mPostLikeCount.setText(String.valueOf(mUserPostObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));
        }
    }

    @OnClick(R.id.comment_like)
    public void onCommentLikeClicked() {
        List<Comment> lastCommentList = mUserPostObj.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            int mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            lastComment.setItemPosition(mUserPostObj.getItemPosition());
            if (lastComment.isLiked) {
                lastComment.isLiked = false;
                lastComment.likeCount--;
            } else {
                lastComment.isLiked = true;
                lastComment.likeCount++;
            }
            invalidateCommentLike(lastComment);
            if (viewInterface instanceof FeedItemCallback) {
                if (lastComment.isLiked) {
                    ((FeedItemCallback) viewInterface).userCommentLikeRequest(mUserPostObj, true, getAdapterPosition());
                } else {
                    ((FeedItemCallback) viewInterface).userCommentLikeRequest(mUserPostObj, false, getAdapterPosition());
                }
            } else {
                if (!lastComment.isLiked) {
                    viewInterface.userCommentLikeRequest(lastComment, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
                } else {
                    viewInterface.userCommentLikeRequest(lastComment, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
                }
            }

        }
    }

    @OnClick(R.id.post_share_button)
    public void onPostShareClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostShared(mUserPostObj);
        }
    }
}
