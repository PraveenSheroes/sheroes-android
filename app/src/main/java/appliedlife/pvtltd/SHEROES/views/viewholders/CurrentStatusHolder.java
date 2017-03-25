package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 24-03-2017.
 */

public class CurrentStatusHolder extends BaseViewHolder<LabelValue> {
    private final String TAG = LogUtils.makeLogTag(CurrentStatusHolder.class);
    BaseHolderInterface viewInterface;
    @Bind(R.id.tv_current_status_item)
    TextView tvCurrentItem;
    @Bind(R.id.li_current_status)
    LinearLayout liCurrentStatus;

    private LabelValue dataItem;
    private Context mContext;
    public CurrentStatusHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(LabelValue labelValue, Context context, int position) {
        dataItem = labelValue;
        mContext = context;
        if(null!=labelValue) {
            tvCurrentItem.setText(dataItem.getLabel());
        }
    }

    @Override
    public void viewRecycled() {

    }
    @OnClick(R.id.li_current_status)
    public void onCurrentStatusClick() {
        viewInterface.handleOnClick(dataItem, liCurrentStatus);
    }
    @Override
    public void onClick(View view) {

    }

}
