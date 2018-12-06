package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedProgressBarHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunitiesDrawerViewHolder;

/**
 * Created by Praveen on 28/05/18.
 */

public class MyCommunitiesDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //region Private variables & Constants
    private static final int TYPE_COMMUNITY = 0;
    private static final int TYPE_SHOW_MORE = 1;
    private List<FeedDetail> mCommunities = null;
    private final Context mContext;
    private BaseHolderInterface baseHolderInterface;
    //endregion

    //region Constructor
    public MyCommunitiesDrawerAdapter(Context context, BaseHolderInterface baseHolderInterface) {
        mContext = context;
        this.baseHolderInterface = baseHolderInterface;
    }
    //endregion

    //region Adapter method
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_COMMUNITY) {
            View view = mInflater.inflate(R.layout.my_communities_drawer_item, parent, false);
            return new MyCommunitiesDrawerViewHolder(view, baseHolderInterface);
        } else {
            View view = mInflater.inflate(R.layout.horizontal_infinite_loading, parent, false);
            return new FeedProgressBarHolder(view, baseHolderInterface);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (CommonUtil.isEmpty(mCommunities)) return;
        if (holder.getItemViewType() == TYPE_COMMUNITY) {
            MyCommunitiesDrawerViewHolder myCommunitiesDrawerViewHolder = (MyCommunitiesDrawerViewHolder) holder;
            FeedDetail communityFeedSolrObj = mCommunities.get(position);
            myCommunitiesDrawerViewHolder.bindData(communityFeedSolrObj, mContext, position);
        } else {
            FeedProgressBarHolder loaderViewHolder = ((FeedProgressBarHolder) holder);
            loaderViewHolder.bindData(mCommunities.get(position), mContext, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return !CommonUtil.isEmpty(mCommunities) && mCommunities.get(position).getSubType() != null && mCommunities.get(position).getSubType().equals(AppConstants.FEED_PROGRESS_BAR) ? TYPE_SHOW_MORE : TYPE_COMMUNITY;
    }

    @Override
    public int getItemCount() {
        return CommonUtil.isEmpty(mCommunities) ? 0 : mCommunities.size();
    }
    //endregion

    //region Public method
    public void setData(List<FeedDetail> communities) {
        if (!StringUtil.isNotEmptyCollection(communities)) {
            this.mCommunities = communities;
        }
    }
    //endregion
}