package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 19-01-2017.
 */

public class FeatureCardHolder extends BaseViewHolder<Feature> {
    private final String TAG = LogUtils.makeLogTag(FeatureCardHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<font color='#50e3c2'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font>";
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
    @Bind(R.id.tv_featured_community_tag_lable)
    TextView tvFeaturedCommunityTag;
    @Bind(R.id.tv_featured_community_join)
    TextView tvFeaturedCommunityJoin;
    BaseHolderInterface viewInterface;
    private Feature dataItem;


    public FeatureCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(Feature item, final Context context, int position) {
        this.dataItem = item;
        liFeaturedCoverImage.removeAllViews();
        liFeaturedCoverImage.removeAllViewsInLayout();
        imageOperations(context);
    }
    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getArticleCircleIconUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {

            ivFeaturedCommunityCircleIcon.setCircularImage(true);
            ivFeaturedCommunityCircleIcon.bindImage(feedCircleIconUrl);
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            ImageView ivFirstLandscape = (ImageView) child.findViewById(R.id.iv_feed_article_single_image);
            TextView tvFeedArticleTimeLabel = (TextView) child.findViewById(R.id.tv_feed_article_time_label);
            tvFeedArticleTimeLabel.setVisibility(View.GONE);
            TextView tvFeedArticleTotalViews = (TextView) child.findViewById(R.id.tv_feed_article_total_views);
            tvFeedArticleTotalViews.setText(dataItem.getTotalViews() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            Glide.with(context)
                    .load(feedCircleIconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            liFeaturedCoverImage.addView(child);
        }
        String str=dataItem.getDescription();
        if (str.length() > AppConstants.WORD_LENGTH) {
            str = str.substring(0, AppConstants.WORD_COUNT);
        }
        String changeDate = LEFT_HTML_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_TAG_FOR_COLOR;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvFeaturedDescriptionText.setText(Html.fromHtml(str + AppConstants.SPACE + changeDate, 0)); // for 24 api and more
        } else {
            tvFeaturedDescriptionText.setText(Html.fromHtml(str + AppConstants.SPACE + changeDate));// or for older api
        }
    }
    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_featured_community_join:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
