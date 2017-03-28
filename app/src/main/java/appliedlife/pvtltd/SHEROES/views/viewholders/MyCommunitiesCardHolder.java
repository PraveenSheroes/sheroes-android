package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 30-01-2017.
 */

public class MyCommunitiesCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(MyCommunitiesCardHolder.class);
    private static final String LEFT_HTML_TAG = "<font color='#333333'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    private static final String LEFT_VIEW_MORE = "<font color='#323840'>";
    private static final String RIGHT_VIEW_MORE = "</font>";
    @Bind(R.id.li_community_images)
    LinearLayout liCoverImage;
    @Bind(R.id.iv_community_card_circle_icon)
    CircleImageView ivCommunityCircleIcon;
    @Bind(R.id.tv_community_card_title)
    TextView tvCommunityCardTitle;
    @Bind(R.id.tv_community_time)
    TextView tvCommunityTime;
    @Bind(R.id.tv_community_text)
    TextView tvDescriptionText;
    @Bind(R.id.tv_my_community_view_more)
    TextView tvMyCommunityViewMore;
    @Bind(R.id.tv_community_tag_lable)
    TextView tvCommunityTag;
    @Bind(R.id.tv_community_detail_invite)
    TextView tvCommunityInvite;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    String mViewMoreDescription;
    String mViewMore, mLess;
    Context mContext;

    public MyCommunitiesCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
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
        tvDescriptionText.setTag(mViewMore);
        liCoverImage.removeAllViews();
        liCoverImage.removeAllViewsInLayout();
        imageOperations(context);
        textViewOperation(context);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textViewOperation(Context context) {
        if (dataItem.isClosedCommunity()) {
            tvCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        } else {
            tvCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (dataItem.isMember() && !dataItem.isOwner()) {
            tvCommunityInvite.setVisibility(View.VISIBLE);
            tvCommunityInvite.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvCommunityInvite.setText(mContext.getString(R.string.ID_VIEW));
            tvCommunityInvite.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvCommunityInvite.setVisibility(View.VISIBLE);
            tvCommunityInvite.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvCommunityInvite.setText(mContext.getString(R.string.ID_INVITE));
            tvCommunityInvite.setBackgroundResource(R.drawable.rectangle_community_invite);
        }
        //TODO:: change for UI
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvCommunityCardTitle.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityType())) {
            tvCommunityTime.setText(dataItem.getCommunityType());
        }

        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
                tvMyCommunityViewMore.setVisibility(View.VISIBLE);
            } else {
                tvMyCommunityViewMore.setVisibility(View.GONE);
            }
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
            } else {
                tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription));// or for older api
            }
            String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvMyCommunityViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
            } else {
                tvMyCommunityViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
            }
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getTags())) {
            List<String> tags = dataItem.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA;
            }
            String tagHeader = LEFT_HTML_TAG + context.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvCommunityTag.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvCommunityTag.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }
    }

    private void imageOperations(Context context) {
        // dataItem.setAuthorImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14845475641484547564.png");
        String authorImageUrl = dataItem.getThumbnailImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {

            ivCommunityCircleIcon.setCircularImage(true);
            ivCommunityCircleIcon.bindImage(authorImageUrl);
        }
        //  dataItem.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14845475641484547564.png");
        String imageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvTotalMember = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_total_views);
            final TextView time = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_time_label);
            time.setVisibility(View.INVISIBLE);
            final RelativeLayout rlFeedArticleViews = (RelativeLayout) backgroundImage.findViewById(R.id.rl_gradiant);
            //TODO: Need to change members
            tvTotalMember.setText(AppConstants.SPACE + context.getString(R.string.ID_MEMBERS));
            Glide.with(mContext)
                    .load(imageUrl).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                            rlFeedArticleViews.setVisibility(View.VISIBLE);
                        }
                    });
            liCoverImage.addView(backgroundImage);
        } else {
            liCoverImage.setBackgroundResource(R.drawable.ic_image_holder);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.li_community_images)
    public void detailImageClick() {
        viewInterface.handleOnClick(dataItem, liCoverImage);
    }

    @OnClick(R.id.card_my_communities)
    public void myCardClick() {
        viewInterface.handleOnClick(dataItem, liCoverImage);
    }

    @OnClick(R.id.tv_my_community_view_more)
    public void viewMoreTextClick() {
        viewMoreText();
    }

    @OnClick(R.id.tv_community_text)
    public void viewMoreClick() {
        viewMoreText();
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void viewMoreText() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (tvDescriptionText.getTag().toString().equalsIgnoreCase(mViewMore)) {
                String lessWithColor = LEFT_HTML_VEIW_TAG_FOR_COLOR + mLess + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                mViewMoreDescription = dataItem.getListDescription();
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                } else {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + lessWithColor));// or for older api
                }
                tvDescriptionText.setTag(mLess);
                tvMyCommunityViewMore.setVisibility(View.GONE);
            } else {
                tvDescriptionText.setTag(mViewMore);
                if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                    mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
                    tvMyCommunityViewMore.setVisibility(View.VISIBLE);
                } else {
                    tvMyCommunityViewMore.setVisibility(View.GONE);
                }
                String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvMyCommunityViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvMyCommunityViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
                } else {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription));// or for older api
                }
            }
        }
    }

    @OnClick(R.id.tv_community_detail_invite)
    public void joinClick() {
        if (tvCommunityInvite.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_VIEW))) {
            viewInterface.handleOnClick(dataItem, liCoverImage);
        } else {
            viewInterface.handleOnClick(dataItem, tvCommunityInvite);
        }
    }

    @Override
    public void onClick(View view) {

    }

}
