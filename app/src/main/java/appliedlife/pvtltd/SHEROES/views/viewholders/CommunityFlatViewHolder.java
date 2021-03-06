package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.core.content.ContextCompat;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;

/**
 * Created by Ujjwal on 05-02-2018.
 */

public class CommunityFlatViewHolder extends BaseViewHolder<FeedDetail> {

    //region private variable and constant
    private final String TAG = LogUtils.makeLogTag(CommunityFlatViewHolder.class);
    private final long COMMUNITY_ID = 273;
    private BaseHolderInterface viewInterface;
    private CommunityFeedSolrObj mCommunityFeedObj;
    //endregion

    //region bind variables
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

        if (!mCommunityFeedObj.isMember() && !mCommunityFeedObj.isOwner()) {
            mCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            mCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
            mCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        } else if (mCommunityFeedObj.isOwner() || mCommunityFeedObj.isMember()) {
            mCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            mCommunityJoin.setText(mContext.getString(R.string.ID_JOINED));
            mCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        }

        if (mCommunityFeedObj.getIdOfEntityOrParticipant() == COMMUNITY_ID) {
            mCommunityJoin.setVisibility(View.GONE);
        } else {
            mCommunityJoin.setVisibility(View.VISIBLE);
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedObj.getThumbnailImageUrl())) {
            String thumbImageUrl = CommonUtil.getThumborUri(mCommunityFeedObj.getThumbnailImageUrl(), mCommunityIconSize, mCommunityIconSize);
            Glide.with(context)
                    .asBitmap()
                    .load(thumbImageUrl)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)))
                    .into(mCommunityIcon);
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedObj.getNameOrTitle())) {
            mCommunityName.setText(Html.fromHtml(mCommunityFeedObj.getNameOrTitle()).toString());
        }

        String pluralMember = mContext.getResources().getQuantityString(R.plurals.numberOfMembers, mCommunityFeedObj.getNoOfMembers());
        mCommunityMemberCount.setText(String.valueOf(changeNumberToNumericSuffix(mCommunityFeedObj.getNoOfMembers()) + AppConstants.SPACE + pluralMember));
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
        } else if (viewInterface instanceof AllCommunityItemCallback) {
            ((AllCommunityItemCallback) viewInterface).onCommunityJoinOrUnjoin(mCommunityFeedObj);
        }
    }

    @OnClick({R.id.community_card_view})
    public void onCardClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onCommunityClicked(mCommunityFeedObj);
        } else if (viewInterface instanceof AllCommunityItemCallback) {
            ((AllCommunityItemCallback) viewInterface).onCommunityClicked(mCommunityFeedObj);
        }
    }
    //endregion
}
