package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedProgressBarHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunityHolder;

/**
 * Created by ravi on 31/01/18.
 */

public class MyCommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedDetail> communities;
    private final Context mContext;
    private BaseHolderInterface baseHolderInterface;

    //region Constructor
    public MyCommunityAdapter(Context context, BaseHolderInterface baseHolderInterface) {
        mContext = context;
        this.baseHolderInterface = baseHolderInterface;
    }

    //endregion
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        if(viewType == 0) {
            View view = mInflater.inflate(R.layout.my_communities_item, parent, false);
            return new MyCommunityHolder(view, baseHolderInterface);
        } else {
            View view = mInflater.inflate(R.layout.horizontal_infinite_loading, parent, false);
            return new FeedProgressBarHolder(view, baseHolderInterface);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == 0) {
            MyCommunityHolder commentListItemViewHolder = (MyCommunityHolder) holder;
            FeedDetail communityFeedSolrObj = communities.get(position);
            commentListItemViewHolder.bindData(communityFeedSolrObj, mContext, position);

        } else {
            FeedProgressBarHolder loaderViewHolder = ((FeedProgressBarHolder) holder);
            loaderViewHolder.bindData(communities.get(position), mContext,  position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return communities.get(position).getSubType()!= null && communities.get(position).getSubType().equals(AppConstants.FEED_PROGRESS_BAR) ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(communities)) {
            return 0;
        }
        return communities.size();
    }


    public void setData(List<FeedDetail> communities) {
        this.communities = communities;
        notifyDataSetChanged();
    }
}
