package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.IHashTagCallBack;
import appliedlife.pvtltd.SHEROES.views.viewholders.HashTagsHeaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HashTagsViewHolder;

public class HashTagsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //region static variables
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    //endregion static variables

    //region member variables
    private Context mContext;
    private List<String> mHashTagsList;
    private IHashTagCallBack mIHashTagCallBack;
    //endregion member variables

    //region constructor
    public HashTagsAdapter(Context context, IHashTagCallBack hashTagCallBack, List<String> hashTagsList) {
        this.mContext = context;
        this.mIHashTagCallBack = hashTagCallBack;
        this.mHashTagsList = hashTagsList;
    }
    //endregion constructor

    //region lifecycle methods
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_HEADER:
                View headerView = inflater.inflate(R.layout.hashtag_header_layout, parent, false);
                viewHolder = new HashTagsHeaderViewHolder(headerView);
            break;

            case TYPE_ITEM:
                View itemView = inflater.inflate(R.layout.hashtag_row_element_layout, parent, false);
                viewHolder = new HashTagsViewHolder(itemView, mIHashTagCallBack, mHashTagsList);
            break;

            default:
                View view = inflater.inflate(R.layout.hashtag_row_element_layout, parent, false);
                viewHolder = new HashTagsViewHolder(view, mIHashTagCallBack, mHashTagsList);
            break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int pos = position - 1;

        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                HashTagsHeaderViewHolder hashTagsHeaderViewHolder = (HashTagsHeaderViewHolder) holder;

                hashTagsHeaderViewHolder.getHashTagHeaderTxt().setText(mContext.getString(R.string.ID_TRENDING));
                break;

            case TYPE_ITEM:
                HashTagsViewHolder hashTagsViewHolder = (HashTagsViewHolder) holder;

                hashTagsViewHolder.getHashTagTxt().setText(mHashTagsList.get(pos));
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mHashTagsList.size() > 0) {
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_ITEM;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mHashTagsList.size() + 1;
    }
    //endregion lifecycle methods

    //region public methods
    public void refreshList(List<String> hashTagsList){
        this.mHashTagsList = hashTagsList;
        notifyDataSetChanged();
    }
    //endregion public methods
}
