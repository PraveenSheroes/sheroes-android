package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.VideoPlayActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.hashTagColorInString;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by Praveen_Singh on 22-01-2017.
 */

public class FeedCommunityPostHolder extends BaseViewHolder<FeedDetail> {
    private static final String LEFT_POSTED = "<font color='#8a8d8e'>";
    private static final String RIGHT_POSTED = "</font>";
    private final String TAG = LogUtils.makeLogTag(FeedCommunityPostHolder.class);
    @Inject
    DateUtil mDateUtil;
    @Inject
    Preference<LoginResponse> userPreference;
    private static final String LEFT_HTML_TAG = "<font color='#3c3c3c'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    //spam handling
    @Bind(R.id.fl_spam_post_ui)
    FrameLayout flSpamPostUi;
    @Bind(R.id.tv_review_description)
    TextView tvReviewDescription;
    @Bind(R.id.li_approve_delete)
    LinearLayout liApproveDelete;
    @Bind(R.id.tv_delete_spam_post)
    TextView tvDeleteSpamPost;
    @Bind(R.id.tv_approve_spam_post)
    TextView tvApproveSpamPost;


    @Bind(R.id.li_community_post_main_layout)
    LinearLayout liCommunityPostMainLayout;


    //Communitypost handling
    @Bind(R.id.li_feed_community_post_user_comments)
    LinearLayout liFeedCommunityPostUserComments;
    @Bind(R.id.li_feed_community_user_post_images)
    LinearLayout liFeedCommunityUserPostImages;
    @Bind(R.id.iv_feed_community_post_circle_icon)
    CircleImageView ivFeedCommunityPostCircleIcon;
    @Bind(R.id.iv_feed_community_post_login_user_pic)
    CircleImageView ivFeedCommunityPostLoginUserPic;
    @Bind(R.id.tv_feed_community_post_login_user_name)
    TextView tvFeedCommunityPostLoginUserName;
    @Bind(R.id.iv_feed_community_post_circle_icon_verified)
    ImageView ivFeedCommunityPostCircleIconVerified;
    @Bind(R.id.iv_feed_community_post_user_icon_verified)
    ImageView ivFeedCommunityPostUserIconVerified;
    @Bind(R.id.tv_feed_community_post_user_share)
    TextView tvFeedCommunityPostUserShare;
    @Bind(R.id.tv_feed_community_post_user_reaction)
    TextView tvFeedCommunityPostUserReaction;
    @Bind(R.id.tv_feed_community_post_user_comment)
    TextView tvFeedCommunityPostUserComment;
    @Bind(R.id.tv_feed_community_post_view_more)
    TextView tvFeedCommunityPostViewMore;
    @Bind(R.id.tv_feed_community_post_user_comment_post_view_more)
    TextView tvFeedCommunityPostUserCommentPostViewMore;
    @Bind(R.id.iv_feed_community_post_user_pic)
    CircleImageView ivFeedCommunityPostUserPic;
    @Bind(R.id.tv_feed_community_post_user_bookmark)
    TextView tvFeedCommunityPostUserBookmark;
    @Bind(R.id.tv_feed_community_post_card_title)
    TextView tvFeedCommunityPostCardTitle;
    @Bind(R.id.tv_feed_community_post_time)
    TextView tvFeedCommunityPostTime;
    @Bind(R.id.tv_feed_community_post_text)
    TextView tvFeedCommunityPostText;
    @Bind(R.id.tv_feed_community_post_user_menu)
    TextView tvFeedCommunityPostUserMenu;
    @Bind(R.id.tv_spam_post_menu)
    TextView tvSpamPostMenu;
    @Bind(R.id.tv_feed_community_post_reaction1)
    TextView tvFeedCommunityPostReaction1;
    @Bind(R.id.tv_feed_community_post_total_reactions)
    TextView tvFeedCommunityPostTotalReactions;
    @Bind(R.id.tv_feed_community_post_total_replies)
    TextView tvFeedCommunityPostTotalReplies;
    @Bind(R.id.tv_feed_community_post_user_comment_post)
    TextView tvFeedCommunityPostUserCommentPost;
    @Bind(R.id.tv_feed_community_post_user_name)
    TextView tvFeedCommunityPostUserName;
    @Bind(R.id.line_for_no_image)
    View lineForNoImage;

