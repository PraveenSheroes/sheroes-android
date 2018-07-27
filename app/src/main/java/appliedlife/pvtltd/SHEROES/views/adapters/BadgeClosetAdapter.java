package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.views.viewholders.BadgeClosetViewHolder;

public class BadgeClosetAdapter extends RecyclerView.Adapter<BadgeClosetViewHolder> {

    private final Context mContext;
    private List<BadgeDetails> mBadgeDetails;
    private OnItemClickListener mOnItemClickListener;

    //region Constructor
    public BadgeClosetAdapter(Context context, List<BadgeDetails> badgeDetails, OnItemClickListener itemClickListener) {
        this.mContext = context;
        this.mBadgeDetails = badgeDetails;
        this.mOnItemClickListener = itemClickListener;
    }
    //endregion

    //region BadgeCloset methods
    @Override
    public BadgeClosetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_list_item, parent, false);
        return new BadgeClosetViewHolder(view, mContext, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BadgeClosetViewHolder holder, int position) {
        BadgeDetails badgeDetails = mBadgeDetails.get(position);
        if (badgeDetails != null) {
            holder.bindData(badgeDetails);
        }
    }

    @Override
    public int getItemCount() {
        return mBadgeDetails == null ? 0 : mBadgeDetails.size();
    }
    // endregion

    public interface OnItemClickListener {
        void onItemClick(BadgeDetails badgeDetails);
    }


}
