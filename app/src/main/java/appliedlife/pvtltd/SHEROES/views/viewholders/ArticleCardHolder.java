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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(ArticleCardHolder.class);
    @Inject
    DateUtil mDateUtil;
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#50e3c2'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    private static final String LEFT_HTML_TAG = "<font color='#333333'>";
    private static final String RIGHT_HTML_TAG = "</font>";
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
    @Bind(R.id.tv_article_bookmark)
    TextView tvArticleBookmark;
    @Bind(R.id.tv_article_menu)
    TextView tvArticleMenu;
    @Bind(R.id.tv_article_description_header)
    TextView tvArticleDescriptionHeader;
    @Bind(R.id.tv_article_description_text)
    TextView tvArticleDescriptionText;
    @Bind(R.id.tv_article_tag)
    TextView tvArticleTag;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    Context mContext;
    String mViewMore,mLess;
    String mViewMoreDescription;
    public ArticleCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        mViewMore = context.getString(R.string.ID_VIEW_MORE);
        mLess = context.getString(R.string.ID_LESS);
        tvArticleDescriptionText.setTag(mViewMore);
        liArticleCoverImage.removeAllViews();
        liArticleCoverImage.removeAllViewsInLayout();

        if(dataItem.isTrending())
        {
            tvArticleTrendingLabel.setText(mContext.getString(R.string.ID_TRENDING));
        }
        else
        {
            tvArticleTrendingLabel.setText(AppConstants.EMPTY_STRING);
        }
        if(dataItem.isBookmarked())
        {
            tvArticleBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_bookmark_active, 0);
        }
        else
        {
            tvArticleBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_bookmark_in_active, 0);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvArticleCardTitle.setText(dataItem.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvArticleTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));


        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvArticleDescriptionHeader.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getTags())) {
            List<String> tags = dataItem.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA;
            }
            String tagHeader = LEFT_HTML_TAG + context.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleTag.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvArticleTag.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }
        imageOperations(context);
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void imageOperations(Context context) {

        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorImageUrl())) {
            String feedCircleIconUrl = dataItem.getAuthorImageUrl();
            ivArticleCircleIcon.setCircularImage(true);
            ivArticleCircleIcon.bindImage(feedCircleIconUrl);

        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getImageUrl())) {
            String backgrndImageUrl = dataItem.getImageUrl();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_feed_article_single_image);
            final TextView tvFeedArticleTimeLabel = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_time_label);
            final TextView tvFeedArticleTotalViews = (TextView) backgroundImage.findViewById(R.id.tv_feed_article_total_views);
            final RelativeLayout rlFeedArticleViews = (RelativeLayout) backgroundImage.findViewById(R.id.rl_gradiant);
            tvFeedArticleTotalViews.setText(dataItem.getNoOfViews() + AppConstants.SPACE + context.getString(R.string.ID_VIEWS));
            Glide.with(mContext)
                    .load(backgrndImageUrl).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                            rlFeedArticleViews.setVisibility(View.VISIBLE);
                        }
                    });
            liArticleCoverImage.addView(backgroundImage);
        }

        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
            }
            String viewMore = LEFT_HTML_TAG_FOR_COLOR + mViewMore + RIGHT_HTML_TAG_FOR_COLOR;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
            } else {
                tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_article_menu)
    public void tvMenuClick() {
        viewInterface.handleOnClick(dataItem, tvArticleMenu);
    }

    @OnClick(R.id.li_article_cover_image)
    public void articleCoverImageClick() {
        viewInterface.handleOnClick(dataItem, liArticleCoverImage);
    }
    @OnClick(R.id.card_article)
    public void articleCardClick() {
        viewInterface.handleOnClick(dataItem, liArticleCoverImage);
    }

    @OnClick(R.id.tv_article_bookmark)
    public void tvBookMarkClick() {
        dataItem.setItemPosition(getAdapterPosition());
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvArticleBookmark);
        } else {
            viewInterface.handleOnClick(dataItem, tvArticleBookmark);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @OnClick(R.id.tv_article_description_text)
    public void viewMoreClick() {
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            if (tvArticleDescriptionText.getTag().toString().equalsIgnoreCase(mViewMore)) {
                String lessWithColor = LEFT_HTML_TAG_FOR_COLOR + mLess + RIGHT_HTML_TAG_FOR_COLOR;
                mViewMoreDescription = dataItem.getListDescription();
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription  + AppConstants.SPACE + lessWithColor, 0)); // for 24 api and more
                } else {
                    tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription  + AppConstants.SPACE + lessWithColor));// or for older api
                }
                tvArticleDescriptionText.setTag(mLess);
            } else {
                tvArticleDescriptionText.setTag(mViewMore);
                if (mViewMoreDescription.length() > AppConstants.WORD_LENGTH) {
                    mViewMoreDescription = mViewMoreDescription.substring(0, AppConstants.WORD_COUNT);
                }
                String viewMore = LEFT_HTML_TAG_FOR_COLOR + mViewMore + RIGHT_HTML_TAG_FOR_COLOR;
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + viewMore, 0)); // for 24 api and more
                } else {
                    tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription + AppConstants.DOTS + AppConstants.SPACE + viewMore));// or for older api
                }
            }
        }
    }


    @Override
    public void onClick(View view) {


    }

}
