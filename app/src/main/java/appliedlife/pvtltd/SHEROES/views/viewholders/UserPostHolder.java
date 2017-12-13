package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
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

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
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
public class UserPostHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(UserPostHolder.class);
    @Inject
    DateUtil mDateUtil;
    @Inject
    Preference<LoginResponse> userPreference;
    private static final String LEFT_HTML_TAG = "<font color='#3c3c3c'>";
    private static final String RIGHT_HTML_TAG = "</font>";

    @Bind(R.id.view_container)
    LinearLayout viewContainer;

    @Bind(R.id.author_pic_icon)
    CircleImageView mAuthorIcon;

    @Bind(R.id.author_verified_icon)
    ImageView mAuthorVerifiedIcon;

    @Bind(R.id.title)
    TextView mTitle;

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

    private UserPostSolrObj mUserPostObj;
    private Context mContext;
    private int mItemPosition;
    private long mUserId;
    private String loggedInUser;
    private int mAdminId;
    private String mPhotoUrl;
    private Handler mHandler;

    private PostDetailCallBack mPostDetailCallback;

    public UserPostHolder(View itemView, PostDetailCallBack postDetailCallBack) {
        super(itemView);
        this.mPostDetailCallback = postDetailCallBack;
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
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

        this.mUserPostObj = (UserPostSolrObj)item;
        mContext = context;
        mUserPostObj.setItemPosition(position);
        normalCommunityPostUi(mUserId, mAdminId);
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

    private void normalCommunityPostUi(long userId, int adminId) {
        viewContainer.setVisibility(View.VISIBLE);
        mLikeButtonText.setTag(true);
        mUserPostObj.setLastReactionValue(mUserPostObj.getReactionValue());
        if (!mUserPostObj.isTrending()) {
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
        }
        allTextViewStringOperations(mContext);
        likeCommentOps();

        if (mUserPostObj.getAuthorId() == userId || mUserPostObj.isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
            mPostMenu.setVisibility(View.VISIBLE);
            if (mUserPostObj.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
                mPostMenu.setVisibility(View.GONE);
            } else {
            mPostMenu.setVisibility(View.VISIBLE);
            if (mUserPostObj.communityId == 0) {
                mPostMenu.setVisibility(View.GONE);
            } else {
                mPostMenu.setVisibility(View.VISIBLE);
            }
            }
        } else {
            mPostMenu.setVisibility(View.GONE);
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
       /* if (mUserPostObj.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
            mPostDescription.setVisibility(View.GONE);
            userPostImages.removeAllViews();
            userPostImages.removeAllViewsInLayout();
            userPostImages.setVisibility(View.VISIBLE);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.challenge_image, null);
            ImageView ivChallenge = (ImageView) child.findViewById(R.id.iv_feed_challenge);
            TextView tvChallengePost = (TextView) child.findViewById(R.id.tv_challenge_name_post);
            if (StringUtil.isNotEmptyCollection(mUserPostObj.getImageUrls())) {
                tvChallengePost.setText(AppConstants.EMPTY_STRING);
                Glide.with(mContext)
                        .load(mUserPostObj.getImageUrls().get(0))
                        .into(ivChallenge);
            } else {
                mUserPostObj.setListDescription(AppConstants.EMPTY_STRING);
                if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getNameOrTitle())) {
                    tvChallengePost.setText(mUserPostObj.getNameOrTitle());
                }
                ivChallenge.setBackgroundResource(R.drawable.challenge_post);
            }
            userPostImages.addView(child);
        } else {*/
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
            //}
       // }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getAuthorName())) {
            StringBuilder posted = new StringBuilder();
           /* if (mUserPostObj.getCommunityId() == AppConstants.NO_REACTION_CONSTANT) {
                tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                String feedTitle = mUserPostObj.getAuthorName();
                posted.append(feedTitle).append(AppConstants.SPACE).append(LEFT_POSTED).append(mContext.getString(R.string.ID_HAS_ACCEPTED)).append(RIGHT_POSTED).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_HAS_ACCEPTED_CHALLENGE));
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostCardTitle.setText(Html.fromHtml(posted.toString(), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostCardTitle.setText(Html.fromHtml(posted.toString()));// or for older api
                }
            } else {*/
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
                // }
            }
            if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getCreatedDate())) {
                long createdDate = mDateUtil.getTimeInMillis(mUserPostObj.getCreatedDate(), AppConstants.DATE_FORMAT);
                mPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            } else {
                mPostTime.setText(mContext.getString(R.string.ID_JUST_NOW));
            }
    }

    private void likeCommentOps() {
        if (mUserPostObj.getNoOfLikes() < AppConstants.ONE_CONSTANT && mUserPostObj.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
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
        mLikesCount.setText(String.valueOf(mUserPostObj.getNoOfLikes()+AppConstants.SPACE+pluralLikes));
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
        String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, mUserPostObj.getNoOfComments());
        mCommentsCount.setText(String.valueOf(mUserPostObj.getNoOfComments()+AppConstants.SPACE+pluralComments));
    }

    private void populatePostText() {
        final String listDescription = mUserPostObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            mPostDescription.setVisibility(View.GONE);
            return;
        }else
        {
            mPostDescription.setVisibility(View.VISIBLE);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mPostDescription.setMaxLines(Integer.MAX_VALUE);
                mPostDescription.setText(hashTagColorInString(listDescription));
                linkifyURLs(mPostDescription);

                if (mPostDescription.getLineCount() > 4) {
                    collapseFeedPostText();
                } else {
                    mPostDescription.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void collapseFeedPostText() {
        mPostDescription.setMaxLines(4);
        mPostDescription.setVisibility(View.VISIBLE);
        String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
    }

    private void expandFeedPostText() {
        mPostDescription.setMaxLines(Integer.MAX_VALUE);
        mPostDescription.setVisibility(View.VISIBLE);
    }

    private void userLike() {

        switch (mUserPostObj.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
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
            mAuthorIcon.bindImage(authorImageUrl);
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
        userPostImages.addView(child);
    }

    @OnClick(R.id.user_post_images)
    public void communityPostImageClick() {
        mPostDetailCallback.onPostImageClicked(mUserPostObj);
    }

    @OnClick(R.id.post_menu)
    public void userMenuClick() {
        mPostDetailCallback.onPostMenuClicked(mUserPostObj, mPostMenu);
    }

    @OnClick(R.id.tv_spam_post_menu)
    public void spamMenuClick() {
        mPostDetailCallback.onSpamMenuClicked(mUserPostObj, tvSpamPostMenu);
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
            if(mUserPostObj.getReactedValue() != AppConstants.NO_REACTION_CONSTANT){
                mUserPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                mPostDetailCallback.onPostUnLikeClicked(mUserPostObj);
            }else {
                mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                mPostDetailCallback.onPostLikeClicked(mUserPostObj);
            }
        }
    }


 /*   private void userReactionWithouLongPress() {
        mLikeButtonText.setTag(false);
        mUserPostObj.setTrending(true);
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
            mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
        } else {
            mUserPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            mUserPostObj.setNoOfLikes(mUserPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            mLikeButtonText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);


        }
        likeCommentOps();
    }*/

    @OnClick(R.id.author_pic_icon)
    public void onFeedCommunityPostCircleIconClick() {
        if (mUserPostObj.isAuthorMentor()) {
            mPostDetailCallback.onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    private void clickOnMentorAndCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                if (mUserPostObj.isAuthorMentor()) {
                    mPostDetailCallback.onChampionProfileClicked(mUserPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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
                   mPostDetailCallback.onCommunityTitleClicked(mUserPostObj);
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
                SpanString.setSpan(new StyleSpan(Typeface.NORMAL),feedTitle.length(), nameAndCommunity.length(), 0);

            }
            mTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTitle.setSelected(true);
        }
    }

    private void handlingSpamUi(long userId, int adminId) {
        if (adminId == AppConstants.TWO_CONSTANT || mUserPostObj.isCommunityOwner()) {
            viewContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            viewContainer.setAlpha(1f);
            flSpamPostUi.setVisibility(View.VISIBLE);
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
    public void onCommentButtonClicked(){
        mPostDetailCallback.onCommentButtonClicked();
    }

}