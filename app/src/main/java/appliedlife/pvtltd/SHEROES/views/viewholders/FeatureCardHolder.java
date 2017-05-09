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
 * Created by Praveen_Singh on 19-01-2017.
 */

public class FeatureCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeatureCardHolder.class);
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    private static final String LEFT_HTML_TAG = "<font color='#000000'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    private static final String LEFT_VIEW_MORE = "<font color='#323840'>";
    private static final String RIGHT_VIEW_MORE = "</font>";
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
    @Bind(R.id.tv_featured_community_text_full_view)
    TextView tvFeaturedDescriptionTextFullView;

    @Bind(R.id.tv_featured_view_more)
    TextView tvFeaturedViewMore;
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
        dataItem.setItemPosition(position);
        tvFeaturedDescriptionText.setTag(mViewMore);
        tvFeaturedDescriptionTextFullView.setTag(mViewMore);
        textViewOperation(context);
        if (!dataItem.isTrending()) {
            liFeaturedCoverImage.removeAllViews();
            liFeaturedCoverImage.removeAllViewsInLayout();
            imageOperations(context);
        }

    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textViewOperation(Context context) {
        if (dataItem.isClosedCommunity()) {
            tvFeaturedCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        } else {
            tvFeaturedCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (!dataItem.isMember() && !dataItem.isOwner() && !dataItem.isRequestPending()) {
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
        //TODO:: change for UI
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvFeaturedCommunityCardTitle.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityType()))
        {
            tvFeaturedCommunityTime.setText(dataItem.getCommunityType());
        }

        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                tvFeaturedViewMore.setVisibility(View.VISIBLE);
            } else {
                tvFeaturedViewMore.setVisibility(View.GONE);
            }
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
            } else {
                tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription));// or for older api
            }
            String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeaturedViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
            } else {
                tvFeaturedViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
            }
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getTags()))

        {
            List<String> tags = dataItem.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA;
            }
            mergeTags = mergeTags.substring(0, mergeTags.length() - 1);
            String tagHeader = LEFT_HTML_TAG + context.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeaturedCommunityTagLable.setText(Html.fromHtml(tagHeader +AppConstants.SPACE+ AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvFeaturedCommunityTagLable.setText(Html.fromHtml(tagHeader+AppConstants.SPACE+ AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }

    }

    private void imageOperations(Context context) {
        String authorImageUrl = dataItem.getThumbnailImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {

            ivFeaturedCommunityCircleIcon.setCircularImage(true);
            ivFeaturedCommunityCircleIcon.bindImage(authorImageUrl);
        }
        String imageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.communities_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_community_single_image);
            final TextView time = (TextView) backgroundImage.findViewById(R.id.tv_community_time_label);
            time.setVisibility(View.INVISIBLE);
            final TextView tvTotalMember = (TextView) backgroundImage.findViewById(R.id.tv_community_total_views);
            final RelativeLayout rlFeedArticleViews = (RelativeLayout) backgroundImage.findViewById(R.id.rl_gradiant);
            tvTotalMember.setText(dataItem.getNoOfMembers() + AppConstants.SPACE + context.getString(R.string.ID_MEMBERS));
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
            liFeaturedCoverImage.addView(backgroundImage);
        } else {
            liFeaturedCoverImage.setBackgroundResource(R.drawable.ic_image_holder);
        }

    }

    @OnClick(R.id.tv_featured_view_more)
    public void viewMoreTextClick() {
        viewText();
    }

    @OnClick(R.id.tv_featured_community_text)
    public void viewMoreClick() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                viewText();
            }
        }
    }
    @OnClick(R.id.tv_featured_community_text_full_view)
    public void viewMoreFullViewClick() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                viewText();
            }
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void viewText() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (tvFeaturedDescriptionText.getTag().toString().equalsIgnoreCase(mViewMore)) {
                String lessWithColor = LEFT_HTML_VEIW_TAG_FOR_COLOR + mLess + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                mViewMoreDescription = dataItem.getListDescription();
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeaturedDescriptionTextFullView.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                } else {
                    tvFeaturedDescriptionTextFullView.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + AppConstants.SPACE + lessWithColor));// or for older api
                }
                tvFeaturedDescriptionText.setVisibility(View.GONE);
                tvFeaturedDescriptionTextFullView.setVisibility(View.VISIBLE);
                tvFeaturedDescriptionText.setTag(mLess);
                tvFeaturedDescriptionTextFullView.setTag(mLess);
                tvFeaturedViewMore.setVisibility(View.GONE);
            } else {
                tvFeaturedDescriptionText.setTag(mViewMore);
                tvFeaturedDescriptionTextFullView.setTag(mViewMore);
                if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                    tvFeaturedViewMore.setVisibility(View.VISIBLE);
                } else {
                    tvFeaturedViewMore.setVisibility(View.GONE);
                }
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
                } else {
                    tvFeaturedDescriptionText.setText(Html.fromHtml(mViewMoreDescription));// or for older api
                }
                tvFeaturedDescriptionTextFullView.setVisibility(View.GONE);
                tvFeaturedDescriptionText.setVisibility(View.VISIBLE);
                String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvFeaturedViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
                } else {
                    tvFeaturedViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
                }
            }
        }
    }

    @OnClick(R.id.tv_featured_community_join)
    public void joinClick() {
        dataItem.setTrending(true);
        if (tvFeaturedCommunityJoin.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_JOIN))) {
            viewInterface.handleOnClick(dataItem, tvFeaturedCommunityJoin);
        }
    }

    @OnClick(R.id.li_featured_community_images)
    public void detailImageClick() {
        viewInterface.handleOnClick(dataItem, liFeaturedCoverImage);
    }

    @OnClick(R.id.card_feature_communities)
    public void featureClick() {
        viewInterface.handleOnClick(dataItem, liFeaturedCoverImage);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

    }

}
