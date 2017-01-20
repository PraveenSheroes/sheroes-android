package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.AdapterHolder;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class HomeSpinnerSelectorHolder extends BaseViewHolder<HomeSpinnerItem>{
    private final String TAG = LogUtils.makeLogTag(HomeSpinnerSelectorHolder.class);
    @Bind(R.id.li_spinner_iten)
    LinearLayout liSpinnerItem;
    @Bind(R.id.tv_spinner)
    TextView tvSpinner;
    @Bind(R.id.checkbox_spinner)
    CheckBox cbSpinner;
    BaseHolderInterface viewInterface;
    private HomeSpinnerItem dataItem;
    private int position;
    public HomeSpinnerSelectorHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(HomeSpinnerItem item, Context context, int position) {
        this.dataItem = item;
        String spinnerItemName=item.getName();
        tvSpinner.setText(spinnerItemName);
        tvSpinner.setText(spinnerItemName);
        cbSpinner.setChecked(dataItem.isChecked());
        AdapterHolder adapterHolder = new AdapterHolder();
        adapterHolder.setPosition(position);
        tvSpinner.setTag(adapterHolder);
        liSpinnerItem.setOnClickListener(this);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_spinner_iten:
                AdapterHolder adapterHolder = (AdapterHolder) tvSpinner.getTag();
                int position = adapterHolder.getPosition();
                dataItem.setChecked(!cbSpinner.isChecked());
                cbSpinner.setChecked(!cbSpinner.isChecked());
                viewInterface.setListData(dataItem,cbSpinner.isChecked());
               // checkBoxClick(cbSpinner, position);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }
    private void checkBoxClick(CheckBox checkBox, int position)
    {
        if (checkBox.isChecked())
        {
            dataItem.setChecked(checkBox.isChecked());
            cbSpinner.setChecked(checkBox.isChecked());
            viewInterface.setListData(dataItem,checkBox.isChecked());
        }
        else
        {
            cbSpinner.setChecked(true);
            viewInterface.setListData(dataItem,true);
        }
    }
}
