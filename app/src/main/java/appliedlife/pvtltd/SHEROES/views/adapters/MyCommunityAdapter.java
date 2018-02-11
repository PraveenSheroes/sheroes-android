package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedProgressBarHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunityHolder;

/**
 * Created by ravi on 31/01/18.
 */

public class MyCommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //region Private variables & Constants
    private static final int TYPE_COMMUNITY = 0;
    private static final int TYPE_SHOW_MORE = 1;
    private static final int TYPE_EMPTY = 0;
    private List<FeedDetail> mCommunities;
    private final Context mContext;
    private BaseHolderInterface baseHolderInterface;
    //endregion

    //region Constructor
    public MyCommunityAdapter(Context context, BaseHolderInterface baseHolderInterface) {
        mContext = context;
        this.baseHolderInterface = baseHolderInterface;
    }

    //endregion

    //region Adapter method
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_COMMUNITY) {
            View view = mInflater.inflate(R.layout.my_communities_item, parent, false);
            return new MyCommunityHolder(view, baseHolderInterface);
        } else {
            View view = mInflater.inflate(R.layout.horizontal_infinite_loading, parent, false);
            return new FeedProgressBarHolder(view, baseHolderInterface);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_COMMUNITY) {
            MyCommunityHolder commentListItemViewHolder = (MyCommunityHolder) holder;
            FeedDetail communityFeedSolrObj = mCommunities.get(position);
            commentListItemViewHolder.bindData(communityFeedSolrObj, mContext, position);

        } else {
            FeedProgressBarHolder loaderViewHolder = ((FeedProgressBarHolder) holder);
            loaderViewHolder.bindData(mCommunities.get(position), mContext, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mCommunities.get(position).getSubType() != null && mCommunities.get(position).getSubType().equals(AppConstants.FEED_PROGRESS_BAR) ? TYPE_SHOW_MORE : TYPE_COMMUNITY;
    }

    @Override
    public int getItemCount() {
        return CommonUtil.isEmpty(mCommunities) ? TYPE_EMPTY : mCommunities.size();
    }
    //endregion

    //region Public method
    public void setData(List<FeedDetail> communities) {
        this.mCommunities = communities;
        notifyDataSetChanged();
    }
    //endregion
}
