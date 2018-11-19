package appliedlife.pvtltd.SHEROES.viewholder;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
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

    @Inject
    Preference<AppConfiguration> mConfiguration;
    // region ButterKnife Bindings
    @Bind(R.id.user_post_compact_card)
    CardView mUserCompactCard;

    @Bind(R.id.post_author_image)
    CircleImageView mPostAuthorImage;

    @Bind(R.id.author_verified_icon)
    ImageView mAuthorVerifiedIcon;

    @Bind(R.id.bade_icon)
    ImageView mBadgeIcon;

    @Bind(R.id.last_comment_badge_icon)
    ImageView mLastCommentBadgeIcon;

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

    @Bind(R.id.fl_spam_post_ui)
    FrameLayout spamPostUi;

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

    @Bind(R.id.spam_comment_ui)
    RelativeLayout spamCommentUi;

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

    @BindDimen(R.dimen.dp_size_40)
    int authorProfileSize;

    @BindDimen(R.dimen.dp_size_30)
    int commentAuthorProfileSize;


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
            String authorThumborUrl = CommonUtil.getThumborUri(userPostSolrObj.getAuthorImageUrl(), authorProfileSize, authorProfileSize);
            mPostAuthorImage.bindImage(authorThumborUrl);
        }
        if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null) {
            mJoinConversation.setText(mConfiguration.get().configData.mCommentHolderText);
        } else {
            mJoinConversation.setText(new ConfigData().mCommentHolderText);
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
            mPostDescription.setVisibility(View.VISIBLE);
            if (mUserPostObj.isHasMention()) {
                List<MentionSpan> mentionSpanList = mUserPostObj.getUserMentionList();
                if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
                    showUserMentionName(listDescription, mentionSpanList);
                } else {
                    mPostDescription.setText(hashTagColorInString(listDescription), TextView.BufferType.SPANNABLE);
                }
            } else {
                mPostDescription.setText(hashTagColorInString(listDescription), TextView.BufferType.SPANNABLE);
            }

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

        if (userPostSolrObj.isSpamPost()) {  //Spam Post 
            spamPostUi.setVisibility(View.VISIBLE);
        } else {
            spamPostUi.setVisibility(View.GONE);

            if (CommonUtil.isEmpty(userPostSolrObj.getLastComments())) {
                return;
            }
            Comment comment = userPostSolrObj.getLastComments().get(0);
            invalidateSpamLastComment(comment);
        }
    }

    private void invalidateSpamLastComment(Comment comment) {
        if (comment != null && comment.isSpamComment()) {
            spamCommentUi.setVisibility(View.VISIBLE);
            mLastCommentContainer.setVisibility(View.GONE);
        } else {
            spamCommentUi.setVisibility(View.GONE);
            mLastCommentContainer.setVisibility(View.VISIBLE);
        }
    }


    private void setImage() {
        mPostDescription.setLines(1);
        if (!CommonUtil.isEmpty(mUserPostObj.getImageUrls())) {
            mImageContainer.setVisibility(View.VISIBLE);
            if (mUserPostObj.getImageUrls().size() >= 2) {
                mImageFirst.setVisibility(View.VISIBLE);
                mImageSecond.setVisibility(View.VISIBLE);
                mSecondImageContainer.setVisibility(View.VISIBLE);
                String imageFirstKitUrl = CommonUtil.getThumborUri(mUserPostObj.getImageUrls().get(0), CommonUtil.getWindowWidth(mContext) / 2, mAuthorPicSize);
                Glide.with(mContext)
                        .asBitmap()
                        .load(imageFirstKitUrl)
                        .into(mImageFirst);

                mImageFirst.setVisibility(View.VISIBLE);
                String imageSecondKitUrl = CommonUtil.getThumborUri(mUserPostObj.getImageUrls().get(1), CommonUtil.getWindowWidth(mContext) / 2, mAuthorPicSize);
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
                String imageKitUrl = CommonUtil.getThumborUri(mUserPostObj.getImageUrls().get(0), CommonUtil.getWindowWidth(mContext), mAuthorPicSize);
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
            String communityName = mUserPostObj.communityId == 0 ? acceptPostText + " " + mContext.getString(R.string.challenge) : mUserPostObj.getPostCommunityName();
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

                CommonUtil.showHideUserBadge(context, mUserPostObj.isAnonymous(), mBadgeIcon, mUserPostObj.isBadgeShownOnPic(), mUserPostObj.getProfilePicBadgeUrl());
                boolean isMentor;
                if (mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                    isMentor = true;
                    String header;
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                         header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                    }
                    clickOnUserNameAndCommunityName(header, feedTitle, communityName, isMentor);
                } else {
                    isMentor = false;
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        String header = mContext.getString(R.string.post_header_name_community, feedTitle, communityName);
                        clickOnUserNameAndCommunityName(header, feedTitle, communityName, isMentor);
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        String header = mContext.getString(R.string.post_header_community, feedTitle);
                        clickOnCommunityName(header, feedTitle);
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        String header = mContext.getString(R.string.post_header_name_community, feedTitle, communityName);
                        clickOnUserNameAndCommunityName(header, feedTitle, communityName, isMentor);
                    }
                }

            }
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(mUserPostObj.getCreatedDate(), AppConstants.DATE_FORMAT);
            mPostRelativeTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate, mContext));
        } else {
            mPostRelativeTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }
    }

    private void clickOnUserNameAndCommunityName(String userNameAndCommunity, String userName, String communityName, final boolean isMentor) {

        SpannableString spannableString = new SpannableString(userNameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.USER_POST) {
                    ((FeedItemCallback) viewInterface).onMentorProfileClicked(mUserPostObj);
                } else if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) {
                    if (isMentor) {
                        ((FeedItemCallback) viewInterface).onCommunityClicked(mUserPostObj.getCommunityId());
                    } else {
                        ((FeedItemCallback) viewInterface).onCommunityClicked(mUserPostObj.getIdOfEntityOrParticipant());
                    }
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
                if (isMentor) {
                    if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.USER_POST) {
                        ((FeedItemCallback) viewInterface).onMentorProfileClicked(mUserPostObj);
                    } else if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) {
                        ((FeedItemCallback) viewInterface).onCommunityClicked(mUserPostObj.getCommunityId());
                    }
                } else {
                    if (!mUserPostObj.isAnonymous() && (mUserPostObj.getCommunityId() != 0 || mUserPostObj.getCommunityId() != AppConstants.SHEROES_EVENT_ID)) {
                        ((FeedItemCallback) viewInterface).onCommunityClicked(mUserPostObj.getCommunityId());
                    }
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(userName)) {
            spannableString.setSpan(authorTitle, 0, userName.length(), 0);
            StyleSpan boldNameSpan = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(boldNameSpan, 0, userName.length(), 0);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, userName.length(), 0);
            if (StringUtil.isNotNullOrEmptyString(userNameAndCommunity)) {
                int firstIndex = userNameAndCommunity.indexOf(communityName);
                spannableString.setSpan(community, firstIndex, firstIndex + communityName.length(), 0);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), firstIndex, firstIndex + communityName.length(), 0);
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                spannableString.setSpan(boldSpan, firstIndex, firstIndex + communityName.length(), 0);
            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(spannableString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);
        }
    }

    private void clickOnCommunityName(String nameAndCommunity, String communityName) {

        SpannableString spannableString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) { //community
                    viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                } else if (!mUserPostObj.isAnonymous() && viewInterface instanceof FeedItemCallback) {
                    ((FeedItemCallback) viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                } else {
                    if (!mUserPostObj.isAnonymous()) {
                        viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    }
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(communityName)) {
            spannableString.setSpan(authorTitle, 0, communityName.length(), 0);
            if (!communityName.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (mUserPostObj.isAuthorMentor()) {
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
                } else {
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
                }
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                spannableString.setSpan(boldSpan, 0, communityName.length(), 0);
            } else {
                StyleSpan regularSpan = new StyleSpan(Typeface.NORMAL);
                spannableString.setSpan(regularSpan, 0, communityName.length(), 0);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(spannableString, TextView.BufferType.SPANNABLE);
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
            String linkImageUrl = CommonUtil.getThumborUri(mUserPostObj.getOgImageUrlS(), CommonUtil.getWindowWidth(mContext), mLinkImageHeight);
            Glide.with(mContext)
                    .asBitmap()
                    .load(linkImageUrl)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                            mLinkImage.setVisibility(View.VISIBLE);
                            mLinkImage.setImageBitmap(profileImage);
                            if (mUserPostObj.isOgVideoLinkB()) {
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

            CommonUtil.showHideUserBadge(mContext, lastComment.isAnonymous(), mLastCommentBadgeIcon, lastComment.isBadgeShown(), lastComment.getBadgeUrl());
            invalidateCommentLike(lastComment);
            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    String authorThumborUrl = CommonUtil.getThumborUri(lastComment.getParticipantImageUrl(), commentAuthorProfileSize, commentAuthorProfileSize);
                    mCommentAuthorImage.bindImage(authorThumborUrl);
                    mCommentAuthorName.setText(lastComment.getParticipantName());
                    mCommentDescription.setText(hashTagColorInString(lastComment.getComment()));
                    mCommentAuthorVerifiedIcon.setVisibility(View.GONE);
                    invalidateCommentLike(lastComment);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getComment()) && StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    String authorThumborUrl = CommonUtil.getThumborUri(lastComment.getParticipantImageUrl(), authorProfileSize, authorProfileSize);
                    mCommentAuthorImage.bindImage(authorThumborUrl);
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
                mCommentRelativeTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate, mContext));
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
                String authorThumborUrl = CommonUtil.getThumborUri(userPreference.get().getUserSummary().getPhotoUrl(), commentAuthorProfileSize, commentAuthorProfileSize);
                mConversationAuthorImageConversation.setCircularImage(true);
                mConversationAuthorImageConversation.bindImage(authorThumborUrl);
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
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active_16dp, 0, 0, 0);
        } else {
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_inactive_16dp, 0, 0, 0);
        }
    }


    private void invalidatePostLike(UserPostSolrObj userPostSolrObj) {
        if (mUserPostObj.getReactedValue() == AppConstants.NO_REACTION_CONSTANT) {
            mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
        } else {
            mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
        }

    }

    @OnClick(R.id.fl_spam_post_ui)
    public void spamPostCLick() {
    }

    @OnClick(R.id.post_author_image)
    public void onUserPicClick() {
        if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == 14) {
            ((FeedItemCallback) viewInterface).onMentorProfileClicked(mUserPostObj);
        } else if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == 15) {
            ((FeedItemCallback) viewInterface).onCommunityClicked(mUserPostObj.getCommunityId());
        }
    }

    //Last comment user name or user pic
    @OnClick({R.id.comment_author_name, R.id.comment_author_image_container})
    public void onLastCommentUserClick() {
        ((FeedItemCallback) viewInterface).onFeedLastCommentUserClicked(mUserPostObj);
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
                mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onUserPostUnLiked(mUserPostObj);
            } else {
                mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mPostLikeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
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
            }
        }
    }

    @OnClick(R.id.post_share_button)
    public void onPostShareClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostShared(mUserPostObj);
        }
    }

    private void showUserMentionName(String description, List<MentionSpan> mentionSpanList) {
        StringBuilder strWithAddExtra = new StringBuilder(description);
        for (int i = 0; i < mentionSpanList.size(); i++) {
            final MentionSpan mentionSpan = mentionSpanList.get(i);
            if (null != mentionSpan && null != mentionSpan.getMention()) {
                if (mentionSpan.getMention().getStartIndex() >= 0 && mentionSpan.getMention().getStartIndex() + i < strWithAddExtra.length()) {
                    strWithAddExtra.insert(mentionSpan.getMention().getStartIndex() + i, '@');
                }
            }
        }
        SpannableString spannableString = new SpannableString(strWithAddExtra);
        for (int i = 0; i < mentionSpanList.size(); i++) {
            final MentionSpan mentionSpan = mentionSpanList.get(i);
            if (null != mentionSpan && null != mentionSpan.getMention()) {
                if (mentionSpan.getMention().getStartIndex() >= 0 && mentionSpan.getMention().getEndIndex() > 0 && mentionSpan.getMention().getEndIndex() + i + 1 <= spannableString.length() && mentionSpan.getMention().getStartIndex() + i <= spannableString.length()) {
                    int start = mentionSpan.getMention().getStartIndex() + i;
                    int end = mentionSpan.getMention().getEndIndex() + i;
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.user_tagg)), start, end + 1, 0);
                }
            }
        }

        mPostDescription.setMovementMethod(LinkMovementMethod.getInstance());
        mPostDescription.setText(hashTagColorInString(spannableString), TextView.BufferType.SPANNABLE);
    }
}
