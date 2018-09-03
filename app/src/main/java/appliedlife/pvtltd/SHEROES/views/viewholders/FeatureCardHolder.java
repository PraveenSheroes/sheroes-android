package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by Praveen_Singh on 19-01-2017.
 */

public class FeatureCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeatureCardHolder.class);
    private static final String LEFT_HTML_TAG = "<font color='#000000'>";
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
    @Bind(R.id.tv_feature_communities_view_more)
    TextView tvFeatureCommunitiesViewMore;
    @Bind(R.id.tv_featured_community_join)
    TextView tvFeaturedCommunityJoin;
    @Bind(R.id.tv_featured_community_tag_lable)
    TextView tvFeaturedCommunityTagLable;
    BaseHolderInterface viewInterface;
    private CommunityFeedSolrObj CommunityFeedObj;
    private Context mContext;
    private Handler mHandler;

    public FeatureCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.CommunityFeedObj = (CommunityFeedSolrObj)item;
        this.mContext = context;
        CommunityFeedObj.setItemPosition(position);
        textViewOperation(context);
        populatePostText();
        if (!CommunityFeedObj.isTrending()) {
            liFeaturedCoverImage.removeAllViews();
            liFeaturedCoverImage.removeAllViewsInLayout();
            imageOperations(context);
        }

    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textViewOperation(Context context) {
        if (CommunityFeedObj.isClosedCommunity()) {
            tvFeaturedCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vector_lock, 0);
        } else {
            tvFeaturedCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (!CommunityFeedObj.isMember() && !CommunityFeedObj.isOwner() && !CommunityFeedObj.isRequestPending()) {
            tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
            tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
            tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
        } else if (CommunityFeedObj.isRequestPending()) {
            tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_REQUESTED));
            tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
            tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
        } else if (CommunityFeedObj.isOwner() || CommunityFeedObj.isMember()) {
            tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_JOINED));
            tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
            tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
        }
        //TODO:: change for UI
        if (StringUtil.isNotNullOrEmptyString(CommunityFeedObj.getNameOrTitle())) {
            tvFeaturedCommunityCardTitle.setText(CommunityFeedObj.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(CommunityFeedObj.getCommunityType())) {
            tvFeaturedCommunityTime.setText(CommunityFeedObj.getCommunityType());
        }
        if (StringUtil.isNotEmptyCollection(CommunityFeedObj.getTags()))
        {
            List<String> tags = CommunityFeedObj.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA + AppConstants.SPACE;
            }
            mergeTags = mergeTags.substring(0, mergeTags.length() - 2);
            String tagHeader = LEFT_HTML_TAG + context.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeaturedCommunityTagLable.setText(Html.fromHtml(tagHeader + AppConstants.SPACE + AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvFeaturedCommunityTagLable.setText(Html.fromHtml(tagHeader + AppConstants.SPACE + AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }

    }

    private void imageOperations(Context context) {
        String authorImageUrl = CommunityFeedObj.getThumbnailImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {

            ivFeaturedCommunityCircleIcon.setCircularImage(true);
            ivFeaturedCommunityCircleIcon.bindImage(authorImageUrl);
        }
        String imageUrl = CommunityFeedObj.getImageUrl();
        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View backgroundImage = layoutInflater.inflate(R.layout.communities_single_image, null);
            final ImageView ivFirstLandscape = backgroundImage.findViewById(R.id.iv_community_single_image);
            final TextView time = backgroundImage.findViewById(R.id.tv_community_time_label);
            time.setVisibility(View.INVISIBLE);
            final TextView tvTotalMember = backgroundImage.findViewById(R.id.tv_community_total_views);
            final RelativeLayout rlFeedArticleViews = backgroundImage.findViewById(R.id.rl_gradiant);
            tvTotalMember.setText(CommunityFeedObj.getNoOfMembers() + AppConstants.SPACE + context.getString(R.string.ID_MEMBERS));
            Glide.with(mContext)
                    .asBitmap()
                    .load(imageUrl)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                            ivFirstLandscape.setImageBitmap(profileImage);
                            rlFeedArticleViews.setVisibility(View.VISIBLE);
                        }
                    });
            liFeaturedCoverImage.addView(backgroundImage);
        } else {
            liFeaturedCoverImage.setBackgroundResource(R.drawable.vector_image_holder);
        }

    }
    private void populatePostText() {

        final String listDescription = CommunityFeedObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tvFeaturedDescriptionText.setMaxLines(Integer.MAX_VALUE);
                tvFeaturedDescriptionText.setText(listDescription);
                linkifyURLs(tvFeaturedDescriptionText);
                if (tvFeaturedDescriptionText.getLineCount() > 4) {
                    collapseFeedPostText();
                } else {
                    tvFeaturedDescriptionText.setVisibility(View.VISIBLE);
                    tvFeatureCommunitiesViewMore.setVisibility(View.GONE);
                }
            }
        });
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void collapseFeedPostText() {
        tvFeaturedDescriptionText.setMaxLines(4);
        tvFeaturedDescriptionText.setVisibility(View.VISIBLE);
        String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvFeatureCommunitiesViewMore.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
        } else {
            tvFeatureCommunitiesViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
        }
        tvFeatureCommunitiesViewMore.setVisibility(View.VISIBLE);
    }

    private void expandFeedPostText() {
        tvFeaturedDescriptionText.setMaxLines(Integer.MAX_VALUE);
        tvFeaturedDescriptionText.setVisibility(View.VISIBLE);
        tvFeatureCommunitiesViewMore.setText(mContext.getString(R.string.ID_LESS));
        tvFeatureCommunitiesViewMore.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.tv_featured_community_join)
    public void joinClick() {
        CommunityFeedObj.setTrending(true);
        if (tvFeaturedCommunityJoin.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_JOIN))) {
            viewInterface.handleOnClick(CommunityFeedObj, tvFeaturedCommunityJoin);
        }
    }

    @OnClick(R.id.li_featured_community_images)
    public void detailImageClick() {
        viewInterface.handleOnClick(CommunityFeedObj, liFeaturedCoverImage);
    }

    @OnClick(R.id.card_feature_communities)
    public void featureClick() {
        viewInterface.handleOnClick(CommunityFeedObj, liFeaturedCoverImage);
    }

    @Override
    public void viewRecycled() {

    }
    @OnClick(R.id.tv_feature_communities_view_more)
    public void onViewMoreClicked(){
        if (tvFeatureCommunitiesViewMore.getText().equals(mContext.getString(R.string.ID_LESS))) {
            collapseFeedPostText();
        } else {
            expandFeedPostText();
        }
    }

    @Override
    public void onClick(View view) {

    }

}
