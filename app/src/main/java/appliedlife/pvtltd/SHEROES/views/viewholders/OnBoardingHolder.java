package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 22-02-2017.
 */

public class OnBoardingHolder extends BaseViewHolder<OnBoardingData> {

    @Bind(R.id.rvl)
    RelativeLayout rvl;
    BaseHolderInterface viewInterface;

    private OnBoardingData dataItem;


    public OnBoardingHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(OnBoardingData onBoardingData, Context context, int position) {
        dataItem = onBoardingData;

    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
