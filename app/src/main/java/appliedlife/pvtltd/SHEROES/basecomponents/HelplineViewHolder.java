package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.view.View;

/**
 * @author purnima
 * @param <T>
 */
public abstract class HelplineViewHolder<T extends Object> extends BaseViewHolder {
    public HelplineViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindData(T obj, Context context, int position, T prevObj);
}
