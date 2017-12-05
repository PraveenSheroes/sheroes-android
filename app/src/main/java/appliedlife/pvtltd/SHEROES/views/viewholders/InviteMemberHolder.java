package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 07-03-2017.
 */

public class InviteMemberHolder extends BaseViewHolder<FeedDetail> {
    @Bind(R.id.rl_invite_member_item)
    RelativeLayout rlInviteMemberItem;
    @Bind(R.id.tv_member_city)
    TextView tvCity;
    @Bind(R.id.tv_member_name)
    TextView tvMemberName;
    @Bind(R.id.iv_circle_profile_pic)
    CircleImageView ivCircleProfilePic;
    @Bind(R.id.tv_add_invite)
    TextView tvAddInvite;
    BaseHolderInterface viewInterface;
    private CommunityFeedSolrObj mCommunityFeedObj;
    private Context mContext;
    public InviteMemberHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail feedDetail, Context context, int position) {
        this.mCommunityFeedObj = (CommunityFeedSolrObj) feedDetail;
        mContext = context;
        if (StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getNameOrTitle())) ;
        {
            tvMemberName.setText(mCommunityFeedObj.getNameOrTitle());
        }
        // TODO: ujjwal
     /*   if (StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getCityName())) ;
        {
            tvCity.setText(mCommunityFeedObj.getCityName());
        }*/
        String images = mCommunityFeedObj.getImageUrl();

        if(mCommunityFeedObj.isOwner() || mCommunityFeedObj.isMember())
        {
            rlInviteMemberItem.setVisibility(View.GONE);
            tvAddInvite.setText(mContext.getString(R.string.ID_ADDED));
            tvAddInvite.setBackgroundResource(R.drawable.select_inivite_button_added_color);
            tvAddInvite.setTextColor(ContextCompat.getColor(mContext, R.color.white));

        }else {
            rlInviteMemberItem.setVisibility(View.VISIBLE);
            tvAddInvite.setText(mContext.getString(R.string.ID_ADD_HERE));
            tvAddInvite.setBackgroundResource(R.drawable.select_purpose_btn_shap);
            tvAddInvite.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        }
        ivCircleProfilePic.setCircularImage(true);
        ivCircleProfilePic.bindImage(images);
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_add_invite)
    public void inviteOnclick() {
        if(!mCommunityFeedObj.isLongPress()) {
            mCommunityFeedObj.setLongPress(true);
            tvAddInvite.setText(mContext.getString(R.string.ID_ADDED));
            tvAddInvite.setBackgroundResource(R.drawable.select_inivite_button_added_color);
            tvAddInvite.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }else
        {
            mCommunityFeedObj.setLongPress(false);
            tvAddInvite.setText(mContext.getString(R.string.ID_ADD_HERE));
            tvAddInvite.setBackgroundResource(R.drawable.select_purpose_btn_shap);
            tvAddInvite.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        }
        viewInterface.handleOnClick(mCommunityFeedObj, tvAddInvite);
    }

    @Override
    public void onClick(View view) {
    }
}
