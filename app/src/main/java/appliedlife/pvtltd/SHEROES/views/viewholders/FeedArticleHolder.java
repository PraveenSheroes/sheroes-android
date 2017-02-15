package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.LastComment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 23-01-2017.
 */

public class FeedArticleHolder extends BaseViewHolder<FeedDetail> implements View.OnLongClickListener {
    private final String TAG = LogUtils.makeLogTag(FeedArticleHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    @Bind(R.id.li_feed_article_images)
    LinearLayout liFeedArticleImages;
    @Bind(R.id.li_feed_article_join_conversation)
    LinearLayout liFeedArticleJoinConversation;
    @Bind(R.id.li_feed_article_card_emoji_pop_up)
    LinearLayout liFeedArticlCardEmojiPopUp;
    @Bind(R.id.iv_feed_article_card_circle_icon)
    CircleImageView ivFeedArticleCircleIcon;
    @Bind(R.id.iv_feed_article_user_pic)
    CircleImageView ivFeedArticleUserPic;
    @Bind(R.id.iv_feed_article_register_user_pic)
    CircleImageView ivFeedArticleRegisterUserPic;
    @Bind(R.id.tv_feed_article_user_bookmark)
    TextView tvFeedArticleUserBookmark;
    @Bind(R.id.tv_feed_article_card_title)
    TextView tvFeedArticleCardTitle;
    @Bind(R.id.tv_feed_article_title_label)
    TextView tvFeedArticleTitleLabel;
    @Bind(R.id.iv_feed_article_menu)
    ImageView ivFeedArticleMenu;
    @Bind(R.id.tv_feed_article_header)
    TextView tvFeedArticleHeader;
    @Bind(R.id.tv_feed_article_header_lebel)
    TextView tvFeedArticleHeaderLebel;
    @Bind(R.id.tv_feed_article_user_comment_post)
    TextView tvFeedArticleUserCommentPost;
    @Bind(R.id.tv_feed_article_register_user_comment)
    TextView tvFeedArticleRegisterUserComment;
    @Bind(R.id.tv_feed_article_total_replies)
    TextView tvFeedArticleTotalReplies;
    @Bind(R.id.tv_feed_article_reaction1)
    TextView tvFeedArticleReaction1;
    @Bind(R.id.tv_feed_article_reaction2)
    TextView tvFeedArticleReaction2;
    @Bind(R.id.tv_feed_article_reaction3)
    TextView tvFeedArticleReaction3;
    @Bind(R.id.tv_feed_article_total_reactions)
    TextView tvFeedArticleTotalReactions;
    @Bind(R.id.tv_feed_article_user_menu)
    TextView tvFeedArticleUserMenu;
    @Bind(R.id.tv_feed_article_user_reaction)
    TextView tvFeedArticleUserReaction;
    @Bind(R.id.tv_feed_article_user_comment)
    TextView tvFeedArticleUserComment;
    @Bind(R.id.fl_feed_article_no_reaction_comments)
    FrameLayout flFeedArticleNoReactionComment;
    @Bind(R.id.sp_feed_article_user_menu)
    Spinner spFeedArticleUserMenu;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    String mViewMore, mLess;
    Context mContext;
    List<String> mSpinnerMenuItems;
    int mUserLike;

    public FeedArticleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
        mViewMore = context.getString(R.string.ID_VIEW_MORE);
        mLess = context.getString(R.string.ID_LESS);
        liFeedArticleJoinConversation.setOnClickListener(this);
        tvFeedArticleUserReaction.setOnLongClickListener(this);
        tvFeedArticleUserReaction.setOnClickListener(this);
        tvFeedArticleUserComment.setOnClickListener(this);
        liFeedArticleImages.setOnClickListener(this);
        tvFeedArticleUserMenu.setOnClickListener(this);
        tvFeedArticleHeaderLebel.setOnClickListener(this);
        tvFeedArticleUserBookmark.setOnClickListener(this);
        tvFeedArticleHeaderLebel.setTag(mViewMore);
        liFeedArticleImages.removeAllViews();
        liFeedArticleImages.removeAllViewsInLayout();
        imageOperations(context);
        allTextViewStringOperations(context);
        menuItemForArticleClick();
    }


    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvFeedArticleCardTitle.setText(dataItem.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            //   tvFeedArticleTitleLabel.setText(dataItem.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvFeedArticleHeader.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getListShortDescription())) {
            String description = dataItem.getListShortDescription().trim();
            LogUtils.info("Article", "***********Short description******" + description);
            if (description.length() > AppConstants.WORD_LENGTH) {
                description = description.substring(0, AppConstants.WORD_COUNT);
            }
            String viewMore = LEFT_HTML_VEIW_TAG_FOR_COLOR + mViewMore + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
            } else {
                tvFeedArticleHeaderLebel.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
            }

        }
        /*if (dataItem.getBookmarked()) {
            tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        }*/
        mUserLike = dataItem.getNoOfLikes();
        if (mUserLike < 1 && dataItem.getNoOfComments() < 1) {
            tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            flFeedArticleNoReactionComment.setVisibility(View.GONE);
        } else if (mUserLike < 1) {
            flFeedArticleNoReactionComment.setVisibility(View.GONE);
            tvFeedArticleTotalReactions.setVisibility(View.GONE);
        } else {
            flFeedArticleNoReactionComment.setVisibility(View.VISIBLE);
            switch (mUserLike) {
                case 1:
                    tvFeedArticleTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION));
                    userLike();
                    break;
                default:
                    tvFeedArticleTotalReactions.setText(String.valueOf(dataItem.getNoOfLikes()) + AppConstants.SPACE + context.getString(R.string.ID_REACTION) + AppConstants.S);
                    userLike();
            }
        }
        if (dataItem.getNoOfComments() < 1) {
            tvFeedArticleTotalReplies.setVisibility(View.GONE);
        } else {
            switch (dataItem.getNoOfComments()) {
                case 1:
                    tvFeedArticleTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLY));
                    userComments();
                    break;
                default:
                    tvFeedArticleTotalReplies.setText(String.valueOf(dataItem.getNoOfComments()) + AppConstants.SPACE + context.getString(R.string.ID_REPLIES));
                    userComments();
            }
        }
    }

    private void userLike() {

        switch (dataItem.getReactionValue()) {
            case 0:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                break;
            case 10:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                break;
            case 20:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji_xo_xo, 0, 0, 0);
                break;
            case 30:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji2_with_you, 0, 0, 0);
                break;
            case 40:
                tvFeedArticleUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_emoji3_whistel, 0, 0, 0);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + dataItem.getReactionValue());
        }
    }

    private void userComments() {
        LastComment lastComment = dataItem.getLastComment();
        if (null != lastComment) {
            String feedUserIconUrl = lastComment.getParticipantImageUrl();
            ivFeedArticleUserPic.setCircularImage(true);
            ivFeedArticleUserPic.bindImage(feedUserIconUrl);
            String userName = LEFT_HTML_TAG_FOR_COLOR + lastComment.getParticipantName() + RIGHT_HTML_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + lastComment.getComment(), 0)); // for 24 api and more
            } else {
                tvFeedArticleUserCommentPost.setText(Html.fromHtml(userName + AppConstants.COLON + lastComment.getComment()));// or for older api
            }

        }
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {

            ivFeedArticleCircleIcon.setCircularImage(true);
            ivFeedArticleCircleIcon.bindImage(feedCircleIconUrl);
            ivFeedArticleRegisterUserPic.setCircularImage(true);
            ivFeedArticleRegisterUserPic.bindImage(feedCircleIconUrl);
        }
        String backgrndImageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(backgrndImageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_feed_article_single_image);
            TextView tvFeedArticleTimeLabel = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_time_label);
            TextView tvFeedArticleTotalViews = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_total_views);
            //  tvFeedArticleTotalViews.setText(dataItem.getNoOfComments() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            Glide.with(context)
                    .load(backgrndImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            liFeedArticleImages.addView(backgroundImage);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_feed_article_total_replies)
    public void repliesClick() {
        viewInterface.handleOnClick(dataItem, tvFeedArticleTotalReplies);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_article_images:
                viewInterface.handleOnClick(dataItem, liFeedArticleImages);
                break;
            case R.id.tv_feed_article_user_reaction:
                if (dataItem.getReactionValue() == 10) {
                    if(mUserLike>0) {
                        dataItem.setNoOfLikes(mUserLike-1);
                    }
                    dataItem.setReactionValue(0);
                    if (liFeedArticlCardEmojiPopUp.getVisibility() == View.VISIBLE) {
                        viewInterface.handleOnClick(dataItem, tvFeedArticleUserReaction);
                    }
                    viewInterface.userCommentLikeRequest(dataItem.getEntityOrParticipantId(), 1);

                } else {
                    if (liFeedArticlCardEmojiPopUp.getVisibility() == View.VISIBLE) {
                        viewInterface.handleOnClick(dataItem, tvFeedArticleUserReaction);
                    }
                    dataItem.setNoOfLikes(mUserLike+ 1);
                    dataItem.setReactionValue(10);
                    viewInterface.userCommentLikeRequest(dataItem.getEntityOrParticipantId(), 10);
                }
                break;
            case R.id.li_feed_article_join_conversation:
                viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
                break;
            case R.id.tv_feed_article_user_comment:
                viewInterface.handleOnClick(dataItem, tvFeedArticleUserComment);
                break;
            case R.id.tv_feed_article_user_bookmark:
             /*   if (dataItem.getBookmarked()) {
                    tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
                    dataItem.setBookmarked(false);
                } else {
                    tvFeedArticleUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
                    dataItem.setBookmarked(true);
                }*/
                break;
            case R.id.tv_feed_article_header_lebel:
                if (tvFeedArticleHeaderLebel.getTag().toString().equalsIgnoreCase(mViewMore)) {
                    if (StringUtil.isNotNullOrEmptyString(dataItem.getListShortDescription())) {
                        String description = dataItem.getListShortDescription();
                        String lessWithColor = LEFT_HTML_VEIW_TAG_FOR_COLOR + mLess + RIGHT_HTML_VIEW_TAG_FOR_COLOR;

                        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                            tvFeedArticleHeaderLebel.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                        } else {
                            tvFeedArticleHeaderLebel.setText(Html.fromHtml(description + AppConstants.DOTS + AppConstants.SPACE + lessWithColor));// or for older api
                        }
                        tvFeedArticleHeaderLebel.setTag(mLess);
                    }
                } else {
                    tvFeedArticleHeaderLebel.setTag(mViewMore);
                    if (StringUtil.isNotNullOrEmptyString(dataItem.getListShortDescription())) {
                        String description = dataItem.getListShortDescription();
                        String viewMore = LEFT_HTML_VEIW_TAG_FOR_COLOR + mViewMore + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                            tvFeedArticleHeaderLebel.setText(Html.fromHtml(description.substring(0, AppConstants.WORD_COUNT) + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
                        } else {
                            tvFeedArticleHeaderLebel.setText(Html.fromHtml(description.substring(0, AppConstants.WORD_COUNT) + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
                        }
                    }
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
    }


    @Override
    public boolean onLongClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.tv_feed_article_user_reaction:
                //  viewInterface.handleOnClick(dataItem, tvFeedArticleUserReaction);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
        return true;
    }

    public void menuItemForArticleClick() {
        mSpinnerMenuItems = new ArrayList();
        mSpinnerMenuItems.add(mContext.getString(R.string.ID_SHARE));
        mSpinnerMenuItems.add(mContext.getString(R.string.ID_REPORT));
        mSpinnerMenuItems.add(AppConstants.SPACE);
        ArrayAdapter<String> spinClockInWorkSiteAdapter = new ArrayAdapter<>(mContext, R.layout.about_community_spinner_row_back, mSpinnerMenuItems);
        spFeedArticleUserMenu.setAdapter(spinClockInWorkSiteAdapter);
        spFeedArticleUserMenu.setSelection(2);
        spFeedArticleUserMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (null != view) {
                    String msupplier = parent.getItemAtPosition(position).toString();
                    ((TextView) view).setTextSize(0);
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.fully_transparent));
                    LogUtils.info("Selected item : ", spFeedArticleUserMenu.getSelectedItem().toString());
                    LogUtils.info("Selected item position : ", "" + position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}