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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.w3c.dom.Text;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Ujjwal on 05-02-2018.
 */

public class CommunityCompactViewHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(CommunityCompactViewHolder.class);
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
    BaseHolderInterface viewInterface;
    private Context mContext;
    private Handler mHandler;
    private CommunityFeedSolrObj mCommunityFeedObj;
    private CarouselViewHolder mCarouselViewHolder;

    public CommunityCompactViewHolder(View itemView, BaseHolderInterface baseHolderInterface, CarouselViewHolder carouselViewHolder) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        this.mCarouselViewHolder = carouselViewHolder;
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.mCommunityFeedObj = (CommunityFeedSolrObj) item;
        mCommunityFeedObj.setItemPosition(position);
        mContext = context;

        if (mCommunityFeedObj.isOwner() || mCommunityFeedObj.isMember()) {
            mCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            mCommunityJoin.setText(mContext.getString(R.string.ID_JOINED));
            mCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        }
        else if (!mCommunityFeedObj.isMember() && !mCommunityFeedObj.isOwner() && !mCommunityFeedObj.isRequestPending()) {
            mCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            mCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
            mCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }

        if (CommonUtil.isNotEmpty(mCommunityFeedObj.getImageUrl())) {
            Glide.with(context)
                    .asBitmap()
                    .load(mCommunityFeedObj.getImageUrl())
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

        if(CommonUtil.isNotEmpty(mCommunityFeedObj.getNameOrTitle())) {
            mCommunityName.setText(mCommunityFeedObj.getNameOrTitle());
        }

        String pluralMember = mContext.getResources().getQuantityString(R.plurals.numberOfMembers, mCommunityFeedObj.getNoOfMembers());
        mCommunityMemberCount.setText(String.valueOf(numericToThousand(mCommunityFeedObj.getNoOfMembers()) + AppConstants.SPACE + pluralMember));
    }

    @Override
    public void viewRecycled() {

    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void textViewOperation(Context context) {
        //TODO:: change for UI
    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.community_join)
    public void onCommunityJoinUnjoinedClicked() {
        if(viewInterface instanceof AllCommunityItemCallback) {
            if(mCommunityFeedObj.isMember()){
                mCommunityFeedObj.setMember(false);
                mCommunityFeedObj.setNoOfMembers(mCommunityFeedObj.getNoOfMembers() - 1);
                ((AllCommunityItemCallback) viewInterface).unJoinCommunity(mCommunityFeedObj, mCarouselViewHolder);
            }else {
                mCommunityFeedObj.setMember(true);
                mCommunityFeedObj.setNoOfMembers(mCommunityFeedObj.getNoOfMembers() + 1);
                ((AllCommunityItemCallback) viewInterface).joinRequestForOpenCommunity(mCommunityFeedObj, mCarouselViewHolder);
            }
        }
    }

    @OnClick({R.id.community_card_view})
    public void onCardClicked(){
        if(viewInterface instanceof AllCommunityItemCallback){
            ((AllCommunityItemCallback)viewInterface).onCommunityClicked(mCommunityFeedObj);
        }
    }
}
