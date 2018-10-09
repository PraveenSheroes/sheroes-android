package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleViewLinear;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 30/05/18.
 */

public class MyCommunitiesDrawerViewHolder extends BaseViewHolder<FeedDetail> {

    //region private variables
    private FeedDetail mFeedDetail;
    private Context mContext;
    //endregion

    //region bind variables
    @Bind(R.id.community_item)
    RippleViewLinear communityItemContainer;

    @Bind(R.id.mutual_community_icon)
    CircleImageView mCommunityIcon;

    @Bind(R.id.community_name)
    TextView mCommunityName;

    @BindDimen(R.dimen.dp_size_50)
    int iconSize;
    //endregion

    //region constructor
    public MyCommunitiesDrawerViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    //endregion

    //region adapter method
    @Override
    public void bindData(FeedDetail feedDetail, Context context, int position) {
        mContext = context;
        mFeedDetail = feedDetail;

        if (CommonUtil.isNotEmpty(mFeedDetail.getThumbnailImageUrl())) {
            String imageKitUrl = CommonUtil.getThumborUri(mFeedDetail.getThumbnailImageUrl(), iconSize, iconSize);
            if (CommonUtil.isNotEmpty(imageKitUrl)) {
                Glide.with(mContext)
                        .load(imageKitUrl)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)))
                        .into(mCommunityIcon);
            }
        } else {
            mCommunityIcon.setImageResource(R.drawable.challenge_placeholder);
        }

        mCommunityName.setText(mFeedDetail.getNameOrTitle());
    }
    //endregion

    //region public method
    @Override
    public void viewRecycled() {
    }
    //endregion

    //region onclick method
    @Override
    public void onClick(View view) {
    }

    @OnClick(R.id.community_item)
    public void communityClick() {
        communityItemContainer.setOnRippleCompleteListener(new RippleViewLinear.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleViewLinear rippleView) {
                if (mContext instanceof HomeActivity) {
                    CommunityDetailActivity.navigateTo(((HomeActivity) mContext), (CommunityFeedSolrObj) mFeedDetail, mContext.getString(R.string.RightNavigationDrawer), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                } else if (mContext instanceof CommunityDetailActivity) {
                    CommunityDetailActivity.navigateTo(((CommunityDetailActivity) mContext), (CommunityFeedSolrObj) mFeedDetail, mContext.getString(R.string.RightNavigationDrawer), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                }
            }
        });

    }
    //endregion
}
