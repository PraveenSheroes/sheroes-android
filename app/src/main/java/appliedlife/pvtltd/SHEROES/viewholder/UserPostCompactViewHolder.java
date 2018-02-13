package appliedlife.pvtltd.SHEROES.viewholder;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
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

    // region ButterKnife Bindings
    @Bind(R.id.user_post_compact_card)
    CardView mUserCompactCard;

    @Bind(R.id.author_image)
    CircleImageView mAuthorImage;

    @Bind(R.id.author_verified_icon)
    ImageView mAuthorVerifiedIcon;

    @Bind(R.id.post_title)
    TextView mTitle;

    @Bind(R.id.post_relative_time)
    TextView mPostRelativeTime;

    @Bind(R.id.post_description)
    TextView mPostDescription;

    @Bind(R.id.link_detail_container)
    RelativeLayout mLinkContainer;

    @Bind(R.id.link_image)
    ImageView mLinkImage;

    @Bind(R.id.play_icon)
    ImageView mPlayIcon;

    @Bind(R.id.link_description)
    TextView mLinkDescription;

    @Bind(R.id.like_count)
    TextView mLikeCount;

    @Bind(R.id.comments_count)
    TextView mCommentsCount;

    private BaseHolderInterface viewInterface;
    private UserPostSolrObj mUserPostObj;

    // endregion

    public UserPostCompactViewHolder(View itemView, Context context, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = context;
        this.viewInterface = baseHolderInterface;
    }

    public void bindData(UserPostSolrObj userPostSolrObj, Context context) {
        if (mDateUtil == null) {
            mDateUtil = DateUtil.getInstance();
        }
        mUserPostObj = userPostSolrObj;
        if (CommonUtil.isNotEmpty(userPostSolrObj.getAuthorImageUrl())) {
            mAuthorImage.setCircularImage(true);
            mAuthorImage.bindImage(userPostSolrObj.getAuthorImageUrl());
        }


        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, userPostSolrObj.getNoOfLikes());
        mLikeCount.setText(String.valueOf(userPostSolrObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));

        String pluralComments;
        if (userPostSolrObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfAnswers, userPostSolrObj.getNoOfComments());
        } else {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, userPostSolrObj.getNoOfComments());
        }
        mCommentsCount.setText(String.valueOf(userPostSolrObj.getNoOfComments() + AppConstants.SPACE + pluralComments));

        final String listDescription = userPostSolrObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            mPostDescription.setText("");
            mPostDescription.setVisibility(View.GONE);
            return;
        } else {
            mPostDescription.setText(hashTagColorInString(listDescription));
            mPostDescription.setVisibility(View.VISIBLE);
        }
        linkifyURLs(mPostDescription);

        allTextViewStringOperations(mContext);
        setLinkData();
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getAuthorName())) {
            StringBuilder posted = new StringBuilder();
            String feedTitle = mUserPostObj.getAuthorName();
            String acceptPostText = mUserPostObj.getChallengeAcceptPostTextS()==null ? "" :mUserPostObj.getChallengeAcceptPostTextS();
            String feedCommunityName = mUserPostObj.communityId == 0 ? acceptPostText + " " + "Challenge" :mUserPostObj.getPostCommunityName();
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
            } else {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            }

            if (StringUtil.isNotNullOrEmptyString(postedIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(postedInClick, feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)), feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                SpanString.setSpan(new StyleSpan(Typeface.NORMAL), feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)), feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
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
            } else {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            }

            if (StringUtil.isNotNullOrEmptyString(postedIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)), feedTitle.length(), feedTitle.length() + postedIn.length() + 1, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new StyleSpan(Typeface.NORMAL), feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)), feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
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
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)), feedTitle.length(), nameAndCommunity.length(), 0);
                SpanString.setSpan(new StyleSpan(Typeface.NORMAL), feedTitle.length(), nameAndCommunity.length(), 0);

            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);
        }
    }

    private void setLinkData() {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgTitleS())) {
            mLinkDescription.setText(mUserPostObj.getOgTitleS());
        }
    /*    if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgDescriptionS())) {
            tvLinkSubTitle.setText(mUserPostObj.getOgDescriptionS());
        }*/
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgImageUrlS())) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mUserPostObj.getOgImageUrlS())
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

    @OnClick(R.id.user_post_compact_card)
    public void onUserCardClicked() {
        ((FeedItemCallback)viewInterface).onUserPostClicked(mUserPostObj);
    }
}
