package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T extends Object> extends RecyclerView.ViewHolder implements View.OnClickListener{

  public BaseViewHolder(View itemView) {
    super(itemView);
  }
  public abstract void bindData(T obj, Context context, int position);
  public abstract void viewRecycled();
}
