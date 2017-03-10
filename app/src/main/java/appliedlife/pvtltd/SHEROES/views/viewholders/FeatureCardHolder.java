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
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by Praveen_Singh on 19-01-2017.
 */

public class FeatureCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeatureCardHolder.class);
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    private static final String LEFT_HTML_TAG = "<font color='#333333'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    @Bind(R.id.li_featured_community_images)
    LinearLayout liFeaturedCoverImage;
    @Bind(R.id.iv_featured_community_card_circle_icon)
    CircleImageView ivFeaturedCommunityCircleIcon;
    @Bind(R.id.tv_featured_community_card_title)
    TextView tvFeaturedCommunityCardTitle;
    @Bind(R.id.tv_featured_community_time)
    TextView tvFeaturedCommunityTime;
    @Bind(R.id.tv_featured_community_text)
    TextView tvFeaturedDescriptionText;
    @Bind(R.id.tv_featured_community_join)
    TextView tvFeaturedCommunityJoin;
    @Bind(R.id.tv_featured_community_tag_lable)
    TextView tvFeaturedCommunityTagLable;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    String mViewMoreDescription;
    String mViewMore, mLess;
    Context mContext;

    public FeatureCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
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
        tvFeaturedDescriptionText.setTag(mViewMore);
        liFeaturedCoverImage.removeAllViews();
        liFeaturedCoverImage.removeAllViewsInLayout();
        imageOperations(context);
        textViewOperation(context);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textViewOperation(Context context) {
        if (dataItem.isClosedCommunity()) {
            tvFeaturedCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        } else {
            tvFeaturedCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (!dataItem.isMember() && !dataItem.isOwner() && !dataItem.isRequestPending()) {

           /* if(dataItem.isClosedCommunity())
            {
                tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_REQUESTED));
                tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
            }
            else
            {
                tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
                tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
            }*/
            tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
            tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
            tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
        } else if (dataItem.isRequestPending()) {
            tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_REQUESTED));
            tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
            tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
        } else if (dataItem.isOwner() || dataItem.isMember()) {
            tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_JOINED));
            tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
            tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle()))
        {
            tvFeaturedCommunityCardTitle.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityType()))

        {
            tvFeaturedCommunityTime.setText(dataItem.getCommunityType());
        }

        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription))

        {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
            }
            String changeDate = LEFT_HTML_VEIW_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + changeDate, 0)); // for 24 api and more
            } else {
                tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + changeDate));// or for older api
            }
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getTags()))

        {
            List<String> tags = dataItem.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA;
            }
            String tagHeader = LEFT_HTML_TAG + context.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeaturedCommunityTagLable.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvFeaturedCommunityTagLable.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }

    }

    private void imageOperations(Context context) {
        String authorImageUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {

            ivFeaturedCommunityCircleIcon.setCircularImage(true);
            ivFeaturedCommunityCircleIcon.bindImage(authorImageUrl);
        }
        String imageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvTotalViews = (TextView) child.findViewById(R.id.tv_feed_article_total_views);
            //  tvFeedArticleTotalViews.setText(dataItem.getTotalViews() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            Glide.with(mContext)
                    .load(imageUrl).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                            tvTotalViews.setVisibility(View.VISIBLE);
                        }
                    });
            liFeaturedCoverImage.addView(child);
        } else {
            liFeaturedCoverImage.setBackgroundResource(R.drawable.ic_image_holder);
        }

    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @OnClick(R.id.tv_featured_community_text)
    public void viewMoreClick() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (tvFeaturedDescriptionText.getTag().toString().equalsIgnoreCase(mViewMore)) {
                String lessWithColor = LEFT_HTML_VEIW_TAG_FOR_COLOR + mLess + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                mViewMoreDescription = dataItem.getListDescription();
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                } else {
                    tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + AppConstants.SPACE + lessWithColor));// or for older api
                }
                tvFeaturedDescriptionText.setTag(mLess);
            } else {
                tvFeaturedDescriptionText.setTag(mViewMore);
                if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                    mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
                }
                String viewMore = LEFT_HTML_VEIW_TAG_FOR_COLOR + mViewMore + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
                } else {
                    tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
                }
            }
        }
    }

    @OnClick(R.id.tv_featured_community_join)
    public void joinClick() {
        Toast.makeText(mContext, "Clciekd", Toast.LENGTH_SHORT).show();
            viewInterface.handleOnClick(dataItem, tvFeaturedCommunityJoin);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

    }

}
