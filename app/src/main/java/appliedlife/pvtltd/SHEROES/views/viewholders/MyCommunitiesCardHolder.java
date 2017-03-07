package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @Bind(R.id.tv_community_tag_lable)
    TextView tvCommunityTag;
    @Bind(R.id.tv_community_join)
    TextView tvCommunityJoin;
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
        if(!dataItem.isApplied())
        {
            tvCommunityJoin.setVisibility(View.VISIBLE);
        }else
        {
            tvCommunityJoin.setVisibility(View.GONE);
        }
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
            }
            String changeDate = LEFT_HTML_VEIW_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + changeDate, 0)); // for 24 api and more
            } else {
                tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.SPACE + changeDate));// or for older api
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
        String authorImageUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {

            ivCommunityCircleIcon.setCircularImage(true);
            ivCommunityCircleIcon.bindImage(authorImageUrl);
        }
        String imageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvTotalViews = (TextView) child.findViewById(R.id.tv_feed_article_total_views);
            //   tvFeedArticleTotalViews.setText(dataItem.getTotalViews() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
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
            liCoverImage.addView(child);
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

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @OnClick(R.id.tv_community_text)
    public void viewMoreClick() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (tvDescriptionText.getTag().toString().equalsIgnoreCase(mViewMore)) {
                String lessWithColor = LEFT_HTML_VEIW_TAG_FOR_COLOR + mLess + RIGHT_HTML_VIEW_TAG_FOR_COLOR;

                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                } else {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + lessWithColor));// or for older api
                }
                tvDescriptionText.setTag(mLess);
            } else {
                tvDescriptionText.setTag(mViewMore);
                String viewMore = LEFT_HTML_VEIW_TAG_FOR_COLOR + mViewMore + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription.substring(0, AppConstants.WORD_COUNT) + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
                } else {
                    tvDescriptionText.setText(Html.fromHtml(mViewMoreDescription.substring(0, AppConstants.WORD_COUNT) + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
                }
            }
        }
    }

    @OnClick(R.id.tv_community_join)
    public void joinClick()
    {
        viewInterface.handleOnClick(dataItem, tvCommunityJoin);
    }

    @Override
    public void onClick(View view) {

    }

}
