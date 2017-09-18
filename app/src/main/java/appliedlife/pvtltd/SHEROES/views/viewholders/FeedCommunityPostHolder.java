package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spannable;
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
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
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
import butterknife.OnLongClick;

/**
 * Created by Praveen_Singh on 22-01-2017.
 */

public class FeedCommunityPostHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeedCommunityPostHolder.class);
    private static final String LEFT_POSTED = "<font color='#8a8d8e'>";
    private static final String RIGHT_POSTED = "</font>";
    @Inject
    DateUtil mDateUtil;
    @Inject
    Preference<LoginResponse> userPreference;

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


    @Bind(R.id.li_reaction_comment_block)
    LinearLayout liReactionCommentBlock;
    @Bind(R.id.li_community_post_main_layout)
    LinearLayout liCommunityPostMainLayout;



    //Communitypost handling
    @Bind(R.id.li_feed_community_post_user_comments)
    LinearLayout liFeedCommunityPostUserComments;
    @Bind(R.id.li_feed_community_user_post_images)
    LinearLayout liFeedCommunityUserPostImages;
    @Bind(R.id.li_feed_community_post_join_conversation)
    LinearLayout liFeedCommunityPostJoinConversation;
    @Bind(R.id.iv_feed_community_post_circle_icon)
    CircleImageView ivFeedCommunityPostCircleIcon;
    @Bind(R.id.line_for_no_image)
    View lineForNoImage;
    @Bind(R.id.iv_feed_community_post_circle_icon_verified)
    ImageView ivFeedCommunityPostCircleIconVerified;
    @Bind(R.id.iv_feed_community_post_user_icon_verified)
    ImageView ivFeedCommunityPostUserIconVerified;
    @Bind(R.id.tv_feed_community_post_user_share)
    TextView tvFeedCommunityPostUserShare;
    @Bind(R.id.tv_feed_community_post_user_reaction)
    TextView tvFeedCommunityPostUserReaction;
    @Bind(R.id.tv_feed_community_post_user_reaction_text)
    TextView tvFeedCommunityPostUserReactionText;
    @Bind(R.id.tv_feed_community_post_user_comment)
    TextView tvFeedCommunityPostUserComment;
    @Bind(R.id.tv_feed_community_post_view_more)
    TextView tvFeedCommunityPostViewMore;
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
    @Bind(R.id.tv_feed_community_post_text_full_view)
    TextView tvFeedCommunityPostTextFullView;
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
    @Bind(R.id.progress_bar_post_link)
    ProgressBar pbLink;

    @Bind(R.id.iv_play)
    ImageView ivPlay;
    @Bind(R.id.fm_image_thumb)
    FrameLayout fmImageThumb;
    @Bind(R.id.iv_post_link_thumbnail)
    ImageView ivLinkThumbnail;
    @Bind(R.id.card_post_link_render)
    CardView cardViewLinkRender;
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
    private String mViewMoreDescription;
    private String mViewMore, mLess;
    private Context mContext;
    private int mItemPosition;
    private long mUserId;
    private int mAdminId;
    private String mPhotoUrl;
    public FeedCommunityPostHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
             mUserId = userPreference.get().getUserSummary().getUserId();
            if(null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            if(StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl()))
            {
                mPhotoUrl=userPreference.get().getUserSummary().getPhotoUrl();
            }
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        dataItem.setItemPosition(position);
        normalCommunityPostUi(mUserId, mAdminId);
        if(dataItem.isSpamPost()) {
            handlingSpamUi(mUserId, mAdminId);
        }else
        {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liReactionCommentBlock.setVisibility(View.VISIBLE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        }
    }

    private void normalCommunityPostUi(long userId,int adminId)
    {
        liCommunityPostMainLayout.setVisibility(View.VISIBLE);
        mViewMore = mContext.getString(R.string.ID_VIEW_MORE);
        mLess = mContext.getString(R.string.ID_LESS);
        tvFeedCommunityPostText.setTag(mViewMore);
        tvFeedCommunityPostTextFullView.setTag(mViewMore);
        tvFeedCommunityPostUserBookmark.setEnabled(true);
        tvFeedCommunityPostUserReaction.setEnabled(true);
        tvFeedCommunityPostUserReactionText.setEnabled(true);
        dataItem.setLastReactionValue(dataItem.getReactionValue());
        if (!dataItem.isTrending()) {
            imageOperations(mContext);
            if (StringUtil.isNotNullOrEmptyString(dataItem.getOgRequestedUrlS())) {
                liFeedCommunityUserPostImages.removeAllViews();
                liFeedCommunityUserPostImages.removeAllViewsInLayout();
                liFeedCommunityUserPostImages.setVisibility(View.GONE);
                cardViewLinkRender.setVisibility(View.VISIBLE);
                setLinkData();
            } else {
                cardViewLinkRender.setVisibility(View.GONE);
            }
            multipleImageURLs();
        }
        onBookMarkClick();
        allTextViewStringOperations(mContext);
        if (dataItem.getAuthorId() == userId||dataItem.isCommunityOwner()||adminId==AppConstants.TWO_CONSTANT) {
            tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
            if (dataItem.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
                tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
            } else {
                tvFeedCommunityPostUserMenu.setVisibility(View.VISIBLE);
            }
        } else {
            tvFeedCommunityPostUserMenu.setVisibility(View.GONE);
        }

        if(dataItem != null&&StringUtil.isNotNullOrEmptyString(dataItem.getListDescription())){
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(AppConstants.IMPRESSIONS, AppConstants.COMMUNITY_POST_IMPRESSION,dataItem.getIdOfEntityOrParticipant()+ AppConstants.DASH + userId + AppConstants.DASH + dataItem.getListDescription());
        }
    }
    @OnClick(R.id.card_post_link_render)
    public void tvLinkClick() {
        if (null != dataItem && dataItem.isOgVideoLinkB() && StringUtil.isNotNullOrEmptyString(dataItem.getOgRequestedUrlS())) {
            Intent youTube = new Intent(mContext, VideoPlayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.YOUTUBE_VIDEO_CODE, dataItem.getOgRequestedUrlS());
            youTube.putExtras(bundle);
            mContext.startActivity(youTube);
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataItem.getOgRequestedUrlS()));
            mContext.startActivity(browserIntent);
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
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivLinkThumbnail.setImageBitmap(profileImage);
                            fmImageThumb.setVisibility(View.VISIBLE);
                            pbLink.setVisibility(View.GONE);
                            if (dataItem.isOgVideoLinkB()) {
                                ivPlay.setVisibility(View.VISIBLE);
                            } else {
                                ivPlay.setVisibility(View.GONE);
                            }
                        }
                    });
            cardViewLinkRender.setVisibility(View.VISIBLE);
        } else {
            cardViewLinkRender.setVisibility(View.GONE);
        }
    }





    private void multipleImageURLs() {
        if (dataItem.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
            tvFeedCommunityPostUserShare.setVisibility(View.GONE);
            liFeedCommunityUserPostImages.removeAllViews();
            liFeedCommunityUserPostImages.removeAllViewsInLayout();
            liFeedCommunityUserPostImages.setVisibility(View.VISIBLE);
            lineForNoImage.setVisibility(View.GONE);
            ivFeedCommunityPostMenu.setBackgroundResource(R.drawable.ic_completed_select);
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
            tvFeedCommunityPostUserShare.setVisibility(View.VISIBLE);
            ivFeedCommunityPostMenu.setBackgroundResource(R.drawable.ic_search_group_icon);
            if (StringUtil.isNotEmptyCollection(dataItem.getImageUrls())) {
                liFeedCommunityUserPostImages.setVisibility(View.VISIBLE);
                lineForNoImage.setVisibility(View.GONE);
                List<String> coverImageList = dataItem.getImageUrls();
                int listSize = coverImageList.size();
                if (listSize > AppConstants.NO_REACTION_CONSTANT) {
                    switch (listSize) {
                        case AppConstants.ONE_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0))) {
                                oneImagesSetting(mContext, coverImageList.get(0));
                            }
                            break;
                        case AppConstants.TWO_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1))) {
                                twoImagesSetting(mContext, coverImageList.get(0), coverImageList.get(1));
                            }
                            break;
                        case AppConstants.THREE_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2))) {
                                //boolean isHeightGreater = getCoverImageHeightWidth(coverImageList.get(0));
                           /* if (isHeightGreater) {
                                feedFirstPortraitWithTwoImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                            } else {
                                feedFirstLandscapWIthTwoImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                            }*/
                                //  feedFirstPortraitWithTwoImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                                feedFirstLandscapWIthTwoImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                            }
                            break;
                        case AppConstants.FOURTH_CONSTANT:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                                //  boolean isHeightGreater = getCoverImageHeightWidth(coverImageList.get(0));
                            /*if (isHeightGreater) {
                                feedFirstPortraitImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            } else {
                                feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            }*/
                                feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            }
                            break;
                        default:
                            liFeedCommunityUserPostImages.removeAllViews();
                            liFeedCommunityUserPostImages.removeAllViewsInLayout();
                            if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                                //  boolean isHeightGreater = getCoverImageHeightWidth(coverImageList.get(0));
                         /*   if (isHeightGreater) {
                                feedFirstPortraitImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            } else {
                                feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            }*/
                                feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            }
                    }
                }
            } else {
                liFeedCommunityUserPostImages.removeAllViews();
                liFeedCommunityUserPostImages.removeAllViewsInLayout();
                lineForNoImage.setVisibility(View.VISIBLE);
                liFeedCommunityUserPostImages.setVisibility(View.GONE);
            }
        }
    }

    private boolean getCoverImageHeightWidth(String imagePath) {
        final boolean[] isHeightGreater = new boolean[1];
        Glide.with(mContext)
                .load(imagePath).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
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
                tvFeedCommunityPostText.setVisibility(View.GONE);
                String feedTitle = dataItem.getAuthorName();
                posted.append(feedTitle).append(AppConstants.SPACE).append(LEFT_POSTED).append(mContext.getString(R.string.ID_HAS_ACCEPTED)).append(RIGHT_POSTED).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_HAS_ACCEPTED_CHALLENGE));
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostCardTitle.setText(Html.fromHtml(posted.toString(), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostCardTitle.setText(Html.fromHtml(posted.toString()));// or for older api
                }
            } else {
                tvFeedCommunityPostText.setVisibility(View.VISIBLE);
                String feedTitle = dataItem.getAuthorName();
                String feedCommunityName = dataItem.getPostCommunityName();
                if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
                    if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        if (dataItem.isAuthorMentor()) {
                            ivFeedCommunityPostCircleIconVerified.setVisibility(View.VISIBLE);
                        } else {
                            ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                        }
                    }else {
                        ivFeedCommunityPostCircleIconVerified.setVisibility(View.GONE);
                    }

                    if(dataItem.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID){
                        rlOrgCompanyFeedCard.setVisibility(View.VISIBLE);
                        if(!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))){
                            posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                            clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                        }else{
                            feedTitle = mContext.getString(R.string.ID_ANONYMOUS);
                            posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK)).append(AppConstants.SPACE).append(feedCommunityName);
                            clickOnMentorAndCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.ID_POSTED_ASK_FEEDBACK));
                        }
                        if(!dataItem.isTrending()) {
                            if (StringUtil.isNotNullOrEmptyString(dataItem.getSolrIgnorePostCommunityLogo())) {
                                Glide.with(context)
                                        .load(dataItem.getSolrIgnorePostCommunityLogo())
                                        .into(ivCompanyThumbnailCommPost);
                            }
                        }
                        tvCompanyNameCommPost.setText(feedCommunityName);
                        orgCommPostSeparateLine.setVisibility(View.VISIBLE);

                    }else {
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
        mViewMoreDescription = dataItem.getShortDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            int index = 0;
            int lengthOfDesc = mViewMoreDescription.length();
            try {
                if (lengthOfDesc < AppConstants.WORD_LENGTH) {
                    if (mViewMoreDescription.contains(AppConstants.SLASH_N))
                        index = AppUtils.findNthIndexOf(mViewMoreDescription, AppConstants.SLASH_N, 1);
                }
            } catch (Exception e) {

            }
            tvFeedCommunityPostTextFullView.setVisibility(View.GONE);
            tvFeedCommunityPostText.setVisibility(View.VISIBLE);
            if (lengthOfDesc > AppConstants.WORD_LENGTH || index > 0 && index < 50) {
                tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
                tvFeedCommunityPostViewMore.setTag(mViewMore);
                tvFeedCommunityPostViewMore.setText(mContext.getString(R.string.ID_VIEW_MORE));
                tvFeedCommunityPostText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));

            } else {
                tvFeedCommunityPostViewMore.setText(mContext.getString(R.string.ID_LESS));
                tvFeedCommunityPostViewMore.setTag(mLess);
                tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                tvFeedCommunityPostText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
            }

        } else {
            tvFeedCommunityPostViewMore.setText(mContext.getString(R.string.ID_LESS));
            tvFeedCommunityPostViewMore.setTag(mLess);
            tvFeedCommunityPostTextFullView.setVisibility(View.GONE);
            tvFeedCommunityPostText.setVisibility(View.GONE);
            tvFeedCommunityPostViewMore.setVisibility(View.GONE);
        }
        if (dataItem.getNoOfLikes() < AppConstants.ONE_CONSTANT && dataItem.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
        }
        tvFeedCommunityPostReaction1.setVisibility(View.VISIBLE);
        tvFeedCommunityPostReaction2.setVisibility(View.VISIBLE);
        tvFeedCommunityPostReaction3.setVisibility(View.VISIBLE);
        switch (dataItem.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:

                if (dataItem.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    flFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReactions.setVisibility(View.GONE);
                    tvFeedCommunityPostReaction1.setVisibility(View.INVISIBLE);
                    tvFeedCommunityPostReaction2.setVisibility(View.INVISIBLE);
                    tvFeedCommunityPostReaction3.setVisibility(View.INVISIBLE);
                    tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                } else {
                    flFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                flFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setText(AppConstants.ONE_CONSTANT + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                tvFeedCommunityPostUserReactionText.setText(AppConstants.EMPTY_STRING);
                userLike();
                break;
            default:
                flFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                tvFeedCommunityPostTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                tvFeedCommunityPostUserReactionText.setText(AppConstants.EMPTY_STRING);
                userLike();
        }
        switch (dataItem.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (dataItem.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    flFeedCommunityPostNoReactionComment.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReactions.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostReaction1.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostReaction2.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostReaction3.setVisibility(View.VISIBLE);
                    tvFeedCommunityPostTotalReplies.setVisibility(View.INVISIBLE);
                } else {
                    flFeedCommunityPostNoReactionComment.setVisibility(View.GONE);
                }
                userComments();
                break;
            case AppConstants.ONE_CONSTANT:
                tvFeedCommunityPostTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
                userComments();
                break;
            default:
                tvFeedCommunityPostTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                tvFeedCommunityPostTotalReplies.setVisibility(View.VISIBLE);
                liFeedCommunityPostUserComments.setVisibility(View.VISIBLE);
                userComments();
        }
    }

    private void userLike() {

        switch (dataItem.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                tvFeedCommunityPostUserReactionText.setText(AppConstants.EMPTY_STRING);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                tvFeedCommunityPostUserReactionText.setText(mContext.getString(R.string.ID_LOVE));
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji3_whistel, 0, 0, 0);
                tvFeedCommunityPostUserReactionText.setText(mContext.getString(R.string.ID_WISHTLE));
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji_xo_xo, 0, 0, 0);
                tvFeedCommunityPostUserReactionText.setText(mContext.getString(R.string.ID_XOXO));
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji2_with_you, 0, 0, 0);
                tvFeedCommunityPostUserReactionText.setText(mContext.getString(R.string.ID_LIKE));
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji4_face_palm, 0, 0, 0);
                tvFeedCommunityPostUserReactionText.setText(mContext.getString(R.string.ID_FACE_PALM));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + dataItem.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<LastComment> lastCommentList = dataItem.getLastComments();
        LastComment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            ivFeedCommunityPostUserPic.setCircularImage(true);
            if (lastComment.isAnonymous()) {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    ivFeedCommunityPostUserPic.setImageResource(R.drawable.ic_anonomous);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(lastComment.getParticipantName()).append(AppConstants.COLON).append(AppConstants.SPACE).append(lastComment.getComment());
                    Spannable getCommentString = new SpannableString(stringBuilder.toString());
                    int size = lastComment.getParticipantName().length() + 1;
                    getCommentString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    getCommentString.setSpan(new StyleSpan(Typeface.BOLD), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    tvFeedCommunityPostUserCommentPost.setText(getCommentString);
                    ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(lastComment.getComment()) && StringUtil.isNotNullOrEmptyString(lastComment.getParticipantName())) {
                    ivFeedCommunityPostUserPic.bindImage(lastComment.getParticipantImageUrl());
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(lastComment.getParticipantName()).append(AppConstants.COLON).append(AppConstants.SPACE).append(lastComment.getComment());
                    Spannable getCommentString = new SpannableString(stringBuilder.toString());
                    int size = lastComment.getParticipantName().length() + 1;
                    getCommentString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    getCommentString.setSpan(new StyleSpan(Typeface.BOLD), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    tvFeedCommunityPostUserCommentPost.setText(getCommentString);
                    if (!lastComment.getParticipantName().equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                        if (lastComment.isVerifiedMentor()) {
                            ivFeedCommunityPostUserIconVerified.setVisibility(View.VISIBLE);
                        } else {
                            ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                        }
                    }else {
                        ivFeedCommunityPostUserIconVerified.setVisibility(View.GONE);
                    }
                }
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
            ivFeedCommunityPostRegisterUserPic.setCircularImage(true);
            ivFeedCommunityPostRegisterUserPic.bindImage(mPhotoUrl);
    }

    private void oneImagesSetting(Context context, String firstImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_single_image, null);
       final ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_single);
        final  FrameLayout flShadow=(FrameLayout) child.findViewById(R.id.fl_shadow_image);
        flShadow.getLayoutParams().height=dataItem.getImageHeight();
        Glide.with(context)
                .load(firstImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                        ivFirstLandscape.setImageBitmap(profileImage);
                        flShadow.setVisibility(View.GONE);
                    }
                });

        liFeedCommunityUserPostImages.addView(child);
    }

    private void twoImagesSetting(Context context, String firstImage, String secondImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_two_images, null);
       final ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first);
        final  FrameLayout flShadowPostFirst=(FrameLayout) child.findViewById(R.id.fl_shadow_image_post_first);
       ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                         ivFirstLandscape.setImageBitmap(profileImage);
                        ivFirstLandscape.setVisibility(View.VISIBLE);
                       flShadowPostFirst.setVisibility(View.GONE);
                    }
                });

       final ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_comunity_post_second);
       final  FrameLayout flShadowPostSecond=(FrameLayout) child.findViewById(R.id.fl_shadow_image_post_second);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                         ivSecond.setImageBitmap(profileImage);
                        ivSecond.setVisibility(View.VISIBLE);
                        flShadowPostSecond.setVisibility(View.GONE);
                    }
                });
        liFeedCommunityUserPostImages.addView(child);
    }


    private void feedFirstLandscapWIthTwoImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_landscape_with_two_images, null);
        final ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_landscape_with_two_images);
        final  FrameLayout flShadowPostFirst=(FrameLayout) child.findViewById(R.id.fl_shadow_image_with_landscap);
       flShadowPostFirst.getLayoutParams().height=dataItem.getImageHeight();
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                         ivFirstLandscape.setImageBitmap(profileImage);
                         ivFirstLandscape.setVisibility(View.VISIBLE);
                        flShadowPostFirst.setVisibility(View.GONE);
                    }
                });

      final   ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape_with_two_images);
        final  FrameLayout flShadowPostFirstWithLandscap=(FrameLayout) child.findViewById(R.id.fl_shadow_image_post_first_with_landscap);
        ivSecond.setOnClickListener(this);

        Glide.with(context)
                .load(secondImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                         ivSecond.setImageBitmap(profileImage);
                        ivSecond.setVisibility(View.VISIBLE);
                        flShadowPostFirstWithLandscap.setVisibility(View.GONE);
                    }
                });

       final ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape_with_two_images);
        final  FrameLayout flShadowPostSecondWithLandscap=(FrameLayout) child.findViewById(R.id.fl_shadow_image_post_second_with_landscap);
        ivThird.setOnClickListener(this);
        Glide.with(context)
                .load(thirdImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                         ivThird.setImageBitmap(profileImage);
                       ivThird.setVisibility(View.VISIBLE);
                        flShadowPostSecondWithLandscap.setVisibility(View.GONE);
                    }
                });
        liFeedCommunityUserPostImages.addView(child);
    }

    private void feedFirstLandscapImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage, String fourthImage, int listSize) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_landscape_with_multiple, null);
        TextView tvFeedCommunityPost = (TextView) child.findViewById(R.id.tv_feed_community_post_landscape_total_images);
        TextView tvFeedCommunityPostMore = (TextView) child.findViewById(R.id.tv_feed_community_image_more);
        if (listSize > AppConstants.FOURTH_CONSTANT) {
            tvFeedCommunityPost.setVisibility(View.VISIBLE);
            tvFeedCommunityPostMore.setVisibility(View.VISIBLE);
        final     ImageView ivFourth = (ImageView) child.findViewById(R.id.iv_feed_community_post_fourth_image_landscape);
            final  FrameLayout flShadowPostThirdLandscapMultiple=(FrameLayout) child.findViewById(R.id.fl_shadow_image_post_fourth_landscap_multiple);
            ivFourth.setOnClickListener(this);
            Glide.with(context)
                    .load(fourthImage).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivFourth.setImageBitmap(profileImage);
                            ivFourth.setVisibility(View.VISIBLE);
                            flShadowPostThirdLandscapMultiple.setVisibility(View.GONE);
                        }
                    });
        }else
        {
            tvFeedCommunityPost.setVisibility(View.GONE);
            tvFeedCommunityPostMore.setVisibility(View.GONE);
        }
      final   ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_landscape);
        final  FrameLayout flShadowPostFirst=(FrameLayout) child.findViewById(R.id.fl_shadow_image_with_multiple_landscap);
        flShadowPostFirst.getLayoutParams().height=dataItem.getImageHeight();
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                        ivFirstLandscape.setImageBitmap(profileImage);
                        ivFirstLandscape.setVisibility(View.VISIBLE);
                        flShadowPostFirst.setVisibility(View.GONE);
                    }
                });



      final  ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape);
        final  FrameLayout flShadowPostSecondLandscapMultiple=(FrameLayout) child.findViewById(R.id.fl_shadow_image_post_second_landscap_multiple);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                        ivSecond.setImageBitmap(profileImage);
                        ivSecond.setVisibility(View.VISIBLE);
                        flShadowPostSecondLandscapMultiple.setVisibility(View.GONE);
                    }
                });


      final   ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape);
        final  FrameLayout flShadowPostThirdLandscapMultiple=(FrameLayout) child.findViewById(R.id.fl_shadow_image_post_third_landscap_multiple);
        ivThird.setOnClickListener(this);
        Glide.with(context)
                .load(thirdImage).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                        ivThird.setImageBitmap(profileImage);
                        ivThird.setVisibility(View.VISIBLE);
                        flShadowPostThirdLandscapMultiple.setVisibility(View.GONE);
                    }
                });

        liFeedCommunityUserPostImages.addView(child);
    }