    @Bind(R.id.rl_feed_community_post_no_reaction_comments)
    RelativeLayout rlFeedCommunityPostNoReactionComment;
    @Bind(R.id.tv_feed_community_post_user_comment_post_menu)
    TextView tvFeedCommunityPostUserCommentPostMenu;
    @Bind(R.id.tv_feed_community_post_user_comment_post_time)
    TextView tvFeedCommunityPostUserCommentPostTime;
    @Bind(R.id.progress_bar_post_link)
    ProgressBar pbLink;

    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.fm_image_thumb)
    FrameLayout fmImageThumb;
    @Bind(R.id.iv_post_link_thumbnail)
    ImageView ivLinkThumbnail;
    @Bind(R.id.li_post_link_render)
    LinearLayout liViewLinkRender;
    @Bind(R.id.tv_post_link_title)
    TextView tvLinkTitle;
    @Bind(R.id.tv_post_link_sub_title)
    TextView tvLinkSubTitle;


    //Organisation type post in communityPost
    @Bind(R.id.rl_comm_post_org_details)
    RelativeLayout rlOrgCompanyFeedCard;
    @Bind(R.id.iv_company_icon_community_post)
    ImageView ivCompanyThumbnailCommPost;
    @Bind(R.id.tv_review_company_title_community_post)
    TextView tvCompanyNameCommPost;
    @Bind(R.id.line_for_organsiation_text)
    View orgCommPostSeparateLine;

    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private Context mContext;
    private int mItemPosition;
    private long mUserId;
    private String loggedInUser;
    private int mAdminId;
    private String mPhotoUrl;
    private Handler mHandler;

    public FeedCommunityPostHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            mUserId = userPreference.get().getUserSummary().getUserId();
            if (null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
                mPhotoUrl = userPreference.get().getUserSummary().getPhotoUrl();
            }
            String first = userPreference.get().getUserSummary().getFirstName();
            String last = userPreference.get().getUserSummary().getLastName();
            if (StringUtil.isNotNullOrEmptyString(first) || StringUtil.isNotNullOrEmptyString(last)) {
                loggedInUser = first + AppConstants.SPACE + last;
            }
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        dataItem.setItemPosition(position);
        normalCommunityPostUi(mUserId, mAdminId);
        if (dataItem.isSpamPost()) {
            handlingSpamUi(mUserId, mAdminId);
        } else {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        }
    }

    private void normalCommunityPostUi(long userId, int adminId) {
        liCommunityPostMainLayout.setVisibility(View.VISIBLE);
        tvFeedCommunityPostUserBookmark.setEnabled(true);
        tvFeedCommunityPostUserReaction.setTag(true);
        dataItem.setLastReactionValue(dataItem.getReactionValue());
        if (!dataItem.isTrending()) {
            imageOperations(mContext);
            if (StringUtil.isNotNullOrEmptyString(dataItem.getOgRequestedUrlS())) {
                liFeedCommunityUserPostImages.removeAllViews();
                liFeedCommunityUserPostImages.removeAllViewsInLayout();
                liFeedCommunityUserPostImages.setVisibility(View.GONE);
                liViewLinkRender.setVisibility(View.VISIBLE);
                setLinkData();
            } else {
                liViewLinkRender.setVisibility(View.GONE);
            }
            multipleImageURLs();
            populatePostText();
        }
        onBookMarkClick();
        allTextViewStringOperations(mContext);
        likeCommentOps();
        if (dataItem.getAuthorId() == userId || dataItem.isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
            tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
            if (dataItem.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
                tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
                tvFeedCommunityPostUserBookmark.setVisibility(View.VISIBLE);
            } else {
                tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
                tvFeedCommunityPostUserBookmark.setVisibility(View.GONE);
            }
        } else {
            tvFeedCommunityPostUserBookmark.setVisibility(View.VISIBLE);
            tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.li_post_link_render)
    public void tvLinkClick() {
        if (null != dataItem) {
            if (dataItem.isOgVideoLinkB() && StringUtil.isNotNullOrEmptyString(dataItem.getOgRequestedUrlS())) {
                if (!dataItem.getOgRequestedUrlS().contains(AppConstants.USER_YOU_TUBE) || !dataItem.getOgRequestedUrlS().contains(AppConstants.CHANNEL_YOU_TUBE)) {
                    Intent youTube = new Intent(mContext, VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.YOUTUBE_VIDEO_CODE, dataItem.getOgRequestedUrlS());
                    youTube.putExtras(bundle);
                    mContext.startActivity(youTube);
                }
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataItem.getOgRequestedUrlS()));
                mContext.startActivity(browserIntent);
            }
        }
    }

    private void setLinkData() {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getOgTitleS())) {
            tvLinkTitle.setText(dataItem.getOgTitleS());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getOgDescriptionS())) {
            tvLinkSubTitle.setText(dataItem.getOgDescriptionS());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getOgImageUrlS())) {
            Glide.with(mContext)
                    .load(dataItem.getOgImageUrlS()).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            fmImageThumb.setLayoutParams(params);
                            ivLinkThumbnail.setVisibility(View.VISIBLE);
                            ivLinkThumbnail.setImageBitmap(profileImage);
                            pbLink.setVisibility(View.GONE);
                            if (dataItem.isOgVideoLinkB()) {
                                ivPlay.setVisibility(View.VISIBLE);
                            } else {
                                ivPlay.setVisibility(View.GONE);
                            }
                        }
                    });
            liViewLinkRender.setVisibility(View.VISIBLE);
        } else {
            liViewLinkRender.setVisibility(View.GONE);
        }
    }

    private void multipleImageURLs() {
        if (dataItem.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
            tvFeedCommunityPostText.setVisibility(View.GONE);
            liFeedCommunityUserPostImages.removeAllViews();
            liFeedCommunityUserPostImages.removeAllViewsInLayout();
            liFeedCommunityUserPostImages.setVisibility(View.VISIBLE);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.challenge_image, null);
            ImageView ivChallenge = (ImageView) child.findViewById(R.id.iv_feed_challenge);
            TextView tvChallengePost = (TextView) child.findViewById(R.id.tv_challenge_name_post);
            if (StringUtil.isNotEmptyCollection(dataItem.getImageUrls())) {
                tvChallengePost.setText(AppConstants.EMPTY_STRING);
                Glide.with(mContext)
                        .load(dataItem.getImageUrls().get(0))
                        .into(ivChallenge);
            } else {
                dataItem.setListDescription(AppConstants.EMPTY_STRING);
                if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
                    tvChallengePost.setText(dataItem.getNameOrTitle());
                }
                ivChallenge.setBackgroundResource(R.drawable.challenge_post);
            }
            liFeedCommunityUserPostImages.addView(child);
        } else {
            tvFeedCommunityPostText.setVisibility(View.VISIBLE);
            if (StringUtil.isNotEmptyCollection(dataItem.getImageUrls())) {
                liFeedCommunityUserPostImages.setVisibility(View.VISIBLE);
                List<String> coverImageList = dataItem.getImageUrls();
                int listSize = coverImageList.size();
                if (listSize > AppConstants.NO_REACTION_CONSTANT) {
                    switch (listSize) {
                        case AppConstants.ONE_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0))) {
                                feedAlbum(mContext, coverImageList.get(0), null, null, 1);
                            }
                            break;
                        case AppConstants.TWO_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1))) {
                                feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), null, 2);
                            }
                            break;
                        case AppConstants.THREE_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2))) {
                                feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), 3);
                            }
                            break;
                        case AppConstants.FOURTH_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                                feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), 4);

                            }
                            break;
                        default:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                                feedAlbum(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), listSize);
                            }
                    }
                }
            } else {
                liFeedCommunityUserPostImages.removeAllViews();
                liFeedCommunityUserPostImages.removeAllViewsInLayout();
                liFeedCommunityUserPostImages.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    private void onBookMarkClick() {
        if (dataItem.isBookmarked()) {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            StringBuilder posted = new StringBuilder();
            if (dataItem.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
                tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                String feedTitle = dataItem.getAuthorName();
                posted.append(feedTitle).append(AppConstants.SPACE).append(LEFT_POSTED).append(mContext.getString(R.string.ID_HAS_ACCEPTED)).append(RIGHT_POSTED).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_HAS_ACCEPTED_CHALLENGE));
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostCardTitle.setText(Html.fromHtml(posted.toString(), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostCardTitle.setText(Html.fromHtml(posted.toString()));// or for older api
                }
            } else {
                String feedTitle = dataItem.getAuthorName();
                String feedCommunityName = dataItem.getPostCommunityName();
                if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        if (dataItem.isAuthorMentor()) {
                            ivFeedCommunityPostCircleIconVerified.setVisibility(View.VISIBLE);
                        } else {
                            ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                        }
                    } else {
                        ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                    }

                    if (dataItem.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                        rlOrgCompanyFeedCard.setVisibility(View.VISIBLE);
                        if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                            posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                            clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                        } else {
                            feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                            posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                            clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                        }
                        if (!dataItem.isTrending()) {
                            if (StringUtil.isNotNullOrEmptyString(dataItem.getSolrIgnorePostCommunityLogo())) {
                                Glide.with(context)
                                        .load(dataItem.getSolrIgnorePostCommunityLogo())
                                        .into(ivCompanyThumbnailCommPost);
                            }
                        }
                        tvCompanyNameCommPost.setText(feedCommunityName);
                        orgCommPostSeparateLine.setVisibility(View.VISIBLE);

                    } else {
                        rlOrgCompanyFeedCard.setVisibility(View.GONE);
                        orgCommPostSeparateLine.setVisibility(View.GONE);
                        if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                            posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_IN)).append(AppConstants.SPACE);
                            posted.append(feedCommunityName);
                            clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_IN));
                        } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                            feedTitle = dataItem.getPostCommunityName();
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
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvFeedCommunityPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        }


    }

    private void likeCommentOps() {
        if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT && dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            rlFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
            lineForNoImage.setVisibility(View.GONE);
        }
        tvFeedCommunityPostReaction1.setVisibility(View.VISIBLE);
        switch (dataItem.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:

                if (dataItem.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                    lineForNoImage.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReactions.setVisibility(View.GONE);
                    tvFeedCommunityPostReaction1.setVisibility(View.INVISIBLE);
                    tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                } else {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
                    lineForNoImage.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                userLike();
                break;
            default:
                rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                userLike();
        }
        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, dataItem.getNoOfLikes());
        tvFeedCommunityPostTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()+AppConstants.SPACE+pluralLikes));
        switch (dataItem.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (dataItem.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                    lineForNoImage.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostReaction1.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReplies.setVisibility(View.INVISIBLE);
                } else {
                    rlFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
                    lineForNoImage.setVisibility(View.GONE);
                }
                userComments();
                break;
            case AppConstants.ONE_CONSTANT:
                tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
                userComments();
                break;
            default:
                tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
                userComments();
        }
        String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, dataItem.getNoOfComments());
        tvFeedCommunityPostTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()+AppConstants.SPACE+pluralComments));
    }

    private void populatePostText() {
        final String listDescription = dataItem.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            tvFeedCommunityPostText.setVisibility(View.GONE);
            return;
        }else
        {
            tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tvFeedCommunityPostText.setMaxLines(Integer.MAX_VALUE);
                tvFeedCommunityPostText.setText(hashTagColorInString(listDescription));
                linkifyURLs(tvFeedCommunityPostText);

                if (tvFeedCommunityPostText.getLineCount() > 4) {
                    collapseFeedPostText();
                } else {
                    tvFeedCommunityPostText.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                }
            }
        });
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void collapseFeedPostText() {
        tvFeedCommunityPostText.setMaxLines(4);
        tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvFeedCommunityPostViewMore.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
        } else {
            tvFeedCommunityPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
        }
        tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
    }

    private void expandFeedPostText() {
        tvFeedCommunityPostText.setMaxLines(Integer.MAX_VALUE);
        tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        tvFeedCommunityPostViewMore.setText(mContext.getString(R.string.ID_LESS));
        tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
    }

    private void userLike() {

        switch (dataItem.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
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
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + dataItem.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<Comment> lastCommentList = dataItem.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            ivFeedCommunityPostUserPic.setCircularImage(true);
            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    ivFeedCommunityPostUserPic.setImageResource(R.drawable.ic_anonomous);
                    tvFeedCommunityPostUserName.setText(lastComment.getParticipantName());
                    tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(lastComment.getComment()));
                    ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getComment()) && StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    ivFeedCommunityPostUserPic.bindImage(lastComment.getParticipantImageUrl());
                    tvFeedCommunityPostUserName.setText(lastComment.getParticipantName());
                    tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(lastComment.getComment()));
                    if (!lastComment.getParticipantName().equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        if (lastComment.isVerifiedMentor()) {
                            ivFeedCommunityPostUserIconVerified.setVisibility(View.VISIBLE);
                        } else {
                            ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                        }
                    } else {
                        ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                    }

                }
            }
            if(tvFeedCommunityPostUserCommentPost.getLineCount()>3)
            {
                tvFeedCommunityPostUserCommentPostViewMore.setVisibility(View.VISIBLE);
                String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostUserCommentPostViewMore.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
            }else
            {
                tvFeedCommunityPostUserCommentPostViewMore.setVisibility(View.GONE);
            }
            if (StringUtil.isNotNullOrEmptyString(lastComment.getLastModifiedOn())) {
                long createdDate = mDateUtil.getTimeInMillis(lastComment.getLastModifiedOn(), AppConstants.DATE_FORMAT);
                tvFeedCommunityPostUserCommentPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            }
            if (lastComment.isMyOwnParticipation()) {
                tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.VISIBLE);
            } else {
                tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.GONE);
            }
        } else {
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
            tvFeedCommunityPostTotalReplies.setVisibility(View.GONE);
        }

    }

    private void imageOperations(Context context) {
        String authorImageUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            ivFeedCommunityPostCircleIcon.setCircularImage(true);
            ivFeedCommunityPostCircleIcon.bindImage(authorImageUrl);
        }
        ivFeedCommunityPostLoginUserPic.setCircularImage(true);
        ivFeedCommunityPostLoginUserPic.bindImage(mPhotoUrl);
        if (StringUtil.isNotNullOrEmptyString(loggedInUser)) {
            tvFeedCommunityPostLoginUserName.setText(loggedInUser);
        }

    }

    private void feedAlbum(Context context, String firstImage, String secondImage, String thirdImage, int typeOfHolder) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_feed_album, null);

        final LinearLayout liFeedAlbum = (LinearLayout) child.findViewById(R.id.li_feed_album);
        int width = AppUtils.getWindowWidth(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (width * 2 / 3));
        liFeedAlbum.setLayoutParams(params);

        final LinearLayout liHolder = (LinearLayout) child.findViewById(R.id.li_holder);

        final ImageView ivFirst = (ImageView) child.findViewById(R.id.iv_first);

        final ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_second);

        final ImageView ivThird = (ImageView) child.findViewById(R.id.iv_third);
        final TextView tvMoreImage = (TextView) child.findViewById(R.id.tv_feed_community_more_image);
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
                int count = typeOfHolder - 3;
                tvMoreImage.setText(String.valueOf("+" + count));
                tvMoreImage.setVisibility(View.VISIBLE);
        }


        ivFirst.setOnClickListener(this);
        if (StringUtil.isNotNullOrEmptyString(firstImage)) {
            Glide.with(context)
                    .load(firstImage).asBitmap()
                    .placeholder(R.color.photo_placeholder)
                    .into(ivFirst);
        }

        if (StringUtil.isNotNullOrEmptyString(secondImage)) {
            ivSecond.setOnClickListener(this);

            Glide.with(context)
                    .load(secondImage).asBitmap()
                    .placeholder(R.color.photo_placeholder)
                    .into(ivSecond);
        }
        if (StringUtil.isNotNullOrEmptyString(thirdImage)) {
            ivThird.setOnClickListener(this);
            Glide.with(context)
                    .load(thirdImage).asBitmap()
                    .placeholder(R.color.photo_placeholder)
                    .into(ivThird);
        }
        liFeedCommunityUserPostImages.addView(child);
    }


    @OnClick(R.id.tv_feed_community_post_total_replies)
    public void repliesClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
    }

    @OnClick(R.id.tv_join_conversation)
    public void joinConversationClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
    }

    @OnClick(R.id.tv_feed_community_post_user_comment_post)
    public void recentCommentClick() {
        dataItem.setCallFromName(mContext.getString(R.string.ID_REPLY));
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
    }

    @OnClick(R.id.li_feed_community_user_post_images)
    public void communityPostImageClick() {
        viewInterface.dataOperationOnClick(dataItem);
    }


    @OnClick(R.id.li_feed_community_post_user_comments)
    public void openCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
    }

    @OnClick(R.id.tv_feed_community_post_user_comment)
    public void userCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
    }
    @OnClick(R.id.tv_feed_community_post_user_comment_post_view_more)
    public void userCommentViewMoreClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserComment);
    }
    @OnClick(R.id.tv_feed_community_post_user_menu)
    public void userMenuClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserMenu);
    }

    @OnClick(R.id.tv_spam_post_menu)
    public void spamMenuClick() {
        viewInterface.handleOnClick(dataItem, tvSpamPostMenu);
    }


    @OnClick(R.id.tv_feed_community_post_user_comment_post_menu)
    public void userCommentMenuClick() {
        dataItem.setNoOfOpenings(mItemPosition);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserCommentPostMenu);

    }

    @OnClick(R.id.tv_feed_community_post_view_more)
    public void onViewMoreClicked(){
        if (tvFeedCommunityPostViewMore.getText().equals(mContext.getString(R.string.ID_LESS))) {
            collapseFeedPostText();
        } else {
            expandFeedPostText();
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_first: {
                dataItem.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_second: {
                dataItem.setItemPosition(AppConstants.ONE_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_third: {
                dataItem.setItemPosition(AppConstants.TWO_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

        }
    }


    @OnClick(R.id.tv_feed_community_post_user_bookmark)
    public void isBookMarkClick() {
        dataItem.setTrending(true);
        tvFeedCommunityPostUserBookmark.setEnabled(false);
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserBookmark);
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserBookmark);
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        }
        if (!dataItem.isBookmarked()) {
            dataItem.setBookmarked(true);
        } else {
            dataItem.setBookmarked(false);
        }
        onBookMarkClick();
    }

    @OnClick(R.id.tv_feed_community_post_user_share)
    public void tvShareClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserShare);
        if (dataItem.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ORGANISATION_FEEDBACK_POST, dataItem.communityId + AppConstants.DASH + mUserId + AppConstants.DASH + dataItem.getIdOfEntityOrParticipant());
        } else {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        }
    }

    @OnClick(R.id.tv_feed_community_post_total_reactions)
    public void reactionClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostTotalReactions);
    }

    @OnClick(R.id.tv_feed_community_post_reaction1)
    public void reaction1Click() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostTotalReactions);
    }

    @OnClick(R.id.tv_feed_community_post_user_reaction)
    public void userReactionClick() {
        if ((Boolean) tvFeedCommunityPostUserReaction.getTag()) {
            userReactionWithouLongPress();
        }
    }


    private void userReactionWithouLongPress() {
        tvFeedCommunityPostUserReaction.setTag(false);
        dataItem.setTrending(true);
        dataItem.setLongPress(false);
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            if (dataItem.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ORGANISATION_FEEDBACK_POST, AppConstants.EMPTY_STRING);
            } else {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
            }
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            if (dataItem.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ORGANISATION_FEEDBACK_POST, AppConstants.EMPTY_STRING);

            } else {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_COMMUNITY_POST, AppConstants.EMPTY_STRING);
            }
        }
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            dataItem.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
        } else {
            dataItem.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);


        }
        likeCommentOps();
    }

    @OnClick(R.id.iv_feed_community_post_circle_icon)
    public void onFeedCommunityPostCircleIconClick() {
        if (dataItem.isAuthorMentor()) {
            viewInterface.championProfile(dataItem, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    private void clickOnMentorAndCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                if (dataItem.isAuthorMentor()) {
                    viewInterface.championProfile(dataItem, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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
                viewInterface.handleOnClick(dataItem, tvFeedCommunityPostCardTitle);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (dataItem.isAuthorMentor()) {
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
            tvFeedCommunityPostCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvFeedCommunityPostCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            tvFeedCommunityPostCardTitle.setSelected(true);

        }
    }

    private void clickOnCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                viewInterface.handleOnClick(dataItem, tvFeedCommunityPostCardTitle);
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
                SpanString.setSpan(new StyleSpan(Typeface.NORMAL),feedTitle.length(), nameAndCommunity.length(), 0);

            }
            tvFeedCommunityPostCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvFeedCommunityPostCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            tvFeedCommunityPostCardTitle.setSelected(true);
        }
    }

    private void handlingSpamUi(long userId, int adminId) {
        if (adminId == AppConstants.TWO_CONSTANT || dataItem.isCommunityOwner()) {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.VISIBLE);
            tvReviewDescription.setVisibility(View.GONE);
        } else if (dataItem.getAuthorId() == userId) {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.spam_post));
            liCommunityPostMainLayout.setAlpha(.2f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        } else {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.fl_spam_post_ui)
    public void onReviewDescriptionClick() {

    }

    @OnClick(R.id.tv_approve_spam_post)
    public void onApproveSpamPostClick() {
        viewInterface.handleOnClick(dataItem, tvApproveSpamPost);
    }

    @OnClick(R.id.tv_delete_spam_post)
    public void onDeleteSpamPostClick() {
        viewInterface.handleOnClick(dataItem, tvDeleteSpamPost);
    }


}