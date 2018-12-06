package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.IHashTagCallBack;
import appliedlife.pvtltd.SHEROES.views.viewholders.HashTagsHeaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HashTagsViewHolder;

public class HashTagsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private Context context;
    private List<String> hashTagsList;
    private IHashTagCallBack iHashTagCallBack;

    public HashTagsAdapter(Context context, IHashTagCallBack hashTagCallBack, List<String> hashTagsList) {
        this.context = context;
        this.iHashTagCallBack = hashTagCallBack;
        this.hashTagsList = hashTagsList;
    }

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
                viewHolder = new HashTagsViewHolder(itemView, iHashTagCallBack, hashTagsList);
            break;

            default:
                View view = inflater.inflate(R.layout.hashtag_row_element_layout, parent, false);
                viewHolder = new HashTagsViewHolder(view, iHashTagCallBack, hashTagsList);
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

                hashTagsHeaderViewHolder.getHashTagHeaderTxt().setText("Trending");
                break;

            case TYPE_ITEM:
                HashTagsViewHolder hashTagsViewHolder = (HashTagsViewHolder) holder;

                hashTagsViewHolder.getHashTagTxt().setText(hashTagsList.get(pos));
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }

    }

    @Override
    public int getItemCount() {
        return hashTagsList.size() + 1;
    }

    public void refreshList(List<String> hashTagsList){
        this.hashTagsList.addAll(hashTagsList);
        notifyDataSetChanged();
    }
}
