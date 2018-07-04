package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ArticleTextView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

public class ArticleCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(ArticleCardHolder.class);
    @Inject
    DateUtil mDateUtil;

    @Inject
    Preference<Configuration> mConfiguration;

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

    @BindDimen(R.dimen.dp_size_40)
    int authorProfileSize;

    private ArticleSolrObj dataItem;
    Context mContext;
    String mViewMoreDescription;
    @Inject
    Preference<LoginResponse> mUserPreference;
    private boolean isWhatappShareOption = false;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    public ArticleCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareOption = "";
            shareOption = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareOption)) {
                if (shareOption.equalsIgnoreCase("true")) {
                    isWhatappShareOption = true;
                }
            }
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = (ArticleSolrObj) item;
        dataItem.setItemPosition(position);
        mContext = context;
        tvArticleBookmark.setEnabled(true);
        if (!dataItem.isLongPress()) {
            imageOperations(context);
        }
        textRelatedOperation();
        onBookMarkClick();
    }

    private void onBookMarkClick() {
        if (dataItem.isBookmarked()) {
            tvArticleBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvArticleBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textRelatedOperation() {
        if (isWhatappShareOption) {
            tvArticleShare.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext, R.drawable.ic_share_card), null);
        } else {
            tvArticleShare.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext, R.drawable.ic_share_black), null);
        }

        mViewMoreDescription = dataItem.getShortDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            tvArticleDescriptionText.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription, 0)); // for 24 api and more
            } else {
                tvArticleDescriptionText.setText(Html.fromHtml(mViewMoreDescription));// or for older api
            }
            ArticleTextView.doResizeTextView(tvArticleDescriptionText, 4, AppConstants.VIEW_MORE_TEXT, true);
        } else {
            tvArticleDescriptionText.setVisibility(View.GONE);
        }
        if (dataItem.isTrending()) {
            tvArticleTrendingLabel.setText(mContext.getString(R.string.ID_TRENDING));
        } else {
            tvArticleTrendingLabel.setText(AppConstants.EMPTY_STRING);
        }

        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvArticleCardTitle.setText(dataItem.getAuthorName());
            String articleObjAuthorName = dataItem.getAuthorName();
            SpannableString SpanString = new SpannableString(articleObjAuthorName);
            ClickableSpan authorTitle = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    viewInterface.navigateToProfileView(dataItem, AppConstants.REQUEST_CODE_FOR_USER_PROFILE_DETAIL);
                }

                @Override
                public void updateDrawState(final TextPaint textPaint) {
                    textPaint.setUnderlineText(false);
                }
            };

            SpanString.setSpan(authorTitle, 0, articleObjAuthorName.length(), 0);
            tvArticleCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvArticleCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            tvArticleCardTitle.setSelected(true);

        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
            if (dataItem.getCharCount() > 0) {
                stringBuilder.append(AppConstants.DOT).append(dataItem.getCharCount()).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_MIN_READ));
            }
            tvArticleTime.setText(stringBuilder);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvArticleDescriptionHeader.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getTags())) {
            List<String> tags = dataItem.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA + AppConstants.SPACE;
            }
            mergeTags = mergeTags.substring(0, mergeTags.length() - 2);
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
            String authorThumborUrl = CommonUtil.getThumborUri(feedCircleIconUrl, authorProfileSize, authorProfileSize);
            ivArticleCircleIcon.bindImage(authorThumborUrl);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getImageUrl())) {
            String backgrndImageUrl = dataItem.getImageUrl();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            final ImageView ivFirstLandscape = backgroundImage.findViewById(R.id.iv_feed_article_single_image);

            final TextView tvFeedArticleTotalViews = backgroundImage.findViewById(R.id.tv_feed_article_total_views);
            final RelativeLayout rlFeedArticleViews = backgroundImage.findViewById(R.id.rl_gradiant);
            final ProgressBar pbImage = backgroundImage.findViewById(R.id.pb_article_image);
            StringBuilder stringBuilder = new StringBuilder();
            if (dataItem.getNoOfViews() > 1) {
                stringBuilder.append(numericToThousand(dataItem.getNoOfViews())).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEWS));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else if (dataItem.getNoOfViews() == 1) {
                stringBuilder.append(dataItem.getNoOfViews()).append(AppConstants.SPACE).append(context.getString(R.string.ID_VIEW));
                tvFeedArticleTotalViews.setText(stringBuilder.toString());
                tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
            } else {
                tvFeedArticleTotalViews.setVisibility(View.GONE);
            }

            if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null) {
                if (mConfiguration.get().configData.showArticleViews) {
                    tvFeedArticleTotalViews.setVisibility(View.VISIBLE);
                } else {
                    tvFeedArticleTotalViews.setVisibility(View.GONE);
                }
            } else {
                tvFeedArticleTotalViews.setVisibility(View.GONE);
            }


            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.photo_placeholder)
                    .error(R.color.photo_placeholder)
                    .priority(Priority.HIGH);

            int imageWidth = CommonUtil.getWindowWidth(context);
            int imageHeight = CommonUtil.getWindowWidth(context) / 2;
            String thumborFeatureImageUrl = CommonUtil.getThumborUri(backgrndImageUrl, imageWidth, imageHeight);
            Glide.with(mContext)
                    .asBitmap()
                    .apply(options)
                    .load(thumborFeatureImageUrl)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                            rlFeedArticleViews.setVisibility(View.VISIBLE);
                            pbImage.setVisibility(View.GONE);
                        }
                    });

            liArticleCoverImage.addView(backgroundImage);

        }

    }

    @Override
    public void viewRecycled() {

    }

    @OnClick({R.id.tv_article_card_title,R.id.iv_article_circle_icon})
    public void articleAuthorNameClick() {
        viewInterface.navigateToProfileView(dataItem, AppConstants.REQUEST_CODE_FOR_USER_PROFILE_DETAIL);
        //viewInterface.handleOnClick(dataItem, tvArticleCardTitle);
        //   ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ARTICLE, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.tv_article_share)
    public void tvMenuClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostShared(dataItem);
        } else {
            viewInterface.handleOnClick(dataItem, tvArticleShare);
        }
        ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ARTICLE, AppConstants.EMPTY_STRING);
    }

    @OnClick({R.id.li_article_cover_image, R.id.li_article_decription, R.id.tv_article_description_text})
    public void articleCoverImageClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onArticleItemClicked(dataItem);
        } else {
            viewInterface.handleOnClick(dataItem, liArticleCoverImage);
        }
    }

    @OnClick(R.id.tv_article_bookmark)
    public void tvBookMarkClick() {
        dataItem.setLongPress(true);
        dataItem.setItemPosition(getAdapterPosition());
        if (dataItem.isBookmarked()) {
            if (viewInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) viewInterface).onArticleUnBookMarkClicked(dataItem);
            } else {
                viewInterface.handleOnClick(dataItem, tvArticleBookmark);
            }
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
        } else {
            if (viewInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) viewInterface).onArticleBookMarkClicked(dataItem);
            } else {
                viewInterface.handleOnClick(dataItem, tvArticleBookmark);
            }
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARKED_ON_ARTICLE, AppConstants.EMPTY_STRING);
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
