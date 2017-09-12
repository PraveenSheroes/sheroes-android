package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 12/09/17.
 */

public class FeedProgressBarHolder extends BaseViewHolder<BaseResponse> {
    private final String TAG = LogUtils.makeLogTag(BlankHolder.class);
    BaseHolderInterface viewInterface;
    private BaseResponse dataItem;

    public FeedProgressBarHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(BaseResponse item, final Context context, int position) {
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

    }


}
