package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.CityListData;
import butterknife.ButterKnife;

public class FooterViewHolder extends BaseViewHolder<CityListData> {
    BaseHolderInterface viewInterface;
    private CityListData dataItem;
    private int position;

    public FooterViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(CityListData item, Context context, int position) {
        this.dataItem = item;
        itemView.setOnClickListener(this);
    }

    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(this.dataItem, view);
    }

}