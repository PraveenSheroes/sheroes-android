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
import com.f2prateek.rx.preferences.Preference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(ArticleCardHolder.class);
    @Inject
    DateUtil mDateUtil;
    private static final String LEFT_VIEW_MORE = "<font color='#323840'>";
    private static final String RIGHT_VIEW_MORE = "</font>";
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
    @Bind(R.id.tv_article_share)
    TextView tvArticleShare;
    @Bind(R.id.tv_article_description_header)
    TextView tvArticleDescriptionHeader;
    @Bind(R.id.tv_article_description_text)
    TextView tvArticleDescriptionText;
    @Bind(R.id.tv_article_tag)
    TextView tvArticleTag;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    Context mContext;
    String mViewMoreDescription;
    @Bind(R.id.tv_article_view_more)
    TextView tvArticleView;
    @Inject
    Preference<LoginResponse> mUserPreference;
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
        dataItem.setItemPosition(position);
        mContext = context;
        tvArticleBookmark.setEnabled(true);
        if(!dataItem.isLongPress()) {
            imageOperations(context);
        }
        textRelatedOperation();
        onBookMarkClick();

        if(dataItem != null && StringUtil.isNotNullOrEmptyString(dataItem.getId() )&&StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle()) && null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && mUserPreference.get().getUserSummary() !=null){
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(AppConstants.IMPRESSIONS,AppConstants.ARTICLE_IMPRSSION, dataItem.getId() + AppConstants.DASH +mUserPreference.get().getUserSummary().getUserId() + AppConstants.DASH + dataItem.getNameOrTitle() );
        }

    }
    private void onBookMarkClick() {
        if(dataItem.isBookmarked())
        {
            tvArticleBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_bookmark_active, 0);
        }
        else
        {
            tvArticleBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_bookmark_in_active, 0);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textRelatedOperation()
    {
        String dots = LEFT_VIEW_MORE + AppConstants.DOTS + RIGHT_VIEW_MORE;
        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            Document documentString = Jsoup.parse(mViewMoreDescription);
            tvArticleView.setVisibility(View.VISIBLE);
            tvArticleDescriptionText.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleView.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
            } else {
                tvArticleView.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE) ));// or for older api
            }

            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleDescriptionText.setText(Html.fromHtml(documentString.text(), 0)); // for 24 api and more
            } else {
                tvArticleDescriptionText.setText(Html.fromHtml(documentString.text()));// or for older api
            }
        }
        else
        {
            tvArticleView.setVisibility(View.GONE);
            tvArticleDescriptionText.setVisibility(View.GONE);
        }

        if(dataItem.isTrending())
        {
            tvArticleTrendingLabel.setText(mContext.getString(R.string.ID_TRENDING));
        }
        else
        {
            tvArticleTrendingLabel.setText(AppConstants.EMPTY_STRING);
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
            mergeTags=mergeTags.substring(0,mergeTags.length()-1);
            String tagHeader = LEFT_HTML_TAG + mContext.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleTag.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvArticleTag.setText(Html.fromHtml(tagHeader + AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void imageOperations(Context context) {
        liArticleCoverImage.removeAllViews();
        liArticleCoverImage.removeAllViewsInLayout();
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
            StringBuilder stringBuilder = new StringBuilder();
            if (dataItem.getNoOfViews() > 1) {
                stringBuilder.append(dataItem.getNoOfViews()).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEWS));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else if (dataItem.getNoOfViews() == 1) {
                stringBuilder.append(dataItem.getNoOfViews()).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEW));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleTotalViews.setVisibility(View.GONE);
            }
            stringBuilder = new StringBuilder();
            if (dataItem.getCharCount() > 0) {
                stringBuilder.append(dataItem.getCharCount()).append(AppConstants.SPACE).append(context.getString(R.string.ID_MIN_READ));
                tvFeedArticleTimeLabel.setText(stringBuilder.toString());
                tvFeedArticleTimeLabel.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleTimeLabel.setVisibility(View.INVISIBLE);
            }
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

    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_article_share)
    public void tvMenuClick() {
        viewInterface.handleOnClick(dataItem, tvArticleShare);
    }

    @OnClick(R.id.li_article_cover_image)
    public void articleCoverImageClick() {
        viewInterface.handleOnClick(dataItem, liArticleCoverImage);
    }
    @OnClick(R.id.li_article_decription)
    public void articleCardClick() {
        viewInterface.handleOnClick(dataItem, liArticleCoverImage);
    }

    @OnClick(R.id.tv_article_bookmark)
    public void tvBookMarkClick() {

        tvArticleBookmark.setEnabled(false);
        dataItem.setLongPress(true);
        dataItem.setItemPosition(getAdapterPosition());
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvArticleBookmark);
        } else {
            viewInterface.handleOnClick(dataItem, tvArticleBookmark);
        }
        if (!dataItem.isBookmarked()) {
            dataItem.setBookmarked(true);
        } else {
            dataItem.setBookmarked(false);
        }
        onBookMarkClick();

    }



    @Override
    public void onClick(View view) {


    }

}
