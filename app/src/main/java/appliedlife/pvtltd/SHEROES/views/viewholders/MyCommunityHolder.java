package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 31/01/18.
 */

public class MyCommunityHolder extends BaseViewHolder<FeedDetail> {

    @Bind(R.id.community_item)
    LinearLayout communityItemContainer;

    @Bind(R.id.mutual_community_icon)
    CircleImageView mCommunityIcon;

    @Bind(R.id.community_name)
    TextView mCommunityName;

    BaseHolderInterface viewInterface;
    private FeedDetail mFeedDetail;
    private Context mContext;

    public MyCommunityHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail feedDetail, Context context, int position) {
        mContext = context;
        mFeedDetail = feedDetail;

        if (CommonUtil.isNotEmpty(mFeedDetail.getImageUrl())) {
           String imageKitUrl = mFeedDetail.getImageUrl();
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
        communityItemContainer.setOnClickListener(this);
    }

    @Override
    public void viewRecycled() {
    }

    @Override
    public void onClick(View view) {
        if(viewInterface instanceof AllCommunityItemCallback){
            ((AllCommunityItemCallback)viewInterface).onMyCommunityClicked((CommunityFeedSolrObj) mFeedDetail);
        }
    }
}
