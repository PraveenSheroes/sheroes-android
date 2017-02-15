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
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(ArticleCardHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#50e3c2'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    @Bind(R.id.li_article_cover_image)
    LinearLayout liArticleCoverImage;
    @Bind(R.id.iv_article_circle_icon)
    CircleImageView ivArticleCircleIcon;
    @Bind(R.id.tv_article_card_title)
    TextView tvArticleCardTitle;
    @Bind(R.id.tv_article_time)
    TextView tvArticleTime;
    @Bind(R.id.tv_article_trending_label)
    TextView tvArticleTrendingLabel;
    @Bind(R.id.tv_article_menu)
    TextView ivArticleMenu;
    @Bind(R.id.tv_article_description_header)
    TextView tvArticleDescriptionHeader;
    @Bind(R.id.tv_article_description_text)
    TextView tvArticleDescriptionText;
    @Bind(R.id.tv_article_tag)
    TextView tvArticleTag;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;


    public ArticleCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        liArticleCoverImage.removeAllViews();
        liArticleCoverImage.removeAllViewsInLayout();
        imageOperations(context);
    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {

            ivArticleCircleIcon.setCircularImage(true);
            ivArticleCircleIcon.bindImage(feedCircleIconUrl);

        }
        String backgrndImageUrl = dataItem.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(backgrndImageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_feed_article_single_image);
            ivFirstLandscape.setOnClickListener(this);
            TextView tvFeedArticleTimeLabel = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_time_label);
            TextView tvFeedArticleTotalViews = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_total_views);
        //    tvFeedArticleTotalViews.setText(dataItem.getTotalViews() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            Glide.with(context)
                    .load(feedCircleIconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            liArticleCoverImage.addView(backgroundImage);
        }
        String listShortDescription = dataItem.getListShortDescription();
        if (StringUtil.isNotNullOrEmptyString(listShortDescription)) {
            if (listShortDescription.length() > AppConstants.WORD_LENGTH) {
                listShortDescription = listShortDescription.substring(0, AppConstants.WORD_COUNT);
            }
            String changeDate = LEFT_HTML_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleDescriptionText.setText(Html.fromHtml(listShortDescription + AppConstants.SPACE + changeDate, 0)); // for 24 api and more
            } else {
                tvArticleDescriptionText.setText(Html.fromHtml(listShortDescription + AppConstants.SPACE + changeDate));// or for older api
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_article_menu)
    public void tvMenuClick() {
    }

    @OnClick(R.id.tv_article_trending_label)
    public void tvArticleTrendingClick() {
       /* if (dataItem.getTrending()) {
            tvArticleTrendingLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
            dataItem.setTrending(false);
        } else {
            tvArticleTrendingLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
            dataItem.setTrending(true);
        }*/
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.iv_feed_article_single_image:
                viewInterface.handleOnClick(dataItem, view);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
