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

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by Praveen_Singh on 30-01-2017.
 */

public class MyCommunitiesCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(MyCommunitiesCardHolder.class);
    private static final String LEFT_HTML_TAG = "<font color='#000000'>";
    private static final String RIGHT_HTML_TAG = "</font>";
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
    @Bind(R.id.tv_my_communities_view_more)
    TextView tvMyCommunitiesViewMore;
    @Bind(R.id.tv_community_tag_lable)
    TextView tvCommunityTag;
    @Bind(R.id.tv_community_detail_invite)
    TextView tvCommunityInvite;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private Context mContext;
    private Handler mHandler;

    public MyCommunitiesCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        dataItem.setItemPosition(position);
        mContext = context;
        liCoverImage.removeAllViews();
        liCoverImage.removeAllViewsInLayout();
        imageOperations(context);
        textViewOperation(context);
        populatePostText();
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textViewOperation(Context context) {
        // TODO: ujjwal
     /*   if (dataItem.isClosedCommunity()) {
            tvCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        } else {
            tvCommunityTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }*/

        if (StringUtil.isNotNullOrEmptyString(dataItem.getScreenName()) && dataItem.getScreenName().equalsIgnoreCase(AppConstants.FEATURE_FRAGMENT)) {
            tvCommunityInvite.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvCommunityInvite.setText(mContext.getString(R.string.ID_JOINED));
            tvCommunityInvite.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
            tvCommunityInvite.setVisibility(View.VISIBLE);
            dataItem.setCallFromName(AppConstants.FEATURE_FRAGMENT);
        } else {
            // TODO: ujjwal
        /*    if (dataItem.isMember() && !dataItem.isOwner()) {
                tvCommunityInvite.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvCommunityInvite.setText(mContext.getString(R.string.ID_VIEW));
                tvCommunityInvite.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
            } else {
                tvCommunityInvite.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvCommunityInvite.setText(mContext.getString(R.string.ID_INVITE));
                tvCommunityInvite.setBackgroundResource(R.drawable.rectangle_community_invite);
            }*/
        }
        //TODO:: change for UI
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvCommunityCardTitle.setText(dataItem.getNameOrTitle());
        }
        // TODO: ujjwal
      /*  if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityType())) {
            tvCommunityTime.setText(dataItem.getCommunityType());
        }

        if (StringUtil.isNotEmptyCollection(dataItem.getTags())) {
            List<String> tags = dataItem.getTags();
            String mergeTags = AppConstants.EMPTY_STRING;
            for (String tag : tags) {
                mergeTags += tag + AppConstants.COMMA+AppConstants.SPACE;
            }
            mergeTags = mergeTags.substring(0, mergeTags.length() - 2);
            String tagHeader = LEFT_HTML_TAG + context.getString(R.string.ID_TAGS) + RIGHT_HTML_TAG;
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvCommunityTag.setText(Html.fromHtml(tagHeader +AppConstants.SPACE+ AppConstants.COLON + AppConstants.SPACE + mergeTags, 0)); // for 24 api and more
            } else {
                tvCommunityTag.setText(Html.fromHtml(tagHeader+AppConstants.SPACE + AppConstants.COLON + AppConstants.SPACE + mergeTags));// or for older api
            }
        }*/
    }
    private void populatePostText() {

        final String listDescription = dataItem.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tvDescriptionText.setMaxLines(Integer.MAX_VALUE);
                tvDescriptionText.setText(listDescription);
                linkifyURLs(tvDescriptionText);
                if (tvDescriptionText.getLineCount() > 4) {
                    collapseFeedPostText();
                } else {
                    tvDescriptionText.setVisibility(View.VISIBLE);
                    tvMyCommunitiesViewMore.setVisibility(View.GONE);
                }
            }
        });
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void collapseFeedPostText() {
        tvDescriptionText.setMaxLines(4);
        tvDescriptionText.setVisibility(View.VISIBLE);
        String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvMyCommunitiesViewMore.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
        } else {
            tvMyCommunitiesViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
        }
        tvMyCommunitiesViewMore.setVisibility(View.VISIBLE);
    }

    private void expandFeedPostText() {
        tvDescriptionText.setMaxLines(Integer.MAX_VALUE);
        tvDescriptionText.setVisibility(View.VISIBLE);
        tvMyCommunitiesViewMore.setText(mContext.getString(R.string.ID_LESS));
        tvMyCommunitiesViewMore.setVisibility(View.VISIBLE);
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
            View backgroundImage = layoutInflater.inflate(R.layout.communities_single_image, null);
            final ImageView ivFirstLandscape = (ImageView) backgroundImage.findViewById(R.id.iv_community_single_image);
            final TextView tvTotalMember = (TextView) backgroundImage.findViewById(R.id.tv_community_total_views);
            final TextView time = (TextView) backgroundImage.findViewById(R.id.tv_community_time_label);
            time.setVisibility(View.INVISIBLE);
            final RelativeLayout rlFeedArticleViews = (RelativeLayout) backgroundImage.findViewById(R.id.rl_gradiant);
            // TODO: ujjwal
            //tvTotalMember.setText(dataItem.getNoOfMembers() + AppConstants.SPACE + context.getString(R.string.ID_MEMBERS));
            Glide.with(mContext)
                    .load(imageUrl).asBitmap()
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

    @OnClick(R.id.tv_community_detail_invite)
    public void joinClick() {
        if (tvCommunityInvite.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_VIEW))) {
            viewInterface.handleOnClick(dataItem, liCoverImage);
        } else if (tvCommunityInvite.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_INVITE))) {
            viewInterface.handleOnClick(dataItem, tvCommunityInvite);
        }
    }
    @OnClick(R.id.tv_my_communities_view_more)
    public void onViewMoreClicked(){
        if (tvMyCommunitiesViewMore.getText().equals(mContext.getString(R.string.ID_LESS))) {
            collapseFeedPostText();
        } else {
            expandFeedPostText();
        }
    }
    @Override
    public void onClick(View view) {

    }

}
