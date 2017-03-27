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
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class HomeSpinnerSelectorHolder extends BaseViewHolder<HomeSpinnerItem> {
    private final String TAG = LogUtils.makeLogTag(HomeSpinnerSelectorHolder.class);
    @Bind(R.id.li_article_spinner_iten)
    LinearLayout liSpinnerItem;
    @Bind(R.id.tv_spinner)
    TextView tvSpinner;
    @Bind(R.id.checkbox_spinner)
    CheckBox cbSpinner;
    BaseHolderInterface viewInterface;
    private HomeSpinnerItem dataItem;

    public HomeSpinnerSelectorHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(HomeSpinnerItem item, Context context, int position) {
        this.dataItem = item;
        if(StringUtil.isNotNullOrEmptyString(dataItem.getName())) {
            tvSpinner.setText( dataItem.getName());
            cbSpinner.setChecked(dataItem.isChecked());
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.li_article_spinner_iten)
    public void checkBoxClick() {
        dataItem.setChecked(!cbSpinner.isChecked());
        cbSpinner.setChecked(!cbSpinner.isChecked());
        if(cbSpinner.isChecked()) {
            liSpinnerItem.setBackgroundResource(R.drawable.rectangle_grey_round_corner);
        }
        else
        {
            liSpinnerItem.setBackgroundResource(R.color.white);
        }
        viewInterface.setListData(dataItem, cbSpinner.isChecked());
    }


    @Override
    public void onClick(View view) {

    }

}
