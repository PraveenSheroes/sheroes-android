package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.VideoPlayActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.hashTagColorInString;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by Praveen_Singh on 22-01-2017.
 */
public class UserPostHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(UserPostHolder.class);

    private static final float FOLLOW_BUTTON_ORIGINAL = 1.0f;
    private static final float FOLLOW_BUTTON_SEMI_TRANSPARENT = 0.3f;

    @Inject
    DateUtil mDateUtil;
    @Inject
    Preference<LoginResponse> userPreference;

    @Bind(R.id.view_container)
    LinearLayout viewContainer;

    @Bind(R.id.top_post_view)
    RelativeLayout topPostView;

    @Bind(R.id.author_pic_icon)
    CircleImageView mAuthorIcon;

    @Bind(R.id.bade_icon)
    ImageView mBadgeIcon;

    @Bind(R.id.author_verified_icon)
    ImageView mAuthorVerifiedIcon;

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.follow_button)
    TextView mFollowButton;

    @Bind(R.id.post_time)
    TextView mPostTime;

    @Bind(R.id.post_menu)
    TextView mPostMenu;

    @Bind(R.id.description)
    TextView mPostDescription;

    @Bind(R.id.li_post_link_render)
    LinearLayout liViewLinkRender;

    @Bind(R.id.fm_image_thumb)
    FrameLayout fmImageThumb;

    @Bind(R.id.iv_post_link_thumbnail)
    ImageView ivLinkThumbnail;

    @Bind(R.id.iv_play)
    ImageView ivPlay;

    @Bind(R.id.progress_bar_post_link)
    ProgressBar pbLink;

    @Bind(R.id.tv_post_link_title)
    TextView tvLinkTitle;

    @Bind(R.id.tv_post_link_sub_title)
    TextView tvLinkSubTitle;

    @Bind(R.id.user_post_images)
    LinearLayout userPostImages;

    @Bind(R.id.like_comment_count_view)
    RelativeLayout mLikeCommentCountCointainer;

    @Bind(R.id.like_heart_icon)
    TextView mLikeHeartIconForCount;

    @Bind(R.id.likes_count)
    TextView mLikesCount;

    @Bind(R.id.comment_counts)
    TextView mCommentsCount;

    @Bind(R.id.like_button)
    TextView mLikeButtonText;

    @Bind(R.id.comment_button)
    TextView mCommentButtonText;

    @Bind(R.id.share_button)
    TextView mShare;

    @Bind(R.id.fl_spam_post_ui)
    FrameLayout flSpamPostUi;

    @Bind(R.id.tv_review_description)
    TextView tvReviewDescription;

    @Bind(R.id.tv_spam_post_menu)
    TextView tvSpamPostMenu;

    @Bind(R.id.li_approve_delete)
    LinearLayout liApproveDelete;

    @Bind(R.id.tv_delete_spam_post)
    TextView tvDeleteSpamPost;

    @Bind(R.id.tv_approve_spam_post)
    TextView tvApproveSpamPost;

    @Bind(R.id.tv_source_name)
    TextView tvSourceName;

    @BindDimen(R.dimen.dp_size_40)
    int authorPicIconSize;

    private UserPostSolrObj mUserPostObj;
    private Context mContext;

    private long mUserId;
    private int mAdminId;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    private PostDetailCallBack mPostDetailCallback;

    private boolean isWhatappShareOption;

    public UserPostHolder(View itemView, PostDetailCallBack postDetailCallBack) {
        super(itemView);
        this.mPostDetailCallback = postDetailCallBack;
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            mUserId = userPreference.get().getUserSummary().getUserId();
            if (null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
        }
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareOption = "";
            shareOption = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareOption)) {
                if (shareOption.equalsIgnoreCase("true")) {
                    isWhatappShareOption = true;
                }
            }
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.mUserPostObj = (UserPostSolrObj) item;
        mContext = context;
        if (mUserPostObj.isTopPost()) {
            topPostView.setVisibility(View.VISIBLE);
        } else {
            topPostView.setVisibility(View.GONE);
        }
        mUserPostObj.setItemPosition(position);
        normalCommunityPostUi(mUserId, mAdminId);
        displayFollowUnFollowButton();

        if (mUserPostObj.isSpamPost()) {
            handlingSpamUi(mUserId, mAdminId);
        } else {
            viewContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            viewContainer.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void viewRecycled() {

    }

    private void displayFollowUnFollowButton() {
        if (mUserPostObj.isAnonymous() || mUserId == mUserPostObj.getAuthorId() || mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_MODERATOR_TYPE || mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) {
            mFollowButton.setVisibility(View.GONE);
        } else {
            if (!mUserPostObj.isSolrIgnoreIsUserFollowed()) {
                mFollowButton.setVisibility(View.VISIBLE);
                followButtonVisibility(mContext, false);
            } else {
                mFollowButton.setVisibility(View.GONE);
            }
        }
    }

    //Follow/Following button in leaderboard
    private void followButtonVisibility(Context context, boolean isFollowed) {
        if (isFollowed) {
            mFollowButton.setEnabled(false);
            mFollowButton.setAlpha(FOLLOW_BUTTON_SEMI_TRANSPARENT);
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.white));
            mFollowButton.setText(context.getString(R.string.following_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_grey_winner_dialog);
        } else {
            mFollowButton.setEnabled(true);
            mFollowButton.setAlpha(FOLLOW_BUTTON_ORIGINAL);
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.footer_icon_text));
            mFollowButton.setText(context.getString(R.string.follow_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }

    private void normalCommunityPostUi(long userId, int adminId) {
        viewContainer.setVisibility(View.VISIBLE);
        mLikeButtonText.setTag(true);
        mUserPostObj.setLastReactionValue(mUserPostObj.getReactionValue());
        imageOperations(mContext);
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgRequestedUrlS())) {
            userPostImages.removeAllViews();
            userPostImages.removeAllViewsInLayout();
            userPostImages.setVisibility(View.GONE);
            liViewLinkRender.setVisibility(View.VISIBLE);
            setLinkData();
        } else {
            liViewLinkRender.setVisibility(View.GONE);
        }
        multipleImageURLs();
        populatePostText();
        allTextViewStringOperations(mContext);
        likeCommentOps();
        mPostMenu.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.follow_button)
    public void onFollowedButtonClick() {
        followButtonVisibility(mContext, !mUserPostObj.isSolrIgnoreIsUserFollowed());
        mPostDetailCallback.onPostDetailsAuthorFollow(mUserPostObj);
    }

    @OnClick(R.id.li_post_link_render)
    public void tvLinkClick() {
        if (null != mUserPostObj) {
            if (mUserPostObj.isOgVideoLinkB() && StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgRequestedUrlS())) {
                if (!mUserPostObj.getOgRequestedUrlS().contains(AppConstants.USER_YOU_TUBE) || !mUserPostObj.getOgRequestedUrlS().contains(AppConstants.CHANNEL_YOU_TUBE)) {
                    Intent youTube = new Intent(mContext, VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.YOUTUBE_VIDEO_CODE, mUserPostObj.getOgRequestedUrlS());
                    youTube.putExtras(bundle);
                    mContext.startActivity(youTube);
                }
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUserPostObj.getOgRequestedUrlS()));
                mContext.startActivity(browserIntent);
            }
        }
    }

    private void setLinkData() {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgTitleS())) {
            tvLinkTitle.setText(mUserPostObj.getOgTitleS());
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgDescriptionS())) {
            tvLinkSubTitle.setText(mUserPostObj.getOgDescriptionS());
        }
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgImageUrlS())) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mUserPostObj.getOgImageUrlS())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            fmImageThumb.setLayoutParams(params);
                            ivLinkThumbnail.setVisibility(View.VISIBLE);
                            ivLinkThumbnail.setImageBitmap(profileImage);
                            pbLink.setVisibility(View.GONE);
                            if (mUserPostObj.isOgVideoLinkB()) {
                                ivPlay.setVisibility(View.VISIBLE);
                                tvSourceName.setVisibility(View.VISIBLE);
                            } else {
                                ivPlay.setVisibility(View.GONE);
                                tvSourceName.setVisibility(View.GONE);
                            }
                        }
                    });
            liViewLinkRender.setVisibility(View.VISIBLE);
        } else {
            liViewLinkRender.setVisibility(View.GONE);
        }
    }

    private void multipleImageURLs() {
        mPostDescription.setVisibility(View.VISIBLE);
        if (StringUtil.isNotEmptyCollection(mUserPostObj.getImageUrls())) {
            userPostImages.setVisibility(View.VISIBLE);
            List<String> coverImageList = mUserPostObj.getImageUrls();
            int listSize = coverImageList.size();
            if (listSize > AppConstants.NO_REACTION_CONSTANT) {
                switch (listSize) {
                    case AppConstants.ONE_CONSTANT:
                        userPostImages.removeAllViews();
                        userPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0))) {
                            feedAlbum(mContext, coverImageList.get(0), null, null, 1);
                        }
                        break;
                    case AppConstants.TWO_CONSTANT:
                        userPostImages.removeAllViews();
                        userPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), null, 2);
                        }
                        break;
                    case AppConstants.THREE_CONSTANT:
                        userPostImages.removeAllViews();
                        userPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), 3);
                        }
                        break;
                    case AppConstants.FOURTH_CONSTANT:
                        userPostImages.removeAllViews();
                        userPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), 4);

                        }
                        break;
                    default:
                        userPostImages.removeAllViews();
                        userPostImages.removeAllViewsInLayout();
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                            feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), listSize);
                        }
                }
            }
        } else {
            userPostImages.removeAllViews();
            userPostImages.removeAllViewsInLayout();
            userPostImages.setVisibility(View.GONE);
        }

    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getAuthorName())) {
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
                CommonUtil.showHideUserBadge(mContext, mUserPostObj.isAnonymous(), mBadgeIcon, mUserPostObj.isBadgeShownOnPic(), mUserPostObj.getProfilePicBadgeUrl());
                boolean isMentor;
                if (mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                    isMentor = true;
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        String header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                        clickOnUserNameAndCommunityName(header, feedTitle, communityName, isMentor);
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        String header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                        clickOnUserNameAndCommunityName(header, feedTitle, communityName, isMentor);
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        String header = mContext.getString(R.string.post_header_asked_community, feedTitle, communityName);
                        clickOnUserNameAndCommunityName(header, feedTitle, communityName, isMentor);
                    }
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
            mPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate, mContext));
        } else {
            mPostTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }
    }

    private void clickOnUserNameAndCommunityName(String userNameAndCommunity, String userName, String communityName, final boolean isMentor) {

        SpannableString spanString = new SpannableString(userNameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (isMentor) {
                    if (!mUserPostObj.isAnonymous()) {
                        mPostDetailCallback.onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    }
                } else {
                    if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) {
                        mPostDetailCallback.onCommunityTitleClicked(mUserPostObj);
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
                mPostDetailCallback.onCommunityTitleClicked(mUserPostObj);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(userName)) {
            spanString.setSpan(authorTitle, 0, userName.length(), 0);
            if (!userName.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (mUserPostObj.isAuthorMentor()) {
                    spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, userName.length(), 0);
                } else {
                    spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, userName.length(), 0);
                }
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                spanString.setSpan(typefaceSpan, 0, userName.length(), 0);
            } else {
                spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, userName.length(), 0);
            }

            if (StringUtil.isNotNullOrEmptyString(userNameAndCommunity)) {
                int firstIndex = userNameAndCommunity.indexOf(communityName);
                spanString.setSpan(community, firstIndex, firstIndex + communityName.length(), 0);
                spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), firstIndex, firstIndex + communityName.length(), 0);
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                spanString.setSpan(typefaceSpan, firstIndex, firstIndex + communityName.length(), 0);
            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(spanString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);
        }

    }

    private void clickOnCommunityName(String nameAndCommunity, String communityName) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (mUserPostObj != null && !mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) {
                    mPostDetailCallback.onCommunityTitleClicked(mUserPostObj);
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(communityName)) {
            SpanString.setSpan(authorTitle, 0, communityName.length(), 0);
            if (!communityName.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (mUserPostObj.isAuthorMentor()) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
                } else {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
                }
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                SpanString.setSpan(typefaceSpan, 0, communityName.length(), 0);
            } else {
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                SpanString.setSpan(typefaceSpan, 0, communityName.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, communityName.length(), 0);
            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);

        }
    }

    private void likeCommentOps() {
        if (mUserPostObj.getNoOfLikes() < AppConstants.ONE_CONSTANT && mUserPostObj.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
            mLikeCommentCountCointainer.setVisibility(View.GONE);
        }
        mLikeHeartIconForCount.setVisibility(View.VISIBLE);
        switch (mUserPostObj.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:

                if (mUserPostObj.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    mLikeCommentCountCointainer.setVisibility(View.VISIBLE);
                    mLikesCount.setVisibility(View.GONE);
                    mLikeHeartIconForCount.setVisibility(View.INVISIBLE);
                    mCommentsCount.setVisibility(View.VISIBLE);
                } else {
                    mLikeCommentCountCointainer.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                mLikeCommentCountCointainer.setVisibility(View.VISIBLE);
                mLikesCount.setVisibility(View.VISIBLE);
                userLike();
                break;
            default:
                mLikeCommentCountCointainer.setVisibility(View.VISIBLE);
                mLikesCount.setVisibility(View.VISIBLE);
                userLike();
        }
        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, mUserPostObj.getNoOfLikes());
        mLikesCount.setText(String.valueOf(mUserPostObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));
        switch (mUserPostObj.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mUserPostObj.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    mLikeCommentCountCointainer.setVisibility(View.VISIBLE);
                    mLikesCount.setVisibility(View.VISIBLE);
                    mLikeHeartIconForCount.setVisibility(View.VISIBLE);
                    mCommentsCount.setVisibility(View.INVISIBLE);
                } else {
                    mLikeCommentCountCointainer.setVisibility(View.GONE);
                }
                break;
            case AppConstants.ONE_CONSTANT:
                mCommentsCount.setVisibility(View.VISIBLE);
                break;
            default:
                mCommentsCount.setVisibility(View.VISIBLE);
        }
        String pluralComments;
        if (mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfAnswers, mUserPostObj.getNoOfComments());
        } else {
            pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, mUserPostObj.getNoOfComments());
        }
        mCommentsCount.setText(String.valueOf(mUserPostObj.getNoOfComments() + AppConstants.SPACE + pluralComments));
    }

    private void populatePostText() {
        if (isWhatappShareOption) {
            mShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.vector_share_card), null, null, null);
            mShare.setText(mContext.getString(R.string.ID_SHARE_ON_WHATS_APP));
        } else {
            mShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.vector_share_white_out), null, null, null);
            mShare.setText(mContext.getString(R.string.ID_SHARE));
        }
        final String listDescription = mUserPostObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            mPostDescription.setText("");
            mPostDescription.setVisibility(View.GONE);
            return;
        } else {
            mPostDescription.setText(hashTagColorInString(listDescription));
            mPostDescription.setVisibility(View.VISIBLE);
        }
        if (mUserPostObj.isHasMention()) {
            List<MentionSpan> mentionSpanList = mUserPostObj.getUserMentionList();
            if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
                showUserMentionName(listDescription, mentionSpanList);
            } else {
                mPostDescription.setText(hashTagColorInString(listDescription));
            }
        } else {
            mPostDescription.setText(hashTagColorInString(listDescription));
        }
        linkifyURLs(mPostDescription);
    }

    private void userLike() {

        switch (mUserPostObj.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + mUserPostObj.getReactionValue());
        }
    }

    private void imageOperations(Context context) {
        String authorImageUrl = mUserPostObj.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            mAuthorIcon.setCircularImage(true);
            if (mAuthorIcon != null && CommonUtil.isValidContextForGlide(mAuthorIcon.getContext())) {
                String authorThumborUrl = CommonUtil.getThumborUri(authorImageUrl, authorPicIconSize, authorPicIconSize);
                mAuthorIcon.bindImage(authorThumborUrl);
            }
        }
    }

    private void feedAlbum(Context context, String firstImage, String secondImage, String thirdImage, int typeOfHolder) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_feed_album, null);

        final LinearLayout liFeedAlbum = child.findViewById(R.id.li_feed_album);
        double imageRatio;
        if (CommonUtil.isEmpty(mUserPostObj.getImageRatio())) {
            imageRatio = AppConstants.MAX_IMAGE_RATIO;
        } else {
            imageRatio = mUserPostObj.getImageRatio().get(0);
        }
        if (imageRatio > AppConstants.MAX_IMAGE_RATIO) {
            imageRatio = AppConstants.MAX_IMAGE_RATIO;
        }
        int imageHeight = 0;
        if (CommonUtil.isNotEmpty(secondImage)) {
            imageHeight = (int) (((double) 2 / (double) 3) * CommonUtil.getWindowWidth(mContext));
        } else {
            imageHeight = (int) (imageRatio * CommonUtil.getWindowWidth(mContext));
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, imageHeight);
        liFeedAlbum.setLayoutParams(params);

        final LinearLayout liHolder = child.findViewById(R.id.li_holder);

        final ImageView ivFirst = child.findViewById(R.id.iv_first);

        final ImageView ivSecond = child.findViewById(R.id.iv_second);

        final ImageView ivThird = child.findViewById(R.id.iv_third);
        final TextView tvMoreImage = child.findViewById(R.id.tv_feed_community_more_image);
        tvMoreImage.setVisibility(View.GONE);
        switch (typeOfHolder) {
            case AppConstants.ONE_CONSTANT:
                LinearLayout.LayoutParams liHolderLayout = (LinearLayout.LayoutParams) liHolder.getLayoutParams();
                liHolderLayout.weight = 0;
                break;
            case AppConstants.TWO_CONSTANT:
                LinearLayout.LayoutParams firstImageLayout = (LinearLayout.LayoutParams) ivFirst.getLayoutParams();
                firstImageLayout.weight = 1;
                LinearLayout.LayoutParams secondImageLayout = (LinearLayout.LayoutParams) ivSecond.getLayoutParams();
                secondImageLayout.weight = 0;
                break;
            case AppConstants.THREE_CONSTANT:
                LinearLayout.LayoutParams liHolderLayoutDefault = (LinearLayout.LayoutParams) liHolder.getLayoutParams();
                liHolderLayoutDefault.weight = 1;
                LinearLayout.LayoutParams firstImageLayoutDefault = (LinearLayout.LayoutParams) ivFirst.getLayoutParams();
                firstImageLayoutDefault.weight = 2;
                LinearLayout.LayoutParams secondImageLayoutDefault = (LinearLayout.LayoutParams) ivSecond.getLayoutParams();
                secondImageLayoutDefault.weight = 1;
                break;
            default:
                int count = typeOfHolder - 2;
                tvMoreImage.setText(String.valueOf("+" + count));
                tvMoreImage.setVisibility(View.VISIBLE);
        }


        ivFirst.setOnClickListener(this);
        if (StringUtil.isNotNullOrEmptyString(firstImage)) {
            String firstThumborUrl = firstImage;
            if (typeOfHolder == 1) {
                firstThumborUrl = CommonUtil.getThumborUri(firstImage, CommonUtil.getWindowWidth(context), imageHeight);
            } else {
                firstThumborUrl = CommonUtil.getThumborUri(firstImage, CommonUtil.getWindowWidth(context) / 2, imageHeight);
            }
            Glide.with(context)
                    .load(firstThumborUrl)
                    .into(ivFirst);
        }

        if (StringUtil.isNotNullOrEmptyString(secondImage)) {
            ivSecond.setOnClickListener(this);
            String secondThumborUrl = "";
            if (typeOfHolder == 2) {
                secondThumborUrl = CommonUtil.getThumborUri(secondImage, CommonUtil.getWindowWidth(context), imageHeight);
            } else {
                secondThumborUrl = CommonUtil.getThumborUri(secondImage, CommonUtil.getWindowWidth(context), imageHeight / 2);
            }
            Glide.with(context)
                    .load(secondThumborUrl)
                    .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                    .into(ivSecond);
        }
        if (StringUtil.isNotNullOrEmptyString(thirdImage)) {
            ivThird.setOnClickListener(this);
            String thirdThumborUrl = CommonUtil.getThumborUri(thirdImage, CommonUtil.getWindowWidth(context), imageHeight / 2);
            Glide.with(context)
                    .load(thirdThumborUrl)
                    .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                    .into(ivThird);
        }
        userPostImages.addView(child);
    }

    @OnClick(R.id.user_post_images)
    public void communityPostImageClick() {
        mPostDetailCallback.onPostImageClicked(mUserPostObj);
    }

    @OnClick(R.id.post_menu)
    public void userMenuClick() {
        if (mUserPostObj.isSpamPost()) {
            return;
        }
        mPostDetailCallback.onPostMenuClicked(mUserPostObj, mPostMenu);
    }

    @OnClick(R.id.tv_spam_post_menu)
    public void spamMenuClick() {
        mPostDetailCallback.onPostMenuClicked(mUserPostObj, tvSpamPostMenu);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_first: {
                mUserPostObj.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                mPostDetailCallback.onPostImageClicked(mUserPostObj);
                break;
            }
            case R.id.iv_second: {
                mUserPostObj.setItemPosition(AppConstants.ONE_CONSTANT);
                mPostDetailCallback.onPostImageClicked(mUserPostObj);
                break;
            }
            case R.id.iv_third: {
                mUserPostObj.setItemPosition(AppConstants.TWO_CONSTANT);
                mPostDetailCallback.onPostImageClicked(mUserPostObj);
                break;
            }

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

        }
    }


    @OnClick(R.id.share_button)
    public void tvShareClick() {
        mPostDetailCallback.onShareButtonClicked(mUserPostObj, mShare);
        if (mUserPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ORGANISATION_FEEDBACK_POST, mUserPostObj.communityId + AppConstants.DASH + mUserId + AppConstants.DASH + mUserPostObj.getIdOfEntityOrParticipant());
        } else {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        }
    }

    @OnClick(R.id.like_button)
    public void userReactionClick() {
        if ((Boolean) mLikeButtonText.getTag()) {
            if (mUserPostObj.getReactedValue() != AppConstants.NO_REACTION_CONSTANT) {
                mUserPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_in_active, 0, 0, 0);
                mPostDetailCallback.onPostUnLikeClicked(mUserPostObj);
            } else {
                mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_heart_active, 0, 0, 0);
                mPostDetailCallback.onPostLikeClicked(mUserPostObj);
            }
        }
    }

    @OnClick(R.id.author_pic_icon)
    public void onFeedCommunityPostCircleIconClick() {
        if (!mUserPostObj.isAnonymous() && mUserPostObj.getEntityOrParticipantTypeId() == AppConstants.COMMUNITY_POST) {
            mPostDetailCallback.onCommunityTitleClicked(mUserPostObj);
        } else if (!mUserPostObj.isAnonymous()) {
            mPostDetailCallback.onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    private void handlingSpamUi(long userId, int adminId) {
        if (adminId == AppConstants.TWO_CONSTANT || mUserPostObj.isCommunityOwner()) {
            viewContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            viewContainer.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.VISIBLE);
            tvReviewDescription.setVisibility(View.GONE);
        } else if (mUserPostObj.getAuthorId() == userId) {
            viewContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.spam_post));
            viewContainer.setAlpha(.2f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        } else {
            viewContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            viewContainer.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.fl_spam_post_ui)
    public void onReviewDescriptionClick() {

    }

    @OnClick(R.id.tv_approve_spam_post)
    public void onApproveSpamPostClick() {
        mPostDetailCallback.onSpamApprovedClicked(mUserPostObj, tvApproveSpamPost);
    }

    @OnClick(R.id.tv_delete_spam_post)
    public void onDeleteSpamPostClick() {
        mPostDetailCallback.onSpamPostDeleteClicked(mUserPostObj, tvDeleteSpamPost);
    }

    @OnClick(R.id.comment_button)
    public void onCommentButtonClicked() {
        mPostDetailCallback.onCommentButtonClicked();
    }

    @OnClick(R.id.likes_count)
    public void onLikeCountClicked() {
        mPostDetailCallback.onLikeCountClicked(mUserPostObj);
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
                final ClickableSpan postedInClick = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        UserPostSolrObj userPostSolrObj = mUserPostObj;
                        userPostSolrObj.setCreatedBy(mentionSpan.getMention().getUserId());
                        if (mentionSpan.getMention().getUserType() == AppConstants.MENTOR_USER_TYPE_FOR_TAGGING) {
                            userPostSolrObj.setAuthorMentor(true);
                        } else {
                            userPostSolrObj.setAuthorMentor(false);
                        }
                        mPostDetailCallback.onChampionProfileClicked(userPostSolrObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);

                    }

                    @Override
                    public void updateDrawState(final TextPaint textPaint) {
                        textPaint.setUnderlineText(false);
                    }
                };
                if (mentionSpan.getMention().getStartIndex() >= 0 && mentionSpan.getMention().getEndIndex() > 0 && mentionSpan.getMention().getEndIndex() + i + 1 <= spannableString.length() && mentionSpan.getMention().getStartIndex() + i <= spannableString.length()) {
                    int start = mentionSpan.getMention().getStartIndex() + i;
                    int end = mentionSpan.getMention().getEndIndex() + i;
                    spannableString.setSpan(postedInClick, start, end + 1, 0);
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.user_tagg)), start, end + 1, 0);
                }
            }
        }

        mPostDescription.setMovementMethod(LinkMovementMethod.getInstance());
        mPostDescription.setText(hashTagColorInString(spannableString), TextView.BufferType.SPANNABLE);

        // tvMention.setSelected(true);
    }

}