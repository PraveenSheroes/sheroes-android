package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 08/03/17.
 */

public class SeeMoreCompactViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private final BaseHolderInterface viewInterface;

    @Bind(R.id.more_button)
    FloatingActionButton mMoreButton;

    @Bind(R.id.more_text)
    TextView mMoreText;

    public SeeMoreCompactViewHolder(View seeMoreView, BaseHolderInterface baseHolderInterface) {
        super(seeMoreView);
        ButterKnife.bind(this, seeMoreView);
        viewInterface = baseHolderInterface;
    }

    public void bindData() {

    }

}
