package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Vibrator;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    private static final String LEFT_HTML_COMMUNITY_TITLE_FOR_COLOR = "<font color='#f2403c'>";
    private static final String RIGHT_HTML_COMMUNITY_TITLE_FOR_COLOR = "</font>";
    private static final String LEFT_VIEW_MORE = "<font color='#323840'>";
    private static final String RIGHT_VIEW_MORE = "</font>";
    private static final String LEFT_POSTED = "<font color='#8a8d8e'>";
    private static final String RIGHT_POSTED = "</font>";
    @Inject
    DateUtil mDateUtil;
    @Inject
    Preference<LoginResponse> userPreference;
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
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private String mViewMoreDescription;
    private String mViewMore, mLess;
    private Context mContext;
    private int mItemPosition;

    public FeedCommunityPostHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        mViewMore = context.getString(R.string.ID_VIEW_MORE);
        mLess = context.getString(R.string.ID_LESS);
        dataItem.setItemPosition(position);
        tvFeedCommunityPostText.setTag(mViewMore);
        tvFeedCommunityPostUserBookmark.setEnabled(true);
        tvFeedCommunityPostUserReaction.setEnabled(true);
        tvFeedCommunityPostUserReactionText.setEnabled(true);
        if (!dataItem.isTrending()) {
            imageOperations(context);
            multipleImageURLs();
        }
        allTextViewStringOperations(context);

    }

    private void multipleImageURLs() {
        liFeedCommunityUserPostImages.removeAllViews();
        liFeedCommunityUserPostImages.removeAllViewsInLayout();
        if (StringUtil.isNotEmptyCollection(dataItem.getImageUrls())) {
            lineForNoImage.setVisibility(View.GONE);
            List<String> coverImageList = dataItem.getImageUrls();
      /*  List<String> coverImageList = new ArrayList<>();
        coverImageList.add("http://www.hotel-r.net/im/hotel/bg/paris-hotel-21.jpg");
        coverImageList.add("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAhNAAAAJDYwZWIyZTg5LWFmOTItNGIwYS05YjQ5LTM2YTRkNGQ2M2JlNw.jpg");
        coverImageList.add("http://www.tnetnoc.com/hotelphotos/591/327591/2631759-The-Cangkringan-Spa-Villas-Hotel-Yogyakarta-Guest-Room-3-RTS.jpg");
        coverImageList.add("https://media-cdn.tripadvisor.com/media/photo-s/08/dc/c3/be/the-park-lane-jakarta.jpg");
        coverImageList.add("http://balispecialevent.com/images/bali-hotel.jpg");*/
            int listSize = coverImageList.size();
            if (listSize > AppConstants.NO_REACTION_CONSTANT) {
                switch (listSize) {
                    case AppConstants.ONE_CONSTANT:
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0))) {
                            oneImagesSetting(mContext, coverImageList.get(0));
                        }

                        break;
                    case AppConstants.TWO_CONSTANT:
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1))) {
                            twoImagesSetting(mContext, coverImageList.get(0), coverImageList.get(1));
                        }
                        break;
                    case AppConstants.THREE_CONSTANT:
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2))) {
                            boolean isHeightGreater = getCoverImageHeightWidth(coverImageList.get(0));
                           /* if (isHeightGreater) {
                                feedFirstPortraitWithTwoImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                            } else {
                                feedFirstLandscapWIthTwoImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                            }*/
                            feedFirstLandscapWIthTwoImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2));
                        }
                        break;
                    case AppConstants.FOURTH_CONSTANT:
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                            boolean isHeightGreater = getCoverImageHeightWidth(coverImageList.get(0));
                            /*if (isHeightGreater) {
                                feedFirstPortraitImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            } else {
                                feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            }*/
                            feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                        }
                        break;
                    default:
                        if (StringUtil.isNotEmptyCollection(coverImageList) && StringUtil.isNotNullOrEmptyString(coverImageList.get(0)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(1)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(2)) && StringUtil.isNotNullOrEmptyString(coverImageList.get(3))) {
                            boolean isHeightGreater = getCoverImageHeightWidth(coverImageList.get(0));
                         /*   if (isHeightGreater) {
                                feedFirstPortraitImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            } else {
                                feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                            }*/
                            feedFirstLandscapImageModeSetting(mContext, coverImageList.get(0), coverImageList.get(1), coverImageList.get(2), coverImageList.get(3), listSize);
                        }
                }
            }
        }
        else
        {
            lineForNoImage.setVisibility(View.VISIBLE);
        }

    }

    private boolean getCoverImageHeightWidth(String imagePath) {
        final boolean[] isHeightGreater = new boolean[1];
        Glide.with(mContext)
                .load(imagePath).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
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

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            String posted=LEFT_POSTED +  mContext.getString(R.string.ID_POSTED_IN) + RIGHT_POSTED;
            String feedTitle = dataItem.getAuthorName() ;
            //TODO:: change for UI
            String feedCommunityName = dataItem.getPostCommunityName();
            String coloredFeedCommunityName = LEFT_HTML_COMMUNITY_TITLE_FOR_COLOR + feedCommunityName + RIGHT_HTML_COMMUNITY_TITLE_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedCommunityPostCardTitle.setText(Html.fromHtml(feedTitle+ AppConstants.SPACE+posted  + AppConstants.SPACE + coloredFeedCommunityName,0)); // for 24 api and more
            } else {
                tvFeedCommunityPostCardTitle.setText(Html.fromHtml(feedTitle +  AppConstants.SPACE+posted + AppConstants.SPACE + coloredFeedCommunityName));// or for older api
            }
