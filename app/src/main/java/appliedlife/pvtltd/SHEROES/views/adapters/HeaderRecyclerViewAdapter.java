package appliedlife.pvtltd.SHEROES.views.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Geet on 14-10-2016.
 */

public abstract class HeaderRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String headers = null;
    public static final String header = "HEADER";
    private static final int TYPE_HEADER = -100;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return getHeaderViewHolder(parent);
        }
        return customOnCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                bindHeaderViewHolder((HeaderViewHolder) holder);
                break;
        }
        customOnBindViewHolder(holder, position - getHeaderSize());
    }

    @Override
    public int getItemViewType(int position) {
        if (getHeaderSize() != 0 && position == 0) {
            return TYPE_HEADER;
        } else {
            return customGetItemViewType(position - getHeaderSize());
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderSize() + customGetItemCount();
    }


    //region Header Code
    //add a header to the adapter
    public void addHeader(String header) {
        if (headers == null) {
            headers = header;
            notifyItemInserted(0);
        }
    }

    //remove a header from the adapter
    public void removeHeader(String header) {
        if (headers !=null && headers.equalsIgnoreCase(header)) {
            notifyItemRemoved(0);
            headers= null;
        }
    }

    public int getHeaderSize() {
        if (headers != null) {
            return 1;
        } else {
            return 0;
        }
    }
    //endregion

    //region custom NotifyItemChanged
    public void customNotifyItemChanged(int position) {
        notifyItemChanged(position + getHeaderSize());
    }
    public int getExactPosition(int position) {
        return (position - getHeaderSize());
    }
    //endregion

    //region abstract methods to be overriden
    public abstract RecyclerView.ViewHolder customOnCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void customOnBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract int customGetItemCount();

    public abstract int customGetItemViewType(int position);
    //endregion

    //region header view holder
    public abstract HeaderViewHolder getHeaderViewHolder(ViewGroup parent);

    public abstract class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View headerView) {
            super(headerView);
        }
    }

    public abstract void bindHeaderViewHolder(HeaderViewHolder holder);
    //endregion
}