/*
    private void feedFirstPortraitImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage, String fourthImage, int listSize) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_portrait_with_multiple, null);
        TextView tvFeedCommunityPost = (TextView) child.findViewById(R.id.tv_feed_community_post_portrait_total_images);
        if (listSize > AppConstants.FOURTH_CONSTANT) {
            tvFeedCommunityPost.setVisibility(View.VISIBLE);
        }
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
    }*/




    @OnClick(R.id.tv_feed_community_post_total_replies)
    public void repliesClick() {
        dataItem.setCallFromName(mContext.getString(R.string.ID_REPLY));
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }


    @OnClick(R.id.li_feed_community_user_post_images)
    public void communityPostImageClick() {
        viewInterface.dataOperationOnClick(dataItem);
    }

    @OnClick(R.id.li_feed_community_post_join_conversation)
    public void joinConversationClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }

    @OnClick(R.id.li_feed_community_post_user_comments)
    public void openCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }

    @OnClick(R.id.tv_feed_community_post_user_comment)
    public void userCommentClick() {
        dataItem.setCallFromName(AppConstants.EMPTY_STRING);
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }

    @OnClick(R.id.tv_feed_community_post_user_menu)
    public void userMenuClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserMenu);
    }
    @OnClick(R.id.tv_spam_post_menu)
    public void spamMenuClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserMenu);
    }


    @OnClick(R.id.tv_feed_community_post_user_comment_post_menu)
    public void userCommentMenuClick() {
        dataItem.setItemPosition(mItemPosition);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserCommentPostMenu);
    }

    @OnClick(R.id.tv_feed_community_post_view_more)
    public void textViewMoreClick() {
        mViewMoreDescription=dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            int index = 0;
            int lengthOfDesc = mViewMoreDescription.length();
            try {
                if (lengthOfDesc < AppConstants.WORD_LENGTH) {
                    if (mViewMoreDescription.contains(AppConstants.SLASH_N))
                        index = AppUtils.findNthIndexOf(mViewMoreDescription, AppConstants.SLASH_N, 1);
                }
            } catch (Exception e) {

            }
            if (lengthOfDesc > AppConstants.WORD_LENGTH || index > 0 && index < 50) {
                viewMoreTextClick();
            }
        }
    }

    private void viewMoreTextClick() {
        if (tvFeedCommunityPostViewMore.getTag().toString().equalsIgnoreCase(mViewMore)) {
            tvFeedCommunityPostViewMore.setText(mContext.getString(R.string.ID_LESS));
            tvFeedCommunityPostViewMore.setTag(mLess);
            tvFeedCommunityPostText.setTag(mLess);
            tvFeedCommunityPostTextFullView.setTag(mLess);
            tvFeedCommunityPostTextFullView.setVisibility(View.VISIBLE);
            tvFeedCommunityPostText.setVisibility(View.GONE);
            tvFeedCommunityPostTextFullView.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
            tvFeedCommunityPostTextFullView.scrollTo(0, 0);
        } else {
            mViewMoreDescription=dataItem.getShortDescription();
            tvFeedCommunityPostTextFullView.setVisibility(View.GONE);
            tvFeedCommunityPostText.setVisibility(View.VISIBLE);
            tvFeedCommunityPostText.setTag(mViewMore);
            tvFeedCommunityPostTextFullView.setTag(mViewMore);
            int index = 0;
            int lengthOfDesc = mViewMoreDescription.length();
            try {
                if (lengthOfDesc < AppConstants.WORD_LENGTH) {
                    if (mViewMoreDescription.contains(AppConstants.SLASH_N))
                        index = AppUtils.findNthIndexOf(mViewMoreDescription, AppConstants.SLASH_N, 1);
                }
            } catch (Exception e) {

            }
            if (lengthOfDesc > AppConstants.WORD_LENGTH || index > 0 && index < 50) {
                tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
                tvFeedCommunityPostViewMore.setText(mContext.getString(R.string.ID_VIEW_MORE));
                tvFeedCommunityPostViewMore.setTag(mViewMore);
                tvFeedCommunityPostText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
                tvFeedCommunityPostTextFullView.scrollTo(0, 0);
            } else {
                tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                tvFeedCommunityPostViewMore.setText(mContext.getString(R.string.ID_LESS));
                tvFeedCommunityPostViewMore.setTag(mLess);
                tvFeedCommunityPostTextFullView.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
                tvFeedCommunityPostText.scrollTo(0, 0);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_feed_community_post_first: {
                dataItem.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_comunity_post_second: {
                dataItem.setItemPosition(AppConstants.ONE_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_portrait: {
                dataItem.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_portrait: {
                dataItem.setItemPosition(AppConstants.ONE_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_portrait: {
                dataItem.setItemPosition(AppConstants.TWO_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_fourth_portrait: {
                dataItem.setItemPosition(AppConstants.THREE_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_portrait_side_two_image: {
                dataItem.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_portrait_side_two_image: {
                dataItem.setItemPosition(AppConstants.ONE_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_portrait_side_two_image: {
                dataItem.setItemPosition(AppConstants.TWO_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_landscape_with_two_images: {
                dataItem.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_image_landscape_with_two_images: {
                dataItem.setItemPosition(AppConstants.ONE_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_image_landscape_with_two_images: {
                dataItem.setItemPosition(AppConstants.TWO_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_landscape: {
                dataItem.setItemPosition(AppConstants.NO_REACTION_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_image_landscape: {
                dataItem.setItemPosition(AppConstants.ONE_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_image_landscape: {
                dataItem.setItemPosition(AppConstants.TWO_CONSTANT);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_fourth_image_landscape: {
                dataItem.setItemPosition(AppConstants.THREE_CONSTANT);
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
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserBookmark);
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
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
        ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.tv_feed_community_post_total_reactions)
    public void reactionClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostTotalReactions);
    }
    @OnClick(R.id.tv_feed_community_post_reaction1)
    public void reaction1Click() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostTotalReactions);
    }
    @OnClick(R.id.tv_feed_community_post_reaction2)
    public void reaction2Click() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostTotalReactions);
    }
    @OnClick(R.id.tv_feed_community_post_reaction3)
    public void reaction3Click() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostTotalReactions);
    }

    @OnClick(R.id.tv_feed_community_post_user_reaction)
    public void userReactionClick() {
        userReactionWithouLongPress();
    }

    @OnClick(R.id.tv_feed_community_post_user_reaction_text)
    public void userReactionByTextClick() {
        userReactionWithouLongPress();
    }

    private void userReactionWithouLongPress() {
        tvFeedCommunityPostUserReactionText.setEnabled(false);
        tvFeedCommunityPostUserReaction.setEnabled(false);
        dataItem.setTrending(true);
        dataItem.setLongPress(false);
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        }
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            dataItem.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            tvFeedCommunityPostUserReactionText.setText(AppConstants.EMPTY_STRING);
        } else {
            dataItem.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvFeedCommunityPostUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
            tvFeedCommunityPostUserReactionText.setText(mContext.getString(R.string.ID_LOVE));

        }
        allTextViewStringOperations(mContext);
    }

    @OnLongClick(R.id.tv_feed_community_post_user_reaction)
    public boolean userReactionLongClick() {
        userReactionLongPress();
        return true;
    }

    @OnLongClick(R.id.tv_feed_community_post_user_reaction_text)
    public boolean userReactionLongByTextClick() {
        userReactionLongPress();
        return true;
    }

    private void userReactionLongPress() {
        dataItem.setTrending(true);
        Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        dataItem.setLongPress(true);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserReaction);
        ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_CHANGED_REACTIONS, GoogleAnalyticsEventActions.CHANGED_REACTIONS_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
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
        if(StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (dataItem.isAuthorMentor()) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)), 0, feedTitle.length(), 0);
                } else {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                }
            }else {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            }

            if(StringUtil.isNotNullOrEmptyString(postedIn)&&StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(postedInClick, feedTitle.length(), feedTitle.length() + postedIn.length()+3, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length()+2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)),  feedTitle.length(), feedTitle.length() + postedIn.length()+3, 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)),feedTitle.length() + postedIn.length()+2, nameAndCommunity.length(), 0);
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


        if(StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            if(StringUtil.isNotNullOrEmptyString(postedIn)&&StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(postedInClick, feedTitle.length(), nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)),  feedTitle.length(), nameAndCommunity.length(), 0);
            }
            tvFeedCommunityPostCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvFeedCommunityPostCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            tvFeedCommunityPostCardTitle.setSelected(true);
        }
    }
    private void handlingSpamUi(long userId,int adminId)
    {
        if (adminId == AppConstants.TWO_CONSTANT||dataItem.isCommunityOwner()) {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            liReactionCommentBlock.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.VISIBLE);
            tvReviewDescription.setVisibility(View.GONE);
        }else if (dataItem.getAuthorId() == userId) {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.spam_post));
            liCommunityPostMainLayout.setAlpha(.2f);
            flSpamPostUi.setVisibility(View.VISIBLE);
            liReactionCommentBlock.setVisibility(View.GONE);
            liApproveDelete.setVisibility(View.GONE);
            tvReviewDescription.setVisibility(View.VISIBLE);
        } else {
            liCommunityPostMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            liCommunityPostMainLayout.setAlpha(1f);
            flSpamPostUi.setVisibility(View.GONE);
            liReactionCommentBlock.setVisibility(View.VISIBLE);
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