/*

            SpannableString str = new SpannableString(feedTitle +AppConstants.SPACE+ posted +AppConstants.SPACE+feedCommunityName);
            str.setSpan(new StyleSpan(Typeface.BOLD),0,feedTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(new RelativeSizeSpan(1.1f), 0, feedTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(new StyleSpan(Typeface.BOLD), feedTitle.length(),feedTitle.length()+posted.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(new ForegroundColorSpan(Color.parseColor("#f2403c")),feedTitle.length()+posted.length()+1,feedTitle.length()+posted.length()+feedCommunityName.length()+2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvFeedCommunityPostCardTitle.setText(str);
*/

        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvFeedCommunityPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        }
        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
                tvFeedCommunityPostText.setEnabled(true);
                mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);

                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription));// or for older api
                }
                String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
            } else {
                tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                tvFeedCommunityPostText.setEnabled(false);
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription, 0));
                } else {
                    tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription));
                }
            }

        }
        if (dataItem.isBookmarked()) {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedCommunityPostUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
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
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getReactionValue());
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void userComments() {
        List<LastComment> lastCommentList = dataItem.getLastComments();
        LastComment lastComment;
        if (StringUtil.isNotEmptyCollection(lastCommentList)) {
            mItemPosition = lastCommentList.size() - 1;
            lastComment = lastCommentList.get(mItemPosition);
            String feedUserIconUrl = lastComment.getParticipantImageUrl();
            ivFeedCommunityPostUserPic.setCircularImage(true);
            if (lastComment.isAnonymous()) {
                String userName = LEFT_HTML_TAG_FOR_COLOR + mContext.getString(R.string.ID_ANONYMOUS) + RIGHT_HTML_TAG_FOR_COLOR;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment(), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment()));// or for older api
                }
                ivFeedCommunityPostUserPic.setImageResource(R.drawable.ic_anonomous);
            } else {
                String userName = LEFT_HTML_TAG_FOR_COLOR + lastComment.getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment(), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostUserCommentPost.setText(Html.fromHtml(userName + AppConstants.SPACE + lastComment.getComment()));// or for older api
                }
                ivFeedCommunityPostUserPic.bindImage(feedUserIconUrl);
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
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
            ivFeedCommunityPostRegisterUserPic.setCircularImage(true);
            ivFeedCommunityPostRegisterUserPic.bindImage(userPreference.get().getUserSummary().getPhotoUrl());
        }
    }
    private void oneImagesSetting(Context context, String firstImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_single_image, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_single);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);

        liFeedCommunityUserPostImages.addView(child);
    }

    private void twoImagesSetting(Context context, String firstImage, String secondImage) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_two_images, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first);
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_comunity_post_second);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        liFeedCommunityUserPostImages.addView(child);
    }

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
    }

    private void feedFirstLandscapWIthTwoImageModeSetting(Context context, String firstImage, String secondImage, String thirdImage) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_community_post_first_landscape_with_two_images, null);
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_landscape_with_two_images);
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape_with_two_images);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape_with_two_images);
        ivThird.setOnClickListener(this);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);

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
        }
        ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_community_post_first_landscape);
        ivFirstLandscape.setOnClickListener(this);
        Glide.with(context)
                .load(firstImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        ImageView ivSecond = (ImageView) child.findViewById(R.id.iv_feed_community_post_second_image_landscape);
        ivSecond.setOnClickListener(this);
        Glide.with(context)
                .load(secondImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSecond);
        ImageView ivThird = (ImageView) child.findViewById(R.id.iv_feed_community_post_third_image_landscape);
        ivThird.setOnClickListener(this);
        Glide.with(context)
                .load(thirdImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivThird);
        ImageView ivFourth = (ImageView) child.findViewById(R.id.iv_feed_community_post_fourth_image_landscape);
        ivFourth.setOnClickListener(this);
        Glide.with(context)
                .load(fourthImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFourth);
        liFeedCommunityUserPostImages.addView(child);
    }

    @OnClick(R.id.tv_feed_community_post_total_replies)
    public void repliesClick() {
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }

    @OnClick(R.id.li_feed_community_user_post_images)
    public void communityPostImageClick() {
        viewInterface.dataOperationOnClick(dataItem);
        //  viewInterface.handleOnClick(dataItem, liFeedCommunityUserPostImages);
    }

    @OnClick(R.id.li_feed_community_post_join_conversation)
    public void joinConversationClick() {
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }

    @OnClick(R.id.li_feed_community_post_user_comments)
    public void openCommentClick() {
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }
    @OnClick(R.id.tv_feed_community_post_user_comment)
    public void userCommentClick() {
        viewInterface.handleOnClick(dataItem, liFeedCommunityPostJoinConversation);
    }

    @OnClick(R.id.tv_feed_community_post_user_menu)
    public void userMenuClick() {
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserMenu);
    }

    @OnClick(R.id.tv_feed_community_post_user_comment_post_menu)
    public void userCommentMenuClick() {
        dataItem.setItemPosition(mItemPosition);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserCommentPostMenu);
    }

    @OnClick(R.id.tv_feed_community_post_view_more)
    public void textViewMoreClick() {
        viewMoreTextClick();
    }

    @OnClick(R.id.tv_feed_community_post_text)
    public void viewMoreClick() {
        viewMoreTextClick();
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void viewMoreTextClick() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (tvFeedCommunityPostText.getTag().toString().equalsIgnoreCase(mViewMore)) {
                tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                String lessWithColor = LEFT_HTML_VEIW_TAG_FOR_COLOR + mLess + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                mViewMoreDescription = dataItem.getListDescription();
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + AppConstants.SPACE + lessWithColor));// or for older api
                }
                String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeedCommunityPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeedCommunityPostViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
                tvFeedCommunityPostText.setTag(mLess);
                tvFeedCommunityPostViewMore.setVisibility(View.GONE);
            } else {

                tvFeedCommunityPostText.setTag(mViewMore);
                if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                    mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
                    } else {
                        tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription));// or for older api
                    }
                    tvFeedCommunityPostViewMore.setVisibility(View.VISIBLE);
                } else {
                    tvFeedCommunityPostViewMore.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription, 0));
                    } else {
                        tvFeedCommunityPostText.setText(Html.fromHtml(mViewMoreDescription));
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_feed_community_post_first: {
                dataItem.setItemPosition(0);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_comunity_post_second: {
                dataItem.setItemPosition(1);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_portrait: {
                dataItem.setItemPosition(0);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_portrait: {
                dataItem.setItemPosition(1);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_portrait: {
                dataItem.setItemPosition(2);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_fourth_portrait: {
                dataItem.setItemPosition(3);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_portrait_side_two_image: {
                dataItem.setItemPosition(0);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_portrait_side_two_image: {
                dataItem.setItemPosition(1);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_portrait_side_two_image: {
                dataItem.setItemPosition(2);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_landscape_with_two_images: {
                dataItem.setItemPosition(0);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_image_landscape_with_two_images: {
                dataItem.setItemPosition(1);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_image_landscape_with_two_images: {
                dataItem.setItemPosition(2);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_first_landscape: {
                dataItem.setItemPosition(0);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_second_image_landscape: {
                dataItem.setItemPosition(1);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_third_image_landscape: {
                dataItem.setItemPosition(2);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            case R.id.iv_feed_community_post_fourth_image_landscape: {
                dataItem.setItemPosition(3);
                viewInterface.dataOperationOnClick(dataItem);
                break;
            }
            default: {
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
                break;
            }
        }
    }


    @OnClick(R.id.tv_feed_community_post_user_bookmark)
    public void isBookMarkClick() {
        dataItem.setTrending(true);
        tvFeedCommunityPostUserBookmark.setEnabled(false);
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserBookmark);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserBookmark);
        }
    }

    @OnClick(R.id.tv_feed_community_post_total_reactions)
    public void reactionClick() {
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
    private void userReactionWithouLongPress()
    {
        tvFeedCommunityPostUserReactionText.setEnabled(false);
        tvFeedCommunityPostUserReaction.setEnabled(false);
        dataItem.setTrending(true);
        dataItem.setLongPress(false);
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
        }
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
    private void userReactionLongPress()
    {
        dataItem.setTrending(true);
        Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        dataItem.setLongPress(true);
        viewInterface.handleOnClick(dataItem, tvFeedCommunityPostUserReaction);
    }

}