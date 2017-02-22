package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 15-02-2017.
 */

public class BlankHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(BlankHolder.class);
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;

    public BlankHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {


    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

    }


}