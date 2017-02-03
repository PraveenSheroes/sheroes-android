package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class DrawerViewHolder extends BaseViewHolder<DrawerItems> {
    private final String TAG = LogUtils.makeLogTag(DrawerViewHolder.class);
    BaseHolderInterface viewInterface;
    private DrawerItems dataItem;
    @Bind(R.id.tv_drawer_item)
    TextView tvDrawerItem;

    public DrawerViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(DrawerItems item, Context context, int position) {
        this.dataItem = item;
        tvDrawerItem.setOnClickListener(this);
        int drawerItemId = item.getId();
        switch (drawerItemId) {
            case 1:
                tvDrawerItem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_profile), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_PROFILE));
                break;
            case 2:
                tvDrawerItem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_article), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_ARTICLE));
                break;
            case 3:
                tvDrawerItem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_job), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_JOBS));
                break;
            case 4:
                tvDrawerItem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_bookmark), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_BOOKMARK));
                break;
            case 5:
                tvDrawerItem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_setting), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_SETTING));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + drawerItemId);
        }

    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_drawer_item:
                viewInterface.handleOnClick(dataItem, view);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}