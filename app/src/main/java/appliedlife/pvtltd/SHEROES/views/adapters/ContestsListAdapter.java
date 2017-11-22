package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.viewholder.ContestFlatViewHolder;

/**
 * Created by ujjwal on 28/04/17.
 */

public class ContestsListAdapter extends RecyclerView.Adapter<ContestFlatViewHolder> {
    private final Context mContext;
    private List<Contest> mContestList;
    private final View.OnClickListener mOnClickListener;

    //region Constructor
    public ContestsListAdapter(Context context, View.OnClickListener onClickListener) {
        this.mContext = context;
        mContestList = new ArrayList<>();
        this.mOnClickListener = onClickListener;
    }
    //endregion

    //region ContestListAdapter methods
    @Override
    public ContestFlatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contest_compact_item, parent, false);
        return new ContestFlatViewHolder(view, mContext, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(ContestFlatViewHolder holder, int position) {
        Contest contest = mContestList.get(position);

        if (contest != null) {
            holder.bindData(contest);
        }
    }

    @Override
    public int getItemCount() {
        return mContestList == null ? 0 : mContestList.size();
    }

    public void setData(final List<Contest> contests) {
        mContestList = contests;
        notifyDataSetChanged();
    }

    public void setItem(Contest contest, int position) {
        mContestList.set(position, contest);
        notifyItemChanged(position);
    }
    //endregion
}
