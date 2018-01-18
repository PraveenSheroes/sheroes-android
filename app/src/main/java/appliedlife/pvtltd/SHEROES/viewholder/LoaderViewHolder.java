package appliedlife.pvtltd.SHEROES.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by ujjwal on 28/12/17.
 */

public class LoaderViewHolder extends RecyclerView.ViewHolder {

    final ProgressBar progress;

    public LoaderViewHolder(View itemView) {
        super(itemView);
        progress = (ProgressBar) itemView;
    }
    public void bindData(int adapterPosition, boolean isFeedLoading) {
        progress.setVisibility((adapterPosition > 0 && isFeedLoading) ? View.VISIBLE : View.GONE);
    }
}