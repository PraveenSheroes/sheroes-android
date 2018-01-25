package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.VideoPlayActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleView;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
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

    @Bind(R.id.card_view_post)
    CardView rootLayout;

    @Bind(R.id.top_post_view)
    RelativeLayout topPostView;

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

    @Bind(R.id.tv_join_conversation)
    TextView mJoinConveration;


    @Bind(R.id.li_community_post_main_layout)
    LinearLayout liCommunityPostMainLayout;


    //Communitypost handling
    @Bind(R.id.li_feed_community_post_user_comments)
    LinearLayout liFeedCommunityPostUserComments;

    @Bind(R.id.comment_like)
    TextView mCommentLike;

    @Bind(R.id.comment_like_count)
    TextView mCommentLikeCount;

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
    @Bind(R.id.ripple_feed_post_comment)
    RippleView rippleView;
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
    private UserPostSolrObj mUserPostObj;
    private Context mContext;
    private int mItemPosition;
    private long mUserId;
    private String loggedInUser;
    private int mAdminId;
    private String mPhotoUrl;
    private Handler mHandler;
    private boolean isWhatappShareOption=false;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Inject
    Preference<InstallUpdateForMoEngage> mInstallUpdatePreference;

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
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareOption = "";
            shareOption = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareOption)) {
                if (shareOption.equalsIgnoreCase("true")) {
                    isWhatappShareOption=true;
                }
            }
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.mUserPostObj = (UserPostSolrObj) item;
        mContext = context;
        if(mUserPostObj.isTopPost()){
            topPostView.setVisibility(View.VISIBLE);
        }else {
            topPostView.setVisibility(View.GONE);
        }
        mUserPostObj.setItemPosition(position);
        normalCommunityPostUi(mUserId, mAdminId);
        if (mUserPostObj.isSpamPost()) {
            handlingSpamUi(mUserId, mAdminId);
        } else {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        }

        if(!mUserPostObj.isAnonymous()) {
            showToolTip(context);
        }
    }

    private void showToolTip(Context context) {
        if (mInstallUpdatePreference.get() != null && mInstallUpdatePreference.get().isAppInstallFirstTime() && CommonUtil.ensureFirstTime(AppConstants.HOME_USER_NAME_PREF)) {
            LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.tooltip_arrow_right, null);
            FrameLayout.LayoutParams lps = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lps.setMargins(CommonUtil.convertDpToPixel(25, context), CommonUtil.convertDpToPixel(50, context), 0, 0);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(CommonUtil.convertDpToPixel(25, context), CommonUtil.convertDpToPixel(18, context));
            imageParams.gravity = Gravity.START;
            imageParams.setMargins(CommonUtil.convertDpToPixel(10, context), 0, 0, 0);
            TextView textView = (TextView) view.findViewById(R.id.tooltip_arrow);
            textView.setLayoutParams(imageParams);
            TextView text = (TextView) view.findViewById(R.id.title);
            text.setText(R.string.tool_tip_user_profile);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootLayout.removeView(view);
                }
            });
            rootLayout.addView(view, lps);
        }
    }

    @Override
    public void viewRecycled() {

    }

    private void normalCommunityPostUi(long userId, int adminId) {
        liCommunityPostMainLayout.setVisibility(View.VISIBLE);
        tvFeedCommunityPostUserBookmark.setEnabled(true);
        tvFeedCommunityPostUserReaction.setTag(true);
        mUserPostObj.setLastReactionValue(mUserPostObj.getReactionValue());

        imageOperations(mContext);

        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getOgRequestedUrlS())) {
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
        onBookMarkClick();
        allTextViewStringOperations(mContext);
        likeCommentOps();
        if (mUserPostObj.getAuthorId() == userId || mUserPostObj.isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
           // tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
            if (mUserPostObj.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
                //  tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
                // tvFeedCommunityPostUserBookmark.setVisibility(View.VISIBLE);
            } else {
                // tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
                // tvFeedCommunityPostUserBookmark.setVisibility(View.GONE);
                if (mUserPostObj.communityId == 0) {
                    // tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
                    tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.GONE);
                } else {
                    tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.VISIBLE);
                    // tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
                }
                if (mUserPostObj.communityId == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                    tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.GONE);
                } else {
                    tvFeedCommunityPostUserCommentPostMenu.setVisibility(View.VISIBLE);
                }
            }
        } else {
           // tvFeedCommunityPostUserBookmark.setVisibility(View.VISIBLE);
           // tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
        }

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
                    .load(mUserPostObj.getOgImageUrlS()).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            fmImageThumb.setLayoutParams(params);
                            ivLinkThumbnail.setVisibility(View.VISIBLE);
                            ivLinkThumbnail.setImageBitmap(profileImage);
                            pbLink.setVisibility(View.GONE);
                            if (mUserPostObj.isOgVideoLinkB()) {
                                ivPlay.getBackground().setAlpha(75);
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

        tvFeedCommunityPostText.setVisibility(View.VISIBLE);
        if (StringUtil.isNotEmptyCollection(mUserPostObj.getImageUrls())) {
            liFeedCommunityUserPostImages.setVisibility(View.VISIBLE);
            List<String> coverImageList = mUserPostObj.getImageUrls();
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

    private void onBookMarkClick() {
        if (mUserPostObj.isBookmarked()) {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
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
                        ivFeedCommunityPostCircleIconVerified.setVisibility(View.VISIBLE);
                    } else {
                        ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                    }
                } else {
                    ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                }

                if (mUserPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                    rlOrgCompanyFeedCard.setVisibility(View.VISIBLE);
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                        clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                        clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                    }
                    //if (!mUserPostObj.isTrending()) {
                    if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getSolrIgnorePostCommunityLogo())) {
                        Glide.with(context)
                                .load(mUserPostObj.getSolrIgnorePostCommunityLogo())
                                .into(ivCompanyThumbnailCommPost);
                    }
                    //}
                    tvCompanyNameCommPost.setText(feedCommunityName);
                    orgCommPostSeparateLine.setVisibility(View.VISIBLE);
                } else if (mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                    rlOrgCompanyFeedCard.setVisibility(View.GONE);
                    orgCommPostSeparateLine.setVisibility(View.GONE);
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_ASKED)).append(AppConstants.SPACE);
                        posted.append(feedCommunityName);
                        clickOnCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_ASKED));
                    } else if (feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_ADMIN))) {
                        feedTitle = mUserPostObj.getPostCommunityName();
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_ASKED)).append(AppConstants.SPACE);
                        clickOnCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_ASKED));
                    } else {
                        feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                        posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_ASKED)).append(AppConstants.SPACE);
                        posted.append(feedCommunityName);
                        clickOnCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_IN));
                    }
                } else {
                    rlOrgCompanyFeedCard.setVisibility(View.GONE);
                    orgCommPostSeparateLine.setVisibility(View.GONE);
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
            tvFeedCommunityPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        } else {
            tvFeedCommunityPostTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }
    }

    private void likeCommentOps() {
        if (mUserPostObj.getNoOfLikes() < AppConstants.ONE_CONSTANT && mUserPostObj.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            rlFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
            lineForNoImage.setVisibility(View.GONE);
        }
        tvFeedCommunityPostReaction1.setVisibility(View.VISIBLE);
        switch (mUserPostObj.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:

                if (mUserPostObj.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
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
        String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, mUserPostObj.getNoOfLikes());
        tvFeedCommunityPostTotalReactions.setText(String.valueOf(mUserPostObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));
        switch (mUserPostObj.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mUserPostObj.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
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
        String pluralComments;
        if(mUserPostObj.getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR)
        {
            pluralComments=  mContext.getResources().getQuantityString(R.plurals.numberOfAnswers, mUserPostObj.getNoOfComments());
        }else
        {
            pluralComments= mContext.getResources().getQuantityString(R.plurals.numberOfComments, mUserPostObj.getNoOfComments());
        }

        tvFeedCommunityPostTotalReplies.setText(String.valueOf(mUserPostObj.getNoOfComments() + AppConstants.SPACE + pluralComments));
    }

    private void populatePostText() {
        if(isWhatappShareOption) {
            tvFeedCommunityPostUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ic_share_card), null, null, null);
            tvFeedCommunityPostUserShare.setText(mContext.getString(R.string.ID_SHARE_ON_WHATS_APP));
            tvFeedCommunityPostUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.share_color));

        }
        else
        {
            tvFeedCommunityPostUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ic_share_white_out), null, null, null);
            tvFeedCommunityPostUserShare.setText(mContext.getString(R.string.ID_SHARE));
            tvFeedCommunityPostUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.recent_post_comment));

        }
        final String listDescription = mUserPostObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            tvFeedCommunityPostText.setVisibility(View.GONE);
            return;
        } else {
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
            tvFeedCommunityPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
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

        switch (mUserPostObj.getReactionValue()) {
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
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + mUserPostObj.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<Comment> lastCommentList = mUserPostObj.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            ivFeedCommunityPostUserPic.setCircularImage(true);
            invalidateCommentLike(lastComment);
            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    ivFeedCommunityPostUserPic.bindImage(lastComment.getParticipantImageUrl());
                    tvFeedCommunityPostUserName.setText(lastComment.getParticipantName());
                    tvFeedCommunityPostUserCommentPost.setText(hashTagColorInString(lastComment.getComment()));
                    ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                    invalidateCommentLike(lastComment);
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
            if (tvFeedCommunityPostUserCommentPost.getLineCount() > 3) {
                tvFeedCommunityPostUserCommentPostViewMore.setVisibility(View.VISIBLE);
                String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostUserCommentPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
            } else {
                tvFeedCommunityPostUserCommentPostViewMore.setVisibility(View.GONE);
            }
            if (StringUtil.isNotNullOrEmptyString(lastComment.getLastModifiedOn())) {
                long createdDate = mDateUtil.getTimeInMillis(lastComment.getLastModifiedOn(), AppConstants.DATE_FORMAT);
                tvFeedCommunityPostUserCommentPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            } else {
                tvFeedCommunityPostUserCommentPostTime.setText(mContext.getString(R.string.ID_JUST_NOW));
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

    private void invalidateCommentLike(Comment lastComment) {
        if (lastComment.likeCount > 0) {
            mCommentLikeCount.setVisibility(View.VISIBLE);
            mCommentLikeCount.setText(Integer.toString(lastComment.likeCount));
        } else {
            mCommentLikeCount.setVisibility(View.GONE);
        }
        if (lastComment.isLiked) {
            mCommentLike.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            mCommentLike.setTextColor(mContext.getResources().getColor(R.color.posted_in));
        }
    }

    private void imageOperations(Context context) {
        String authorImageUrl = mUserPostObj.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            ivFeedCommunityPostCircleIcon.setCircularImage(true);
            ivFeedCommunityPostCircleIcon.bindImage(authorImageUrl);
        }
        ivFeedCommunityPostLoginUserPic.setCircularImage(true);
        ivFeedCommunityPostLoginUserPic.bindImage(mPhotoUrl); //todo - chk it here
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


    @OnClick({R.id.tv_feed_community_post_total_replies, R.id.tv_feed_community_post_user_comment_post, R.id.li_feed_community_post_user_comments, R.id.tv_feed_community_post_user_comment_post_view_more, R.id.card_view_post})
    public void repliesClick() {
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback) viewInterface).onUserPostClicked(mUserPostObj);
        }else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserComment);
        }
    }

    @OnClick({R.id.tv_join_conversation,R.id.tv_feed_community_post_user_comment})
    public void joinConversationClick() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if(viewInterface instanceof FeedItemCallback){
                    ((FeedItemCallback) viewInterface).onUserPostCommentClicked(mUserPostObj);
                }else {
                    viewInterface.handleOnClick(mUserPostObj, mJoinConveration);
                }
            }
        });

    }

    @OnClick(R.id.tv_feed_community_post_user_comment)
    public void userCommentClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onUserPostCommentClicked(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, mJoinConveration);
        }


    }

    @OnClick(R.id.li_feed_community_user_post_images)
    public void communityPostImageClick() {
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback)viewInterface).onUserPostImageClicked(mUserPostObj);
        }else {
            viewInterface.dataOperationOnClick(mUserPostObj);
        }
    }


    @OnClick(R.id.tv_feed_community_post_user_menu)
    public void userMenuClick() {
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback)viewInterface).onPostMenuClicked(mUserPostObj, tvFeedCommunityPostUserMenu);
        }else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserMenu);
        }
    }

    @OnClick(R.id.tv_spam_post_menu)
    public void spamMenuClick() {
        viewInterface.handleOnClick(mUserPostObj, tvSpamPostMenu);
    }


    @OnClick(R.id.tv_feed_community_post_user_comment_post_menu)
    public void userCommentMenuClick() {
        mUserPostObj.setNoOfOpenings(mItemPosition);
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback) viewInterface).onCommentMenuClicked(mUserPostObj, tvFeedCommunityPostUserCommentPostMenu);
        }else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserCommentPostMenu);
        }

    }

    @OnClick(R.id.tv_feed_community_post_view_more)
    public void onViewMoreClicked() {
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
                mUserPostObj.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                if(viewInterface instanceof FeedItemCallback){
                    ((FeedItemCallback)viewInterface).onUserPostImageClicked(mUserPostObj);
                }else {
                    viewInterface.dataOperationOnClick(mUserPostObj);
                }
                break;
            }
            case R.id.iv_second: {
                mUserPostObj.setItemPosition(AppConstants.ONE_CONSTANT);
                if(viewInterface instanceof FeedItemCallback){
                    ((FeedItemCallback)viewInterface).onUserPostImageClicked(mUserPostObj);
                }else {
                    viewInterface.dataOperationOnClick(mUserPostObj);
                }
                break;
            }
            case R.id.iv_third: {
                mUserPostObj.setItemPosition(AppConstants.TWO_CONSTANT);
                if(viewInterface instanceof FeedItemCallback){
                    ((FeedItemCallback)viewInterface).onUserPostImageClicked(mUserPostObj);
                }else {
                    viewInterface.dataOperationOnClick(mUserPostObj);
                }
                break;
            }

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

        }
    }


    @OnClick(R.id.tv_feed_community_post_user_bookmark)
    public void isBookMarkClick() {
        // mUserPostObj.setTrending(true);
        tvFeedCommunityPostUserBookmark.setEnabled(false);
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback) viewInterface).onPostBookMarkedClicked(mUserPostObj);
        }else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserBookmark);
        }
        if (mUserPostObj.isBookmarked()) {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        } else {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        }
        if (!mUserPostObj.isBookmarked()) {
            mUserPostObj.setBookmarked(true);
        } else {
            mUserPostObj.setBookmarked(false);
        }
        onBookMarkClick();
    }

    @OnClick(R.id.tv_feed_community_post_user_share)
    public void tvShareClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostShared(mUserPostObj);
        } else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostUserShare);
        }
        if (mUserPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ORGANISATION_FEEDBACK_POST, mUserPostObj.communityId + AppConstants.DASH + mUserId + AppConstants.DASH + mUserPostObj.getIdOfEntityOrParticipant());
        } else {
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        }
    }

    @OnClick(R.id.tv_feed_community_post_total_reactions)
    public void reactionClick() {
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback)viewInterface).onLikesCountClicked(mUserPostObj.getEntityOrParticipantId());
        }else {
            viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostTotalReactions);
        }
    }

    @OnClick(R.id.tv_feed_community_post_reaction1)
    public void reaction1Click() {
        viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostTotalReactions);
    }

    @OnClick(R.id.tv_feed_community_post_user_reaction)
    public void userReactionClick() {
        if(viewInterface instanceof FeedItemCallback){
            if (mUserPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                mUserPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                ((FeedItemCallback)viewInterface).onUserPostUnLiked(mUserPostObj);
            }else {
                mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                ((FeedItemCallback)viewInterface).onUserPostLiked(mUserPostObj);
            }
            likeCommentOps();
        }else {
            if ((Boolean) tvFeedCommunityPostUserReaction.getTag()) {
                userReactionWithouLongPress();
            }
        }
    }


    private void userReactionWithouLongPress() {
        tvFeedCommunityPostUserReaction.setTag(false);
        // mUserPostObj.setTrending(true);
        mUserPostObj.setLongPress(false);
        if (mUserPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(mUserPostObj, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            if (mUserPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ORGANISATION_FEEDBACK_POST, AppConstants.EMPTY_STRING);
            } else {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
            }
        } else {
            viewInterface.userCommentLikeRequest(mUserPostObj, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            if (mUserPostObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ORGANISATION_FEEDBACK_POST, AppConstants.EMPTY_STRING);

            } else {
                ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_COMMUNITY_POST, AppConstants.EMPTY_STRING);
            }
        }
        if (mUserPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            mUserPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
        } else {
            mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);


        }
        likeCommentOps();
    }

    //Redirect to logged in user
    @OnClick({R.id.iv_feed_community_post_login_user_pic , R.id.tv_feed_community_post_login_user_name})
    public void onCommentAuthorClick() { //Open profile from feed
            viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL);
    }

    @OnClick(R.id.iv_feed_community_post_circle_icon)
    public void onFeedCommunityPostCircleIconClick() { //Open profile from feed and community post
        if (!mUserPostObj.isAnonymous()) {
            if(mUserPostObj.getEntityOrParticipantTypeId() == 15) { //community
                viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            } else {
                viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
            }
        }
    }

    @OnClick(R.id.tv_feed_community_post_card_title)
    public void onAuthorName() { //Open profile from feed
        if (!mUserPostObj.isAnonymous()) {
            if(mUserPostObj.getEntityOrParticipantTypeId() == 15) { //community
                viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
            }
            else if(viewInterface instanceof FeedItemCallback){
                ((FeedItemCallback)viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
            }else {
                // viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_CHAMPION_TITLE);
            }
        }
    }


    private void clickOnMentorAndCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                if (!mUserPostObj.isAnonymous()) {
                    if(viewInterface instanceof FeedItemCallback){
                        ((FeedItemCallback)viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    }
                    if(viewInterface instanceof FeedItemCallback){
                        ((FeedItemCallback)viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    }else {
                        viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);

                    }}
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
                if(viewInterface instanceof FeedItemCallback){
                    ((FeedItemCallback)viewInterface).onCommunityTitleClicked(mUserPostObj);
                }else {
                    viewInterface.handleOnClick(mUserPostObj, tvFeedCommunityPostCardTitle);
                }
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
                //     if (mUserPostObj.isAuthorMentor())
                {

                    if(mUserPostObj.getEntityOrParticipantTypeId() == 15) { //community
                        viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                    }
                    else if(viewInterface instanceof FeedItemCallback){
                        ((FeedItemCallback)viewInterface).onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    }else {
                        viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)), feedTitle.length(), feedTitle.length() + postedIn.length()+1 , 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)), feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
            }
            tvFeedCommunityPostCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvFeedCommunityPostCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            tvFeedCommunityPostCardTitle.setSelected(true);

        }
    }

    private void handlingSpamUi(long userId, int adminId) {
        if (adminId == AppConstants.TWO_CONSTANT || mUserPostObj.isCommunityOwner()) {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            liFeedCommunityPostUserComments.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.VISIBLE);
            tvReviewDescription.setVisibility(View.GONE);
        } else if (mUserPostObj.getAuthorId() == userId) {
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
        viewInterface.handleOnClick(mUserPostObj, tvApproveSpamPost);
    }

    @OnClick(R.id.tv_delete_spam_post)
    public void onDeleteSpamPostClick() {
        viewInterface.handleOnClick(mUserPostObj, tvDeleteSpamPost);
    }

    //Last comment user name or user pic
    @OnClick({R.id.iv_feed_community_post_user_pic, R.id.tv_feed_community_post_user_name})
    public void onLastCommentUserClick() { //Open profile from feed
        viewInterface.navigateToProfileView(mUserPostObj, AppConstants.REQUEST_CODE_FOR_LAST_COMMENT_USER_DETAIL);
    }

    @OnClick(R.id.comment_like)
    public void onCommentLikeClicked() {
        List<Comment> lastCommentList = mUserPostObj.getLastComments();
        Comment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
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
            if(viewInterface instanceof FeedItemCallback){
                if (lastComment.isLiked) {
                    ((FeedItemCallback)viewInterface).userCommentLikeRequest(mUserPostObj, true, getAdapterPosition());
                } else {
                    ((FeedItemCallback)viewInterface).userCommentLikeRequest(mUserPostObj, false, getAdapterPosition());
                }
            }else {
                if (!lastComment.isLiked) {
                    viewInterface.userCommentLikeRequest(lastComment, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
                } else {
                    viewInterface.userCommentLikeRequest(lastComment, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
                }
            }

        }
    }

}