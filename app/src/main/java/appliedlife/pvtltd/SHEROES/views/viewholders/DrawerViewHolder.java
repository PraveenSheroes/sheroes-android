package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class DrawerViewHolder extends BaseViewHolder<DrawerItems> {
    private final String TAG = LogUtils.makeLogTag(DrawerViewHolder.class);
    BaseHolderInterface viewInterface;
    private DrawerItems dataItem;
    private int position;

    public DrawerViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(DrawerItems item, Context context, int position) {
        this.dataItem = item;
        itemView.setOnClickListener(this);
    }

    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(dataItem, view);
        int id = view.getId();
        switch (id) {
         //   case R.id.iv_dashboard:
          //      break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}