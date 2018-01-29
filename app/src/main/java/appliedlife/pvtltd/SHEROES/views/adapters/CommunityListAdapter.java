package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 04/02/16.
 */
public class CommunityListAdapter extends RecyclerView.Adapter<CommunityListAdapter.CommunityListItemViewHolder> {
    private final Context mContext;
    private final List<Community> mCommunityList;
    private final View.OnClickListener mOnClickListener;

    //region Constructor
    public CommunityListAdapter(Context context, List<Community> communityList, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.mCommunityList = communityList;
        this.mOnClickListener = onClickListener;
    }
    //endregion

    //region FollowListAdapter methods
    @Override
    public CommunityListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_community_item, parent, false);

        if (mOnClickListener != null) {
            view.setOnClickListener(mOnClickListener);
        }

        return new CommunityListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommunityListItemViewHolder holder, int position) {
        holder.mCommunityContainer.setOnClickListener(mOnClickListener);
        Community community = mCommunityList.get(position);
        if (community != null) {
            if (community.thumbImageUrl != null && CommonUtil.isNotEmpty(community.thumbImageUrl)) {
                String userImage = CommonUtil.getImgKitUri(community.thumbImageUrl, holder.authorPicSize, holder.authorPicSize);
                Glide.with(holder.communityPic.getContext())
                        .load(userImage)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)))
                        .into(holder.communityPic);
            }
            holder.communityName.setText(community.name);
            if(community.isChecked){
                holder.mCheck.setVisibility(View.VISIBLE);
            }else {
                holder.mCheck.setVisibility(View.GONE);
            }
            if (community.isFirstOther) {
                holder.otherCommunity.setVisibility(View.VISIBLE);
            } else {
                holder.otherCommunity.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCommunityList == null ? 0 : mCommunityList.size();
    }
    //endregion

    // region Follow List Item ViewHolder
    static class CommunityListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings

        @Bind(R.id.other_community)
        TextView otherCommunity;

        @BindDimen(R.dimen.dp_size_36)
        int authorPicSize;

        @Bind(R.id.community_container)
        RelativeLayout mCommunityContainer;

        @Bind(R.id.community_pic)
        public ImageView communityPic;

        @Bind(R.id.community_name)
        public TextView communityName;

        @Bind(R.id.check)
        public ImageView mCheck;

        // endregion

        public CommunityListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    // endregion

    public void setItem(int position, Community community) {
        mCommunityList.set(position, community);
        notifyItemChanged(position);
    }
}
