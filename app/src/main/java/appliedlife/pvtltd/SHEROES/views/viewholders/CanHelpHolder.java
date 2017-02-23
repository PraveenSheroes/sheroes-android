package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.can_help;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 22-02-2017.
 */

public class CanHelpHolder extends BaseViewHolder<can_help> {
    @Bind(R.id.tv_can_help)
    TextView tv_can_help;

    @Bind(R.id.tv_can_help1)
    TextView tv_can_help1;

    @Bind(R.id.tv_can_help2)
    TextView tv_can_help2;

    @Bind(R.id.tv_can_help3)
    TextView tv_can_help3;

    @Bind(R.id.tv_can_help4)
    TextView tv_can_help4;

    @Bind(R.id.tv_can_help5)
    TextView tv_can_help5;

    @Bind(R.id.tv_can_help6)
    TextView tv_can_help6;


    @Bind(R.id.tv_can_help7)
    TextView tv_can_help7;

    @Bind(R.id.tv_can_help8)
    TextView tv_can_help8;

    @Bind(R.id.tv_can_help9)
    TextView tv_can_help9;

    @Bind(R.id.tv_can_help10)
    TextView tv_can_help10;

    @Bind(R.id.tv_can_help11)
    TextView tv_can_help11;

    @Bind(R.id.tv_can_help12)
    TextView tv_can_help12;

    @Bind(R.id.tv_can_help13)
    TextView tv_can_help13;

    @Bind(R.id.tv_can_help14)
    TextView tv_can_help14;

    @Bind(R.id.rvl)
    RelativeLayout rvl;
    BaseHolderInterface viewInterface;

    private can_help dataItem;


    public CanHelpHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
       /* RecyclerView recyclerView = (RecyclerView) itemView.getParent();
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int spanSize = gridLayoutManager.getSpanSizeLookup().getSpanSize(getLayoutPosition());*/
    }

    @Override
    public void bindData(can_help obj, Context context, int position) {
        dataItem=obj;
        tv_can_help.setText(dataItem.getItem1());
        tv_can_help1.setText(dataItem.getItem2());
        tv_can_help2.setText(dataItem.getItem3());
        tv_can_help3.setText(dataItem.getItem4());
        tv_can_help4.setText(dataItem.getItem5());
        tv_can_help5.setText(dataItem.getItem6());
        tv_can_help6.setText(dataItem.getItem7());
        tv_can_help7.setText(dataItem.getItem8());
        tv_can_help8.setText(dataItem.getItem9());
        tv_can_help9.setText(dataItem.getItem10());
        tv_can_help10.setText(dataItem.getItem11());
        tv_can_help11.setText(dataItem.getItem12());
        tv_can_help12.setText(dataItem.getItem13());
        tv_can_help13.setText(dataItem.getItem14());
        tv_can_help14.setText(dataItem.getItem15());
        RelativeLayout layout = new RelativeLayout(context);
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params6.setMargins(5,20,5,10);
      //  RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_can_help1.getLayoutParams();
        params4.addRule(RelativeLayout.BELOW,tv_can_help.getId());
        params5.addRule(RelativeLayout.BELOW,tv_can_help1.getId());
        // Apply the updated layout parameters to TextView
       // tv_can_help1.setLayoutParams(lp);
        rvl.removeAllViews();
        rvl.addView(tv_can_help,params6);
        rvl.addView(tv_can_help1,params4);
        rvl.addView(tv_can_help2,params5);
        }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
