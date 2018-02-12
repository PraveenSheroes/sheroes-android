package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Ujjwal on 05-02-2018.
 */

public class CommunityFlatViewHolder extends BaseViewHolder<FeedDetail> {

    //region private variable and constant
    private final String TAG = LogUtils.makeLogTag(CommunityFlatViewHolder.class);
    private BaseHolderInterface viewInterface;
    private CommunityFeedSolrObj mCommunityFeedObj;
    //endregion

    //region bind variables
    @Bind(R.id.feature_image)
    ImageView mFeatureImage;

    @Bind(R.id.community_icon)
    ImageView mCommunityIcon;

    @Bind(R.id.community_name)
    TextView mCommunityName;

    @Bind(R.id.community_member_count)
    TextView mCommunityMemberCount;

    @Bind(R.id.community_join)
    TextView mCommunityJoin;

    @BindDimen(R.dimen.dp_size_40)
    int mCommunityIconSize;
    //endregion

    //region constructor
    public CommunityFlatViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        Handler mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
    }
    //endregion

    //region adapter method
    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.mCommunityFeedObj = (CommunityFeedSolrObj) item;
        mCommunityFeedObj.setItemPosition(position);
        Context mContext = context;

        if (!mCommunityFeedObj.isMember() && !mCommunityFeedObj.isOwner() && !mCommunityFeedObj.isRequestPending()) {
            mCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            mCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
            mCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        } else if (mCommunityFeedObj.isOwner() || mCommunityFeedObj.isMember()) {
            mCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            mCommunityJoin.setText(mContext.getString(R.string.ID_JOINED));
            mCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        }


        int imageHeight = (int) (((float) 1 / (float) 2) * CommonUtil.getWindowWidth(mContext));
        mFeatureImage.getLayoutParams().height = imageHeight;
        if (CommonUtil.isNotEmpty(mCommunityFeedObj.getImageUrl())) {
            String finalImageUri = CommonUtil.getImgKitUri(mCommunityFeedObj.getImageUrl(), CommonUtil.getWindowWidth(mContext), imageHeight);
            Glide.with(context)
                    .asBitmap()
                    .load(finalImageUri)
                    .into(mFeatureImage);
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedObj.getThumbnailImageUrl())) {
            String thumbImageUrl = CommonUtil.getImgKitUri(mCommunityFeedObj.getThumbnailImageUrl(), mCommunityIconSize, mCommunityIconSize);
            Glide.with(context)
                    .asBitmap()
                    .load(thumbImageUrl)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)))
                    .into(mCommunityIcon);
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedObj.getNameOrTitle())) {
            mCommunityName.setText(mCommunityFeedObj.getNameOrTitle());
        }

        String pluralMember = mContext.getResources().getQuantityString(R.plurals.numberOfMembers, mCommunityFeedObj.getNoOfMembers());
        mCommunityMemberCount.setText(String.valueOf(numericToThousand(mCommunityFeedObj.getNoOfMembers()) + AppConstants.SPACE + pluralMember));
    }

    @Override
    public void viewRecycled() {

    }
//endregion

    //region onclick method
    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.community_join)
    public void onCommunityJoinUnjoinedClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onCommunityJoinOrLeave(mCommunityFeedObj);
        }
    }

    @OnClick({R.id.community_card_view})
    public void onCardClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onCommunityClicked(mCommunityFeedObj);
        }
    }
    //endregion
}
